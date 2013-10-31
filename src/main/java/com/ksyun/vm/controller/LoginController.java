package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.interceptor.HandleAuthenticationInterceptor;
import com.ksyun.vm.pojo.login.Msg;
import com.ksyun.vm.pojo.login.User;
import com.ksyun.vm.service.LoginService;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-30
 * Time: 下午4:27
 * Func:
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request,ModelAndView mav){
        mav.addObject("type", Constants.getPropertyValue("sys.type"));
        mav.setViewName("/gestion/login");
        return mav;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request,HttpServletResponse response) {
        String username = ServletUtil.getString(request,"username");
        String password = ServletUtil.getString(request,"password");
        List<User> list = loginService.getUser(username, password);
        Msg msg = new Msg();
        if (list != null&&!list.isEmpty()){
            msg.setMsg("登陆成功");
            msg.setSuccess("true");
            msg.setUrl("/g/");
            String cookie = SHA1.getDigestOfString((username+"{"+password+"}").getBytes());
            HandleAuthenticationInterceptor.map.put(cookie, Calendar.getInstance());
            msg.setCookie(cookie);
            return JSONObject.toJSONString(msg);
        }else{
            msg.setMsg("登陆失败");
            msg.setSuccess("false");
            msg.setUrl("/login");
            return JSONObject.toJSONString(msg);
        }
    }
}
