package com.sp2p.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_news;


public class QuestionnaireDao {
	
	
	public Map<String, String> queryQuestionnaire(Connection conn,Long id) throws Exception{
		Dao.Tables.t_questionnaire t_questionnaire = new Dao().new Tables().new t_questionnaire();
		DataSet dataSet = t_questionnaire.open(conn, "*", " id=" + id ,
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryOption(Connection conn,Long questionId) throws Exception{
		Dao.Tables.t_option t_option = new Dao().new Tables().new t_option();
		DataSet dataSet = t_option.open(conn, " * ", " questionId =  " + questionId, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Long addQuestionnaire(String question ,int maxSorce , int status,Connection conn, Long type, Long questionType) throws Exception{
		Dao.Tables.t_questionnaire t_questionnaire = new Dao().new Tables().new t_questionnaire();
		t_questionnaire.status.setValue(status);
		t_questionnaire.maxScore.setValue(maxSorce);
		t_questionnaire.question.setValue(question);
		t_questionnaire.type.setValue(type);
		t_questionnaire.questionType.setValue(questionType);
		return t_questionnaire.insert(conn);
	}
	
	public Long addOption(String option ,Long questionId , int score,Connection conn) throws Exception{
		Dao.Tables.t_option t_option = new Dao().new Tables().new t_option();
		t_option.score.setValue(score);
		t_option.option.setValue(option);
		t_option.questionId.setValue(questionId);
		
		return t_option.insert(conn);
	}
	public Long deleteOption( Long id,Connection conn) throws Exception{
		Dao.Tables.t_option t_option = new Dao().new Tables().new t_option();
		return t_option.delete(conn, " id = " + id);
	}
	public Long updateOption(Long id,int score ,String option,Connection conn ) throws Exception{
		Dao.Tables.t_option t_option = new Dao().new Tables().new t_option();
		t_option.score.setValue(score);
		t_option.option.setValue(option);
		
		 return t_option.update(conn, "id=" + id);
	}
	public Long updateQuestionnaire(int maxScore ,String question, Long id,int status,Connection conn, Long type, Long questionType ) throws Exception{
		Dao.Tables.t_questionnaire t_questionnaire = new Dao().new Tables().new t_questionnaire();
		t_questionnaire.status.setValue(status);
		t_questionnaire.maxScore.setValue(maxScore);
		t_questionnaire.question.setValue(question);
		t_questionnaire.type.setValue(type);
		t_questionnaire.questionType.setValue(questionType);
		 return t_questionnaire.update(conn, "id=" + id);
	}
	public Long deleteQuestionnaire(String id,Connection conn ) throws Exception{
		Dao.Tables.t_questionnaire t_questionnaire = new Dao().new Tables().new t_questionnaire();
		 return t_questionnaire.delete(conn, "id in (" + id + ")");
	}
	public Long deleteOptionByParentID( String id,Connection conn) throws Exception{
		Dao.Tables.t_option t_option = new Dao().new Tables().new t_option();
		return t_option.delete(conn, " questionId in (" + id+")");
	}
}
