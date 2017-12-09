package com.kaishengit.mapper;

import com.kaishengit.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用mybatis 添加mybatis-spring-boot-starter依赖  还需要配置
 * 之前需要自动扫描  现在只需注解
 * Created by xiaogao on 2017/12/9.
 */
@Mapper
public interface ProductMapper {

    //没有Mapper.xml配置时的写法
    /*@Insert("insert into user(name,age,address) value(#{name},#{age},#{address})")
    void save (@Param("name") String name,
               @Param("age") Integer age,
               @Param("address") String address);*/

//有Mapper.xml配置时的写法
   /* void save (@Param("name") String name,
               @Param("age") Integer age,
               @Param("address") String address);
*/

    //传入一个对象
    void save(Product product);


}
