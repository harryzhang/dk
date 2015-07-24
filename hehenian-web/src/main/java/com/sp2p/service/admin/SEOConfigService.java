package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.admin.SEOConfigDao;

public class SEOConfigService extends BaseService {
	public static Log log =LogFactory.getLog(SendSMSService.class);
	
	private SEOConfigDao SEOConfigDao;

	public SEOConfigDao getSEOConfigDao() {
		return SEOConfigDao;
	}

	public void setSEOConfigDao(SEOConfigDao configDao) {
		SEOConfigDao = configDao;
	}
	
	/**
	 * 更新SEO标准设置 
	 * @param title
	 * @param description
	 * @param keywords
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long updateSEOConfig(String title,String description,String keywords,int siteMap,String otherTags) 
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		long result = 0L;
		try {
			result = SEOConfigDao.updateSEOConfig(conn,siteMap, otherTags,title, description, keywords);
			if(result <= 0){
				conn.rollback();
			}
			else{
				conn.commit();
			}
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询SEO标准设置 
	 * @return Map<String,String>
	 * @throws Exception
	 */
	public Map<String, String> querySEOConfig() throws SQLException,DataException{
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = SEOConfigDao.querySEOConfig(conn);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;		
	}
}
