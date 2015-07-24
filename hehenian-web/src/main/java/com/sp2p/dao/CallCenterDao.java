package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 客服中心帮助问题处理
 * @author li.hou
 *
 */
public class CallCenterDao {
	/**   
	 * @MethodName: queryHelpList  
	 * @Param: CallCenterDao  
	 * @Author: li.hou
	 * @Date: 2013-3-6 下午 1:48:28
	 * @Return:    Map<String, String>
	 * @Descb: 查询客服中心帮助问题列表
	 * @Throws: SQLException, DataException
	*/
	public List<Map<String,Object>> queryHelpList(Connection conn,int limitStart,int limitCount)
			throws SQLException, DataException {
		Dao.Views.v_t_callcenter_help_list helpList = new Dao().new Views().new v_t_callcenter_help_list();
		DataSet dataSet = helpList.open(conn, "*", "", " order by typeId", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryHelpTypeList(Connection conn,String field,int limitStart,int limitCount)
			throws SQLException, DataException {
		Dao.Tables.t_help_type helpList = new Dao().new Tables().new t_help_type();
		DataSet dataSet = helpList.open(conn, field, "", "  sortId", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String, String> helpQueyNewViewById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_help_type helpList = new Dao().new Tables().new t_help_type();
		DataSet dataSet = helpList.open(conn, " id,helpDescribe ", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> queryAnswerById(Connection conn, long id) throws SQLException, DataException{
		Dao.Views.v_t_callcenter_help_list t_helpAnswer = new Dao().new Views().new v_t_callcenter_help_list();
		DataSet dataSet = t_helpAnswer.open(conn, " question,anwer", " questionId=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Long addHelp(Connection conn,String title,Long helpId,Long serialCount,String content,String publisher,String publishTime) throws SQLException, DataException{
		Dao.Tables.t_help_question tquestion = new Dao().new Tables().new t_help_question();
		tquestion.sortid.setValue(serialCount);
		if(title != null)
			tquestion.questionDescribe.setValue(title);
		if(helpId!= null && helpId > 0)
			tquestion.helpTypeId.setValue(helpId);
		if(publisher != null){
			Map<String,String> vals = getAdminIdByName(conn,publisher);
			if(vals != null){
				tquestion.publisher.setValue(vals.get("id"));
			}
		}
		if(publishTime != null)
			tquestion.publishTime.setValue(publishTime);
		else
			tquestion.publishTime.setValue(new Date());
		
		tquestion.browseCount.setValue(0);
		long questionId = tquestion.insert(conn);
		
		Dao.Tables.t_help_answer tanswer = new Dao().new Tables().new t_help_answer();
		tanswer.helpQuestionId.setValue(questionId);
		tanswer.helpAnswer.setValue(content);
		tanswer.insert(conn);
		return questionId;
	}
	
	public String getTypeIdByDes(Connection conn,String typeDes) throws SQLException, DataException{
		Dao.Tables.t_help_type helpList = new Dao().new Tables().new t_help_type();
		DataSet dataSet = helpList.open(conn, "id", " and helpDescribe='"+StringEscapeUtils.escapeSql(typeDes)+"'", " ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return BeanMapUtils.dataSetToMap(dataSet).get(0);
	}
	
	/**
	 * 根据类型id获得对应的描述
	 * @param conn
	 * @param typeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getDesByTypeId(Connection conn,Long typeId) throws SQLException, DataException{
		Dao.Tables.t_help_type helpList = new Dao().new Tables().new t_help_type();
		DataSet dataSet = helpList.open(conn, " helpDescribe ", "  id="+typeId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
//		DataSet dataSet = helpList.open(conn, "helpDescribe", " id = "+typeId, " ", -1, -1);
//		dataSet.tables.get(0).rows.genRowsMap();
//		return BeanMapUtils.dataSetToMap(dataSet).get(0);
	}
	
	/**
	 * 根据问题id找到该问题所属的帮助类型id
	 * @param conn
	 * @param typeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getTypeId(Connection conn,Long questionId) throws SQLException, DataException{
		Dao.Views.v_t_callcenter_help_list helpList = new Dao().new Views().new v_t_callcenter_help_list();
		DataSet dataSet = helpList.open(conn, " typeId ", " questionId="+questionId , " ", -1, -1);
		
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> getMaxSerial(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_help_question helpList = new Dao().new Tables().new t_help_question();
		DataSet dataSet = helpList.open(conn, " max(id) as max ", "", "", -1, -1);
		
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据问题ID查找到相对应的问题信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String,String> queryQuestionById(Connection conn,long id) throws DataException, SQLException{
		Dao.Tables.t_help_question news = new Dao().new Tables().new t_help_question();
		DataSet dataSet= news.open(conn, "", " id="+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Long deleteQuestion(Connection conn,String ids) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_help_question question = new Dao().new Tables().new t_help_question();
		return question.delete(conn, " id in("+idSQL+")");
	}
	
	public Long deleteQuestionInfo(Connection conn,String ids) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_help_question question = new Dao().new Tables().new t_help_question();
	    question.delete(conn, " id in("+idSQL+")");
	    Dao.Tables.t_help_answer answer = new Dao().new Tables().new t_help_answer();
	    answer.delete(conn, " helpQuestionId in("+idSQL+")");
	    return 1L;
	}
	
	public void deleteQuetions(Connection conn,String commonIds, String delimiter) throws SQLException, DataException{
//		DataSet dataSet = new DataSet();
//		List<Object> outParameterValues = new ArrayList<Object>();
//		Dao.Tables.T_HELP_QUESTION.
//		Dao.Procedures.p_deleteNews(conn, dataSet, outParameterValues, commonIds, delimiter);
	}
	
	/**
	 * 根据帮助问题id，查找对应id的数据信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queyHelpViewById(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_callcenter_help_list v_t_help_list = new Dao().new Views().new v_t_callcenter_help_list();
		DataSet dataSet = v_t_help_list.open(conn, " * ", " questionId=" + id , " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Long updateHelps(Connection conn,String question,String content,Long typeId,Long id,String publisher,String publishTime,Long serialCount) throws SQLException, DataException{
		Dao.Tables.t_help_question questions = new Dao().new Tables().new t_help_question();
		if(question!=null){
			questions.questionDescribe.setValue(question);
		}
		if(typeId!=null&&typeId>0){
			questions.helpTypeId.setValue(typeId);
		}
		if(publisher!=null){
			Map<String,String> vals = getAdminIdByName(conn,publisher);
			if(vals != null){
				questions.publisher.setValue(vals.get("id"));
			}
		}
		if(publishTime!=null){
			questions.publishTime.setValue(publishTime);
		}
		if(serialCount!=null&&serialCount>0){
			questions.sortid.setValue(serialCount);
		}
	    questions.update(conn, " id="+id);//修改问题列表
	    Dao.Tables.t_help_answer answers = new Dao().new Tables().new t_help_answer();
	    if(content != null){
	    	answers.helpAnswer.setValue(content);
	    	answers.update(conn, "helpquestionid ="+id);//修改答案列表
	    }

	    return 1L;
	}
	
	/**
	 * 根据admin用户名查找admin用户Id
	 * @param conn
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	private Map<String,String> getAdminIdByName(Connection conn,String name) throws SQLException, DataException{
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		DataSet ds = t_admin.open(conn, "", " username = '"+StringEscapeUtils.escapeSql(name)+"'", "",-1,-1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 修改问题点击浏览量
	 * @param conn
	 * @param userId
	 * @param money
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public Long updateQuestionBrowse(Connection conn,Long questionId) throws SQLException{
		return Database.executeNonQuery(conn,
				" update t_help_question set browseCount = browseCount+1" 
						+" where id=" + questionId);
	}
	
	public List<Map<String,Object>> queryQuestionsLimit5(Connection conn,
			long helpId,int limitStart,int limitCount)throws SQLException, DataException {
		/**
		 * 显示5条数据
		 */
		String command = "select questionDescribe from t_help_question where helpTypeId="+helpId+" limit 0,5";
		DataSet dataSet = Database.executeQuery(conn, command);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	
	/**
	 * 根据admin用户Id查找admin用户名
	 * @param conn
	 * @param adminId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
//	private Map<String,String> getAdminNameById(Connection conn,long adminId) throws SQLException, DataException{
//		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
//		DataSet ds = t_admin.open(conn, "", " id = "+adminId, "",-1,-1);
//		return BeanMapUtils.dataSetToMap(ds);
//	}
}
