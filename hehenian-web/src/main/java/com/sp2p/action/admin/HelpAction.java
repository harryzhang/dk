package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.HelpAndServicerService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;

/**
 * 客服中心帮助问题处理
 * @author li.hou
 *
 */
@SuppressWarnings("unchecked")
public class HelpAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(HelpAction.class);
	
	private HelpAndServicerService callCenterService;
	
	private List<Map<String,Object>> types; //帮助类型
	private Long typeId = null;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	* 添加合和年在线帮助中心数据初始化
	* @return String
	 * @throws DataException 
	 * @throws SQLException 
	* @throws
	*/
	public String addHelpInit() throws SQLException, DataException {
		//获得序列号
		paramMap.put("serialCount", "0");
		paramMap.put("publisher", "admin");//默认为admin客户
		return SUCCESS;
	}
	
	/**
	* 添加合和年在线帮助问题数据
	* @return String
	* @throws SQLException 
	 * @throws DataException 
	 * @throws NumberFormatException 
	*/
	public String addHelp() throws SQLException, NumberFormatException, DataException {
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")),null); //标题
		long helpId = Convert.strToLong(paramMap.get("typeId"), -1);
		Long serialCount = Convert.strToLong(paramMap.get("serialCount"), -1); //序号
		if(serialCount<0){
			this.addFieldError("paramMap['serialCount']", "序号格式不正确");
			return INPUT;
		}
		String content = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("content")), null); //详细内容
		String publisher = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("publisher")), null); //Convert.strToLong(paramMap.get("adminId"), -1); //发布人
		String dateTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime")), null);
