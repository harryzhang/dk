/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: LoanPersonDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:23:14
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.util.StringUtil;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:23:14
 */
public class LoanPersonDo implements Serializable {
    private static final long    serialVersionUID = 1L;
    private Long                 loanPersonId;         // 借款人ID
    private Long                 loanId;               // 借款ID
    private String               realName;             // 真实姓名
    private String               mobile;               // 手机号码
    private String               referenceMobile;      // 推荐人手机号码
    private String               idNo;                 // 身份证
    private Integer              age;                  // 年龄

    private Sex                  sex;                  // 性别
    private String               sexStr;
    private String               colorId;              // 彩生活用户ID
    private Long                 cid;                  // 小区ID
    private String               cname;                // 小区名称
    private String               caddress;             // 小区地址
    private Marriaged            marriaged;            // 婚姻状况
    private Education            education;            // 受教育程度
    private String               familyPhone;          // 住宅电话
    private String               email;                // 邮箱

    private Date                 applyTime;            // 申请日期
    private String               remark;               // 备注
    private Date                 createTime;           // 创建日期
    private String               updateUser;           // 修改人员
    private Date                 updateTime;           // 最后修改日期

    // loanDo中的
    private String               loanStatus;           // 状态
    private String               applyAmount;          // 借款金额
    private String               loanUsage;            // 借款用途
    private String 				 hasHouse;             //是否有房

    //针对还款中的  
    private Long                 lateDay;              //逾期天数
    private String               loanAmount;           //放款金额
    private Integer              loanPeriod;		   //期数
    private Date                 loanTime;             //放款日期
    
    private PropertyDo           propertyDo;           // 个人资产信息
    private List<LoanRelationDo> loanRelationDoList;   // 个人联系信息
    private LoanDo               loanDo;               // 借款申请信息
    private JobDo                jobDo;                // 工作信息
    private List<CertificateDo>  certificateDoList;    // 证件信息
    private BorrowDo             borrowDo;             //标的表
    private RepaymentDo			 repaymentDo;		   //还款表
    
    private int channel ;//上标渠道

    /** 性别（MALE-男，FEMALE-女） */
    public enum Sex {
        MALE, FEMALE;
    }

    /** 婚姻状况（UNMARRIED-未婚，MARRIED-已婚，DIVORCE-离异） */
    public enum Marriaged {
        UNMARRIED, MARRIED, DIVORCE;
    }

    /** 受教育程度（GRADE_SCHOOL- 初中已下，HIGN_SCHOOL-高中， POLYTECH_SCHOOL-中技     VOCATION_SCHOOL-中专    JUNIOR_COLLEGE-大专，BACHELOR-本科以上  -硕士MASTER   -博士DOCTOR） */
    public enum Education {
    	GRADE_SCHOOL,HIGN_SCHOOL,POLYTECH_SCHOOL,VOCATION_SCHOOL, JUNIOR_COLLEGE, BACHELOR,MASTER,DOCTOR;
    }

    /**
     * @return loanPersonId
     */
    public Long getLoanPersonId() {
        return loanPersonId;
    }

    /**
     * @param loanPersonId
     *            the loanPersonId to set
     */
    public void setLoanPersonId(Long loanPersonId) {
        this.loanPersonId = loanPersonId;
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
     * @return realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName
     *            the realName to set
     */
    public void setRealName(String realName) {
        this.realName = StringUtil.filterDangerString(realName);
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return referenceMobile
     */
    public String getReferenceMobile() {
        return referenceMobile;
    }

    /**
     * @param referenceMobile
     *            the referenceMobile to set
     */
    public void setReferenceMobile(String referenceMobile) {
        this.referenceMobile = referenceMobile;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return sex
     */
    public Sex getSex() {
        return sex;
    }
    

    public String getSexStr() {
    	if(sex == null){
    		return "";
    	}
    	sexStr = "女";
    	if("MALE".equals(sex.toString())){
    		sexStr = "男";
    	}
		return sexStr;
	}

	/**
     * @param sex
     *            the sex to set
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }
    

    /**
     * @return marriaged
     */
    public Marriaged getMarriaged() {
        return marriaged;
    }

    /**
     * @param marriaged
     *            the marriaged to set
     */
    public void setMarriaged(Marriaged marriaged) {
        this.marriaged = marriaged;
    }

    /**
     * @return education
     */
    public Education getEducation() {
        return education;
    }

    /**
     * @param education
     *            the education to set
     */
    public void setEducation(Education education) {
        this.education = education;
    }

    /**
     * @return familyPhone
     */
    public String getFamilyPhone() {
        return familyPhone;
    }

    /**
     * @param familyPhone
     *            the familyPhone to set
     */
    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return applyTime
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * @param applyTime
     *            the applyTime to set
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
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
     * @return updateUser
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     *            the updateUser to set
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
     * @return colorId
     */
   /* public Long getColorId() {
        return colorId;
    }

    *//**
     * @param colorId
     *            the colorId to set
     *//*
    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }*/

    /**
     * @return cid
     */
    public Long getCid() {
        return cid;
    }

    public String getColorId() {
		return colorId;
	}

	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	/**
     * @param cid
     *            the cid to set
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * @return cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname
     *            the cname to set
     */
    public void setCname(String cname) {
        this.cname = StringUtil.filterDangerString(cname);
    }

    /**
     * @return caddress
     */
    public String getCaddress() {
        return caddress;
    }

    /**
     * @param caddress
     *            the caddress to set
     */
    public void setCaddress(String caddress) {
        this.caddress = StringUtil.filterDangerString(caddress);
    }

    public JobDo getJobDo() {
        return jobDo;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public void setJobDo(JobDo jobDo) {
        this.jobDo = jobDo;
    }

    public LoanDo getLoanDo() {
        return loanDo;
    }

    public void setLoanDo(LoanDo loanDo) {
        this.loanDo = loanDo;
    }

    public PropertyDo getPropertyDo() {
        return propertyDo;
    }

    public void setPropertyDo(PropertyDo propertyDo) {
        this.propertyDo = propertyDo;
    }

    public List<LoanRelationDo> getLoanRelationDoList() {
        return loanRelationDoList;
    }

    public void setLoanRelationDoList(List<LoanRelationDo> loanRelationDoList) {
        this.loanRelationDoList = loanRelationDoList;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public List<CertificateDo> getCertificateDoList() {
        return certificateDoList;
    }

    public void setCertificateDoList(List<CertificateDo> certificateDoList) {
        this.certificateDoList = certificateDoList;
    }

	public BorrowDo getBorrowDo() {
		return borrowDo;
	}

	public void setBorrowDo(BorrowDo borrowDo) {
		this.borrowDo = borrowDo;
	}

	public RepaymentDo getRepaymentDo() {
		return repaymentDo;
	}

	public void setRepaymentDo(RepaymentDo repaymentDo) {
		this.repaymentDo = repaymentDo;
	}

	public Long getLateDay() {
		return lateDay;
	}

	public void setLateDay(Long lateDay) {
		this.lateDay = lateDay;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public String getHasHouse() {
		return hasHouse;
	}

	public void setHasHouse(String hasHouse) {
		this.hasHouse = hasHouse;
	}
	
}
