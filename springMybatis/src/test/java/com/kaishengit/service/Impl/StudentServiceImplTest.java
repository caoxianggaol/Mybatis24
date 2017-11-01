package com.kaishengit.service.Impl;

import com.kaishengit.entity.Student;
import com.kaishengit.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by xiaogao on 2017/11/1.
 */
public class StudentServiceImplTest extends BaseTest{

    @Autowired
    private StudentServiceImpl studentService;

    @Test
    public void findById() throws Exception {

        Student student = studentService.findById(22);
        System.out.println(student);
    }

    @Test
    public void save() {
        Student student = new Student();
        student.setStuName("jack");
        student.setStuAge(25);

        studentService.save(student);
        System.out.println("--->" + student.getId());


    }
}