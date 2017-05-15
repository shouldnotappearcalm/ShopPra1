package com.gzr.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by GZR on 2017/3/26.
 */
public class JamesSendMailUtil implements Runnable{

    private String HOST="";
    private String PROTOCOL="";
    private int PORT;
    private String FROM = "";
    private String PWD = "";
    private String IP = "";
    private String SERVER_PORT = "";
    private String PROJECT_NAME = "";
    private String ACTION_NAME = "";
    private String toEmail="";
    private String code="";

    public JamesSendMailUtil(String toEmail) {
        this.toEmail = toEmail;
    }

    public JamesSendMailUtil(String toEmail, String code) {
        this.toEmail = toEmail;
        this.code = code;
    }

    private void parameterPrepare(){
        Properties prop=new Properties();
        InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("secret.properties");
        try {
            prop.load(inputStream);
            HOST=prop.getProperty("email.host");
            PROTOCOL=prop.getProperty("email.protocol");
            PORT=Integer.parseInt(prop.getProperty("email.port"));
            FROM=prop.getProperty("email.form");
            PWD=prop.getProperty("email.password");
            IP=prop.getProperty("email.ip");
            SERVER_PORT=prop.getProperty("email.server_port");
            PROJECT_NAME=prop.getProperty("email.project_name");
            ACTION_NAME=prop.getProperty("email.action_name");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * @return
     */
    private  Session getSession() {
        parameterPrepare();
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.store.protocol" , PROTOCOL);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth" , true);

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }

        };
        Session session = Session.getDefaultInstance(props , authenticator);

        return session;
    }

    //toEmail
    public void send() {
        Session session = getSession();
        try {
            // Instantiate a message
            Message msg = new MimeMessage(session);

            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("商城激活邮件");
            msg.setSentDate(new Date());
            msg.setContent("<h1>欢饮注册成为gzr商城网站!</h1><h3><a target='_blank' href='http://"+IP+":"+SERVER_PORT+"/"+PROJECT_NAME+"/"+ACTION_NAME+"?code="+code+"'>点击此链接激活</a></h3>", "text/html;charset=UTF-8");

            //Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    @Override
    public void run() {
        send();
    }

    /*public static void main(String[] args) {
        ///�ʼ�������
        StringBuffer sb=new StringBuffer("欢饮加入高至荣个人商城，点击下方链接激活</br>");
        sb.append("<a href=\"http://localhost:8080/springmvc/user/register?action=activate&email=");
        sb.append(email);
        sb.append("&validateCode=");
        sb.append("111");
        sb.append("\">http://localhost:8080/springmvc/user/register?action=activate&email=");
        sb.append(email);
        sb.append("&validateCode=");
        sb.append("1111");
        sb.append("</a>");

        JamesSendMailUtil.send(email, sb.toString());
    }*/
}
