package com.gzr.service.consumer.impl;

import com.gzr.common.Constants;
import com.gzr.dao.cache.RedisDao;
import com.gzr.dao.consumer.ConsumerDao;
import com.gzr.entity.Consumer;
import com.gzr.exception.MsgSendFailException;
import com.gzr.exception.RepeatDailyMsgException;
import com.gzr.exception.RepeatSecondMsgException;
import com.gzr.service.consumer.ConsumerService;
import com.gzr.util.AliMessageUtil;
import com.gzr.util.CommonUtils;
import com.gzr.util.JamesSendMailUtil;
import com.gzr.util.UUIDUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by GZR on 2017/3/9.
 */
@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService {

    private Log log= LogFactory.getLog(this.getClass());

    @Resource
    private ConsumerDao consumerDao;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    RedisDao redisDao;


    @Transactional
    public Consumer findByName(String username) {
        return consumerDao.findByName(username);
    }

    @Transactional
    @Override
    public int registConusmerByEmail(Consumer consumer) {
        //获取UUID并发送注册邮件，保存到consumer对象中
        String code= UUIDUtils.getUUID();
        log.info("code"+code);
        consumer.setCode(code);
        consumer.setStage(0);
        //写入数据库
        int success=consumerDao.registConusmerByEmail(consumer);
        log.error("Success:"+success);
        if(success>0){
            taskExecutor.execute(new JamesSendMailUtil(consumer.getEmail(),consumer.getCode()));
        }
        return success;
    }

    /**
     * 手机注册写入数据库
     * @param consumer
     * @return
     */
    @Transactional
    @Override
    public int registConusmerByPhone(Consumer consumer) {
        int success=consumerDao.registConusmerByPhone(consumer);
        return success;
    }

    @Transactional
    @Override
    public int activateConsumerByEmail(String code) {
        return consumerDao.activateConsumerByEmail(code);
    }

    @Transactional
    @Override
    public String sendPhoneMessage(String phone, String ip, HttpServletRequest request) {
        /**
         * 判断发送ip和发送手机号今日的发送量
         * 存入这60秒是否发送过
         * 每天的 存入redis格式为  2017-04-29+phone  todayTotal 设置过期时间为24小时
         */
        String sendFrequencyKeyPrefix="send_frequency_";
        String dailySendLimitKeyPrefix= CommonUtils.getToday();

        Jedis jedis=redisDao.getJedis();

        //先判断120秒之类有没有发送过短信
        String hasSendInSecond=jedis.get(sendFrequencyKeyPrefix+phone);
        if("success".equals(hasSendInSecond)){
            //发送过
            closeJedis(jedis);
            throw new RepeatSecondMsgException(Constants.MSG_INTERVAL+"秒内重复发送短信给此用户");
        }
        //判断24小时内是否发送已经超过每日最大次数的限制
        String dailyTimes=jedis.get(dailySendLimitKeyPrefix+phone);
        int todayPhoneTimes=dailyTimes==null||"".equals(dailyTimes.trim())?0:Integer.parseInt(dailyTimes.trim());
        if(todayPhoneTimes>=Constants.MSG_DAILY_MAX_TIMES){
            closeJedis(jedis);
            throw new RepeatDailyMsgException("超出每日发送短信的最大限制"+Constants.MSG_DAILY_MAX_TIMES+"次");
        }
        //正确的方法开始
        //写入发送记录表   发送短信
        try{
            int number = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
            taskExecutor.execute(new AliMessageUtil("用户",""+number,phone));
            //验证码记录到session,并且设置Constants.MSG_INTERVAL后删除这个attribute
            //改变验证码的存储方式，仍然 key-phone value-code  方式存储到redis中，有效时间为Constants.MSG_INTERVAL
            jedis.set(phone,number+"","NX","EX",Constants.MSG_INTERVAL);

            //写入秒数限制,设置key的有效时间是120秒
            jedis.set(sendFrequencyKeyPrefix+phone,"success","NX","EX",Constants.MSG_INTERVAL);
            //今天的发送次数+1
            todayPhoneTimes++;
            jedis.set(dailySendLimitKeyPrefix+phone,todayPhoneTimes+"","NX","EX",60*60*24);
        }catch (Exception e){
            throw new MsgSendFailException("未知错误发生，短信发送失败");
        }finally {
            closeJedis(jedis);
        }
        /*
        //暂时放弃之前的发送短信写入数据库的策略
        int success=consumerDao.addSendMsgRecord(phone,ip,"用户注册",new Date());
        log.info("success:"+success);
        if(success>0){
            int number = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
            taskExecutor.execute(new AliMessageUtil("用户",""+number,phone));
        }
        */
        return "success";
    }

    @Override
    public String getPhoneCodeByNum(String phone) {
        Jedis jedis=redisDao.getJedis();
        String number=jedis.get(phone);
        closeJedis(jedis);
        return number;
    }

    private void closeJedis(Jedis jedis){
        jedis.close();
    }


}
