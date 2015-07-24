/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.loan.impl
 * @Title: LoanDetailServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月10日 下午7:03:07
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.loan.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.ILoanPersonService;
import com.hehenian.biz.common.loan.dataobject.AuditLogDo;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.loan.IAuditLogComponent;
import com.hehenian.biz.component.loan.ILoanCertificateComponent;
import com.hehenian.biz.component.loan.ILoanComponent;
import com.hehenian.biz.component.loan.ILoanJobComponent;
import com.hehenian.biz.component.loan.ILoanPersonComponent;
import com.hehenian.biz.component.loan.ILoanPropertyComponent;
import com.hehenian.biz.component.loan.ILoanRelationComponent;
import com.hehenian.biz.component.trade.IBorrowComponent;

/**
 * 
 * @author: xiexiang
 */
@Service("loanPersonService")
public class LoanPersonServiceImpl implements ILoanPersonService {
    private final Logger              logger = Logger.getLogger(this.getClass());
    @Autowired
    private ILoanPersonComponent      loanPersonComponent;
    @Autowired
    private ILoanComponent            loanComponent;
    @Autowired
    private IUserComponent            userComponent;
    @Autowired
    private ILoanJobComponent         loanJobComponent;
    @Autowired
    private ILoanRelationComponent    loanRelationComponent;
    @Autowired
    private ILoanPropertyComponent    loanPropertyComponent;
    @Autowired
    private ILoanCertificateComponent loanCertificateComponent;
    @Autowired
    private IBorrowComponent     borrowComponent;
    @Autowired
    private IAuditLogComponent auditLogComponent;
    @Override
    public NPageDo<LoanPersonDo> getLoanPerson(Map searchItems) {
    	NPageDo<LoanPersonDo> loanDo = new NPageDo<LoanPersonDo>();
        try {
            long count = loanPersonComponent.getTotalCount(searchItems);
            loanDo.setTotalCount(count);
            if (count == 0) {
                return loanDo;
            }
            List<LoanPersonDo> loanPersonDo = loanPersonComponent.queryLoanPerson(searchItems);
            loanDo.setModelList(loanPersonDo);
            return loanDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            loanDo.setTotalCount(0l);
            return loanDo;
        }
    }

    @Override
    public IResult<?> updateLoanPerson(LoanPersonDo loanPersonDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
        	logger.info("-----------更新或插入业务受理相关的表开始------------");
        	loanPersonComponent.updateLoanPerson(loanPersonDo);
        	logger.info("-----------更新或插入业务受理相关的表成功------------");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;

    }

