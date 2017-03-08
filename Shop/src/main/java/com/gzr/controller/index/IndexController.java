package com.gzr.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by GZR on 2017/3/5.
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/index","/"})
    public String index(){
        return "redirect:/index.jsp";
    }

}
