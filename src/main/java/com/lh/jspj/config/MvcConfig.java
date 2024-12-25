package com.lh.jspj.config;

import com.lh.jspj.utils.LoginInterceptor;
import com.lh.jspj.utils.RefreshToken;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LH
 * @version 1.0
 * @description 拦截器
 * @date 2024/12/10 12:48
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        //不拦截路径
                        "/user/login",
                        "/alipay/notify"
                ).order(1);
        //token刷新拦截器
        registry.addInterceptor(new RefreshToken(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }
}
