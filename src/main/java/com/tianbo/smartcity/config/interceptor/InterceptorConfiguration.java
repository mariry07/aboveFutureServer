package com.tianbo.smartcity.config.interceptor;

import com.tianbo.smartcity.config.security.IgnoredUrlsProperties;
import com.tianbo.smartcity.config.security.ThirdUrlsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private ThirdUrlsProperties thirdUrlsProperties;

    @Autowired
    private LimitRaterInterceptor limitRaterInterceptor;

    @Autowired
    private SignInterceptor signInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(limitRaterInterceptor);
        // 配置拦截的路径
        ir.addPathPatterns("/**");
        // 配置不拦截的路径
        ir.excludePathPatterns(ignoredUrlsProperties.getUrls());

        // 接口验证拦截器
        // InterceptorRegistration ir1 = registry.addInterceptor(signInterceptor);
        // 配置拦截的路径
        // ir1.addPathPatterns("/**");
        // 配置不拦截的路径
        // ir1.excludePathPatterns(ignoredUrlsProperties.getUrls());

    }
}
