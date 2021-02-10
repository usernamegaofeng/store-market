package pers.store.market.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.store.market.product.entity.CategoryEntity;
import pers.store.market.product.service.CategoryService;
import pers.store.market.product.vo.CategoryLevel2Vo;

import java.util.List;
import java.util.Map;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午9:26
 * @description: 首页跳转控制器
 */
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> dataList = categoryService.getCategoryLevelToOne();
        model.addAttribute("category", dataList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<CategoryLevel2Vo>> getCategoryJson() {
        Map<String, List<CategoryLevel2Vo>> categoryJson = categoryService.getCategoryJson();
        return categoryJson;
    }

}
