package pers.store.market.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pers.store.market.cart.interceptor.CartInterceptor;
import pers.store.market.cart.service.CartService;
import pers.store.market.cart.vo.CartItemVo;
import pers.store.market.common.domain.dto.UserInfoContent;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:17
 * @description: 购物车控制器
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart.html")
    public String cartListPage() {
        UserInfoContent userInfoContent = CartInterceptor.userContent.get();
        System.out.println(userInfoContent);
        return "cartList";
    }

    @GetMapping("/addCartItem")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") int num, RedirectAttributes redirectAttributes) {
        cartService.addCartItemToCart(skuId,num);
        redirectAttributes.addAttribute("skuId", skuId);   //重定向后放在redirectAttributes的参数会拼接在请求地址后面
        return "redirect:http://localhost:9700/addCartItemSuccess";
    }

    @RequestMapping("/addCartItemSuccess")
    public String addCartItemSuccess(@RequestParam("skuId") Long skuId, Model model) {
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItemVo);
        return "success";
    }
}
