package pers.store.market.goods.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @ApiOperation("新增spu数据")
    @ApiImplicitParam(paramType = "body", name = "goods", dataType = "Goods", required = true, value = "商品组合实体类")
    public Result addGoods(@RequestBody Goods goods) {
        spuService.add(goods);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PutMapping(value = "/updateGoods")
    @ApiOperation("修改spu数据")
    @ApiImplicitParam(paramType = "body", name = "goods", dataType = "Goods", required = true, value = "商品组合实体类")
    public Result updateGoods(@RequestBody Goods goods) {
        spuService.update(goods);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @PutMapping("/audit/{id}")
    @ApiOperation("商品审核")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "spuId")
    public Result audit(@PathVariable("id") Long id) {
        spuService.audit(id);
        return new Result(true, StatusCode.OK, "审核成功");
    }

    @PutMapping("/pull/{id}")
    @ApiOperation("商品下架")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "spuId")
    public Result pull(@PathVariable("id") Long id) {
        spuService.pull(id);
        return new Result(true, StatusCode.OK, "下架成功");
    }

    @PutMapping("/put/{id}")
    @ApiOperation("商品上架")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "spuId")
    public Result put(@PathVariable("id") Long id) {
        spuService.put(id);
        return new Result(true, StatusCode.OK, "上架成功");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("逻辑删除商品")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "spuId")
    public Result delete(@PathVariable Long id) {
        spuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @DeleteMapping("/realDelete/{id}")
    @ApiOperation("物理删除商品")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "spuId")
    public Result realDelete(@PathVariable Long id) {
        spuService.realDelete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    @PutMapping("/restore/{id}")
    @ApiOperation("还原商品状态")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "spuId")
    public Result restore(@PathVariable Long id) {
        spuService.restore(id);
        return new Result(true, StatusCode.OK, "还原成功");
    }
}
