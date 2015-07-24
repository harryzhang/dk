/**
 * 
 * 授信额度的处理 controller
 * 
 */
package com.hehenian.app.view.loan.eloan.controllor;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.loan.ILoanPersonCreditService;
import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.common.loan.dataobject.LoanCreditRecDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;


@Controller
@RequestMapping(value="/app/credit")
public class LoanCreditController {
	
	private final Logger             logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ILoanPersonCreditService loanPersonCreditService;
	
	/**
     * 个人授信页面
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "/getCredit", method = RequestMethod.GET)
    public String toCredit(Model model, HttpSession session) {
        return "/app/elend/getCredit";
    }
    
    
    /**
     * 
     * 编辑个人授信
     * 
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/addCredit", method = RequestMethod.GET)
    public String addCredit(Model model, HttpSession session){
    	return "app/elend/addCredit";
    }
    
    
    /**
     * 
     * 保存个人授信去查看个人授信
     * 
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/saveCredit", method = RequestMethod.POST)
    public String saveCredit(HttpServletRequest request, HttpServletResponse response){
    	
    	AccountUserDo sessionUser = (AccountUserDo) request.getSession().getAttribute("user");
    	LoanChannelDo sessionloanChannelDo = (LoanChannelDo) request.getSession().getAttribute("loanChannelDo");
    	
    	
    	String ownerType=request.getParameter("ownerType");
    	String breakfast=request.getParameter("breakfast");
    	String duty=request.getParameter("duty");
    	String education=request.getParameter("education");
    	String marriage=request.getParameter("marriage");
    	String creditCard=request.getParameter("creditCard");
    	String traveling = request.getParameter("traveling");
    	String car = request.getParameter("car");
    	
    	LoanPersonCreditDo newCredit = new LoanPersonCreditDo();
    	//业主类型
    	LoanCreditRecDo ownerCreditRecord = new LoanCreditRecDo();
    	ownerCreditRecord.setCreditItem("ownerType");
    	ownerCreditRecord.setCreditItemVal(ownerType);
    	ownerCreditRecord.setCreditAmt(Long.valueOf(ownerType));
    	newCredit.addCreditRecord(ownerCreditRecord);
    	//早餐
    	LoanCreditRecDo breakfastCreditRecord = new LoanCreditRecDo();
    	breakfastCreditRecord.setCreditItem("breakfast");
    	breakfastCreditRecord.setCreditItemVal(breakfast);
    	breakfastCreditRecord.setCreditAmt(Long.valueOf(breakfast));
    	newCredit.addCreditRecord(breakfastCreditRecord);
    	//职业
    	LoanCreditRecDo dutyCreditRecord = new LoanCreditRecDo();
    	dutyCreditRecord.setCreditItem("duty");
    	dutyCreditRecord.setCreditItemVal(duty);
    	dutyCreditRecord.setCreditAmt(Long.valueOf(duty));
    	newCredit.addCreditRecord(dutyCreditRecord);
    	//学历
    	LoanCreditRecDo educationCreditRecord = new LoanCreditRecDo();
    	educationCreditRecord.setCreditItem("education");
    	educationCreditRecord.setCreditItemVal(education);
    	educationCreditRecord.setCreditAmt(Long.valueOf(education));
    	newCredit.addCreditRecord(educationCreditRecord);
    	//婚姻
    	LoanCreditRecDo marriageCreditRecord = new LoanCreditRecDo();
    	marriageCreditRecord.setCreditItem("marriage");
    	marriageCreditRecord.setCreditItemVal(marriage);
    	marriageCreditRecord.setCreditAmt(Long.valueOf(marriage));
    	newCredit.addCreditRecord(marriageCreditRecord);
    	//信用卡
    	LoanCreditRecDo creditCardCreditRecord = new LoanCreditRecDo();
    	creditCardCreditRecord.setCreditItem("creditCard");
    	creditCardCreditRecord.setCreditItemVal(creditCard);
    	creditCardCreditRecord.setCreditAmt(Long.valueOf(creditCard));
    	newCredit.addCreditRecord(creditCardCreditRecord);
    	//旅游车
    	LoanCreditRecDo carCreditRecord = new LoanCreditRecDo();
    	carCreditRecord.setCreditItem("car");
    	carCreditRecord.setCreditItemVal(car);
    	carCreditRecord.setCreditAmt(Long.valueOf(car));
    	newCredit.addCreditRecord(carCreditRecord);
    	
    	
    	long creditAmt = newCredit.sumCreditAmt();
    	newCredit.setCreditAmt(creditAmt);
    	if(sessionUser == null || sessionUser.getId()==null ){
    		newCredit.setUserId(-1l);
    	}else{
    		newCredit.setUserId(sessionUser.getId());
    	}
    	newCredit.setStatus("T");
    	
    	loanPersonCreditService.addLoanPersonCredit(newCredit);
    	Long cid = sessionloanChannelDo.getLoanUserDo().getCid();
    	loanPersonCreditService.saveCreditToSet(cid, newCredit.getUserId(), creditAmt);
    	
    	Long creditSeq = loanPersonCreditService.getSortedByMember(cid, newCredit.getUserId());
    	request.setAttribute("credit", newCredit);
    	request.getSession().setAttribute("creditSeq", creditSeq);
    	return "app/elend/showCredit";
    }

}
