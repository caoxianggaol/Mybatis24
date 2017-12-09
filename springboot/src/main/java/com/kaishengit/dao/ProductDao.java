package com.kaishengit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by xiaogao on 2017/12/8.
 */
@Repository
public class ProductDao {

    //pom.xml中添加了spring-boot-starter-jdbc依赖 直接可以用 不用配置
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void save(String name,Integer age,String address) {
        String sql = "insert into user(name,age,address) value(?,?,?)";
        jdbcTemplate.update(sql,name,age,address);
    }




}
