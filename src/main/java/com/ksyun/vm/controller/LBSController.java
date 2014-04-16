package com.ksyun.vm.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.lbs.PoolPOJO;
import com.ksyun.vm.pojo.lbs.VipPOJO;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.service.LBSService;
import com.ksyun.vm.utils.UserService;

/**
 * LBS管理
 * 
 * @author XueQi
 * 
 */
@Controller
public class LBSController {
	@Autowired
	private LBSService lbsService;
	@Autowired
	private UserService userService;

	/**
	 * 列出所有用户
	 * 
	 * @param mav
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	@RequestMapping(value = "/g/lbs/user/list")
	public ModelAndView findAllUser(ModelAndView mav)
			throws ErrorCodeException, NoTokenException {
		List<UserPojo> userlList = userService.getUsers();
		mav.addObject("list", userlList);
		mav.setViewName("/gestion/lbs/user_list");
		return mav;
	}

	/**
	 * 查询用户
	 * 
	 * @param name
	 * @param email
	 * @param user_id
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/user/search")
	public ModelAndView userList(@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("userid") String user_id, ModelAndView mav) {
		UserPojo po = null;
		if ((name == null && email == null && user_id == null)
				|| (StringUtils.equals(name, "")
						&& StringUtils.equals(email, "") && StringUtils.equals(
						user_id, ""))) {
			mav.addObject("list", new ArrayList());
			mav.setViewName("/gestion/lbs/user_list");
			return mav;
		}
		try {
			po = userService.searchUser(name, email, user_id);
		} catch (ErrorCodeException e) {
			e.printStackTrace();
		} catch (NoTokenException e) {
			e.printStackTrace();
		}
		List<UserPojo> list = new ArrayList<>();
		list.add(po);
		mav.addObject("list", list);
		mav.setViewName("/gestion/lbs/user_list");
		return mav;
	}

	/**
	 * 获取所有LBS信息
	 * 
	 * @param userId
	 * @param tenantId
	 * @param username
	 * @param poolId
	 * @param mav
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	@RequestMapping(value = "/g/lbs/list/{user_id}/{tenant_id}/{username}")
	public ModelAndView getAllLbsDetails(
			@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("username") String username, ModelAndView mav)
			throws NoTokenException, ErrorCodeException {
		List<PoolPOJO> list = lbsService.getPools(userId, tenantId);
		List<VipPOJO> vip_list = lbsService.getVips(userId, tenantId);
		mav.addObject("list", list);
		mav.addObject("vip_list", vip_list);
		mav.addObject("user_id", userId);
		mav.addObject("tenant_id", tenantId);
		mav.addObject("pool_username", username);
		mav.setViewName("/gestion/lbs/lbs_list");
		return mav;
	}

	/**
	 * 根据pool Id 查询Pool
	 * 
	 * @param userId
	 * @param tenantId
	 * @param username
	 * @param poolId
	 * @param mav
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	@RequestMapping(value = "/g/lbs/pool/{user_id}/{tenant_id}/{username}")
	public ModelAndView getPool(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("username") String username,
			@RequestParam("pool_id") String poolId, ModelAndView mav)
			throws NoTokenException, ErrorCodeException {
		PoolPOJO pojo = lbsService.getPool(userId, tenantId, poolId);
		List<PoolPOJO> list = new ArrayList<>();
		list.add(pojo);
		mav.addObject("list", list);
		mav.addObject("user_id", userId);
		mav.addObject("tenant_id", tenantId);
		mav.addObject("pool_username", username);
		mav.setViewName("/gestion/lbs/lbs_list");
		return mav;
	}

}
