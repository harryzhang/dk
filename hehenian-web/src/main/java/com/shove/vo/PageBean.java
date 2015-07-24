package com.shove.vo;
 
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 杨程
 * @date:Oct 15, 2010 9:14:44 AM
 * @category
 * @version :
 * 
 */
public class PageBean<T> implements Serializable {
	private static final long serialVersionUID = -8652652596351231066L;
	public long pageNum;// 当前页数(>0) 状态
	private long totalNum; // 总记录数(>=0) 读写
	private long totalPageNum; // 总页数(>0) 只读
	private int pageSize; // 每页的记录数(>0) 初始化
	private List<T> page; // 当前页中存放的记录 读写
	public static Log log = LogFactory.getLog(PageBean.class);
	public PageBean(int pageSize) {
		this.pageNum = 1L;
		this.pageSize = pageSize;
	}

	public PageBean() {
		this(10);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param pageNo
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public long getStartOfPage() {
		return (this.pageNum - 1) * pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<T> getPage() {
		return page;
	}

	public long getPageNum() {
		this.pageNum=this.pageNum<=0?1:this.pageNum;
		return pageNum;
	}

	@SuppressWarnings("unused")
	public void setPageNum(Object pageNum) {
		if(pageNum instanceof String[]){
			String[] pageStr = (String[]) pageNum;
			if (StringUtils.isBlank(pageStr[0])) {
				this.pageNum = 1L;
				return;
			}
			try {
				this.pageNum = Integer.parseInt(pageStr[0]);
				this.pageNum=this.pageNum<=0?1:this.pageNum;
			} catch (Exception e) {
				log.error(e);
				this.pageNum = 1L;
			}
		}
		if (pageNum instanceof String) {
			String pageStr = (String) pageNum;
			if (StringUtils.isBlank(pageStr)) {
				this.pageNum = 1L;
				return;
			}
			try {
				this.pageNum = Integer.parseInt(pageStr);
				this.pageNum=this.pageNum<=0?1:this.pageNum;
			} catch (Exception e) {
				log.error(e);
				this.pageNum = 1L;
			}
		}

		if (pageNum instanceof Integer) {
			if (pageNum == null) {
				this.pageNum = 1;
				return;
			}
			try {
				this.pageNum = (Integer) pageNum;
				this.pageNum=this.pageNum<=0?1:this.pageNum;
			} catch (Exception e) {
				log.error(e);
				this.pageNum = 1;
			}
		}
	}

	public long getTotalNum() {
		return totalNum;
	}

	/**
	 * return true : 可以继续查询
	 * return false: 没有记录
	 * @param totalNum
	 * @return
	 */
	public boolean setTotalNum(long totalNum) {
		this.totalNum = totalNum;

		if (this.totalNum == 0) {
			this.totalPageNum = 0;
			return false;
		} else {
			this.totalPageNum = this.totalNum / this.pageSize;
			if (this.totalNum % this.pageSize > 0) {
				this.totalPageNum++;
			}
		}
		this.pageNum = this.pageNum > this.totalPageNum ? this.totalPageNum
				: this.pageNum;
		return true;
	}
	
	public long getTotalPageNum() {
		return totalPageNum;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPage(List<T> page) {
		this.page = page;
	}
}
