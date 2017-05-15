package com.gzr.dao.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by GZR on 2017/4/27.
 */
public class RedisDao {

    private String password;

    private Log log= LogFactory.getLog(RedisDao.class);
    private JedisPool jedisPool;

    public RedisDao(String ip,int port) {
        jedisPool=new JedisPool(ip,port);
    }

    /*public String getValueByKey(String key){
        Jedis jedis=jedisPool.getResource();
    }*/

    public Jedis getJedis(){
        parameterPrepare();
        Jedis jedis=jedisPool.getResource();
        jedis.auth(password);
        return jedis;
    }


    private void parameterPrepare(){
        Properties prop=new Properties();
        InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties");
        try {
            prop.load(inputStream);
            password=prop.getProperty("redis.password");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}