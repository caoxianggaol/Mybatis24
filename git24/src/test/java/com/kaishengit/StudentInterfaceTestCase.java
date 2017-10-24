package com.kaishengit;

import com.kaishengit.entity.Student;
import com.kaishengit.mapper.StudentMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by xiaogao on 2017/10/24.
 */
public class StudentInterfaceTestCase {

    private SqlSession sqlSession;
    private StudentMapper studentMapper;

    @Before
    public void init(){
        sqlSession = MyBatisUtil.getSqlSession();
        studentMapper = sqlSession.getMapper(StudentMapper.class);

    }

    @After
    public void close(){
        sqlSession.close();
    }

    @Test
    public void save(){
//StudentMapper对象根据接口的class动态创建接口的实现类
        //使用动态代理模式
        //StudentMapper studentMapper =  sqlSession.getMapper(StudentMapper.class);

        Student student = new Student("天明",20);
        studentMapper.save(student);

        sqlSession.commit();
    }

    @Test
    public void findAll(){
        List<Student> studentList = studentMapper.findAll();

        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    @Test
    public void findById(){

        Student student = studentMapper.findById(2);
        System.out.println(student);
    }

    @Test
    public void update(){

        Student student = studentMapper.findById(9);
        student.setStuAge(21);

        studentMapper.update(student);
        sqlSession.commit();
    }

    @Test
    public void delete(){
        studentMapper.delete(4);
        sqlSession.commit();
    }
}
