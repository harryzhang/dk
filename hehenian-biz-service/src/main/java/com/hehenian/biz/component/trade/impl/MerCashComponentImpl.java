package com.hehenian.biz.component.trade.impl;

import java.util.Date;

import com.hehenian.biz.component.account.IUserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.BankCardDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.FundrecordDo;
import com.hehenian.biz.common.trade.dataobject.MerCashDo;
import com.hehenian.biz.common.trade.dataobject.WithdrawDo;
import com.hehenian.biz.component.account.IBankCardComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IMerCostComponent;
import com.hehenian.biz.dal.account.IUserDao;
import com.hehenian.biz.dal.trade.IMerCashDao;
import com.hehenian.biz.dal.trade.IWithdrawDao;

@Component("merCoshComponent")
public class MerCashComponentImpl implements IMerCostComponent {
    @Autowired
    private IMerCashDao          merCashDao;
    @Autowired
    private IWithdrawDao         withdrawDao;
    @Autowired
    private IFundrecordComponent fundrecordComponnet;
    @Autowired
    private IUserDao             userDao;
    @Autowired
    private IBankCardComponent   bankCardComponent;
    @Autowired
    private IUserComponent       userComponent;

    @Override
    public Long addMerCash(MerCashDo merCashDo) {
        int count = merCashDao.addMerCash(merCashDo);
        if (count <= 0) {
            throw new BusinessException("新增商户提现记录失败!");
        }
        return merCashDo.getId();
    }

    @Override
    public Boolean updateMerCash(MerCashDo merCashDo) {
        // 修改商户提现记录
        int count = merCashDao.updateMerCash(merCashDo);
        if (count != 1) {
            throw new BusinessException("新增商户提现记录失败!");
        }

        // 新增提现申请记录
        WithdrawDo withdrawDo = new WithdrawDo();
        AccountUserDo userDo = userDao.getById(merCashDo.getUserId());
        withdrawDo.setName(userDo.getUsername());// 提现用户名称
        withdrawDo.setAcount(merCashDo.getCardNo());
        withdrawDo.setSum(merCashDo.getSum());
        withdrawDo.setPoundage(merCashDo.getPoundage());
        withdrawDo.setApplyTime(new Date());
        BankCardDo bankCardDo = bankCardComponent.getByUserIdAndCardNo(
                merCashDo.getUserId(), merCashDo.getCardNo());
        if (bankCardDo != null) {
            withdrawDo.setBankId(bankCardDo.getId());
        }
        withdrawDo.setUserId(merCashDo.getUserId());
        withdrawDo.setStatus(2);
		withdrawDao.addWithdraw(withdrawDo);

        //修改用户可用余额
        userComponent.updateAmount(-merCashDo.getSum(), 0.0, merCashDo.getUserId());

		// 新增提现交易记录
		FundrecordDo fundrecordDo = new FundrecordDo();
		fundrecordDo.setUserId(merCashDo.getUserId());
		fundrecordDo.setFundMode("平台取现");
		fundrecordDo.setHandleSum(merCashDo.getSum());
		fundrecordDo.setRecordTime(new Date());
		fundrecordDo.setRemarks("平台取现金额[" + merCashDo.getSum() + "]元,手续费["
				+ merCashDo.getPoundage() + "]元");
		fundrecordDo.setCost(merCashDo.getPoundage());
		fundrecordDo.setIncome(0.00);
		fundrecordDo.setSpending(merCashDo.getSum() + merCashDo.getPoundage());
		fundrecordComponnet.addFundrecord(fundrecordDo);

		return true;
	}

}
