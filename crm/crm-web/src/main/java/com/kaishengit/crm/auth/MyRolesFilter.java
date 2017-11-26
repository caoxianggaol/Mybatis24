package com.kaishengit.crm.auth;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义角色（Roles）过滤器，用来满足多个角色or的关系
 * Created by xiaogao on 2017/11/26.
 */
public class MyRolesFilter extends RolesAuthorizationFilter{
    @Override//是否被允许
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
       //获取当前登录对象
        Subject subject = getSubject(request,response);
        //获取用户配置项
        String[] roleNames = (String[]) mappedValue;
        if(roleNames == null || roleNames.length == 0) {
            //所有角色都可以访问
            return true;
        }
        for(String roleName : roleNames) {
            //用户 只要拥有任何一个角色都可以访问
            if(subject.hasRole(roleName)) {
                return true;
            }
        }
        return false;
    }
}
