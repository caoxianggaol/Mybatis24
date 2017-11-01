package com.kaishengit.dao;

import com.kaishengit.entity.Student;
import com.kaishengit.service.Impl.StudentServiceImpl;
import com.kaishengit.service.StudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by xiaogao on 2017/10/30.
 */
public class StudentDaoTest {

  /*  @Test
    public void hello() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        StudentDao studentDao = (StudentDao) applicationContext.getBean("studentDao");
        studentDao.save();

    }*/

    @Test
    public void save() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        StudentService studentService = (StudentServiceImpl) applicationContext.getBean("studentServiceImpl");


    }
}
