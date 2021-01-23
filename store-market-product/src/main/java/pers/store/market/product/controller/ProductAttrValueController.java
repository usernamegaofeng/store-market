package pers.store.market.product.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.product.entity.ProductAttrValueEntity;
import pers.store.market.product.service.ProductAttrValueService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * spu属性值
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Api(tags = "spu属性值接口")
@RestController
@RequestMapping("product/productAttrValue")
public class ProductAttrValueController {

    @Autowired
    private ProductAttrValueService productAttrValueService;


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = productAttrValueService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        ProductAttrValueEntity productAttrValue = productAttrValueService.getById(id);
        return R.ok().put("productAttrValue", productAttrValue);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "productAttrValue", dataType = "ProductAttrValueEntity", required = true, value = "实体类")
    public R save(@RequestBody ProductAttrValueEntity productAttrValue) {
        productAttrValueService.save(productAttrValue);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "productAttrValue", dataType = "ProductAttrValueEntity", required = true, value = "实体类")
    public R update(@RequestBody ProductAttrValueEntity productAttrValue) {
        productAttrValueService.updateById(productAttrValue);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        productAttrValueService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
