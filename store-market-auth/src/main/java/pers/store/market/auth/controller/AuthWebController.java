package pers.store.market.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pers.store.market.auth.feign.MemberFeignService;
import pers.store.market.auth.feign.ThirdPartyFeignService;
import pers.store.market.auth.vo.UserLoginVo;
import pers.store.market.auth.vo.UserRegisterVo;
import pers.store.market.common.constant.AuthConstant;
import pers.store.market.common.domain.vo.MemberVo;
import pers.store.market.common.enums.ResultEnum;
import pers.store.market.common.utils.R;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午2:55
 * @description: 认证服务(登录, 注册, 社交登录)控制器
 */
@Slf4j
@Controller
public class AuthWebController {

    private final long TIME = 60000L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MemberFeignService memberFeignService;
    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @GetMapping("/login.html")
    public String loginPage(HttpSession httpSession) {
        Object attribute = httpSession.getAttribute(AuthConstant.LOGIN_USER);
        if (null == attribute) {
            //没有登录返回登录页
            return "login";
        }
        return "redirect:http://localhost:9300";
    }


    @GetMapping("/sms/sendCode")
    @ResponseBody
    public R sendCode(@RequestParam("phone") @NotBlank String phone) {
        //接口防刷,在redis中缓存验证码
        String redisCode = stringRedisTemplate.opsForValue().get(AuthConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (StringUtils.isNotBlank(redisCode)) {
            String[] split = redisCode.split("_");
            long times = Long.parseLong(split[1]);
            //如果存储的时间小于60s，说明60s内发送过验证码
            if (System.currentTimeMillis() - times < TIME) {
                return R.error(ResultEnum.REPEAT_SEND_CODE.getCode(), ResultEnum.REPEAT_SEND_CODE.getMsg());
            }
        }
        //发送验证码
        String code = String.valueOf((int) ((Math.random() + 1) * 100000));
        String redisValue = code + "_" + System.currentTimeMillis();
        stringRedisTemplate.opsForValue().set(AuthConstant.SMS_CODE_CACHE_PREFIX + phone, redisValue, 5, TimeUnit.MINUTES);
        //thirdPartyFeignService.sendCode(phone, code);
        log.info("验证码为 ===> {}", code);
        return R.ok();
    }

    @PostMapping("/register")
    public String register(@Validated UserRegisterVo registerVo, BindingResult result, RedirectAttributes attributes) {
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()) {
            //判断字段是否通过校验
            result.getFieldErrors().forEach(item -> {
                errors.put(item.getField(), item.getDefaultMessage());
                //重定向使用RedirectAttributes封装数据到页面
                attributes.addFlashAttribute("errors", errors);
            });
            return "redirect:http://localhost:9600/register.html";
        }
        //获取数据
        String redisCode = stringRedisTemplate.opsForValue().get(AuthConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());
        if (StringUtils.isNotBlank(redisCode)) {
            stringRedisTemplate.delete(AuthConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone());
            //校验验证码
            String[] split = redisCode.split("_");
            if (registerVo.getCode().equals(split[0])) {
                R r = memberFeignService.register(registerVo);
                if (r.getCode() == 0) {
                    return "redirect:http://localhost:9600/login.html";
                } else {
                    errors.put("msg", (String) r.get("msg"));
                    attributes.addFlashAttribute("errors", errors);
                    return "redirect:http://localhost:9600/register.html";
                }
            } else {
                errors.put("code", "验证码错误");
                attributes.addFlashAttribute("errors", errors);
                return "redirect:http://localhost:9600/register.html";
            }
        } else {
            errors.put("code", "验证码错误");
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://localhost:9600/register.html";
        }
    }

    @PostMapping("/login")
    public String login(@Validated UserLoginVo userLoginVo, RedirectAttributes attributes, HttpSession httpSession) {
        R r = memberFeignService.login(userLoginVo);
        if (r.getCode() == 0) {
            String jsonString = JSON.toJSONString(r.get("memberEntity"));
            MemberVo memberResponseVo = JSON.parseObject(jsonString, new TypeReference<MemberVo>() {
            });
            log.info("登录成功,数据 ===> {}", memberResponseVo.toString());
            httpSession.setAttribute(AuthConstant.LOGIN_USER, memberResponseVo);
            return "redirect:http://localhost:9300";
        }
        Map<String, String> errors = new HashMap<>();
        errors.put("msg", (String) r.get("msg"));
        attributes.addFlashAttribute("errors", errors);
        return "redirect:http://localhost:9600/login.html";
    }

}
