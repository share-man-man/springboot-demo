package com.xiaoman.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shuxiaoman
 * @ClassName WebConfigurer
 * @date 2019/5/9
 * @Description //TODO 拦截器配置
 */
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    /**
     * @Author xiaoman
     * @Description //TODO 手动注入拦截器，使拦截器里面的@autowride注入生效
     * @Date 2019/5/23
     **/
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    /**
     * @Author xiaoman
     * @Description //TODO 1、所有url先拦截,再走映射
     * @Date 2019/5/23
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                loginInterceptor()).addPathPatterns("/**")
                /*放行首页、和登录页面*/
                /*.excludePathPatterns("/","/index.html","/login.html","/index","/login")*/
                /*放行mvc请求*/
                .excludePathPatterns("/mvc","/mvc/**","/hello/**")
                /*放行登录请求*/
                /*.excludePathPatterns("/user/login")*/
                /*放行非登录页面*/
                /*.excludePathPatterns("/static_pages/pass/**")*/
                /*放行静态资源*/
                .excludePathPatterns("/css/**","/img/**","/js/**")
        ;/*放行静态资源*/
    }

//    /**
//     * @Author xiaoman
//     * @Description //TODO 2、所有url先拦截,再走映射
//     * @Date 2019/5/23
//     **/
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry){
//
// /*       映射转发动态页面
//        registry.addViewController("hello/error").setViewName("exception/exceptionPage.html");*/
//
//        /*将url重定向为静态页面*/
//
//        /*直接进入url时，默认进入首页*/
//        registry.addRedirectViewController("/","/index.html");
//        /*index映射首页页面*/
//        registry.addRedirectViewController("/index","/index.html");
//        /*login映射登录页面*/
//        registry.addRedirectViewController("/login","/login.html");
//    }

//    /*3、资源定位*/
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        /*样式拦截,有些内网可能不允许访问cdn*/
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }

}
