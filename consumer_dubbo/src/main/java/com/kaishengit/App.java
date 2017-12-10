package com.kaishengit;

import com.kaishengit.service.ProductService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by xiaogao on 2017/12/9.
 */
public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
        ProductService productService = (ProductService) context.getBean("rpcProductService");
        List<String> productNames = productService.findByProductNames();


        for(String name : productNames) {
            System.out.println(name);
        }

     }
}
