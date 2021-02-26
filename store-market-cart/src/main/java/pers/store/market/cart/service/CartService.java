package pers.store.market.cart.service;

import pers.store.market.cart.vo.CartItemVo;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:18
 * @description: 购物车接口
 */
public interface CartService {

    CartItemVo addCartItemToCart(Long skuId, int num);

    CartItemVo getCartItem(Long skuId);
}
