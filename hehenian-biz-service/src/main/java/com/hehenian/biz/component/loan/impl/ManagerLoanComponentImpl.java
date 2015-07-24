package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckedDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanModifyLogDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.common.loan.dataobject.LoanUserBankDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.loan.IManagerLoanComponent;
import com.hehenian.biz.dal.loan.ILoanCheckedDao;
import com.hehenian.biz.dal.loan.ILoanModifyLogDao;
import com.hehenian.biz.dal.loan.IManagerLoanDao;
import com.hehenian.biz.dal.loan.IManagerLoanProductDao;
import com.hehenian.biz.dal.loan.IManagerLoanRepaymentDao;
import com.hehenian.biz.dal.loan.IManagerLoanRepaymentFeeDao;

/**
 * @author zhengyfmf
 */
@Component("managerLoanComponent")
public class ManagerLoanComponentImpl implements IManagerLoanComponent{
	
	@Autowired
	private IManagerLoanDao managerLoanDao;
	
	@Autowired
	private IManagerLoanProductDao managerLoanProductDao ;
	
	@Autowired
	private IManagerLoanRepaymentDao managerLoanRepaymentDao;
	
	@Autowired
	private IManagerLoanRepaymentFeeDao managerLoanRepaymentFeeDao;
	
	@Autowired
    private ILoanModifyLogDao   loanModifyLogDao;
	
	@Autowired
    private ILoanCheckedDao loanCheckedDao ;
	
	@Override
	public LoanDo getLoanByLoanId(Long loanId) {
		return managerLoanDao.getLoanByLoanId(loanId);
	}
	
	@Override
	public int updateLoanStatus(LoanDo loanDo) {
		return managerLoanDao.updateLoanStatus(loanDo);
	}
	
	@Override
	public boolean updateLoanStatusByMap(List<Map<String,Object>> paramList){
		if(paramList != null){
			for(int i=0;i<paramList.size();i++){
				managerLoanDao.updateLoanStatusByMap(paramList.get(i));
			}
		}
		return true ;
	}
	
	@Override
	public List<Map<String,Object>> getLoanPage(Map<String, Object> param) {
		return managerLoanDao.getLoanPage(param);
	}

	@Override
	public LoanPersonDo getLoanDetailByLoanId(Long loanId) {
		return managerLoanDao.getLoanDetailByLoanId(loanId);
	}

	@Override
	public List<LoanProductDo> listLoanProduct(Map<String, Object> param) {
		return managerLoanProductDao.queryLoanProductList(param);
	}

	@Override
	public List<Map<String, Object>> getLabelExportData(Map<String, Object> param) {
		return managerLoanDao.getLabelExportData(param);
	}

	@Override
	public boolean initRepayPlan(LoanDo loanDo, List<SettDetailDo> list) {
		managerLoanDao.updateLoanForRepayPlan(loanDo);
		if(list != null && list.size()>0){
			for(SettDetailDo sdd:list){
				LoanRepaymentDo lrd = new LoanRepaymentDo();
				lrd.setLoanId(loanDo.getLoanId());
				lrd.setOrderCode(loanDo.getOrderCode());
				lrd.setRepayPeriod(sdd.getPeriod());
				lrd.setStillPrincipal(sdd.getPrincipal());
				lrd.setStillInterest(sdd.getInterest());
				lrd.setPrincipalBalance(sdd.getRemainingPrincipal());
				lrd.setStillRepayAll(CalculateUtils.add(sdd.getPrincipal(), sdd.getInterest()));
				lrd.setRepayDate(sdd.getRepayDate());
				managerLoanRepaymentDao.addLoanRepayment(lrd);
				if(sdd.getRfList() != null && sdd.getRfList().size()>0){
					for(LoanRepaymentFeeDo lrfd:sdd.getRfList()){
						lrfd.setLoanId(loanDo.getLoanId());
						lrfd.setOrderCode(loanDo.getOrderCode());
						lrfd.setRepaymentId(lrd.getRepaymentId());
						lrfd.setRepayType(0);
						managerLoanRepaymentFeeDao.addLoanRepaymentFee(lrfd);
					}
				}
			}
		}else{
			return false;
		}
		return true;
	}

