package com.gzr.service.consumer.impl;

import com.gzr.dao.consumer.ConsumerDao;
import com.gzr.entity.Consumer;
import com.gzr.service.consumer.ConsumerService;
import com.gzr.util.JamesSendMailUtil;
import com.gzr.util.UUIDUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    public Consumer findByName(String username) {
        return consumerDao.findByName(username);
    }

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
            taskExecutor.execute(new JamesSendMailUtil(consumer.getEmail()));
        }
        return 0;
    }
}
