package com.kaishengit.mapper;

import com.kaishengit.entity.Student;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by xiaogao on 2017/10/24.
 */
public interface StudentMapper {

    //返回值为int时表示受影响的行数
    void save(Student student);
    void update(Student student);
    void delete(int id);
    List<Student> findAll();
    Student findById(int id);
}
