package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;

/**
 * Created by xiaogao on 2017/10/28.
 */
public class UserServiceImpl implements UserService {

    @Override
    public void save() {
        System.out.println("save");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public int count() {
        return 10;
    }








/*自动注入*//*
    private UserDao userDao;

    *//*public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }*//*

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {

        userDao.save();
    }*/
}
