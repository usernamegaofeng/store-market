package pers.store.market.ware.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.ware.entity.CouponSpuCategoryRelationEntity;
import pers.store.market.ware.service.CouponSpuCategoryRelationService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * 优惠券分类关联
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:23
 */
@Api(tags = "优惠券SPU分类关联接口")
@RestController
@RequestMapping("coupon/couponSpuCategory")
public class CouponSpuCategoryRelationController {

    @Autowired
    private CouponSpuCategoryRelationService couponSpuCategoryRelationService;


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponSpuCategoryRelationService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        CouponSpuCategoryRelationEntity couponSpuCategoryRelation = couponSpuCategoryRelationService.getById(id);
        return R.ok().put("couponSpuCategoryRelation", couponSpuCategoryRelation);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "couponSpuCategoryRelation", dataType = "CouponSpuCategoryRelationEntity", required = true, value = "实体类")
    public R save(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation) {
        couponSpuCategoryRelationService.save(couponSpuCategoryRelation);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "couponSpuCategoryRelation", dataType = "CouponSpuCategoryRelationEntity", required = true, value = "实体类")
    public R update(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation) {
        couponSpuCategoryRelationService.updateById(couponSpuCategoryRelation);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        couponSpuCategoryRelationService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
