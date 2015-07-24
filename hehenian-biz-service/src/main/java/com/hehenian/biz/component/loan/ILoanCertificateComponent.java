package com.hehenian.biz.component.loan;

import java.util.List;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;

public interface ILoanCertificateComponent {
    /**
     * 修改图片信息
     * @param CertificateDo
     */
    void updateCertificate(CertificateDo certificateDo);
    
    /**
     * 增加图片信息
     * @param CertificateDo
     */
    void addCertificate(CertificateDo certificateDo);
    
    /**
     * 图片信息列表
     * @param loanId
     * @return
     */
    List<CertificateDo> getCertificateByLoanId(Long loanId);
    
    /**
     * 删除上传的图片资料信息
     * @param Long certificateId
     * @return
     */
	IResult<?> deleteCertificateById(Long certificateId);
}
