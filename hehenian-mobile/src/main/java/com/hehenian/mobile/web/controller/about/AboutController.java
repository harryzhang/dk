/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.web.controller.about
 * @Title: AboutController.java
 * @Description: TODO
 *
 * @author: zhanbmf
 * @date 2015-4-3 下午3:55:11
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.web.controller.about;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hehenian.mobile.web.controller.BaseController;

@Controller
@RequestMapping(value = "/about")
public class AboutController extends BaseController {
	private static final Logger logger = Logger.getLogger(AboutController.class);
	
	/**
	 * 更多
	 */
	@RequestMapping(value = "index")
	public String index(){
		return "about/index";
	}
	
	/**
	 * 平台介绍
	 */
	@RequestMapping(value = "introduce")
	public String introduce(){
		return "about/introduce";
	}

}
