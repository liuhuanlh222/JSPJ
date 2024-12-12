package com.lh.jspj.utils;

import com.lh.jspj.dto.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author LH
 * @version 1.0
 * @description 登录拦截器
 * @date 2024/12/10 11:51
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(UserHolder.getUser() == null){
            //直接返回状态码
            response.setStatus(401);
            //拦截
            return false;
        }
        return true;
    }
}
