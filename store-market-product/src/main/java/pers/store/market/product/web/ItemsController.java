package pers.store.market.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.store.market.product.service.SkuInfoService;
import pers.store.market.product.vo.SkuItemVo;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午6:23
 * @description: 商品详情页控制器
 */
@Controller
public class ItemsController {

    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String item(@PathVariable("skuId") Long skuId, Model model){
        SkuItemVo skuItemVo = skuInfoService.itemInfo(skuId);
        model.addAttribute("item",skuItemVo);
        return "item";
    }
}
