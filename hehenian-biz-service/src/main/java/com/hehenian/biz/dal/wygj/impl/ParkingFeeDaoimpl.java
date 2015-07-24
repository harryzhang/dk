/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: ParkingFeeDaoimpl.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-27 下午4:56:57
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

import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;
import com.hehenian.biz.dal.wygj.IParkingFeeDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class ParkingFeeDaoimpl extends AbstractBaseDaoImpl<ParkingFeeDo> implements IParkingFeeDao{
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<ParkingFeeDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(ParkingFeeDo.class);

	
	public ParkingFeeDo getById(Integer id) {
		String sql = "select * from t_parking_fee where id=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Integer[] { id },
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<ParkingFeeDo> listParkingFee(Object[] obj){
		String sql = "select * from t_parking_fee where mobilephone=? and theowner=? ;";
		try {
			return queryList(sp2pJdbcTemplate, sql, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public int insertParkingFee(ParkingFeeDo pf){
		String sql = "insert into t_parking_fee(theowner,mainaddressid,mobilephone,plate_number,car_emissions,the_arage_type,parking_fee,createuserid,updateuserid,	createTime,updateTime) values(?,?,?,?,?,?,?,?,?,?,?) ;";
		return sp2pJdbcTemplate.update(sql, pf.getTheowner(),pf.getMainaddressid(),pf.getMobilephone(),pf.getPlate_number(),pf.getCar_emissions(),pf.getThe_arage_type(),pf.getParking_fee(),pf.getCreateuserid(),pf.getUpdateuserid(),pf.getCreateTime(),pf.getUpdateTime());
	}
	
	public int updateParkingFee(ParkingFeeDo pf){
		String sql = "update t_parking_fee set theowner=?,mobilephone=?,plate_number=?,car_emissions=?,the_arage_type=?,parking_fee=?,updateTime=?,updateuserid=? where id=? limit 1 ;";
		return sp2pJdbcTemplate.update(sql, pf.getTheowner(),pf.getMobilephone(),pf.getPlate_number(),pf.getCar_emissions(),pf.getThe_arage_type(),pf.getParking_fee(),pf.getUpdateTime(),pf.getUpdateuserid(),pf.getId());
	}

	@Override
	public RowMapper<ParkingFeeDo> getRowMapper() {
		return null;
	}

	@Override
	public ParkingFeeDo getByParams(long community, String plateNum) {
		String sql = "select * from t_parking_fee where mainaddressid=? and plate_number=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Object[]{community,plateNum},
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
