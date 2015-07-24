/**
 * Project Name:hehenian-manager
 * File Name:AddressDaoImpl.java
 * Package Name:com.hehenian.manager.modules.basicdata.dao.impl
 * Date:2015年5月6日上午10:55:41
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.modules.basicdata.dao.AddressDao;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月6日 上午10:55:41 	 
 */
@Repository("addressDao")
public class AddressDaoImpl implements AddressDao {

	@Resource
	protected NamedParameterJdbcTemplate sp2pNameJdbcTemplate;
	
	@Override
	public String getByCommunityCode(String communityCode) throws SQLException {
		String sql = "SELECT remark FROM syscode WHERE typeId = 2 AND id = ROUND(?/100000000,0)*100000000 UNION "+ 
					 "SELECT remark FROM syscode WHERE typeId = 3 AND id = ROUND(?/1000000,0)*1000000 UNION "+ 
					 "SELECT remark FROM syscode WHERE typeId = 4 AND id = ROUND(?/10000,0)*10000 UNION "+
					 "SELECT remark FROM syscode WHERE typeId = 5 AND id = ?";
		Long param = Long.valueOf(communityCode);
		List<Map<String, Object>> result = sp2pNameJdbcTemplate.getJdbcOperations().queryForList(sql, param,param,param,param);
		StringBuffer rst = new StringBuffer();
		for(Map<String, Object> map:result){
			rst.append(map.get("remark")==null?"":map.get("remark").toString());
		}
		return rst.toString();
	}

}

