//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gzr.controller.consumer;

import com.gzr.common.Constants;
import com.gzr.entity.Consumer;
import com.gzr.exception.MsgSendFailException;
import com.gzr.exception.RepeatDailyMsgException;
import com.gzr.exception.RepeatSecondMsgException;
import com.gzr.service.consumer.ConsumerService;
import com.gzr.util.CommonUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


/**
 * Created by GZR on 2017/3/8.
 */
@Controller
@RequestMapping({"/consumer"})
public class ConsumerController {
    private Log log= LogFactory.getLog(this.getClass());
    @Resource
    private ConsumerService consumerService;
    @Resource
    private BCryptPasswordEncoder bcryptEncoder;

    @RequestMapping(value = "/{way}/register",method = RequestMethod.GET)
    public String registerPage(@PathVariable String way){
        return "phone".equals(way.trim())?"consumer/registerByPhone":"consumer/registerByEmail";
    }

    @RequestMapping(value = "/testPass")
    public void test(String password){
        log.error("pass:"+bcryptEncoder.encode(password));
    }

    @RequestMapping(value = "/safe/11")
    public String gotoSafe(){
        return "consumer/safe/safe";
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
            //如果字段校验没错，校验验证码
            boolean isCorrectCode=judgeImgCode(request.getSession(),request.getParameter("checkcode"),errorMap);
            //校验验证码没错再校验用户名是否存在
            log.info("isCorrect:"+isCorrect);
            if(!isCorrect||!isCorrectCode){//验证有错误的情况
                log.info(errorMap);
                model.addAttribute("consumer",consumer);
                model.addAttribute("errorMap",errorMap);
                return "phone".equals(way.trim())?"consumer/registerByPhone":"consumer/registerByEmail";
            }
            //正确的逻辑开始
            consumer.setPassword(bcryptEncoder.encode(consumer.getPassword()));
            if("email".equals(way.trim())) {
                int success=consumerService.registConusmerByEmail(consumer);
                if(success>0){
                    return "redirect:/index.jsp";
                }
                return "consumer/registerByEmail";
            }else{
                //手机注册的逻辑开始
                //先判断手机号验证码是否正确
                String code=consumerService.getPhoneCodeByNum(consumer.getPhone());
                String phoneCode=request.getParameter("phoneCode").trim();
                if(phoneCode==null||phoneCode==""||!phoneCode.equals(code.trim())){
                    errorMap.put("phoneCode","验证码不正确");
                    return "consumer/registerByPhone";
                }
                //正确逻辑开始写入数据
                consumerService.registConusmerByPhone(consumer);
                return "redirect:/index.jsp";
            }
        }else{//验证没有错误
            /**
             * 因为选择了邮箱和手机都提交到这一个方法，不会到这个else里面来，只是if里面进行一个
             * 比较复杂点的判断
             */
            return "redirect:/index.jsp";
        }
    }

    @RequestMapping(value = "/email_activate")
    public void emailActivateConsumer(String code){
        log.error("activatecode:"+code);
        //激活账户，改变数据库状态
        consumerService.activateConsumerByEmail(code);
        //暂定跳到登陆页面
    }

    @RequestMapping(value = "/{username}/findByName",method = RequestMethod.GET)
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
     * @param phoneNumber  发送的手机号
     * @param phoneCheckCode 当前填写的验证码
     * @param session
     * @return
     */
    @RequestMapping(value = "/sendPhoneMessage",method = RequestMethod.GET)
    @ResponseBody
    public String sendMessage(@RequestParam(value = "phoneNumber") String phoneNumber,@RequestParam(value="phoneCheckCode")String phoneCheckCode,HttpSession session,HttpServletRequest request){
        log.info("success goto sendMessage");
        if(session.getAttribute(Constants.PHONE_CHECK_CODE).toString().trim().equalsIgnoreCase(phoneCheckCode.trim())){
            //验证码正确，发送短信
            try {
                consumerService.sendPhoneMessage(phoneNumber, CommonUtils.getIpAddr(request),request);
            } catch (RepeatSecondMsgException e) {
                return e.getMessage();
            }catch (RepeatDailyMsgException e) {
                return e.getMessage();
            }catch (MsgSendFailException e) {
                return e.getMessage();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return "success";
        }
        return "验证码不正确";
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

    public boolean judgeImgCode(HttpSession session,String code,Map<String,String> errorMap){
        if(session.getAttribute("img_code").toString().trim().equalsIgnoreCase(code.trim())) {
            return true;
        }else {
            errorMap.put("checkcode","验证码错误");
            return false;
        }
    }
}
