package pers.store.market.order.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.store.market.common.constant.AuthConstant;
import pers.store.market.common.domain.vo.MemberVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午6:40
 * @description: 订单服务登录拦截器
 */
@Component
public class OrderLoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberVo> threadLocal = new ThreadLocal<>();

    /**
     * 登录拦截
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
        MemberVo memberVo = (MemberVo) session.getAttribute(AuthConstant.LOGIN_USER);
        //判断是否登录
        if (memberVo != null) {
            threadLocal.set(memberVo);
            return true;
        }else {
            session.setAttribute("msg", "请先登录!");
            response.sendRedirect("http://localhost:9600/login.html");
            return false;
        }
    }
}
