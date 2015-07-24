package com.hehenian.manager.actions.loan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanRepaymentService;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.actions.common.PageDoUtil;
import com.hehenian.manager.commons.NewPagination;

/**
 * @Description 还款订单管理
 * @author huangzl QQ: 272950754
 * @date 2015年7月22日 下午4:36:28
 * @Project hehenian-manager
 * @Package com.hehenian.manager.actions.loan 
 * @File LoanRepaymentController.java
*/
@Controller
@RequestMapping("/loanRepayment")
public class LoanRepaymentController extends BaseAction {
	private static Log log = LogFactory.getLog(LoanRepaymentController.class);

	@Autowired
	private ILoanRepaymentService loanRepaymentControllerService;
	
	/**
	 * 进入贷后管理页面
	 * @author huangzl QQ: 272950754
	 * @date 2015年7月22日 下午4:40:41
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/index")
	public String menuLoan(ModelMap modelMap) {
		return "/loan/loanRepayment";
	}
	/**
	 * 按条件查询订单List
	 * @author huangzl QQ: 272950754
	 * @date 2015年7月22日 下午6:39:45
	 * @param response
	 */
	@RequestMapping("/loanRepaymentList")
	public void loanRepaymentList(NewPagination<Map<String, Object>> pagination,HttpServletResponse response) {
		PageDo<Map<String, Object>> page = PageDoUtil.getPage(pagination);
		Map<String, Object> searchItems = new HashMap<String, Object>();
		String searchType = getString("searchType");
		if(searchType!=null&&searchType.length()>0){
			searchItems.put("searchType", searchType);
			page=loanRepaymentControllerService.getLoanRepaymentList(searchItems,page);
		}
		
		pagination = PageDoUtil.getPageValue(pagination, page);
		outPrint(response, JSONObject.fromObject(pagination));
	}


}
