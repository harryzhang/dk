package com.hehenian.app.common;

public class PageVO {

	private long pageSize;
	
	private long currentPage;
	
	private long totalPage;
	
	private long beginCount;

	

	

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getBeginCount() {
		return (beginCount < 0 ? 0 : beginCount);
	}

	public void setBeginCount(long beginCount) {
		this.beginCount = beginCount;
	}
	
	
}
