package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.service.admin.RechargebankService;
/**
 * 线下充值银行编辑
 * @author Administrator
 *
 */
public class RechargebankAction extends BaseFrontAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(RechargebankAction.class);
	private RechargebankService rechargebankService;
	
	public String querybankeditInit(){
		return SUCCESS;
	}
	
	public String querybankeditInfo() throws SQLException{
		rechargebankService.queryRechargebanklist(pageBean);
		return SUCCESS;
	}
	
	public String updateRechargeBankInit() throws SQLException{
		long id = Convert.strToLong(request("id")+"", -1);
		paramMap = rechargebankService.queryrechargeBankById(id);
		return SUCCESS;
	}
	
	
	public String queryrechargeBank() throws SQLException, IOException{
		Map<String,String> bankMap = new HashMap<String, String>();
		long id = Convert.strToLong(paramMap.get("id")+"", -1);
		bankMap = rechargebankService.queryrechargeBankById(id);
		JSONUtils.printObject(bankMap);
		return null;
	}
	
	/**
	 * 添加充值银行
	 * @return
	 * @throws SQLException
	 */
	public String addRechargeBankInit() throws SQLException{
		return SUCCESS;
	}
	/**
	 * 添加充值银行
	 * @return
	 * @throws SQLException
	 */
	public String addRechargeBankInfo() throws SQLException{
		String bankname = SqlInfusion.FilteSqlInfusion(paramMap.get("bankname")+"");
		String Account = SqlInfusion.FilteSqlInfusion(paramMap.get("Account")+"");
		String accountbank = SqlInfusion.FilteSqlInfusion(paramMap.get("accountbank")+"");
		String bankimage = SqlInfusion.FilteSqlInfusion(paramMap.get("bankimage")+"");
		String accountname = SqlInfusion.FilteSqlInfusion(paramMap.get("accountname")+"");
	    rechargebankService.addRechargeBankInit(bankname, Account, accountbank, bankimage,accountname);
	    return SUCCESS;
	}
	
	
	
	public String updateRechargeBankInfo() throws SQLException{
		long id = Convert.strToLong(paramMap.get("id"), -1);
		String bankname = SqlInfusion.FilteSqlInfusion(paramMap.get("bankname")+"");
		String Account = SqlInfusion.FilteSqlInfusion(paramMap.get("Account")+"");
		String accountbank = SqlInfusion.FilteSqlInfusion(paramMap.get("accountbank")+"");
		String bankimage = SqlInfusion.FilteSqlInfusion(paramMap.get("bankimage")+"");
		String accountname = SqlInfusion.FilteSqlInfusion(paramMap.get("accountname")+"");
		rechargebankService.updaterechargeBankById(id, bankname, Account, accountbank, bankimage,accountname);
		return SUCCESS;
	}
	
	public void setRechargebankService(RechargebankService rechargebankService) {
		this.rechargebankService = rechargebankService;
	}

	public RechargebankService getRechargebankService() {
		return rechargebankService;
	}
	
}
