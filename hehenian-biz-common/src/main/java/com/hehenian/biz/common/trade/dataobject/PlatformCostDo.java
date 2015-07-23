/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: PlatformCostDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月24日 下午3:23:54
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.util.Date;

/**
 * 平台费用对象
 * 
 * @author: liuzgmf
 * @date 2014年9月24日 下午3:23:54
 */
public class PlatformCostDo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long              id;                   // 编号
    private String            costName;             // 收费名称
    private Double            costFee;              // 费用
    private Integer           costMode;             // 费用模式(1 比率 2 金额)
    private String            remark;               // 备注
    private Date              createTime;           // 创建时间
    private String            alias;                // 别名
    private Integer           types;                // 费用类型(1.奖励费用管理 2.借款费用管理
                                                     // 3.其他费用管理)
    private Integer           showView;             // 显示/隐藏 1 显示 2 隐藏

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return costName
     */
    public String getCostName() {
        return costName;
    }

    /**
     * @param costName
     *            the costName to set
     */
    public void setCostName(String costName) {
        this.costName = costName;
    }

    /**
     * @return costFee
     */
    public Double getCostFee() {
        return costFee;
    }

    /**
     * @param costFee
     *            the costFee to set
     */
    public void setCostFee(Double costFee) {
        this.costFee = costFee;
    }

    /**
     * @return costMode
     */
    public Integer getCostMode() {
        return costMode;
    }

    /**
     * @param costMode
     *            the costMode to set
     */
    public void setCostMode(Integer costMode) {
        this.costMode = costMode;
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
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias
     *            the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return types
     */
    public Integer getTypes() {
        return types;
    }

    /**
     * @param types
     *            the types to set
     */
    public void setTypes(Integer types) {
        this.types = types;
    }

    /**
     * @return showView
     */
    public Integer getShowView() {
        return showView;
    }

    /**
     * @param showView
     *            the showView to set
     */
    public void setShowView(Integer showView) {
        this.showView = showView;
    }

}
