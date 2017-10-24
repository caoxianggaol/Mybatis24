package com.kaishengit;

import com.kaishengit.entity.Student;
import com.kaishengit.mapper.StudentMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xiaogao on 2017/10/24.
 */
public class StudentInterfaceTestCase {

    private SqlSession sqlSession;

    @Before
    public void init(){
        sqlSession = MyBatisUtil.getSqlSession();
    }

    @After
    public void close(){
        sqlSession.close();
    }

    @Test
    public void save(){
//StudentMapper对象根据接口的class动态创建接口的实现类
        //使用动态代理模式
        StudentMapper studentMapper =  sqlSession.getMapper(StudentMapper.class);

        Student student = new Student("天明",20);
        studentMapper.save(student);

        sqlSession.commit();

    }
}
