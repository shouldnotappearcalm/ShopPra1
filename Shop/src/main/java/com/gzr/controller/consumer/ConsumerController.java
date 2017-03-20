package com.gzr.controller.consumer;

import com.gzr.common.Constants;
import com.gzr.entity.Consumer;
import com.gzr.service.consumer.ConsumerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GZR on 2017/3/8.
 */
@Controller
@RequestMapping(value = "/consumer")
public class ConsumerController {
    private Log log= LogFactory.getLog(this.getClass());
    @Resource
    private ConsumerService consumerService;

    @RequestMapping(value = "/{way}/register",method = RequestMethod.GET)
    public String registerPage(@PathVariable String way){
        return "phone".equals(way.trim())?"consumer/registerByPhone":"consumer/registerByEmail";
    }

    @RequestMapping(value = "{way}/register",method = RequestMethod.POST)
    public String register(@Valid Consumer consumer, BindingResult result, @PathVariable String way, Model model, HttpServletRequest request){
        log.error(consumer.toString());

        //处理错误 way 不合理的情况
        if(!"phone".equals(way)&&!"email".equals(way)){
            log.error("no way");
            model.addAttribute("consumer",consumer);
            return "consumer/registerByPhone";
        }
        //用来存所有的错误信息
        HashMap<String,String> errorMap;
        if(result.hasErrors()){
            errorMap=new HashMap<String, String>();
            //从Hibernate-Validator中取出所有的错误信息
            List<FieldError> errors=result.getFieldErrors();
            /**
             * 这里邮箱注册和手机号注册都调用这个方法，way 1.email 2.phone
             * 邮箱注册时手机号码总为null
             * 手机号注册邮箱总为null
             *
             * 所以分开判断
             * dealError为处理和判断是否错误的方法
             * 有错误返回false
             */
            boolean isCorrect=dealError(errors,errorMap,way);
            //如果字段校验没错
            isCorrect=judgeImgCode(request.getSession(),request.getParameter("checkcode"),errorMap);
            log.info("isCorrect:"+isCorrect);
            if(!isCorrect){//验证有错误的情况
                log.info(errorMap);
                model.addAttribute("consumer",consumer);
                model.addAttribute("errorMap",errorMap);
                return "phone".equals(way.trim())?"consumer/registerByPhone":"consumer/registerByEmail";
            }
            //正确的逻辑开始


            return "consumer/registerByPhone";
        }else{//验证没有错误
            return "redirect:/index.jsp";
        }
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

    /**
     *
     * @param errors   所有的错误信息
     * @param errorMap  存储所有的错误到map
     * @param way  phone or email
     * @return
     */
    private boolean dealError(List<FieldError> errors, Map<String,String> errorMap,String way){
        boolean isCorrect=true;
        String judgeString="phone".equals(way)?"email":"phone";
        for(FieldError error:errors){
            /*log.info("ObjectName:" + error.getObjectName() + "\tFieldName:" + error.getField()
                    + "\tFieldValue:" + error.getRejectedValue() + "\tMessage:" + error.getDefaultMessage());*/
            //如果是phone 注册，里面存在非email的其他错误
            if(!error.getField().trim().equals(judgeString)){
                isCorrect=false;
                errorMap.put(error.getField().trim(),error.getDefaultMessage());
            }
        }
        return isCorrect;
    }

    /**
     * 判断session中存的验证码和当前页面提交过来的验证码是否一致
     * @param session
     * @param code
     * @return
     */
    public boolean judgeImgCode(HttpSession session,String code,Map<String,String> errorMap){
        if(code.equalsIgnoreCase(session.getAttribute(Constants.CONSUMER_CHECK_CODE).toString().trim())){
            return true;
        }else {
            errorMap.put("checkcode","验证码错误");
            return false;
        }
    }

}
