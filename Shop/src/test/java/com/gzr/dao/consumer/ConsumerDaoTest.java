package com.gzr.dao.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by GZR on 2017/4/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class ConsumerDaoTest {
    private Log log= LogFactory.getLog(ConsumerDaoTest.class);
    @Resource
    ConsumerDao consumerDao;
    @Test
    public void getTodayTimesByPhone() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.info(consumerDao.getTodayTimesByPhone("15520497313",sdf.format(new Date())));
    }

}