	@Override
	public LoanPersonDo getLoanInfoForSbByLoanId(Long loanId) {
		return managerLoanDao.getLoanInfoForSbByLoanId(loanId);
	}

	@Override
	public LoanPersonDo getLoanInfoForFkByOrderCode(String orderCode) {
		return managerLoanDao.getLoanInfoForFkByOrderCode(orderCode);
	}

	@Override
	public Map<String, Object> getBankAccountForHF(String idNo) {
		return managerLoanDao.getBankAccountForHF(idNo);
	}

	@Override
	public int updateLoan(LoanDo loanDo) {
		Long loanId = loanDo.getLoanId();
		LoanDo oldLoanDo = managerLoanDao.getLoanByLoanId(loanId);
		int result = managerLoanDao.update(loanDo);
		
		//记录修改日志， 贷款金额
		Long updateBy = loanDo.getUpdateBy();
		LoanModifyLogDo modifyLog = new LoanModifyLogDo();
		modifyLog.setLoanId(loanId);
		modifyLog.setModifyBy(updateBy);
		modifyLog.setNewVal(loanDo.getApplyAmount().toString());
		modifyLog.setOldVal(oldLoanDo.getApplyAmount().toString());
		modifyLog.setField("ApplyAmount");
		this.addLoanModifyLog(modifyLog);
		
		//借款期限 
		LoanModifyLogDo modifyLog1 = new LoanModifyLogDo();
		modifyLog1.setLoanId(loanId);
		modifyLog1.setModifyBy(updateBy);
		modifyLog1.setNewVal(loanDo.getLoanPeriod().toString());
		modifyLog1.setOldVal(oldLoanDo.getLoanPeriod().toString());
		modifyLog1.setField("LoanPeriod");
		this.addLoanModifyLog(modifyLog1);
		//借款利率
		LoanModifyLogDo modifyLog2 = new LoanModifyLogDo();
		modifyLog2.setLoanId(loanId);
		modifyLog2.setModifyBy(updateBy);
		modifyLog2.setNewVal(loanDo.getAnnualRate().toString());
		modifyLog2.setOldVal(oldLoanDo.getAnnualRate().toString());
		modifyLog2.setField("AnnualRate");
		this.addLoanModifyLog(modifyLog2);
		//还款方式 
		LoanModifyLogDo modifyLog3 = new LoanModifyLogDo();
		modifyLog3.setLoanId(loanId);
		modifyLog3.setModifyBy(updateBy);
		modifyLog3.setNewVal(loanDo.getSchemeId().toString());
		modifyLog3.setOldVal(oldLoanDo.getSchemeId().toString());
		modifyLog3.setField("SchemeId");
		this.addLoanModifyLog(modifyLog3);
		//用途
		LoanModifyLogDo modifyLog4 = new LoanModifyLogDo();
		modifyLog4.setLoanId(loanId);
		modifyLog4.setModifyBy(updateBy);
		modifyLog4.setNewVal(loanDo.getLoanUsage());
		modifyLog4.setOldVal(oldLoanDo.getLoanUsage());
		modifyLog4.setField("LoanUsage");
		this.addLoanModifyLog(modifyLog4);
		//标题
		LoanModifyLogDo modifyLog5 = new LoanModifyLogDo();
		modifyLog5.setLoanId(loanId);
		modifyLog5.setModifyBy(updateBy);
		modifyLog5.setNewVal(loanDo.getLoanTitle());
		modifyLog5.setOldVal(oldLoanDo.getLoanTitle());
		modifyLog5.setField("LoanTitle");
		this.addLoanModifyLog(modifyLog5);
		
		//状态
		LoanModifyLogDo modifyLog6 = new LoanModifyLogDo();
		modifyLog6.setLoanId(loanId);
		modifyLog6.setModifyBy(updateBy);
		modifyLog6.setNewVal(loanDo.getLoanStatus().toString());
		modifyLog6.setOldVal(oldLoanDo.getLoanStatus().toString());
		modifyLog6.setField("LoanStatus");
		this.addLoanModifyLog(modifyLog6);
		return result;
		
	}

