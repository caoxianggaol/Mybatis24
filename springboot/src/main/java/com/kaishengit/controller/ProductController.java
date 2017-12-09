package com.kaishengit.controller;

import com.kaishengit.dao.ProductDao;
import com.kaishengit.entity.Product;
import com.kaishengit.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiaogao on 2017/12/8.
 */
@Controller
public class ProductController {


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductMapper productMapper;


    /*@GetMapping("/save")
    @ResponseBody
    public String save() {
        productDao.save("SpringBoot",18,"baijin");
        return "HI";
    }*/


   /* @GetMapping("/save")
    @ResponseBody
    public String save() {
       productMapper.save("SpringBoot",20,"baijin");
        return "HI";
    }*/

//有mapper.xml配置文件
    @GetMapping("/save")
    @ResponseBody
    public String save() {
        Product product = new Product();
        product.setName("小小");
        product.setAge(15);
        product.setAddress("天水");
        productMapper.save(product);
        return "HI";
    }
}
