/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.colorlife.impl
 * @Title: BusinessDaoImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-8 下午9:41:22
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.colorlife.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.hehenian.biz.common.colorlife.dataobject.BusinessDo;
import com.hehenian.biz.dal.colorlife.IBusinessDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class BusinessDaoImpl extends AbstractBaseDaoImpl<BusinessDo> implements IBusinessDao {

	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<BusinessDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(BusinessDo.class);
	
	@Override
	public int addBusiness(BusinessDo businessDo) {
		String sql = "insert into td_business(businessType,businessId,createTime,status)values(?,?,now(),0)";
		return sp2pJdbcTemplate.update(sql, businessDo.getBusinessType(),businessDo.getBusinessId());
	}

	@Override
	public int updateBusiness(BusinessDo businessDo) {
		String sql = "update td_business set status=?,externalId=?,updateTime=now() where businessType=? and businessId=? and status=0";
		return sp2pJdbcTemplate.update(sql, businessDo.getStatus(),businessDo.getExternalId(),businessDo.getBusinessType(),businessDo.getBusinessId());
	}

	@Override
	public BusinessDo getBusinessByDo(BusinessDo businessDo) {
		String sql = "select * from td_business where businessType=? and businessId=? limit 1";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Object[]{businessDo.getBusinessType(),businessDo.getBusinessId()});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<BusinessDo> queryFailBusinessList(){
		String sql = "select * from td_business where status=?";
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[]{0});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RowMapper<BusinessDo> getRowMapper() {
		return rowMapper;
	}

}
