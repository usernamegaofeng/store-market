package pers.store.market.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pers.store.market.common.constant.OrderConstant;
import pers.store.market.common.domain.vo.MemberVo;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.enums.OrderStatusEnum;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;
import pers.store.market.common.utils.R;
import pers.store.market.order.dao.OrderDao;
import pers.store.market.order.entity.OrderEntity;
import pers.store.market.order.entity.OrderItemEntity;
import pers.store.market.order.exception.NoStockException;
import pers.store.market.order.feign.CartFeignService;
import pers.store.market.order.feign.MemberFeignService;
import pers.store.market.order.feign.ProductFeignService;
import pers.store.market.order.feign.WareFeignService;
import pers.store.market.order.interceptor.OrderLoginInterceptor;
import pers.store.market.order.service.OrderItemService;
import pers.store.market.order.service.OrderService;
import pers.store.market.order.vo.*;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private ThreadLocal<OrderSubmitVo> orderSubmitContext = new ThreadLocal<>();

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private CartFeignService cartFeignService;
    @Autowired
    private MemberFeignService memberFeignService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取订单确认页数据接口
     *
     * @return OrderConfirmVo
     */
    @Override
    public OrderConfirmVo confirmOrders() {
        MemberVo memberVo = OrderLoginInterceptor.threadLocal.get();
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        //获取请求上下文对象
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //异步编排获取所有收货地址
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            //异步情况下,可能存在多线程操作丢失请求头数据的情况,需要重新放到上下文容器中去
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddressVo> address = memberFeignService.getAddress(memberVo.getId());
            orderConfirmVo.setAddress(address);
            orderConfirmVo.setIntegration(memberVo.getIntegration());
        }, threadPoolExecutor);

        //异步编排获取所有的购物项
        CompletableFuture<Void> itemFuture = CompletableFuture.runAsync(() -> {
            //每一个线程都来共享之前的请求数据
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItemVo> userCartItems = cartFeignService.getUserCartItems();
            orderConfirmVo.setItems(userCartItems);
        }, threadPoolExecutor).thenRunAsync(() -> {
            List<OrderItemVo> items = orderConfirmVo.getItems();
            List<Long> skuIds = items.stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
            //获取库存数据
            List<SkuHasStockVo> skuHasStockVos = wareFeignService.hasStock(skuIds);
            if (skuHasStockVos != null) {
                Map<Long, Boolean> stocks = skuHasStockVos.stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
                orderConfirmVo.setStocks(stocks);
            }
        }, threadPoolExecutor);
        //为用户设置一个token，三十分钟过期时间（存在redis,防止重复调用下单,接口幂等性
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberVo.getId(), token, 30, TimeUnit.MINUTES);
        orderConfirmVo.setOrderToken(token);

        try {
            CompletableFuture.allOf(addressFuture, itemFuture).get();
        } catch (InterruptedException e) {
            log.error("订单确认页数据异步编排出现异常 ===> {}", e.getMessage());
        } catch (ExecutionException e) {
            log.error("订单确认页数据异步编排出现异常 ===> {}", e.getMessage());
        }
        return orderConfirmVo;
    }

    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) {
        //每个线程存放数据
        orderSubmitContext.set(vo);
        SubmitOrderResponseVo response = new SubmitOrderResponseVo();
        MemberVo memberVo = OrderLoginInterceptor.threadLocal.get();
        response.setCode(0);

        //验证令牌是否合法【令牌的对比和删除必须保证原子性】
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        String orderToken = vo.getOrderToken();
        //执行lua脚本
        Long result = (Long) redisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberVo.getId()),
                orderToken);

        if (result.intValue() == 0) {
            //令牌验证失败
            response.setCode(1);
            return response;
        } else {
            //令牌验证成功
            OrderCreateDto order = createOrder();
            //验证价格
            BigDecimal payAmount = order.getOrder().getPayAmount();
            BigDecimal payPrice = vo.getPayPrice();

            //两者金额差额小于0.01忽略不计
            if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
                //保存订单
                saveOrder(order);
                //库存锁定,只要有异常，回滚订单数据
                WareSkuLockVo lockVo = new WareSkuLockVo();
                lockVo.setOrderSn(order.getOrder().getOrderSn());

                //获取出要锁定的商品数据信息
                List<OrderItemVo> orderItemVos = order.getOrderItems().stream().map((item) -> {
                    OrderItemVo orderItemVo = new OrderItemVo();
                    orderItemVo.setSkuId(item.getSkuId());
                    orderItemVo.setCount(item.getSkuQuantity());
                    orderItemVo.setTitle(item.getSkuName());
                    return orderItemVo;
                }).collect(Collectors.toList());
                lockVo.setLocks(orderItemVos);
                //扣减库存成功了，但是由于网络原因超时，出现异常，导致订单事务回滚，库存事务不回滚(解决方案：seata)
                //为了保证高并发，不推荐使用seata的AT模式，因为是加锁，并行化，提升不了效率,可以发消息给库存服务,(解决方案:rabbitmq延时队列,柔性事务:可靠消息+最终一致性方案)
                R r = wareFeignService.orderLockStock(lockVo);
                if (r.getCode() == 0) {
                    //锁定成功
                    response.setOrder(order.getOrder());
//                    // int i = 10/0;
//
//                    //TODO 订单创建成功，发送消息给MQ
//                    rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", order.getOrder());
//
//                    //删除购物车里的数据
//                    redisTemplate.delete(CART_PREFIX + memberResponseVo.getId());
//                    return responseVo;
                } else {
                    //锁定失败
                    String msg = (String) r.get("msg");
                    throw new NoStockException(msg);
                    // responseVo.setCode(3);
                    // return responseVo;
                }
            } else {
                response.setCode(2);
                return response;
            }
        }
        return response;
    }

    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {
        return this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
    }

    //保存订单
    private void saveOrder(OrderCreateDto orderCreateDto) {
        OrderEntity order = orderCreateDto.getOrder();
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        this.save(order);
        orderItemService.saveBatch(orderCreateDto.getOrderItems());
    }

    //创建订单所有数据
    private OrderCreateDto createOrder() {
        OrderCreateDto createTo = new OrderCreateDto();
        //生成订单号
        String orderSn = IdWorker.getTimeId();
        OrderEntity orderEntity = builderOrder(orderSn);
        //获取到所有的订单项
        List<OrderItemEntity> orderItemEntities = builderOrderItems(orderSn);
        //验价(计算价格、积分等信息)
        computePrice(orderEntity, orderItemEntities);
        createTo.setOrder(orderEntity);
        createTo.setOrderItems(orderItemEntities);
        return createTo;
    }

    //验价格
    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {
        //总价
        BigDecimal total = new BigDecimal("0.0");
        //优惠价
        BigDecimal coupon = new BigDecimal("0.0");
        //积分
        BigDecimal intergration = new BigDecimal("0.0");
        BigDecimal promotion = new BigDecimal("0.0");
        //积分
        Integer integrationTotal = 0;
        //成长值
        Integer growthTotal = 0;
        //订单总额，叠加每一个订单项的总额信息
        for (OrderItemEntity orderItem : orderItemEntities) {
            //优惠价格信息
            coupon = coupon.add(orderItem.getCouponAmount());
            promotion = promotion.add(orderItem.getPromotionAmount());
            intergration = intergration.add(orderItem.getIntegrationAmount());
            //总价
            total = total.add(orderItem.getRealAmount());
            //积分信息
            integrationTotal += orderItem.getGiftIntegration();
            //成长值信息
            growthTotal += orderItem.getGiftGrowth();
        }
        //1、订单价格相关的
        orderEntity.setTotalAmount(total);
        //设置应付总额(总额+运费)
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));
        orderEntity.setCouponAmount(coupon);
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(intergration);

        //设置积分成长值信息
        orderEntity.setIntegration(integrationTotal);
        orderEntity.setGrowth(growthTotal);

        //设置删除状态(0-未删除，1-已删除)
        orderEntity.setDeleteStatus(0);
    }

    //构建所有订单项数据
    private List<OrderItemEntity> builderOrderItems(String orderSn) {
        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();
        //最后确定每个购物项的价格
        List<OrderItemVo> currentCartItems = cartFeignService.getUserCartItems();
        if (currentCartItems != null && currentCartItems.size() > 0) {
            orderItemEntityList = currentCartItems.stream().map((items) -> {
                //构建订单项数据
                OrderItemEntity orderItemEntity = builderOrderItem(items);
                orderItemEntity.setOrderSn(orderSn);
                return orderItemEntity;
            }).collect(Collectors.toList());
        }
        return orderItemEntityList;
    }

    //构建某一个订单项的数据
    private OrderItemEntity builderOrderItem(OrderItemVo items) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        //商品的spu信息
        Long skuId = items.getSkuId();
        //远程服务获取spu的信息
        R spuInfo = productFeignService.getSpuInfoBySkuId(skuId);
        SpuInfoVo spuInfoData = JSON.parseObject(JSON.toJSONString(spuInfo.get("data")), SpuInfoVo.class);
        orderItemEntity.setSpuId(spuInfoData.getId());
        orderItemEntity.setSpuName(spuInfoData.getSpuName());
        orderItemEntity.setSpuBrand(spuInfoData.getBrandName());
        orderItemEntity.setCategoryId(spuInfoData.getCatalogId());

        //商品的sku信息
        orderItemEntity.setSkuId(skuId);
        orderItemEntity.setSkuName(items.getTitle());
        orderItemEntity.setSkuPic(items.getImage());
        orderItemEntity.setSkuPrice(items.getPrice());
        orderItemEntity.setSkuQuantity(items.getCount());

        //使用StringUtils.collectionToDelimitedString将list集合转换为String
        String skuAttrValues = StringUtils.collectionToDelimitedString(items.getSkuAttrValues(), ";");
        orderItemEntity.setSkuAttrsVals(skuAttrValues);

        //商品的积分信息
        orderItemEntity.setGiftGrowth(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());
        orderItemEntity.setGiftIntegration(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());

        //订单项的价格信息
        orderItemEntity.setPromotionAmount(BigDecimal.ZERO);
        orderItemEntity.setCouponAmount(BigDecimal.ZERO);
        orderItemEntity.setIntegrationAmount(BigDecimal.ZERO);

        //当前订单项的实际金额.总额 - 各种优惠价格
        //原来的价格
        BigDecimal origin = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity().toString()));
        //原价减去优惠价得到最终的价格
        BigDecimal subtract = origin.subtract(orderItemEntity.getCouponAmount())
                .subtract(orderItemEntity.getPromotionAmount())
                .subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(subtract);

        return orderItemEntity;

    }

    //构建大订单数据
    private OrderEntity builderOrder(String orderSn) {
        //获取当前用户登录信息
        MemberVo memberVo = OrderLoginInterceptor.threadLocal.get();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setMemberId(memberVo.getId());
        orderEntity.setOrderSn(orderSn);
        orderEntity.setMemberUsername(memberVo.getUsername());
        //获取线程中的数据
        OrderSubmitVo orderSubmitVo = orderSubmitContext.get();
        //远程获取收货地址和运费信息
        FareVo fareVo = wareFeignService.getFare(orderSubmitVo.getAddrId());


        //获取到运费信息
        BigDecimal fare = fareVo.getFare();
        orderEntity.setFreightAmount(fare);
        //获取到收货地址信息
        MemberAddressVo address = fareVo.getAddress();
        //设置收货人信息
        orderEntity.setReceiverName(address.getName());
        orderEntity.setReceiverPhone(address.getPhone());
        orderEntity.setReceiverPostCode(address.getPostCode());
        orderEntity.setReceiverProvince(address.getProvince());
        orderEntity.setReceiverCity(address.getCity());
        orderEntity.setReceiverRegion(address.getRegion());
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());
        //设置订单相关的状态信息
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setAutoConfirmDay(7);
        orderEntity.setConfirmStatus(0);
        return orderEntity;
    }

}