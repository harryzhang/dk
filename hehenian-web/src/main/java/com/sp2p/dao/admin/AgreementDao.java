package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 后台协议
 * @author Administrator
 *
 */
public class AgreementDao {




	/**
	 * 更新协议内容
	 * @param conn
	 * @param id
	 * @param sort
	 * @param columName
	 * @param content
	 * @param publishTimee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateAgreement(Connection conn,Integer agreementType,Integer provisionType,String content,String title)throws SQLException,DataException{
		Dao.Tables.t_agreement agreement=new Dao().new Tables().new t_agreement();
		agreement.title.setValue(title);
		agreement.content.setValue(content);
		
		return agreement.update(conn, "agreementType="+agreementType+" AND provisionType="+provisionType);
		
	}
	
	/**
	 * 获取协议详情
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getAgreementDetail(Connection conn,Integer agreementType, Integer provisionType)throws SQLException,DataException{
		Dao.Tables.t_agreement agreement=new Dao().new Tables().new t_agreement();
		DataSet dataSet=agreement.open(conn, "*", "agreementType="+agreementType+" AND provisionType="+provisionType, "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);		
	}
	

	


}
