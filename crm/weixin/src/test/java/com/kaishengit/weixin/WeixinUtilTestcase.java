package com.kaishengit.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        String accessToken = weiXinUtil.getAccessToken();//获取
        accessToken = weiXinUtil.getAccessToken();//缓存
        accessToken = weiXinUtil.getAccessToken();//缓存
        System.out.println(accessToken);//获取到值再做后面，如果没值后面不用做
    }
}
