package com.gzr.service.consumer.impl;

import com.gzr.dao.TestUserDao;
import com.gzr.dao.consumer.ConsumerDao;
import com.gzr.entity.Consumer;
import com.gzr.service.consumer.ConsumerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by GZR on 2017/3/9.
 */
@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService {

    @Resource
    private ConsumerDao consumerDao;

    public Consumer findByName(String username) {
        return consumerDao.findByName(username);
    }
}
