package com.kaishengit.controller.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaogao on 2017/11/3.
 */
public class MyInterceptor extends HandlerInterceptorAdapter{

    /*  拦截器   请求之前做什么*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("--------");
        return true;//先过拦截器，再过HelloController为false时不放行 为true时放行
    }//静态资源同样会过拦截器  
}
