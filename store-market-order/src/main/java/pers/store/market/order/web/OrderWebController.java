package pers.store.market.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pers.store.market.order.service.OrderService;
import pers.store.market.order.vo.OrderConfirmVo;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午4:13
 * @description: 订单服务页面跳转controller
 */
@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/confirm.html")
    public String confirm() {
        return "confirm";
    }

    @GetMapping("/pay.html")
    public String pay() {
        return "pay";
    }

    @GetMapping("/list.html")
    public String list() {
        return "list";
    }

    @GetMapping("/detail.html")
    public String detail() {
        return "detail";
    }

    @GetMapping("/toTrade")
    public String confirmPage(Model model){
        OrderConfirmVo orderConfirmVo = orderService.confirmOrders();
        model.addAttribute("confirmOrderData",orderConfirmVo);
        return "confirm";
    }

}
