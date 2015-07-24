package com.hehenian.manager.actions.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController extends BaseController<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping("/common/error")
	public ModelAndView error(String message){
		if(StringUtils.isBlank(message)){
			message="程序异常";
		}
		 return this.ajaxSuccessAndRefresh(message);
	}
	
}
