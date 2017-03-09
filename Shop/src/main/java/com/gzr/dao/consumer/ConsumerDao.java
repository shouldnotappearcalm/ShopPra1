package com.gzr.dao.consumer;

import com.gzr.entity.Consumer;
import org.apache.ibatis.annotations.Param;

/**
 * Created by GZR on 2017/3/9.
 */
public interface ConsumerDao {

    public Consumer findByName(@Param("username")String username);

}
