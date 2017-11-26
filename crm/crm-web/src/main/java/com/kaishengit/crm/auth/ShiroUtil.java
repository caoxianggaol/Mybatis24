package com.kaishengit.crm.auth;

import com.kaishengit.crm.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * shiro 的工具类
 * Created by xiaogao on 2017/11/25.
 */
public class ShiroUtil {
    /**
     * 获得当前登录对象
     * @return
     */
    public static Account getCurrentAccount() {
        /*获取账号对象 即登录主体*/
        //Subject subject = SecurityUtils.getSubject();
        /*获得当前登录对象*/
       // Account account = (Account) subject.getPrincipal();
        //return account;

        return (Account) getSubject().getPrincipal();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
