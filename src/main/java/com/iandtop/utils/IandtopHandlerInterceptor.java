package com.iandtop.utils;

import com.iandtop.model.system.UserModel;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Klin
 * @description 描述
 * @create 2017/5/5 0005
 * @Version 1.0
 */
public class IandtopHandlerInterceptor implements HandlerInterceptor {

    private static final String LOGIN_URL = "http://localhost:88888";
    public static final String SESSION_USER = "user";

    /**
     * @author Klin
     * @date 2017/5/5 0005
     * @parm
     * @result
     * @description
     */
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /**
         * 校验登陆 Session
         */
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        Object users = httpServletRequest.getSession().getAttribute(SESSION_USER);
//        if (users != null) {
//            UserModel user = (UserModel) users;
//            return true;
//
//        } else {
//            httpServletResponse.sendRedirect(LOGIN_URL);
//        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
