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
import com.hehenian.biz.common.wygj.dataobject.OffsetRecordsDo;
import com.hehenian.biz.dal.wygj.IOffsetRecordsDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class OffsetRecordsDaoimpl extends AbstractBaseDaoImpl<OffsetRecordsDo> implements IOffsetRecordsDao{
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<OffsetRecordsDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(OffsetRecordsDo.class);

	
	
	@Override
	public List<OffsetRecordsDo> listOffsetRecords(int userId,int infotype){
		String sql = "select * from t_offset_records  where user_id = ? and infotype = ?;";
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[]{userId,infotype});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	@Override
	public int insertOffsetRecord(OffsetRecordsDo odd){
		String sql = "insert into t_offset_records(id,trade_id,user_id,fee,infotype,begindate,enddate) values(?,?,?,?,?,?,?) ;";
		return sp2pJdbcTemplate.update(sql, odd.getId(),odd.getTrade_id(),odd.getUser_id(),odd.getFee(),odd.getInfotype(),odd.getBegindate(),odd.getEnddate());
	}
	

	@Override
	public RowMapper<OffsetRecordsDo> getRowMapper() {
		return rowMapper;
	}
	public OffsetRecordsDo getParkingOffsetJoinEndDate(long mainaddressid,String plateno){
		String sql = "select * from t_offset_records tor,t_parking_detailinfo tpd " +
		" where STR_TO_DATE(tor.enddate,\"%Y-%m\")>=NOW()  "+
		" and tor.id=tpd.id "+
		" and tpd.mainaddressid=? and tpd.plate_number=? limit 1;";
		try {
		return queryObject(sp2pJdbcTemplate, sql, new Object[]{mainaddressid,plateno},rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public OffsetRecordsDo getManageOffsetJoinEndDate(long mainaddressid,String roomno){
		String sql = "select * from t_offset_records tor,t_property_management_detailinfo tpd " +
		" where STR_TO_DATE(tor.enddate,\"%Y-%m\")>=NOW()  "+
		" and tor.id=tpd.id "+
		" and tpd.mainaddressid=? and tpd.roomnum=? limit 1;";
		try {
		return queryObject(sp2pJdbcTemplate, sql, new Object[]{mainaddressid,roomno},rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
