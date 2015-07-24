package com.sp2p.action.admin;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.service.RechargeService;
/**
 * 后台线下充值
 * @author Administrator
 *
 */
public class RechargeAdminAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(RechargeAdminAction.class);
	private static final long serialVersionUID = 1L;
    private RechargeService	rechargeService;
    
    public String queryxxRechargeInit(){
    	return SUCCESS;
    }
    public String queryxxRechargeInfo() throws SQLException, DataException{
    	String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
    	String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
    	
    	rechargeService.queryRechareDetil(pageBean,userName,realName);
    	return SUCCESS;
    }
    
    public String  queryrechargeAdminInit() throws SQLException, DataException{
    	long userid = Convert.strToLong(request("id"), -1);
    	paramMap = rechargeService.queryupdateRechargeDetailById(userid);
    	request().setAttribute("awardmoney", Convert.strToDouble(paramMap.get("award"), 0)*Convert.strToDouble(paramMap.get("rechargeMoney"),0)*0.01);
		
    	return SUCCESS;
    }
    public String  queryrechargeAdminInfo(){
    	return SUCCESS;
    }
    
    
	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}
}
