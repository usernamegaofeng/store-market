package pers.store.market.ware.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.ware.entity.CouponHistoryEntity;
import pers.store.market.ware.service.CouponHistoryService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * 优惠券领取历史记录
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:22
 */
@Api(tags = "优惠券历史接口")
@RestController
@RequestMapping("coupon/couponHistory")
public class CouponHistoryController {

    @Autowired
    private CouponHistoryService couponHistoryService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponHistoryService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        CouponHistoryEntity couponHistory = couponHistoryService.getById(id);
        return R.ok().put("couponHistory", couponHistory);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "couponHistory", dataType = "CouponHistoryEntity", required = true, value = "实体类")
    public R save(@RequestBody CouponHistoryEntity couponHistory) {
        couponHistoryService.save(couponHistory);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "couponHistory", dataType = "CouponHistoryEntity", required = true, value = "实体类")
    public R update(@RequestBody CouponHistoryEntity couponHistory) {
        couponHistoryService.updateById(couponHistory);
        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        couponHistoryService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
