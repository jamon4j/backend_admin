package com.ksyun.vm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.vm.pojo.acl.RolePo;
import com.ksyun.vm.pojo.login.User;
import com.ksyun.vm.service.LoginService;
import com.ksyun.vm.service.RoleService;

@Controller
public class ACLController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/g/acl/role_list")
	public ModelAndView getRoleList(ModelAndView mav) {
		List<RolePo> roleList = roleService.getAll();
		mav.addObject("list", roleList);
		mav.setViewName("/gestion/acl/role_list");
		return mav;
	}

	@RequestMapping(value = "/g/acl/user_list")
	public ModelAndView getAclUserList(ModelAndView mav) {
		List<User> userList = loginService.getUserAll();

		User user = null;
		String roles = null;
		String[] roles_list = null;

		int length = userList.size();
		for (int i = 0; i < length; i++) {
			String roles_name = "";
			user = userList.get(i);
			roles = user.getRoles();
			if (roles != null) {
				roles_name = "";
				roles_list = roles.split(",");
				int role_length = roles_list.length;
				for (int j = 0; j < role_length; j++) {
					int roleId = Integer.parseInt(roles_list[j]);
					RolePo role = roleService.getRole(roleId);
					if (role != null) {
						roles_name += roleService.getRole(roleId).getRoleName()
								+ " , ";
					}
				}
			}
			user.setRoles(roles_name);
		}
		mav.addObject("list", userList);
		mav.setViewName("/gestion/acl/user_list");
		return mav;
	}

	@RequestMapping(value = "/g/acl/add_user")
	public String addUser(@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("roles") String roles) {
		String[] role_list = roles.split(",");
		int length = role_list.length;
		for (int i = 0; i < length; i++) {
			String roleId = role_list[i];
			if (!isNumberic(roleId))
				return "redirect:/g/acl/error";
			RolePo role = roleService.getRole(new Integer(roleId));
			if (role == null)
				return "redirect:/g/acl/error";
		}
		loginService.addUser(email, password, roles);
		return "redirect:/g/acl/user_list";
	}

	@RequestMapping(value = "/g/acl/error")
	public ModelAndView error(ModelAndView mav) {
		mav.setViewName("/gestion/acl/error");
		return mav;
	}

	@RequestMapping(value = "/g/acl/add_role")
	public String addRole(@RequestParam("name") String name,
			@RequestParam("roles") String roles,
			@RequestParam("roleType") Byte roleType) {
		roleService.addRole(name, roles, roleType);
		return "redirect:/g/acl/role_list";
	}

	@RequestMapping(value = "/g/acl/delete_role")
	public String deleteRole(@RequestParam("roleId") int roleId) {
		roleService.deleteRole(roleId);
		return "redirect:/g/acl/role_list";
	}

	@RequestMapping(value = "/g/acl/delete_user")
	public String deleteUser(@RequestParam("userId") int userId) {
		loginService.deleteUser(userId);
		return "redirect:/g/acl/user_list";
	}

	@RequestMapping(value = "/g/acl/edit_role_input")
	public ModelAndView edit_role_input(@RequestParam("roleId") int roleId,
			@RequestParam("roleType") Byte roleType,
			@RequestParam("roleName") String roleName,
			@RequestParam("rolePower") String rolePower, ModelAndView mav) {
		mav.addObject("roleId", roleId);
		mav.addObject("roleType", roleType);
		mav.addObject("roleName", roleName);
		mav.addObject("rolePower", rolePower);
		mav.setViewName("/gestion/acl/role_edit");
		return mav;
	}

	@RequestMapping(value = "/g/acl/edit_role")
	public ModelAndView editRole(@RequestParam("roleId") int roleId,
			@RequestParam("roleType") Byte roleType,
			@RequestParam("roleName") String roleName,
			@RequestParam("rolePower") String rolePower, ModelAndView mav) {
		RolePo dto = new RolePo();
		dto.setId(roleId);
		dto.setRoleName(roleName);
		dto.setRolePower(rolePower);
		dto.setRoleType(roleType);
		roleService.updateRole(dto);

		mav.addObject("ret", 0);
		mav.setViewName("/gestion/acl/ret");
		return mav;
	}

	@RequestMapping(value = "/g/acl/user_role_input")
	public ModelAndView user_role_input(@RequestParam("userId") int userId,
			String roles, ModelAndView mav) {
		mav.addObject("userId", userId);
		mav.setViewName("/gestion/acl/user_role_edit");
		return mav;
	}

	@RequestMapping(value = "/g/acl/edit_user_role")
	public ModelAndView edit_user_role(@RequestParam("userId") int userId,
			@RequestParam("roles") String roles, ModelAndView mav) {
		loginService.updateUserRoles(userId, roles);
		mav.addObject("ret", 0);
		mav.setViewName("/gestion/acl/ret");
		return mav;
	}

	@RequestMapping(value = "/g/acl/role_uri")
	public ModelAndView role_uri(ModelAndView mav) {
		mav.setViewName("/gestion/acl/role_uri");
		return mav;
	}

	private boolean isNumberic(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

}