	@Override
	public LoanPersonDo getLoanPersonDetail(Long loanId) {
		return managerLoanDao.getLoanPersonByLoanId(loanId);
	}

	@Override
	public Map<String, Object> getBankAccountForTL(String idNo) {
		return managerLoanDao.getBankAccountForTL(idNo);
	}

	@Override
	public List<Map<String,Object>> getSbNameForHF(String orderCode) {
		return managerLoanDao.getSbNameForHF(orderCode);
	}

	@Override
	public List<Map<String,Object>> getSbNameForTL(String orderCode) {
		return managerLoanDao.getSbNameForTL(orderCode);
	}

	@Override
	public List<LoanDo> getLoanListPage(Map<String, Object> param) {
		List<LoanDo> list = managerLoanDao.getLoanListPage(param);
		return list;
	}


	/******************** 申请日志  api*********************************/

	@Override
	public LoanDo getDetailLoanByLoanId(Long loanId) {
		return managerLoanDao.getDetailById(loanId);
	}


	
	/**
	 * 新增修改日志
	 * @param modifyLog
	 * @return
	 */
	@Override
	public int addLoanModifyLog(LoanModifyLogDo modifyLog){
		return loanModifyLogDao.addLoanModifyLog(modifyLog);
	}

	@Override
	public List<LoanUserBankDo> getTbcInfo(Long userId) {
		return managerLoanDao.getTbcInfo(userId);
	}

	@Override
	public List<LoanUserBankDo> getTdbcInfo(Long userId) {
		return managerLoanDao.getTdbcInfo(userId);
	}

	/******************** end 申请日志  api*********************************/
	
	@Override
	public List<LoanCheckedDo> getLoanCheckedByLoanId(Map<String, Object> param) {
		return loanCheckedDao.getByLoanIdAndChecked(param);
	}

	@Override
	public int updateLoanChecked(LoanCheckedDo loanCheckedDo) {
		return loanCheckedDao.updateLoanCheckedById(loanCheckedDo);
	}

	@Override
	public int createLoanChecked(LoanCheckedDo loanCheckedDo) {
		return loanCheckedDao.addLoanChecked(loanCheckedDo);
	}

	@Override
	public LoanDo getLoanforUpdate(Map<String, Object> param) {
		return managerLoanDao.getLoanforUpdate(param);
	}

	/**
	 * 获取渠道列表
	 */
	@Override
	public List<Map<String, Object>> getChannelTypeList() {
		return managerLoanDao.getChannelTypeList();
	}

	@Override
	public LoanDo getFullLoanDo(Long loanId) {
		LoanDo  fullLoanDo = managerLoanDao.getLoanByLoanId(loanId);
		
		LoanPersonDo loanPersonDo=managerLoanDao.getLoanPersonByLoanId(loanId);
		List<LoanRelationDo> loanRelationDoList=managerLoanDao.getLoanRelationDoList(loanId);
		List<CertificateDo> certificateDoList=managerLoanDao.getCertificateDoList(loanId);
		PropertyDo propertyDo=managerLoanDao.getPropertyDo(loanId);
		loanPersonDo.setLoanRelationDoList(loanRelationDoList);
		loanPersonDo.setCertificateDoList(certificateDoList);
		loanPersonDo.setPropertyDo(propertyDo);
		fullLoanDo.setLoanPersonDo(loanPersonDo);
		return fullLoanDo;
		
	}

	@Override
	public List<LoanRelationDo> getLoanRelationDoList(Long loanId) {
		return managerLoanDao.getLoanRelationDoList(loanId);
	}
	
}
