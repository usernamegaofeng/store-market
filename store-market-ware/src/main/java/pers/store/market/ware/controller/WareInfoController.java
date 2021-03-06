package pers.store.market.ware.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.ware.entity.WareInfoEntity;
import pers.store.market.ware.service.WareInfoService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;
import pers.store.market.ware.vo.FareVo;


/**
 * 仓库信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:46
 */
@Api(tags = "仓库信息接口")
@RestController
@RequestMapping("ware/wareInfo")
public class WareInfoController {

    @Autowired
    private WareInfoService wareInfoService;

    @RequestMapping("/fare/{addrId}")
    @ApiOperation(value = "查询运费")
    @ApiImplicitParam(paramType = "path", name = "addrId", dataType = "Long", required = true, value = "地址ID")
    public FareVo getFare(@PathVariable("addrId") Long addrId) {
        return wareInfoService.getFare(addrId);
    }


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareInfoService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        WareInfoEntity wareInfo = wareInfoService.getById(id);
        return R.ok().put("wareInfo", wareInfo);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "wareInfo", dataType = "WareInfoEntity", required = true, value = "实体类")
    public R save(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.save(wareInfo);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "wareInfo", dataType = "WareInfoEntity", required = true, value = "实体类")
    public R update(@RequestBody WareInfoEntity wareInfo) {
        wareInfoService.updateById(wareInfo);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        wareInfoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
