package pers.store.market.cart.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.store.market.common.constant.AuthConstant;
import pers.store.market.common.constant.CartConstant;
import pers.store.market.common.domain.dto.UserInfoContent;
import pers.store.market.common.domain.vo.MemberVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:56
 * @description: 监听器
 */
public class CartInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfoContent> userContent = new ThreadLocal<UserInfoContent>();

    /**
     * 业务执行前处理
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberVo member = (MemberVo) session.getAttribute(AuthConstant.LOGIN_USER);
        UserInfoContent userInfoContent = new UserInfoContent();
        //用户已登录,封装用户ID
        if (member != null) {
            userInfoContent.setUserId(member.getId());
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                //如果浏览器中已经有user-Key，则直接设置
                if (cookie.getName().equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                    userInfoContent.setUserKey(cookie.getValue());
                    userInfoContent.setTempUser(true);
                }
            }
        }
        //浏览器如果没有user-Key,则创建user-Key
        if (StringUtils.isBlank(userInfoContent.getUserKey())) {
            String uuid = UUID.randomUUID().toString();
            userInfoContent.setUserKey(uuid);
        }
        userContent.set(userInfoContent);
        return true;
    }

    /**
     * 业务执行后处理
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //如果浏览器中没有user-key，我们为其生成并存到cookie中去
        UserInfoContent userInfoContent = userContent.get();
        if (!userInfoContent.isTempUser()) {
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoContent.getUserKey());
            cookie.setDomain("localhost");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT); //设置有效时间
            response.addCookie(cookie);
        }
    }
}
