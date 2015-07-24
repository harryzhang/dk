package com.sp2p.action.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.PublicModelService;


/**
 * 前台友情链接
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class LinksAppAction extends BaseAppAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(LinksAppAction.class);
//	private LinksService linksService;
//	public LinksService getLinksService() {
//		return linksService;
//	}
//	public void setLinksService(LinksService linksService) {
//		this.linksService = linksService;
//	}
	private PublicModelService publicModelService;
	
	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

	/**
	 * 查询下载资料列表
	 * @return String
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 * @throws UnsupportedEncodingException
	 */
	public String frontQueryMediaReportdList() throws SQLException, DataException, IOException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String pageNum = (String) (infoMap.get("curPage") == null ? ""
					: infoMap.get("curPage"));
			if (StringUtils.isNotBlank(pageNum)) {
				pageBean.setPageNum(pageNum);
			}
			if (IConstants.ISDEMO.equals("1")) {
				pageBean.setPageSize(100);
			}else{
				pageBean.setPageSize(IConstants.PAGE_SIZE_20);
			}
		//	linksService.queryLinksPage(pageBean);
			publicModelService.queryLinksPage(pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (IOException e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 根据Id获取下载资料详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQueryMediaReportById()throws IOException, SQLException,DataException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Long id=Convert.strToLong(infoMap.get("id"),-1);
		//	Map<String, String> map=linksService.queryLinksInfoByid(id);
			Map<String, String> map=publicModelService.queryLinksInfoByid(id);
			jsonMap.put("map", map);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (IOException e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}
	
	



}
