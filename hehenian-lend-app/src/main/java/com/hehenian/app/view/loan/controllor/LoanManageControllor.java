package com.hehenian.app.view.loan.controllor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.app.common.exception.SessionException;
import com.hehenian.app.view.loan.common.AppConstants;
import com.hehenian.biz.common.loan.ILoanPersonService;
import com.hehenian.biz.common.loan.ILoanRepaymentService;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;

/**
 * @Description 贷后管理
 * @author huangzl QQ: 272950754  zhengyfmf
 * @date 2015年4月4日 下午2:36:00
 * @Project hehenian-lend-app
 * @Package com.hehenian.app.view.loan.controllor 
 * @File LoanManageControllor.java
*/
@Controller
@RequestMapping(value = "app/mhk/loanManage")
public class LoanManageControllor extends AppBaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ILoanRepaymentService loanRepaymentService;

	@Autowired
	private ILoanPersonService loanPersonService;

	/**
	 * 逾期页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/overdueRepayPage")
	public String overdueRepayPage() {
		return "app/mhk/overdue/overdueRepay";
	}

	/**
	 * 代还款页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/stillRepayPage")
	public String stillRepayPage() {
		return "app/mhk/overdue/stillRepay";
	}

	/**
	 * 已还款页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasRepayPage")
	public String hasRepayPage() {
		return "app/mhk/overdue/hasRepay";
	}

	/**
	 * 已还清页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/repayedPage")
	public String repayedPage() {
		return "app/mhk/overdue/repayed";
	}

	@RequestMapping(value = "/overdueList")
	@ResponseBody
	public Map<String, Object> overdueList(String realName, Long pageSize, Long curPage, Long loanStatus, Model model, HttpServletRequest request) {
		logger.debug("[逾期查询：搜索内容：=" + realName + ",pageSize=" + pageSize + ",curPage=" + curPage + "],loanStatus=" + loanStatus);
		List<Map<String, Object>> list = queryRepaying(realName, pageSize, curPage, loanStatus, request);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", 0);
		data.put("data", list);
		System.out.println("========================");
		System.out.println("data:" + data.toString());
		return data;
	}

	/**
	 * 逾期
	 * 
	 * @return
	 */
	@RequestMapping(value = "/overdueRepay")
	@ResponseBody
	public Map<String, Object> overdueRepay(String searchStr, Long pageSize, Long pageNum, Model model, HttpServletRequest request) {
		logger.debug("[逾期查询：搜索内容：=" + searchStr + ",pageSize=" + pageSize + ",pageNum=" + pageNum + "]");
		Long queryStatus = 1L;
		List<Map<String, Object>> list = queryRepaying(searchStr, pageSize, pageNum, queryStatus, request);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", 0);
		data.put("data", list);
		System.out.println("========================");
		System.out.println("data:" + data.toString());
		return data;
	}

	/**
	 * 待还款
	 * 
	 * @return
	 */
	@RequestMapping(value = "/stillRepay")
	@ResponseBody
	public Map<String, Object> stillRepayment(String searchStr, Long pageSize, Long pageNum, Model model, HttpServletRequest request) {
		logger.debug("[逾期查询：搜索内容：=" + searchStr + ",pageSize=" + pageSize + ",pageNum=" + pageNum + "]");
		Long queryStatus = 2L;
		List<Map<String, Object>> list = queryRepaying(searchStr, pageSize, pageNum, queryStatus, request);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", 0);
		data.put("data", list);
		System.out.println("========================");
		System.out.println("data:" + data.toString());
		return data;
	}

	/**
	 * 已还款
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasRepay")
	@ResponseBody
	public Map<String, Object> hasRepayment(String searchStr, Long pageSize, Long pageNum, Model model, HttpServletRequest request) {
		logger.debug("[逾期查询：搜索内容：=" + searchStr + ",pageSize=" + pageSize + ",pageNum=" + pageNum + "]");
		Long queryStatus = 3L;
		List<Map<String, Object>> list = queryRepaying(searchStr, pageSize, pageNum, queryStatus, request);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", 0);
		data.put("data", list);
		return data;
	}

	/**
	 * 已还清
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repayed")
	@ResponseBody
	public Map<String, Object> repayed(String searchStr, Long pageSize, Long pageNum, Model model, HttpServletRequest request) {
		logger.debug("[逾期查询：搜索内容：=" + searchStr + ",pageSize=" + pageSize + ",pageNum=" + pageNum + "]");
		Map<String, Object> searchItems = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(searchStr)) {
			searchItems.put("realName", searchStr);
			/*
			 * boolean result=searchStr.matches("[0-9]+"); if(result){
			 * if(searchStr.length()>3){ searchItems.put("mobile", searchStr); }
			 * }else{ searchItems.put("realName", searchStr); }
			 */
		}
		searchItems.put("loanStatus", LoanStatus.REPAYED.name());
		try {
			searchItems.put("loanCidList", getLoanCidList());
		} catch (SessionException e) {
			e.printStackTrace();
		}
		pageNum = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request.getParameter("curPage")) : 1);
		pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request.getParameter("pageSize")) : 10);
		Long beginCount = (pageNum - 1) * pageSize;
		searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
		searchItems.put("pageSize", pageSize);
		List<Map<String, Object>> list = loanRepaymentService.getByLoanStatus(searchItems);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", list);
		data.put("status", 0);
		return data;
	}

	/**
	 * 查看记录
	 * 
	 * @param detailType
	 *            //查看记录类型 ：1-逾期 2-待还款 3-已还款 4-已还清
	 * @author zhengyfmf
	 * @return
	 */
	@RequestMapping(value = "/repayDetail")
	public String repayDetail(Integer detailType, Long loanId, Integer repayPeriod, Model model) {
		// 查询借款人信息
		LoanPersonDo loanPersonDo = loanPersonService.getLoanPersonById(loanId);
		model.addAttribute("loanPersonDo", loanPersonDo);
		// 查询还款信息
		List<LoanRepaymentDo> list = loanRepaymentService.queryRepaymentByLoanId(loanId);
		model.addAttribute("rmList", list);
		if (detailType.intValue() != 4) {
			if (list != null) {
				for (LoanRepaymentDo rm : list) {
					if (rm.getRepayPeriod() == repayPeriod) {
						model.addAttribute("loanRepaymentDo", rm);
						break;
					}
				}
			}
		}
		String resStr = "";
		switch (detailType.intValue()) {
		case 1:
			resStr = "app/mhk/overdue/overdueRepayDetail";
			break;
		case 2:
			resStr = "app/mhk/overdue/stillRepayDetail";
			break;
		case 3:
			resStr = "app/mhk/overdue/hasRepayDetail";
			break;
		case 4:
			resStr = "app/mhk/overdue/repayedDetail";
			break;
		default:
			break;
		}
		return resStr;
	}

	/**
	 * @param queryStatus
	 *            查询状态：1-逾期、2-待还款、3-已还款,4、已还清
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> queryRepaying(String searchStr, Long pageSize, Long pageNum, Long queryStatus, HttpServletRequest request) {
		Map<String, Object> searchItems = new HashMap<String, Object>();
		try {
			searchItems.put("loanCidList", getLoanCidList());
		} catch (SessionException e) {
			e.printStackTrace();
		}
		if (queryStatus == 4) {
			if (StringUtils.isNotBlank(searchStr)) {
				searchItems.put("realName", searchStr);
			}
			searchItems.put("loanStatus", LoanStatus.REPAYED.name());
			pageNum = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request.getParameter("curPage")) : 1);
			pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request.getParameter("pageSize")) : 10);
			Long beginCount = (pageNum - 1) * pageSize;
			searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
			searchItems.put("pageSize", pageSize);
			return loanRepaymentService.getByLoanStatus(searchItems);
		} else {
			searchItems.put("queryStatus", queryStatus);
			if (StringUtils.isNotBlank(searchStr)) {
				boolean result = searchStr.matches("[0-9]+");
				if (result) {
					if (searchStr.length() > 3) {
						searchItems.put("mobile", searchStr);
					}
				} else {
					searchItems.put("realName", searchStr);
				}
			}
			pageNum = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request.getParameter("curPage")) : 1);
			pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request.getParameter("pageSize")) : 10);
			Long beginCount = (pageNum - 1) * pageSize;
			searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
			searchItems.put("pageSize", pageSize);
			return loanRepaymentService.listRepayment(searchItems);
		}
	}

}
