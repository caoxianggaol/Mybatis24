package com.kaishengit.timertask;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
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
}
