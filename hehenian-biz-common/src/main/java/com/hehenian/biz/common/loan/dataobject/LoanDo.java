/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: LoanDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:08:48
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.util.StringUtil;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:08:48
 */
public class LoanDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              loanId;               // 借款ID
    private Integer           loanType;             // 贷款类型 1:信用贷 2：抵押贷 3：担保贷
    private Long              userId;               // 用户ID
    private Long              schemeId;             // 放款方案ID
    private Double            annualRate;           // 借款年利率
    private Long              borrowId;             // 借款ID
    private String            loanTitle;            // 借款标题
    private String            loanUsage;            // 贷款用途
    private Double            applyAmount;          // 申请金额
    private String            applyAmountString;    // 申请金额
    private Double            applyAmounttemp;    	// 限制金额

    private Integer           loanPeriod;           // 借款期限
    private Date              loanTime;             //放款日期
    private Double            loanAmount;           // 放款金额
    private String            remark;               // 备注
    private LoanStatus        loanStatus;           // 借款状态
    private Long              auditUserId;          // 审核人ID

    private String            auditUser;            // 审核人
    private Date              auditTime;            // 审核时间
    private Date              createTime;           // 创建日期
    private Date              updateTime;           // 最后修改日期

    private LoanPersonDo      loanPersonDo;         // 借款人信息
    /**add By wangt*/
    private String            productCode; 			//产品编码
    private String            orderCode;			//订单号
    private Long			  channelId;			//渠道ID
    
    
    private Double            investAnnualRate;     //投资者利息
    /* add by liminglong  */
    private String     		realName;//借款人姓名
    private String  		mobile; //借款人手机
    private String 			referenceMobile ;//推荐人手机
    private String  		productName;//产品名称
    private String 			schemeName;//方案名称
    private Long			updateBy; //当前操作用户
    
    private ProcessStep	    processCurrentStep;  //当前处理步骤
    private ProcessStep	    processNextStep;  //下一个处理步骤
    private BigDecimal		auditAmount; //风控金额
    private Integer 			subjectType;  //上标渠道
    
    /* add by liminglong  */
    
    private String caddress;
    
	public String getCaddress() {
		return caddress;
	}


	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReferenceMobile() {
		return referenceMobile;
	}

	public void setReferenceMobile(String referenceMobile) {
		this.referenceMobile = referenceMobile;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
     * DRAFT 草稿,PENDING-待处理，PROCESSING-处理中，AUDITED-已审核，TREATY-已签约，SUBJECTED 已上标 REPAYING还款中  REPAYED已还清 
     * ，NOPASS-拒绝,INVALID失效
     * *
     */    
    public enum LoanStatus {
		DRAFT,PENDING,PROCESSING,AUDITED,TREATY,SUBJECTED,REPAYING, REPAYED, 
        NOPASS,INVALID
    }
    
    
    /**
     * CALL_COLOR_HOUSE_CHECK 调用地产验证是否业主， PROXY_CHECK 待彩管家验证,INPUT_CREDIT_REPORT-输入征信报告，XIAODAI_AUDIT-小贷审批，TO_SUBJECT 待上标 , TO_EDIT 用户编辑
     * MENDSTEP1 一审，MENDSTEP2 二审, NULL 没有步骤的时候用这个项
     * *
     */    
    public enum ProcessStep {
    	CALL_COLOR_HOUSE_CHECK,PROXY_CHECK,INPUT_CREDIT_REPORT,XIAODAI_AUDIT,TO_SUBJECT, MENDSTEP1,MENDSTEP2, TO_EDIT, NULL
    }
    
    
    public static String  getLoanStepName(ProcessStep step){
    	if(ProcessStep.CALL_COLOR_HOUSE_CHECK.equals(step)){
    		return "验证业主";
    	}else if(ProcessStep.PROXY_CHECK.equals(step)){
    		return "彩管家审核";
    	}else if(ProcessStep.INPUT_CREDIT_REPORT.equals(step)){
    		return "征信补入";
    	}else if(ProcessStep.XIAODAI_AUDIT.equals(step)){
    		return "小贷审批";
    	}else if(ProcessStep.TO_SUBJECT.equals(step)){
    		return "待上标";
    	}else if(ProcessStep.TO_EDIT.equals(step)){
    		return "用户提交";
    	}else if(ProcessStep.MENDSTEP1.equals(step)){
    		return "一审";
    	}else if(ProcessStep.MENDSTEP2.equals(step)){
    		return "二审";
    	}else if(ProcessStep.NULL.equals(step)){
    		return "--";
    	}
    	return null;
    }
    

    public static String getChannelName(Long channelId2) {
    	if(null == channelId2) return "";
    	switch (Integer.valueOf(channelId2.toString())) {
		case 1:
			return "彩生活";
		case 2:
			return "文旅集团";
		case 3:
			return "地产集团";
		case 4:
			return "国际物业";
		case 5:
			return "融资租赁";
		case 6:
			return "福泰年";
		case 7:
			return "中国集团";
		case 8:
			return "合和年在线";
		case 9:
			return "小贷";
		case 10:
			return "商业管理";
		case 11:
			return "物业管理";
		default:
			return "";
		}
	}
    
    public static List<LoanStatusDo> getLoanStatusList(){
    	List<LoanStatusDo> lsList = new ArrayList<LoanStatusDo>();
    	lsList.add(new LoanStatusDo("DRAFT", "草稿"));
    	lsList.add(new LoanStatusDo("PENDING", "待处理"));
    	lsList.add(new LoanStatusDo("PROCESSING", "处理中"));
    	lsList.add(new LoanStatusDo("AUDITED", "已审核"));
    	lsList.add(new LoanStatusDo("TREATY", "已签约"));
    	lsList.add(new LoanStatusDo("SUBJECTED", "已上标"));
    	lsList.add(new LoanStatusDo("REPAYING", "还款中"));
    	lsList.add(new LoanStatusDo("REPAYED", "已还清"));
    	lsList.add(new LoanStatusDo("NOPASS", "拒绝"));
    	lsList.add(new LoanStatusDo("INVALID", "失效"));
    	return lsList;
    }
    
    /**
     * @return loanId
     */
    public Long getLoanId() {
        return loanId;
    }

    /**
     * @param loanId
     *            the loanId to set
     */
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    /**
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return schemeId
     */
    public Long getSchemeId() {
        return schemeId;
    }

    /**
     * @param schemeId
     *            the schemeId to set
     */
    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    /**
     * @return loanTitle
     */
    public String getLoanTitle() {
        return loanTitle;
    }

    /**
     * @param loanTitle
     *            the loanTitle to set
     */
    public void setLoanTitle(String loanTitle) {
        this.loanTitle = loanTitle;
    }

    /**
     * @return loanUsage
     */
    public String getLoanUsage() {
        return loanUsage;
    }

    /**
     * @param loanUsage
     *            the loanUsage to set
     */
    public void setLoanUsage(String loanUsage) {
        this.loanUsage = StringUtil.filterDangerString(loanUsage);
    }

    /**
     * @return applyAmount
     */
    public Double getApplyAmount() {
    	if(applyAmount != null){
    		BigDecimal b = new BigDecimal(Double.toString(applyAmount));
    		BigDecimal one = new BigDecimal("1");
    		applyAmount =  b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    	}
        return applyAmount;
    }

    /**
     * @param applyAmount
     *            the applyAmount to set
     */
    public void setApplyAmount(Double applyAmount) {
    	if(applyAmount==null)applyAmount=0.0;
        this.applyAmount = applyAmount;
        if(null!=applyAmount){
        	setApplyAmountString(new DecimalFormat("###0.#").format(applyAmount));
        }
    }

    
    public String getApplyAmountString() {
		return applyAmountString;
	}

	public void setApplyAmountString(String applyAmountString) {
		this.applyAmountString = applyAmountString;
	}

	/**
     * @return loanPeriod
     */
    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    /**
     * @param loanPeriod
     *            the loanPeriod to set
     */
    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    /**
     * @return loanAmount
     */
    public Double getLoanAmount() {
        return loanAmount;
    }

    /**
     * @param loanAmount
     *            the loanAmount to set
     */
    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
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
        this.remark = StringUtil.filterDangerString(remark);
    }

    /**
     * @return loanStatus
     */
    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    /**
     * @param loanStatus
     *            the loanStatus to set
     */
    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    /**
     * @return auditUserId
     */
    public Long getAuditUserId() {
        return auditUserId;
    }

    /**
     * @param auditUserId
     *            the auditUserId to set
     */
    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    /**
     * @return auditUser
     */
    public String getAuditUser() {
        return auditUser;
    }

    /**
     * @param auditUser
     *            the auditUser to set
     */
    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    /**
     * @return auditTime
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * @param auditTime
     *            the auditTime to set
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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

    /**
     * @return loanPersonDo
     */
    public LoanPersonDo getLoanPersonDo() {
        return loanPersonDo;
    }

    /**
     * @param loanPersonDo
     *            the loanPersonDo to set
     */
    public void setLoanPersonDo(LoanPersonDo loanPersonDo) {
        this.loanPersonDo = loanPersonDo;
    }

	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
	}

	public Double getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(Double annualRate) {
		this.annualRate = annualRate;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Double getInvestAnnualRate() {
		return investAnnualRate;
	}

	public void setInvestAnnualRate(Double investAnnualRate) {
		this.investAnnualRate = investAnnualRate;
	}


	public Long getChannelId() {
		return channelId;
	}


	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}


	public Double getApplyAmounttemp() {
		return applyAmounttemp;
	}

	public void setApplyAmounttemp(Double applyAmounttemp) {
		this.applyAmounttemp = applyAmounttemp;
	}


	public ProcessStep getProcessCurrentStep() {
		return processCurrentStep;
	}


	public void setProcessCurrentStep(ProcessStep processCurrentStep) {
		this.processCurrentStep = processCurrentStep;
	}


	public ProcessStep getProcessNextStep() {
		return processNextStep;
	}


	public void setProcessNextStep(ProcessStep processNextStep) {
		this.processNextStep = processNextStep;
	}


	public BigDecimal getAuditAmount() {
		return auditAmount;
	}


	public void setAuditAmount(BigDecimal auditAmount) {
		if(auditAmount==null)auditAmount=BigDecimal.ZERO ;
		this.auditAmount = auditAmount;
	}


	public Integer getSubjectType() {
		return this.subjectType;
	}


	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}

}
