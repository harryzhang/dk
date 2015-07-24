package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.DownloadService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;


/**
 * 
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class DownloadAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(DownloadAction.class);
	
	private DownloadService downloadService;
	
	
	public DownloadService getDownloadService() {
		return downloadService;
	}

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	/**
	 * 初始化添加下载资料
	 * @return
	 */
	public String addDownloadInit(){
		paramMap.put("visits", "0");
		paramMap.put("state", "1");
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=format.format(new Date());
		
		paramMap.put("publishTime", date);
		paramMap.put("seqNum", "1");
		
		
        
		return SUCCESS;
	}
	
	/**
	* 增加资料下载数据初始化
	* @throws SQLException
	* @throws DataException
	* @return String
	 * @throws DataException 
	 * @throws ParseException 
	* @throws
	*/
	public String addDownload() throws SQLException, DataException, ParseException{
		Admin user=(Admin)session().getAttribute(IConstants.SESSION_ADMIN);
	  
		String publishTime=SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title")); //标题
	    
		int visits = Convert.strToInt(paramMap.get("visits"),0); //浏览次数
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content")); //详细内容
		String attachment = SqlInfusion.FilteSqlInfusion(paramMap.get("attachment")); //详细内容
		
		Integer state=Convert.strToInt(paramMap.get("state"), 1);
		Integer seqNum=Convert.strToInt(paramMap.get("seqNum"), 1);
		Long userId=0L;
		if(user!=null){
			 userId = user.getId();
		}
		
		@SuppressWarnings("unused")
		String messageInfo = "添加失败";
		
		try {
			
		
			long result = downloadService.addDownload(title, content, publishTime, userId, visits, state, seqNum, attachment);
			if (result > 0) {
				messageInfo = "添加成功";
				//清除列表
				CacheManager.clearStartsWithAll(IConstants.CACHE_XZZQ_PAGE_);
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
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_download", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "增加资料下载数据", 2);
		return SUCCESS;
	}
	
	
	/**
	* 分页查询资料下载数据初始化
	* @return String
	* @throws
	*/
	public String queryDownloadListPageInit(){
		return SUCCESS;
	}
	
	/**
	* 分页查询资料下载数据
	* @throws SQLException
	* @throws DataException
	* @return String
	* @throws
	*/
	public String queryDownloadListPage() throws SQLException, DataException{
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		
		try {
			downloadService.queryDownloadPage(pageBean, title);
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
	 * 根据Id获取下载资料详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String queryDownloadById()throws SQLException,DataException,IOException{
		Long id=Convert.strToLong(request("id"),-1L);
	
		try {
			paramMap=downloadService.queryDownloadById(id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 更新下载资料
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException 
	 */
	public String updateDownload()throws Exception{
		String publishTime=SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));			
		int visits = Convert.strToInt(paramMap.get("visits"),0); //浏览次数
		Admin user=(Admin)session().getAttribute("admin");
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title")); //标题
	    Long id=Convert.strToLong(paramMap.get("id"),0);
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content")); //详细内容
		String attachment = SqlInfusion.FilteSqlInfusion(paramMap.get("attachment")); //附件路径
		Integer state=Convert.strToInt(paramMap.get("state"), 1);
		Integer seqNum=Convert.strToInt(paramMap.get("seqNum"), 1);
		Long userId=0L;
		if(user!=null){
			 userId = user.getId();
		}
		
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			long result = downloadService.updateDownload(id, title, content, userId, visits, state, seqNum, publishTime, attachment);
			if (result > 0) {
				//清除列表
				CacheManager.clearStartsWithAll(IConstants.CACHE_XZZQ_PAGE_);
				CacheManager.clearByKey(IConstants.CACHE_XZZQ_INFO_+id);
				operationLogService.addOperationLog("t_download", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新下载资料信息成功", 2);
				JSONUtils.printStr("1");
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("2");
			throw e;
		}
		return null;
	}
	/**
	 * 删除下载资料信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String deleteDownload()throws SQLException,DataException{
		
		Long id=Convert.strToLong(request("id"),-1L);
		@SuppressWarnings("unused")
		String message="删除失败";
		try {
			Long result=downloadService.deleteDownload(id);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if(result>0){
				message="删除成功";
				//清除列表
				CacheManager.clearStartsWithAll(IConstants.CACHE_XZZQ_PAGE_);
				CacheManager.clearByKey(IConstants.CACHE_XZZQ_INFO_+id);
				operationLogService.addOperationLog("t_download", admin.getUserName(),IConstants.DELETE, admin.getLastIP(), 0, "删除下载资料信息成功", 2);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
		
		
	}
	
	

}
