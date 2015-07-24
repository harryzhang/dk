package com.sp2p.action.admin;



import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.UserService;



@SuppressWarnings({ "unchecked", "serial" })
public class BlacklistAction extends BasePageAction{
	
	public static Log log = LogFactory.getLog(BlacklistAction.class);
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String queryBlacklistInit(){
		return SUCCESS;
	}
	
	/**
	 * 获取黑名单列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryBlacklistPage()throws SQLException,DataException{
		String userName=SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		try {
		 userService.queryBlacklistUser(pageBean, userName);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			
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
	 * 删除黑名单用户
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String deleteBlackList()throws SQLException,DataException{
		Long id=Convert.strToLong(request("id"),-1L);
		Integer enable=1;
		@SuppressWarnings("unused")
		Long result=-1L;
		try {
			result= userService.updateEnable(id, enable);
				
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "将用户从黑名单中移除", 2);
			return SUCCESS;
		
	}
	
	/**
	 * 添加黑名单用户
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateBlackList()throws SQLException,DataException{
		Long id=Convert.strToLong(request("id"),-1L);
		Integer enable=3;
		@SuppressWarnings("unused")
		Long result=-1L;
		try {
			result= userService.updateEnable(id, enable);
				
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "将用户添加到黑名单中", 2);
			return SUCCESS;
	}
	

	
}
