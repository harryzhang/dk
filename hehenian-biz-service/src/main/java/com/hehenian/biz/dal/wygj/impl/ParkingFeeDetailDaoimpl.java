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

import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.dal.wygj.IParkingFeeDetailDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class ParkingFeeDetailDaoimpl extends AbstractBaseDaoImpl<ParkingDetailDo> implements IParkingFeeDetailDao{
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<ParkingDetailDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(ParkingDetailDo.class);

	
	public ParkingDetailDo getById(Integer id) {
		String sql = "select * from t_parking_detailinfo where id=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Integer[] { id },
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<ParkingDetailDo> listParkingDetailDo(Object[] obj){
		String sql = "select * from t_parking_detailinfo where theowner=? ;";
		try {
			return queryList(sp2pJdbcTemplate, sql, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	

	public int updateParkingDetail(ParkingDetailDo pdd){
		String sql = "update t_parking_detailinfo set infotype=?  where id=? limit 1 ;";
		return sp2pJdbcTemplate.update(sql, pdd.getInfotype(),pdd.getId());
	}
	
    public int deleteParkingDetail(int id){
    	String sql = "delete  from t_parking_detailinfo where id=? limit 1;";
    	try {
			return delete(sp2pJdbcTemplate, sql, new Object[]{id});
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return -1;
    }
	@Override
	public RowMapper<ParkingDetailDo> getRowMapper() {
		return rowMapper;
	}

	@Override
	public ParkingDetailDo getDefaultByUserId(int userId) {
		String sql = "select * from t_parking_detailinfo where user_id=? and defaultset =1 limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Integer[] { userId },
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getParkingDetailCounts(int userId) {
		String sql = "select count(*) from t_parking_detailinfo where user_id=? ";
		try {
			return queryCount(sp2pJdbcTemplate, sql, new Integer[]{userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public List<ParkingDetailDo> listParkingDetailsByUserId(int userId) {
		String sql = "select * from t_parking_detailinfo where user_id=? ;";
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[]{userId});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int insertParkingDetail(ParkingDetailDo pdd) {
		String sql = "insert into t_parking_detailinfo(user_id,mainaddressid,plate_number,car_emissions,the_arage_type,infotype,defaultset) values(?,?,?,?,?,?,?) ;";
		return sp2pJdbcTemplate.update(sql, pdd.getUser_id(),pdd.getMainaddressid(),pdd.getPlate_number(),pdd.getCar_emissions(),pdd.getThe_arage_type(),pdd.getInfotype(),pdd.getDefaultset());
	}

	@Override
	public int updateDefaultByPlateNo(int userId, String plateNo , int defaultSet) {
		String sql = "update t_parking_detailinfo set defaultset=? where user_id=?  ";
		if(plateNo!=null){
			sql += " and plate_number = ? ";
			return sp2pJdbcTemplate.update(sql, defaultSet,userId,plateNo);
		}else
		return sp2pJdbcTemplate.update(sql, defaultSet,userId);
	}
	
	@Override
	public int updateAnotherDefaultParkingDetail(int userId, int id) {
		String sql = "update t_parking_detailinfo set defaultset=1 where user_id=? and id<>? limit 1; ";
		return sp2pJdbcTemplate.update(sql, userId,id);
	}

	@Override
	public ParkingDetailDo getDetailByParams(long mainaddressid, String platenum,int userId) {
		String sql = "select * from t_parking_detailinfo where mainaddressid=? and plate_number =? and user_id=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Object[] { mainaddressid,platenum ,userId},
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<ParkingDetailDo> getParkingDetailDo(ParkingDetailDo p) {
		String sql = "select * from t_parking_detailinfo where mainaddressid=? and plate_number = ? and STR_TO_DATE(enddate,\"%Y-%m\")>=NOW() limit 1;";
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[] { Long.valueOf(p.getMainaddressid()).intValue(),p.getPlate_number() });
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
