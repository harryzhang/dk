package com.hehenian.manager.actions.common;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.manager.actions.common.exception.SessionException;

public class BaseController<T> implements Serializable {

	protected transient final Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	public final String AJAX_PAGE = "ajaxDone";
	public final String SUCCESS = "success";
	public final String CLOSECURRENT = "closeCurrent";
	
	public final String ADD_SUCCESS = "添加成功 ！";
	public final String UPDATE_SUCCESS = "修改成功 ！";
	public final String DELETE_SUCCESS = "删除成功 ！";
	public final String ADD_FAILURE = "添加失败 ！";
	public final String UPDATE_FAILURE = "修改失败 ！";
	public final String DELETE_FAILURE = "删除失败 ！";
	public final String UN_EXIST = "不存在该记录 ！";
	public final String EXIST = "已存在该记录 ！";
	

	@Autowired
	protected HttpServletRequest request;
	@InitBinder
    public void initBinderCalendar(WebDataBinder binder) {
    	//binder.registerCustomEditor(Calendar.class, new CalendarEditor());
    }
	/*
	 * @param msg : 需要在页面显示的消息
	 * 默认【关闭】标签页面
	 * 默认navTabId即页面的需要刷新的页面rel的值为action名称
	 * */
	protected ModelAndView ajaxSuccess(String msg) {
		ModelAndView mav = new ModelAndView(AJAX_PAGE);
		mav.addObject("callbackType", CLOSECURRENT);
		mav.addObject("statusCode", 200);
		mav.addObject("message", msg);
		mav.addObject("navTabId", this.getClass().getSimpleName().toLowerCase());
		return mav;
	}
	protected ModelAndView ajaxSuccessAndRefresh(String msg) {
		ModelAndView mav = new ModelAndView(AJAX_PAGE);
		mav.addObject("callbackType", "");
		mav.addObject("statusCode", 200);
		mav.addObject("message", msg);
		mav.addObject("navTabId", this.getClass().getSimpleName().toLowerCase());
		return mav;
	}
	/*
	 * @param msg : 需要在页面显示的消息
	 * @param isClose:是否关闭标签页面，true关闭，false 不关闭
	 * 默认navTabId即页面的需要刷新的页面rel的值为action名称
	 * */
	public ModelAndView ajaxSuccess(String msg, boolean isClose) {
		ModelAndView mav = new ModelAndView(AJAX_PAGE);
		mav.addObject("callbackType", isClose ? CLOSECURRENT : "");
		mav.addObject("statusCode", 200);
		mav.addObject("message", msg);
		mav.addObject("navTabId", this.getClass().getSimpleName().toLowerCase());
		return mav;
	}
	
	/*
	 * @param msg : 需要在页面显示的消息
	 * @param isClose:是否关闭标签页面，true关闭，false 不关闭
	 * @param navTabId:标签页ID
	 * */
	public ModelAndView ajaxSuccess(String msg, boolean isClose, String navTabId) {
		ModelAndView mav = new ModelAndView(AJAX_PAGE);
		mav.addObject("callbackType", isClose ? CLOSECURRENT : "");
		mav.addObject("statusCode", 200);
		mav.addObject("message", msg);
		mav.addObject("navTabId", navTabId);
		return mav;
	}
	
  
	/*
	 * @param msg : 需要在页面显示的消息
	 * 默认【不关闭】标签页面
	 * 默认navTabId即页面的需要刷新的页面rel的值为action名称
	 * */
	protected ModelAndView ajaxFailure(String msg) {
		ModelAndView mav = new ModelAndView(AJAX_PAGE);
		mav.addObject("callbackType", "");
		mav.addObject("statusCode", 300);
		mav.addObject("message", msg);
		mav.addObject("navTabId", this.getClass().getSimpleName().toLowerCase());
		return mav;
	}
	
	/*
	 * @param msg : 需要在页面显示的消息
	 * @param isClose:是否关闭标签页面，true关闭，false 不关闭
	 * 默认navTabId即页面的需要刷新的页面rel的值为action名称
	 * */
	public ModelAndView ajaxFailure(String msg, boolean isClose) {
		ModelAndView mav = new ModelAndView(AJAX_PAGE);
		mav.addObject("callbackType", CLOSECURRENT);
		mav.addObject("statusCode", 300);
		mav.addObject("message", msg);
		mav.addObject("navTabId", this.getClass().getSimpleName().toLowerCase());
		return mav;
	}
	
	/*
	 * @param msg : 需要在页面显示的消息
	 * @param isClose:是否关闭标签页面，true关闭，false 不关闭
	 * @param navTabId:标签页ID
	 * */
	public ModelAndView ajaxFailure(String msg, boolean isClose, String navTabId) {
		ModelAndView mav = new ModelAndView(AJAX_PAGE);
		mav.addObject("callbackType", isClose ? CLOSECURRENT : "");
		mav.addObject("statusCode", 200);
		mav.addObject("message", msg);
		mav.addObject("navTabId", navTabId);
		return mav;
	}
 
	public PageDo getPage(){
		PageDo page = new PageDo(); 
		String curPage = request.getParameter("pageNum");//当前页
		String pageSize = request.getParameter("numPerPage");//每页的条数
		if(StringUtils.isNotBlank(curPage)){
		    long currentP = 1;
		    try{
		        currentP = Long.parseLong(curPage);
		        if(currentP < 1){
		        	 currentP = 1;
		        }
		    }catch(Exception e){
		        currentP = 1;
		    }
		    page.setPageNum(currentP);
		}
		
		if(StringUtils.isNotBlank(pageSize)){
		    int pSize = Constants.PAGESIZE;
		    try{
		        pSize = Integer.parseInt(pageSize);
		        if(pSize < 1){
		        	pSize = Constants.PAGESIZE;
		        }
		    }catch(Exception e){
		        pSize =Constants.PAGESIZE;
		    }
		    page.setPageSize(pSize);
		}
		
		return page;
	}
	
	
	/** 基于@ExceptionHandler异常处理 */ 
    @ExceptionHandler  
    public ModelAndView exp(HttpServletRequest request, Exception ex) {  
          
    	ModelAndView mv = new ModelAndView(); 
		if (ex instanceof SessionException) {
			  return this.ajaxFailure(ex.getMessage());
	        } else if (ex instanceof RuntimeException) {
			  return this.ajaxFailure(ex.getMessage());

	        } else {
				  return this.ajaxFailure("数据异常");
	        }
		
}  

}
