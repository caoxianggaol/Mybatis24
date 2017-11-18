package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundEXception;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 待办事项业务层
 * Created by xiaogao on 2017/11/14.
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController{

    @Autowired
    private TaskService taskService;


    /**
     *  显示待办事项列表
     * @param pageNo
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping
    public String taskList(@RequestParam(required = false, defaultValue = "1", name = "p") Integer pageNo,
                         Model model, HttpSession httpSession) {
        Account account = getCurrentAccount(httpSession);

        PageInfo<Task> pageInfo = taskService.pageForAccountTask(account,pageNo);

        model.addAttribute("page",pageInfo);
        return "task/list";

    }

    /**
     * ===================================================
     * 新增待办事项
     * @return
     */
    @GetMapping("/new")
    public String newTask() {
        return "task/new";
    }

    /**
     * 新增表单提交保存
     * @param task
     * @return
     */
    @PostMapping("/new")
    public String newTask(Task task) {
        taskService.saveNewTask(task);

        return "redirect:/task";
    }
/*====================================================================*/


    /**
     * 删除代办事项
     * @param id
     * @param httpSession
     * @return
     */
    @GetMapping("/{id:\\d+}/del")
    public String deleteTask(@PathVariable Integer id, HttpSession httpSession) {
        Account account = getCurrentAccount(httpSession);

        Task task = taskService.findTaskById(id);
        if(task == null) {
            throw new NotFoundEXception();
        }
        if(!task.getAccountId().equals(account.getId())) {
           throw new ForbiddenException();
        }

        taskService.deleteTaskById(id);
        return "redirect:/task";
    }





}
