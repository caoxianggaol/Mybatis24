package com.kaishengit.service.impl;

import com.google.gson.Gson;
import com.kaishengit.entity.Seckill;
import com.kaishengit.entity.SeckillExample;
import com.kaishengit.mapper.SeckillMapper;
import com.kaishengit.service.SeckillService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by xiaogao on 2017/12/5.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillMapper seckillMapper;
    //TODO
    @Value("${qiniu.ak}")
    private String qiniuAk;
    @Value("${qiniu.sk}")
    private String qiniuSk;
    @Value("${qiniu.buket}")
    private String qiniuBuket;
    /**
     * 添加商品（图片）
     *
     * @param seckill
     * @param inputStream
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveSeckill(Seckill seckill, InputStream inputStream) {
        //上传七牛 调下面方法
        String key = uploadToQiniu(inputStream);
        //保存seckill对象
        seckill.setProductImage(key);
        seckillMapper.insertSelective(seckill);

    }

    /**
     * 显示全部抢购商品
     *
     * @return
     */
    @Override
    public List<Seckill> findAll() {
        SeckillExample seckillExample = new SeckillExample();
        seckillExample.setOrderByClause("start_time asc");
        return seckillMapper.selectByExample(seckillExample);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    @Override
    public Seckill findById(Integer id) {
        return seckillMapper.selectByPrimaryKey(id);
    }

    /*================================================================================*/
    private String uploadToQiniu(InputStream inputStream) throws RuntimeException{
        /*上传七牛云*/
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        /*获取口令*/
        Auth auth = Auth.create(qiniuAk,qiniuSk);
        String uploadToken = auth.uploadToken(qiniuBuket);

        try {
            Response response = uploadManager.put(IOUtils.toByteArray(inputStream),null,uploadToken);
            /*获取上传到七牛的key
            * fromJson是Gson提供的一个方法。用来将一个Json数据转换为对象。调用方法是：new Gson().fromJson(Json_string,class)
            * */
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            return defaultPutRet.key;//把key存入数据库
        } catch (IOException e) {
            throw new RuntimeException("上传文件到七牛失败",e);//回滚
        }
    }
}
