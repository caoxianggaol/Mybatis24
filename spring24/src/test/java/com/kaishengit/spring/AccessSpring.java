package com.kaishengit.spring;


import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaogao on 2017/10/28.
 */
public class AccessSpring {

    @Test
    public void hello(){
        /*获取Spring容器 */
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserServiceImpl userService = (UserServiceImpl)applicationContext.getBean("userService");
        /*UserService userService = (UserService)applicationContext.getBean("userService");*/
        userService.save();
        /*Spring容器加载时会将容器内的类进行实例化（默认）*/

        /*从Spring容器中获取Bean*/

       /* UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        UserDao userDao2 = (UserDao) applicationContext.getBean("userDao");
        //userDao.save();
        System.out.println(userDao==userDao2);//ttrue*/
    }
}
