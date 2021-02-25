package pers.store.market.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pers.store.market.cart.interceptor.CartInterceptor;
import pers.store.market.common.domain.dto.UserInfoContent;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:17
 * @description: 购物车控制器
 */
@Controller
public class CartController {

    @GetMapping("/cart.html")
    public String cartListPage() {
        UserInfoContent userInfoContent = CartInterceptor.userContent.get();
        System.out.println(userInfoContent);
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart() {
        return "success";
    }

}
