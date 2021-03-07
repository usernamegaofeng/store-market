package pers.store.market.ware.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import pers.store.market.common.constant.RabbitmqConstant;
import pers.store.market.common.domain.dto.OrderDto;
import pers.store.market.common.domain.dto.mq.StockDetailDto;
import pers.store.market.common.domain.dto.mq.StockLockedDto;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.enums.OrderStatusEnum;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.common.utils.R;
import pers.store.market.ware.dao.WareSkuDao;
import pers.store.market.ware.entity.WareOrderTaskDetailEntity;
import pers.store.market.ware.entity.WareOrderTaskEntity;
import pers.store.market.ware.entity.WareSkuEntity;
import pers.store.market.ware.exception.NoStockException;
import pers.store.market.ware.feign.OrderFeignService;
import pers.store.market.ware.feign.ProductFeignService;
import pers.store.market.ware.service.WareOrderTaskDetailService;
import pers.store.market.ware.service.WareOrderTaskService;
import pers.store.market.ware.service.WareSkuService;
import pers.store.market.ware.vo.OrderItemVo;
import pers.store.market.ware.vo.SkuWareStockVo;
import pers.store.market.ware.vo.WareSkuLockVo;

@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuDao wareSkuDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderFeignService orderFeignService;
    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private WareOrderTaskService wareOrderTaskService;
    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        List<WareSkuEntity> skuList = this.baseMapper.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        //判断该库存是否存在,没有就新增
        if (skuList.size() == 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            try {
                R info = productFeignService.info(skuId);
                if (info.getCode() == 0) {
                    Map<String, Object> map = (Map<String, Object>) info.get("skuInfo");
                    wareSkuEntity.setSkuName((String) map.get("skuName"));
                }
            } catch (Exception e) {
                log.error("远程调用[ProductFeignService]失败,失败原因 ===> {}", e.getMessage());
            }
            wareSkuDao.insert(wareSkuEntity);
        } else {
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }
    }

    /**
     * 判断是sku是否有库存
     *
     * @param skuIds ID数组
     * @return List<SkuHasStockVo>
     */
    @Override
    public List<SkuHasStockVo> hasStock(List<Long> skuIds) {
        List<SkuHasStockVo> list = skuIds.stream().map(item -> {
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            Long count = this.baseMapper.getStockById(item);
            skuHasStockVo.setSkuId(item);
            skuHasStockVo.setHasStock(count == null ? false : count > 0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 锁定库存
     *
     * @param vo
     * @return
     */
    @Transactional
    @Override
    public boolean orderLockStock(WareSkuLockVo vo) {
        List<OrderItemVo> locks = vo.getLocks();
        //查询哪个库存有库存
        List<SkuWareStockVo> collect = locks.stream().map(item -> {
            SkuWareStockVo skuWareStockVo = new SkuWareStockVo();
            skuWareStockVo.setSkuId(item.getSkuId());
            skuWareStockVo.setNum(item.getCount());
            List<Long> wareIds = wareSkuDao.listWareIdHasStock(item.getSkuId());
            skuWareStockVo.setWareId(wareIds);
            return skuWareStockVo;
        }).collect(Collectors.toList());
        //创建库存工作单
        WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
        wareOrderTaskEntity.setOrderSn(vo.getOrderSn());
        wareOrderTaskService.save(wareOrderTaskEntity);

        //锁定库存
        for (SkuWareStockVo skuWareStockVo : collect) {
            Long skuId = skuWareStockVo.getSkuId();
            List<Long> wareIds = skuWareStockVo.getWareId();
            if (wareIds == null || wareIds.size() == 0) {
                throw new NoStockException("该商品库存不足!");
            }
            boolean hasLock = false;
            for (Long wareId : wareIds) {
                //锁定库存
                Integer count = wareSkuDao.orderLockStocks(skuId, wareId, skuWareStockVo.getNum());
                //成功锁库存影响行数为1,没有锁定就为0
                if (count == 1) {
                    hasLock = true;
                    //每锁定一次库存生成一个库存工作单详情
                    WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity(null, skuId, null, skuWareStockVo.getNum(), wareOrderTaskEntity.getId(), wareId, 1);
                    wareOrderTaskDetailService.save(detailEntity);

                    //发送库存锁定消息至延迟队列
                    StockLockedDto lockedDto = new StockLockedDto();
                    lockedDto.setId(wareOrderTaskEntity.getId());
                    StockDetailDto detailTo = new StockDetailDto();
                    BeanUtils.copyProperties(detailEntity, detailTo);
                    lockedDto.setDetailDto(detailTo);
                    rabbitTemplate.convertAndSend(RabbitmqConstant.STOCK_EVENT_EXCHANGE, RabbitmqConstant.STOCK_LOCK_ROUTING_KEY, lockedDto);
                    break;
                } else {
                    //当前仓库没有库存,继续锁定下个库存
                }
            }
            if (hasLock == false) {
                //所有的仓库都没有库存
                throw new NoStockException("该商品库存不足!");
            }
        }
        return true;
    }

    @Override
    public void releaseStock(StockLockedDto stockLockedDto) {
        StockDetailDto detailDto = stockLockedDto.getDetailDto();
        WareOrderTaskDetailEntity detailEntity = wareOrderTaskDetailService.getById(detailDto.getId());
        if (detailEntity == null) {
            //锁库详情纪录被回滚了,说明锁库时发生异常,无需继续解锁
        } else {
            /**
             *  1、根据锁库任务单Id查出 wms_ware_order_task锁库任务单的信息
             *  2、任务单保存了订单的Id、根据订单Id远程查询该条订单数据
             *  3、结果讨论
             *      如果查询订单纪录为null、说明库存锁定成功后、订单创建的后续逻辑发生错误、订单被回滚
             *      如果查询的订单记录为已取消、说明用户在规定时间内未支付、或者主动取消支付。
             */
            WareOrderTaskEntity wareOrderTaskEntity = wareOrderTaskService.getById(stockLockedDto.getId());
            String orderSn = wareOrderTaskEntity.getOrderSn();
            R r = orderFeignService.getOrderBySn(orderSn);
            if (r.getCode() == 0) {
                OrderDto data = JSON.parseObject(JSON.toJSONString(r.get("data")), OrderDto.class);
                if (data == null || data.getStatus().equals(OrderStatusEnum.CANCELED.getCode())) {
                    //为保证幂等性，只有当工作单详情处于被锁定的情况下才进行解锁
                    if (detailEntity.getLockStatus() == 1) {
                        this.unlockStock(detailDto.getSkuId(), detailDto.getWareId(), detailDto.getSkuNum(), detailEntity.getId());
                    }
                }
            } else {
                throw new RuntimeException("远程调用订单服务失败");
            }
        }


    }

    //修改库存
    private void unlockStock(Long skuId, Long wareId, Integer num, Long detailId) {
        wareSkuDao.unlockStock(skuId, wareId, num);
        //更新工作单的状态
        WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity();
        wareOrderTaskDetailEntity.setId(detailId);
        wareOrderTaskDetailEntity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(wareOrderTaskDetailEntity);
    }

}