package com.kaishengit.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 待办事项业务层
 * Created by xiaogao on 2017/11/14.
 */
@Controller
@RequestMapping("/task")
public class TaskController {


    @GetMapping("/list")
    public String taskMy() {

        return "task/list";

    }

    @GetMapping("/new")
    public String taskNew() {

        return "task/new";
    }

}
