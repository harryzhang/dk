package com.sp2p.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.QuestionnaireService;

public class QuestionnaireAction extends BasePageAction  {
	QuestionnaireService questionnaireService;
	List<Map<String, Object>>  options;
	
	public List<Map<String, Object>> getOptions() {
		return options;
	}
	public void setOptions(List<Map<String, Object>> options) {
		this.options = options;
	}
	public static Log log =LogFactory.getLog(QuestionnaireAction.class);
	public QuestionnaireService getQuestionnaireService() {
		return questionnaireService;
	}
	public void setQuestionnaireService(QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}
	public String queryQuestionnaireInit(){
		
		return SUCCESS;
	}
	public String queryQuestionnaireList() throws Exception{
		try {
			questionnaireService.queryQuestionnaire(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return SUCCESS;
	}
	public String updateQuestionnaireInit() throws Exception{
		Long id = Convert.strToLong(request("id"),0);
		try {
			options= questionnaireService.queryOption(id);
			paramMap=questionnaireService.queryQuestionnaire(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return SUCCESS;
	}
	public String  addQuestionnaireInit(){
		return SUCCESS;
	}
	
	public String  addQuestionnaire()throws Exception{
		String question = SqlInfusion.FilteSqlInfusion(paramMap.get("question"));
		int maxScore= Convert.strToInt(paramMap.get("maxScore"), 0); 
		int status= Convert.strToInt(paramMap.get("status"), 0); 
		String options = SqlInfusion.FilteSqlInfusion(paramMap.get("options"));
		String scores = SqlInfusion.FilteSqlInfusion(paramMap.get("sorces"));
		Long type = Convert.strToLong(paramMap.get("type"), 0); 
		Long questionType = Convert.strToLong(paramMap.get("questionType"), 0); 
		try {
			questionnaireService.addQuestionnaire(question, maxScore, status, options, scores,type,questionType);
			JSONUtils.printStr("1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			JSONUtils.printStr("2");
			return INPUT;
		}
		return null;
	}
	
	public String  updateOption() throws Exception{
		int score= Convert.strToInt(paramMap.get("score"), 0); 
		Long id= Convert.strToLong(paramMap.get("id"), 0); 
		String option = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("option")),"");
		Long questionId = Convert.strToLong(paramMap.get("questionId"), 0); 
		try {
			questionnaireService.updateOption(score, option, id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			JSONUtils.printStr("1");
		}
		options= questionnaireService.queryOption(questionId);
		JSONUtils.printArray(options);
		return null;
	}
	public String  addOption() throws Exception{
		int score= Convert.strToInt(paramMap.get("score"), 0); 
		String option = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("option")),"");
		Long questionId = Convert.strToLong(paramMap.get("questionId"), 0); 
		
		try {
			questionnaireService.addOption( score, option, questionId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			JSONUtils.printStr("1");
		}
		options= questionnaireService.queryOption(questionId);
		JSONUtils.printArray(options);
		return null;
	}
	
	public String updateQuestionnaire() throws Exception{
		String question = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("question")),"");
		Long id = Convert.strToLong(paramMap.get("id"), 0); 
		int maxScore= Convert.strToInt(paramMap.get("maxScore"), 0); 
		int status= Convert.strToInt(paramMap.get("status"), 0); 
		Long type = Convert.strToLong(paramMap.get("type"), 0); 
		Long questionType = Convert.strToLong(paramMap.get("questionType"), 0); 
		try {
			questionnaireService.updateQuestionnaire(maxScore, question, id, status,type,questionType);
			JSONUtils.printStr("1");
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtils.printStr("2");
			log.error(e);
		}
		return null;
	}
	public String  deleteOption() throws Exception{
		Long id= Convert.strToLong(paramMap.get("id"), 0); 
		Long questionId = Convert.strToLong(paramMap.get("questionId"), 0); 
		try {
			questionnaireService.deleteOption(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			JSONUtils.printStr("1");
		}
		options= questionnaireService.queryOption(questionId);
		JSONUtils.printArray(options);
		return null;
	}
	public String  deleteQuestionnaire() throws Exception{
		String id=request("id"); 
		try {
			questionnaireService.deleteQuestionnaire(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return SUCCESS;
	}
}
