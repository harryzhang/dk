package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.loan.dataobject.AuditLogDo;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;

/**
 * 业务受理
 * 
 * @author xiexiangmf
 *
 */
public interface ILoanPersonService {

    /**
     * 查询对账信息
     * 
     * @param parameterMap
     * @param pageBean
     * @return
     * @author: xiexiangmf
     * @date: 2014年12月23日下午2:41:01
     */
    NPageDo<LoanPersonDo> getLoanPerson(Map parameterMapn);

    /**
     * 修改借款人信息
     */
    IResult<?> updateLoanPerson(LoanPersonDo loanPersonDo);

    /**
     * 新增借款人信息
     * 
     * @return
     * 
     */
    IResult<?> addLoanPerson(LoanPersonDo loanPersonDo);

    /**
     * 根据ID查询表的记录
     * 
     * @return
     */
    LoanPersonDo getCountByIds(Long id);

    /**
     * 初始化数据
     * 
     * @param loanId
     * @return
     */
    LoanPersonDo getInitData(Long loanId);

    /**
     * 初始化 资产信息
     * @param loanId
     * @author zhengyfmf
     * @return
     */
    PropertyDo initPropertyData(Long loanId);
    
    /**
     * 修改 资产信息
     * @param propertyDo
     * @author zhengyfmf
     */
    IResult<?> saveOrUpdateProperty(PropertyDo propertyDo);
    
    /**
     * 初始 图片资料信息
     * @param loanId
     * @author zhengyfmf
     * @return
     */
    List<CertificateDo> initCertificateData(Long loanId);
    
    /**
     * 修改 图片资料信息
     * @param certificateDo
     * @return
     */
    IResult<?> saveOrUpdateCertificate(List<CertificateDo> certificateDoList);
    
    /**
     * 查询上传的图片资料信息
     * @param paraMap
     * @return
     */
    CertificateDo getcertificate(Map<String, Object> paraMap);
    
    /**
     * 删除上传的图片资料信息
     * @param certificateDo
     * @return
     */
    IResult<?> deleteCertificateById(Long certificateId);
    
    /**
     * 修改 保存图片资料
     * @param certificateDo
     * @author zhengyfmf
     * @return
     */
    IResult<?> saveOrUpdateCertificate(CertificateDo certificateDo);
    /**
     * 提交之后更变借款状态
     * 
     * @param loanId
     * @return
     */
    IResult<?> changeloanStatus(LoanPersonDo loanPersonDo);

    /**
     * 修改审核信息
     * 
     * @param loanPersonDo
     * @return
     */
    IResult<?> updateLoanShInfo(LoanPersonDo loanPersonDo);

    /**
     * 查询签约记录
     * 
     * @param searchItems
     * @return
     */
    NPageDo<LoanPersonDo> queryLoanAuditeds(Map searchItems);

    /**
     * 上传文件
     * 
     * @param loanPersonDo
     * @return
     */
    IResult<?> uploadFile(LoanPersonDo loanPersonDo);

    /**
     * 自动上标
     * 
     * @param loanPersonDo
     * @return
     */
    IResult<?> loanAutoSubject(LoanPersonDo loanPersonDo);

    /**
     * 根据借款申请ID查询借款人信息
     * 
     * @param loanId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日下午4:02:19
     */
    LoanPersonDo getByLoanId(Long loanId);

    /**
     * 保存借款人信息
     * 
     * @param userId
     * @param loanPersonDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月26日下午4:06:31
     */
    IResult<?> saveLoanPerson(LoanPersonDo loanPersonDo);
    
	LoanPersonDo initTreatyData(Map<String, Object> searchItems);

	/**
	 * 预期收益
	 * @param searchItems
	 * @return
	 */
	Map<String, Object> getYqsl(Map<String, Object> searchItems);
	
	/**
	 * 贷后管理查询
	 * @param searchItems
	 * @return
	 */
	NPageDo<LoanPersonDo> getLoanManager(Map<String, Object> searchItems);
	
	/**
	 * 贷后管理预期收益
	 * @param searchItems
	 * @return
	 */
	NPageDo<RepaymentDo> getRepayMentByBwId(Long borrowId);
	
	/**
	 * 查询贷后管理预期收益
	 * @param searchItems
	 * @return
	 */
	Map<String, Object> getdLYqsl(Map<String, Object> searchItems);

	/**
	 * 业绩查询
	 * @param searchItems
	 * @return
	 */
	Map<String, Object> getIncomeManager(Map<String, Object> searchItems);

	/**
	 * 保存工作信息
	 * @param jobDo
	 */
	IResult<?> saveJobInfo(JobDo jobDo);
	/**
	 * 保存借款人信息
	 * @param loanPerson
	 */
	IResult<?> updateRelations(LoanPersonDo loanPerson);
	/**
	 * @author wangt
	 * 更新借款人信息和借款信息
	 * @param loanPerson
	 * @return
	 */
	IResult<?> updatePersonAndLoan(LoanPersonDo loanPerson);
	
	/**
	 * 查询借款人信息（App端）
	 * @author wangt  
	 *
	 * 2015年3月30日 上午11:37:17 
	 * @param searchItems
	 * @return
	 */
	List<LoanPersonDo> queryLoanPersonByApp(Map<String, Object> searchItems);
	
	/**
	 * 业务查询模块-》查询新订单 已拒绝 的订单数量以及总额
	 * @author wangt
	 * @param searchItems
	 * @return
	 */
	Map<String,Object> getSumLoan(Map<String,Object> searchItems);
	
	/**
	 * 业务查询模块-》查询还款中的订单总数
	 * @author wangt
	 * @param searchItems
	 * @return
	 */
	Map<String,Object> getSumBorrowing(Map<String,Object> searchItems);
	
	/**
	 * 业务查询模块-》查询已还款
	 * @author wangt
	 * @param searchItems
	 * @return
	 */
	Map<String,Object> getSumBorrowed(Map<String,Object> searchItems);

	/**
	 * 业绩查询模块-》查询还款相关订单
	 * @author wangt  
	 *
	 * 2015年3月30日 下午5:50:43 
	 * @param searchItems
	 * @return
	 */
	List<LoanPersonDo> queryLoanBorrowByApp(Map<String, Object> searchItems);
	
	/**
	 * 根据loanId 查询loanPerson loan的信息
	 * @author wangt  
	 *
	 * 2015年4月1日 下午3:45:05 
	 * @param loanId
	 * @return
	 */
	LoanPersonDo  getLoanPersonById(Long loanId); 
	/**
	 * 审核流程  新增审核日志
	 * @author wangt  
	 *
	 * 2015年4月1日 下午4:58:25 
	 * @param loanPersonDo
	 * @param auditUser
	 * @param auditUserId
	 * @param preStatus
	 * @param reason
	 * @return
	 */
    IResult<?> updateLoanShInfo(LoanPersonDo loanPersonDo,String auditUser,String auditUserId,String preStatus,String reason);

    /**
     * 获取借款人最新的一条审核日志
     * @author wangt  
     *
     * 2015年4月1日 下午5:12:54 
     * @param loanId
     * @return
     */
    AuditLogDo getOneAuditLogDoByLoanId(Long loanId);
    
    /**
     * 根据条件查询借款人信息
     * 
     * @param searchItems
     * @return
     */
    int getTotalCount(Map<String, Object> searchItems);

	IResult updateLoanPersonAndChild(LoanPersonDo loanPersonDo);
	
}
