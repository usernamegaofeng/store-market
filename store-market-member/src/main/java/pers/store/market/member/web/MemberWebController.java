package pers.store.market.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Gaofeng
 * @date 2021/3/13 上午11:39
 * @description:
 */
@Controller
public class MemberWebController {

    @GetMapping("/memberOrder.html")
    public String orderListPage(){
        return "orderList";
    }
}
