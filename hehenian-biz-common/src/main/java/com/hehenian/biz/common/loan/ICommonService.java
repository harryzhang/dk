package com.hehenian.biz.common.loan;

import com.hehenian.biz.common.loan.dataobject.ConsultVO;
import com.hehenian.biz.common.loan.dataobject.ContactVO;

public interface ICommonService {

	public String generateOrderCode(String pri);
	
	public void generateContactPdf(ContactVO contactVO);
	
	public void generateConsultPdf(ConsultVO consultVO );
}
