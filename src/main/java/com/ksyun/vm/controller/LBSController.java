package com.ksyun.vm.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.lbs.HealthMonitorsStatusPOJO;
import com.ksyun.vm.pojo.lbs.HealthPOJO;
import com.ksyun.vm.pojo.lbs.HealthVipPOJO;
import com.ksyun.vm.pojo.lbs.MemberPOJO;
import com.ksyun.vm.pojo.lbs.PoolPOJO;
import com.ksyun.vm.pojo.lbs.SessionPersistencePOJO;
import com.ksyun.vm.pojo.lbs.VipPOJO;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.pojo.vm.VmPojo;
import com.ksyun.vm.service.LBSService;
import com.ksyun.vm.service.VmService;
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
	@Autowired
	private VmService vmService;

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
		List<MemberPOJO> member_list = lbsService.getMembers(userId, tenantId);
		List<HealthPOJO> health_list = lbsService.getHealths(userId, tenantId);
		List<VmPojo> vm_list = vmService.getVms(userId, tenantId);
		mav.addObject("list", list);
		mav.addObject("vip_list", vip_list);
		mav.addObject("member_list", member_list);
		mav.addObject("health_list", health_list);
		mav.addObject("vm_list", vm_list);
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

	/**
	 * 创建POOL
	 * 
	 * @param userId
	 * @param tenantId
	 * @param poolName
	 *            负载均衡的名称
	 * @param type
	 *            类型
	 * @param egress
	 *            带宽
	 */
	@RequestMapping(value = "/g/lbs/pool/add/{user_id}/{tenant_id}")
	@ResponseBody
	public String creatPool(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@RequestParam("new_pool_name") String poolName,
			@RequestParam("net_type") String type,
			@RequestParam("new_pool_egress") String egress) {
		try {
			lbsService.addPool(userId, tenantId, poolName, type, egress);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 创建规则 VIP
	 * 
	 * @param userId
	 * @param tenantId
	 * @param name
	 * @param protocol
	 * @param protocol_port
	 * @param lb_method
	 * @param pool_id
	 * @param cookie_name
	 * @param cookie_type
	 * @param cookie_timeout
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/vip/add/{user_id}/{tenant_id}")
	@ResponseBody
	public String createVip(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@RequestParam("name") String name,
			@RequestParam("protocol") String protocol,
			@RequestParam("protocol_port") String protocol_port,
			@RequestParam("lb_method") String lb_method,
			@RequestParam("pool_id") String pool_id,
			@RequestParam("cookie_name") String cookie_name,
			@RequestParam("cookie_type") String cookie_type,
			@RequestParam("cookie_timeout") String cookie_timeout) {
		SessionPersistencePOJO persistencePOJO = new SessionPersistencePOJO();
		persistencePOJO.setCookie_name(cookie_name);
		persistencePOJO.setTimeout(cookie_timeout);
		persistencePOJO.setType(cookie_type);
		try {
			lbsService.addVip(userId, tenantId, name, protocol, protocol_port,
					lb_method, pool_id, persistencePOJO);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 创建Member 负载主机
	 * 
	 * @param userId
	 * @param tenantId
	 * @param address
	 * @param protocol_port
	 * @param vip_id
	 * @param weight
	 * @param vm_id
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/member/add/{user_id}/{tenant_id}")
	@ResponseBody
	public String createMember(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@RequestParam("address") String address,
			@RequestParam("protocol_port") String protocol_port,
			@RequestParam("vip_id") String vip_id,
			@RequestParam("weight") String weight,
			@RequestParam("vm_id") String vm_id) {
		try {
			lbsService.addMember(userId, tenantId, address, protocol_port,
					vip_id, weight, vm_id);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 创建健康检查
	 * 
	 * @param userId
	 * @param tenantId
	 * @param delay
	 * @param max_retries
	 * @param type
	 * @param timeout
	 * @param rise
	 * @param fall
	 * @param http_method
	 * @param url_path
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/health/add/{user_id}/{tenant_id}")
	@ResponseBody
	public String createHealth(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@RequestParam("delay") String delay,
			@RequestParam("max_retries") String max_retries,
			@RequestParam("type") String type,
			@RequestParam("timeout") String timeout,
			@RequestParam("rise") String rise,
			@RequestParam("fall") String fall,
			@RequestParam("http_method") String http_method,
			@RequestParam("url_path") String url_path) {
		try {
			lbsService.addHealth(userId, tenantId, delay, max_retries, type,
					timeout, rise, fall, http_method, url_path);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除健康检查
	 * 
	 * @param userId
	 * @param tenantId
	 * @param healthId
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/health/del/{user_id}/{tenant_id}/{health_monitor_id}")
	@ResponseBody
	public String removeHealth(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("health_monitor_id") String healthId) {
		try {
			lbsService.deleteHealth(userId, tenantId, healthId);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除负载主机
	 * 
	 * @param userId
	 * @param tenantId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/member/del/{user_id}/{tenant_id}/{member_id}")
	@ResponseBody
	public String removeMember(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("member_id") String memberId) {
		try {
			lbsService.deleteMember(userId, tenantId, memberId);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除规则
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vip_id
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/vip/del/{user_id}/{tenant_id}/{vip_id}")
	@ResponseBody
	public String removeVip(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("vip_id") String vip_id) {
		try {
			// 同时要删除健康检查的引用
			List<HealthPOJO> health_list = lbsService.getHealths(userId,
					tenantId);
			List<String> health_ids = new ArrayList<>();// 存放与该VIP绑定的健康检查ID
			// 查找当前VIP绑定的Health的ID
			for (HealthPOJO healthPOJO : health_list) {
				List<HealthVipPOJO> vip = healthPOJO.getVips();
				for (HealthVipPOJO healthVipPOJO : vip) {
					if (healthVipPOJO.getVip_id().equals(vip_id)) {
						// 如果有绑定,将Health id添加到集合
						health_ids.add(healthPOJO.getId());
					}
				}
			}
			// 解除绑定
			for (String health_id : health_ids) {
				lbsService.vipUnBindHealth(userId, tenantId, vip_id, health_id);
			}
			// 删除VIP
			lbsService.deleteVip(userId, tenantId, vip_id);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 删除POOL
	 * 
	 * @param userId
	 * @param tenantId
	 * @param pool_id
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/pool/del/{user_id}/{tenant_id}/{pool_id}")
	@ResponseBody
	public String removePool(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("pool_id") String poolId) {
		try {
			// 删除POOL，要先删除与VIP的绑定
			PoolPOJO poolPOJO = lbsService.getPool(userId, tenantId, poolId);
			// 当前Pool下的VIP
			List<String> vips = poolPOJO.getVips();
			for (String vip_id : vips) {
				VipPOJO vip = lbsService.getVip(userId, tenantId, vip_id);
				List<String> health_ids = vip.getHealth_monitors();
				for (String health_id : health_ids) {// 接触VIP与Health的绑定
					lbsService.vipUnBindHealth(userId, tenantId, vip_id,
							health_id);
				}
				// 删除VIP
				lbsService.deleteVip(userId, tenantId, vip_id);
			}
			// 删除POOL
			lbsService.deletePool(userId, tenantId, poolId);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * VIP绑定健康检查
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vip_id
	 * @param health_monitor_id
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/health/bind/{user_id}/{tenant_id}/{vip_id}")
	@ResponseBody
	public String bindHealth(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("vip_id") String vip_id,
			@RequestParam("health_monitor_id") String health_monitor_id) {
		try {
			lbsService.vipBindHealth(userId, tenantId, vip_id,
					health_monitor_id);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * VIP解除健康检查绑定
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vip_id
	 * @param health_monitor_id
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/health/unbind/{user_id}/{tenant_id}/{vip_id}/{health_monitor_id}")
	@ResponseBody
	public String unBindHealth(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("vip_id") String vip_id,
			@PathVariable("health_monitor_id") String health_monitor_id) {
		try {
			lbsService.vipUnBindHealth(userId, tenantId, vip_id,
					health_monitor_id);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 更新Health 健康检查
	 * 
	 * @param userId
	 * @param tenantId
	 * @param healthId
	 * @param timeout
	 * @param delay
	 * @param fall
	 * @param rise
	 * @param max_retries
	 * @param admin_state_up
	 * @param type
	 * @param url_path
	 * @param http_method
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/health/update/{user_id}/{tenant_id}/{health_monitor_id}")
	@ResponseBody
	public String setHealth(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("health_monitor_id") String healthId,
			@RequestParam("timeout") String timeout,
			@RequestParam("delay") String delay,
			@RequestParam("fall") String fall,
			@RequestParam("rise") String rise,
			@RequestParam("max_retries") String max_retries,
			@RequestParam("admin_state_up") String admin_state_up,
			@RequestParam("type") String type,
			@RequestParam("url_path") String url_path,
			@RequestParam("http_method") String http_method) {
		try {
			// 将参数转换大写
			type = type.trim().toUpperCase();
			http_method = http_method.trim().toUpperCase();
			lbsService.updateHealth(userId, tenantId, healthId, timeout, delay,
					fall, rise, max_retries, admin_state_up, type, url_path,
					http_method);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 更新Member
	 * 
	 * @param userId
	 * @param tenantId
	 * @param memberId
	 * @param weight
	 * @param admin_state_up
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/member/update/{user_id}/{tenant_id}/{member_id}")
	@ResponseBody
	public String setMember(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("member_id") String memberId,
			@RequestParam("weight") String weight,
			@RequestParam("admin_state_up") String admin_state_up) {
		try {
			lbsService.updateMember(userId, tenantId, memberId, weight,
					admin_state_up);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 更新VIP
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vipId
	 * @param name
	 * @param admin_state_up
	 * @param connection_limit
	 * @param cookie_name
	 * @param cookie_type
	 * @param cookie_timeout
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/vip/update/{user_id}/{tenant_id}/{vip_id}")
	@ResponseBody
	public String setVip(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("vip_id") String vipId,
			@RequestParam("name") String name,
			@RequestParam("admin_state_up") String admin_state_up,
			@RequestParam("connection_limit") String connection_limit,
			@RequestParam("cookie_name") String cookie_name,
			@RequestParam("cookie_type") String cookie_type,
			@RequestParam("cookie_timeout") String cookie_timeout) {
		try {
			lbsService.updateVip(userId, tenantId, vipId, name, admin_state_up,
					connection_limit, cookie_name, cookie_type, cookie_timeout);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

	/**
	 * 更新pool
	 * 
	 * @param userId
	 * @param tenantId
	 * @param poolId
	 * @param name
	 * @param egress
	 * @param admin_state_up
	 * @return
	 */
	@RequestMapping(value = "/g/lbs/pool/update/{user_id}/{tenant_id}/{pool_id}")
	@ResponseBody
	public String setPool(@PathVariable("user_id") String userId,
			@PathVariable("tenant_id") String tenantId,
			@PathVariable("pool_id") String poolId,
			@RequestParam("name") String name,
			@RequestParam("egress") String egress,
			@RequestParam("admin_state_up") String admin_state_up) {
		try {
			lbsService.updatePool(userId, tenantId, poolId, name, egress,
					admin_state_up);
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		return "ok";
	}

}
