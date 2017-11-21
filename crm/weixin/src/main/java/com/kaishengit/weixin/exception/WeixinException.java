package com.kaishengit.weixin.exception;

/**
 * 微信异常
 * Created by xiaogao on 2017/11/21.
 */
public class WeixinException extends RuntimeException {


    public WeixinException() {}

    public WeixinException(String message) {
        super(message);
    }

}
