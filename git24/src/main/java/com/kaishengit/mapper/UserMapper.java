package com.kaishengit.mapper;


import com.kaishengit.entity.User;

/**
 * Created by xiaogao on 2017/10/25.
 */
public interface UserMapper {

    User findById(Integer userId);

}
