package com.hehenian.manager.commons;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用分页排序类
 */
public class NewPagination<T> {
	private static final int DEFAULT_PAGE = 1;
	public static int DEFAULT_PAGE_SIZE = 20;
	private int rows = DEFAULT_PAGE_SIZE;
	private int total;
	private List<T> datas;
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
	public NewPagination(){
	}
	public NewPagination(int pageSize, int page) {
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
	
	public void setPageSize(int pageSize) {
		if(getRows()>0){
			this.pageSize = getRows();
		}else{
			this.pageSize = pageSize;
		}
	}

	public List<T> getDatas() {
		if(null==this.datas)
			this.datas=new ArrayList<T>();
		return datas;
	}

	public int getTotal() {
		return total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
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

	public void setPage(int page) {
		this.page = page;
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
	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	
}