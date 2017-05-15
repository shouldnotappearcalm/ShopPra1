package com.gzr.exception;

/**
 * Created by GZR on 2017/4/30.
 */
public class MsgSendFailException extends RuntimeException {

    public MsgSendFailException(String message) {
        super(message);
    }

    public MsgSendFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
