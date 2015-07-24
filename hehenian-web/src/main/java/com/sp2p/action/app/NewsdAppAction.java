package com.sp2p.action.app;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.sp2p.service.NewsAndMediaReportService;


/**
 * 前台下载专区
 * @author Administrator
 *
 */
public class NewsdAppAction extends BaseAppAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(NewsdAppAction.class);
//	private NewsService newsService;
	private NewsAndMediaReportService newsAndMediaReportService;
	private Map<String, String> previousDate;  //上一条
	private Map<String,String> lastDate;  //下一条

	
	public String initNews()throws SQLException,DataException{
		return SUCCESS;
	}
	
	/**
	 * 查询网站公告列表(每次显示五条)
	 * @return String
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String frontQueryNewsList() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Integer id=Convert.strToInt(infoMap.get("id"), -1);
			List<Map<String,Object>> newsList = new ArrayList<Map<String,Object>>();
			pageBean.setPageSize(5);
	//		newsService.frontQueryNewsPage(pageBean);
			newsAndMediaReportService.frontQueryNewsPage(pageBean);
			newsList = pageBean.getPage();
//			request().setAttribute("newsList", newsList);
			jsonMap.put("newsList", newsList);
			if(id==2){
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "公告查询成功");
				JSONUtils.printObject(jsonMap);
//				return "ganggao";
			}else{
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "查询成功");
				JSONUtils.printObject(jsonMap);
//				return SUCCESS;
			}
		}catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 分页显示网站公告列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 */
	public String frontQueryNewsListPage()throws SQLException,DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
	//		newsService.queryNewsPage(pageBean);
			newsAndMediaReportService.queryNewsPage(pageBean);
			/*String key = IConstants.CACHE_WZGG_PAGE_+pageBean.getPageNum();
			Cache cache = CacheManager.getCacheInfo(key);
			if(cache==null){
				cache = new Cache();
				newsService.queryNewsPage(pageBean);
				cache.setValue(pageBean);
				CacheManager.putCache(key, cache);
			}else{
				pageBean = (PageBean) cache.getValue();
			}*/
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 根据Id获取网站公告详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 */
	public String frontQueryNewsById()throws SQLException,DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Long id=Convert.strToLong(appInfoMap.get("id"),-1);
			//上一条
		//	previousDate=newsService.getNewsById(id-1);
			previousDate=newsAndMediaReportService.getNewsById(id-1);
			//下一条
		//	lastDate=newsService.getNewsById(id+1);
			lastDate=newsAndMediaReportService.getNewsById(id+1);
		//	paramMap=newsService.getNewsById(id);
			paramMap=newsAndMediaReportService.getNewsById(id);
			//浏览次数增加
	 //		newsService.updateNews(id, null, null, null, null,
	//				Convert.strToInt(paramMap.get("visits"),-1)+1, null);
			newsAndMediaReportService.updateNews(id, null, null, null, null,
					Convert.strToInt(paramMap.get("visits"),-1)+1, null);
			
			//添加缓存后，浏览次数会改变
			paramMap.put ("visits", Convert.strToLong(paramMap.get("visits"),-1)+1+"");
			jsonMap.put("paramMap", paramMap);
			jsonMap.put("lastDate", lastDate);
			jsonMap.put("previousDate", previousDate);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		}catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	

	

//	public NewsService getNewsService() {
//		return newsService;
//	}
//
//	public void setNewsService(NewsService newsService) {
//		this.newsService = newsService;
//	}

	public NewsAndMediaReportService getNewsAndMediaReportService() {
		return newsAndMediaReportService;
	}

	public void setNewsAndMediaReportService(
			NewsAndMediaReportService newsAndMediaReportService) {
		this.newsAndMediaReportService = newsAndMediaReportService;
	}

	public Map<String, String> getPreviousDate() {
		return previousDate;
	}

	public void setPreviousDate(Map<String, String> previousDate) {
		this.previousDate = previousDate;
	}

	public Map<String, String> getLastDate() {
		return lastDate;
	}

	public void setLastDate(Map<String, String> lastDate) {
		this.lastDate = lastDate;
	}




	
}
