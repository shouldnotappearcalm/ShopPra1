package com.gzr.exception;

/**
 * Created by GZR on 2017/4/29.
 */
public class RepeatDailyMsgException extends MsgSendFailException{

    public RepeatDailyMsgException(String message) {
        super(message);
    }

    public RepeatDailyMsgException(String message, Throwable cause) {
        super(message, cause);
    }
}
