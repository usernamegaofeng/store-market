package pers.store.market.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.product.entity.SkuSaleAttrValueEntity;
import pers.store.market.product.service.SkuSaleAttrValueService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * sku销售属性&值
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Api(tags = "sku销售属性&值接口")
@RestController
@RequestMapping("product/skuSaleAttrValue")
public class SkuSaleAttrValueController {

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;


    @GetMapping("/getSkuSaleAttrAsStringList")
    public List<String> getSkuSaleAttrAsStringList(@RequestParam("skuId") Long skuId){
        return skuSaleAttrValueService.getSkuSaleAttrAsStringList(skuId);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuSaleAttrValueService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        SkuSaleAttrValueEntity skuSaleAttrValue = skuSaleAttrValueService.getById(id);
        return R.ok().put("skuSaleAttrValue", skuSaleAttrValue);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "skuSaleAttrValue", dataType = "SkuSaleAttrValueEntity", required = true, value = "实体类")
    public R save(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue) {
        skuSaleAttrValueService.save(skuSaleAttrValue);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "skuSaleAttrValue", dataType = "SkuSaleAttrValueEntity", required = true, value = "实体类")
    public R update(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue) {
        skuSaleAttrValueService.updateById(skuSaleAttrValue);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        skuSaleAttrValueService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
