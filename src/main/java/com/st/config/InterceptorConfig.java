package com.st.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //除register、login接口外所有接口验证token有效性
//        String[] addPathPatterns = {"/api/**"};
//        String[] excludePathPatterns = {"/api/user/login","/api/shop/**","/api/shopinfo/**"};
//        registry.addInterceptor(new JWTInterceptor())
//                .addPathPatterns(addPathPatterns)
//                .excludePathPatterns(excludePathPatterns);

        //只有商家才能访问的接口
//        String[] addPathPatterns_forshop = {""};
//        registry.addInterceptor(new ShopAuthenticationInterceptor())
//                .addPathPatterns(addPathPatterns_forshop);

        //只有普通用户才能访问的接口
//        String[] addPathPatterns_foruser = {"/api/userinfo/update"};
//        registry.addInterceptor(new UserAuthenticationInterceptor())
//                .addPathPatterns(addPathPatterns_foruser);

    }
}