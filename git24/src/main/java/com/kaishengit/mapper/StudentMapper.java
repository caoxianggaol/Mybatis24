package com.kaishengit.mapper;

import com.kaishengit.entity.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaogao on 2017/10/24.
 */
/*没有xml文件时的做法相当于<cache/>
* 有xml加在xml里（加<cache/>节点）*/
/*@CacheNamespace*/
public interface StudentMapper {

    //前三种方法的返回值可以为int，为int时表示受影响的行数，不用配置
    @Insert("insert into student(stu_name,stu_age) values(#{stuName},#{stuAge})")
    /*@Options(useGeneratedKeys = true,keyProperty = "id") 主键*/
   /* @Options(flushCache = false) 不刷新缓存*/
    void save(Student student);

    @Update("update student set stu_name = #{stuName},stu_age = #{stuAge} where id = #{id}")
    void update(Student student);
    @Delete("delete from student where id = #{id}")
    void delete(int id);
    @Select("select * from student")
    /*@Options(useCache = false)  不被缓存*/
    List<Student> findAll();
    @Select("select * from student where id = #{id}")
    Student findById(int id);
   // List<Student> page(Map<String,Integer> map);
    //正常-->
   //
   // /* #{offset},#{size}*/直接传两参数sql中写法--> /*直接传两参数时#{offset},#{size}会抛错，
   // 处理如下1.#{arg0},#{arg1}2.#{param1},#{param2}*/
    /*最好解决方案为加注解 @Param(别名)之后sql中可以写成#{offset},#{size}*/

    List<Student> page(@Param("offset") int offset,@Param("size") int size);

    /*批量添加*/
    int batchSave(@Param("studentList") List<Student> studentList);
}
