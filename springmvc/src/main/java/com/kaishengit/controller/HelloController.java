package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by xiaogao on 2017/11/2.
 */
@Controller
public class HelloController {
    /*@RequestMapping(value = "/hello",method = {RequestMethod.GET,RequestMethod.POST})*/
  /*  @RequestMapping(value = "/hello",method = RequestMethod.GET)*/
    @GetMapping("/hello")
    public String sayHello(){
        System.out.println("hello,SpringMvc");
         return "hello";
    }
}
