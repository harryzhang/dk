package com.sp2p.action.admin;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.service.NewsAndMediaReportService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;

/**
 * 媒体报道管理
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class MediaReportAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(MediaReportAction.class);
	
	private NewsAndMediaReportService mediaReportService;

	/**
	 * 初始化分页查询媒体报道列表
	 * @return
	 */
	public String queryMediaReportListInit(){
		return SUCCESS;
	}
	
	/**
	 * 分页查询媒体报道列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMediaReportListPage()throws SQLException,DataException{
		try {
			mediaReportService.queryMediaReportPage(pageBean,3);
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
	 * 添加团队信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException 
	 */
	public String addMedaiReport()throws SQLException,DataException, ParseException{
		String title=SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String url=SqlInfusion.FilteSqlInfusion(paramMap.get("url"));
		String source=SqlInfusion.FilteSqlInfusion(paramMap.get("source"));
		String imgPath=SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		String publishTime=SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
		Integer sort=Convert.strToInt(paramMap.get("sort"),-1);
		int stick = Convert.strToInt(paramMap.get("stick"),1);
		int state = Convert.strToInt(paramMap.get("state"),1);
		@SuppressWarnings("unused")
		String message="添加失败";
        Long result=-1L;
		try {
			result=mediaReportService.addMediaReport(sort, title, source, url, imgPath, content, publishTime,state,stick);
			if(result>0){
				message="添加成功";
				//清除分页信息
				CacheManager.clearStartsWithAll(IConstants.CACHE_MTBD_PAGE_);
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
	 * 添加媒体报道信息初始化
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String addMediaReportInit()throws SQLException,DataException{
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publishTime=format.format(new Date());
		paramMap.put("publishTime",publishTime);
		
		return SUCCESS;
	}
	
	/**
	 * 更新初始化，根据Id获取团队信息详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateMediaReportInit()throws SQLException,DataException{
	    	Long id=Convert.strToLong(request("id"), 0);
		try {
			 paramMap=mediaReportService.getMediaReportById(id);
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
	 * 更新媒体报道信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException 
	 */
	public String updateMediaReport()throws SQLException,DataException, ParseException{
		Long id=Convert.strToLong(paramMap.get("id"), -1L);
		String title=SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String url=SqlInfusion.FilteSqlInfusion(paramMap.get("url"));
		String source=SqlInfusion.FilteSqlInfusion(paramMap.get("source"));
		String imgPath=SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath"));
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		String publishTime=SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
		Integer sort=Convert.strToInt(paramMap.get("sort"),-1);
		int stick = Convert.strToInt(request("stick"), -1);
		int state = Convert.strToInt(request("state"), -1);
		@SuppressWarnings("unused")
		String message="更新失败";
		
		try {
			long result = mediaReportService.updateMediaReport(id, sort, title, source, url, 
					                 imgPath, content, publishTime,state,stick);
			if (result > 0) {
				message = "更新成功";
				//清除分页信息
				CacheManager.clearStartsWithAll(IConstants.CACHE_MTBD_PAGE_);
				CacheManager.clearByKey(IConstants.CACHE_MTBD_INFO_+id);
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
	 * 修改置顶状态
	 * @return
	 */
	public String updateMediaStick(){
		int id =Convert.strToInt( request("id"),-1);
		int stick = Convert.strToInt(request("stick"), -1);
		long result = -1L;
		try {
			result  = mediaReportService.updateReportStick(id, stick);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		if(result > 0 ){
			return SUCCESS;
		}else{
			return INPUT;
		}
	}
	
	/**
	* 删除媒体报道数据
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String deleteMediaReprot() throws DataException, SQLException{
		String dynamicIds = request("id");
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
			mediaReportService.deleteMediaReport(dynamicIds, ",");
			//清除分页信息
			CacheManager.clearStartsWithAll(IConstants.CACHE_MTBD_PAGE_);
			for (String id : newsids) {
				CacheManager.clearByKey(IConstants.CACHE_MTBD_INFO_+id);
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

	public String queryIndexRollImg() throws SQLException, DataException{
		List<Map<String, Object>> imgList = mediaReportService.queryIndexRollImg();
	    request().setAttribute("imgList", imgList);
		return SUCCESS;
	}
	public NewsAndMediaReportService getMediaReportService() {
		return mediaReportService;
	}

	public void setMediaReportService(NewsAndMediaReportService mediaReportService) {
		this.mediaReportService = mediaReportService;
	}
	

}
