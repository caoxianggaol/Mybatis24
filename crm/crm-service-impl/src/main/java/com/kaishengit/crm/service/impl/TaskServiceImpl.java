package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 待办事项业务曾
 * Created by xiaogao on 2017/11/14.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);


    @Autowired
    private TaskMapper taskMapper;


    /**
     * 代办列表分页
     *
     * @param account
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<Task> pageForAccountTask(Account account, Integer pageNo) {

        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAccountIdEqualTo(account.getId());
        PageHelper.startPage(pageNo,10);
        List<Task> taskList = taskMapper.selectByExample(taskExample);

        return new PageInfo<>(taskList);
    }

    /**
     * 保存新增待办事项
     *
     * @param task
     */
    @Override
    public void saveNewTask(Task task) {
        task.setDone("0");//未完成
        task.setCreateTime(new Date());

        taskMapper.insert(task);
        logger.info("创建新的待办事项 {}",task.getTitle());
    }

    /**
     * 根据id查找对应的待办事项
     *
     * @param id
     * @return
     */
    @Override
    public Task findTaskById(Integer id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据Id删除对应的待办事项
     *
     * @param id
     */
    @Override
    public void deleteTaskById(Integer id) {
        taskMapper.deleteByPrimaryKey(id);
    }
}
