package com.sp2p.action.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.util.ExcelUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IPersonListsConstants;
import com.sp2p.dao.BeVipDao;
import com.sp2p.dao.RegionDao;
import com.sp2p.entity.Admin;
import com.sp2p.service.BeVipService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.UnactivatedService;
import com.sp2p.util.DateUtil;
/**
 * 后台管理用户 - 未激活用户
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class UnactivatedAction extends BaseFrontAction{
	public static Log log = LogFactory.getLog(UnactivatedAction.class);
	private UnactivatedService unactivatedService;
	private UserService  userService;
	private List<Map<String,Object>> detailList ; 
	
	/**
	 * 查询未激活用户
	 * @return
	 */
	public String unactivatedindex(){
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String unactivatedinfo() throws SQLException, Exception{
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String createtimeStart = SqlInfusion.FilteSqlInfusion(paramMap.get("createtimeStart"));
		String createtimeEnd = SqlInfusion.FilteSqlInfusion(paramMap.get("createtimeEnd"));
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
		unactivatedService.queryUserUnactivated(pageBean,userName,createtimeStart,createtimeEnd,email);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}
	/**
	 *  查询用户信息 
	 * */
	public String unactivetedUserDetail() throws Exception{
		String id = SqlInfusion.FilteSqlInfusion(request("id"));
		try {
			detailList = unactivatedService.queryUserUnactivatedDetailById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 激活账户
	 * @return
	 */
	public String updateUserActivate(){
		long userId =Convert.strToLong(request("userId"),-1L);
		Map<String,String> userMap = new  HashMap<String, String>();
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			long result = userService.updateUserActivate(userId);
			if(result > 0 ){
				userMap = userService.queryUserById(userId);
				operationLogService.addOperationLog("t_user", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "管理员激活用户:"+Convert.strToStr(userMap.get("username"), ""), 2);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public UnactivatedService getUnactivatedService() {
		return unactivatedService;
	}

	public void setUnactivatedService(UnactivatedService unactivatedService) {
		this.unactivatedService = unactivatedService;
	}
	public List<Map<String, Object>> getDetailList() {
		return detailList;
	}

}
