package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.MailBoxManagerService;

/**
 *站内信管理
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class MailBoxManagerAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(MailBoxManagerAction.class);
	
	private MailBoxManagerService mailBoxManagerService;
	private AdminService adminService;
	private UserService userService;
	private HomeInfoSettingService homeInfoSettingService;

	/**
	 * 初始化管理员与用户列表页面
	 * @return
	 */
	public String queryMailBoxListInit(){		
		return SUCCESS;
	}
	
	/**
	 * 初始化用户与用户列表页面
	 * @return
	 */
	public String queryMailBoxListUserInit(){
		return SUCCESS;
	}
	/**
	 * 分页查询管理员已用户之间的站内信列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMailBoxListPage()throws SQLException,DataException{
		Integer mailType=Convert.strToInt(request("mailType"),2);
		String beginTime=SqlInfusion.FilteSqlInfusion(paramMap.get("beginTime"));
		String endTime=SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		String senders=SqlInfusion.FilteSqlInfusion(paramMap.get("sender"));
		String sender=null;
		if(StringUtils.isNotBlank(senders)&&!senders.equals("\\")){
			Map<String, String> map=userService.queryIdByUser(senders);
			if(map==null){
				map=adminService.queryIdByUser(senders);
			}
			if(map!=null){
				sender=map.get("id");
			}
		}
		try {
			pageBean.setPageSize(5);
			mailBoxManagerService.queryMailByCondition(pageBean, mailType, sender, beginTime, endTime);
			List<Map<String,Object>> lists = pageBean.getPage();
			if(lists != null){
				changeLists2Lists(lists);
				changeLists2Lists2(lists);
			}
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
	 * 分页查询用户与用户之间的站内信列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMailBoxListUserPage()throws SQLException,DataException{
		Integer mailType=Convert.strToInt(request("mailType"),1);
		String beginTime=SqlInfusion.FilteSqlInfusion(paramMap.get("beginTime"));
		String endTime=SqlInfusion.FilteSqlInfusion(paramMap.get("endTime"));
		String senders=SqlInfusion.FilteSqlInfusion(paramMap.get("sender"));
		String sender=null;
		if(StringUtils.isNotBlank(senders)&&!senders.equals("\\")){
			Map<String, String> map=userService.queryIdByUser(senders);
			if(map==null){
				map=adminService.queryIdByUser(senders);
			}
			if(map!=null){
				sender=map.get("id");
			}
		}
		try {
			pageBean.setPageSize(5);
			mailBoxManagerService.queryMailByCondition(pageBean, mailType, sender, beginTime, endTime);
			List<Map<String,Object>> lists = pageBean.getPage();
			if(lists != null){
				changeLists2Lists(lists);
				changeLists2Lists2(lists);
			}
		
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
	//发送站内信初始化
	public String sendMailInit(){
		request().setAttribute("sender",session().getAttribute("admin"));
		return SUCCESS;
		
	}
	
	/**
	 * 更改lists里面的一些信息。这样前台直接显示。
	 * 将用户id改成用户名，信息状态更改中文显示
	 * @throws SQLException 
	 * @throws DataException 
	 */
	private void changeLists2Lists(List<Map<String,Object>> lists) throws DataException, SQLException{
		
		try {
			Map<String,String> mp = null;
			int status = -1;
			for(Map<String,Object> map : lists){	
				String username = "";
					mp = adminService.queryAdminById(Convert.strToLong(map.get("sender")+"",-1));
					if(mp != null && mp.size() >0){
						username = Convert.strToStr(mp.get("userName"), "");
						map.put("sender", username);
					}
				    if(username.equals("")){
				    	mp = userService.queryUserById(Convert.strToLong(map.get("sender")+"",-1));
						if(mp != null && mp.size() >0){
							username = Convert.strToStr(mp.get("username"), "");
							map.put("sender", username);
							
						}
				    }
				status = Convert.strToInt( map.get("mailStatus")+"",-1);
				if(status == IConstants.MAIL_READED){
					map.put("mailStatus", "已读");
				}else if(status == IConstants.MAIL_UN_READ){
					map.put("mailStatus", "未读");
				}
			
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void changeLists2Lists2(List<Map<String,Object>> lists) throws DataException, SQLException{
		String username = "";
		try {
			
		
		for(Map<String,Object> map : lists){
			username = this.getUserNameById(Convert.strToLong(map.get("reciver")+"",-1));
			if(username.equals("")){
				username = this.getAdminNameById(Convert.strToLong(map.get("reciver")+"",-1));
			}
			map.put("reciver", username);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据用户id获得用户名
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	private String getUserNameById(long userId) throws DataException, SQLException{
		Map<String,String> mp = userService.queryUserById(userId);
		if(mp != null){
			return Convert.strToStr(mp.get("username"), "");
		}
		return "";
	}
	
	private String getAdminNameById(long adminId)throws DataException, SQLException{
		Map<String,String> mp = adminService.queryAdminById(adminId);
		if(mp != null){
			return Convert.strToStr(mp.get("userName"), "");
		}
		return "";
	}
	
	/**
	 * 添加黑名单初始化
	 * @return
	 */
	public String addBlackListInit(){
		return SUCCESS;
	}
	
	/**
	 * 添加黑名单
	 * @return
	 */
	public String addBlackList()throws SQLException,DataException,IOException{
		String username=SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		Map<String,String> map = userService.queryIdByUser(username);
		if(map==null){
			JSONUtils.printStr("1");
			return null;
			
		}else{
			Long userId=Convert.strToLong(map.get("id"), -1L);
			userService.updateEnable(userId, 3);
		}
		
		return null;
		
	}
	
	//加入黑名单
	public String joinBlackList()throws SQLException,DataException{
		Long id=Convert.strToLong(request("id"), -1L);
		
		try {
			paramMap=mailBoxManagerService.getMailById(id);
			Long serder=Convert.strToLong(paramMap.get("sender"), -1L);
			userService.updateEnable(serder, 3);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 添加邮件
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public String addMail() throws IOException, DataException, SQLException{
		String receiver = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("receiver")),null);
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")),null);
		String content = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("content")),null);
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId")); // 
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code").toString().trim());
		try {
			if (code == null || !_code.equals(code)) {
				JSONUtils.printStr(IConstants.USER_REGISTER_CODE_ERROR);
				return null;
			}
			Admin user = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			Long id = user.getId();//获得用户编号
			//前台页面进行了判断，这里名称不可能为空
			Map<String,String> map = userService.queryIdByUser(receiver);
			Long receiverId = -2L;
			if(map == null || map.size() < 0){//到t_admin表中查数据
				List<Map<String,Object>> lists = adminService.queryAdminList(receiver, 1);
				if(lists.size()>0){
					receiverId = Convert.strToLong(lists.get(0).get("id").toString(), -1L);
				}
				
			}else{
				receiverId = Convert.strToLong(map.get("id"), -1L);
			}
			
			//检查用户名是否存在 t_user
			long results = userService.isExistEmailORUserName(null, receiver);
			if (results < 0) { // 用户名不存在
				//到t_admin表中检查用户名
				List<Map<String,Object>>  lists = adminService.queryAdminList(receiver, 1);
				if(lists == null || lists.size() <= 0){
					JSONUtils.printStr("8");
					return null;
				}
			}
			
			long result = -1;
			/**
			 * 如果是发给admin，系统管理员，则该邮件为系统邮件(如果发件人或者收件人为admin,则为系统消息)
			 */
			
				result = homeInfoSettingService.addMail(id,receiverId, 
						title, content, IConstants.MAIL_UN_READ,2, IConstants.MALL_TYPE_SYS);
			
			if(result  < 0){
				JSONUtils.printStr("1");
				return null;
			}else{
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	
	
	/**
	 * 更新初始化，根据Id获取站内信详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateMailBoxInit()throws SQLException,DataException{
	    	Long id=Convert.strToLong(request("id"), -1L);
		try {
			 paramMap=mailBoxManagerService.getMailById(id);
			 //把发件人Id换成发件人名称
			 Integer userId=Convert.strToInt(paramMap.get("sender"),-1);
			 Map<String, String> map=userService.queryUserById(userId);
			 if(map!=null){
				 paramMap.put("sender",map.get("username"));
				
			 }else{
				 map=adminService.queryAdminById(userId);
				 if(map!=null){
					 paramMap.put("sender", map.get("userName"));
				 }
			 }
			 
			 //把收件人ID换成收人人名
			 Integer reciverId=Convert.strToInt(paramMap.get("reciver"),-1);
			 Map<String, String> maps=userService.queryUserById(reciverId);
			 if(maps!=null){
				 paramMap.put("reciver",maps.get("username"));
				
			 }else{
				 maps=adminService.queryAdminById(reciverId);
				 if(maps!=null){
					 paramMap.put("reciver", maps.get("userName"));
				 }
			 }
				 
			 
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 更新站内信息信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateMailBox()throws SQLException,DataException,IOException{
		Long id=Convert.strToLong(paramMap.get("id"), -1L);
		String title=SqlInfusion.FilteSqlInfusion(paramMap.get("mailTitle"));		
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("mailContent"));
		
		@SuppressWarnings("unused")
		String message="更新失败";
		try {
			long result = mailBoxManagerService.updateMailBoxById(id, title, content);
			if (result > 0) {
				JSONUtils.printStr("1");
				return null;
			}else{
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
	
	
	/**
	* 删除站内信管理员与用户数据
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String deleteMailBox() throws DataException, SQLException{
		String dynamicIds = SqlInfusion.FilteSqlInfusion(request("id"));
		
		String[] newsids = dynamicIds.split(",");
		if (newsids.length > 0) {
			long tempId = 0;
			for (String str : newsids) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		
		try {
			mailBoxManagerService.deleteMailBox(dynamicIds, ",");
			
			
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	

	/**
	* 删除站内信用户与用户数据
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String deleteMailBoxUser() throws DataException, SQLException{
		String dynamicIds = SqlInfusion.FilteSqlInfusion(request("id"));
		
		String[] newsids = dynamicIds.split(",");
		if (newsids.length > 0) {
			long tempId = 0;
			for (String str : newsids) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		
		try {
			mailBoxManagerService.deleteMailBox(dynamicIds, ",");
			
			
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}


	public MailBoxManagerService getMailBoxManagerService() {
		return mailBoxManagerService;
	}

	public void setMailBoxManagerService(MailBoxManagerService mailBoxManagerService) {
		this.mailBoxManagerService = mailBoxManagerService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}
}
