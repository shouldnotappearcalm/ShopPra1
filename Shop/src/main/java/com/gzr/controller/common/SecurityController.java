package com.gzr.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by GZR on 2017/1/21.
 */
@Controller
public class SecurityController {

    @RequestMapping("/security/deny")
    public String deny(HttpServletRequest request, HttpServletResponse response){
        return "security_deny";
    }

}
