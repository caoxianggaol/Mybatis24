package com.kaishengit.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by xiaogao on 2017/10/31.
 */
public class AopAdvice {

    public void beforeAdvice() {
        System.out.println("前置通知");
    }

    public void afterReturning(Object result){
        System.out.println("后置通知" + result);
    }

    public void afterThrowing (Exception ex){
        System.out.println("异常通知");
    }

    public void after(){
        System.out.println("最终通知");
    }

    /*环绕通知*/
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {

        Object result = null;
        try {
            System.out.println("-------");
            result = joinPoint.proceed();//目标对象方法执行
            System.out.println("后置");
        } catch (Throwable throwable) {
            System.out.println("异常");
        } finally {
            System.out.println("最终");
        }
        return result;
    }
}
