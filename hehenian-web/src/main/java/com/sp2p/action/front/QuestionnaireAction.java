package com.sp2p.action.front;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.util.JSONUtils;
import com.shove.web.action.BasePageAction;
import com.sp2p.entity.User;
import com.sp2p.service.QuestionnaireService;
import com.sp2p.service.admin.ScoreService;

public class QuestionnaireAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5405172590794425509L;
	private QuestionnaireService questionnaireService ;
	private ScoreService scoreService;
	
	public QuestionnaireService getQuestionnaireService() {
		return questionnaireService;
	}

	public void setQuestionnaireService(QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	public ScoreService getScoreService() {
		return scoreService;
	}

	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	public String queryAllQuestionnaire() {
		try {
			List<Map<String, Object>>  questions = questionnaireService.queryQuestionnaire();
			List<Map<String, Object>>  options = questionnaireService.queryOption();
			request().setAttribute("questions", questions);
			request().setAttribute("options", options);
			request().setAttribute("questionsize", questions.size());
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		return SUCCESS;
	}
	public String addScore() throws Exception{
		Long user =this.getUserId();
		scoreService.queryScoreById(pageBean, user);
		if(pageBean.getPage()!=null){
			JSONUtils.printStr("3");
			return null;
		}
		int total = Convert.strToInt(paramMap.get("total"), 0);
		int questionScore = Convert.strToInt(paramMap.get("total"), 0);
		int internet = Convert.strToInt(paramMap.get("internet"), 0);
		int intnetScore = Convert.strToInt(paramMap.get("intnetScore"), 0);
		int degree = Convert.strToInt(paramMap.get("degree"), 0);
		int degreeScore = Convert.strToInt(paramMap.get("degreeScore"), 0);
		int predilection = Convert.strToInt(paramMap.get("predilection"), 0);
		int prediScore = Convert.strToInt(paramMap.get("prediScore"), 0);
		try{
			scoreService.addScore(user, total, questionScore, degree, degreeScore, internet, intnetScore, predilection, prediScore);
			JSONUtils.printStr("1");
		}catch (Exception e) {
			JSONUtils.printStr("2");
		}
		return null;
	}

}
