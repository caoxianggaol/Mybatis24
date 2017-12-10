package com.kaishengit.service.impl;

import com.kaishengit.service.ProductService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiaogao on 2017/12/9.
 */

public class ProductServiceImpl implements ProductService {

    public List<String> findByProductNames() {
        return Arrays.asList("小明","张三","李凯","王大宝");
    }
}
