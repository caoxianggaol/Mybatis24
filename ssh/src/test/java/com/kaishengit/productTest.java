package com.kaishengit;

import com.kaishengit.pojo.Product;
import com.kaishengit.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by xiaogao on 2017/11/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-hibernate.xml")
public class productTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findAll() {
        List<Product> productList = productService.findAll();
        Assert.assertEquals(productList.size(),100);
    }
}
