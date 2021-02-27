package pers.store.market.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午4:13
 * @description: 订单服务页面跳转controller
 */
@Controller
public class OrderWebController {

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
    public String confirmPage(){

        return "confirm";
    }

}
