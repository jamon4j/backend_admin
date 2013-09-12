package com.ksyun.vm.controller;

import com.ksyun.vm.utils.Constants;
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
    public ModelAndView adminHome(ModelAndView mav) throws IOException {
        mav.addObject("type",Constants.getPropertyValue("sys.type"));
        mav.setViewName("/gestion/home");
        return mav;
    }
}
