package com.kaishengit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiaogao on 2017/12/7.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    private String hello() {
        return "Hello,Springboot";
    }
}