    /**
     * 修改审核信息
     * 
     * @param loanPersonDo
     * @return
     */
    @Override
    public IResult<?> updateLoanShInfo(LoanPersonDo loanPersonDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            LoanDo loanDo = loanPersonDo.getLoanDo();
            if (loanDo != null && loanDo.getLoanId() != null) {
                loanComponent.changeLoanStatus(loanDo);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    @Override
    public IResult<?> updateLoanShInfo(LoanPersonDo loanPersonDo,String auditUser,String auditUserId,String preStatus,String reason) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            LoanDo loanDo = loanPersonDo.getLoanDo();
            if (loanDo != null && loanDo.getLoanId() != null) {
                loanComponent.changeLoanStatus(loanDo);
                //生成日志
                AuditLogDo auditLog = new AuditLogDo();
                auditLog.setLoanId(loanDo.getLoanId());
                auditLog.setAfterStatus(loanDo.getLoanStatus().name());
                auditLog.setPreStatus(preStatus);
                auditLog.setAuditUser(auditUser);
                auditLog.setAuditUserId(auditUserId);
                auditLog.setReason(reason);
                auditLogComponent.addAuditLog(auditLog);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public AuditLogDo getOneAuditLogDoByLoanId(Long loanId){
    	return auditLogComponent.getOneAuditLogByLoanId(loanId);
    }
    @Override
    public IResult<?> addLoanPerson(LoanPersonDo loanPersonDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            loanPersonComponent.addLoanPerson(loanPersonDo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;

    }

    @Override
    public LoanPersonDo getCountByIds(Long id) {
        return loanPersonComponent.getCountByIds(id);
    }

    /**
     * 初始化业务受理数据
     */
    @Override
    public LoanPersonDo getInitData(Long loanId) {
        LoanPersonDo loanPersonDo = null;
        try {
            logger.info("---------初始化业务受理数据开始-----------");
            loanPersonDo = loanPersonComponent.getCountByIds(loanId);
            logger.info("---------初始化业务受理数据结束-----------");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return loanPersonDo;
    }

    /**
     * 初始化资产信息
     * @author zhengyfmf
     */
    @Override
	public PropertyDo initPropertyData(Long loanId) {
    	PropertyDo propertyDo = null;
    	try {
    		logger.info("---------初始化资产信息开始-----------");
    		propertyDo = loanPropertyComponent.getCountByIds(loanId);
			logger.info("---------初始资产信息结束-----------");
		} catch (Exception e) {
			 logger.error(e.getMessage(), e);
			 e.printStackTrace();
		}
		return propertyDo;
	}

    /**
     * 修改资产信息
     * @author zhengyfmf
     */
	@Override
	public IResult<?> saveOrUpdateProperty(PropertyDo propertyDo) {
		IResult<String> result = new ResultSupport<String>(true);
        try {
        	logger.info("-----------更新或插入资产信息开始------------");
        	if(propertyDo.getPropertyId() != null && propertyDo.getPropertyId().intValue()>0){
        		loanPropertyComponent.updateProperty(propertyDo);
        	}else{
        		loanPropertyComponent.addProperty(propertyDo);
        	}
        	logger.info("-----------更新或插入资产信息成功------------");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;

	}
	
	/**
	 * 初始 图片资料信息
	 * @author zhengyfmf
	 */
	@Override
	public List<CertificateDo> initCertificateData(Long loanId) {
		List<CertificateDo> certificateDoList = null;
		try {
    		logger.info("---------初始化资产信息开始-----------");
    		certificateDoList = loanCertificateComponent.getCertificateByLoanId(loanId);
			logger.info("---------初始资产信息结束-----------");
		} catch (Exception e) {
			 logger.error(e.getMessage(), e);
			 e.printStackTrace();
		}
		return certificateDoList;
	}

	/**
	 * 修改 图片资产信息
	 * @author zhengyfmf
	 */
	@Override
	public IResult<?> saveOrUpdateCertificate(List<CertificateDo> certificateDoList) {
		IResult<String> result = new ResultSupport<String>(true);
		if(certificateDoList != null && certificateDoList.size()>0){
			for(int i=0;i<certificateDoList.size();i++){
				CertificateDo cd = certificateDoList.get(i);
				try {
					if(cd.getCertificateId() != null){
						loanCertificateComponent.updateCertificate(cd);
					}else{
						loanCertificateComponent.addCertificate(cd);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					result.setSuccess(false);
		            result.setErrorMessage("操作失败,请稍后再试!");
		            e.printStackTrace();
				}
			}
		}else{
			result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
		}
		return result;
	}

	@Override
	public IResult<?> saveOrUpdateCertificate(CertificateDo certificateDo) {
		IResult<String> result = new ResultSupport<String>(true);
		try {
			
			//处理oanpersonId == null的情况
			if(certificateDo.getLoanPersonId() == null){
				LoanPersonDo personDo = this.getByLoanId(certificateDo.getLoanId());
				certificateDo.setLoanPersonId(personDo.getLoanPersonId());
			}
			//end 处理oanpersonId == null的情况
			
			
			if(certificateDo.getCertificateId() != null){
				loanCertificateComponent.updateCertificate(certificateDo);
			}else{
				loanCertificateComponent.addCertificate(certificateDo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
		}
		return result;
	}
	
	 /**
     * 查询上传的图片资料信息
     * @param paraMap
     * @return
     */
    public CertificateDo getcertificate(Map<String, Object> paraMap){
    	String loanId = (String)paraMap.get("loanId");
    	String certificateName = (String)paraMap.get("certificateName");
    	List<CertificateDo>  certificateList = loanCertificateComponent.getCertificateByLoanId(Long.valueOf(loanId));
    	if(null == certificateList || certificateList.size()<1 ){
    		return null;
    	}
    	for(CertificateDo certificate : certificateList){
    		if(certificateName.equals(certificate.getCertificateName())){
    			return certificate;
    		}
    	}
    	return null;
    }
    
    /**
     * 删除上传的图片资料信息
     * @param Long certificateId
     * @return
     */
    public IResult<?> deleteCertificateById(Long certificateId){
    	return loanCertificateComponent.deleteCertificateById(certificateId);
    }
    

	@Override
    public IResult<?> changeloanStatus(LoanPersonDo loanPersonDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            LoanDo loanDo = loanPersonDo.getLoanDo();
            String idNo = loanPersonDo.getIdNo();
            if(idNo!= null || !("".equals(idNo))) {
            	  BorrowDo borrowDo = borrowComponent.getByIdNo(idNo);
            	  if(borrowDo != null && borrowDo.getId() != null) {
            		  loanDo.setBorrowId(borrowDo.getId());
            	  }
            }
            if (loanDo != null && loanDo.getLoanId() != null) {
                loanComponent.updateLoanDo(loanPersonDo.getLoanDo());
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 合约的信息
     * 
     * @param searchItems
     * @return
     */
    @Override
    public NPageDo<LoanPersonDo> queryLoanAuditeds(Map searchItems) {
        NPageDo<LoanPersonDo> loanDo = new NPageDo<LoanPersonDo>();
        try {
            long count = loanPersonComponent.getAuditedTotalCount(searchItems);
            loanDo.setTotalCount(count);
            if (count == 0) {
                return loanDo;
            }
            List<LoanPersonDo> loanPersonDo = loanPersonComponent.queryLoanAuditeds(searchItems);
            loanDo.setModelList(loanPersonDo);
            return loanDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            loanDo.setTotalCount(0l);
            return loanDo;
        }
    }

    @Override
    public IResult<?> uploadFile(LoanPersonDo loanPersonDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            List<CertificateDo> certificateDoList = loanPersonDo.getCertificateDoList();
            if (certificateDoList != null && certificateDoList.size() > 0) {
                for (CertificateDo certificateDo : certificateDoList) {
                    if (certificateDo.getCertificateId() != null) {
                        loanCertificateComponent.updateCertificate(certificateDo);
                    } else {
                        loanCertificateComponent.addCertificate(certificateDo);
                    }
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 自动上标
     */
    @Override
    public IResult<?> loanAutoSubject(LoanPersonDo loanPersonDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            logger.info("-----------------自动上标开始------------------");

            logger.info("-----------------自动上标结束------------------");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }
	@Override
	public LoanPersonDo initTreatyData(Map<String, Object> searchItems) {
		return loanPersonComponent.initTreatyData(searchItems);
	}

    @Override
    public LoanPersonDo getByLoanId(Long loanId) {
        return loanPersonComponent.getByLoanId(loanId);
    }

    @Override
    public IResult<?> saveLoanPerson(LoanPersonDo loanPersonDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            loanPersonComponent.saveLoanPerson(loanPersonDo);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
	@Override
	public Map<String, Object> getYqsl(Map<String, Object> searchItems) {
		Map<String,Object> map = loanPersonComponent.getYqsl(searchItems);
		if(map != null){
			map.put("slExpectedEarnings", map.get("slExpectedEarnings")+"");
			map.put("hyExpectedEarnings", map.get("hyExpectedEarnings")+"");
		}
		return  map;
	}

	@Override
	public NPageDo<LoanPersonDo> getLoanManager(Map<String, Object> searchItems) {
		NPageDo<LoanPersonDo> loanDo = new NPageDo<LoanPersonDo>();
        try {
            long count = loanPersonComponent.getManagerTotalCount(searchItems);
            loanDo.setTotalCount(count);
            if (count == 0) {
                return loanDo;
            }
            List<LoanPersonDo> loanPersonDo = loanPersonComponent.getLoanManager(searchItems);
            loanDo.setModelList(loanPersonDo);
            return loanDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            loanDo.setTotalCount(0l);
            return loanDo;
        }
	}

	@Override
	public NPageDo<RepaymentDo> getRepayMentByBwId(Long borrowId) {
		NPageDo<RepaymentDo> reDo = new NPageDo<RepaymentDo>();
        try {
        	List<RepaymentDo> repaymentDo = loanPersonComponent.getRepayMentByBwId(borrowId);
        	reDo.setModelList(repaymentDo);
            return reDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return reDo;
        }
	}

	@Override
	public Map<String, Object> getdLYqsl(Map<String, Object> searchItems) {
		Map<String,Object> map = loanPersonComponent.getdLYqsl(searchItems);
		if(map != null){
			map.put("dhExpectedEarnings", map.get("dhExpectedEarnings")+"");
		}
		return  map;
	}

	@Override
	public Map<String, Object> getIncomeManager(Map<String, Object> searchItems) {
		Map<String,Object> map = loanPersonComponent.getIncomeManager(searchItems);
		if(map != null){
			map.put("successDs", map.get("successDs")+"");
			map.put("borrowAmount", map.get("borrowAmount")+"");
			map.put("hasPI", map.get("hasPI")+"");
			map.put("stillPI", map.get("stillPI")+"");
		}
		return map;
	}

	@Override
	public IResult<?> saveJobInfo(JobDo jobDo) {
		//界面注意传  loanId  loanPersonId参数
		 IResult<String> result = new ResultSupport<String>(true);
		 try{
			 loanPersonComponent.saveJobInfo(jobDo);
		 }catch(Exception e){
			 result.setSuccess(false);
	         result.setErrorMessage("操作失败,请稍后再试!");
	        logger.error(e.getMessage(), e);
		 }
		
		return result;
	}

	@Override
	public IResult<?> updateRelations(LoanPersonDo loanPerson) {
		 IResult<String> result = new ResultSupport<String>(true);
		 try{
			 loanPersonComponent.updateRelationList(loanPerson.getLoanRelationDoList(), loanPerson.getLoanId(), loanPerson.getLoanPersonId());
		}catch(Exception e){
			 result.setSuccess(false);
	        result.setErrorMessage("操作失败,请稍后再试!");
	       logger.error(e.getMessage(), e);
		 }
		return result;
	}

	@Override
	public IResult<?> updatePersonAndLoan(LoanPersonDo loanPersonDo) {
		
		IResult<String> result = new ResultSupport<String>(true);
        try {
            loanPersonComponent.saveLoanPerson(loanPersonDo);
            //借款用途需要更新
            if(StringUtils.isNotBlank(loanPersonDo.getLoanUsage())){
            	 LoanDo loanDo= new LoanDo();
                 loanDo.setLoanId(loanPersonDo.getLoanId());
                 loanDo.setLoanUsage(loanPersonDo.getLoanUsage());
                 loanComponent.updateLoanDo(loanDo);
            }
           
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
		return result;
	}

	@Override
	public List<LoanPersonDo> queryLoanPersonByApp(
			Map<String, Object> searchItems) {
        try {
        	
            List<LoanPersonDo> loanPersonDo = loanPersonComponent.queryLoanPersonByApp(searchItems);
             
            return loanPersonDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //loanDo.setTotalCount(0l);
            return null;
        }
	}

	@Override
	public Map<String, Object> getSumLoan(Map<String, Object> searchItems) {
		Map<String,Object> map = loanPersonComponent.getSumLoan(searchItems);
		if(map != null){
			map.put("sumIds", map.get("sumIds")!=null?String.valueOf(map.get("sumIds")) : 0 );
			map.put("sumApplyAmount", map.get("sumApplyAmount")!=null? String.valueOf(map.get("sumApplyAmount")): 0);
		} else{
			map =new HashMap<String,Object>();
			map.put("sumIds", 0);
			map.put("sumApplyAmount", 0);
		
		}
		return map;
	}

	@Override
	public Map<String, Object> getSumBorrowing(Map<String, Object> searchItems) {
		//repayStatus=1  未还款
		searchItems.put("repayStatus", 1);
		Map<String,Object> map = loanPersonComponent.getSumBorrowing(searchItems);
		
		searchItems.put("repayStatus", 2);
		Map<String,Object> map2 = loanPersonComponent.getSumBorrowing(searchItems);
		if(map != null){
			Long sumIds =  (map.get("sumIds")!=null?(Long)map.get("sumIds") : 0); 
			map.put("sumIds",sumIds );
			map.put("sumBorrowingAmount",map.get("borrowAmount")!=null? String.valueOf(map.get("borrowAmount")): 0);
			map.put("sumBorrowedAmount", map2.get("borrowAmount")!=null?String.valueOf(map2.get("borrowAmount")):0);
		}else{
			map =new HashMap<String,Object>();
			map.put("sumIds", 0);
			map.put("sumBorrowingAmount", 0);
			map.put("sumBorrowedAmount", 0);
		
		}
		return map;
	}

	@Override
	public Map<String, Object> getSumBorrowed(Map<String, Object> searchItems) {
		Map<String,Object> map = loanPersonComponent.getSumBorrowed(searchItems);
		if(map != null){
			map.put("sumIds", map.get("sumIds")!=null?String.valueOf(map.get("sumIds")) : 0 );
			map.put("sumBorrowAmount",map.get("sumBorrowAmount")!=null?String.valueOf(map.get("sumBorrowAmount")):0);
		}else{
			map =new HashMap<String,Object>();
			map.put("sumIds", 0);
			map.put("sumApplyAmount", 0);
		
		}
		return map;
	}

	@Override
	public List<LoanPersonDo> queryLoanBorrowByApp(
			Map<String, Object> searchItems) {
		try{
		
			List<LoanPersonDo> list = loanPersonComponent.queryLoanBorrowByApp(searchItems);
	
			return list;
		}catch(Exception e){
			logger.error("查询还款订单失败："+e.getMessage());
			return null;
		}
	}

	@Override
	public LoanPersonDo getLoanPersonById(Long loanId) {
		 
		return loanPersonComponent.getLoanPersonById(loanId);
	}

	@Override
	public int getTotalCount(Map<String, Object> searchItems) {
		 
		return loanPersonComponent.getTotalCount(searchItems);
	}

	@Override
	public IResult updateLoanPersonAndChild(LoanPersonDo loanPersonDo) {
		
		IResult<String> result = new ResultSupport<String>(true);
        try {
        	loanPersonComponent.saveLoanPersonChild(loanPersonDo);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
        
	}

    
	
	
}
