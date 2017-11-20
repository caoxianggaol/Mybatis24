package com.kaishengit.timertask;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xiaogao on 2017/11/19.
 */
public class FastDfsTest {

    @Test
    public void uploadFile() throws IOException, MyException {
        //配置tracker
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.27.111:22122");
       //初始化配置
        ClientGlobal.initByProperties(properties);

        //文件上传
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        InputStream inputStream = new FileInputStream("F:/upload/1.jpeg");
        byte[] bytes = IOUtils.toByteArray(inputStream);

        String[] resulets = storageClient.upload_file(bytes,"jpeg",null);
        for(String str : resulets) {
            System.out.println(str);
        }

        inputStream.close();
    }




    @Test
    public void uploadFileWithMetaData() throws IOException, MyException {
        //配置tracker(告诉tracker的位置)
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.27.111:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        //文件上传
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        InputStream inputStream = new FileInputStream("F:/upload/1.jpeg");
        byte[] bytes = IOUtils.toByteArray(inputStream);

        //metadata(类似标签) 其他信息设置
        NameValuePair[] nameValuePairs = new NameValuePair[3];//长度为3
        nameValuePairs[0] = new NameValuePair("width","300");
        nameValuePairs[1] = new NameValuePair("device","iPhone");
        /*拍照片时的感光度（快门速度） storageClient.upload_file中的第三个参数*/
        nameValuePairs[2] = new NameValuePair("iso","3000");

        String[] resulets = storageClient.upload_file(bytes,"jpeg",nameValuePairs);

        for(String str : resulets) {
            System.out.println(str);
        }

       /*上传时服务器会存储并返回  group1
                M00/00/00/wKgbb1oRqHiAYqwQAALwVZu1QsI67.jpeg*/

        inputStream.close();
    }
    /*+===========================================================*/
    /**
     * 文件下载
     */
    @Test
    public void downloadFile() throws IOException, MyException {

        //配置tracker
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.27.111:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        //文件下载
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        byte[] bytes = storageClient.download_file("group1","M00/00/00/wKgbb1oRqHiAYqwQAALwVZu1QsI67.jpeg");
       /* InputStream inputStream = IOUtils.toInputStream(new String(bytes),"UTF-8");*/
        FileOutputStream outputStream = new FileOutputStream("F:/new.jpeg");
        outputStream.write(bytes,0,bytes.length);

        outputStream.flush();
        outputStream.close();

    }

    @Test
    public void downloadFileWithMetaData() throws IOException, MyException {

        //配置tracker
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.27.111:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        //文件下载
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        byte[] bytes = storageClient.download_file("group1","M00/00/00/wKgbb1oRqHiAYqwQAALwVZu1QsI67.jpeg");
       /* InputStream inputStream = IOUtils.toInputStream(new String(bytes),"UTF-8");*/

       //获得metaData数组
        NameValuePair[] nameValuePairs = storageClient.get_metadata("group1","M00/00/00/wKgbb1oRqHiAYqwQAALwVZu1QsI67.jpeg");
        for(NameValuePair nameValuePair : nameValuePairs) {
            System.out.println(nameValuePair.getName()+ "->" + nameValuePair.getValue());
        }//取出上传时的信息

        //Fileinfo 传的时候不用管，下载时可以取出
        FileInfo fileInfo = storageClient.get_file_info("group1","M00/00/00/wKgbb1oRqHiAYqwQAALwVZu1QsI67.jpeg");
        System.out.println("FileSize" + fileInfo.getFileSize());//大小
        System.out.println("Crc32" + fileInfo.getCrc32());//用于校（全称为：循环冗余校验）
        System.out.println("createtime" + fileInfo.getCreateTimestamp());//创建时间
        System.out.println("ip" + fileInfo.getSourceIpAddr());//存储服务器



       FileOutputStream outputStream = new FileOutputStream("F:/new.jpeg");
        outputStream.write(bytes,0,bytes.length);

        outputStream.flush();
        outputStream.close();

    }
}
