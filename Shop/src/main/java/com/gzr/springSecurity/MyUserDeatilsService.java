package com.gzr.springSecurity;

import com.gzr.entity.Consumer;
import com.gzr.service.consumer.ConsumerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by GZR on 2017/1/20.
 */
@Service
public class MyUserDeatilsService implements UserDetailsService {

    private Log log= LogFactory.getLog(this.getClass());
    @Resource
    private ConsumerService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        log.info("username如下:"+username);
        Consumer user=userService.findByName(username);
        if(user!=null){
            //设置角色
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
        }
        throw new UsernameNotFoundException("User '" + username
                + "' not found.");
    }
}
