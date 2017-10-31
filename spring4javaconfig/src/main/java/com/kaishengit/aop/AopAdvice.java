package com.kaishengit.aop;



/**
 * Created by xiaogao on 2017/10/30.
 */

public class AopAdvice {

    public void beforeAdvice() {
        System.out.println("前置通知");
    }

    public void afterReturning(Object result){
        System.out.println("后置通知");
    }

    public void afterThrowing(Exception ex){
        System.out.println("异常通知");
    }

    public void after(){
        System.out.println("最终通知");
    }

}
