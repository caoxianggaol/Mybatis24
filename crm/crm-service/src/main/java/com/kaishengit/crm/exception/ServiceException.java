package com.kaishengit.crm.exception;

/**
 * 业务异常类
 * Created by xiaogao on 2017/11/9.
 */
public class ServiceException extends RuntimeException{

    public ServiceException(Exception ex, String s) {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable th) {
        super(th);
    }

    public ServiceException(String message, Throwable th) {
        super(message,th);
    }


}
