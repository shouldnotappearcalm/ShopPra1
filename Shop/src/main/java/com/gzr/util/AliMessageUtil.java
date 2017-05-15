package com.gzr.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by GZR on 2017/3/27.
 */
public class AliMessageUtil implements Runnable{
    private Log log= LogFactory.getLog(AliMessageUtil.class);
    private String url="";
    private String appKey="";
    private String appSecret="";
    private String name;
    private String code;
    private String phone;

    public AliMessageUtil(String name, String code,String phone) {
        this.name = name;
        this.code = code;
        this.phone=phone;
    }

    private void parametersPrepare(){
        Properties prop=new Properties();
        InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("secret.properties");
        try{
            prop.load(inputStream);
            url=prop.getProperty("phone.url");
            appKey=prop.getProperty("phone.appKey");
            appSecret=prop.getProperty("phone.appSecret");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(){
        TaobaoClient client = new DefaultTaobaoClient(url, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("gzr的商城");
        req.setSmsParamString("{name:'"+name+"',code:'"+code+"'}");
        req.setRecNum(phone);
        req.setSmsTemplateCode("SMS_58380123");
        AlibabaAliqinFcSmsNumSendResponse rsp=null;
        try {
            rsp = client.execute(req);
            log.info(rsp.getBody());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        parametersPrepare();
        send();
    }
}
