package com.kaishengit.crm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 404异常 @ResponseStatus(响应的状态)
 * Created by xiaogao on 2017/11/12.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundEXception extends RuntimeException{

    public NotFoundEXception() {}

    public NotFoundEXception(String message) {
        super(message);
    }
}
