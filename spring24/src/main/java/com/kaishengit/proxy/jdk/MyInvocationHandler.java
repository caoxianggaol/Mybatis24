package com.kaishengit.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk自带动态代理（可以自动产生代理对象）
 * Created by xiaogao on 2017/10/29.
 */
public class MyInvocationHandler implements InvocationHandler{

    /*定义一个目标对象，可以是任意的 target（目标）*/
    private Object target;
    /*创建构造方法*/
    public MyInvocationHandler (Object target) {
        this.target = target;
    }

    /*目标对象方法其中之一 method*/
    @Override
    public Object invoke(Object proxy, Method method,
                         Object[] args) throws Throwable {
        System.out.println("前置----------");
    /*调用method的invoke方法  参数1调用对象 2该对象的参数*/
    Object result = method.invoke(target, args);//代表目标对象方法的执行

        System.out.println("后置==============");

        return result;
    }
}
