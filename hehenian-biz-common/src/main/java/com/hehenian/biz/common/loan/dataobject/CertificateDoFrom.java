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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年6月3日 上午10:16:27
 * @Project hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject 
 * @File CertificateDoFrom.java
*/
public class CertificateDoFrom {
	
    private List<CertificateDo> certificateDoList;
    private LoanPersonDo loanPerson;
    private String pageId;
    private String tableCode;
    
	public List<CertificateDo> getCertificateDoList() {
		return certificateDoList;
	}
	public void setCertificateDoList(List<CertificateDo> certificateDoList) {
		this.certificateDoList = certificateDoList;
	}
	public LoanPersonDo getLoanPerson() {
		return loanPerson;
	}
	public void setLoanPerson(LoanPersonDo loanPerson) {
		this.loanPerson = loanPerson;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	
}
