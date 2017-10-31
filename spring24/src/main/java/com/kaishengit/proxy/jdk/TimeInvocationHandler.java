package com.kaishengit.proxy.jdk;

import com.kaishengit.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xiaogao on 2017/10/31.
 */
public class TimeInvocationHandler implements InvocationHandler{

    private Object object;
    public TimeInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Long startTime = System.currentTimeMillis();
        Object result = method.invoke(object,args);
        Long endTime = System.currentTimeMillis();

        String className = object.getClass().getName();
        String methodName = method.getName();
        System.out.println(className+"."+methodName+"->"+(endTime-startTime)+"ms");

        return result;
    }
}
