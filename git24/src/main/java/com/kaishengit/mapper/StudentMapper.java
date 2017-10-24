package com.kaishengit.mapper;

import com.kaishengit.entity.Student;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by xiaogao on 2017/10/24.
 */
public interface StudentMapper {

    void save(Student student);
    void update(Student student);
    void delete(int id);
    List<Student> findAll();
    Student findById(int id);
}
