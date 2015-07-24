package com.sp2p.action.admin;

import com.shove.Convert;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.service.QuestionService;

public class QuestionAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private QuestionService questionService;
	
	public QuestionService getQuestionService() {
		return questionService;
	}
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
	public String queryQuestionListInit(){
		
		return SUCCESS;
	}
	public String queryQuestionList(){
		try {
			String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("username")), "");
			String keyword = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("keyword")),"");
			questionService.queryQuestionList(pageBean,keyword,username);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String deleteQuestion(){
		String id = request("id");
		try {
			
			questionService.deleteQuestion(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String updateQuestionStatus() throws Exception{
		int askStatus = Convert.strToInt(paramMap.get("askStatus"), -1);
		int forwardStatus = Convert.strToInt(paramMap.get("forwardStatus"), -1);
		try {
			
			if(askStatus!=-1){
				IConstants.ASK_STATUS=askStatus;
			}
			if(forwardStatus!=-1){
				IConstants.FORWARD_STATUS=forwardStatus;
			}
			JSONUtils.printStr("1");
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtils.printStr("2");
		}
		return null;
	}
}
