package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.service.AwardMonthService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
/**
 * 团队长月结
 * @author Administrator
 *
 */
public class AwardMonthAction extends BaseFrontAction{
	public static Log log=LogFactory.getLog(AwardMonthAction.class);
	private AwardMonthService awardMonthService;
	private String[] monthStr = new String[]{"","一","二","三","四","五","六","七","八","九","十","十一","十二"};
	private List<Map<String,Object>> monthList;

	public void setAwardMonthService(AwardMonthService awardMonthService) {
		this.awardMonthService = awardMonthService;
	}
	/**
	 * 团队长按月查看月流量 并能结算初始化
	 */
	public String quereyGroupCloseMoneyInit(){
		return SUCCESS;
	}
	/**
	 * 团队长按月查看月流量 并能结算
	 * @throws Exception 
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public String quereyGroupCloseMoneyInfo() throws Exception {
		String level1userName = SqlInfusion.FilteSqlInfusion(paramMap.get("level1userName"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		String startTime = SqlInfusion.FilteSqlInfusion(paramMap.get("startTime"));
		String endTime = SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		try {
			awardMonthService.quereyGroupCloseMoneyInfo(pageBean, startTime,endTime, 1, level1userName,realName);
			//得到总计
			paramMap = awardMonthService.quereyGroupCloseMoneySum(startTime, endTime, 1, level1userName, realName);
			//得到当前页合计
			List<Map<String,Object>> list = pageBean.getPage();
			double countMoney = 0;
			if (list!=null) {
				for (Map<String, Object> map : list) {
					countMoney = countMoney + Convert.strToDouble(map.get("moneys")+"", 0);
				}
			}
			request().setAttribute("countMoney", countMoney);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
	}
	/**
	 * 经济人按月查看月流量 并能结算初始化
	 */
	public String quereyAgentCloseMoneyInit(){
		return SUCCESS;
	}
	/**
	 * 经济人按月查看月流量 并能结算
	 * @throws Exception 
	 */
	public String quereyAgentCloseMoneyInfo() throws Exception{
		String level1userName = SqlInfusion.FilteSqlInfusion(paramMap.get("level1userName"));
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		String startTime = SqlInfusion.FilteSqlInfusion(paramMap.get("startTime"));
		String endTime = SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		awardMonthService.quereyGroupCloseMoneyInfo(pageBean, startTime,endTime, 2, level1userName,realName);
		//得到总计
		try {
			paramMap = awardMonthService.quereyGroupCloseMoneySum(startTime, endTime, 2, level1userName, realName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		//得到当前页合计
		List<Map<String,Object>> list = pageBean.getPage();
		double countMoney = 0;
		if (list!=null) {
			for (Map<String, Object> map : list) {
				countMoney = countMoney + Convert.strToDouble(map.get("moneys")+"", 0);
			}
		}
		request().setAttribute("countMoney", countMoney);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	
	public String updateMoneyInfo() throws SQLException, IOException{
		long id = Convert.strToLong(paramMap.get("id")+"",-1);
		//int applystatus = Convert.strToInt(paramMap.get("applystatus")+"",-1);
		long result =  awardMonthService.updateMoneyInfo(id, 2);
		Map<String,String> msgMap = new HashMap<String,String>();
		if(result<0){
			msgMap.put("msg", "更新失败");
			JSONUtils.printObject(msgMap);
		return null;
		}
		msgMap.put("msg", "1");
		JSONUtils.printObject(msgMap);
		return null;
	}
	
	public List<Map<String, Object>> getMonthList() {
		if(monthList==null){
			monthList = new ArrayList<Map<String,Object>>();
			for(int i = 0;i<=12;i++){
				Map<String,Object> map = new HashMap<String, Object>();
				if(i==0){
					map.put("id", -1);
					map.put("name", "--请选择--");
				}else{
					map.put("id", i);
					map.put("name", "第"+monthStr[i]+"个月");
				}
				monthList.add(map);
			}
		}
		return monthList;
	}

	public String[] getMonthStr() {
		return monthStr;
	}
}
