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
package com.hehenian.manager.actions.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.loan.ILoanPersonCreditService;
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.dataobject.LoanCreditRecDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.actions.common.Maps;

/**
 * @Description 征信报告补入
 * @author huangzl QQ: 272950754
 * @date 2015年4月20日 下午2:32:14
 * @Project hehenian-lend-web
 * @Package com.hehenian.web.view.loan.action
 * @File CreditAction.java
 */
@Controller
@RequestMapping(value="/credit")
public class CreditAction extends BaseAction{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IManagerLoanService managerLoanService;
	
	
	@Autowired
	private ILoanPersonCreditService loanPersonCreditService;
	
	Map<Object, Object> map_success = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_SUCCESS);
	Map<Object, Object> map_failure  = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_FAILURE);
	


	/**
	 * 新增 征信记录表
	 * 
	 * @author: huangzlmf
	 * @date: 2015年4月20日 21:09:54
	 * @param param
	 * @param sign
	 * @param map
	 * @return
	 */
	@RequestMapping("/toAddCrdit")
	public String toAddCrdit() {
		logger.info("----toAddCrdit----");
		return "/loan/addCredit";
	}

	/**
	 * 保存征信记录
	 * 
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/addCredit")
	@ResponseBody
	public void addCredit(String loanUserName, String loanMobile, double repayAmount, String repayExceptionNumber,
			int overNumber, double creditAmount, int queryNumber ,double houseFundYears, double houseFundAmount, 
			double houseFundScale , HttpServletResponse response) {
		
		logger.info("----addCredit------");
		
		LoanPersonCreditDo newLoanPersonCreditDo = new LoanPersonCreditDo();
		newLoanPersonCreditDo.setCreateTime(new Date());
		newLoanPersonCreditDo.setStatus("T");
		newLoanPersonCreditDo.setUpdateTime(new Date());
		
		
		//根据用户名和电话取用户ID
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("realName", loanUserName);
		param.put("mobile", loanMobile);
		param.put("productCode", "D03");
		param.put("productCode", "D03");
		List<String> statusList = new ArrayList<String>();
		statusList.add("PENDING");
		statusList.add("PROCESSING");
		param.put("status", statusList);
		LoanDo loan = managerLoanService.getLoanforUpdate(param);
		if(null == loan || loan.getUserId() == null){
			Map<Object,Object> resultMap = new HashMap<Object,Object>();
			resultMap.putAll(map_failure);
			resultMap.put("msg", "该用户没有审核中和待处理的订单");
			outPrint(response, JSONSerializer.toJSON(resultMap));
			return;
		}
		newLoanPersonCreditDo.setUserId(loan.getUserId());
		newLoanPersonCreditDo.setLoanId(loan.getLoanId());
		//end 根据用户名和电话取用户ID
		
		//征信记录
		List<LoanCreditRecDo> creditRecordList = new ArrayList<LoanCreditRecDo>();
		LoanCreditRecDo loanCreditRec = new LoanCreditRecDo();
		loanCreditRec.setCreditItem("repayAmount");
		loanCreditRec.setCreditItemVal(String.valueOf(repayAmount));
		loanCreditRec.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec);
		
		
		LoanCreditRecDo loanCreditRec1 = new LoanCreditRecDo();
		loanCreditRec1.setCreditItem("repayExceptionNumber");
		loanCreditRec1.setCreditItemVal(String.valueOf(repayExceptionNumber));
		loanCreditRec1.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec1);
		
		//
		LoanCreditRecDo loanCreditRec2 = new LoanCreditRecDo();
		loanCreditRec2.setCreditItem("overNumber");
		loanCreditRec2.setCreditItemVal(String.valueOf(overNumber));
		loanCreditRec2.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec2);
		
		
		LoanCreditRecDo loanCreditRec3 = new LoanCreditRecDo();
		loanCreditRec3.setCreditItem("creditAmount");
		loanCreditRec3.setCreditItemVal(String.valueOf(creditAmount));
		loanCreditRec3.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec3);
		
		
		LoanCreditRecDo loanCreditRec4 = new LoanCreditRecDo();
		loanCreditRec4.setCreditItem("queryNumber");
		loanCreditRec4.setCreditItemVal(String.valueOf(queryNumber));
		loanCreditRec4.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec4);
		
		
		
		LoanCreditRecDo loanCreditRec5 = new LoanCreditRecDo();
		loanCreditRec5.setCreditItem("houseFundYears");
		loanCreditRec5.setCreditItemVal(String.valueOf(houseFundYears));
		loanCreditRec5.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec5);
		
		
		LoanCreditRecDo loanCreditRec6 = new LoanCreditRecDo();
		loanCreditRec6.setCreditItem("houseFundAmount");
		loanCreditRec6.setCreditItemVal(String.valueOf(houseFundAmount));
		loanCreditRec6.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec6);
		
		LoanCreditRecDo loanCreditRec7 = new LoanCreditRecDo();
		loanCreditRec7.setCreditItem("houseFundScale");
		loanCreditRec7.setCreditItemVal(String.valueOf(houseFundScale));
		loanCreditRec7.setCreditAmt(0l);
		creditRecordList.add(loanCreditRec7);
		
		newLoanPersonCreditDo.setCreditRecordList(creditRecordList);
		//end 征信明细
		
		int i = loanPersonCreditService.addLoanPersonCreditWithLoan(newLoanPersonCreditDo,loan);	
	
		if(i <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
			 return;
		}
        outPrint(response, JSONSerializer.toJSON(map_success));
        
		logger.info("----End:addCredit--------");
	}
}
