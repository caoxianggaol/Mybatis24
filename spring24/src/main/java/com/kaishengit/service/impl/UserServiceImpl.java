package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;

/**
 * Created by xiaogao on 2017/10/28.
 */
public class UserServiceImpl implements UserService {
/*自动注入*/
    private UserDao userDao;

    /*public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }*/

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {

        userDao.save();
    }
}
