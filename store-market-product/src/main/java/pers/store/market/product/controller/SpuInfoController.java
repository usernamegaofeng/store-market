package pers.store.market.product.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.product.entity.SpuInfoEntity;
import pers.store.market.product.service.SpuInfoService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;
import pers.store.market.product.vo.SpuSaveVo;


/**
 * spu信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Api(tags = "spu信息接口")
@RestController
@RequestMapping("product/spuInfo")
public class SpuInfoController {

    @Autowired
    private SpuInfoService spuInfoService;


    @PostMapping("/{spuId}/up")
    @ApiOperation(value = "商品上架")
    @ApiImplicitParam(paramType = "path", name = "spuId", dataType = "Long", required = true, value = "spuId")
    public R up(@PathVariable("spuId") Long spuId) {
        spuInfoService.up(spuId);
        return R.ok();
    }

    @GetMapping("/getSpuInfo/{skuId}")
    @ApiOperation(value = "根据skuI查询spu信息")
    @ApiImplicitParam(paramType = "path", name = "skuId", dataType = "Long", required = true, value = "skuId")
    public R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId){
        SpuInfoEntity spuInfoEntity = spuInfoService.getSpuInfoBySkuId(skuId);
        return R.ok().put("data",spuInfoEntity);
    }


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);
        return R.ok().put("spuInfo", spuInfo);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存spu")
    @ApiImplicitParam(paramType = "body", name = "vo", dataType = "SpuSaveVo", required = true, value = "实体类")
    public R save(@RequestBody SpuSaveVo vo) {
        spuInfoService.saveSpuInfo(vo);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "spuInfo", dataType = "SpuInfoEntity", required = true, value = "实体类")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
