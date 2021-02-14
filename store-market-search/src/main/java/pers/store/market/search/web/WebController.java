package pers.store.market.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pers.store.market.search.service.SearchService;
import pers.store.market.search.vo.SearchParam;
import pers.store.market.search.vo.SearchResult;

/**
 * @author Gaofeng
 * @date 2021/2/13 上午10:09
 * @description: 页面跳转控制器
 */
@Controller
public class WebController {

    @Autowired
    private SearchService searchService;

    @GetMapping(value = "/list.html")
    public String searchPage(SearchParam searchParam, Model model) {
        SearchResult searchResult = searchService.search(searchParam);
        model.addAttribute("result", searchResult);
        return "list";
    }
}
