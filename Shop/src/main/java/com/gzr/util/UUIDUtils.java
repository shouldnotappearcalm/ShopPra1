package com.gzr.util;

/**
 * Created by GZR on 2017/3/26.
 */
public class UUIDUtils {

    public static String getUUID(){
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }


}
