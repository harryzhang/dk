/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.loan.impl
 * @Title: LoanApplyServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月26日 下午4:23:11
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.ILoanApplyService;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.component.loan.ILoanComponent;
import com.hehenian.biz.component.trade.IBorrowComponent;
import com.hehenian.common.redis.SpringRedisCacheService;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月26日 下午4:23:11
 */
@Service("loanApplyService")
public class LoanApplyServiceImpl implements ILoanApplyService {
    @Autowired
    private ILoanComponent loanComponent;
    
    @Autowired
	private SpringRedisCacheService localCacheService;
    
    @Autowired
    private IBorrowComponent     borrowComponent;
    
    private static String cacheKeypriv="E_LOAN_ORDER_";// E贷款的缓存KEY前缀
    private static int   expTime= 12*60*60; //设置缓存一天的时间

    @Override
    public int getLoanQty() {
        return loanComponent.getLoanQty();
    }
    
    /**
     * 
     * @param oldLoanDo
     * @param newLoanDo
     */
    private void setIdFromCacheLoan(String cacheKey, LoanDo loanDo){
    	LoanDo oldLoanDo = (LoanDo)localCacheService.get(cacheKey);
    	
    	loanDo.setOrderCode(oldLoanDo.getOrderCode());
        
        //借款ID
        long loanId =loanDo.getLoanId();
        
        
        //处理借款人个人信息
        if (loanDo.getLoanPersonDo() != null) {
        	LoanPersonDo loanPersonDo = loanDo.getLoanPersonDo();
        	LoanPersonDo oldLoanPersonDo = oldLoanDo.getLoanPersonDo();
        	long personId = -1;
        	if(oldLoanPersonDo != null){
        		//个人ID
                personId = oldLoanPersonDo.getLoanPersonId();
                loanPersonDo.setLoanPersonId(personId);
                loanPersonDo.setLoanId(loanId);
        	}
            
            if (loanPersonDo.getJobDo() != null) {  //工作
            	JobDo jobDo = loanPersonDo.getJobDo();
            	jobDo.setLoanId(loanId);
            	jobDo.setLoanPersonId(personId);
            	if(oldLoanPersonDo.getJobDo() != null){
            		jobDo.setJobId(oldLoanPersonDo.getJobDo().getJobId());
            	}
            }
            
            //联系人
            if (loanPersonDo.getLoanRelationDoList() != null && loanPersonDo.getLoanRelationDoList().size()>0) {
            	
            	for(int i = 0 ; i < loanPersonDo.getLoanRelationDoList().size();i++){
            		LoanRelationDo relation = loanPersonDo.getLoanRelationDoList().get(i);
            		relation.setLoanId(loanId);
            		relation.setLoanPersonId(personId);
            		if(oldLoanPersonDo.getLoanRelationDoList() != null){
            			if(null != oldLoanPersonDo.getLoanRelationDoList().get(i)){
            				relation.setRalationId(oldLoanPersonDo.getLoanRelationDoList().get(i).getRalationId());
            			}
            		}
            	}
            }
            
        }
    }

    @Override
    public Long saveLoan(LoanDo loanDo) {
//    	String cacheKey = null;
    	long result ;
        if(null != loanDo.getLoanId()){
//        	cacheKey = cacheKeypriv+loanDo.getLoanId();
//        	setIdFromCacheLoan(cacheKey,loanDo);
        	loanComponent.updateLoanDo(loanDo);
        	result = loanDo.getLoanId();
        }else{
        	result = loanComponent.saveLoan(loanDo);
        }
        
//        cacheKey = cacheKeypriv+loanDo.getLoanId();
//        localCacheService.set(cacheKey, loanDo, expTime);
        return result;
    }

    @Override
    public LoanDo getByLoanId(Long loanId) {
        return loanComponent.getByLoanId(loanId);
    }

    @Override
    public List<LoanDo> queryBySourceUserId(String sourceUserId) {
        return loanComponent.queryBySourceUserId(sourceUserId);
    }

	@Override
	public Map<String, Object> getTotalInfoByPerson(LoanChannelDo loanChannelDo) {
		return loanComponent.getTotalInfoByPerson(loanChannelDo);
	}

	@Override
	public String getCmoblie(long cid){
		return loanComponent.getCmoblie(cid);
	}
	

	@Override
	public List<Map<String, Object>> getAreaList(Map<String,Object> paramMap){
		return loanComponent.getAreaList(paramMap);
	}
	 /**
	  * 根据条件查询
	  * @param loanDo 
	  */
	@Override
	public List<LoanDo> selectLoanList(LoanDo loanDo) {
		return loanComponent.selectLoanList(loanDo);
	}

	
	
	/********************审核API**************************/
	@Override
	public LoanProxyCheckDo getProxyCheckDoById(Long id) {
		return loanComponent.getProxyCheckDoById(id);
	}

	@Override
	public List<LoanProxyCheckDo> selectLoanProxyCheck(Map<String, Object> parameterMap) {
		return loanComponent.selectLoanProxyCheck(parameterMap);
	}

	@Override
	public int updateLoanProxyCheckById(LoanProxyCheckDo newLoanProxyCheckDo) {
		return loanComponent.updateLoanProxyCheckById(newLoanProxyCheckDo);
	}

	@Override
	public int addLoanProxyCheck(LoanProxyCheckDo newLoanProxyCheckDo) {
		return loanComponent.addLoanProxyCheck(newLoanProxyCheckDo);
	}

	/********************end 审核API**************************/

	@Override
	public List<Map<String, Object>> getJBCmobileDo(Map<String, String> params) {
		return loanComponent.getJBCmobileDo(params);
	}

	@Override
	public int updateHousePrice(Map<String, Object> parameterMap) {
		return loanComponent.updateHousePrice( parameterMap);
	}

	@Override
	public int saveHousePrice(Map<String, Object> parameterMap) {
		return loanComponent.saveHousePrice(parameterMap);
	}

	@Override
	public LoanDo getByIdNoGroup(String idNo) {
		LoanDo loanDo =loanComponent.getByIdNoGroup(idNo);
        return loanDo;
	}
	
}
