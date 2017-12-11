package com.kaishengit;

import com.kaishengit.controller.HomeController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Created by xiaogao on 2017/12/9.
 */
public class App {


    public static void main(String[] args) throws IOException {
       /* ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
        ProductService productService = (ProductService) context.getBean("rpcProductService");

        List<String> productNames = productService.findByProductNames();


        for(String name : productNames) {
            System.out.println(name);
        }

        productService.save("hello,Dubbo");*/


        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        HomeController homeController = (HomeController) context.getBean("homeController");
        homeController.sayHello();

        System.in.read();

     }
}
