package com.kaishengit.controller;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
/*==================================================================*/
    @GetMapping("/upload/local")
    public String uploadLocal() {
        return "upload_loacl";
    }

   /*私有空间上传*/
    @PostMapping("/upload/local")
    /*file需要和jsp页面的表单中的name属性值一样*/
    public String uploadLocal(MultipartFile file) throws IOException {
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        //生成七牛上传文件凭证
        //key的位置 七牛云->个人面板->密钥管理
        String accessKey = "vb8EtRoV8YTEzFqNAGlTbU3tJHdMQRTTZ0uWvFav";//access key
        String secretKey = "Yh1RY6WYvXZdRnfz_8A3voGs6ls8PonP07j8vkPx";//secret key
        String bucket = "java24";//"bucket name

        Auth auth = Auth.create(accessKey,secretKey);
        String uploadToken = auth.uploadToken(bucket);

        byte[] bytes = file.getBytes();
        Response response = uploadManager.put(bytes,null,uploadToken);
        //System.out.println(response.bodyString());
        DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
        System.out.println("key -> " + defaultPutRet.key);
        return "redirect:/upload/local";
    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {

        String domainOfBucket = "http://ozoybvszl.bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(name, "utf-8");
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        //如果是私有空间，需要通过Auth对象获取临时的下载路径
        String accessKey = "your access key";
        String secretKey = "your secret key";
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
       // String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);


        HttpURLConnection urlConnection = (HttpURLConnection) new URL(finalUrl).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        OutputStream outputStream = response.getOutputStream();

        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();


    }

}
