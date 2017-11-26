package com.kaishengit.crm.auth;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaogao on 2017/11/24.
 */
public class ShiroAuth extends AuthorizingRealm{

    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 角色或权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
       //获取当前登录的account对象（下面方法返回的） 两种都行
        //Account account = (Account) principalCollection.fromRealm(getName()).iterator().next();
        Account account = (Account) principalCollection.getPrimaryPrincipal();
       //根据登录的对象获取所在部门列表
        List<Dept> deptList = accountService.findDeptByAccountId(account.getId());

        //获取Dept集合中的名称，创建字符串列表
        List<String> deptNameList = new ArrayList<>();
        for(Dept dept : deptList) {
            deptNameList.add(dept.getDeptName());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将部门名称作为当前用户的角色（角色是权限的集合）
        simpleAuthorizationInfo.addRoles(deptNameList);
        //权限
        //simpleAuthorizationInfo.setStringPermissions(集合);

        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userName = usernamePasswordToken.getUsername();
        Account account = accountService.findByMobile(userName);
        if(account != null) {
            /*参数一 为主体*/
            return new SimpleAuthenticationInfo(account,account.getPassword(),getName());
        }
        return null;
    }
}
