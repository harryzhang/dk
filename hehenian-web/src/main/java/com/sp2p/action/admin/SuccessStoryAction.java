package com.sp2p.action.admin;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.PublicModelService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;

/**
 * 网站公告Action
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class SuccessStoryAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(SuccessStoryAction.class);
	private PublicModelService successStoryService;
	
	public PublicModelService getSuccessStoryService() {
		return successStoryService;
	}

	public void setSuccessStoryService(PublicModelService successStoryService) {
		this.successStoryService = successStoryService;
	}

	/**
	 * 初始化分页查询成功故事列表
	 * @return
	 */
	public String querySuccessStoryListInit(){
		return SUCCESS;
	}
	
	/**
	 * 分页查询成功故事列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String querySuccessStoryListPage()throws SQLException,DataException{
		try {
			successStoryService.querySuccessStoryPage(pageBean);
			int pageNums = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNums);
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
	 * 添加网站公告信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException 
	 */
	public String addSuccessStory()throws SQLException,DataException, ParseException{
		String publisher=SqlInfusion.FilteSqlInfusion(paramMap.get("publisher"));
		String sort=SqlInfusion.FilteSqlInfusion(paramMap.get("sort"));
		String title=SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		
	
		String browseNum=SqlInfusion.FilteSqlInfusion(paramMap.get("browseNum"));
		String publishTime=SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
		String imgPath=SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
		@SuppressWarnings("unused")
		String message="添加失败";
        Long result=-1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			result=successStoryService.addSuccessStory(sort, title, content, publishTime, publisher, browseNum, imgPath);
			if(result>0){
				message="添加成功";
				//添加数据，需要清空分页数据，和当前明细
				CacheManager.clearStartsWithAll(IConstants.CACHE_CGGS_PAGE_);
				CacheManager.clearByKey(IConstants.CACHE_CGGS_INDEX); 
				operationLogService.addOperationLog("t_successstory", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "添加成功的故事信息成功", 2);
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
	 * 添加成功故事信息初始化
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String addSuccessStoryInit()throws SQLException,DataException{
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publishTime=format.format(new Date());
		paramMap.put("publishTime",publishTime);
		
		return SUCCESS;
	}
	
	/**
	 * 更新初始化，根据Id获取成功故事信息详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateSuccessStoryInit()throws SQLException,DataException{
	    	Long id=Convert.strToLong(request("id"), 0);
		try {
			 paramMap=successStoryService.getSuccessStoryById(id);
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
	 * 更新成功故事信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException 
	 */
	public String updateSuccessStory()throws SQLException,DataException, ParseException{
		
		Long id=Convert.strToLong(paramMap.get("id"),0);
		String publisher=SqlInfusion.FilteSqlInfusion(paramMap.get("publisher"));
		String sort=SqlInfusion.FilteSqlInfusion(paramMap.get("sort"));
		String title=SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		
	
		Long browseNum=Convert.strToLong(paramMap.get("browseNum"),-1L);
		String publishTime=SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
		String imgPath=SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
		
		@SuppressWarnings("unused")
		String message="更新失败";
		
		try {
			
			
			long result =successStoryService.updateSuccessStory(id, sort, title, content, publishTime, publisher, browseNum, imgPath);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if (result > 0) {
				message = "更新成功";
				operationLogService.addOperationLog("t_successstory", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新成功的故事数据成功", 2);
				//修改一条数据，需要清空分页数据，和当前明细
				CacheManager.clearStartsWithAll(IConstants.CACHE_CGGS_PAGE_);
				CacheManager.clearByKey(IConstants.CACHE_CGGS_INDEX); 
				CacheManager.clearByKey(IConstants.CACHE_CGGS_INFO_+id) ;
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
		
		return SUCCESS;
		
	}
	
	
	/**
	* 删除成功故事数据
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String deleteSuccessStory() throws DataException, SQLException{
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
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			successStoryService.deleteSuccessStory(dynamicIds, ",");
			operationLogService.addOperationLog("t_successstory", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "删除成功的故事数据成功", 2);
			//删除数据，需要清空分页数据，和当前明细
			CacheManager.clearStartsWithAll(IConstants.CACHE_CGGS_PAGE_);
			CacheManager.clearByKey(IConstants.CACHE_CGGS_INDEX); 
			for (String id : newsids) {
				CacheManager.clearByKey(IConstants.CACHE_CGGS_INFO_+id) ;
			}
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
