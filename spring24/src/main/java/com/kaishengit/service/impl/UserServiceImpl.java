package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by xiaogao on 2017/10/28.
 */
public class UserServiceImpl implements UserService {

    /*基本类型及集合注入*/
    private Integer id;
    private String name;
    private List<String> nameaList;
    private Set<UserDao> userDaoSet;
    private Map<String,UserDao> UserDaoMap;
    /*Properties本质为键值对*/
    private Properties properties;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameaList(List<String> nameaList) {
        this.nameaList = nameaList;
    }

    public void setUserDaoSet(Set<UserDao> userDaoSet) {
        this.userDaoSet = userDaoSet;
    }

    public void setUserDaoMap(Map<String, UserDao> userDaoMap) {
        UserDaoMap = userDaoMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }





    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {

        userDao.save();
    }
}
