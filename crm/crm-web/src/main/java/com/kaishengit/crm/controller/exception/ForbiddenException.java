package com.kaishengit.crm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.PublicKey;

/**
 * 403 权限不足 正常为OK
 * Created by xiaogao on 2017/11/12.
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{

    public ForbiddenException() {}

    public ForbiddenException(String message) {
        super(message);
    }

}
