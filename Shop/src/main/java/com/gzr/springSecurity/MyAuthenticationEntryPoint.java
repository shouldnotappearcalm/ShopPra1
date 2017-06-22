package com.gzr.springSecurity;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by GZR on 2017/1/19.
 */
public class MyAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {


    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public MyAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    private Map<String,String> authEntryPointMap;
    //ANT方式的通配符有三种：

    //?（匹配任何单字符），*（匹配0或者任意数量的字符），**（匹配0或者更多的目录）
    /**
     * http://blog.csdn.net/z69183787/article/details/23173789
     */
    private PathMatcher pathMatcher=new AntPathMatcher();

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String requestURI=request.getRequestURI().replace(request.getContextPath(),"");
        for(String url:this.authEntryPointMap.keySet()){
            //匹配路径 是否/consumer/**   /manager/**
            if(this.pathMatcher.match(url,requestURI)){
                return this.authEntryPointMap.get(url);
            }
        }
        return super.determineUrlToUseForThisRequest(request,response,exception);
    }

    public Map<String, String> getAuthEntryPointMap() {
        return authEntryPointMap;
    }

    public void setAuthEntryPointMap(Map<String, String> authEntryPointMap) {
        this.authEntryPointMap = authEntryPointMap;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }
}
