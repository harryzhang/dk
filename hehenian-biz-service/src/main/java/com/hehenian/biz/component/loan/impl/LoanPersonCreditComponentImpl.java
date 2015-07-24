/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.loan.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.dataobject.LoanCreditRecDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.ProcessStep;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.loan.ILoanComponent;
import com.hehenian.biz.component.loan.ILoanPersonCreditComponent;
import com.hehenian.biz.dal.loan.ILoanCreditRecDao;
import com.hehenian.biz.dal.loan.ILoanPersonCreditDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("loanPersonCreditComponent")
public class LoanPersonCreditComponentImpl implements ILoanPersonCreditComponent {

	private static Logger logger = Logger.getLogger(LoanPersonCreditComponentImpl.class);
	
	@Autowired
    private ILoanPersonCreditDao  loanPersonCreditDao;
	
	@Autowired
	private ILoanCreditRecDao loanCreditRecDao;
	
	
	@Autowired
	private ILoanComponent loanComponent;
	
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanPersonCreditDo getById(int id){
	  return loanPersonCreditDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanPersonCreditDo> selectLoanPersonCredit(Map<String,Object> parameterMap){
		return loanPersonCreditDao.selectLoanPersonCredit(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanPersonCreditById(LoanPersonCreditDo newLoanPersonCreditDo){
		int result = loanPersonCreditDao.updateLoanPersonCreditById(newLoanPersonCreditDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 验证征信
	 * @param creditRecords
	 * @param loanDo
	 * @return
	 */
	private Map<String,Object> checkCreditData(List<LoanCreditRecDo> creditRecords, LoanDo loanDo){
		Map<String,Object>  resultMap = new HashMap<String,Object>();
		if(null == creditRecords){
			return Collections.EMPTY_MAP;
		}
		
		boolean  isPass = true;
		double   creditAmtIntVal = 0;
		double  repayAmtIntVal = 0;
		
		for(LoanCreditRecDo creditRec : creditRecords ){
			
			String item =  creditRec.getCreditItem();
			String val = creditRec.getCreditItemVal();
			if("repayExceptionNumber".equals(item)){
				if("无".equals(val.trim()) || "非正常".equals(val.trim())){
					isPass=false;
				}
			}
			
			if("overNumber".equals(item)){
				int overIntVal = Integer.valueOf(val);
				if(overIntVal>6){
					isPass=false;
				}
			}
			
			if("queryNumber".equals(item)){
				int queryIntVal = Integer.valueOf(val);
				if(queryIntVal>10){
					isPass=false;
				}
			}
			
			if("creditAmount".equals(item)){
				creditAmtIntVal = Double.valueOf(val);
				creditAmtIntVal = CalculateUtils.mul(1.5, creditAmtIntVal);
			}
			
			if("repayAmount".equals(item)){
				repayAmtIntVal = Double.valueOf(val);
				repayAmtIntVal = CalculateUtils.mul(repayAmtIntVal, loanDo.getLoanPeriod());
			}
		}
		
		resultMap.put("isPass", isPass);
		resultMap.put("auditAmt", creditAmtIntVal>repayAmtIntVal? creditAmtIntVal : repayAmtIntVal );
		return resultMap;
	}
	
	/**
	 * 新增
	 */
	public int addLoanPersonCredit(LoanPersonCreditDo newLoanPersonCreditDo,LoanDo loanDo){
		//将之前的授信额度设置成无效
		loanPersonCreditDao.updateCreditStatusByUser(newLoanPersonCreditDo.getUserId(),"F");
				
		//新增
		int result =  loanPersonCreditDao.addLoanPersonCredit(newLoanPersonCreditDo);
		
		
		
		if(result < 1){
			logger.error("新增授信异常, newLoanPersonCreditDo:"+newLoanPersonCreditDo);
			throw new BusinessException("新增授信异常");
		}else{
			List<LoanCreditRecDo> creditRecords = newLoanPersonCreditDo.getCreditRecordList();
			for(LoanCreditRecDo creditRec : creditRecords){
				creditRec.setCreditId(newLoanPersonCreditDo.getCreditId());
				loanCreditRecDao.addLoanCreditRec(creditRec);
			}
		}
		
		Map<String,Object> creditMap = checkCreditData(newLoanPersonCreditDo.getCreditRecordList(),loanDo);
		
		boolean isPass =(Boolean) creditMap.get("isPass");
		double auditAmt = (Double) creditMap.get("auditAmt");
		
		LoanDo newLoanDo = new LoanDo();
		if(isPass){
			newLoanDo.setLoanId(newLoanPersonCreditDo.getLoanId());
			newLoanDo.setLoanStatus(loanComponent.getNextStatus(LoanDo.LoanStatus.PROCESSING, LoanDo.ProcessStep.INPUT_CREDIT_REPORT, loanDo.getApplyAmount()) );
			newLoanDo.setProcessCurrentStep(LoanDo.ProcessStep.INPUT_CREDIT_REPORT);
			newLoanDo.setApplyAmount(loanDo.getApplyAmount());
			newLoanDo.setProcessNextStep(loanComponent.getNextStep(newLoanDo));
			newLoanDo.setAuditAmount(BigDecimal.valueOf(auditAmt));
			//发送给小贷
//			if(newLoanDo.getProcessNextStep().equals(ProcessStep.XIAODAI_AUDIT)){//通过，走小贷,将数据传给小贷
//			} 
			
		}else{
			newLoanDo.setLoanId(newLoanPersonCreditDo.getLoanId());
			newLoanDo.setLoanStatus(LoanDo.LoanStatus.NOPASS);
			newLoanDo.setProcessCurrentStep(LoanDo.ProcessStep.INPUT_CREDIT_REPORT);
			newLoanDo.setProcessNextStep(LoanDo.ProcessStep.NULL);
			newLoanDo.setApplyAmount(loanDo.getApplyAmount());
			newLoanDo.setAuditAmount(BigDecimal.valueOf(auditAmt));
		}
		//更新订单状态
		loanComponent.changeLoanStatus(newLoanDo);
		
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanPersonCredit(LoanPersonCreditDo newLoanPersonCreditDo){
		//将之前的授信额度设置成无效
		loanPersonCreditDao.updateCreditStatusByUser(newLoanPersonCreditDo.getUserId(),"F");
				
		//新增
		int result =  loanPersonCreditDao.addLoanPersonCredit(newLoanPersonCreditDo);
		
		
		
		if(result < 1){
			logger.error("新增授信异常, newLoanPersonCreditDo:"+newLoanPersonCreditDo);
			throw new BusinessException("新增授信异常");
		}else{
			List<LoanCreditRecDo> creditRecords = newLoanPersonCreditDo.getCreditRecordList();
			for(LoanCreditRecDo creditRec : creditRecords){
				creditRec.setCreditId(newLoanPersonCreditDo.getCreditId());
				loanCreditRecDao.addLoanCreditRec(creditRec);
			}
		}
		
		//更新订单状态
		LoanDo newLoanDo = new LoanDo();
		newLoanDo.setLoanId(newLoanPersonCreditDo.getLoanId());
		newLoanDo.setLoanStatus(LoanDo.LoanStatus.PROCESSING);
		newLoanDo.setProcessCurrentStep(LoanDo.ProcessStep.INPUT_CREDIT_REPORT);
		newLoanDo.setProcessNextStep(loanComponent.getNextStep(newLoanDo));
		loanComponent.changeLoanStatus(newLoanDo);
		
		return result;
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return loanPersonCreditDao.deleteById(id);
	}

	@Override
	public List<LoanPersonCreditDo> selectLoanPersonCreditWithDetail(
			Map<String, Object> parameterMap) {
		List<LoanPersonCreditDo>  personCreditList = loanPersonCreditDao.selectLoanPersonCredit(parameterMap);
		for(LoanPersonCreditDo personCredit : personCreditList){
			Map<String,Object> creditRecParaMap = new HashMap<String,Object>();
			creditRecParaMap.put("creditId", personCredit.getCreditId());
			List<LoanCreditRecDo> creditRecList = loanCreditRecDao.selectLoanCreditRec(creditRecParaMap);
			personCredit.setCreditRecordList(creditRecList);
		}
		return personCreditList;
	}

}
