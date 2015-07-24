/**
 * @auther liminglmf
 * @date 2015年4月24日
 *//*
package com.hehenian.manager.actions.loan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanDictDtlService;
import com.hehenian.biz.common.loan.ILoanDictService;
import com.hehenian.biz.common.loan.dataobject.CommonComboxConstants;
import com.hehenian.biz.common.loan.dataobject.LoanDictDo;
import com.hehenian.biz.common.loan.dataobject.LoanDictDtlDo;
import com.hehenian.manager.web.view.controller.BaseController;

*//**
 * @author liminglmf
 *
 *//*
@Controller
@RequestMapping("/loan/dict")
public class LoanDictController extends BaseController<Object>{
	
	@Autowired
	private ILoanDictService loanDictService;
	
	@Autowired
	private ILoanDictDtlService loanDictDtlService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String loanDictList(Model model){
		PageDo<LoanDictDo> page = getPage();
		Map<String,Object> param = new HashMap<String,Object>();
		
		String dictName = request.getParameter("dictName");//名称
		if(StringUtils.isNotBlank(dictName)){
			param.put("name", dictName);
			model.addAttribute("dictName",dictName);
		}
		String dictStatus = request.getParameter("dictStatus");//产品类型
		if(StringUtils.isNotBlank(dictStatus)){
			param.put("status", dictStatus);
			model.addAttribute("dictStatus",dictStatus);
		}
		
		page = loanDictService.getLoanDictPage(param, page);
		List<CommonComboxConstants> statusList = CommonComboxConstants.getStatusList();
		model.addAttribute("statusList", statusList);
		model.addAttribute("pageDo", page);
		return "loan/loanDictList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/dtl/list")
	public String loanDictDtlList(Model model){
		PageDo<LoanDictDtlDo> page = getPage();
		Map<String,Object> param = new HashMap<String,Object>();
		
		String dictName = request.getParameter("dictName");//名称
		if(StringUtils.isNotBlank(dictName)){
			param.put("name", dictName);
			model.addAttribute("dictName",dictName);
		}
		String dictStatus = request.getParameter("dictStatus");//产品类型
		if(StringUtils.isNotBlank(dictStatus)){
			param.put("status", dictStatus);
			model.addAttribute("dictStatus",dictStatus);
		}
		
		page = loanDictDtlService.getLoanDictDtlPage(param, page);
		List<CommonComboxConstants> statusList = CommonComboxConstants.getStatusList();
		model.addAttribute("statusList", statusList);
		model.addAttribute("pageDo", page);
		return "loan/loanDictDtlList";
	}
	
}
*/