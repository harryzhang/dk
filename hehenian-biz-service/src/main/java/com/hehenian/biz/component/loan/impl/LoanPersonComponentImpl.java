/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan
 * @Title: ILoanDetailComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:54:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.loan.ILoanJobComponent;
import com.hehenian.biz.component.loan.ILoanPersonComponent;
import com.hehenian.biz.component.loan.ILoanPropertyComponent;
import com.hehenian.biz.component.loan.ILoanRelationComponent;
import com.hehenian.biz.dal.loan.ICertificateDao;
import com.hehenian.biz.dal.loan.IJobDao;
import com.hehenian.biz.dal.loan.ILoanDao;
import com.hehenian.biz.dal.loan.ILoanPersonDao;
import com.hehenian.biz.dal.loan.ILoanRelationDao;
import com.hehenian.biz.dal.loan.IPropertyDao;

/**
 * @author xiexiangmf
 *
 */
@Component("loanPersonComponent")
public class LoanPersonComponentImpl implements ILoanPersonComponent {
    @Autowired
    private ILoanPersonDao   loanpersonDao;
    @Autowired
    private ILoanDao         loanDao;
    @Autowired
    private IJobDao          jobDao;
    @Autowired
    private IPropertyDao     propertyDao;
    @Autowired
    private ILoanRelationDao loanRelationDao;
    @Autowired
    private ICertificateDao  certificateDao;
    
    @Autowired
    private ILoanJobComponent loanJobComponent;
    
    @Autowired
    private ILoanRelationComponent relationComponent;
    
    @Autowired
    private ILoanPropertyComponent loanPropertyComponent;

    /**
     * @author wangt
     */
    @Override
    public void saveJobInfo(JobDo jobDo){
        if (jobDo != null && jobDo.getJobId() != null) {
            jobDao.updateJob(jobDo);
        } else {
            jobDao.addJob(jobDo);
        }
    }
    @Override
    public void updateRelationList(List<LoanRelationDo> loanRelationDoList,long loanId,long loanPersonId){
    	if (loanRelationDoList != null && loanRelationDoList.size() > 0) {
            for (LoanRelationDo loanRelationDo : loanRelationDoList) {
                loanRelationDo.setLoanId(loanId);
                loanRelationDo.setLoanPersonId(loanPersonId);
                if (loanRelationDo.getRalationId() != null) {
                    loanRelationDao.updateLoanRelation(loanRelationDo);
                } else {
                	if(StringUtils.isNotBlank(loanRelationDo.getRalationName()) ){
                		loanRelationDao.addLoanRelation(loanRelationDo);
                	}
                    
                }
            }
        }
    }
    
    
    @Override
    public void updateLoanPerson(LoanPersonDo loanPersonDo) {
        loanpersonDao.updateLoanPerson(loanPersonDo);

        LoanDo loanDo = loanPersonDo.getLoanDo();
        if (loanDo != null && loanDo.getLoanId() != null) {
            loanDao.updateLoanDo(loanDo);
        }
        // 插入或更新jobDo数据
        JobDo jobDo = loanPersonDo.getJobDo();
        if(null != jobDo ){
	        jobDo.setLoanId(loanPersonDo.getLoanId());
	        jobDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
	        if (jobDo != null && jobDo.getJobId() != null) {
	            jobDao.updateJob(jobDo);
	        } else {
	            jobDao.addJob(jobDo);
	        }
        }
        
        PropertyDo propertyDo = loanPersonDo.getPropertyDo();
        if(null != propertyDo){
	        propertyDo.setLoanId(loanPersonDo.getLoanId());
	        propertyDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
	        if (propertyDo != null && propertyDo.getPropertyId() != null) {
	            propertyDao.updateProperty(propertyDo);
	        } else {
	            propertyDao.addProperty(propertyDo);
	        }
        }

        // 附件上传
        List<CertificateDo> certificateDoList = loanPersonDo.getCertificateDoList();
        if (certificateDoList != null && certificateDoList.size() > 0) {
            for (CertificateDo certificateDo : certificateDoList) {
                if (certificateDo.getCertificateId() != null) {
                    certificateDao.updateCertificate(certificateDo);
                } else {
                    certificateDao.addCertificate(certificateDo);
                }
            }
        }

        List<LoanRelationDo> loanRelationDoList = loanPersonDo.getLoanRelationDoList();
        if (loanRelationDoList != null && loanRelationDoList.size() > 0) {
            for (LoanRelationDo loanRelationDo : loanRelationDoList) {
                loanRelationDo.setLoanId(loanPersonDo.getLoanId());
                loanRelationDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
                if (loanRelationDo.getRalationId() != null) {
                    loanRelationDao.updateLoanRelation(loanRelationDo);
                } else {
                    loanRelationDao.addLoanRelation(loanRelationDo);
                }
            }
        }
    }

