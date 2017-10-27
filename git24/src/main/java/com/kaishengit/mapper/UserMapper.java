package com.kaishengit.mapper;


import com.kaishengit.entity.User;

import org.apache.ibatis.annotations.*;


/**
 * Created by xiaogao on 2017/10/25.
 */
public interface UserMapper {

    @Select("select * from t_user where id = #{userId}")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "address", property = "address"),
            @Result(column = "password", property = "password"),
            @Result(column = "dept_id", property = "deptId"),
            @Result(column = "dept_id", property = "dept", one = @One(select = "com.kaishengit.mapper.DeptMapper.findById")),
            @Result(column = "id", property = "tagList", many = @Many(select = "com.kaishengit.mapper.TagMapper.findByUserId"))
    })
    User findById(Integer userId);
    User findByIdWithTag(Integer userId);

}
