package com.sp2p.action.front;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.sp2p.service.PublicModelService;


/**
 * 前台成功故事
 * @author Administrator
 *
 */
public class FrontSuccessStorydAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontSuccessStorydAction.class);
	private PublicModelService successStoryService;
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
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String frontQuerySuccessStoryList() throws SQLException, DataException{
	
		List<Map<String,Object>> newsList = new ArrayList<Map<String,Object>>();
		pageBean.setPageSize(2);
	    successStoryService.querySuccessStoryPage(pageBean);
		newsList = pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("storyList", newsList);
			return SUCCESS;
	}
	
	/**
	 * 分页显示成功故事列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQuerySuccessStoryListPage()throws SQLException,DataException{
		try {
			successStoryService.querySuccessStoryPage(pageBean);
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
	 * 根据Id获取网站公告详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQuerySuccessStoryById()throws SQLException,DataException{
		Long id=Convert.strToLong(request("id"),-1);
		try {
			//上一条
			previousDate=successStoryService.getSuccessStoryById(id-1);
			//下一条
			lastDate=successStoryService.getSuccessStoryById(id+1);
			paramMap=successStoryService.getSuccessStoryById(id);
			
			//浏览次数增加
			successStoryService.updateSuccessStory(id, null, null, null, null, null,
					Convert.strToLong(paramMap.get("browseNum"),-1)+1, null);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
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

	public PublicModelService getSuccessStoryService() {
		return successStoryService;
	}

	public void setSuccessStoryService(PublicModelService successStoryService) {
		this.successStoryService = successStoryService;
	}
}
