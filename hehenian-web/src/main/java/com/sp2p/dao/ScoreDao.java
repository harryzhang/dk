package com.sp2p.dao;

import java.sql.Connection;

import com.sp2p.database.Dao;

public class ScoreDao {
	
	public long addScore(long userId ,int total,int questionScore,int degree,int degreeScore,int internet,int intnetScore,
			int predilection,int prediScore,Connection conn,int attitude, int levels)throws Exception{
		Dao.Tables.t_score t_score = new Dao().new Tables() .new t_score();
		t_score.userId.setValue(userId);
		t_score.total.setValue(total);
		t_score.questionScore.setValue(questionScore);
		t_score.degree.setValue(degree);
		t_score.degreeScore.setValue(degreeScore);
		t_score.internet.setValue(internet);
		t_score.intnetScore.setValue(intnetScore);
		t_score.predilection.setValue(predilection);
		t_score.prediScore.setValue(prediScore);
		return t_score.insert(conn);
		
	}
	
}
