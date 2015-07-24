package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * SEO标准配置
 * 
 * @author Administrator
 * 
 */
public class SEOConfigDao {
	
	/**
	 * 更新SEO配置信息
	 * 
	 * @param conn
	 * @param title
	 * @param description
	 * @param keywords
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long updateSEOConfig(Connection conn,int siteMap, String otherTags,String title,String description,String keywords) 
			throws SQLException, DataException{
		Dao.Tables.t_seoconfig seo = new Dao().new Tables().new t_seoconfig();		
		if(querySEOConfig(conn) == null){
			return addSEOConfig(conn,siteMap, otherTags, title, description, keywords);
		}
		else{
			if (StringUtils.isNotBlank(title)) {
				seo.title.setValue(title);
			}
			else if(title.equals("")){
				seo.title.setValue("");
			}
			if (StringUtils.isNotBlank(description)) {
				seo.description.setValue(description);
			}
			else if(description.equals("")){
				seo.description.setValue("");
			}
			if (StringUtils.isNotBlank(keywords)) {
				seo.keywords.setValue(keywords);
			}
			else if(keywords.equals("")){
				seo.keywords.setValue("");
			}
			if (StringUtils.isNotBlank(otherTags)) {
				seo.otherTags.setValue(otherTags);
			}
			else if(otherTags.equals("")){
				seo.otherTags.setValue("");
			}
			seo.siteMap.setValue(siteMap);
			return seo.update(conn,"");
		}
	}
	
	/**
	 * 查看SEO配置信息
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  querySEOConfig(Connection conn) 
			throws SQLException, DataException{
		Dao.Tables.t_seoconfig seo = new Dao().new Tables().new t_seoconfig();
		DataSet dataSet = seo.open(conn, "*", "",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);	
	}
	
	/**
	 * 添加SEO配置信息
	 * 
	 * @param conn
	 * @param title
	 * @param description
	 * @param keywords
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addSEOConfig(Connection conn,int siteMap, String otherTags,String title,String description,String keywords) 
			throws SQLException, DataException{
		Dao.Tables.t_seoconfig seo = new Dao().new Tables().new t_seoconfig();
		if (StringUtils.isNotBlank(title)) {
			seo.title.setValue(title);
		}
		if (StringUtils.isNotBlank(description)) {
			seo.description.setValue(description);
		}
		if (StringUtils.isNotBlank(keywords)) {
			seo.keywords.setValue(keywords);
		}		
		if (StringUtils.isNotBlank(otherTags)) {
			seo.otherTags.setValue(otherTags);
		}
		seo.siteMap.setValue(siteMap);
		return seo.insert(conn);
	}
}
