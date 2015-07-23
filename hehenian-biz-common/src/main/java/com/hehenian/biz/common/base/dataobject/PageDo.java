package com.hehenian.biz.common.base.dataobject;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 分页对象
 * 
 * @author: liuzg
 * @date 2014年7月7日 下午2:17:18
 */
public class PageDo<T> implements Serializable {
	
    private static final long serialVersionUID = 103177325764334271L;

	public long pageNum;// 当前页数(>0) 状态
	private long totalNum; // 总记录数(>=0) 读写
	private long totalPageNum; // 总页数(>0) 只读
	private int pageSize; // 每页的记录数(>0) 初始化
	private List<T> page; // 当前页中存放的记录 读写
	
	public PageDo(int pageSize) {
		this.pageNum = 1L;
		this.pageSize = pageSize;
	}

	public PageDo() {
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

	public void setPageNum(long pageNum) {
	    this.pageNum = pageNum;
        this.pageNum=this.pageNum<=0?1:this.pageNum;
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
	
	public void setTotalPageNum(long totalPageNum) {
		this.totalPageNum = totalPageNum;
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
	
	
	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pageNum",getPageNum())
            .append("totalNum",getTotalNum())
            .append("totalPageNum",getTotalPageNum())
            .append("pageSize",getPageSize())
            .toString();
    }
}
