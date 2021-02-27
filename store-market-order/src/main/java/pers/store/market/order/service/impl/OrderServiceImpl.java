package pers.store.market.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pers.store.market.common.domain.vo.MemberVo;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.common.utils.R;
import pers.store.market.order.config.OrderConfiguration;
import pers.store.market.order.dao.OrderDao;
import pers.store.market.order.entity.OrderEntity;
import pers.store.market.order.feign.CartFeignService;
import pers.store.market.order.feign.MemberFeignService;
import pers.store.market.order.feign.WmsFeignService;
import pers.store.market.order.interceptor.OrderLoginInterceptor;
import pers.store.market.order.service.OrderService;
import pers.store.market.order.vo.MemberAddressVo;
import pers.store.market.order.vo.OrderConfirmVo;
import pers.store.market.order.vo.OrderItemVo;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private WmsFeignService wmsFeignService;
    @Autowired
    private CartFeignService cartFeignService;
    @Autowired
    private MemberFeignService memberFeignService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

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
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItemVo> userCartItems = cartFeignService.getUserCartItems();
            orderConfirmVo.setItems(userCartItems);
        }, threadPoolExecutor).thenRunAsync(() -> {
            List<OrderItemVo> items = orderConfirmVo.getItems();
            List<Long> skuIds = items.stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
            //获取库存数据
            List<SkuHasStockVo> skuHasStockVos = wmsFeignService.hasStock(skuIds);
            if (skuHasStockVos != null) {
                Map<Long, Boolean> stocks = skuHasStockVos.stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
                orderConfirmVo.setStocks(stocks);
            }
        }, threadPoolExecutor);
        try {
            CompletableFuture.allOf(addressFuture, itemFuture).get();
        } catch (InterruptedException e) {
            log.error("订单确认页数据异步编排出现异常 ===> {}", e.getMessage());
        } catch (ExecutionException e) {
            log.error("订单确认页数据异步编排出现异常 ===> {}", e.getMessage());
        }
        return orderConfirmVo;
    }

}