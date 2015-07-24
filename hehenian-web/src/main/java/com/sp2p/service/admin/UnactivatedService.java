package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;

public class UnactivatedService extends BaseService{
	public static Log log = LogFactory.getLog(UnactivatedService.class);

	/**
	 * 查询为激活的用户
	 */
	public void queryUserUnactivated(PageBean<Map<String, Object>> pageBean,String userName,String createtimeStart,String createtimeEnd,String email)
	throws SQLException, Exception {
			Connection conn = connectionManager.getConnection();
		try {
	StringBuffer condition = new StringBuffer();
	if (StringUtils.isNotBlank(userName)) {
		condition.append(" and  username  like '%"+StringEscapeUtils.escapeSql(userName.trim())+"%' ");
	}
	if (StringUtils.isNotBlank(email)) {
		condition.append(" and  email  like '%"+StringEscapeUtils.escapeSql(email.trim())+"%' ");
	}
	if (StringUtils.isNotBlank(createtimeStart)) {
		condition.append(" and createTime >'" +StringEscapeUtils.escapeSql( createtimeStart.trim())
				+ "'");
	}
	if (StringUtils.isNotBlank(createtimeEnd)) {
		condition.append(" and createTime <'" + StringEscapeUtils.escapeSql(createtimeEnd.trim())
				+ "'");
	}
	//condition.append(" 1 = 1");
	condition.append(" AND enable =  2 " );//2为状态未激活的值
	dataPage(conn, pageBean, "t_user", "*", " ORDER BY id", condition.toString());
	
} catch (SQLException e) {
	log.error(e);
	e.printStackTrace();
	throw e;
} catch (DataException e) {
	log.error(e);
	e.printStackTrace();
	throw e;
} finally {
	conn.close();
}
}
	/**
	 * 查询未激活用户详细信息 
	 * @throws SQLException 
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws DataException 
	 * */
	public List<Map<String,Object>> queryUserUnactivatedDetailById(String id) throws Exception{
		//select u.id,u.username,u.createTime,u.creditLimit,u.creditrating,u.enable,count(*) as counts ,p.realname from t_user u
		//, t_materialsauth m ,t_person p where u.id = 152 and u.id = m.userId  and m.auditStatus = 3 and u.id = p.userId
		//GROUP BY u.id
		Connection conn = null;
		try {
			conn = MySQL.getConnection();
			StringBuffer condition = new StringBuffer();
			if(StringUtils.isNotBlank(id)){
//				condition.append(" u.id = "+ StringEscapeUtils.escapeSql(id)+" and u.id = m.userId  and m.auditStatus = 3 and u.id = p.userId ");
//				dataPage(conn, pageBean, "t_user u ,t_materialsauth m ,t_person p", "u.id,u.username,u.createTime,u.creditLimit,u.creditrating,u.enable,count(*) as counts ,p.realname", " ORDER BY u.id", condition.toString());
				condition.append(" select u.id,u.username,u.createTime,u.creditLimit,u.creditrating,u.enable,count(*) as counts ,p.realname ");
				condition.append(" from t_user u , t_materialsauth m ,t_person p ");
				condition.append(" where u.id = " + id + " and u.id = m.userId  and m.auditStatus = 3 ");
				condition.append(" and u.id = p.userId GROUP BY u.id ");
				DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
				List<Map<String,Object>> infoList =   dataSet.tables.get(0).rows.rowsMap;
				return infoList != null ? infoList : null; 
			}
			return null;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			if(conn != null){
				conn.close();
			}
		}
	}
	
	

}
