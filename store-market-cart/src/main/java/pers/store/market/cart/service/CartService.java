package pers.store.market.cart.service;

import pers.store.market.cart.vo.CartItemVo;
import pers.store.market.cart.vo.CartVo;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:18
 * @description: 购物车接口
 */
public interface CartService {

    CartItemVo addCartItemToCart(Long skuId, int num);

    CartItemVo getCartItem(Long skuId);

    CartVo getCart();

    void checkCart(Long skuId, Integer isChecked);

    void changeItemCount(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItemVo> getCheckedItems();

    List<CartItemVo> getCurrentUserCartItems();
}
