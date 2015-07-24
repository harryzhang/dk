package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.SEOConfigService;

public class SEOConfigAction extends BasePageAction {
	public static Log log = LogFactory.getLog(SEOConfigAction.class);
	
	private SEOConfigService SEOConfigService;

	public SEOConfigService getSEOConfigService() {
		return SEOConfigService;
	}

	public void setSEOConfigService(SEOConfigService configService) {
		SEOConfigService = configService;
	}
	
	/**
	 * 更新SEO标准设置 
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String updateSEOConfig() throws SQLException, DataException, IOException{
		JSONObject obj = new JSONObject();
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String keywords = SqlInfusion.FilteSqlInfusion(paramMap.get("keywords"));
		String description = SqlInfusion.FilteSqlInfusion(paramMap.get("description"));
		String otherTags = SqlInfusion.FilteSqlInfusion(paramMap.get("otherTags"));
		Long result = -1L;
		result = SEOConfigService.updateSEOConfig( title, description, keywords,1,otherTags);
		if(result > 0){
			IConstants.SEO_TITLE = title;
			IConstants.SEO_KEYWORDS = keywords;
			IConstants.SEO_DESCRIPTION = description;
			IConstants.SEO_SITEMAP = 1;
			IConstants.SEO_OTHERTAGS = otherTags;
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
		}
		else{
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_seoconfig", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新ＳＥＯ标准设置", 2);
		return SUCCESS;
	}
	
	/**
	 * 查看SEO标准设置 
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String querySEOConfig() throws SQLException, DataException{
		Map<String, String> seoMap = null;
		seoMap = SEOConfigService.querySEOConfig();
		request().setAttribute("seoMap", seoMap);
 		return SUCCESS;
	}
}
