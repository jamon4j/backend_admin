package com.ksyun.vm.controller;

import com.ksyun.vm.dto.zone.AggregatesDto;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA. User: yuri Date: 13-7-9 Time: 下午12:58 To change
 * this template use File | Settings | File Templates.
 */
@Controller
public class ZoneController {
	//zone列表
	@RequestMapping("/g/zonelist")
	public ModelAndView returnZoneList(HttpServletRequest request, ModelAndView mav) throws IOException {
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		List<AggregatesDto> list = JsonParser.returnAggregatesList(tenantId);
		mav.addObject("zonelist", list);
		mav.setViewName("/gestion/zone/zone_list");
		return mav;
	}
}
