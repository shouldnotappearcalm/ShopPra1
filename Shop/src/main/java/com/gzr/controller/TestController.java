package com.gzr.controller;

import com.gzr.entity.TestUser;
import com.gzr.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by GZR on 2017/3/1.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping(value = "/springmvc")
    public void testForSpringMvc(HttpServletResponse response) throws IOException {
        PrintWriter writer=response.getWriter();
        writer.println("test is succeed");
    }

    @RequestMapping(value = "/getAll")
    public void testDruid(HttpServletResponse response) throws IOException {
        List<TestUser> list=testService.getAll();
        PrintWriter writer=response.getWriter();
        writer.println(list);
    }

}
