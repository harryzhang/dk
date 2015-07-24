package com.hehenian.biz.component.loan.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanDo.ProcessStep;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.component.loan.ILoanComponent;
import com.hehenian.biz.component.loan.ILoanJobComponent;
import com.hehenian.biz.component.loan.ILoanPersonComponent;
import com.hehenian.biz.component.loan.ILoanRelationComponent;
import com.hehenian.biz.dal.loan.ILoanDao;
import com.hehenian.biz.dal.loan.ILoanProxyCheckDao;

/**
 * @author xiexiangmf
 *
 */
@Component("loanComponent")
public class LoanComponentImpl implements ILoanComponent {
    @Autowired
    private ILoanDao             loanDao;
    @Autowired
    private ILoanPersonComponent loanPersonComponent;
    @Autowired
    private ILoanJobComponent loanJobComponent;
    
    @Autowired
    private ILoanRelationComponent relationComponent;
    
    @Autowired
    private ILoanProxyCheckDao  loanProxyCheckDao;

    @Override
    public Long addLoanDo(LoanDo loanDo) {
        loanDao.addLoanDo(loanDo);
        return loanDo.getLoanId();
    }

    @Override
    public void updateLoanDo(LoanDo loanDo) {

        if (loanDo == null) {
            return ;
        }
        
        loanDao.updateLoanDo(loanDo);
        
        //借款ID
        long loanId =loanDo.getLoanId();
        
        //处理借款人个人信息
        if (loanDo.getLoanPersonDo() != null) {
            LoanPersonDo loanPersonDo = loanDo.getLoanPersonDo();
            loanPersonDo.setLoanId(loanDo.getLoanId());
            if(loanPersonDo.getLoanPersonId() != null){
            	loanPersonComponent.updateLoanPerson(loanPersonDo);
            }else{
            	loanPersonComponent.addLoanPerson(loanPersonDo);
            }
            //个人ID
            long personId = loanPersonDo.getLoanPersonId();
            
            if (loanPersonDo.getJobDo() != null) {  //工作
            	JobDo jobDo = loanPersonDo.getJobDo();
            	jobDo.setLoanId(loanId);
            	jobDo.setLoanPersonId(personId);
            	if(jobDo.getJobId() !=null){
            		loanJobComponent.updateJob(loanPersonDo.getJobDo());
            	}else{
            		loanJobComponent.addJob(loanPersonDo.getJobDo());
            	}
            }
            
            //联系人
            if (loanPersonDo.getLoanRelationDoList() != null && loanPersonDo.getLoanRelationDoList().size()>0) {
//            	for(LoanRelationDo relation: loanPersonDo.getLoanRelationDoList()){
//            		relation.setLoanId(loanId);
//            		relation.setLoanPersonId(personId);
//            		relationComponent.addLoanRelation(relation);
//            	}
            	loanPersonComponent.updateRelationList(loanPersonDo.getLoanRelationDoList(), loanId, personId);
            }
            
        }
    }
    
    
    /**
     * 获取下个状态
     * @param loanDo
     * @return
     */
    @Override
	public LoanStatus getNextStatus(LoanStatus currentStatus, ProcessStep currentStep ,Double applayAmount ){
    	    	
    	if(LoanStatus.PROCESSING.equals(currentStatus)&&ProcessStep.INPUT_CREDIT_REPORT.equals(currentStep)){
    		if(applayAmount.doubleValue() >= 300000){
    			return LoanStatus.PROCESSING;
    		}else{
    			return LoanStatus.AUDITED;
    		}
    	}
    		
    	return LoanStatus.DRAFT;
    }
    
    /**
     * 获取下个步骤
     * @param loanDo
     * @return
     */
    @Override
	public ProcessStep getNextStep(LoanDo loanDo){
    	Double applayAmount = loanDo.getApplyAmount();
    	ProcessStep currentStep = loanDo.getProcessCurrentStep();
    	LoanStatus currentStatus = loanDo.getLoanStatus();
    	
    	if(LoanStatus.PROCESSING.equals(currentStatus)&&ProcessStep.INPUT_CREDIT_REPORT.equals(currentStep)){
    		if(applayAmount.doubleValue() >= 300000){
    			return ProcessStep.XIAODAI_AUDIT;
    		}else{
    			return ProcessStep.TO_SUBJECT;
    		}
    	}
    		
    	return ProcessStep.NULL;
    }

    @Override
    public int changeLoanStatus(LoanDo loanDo) {
        return loanDao.changeLoanStatus(loanDo);
    }

    @Override
    public LoanDo getByIdNo(String idNo) {
        return null;
    }

    @Override
    public int getLoanQty() {
        return loanDao.getLoanQty();
    }

