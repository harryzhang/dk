/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.base.dataobject
 * @Title: NPageDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年11月21日 上午10:48:42
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.base.dataobject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * 
 * @author: liuzgmf
 * @date 2014年11月21日 上午10:48:42
 */
public class NPageDo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              currentPage;          // 当前页
    private Long              pageSize;             // 每页记录数
    private Long              totalCount;           // 总记录数
    private Long              totalPage;            // 总页数
    private List<T>           modelList;            // 实体对象列表
    private List<Map<String, Object>> commonModeList;   //通用查询对象列表

    public NPageDo() {

    }

    public NPageDo(Long currentPage, Long pageSize, Long totalCount, List<T> modelList) {
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        long totalPage = (getTotalCount() / getPageSize()) + ((getTotalCount() % getPageSize()) > 0 ? 1 : 0);
        this.setTotalPage(totalPage);
        this.setModelList(modelList);
    }

    /**
     * @return currentPage
     */
    public Long getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(Long currentPage) {
        if (currentPage == null || currentPage.intValue() <= 0) {
            currentPage = 1l;
        }
        this.currentPage = currentPage;
    }

    /**
     * @return pageSize
     */
    public Long getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(Long pageSize) {
        if (pageSize == null || pageSize.intValue() <= 0) {
            pageSize = 10l;
        }
        this.pageSize = pageSize;
    }

    /**
     * @return totalCount
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(Long totalCount) {
        if (totalCount == null) {
            totalCount = 0l;
        }
        this.totalCount = totalCount;
    }

    /**
     * @return totalPage
     */
    public Long getTotalPage() {
        return totalPage;
    }

    /**
     * @param totalPage
     *            the totalPage to set
     */
    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @return modelList
     */
    public List<T> getModelList() {
        return modelList;
    }

    /**
     * @param modelList
     *            the modelList to set
     */
    public void setModelList(List<T> modelList) {
        this.modelList = modelList;
    }

	public List<Map<String, Object>> getCommonModeList() {
		return commonModeList;
	}

	public void setCommonModeList(List<Map<String, Object>> commonModeList) {
		this.commonModeList = commonModeList;
	}

}
