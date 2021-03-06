package pers.store.market.ware.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.common.utils.R;
import pers.store.market.ware.dao.WareSkuDao;
import pers.store.market.ware.entity.WareSkuEntity;
import pers.store.market.ware.exception.NoStockException;
import pers.store.market.ware.feign.ProductFeignService;
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
    private ProductFeignService productFeignService;

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

}