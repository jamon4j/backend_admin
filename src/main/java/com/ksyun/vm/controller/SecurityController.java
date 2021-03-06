package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.security.SecurityPojo;
import com.ksyun.vm.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;
/*	@RequestMapping("/g/sglist")
	public ModelAndView returnSecurityGroupList(HttpServletRequest request, ModelAndView mav) throws HttpException, IOException{
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		List<SecurityGroupDto> list = JsonParser.returnSecurityGroupList(tenantId);
		System.out.println(list);
		mav.addObject("sglist", list);
		mav.setViewName("/gestion/securitygroup/sg_list");
		return mav;
	}*/

    // 查找指定用户的安全组
    @RequestMapping(value = "/g/user/security_groups/ajax/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String ajax(@PathVariable("tenantid") String tenantId,@PathVariable("userid") String userId,@PathVariable("region") String region){
        List<SecurityPojo> list = null;
        try {
            list = securityService.getSecurity(userId,tenantId);
        } catch (ErrorCodeException | NoTokenException e) {
            if(e instanceof ErrorCodeException){
                System.out.println(((ErrorCodeException)e).getResult().getStatus()+"---"+((ErrorCodeException)e).getMessage());
            }
            e.printStackTrace();
        }
        return JSONArray.toJSONString(list);
    }
	
    // 查找指定用户的安全组
    @RequestMapping(value = "/g/user/security_groups/{tenantid}/{userid}/{region}")
    public ModelAndView security_groups(@PathVariable("tenantid") String tenantId,@PathVariable("userid") String userId,@PathVariable("region") String region,ModelAndView mav, HttpServletRequest request){
        List<SecurityPojo> list = null;
        try {
            list = securityService.getSecurity(userId,tenantId,region);
        } catch (ErrorCodeException | NoTokenException e) {
            if(e instanceof ErrorCodeException){
                System.out.println(((ErrorCodeException)e).getResult().getStatus()+"---"+((ErrorCodeException)e).getMessage());
            }
            e.printStackTrace();
        }
        request.setAttribute("tenantid", tenantId);
        request.setAttribute("userid", userId);
        mav.addObject("sglist", list);
        mav.addObject("region", region);
        mav.addObject("regionname",region=="SHRegionOne"?"上海":"北京");
        mav.setViewName("/gestion/user/sg_list");
        return mav;
    }
    // 创建安全组
    @RequestMapping(value = "/g/user/createsg/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String createsg(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,   @PathVariable("region") String region,
                           @RequestParam("name") String name,@RequestParam("desc") String desc, ModelAndView mav) {
        try {
            securityService.addSecurity(userId,tenantId,name,desc);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    // 删除安全组
    @RequestMapping(value = "/g/user/deletesgs/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String deletesgs(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,
                            @PathVariable("region") String region,
                            @RequestParam("sgids") String sgids){
        try {
            securityService.delSecurity(userId,tenantId,sgids,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    // 查找指定用户安全组规则
    @RequestMapping(value = "/g/user/security_groups/rules/{sgid}/{tenantid}/{userid}/{region}")
    public ModelAndView rules(@PathVariable("sgid") String sgid,@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,
                              @PathVariable("region") String region,
                              ModelAndView mav, HttpServletRequest request) {
        SecurityPojo dto = null;
        try {
            dto = securityService.getRules(userId, tenantId, sgid,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("dto", dto);
        request.setAttribute("sgid", sgid);
        request.setAttribute("tenantid", tenantId);
        request.setAttribute("userid", userId);
        mav.addObject("region",region);
//        mav.addObject("regionname",region=="SHRegionOne"?"上海":"北京");
        mav.setViewName("/gestion/user/rules");
        return mav;
    }
    // 创建安全组规则
    @RequestMapping(value = "/g/user/createrule/{sgid}/{tenantid}/{userid}/{region}",method = RequestMethod.POST)
    @ResponseBody
    public String createRule(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@PathVariable("sgid") String sgid,@PathVariable("region") String region,
                             @RequestParam("protocal") String protocal,@RequestParam("from_port") String fromPort,@RequestParam("to_port") String toPort,@RequestParam("cidr") String cidr) {
        try {
            securityService.addRule(userId,tenantId,sgid,protocal,fromPort,toPort,cidr,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
    // 删除安全组规则
    @RequestMapping(value = "/g/user/deleterule/{ruleid}/{tenantid}/{userid}/{region}",method = RequestMethod.POST)
    @ResponseBody
    public String deleterule(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@PathVariable("ruleid") String ruleId, @PathVariable("region") String region) {
        try {
            securityService.delRule(userId,tenantId,ruleId);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
}
