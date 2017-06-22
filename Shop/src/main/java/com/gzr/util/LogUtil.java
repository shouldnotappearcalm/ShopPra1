package com.gzr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GZR on 2017/1/19.
 */
public class LogUtil {

    private static Logger logger= LoggerFactory.getLogger(LogUtil.class);

    public static boolean isPrintStackTrace = true;

    // 记录test错误日志信息
    public static void testLogError(String errorMessage, Exception ex) {
        if (logger != null) {
            logger.error(errorMessage);
        }

        if (isPrintStackTrace && ex != null && logger != null) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static void printInfoLog(Class<?> cla, String message) {
        LoggerFactory.getLogger(cla).info(message);
    }

}
