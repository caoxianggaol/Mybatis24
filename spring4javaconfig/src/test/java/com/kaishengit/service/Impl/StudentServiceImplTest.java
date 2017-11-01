package com.kaishengit.service.Impl;

import com.kaishengit.entity.Student;
import com.kaishengit.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by xiaogao on 2017/11/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;
    @Test
    public void save() throws Exception {

      /*  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        StudentService studentService = (StudentServiceImpl) applicationContext.getBean("studentDao");*/
        Student student = new Student();
        student.setStuName("aa");
        student.setStuAge(22);
        studentService.save(student);

    }

}