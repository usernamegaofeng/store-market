package pers.store.market.cart.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pers.store.market.cart.feign.ProductFeignService;
import pers.store.market.cart.interceptor.CartInterceptor;
import pers.store.market.cart.service.CartService;
import pers.store.market.cart.vo.CartItemVo;
import pers.store.market.cart.vo.CartVo;
import pers.store.market.cart.vo.SkuInfoVo;
import pers.store.market.common.constant.CartConstant;
import pers.store.market.common.domain.dto.UserInfoContent;
import pers.store.market.common.utils.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:18
 * @description: 购物车接口实现类
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProductFeignService productFeignService;

    /**
     * 添加到购物车
     *
     * @param skuId
     * @param num
     * @return
     */
    @Override
    public CartItemVo addCartItemToCart(Long skuId, int num) {
        BoundHashOperations<String, Object, Object> redisOperations = getRedisOperations();
        String redisValue = (String) redisOperations.get(skuId.toString());
        if (StringUtils.isBlank(redisValue)) {
            CartItemVo cartItemVo = new CartItemVo();
            //远程查询sku信息,异步编排
            CompletableFuture<Void> baseInfoFuture = CompletableFuture.runAsync(() -> {
                R r = productFeignService.getSkuInfo(skuId);
                if (r.getCode() == 0) {
                    SkuInfoVo skuInfo = JSON.parseObject(JSON.toJSONString(r.get("skuInfo")), SkuInfoVo.class);
                    cartItemVo.setSkuId(skuId);
                    cartItemVo.setCount(num);
                    cartItemVo.setCheck(true);
                    cartItemVo.setPrice(skuInfo.getPrice());
                    cartItemVo.setTitle(skuInfo.getSkuTitle());
                    cartItemVo.setImage(skuInfo.getSkuDefaultImg());
                }
            }, threadPoolExecutor);
            CompletableFuture<Void> skuSaleAttrFuture = CompletableFuture.runAsync(() -> {
                List<String> stringList = productFeignService.getSkuSaleAttrAsStringList(skuId);
                cartItemVo.setSkuAttrValues(stringList);
            }, threadPoolExecutor);
            try {
                CompletableFuture.allOf(baseInfoFuture, skuSaleAttrFuture).get();
            } catch (InterruptedException e) {
                log.error("购物车异步编排出现异常 ===> {}", e.getMessage());
            } catch (ExecutionException e) {
                log.error("购物车异步编排出现异常 ===> {}", e.getMessage());
            }
            //存入到Redis中去
            redisOperations.put(skuId.toString(), JSON.toJSONString(cartItemVo));
            return cartItemVo;
        } else {
            //如果该购物项存在就只增加数量即可
            CartItemVo cartItem = JSON.parseObject(redisValue, CartItemVo.class);
            cartItem.setCount(cartItem.getCount() + num);
            redisOperations.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        }
    }

    /**
     * 获取购物车项
     *
     * @param skuId
     * @return
     */
    @Override
    public CartItemVo getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> redisOperations = getRedisOperations();
        String redisJson = (String) redisOperations.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(redisJson, CartItemVo.class);
        return cartItemVo;
    }

    /**
     * 获取购物车数据
     *
     * @return
     */
    @Override
    public CartVo getCart() {
        CartVo cartVo = new CartVo();
        UserInfoContent userInfoContent = CartInterceptor.userContextHolder.get();
        List<CartItemVo> tempCart = new ArrayList<>();
        //1.用户未登录，直接通过user-key获取临时购物车
        if (userInfoContent.getUserId() == null) {
            tempCart = getCartByKey(userInfoContent.getUserKey());
            cartVo.setItems(tempCart);
        } else {
            //2.用户登录
            //查询userId对应的购物车
            List<CartItemVo> userCart = getCartByKey(userInfoContent.getUserId().toString());
            //查询user-key对应的临时购物车，并和用户购物车合并
            if (tempCart != null && tempCart.size() > 0) {
                for (CartItemVo cartItemVo : tempCart) {
                    userCart.add(cartItemVo);
                    //在redis中更新数据
                    addCartItemToCart(cartItemVo.getSkuId(), cartItemVo.getCount());
                }
            }
            cartVo.setItems(userCart);
            //删除临时购物车数据
            stringRedisTemplate.delete(CartConstant.CART_PREFIX + userInfoContent.getUserKey());
        }
        return cartVo;
    }


    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        BoundHashOperations<String, Object, Object> ops = getRedisOperations();
        String cartJson = (String) ops.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(cartJson, CartItemVo.class);
        cartItemVo.setCheck(isChecked == 1);
        ops.put(skuId.toString(), JSON.toJSONString(cartItemVo));
    }

    @Override
    public void changeItemCount(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> ops = getRedisOperations();
        String cartJson = (String) ops.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(cartJson, CartItemVo.class);
        cartItemVo.setCount(num);
        ops.put(skuId.toString(), JSON.toJSONString(cartItemVo));
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> ops = getRedisOperations();
        ops.delete(skuId.toString());
    }

    @Override
    public List<CartItemVo> getCheckedItems() {
        UserInfoContent userInfoTo = CartInterceptor.userContextHolder.get();
        List<CartItemVo> cartByKey = getCartByKey(userInfoTo.getUserId().toString());
        return cartByKey.stream().filter(CartItemVo::getCheck).collect(Collectors.toList());
    }

    //获取用户当前所有的购物车项,必须为登录情况下
    @Override
    public List<CartItemVo> getCurrentUserCartItems() {
        UserInfoContent userInfoContent = CartInterceptor.userContextHolder.get();
        if (userInfoContent.getUserId() != null) {
            List<CartItemVo> cartItemsList = getCartByKey(userInfoContent.getUserId().toString());
            //获取所有被选中的购物项
            List<CartItemVo> dataList = cartItemsList.stream().filter(CartItemVo::getCheck).map(item -> {
                //获取商品最新的价格
                R r = productFeignService.getCurrentPrice(item.getSkuId());
                if (r.getCode() == 0) {
                    String price = (String) r.get("price");
                    item.setPrice(new BigDecimal(price));
                }
                return item;
            }).collect(Collectors.toList());
            return dataList;
        } else {
            return null;
        }

    }


    //根据userKey获取所有的购物项列表
    private List<CartItemVo> getCartByKey(String userKey) {
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(CartConstant.CART_PREFIX + userKey);
        List<Object> values = ops.values();
        if (values != null && values.size() > 0) {
            List<CartItemVo> cartItemVos = values.stream().map(obj -> {
                String json = (String) obj;
                return JSON.parseObject(json, CartItemVo.class);
            }).collect(Collectors.toList());
            return cartItemVos;
        }
        return null;
    }

    //构造Redis操作对象
    private BoundHashOperations<String, Object, Object> getRedisOperations() {
        UserInfoContent userInfoContent = CartInterceptor.userContextHolder.get();
        String cartKey = "";
        if (userInfoContent.getUserId() != null) {
            cartKey = cartKey + CartConstant.CART_PREFIX + userInfoContent.getUserId();
        } else {
            cartKey = cartKey + CartConstant.CART_PREFIX + userInfoContent.getUserKey();
        }
        BoundHashOperations<String, Object, Object> operations = stringRedisTemplate.boundHashOps(cartKey);
        return operations;
    }


}
