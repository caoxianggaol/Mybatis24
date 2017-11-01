package com.kaishengit.service.Impl;


import com.kaishengit.entity.Student;
import com.kaishengit.mapper.StudentMapper;
import com.kaishengit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交给Spring管理
 * Created by xiaogao on 2017/11/1.
 */
@Service
public class StudentServiceImpl implements StudentService {
    /*注入*/
    @Autowired
   private StudentMapper studentMapper;


    @Override
    public Student findById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Student student) {
        studentMapper.insert(student);
    }
}
