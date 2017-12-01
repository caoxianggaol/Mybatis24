package com.kaishengit.service;

import com.kaishengit.dao.ProductDao;
import com.kaishengit.pojo.Product;
import com.kaishengit.util.Page;
import com.kaishengit.util.RequestQuery;
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
       return productDao.findByPage(0,100);
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void deleteById(Integer id) {
        productDao.deleteById(id);
    }

    public Product findById(Integer id) {
        return productDao.findById(id);
    }


    public List<Product> findByRequestQuery(List<RequestQuery> requestQueryList) {
        return productDao.findByRequestQueryList(requestQueryList);
    }

    public Page<Product> findByRequestQuery(List<RequestQuery> requestQueryList, Integer pageNo) {
        return productDao.findByRequestQueryAndPage(requestQueryList,pageNo);
    }

   /* public List<Product> findByProductName(String productName) {
        if(productName != null && !"".equals(productName)) {
            return productDao.findByProducName(productName);
        } else {
            return findAll();
        }
    }*/
}
