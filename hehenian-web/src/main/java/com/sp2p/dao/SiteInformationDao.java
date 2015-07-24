package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
public class SiteInformationDao {
	/***
	 * 查询  站点资料
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public  Map<String,String>  querySiteAll( Connection conn) throws SQLException, DataException
	{
		Dao.Tables.t_site_information t_site_information = new Dao().new Tables().new t_site_information();
	   DataSet  ds=	t_site_information.open(conn, "", "", "id desc", -1, -1);
	   return  BeanMapUtils.dataSetToMap(ds);
	}
	
	
	/**
	 * 修改
	 * @return
	 * @throws SQLException 
	 */
	public Long updateSiteById(Connection  conn,int id,String siteName,String companyName,String postcode ,String address ,String principal,String contact,String telephone
								,String cellphone,String fax,String emial,String qq,String servicePhone,String certificate,String regionName) throws SQLException{
		Dao.Tables.t_site_information t_site_information = new Dao().new Tables().new t_site_information();
		t_site_information.siteName.setValue(siteName);
		t_site_information.companyName.setValue(companyName);
		t_site_information.postcode.setValue(postcode);
		t_site_information.principal.setValue(principal);
		t_site_information.address.setValue(address);
		t_site_information.contact.setValue(contact);
		t_site_information.telephone.setValue(telephone);
		t_site_information.cellphone.setValue(cellphone);
		t_site_information.fax.setValue(fax);
		t_site_information.emial.setValue(emial);
		t_site_information.qq.setValue(qq);
		t_site_information.servicePhone.setValue(servicePhone);
		t_site_information.certificate.setValue(certificate);
		t_site_information.regionName.setValue(regionName);
		
		return  t_site_information.update(conn, " id = " + id);
	}
}
