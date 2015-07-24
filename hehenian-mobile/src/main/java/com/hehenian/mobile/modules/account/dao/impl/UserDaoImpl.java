/**  
 * @Project: hehenian-mobile
 * @Package com.dao.impl
 * @Title: UserDaoImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-3-23 上午11:55:40
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.modules.account.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.mobile.modules.account.dao.UserDao;


@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public AccountUserDo findUserByUserNamePwd(String userName, String password){
		RowMapper<AccountUserDo> rowMapper = new BeanPropertyRowMapper<AccountUserDo>(AccountUserDo.class);
		String sql = "select * from t_user where username=? and password=?";
		List<AccountUserDo> userList = jdbcTemplate.query(sql,rowMapper,userName,password);
		if(!CollectionUtils.isEmpty(userList)){
			return userList.get(0);
		}
		return null;
	}
}
