package pers.store.market.search.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.store.market.common.domain.model.SkuEsModel;
import pers.store.market.common.enums.ResultEnum;
import pers.store.market.common.utils.R;
import pers.store.market.search.service.SearchSaveService;

import java.io.IOException;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午4:28
 * @description: 搜索服务前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchSaveService searchSaveService;

    @PostMapping("/product/save")
    public R saveIndex(@RequestBody List<SkuEsModel> skuEsModelList) {
        boolean flag = false;
        try {
            flag = searchSaveService.saveIndex(skuEsModelList);
        } catch (IOException e) {
            log.error("保存索引失败,错误原因 ===> {}", e.getMessage());
            return R.error(ResultEnum.PRODUCT_UP_ERROR.getCode(), ResultEnum.PRODUCT_UP_ERROR.getMsg());
        }
        if (flag) {
            log.error("商品上架失败,可能存在异常");
            return R.error(ResultEnum.PRODUCT_UP_ERROR.getCode(), ResultEnum.PRODUCT_UP_ERROR.getMsg());
        } else {
            return R.ok();
        }
    }
}
