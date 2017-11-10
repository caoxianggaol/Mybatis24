package com.kaishengit.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaogao on 2017/11/9.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping
    public String my() {
        return "customer/my";
    }

}
