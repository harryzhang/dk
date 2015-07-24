package com.sp2p.action.admin;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.OperationLogService;
import com.sp2p.util.DateUtil;

/**
 * 债权转让
 */
public class DebtManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(DebtManageAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;
	
	/**
	 * 申请中的债权转让初始化
	 * @return
	 */
	public String queryApplyDebtInit(){
		return SUCCESS;
	}
	
	/**
	 * 申请中的债权转让
	 * @throws DataException 
	 */
	public String queryApplyDebtInfo() throws DataException{
		String debtStatus = "1";
		String borrowerName = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowerName"));
		String alienatorName = SqlInfusion.FilteSqlInfusion(paramMap.get("alienatorName"));
		try {
			assignmentDebtService.queryApplyDebt(borrowerName,alienatorName,debtStatus,pageBean);
			Map<String, String> repaymentMap = assignmentDebtService.queryApplyDebtDetail();
	         request().setAttribute("repaymentMap",repaymentMap);
			//统计当前页应收款
			double debtSumm = 0;
			List<Map<String,Object>> payList = pageBean.getPage();
			if (payList!=null){
				for (Map<String, Object> map : payList) {
					debtSumm = debtSumm +Convert.strToDouble(map.get("debtSum")+"",0);
				}
			}
			DecimalFormat fmt=new DecimalFormat("0.00");
			request().setAttribute("debtSumm", fmt.format(debtSumm));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}
	
	/**
	 * 债权转让审核
	 * @return
	 */
	public String auditDebt(){
		String auditStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("auditStatus"));
		long id = Convert.strToLong(paramMap.get("id"), -1);
		Map<String,String> map = new HashMap<String, String>();
		map.put("publishTime", DateUtil.dateToString(new Date()));
		Admin  admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if("1".equals(auditStatus)){
			map.put("debtStatus", "2");
		}else{
			map.put("debtStatus", "6");
		}
		try {
			assignmentDebtService.updateAssignmentDebt(id,1, map);
			if("1".equals(auditStatus)){
				operationLogService.addOperationLog("t_assignment_debt", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "债权转让审核成功", 2);
			}else{
				operationLogService.addOperationLog("t_assignment_debt", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "债权转让审核失败", 2);
			}
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转到审核页面
	 * @return
	 */
	public String queryApplyDebtAuditDetail(){
		long id = Convert.strToLong(request("id"), -1);
		try {
			paramMap.putAll(assignmentDebtService.getAssignmentDebt(id));
			long borrowId = Convert.strToLong(paramMap.get("borrowId"), -1);
			long userId = Convert.strToLong(paramMap.get("alienatorId"), -1);
			long investId = Convert.strToLong(paramMap.get("investId"), -1);
			List<Map<String,Object>> list = assignmentDebtService.queryDebtBacking(borrowId,userId,investId);
			request().setAttribute("list", list);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询正在转让中的债权初始化
	 * @return
	 */
	public String queryAuctingAssignmentDebtInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询正在转让中的债权
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryAuctingAssignmentDebtInfo(){
		String debtStatus = "2";
		String borrowerName = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowerName"));
		String alienatorName = SqlInfusion.FilteSqlInfusion(paramMap.get("alienatorName"));
		try {
			assignmentDebtService.queryAssignmentDebt(borrowerName,alienatorName,debtStatus,pageBean);
			List<Map<String,Object>> list = pageBean.getPage();
			if(list != null){
				Date nowDate = new Date();
				for(Map<String,Object> map : list){
					Date date = (Date)map.get("remainAuctionTime");
					map.put("remainDays", DateUtil.remainDateToString(nowDate, date));
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	/**
	 * 查询正在转让中的债权初始化
	 * @return
	 */
	public String querySuccessAssignmentDebtInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询转让成功的债权
	 * @return
	 * @throws DataException 
	 */
	public String querySuccessAssignmentDebtInfo() throws DataException{
		String debtStatus = "3";
		String borrowerName = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowerName"));
		String alienatorName = SqlInfusion.FilteSqlInfusion(paramMap.get("alienatorName"));
		try {
			assignmentDebtService.queryAssignmentDebt(borrowerName,alienatorName,debtStatus,pageBean);
			Map<String, String> repaymentMap = assignmentDebtService.queryApplySuccessDebtDetail();
	         request().setAttribute("repaymentMap",repaymentMap);
			//统计当前页应收款
			double debtSumm = 0;
			List<Map<String,Object>> payList = pageBean.getPage();
			if (payList!=null){
				for (Map<String, Object> map : payList) {
					debtSumm = debtSumm +Convert.strToDouble(map.get("debtSum")+"",0);
				}
			}
			DecimalFormat fmt=new DecimalFormat("0.00");
			request().setAttribute("debtSumm", fmt.format(debtSumm));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}
	
	/**
	 * 查询失败的债权初始化
	 * @return
	 */
	public String queryFailDebtInit(){
		return SUCCESS;
	}
	
	/**
	 * 查询失败的债权
	 * @return
	 * @throws DataException 
	 */
	public String queryFailDebtInfo() throws DataException{
		String debtStatus = "4,5,6,7";
		String borrowerName = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowerName"));
		String alienatorName = SqlInfusion.FilteSqlInfusion(paramMap.get("alienatorName"));
		try {
			assignmentDebtService.queryAssignmentDebt(borrowerName,alienatorName,debtStatus,pageBean);
			Map<String, String> repaymentMap = assignmentDebtService.queryApplyFailDebtDetail();
	         request().setAttribute("repaymentMap",repaymentMap);
			//统计当前页应收款
			double debtSumm = 0;
			List<Map<String,Object>> payList = pageBean.getPage();
			if (payList!=null){
				for (Map<String, Object> map : payList) {
					debtSumm = debtSumm +Convert.strToDouble(map.get("debtSum")+"",0);
				}
			}
			DecimalFormat fmt=new DecimalFormat("0.00");
			request().setAttribute("debtSumm", fmt.format(debtSumm));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	/**
	 * 结束竞拍
	 * @return
	 */
	public String debtEndSuccess(){
		long id = Convert.strToLong(request("id"), -1);
		Admin   admin =  (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			assignmentDebtService.auctingDebtSuccess(id,admin.getId(),2);  //区分前后台用户
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
	}
	
	/**
	 * 撤回债权转让
	 * @return
	 */
	public String cancelManageDebt(){
		long id = Convert.strToLong(request("id"), -1);
		Admin  admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			assignmentDebtService.cancelAssignmentDebt(id, 5,admin.getId(),2);  //区分前后台用户
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 债权转让详情
	 * @return
	 */
	public String queryManageDebtDetail(){
		long id = Convert.strToLong(request("id"), -1);
		try {
			Map<String, String>  map = assignmentDebtService.getAssignmentDebt(id);
			if(map==null)
				return SUCCESS;
			paramMap.putAll(map);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	
	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

}