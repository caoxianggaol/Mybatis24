package com.kaishengit.crm.controller;


import com.kaishengit.crm.entity.Account;

import javax.servlet.http.HttpSession;

/**
 * 作为所有Controller的父类，提供控制器公共的方法
 * 抽象的不能被实例化的 只作为父类而存在
 * Created by xiaogao on 2017/11/12.
 */
public abstract class BaseController {

    /**
     * 获取当前登录系统的账户对象
     * @return
     */
    public Account getCurrentAccount(HttpSession httpSession) {
        return (Account) httpSession.getAttribute("curr_account");
    }
}