//		
		@SuppressWarnings("unused")
		String messageInfo = "添加失败";
		try {
			long result = callCenterService.addHelp(title, helpId, serialCount, content, publisher, dateTime);
			if (result > 0) {
				messageInfo = "添加成功";
				//删除此分类下的分页数据
				CacheManager.clearStartsWithAll(IConstants.CACHE_BZZX_PAGE_+helpId+"_");
				Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_help_question", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "添加桂林市合和年信贷帮助问题数据", 2);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	
	/**
	* 修改初始化
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String updateHelpInit() throws DataException, SQLException {
		long typeId = Convert.strToLong(request("commonId"),-1L);
		try {
			paramMap = callCenterService.queryHelpViewByid(typeId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		request().setAttribute("commonId", typeId);
		return SUCCESS;
	}
	
	/**
	* 修改
	* @throws SQLException
	* @return String
	 * @throws DataException 
	*/
	public String updateHelp() throws SQLException, DataException {
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("question")),null); //标题
		
		long id = Convert.strToLong(paramMap.get("questionId"),-1L); //序号不能改，问题Id
		long typeId = Long.valueOf(paramMap.get("typeId")); //帮助类型id
		String publisher = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("publisher")),null); //发布人
		String publishTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime")),null); //发布时间
		Long serialCount = Convert.strToLong(paramMap.get("serialCount"), -1L); //序号
		if(serialCount<0){
			this.addFieldError("paramMap['serialCount']", "序号格式不正确");
			return INPUT;
		}
		@SuppressWarnings("unused")
		String messageInfo = "修改失败";
		try {
			long result = callCenterService.updateHelp(title,typeId,id,publisher,publishTime,serialCount);
			if (result > 0) {
				messageInfo = "修改成功";
				//删除此分类下的分页数据,明细
				CacheManager.clearStartsWithAll(IConstants.CACHE_BZZX_PAGE_+typeId+"_");
				CacheManager.clearByKey(IConstants.CACHE_BZZX_INFO_+id);
				Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_help_question", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "修改桂林市合和年信贷帮助问题数据成功", 2);
			
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 更新帮助问题列表。更新帮助问题到t_help_question  更新内容到 t_help_question
	 * @throws DataException 
	 */
	public String updateHelpManager () throws  Exception {
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("question")),null); //标题
	    if(title == null){
	    	paramMap.put("question", "标题不能为空");
	    	return null;
	    }
		long id = Convert.strToLong(paramMap.get("questionId"),-1L); //序号不能改，问题Id
		long typeId = Convert.strToLong(paramMap.get("typeId"),-1); //帮助类型id
		String content = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("anwer")), null);
		String publisher = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("publisher")),null); //发布人
		String publishTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime")),null); //发布时间
		Long serialCount = Convert.strToLong(paramMap.get("sortId"), -1L); //序号
		if(serialCount<0){
			this.addFieldError("paramMap['sortId']", "序号格式不正确");
			JSONUtils.printStr("2");
			return null;
		}
		@SuppressWarnings("unused")
		String messageInfo = "修改失败";
		try {
			long result = callCenterService.updateHelpManager(title,content,typeId,id,publisher,publishTime,serialCount);
			if (result > 0) {
				CacheManager.clearStartsWithAll(IConstants.CACHE_BZZX_PAGE_+typeId+"_");
				CacheManager.clearByKey(IConstants.CACHE_BZZX_INFO_+id);
				Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_help_question", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新帮助问题到t_help_question成功", 2);
			
			}
			JSONUtils.printStr("1");
		} catch (Exception e) {
			log.error(e);
			JSONUtils.printStr("2");
			e.printStackTrace();
			throw e;
		}
		setTypeId(typeId);
		request().setAttribute("typeId", typeId);
		return null;
	}
	
	public String queryHelpListPageInit() throws DataException, SQLException {
		return SUCCESS;
	}
	
	/**
	* 分页查询 
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String queryHelpListPage() throws DataException, SQLException {
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")), null);
		long helpId = Convert.strToLong(paramMap.get("typeId"), -1);
		if(helpId == -1){//从管理类型列表请求，获得对应的帮助问题类型id
			helpId = Convert.strToLong(request("typeId"), -1);
		}
		try {
			callCenterService.queryHelpPage(pageBean, title, helpId);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

		} catch (DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	* 删除
	* @throws SQLException
	* @throws DataException
	* @return String
	*/
	public String deleteHelp() throws SQLException, DataException {
		String helpIds = SqlInfusion.FilteSqlInfusion(request("commonId"));
		String[] allIds = helpIds.split(",");//进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		Map<String,String> map = callCenterService.getTypeId(Convert.strToLong(allIds[0],-1));
		typeId = Convert.strToLong(map.get("typeId"),1L);
 		callCenterService.deleteQuestions(helpIds);
 		//删除此分类下的分页数据,明细
		CacheManager.clearStartsWithAll(IConstants.CACHE_BZZX_PAGE_);
		for (String id : allIds) {
			CacheManager.clearByKey(IConstants.CACHE_BZZX_INFO_+id);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_help_question", admin.getUserName(),IConstants.DELETE, admin.getLastIP(), 0, "删除问题", 2);
		return SUCCESS;
	}
	
	public String deleteHelpManager() throws SQLException, DataException {
		String helpIds = request("typeId");//这里只是单条信息的删除
		String[] allIds = helpIds.split(",");
		Long typeId = -1L;
		Map<String,String> map = null;
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
				//获得所属的问题类型id
				map = callCenterService.getTypeId(tempId);
			}
		} else {
			return INPUT;
		}
		typeId = Convert.strToLong(map.get("typeId"),1L);
		setTypeId(typeId);
 		callCenterService.deleteQuestionInfo(helpIds);
 		//删除此分类下的分页数据,明细
		CacheManager.clearStartsWithAll(IConstants.CACHE_BZZX_PAGE_+typeId);
		for (String id : allIds) {
			CacheManager.clearByKey(IConstants.CACHE_BZZX_INFO_+id);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_help_question", admin.getUserName(),IConstants.DELETE, admin.getLastIP(), 0, "根据问题id删除问题信息及其引用该问题信息的答案", 2);
		return SUCCESS;
	}
	

	public List<Map<String,Object>> getTypes() throws SQLException, DataException{
		if(types == null)
			types = callCenterService.queryHelpTypeList();
		return types;
	}

	public HelpAndServicerService getCallCenterService() {
		return callCenterService;
	}

	public void setCallCenterService(HelpAndServicerService callCenterService) {
		this.callCenterService = callCenterService;
	}

	public void setTypes(List<Map<String,Object>> types) {
		this.types = types;
	}
}
