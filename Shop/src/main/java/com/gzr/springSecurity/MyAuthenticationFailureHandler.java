package com.gzr.springSecurity;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by GZR on 2017/1/20.
 */
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 其中依赖了myAuthenticationEntryPoint，就是要根据登录路径，返回到原登录页面，
     * 并携带错误信息，这里就需要管理登录的form action设置不同
     */
    private MyAuthenticationEntryPoint loginEntry;

    public MyAuthenticationEntryPoint getLoginEntry() {
        return loginEntry;
    }

    public void setLoginEntry(MyAuthenticationEntryPoint loginEntry) {
        this.loginEntry = loginEntry;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String authenfailUrl=this.loginEntry.determineUrlToUseForThisRequest(request,response,exception);
        authenfailUrl=authenfailUrl+"?error";
        super.setDefaultFailureUrl(authenfailUrl);
        super.onAuthenticationFailure(request, response, exception);
    }
}
