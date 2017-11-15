package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;

/**
 * 待办事项业务层
 * Created by xiaogao on 2017/11/14.
 */
public interface TaskService {

    /**
     * 代办列表分页
     * @param account
     * @param pageNo
     * @return
     */
    PageInfo<Task> pageForAccountTask(Account account, Integer pageNo);

    /**
     * 保存新增待办事项
     * @param task
     */
    void saveNewTask(Task task);

    /**
     * 根据id查找对应的待办事项
     * @param id
     * @return
     */
    Task findTaskById(Integer id);

    /**
     * 根据Id删除对应的待办事项
     * @param id
     */
    void deleteTaskById(Integer id);
}


