package pers.store.market.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pers.store.market.cart.service.CartService;
import pers.store.market.cart.vo.CartItemVo;
import pers.store.market.cart.vo.CartVo;

import java.util.List;

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
    public String cartListPage(Model model) {
        CartVo cartVo = cartService.getCart();
        model.addAttribute("cart", cartVo);
        return "cartList";
    }

    @GetMapping("/addCartItem")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") int num, RedirectAttributes redirectAttributes) {
        cartService.addCartItemToCart(skuId, num);
        redirectAttributes.addAttribute("skuId", skuId);   //重定向后放在redirectAttributes的参数会拼接在请求地址后面
        return "redirect:http://localhost:9700/addCartItemSuccess";
    }

    @RequestMapping("/addCartItemSuccess")
    public String addCartItemSuccess(@RequestParam("skuId") Long skuId, Model model) {
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItemVo);
        return "success";
    }

    @RequestMapping("/checkCart")
    public String checkCart(@RequestParam("isChecked") Integer isChecked,@RequestParam("skuId")Long skuId) {
        cartService.checkCart(skuId, isChecked);
        return "redirect:http://localhost:9700/cart.html";
    }

    @RequestMapping("/countItem")
    public String changeItemCount(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {
        cartService.changeItemCount(skuId, num);
        return "redirect:http://localhost:9700/cart.html";
    }

    @RequestMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {
        cartService.deleteItem(skuId);
        return "redirect:http://localhost:9700/cart.html";
    }

    @ResponseBody
    @RequestMapping("/getCheckedItems")
    public List<CartItemVo> getCheckedItems() {
        return cartService.getCheckedItems();
    }

    @ResponseBody
    @RequestMapping("/getUserCartItems")
    public List<CartItemVo> getUserCartItems(){
        return cartService.getCurrentUserCartItems();
    }
}