    @Override
    public Long saveLoan(LoanDo loanDo) {
        if (loanDo == null) {
            return 0l;
        }
        if (loanDo.getLoanId() != null && loanDo.getLoanId().intValue() > 0) {
            loanDao.updateLoanDo(loanDo);

        } else {
        	if(null==loanDo.getCreateTime()){
        		 loanDao.addLoanDo(loanDo);
        	}else{
        		 loanDao.addLoanDo1(loanDo);
        	}
           
        }
        //借款ID
        long loanId =loanDo.getLoanId();
        
        
        //处理借款人个人信息
        if (loanDo.getLoanPersonDo() != null) {
            LoanPersonDo loanPersonDo = loanDo.getLoanPersonDo();
            loanPersonDo.setLoanId(loanDo.getLoanId());
            loanPersonComponent.saveLoanPerson(loanDo.getLoanPersonDo());
            //个人ID
            long personId = loanPersonDo.getLoanPersonId();
            
            if (loanPersonDo.getJobDo() != null) {  //工作
            	JobDo jobDo = loanPersonDo.getJobDo();
            	jobDo.setLoanId(loanId);
            	jobDo.setLoanPersonId(personId);
            	loanJobComponent.addJob(loanDo.getLoanPersonDo().getJobDo());
            }
            
            //联系人
            if (loanPersonDo.getLoanRelationDoList() != null && loanPersonDo.getLoanRelationDoList().size()>0) {
            	
            	for(LoanRelationDo relation: loanPersonDo.getLoanRelationDoList()){
            		relation.setLoanId(loanId);
            		relation.setLoanPersonId(personId);
            		relationComponent.addLoanRelation(relation);
            	}
            }
            
        }
        
        return loanDo.getLoanId();
    }

    @Override
    public LoanDo getByLoanId(Long loanId) {
        return loanDao.getByLoanId(loanId);
    }

	@Override
	public List<LoanDo> queryBySourceUserId(String sourceUserId) {
		return loanDao.queryBySourceUserId(sourceUserId);
	}

	@Override
	public Map<String, Object> getTotalInfoByPerson(LoanChannelDo loanChannelDo) {
		List<LoanDo> myLoanList = loanDao.getMyLoanList(loanChannelDo.getId()+"");
		List<LoanDo> refLoanList = loanDao.getMyRefLoanList(loanChannelDo.getLoanUserDo().getMobile());
		int  lateCount = loanDao.queryLateCount(loanChannelDo.getId()+"");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(myLoanList != null ){
			resultMap.put("myLoan", myLoanList.size());
		}
		if(refLoanList != null  ){
			resultMap.put("refLoanList", refLoanList.size());
		}
		resultMap.put("lateCount", lateCount);
		return resultMap;
	}
	
	public String getCmoblie(long cid){
		return loanDao.getCmoblie(cid);
	}
	
	/**
	 * 查询小区的清单
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getAreaList(Map<String,Object> paramMap){
		return loanDao.getAreaList(paramMap);
	}
	
	
	 /**
	  * 根据条件查询
	  * @param loanDo 
	  */
	@Override
	public List<LoanDo> selectLoanList(LoanDo loanDo) {
		return loanDao.selectLoanList(loanDo);
	}

	
	
	/********************审核API**************************/
	@Override
	public LoanProxyCheckDo getProxyCheckDoById(Long id) {
		return loanProxyCheckDao.getById(id);
	}

	public String loanProxyCheckKey(LoanProxyCheckDo loanProxyCheckDo){
		String key=loanProxyCheckDo.getNameCode()==null?"":loanProxyCheckDo.getNameCode();
		key+=loanProxyCheckDo.getFieldName()==null?"":loanProxyCheckDo.getFieldName();
		return key.trim();
	}
	
	@Override
	public List<LoanProxyCheckDo> selectLoanProxyCheck(Map<String, Object> parameterMap) {
//		Map<String, Integer> tempMap =new HashMap<String, Integer>();
//		List<LoanProxyCheckDo> tempList= loanProxyCheckDao.selectLoanProxyCheck(parameterMap);
//		if(null!=tempList&&tempList.size()>0){
//			for(LoanProxyCheckDo temp:tempList){
//				String key=loanProxyCheckKey(temp);
//				if(key.length()>0){
//					tempMap.put(key, temp.getStatus()==null?0:temp.getStatus());
//				}
//			}
//		}
//		return tempMap;
		return loanProxyCheckDao.selectLoanProxyCheck(parameterMap);
	}

	@Override
	public int updateLoanProxyCheckById(LoanProxyCheckDo newLoanProxyCheckDo) {
		int result =  loanProxyCheckDao.updateLoanProxyCheckById(newLoanProxyCheckDo);
		if(result<1){
			throw new BusinessException("审核资料更新出错：newLoanProxyCheckDo id is "+newLoanProxyCheckDo.getId());
		}
		return result;
	}

	@Override
	public int addLoanProxyCheck(LoanProxyCheckDo newLoanProxyCheckDo) {
		int result = loanProxyCheckDao.addLoanProxyCheck(newLoanProxyCheckDo);
		if(result<1){
			throw new BusinessException("审核资料新增出错：newLoanProxyCheckDo: "+newLoanProxyCheckDo);
		}
		return result;
	}

	/********************END 审核API**************************/
	
	@Override
	public List<Map<String, Object>> getJBCmobileDo(Map<String, String> params) {
		return loanDao.getJBCmobileDo(params);
	}

	@Override
	public int updateHousePrice(Map<String, Object> parameterMap) {
		return loanDao.updateHousePrice( parameterMap);
	}

	@Override
	public int saveHousePrice(Map<String, Object> parameterMap) {
		return loanDao.saveHousePrice(parameterMap);
	}
	
	@Override
	public LoanDo getByIdNoGroup(String idNo) {
		return loanDao.getByIdNoGroup( idNo) ;
	}
	
	
}
