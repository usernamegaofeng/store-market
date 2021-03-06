package pers.store.market.product.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.product.entity.SpuInfoDescEntity;
import pers.store.market.product.service.SpuInfoDescService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * spu信息详情
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Api(tags = "spu信息详情接口")
@RestController
@RequestMapping("product/spuInfoDesc")
public class SpuInfoDescController {

    @Autowired
    private SpuInfoDescService spuInfoDescService;


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoDescService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{spuId}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("spuId") Long spuId) {
        SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(spuId);
        return R.ok().put("spuInfoDesc", spuInfoDesc);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "spuInfoDesc", dataType = "SpuInfoDescEntity", required = true, value = "实体类")
    public R save(@RequestBody SpuInfoDescEntity spuInfoDesc) {
        spuInfoDescService.save(spuInfoDesc);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "spuInfoDesc", dataType = "SpuInfoDescEntity", required = true, value = "实体类")
    public R update(@RequestBody SpuInfoDescEntity spuInfoDesc) {
        spuInfoDescService.updateById(spuInfoDesc);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "spuIds", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] spuIds) {
        spuInfoDescService.removeByIds(Arrays.asList(spuIds));
        return R.ok();
    }

}
