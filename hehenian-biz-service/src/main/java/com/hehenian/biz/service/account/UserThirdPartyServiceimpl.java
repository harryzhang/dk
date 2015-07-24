package com.hehenian.biz.service.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IUserThirdPartyService;
import com.hehenian.biz.common.account.dataobject.UserThirdPartyDo;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;
@Service("userThirdPartyService")
public class UserThirdPartyServiceimpl extends AbstractBaseDaoImpl<UserThirdPartyDo> implements IUserThirdPartyService {
	
	 @Resource
		private JdbcTemplate sp2pJdbcTemplate;
	    
	    private static RowMapper<UserThirdPartyDo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(UserThirdPartyDo.class);
	@Override
	public UserThirdPartyDo getByTheThirdUserName(String theThirdUserName) {
		String sql = "select * from t_user_thirdparty where thethirdusername=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new String[] {theThirdUserName}, rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserThirdPartyDo getByUserId(Integer userId) {
		String sql = "select * from t_user_thirdparty where userId=? limit 1;";
		try {
			return queryObject(sp2pJdbcTemplate, sql, new Integer[] {userId}, rowMapper);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int saveUserThirdParty(UserThirdPartyDo userThirdPartyDo) {
		String sql = "INSERT INTO t_user_thirdparty (thethirdusername,userId) values (?,?) ";
		return sp2pJdbcTemplate.update(sql, userThirdPartyDo.getThethirdusername(),userThirdPartyDo.getUserId());
	}

	@Override
	public RowMapper<UserThirdPartyDo> getRowMapper() {
		return new RowMapper<UserThirdPartyDo>() {
			@Override
			public UserThirdPartyDo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UserThirdPartyDo pd = new UserThirdPartyDo();
				//to do...
				return null;
			}
		};
	}

	@Override
	public int deleteUserThirdPartyByUserId(Integer userId) {
		String sql = "delete from t_user_thirdparty where userId=? ;";
		try {
			return delete(sp2pJdbcTemplate, sql, new Integer[] {userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteUserThirdPartyBytheThirdUserName(String theThirdUserName) {
		String sql = "delete from t_user_thirdparty where thethirdusername=? ;";
		try {
			return delete(sp2pJdbcTemplate, sql, new String[] {theThirdUserName});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void saveUserColor() {
		String sql = "INSERT INTO t_color_life_buy_info VALUES ('111', '7', '111', 1000072, 111, '2015-4-14 00:00:00', 1, '111', '2015-4-14 00:00:00', '2015-4-14 00:00:00'); ";
		sp2pJdbcTemplate.update(sql);
	}

}
