package com.ksyun.ic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.dto.UserInfoDto;
import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.UserPo;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.pojo.vm.VmPojo;
import com.ksyun.vm.service.VmService;
import com.ksyun.vm.utils.UserService;

/**
 * 带宽升级 计费Controller
 * 
 * @author XueQi
 * 
 */
@Controller
public class EGressController {
	@Autowired
	private UserService userService;
	@Autowired
	private VmService vmService;

	/**
	 * 访问带宽升级首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	@RequestMapping(value = "/egress/index")
	public String indexEgressUpdate(HttpServletRequest request,
			HttpServletResponse response) throws ErrorCodeException,
			NoTokenException {
		Long userId = ServletUtil.getLong(request, "userId", 0);
		String email = ServletUtil.getStr(request, "email");
		String mobile = ServletUtil.getStr(request, "mobile");

		request.setAttribute("userId", 0);
		request.setAttribute("email", "");
		request.setAttribute("mobile", "");
		// 查询所有用户
		List<UserInfoDto> list = userService.findUserInfoByEmailAndMobile(
				userId, email, mobile);
		request.setAttribute("user_list", list);
		return "admin/egress/egress_index";
	}

	/**
	 * 根据Email查询VM
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws NoTokenException
	 * @throws ErrorCodeException
	 */
	@RequestMapping(value = "/egress/vms/{email}")
	public String getUserVm(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("email") String email) {
		try {
			String[] urls = request.getRequestURI().split("/");
			email = urls[urls.length - 1];
			request.setAttribute("user_email", email);
			UserPojo user = userService.searchUser("", email, "");
			// 查询到UserPOJO，可获得token
			List<VmPojo> vm_list = vmService.getVms(user.getUser_id(),
					user.getTenant_id());
			request.setAttribute("vm_list", vm_list);
		} catch (ErrorCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "admin/egress/egress_vms_error";
		} catch (NoTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "admin/egress/egress_vms_error";
		}
		return "admin/egress/egress_vms";
	}

	/**
	 * 带宽更新(AJAX)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String updateEgress(HttpServletRequest request,
			HttpServletResponse response) {
		// 调用宗礼的接口
		// 根据返回值调用OpenStack接口
		return null;
	}
}
