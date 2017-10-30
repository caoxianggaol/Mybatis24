package com.kaishengit.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by xiaogao on 2017/10/30.
 */
public class StudentDaoTest {

    @Test
    public void hello() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        StudentDao studentDao = (StudentDao) applicationContext.getBean("studentDao");
        studentDao.save();

    }
}
