package com.hehenian.biz.component.loan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.component.loan.ILoanCertificateComponent;
import com.hehenian.biz.dal.loan.ICertificateDao;

/**
 * @author xiexiangmf
 *
 */
@Component("loanCertificateComponent")
public class LoanCertificateComponentImpl implements ILoanCertificateComponent{
	
    @Autowired
    private ICertificateDao certificateDao;

	@Override
	public void updateCertificate(CertificateDo certificateDo) {
		certificateDao.updateCertificate(certificateDo);
	}

	@Override
	public void addCertificate(CertificateDo certificateDo) {
		certificateDao.addCertificate(certificateDo);
	}

	@Override
	public List<CertificateDo> getCertificateByLoanId(Long loanId) {
		return certificateDao.getCertificateByLoanId(loanId);
	}

	@Override
	public IResult<?> deleteCertificateById(Long certificateId) {
		IResult result = new ResultSupport<String>(true);
		int resultRow = certificateDao.deleteCertificateById(certificateId);
		if(resultRow<1){
			result.setSuccess(false);
			result.setErrorMessage("删除失败");
		}
		return result;
	} 
}
