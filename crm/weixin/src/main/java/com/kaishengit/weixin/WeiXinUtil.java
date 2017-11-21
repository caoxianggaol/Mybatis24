package com.kaishengit.weixin;


import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.weixin.exception.WeixinException;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaogao on 2017/11/21.
 */
@Component
public class WeiXinUtil {

    public static final String ACCESSTOKEN_TYPE_NORMAL = "normal";//普通

    public static final String ACCESSTOKEN_TYPE_CONTACTS = "contacts";//通讯录

    /**
     * 获取AccessToken的URL
     */
    private static final String GET_ACCESS_TOKRN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    /**
     * 创建部门的URL
     */
    private static final String POST_CREATE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";
    /**
     * 删除部门的URL
     */
    private static final String GET_DEL_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=%s&id=%s";
    /**
     * 创建成员的URL
     */
    private static final String POST_CREATE_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=%s";
    /**
     * 删除部门成的URL
     */
    private static final String GET_DEL_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=%s&userid=%s";
    /**
     * 发送文本消息的URL
     */
    private static final String POST_SEND_MASSAGE = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";

    @Value("${weixin.corpID}")
    private String corpID;

    @Value("${weixin.secret}")
    private String secret;

    @Value("${weixin.contact.secret}")
    private String contactSecret;

    @Value("${weixin.app.agentid}")
    private Integer agentid;


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
                public String load(String type) throws Exception {
                    //缓存中没值时调用此方法 去哪取如下
                    String url;
                    //判断获取的是普通的AccessToken还是通讯录的Accesstoken
                    if(ACCESSTOKEN_TYPE_CONTACTS.equals(type)) {
                        url = String.format(GET_ACCESS_TOKRN_URL,corpID,contactSecret);
                    } else {//普通的和通讯录secret不同
                        url = String.format(GET_ACCESS_TOKRN_URL,corpID,secret);
                    }

                    String resultJson = sendHttpGetRequest(url);//调用下面方法
                    //将json转为Map集合（因为内部为键值对）
                    Map<String,Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if(map.get("errcode").equals(0)) {//判断是否有错 如果相等则无措
                        System.out.println("get AccessToken from weixin");
                        //传入的secret不同换回的access_token不同
                        return (String) map.get("access_token");
                    }
                    throw new WeixinException(resultJson);

                }
            });
    /**
     * 获取AccessToken
     * @return type 获取AccessToken的类型 normal(普通的，正常的) contacts(通讯录)
     */
    public String getAccessToken(String type) {
        //缓存  参数为内部类中load方法的参数 type
        try {
            return accessTokenCache.get(type);
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

    /**
     * 创建部门
     * @param id
     * @param parentId
     * @param name
     */
    public void createDept(Integer id,Integer parentId,String name) {

    //构建发送请求的url 其中带有json格式的请求体
        String url = String.format(POST_CREATE_DEPT_URL,getAccessToken(ACCESSTOKEN_TYPE_CONTACTS));

        Map<String,Object> data = new HashMap<String, Object>();
        data.put("name",name);
        data.put("parentid",parentId);
        data.put("id",id);

        String resultJson = sendHttpPostRequest(url,JSON.toJSONString(data));
        Map<String,Object> resultMap = JSON.parseObject(resultJson,HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("创建部门失败"+resultJson);
        }
    }

    /**
     * 删除部门
     * @param id 部门主键
     */
    public void delDept(Integer id) {
        String url = String.format(GET_DEL_DEPT_URL,getAccessToken(ACCESSTOKEN_TYPE_CONTACTS),id);
        String resultJson = sendHttpGetRequest(url);
        Map<String,Object> resultMap = JSON.parseObject(resultJson,HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("删除部门异常" + resultJson);
        }
    }

    /**
     * 创建部门成员
     * @param accountId 账号Id（唯一）
     * @param name     账号姓名
     * @param mobile   手机号码
     * @param departmentIdList 所属部门的Id列表
     */
    public void createAccount(Integer accountId, String name, String mobile, List<Integer> departmentIdList) {
        String url = String.format(POST_CREATE_ACCOUNT_URL,getAccessToken(ACCESSTOKEN_TYPE_CONTACTS));

        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("userid",accountId);
        resultMap.put("name",name);
        resultMap.put("mobile",mobile);
        resultMap.put("department",departmentIdList);

        String resultJson = sendHttpPostRequest(url,JSON.toJSONString(resultMap));
        Map<String,Object> map = JSON.parseObject(resultJson,HashMap.class);
        if(!map.get("errcode").equals(0)) {
            throw new WeixinException("添加成员失败"+ resultJson);
        }
    }

    /**
     * 删除成员
     * @param id 账户Id
     */
   public void delAccount(Integer id) {

        String url = String.format(GET_DEL_ACCOUNT_URL,getAccessToken(ACCESSTOKEN_TYPE_CONTACTS),id);
        String resultJson = sendHttpGetRequest(url);
        Map<String,Object> resultMap = JSON.parseObject(resultJson,HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("删除成员失败"+resultJson);
        }
   }

    /**
     * 发送文本消息给用户
     * @param userIdList 接收消息的用户id
     * @param message 消息内容（支持转义字符和HTML）
     */
   public void sedTextMessageToUser(List<Integer> userIdList,String message) {

       String url = String.format(POST_SEND_MASSAGE,getAccessToken(ACCESSTOKEN_TYPE_NORMAL));//普通
        /*给多个人发消息 拼接 截取*/
       StringBuilder stringBuilder = new StringBuilder();
       for(Integer userId : userIdList) {
           stringBuilder.append(userId).append("|");
       }
       String idString = stringBuilder.toString();
       idString = idString.substring(0,idString.lastIndexOf("|"));

       Map<String,Object> data = new HashMap<String, Object>();
       data.put("touser",idString);//接收人的id
       data.put("msgtype","text");
       data.put("agentid",agentid);
       Map<String,Object> messageMap = new HashMap<String, Object>();
       messageMap.put("content",message);//message 参数
       data.put("text",messageMap);

       String resultJson = sendHttpPostRequest(url,JSON.toJSONString(data));

       Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
       if(!resultMap.get("errcode").equals(0)) {
           throw new WeixinException("发送文本消息失败"+resultJson);
       }
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
     * 发出Http的POST请求
     * @param url 目标的url
     * @param json 请求体
     */
    private String sendHttpPostRequest(String url,String json) {
        /*参照 Okhttp中发出一个post请求*/
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        //通过JSON格式构建Post请求
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("HttpPost请求异常",e);
        }

    }

/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/


}
