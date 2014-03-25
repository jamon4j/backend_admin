package com.ksyun.payment.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.payment.common.ParamValueCheckedException;
/**
 * 全局错误处理
 * @author ZhangYanchun
 * @Date 2013-08-15
 */
public class PaymentHandlerExceptionResolver implements HandlerExceptionResolver {
	
    private Logger logger = Logger.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse rep, Object handler, Exception ex) {
		
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex_class", ex.getClass().getSimpleName());
        model.put("ex_cause", ex.getMessage());
        
        String url = req.getRequestURI();
        String param =req .getQueryString();
        if (param != null && param.length() > 0) {
                  url += ("?" +param);
        }
        logger.error("异常跳转: "+ url, ex);//把漏网的异常信息记入日志 
        if(ex instanceof ParamValueCheckedException) {
        	model.put("errorMsgs", (Map<String, String>)req.getAttribute("errorMsgs"));
        	return new ModelAndView("error/params_checked", model);
        }
        return new ModelAndView("error/error", model);
	}

}
