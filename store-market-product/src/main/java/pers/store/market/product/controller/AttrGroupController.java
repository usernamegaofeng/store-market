package pers.store.market.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.product.entity.AttrEntity;
import pers.store.market.product.entity.AttrGroupEntity;
import pers.store.market.product.service.AttrAttrgroupRelationService;
import pers.store.market.product.service.AttrGroupService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;
import pers.store.market.product.service.AttrService;
import pers.store.market.product.service.CategoryService;
import pers.store.market.product.vo.AttrRelationVo;
import pers.store.market.product.vo.AttrsGroupVo;


/**
 * 属性分组
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:15
 */
@Api(tags = "属性分组接口")
@RestController
@RequestMapping("product/attrGroup")
public class AttrGroupController {

    @Autowired
    private AttrService attrService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;


    @GetMapping("/{attrGroupId}/attr/relation")
    @ApiOperation(value = "获取属性分组的关联的所有属性")
    @ApiImplicitParam(paramType = "path", name = "attrGroupId", dataType = "Long", required = true, value = "分组属性关联ID")
    public R getAttrRelation(@PathVariable("attrGroupId") Long attrGroupId) {
        List<AttrEntity> attrEntityList = attrService.getRelationAttr(attrGroupId);
        return R.ok().put("data", attrEntityList);
    }

    @GetMapping("/{attrGroupId}/noattr/relation")
    @ApiOperation(value = "获取属性分组没有关联的所有属性")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数"),
            @ApiImplicitParam(paramType = "path", name = "attrGroupId", dataType = "Long", required = true, value = "分组ID")
    })
    public R getNoAttrRelation(@PathVariable("attrGroupId") Long attrGroupId, @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNoRelationAttr(attrGroupId, params);
        return R.ok().put("page", page);
    }

    @GetMapping("/{categoryId}/withattr")
    @ApiOperation(value = "获取当前分类下的所有分组和分类属性")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "分类ID")
    })
    public R getAttrGroupWithAttrs(@PathVariable("categoryId") Long categoryId) {
        List<AttrsGroupVo> dataList = attrGroupService.getAttrGroupWithAttrs(categoryId);
        return R.ok().put("data", dataList);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/list/{categoryId}")
    @ApiOperation(value = "根据三级分类ID获取分类属性分组")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数"),
            @ApiImplicitParam(paramType = "path", name = "categoryId", dataType = "Long", required = true, value = "三级分类ID")
    })
    public R list(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId) {
        PageUtils page = attrGroupService.queryPageByCategoryId(params, categoryId);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{attrGroupId}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long[] path = categoryService.findCategoryPath(attrGroup.getCatelogId());
        attrGroup.setCategoryPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "attrGroup", dataType = "AttrGroupEntity", required = true, value = "实体类")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);
        return R.ok();
    }

    @PostMapping("/attr/relation")
    @ApiOperation(value = "添加属性与分组关联关系")
    @ApiImplicitParam(paramType = "body", name = "relationVos", dataType = "AttrRelationVo", allowMultiple = true, required = true, value = "参数数组")
    public R addRelation(@RequestBody AttrRelationVo[] relationVos) {
        attrAttrgroupRelationService.addRelation(relationVos);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "attrGroup", dataType = "AttrGroupEntity", required = true, value = "实体类")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "attrGroupIds", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        return R.ok();
    }

    @PostMapping("/attr/relation/delete")
    @ApiOperation(value = "批量删除", notes = "批量删除属性与分组的关联关系")
    @ApiImplicitParam(paramType = "body", name = "relationVos", dataType = "AttrRelationVo", allowMultiple = true, required = true, value = "参数数组")
    public R delete(@RequestBody AttrRelationVo[] relationVos) {
        attrGroupService.removeAttrRelation(relationVos);
        return R.ok();
    }
}