    @Override
    public List<LoanPersonDo> queryLoanPerson(Map<String, Object> searchItems) {
        return loanpersonDao.queryLoanPersons(searchItems);
    }

    @Override
    public LoanPersonDo getCountByIds(Long loanId) {
        return loanpersonDao.getLoanPersonByIds(loanId);
    }

    @Override
    public void addLoanPerson(LoanPersonDo loanPersonDo) {
        loanpersonDao.addLoanPerson(loanPersonDo);
    }

    @Override
    public int getTotalCount(Map<String, Object> searchItems) {
        return loanpersonDao.getTotalCount(searchItems);
    }

    @Override
    public int getAuditedTotalCount(Map<String, Object> searchItems) {
        return loanpersonDao.getAuditedTotalCount(searchItems);
    }

    @Override
    public List<LoanPersonDo> queryLoanAuditeds(Map<String, Object> searchItems) {
        return loanpersonDao.queryLoanAuditeds(searchItems);
    }

    @Override
    public LoanPersonDo getByLoanId(Long loanId) {
        return loanpersonDao.getByLoanId(loanId);
    }

    @Override
    public int saveLoanPerson(LoanPersonDo loanPersonDo) {
        if (loanPersonDo == null) {
            return 0;
        }
        if (loanPersonDo.getLoanPersonId() != null && loanPersonDo.getLoanPersonId().longValue() >= 0) {
            return loanpersonDao.updateLoanPerson(loanPersonDo);
        } else {
            return loanpersonDao.addLoanPerson(loanPersonDo);
        }
    }
    
    
    /**
     * 更新个人信息，并保存子对象, 用于用户申报的时候第二提交个人资料和资产，job 联系人用的
     * @param loanPersonDo
     * @return
     */
    @Override
	public void saveLoanPersonChild(LoanPersonDo loanPersonDo){
    	
    	LoanPersonDo  oldPersonDo = this.getLoanPersonById(loanPersonDo.getLoanId());
    	
    	oldPersonDo.setEducation(loanPersonDo.getEducation());
    	oldPersonDo.setMarriaged(loanPersonDo.getMarriaged());
    	oldPersonDo.setEmail(loanPersonDo.getEmail());
    	loanpersonDao.updateLoanPerson(oldPersonDo);//保存更新
    	
    	Double houseArea = null;
    	
    	
    	long loanId = loanPersonDo.getLoanId();
    	
    	//处理借款人个人信息
        if (loanPersonDo != null) {
            //个人ID
            long personId = oldPersonDo.getLoanPersonId();
            //job
            if (loanPersonDo.getJobDo() != null) {  //工作
            	JobDo jobDo = loanPersonDo.getJobDo();
            	jobDo.setLoanId(loanId);
            	jobDo.setLoanPersonId(personId);
            	JobDo jobDotemp =loanJobComponent.getJobByIds(loanId+"");
            	if(jobDotemp==null){
                	loanJobComponent.addJob(loanPersonDo.getJobDo());
            	}else{
            		loanPersonDo.getJobDo().setJobId(jobDotemp.getJobId());
            		loanJobComponent.updateJob(loanPersonDo.getJobDo());
            	}
            }
            
            //联系人
            relationComponent.deleteLoanRelationByLoanId(loanId);
            if (loanPersonDo.getLoanRelationDoList() != null && loanPersonDo.getLoanRelationDoList().size()>0) {
            	
            	for(LoanRelationDo relation: loanPersonDo.getLoanRelationDoList()){
            		relation.setLoanId(loanId);
            		relation.setLoanPersonId(personId);
            		relationComponent.addLoanRelation(relation);
            	}
            }
            
            
            //资产
            if(loanPersonDo.getPropertyDo() != null){
            	PropertyDo  propertyDo1 =loanPropertyComponent.getCountByIds(loanId);
            	PropertyDo  propertyDo = loanPersonDo.getPropertyDo();
            	
            	//暂存房子面积,用来计算房价
            	if("T".equals(oldPersonDo.getHasHouse())){
            		houseArea = propertyDo.getCoveredArea();
            	}
            	
            	propertyDo.setLoanId(loanId);
            	propertyDo.setLoanPersonId(personId);
            	if(propertyDo1==null){
            		loanPropertyComponent.addProperty(propertyDo);
            	}else{
            		propertyDo.setPropertyId(propertyDo1.getPropertyId());
            		loanPropertyComponent.updateProperty(propertyDo);
            	}
            	
            }
            
        }
        
        
        //计算房价， 更具房价更新风控金额
        if("T".equals(oldPersonDo.getHasHouse()) &&  null != houseArea){
    		double price = getCnameHousePriceById(oldPersonDo.getCid());
    		double houseAmount = CalculateUtils.mul(price, houseArea);
    		houseAmount = CalculateUtils.mul(0.1, houseAmount);
    		LoanDo houseLoanDo = new LoanDo();
    		houseLoanDo.setAuditAmount(BigDecimal.valueOf(houseAmount));
    		houseLoanDo.setLoanId(loanId);
    		loanDao.updateLoanDo(houseLoanDo);
    	}
        
    }
    
