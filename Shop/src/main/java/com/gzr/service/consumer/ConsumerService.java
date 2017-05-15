package com.gzr.service.consumer;

import com.gzr.entity.Consumer;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by GZR on 2017/3/9.
 */
public interface ConsumerService {

    public Consumer findByName(String username);

    public int registConusmerByEmail(Consumer consumer);

    public int registConusmerByPhone(Consumer consumer);

    public int activateConsumerByEmail(@Param("code")String code);

    public String sendPhoneMessage(String phone, String ip, HttpServletRequest request);

    public String getPhoneCodeByNum(String phone);

}
