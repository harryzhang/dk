package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.FundManagementService;

@SuppressWarnings({ "unchecked", "serial" })
public class RiskManageAction extends BasePageAction{
	
	public static Log log = LogFactory.getLog(RiskManageAction.class);
	 private FundManagementService fundManagementService;
		public FundManagementService getFundManagementService() {
			return fundManagementService;
		}

		public void setFundManagementService(FundManagementService fundManagementService) {
			this.fundManagementService = fundManagementService;
		}
	/**   
	 * @MethodName: queryRiskInit  
	 * @Param: RechargeAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午10:46:59
	 * @Return:    
	 * @Descb: 风险保证金初始化 
	 * @Throws:
	*/
	public String queryRiskInit(){
		return "success";
	}
	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryRiskList  
	 * @Param: RechargeAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午10:47:35
	 * @Return:    
	 * @Descb: 风险保证金查询列表
	 * @Throws:
	*/
	public String queryRiskList() {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String resource = SqlInfusion.FilteSqlInfusion(paramMap.get("resource") == null ? "" : paramMap
				.get("resource"));
		String timeStart = SqlInfusion.FilteSqlInfusion(paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart"));
		String timeEnd = SqlInfusion.FilteSqlInfusion(paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd"));
		String riskType = SqlInfusion.FilteSqlInfusion(paramMap.get("riskType") == null ? "" : paramMap
				.get("riskType"));
		try {
			fundManagementService.queryRiskByCondition(resource,
					timeStart, timeEnd, riskType,pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}


/**
 * 导出风险保证金查询列表
 * 
 * @return
 */
public String exportRiskList() {
	pageBean.pageNum = 1;
	pageBean.setPageSize(100000);
	try {
		String resource = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("resource")),"");
		resource = URLDecoder.decode(resource,"UTF-8");
		String timeStart = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("timeStart")),null);
		String timeEnd = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("timeEnd")),null);
		String riskType =Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("riskType")),"");
		riskType = URLDecoder.decode(riskType,"UTF-8");
		// 待还款详情
		fundManagementService.queryRiskByCondition(resource,
				timeStart, timeEnd, riskType,pageBean);
		
		if(pageBean.getPage()==null)
		{
			getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
			return  null;
		}
		if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
		{
		getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
		return  null;
		}
		HSSFWorkbook wb = ExcelUtils.exportExcel("风险保障金记录", pageBean
				.getPage(), new String[] { "操作金额", "真实姓名", "类型", "详细来源",
				"操作时间"}, new String[] { "amount",
				"realName", "riskType", "resource", "riskDate"
				});
		this.export(wb, new Date().getTime() + ".xls");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("v_t_risk_list_h", admin.getUserName(),IConstants.EXCEL, admin.getLastIP(), 0, "导出风险保障金记录", 2);
	} catch (SQLException e) {

		e.printStackTrace();
	} catch (DataException e) {

		e.printStackTrace();
	} catch (IOException e) {

		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
	
	
	/**   
	 * @MethodName: queryRiskDetail  
	 * @Param: RechargeAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午11:17:16
	 * @Return:    
	 * @Descb: 查询风险保证金详情
	 * @Throws:
	*/
	public String queryRiskDetail() throws SQLException, DataException{
		String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id")==null?"":request().getParameter("id"));
		long idLong = Convert.strToLong(id, -1L);
		Map<String,String> riskDetailMap =fundManagementService.queryRiskDetailById(idLong);
		request().setAttribute("riskDetailMap", riskDetailMap);
		return "success";
	}
	
	
	/**   
	 * @MethodName: addRiskInit  
	 * @Param: RiskManageAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午02:15:52
	 * @Return:    
	 * @Descb: 手动添加风险保障金初始化
	 * @Throws:
	*/
	public String addRiskInit(){
		return "success";
	}
	
	
	/**   
	 * @throws IOException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: addRisk  
	 * @Param: RiskManageAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午01:35:37
	 * @Return:    
	 * @Descb: 手动添加风险保障金
	 * @Throws:
	*/
	public String addRisk() throws IOException, SQLException, DataException{
		Admin admin = (Admin)session().getAttribute("admin");
		JSONObject obj = new JSONObject();
		String amount = SqlInfusion.FilteSqlInfusion(paramMap.get("amount"));
		double amountDouble = Convert.strToDouble(amount, -1);
		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark"));
		if(StringUtils.isBlank(amount)){
			obj.put("msg","操作金额不能为空");
			JSONUtils.printObject(obj);
			return null;
		}
		if(amountDouble < 1){
			obj.put("msg","操作金额最少为1.00");
			JSONUtils.printObject(obj);
			return null;
		}
		if(remark.length() > 500){
			obj.put("msg","备注内容不能超过500字符");
			JSONUtils.printObject(obj);
			return null;
		}
		long returnid = -1;
		returnid = fundManagementService.addRisk(amountDouble,admin.getId(),remark);
		if(returnid <=0){
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_risk_detail", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), amountDouble, "手动添加风险保障金失败", 2);
			return null;
		}
		obj.put("msg","1");
		JSONUtils.printObject(obj);
		operationLogService.addOperationLog("t_risk_detail", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), amountDouble, "手动添加风险保障金成功", 2);
		return null;
	}
	
	
	/**   
	 * @MethodName: deductedRiskInit  
	 * @Param: RiskManageAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午02:16:27
	 * @Return:    
	 * @Descb: 扣除风险保障金初始化
	 * @Throws:
	*/
	public String deductedRiskInit(){
		return "success";
	}
	/**   
	 * @throws IOException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: deductedRisk  
	 * @Param: RiskManageAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午01:35:51
	 * @Return:    
	 * @Descb: 手动扣除风险保障金
	 * @Throws:
	*/
	public String deductedRisk() throws IOException, SQLException, DataException{
		Admin admin = (Admin)session().getAttribute("admin");
		JSONObject obj = new JSONObject();
		String amount = SqlInfusion.FilteSqlInfusion(paramMap.get("amount"));
		double amountDouble = Convert.strToDouble(amount, -1);
		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark"));
		if(StringUtils.isBlank(amount)){
			obj.put("msg","操作金额不能为空");
			JSONUtils.printObject(obj);
			return null;
		}
		if(amountDouble < 1){
			obj.put("msg","操作金额最少为1.00");
			JSONUtils.printObject(obj);
			return null;
		}
		if(remark.length() > 500){
			obj.put("msg","备注内容不能超过500字符");
			JSONUtils.printObject(obj);
			return null;
		}
		long returnid = -1;
		returnid = fundManagementService.deductedRisk(amountDouble,admin.getId(),remark);
		if(returnid <=0){
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_risk_detail", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), amountDouble, "手动扣除风险保障金失败", 2);
			return null;
		}
		obj.put("msg","1");
		JSONUtils.printObject(obj);
		operationLogService.addOperationLog("t_risk_detail", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), amountDouble, "手动扣除风险保障金策划成功", 2);
		return null;
	}
}
