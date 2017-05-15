package com.gzr.dao.consumer;

import com.gzr.entity.Consumer;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by GZR on 2017/3/9.
 */
public interface ConsumerDao {

    public Consumer findByName(@Param("username")String username);

    public int registConusmerByEmail(@Param("consumer")Consumer consumer);

    public int activateConsumerByEmail(@Param("code")String code);

    public int addSendMsgRecord(@Param("mobile")String mobile,@Param("ip")String ip,@Param("type")String type, @Param("sendTime")Date sendTime);

    public int getTodayTimesByPhone(@Param("mobile") String mobile,@Param("today")String today);

    public int registConusmerByPhone(@Param("consumer")Consumer consumer);
}
