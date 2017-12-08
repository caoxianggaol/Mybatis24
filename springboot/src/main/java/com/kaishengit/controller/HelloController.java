package com.kaishengit.controller;

import com.kaishengit.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiaogao on 2017/12/7.
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    private String hello(Model model, HttpSession session) {

        model.addAttribute("message","SpingBoot");
        model.addAttribute("user",new User(110,"tom","天津"));
        session.setAttribute("msg","HELLO------");

        List<String> userNameList = Arrays.asList("aa","bb","cc");

        model.addAttribute("userNameList",userNameList);

        //return "hello";
        return "neirong";
    }
}
