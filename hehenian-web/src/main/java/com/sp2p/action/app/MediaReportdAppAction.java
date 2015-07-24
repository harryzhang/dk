package com.sp2p.action.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.NewsAndMediaReportService;


/**
 * 前台媒体报道
 * @author Administrator
 *
 */
public class MediaReportdAppAction extends BaseAppAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(MediaReportdAppAction.class);
//	private MediaReportService mediaReportService;
	private NewsAndMediaReportService newsAndMediaReportService;

	


//	public MediaReportService getMediaReportService() {
//		return mediaReportService;
//	}
//
//	public void setMediaReportService(MediaReportService mediaReportService) {
//		this.mediaReportService = mediaReportService;
//	}

	public NewsAndMediaReportService getNewsAndMediaReportService() {
		return newsAndMediaReportService;
	}

	public void setNewsAndMediaReportService(
			NewsAndMediaReportService newsAndMediaReportService) {
		this.newsAndMediaReportService = newsAndMediaReportService;
	}

	/**
	 * 初始化下载数据
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQueryMediaReportdInit()throws SQLException,DataException{
		return SUCCESS;
	}
	
	/**
	 * 查询媒体报道列表
	 * @return String
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String frontQueryMediaReportdList() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String pageNum = (String) (infoMap.get("curPage") == null ? ""
					: infoMap.get("curPage"));
			if (StringUtils.isNotBlank(pageNum)) {
				pageBean.setPageNum(pageNum);
			}
			pageBean.setPageSize(IConstants.PAGE_SIZE_10);
	//		mediaReportService.queryMediaReportPage(pageBean);
			newsAndMediaReportService.queryMediaReportPage(pageBean);
			List<Map<String,Object>> lists = pageBean.getPage();
			
			//截取内容字段 houli  
			String content = "";
			if(lists != null){
				for(Map<String,Object> map : lists){
					if(map.get("content").toString().length() > 50){
						content = map.get("content").toString().substring(0, 50);
						map.put("content", content+"...");
					}
					
				}
			}
			jsonMap.put("lists", lists);
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
	 * 根据Id获取媒体报道详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQueryMediaReportById()throws IOException, SQLException,DataException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Long id=Convert.strToLong(infoMap.get("id"),-1);
			Map<String, String> map = null;
		//	map=mediaReportService.getMediaReportById(id);
			map=newsAndMediaReportService.getMediaReportById(id);
			jsonMap.put("map", map);
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
	 * 根据Id获取媒体报道
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontMedialinkId()throws IOException, SQLException,DataException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Long id=Convert.strToLong(infoMap.get("id"),-1);
	//		paramMap=mediaReportService.getMediaReportById(id);
			paramMap=newsAndMediaReportService.getMediaReportById(id); 
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

	





}
