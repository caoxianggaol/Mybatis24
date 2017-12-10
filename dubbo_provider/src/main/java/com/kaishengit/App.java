package com.kaishengit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by xiaogao on 2017/12/9.
 */
public class App {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-provider-dubbo.xml");
        context.start();

        System.out.println("Provider start........................");

        System.in.read();

    }
}
