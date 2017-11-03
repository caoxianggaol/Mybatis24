package com.kaishengit.service.Impl;


import com.github.pagehelper.PageHelper;
import com.kaishengit.entity.Student;
import com.kaishengit.entity.StudentExample;
import com.kaishengit.mapper.StudentMapper;
import com.kaishengit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Student> findAll() {
        return studentMapper.selectByExample(new StudentExample());
    }

    @Override
    public List<Student> findByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);//从pageNo里，一页10条
       StudentExample studentExample = new StudentExample();
        return studentMapper.selectByExample(studentExample);
    }
}
