package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.ebs.EBSPojo;
import com.ksyun.vm.pojo.snapshot.SnapshotEBSPojo;
import com.ksyun.vm.pojo.snapshot.SnapshotVmPojo;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.pojo.vm.ImagePojo;
import com.ksyun.vm.service.EBSService;
import com.ksyun.vm.service.SnapshotService;
import com.ksyun.vm.service.VmService;
import com.ksyun.vm.utils.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EBSService ebsService;
    @Autowired
    private SnapshotService snapshotService;
    @Autowired
    private VmService vmService;

    // 用户列表
    @RequestMapping(value = "/g/user/list/{pagenum}")
    public ModelAndView findAll(@PathVariable("pagenum") String pageNum, ModelAndView mav) throws NoTokenException, ErrorCodeException {
        List<UserPojo> userlist = userService.getUsers();
        mav.addObject("list",userlist);
        mav.setViewName("/gestion/user/user_list");
        return mav;
    }

    //搜索用户
    @RequestMapping(value = "/g/user/search")
	public ModelAndView userList(@RequestParam("name") String name, @RequestParam("email") String email,@RequestParam("userid") String user_id, ModelAndView mav) {
    	UserPojo po = null;
        if((name==null&&email==null&&user_id==null)||(StringUtils.equals(name,"")&&StringUtils.equals(email,"")&&StringUtils.equals(user_id,""))){
            mav.addObject("list", new ArrayList());
            mav.setViewName("/gestion/user/user_list");
            return mav;
        }
		try {
			po = userService.searchUser(name, email,user_id);
		} catch (ErrorCodeException e) {
			e.printStackTrace();
		} catch (NoTokenException e) {
			e.printStackTrace();
		}
        List<UserPojo> list = new ArrayList<>();
        list.add(po);
		mav.addObject("list", list);
		mav.setViewName("/gestion/user/user_list");
        return mav;
	}

    // 创建用户(ajax请求)
    @RequestMapping(value = "/g/user/createuser",method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestParam("name") String userName,@RequestParam("email")String email){
        try {
            userService.addUser(userName,email);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
	// ebs及镜像列表
	@RequestMapping(value = "/g/user/ebs_snapshot_list/{tenant_id}/{user_id}/{region}")
	public ModelAndView ebsSnapShotList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId,@PathVariable("region") String region, ModelAndView mav)
			throws HttpException, IOException {
		List<EBSPojo> ebsList = null;
		List<SnapshotVmPojo> imageList = null;
        List<SnapshotEBSPojo> snapshotList = null;
        try {
            ebsList = ebsService.getEBS(userId,tenantId,region);
            snapshotList = snapshotService.getEBSSnapshots(userId, tenantId,region);
            imageList = snapshotService.getVmSnapshots(userId,tenantId,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
		map.put("tenantId", tenantId);
		map.put("userId", userId);
		map.put("ebslist", ebsList);
		map.put("systemImagelist", imageList);
		map.put("snapshotlist", snapshotList);
		mav.addAllObjects(map);
//        mav.addObject("regionname",region=="SHRegionOne"?"上海":"北京");
        mav.addObject("region",region);
		mav.setViewName("/gestion/user/ebs_snapshot_list");
		return mav;
	}

	// 返回可选镜像列表(ajax请求)
	@RequestMapping(value = "/g/user/image_public_id_list/{tenant_id}/{user_id}/{region}")
	@ResponseBody
	public String imageIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @PathVariable("region") String region,ModelAndView mav) throws HttpException,
			IOException {
        List<ImagePojo> list = null;
        try {
            list = vmService.getImages(userId, tenantId,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return JSONArray.toJSONString(list);
    }

	/*// 返回可选虚拟机类型(ajax请求 )
	@RequestMapping(value = "/g/user//{tenant_id}/{user_id}")
	@ResponseBody
	public String flavorIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav) throws HttpException,
			IOException {
		List<FlavorDto> list = JsonParser.returnFlavorIdList(tenantId, userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}*/



}

