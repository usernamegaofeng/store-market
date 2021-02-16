package pers.store.market.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午6:23
 * @description: 商品详情页控制器
 */
@Controller
public class ItemsController {

    @GetMapping("/{spuId}.html")
    public String item(@PathVariable("spuId") Long spuId){
        return "item";
    }
}
