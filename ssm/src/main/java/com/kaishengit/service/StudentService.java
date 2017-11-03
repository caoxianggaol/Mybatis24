package com.kaishengit.service;

import com.kaishengit.entity.Student;

import java.util.List;

/**
 * Created by xiaogao on 2017/11/1.
 */
public interface StudentService {

    Student findById(Integer id);
    void save(Student student);
    List<Student> findAll();
    List<Student> findByPageNo(Integer pageNo);




}
