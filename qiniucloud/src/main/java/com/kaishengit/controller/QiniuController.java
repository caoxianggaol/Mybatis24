package com.kaishengit.controller;

import com.qiniu.util.Auth;

import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;

/**
 * Created by xiaogao on 2017/11/20.
 */
@Controller
public class QiniuController {

    @GetMapping("/upload")
    private String upload(Model model) {

        //生成七牛上传文件凭证
        //key的位置 七牛云->个人面板->密钥管理
        String accessKey = "vb8EtRoV8YTEzFqNAGlTbU3tJHdMQRTTZ0uWvFav";//access key
        String secretKey = "Yh1RY6WYvXZdRnfz_8A3voGs6ls8PonP07j8vkPx";//secret key
        String bucket = "java24";//"bucket name

        //创建Auth对象 普通上传
        Auth auth = Auth.create(accessKey,secretKey);
        //String uploadToken = auth.uploadToken(bucket);

        //设置回调Json格式（上传七牛时不返回json而是回调业务服务器写入数据库）
        // StringMap stringMap = new StringMap();
        /*returnBody key值  自定义json返回格式{fileKey:"$(key)"}  $(key)占位符*/
        //stringMap.put("returnBody","{\"fileKey\":\"$(key)\"}");

        //303重定向 时的地址returnUrl
        StringMap stringMap = new StringMap();
        stringMap.put("returnUrl","http://localhost/upload/callback");

        /*参数 1.空间名 2.上传时设置的名字 3.产生的Token的存活周期 4. stringMap*/
        String uploadToken = auth.uploadToken(bucket,null,3600,stringMap);
        model.addAttribute("uploadToken",uploadToken);

        return "upload";
    }

    @GetMapping("/upload/callback")
    /*upload_ret固定的*/
    public String uploadCallback(String upload_ret) {
        //vb8EtRoV8YTEzFqNAGlTbU3tJHdMQRTTZ0uWvFav:nxwYh5s_1kEdoPU1j5pG35GgbqQ=:eyJzY29wZSI6ImphdmEyNCIsInJldHVyblVybCI6Imh0dHAvL2xvY2FsaG9zdC91cGxvYWQvY2FsbGJhY2siLCJkZWFkbGluZSI6MTUxMTE4NjcxNX0=
        //base64可逆 decode(解密) Base64来自java.util
        //解密后{"hash":"Fn4iz5KSwXBj1U6fVWOJbFs7r1hg","key":"Fn4iz5KSwXBj1U6fVWOJbFs7r1hg","x:pid":"1009"}
        String jsonString = new String(Base64.getDecoder().decode(upload_ret));
        System.out.println(jsonString);
        return "redirect:/upload";
    }

}
