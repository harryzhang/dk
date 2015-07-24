package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.SMSInterfaceService;

/**
 * 短信接口
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class SMSAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(SMSAction.class);
	
	private SMSInterfaceService sMSInterfaceService;
	private List<Map<String, Object>> listSMS;
	
	public SMSInterfaceService getSMSInterfaceService() {
		return sMSInterfaceService;
	}

	public void setSMSInterfaceService(SMSInterfaceService interfaceService) {
		sMSInterfaceService = interfaceService;
	}

	public List<Map<String, Object>> getListSMS() {
		return listSMS;
	}

	public void setListSMS(List<Map<String, Object>> listSMS) {
		this.listSMS = listSMS;
	}

	/**
	 * 查询短信接口列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String findSMSList()throws SQLException,DataException{
		try {
			listSMS=sMSInterfaceService.findBySMS();
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 更新初始化，根据Id获取团队信息详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateSMSInit()throws SQLException,DataException{
	    	Integer id=Convert.strToInt(request("id"), 0);
		try {
			 paramMap=sMSInterfaceService.getSMSById(id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return findSMSList();
	}
	
	/**
	 * 更新团队信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateSMS()throws SQLException,DataException{
		Integer id=Convert.strToInt(paramMap.get("id"), -1);
		String UserID=SqlInfusion.FilteSqlInfusion(paramMap.get("UserID"));
		String Account=SqlInfusion.FilteSqlInfusion(paramMap.get("Account"));
		String Password=SqlInfusion.FilteSqlInfusion(paramMap.get("Password"));
		String status1=SqlInfusion.FilteSqlInfusion(paramMap.get("status_1"));
		String status2=SqlInfusion.FilteSqlInfusion(paramMap.get("status_2"));
		String status=null;
		if(status1!=null){
			status=status1;
		}else{
			status=status2;
		}
		String url=paramMap.get("url");
		String message="更新失败";
		
		try {
			
			
			long result =sMSInterfaceService.updateSMS(id, UserID, Account, Password, status, url);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if (result > 0) {
				operationLogService.addOperationLog("t_sms", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新短信接口信息成功", 2);
				message = "更新成功";
			}
			request().setAttribute("message", message);
		
		
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return findSMSList();
		
	}
}
