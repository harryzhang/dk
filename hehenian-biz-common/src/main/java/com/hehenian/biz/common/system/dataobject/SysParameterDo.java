/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.system.dataobject
 * @Title: SysParameterDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月5日 上午11:42:40
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.system.dataobject;

import java.util.Date;

/**
 * 系统参数信息
 * 
 * @author: liuzgmf
 * @date 2015年1月5日 上午11:42:40
 */
public class SysParameterDo {
    private Long   parameteId;   // 系统参数ID
    private String parameteCode; // 系统K代码
    private String parameteName; // 参数名称
    private String parameteValue; // 参数值
    private String remark;       // 备注
    private Date   createTime;   // 创建时间
    private Date   updateTime;   // 最后修改时间

    /**
     * @return parameteId
     */
    public Long getParameteId() {
        return parameteId;
    }

    /**
     * @param parameteId
     *            the parameteId to set
     */
    public void setParameteId(Long parameteId) {
        this.parameteId = parameteId;
    }

    /**
     * @return parameteCode
     */
    public String getParameteCode() {
        return parameteCode;
    }

    /**
     * @param parameteCode
     *            the parameteCode to set
     */
    public void setParameteCode(String parameteCode) {
        this.parameteCode = parameteCode;
    }

    /**
     * @return parameteName
     */
    public String getParameteName() {
        return parameteName;
    }

    /**
     * @param parameteName
     *            the parameteName to set
     */
    public void setParameteName(String parameteName) {
        this.parameteName = parameteName;
    }

    /**
     * @return parameteValue
     */
    public String getParameteValue() {
        return parameteValue;
    }

    /**
     * @param parameteValue
     *            the parameteValue to set
     */
    public void setParameteValue(String parameteValue) {
        this.parameteValue = parameteValue;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
