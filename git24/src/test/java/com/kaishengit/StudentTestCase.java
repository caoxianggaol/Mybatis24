package com.kaishengit;


import com.kaishengit.entity.Student;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * Created by xiaogao on 2017/10/23.
 */
public class StudentTestCase {

    @Test
    public void save()throws Exception{

            //1.从classpath中获取mybatis-config.xml配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        /*InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");*/

        //2.根据SqlSessionFactoryBuilder对象构建SqlSessionFactory
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);


        //3.根据SqlSessionFactory对象创建SqlSession对象
        SqlSession sqlSession = sessionFactory.openSession();

        Student student = new Student("k",20);

        //执行insert操作 参数1为namespace属性的值加insert节点的id属性值 2为封装的对象
        sqlSession.insert("com.kaishengit.mapper.StudentMapper.save",student);

        //提交
        sqlSession.commit();

        //释放资源
        sqlSession.close();
    }

    @Test
    public void findAll(){

        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        List<Student> studentList = sqlSession.selectList("com.kaishengit.mapper.StudentMapper.findAll");

        for (Student student : studentList){
            System.out.println(student);
        }

        sqlSession.close();
    }


    @Test
    public void findById() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Student student = sqlSession.selectOne("com.kaishengit.mapper.StudentMapper.findById",14);
        System.out.println(student);

        sqlSession.close();
    }


   @Test
    public void update() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Student student = sqlSession.selectOne("com.kaishengit.mapper.StudentMapper.findById",9);
        student.setStuAge(20);
        student.setStuName("丽丽");

        sqlSession.update("com.kaishengit.mapper.StudentMapper.update",student);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void delete() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        sqlSession.delete("com.kaishengit.mapper.StudentMapper.delete",15);
        sqlSession.commit();
        sqlSession.close();
    }
}
