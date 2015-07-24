package com.sp2p.dao;

import java.sql.Connection;

import com.sp2p.database.Dao;

public class QuestionDao {
	
	public void deleteQuestion(Connection conn , String id) throws Exception{
		Dao.Tables.t_question t_question = new Dao().new Tables().new t_question();
		t_question.delete(conn, "id in (" +id+")");
		
	}

}
