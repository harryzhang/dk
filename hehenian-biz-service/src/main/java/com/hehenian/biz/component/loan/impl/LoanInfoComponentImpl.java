/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan.impl
 * @Title: LoanInfoComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:52:52
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.dataobject.DepositLoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.FundProductDo;
import com.hehenian.biz.common.loan.dataobject.FundUserAccountDo;
import com.hehenian.biz.common.loan.dataobject.LoanInfoDo;
import com.hehenian.biz.common.loan.dataobject.LoanInfoDo.LoanStatus;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.fund.Constants;
import com.hehenian.biz.component.loan.ILoanDetailComponent;
import com.hehenian.biz.component.loan.ILoanInfoComponent;
import com.hehenian.biz.dal.loan.ILoanInfoDao;

/**
 * 
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:52:52
 */
@Component("loanInfoComponent")
public class LoanInfoComponentImpl implements ILoanInfoComponent {
    @Autowired
    private ILoanInfoDao         loanInfoDao;
    @Autowired
    private IPersonComponent     personComponent;
    @Autowired
    private ILoanDetailComponent loanDetailComponent;

    @Override
    public int addLoanInfo(List<LoanInfoDo> loanInfoDoList) {
        if (loanInfoDoList == null || loanInfoDoList.size() == 0) {
            return 0;
        }
        for (LoanInfoDo loanInfoDo : loanInfoDoList) {
            loanInfoDao.addLoanInfo(loanInfoDo);
        }
        return loanInfoDoList.size();
    }

    @Override
    public int updateLoanInfo(LoanInfoDo loanInfoDo) {
        int count = loanInfoDao.updateLoanInfo(loanInfoDo);
        if (count <= 0) {
            throw new BusinessException("修改借款标的[" + loanInfoDo.getLoanInfoId() + "]信息失败!");
        }
        return count;
    }

    @Override
    public List<LoanInfoDo> queryLoanInfos(Map<String, Object> searchItems) {
        return loanInfoDao.queryLoanInfos(searchItems);
    }

    @Override
    public long countLoanInfo(Map<String, Object> searchItems) {
        return loanInfoDao.countLoanInfo(searchItems);
    }

    @Override
    public LoanInfoDo getByLoanInfoId(Long loanInfoId) {
        return loanInfoDao.getByLoanInfoId(loanInfoId);
    }

    @Override
    public List<LoanInfoDo> queryByLoanInfoIds(List<Long> loanInfoIdList) {
        return loanInfoDao.queryByLoanInfoIds(loanInfoIdList);
    }

    @Override
    public void addLoanDetail(LoanInfoDo loanInfoDo, Long userId) {
        // 借款信息详情
        DepositLoanDetailDo detail = initDepositLoanDetail(loanInfoDo, userId);
        // 插入定存系统借款详细信息
        Long loanId = loanDetailComponent.addDepositLoanDetail(detail);
        // 投资产品信息
        FundProductDo prd = initFundProduct(loanInfoDo, userId, loanId);
        // 插入投资产品信息
        loanDetailComponent.addFundProduct(prd);
        // 判断是否存在虚拟资金账户
        int flag = loanDetailComponent.existUserAccount(userId);
        // 如果不存在，则插入虚拟账户
        if (flag == 0) {
            // 虚拟账户信息
            FundUserAccountDo account = new FundUserAccountDo();
            account.setUserId(userId);
            account.setBalanceAmount(new BigDecimal(0));
            loanDetailComponent.addFundUserAccount(account);
        }

        // 变更借款标的状态
        LoanInfoDo updLoanInfoDo = new LoanInfoDo();
        updLoanInfoDo.setLoanInfoId(loanInfoDo.getLoanInfoId());
        updLoanInfoDo.setLoanStatus(LoanStatus.TOCHINAPNR);
        loanInfoDao.updateLoanInfo(updLoanInfoDo);
    }

