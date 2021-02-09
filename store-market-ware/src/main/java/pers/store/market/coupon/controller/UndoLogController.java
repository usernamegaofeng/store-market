package pers.store.market.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.coupon.entity.UndoLogEntity;
import pers.store.market.coupon.service.UndoLogService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:45
 */
@Api(tags = "日志接口")
@RestController
@RequestMapping("ware/undolog")
public class UndoLogController {

    @Autowired
    private UndoLogService undoLogService;


    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = undoLogService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        UndoLogEntity undoLog = undoLogService.getById(id);
        return R.ok().put("undoLog", undoLog);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "undoLog", dataType = "UndoLogEntity", required = true, value = "实体类")
    public R save(@RequestBody UndoLogEntity undoLog) {
        undoLogService.save(undoLog);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "undoLog", dataType = "UndoLogEntity", required = true, value = "实体类")
    public R update(@RequestBody UndoLogEntity undoLog) {
        undoLogService.updateById(undoLog);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        undoLogService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
