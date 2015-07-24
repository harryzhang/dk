package com.sp2p.service.admin;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.dao.admin.RelationDao;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;

public class RelationService extends BaseService {
	
	public static Log log = LogFactory.getLog(RelationService.class);

	private RelationDao relationDao;
	/**
	 * 添加关系
	 * @param peopleId
	 * @param parentId
	 * @param level
	 * @param enable
	 * @return
	 * @throws SQLException
	 */
	public long addRelation(long peopleId,long parentId,int level,int enable) throws SQLException{
		Connection conn = connectionManager.getConnection();
		long returnId = -1;
		try {
			returnId = relationDao.addRelation(conn, peopleId, parentId, level, enable);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return returnId;
	}
	/**
	 * 通过ID查询关系
	 * @param peopleId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryRelationByPeopleId(long peopleId) throws Exception{
		Connection conn = connectionManager.getConnection();
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		try {
			list = relationDao.queryRelationByPeopleId(conn, peopleId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return list;
	}
	
	public void queryRelationBy2(String userName1,String userName2,String realName2,String startDate,String endDate,Long parentId,PageBean<Map<String,Object>> pageBean,long adminId,String AdminName) throws Exception{
		StringBuffer condition = new StringBuffer();
		condition.append(" AND level=2 and userName2 is not null ");
		if(StringUtils.isNotBlank(userName1)){
			condition.append(" AND userName1 like '%"+StringEscapeUtils.escapeSql(userName1.trim())+"%'");
		}
		if(StringUtils.isNotBlank(userName2)){
			condition.append(" AND userName2 like '%"+StringEscapeUtils.escapeSql(userName2.trim())+"%'");
		}
		if(StringUtils.isNotBlank(realName2)){
			condition.append(" AND realName2 like '%"+StringEscapeUtils.escapeSql(realName2.trim())+"%'");
		}
		if(StringUtils.isNotBlank(startDate)){
			condition.append(" AND addDate >= '"+StringEscapeUtils.escapeSql(startDate)+"'");
		}
		if(StringUtils.isNotBlank(endDate)){
			condition.append(" AND addDate <= '"+StringEscapeUtils.escapeSql(endDate)+"'");
		}
		if (adminId==1){
			condition.append(" AND userName1 = '"+StringEscapeUtils.escapeSql(AdminName.trim())+"'");
		}if (adminId==2) {
			condition.append(" AND userName2 = '"+StringEscapeUtils.escapeSql(AdminName.trim())+"'");
		}
		if(parentId!=null&&parentId>0){
			condition.append(" AND parentId = '"+parentId+"'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_relation_level2 ", " * ", " order by id DESC ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	/**
	 * 投资人管理
	 * @param userName1
	 * @param userName2
	 * @param userName3
	 * @param realName3
	 * @param startDate
	 * @param endDate
	 * @param parentId
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryRelationBy3(String userName1,String userName2,String userName3,String realName3,String startDate,String endDate,Long parentId,PageBean<Map<String,Object>> pageBean,long adminId,String AdminName) throws Exception{
		StringBuffer condition = new StringBuffer();
		condition.append(" AND level=3 ");
		if(StringUtils.isNotBlank(userName1)){
			condition.append(" AND userName1 like '%"+StringEscapeUtils.escapeSql(userName1.trim())+"%'");
		}
		if(StringUtils.isNotBlank(userName2)){
			condition.append(" AND userName2 like '%"+StringEscapeUtils.escapeSql(userName2.trim())+"%'");
		}
		if(StringUtils.isNotBlank(userName3)){
			condition.append(" AND userName3 like '%"+StringEscapeUtils.escapeSql(userName3.trim())+"%'");
		}
		if(StringUtils.isNotBlank(realName3)){
			condition.append(" AND realName3 like '%"+StringEscapeUtils.escapeSql(realName3.trim())+"%'");
		}
		if(StringUtils.isNotBlank(startDate)){
			condition.append(" AND addDate >= '"+StringEscapeUtils.escapeSql(startDate)+"'");
		}
		if(StringUtils.isNotBlank(endDate)){
			condition.append(" AND addDate <= '"+StringEscapeUtils.escapeSql(endDate)+"'");
		}
		if(parentId!=null&&parentId>0){
			condition.append(" AND parentId = '"+parentId+"'");
		}
		if (adminId==1){
			condition.append(" AND userName1 = '"+StringEscapeUtils.escapeSql(AdminName.trim())+"'");
		}if (adminId==2) {
			condition.append(" AND userName2 = '"+StringEscapeUtils.escapeSql(AdminName.trim())+"'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_relation_level3 ", " * ", " order by id DESC ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	/**
	 * 通过四个查询关系    理财人管理
	 * @param userName1
	 * @param userName2
	 * @param userName3
	 * @param userName4
	 * @param realName4
	 * @param startDate
	 * @param endDate
	 * @param parentId
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryRelationBy4(String userName1,String userName2,String userName3,String userName4,String realName4,String startDate,String endDate,Long parentId,PageBean<Map<String,Object>> pageBean,long adminId,String AdminName) throws Exception{
		StringBuffer condition = new StringBuffer();
		condition.append(" AND level=4 ");
		if(StringUtils.isNotBlank(userName1)){
			condition.append(" AND userName1 like '%"+StringEscapeUtils.escapeSql(userName1.trim())+"%'");
		}
		if(StringUtils.isNotBlank(userName2)){
			condition.append(" AND userName2 like '%"+StringEscapeUtils.escapeSql(userName2.trim())+"%'");
		}
		if(StringUtils.isNotBlank(userName3)){
			condition.append(" AND userName3 like '%"+StringEscapeUtils.escapeSql(userName3.trim())+"%'");
		}
		if(StringUtils.isNotBlank(userName4)){
			condition.append(" AND userName4 like '%"+StringEscapeUtils.escapeSql(userName4.trim())+"%'");
		}
		if(StringUtils.isNotBlank(realName4)){
			condition.append(" AND realName4 like '%"+StringEscapeUtils.escapeSql(realName4.trim())+"%'");
		}
		if(StringUtils.isNotBlank(startDate)){
			condition.append(" AND addDate >= '"+StringEscapeUtils.escapeSql(startDate)+"'");
		}
		if(StringUtils.isNotBlank(endDate)){
			condition.append(" AND addDate <= '"+StringEscapeUtils.escapeSql(endDate)+"'");
		}
		if(parentId!=null&&parentId>0){
			condition.append(" AND parentId = '"+parentId+"'");
		}
		if (adminId==1){
			condition.append(" AND userName1 = '"+StringEscapeUtils.escapeSql(AdminName.trim())+"'");
		}if (adminId==2) {
			condition.append(" AND userName2 = '"+StringEscapeUtils.escapeSql(AdminName.trim())+"'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_relation_level4 ", " * ", " order by id DESC ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 修改角色之间的关系
	 * @return
	 * @throws SQLException 
	 */
	public Long updateRelationEnable(long id,int enable) throws SQLException{
		Connection conn = connectionManager.getConnection();
		long returnId = -1;
		try {
			returnId = relationDao.updateRelation(conn, id, null, null, null, enable);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return returnId;
	}
	
	/**
	 * 判断是否为推广人
	 */
	public Map<String,Object> isPromoter(String userId) throws Exception{
		Map<String,Object> map = null;
		Connection conn = connectionManager.getConnection();
		try{
			//判断是否是经纪人
			List<Map<String,Object>> level2List = new ArrayList<Map<String,Object>>();
			level2List = relationDao.queryUserByRelation2(conn, userId);
			if(level2List!=null&&level2List.size()>0){
				map = new HashMap<String, Object>();
				map.put("level", IConstants.RELATION_LEVEL3);//经纪人下面是投资人
				map.put("parentId", level2List.get(0).get("id"));
				return map;
			}
			//判断是否是投资人
			List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
			userList = relationDao.queryUserByRelation3(conn, userId);
			if(userList!=null&&userList.size()>0){
				map = new HashMap<String, Object>();
				map.put("level", IConstants.RELATION_LEVEL4);//投资人下面是理财人
				map.put("parentId", userList.get(0).get("id"));
			}
			//判断是否是网站会员
            Map<String, String> userListVip = relationDao.queryUserById(conn, userId);
            if (userListVip != null && userListVip.size() > 0)
            {
                map = new HashMap<String, Object>();
                map.put("level", IConstants.RELATION_LEVEL4);//投资人下面是理财人
                map.put("parentId", userListVip.get("id"));
            }
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return map;
	}
	/**
	 * 通过ID查询关系
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> queryRelationById(long id) throws Exception{
		Connection conn = connectionManager.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = relationDao.queryRelationById(conn, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return map;
	}
	
	/**
	 * 修改角色之间的关系
	 * @return
	 * @throws SQLException 
	 */
	public Long updateRelation(long id,Long peopleId,Long parentId,Integer level,Integer enable) throws SQLException{
		Connection conn = connectionManager.getConnection();
		long returnId = -1;
		try {
			returnId = relationDao.updateRelation(conn, id, peopleId, parentId, level, enable);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return returnId;
	}
	
	/**
	 *  经济人关联客户
	 * @return
	 */
	public void relationLeveladdInfo(String userName,String realName,String startDate,String endDate ,PageBean<Map<String,Object>> pageBean) throws Exception{
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(userName)){
			condition.append(" AND t.userName like '%"+StringEscapeUtils.escapeSql(userName.trim())+"%'");
		}
		if(StringUtils.isNotBlank(realName)){
			condition.append(" AND t.realName like '%"+StringEscapeUtils.escapeSql(realName.trim())+"%'");
		}
		if(StringUtils.isNotBlank(startDate)){
			condition.append(" AND t.addDate >= '"+StringEscapeUtils.escapeSql(startDate.trim())+"'");
		}
		if(StringUtils.isNotBlank(endDate)){
			condition.append(" AND t.addDate <= '"+StringEscapeUtils.escapeSql(endDate.trim())+"'");
		}
		StringBuffer tablecondition = new StringBuffer();
		tablecondition.append(" (select a.id as id,a.username as userName,a.createTime as addDate,b.realName as  realName,a.mobilePhone as  telphone,b.idNo as card ");
		tablecondition.append("  from t_user a left join t_person b on a.id = b.userId ");
		tablecondition.append("  where a.id not in (select a.peopleId from t_relation a )) t ");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean,tablecondition.toString(), " t.id,t.userName,t.realName,t.card,t.telphone,t.addDate ", " order by t.id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}

	/**
	 * 添加经济人与客户之间的关系
	 * @param commonIds
	 * @param level2Id
	 * @return
	 * @throws SQLException
	 */
	public Long addRelationkhId(String commonIds,long  level2Id) throws SQLException{
		Connection conn = MySQL.getConnection();
		String[] fileUrls = commonIds.split(",");//截取;符号 放入String数组  
		long result = -1;
		if (fileUrls != null && fileUrls.length > 0){ 
		for(String userid :fileUrls){
			try {
				result = relationDao.addRelation(conn, Convert.strToLong(userid, -2), level2Id, IConstants.RELATION_LEVEL3, 1);
				if(result<0){
					conn.rollback();
					return result;
				}
			} catch (SQLException e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			}
		 }
		conn.commit();
		}
		return result;
	}

	/**
	 * 查询投资人信息
	 * @param userName
	 * @param realName
	 * @param startDate
	 * @param endDate
	 * @throws Exception 
	 */
	public void queryrelationinvestorsInfo(String userName,String realName,String startDate,String endDate,PageBean<Map<String,Object>> pageBean) throws Exception{
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(userName)){
			condition.append(" AND t.userName like '%"+StringEscapeUtils.escapeSql(userName.trim())+"%'");
		}
		if(StringUtils.isNotBlank(realName)){
			condition.append(" AND t.realName like '%"+StringEscapeUtils.escapeSql(realName.trim())+"%'");
		}
		if(StringUtils.isNotBlank(startDate)){
			condition.append(" AND t.createTime >= '"+StringEscapeUtils.escapeSql(startDate.trim())+"'");
		}
		if(StringUtils.isNotBlank(endDate)){
			condition.append(" AND t.createTime <= '"+StringEscapeUtils.escapeSql(endDate.trim())+"'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " (select  b.mobilePhone as telphone,a.peopleId as id,b.username as userName,c.realName as realName ,c.idNo as card,b.createTime as createTime,a.enable as enable,a.level as level,a.parentId as parentId " +
					"  from t_relation a " +
					"  left join t_user b on a.peopleId = b.id " +
					"  left join t_person c on a.peopleId = c.userId " +
					"  where level = 3 ) t", " * ", "", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	
	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}
}
