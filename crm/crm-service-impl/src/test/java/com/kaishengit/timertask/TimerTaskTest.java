package com.kaishengit.timertask;


import com.kaishengit.crm.jobstest.MyTimerTask;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

/**
 * 简单的任务调度
 * Created by xiaogao on 2017/11/15.
 */
public class TimerTaskTest {

    @Test
    public void timeTest() throws IOException {
         /* 来自 java.util.Timer; schedule（调度）
         * TimerTask接口的实现类 new MyTimerTask()
         * delay(延迟) period（间隔2s时间执行一次）*/
        Timer timer = new Timer();
        //当前任务延迟0秒，每隔2000毫秒做一次
        //timer.schedule(new MyTimerTask(),0,2000);

        //当前任务延迟2秒做，并且只做一次 不重复
        //timer.schedule(new MyTimerTask(),2000);

        //在指定的时间去执行，不重复 new Date()当前时间
        //timer.schedule(new MyTimerTask(),new Date());

        //从指定时间开始，每隔2000秒执行一次
        timer.schedule(new MyTimerTask(),new Date(),200);


       // System.in.read();//不退出运行
    }

    /*===================================================================*/
    /*定时任务调度 框架Quartz*/

}
