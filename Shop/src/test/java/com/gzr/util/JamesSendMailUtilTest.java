package com.gzr.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by GZR on 2017/3/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-task.xml"})
public class JamesSendMailUtilTest {
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Test
    public void test(){
        //taskExecutor.execute(new JamesSendMailUtil("1191465097@qq.com"));
        new JamesSendMailUtil("15520497313@163.com").run();
        new JamesSendMailUtil("1191465097@qq.com").run();
    }

}