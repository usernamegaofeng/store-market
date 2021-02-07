package pers.store.market.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.ware.entity.PurchaseEntity;
import pers.store.market.ware.service.PurchaseService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;
import pers.store.market.ware.vo.MergePurchaseVo;
import pers.store.market.ware.vo.PurchaseDoneVo;


/**
 * 采购信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:45
 */
@Api(tags = "采购信息接口")
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/unreceive/list")
    @ApiOperation(value = "查询未领取的采购单")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "查询参数")
    public R unReceivePurchase(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.unReceivePurchase(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        PurchaseEntity purchase = purchaseService.getById(id);
        return R.ok().put("purchase", purchase);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "purchase", dataType = "PurchaseEntity", required = true, value = "实体类")
    public R save(@RequestBody PurchaseEntity purchase) {
        Date date = new Date();
        purchase.setCreateTime(date);
        purchase.setUpdateTime(date);
        purchaseService.save(purchase);
        return R.ok();
    }

    @PostMapping("/merge")
    @ApiOperation(value = "合并采购单")
    @ApiImplicitParam(paramType = "body", name = "mergePurchaseVo", dataType = "MergePurchaseVo", required = true, value = "采购单参数实体类")
    public R mergePurchase(@RequestBody MergePurchaseVo mergePurchaseVo) {
        purchaseService.mergePurchase(mergePurchaseVo);
        return R.ok();
    }

    @PostMapping("/received")
    @ApiOperation(value = "领取采购单")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "List", required = true, value = "ID数组")
    public R received(@RequestBody List<Long> ids) {
        purchaseService.received(ids);
        return R.ok();
    }

    @PostMapping("/done")
    @ApiOperation(value = "完成采购单")
    @ApiImplicitParam(paramType = "body", name = "doneVo", dataType = "PurchaseDoneVo", required = true, value = "参数实体类")
    public R finish(@RequestBody PurchaseDoneVo doneVo) {
        purchaseService.done(doneVo);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "purchase", dataType = "PurchaseEntity", required = true, value = "实体类")
    public R update(@RequestBody PurchaseEntity purchase) {
        purchaseService.updateById(purchase);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        purchaseService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
