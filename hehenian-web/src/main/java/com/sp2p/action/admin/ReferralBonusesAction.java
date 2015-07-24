package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.ReferralBonusesService;


/**
 * 
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class ReferralBonusesAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(ReferralBonusesAction.class);
	
	private ReferralBonusesService referralBonusesService;
	private List<Map<String, Object>> referralList;
	
	public ReferralBonusesService getReferralBonusesService() {
		return referralBonusesService;
	}

	public void setReferralBonusesService(
			ReferralBonusesService referralBonusesService) {
		this.referralBonusesService = referralBonusesService;
	}
	
	public String queryReferralBonusesList()throws SQLException,DataException,IOException{
		try {
			referralList=referralBonusesService.queryReferralBonusersList();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateReferralBonuses()throws SQLException,DataException,IOException{
		Integer typeId=Convert.strToInt(paramMap.get("type"), -1);
		String general=SqlInfusion.FilteSqlInfusion(paramMap.get("general"));
		String fieldVisit=SqlInfusion.FilteSqlInfusion(paramMap.get("fieldVisit"));
		String organization=SqlInfusion.FilteSqlInfusion(paramMap.get("organization"));
		String netWorth=SqlInfusion.FilteSqlInfusion(paramMap.get("netWorth"));
		try {
			Long result=referralBonusesService.updateMReferralBonuses(typeId, general, fieldVisit, organization, netWorth);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if(result>0){
				operationLogService.addOperationLog("t_referral_bonuses", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新好友奖励成功", 2);
				JSONUtils.printStr("1");
			}else{
				operationLogService.addOperationLog("t_referral_bonuses", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新好友奖励失败", 2);
				JSONUtils.printStr("2");
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}


	public List<Map<String, Object>> getReferralList() {
		return referralList;
	}


	public void setReferralList(List<Map<String, Object>> referralList) {
		this.referralList = referralList;
	}

}
