package com.kaishengit.controller;

import com.kaishengit.entity.Student;
import com.kaishengit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xiaogao on 2017/11/3.
 */
/*声明控制器*/
@Controller
public class StudentController {

    @Autowired
    public StudentService studentService;

    @GetMapping("/student")
    @ResponseBody
    public List<Student> list() {
        List<Student> list = studentService.findAll();
        return list;
    }

    @GetMapping("/students")
    @ResponseBody

    public List<Student> page(@RequestParam(required = false, defaultValue = "1", name = "p") Integer pageNo) {

        return studentService.findByPageNo(pageNo);
    }
}
