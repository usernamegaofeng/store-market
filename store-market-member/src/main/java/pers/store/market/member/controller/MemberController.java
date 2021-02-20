package pers.store.market.member.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.common.domain.vo.SocialUserVo;
import pers.store.market.common.enums.ResultEnum;
import pers.store.market.member.entity.MemberEntity;
import pers.store.market.member.exception.PhoneNumExistException;
import pers.store.market.member.exception.UserExistException;
import pers.store.market.member.service.MemberService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.R;
import pers.store.market.member.vo.UserLoginVo;
import pers.store.market.member.vo.UserRegisterVo;


/**
 * 会员
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:22:15
 */
@Api(tags = "会员接口")
@RestController
@RequestMapping("member/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @RequestMapping("/oauth2/login")
    @ApiOperation(value = "新浪微博登录")
    @ApiImplicitParam(paramType = "body", name = "socialUser", dataType = "SocialUserVo", required = true, value = "社交登录vo类")
    public R login(@RequestBody SocialUserVo socialUser) {
        MemberEntity entity = memberService.login(socialUser);
        if (entity != null) {
            return R.ok().put("memberEntity", entity);
        } else {
            return R.error();
        }
    }


    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(paramType = "body", name = "userLoginVo", dataType = "UserLoginVo", required = true, value = "用户登录参数vo")
    public R login(@RequestBody UserLoginVo userLoginVo) {
        MemberEntity memberEntity = memberService.login(userLoginVo);
        if (memberEntity != null) {
            return R.ok().put("memberEntity", memberEntity);
        }
        return R.error(ResultEnum.USERNAME_OR_PASSWORD_ERROR.getCode(), ResultEnum.USERNAME_OR_PASSWORD_ERROR.getMsg());
    }


    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(paramType = "body", name = "registerVo", dataType = "UserRegisterVo", required = true, value = "用户注册参数vo")
    public R register(@RequestBody UserRegisterVo registerVo) {
        try {
            memberService.register(registerVo);
            return R.ok();
        } catch (UserExistException userExistException) {
            return R.error(ResultEnum.USERNAME_EXIST.getCode(), ResultEnum.USERNAME_EXIST.getMsg());
        } catch (PhoneNumExistException phoneNumExistException) {
            return R.error(ResultEnum.PHONE_EXIST.getCode(), ResultEnum.PHONE_EXIST.getMsg());
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(paramType = "query", name = "params", dataType = "Map", required = true, value = "分页列表请求参数")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping(value = "/info/{id}")
    @ApiOperation(value = "查询信息")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", required = true, value = "ID")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);
        return R.ok().put("member", member);
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存操作")
    @ApiImplicitParam(paramType = "body", name = "member", dataType = "MemberEntity", required = true, value = "实体类")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);
        return R.ok();
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改操作")
    @ApiImplicitParam(paramType = "body", name = "member", dataType = "MemberEntity", required = true, value = "实体类")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "批量删除", notes = "根据id集合集来批量删除对象")
    @ApiImplicitParam(paramType = "body", name = "ids", dataType = "Long", allowMultiple = true, required = true, value = "ID数组")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
