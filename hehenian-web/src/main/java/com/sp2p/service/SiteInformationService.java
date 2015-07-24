package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.SiteInformationDao;

/**
 * 站点资料
 * @author Administrator
 *
 */
public class SiteInformationService extends BaseService{
	public static Log log =LogFactory.getLog(SiteInformationService.class);
	private SiteInformationDao siteInformationDao;
	
	/***
	 * 查询  站点资料
	 * @param conn
	 * @return
	 * @throws Exception 
	 */
	public  Map<String,String>  querySiteAll() throws Exception
	{
		Connection  conn=connectionManager.getConnection();
		try {
			return  siteInformationDao.querySiteAll(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally
		{
			conn.close();
		}
	}
	
	/**
	 * 修改
	 * @return
	 * @throws SQLException 
	 */
		public Long updateSiteById(int id,String siteName,String companyName,String postcode ,String address ,String principal,String contact,String telephone
									,String cellphone,String fax,String emial,String qq,String servicePhone,String certificate,String regionName) throws SQLException{
			Connection  conn = MySQL.getConnection();
			long result = -1L;
			try {
				result = siteInformationDao.updateSiteById(conn, id, siteName, companyName, postcode, address, principal, contact, telephone, cellphone, fax, emial, qq, servicePhone, certificate, regionName);
				conn.commit();
			} catch (SQLException e) {
				log.error(e);
				conn.rollback();
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
		
		return result;
	}

	public void setSiteInformationDao(SiteInformationDao siteInformationDao) {
		this.siteInformationDao = siteInformationDao;
	}
	
	
}
