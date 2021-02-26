package pers.store.market.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pers.store.market.cart.feign.ProductFeignService;
import pers.store.market.cart.interceptor.CartInterceptor;
import pers.store.market.cart.service.CartService;
import pers.store.market.cart.vo.CartItemVo;
import pers.store.market.cart.vo.SkuInfoVo;
import pers.store.market.common.domain.dto.UserInfoContent;
import pers.store.market.common.utils.R;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:18
 * @description: 购物车接口实现类
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final String CART_PREFIX = "cart:";

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProductFeignService productFeignService;

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

    @Override
    public CartItemVo getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> redisOperations = getRedisOperations();
        String redisJson = (String) redisOperations.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(redisJson, CartItemVo.class);
        return cartItemVo;
    }

    //构造Redis操作对象
    private BoundHashOperations<String, Object, Object> getRedisOperations() {
        UserInfoContent userInfoContent = CartInterceptor.userContent.get();
        String cartKey = "";
        if (userInfoContent.getUserId() != null) {
            cartKey = cartKey + CART_PREFIX + userInfoContent.getUserId();
        } else {
            cartKey = cartKey + CART_PREFIX + userInfoContent.getUserKey();
        }
        BoundHashOperations<String, Object, Object> operations = stringRedisTemplate.boundHashOps(cartKey);
        return operations;
    }


}
