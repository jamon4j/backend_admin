package com.ksyun.vm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 管理页面入口
 */
@Controller
public class GestionController {
	
    @RequestMapping("/g/")
    public String adminIndex(ModelAndView mav) throws IOException {
        return "/gestion/index";
    }

    @RequestMapping("/g/home")
    public String adminHome(ModelAndView mav) throws IOException {
        return "/gestion/home";
    }
}