	/**
	 * 获取小区房价
	 * @param cid 小区ID
	 * @return
	 */
	public double getCnameHousePriceById(Long cid){
		List<Map<String,Object>> cList = loanDao.getCname(cid);
		if(null == cList || cList.size()<1){
			return 0;
		}
		Map<String,Object> cname = cList.get(0);
		BigDecimal  price =  cname.get("housePrice")==null?BigDecimal.ZERO:(BigDecimal)cname.get("housePrice");
//		BigDecimal  price = (BigDecimal) cname.get("housePrice");
		return price.doubleValue();
	}

    @Override
    public LoanPersonDo initTreatyData(Map<String, Object> searchItems) {
        return loanpersonDao.initTreatyData(searchItems);
    }
	
	
	@Override
	public Map<String, Object> getYqsl(Map<String, Object> searchItems) {
		return  loanpersonDao.getYqsl(searchItems);
	}

	@Override
	public int getManagerTotalCount(Map<String, Object> searchItems) {
		return loanpersonDao.getManagerTotalCount(searchItems);
	}

	@Override
	public List<LoanPersonDo> getLoanManager(Map<String, Object> searchItems) {
		return loanpersonDao.getLoanManager(searchItems);
	}
	
	@Override
	public List<RepaymentDo> getRepayMentByBwId(Long borrowId) {
		return loanpersonDao.getRepayMentByBwId(borrowId);
	}

	@Override
	public Map<String, Object> getdLYqsl(Map<String, Object> searchItems) {
		return loanpersonDao.getdLYqsl(searchItems);
	}

	@Override
	public Map<String, Object> getIncomeManager(Map<String, Object> searchItems) {
		return loanpersonDao.getIncomeManager(searchItems);
	}
	@Override
	public List<LoanPersonDo> queryLoanPersonByApp(
			Map<String, Object> searchItems) {
		 
		return loanpersonDao.queryLoanPersonsByApp(searchItems);
	}
	@Override
	public Map<String, Object> getSumLoan(Map<String, Object> searchItems) {
		 
		return loanpersonDao.getSumLoan(searchItems);
	}
	@Override
	public Map<String, Object> getSumBorrowing(Map<String, Object> searchItems) {
		return loanpersonDao.getSumBorrowing(searchItems);
	}
	@Override
	public Map<String, Object> getSumBorrowed(Map<String, Object> searchItems) {
		return loanpersonDao.getSumBorrowed(searchItems);
	}
	@Override
	public List<LoanPersonDo> queryLoanBorrowByApp(
			Map<String, Object> searchItems) {
		return loanpersonDao.queryLoanBorrowByApp(searchItems);
	}
	@Override
	public LoanPersonDo getLoanPersonById(Long loanId) {
 		return loanpersonDao.getLoanPersonById(loanId);
	}
}
