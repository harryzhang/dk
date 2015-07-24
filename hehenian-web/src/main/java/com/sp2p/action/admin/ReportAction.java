package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.ReportService;

/**
 * 网站公告Action
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class ReportAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(ReportAction.class);
	
	private ReportService reportService;
	private UserService userService;
	private UserIntegralService userIntegralService;

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 初始化分页查询举报列表
	 * @return
	 */
	public String queryReportListInit(){
		return SUCCESS;
	}
	
	/**
	 * 分页查询举报列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryReportListPage()throws SQLException,DataException{
		String users =SqlInfusion.FilteSqlInfusion(paramMap.get("user"));
		String reporters=SqlInfusion.FilteSqlInfusion(paramMap.get("reporter"));
		Integer status=Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("status")),-1);
		Integer user=-1;
		Integer reporter=-1;
		try {
			if(StringUtils.isNotBlank(users)&&!users.equals("")&&!users.equals("\\")){
				Map<String, String> mm=userService.queryIdByUser(users);
				if(mm!=null){
					user=Convert.strToInt(mm.get("id"), -1);
				}else{
					user=0;
				}
			}
			if(StringUtils.isNotBlank(reporters)&&!reporters.equals("")&&!reporters.equals("\\")){
				Map<String, String> mm=userService.queryIdByUser(reporters);
				if(mm!=null){
					reporter=Convert.strToInt(mm.get("id"), -1);
				}else{
					reporter=0;
				}
			}
			
			reportService.queryReportPage(pageBean, user, reporter, status);
			List<Map<String, Object>> list=pageBean.getPage();
			Map<String, String> mp=null;
			Map<String, String> maps=null;
			if(list!=null){
				for(Map<String, Object> map:list){
					   mp=userService.queryUserById(Convert.strToLong(map.get("user")+"", -1L));
					   maps=userService.queryUserById(Convert.strToLong(map.get("reporter")+"", -1L));
					   if(mp!=null){
						   map.put("user", mp.get("username"));
					   }
					   if(maps!=null){
						   map.put("reporter", maps.get("username"));
					   }
				}
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
	 * 根据用户id获得用户名
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private String getUserNameById(long userId) throws DataException, SQLException{
		Map<String,String> mp = userService.queryUserById(userId);
		if(mp != null){
			return Convert.strToStr(mp.get("username"), "");
		}
		return "";
	}
	
	/**
	 * 更新初始化，根据Id获取举报信息详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateReportInit()throws SQLException,DataException{
	    	Long id=Convert.strToLong(request("id"), -1L);
		try {
			 paramMap=reportService.getReportById(id);
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
	 * 更新网站公告信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateReport()throws SQLException,DataException,IOException{
	    //add by lw 
		Long userid=Convert.strToLong(paramMap.get("user"),-1L);//举报人id
		//...............
		Long id=Convert.strToLong(paramMap.get("id"),-1L);
		Integer status=Convert.strToInt(paramMap.get("status"), 1);
		String remark=paramMap.get("remark");
		@SuppressWarnings("unused")
		String message="更新失败";
		
		try {
			long result =reportService.updateReport(id, remark, status);
			if (result > 0) {
				//添加用户举报积分  add by liuwei
				if(status==2){
					   int score = 10;
					   Map<String,String> Usermap = null;
					   Integer preScore = null;
					   Usermap = userService.queryUserById(userid);
					   preScore = Convert.strToInt(Usermap.get("rating"),-1);//查找用户的之前的信用积分
					   //添加举报所得积分
					   userIntegralService.UpdateJubaoRating(userid, score, preScore);//如果审核成功
					   Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
						operationLogService.addOperationLog("t_user/t_userintegraldetail", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "添加用户举报积分明细", 2);
						
				}
				//................
				
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
	* 删除网站公告数据
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String deleteReport() throws DataException, SQLException{
		Long id =Convert.strToLong(request("id"), -1L); ;		
		@SuppressWarnings("unused")
		Long result=-1L;
		try {
			result=reportService.deleteReport(id);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_report", admin.getUserName(),IConstants.DELETE, admin.getLastIP(), 0, "删除举报信息", 2);
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
}
