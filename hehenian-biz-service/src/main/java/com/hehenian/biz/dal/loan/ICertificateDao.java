package com.hehenian.biz.dal.loan;

import java.util.List;

import com.hehenian.biz.common.loan.dataobject.CertificateDo;

/** 
 *  
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:43:39
 */
public interface ICertificateDao {

	
	void updateCertificate(CertificateDo certificateDo);

	void addCertificate(CertificateDo certificateDo);

	List<CertificateDo> getCertificateByLoanId(Long loanId);

	/**
	 * 删除
	 * @param certificateId
	 * @return
	 */
	int deleteCertificateById(Long certificateId);
}
