package com.hehenian.biz.dal.wygj.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;
import com.hehenian.biz.dal.wygj.IPropertyManagementDetailDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class PropertyManagementDetailDaoImpl extends AbstractBaseDaoImpl<PropertyManagementDetailDo>  implements
		IPropertyManagementDetailDao {

	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<PropertyManagementDetailDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(PropertyManagementDetailDo.class);
	
	@Override
	public PropertyManagementDetailDo getById(Integer id) {
		String sql = "select * from t_property_management_detailinfo where id=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Integer[] { id },
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	@Override
	public PropertyManagementDetailDo getDefaultByUserId(int userId) {
		String sql = "select * from t_property_management_detailinfo where user_id=? and defaultset =1 limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Integer[] { userId },
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getPropertyManagementCounts(int userId) {
		String sql = "select count(*) from t_property_management_detailinfo where user_id=? ";
		try {
			return queryCount(sp2pJdbcTemplate, sql, new Integer[]{userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public List<PropertyManagementDetailDo> listPropertyManagementsByUserId(
			int userId) {
		String sql = "select * from t_property_management_detailinfo where user_id=? ;";
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[]{userId});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int insertManageDetail(PropertyManagementDetailDo pmd) {
		String sql = "insert into t_property_management_detailinfo(user_id,theowner,mainaddressid,building,roomnum,infotype,defaultset) values(?,?,?,?,?,?,?) ;";
		return sp2pJdbcTemplate.update(sql, pmd.getUser_id(),pmd.getTheowner(),pmd.getMainaddressid(),pmd.getBuilding(),pmd.getRoomnum(),pmd.getInfotype(),pmd.getDefaultset());
	}

	@Override
	public int updateDefaultByBuildingNo(int userId, String buildingNo) {
		String sql = "update t_property_management_detailinfo set defaultset=1 where user_id=? and roomnum=? ";
		return sp2pJdbcTemplate.update(sql, userId,buildingNo);
	}

	@Override
	public int deleteManagerDetailById(int id) {
		String sql = "delete  from t_property_management_detailinfo where id=? limit 1;";
    	try {
			return delete(sp2pJdbcTemplate, sql, new Object[]{id});
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return -1;
	}

	@Override
	public RowMapper<PropertyManagementDetailDo> getRowMapper() {
		return rowMapper;
	}
	@Override
	public int updateAnotherDefaultManageDetail(int userId, int id) {
		String sql = "update t_property_management_detailinfo set defaultset=1 where user_id=? and id<>? limit 1; ";
		return sp2pJdbcTemplate.update(sql, userId,id);
	}
	@Override
	public int updatePropertyManagementDetailDo(PropertyManagementDetailDo pmdd) {
		String sql = "update t_property_management_detailinfo set infotype=?  where id=? limit 1 ;";
		return sp2pJdbcTemplate.update(sql, pmdd.getInfotype(),pmdd.getId());
	}
	@Override
	public PropertyManagementDetailDo getDetailByParams(long mainaddressid,
			String roomno, int userId) {
		String sql = "select * from t_property_management_detailinfo where mainaddressid=? and roomnum =? and user_id=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Object[] { mainaddressid,roomno ,userId},
					rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
