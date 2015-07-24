package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.CallCenterDao;
import com.sp2p.dao.CategoryDao;
import com.sp2p.dao.admin.KefuDao;

/**
 * 帮助中心和客服管理的Service
 */
public class HelpAndServicerService extends BaseService {
	public static Log log =LogFactory.getLog(HelpAndServicerService.class);

	private CallCenterDao callCenterDao;

	private ConnectionManager connectionManager;
	
	private KefuDao kefusDao;
	
	private CategoryDao categoryDao;
	
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public KefuDao getKefusDao() {
		return kefusDao;
	}

	public void setKefusDao(KefuDao kefusDao) {
		this.kefusDao = kefusDao;
	}
	
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	public CallCenterDao getCallCenterDao() {
		return callCenterDao;
	}

	public void setCallCenterDao(CallCenterDao callCenterDao) {
		this.callCenterDao = callCenterDao;
	}
	
	/**
	 * 获取帮助问题类型列表
	 * @param order
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	
	@SuppressWarnings("unchecked")
	public void queryHelpList(String order,PageBean pageBean) throws SQLException, DataException{
		StringBuffer condition = new StringBuffer();
		
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "t_help_type", " * ", " ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	/**
	 * 获取问题答案列表
	 * @param order
	 * @param pageBean
	 * @param id
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public void queryAllAnswerLists(String order,PageBean pageBean,long id) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_callcenter_help_list", " * ", "", " and typeId="+id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 通过类型id获取所有问题
	 * @param typeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAnswer(long typeId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = callCenterDao.queryAnswerById(conn, typeId);
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
		return map;
	}
	
	/**
	 * 获取帮助信息
	 * @param typeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> helpQueryHelpInfo(Long typeId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = callCenterDao.helpQueyNewViewById(conn, typeId);
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
		return map;
	}
	
	/**
	 * 查询帮助中心的问题
	 * @param pageBean
	 * @param id
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryHelpQuestions(PageBean<Map<String, Object>> pageBean,long id) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		String field = "questionId,typeId,typeDescribe as helpDescribe,question,anwer";
		try {
			if(id < 0){
				dataPage(conn, pageBean, "v_t_callcenter_help_list", field, "", "");
			}else{
				dataPage(conn, pageBean, "v_t_callcenter_help_list", field, "", " and typeId="+id);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
	}
	
	/**
	 * 查询问题类型
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryHelpTypes(PageBean<Map<String, Object>> pageBean) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		String field = " id,helpDescribe ";
		try {
			dataPage(conn, pageBean, "t_help_type", field, " ", "");
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
	}
	
	/**
	 * 通过ID查询问题描述
	 * @param typeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getDescribeById(Long typeId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try{
			return callCenterDao.getDesByTypeId(conn, typeId);
		}finally {
			conn.close();
		}
	}
	
	/**
	 * 通过问题ID获取问题的类型ID
	 * @param questionId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getTypeId(Long questionId)throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try{
			return callCenterDao.getTypeId(conn, questionId);
		}finally {
			conn.close();
		}
	}
	
	/**
	 * 获取问题类型的列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryHelpTypeList() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return callCenterDao.queryHelpTypeList(conn, "id,helpDescribe ", -1, -1);
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 添加新问题列表的时候，首先获得序列号
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> getMaxSerial() throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try{
			return callCenterDao.getMaxSerial(conn);
		}finally {
			conn.close();
		}
	}
	
	/**
	 * 获取帮助
	 * @param pageBean
	 * @param title
	 * @param typeId
	 * @throws DataException
	 * @throws SQLException
	 */
	public void queryHelpPage(PageBean<Map<String, Object>> pageBean, String title, long typeId) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		StringBuffer where = new StringBuffer("");
		if(typeId >= 0){
			where.append(" AND typeId = " + typeId);
		}
		if(title != null && StringUtils.isNotBlank(title)){//title代表问题内容的部分信息
			where.append(" AND question LIKE '%"+StringEscapeUtils.escapeSql(title)+"%'");
		}
		try {
			dataPage(conn, pageBean, "v_t_callcenter_help_list", " questionId,typeId,typeDescribe ,question,anwer, publishTime,browseCount,publisher", "", where.toString());
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
	 * 添加问题
	 * @param title
	 * @param helpId
	 * @param serialCount
	 * @param content
	 * @param publisher
	 * @param dateTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addHelp(String title,Long helpId,Long serialCount,String content,String publisher,String dateTime) throws SQLException, DataException{
		Connection conn = Database.getConnection();
		Long commonId = -1L;
		try {
			commonId = callCenterDao.addHelp(conn, title, helpId, serialCount, content, publisher, dateTime);
			if(commonId<0){//如果小于0，添加失败，回滚
				conn.rollback();
			}
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return commonId;
	}
	
	/**
	 * 删除问题
	 * @param commonIds
	 * @throws SQLException
	 * @throws DataException
	 */
	public void deleteQuestions(String commonIds) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			callCenterDao.deleteQuestion(conn, commonIds);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 根据问题id删除问题信息及其引用该问题信息的答案
	 * @param quesId
	 * @throws SQLException
	 * @throws DataException
	 */
	public void deleteQuestionInfo(String quesId)throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			callCenterDao.deleteQuestionInfo(conn, quesId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 删除问题信息
	 * @param ids
	 * @throws SQLException
	 * @throws DataException
	 */
	public void deleteQuestionsInfo(String[] ids) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			if (ids.length>0) {
				for (int i = 0; i < ids.length; i++) {
					Map<String,String> map = callCenterDao.queryQuestionById(conn, Convert.strToLong(ids[i], -1L));
					String commonId = map.get("commonId");
					if (StringUtils.isNotBlank(commonId)) {
						callCenterDao.deleteQuestion(conn, commonId);
					}
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 查询帮助视图
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryHelpViewByid(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = callCenterDao.queyHelpViewById(conn, id);
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
		return map;
	}
	
	
	/**
	 * 更新帮助问题中心的问题内容
	 * @param id
	 * @param commonId
	 * @param categoryId
	 * @param type
	 * @param SSId
	 * @param title
	 * @param summary
	 * @param browseCount
	 * @param content
	 * @param indexs
	 * @return
	 * @throws SQLException
	 * @throws DataException 
	 */
	public Long updateHelp(String question,Long typeId,Long id,String publisher,String publishTime,Long serialCount) throws SQLException, DataException{
		Connection conn = Database.getConnection();
		try {
			callCenterDao.updateHelps(conn, question,null, typeId, id, publisher, publishTime,serialCount);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return 1L;
	}
	
	/**
	 * 修改帮助问题信息
	 * @param question
	 * @param content
	 * @param typeId
	 * @param id
	 * @param publisher
	 * @param publishTime
	 * @param serialCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateHelpManager(String question,String content,Long typeId,Long id,String publisher,String publishTime,Long serialCount) throws SQLException, DataException{
		Connection conn = Database.getConnection();
		try {
			callCenterDao.updateHelps(conn, question, content, typeId, id, publisher, publishTime,serialCount);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		return 1L;
	} 
	
	/**
	 * 获取帮助ID
	 * @param typeDes
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String getTypeId(String typeDes) throws SQLException, DataException{
		Connection conn = Database.getConnection();
		try{
			return callCenterDao.getTypeIdByDes(conn, typeDes);
		}finally {
			conn.close();
		}
	}
	
	/**
	 * 
	 * @param questionId
	 * @return
	 * @throws SQLException
	 */
	public Long updateQuestionBrowse(Long questionId) throws SQLException{
		Connection conn = Database.getConnection();
		
		Long result = -1L;
		try {
			result = callCenterDao.updateQuestionBrowse(conn,questionId);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 获取问题信息
	 * @param helpId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryQuestionsLimit5(long helpId ) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			return callCenterDao.queryQuestionsLimit5(conn, helpId,-1,-1);
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
	//以上为帮助中心，问题及答案等
	/**
	 * 添加客服信息
	 * @param conn
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addKefu(String kefuName,String imgPath,String qq,String remark)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			result=kefusDao.addKefu(conn, kefuName, imgPath, qq, remark);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 更新客服信息
	 * @param id
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateKefu(Long id,String kefuName,String imgPath,String qq,String remark)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			  result=kefusDao.updateKefu(conn, id, kefuName, imgPath, qq, remark);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	

	
	/**
	* 删除客服的数据
	* @param commonIds id拼接字符串
	* @param delimiter 分割符
	* @throws DataException
	* @throws SQLException
	* @return int
	*/
	public int deleteKefu(String commonIds, String delimiter) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		int result = -1;
		try {
			result =kefusDao.deleteKefu(conn, commonIds, delimiter);
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
		return result;
	}
	
	/**
	 * 更新用户的客服
	 * @param commonIds
	 * @return
	 * @throws SQLException
	 */
	public long updateUserKefu(String commonIds,String kufuId) throws SQLException{
		Connection conn = MySQL.getConnection();
		String[] fileUrls = commonIds.split(",");//截取;符号 放入String数组  
		long result = -1;
	
			try {
				if (fileUrls != null && fileUrls.length > 0){ 
					for(String userid :fileUrls){
				int index = userid.indexOf("_");
				String uid =  userid.substring(0,index);
				result =	 kefusDao.updateKefuByid(conn, Convert.strToLong(uid,-1),Convert.strToLong(kufuId,-1));
				if(result<0){
					conn.rollback();
					return result;
				}
				}
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
		
		
	
		return result;
	}
	
	/**
	 * 添加客服  (客服分配操作)
	 * @param kefuid
	 * @param userid
	 * @return
	 * @throws SQLException
	 */
	public long updateUserKefu_(long kefuid,long userid) throws SQLException{
		Connection conn = connectionManager.getConnection();
		long result = -1;
		try{
				result =	 kefusDao.updateKefuByid(conn,userid,kefuid);
				if(result<0){
					return result;
				}
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
		return result;
	}
	
	/**
	 * 根据Id获取客服信息详情
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getTeamById(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Map<String, String> map=null;
		try {
			map=kefusDao.getKefuById(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return map;
	}
	
	public void queryKefuPage(PageBean<Map<String,Object>> pageBean)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "t_user_kefu", "*", "", "");
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	public void queryKefuForInfo(PageBean<Map<String,Object>> pageBean,String kefuname,String username,boolean flag)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		
		StringBuffer condition = new StringBuffer();
		if(!flag){
			condition.append(" and t.kefuId is  null or t.kefuId = -1 ");
		if(username!=null){
			condition.append(" AND username  LIKE CONCAT('%','"+StringEscapeUtils.escapeSql(username.trim())+"','%')");
			}
		}else{
		if(kefuname!=null){
		condition.append(" AND kfname  LIKE CONCAT('%','"+StringEscapeUtils.escapeSql(kefuname.trim())+"','%')");
		}
		if(username!=null){
		condition.append(" AND username  LIKE CONCAT('%','"+StringEscapeUtils.escapeSql(username.trim())+"','%')");
		}
		}
		try {
			dataPage(conn, pageBean, " (select a.id,a.username,a.mobilePhone,a.kefuId,b.name as kfname from  t_user a " +
			" left join t_user_kefu b on a.kefuId = b.id " +
			") t", "*", "", condition.toString());
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
			condition = null;
		}
	}
	
	/**
	 * 查询客服列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querykefulist()throws Exception {
		Connection conn=connectionManager.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select id,name from t_user_kefu ");
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 后台查询客服的投资人信息
	 * @param pageBean
	 * @param kefuid
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryKefuOfInvest(PageBean<Map<String,Object>> pageBean,long kefuid)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
//		StringBuffer sbCondition = new StringBuffer();
//		sbCondition.append(" and t.borrowStatus in(4,5) and t.kid = "+kefuid);
		StringBuffer sbTable = new StringBuffer();
		sbTable.append(" (select" +
				" b.borrowStatus as borrowStatus,sum(a.investAmount) as countRealAmount,c.username as username,p.realName,d.id as kid " +
				" from t_invest a left join t_borrow b on a.borrowId = b.id left join t_user c on c.id = a.investor " +
				" left join  t_user_kefu d on d.id = c.kefuId left join t_person p on p.userId = c.id  " +
				"	where borrowStatus in (4,5) and kefuId = " +kefuid+
				"	GROUP BY a.investor ORDER BY countRealAmount DESC ) t "); 
		try {
			dataPage(conn, pageBean, sbTable.toString(), "*", " ", "");
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	/**
	 * 查询客服列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryKefuList() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return kefusDao.queryKefuList(conn, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	/**
	 * 前台获取4个可客服
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryKefuLimit4() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn, "select * from t_user_kefu LIMIT 4");
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}finally{
			conn.close();
		}
	}
	
	
	/**
	 * 添加分类
	 */
	public Long addCategory(String name) throws SQLException{
		Connection conn = connectionManager.getConnection();
		Long returnId = -1L;
		try {
			returnId = categoryDao.addCategory(conn, name);
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
	 * 修改分类的下标
	 * @param ids
	 * @return
	 * @throws SQLException 
	 */
	public Long updateCategoryIndex(String ids) throws SQLException{
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = categoryDao.updateCategoryIndex(conn, ids);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 修改分类
	 * 
	 */
	public Long updateCategory(long id,String helpDescribe) throws SQLException{
		Connection conn = connectionManager.getConnection();
		Long returnId = -1L;
		try {
			returnId = categoryDao.updateCategory(conn, id, helpDescribe);
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
	 * 根据编号查询分类信息
	 * @param id 编号
	 * @return	Map<String,String>
	 * @throws SQLException 
	 * @throws SQLException
	 * @throws DataException 
	 * @throws DataException
	 */
	public Map<String,String> queryCategoryById(Long id) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = categoryDao.queryCategoryById(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return map;
	}
	
	/**
	 * 删除分类
	 * @param categoryId
	 * @return	long
	 * @throws SQLException 
	 * @throws SQLException
	 * @throws DataException 
	 * @throws DataException
	 */
	public long deleteCategory(long categoryId) throws SQLException, DataException{
		Connection conn = MySQL.getConnection();
		
		try {
			categoryDao.deleteCategory(conn, categoryId);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return 1;
	}
	
	
	/**
	 * 查询帮助中心问题分类
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void queryCategoryList(PageBean<Map<String, Object>> pageBean) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "t_help_type", "*", "","");
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

	
	public void queryKefuForInfo(PageBean<Map<String,Object>> pageBean,String kefuname,String username,int flag)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		
		StringBuffer condition = new StringBuffer();
		if(1==flag){
			condition.append(" and (t.kefuId is  null or t.kefuId = -1) ");
		if(username!=null){
			condition.append(" AND username  LIKE CONCAT('%','"+StringEscapeUtils.escapeSql(username.trim())+"','%')");
			}
		}else if(2==flag){
		if(kefuname!=null){
		condition.append(" AND kfname = '"+StringEscapeUtils.escapeSql(kefuname.trim())+"' ");
		}
		if(username!=null){
		condition.append(" AND username  LIKE CONCAT('%','"+StringEscapeUtils.escapeSql(username.trim())+"','%')");
		}
		}else if(3==flag){
			condition.append("");
		}
		try {
			dataPage(conn, pageBean, " (select a.id,a.username,a.mobilePhone,a.kefuId,b.name as kfname from  t_user a " +
			" left join t_user_kefu b on a.kefuId = b.id " +
			") t", "*", "", condition.toString());
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
			condition = null;
		}
	}
	
	
}
