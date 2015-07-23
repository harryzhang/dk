package com.hehenian.biz.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: JsonPage 
* @Description: 分页对象
* @author yeqiagn
* @param <T> 泛型变量 实际使用中调用户
*/
public class JsonPage<T> implements Serializable{
	/** 
	* @Fields serialVersionUID : TODO 
	*/ 
	private static final long serialVersionUID = -6310539046385611435L;
	/**
	 * @Fields pageSize : 每页显示条数
	 */
	int pageSize = 0;
	/**
	 * @Fields totalSize : 总页数
	 */
	int totalSize = 0;
	/**
	 * @Fields pgeNow : 当前页码
	 */
	int pageNow = 0;
	/**
	 * @Fields durin : 页面排序关键子
	 */
	int durin = 0;
	/**
	 * @Fields list : 查询结果存放list
	 */
	List<T> list = new ArrayList<T>();

	/** 
	* @Title getPageSize 
	* @Description TODO
	* @return
	*/
	public int getPageSize() {
		return pageSize;
	}

	/** 
	* @Title setPageSize 
	* @Description TODO
	* @param pageSize 每页显示条数
	*/
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** 
	* @Title getTotalSize 
	* @Description TODO
	* @return
	*/
	public int getTotalSize() {
		return totalSize;
	}

	/** 
	* @Title setTotalSize 
	* @Description TODO
	* @param totalSize 总条数
	*/
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/** 
	* @Title getPageNow 
	* @Description TODO
	* @return
	*/
	public int getPageNow() {
		return pageNow;
	}

	/** 
	* @Title setPageNow 
	* @Description TODO
	* @param pageNow 当前页码
	*/
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	/** 
	* @Title getDurin 
	* @Description TODO
	* @return
	*/
	public int getDurin() {
		return durin;
	}

	/** 
	* @Title getList 
	* @Description TODO
	* @return
	*/
	public List<T> getList() {
		return list;
	}
	/** 
	* @Title setList 
	* @Description TODO
	* @param list 分页数据
	*/
	public void setList(List<T> list) {
		this.list = list;
	}

	/** 
	* @Title setDurin 
	* @Description TODO
	* @param durin 排序条件
	*/
	public void setDurin(int durin) {
		this.durin = durin;
	}
}