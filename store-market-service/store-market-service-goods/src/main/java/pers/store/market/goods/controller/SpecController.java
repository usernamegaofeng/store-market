package pers.store.market.goods.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.common.core.Result;
import pers.store.market.common.core.StatusCode;
import pers.store.market.goods.service.ISpecService;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/2 下午5:09
 */
@Api(tags = "商品规格服务前端控制器")
@RestController
@CrossOrigin
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private ISpecService specService;

    @GetMapping("/category/{category}")
    @ApiOperation(value = "根据商品分类名称查询规格列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "category", dataType = "String", required = true, value = "分类名称")
    })
    public Result findListByCategoryName(@PathVariable String category) {
        List<Map> specList = specService.findListByCategoryName(category);
        return new Result(true, StatusCode.OK, "查询成功", specList);
    }
}
