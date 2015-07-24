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
import com.shove.web.util.JSONUtils;
import com.sp2p.service.PublicModelService;


/**
 * 前台成功故事
 * @author Administrator
 *
 */
public class SuccessStorydAppAction extends BaseAppAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(SuccessStorydAppAction.class);
//	private SuccessStoryService successStoryService;
	private PublicModelService publicModelService;
	private Map<String, String> previousDate;  //上一条
	private Map<String,String> lastDate;  //下一条
	
	public String initSuccessStory()throws SQLException,DataException{
		return SUCCESS;
	}
	
	/**
	 * 查询网站成功故事列表(每次显示二条)
	 * @return String
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String frontQuerySuccessStoryList() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> newsList = new ArrayList<Map<String,Object>>();
			pageBean.setPageSize(2);
		//    successStoryService.querySuccessStoryPage(pageBean);
		    publicModelService.querySuccessStoryPage(pageBean);
			newsList = pageBean.getPage();
			pageBean.setPage(null);
//			request().setAttribute("storyList", newsList);
			jsonMap.put("newsList", newsList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("msg", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	
	/**
	 * 分页显示成功故事列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 */
	public String frontQuerySuccessStoryListPage()throws SQLException,DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
		//	successStoryService.querySuccessStoryPage(pageBean);
			publicModelService.querySuccessStoryPage(pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("msg", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	
	/**
	 * 根据Id获取成功故事详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 */
	public String frontQuerySuccessStoryById()throws SQLException,DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Long id=Convert.strToLong(infoMap.get("id"),-1);
			//上一条
	//		previousDate=successStoryService.getSuccessStoryById(id-1);
			previousDate=publicModelService.getSuccessStoryById(id-1);
			//下一条
	//		lastDate=successStoryService.getSuccessStoryById(id+1);
			lastDate=publicModelService.getSuccessStoryById(id+1);
	//		paramMap=successStoryService.getSuccessStoryById(id);
			paramMap=publicModelService.getSuccessStoryById(id);
			//浏览次数增加
	//		successStoryService.updateSuccessStory(id, null, null, null, null, null,
	//				Convert.strToLong(paramMap.get("browseNum"),-1)+1, null);
			publicModelService.updateSuccessStory(id, null, null, null, null, null,
							Convert.strToLong(paramMap.get("browseNum"),-1)+1, null);
			jsonMap.put("previousDate", previousDate);
			jsonMap.put("lastDate", lastDate);
			jsonMap.put("paramMap", paramMap);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("msg", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}
	
	/*
	*//**
	 * 根据网站公告查询公告明细
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *//*
	private Map<String,String> cacheStoryInfo(Long id) throws SQLException, DataException{
		String key = IConstants.CACHE_CGGS_INFO_+id;
		Cache cache = CacheManager.getCacheInfo(key);
		Map<String,String> map = new HashMap<String, String>();
		if(cache==null){
			cache = new Cache();
			map = successStoryService.getSuccessStoryById(id);
			cache.setValue(map);
			CacheManager.putCache(key, cache);
		}else{
			map = (Map<String, String>) cache.getValue();
		}
		return map;
	}
	*/
	


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

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

//	public SuccessStoryService getSuccessStoryService() {
//		return successStoryService;
//	}
//
//	public void setSuccessStoryService(SuccessStoryService successStoryService) {
//		this.successStoryService = successStoryService;
//	}




	
}
