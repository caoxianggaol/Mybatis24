package com.kaishengit.crm.jobstest;

import org.springframework.stereotype.Component;

/**
 * Spring中的QuarterJob类
 * Created by xiaogao on 2017/11/15.
 */
/*@Component  1.注解放入Spring容器 2.配置文件*/
public class SpringQuartzJob {

    public void doSomething() {
        System.out.println("Spring Job --------------");
    }
}
