package com.gzr.dao.cache;

import com.gzr.exception.MsgSendFailException;
import com.gzr.util.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;


/**
 * Created by GZR on 2017/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private Log log= LogFactory.getLog(RedisDaoTest.class);
    @Resource
    private RedisDao redisDao;

    @Test
    public void test(){
        Jedis jedis=redisDao.getJedis();
        //jedis.set("qqq","123");
        log.info(CommonUtils.getToday());
        jedis.set("asd","123","NX","EX",60);
        log.info(jedis.get("ggg"));
        //测试异常
        try{
            throw new MsgSendFailException("未知错误");
        }
        catch (MsgSendFailException e){
            log.info(e.getMessage());
        }
    }

    @Test
    public void test2(){
        Jedis jedis=new Jedis("119.29.109.156",6379);
        jedis.auth("redis");
        //jedis.set("qqq","123");
        log.info(jedis.get("asd"));
    }

}