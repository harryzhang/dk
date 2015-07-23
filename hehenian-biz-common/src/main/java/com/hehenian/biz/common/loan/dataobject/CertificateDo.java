/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: CertificateDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:13:58
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:13:58
 */
public class CertificateDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              certificateId;        // 证件ID
    private Long              loanId;               // 借款申请ID
    private Long              loanPersonId;         // 借款人ID
    private CertificateType   certificateType;      // 证件类型
    private String            certificateName;      // 证件类型

    private String            filePath;             // 文件路径
    private String            destFilePath;         // 压缩文件路径
    private FileType          fileType;             // 文件类型
    private String 			  createUser;            //上传人
    private Date              createTime;           // 创建日期
    private Date              updateTime;           // 最后修改日期
    private Long              statusId;         // 状态ID
    private int               statusInt=-1;           // 状态INT
    private String               relationType;           // 状态INT
    private String            certificateTypeHead;  // 证件类型jsp头
    
    /** 证件类型（IDCARDZ正面-身份证，IDCARDZS本人手持身份证正面，IDCARDF-身份证反面，HOUSE-房产证明，JOB-工作证明，PROTOCOL -协议，INCOME-收入流水,OTHERFILE-其他证件,ENTRUST_PROTOCOL-委托划款协议,CREDIT_AUDITK_PROTOCOL-信用审核服务协议,DRIVERCARD-驾驶证,ASSETS --资产相关证明） */
    public enum CertificateType {
    	IDCARDZ,IDCARDZS, IDCARDF, HOUSE, JOB, PROTOCOL,INCOME,CREDIT,OTHERFILE,CREDIT_AUDITK_PROTOCOL,ENTRUST_PROTOCOL,DRIVERCARD,ASSETS;
    }

    /** 文件类型（IMAGE-图片，VIDEO-视频，PDF-PDF） */
    public enum FileType {
        IMAGE, VIDEO, PDF;
    }

    /**
     * @return certificateId
     */
    public Long getCertificateId() {
        return certificateId;
    }

    /**
     * @param certificateId
     *            the certificateId to set
     */
    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
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
     * @return certificateType
     */
    public CertificateType getCertificateType() {
        return certificateType;
    }

    /**
     * @param certificateType
     *            the certificateType to set
     */
    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * @return certificateName
     */
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * @param certificateName
     *            the certificateName to set
     */
    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    /**
     * @return filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath
     *            the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return fileType
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     *            the fileType to set
     */
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCtPageId() {
		if(StringUtils.isNotBlank(certificateType.toString())){
			return certificateType.toString();
		}
		return null;
	}

	public String getDestFilePath() {
		return destFilePath;
	}

	public void setDestFilePath(String destFilePath) {
		this.destFilePath = destFilePath;
	}

	public int getStatusInt() {
		return statusInt;
	}

	public void setStatusInt(int statusInt) {
		this.statusInt = statusInt;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getCertificateTypeHead() {
		return certificateTypeHead;
	}

	public void setCertificateTypeHead(String certificateTypeHead) {
		this.certificateTypeHead = certificateTypeHead;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

}
