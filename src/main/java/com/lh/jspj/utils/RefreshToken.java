package com.lh.jspj.utils;

import cn.hutool.core.bean.BeanUtil;
import com.lh.jspj.dto.UserDTO;
import com.lh.jspj.dto.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.lh.jspj.utils.RedisConstant.LOGIN_USER_KEY;
import static com.lh.jspj.utils.RedisConstant.LOGIN_USER_TTL;

/**
 * @author LH
 * @version 1.0
 * @description 刷新token
 * @date 2024/12/10 11:43
 */
public class RefreshToken implements HandlerInterceptor {


    private final StringRedisTemplate stringRedisTemplate;

    public RefreshToken(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是OPTIONS请求，直接返回成功响应
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            //不能继续进入Controller
            return false;
        }
        //获取请求头token
        String token = request.getHeader("Authorization");
        if (token == null) {
            return true;
        }
        //基于token获取用户
        String key = LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        //判断用户是否存在
        if (userMap.isEmpty()) {
            return true;
        }
        //将userMap转化为UserDTO
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        //将用户对象保存至ThreadLocal中
        UserHolder.saveUser(userDTO);
        //刷新token有效期
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户，防止信息泄露
        UserHolder.removeUser();
    }
}
