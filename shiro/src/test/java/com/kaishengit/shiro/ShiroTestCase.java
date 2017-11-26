package com.kaishengit.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;

/**
 * Created by xiaogao on 2017/11/24.
 */
public class ShiroTestCase {

    public void helloShiro() {
        //1.创建SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2. 使用工厂创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        //3. 使用SecurityUtils设置SecurityManager
        SecurityUtils.setSecurityManager(securityManager);





    }

}
