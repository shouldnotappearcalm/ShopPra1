package com.gzr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by GZR on 2017/3/1.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/springmvc")
    public void testForSpringMvc(HttpServletResponse response) throws IOException {
        PrintWriter writer=response.getWriter();
        writer.println("test is succeed");
    }

}
