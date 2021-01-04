package pers.store.market.goods.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.store.market.common.core.Result;
import pers.store.market.common.core.StatusCode;
import pers.store.market.goods.domain.pojo.Goods;
import pers.store.market.goods.service.ISpuService;

/**
 * @author 高枫
 * @date 2021/1/4 上午11:27
 */
@Api(tags = "spu服务前端控制器")
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private ISpuService spuService;

    @PostMapping(value = "/addGoods")
    @ApiOperation("新增数据")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "body", name = "goods", dataType = "Goods", required = true, value = "商品组合实体类")
    )
    public Result addGoods(@RequestBody Goods goods) {
        spuService.add(goods);
        return new Result(true, StatusCode.OK, "添加成功");
    }
}
