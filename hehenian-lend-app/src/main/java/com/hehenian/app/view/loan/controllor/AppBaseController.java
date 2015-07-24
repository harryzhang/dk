package com.hehenian.app.view.loan.controllor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.hehenian.app.common.PageVO;
import com.hehenian.app.common.exception.SessionException;
import com.hehenian.app.view.loan.common.AppConstants;

public class AppBaseController {

	@Autowired
	public HttpServletRequest request;
	
	public PageVO getPageVO(HttpServletRequest request){
		PageVO page = new PageVO();
		page.setCurrentPage((StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
	                .getParameter("curPage")) : AppConstants.PAGENO));
	    page.setPageSize((StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
	                .getParameter("pageSize")) : AppConstants.PAGESIZE));
	    page.setBeginCount((page.getCurrentPage() - 1) * page.getPageSize());
	    
	    return page;		
	}
	public Map<String,String> getSessionParams(){
		return (Map<String, String>) request.getSession().getAttribute(
                AppConstants.COLOURLIFE_ADMIN_USER);
	}
	
	public List getLoanCidList() throws SessionException{
		List  loanStatusList = new ArrayList();
		List<Map<String,Object>> tempList = (List<Map<String,Object>>) request.getSession().getAttribute("JBCmobile");
		if(tempList!=null){
			for(Map<String,Object> tempMap:tempList){
				if(tempMap.get("cid")!=null){
					loanStatusList.add(tempMap.get("cid"));
				}
			}
			return loanStatusList;

		}else{
			throw new SessionException("session无效，获取小区名失败,请重新登陆");
		}
	}
	public String getCurentCname() throws SessionException{
		Map<String, String> params = this.getSessionParams();
		if(params!=null){
			return StringUtils.isBlank(params.get("areaName"))?"":params.get("areaName");
			
		}else{
			throw new SessionException("session无效，获取小区名失败,请重新登陆");
		}
	}
	
	public String getCurentUserName() throws SessionException{
		 Map<String, String> params = this.getSessionParams();
		 if(params!=null){
			 return StringUtils.isBlank(params.get("userName"))?"":params.get("userName");

		 }else{
			 throw new SessionException("session无效，获取用户名失败,请重新登陆");
		 }
		
	}
	public String getCurentUserId() throws SessionException{
		Map<String, String> params = this.getSessionParams();
		if(params!=null){
			 return StringUtils.isBlank(params.get("userId"))?"":params.get("userId");

		 }else{
			 throw new SessionException("session无效，获取用户ID失败,请重新登陆");
		 }
        
	}
	
	    /** 基于@ExceptionHandler异常处理 */ 
	    @ExceptionHandler  
	    public ModelAndView exp(HttpServletRequest request, Exception ex) {  
	          
	    	ModelAndView mv = new ModelAndView(); 
			if (ex instanceof SessionException) {
		            mv.addObject("message", ex.getMessage());
		        } else if (ex instanceof RuntimeException) {
		        	mv.addObject("message", ex.getMessage());
		        } else {
		        	mv.addObject("message", "数据异常");
		        }
			 mv.setViewName("/common/error");
			return mv;
	}  
}
