package com.kaishengit.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by xiaogao on 2017/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)//让测试运行于Spring测试环境
@ContextConfiguration(locations = "classpath:applicationContext-weixin.xml")//读取
public class WeixinUtilTestcase {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Test
    public void getAccessToken() {
        //不同参数获取不同
        String accessToken = weiXinUtil.getAccessToken(WeiXinUtil.ACCESSTOKEN_TYPE_CONTACTS);//获取
       // accessToken = weiXinUtil.getAccessToken();//缓存
       // accessToken = weiXinUtil.getAccessToken();//缓存
        System.out.println(accessToken);//获取到值再做后面，如果没值后面不用做
    }

    @Test
    public void createDept() {
        weiXinUtil.createDept(102,100,"市发部");
    }

    @Test
    public void delDept() {
        weiXinUtil.delDept(102);
    }

    @Test
    public void createAccount() {
        weiXinUtil.createAccount(1,"小明","15293869055", Arrays.asList(101));
    }


    @Test
    public void delAccount() {
        weiXinUtil.delAccount(1);
    }

    @Test
    public void sendMAssage() {
        /*weiXinUtil.sedTextMessageToUser(null,"\"你有一个待办任务需要今天完成，请点击<a href=\\\"http://www.kaishengit.com\\\">链接</a>查看\"");*/
        weiXinUtil.sedTextMessageToUser(Arrays.asList(100,109,108),"\"你有一个待办任务需要今天完成，请点击<a href=\\\"http://www.kaishengit.com\\\">链接</a>查看\"");
    }
}
