/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj.impl
 * @Title: SysCodeDaoImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-6 下午7:03:47
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
import com.hehenian.biz.common.wygj.dataobject.SysCodeDo;
import com.hehenian.biz.dal.wygj.SysCodeDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;


public class SysCodeDaoImpl extends AbstractBaseDaoImpl<SysCodeDo>  implements SysCodeDao {
	
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<SysCodeDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(SysCodeDo.class);

	@Override
	public List<SysCodeDo> querySysCodeListByDo(SysCodeDo codeDo) {
		String sql = "select * from syscode where parentId=? and typeId=?";
		try {
			return queryList(sp2pJdbcTemplate, sql, new Object[]{codeDo.getId(),codeDo.getTypeId()});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SysCodeDo> queryAllSysCodeByType(Integer type[]){
		String sql ="select id,parentId,remarkForShow from syscode where typeId in(";
		for(Integer i : type){
			sql+=i+",";
		}
		sql = sql.substring(0, sql.lastIndexOf(","));
		sql+=")";
		try {
			return queryList(sp2pJdbcTemplate, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getByCommunityCode(String id){
		String sql = "SELECT GROUP_CONCAT(remarkForShow) AS remarkForShow FROM (SELECT remarkForShow FROM syscode WHERE typeId = 2 AND id = ROUND("+id+"/100000000,0)*100000000 UNION "+ 
				"SELECT remarkForShow FROM syscode WHERE typeId = 3 AND id = ROUND("+id+"/1000000,0)*1000000 UNION  "+
				"SELECT remarkForShow FROM syscode WHERE typeId = 4 AND id = ROUND("+id+"/10000,0)*10000 UNION  "+
				"SELECT remarkForShow FROM syscode WHERE typeId = 5 AND id = "+id+") t;";
		try {
			return queryString(sp2pJdbcTemplate, sql).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	

	@Override
	public RowMapper<SysCodeDo> getRowMapper() {
		return rowMapper;
	}

}
