package com.hehenian.manager.commons;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用分页排序类
 */
public class Pagination<T> {
	private static final int DEFAULT_PAGE = 1;
	public static int DEFAULT_PAGE_SIZE = 20;
	private List<T> rows;
	private int total;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int page = DEFAULT_PAGE;
	private String exceTime;
	private String exceSql;
	private String sortname;
	private String sortorder;
	public String getExceTime() {
		return exceTime;
	}
	public void setExceTime(String exceTime) {
		this.exceTime = exceTime;
	}
	public String getExceSql() {
		return exceSql;
	}
	public void setExceSql(String exceSql) {
		this.exceSql = exceSql;
	}
	public Pagination(){
	}
	public Pagination(int pageSize, int page) {
		if (pageSize < 1) {
			throw new IllegalArgumentException("Count should be greater than zero!");
		} else if (page < 1) {
			page = 1;
		} else {
			this.pageSize = pageSize;
			this.page = page;
		}
	}
	
	public String getCount(){
		return this.getTotal()+"";
	}
	
	public void setPageSize(int countOnEachPage) {
		this.pageSize = countOnEachPage;
	}

	public List<T> getRows() {
		if(null==this.rows)
			this.rows=new ArrayList<T>();
		return rows;
	}

	public int getTotal() {
		return total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setRows(List<T> items) {
		this.rows = items;
	}

	public void setTotal(int totalCount) {
		this.total = totalCount;
	}
	

	public int getPageCount() {
		return (total == 0) ? 1 : ((total % pageSize == 0) ? (total / pageSize)
				: (total / pageSize) + 1);
	}

	public int getPreviousPage() {
		if(page > 1) return page - 1;
		else return DEFAULT_PAGE;
	}
	
	public int getNextPage() {
		if(page < getPageCount()) return page + 1;
		else return getPageCount();
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int currentPage) {
		this.page = currentPage;
	}
	
	public int getStart() {
		return (this.page - 1) * pageSize ;//+ 1;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
}