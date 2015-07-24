package com.hehenian.biz.component.trade.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.AdminDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.FundrecordDo;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationStatus;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationType;
import com.hehenian.biz.common.trade.dataobject.WithdrawDo;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IOperationLogComponent;
import com.hehenian.biz.component.trade.IWithdrawComponent;
import com.hehenian.biz.dal.account.IAdminDao;
import com.hehenian.biz.dal.account.IBankCardDao;
import com.hehenian.biz.dal.account.IPersonDao;
import com.hehenian.biz.dal.account.IUserDao;
import com.hehenian.biz.dal.trade.IInvestDao;
import com.hehenian.biz.dal.trade.IWithdrawDao;

@Component("withdrawComponent")
public class WithdrawComponentImpl implements IWithdrawComponent {
    @Autowired
    private IWithdrawDao           withdrawDao;
    @Autowired
    private IOperationLogComponent operationLogComponent;
    @Autowired
    private IUserComponent         userComponent;
    @Autowired
    private IFundrecordComponent   fundrecordComponnet;
    @Autowired
    private IUserDao               userDao;
    @Autowired
    private IAdminDao              adminDao;
    @Autowired
    private IInvestDao             investDao;
    @Autowired
    private IPersonDao             personDao;
    @Autowired
    private IBankCardDao           bankCardDao;

    @Override
    public Long addWithdraw(WithdrawDo withdrawDo) {
        // 新增提现申请
        AccountUserDo userDo = userComponent.getById(withdrawDo.getUserId());
        withdrawDo.setName(userDo.getUsername());// 提现用户名称
        PersonDo personDo = personDao.getByUserId(userDo.getId());
        withdrawDo.setCellPhone(personDo.getCellPhone());// 电话号码
        // BankCardDo bankCardDo = bankCardDao.getById(withdrawDo.getBankId());
        // withdrawDo.setAcount(bankCardDo.getCardNo());// 银行账户
        withdrawDo.setPoundage(0.00);// 手续费
        withdrawDo.setStatus(0);// 0为提现申请中
        int count = withdrawDao.addWithdraw(withdrawDo);
        if (count <= 0) {
            throw new BusinessException("新增提现申请失败!");
        }
        // 新增操作日志
        operationLogComponent.addOperationLog("t_withdraw", withdrawDo.getName(), 1, withdrawDo.getIpAddress(),
                withdrawDo.getSum(), "申请提现", 1);
        return withdrawDo.getId();
    }

    @Override
    public Integer deleteById(Long id) {
        return withdrawDao.deleteById(id);
    }

    @Override
    public WithdrawDo getById(Long id) {
        return withdrawDao.getById(id);
    }

