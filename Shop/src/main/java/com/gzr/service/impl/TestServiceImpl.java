package com.gzr.service.impl;

import com.gzr.dao.TestUserDao;
import com.gzr.entity.TestUser;
import com.gzr.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by GZR on 2017/3/5.
 */
@Service("testService")
public class TestServiceImpl implements TestService{

    @Resource
    private TestUserDao testUserDao;

    public List<TestUser> getAll() {
        return testUserDao.getAll();
    }
}
