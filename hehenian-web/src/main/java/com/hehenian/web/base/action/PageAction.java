package com.hehenian.web.base.action;

import org.apache.commons.lang.StringUtils;

import com.hehenian.biz.common.base.dataobject.PageDo;


/**
 * @author harry
 * @date:Oct 15, 2010 8:32:16 AM
 * @category
 * @version :
 */
public class PageAction<T> extends BaseAction {

    private static final long serialVersionUID = 3850298101952137636L;
    
	public PageDo<T> pageBean;//存放分页信息
	
	public PageAction() {		
		this.pageBean = new PageDo<T>(10);
	}

	public PageDo<T> getPageBean() {
		String curPage = request().getParameter("curPage");//当前页
		String pageSize = request().getParameter("pageSize");//每页的条数
		if(StringUtils.isNotBlank(curPage)){
		    long currentP = 1;
		    try{
		        currentP = Long.parseLong(curPage);
		    }catch(Exception e){
		        currentP = 1;
		    }
			pageBean.setPageNum(currentP);
		}
		
		if(StringUtils.isNotBlank(pageSize)){
		    int pSize = 10;
		    try{
		        pSize = Integer.parseInt(pageSize);
		    }catch(Exception e){
		        pSize = 10;
		    }
			pageBean.setPageSize(pSize);
		}
		return pageBean;
	}

}
