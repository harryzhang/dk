package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.NewsAndMediaReportService;

/**
 * 网站公告Action
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class NewsAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(NewsAction.class);
	private List<Map<String,Object>> types;
	private NewsAndMediaReportService newsService;

	public NewsAndMediaReportService getNewsService() {
		return newsService;
	}

	public List<Map<String, Object>> getTypes() {
		return types;
	}

	public void setTypes(List<Map<String, Object>> types) {
		this.types = types;
	}

	public void setNewsService(NewsAndMediaReportService newsService) {
		this.newsService = newsService;
	}

	/**
	 * 初始化分页查询网站公告列表
	 * @return
	 */
	public String queryNewsListInit(){
		return SUCCESS;
	}
	
	/**
	 * 分页查询网站公告列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryNewsListPage()throws SQLException,DataException{
		try {
			newsService.queryNewsPage(pageBean);
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
	 * 添加网站公告信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException 
	 */
	public String addNews()throws SQLException,DataException, ParseException{
		Admin user=(Admin)session().getAttribute("admin");
		Integer sort=Convert.strToInt(paramMap.get("sort"), 1);
		String title=SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		Integer type=Convert.strToInt(paramMap.get("type"),1);
		Long userId=-1L;
		if(user!=null){
			 userId=user.getId();
		}
		String visits=paramMap.get("visits");
		String publishTime=paramMap.get("publishTime");
		@SuppressWarnings("unused")
		String message="添加失败";
        Long result=-1L;
		try {
			result = newsService.addNews(sort, title, content, userId, visits, publishTime ,type);
			if(result>0){
				message="添加成功";
				//清空分页，列表数据
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INDEX);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_WZDT);
				CacheManager.clearStartsWithAll(IConstants.CACHE_WZGG_PAGE_);
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
	 * 添加网站公告信息初始化
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String addNewsInit()throws SQLException,DataException{
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publishTime=format.format(new Date());
		paramMap.put("publishTime",publishTime);
		
		//-----add by houli 给出默认序列号值
		Map<String,String> map = newsService.getMaxSerial();
		if(map == null || map.get("sortId")==null){
			paramMap.put("sort", String.valueOf(1));
		}else{
			int sortId = Convert.strToInt(map.get("sortId"),1);
			paramMap.put("sort", String.valueOf(sortId+1));
		}
		//新添加的公告信息浏览量默认为0
		paramMap.put("visits", String.valueOf(0));
		//-----------
		
		return SUCCESS;
	}
	
	/**
	 * 更新初始化，根据Id获取网站公告信息详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateNewsInit()throws Exception{
	    	Long id=Convert.strToLong(request("id"), 0);
		try {
			 paramMap=newsService.getNewsById(id);
			 this.types = newsService.queryNewsType();
			 
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 预览（在添加或更新中）
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String PreviewNews()throws Exception{

		Admin user=(Admin)session().getAttribute("admin");	
		String userName=null;
		if(user!=null){
			 userName=user.getUserName();
		}
		
		 
		String sort=SqlInfusion.FilteSqlInfusion(paramMap.get("sort"));
		String title = SqlInfusion.FilteSqlInfusion(request().getParameter("title"));
		String content =SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		String visits=SqlInfusion.FilteSqlInfusion(paramMap.get("visits"));
		String publishTime= SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
		title = URLDecoder.decode(title,"UTF-8");
		content = URLDecoder.decode(content,"UTF-8");
		visits  =  URLDecoder.decode(visits,"UTF-8");
		publishTime  =  URLDecoder.decode(publishTime,"UTF-8");
		Map<String,String> map=new HashMap<String, String>();
		map.put("userName", userName);
		map.put("sort", sort);
		map.put("title",title);
		map.put("content", content);
		map.put("visits",visits);
		map.put("pubiishTime", publishTime);
		request().setAttribute("newsPreview", map);
		return SUCCESS;
	}
	
	
	/**
	 * 更新网站公告信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws ParseException 
	 */
	public String updateNews()throws Exception{
		Admin user=(Admin)session().getAttribute("admin");
		Long id=Convert.strToLong(paramMap.get("id"),0);
		Integer sort=Convert.strToInt(paramMap.get("sort"), 1);
		String title=SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String content=SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		Long userId=-1L;
		if(user!=null){
			 userId=user.getId();
		}
		Integer visits=Convert.strToInt(paramMap.get("visits"), -1);
		String publishTime=SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
		@SuppressWarnings("unused")
		String message="更新失败";
		
		try {
			long result =newsService.updateNews(id, sort, title, content, userId, visits, publishTime);
			if (result > 0) {
				message = "更新成功";
				//清空分页，列表数据，当前明细
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INDEX);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_WZDT);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INFO_+id);
				CacheManager.clearStartsWithAll(IConstants.CACHE_WZGG_PAGE_);
				JSONUtils.printStr("1");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	
	/**
	* 删除网站公告数据
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String deleteNews() throws DataException, SQLException{
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
			newsService.deleteNews(dynamicIds, ",");
			//清空分页，列表数据，当前明细
			CacheManager.clearByKey(IConstants.CACHE_WZGG_INDEX);
			CacheManager.clearByKey(IConstants.CACHE_WZGG_WZDT);
			CacheManager.clearStartsWithAll(IConstants.CACHE_WZGG_PAGE_);
			for (String id : newsids) {
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INFO_+id);
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
	
	/**
	 * add by houli 判断所填写的sort是否是唯一的
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String isExistSortId() throws DataException, SQLException, IOException{
		int id = Convert.strToInt(request("sort"), -1);
		try{
			Long result = newsService.isExistSortId(id);
			if(result <= 0){
				JSONUtils.printStr("1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	public String isExistToUpdate() throws DataException, SQLException, IOException{
		int id = Convert.strToInt(paramMap.get("sortId"), -1);
		int originalId = Convert.strToInt(paramMap.get("originalId"), -1);
		try{
			Long result = newsService.isExistToUpdate(id,originalId);
		
			if(result <= 0){
				JSONUtils.printStr("1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
}
