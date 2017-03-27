package com.gzr.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by GZR on 2017/3/26.
 */
public class JamesSendMailUtil implements Runnable{

    public static final String HOST="119.29.109.156";
    public static final String PROTOCOL="smtp";
    public static final int PORT=25;
    public static final String FROM = "baofei'sdad@losergzr.cn";
    public static final String PWD = "codemonkey";
    public static final String IP = "localhost";
    public static final String SERVER_PORT = "localhost";
    public static final String PROJECT_NAME = "localhost";
    public static final String ACTION_NAME = "user_active.do";
    public String toEmail=null;

    public JamesSendMailUtil(String toEmail) {
        this.toEmail = toEmail;
    }



    /**
     * @return
     */
    private static Session getSession() {
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
            msg.setContent("<h1>欢饮注册成为网站会员!</h1><h3><a href='http://"+IP+":"+SERVER_PORT+"/"+PROJECT_NAME+"/"+ACTION_NAME+"?code="+"===code===="+"'>点击链接激活</a></h3>", "text/html;charset=UTF-8");

            //Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
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
