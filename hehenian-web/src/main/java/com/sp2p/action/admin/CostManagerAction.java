package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.CostManagerService;


/**
 * 
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class CostManagerAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(CostManagerAction.class);
	
	private CostManagerService costManagerService;
	
	


	public CostManagerService getCostManagerService() {
		return costManagerService;
	}
	
	public void setCostManagerService(CostManagerService costManagerService) {
		this.costManagerService = costManagerService;
	}


	

	public String CostManagerInit()throws SQLException,DataException{
		paramMap=costManagerService.getCostManagerByType(1);
		return SUCCESS;
	}

	
	/**
	 * 根据type获取奖励详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryCostManagerByType()throws SQLException,DataException,IOException{
		Integer typeId=Convert.strToInt(request("type"),-1);
	
		try {
			paramMap=costManagerService.getCostManagerByType(typeId);
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}
		if(typeId==1){
			//手续费
			return "poundage";
		}
		if(typeId==2){
			//投资信息管理费
			return "manager";
		}
		if(typeId==3){
			//普通用户好友奖励
			return "friendAward";
		}
		if(typeId==4){
			//VIP会费设置
			return "vipCost";
		}
		if(typeId==5){
			//推荐好友奖励设置
			return "recommend";
		}
		return SUCCESS;
		
	}
	
	/**
	 * 更新奖励
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateCostManager()throws SQLException,DataException,IOException{
			Integer type=Convert.strToInt(request("typeId"),-1);
			Double number=Convert.strToDouble(request("number"), -1);
			
		    
		try {
			
		
			long result=costManagerService.updateCostManager(type, number);
			if (result > 0) {
				
				JSONUtils.printStr("1");
				Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_cost_manager", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新费用设置", 2);
				return null;
			}
			else{
				JSONUtils.printStr("2");
				return null;
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
	}

	

}
