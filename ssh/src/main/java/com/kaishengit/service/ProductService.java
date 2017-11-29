package com.kaishengit.service;

import com.kaishengit.dao.ProductDao;
import com.kaishengit.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xiaogao on 2017/11/29.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ProductService {

    @Autowired
    private ProductDao productDao;


    public List<Product> findAll() {
       return productDao.findAll();
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void deleteById(Integer id) {
        productDao.delete(id);
    }
}
