package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.vm.utils.PageWithoutSize;

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
	public ModelAndView userList(@PathVariable("pagenum") String pageNum, ModelAndView mav) {
        List<UserPojo> list = null;
        try {
            list = userService.getUsers();
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        PageWithoutSize page = new PageWithoutSize(Integer.valueOf(pageNum));
		if (list == null) {
			page.setData(null);
		}
		
		page.setData(list);
		mav.addObject("page", page);
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
	@RequestMapping(value = "/g/user/ebs_snapshot_list/{tenant_id}/{user_id}")
	public ModelAndView ebsSnapShotList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav)
			throws HttpException, IOException {
		List<EBSPojo> ebsList = null;
		List<SnapshotVmPojo> imageList = null;
        List<SnapshotEBSPojo> snapshotList = null;
        try {
            ebsList = ebsService.getEBS(userId,tenantId);
            snapshotList = snapshotService.getEBSSnapshots(userId, tenantId);
            imageList = snapshotService.getVmSnapshots(userId,tenantId);
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
		mav.setViewName("/gestion/user/ebs_snapshot_list");
		return mav;
	}

	// 返回可选镜像列表(ajax请求)
	@RequestMapping(value = "/g/user/image_public_id_list/{tenant_id}/{user_id}")
	@ResponseBody
	public String imageIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav) throws HttpException,
			IOException {
        List<ImagePojo> list = null;
        try {
            list = vmService.getImages(userId, tenantId);
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

