package com.gzr.exception;

/**
 * Created by GZR on 2017/4/29.
 */
public class RepeatSecondMsgException extends MsgSendFailException{

    public RepeatSecondMsgException(String message) {
        super(message);
    }

    public RepeatSecondMsgException(String message, Throwable cause) {
        super(message, cause);
    }
}
