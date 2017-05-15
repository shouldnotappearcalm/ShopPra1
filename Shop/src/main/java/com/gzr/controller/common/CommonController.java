package com.gzr.controller.common;

import com.gzr.common.Constants;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by GZR on 2017/3/9.
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController {

    @RequestMapping(value = "/{way}/getCode")
    // 产生登录验证码
    public String getCode(HttpSession session, HttpServletRequest request, HttpServletResponse response,@PathVariable String way) throws Exception {
        response.setHeader("Cache-Control", "no-cache");
        int width = 75; // 图片宽度
        int height = 25; // 图片高度
        if("PhoneImgCode".equals(way.trim())){
            width=200;
            height=40;
        }

        // 生成前景和背景颜色，形成反色
        int red = (int) (Math.random() * 1000 % 64);
        int green = (int) (Math.random() * 1000 % 64);
        int blue = (int) (Math.random() * 1000 % 64);
        //Color backColor = new Color(red, green, blue);
        //底色固定为#F1FCFF
        Color backColor = new Color(0xF1, 0xFC, 0xFF);
        Color fontColor = new Color(255 - red, green, blue);

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.setColor(backColor); // 背景颜色
        graphics.fillRect(0, 0, width, height);

        int fontSize=18;
        if(width>=200){
            fontSize=25;
        }

        graphics.setFont(new Font("Sans-Serif", Font.BOLD, fontSize));
        //固定验证码颜色为绿色
        graphics.setColor(Color.GREEN); // 前景颜色
        Random random = new Random();//
        // 图片背景上随机生成5条线条，避免通过图片识别破解验证码
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
        String code = getCodeString();
        //暂时这么写
        if("PhoneImgCode".equals(way.trim())){
            session.setAttribute(Constants.PHONE_CHECK_CODE, code); // 写入session中
        }else{
            session.setAttribute(Constants.IMG_CODE, code); // 写入session中
        }
        graphics.drawString(code, (int) (width * 0.1), (int) (height * 0.8));
        graphics.dispose();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response
                .getOutputStream());
        encoder.encode(image);
        response.getOutputStream().flush(); // 刷新到页面生成图片
        response.getOutputStream().close(); // 关闭writer
        return null;
    }

    // 产生验证图片上面的随机字符
    protected String getCodeString() {
        Random random = new Random();
        String old = "23456789abcdefghijkmnpqrstuvwxy";
        StringBuffer sb = new StringBuffer();
        int j = 0;
        for (int i = 0; i < 5; i++) {
            j = random.nextInt(old.length());
            sb.append(old.substring(j, j + 1));
        }
        return sb.toString();
    }

}
