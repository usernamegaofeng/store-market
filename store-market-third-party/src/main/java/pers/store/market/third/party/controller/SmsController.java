package pers.store.market.third.party.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.store.market.common.utils.R;
import pers.store.market.third.party.sms.SmsComponent;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午3:45
 * @description: 短信服务控制器
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsComponent smsComponent;

    /**
     * 发送验证码
     *
     * @param phone 电话号码
     * @param code  验证码
     * @return
     */
    @GetMapping("/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        smsComponent.sendCode(phone, code);
        return R.ok();
    }
}
