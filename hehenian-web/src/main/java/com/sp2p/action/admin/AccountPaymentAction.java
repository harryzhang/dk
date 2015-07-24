package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.shove.Convert;
import com.shove.config.GopayConfig;
import com.shove.config.IPayConfig;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.FundManagementService;

public class AccountPaymentAction  extends BasePageAction{
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(AfterCreditManageAction.class);
	/*private AccountPaymentService  accountPaymentService;*/
	 private FundManagementService fundManagementService;
		public FundManagementService getFundManagementService() {
			return fundManagementService;
		}

		public void setFundManagementService(FundManagementService fundManagementService) {
			this.fundManagementService = fundManagementService;
		}
	
	/**
	 *  查询初始化
	 * @return
	 */
	public String queryAccountPayInit() {

		return 	SUCCESS;
	}
	/**
	 *  查询
	 * @return
	 */
	public String queryAccountPayInfo() {
		try {
			fundManagementService.queryAccountPaymentPage(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	/**
	 *  修改初始化
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateAccountPayInit() {
		 String nid = Convert.strToStr(request("nid"), "");
		try {
			paramMap =fundManagementService.queryAccountPaymentById(nid);
			String config = paramMap.get("config");
			if(StringUtils.isNotBlank(config)){
				Map<String,String> map = (Map<String,String>)JSONObject.toBean(JSONObject.fromObject(config),HashMap.class);
				if(map != null){
					paramMap.putAll(map);
				}
			}
			
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return 	SUCCESS;
	}
	/**
	 *  修改
	 * @return
	 * @throws SQLException 
	 */
	public String updateAccountPay() throws SQLException {
		try {
			Map<String,String> map = new HashMap<String, String>();
			String  name = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("name")),"");
			String  litpic =Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("litpic")),"");
			int  orders = Convert.strToInt(paramMap.get("orders"),-1);;
			String  description =Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMap.description"))+"","");
			long id = Convert.strToLong(paramMap.get("id"),-1L);
			String merchantID  = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("merchantID")),"");
			String virCardNoIn  = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("virCardNoIn")),"");
			String VerficationCode  =Convert.strToStr( SqlInfusion.FilteSqlInfusion(paramMap.get("VerficationCode")),"");
			String privatekey  = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("privatekey")),"");
			String customerID  = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("customerID")),"");
			String nid = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("nid")), "");
			if("IPS".equals(nid)){
				map.put("customerID", customerID);
				map.put("privatekey", privatekey);
			}
			if("gopay".equals(nid)){
				map.put("virCardNoIn", virCardNoIn);
				map.put("VerficationCode", VerficationCode);
				map.put("merchantID", merchantID);
			}
			String json = JSONObject.fromObject(map).toString();
			long result = 	fundManagementService.updateAccountPaymentPage(id, name,  litpic,  json, description, orders);
			if(result > 0 ){
				if("IPS".equals(nid)){
					IPayConfig.ipay_mer_code = customerID;
					IPayConfig.ipay_certificate = privatekey;
				}
				if ("gopay".equals(nid)) {
					GopayConfig.gopay_virCardNoIn = virCardNoIn;
					GopayConfig.gopay_verficationCode =  VerficationCode;
					GopayConfig.gopay_merchantID = merchantID;
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_account_payment", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改支付类型", 2);
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 * @throws SQLException 
	 */
	public String delteAccountPay() throws SQLException{
		long id = Convert.strToLong(request("id"), -1);
		long status = Convert.strToLong(request("status"),1);
		long result = -1L;
		try {
			result = fundManagementService.deleteAccountPaymentPage(id, status);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		if(result >  0 )
		{
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_account_payment", admin.getUserName(),IConstants.DELETE, admin.getLastIP(), 0, "删除id为"+id+"的支付类型", 2);
			return  SUCCESS;
		}
		else 
			return INPUT;
	}
	
}
