/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj.impl
 * @Title: IPropertyManagementFeeDaoImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-7 下午10:24:21
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;
import com.hehenian.biz.dal.wygj.IPropertyManagementFeeDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class PropertyManagementFeeDaoImpl extends AbstractBaseDaoImpl<PropertyManagementFeeDo> implements IPropertyManagementFeeDao {
	
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<PropertyManagementFeeDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(PropertyManagementFeeDo.class);

	@Override
	public List<PropertyManagementFeeDo> queryBuildingByAddressId(Long addressId,String building){
		String queryField = building==null?"building":"roomnum";
		String sql = "select distinct "+queryField+" from t_property_management_fee where mainaddressid=?";
		if(building!=null){
			sql+=" and building="+building;
		}
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[]{addressId});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public RowMapper<PropertyManagementFeeDo> getRowMapper() {
		return rowMapper;
	}

	@Override
	public PropertyManagementFeeDo getByParams(long mainaddressid,String buildingno,String roomno,String theOwner) {
		String sql = "select * from t_property_management_fee where mainaddressid=? and building=? and roomnum=? and theowner like \"%"+theOwner+"%\" limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Object[]{mainaddressid,buildingno,roomno},
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}



}
