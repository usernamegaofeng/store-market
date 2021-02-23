package pers.store.market.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.store.market.auth.feign.MemberFeignService;
import pers.store.market.auth.vo.MemberVo;
import pers.store.market.common.constant.AuthConstant;
import pers.store.market.common.domain.vo.SocialUserVo;
import pers.store.market.common.utils.HttpUtils;
import pers.store.market.common.utils.R;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gaofeng
 * @date 2021/2/20 上午10:59
 * @description: 社交登录控制器
 */
@Controller
public class OAuthController {

    @Autowired
    private MemberFeignService memberFeignService;

    @RequestMapping("/oauth2.0/weibo/success")
    public String authorize(String code, HttpSession session) throws Exception {
        //1. 使用code换取token，换取成功则继续2，否则重定向至登录页
        Map<String, String> query = new HashMap<>();
        query.put("client_id", "2144471074");
        query.put("client_secret", "ff63a0d8d591a85a29a19492817316ab");
        query.put("grant_type", "authorization_code");
        query.put("redirect_uri", "http://localhost:9600/oauth2.0/weibo/success");
        query.put("code", code);
        //发送post请求换取token
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<String, String>(), query, new HashMap<String, String>());
        Map<String, String> errors = new HashMap<>();
        if (response.getStatusLine().getStatusCode() == 200) {
            //2. 调用member远程接口进行oauth登录，登录成功则转发至首页并携带返回用户信息，否则转发至登录页
            String json = EntityUtils.toString(response.getEntity());
            SocialUserVo socialUser = JSON.parseObject(json, new TypeReference<SocialUserVo>() {
            });
            R login = memberFeignService.login(socialUser);
            //2.1 远程调用成功，返回首页并携带用户信息
            if (login.getCode() == 0) {
                String jsonString = JSON.toJSONString(login.get("memberEntity"));
                MemberVo memberResponseVo = JSON.parseObject(jsonString, new TypeReference<MemberVo>() {
                });
                session.setAttribute(AuthConstant.LOGIN_USER, memberResponseVo);
                return "redirect:http://localhost:9300/index.html";
            } else {
                //2.2 否则返回登录页
                errors.put("msg", "登录失败，请重试");
                session.setAttribute("errors", errors);
                return "redirect:http://localhost:9600/login.html";
            }
        } else {
            errors.put("msg", "获得第三方授权失败，请重试");
            session.setAttribute("errors", errors);
            return "redirect:http://localhost:9600/login.html";
        }
    }
}