    @Override
    public Boolean updateWithdraw(WithdrawDo withdrawDo) {
        WithdrawDo localWithdrawDo = withdrawDao.getById(withdrawDo.getId());
        withdrawDo.setVersoin(localWithdrawDo.getVersoin());
        int count = withdrawDao.updateWithdraw(withdrawDo);
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateWithdrawCallback(WithdrawDo withdrawDo) {
        withdrawDo.setStatus(1);// 1-初审核中
        WithdrawDo localWithdrawDo = withdrawDao.getById(withdrawDo.getId());
        withdrawDo.setVersoin(localWithdrawDo.getVersoin());
        withdrawDo.setPoundage((withdrawDo.getPoundage() == null ? 0.00 : withdrawDo.getPoundage()));
        int count = withdrawDao.updateWithdrawApply(withdrawDo);
        if (count != 1) {
            throw new BusinessException("变更提现申请失败!");
        }
        // 修改用户账户信息（可用余额，冻结金额）
        userComponent.updateAmount(-withdrawDo.getSum(), withdrawDo.getSum(), localWithdrawDo.getUserId());

        return true;
    }

    @Override
    public Boolean updateWithdrawAudit(WithdrawDo withdrawDo) {
        withdrawDo.setCheckTime(new Date());
        boolean success = updateWithdraw(withdrawDo);
        if (!success) {
            throw new BusinessException("变更提现申请失败!");
        }

        // 新增操作日志
        AdminDo adminDo = adminDao.getById(withdrawDo.getCheckId());
        operationLogComponent.addOperationLog("t_withdraw", adminDo.getUserName(), 2, adminDo.getLastIp(), 0.00,
                "提现审核通过", 2);
        return true;
    }

    @Override
    public boolean updateWithdrawFault(WithdrawDo withdrawDo) {
        withdrawDo.setCheckTime(new Date());
        boolean success = updateWithdraw(withdrawDo);
        if (!success) {
            throw new BusinessException("变更提现申请失败!");
        }

        WithdrawDo localWithdrawDo = withdrawDao.getById(withdrawDo.getId());
        userComponent.updateAmount(localWithdrawDo.getSum(), -localWithdrawDo.getSum(), localWithdrawDo.getUserId());

        // 新增提现交易记录
        FundrecordDo fundrecordDo = new FundrecordDo();
        fundrecordDo.setUserId(localWithdrawDo.getUserId());
        fundrecordDo.setRecordTime(new Date());
        fundrecordDo.setOperateType(-1);
        fundrecordDo.setFundMode("提现失败");
        fundrecordDo.setHandleSum(localWithdrawDo.getSum());
        AccountUserDo userDo = userDao.getById(localWithdrawDo.getUserId());
        fundrecordDo.setUsableSum(userDo.getUsableSum());// 可用金额
        fundrecordDo.setFreezeSum(userDo.getFreezeSum());// 冻结金额
        Double dueinSum = investDao.getDueinSum(localWithdrawDo.getUserId());
        fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
        fundrecordDo.setTrader(-1l);
        fundrecordDo.setDueoutSum(0.00);
        fundrecordDo.setRemarks("提现失败,解冻冻结金额[" + localWithdrawDo.getSum() + "]元");
        fundrecordDo.setOninvest(0.00);
        fundrecordDo.setPaynumber(localWithdrawDo.getUserId() + "");
        fundrecordDo.setPaybank("");
        fundrecordDo.setCost(0.00);
        fundrecordDo.setIncome(0.00);
        fundrecordDo.setSpending(0.00);
        fundrecordComponnet.addFundrecord(fundrecordDo);

        // 新增操作日志
        AdminDo adminDo = adminDao.getById(withdrawDo.getCheckId());
        operationLogComponent.addOperationLog("t_withdraw", adminDo.getUserName(), 2, adminDo.getLastIp(), 0.00,
                "提现失败", 2);
        return true;
    }

    @Override
    public Boolean updateWithdrawTransfer(WithdrawDo withdrawDo) {
        // 变更提现申请记录，包括审核人，审核时间，状态等
        boolean success = updateWithdraw(withdrawDo);
        if (!success) {
            throw new BusinessException("变更提现申请失败!");
        }

        // 修改用户账户的可用余额和冻结金额
        WithdrawDo localWithdrawDo = withdrawDao.getById(withdrawDo.getId());
        if (withdrawDo.getStatus().intValue() == 2) {
            userComponent.updateAmount(0.00, -withdrawDo.getSum(), localWithdrawDo.getUserId());
        } else {
            userComponent.updateAmount(withdrawDo.getSum(), -withdrawDo.getSum(), localWithdrawDo.getUserId());
        }

        // 新增提现交易记录
        FundrecordDo fundrecordDo = new FundrecordDo();
        fundrecordDo.setUserId(localWithdrawDo.getUserId());
        fundrecordDo.setRecordTime(new Date());
        fundrecordDo.setOperateType(-1);
        if (withdrawDo.getStatus().intValue() == 2) {
            fundrecordDo.setFundMode("提现成功");
            fundrecordDo.setRemarks("提现成功,向您的银行卡划款[" + withdrawDo.getSum() + "]元,手续费[" + localWithdrawDo.getPoundage()
                    + "]元");
            fundrecordDo.setSpending(withdrawDo.getSum());// 支出金额
        } else {
            fundrecordDo.setFundMode("提现失败");
            fundrecordDo.setRemarks("提现失败,解冻冻结金额[" + withdrawDo.getSum() + "]元");
            fundrecordDo.setSpending(0.00);// 支出金额
        }
        fundrecordDo.setHandleSum(withdrawDo.getSum());
        AccountUserDo userDo = userDao.getById(localWithdrawDo.getUserId());
        fundrecordDo.setUsableSum(userDo.getUsableSum());// 可用金额
        fundrecordDo.setFreezeSum(userDo.getFreezeSum());// 冻结金额
        Double dueinSum = investDao.getDueinSum(localWithdrawDo.getUserId());
        fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
        fundrecordDo.setTrader(-1l);
        fundrecordDo.setDueoutSum(0.00);
        fundrecordDo.setOninvest(0.00);
        fundrecordDo.setPaynumber(withdrawDo.getId() + "");
        fundrecordDo.setCost(localWithdrawDo.getPoundage());// 服务费
        fundrecordDo.setIncome(0.0);// 收入金额
        fundrecordDo.setBorrowId(-1l);// 借款编号
        fundrecordDo.setRepaymentId(-1l);// 还款编号
        fundrecordDo.setType(0);// 操作状态：0 成功 1 失败
        fundrecordComponnet.addFundrecord(fundrecordDo);
        return true;
    }

    @Override
    public int countWithdrawQty(Long userId, Date beginDate, Date endDate) {
        return withdrawDao.countWithdrawQty(userId, beginDate, endDate);
    }

    @Override
    public boolean updateStatus(Long withdrawId, Integer status, Integer origStatus) {
        int count = withdrawDao.updateStatus(withdrawId, status, origStatus);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<WithdrawDo> queryByIds(List<Long> withdrawIdList) {
        return withdrawDao.queryByIds(withdrawIdList);
    }

}
