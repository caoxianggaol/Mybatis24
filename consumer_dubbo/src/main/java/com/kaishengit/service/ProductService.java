package com.kaishengit.service;

import java.util.List;

/**
 * Created by xiaogao on 2017/12/9.
 */
public interface ProductService {


     List<String> findAllProductNames();
     void save(String name);
}
