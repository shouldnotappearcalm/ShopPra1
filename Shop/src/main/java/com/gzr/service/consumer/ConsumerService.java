package com.gzr.service.consumer;

import com.gzr.entity.Consumer;

/**
 * Created by GZR on 2017/3/9.
 */
public interface ConsumerService {

    public Consumer findByName(String username);

    public int registConusmerByEmail(Consumer consumer);

}
