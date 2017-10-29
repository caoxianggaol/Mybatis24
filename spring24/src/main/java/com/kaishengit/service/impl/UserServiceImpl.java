package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;

/**
 * Created by xiaogao on 2017/10/28.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private String address;

    public UserServiceImpl(){}

    public void setAddress(String address) {
        this.address = address;
    }

    public UserServiceImpl(UserDao userDao, String name){
        this.userDao = userDao;
    }


    @Override
    public void save() {

        userDao.save();
    }
}
