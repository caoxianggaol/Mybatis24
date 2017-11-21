package com.kaishengit.weixin;


import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.weixin.exception.WeixinException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaogao on 2017/11/21.
 */
@Component
public class WeiXinUtil {

    private final String GET_ACCESS_TOKRN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    @Value("${weixin.corpID}")
    private String corpID;

    @Value("${weixin.secret}")
    private String secret;


    /*guava中的缓存LoadingCache*/
    //构建Cache对象
    /**
     * AccessToken的缓存
     */
    private LoadingCache<String,String> accessTokenCache = CacheBuilder.newBuilder()
            /*.expireAfterAccess()//最后一访问多久过期*/
            .expireAfterWrite(7200, TimeUnit.SECONDS)//写进去后多久过期
            .build(new CacheLoader<String, String>() {
                @Override//匿名局部内不类 开始时调用 LoadingCache中的load方法获取值供使用
                //再7200s内 如果取值会从缓存中取  过来7200秒时 如果取值则会再次调用load方法获取
                public String load(String s) throws Exception {
                    //缓存中没值时调用此方法 去哪取如下
                    String url = String.format(GET_ACCESS_TOKRN_URL,corpID,secret);
                    String resultJson = sendHttpGetRequest(url);//调用下面方法
                    //将json转为Map集合（因为内部为键值对）
                    Map<String,Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if(map.get("errcode").equals(0)) {//判断是否有错 如果相等则无措
                        System.out.println("get AccessToken from weixin");
                        return (String) map.get("access_token");
                    }
                    throw new WeixinException(resultJson);

                }
            });
    /**
     * 获取AccessToken
     * @return
     */
    public String getAccessToken() {
        //缓存  参数为内不类中的参数 但是此处不用 所以为""
        try {
            return accessTokenCache.get("");
        }catch(ExecutionException e) {
            throw new RuntimeException("获取accessToken异常",e);

        }





        //匿名局部内部类代替下面内容
            /*拼url 并发出请求*//*
        String url = String.format(GET_ACCESS_TOKRN_URL,corpID,secret);
        String resultJson = sendHttpGetRequest(url);//调用下面方法
        //将json转为Map集合（因为内部为键值对）
        Map<String,Object> map = JSON.parseObject(resultJson, HashMap.class);
        if(map.get("errcode").equals(0)) {//判断是否有错 如果相等则无措
            return (String) map.get("access_token");
        }
        throw new WeixinException(resultJson);*/
    }
/*================================================================*/
    /**
     * 发出Http的get请求
     * @param url 请求的URL地址
     */
    private String sendHttpGetRequest(String url) {

        //先创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //构建请求对象
        Request request = new Request.Builder()
                .url(url)//参数url
                .build();
        //通过client.newCall()发出请求 返回Response对象(json)
        try {
            Response response = client.newCall(request).execute();
            return response.body().string(); //拿到结果将其转换为字符串
        } catch (IOException ex) {
            throw new RuntimeException("HttpGet请求异常",ex);
        }
    }

    /**
     *发出Http的POST请求
     */
    private void sendHttpPostRequest() {

    }

/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/


}
