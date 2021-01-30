package pers.store.market.product.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.product.entity.AttrEntity;
import pers.store.market.product.service.AttrService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;
import pers.store.market.product.vo.AttrVo;


/**
 * 商品属性
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Api(tags = "商品属性接口")
@RestController
@RequestMapping("product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;


    @GetMapping("/{attrType}/list/{categoryId}")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数"),
            @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "分类ID")}
    )
    public R list(@RequestParam Map<String, Object> params, @PathVariable("attrType") String type, @PathVariable("categoryId") Long categoryId) {
        PageUtils page = attrService.queryBasePage(params, categoryId,type);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{attrId}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrVo attr = attrService.getAttrDetail(attrId);
        return R.ok().put("attr", attr);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "attr", dataType = "AttrVo", required = true, value = "实体类vo")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "attr", dataType = "AttrVo", required = true, value = "实体类")
    public R update(@RequestBody AttrVo attr) {
        attrService.updateAttr(attr);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "attrIds", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));
        return R.ok();
    }

}
