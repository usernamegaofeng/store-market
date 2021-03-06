package pers.store.market.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.common.constant.WareConstant;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.enums.ResultEnum;
import pers.store.market.ware.entity.WareSkuEntity;
import pers.store.market.ware.exception.NoStockException;
import pers.store.market.ware.service.WareSkuService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;
import pers.store.market.ware.vo.WareSkuLockVo;


/**
 * 商品库存
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:46
 */
@Api(tags = "商品库存接口")
@RestController
@RequestMapping("ware/wareSku")
public class WareSkuController {

    @Autowired
    private WareSkuService wareSkuService;

    @PostMapping(value = "/lock/order")
    public R orderLockStock(@RequestBody WareSkuLockVo vo) {
        try {
            boolean lockStock = wareSkuService.orderLockStock(vo);
            return R.ok().put("data",lockStock);
        } catch (NoStockException e) {
            return R.error(ResultEnum.NO_STOCK_EXCEPTION.getCode(),ResultEnum.NO_STOCK_EXCEPTION.getMsg());
        }
    }

    @PostMapping("hasStock")
    @ApiOperation(value = "查询sku是否有库存")
    @ApiImplicitParam(paramType = "body", name = "skuIds", dataType = "Long", required = true, value = "skuId数组")
    public List<SkuHasStockVo> hasStock(@RequestBody List<Long> skuIds) {
        return wareSkuService.hasStock(skuIds);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareSkuService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);
        return R.ok().put("wareSku", wareSku);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "wareSku", dataType = "WareSkuEntity", required = true, value = "实体类")
    public R save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "wareSku", dataType = "WareSkuEntity", required = true, value = "实体类")
    public R update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
