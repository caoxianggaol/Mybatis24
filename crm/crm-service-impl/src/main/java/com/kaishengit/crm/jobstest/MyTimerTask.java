package com.kaishengit.crm.jobstest;

import java.util.TimerTask;

/**
 * Created by xiaogao on 2017/11/15.
 * 来自 java.util.TimerTask;
 */
public class MyTimerTask extends TimerTask{
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println("+++++++++++++++ ");
    }
}
