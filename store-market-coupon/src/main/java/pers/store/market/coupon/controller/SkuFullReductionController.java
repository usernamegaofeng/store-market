package pers.store.market.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.common.domain.dto.SkuReductionDto;
import pers.store.market.coupon.entity.SkuFullReductionEntity;
import pers.store.market.coupon.service.SkuFullReductionService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * 商品满减信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:22
 */
@Api(tags = "商品满减信息接口")
@RestController
@RequestMapping("coupon/skuFullReduction")
public class SkuFullReductionController {

    @Autowired
    private SkuFullReductionService skuFullReductionService;


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuFullReductionService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        SkuFullReductionEntity skuFullReduction = skuFullReductionService.getById(id);
        return R.ok().put("skuFullReduction", skuFullReduction);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "skuFullReduction", dataType = "SkuFullReductionEntity", required = true, value = "实体类")
    public R save(@RequestBody SkuFullReductionEntity skuFullReduction) {
        skuFullReductionService.save(skuFullReduction);
        return R.ok();
    }

    @PostMapping("/saveInfo")
    @ApiOperation(value = "保存满减信息")
    @ApiImplicitParam(paramType = "body", name = "skuReductionDto", dataType = "SkuReductionDto", required = true, value = "sku满减信息实体类")
    public R saveInfo(@RequestBody SkuReductionDto skuReductionDto) {
        skuFullReductionService.saveInfo(skuReductionDto);
        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "skuFullReduction", dataType = "SkuFullReductionEntity", required = true, value = "实体类")
    public R update(@RequestBody SkuFullReductionEntity skuFullReduction) {
        skuFullReductionService.updateById(skuFullReduction);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        skuFullReductionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
