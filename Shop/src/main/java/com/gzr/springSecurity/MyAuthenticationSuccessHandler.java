package com.gzr.springSecurity;


import com.gzr.entity.Consumer;
import com.gzr.service.consumer.ConsumerService;
import com.gzr.util.LogUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 登录授权成功后操作控制，如果是直接点击登录的情况下，根据授权权限跳转不同页面； 否则跳转到原请求页面
 * Created by GZR on 2017/1/20.
 */
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Map<String,String> authDispatcherMap;
    private RequestCache requestCache=new HttpSessionRequestCache();

    @Resource
    private ConsumerService consumerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Collection<? extends GrantedAuthority> authCollection=authentication.getAuthorities();
        if(authCollection.isEmpty()){
            return;
        }
        // 认证成功后，获取用户信息并添加到session中
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        Consumer consumer=consumerService.findByName(userDetails.getUsername());
        request.getSession().setAttribute("user",consumer);

        String url=null;
        // 从别的请求页面跳转过来的情况，savedRequest不为空
        /**
         * 其中用从requestCache中获取到的savedRequest来判断是否是直接点击登录还是从其他页面跳转过来的，
         * 上一篇也说过从savedRequest获取实质上是从session中获取，因此这里的requestCache实例可以为任一实例。
         * 这里就实现了如果是直接点击登录的成功后，用户登录的跳转到首页"/"，
         * 管理员登录的就跳转到管理中心“/manager”；如果是从其他请求页过来的还是返回原请求页面。
         */
        SavedRequest savedRequest=requestCache.getRequest(request,response);
        if(savedRequest!=null){
            url=savedRequest.getRedirectUrl();
        }
        // 直接点击登录页面，根据登录用户的权限跳转到不同的页面
        if(url==null){
            for(GrantedAuthority authority:authCollection){
                url=authDispatcherMap.get(authority.getAuthority());
            }
            LogUtil.printInfoLog(this.getClass(),request+","+response+","+url);
            getRedirectStrategy().sendRedirect(request,response,url);
        }
        //父类方法中也判断了是否是从非登录页面跳转过来的
        super.onAuthenticationSuccess(request,response,authentication);
    }

    public Map<String, String> getAuthDispatcherMap() {
        return authDispatcherMap;
    }

    public void setAuthDispatcherMap(Map<String, String> authDispatcherMap) {
        this.authDispatcherMap = authDispatcherMap;
    }

    public RequestCache getRequestCache() {
        return requestCache;
    }

    @Override
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
