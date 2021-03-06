package pers.store.market.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.member.entity.MemberReceiveAddressEntity;
import pers.store.market.member.service.MemberReceiveAddressService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;


/**
 * 会员收货地址
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:22:16
 */
@Api(tags = "会员收货地址接口")
@RestController
@RequestMapping("member/memberReceiveAddress")
public class MemberReceiveAddressController {

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;


    @GetMapping("/{id}/getAddress")
    @ApiOperation(value = "根据会员ID获取所有的收获地址列表")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "会员ID")
    public List<MemberReceiveAddressEntity> getAddress(@PathVariable("id") Long id) {
        List<MemberReceiveAddressEntity> dataList = memberReceiveAddressService.getAddress(id);
        return dataList;
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberReceiveAddressService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        MemberReceiveAddressEntity memberReceiveAddress = memberReceiveAddressService.getById(id);
        return R.ok().put("memberReceiveAddress", memberReceiveAddress);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "memberReceiveAddress", dataType = "MemberReceiveAddressEntity", required = true, value = "实体类")
    public R save(@RequestBody MemberReceiveAddressEntity memberReceiveAddress) {
        memberReceiveAddressService.save(memberReceiveAddress);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "memberReceiveAddress", dataType = "MemberReceiveAddressEntity", required = true, value = "实体类")
    public R update(@RequestBody MemberReceiveAddressEntity memberReceiveAddress) {
        memberReceiveAddressService.updateById(memberReceiveAddress);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        memberReceiveAddressService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