    private DepositLoanDetailDo initDepositLoanDetail(LoanInfoDo loanInfoDo, Long userId) {
        DepositLoanDetailDo detail = new DepositLoanDetailDo();
        detail.setCreateTime(new Date());
        detail.setAnnualRate(BigDecimal.valueOf(loanInfoDo.getAnnualRate()));
        detail.setBorrowerType(Byte.valueOf("0"));// 借款人类型 TODO
        try {
            detail.setBorrowerType(Byte.valueOf(loanInfoDo.getBorrowGroup()));// 借款人类型
        } catch (NumberFormatException ignore) {
            // 忽略
        }
        detail.setBusinessNum(loanInfoDo.getBusinessNo().trim());// 业务编号
        detail.setDepartment(loanInfoDo.getBorrowGroup());// 所属专区
        detail.setIssuerBrunch(loanInfoDo.getConsultantBranch());// 咨询方分行
        // detail.setLoanId(loanInfoDo.getLoanInfoId());
        detail.setLoanAmount(BigDecimal.valueOf(loanInfoDo.getLoanAmt()));// 借款金额
        detail.setLoanDesc(loanInfoDo.getConsultant()); // 借款描述
        detail.setLoanPeriod(loanInfoDo.getLoanPeriod().shortValue());// 借款期限
        detail.setLoanRate(BigDecimal.valueOf(loanInfoDo.getAnnualRate()));// 借款利率
        detail.setLoanStatus(Byte.valueOf("2"));
        detail.setLoanTitle(loanInfoDo.getLoanUsage());// 借款标题
        detail.setLoanUsage(loanInfoDo.getLoanUsage());// 借款用途
        detail.setRemark(loanInfoDo.getRemark());// 借款描述
        detail.setRepayPeriod(loanInfoDo.getLoanPeriod().shortValue());// 还款周期
        detail.setRepayType(loanInfoDo.getRepayType().byteValue());// 还款方式
        detail.setTenderDay(loanInfoDo.getTenderDay().shortValue());// 筹标期限
        detail.setUserId(userId);
        return detail;
    }

    private FundProductDo initFundProduct(LoanInfoDo loanInfoDo, Long userId, Long loanId) {
        FundProductDo prd = new FundProductDo();
        prd.setAnnualRate(BigDecimal.valueOf(loanInfoDo.getAnnualRate()));
        // prd.setBinLevel("A1");// 投标等级
        prd.setCreateTime(new Date());
        prd.setInvestAmount(BigDecimal.valueOf(loanInfoDo.getLoanAmt()));// 产品投资金额/借款金额
        prd.setInvestedAmount(BigDecimal.ZERO);// 已投金额
        prd.setLoanId(Long.valueOf(loanId));
        prd.setLoanPeriod(loanInfoDo.getLoanPeriod().shortValue());// 借款期限
        prd.setLoanRate(BigDecimal.valueOf(loanInfoDo.getAnnualRate()));// 给借款人的利率
        prd.setLoanType(Byte.valueOf(loanInfoDo.getBorrowGroup()));// 借款类型(0-个人借款1-企业借款2-车易贷
                                                                   // 3-房易贷
                                                                   // 4-担保) TODO
        prd.setProductName(loanInfoDo.getLoanUsage());// 产品名称
        prd.setProductStatus(Byte.valueOf("1"));// 产品状态(1-待发布,2-筹标中,3-已满标,4-已流标,5-还款中,6-已还款,7-冻结,8-废弃)
                                                // TODO
        prd.setProductUsage(loanInfoDo.getLoanUsage());// 产品资金用途
        prd.setPublishTime(new Date());
        prd.setRemark(loanInfoDo.getRemark());
        prd.setRepayPeriod(loanInfoDo.getLoanPeriod().shortValue());// 还款周期
        Byte repayType = Constants.getRepayTypeCode(loanInfoDo.getProductType());
        prd.setRepayType(repayType == null ? 1 : repayType);// 还款方式 TODO
        // prd.setSecurityType(Byte.valueOf("1"));// 保障类型
        prd.setTenderDay(loanInfoDo.getTenderDay().shortValue());// 筹标期限
        prd.setUserId(userId);
        return prd;
    }

}
