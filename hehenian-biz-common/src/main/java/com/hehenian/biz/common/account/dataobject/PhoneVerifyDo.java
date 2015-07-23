package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * User: liuwtmf
 * Date: 2014/11/28
 * Time: 9:11
 */
public class PhoneVerifyDo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long    id;				// 编号		
    private Long    userId;			// 用户编号
    private String  mobilePhone;	// 手机号码或邮箱地址
    private Integer status;			// 可用状态1-可用，2-废除
    private Date    createTime;		// 创建时间
    private Integer type;			// 联系方式类型1-手机，2-邮箱
    private Integer source;			// 渠道来源1-PC端，2-移动端
    private Date	verifyTime;		// 验证时间
    
    public static final class ContactType {
    	public static final Integer PHONE = 1;
    	public static final Integer EMAIL = 2;
    }

    public static final class SourceType {
    	public static final Integer PC = 1;
    	public static final Integer MOBILE = 2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	@Override
	public String toString() {
		return "PhoneVerifyDo [id=" + id + ", userId=" + userId
				+ ", mobilePhone=" + mobilePhone + ", status=" + status
				+ ", createTime=" + createTime + ", type=" + type + ", source="
				+ source + ", verifyTime=" + verifyTime + "]";
	}
}
