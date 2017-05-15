package com.gzr.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * Created by GZR on 2017/3/27.
 */
@RunWith(JUnit4.class)
public class AliMessageUtilTest {

    @Test
    public void test(){
        new AliMessageUtil("用户","1234","15520497313").run();
    }

}