package com.kaishengit.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kaishengit.service.ProductService;

import java.util.Arrays;
import java.util.List;

/**
 * @com.alibaba.dubbo.config.annotation.Service(timeout = 5000)
 * Created by xiaogao on 2017/12/9.
 */

@Service(timeout = 2000,version = "1.2")
public class ProductServiceImpl implements ProductService {

    public List<String> findAllProductNames() {
        return Arrays.asList("小明","张三","李凯","王大宝");
    }


    public void save(String name) {
        System.out.println("saved - >" + name);
    }
}
