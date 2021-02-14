package pers.store.market.search.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Gaofeng
 * @date 2021/2/13 上午10:09
 * @description: 页面跳转控制器
 */
@Controller
public class WebController {

    @GetMapping(value = "/list.html")
    public String listPage() {
        return "list";
    }
}
