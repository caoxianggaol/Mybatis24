package com.kaishengit.service;

import com.kaishengit.entity.Student;

/**
 * Created by xiaogao on 2017/11/1.
 */
public interface StudentService {

    Student findById(Integer id);
    void save(Student student);



}
