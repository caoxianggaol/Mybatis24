package com.kaishengit.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 销售层控制器
 * Created by xiaogao on 2017/11/13.
 */
@Controller
@RequestMapping("/sales")
public class SalesController {

    @GetMapping("/smy")
    public String smy() {
        return "/sales/smy";
    }


}
