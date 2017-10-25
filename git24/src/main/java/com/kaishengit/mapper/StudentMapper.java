package com.kaishengit.mapper;

import com.kaishengit.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaogao on 2017/10/24.
 */
public interface StudentMapper {

    //前三种方法的返回值可以为int，为int时表示受影响的行数，不用配置
    void save(Student student);
    void update(Student student);
    void delete(int id);
    List<Student> findAll();
    Student findById(int id);
   // List<Student> page(Map<String,Integer> map);
    //正常-->
   //
   // /* #{offset},#{size}*/直接传两参数sql中写法--> /*直接传两参数时#{offset},#{size}会抛错，
   // 处理如下1.#{arg0},#{arg1}2.#{param1},#{param2}*/
    /*最好解决方案为加注解 @Param(别名)之后sql中可以写成#{offset},#{size}*/
    List<Student> page(@Param("offset") int offset,@Param("size") int size);
}
