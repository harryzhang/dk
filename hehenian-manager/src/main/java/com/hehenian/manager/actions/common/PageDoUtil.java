/**
 * @auther liminglmf
 * @date 2015年5月7日
 */
package com.hehenian.manager.actions.common;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.manager.commons.NewPagination;

/**
 * @author liminglmf
 *
 */
public class PageDoUtil {
	
	/**
	 * 渠道转过来的分页请求信息放到查询分页中
	 * @auther liminglmf
	 * @date 2015年5月7日
	 * @param pagination
	 * @return
	 */
	public static PageDo getPage(NewPagination pagination){
		PageDo page = new PageDo();
		page.setPageNum(pagination.getPage());
		page.setPageSize(pagination.getRows());
		return page;
	}
	
	/**
	 * 转移分页数据
	 * @auther liminglmf
	 * @date 2015年5月7日
	 * @param page
	 * @param pagination
	 * @return pagination
	 */
	public static NewPagination getPageValue(NewPagination pagination,PageDo page){
		pagination.setPage(Integer.parseInt(page.getPageNum()+""));
		pagination.setRows(Integer.parseInt(page.getPageSize()+""));
		pagination.setDatas(page.getPage());
		int total = Integer.parseInt(page.getTotalNum()+"");
		pagination.setTotal(total);
		return pagination;
	}

}
