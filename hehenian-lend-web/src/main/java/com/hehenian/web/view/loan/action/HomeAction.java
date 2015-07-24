/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.loan.action
 * @Title: LoanDetailAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午10:00:04
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.loan.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.loan.ICommonService;
import com.hehenian.biz.common.loan.ILoanApplyService;
import com.hehenian.biz.common.loan.ILoanDetailService;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.JobDo.JobType;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.LoanSMSNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.trade.ISettleCalculatorService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.IdCardUtils;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.web.common.constant.WebConstants;

/**
 * @Description 会员中心
 * @author huangzl QQ: 272950754
 * @date 2015年5月8日 下午3:29:22
 * @Project hehenian-lend-web
 * @Package com.hehenian.web.view.loan.action 
 * @File HomeAction.java
*/
@Controller
// @RequestMapping(value="/web")
public class HomeAction {
	private final Logger logger = Logger.getLogger(this.getClass());
	private DecimalFormat df = new DecimalFormat("##0.00");
	@Autowired
	private ILoanDetailService loanDetailService;
	@Autowired
	private ILoanApplyService loanApplyService;
	@Autowired
	private ISettleCalculatorService settleCalculatorService;
	@Autowired
	private INotifyService smsNotifyService;
	@Autowired
	private ICommonService commonService;
	
	private static Map<Integer, Double> creditRateMap = new HashMap<Integer, Double>();
	static {
		// 借款期限3个月授信比例
		creditRateMap.put(1, 1.5);
		creditRateMap.put(2, 2.0);
		creditRateMap.put(3, 2.5);
		creditRateMap.put(4, 3.0);
		creditRateMap.put(5, 3.5);
		// 借款期限6个月授信比例
		creditRateMap.put(6, 3.0);
		creditRateMap.put(7, 3.5);
		creditRateMap.put(8, 4.0);
		creditRateMap.put(9, 4.5);
		creditRateMap.put(10, 5.0);
	}

	/**
	 * 新增 申请 跳转界面
	 * 
	 * @author: huangzlmf
	 * @date: 2015年4月20日 21:09:54
	 * @param param
	 * @param sign
	 * @param map
	 * @return
	 */
	@RequestMapping("/home")
	public String home() {
		logger.info("----home----");
		return "/web/home/home-index";
	}

	

}
