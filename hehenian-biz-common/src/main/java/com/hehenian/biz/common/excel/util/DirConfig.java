/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.excel.util
 * @Title: DirConfig.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年12月22日 下午3:13:42
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.excel.util;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年12月22日 下午3:13:42
 */
public class DirConfig {

    // 放款模板文件
    private String publishTemplateFile;
    // 放款输出路径
    private String publishExportDir;
    // 还款模板文件
    private String repayTemplateFile;
    // 还款输出路径
    private String repayExportDir;
    
    // 邮件发送execl模板文件
    private String mailTemplateFile;
    // 邮件发送execl保存路径
    private String mailExportDir;
    
    private String reconciliationTemplateFile;
    private String reconciliationExportDir;
    
    private String addressee;
    private String ccList;
    

    /**
     * @return publishTemplateFile
     */
    public String getPublishTemplateFile() {
        return publishTemplateFile;
    }

    /**
     * @param publishTemplateFile
     *            the publishTemplateFile to set
     */
    public void setPublishTemplateFile(String publishTemplateFile) {
        this.publishTemplateFile = publishTemplateFile;
    }

    /**
     * @return publishExportDir
     */
    public String getPublishExportDir() {
        return publishExportDir;
    }

    /**
     * @param publishExportDir
     *            the publishExportDir to set
     */
    public void setPublishExportDir(String publishExportDir) {
        this.publishExportDir = publishExportDir;
    }

    /**
     * @return repayTemplateFile
     */
    public String getRepayTemplateFile() {
        return repayTemplateFile;
    }

    /**
     * @param repayTemplateFile
     *            the repayTemplateFile to set
     */
    public void setRepayTemplateFile(String repayTemplateFile) {
        this.repayTemplateFile = repayTemplateFile;
    }

    /**
     * @return repayExportDir
     */
    public String getRepayExportDir() {
        return repayExportDir;
    }

    /**
     * @param repayExportDir
     *            the repayExportDir to set
     */
    public void setRepayExportDir(String repayExportDir) {
        this.repayExportDir = repayExportDir;
    }

	public String getMailExportDir() {
		return mailExportDir;
	}

	public void setMailExportDir(String mailExportDir) {
		this.mailExportDir = mailExportDir;
	}

	public String getMailTemplateFile() {
		return mailTemplateFile;
	}

	public void setMailTemplateFile(String mailTemplateFile) {
		this.mailTemplateFile = mailTemplateFile;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getCcList() {
		return ccList;
	}

	public void setCcList(String ccList) {
		this.ccList = ccList;
	}

	public String getReconciliationTemplateFile() {
		return reconciliationTemplateFile;
	}

	public void setReconciliationTemplateFile(String reconciliationTemplateFile) {
		this.reconciliationTemplateFile = reconciliationTemplateFile;
	}

	public String getReconciliationExportDir() {
		return reconciliationExportDir;
	}

	public void setReconciliationExportDir(String reconciliationExportDir) {
		this.reconciliationExportDir = reconciliationExportDir;
	}

}
