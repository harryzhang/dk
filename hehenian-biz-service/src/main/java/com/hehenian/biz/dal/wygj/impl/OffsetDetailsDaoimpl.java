/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: OffsetDaoimpl.java
 * @Description: TODO
 *
 * @author: jiangwmf
 * @date 2015-5-6 下午4:56:57
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

import com.hehenian.biz.common.wygj.dataobject.OffsetDetailsDo;
import com.hehenian.biz.dal.wygj.IOffsetDetailsDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class OffsetDetailsDaoimpl extends AbstractBaseDaoImpl<OffsetDetailsDo> implements IOffsetDetailsDao{
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<OffsetDetailsDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(OffsetDetailsDo.class);

	
	
	@Override
	public List<OffsetDetailsDo> listOffsetDetails(int tradeId){
		String sql = "select * from t_offset_detailinfo where trade_id=? ;";
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[]{tradeId});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	@Override
	public int insertOffsetDetail(OffsetDetailsDo odd){
		String sql = "insert into t_offset_detailinfo(trade_id,fee,offsetdate,timeframe,infostatus,isvalid) values(?,?,?,?,?,?) ;";
		return sp2pJdbcTemplate.update(sql, odd.getTrade_id(),odd.getFee(),odd.getOffsetdate(),odd.getTimeframe(),odd.getInfostatus(),odd.getIsvalid());
	}
	

	@Override
	public RowMapper<OffsetDetailsDo> getRowMapper() {
		return rowMapper;
	}


}
