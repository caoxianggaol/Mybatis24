package com.kaishengit.weixin.mq;

import com.alibaba.fastjson.JSON;
import com.kaishengit.weixin.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaogao on 2017/11/23.
 */
@Component
public class WeixinConsumer {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @JmsListener(destination = "weixinmessage-Queue")
    public void SendMessageToUser(String json) {
        Map<String,Object> map = JSON.parseObject(json, HashMap.class);
        weiXinUtil.sedTextMessageToUser(Arrays.asList(1),map.get("message").toString());
    }
}
