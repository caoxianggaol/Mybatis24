package com.kaishengit.proxy;

import com.kaishengit.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaogao on 2017/10/31.
 */
public class AopTest {

    /*结果为代理对象 此处不能用UserServiceImpl来接收，因为是兄弟*/
    //System.out.println(userService.getClass().getName());
    @Test
    public void test() {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");

        userService.save();
        userService.count();
    }
}
