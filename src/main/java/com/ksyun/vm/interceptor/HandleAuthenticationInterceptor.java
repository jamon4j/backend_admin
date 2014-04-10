package com.ksyun.vm.interceptor;

import com.ksyun.vm.pojo.acl.RolePo;
import com.ksyun.vm.routedatasource.DataSourceInstances;
import com.ksyun.vm.service.DataSwitchService;
import com.ksyun.vm.service.RoleService;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆、鉴权系统拦截器 User: Norton Chen(cjx) Date: 2013-07-17
 */
public class HandleAuthenticationInterceptor extends HandlerInterceptorAdapter {
	public static Map<String, Calendar> map = new HashMap<>();
	public static Map<String, String> mapUserRoles = new HashMap<>();
	public static Map<String, String> mapDataSource = new HashMap<>();

	private RoleService roleService;

	private DataSwitchService dataSwitchService;

	// 定义日志
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String host = request.getRemoteHost();
		Cookie cookie = getCookieByName(request, InitConst.BACKEND);
		String type = Constants.getPropertyValue("sys.type");
		Integer timeout = Integer.valueOf(Constants
				.getPropertyValue("cookie.timeout"));
		/*
		 * if(uri.indexOf("/login")>-1||uri.indexOf("/html")>-1||uri.indexOf("/js"
		 * )>-1||uri.indexOf("/img")>-1|| uri.indexOf("/getvmsum")>-1 ||
		 * host.equals("127.0.0.1")||type.equals("test")){ return true; }
		 */
		if (uri.indexOf("/login") > -1 || uri.indexOf("/html") > -1
				|| uri.indexOf("/js") > -1 || uri.indexOf("/img") > -1
				|| uri.indexOf("/getvmsum") > -1) {
			return true;
		}
		if (cookie != null) {
			String backend = cookie.getValue();
			if (backend != null) {
				Calendar now = Calendar.getInstance();
				Calendar time = map.get(backend);
				if (time == null) {
					response.sendRedirect("/login");
					return false;
				}
				if (now.getTimeInMillis() - time.getTimeInMillis() > timeout) {
					map.remove(backend);
					mapUserRoles.remove(backend);
					response.sendRedirect("/login");
					return false;
				}
			} else {
				response.sendRedirect("/login");
				return false;
			}

		} else {
			response.sendRedirect("/login");
			return false;
		}

		String cookieValue = cookie.getValue();

		String dataSource = mapDataSource.get(cookieValue);

		// ds1代表公有云 ds2代表私有云

		// 获取Cookie中当前使用的云类型
		Cookie nowCookie = getCookieByName(request, InitConst.COOKIE_NOW_NAME);
		if (nowCookie != null && nowCookie.getValue() != null) {// 云类型Cookie存在，且值存在
			String[] splitCookie = nowCookie.getValue().split(
					InitConst.COOKIE_SPLIT);
			switch (splitCookie[splitCookie.length - 1]) {
			case InitConst.COOKIE_PUBLIC:// 公有云
				dataSource = DataSourceInstances.DS1;
				mapDataSource.put(cookieValue, DataSourceInstances.DS1);
				dataSwitchService.setDataSource(dataSource);
				Constants.setPropertyValue(InitConst.HTTP_HOST,
						Constants.getPropertyValue(InitConst.HTTP_HOST_PUBLIC));
				Constants.setPropertyValue(InitConst.HTTP_PORT,
						Constants.getPropertyValue(InitConst.HTTP_PORT_PUBLIC));
				break;
			case InitConst.COOKIE_PRIVATE:// 私有云
				dataSource = DataSourceInstances.DS2;
				mapDataSource.put(cookieValue, DataSourceInstances.DS2);
				dataSwitchService.setDataSource(dataSource);
				Constants.setPropertyValue(InitConst.HTTP_HOST,
						Constants.getPropertyValue(InitConst.HTTP_HOST_PRIVATE));
				Constants.setPropertyValue(InitConst.HTTP_PORT,
						Constants.getPropertyValue(InitConst.HTTP_PORT_PRIVATE));
				break;
			}
		}
		logger.debug(String.format("DataSource: %s", dataSource));

		// 该段代码与上面功能重复
		/*
		 * if (dataSource == null) { mapDataSource.put(cookieValue,
		 * DataSourceInstances.DS1);
		 * dataSwitchService.setDataSource(DataSourceInstances.DS1);
		 * Constants.setPropertyValue(InitConst.HTTP_HOST,
		 * Constants.getPropertyValue("http.host")); } else {
		 * dataSwitchService.setDataSource(dataSource); if
		 * (dataSource.equals(DataSourceInstances.DS2)) {
		 * Constants.setPropertyValue(InitConst.HTTP_HOST,
		 * InitConst.HTTP_HOST_IP); //
		 * 这里写上私有云的ip，正式部署部署公有云环境。需要确保公有云环境可以访问私有云数据库以及ip } }
		 */

		String roles = mapUserRoles.get(cookieValue);

		if (uri.equals("/g/")) {
			return true;
		}
		if (!isHavePower(roles, uri)) {
			response.sendRedirect("/test.jsp");
			return false;
		}
		return true;
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	public boolean isHavePower(String roles, String uri) {
		String[] role_power_list = null;
		if (uri.indexOf("/g/home") == 0 || uri.endsWith("/g/"))
			return true; // 主页不判断权限

		if (roles == null)
			return true;
		String[] role_list = roles.split(",");
		String[] uri_list = uri.split("/");
		int length = role_list.length;
		for (int i = 0; i < length; i++) {
			int roleId = Integer.parseInt(role_list[i]);
			if (roleId == 1)
				return true; // admin

			// 普通员工--根据role_power判断权限。
			RolePo role = roleService.getRole(roleId);
			if (role != null) {
				role_power_list = role.getRolePower().split(",");
				for (int j = 0; j < role_power_list.length; j++) {
					if (uri.indexOf(role_power_list[j]) == 0)
						return true;
				}
			}
		}

		return false;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public DataSwitchService getDataSwitchService() {
		return dataSwitchService;
	}

	public void setDataSwitchService(DataSwitchService dataSwitchService) {
		this.dataSwitchService = dataSwitchService;
	}

}
