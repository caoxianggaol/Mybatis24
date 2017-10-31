package com.kaishengit.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xiaogao on 2017/10/31.
 */
public class MyMethodInterceptor implements MethodInterceptor{


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
/*methodProxy是子类的，而要的是父类的 对象o,参数objects*/
        System.out.println("---------");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("++++++++++");
        return result;
    }
}
