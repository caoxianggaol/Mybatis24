package com.kaishengit.service;

import com.kaishengit.entity.Seckill;


import java.io.InputStream;
import java.util.List;

/**
 * Created by xiaogao on 2017/12/5.
 */

public interface SeckillService {
    /**
     * 添加商品（图片）
     * @param seckill
     * @param inputStream
     */
    void saveSeckill(Seckill seckill, InputStream inputStream);

    /**
     * 显示全部抢购商品
     * @return
     */
    List<Seckill> findAll();

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Seckill findById(Integer id);


}
