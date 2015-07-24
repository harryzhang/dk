package com.sp2p.action.admin;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.service.admin.ArticleManageService;

public class ArticleManageAction  extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log =LogFactory.getLog(ArticleManageAction.class);
	private List<Map<String,Object>> rightsList;
	
	private ArticleManageService articleManageService;

	/**
	 * 查询初始化
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryArticleInit() throws SQLException, DataException {
		return SUCCESS;
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String queryActicleList(){
		String title   = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")),"");
		int  parentID = Convert.strToInt(paramMap.get("parentID"), -1);
		String userName = Convert.strToStr(paramMap.get("usernane"),"");
		try {
			articleManageService.queryArticlePage(userName, title, parentID, pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 增加初始化
	 * @return
	 */
	public String addArticleInit() {
		return SUCCESS;
	}
	/**
	 * 增加
	 * @return
	 */
	public  String addArticle(){
		return SUCCESS;
	}
	
	public List<Map<String, Object>> getRightsList() throws SQLException, DataException {
		if(rightsList!=null){
			return rightsList;
		}
		rightsList = articleManageService.qureyAllList(0);
		return rightsList;
	}

	public void setRightsList(List<Map<String, Object>> rightsList) {
		this.rightsList = rightsList;
	}

	public void setArticleManageService(ArticleManageService articleManageService) {
		this.articleManageService = articleManageService;
	}
	
}
