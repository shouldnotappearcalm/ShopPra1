package com.gzr.util;

import javax.servlet.http.HttpServletRequest;

import com.gzr.common.Constants;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CommonUtils {

    /**
     * 获取请求的ip地址
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        } else {
            ip = request.getHeader("X-Forwarded-For");
            if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(44);
                return index != -1 ? ip.substring(0, index) : ip;
            } else {
                return request.getRemoteAddr();
            }
        }
    }

    public static String getToday(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public boolean setMsgCode(HttpServletRequest request,String code){
        request.getSession().setAttribute(Constants.MSG_CODE,code);
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    request.getSession().removeAttribute(Constants.MSG_CODE);
                    timer.cancel();
                }
            },Constants.MSG_INTERVAL*1000);
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
        return true;
    }

}
