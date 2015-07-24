package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.CapitalEnsureService;
import com.sp2p.service.FinanceService;
/**
 * 本金保障
 * @author Administrator
 *
 */
public class CapitalEnsureAction extends BaseFrontAction {
	
	private CapitalEnsureService capitalEnsureService;
	private FinanceService financeService;
	
	public String capitalEnsureInit() throws SQLException, DataException{
		Map<String, String> totalRiskMap = financeService.queryTotalRisk();
		Map<String, String> currentRiskMap = financeService.queryCurrentRisk();
		request().setAttribute("totalRiskMap", totalRiskMap);
		request().setAttribute("currentRiskMap", currentRiskMap);
		return SUCCESS;
	}
	
	public String queryMessages() throws Exception{
		String flag=Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("flag")), "1");
		List<Map<String,Object>> map=capitalEnsureService.queryMessages(flag);
		request().setAttribute("map", map);
		return SUCCESS;
	}
	
	public CapitalEnsureService getCapitalEnsureService() {
		return capitalEnsureService;
	}

	public void setCapitalEnsureService(CapitalEnsureService capitalEnsureService) {
		this.capitalEnsureService = capitalEnsureService;
	}


	public FinanceService getFinanceService() {
		return financeService;
	}


	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}
	
}
