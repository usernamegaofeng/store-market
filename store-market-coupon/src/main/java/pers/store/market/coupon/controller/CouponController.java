package pers.store.market.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.coupon.entity.CouponEntity;
import pers.store.market.coupon.service.CouponService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;

/**
 * 优惠券信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:22
 */
@Api(tags = "优惠券接口")
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "优惠券ID")
    public R info(@PathVariable("id") Long id) {
        CouponEntity coupon = couponService.getById(id);
        return R.ok().put("coupon", coupon);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "coupon", dataType = "CouponEntity", required = true, value = "优惠券实体类")
    public R save(@RequestBody CouponEntity coupon) {
        couponService.save(coupon);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "coupon", dataType = "CouponEntity", required = true, value = "优惠券实体类")
    public R update(@RequestBody CouponEntity coupon) {
        couponService.updateById(coupon);
        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")  //如果传递到是集合或数组,设置 allowMultiple = true
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        couponService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
