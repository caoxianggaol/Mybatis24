package com.kaishengit.proxy;

import com.kaishengit.proxy.Dell;


import com.kaishengit.proxy.Sales;
import com.kaishengit.proxy.jdk.MyInvocationHandler;
import com.kaishengit.proxy.jdk.TimeInvocationHandler;
import com.kaishengit.service.UserService;
import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by xiaogao on 2017/10/30.
 */
public class JdkProxyTest {


    @Test
    public void proxy() {

        /*真正的代理对象 如Dell*/
        Dell dell = new Dell();
       /* 创建invocationHandler对象,传入代理对象*/
        MyInvocationHandler invocationHandler = new MyInvocationHandler(dell);
        /*动态产生代理类。并且接口指向代理类，不能拿代理对象来接收，因为代理对象和目标对象是兄弟
        参数1.代理对象.getClass().getClassLoader()2.代理对象.getClass().getInterfaces()
        3.invocationHandler*/
        Sales sales = (Sales) Proxy.newProxyInstance(dell.getClass().getClassLoader(),
                dell.getClass().getInterfaces(),invocationHandler);

        sales.salePc();
    }

    @Test
   public void userServiceProxy() {

      UserServiceImpl userServiceImpl = new UserServiceImpl();

      MyInvocationHandler invocationHandler = new MyInvocationHandler(userServiceImpl);

      UserService userService = (UserService) Proxy.newProxyInstance(userServiceImpl.getClass().getClassLoader(),
              userServiceImpl.getClass().getInterfaces(),invocationHandler);


      userService.save();
      userService.update();

   }

   @Test
   public void time() {

        UserServiceImpl userServiceImpl = new UserServiceImpl();
        TimeInvocationHandler timeInvocationHandler = new TimeInvocationHandler(userServiceImpl);
        UserService userService = (UserService) Proxy.newProxyInstance(userServiceImpl.getClass().getClassLoader(),
                userServiceImpl.getClass().getInterfaces(),timeInvocationHandler);

        userService.save();
        userService.update();
   }
}
