package com.gzr.controller.consumer;

import com.gzr.entity.Consumer;
import com.gzr.service.consumer.ConsumerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by GZR on 2017/3/8.
 */
@Controller
@RequestMapping(value = "/consumer")
public class ConsumerController {
    private Log log= LogFactory.getLog(this.getClass());
    @Resource
    private ConsumerService consumerService;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String registerPage(){
        return "consumer/register";
    }

    @RequestMapping(value = "{username}/findByName",method = RequestMethod.GET)
    @ResponseBody
    public String findConsumerByName(@PathVariable String username){
        Consumer consumer=consumerService.findByName(username);
        log.error(consumer);
        if(consumer==null){
            return "<font color='green'>用户名可用</font>";
        }else {
            return "<font color='red'>用户名已存在</font>";
        }
    }

}
