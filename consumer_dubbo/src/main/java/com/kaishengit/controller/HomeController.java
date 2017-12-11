package com.kaishengit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kaishengit.service.ProductService;
import org.springframework.stereotype.Controller;


import java.util.List;

/**
 * Created by xiaogao on 2017/12/11.
 */
@Controller
public class HomeController {

    @Reference(version = "1.2")
    private ProductService productService;


    public void sayHello() {
        List<String> stringList = productService.findAllProductNames();

        for(String str : stringList) {
            System.out.println(str);
        }

    }

}
