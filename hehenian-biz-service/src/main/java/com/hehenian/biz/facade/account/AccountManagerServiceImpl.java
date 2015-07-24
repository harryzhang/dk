package com.hehenian.biz.facade.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

@Service("accountManagerService")
public class AccountManagerServiceImpl implements IAccountManagerService {
    @Autowired
    private IUserManager  chinaPnrUserManager; // 用户管理接口
    @Autowired
    private ITradeManager chinaPnrTradeManager; // 交易类接口
    @Autowired
    private IQueryManager chinaPnrQueryManager; // 查询类接口

    /**
     * 获取用户管理具体实现类
     * 
     * @param accountType
     * @return
     */
    private IUserManager getUserManager(AccountType accountType) {
        if (AccountType.CHINAPNR.equals(accountType)) {
            return chinaPnrUserManager;
        } else if (AccountType.PINGAN.equals(accountType)) {
            return null;
        } else {
            throw new RuntimeException("未知类型!");
        }
    }

    /**
     * 获取交易具体实现类
     * 
     * @param accountType
     * @return
     */
    private ITradeManager getTradeManager(AccountType accountType) {
        if (AccountType.CHINAPNR.equals(accountType)) {
            return chinaPnrTradeManager;
        } else if (AccountType.PINGAN.equals(accountType)) {
            return null;
        } else {
            throw new RuntimeException("未知类型!");
        }
    }

    /**
     * 获取查询具体实现类
     * 
     * @param accountType
     * @return
     */
    private IQueryManager getQueryManager(AccountType accountType) {
        if (AccountType.CHINAPNR.equals(accountType)) {
            return chinaPnrQueryManager;
        } else if (AccountType.PINGAN.equals(accountType)) {
            return null;
        } else {
            throw new RuntimeException("未知类型!");
        }
    }

    @Override
    public OutParameter userRegister(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).userRegister(inParameter);
    }

    @Override
    public OutParameter bgRegister(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).bgRegister(inParameter);
    }

    @Override
    public OutParameter userBindCard(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).userBindCard(inParameter);
    }

    @Override
    public OutParameter bgBindCard(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).bgBindCard(inParameter);
    }

    @Override
    public OutParameter userLogin(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).userLogin(inParameter);
    }

    @Override
    public OutParameter acctModify(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).acctModify(inParameter);
    }

    @Override
    public OutParameter corpRegister(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).corpRegister(inParameter);
    }

    @Override
    public OutParameter delCard(InParameter inParameter, AccountType accountType) {
        return getUserManager(accountType).delCard(inParameter);
    }

    @Override
    public OutParameter netSave(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).netSave(inParameter);
    }

    @Override
    public OutParameter posWhSave(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).posWhSave(inParameter);
    }

    @Override
    public OutParameter usrFreezeBg(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).usrFreezeBg(inParameter);
    }

    @Override
    public OutParameter usrUnFreeze(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).usrUnFreeze(inParameter);
    }

    @Override
    public OutParameter initiativeTender(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).initiativeTender(inParameter);
    }

    @Override
    public OutParameter autoTender(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).autoTender(inParameter);
    }

    @Override
    public OutParameter tenderCancle(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).tenderCancle(inParameter);
    }

    @Override
    public OutParameter autoTenderPlan(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).autoTenderPlan(inParameter);
    }

    @Override
    public OutParameter autoTenderPlanClose(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).autoTenderPlanClose(inParameter);
    }

    @Override
    public OutParameter loans(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).loans(inParameter);
    }

    @Override
    public OutParameter repayment(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).repayment(inParameter);
    }

    @Override
    public OutParameter transfer(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).transfer(inParameter);
    }

    @Override
    public OutParameter cashAudit(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).cashAudit(inParameter);
    }

    @Override
    public OutParameter cash(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).cash(inParameter);
    }

    @Override
    public OutParameter usrAcctPay(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).usrAcctPay(inParameter);
    }

    @Override
    public OutParameter merCash(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).merCash(inParameter);
    }

    @Override
    public OutParameter usrTransfer(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).usrTransfer(inParameter);
    }

    @Override
    public OutParameter creditAssign(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).creditAssign(inParameter);
    }

    @Override
    public OutParameter autoCreditAssign(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).autoCreditAssign(inParameter);
    }

    @Override
    public OutParameter fssTrans(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).fssTrans(inParameter);
    }

    @Override
    public OutParameter direcTrfAuth(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).direcTrfAuth(inParameter);
    }

    @Override
    public OutParameter direcTrf(InParameter inParameter, AccountType accountType) {
        return getTradeManager(accountType).direcTrf(inParameter);
    }

    @Override
    public OutParameter queryBalance(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryBalance(inParameter);
    }

    @Override
    public OutParameter queryBalanceBg(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryBalanceBg(inParameter);
    }

    @Override
    public OutParameter queryAccts(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryAccts(inParameter);
    }

    @Override
    public OutParameter queryTransStat(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryTransStat(inParameter);
    }

    @Override
    public OutParameter queryTenderPlan(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryTenderPlan(inParameter);
    }

    @Override
    public OutParameter reconciliation(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).reconciliation(inParameter);
    }

    @Override
    public OutParameter trfReconciliation(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).trfReconciliation(inParameter);
    }

    @Override
    public OutParameter cashReconciliation(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).cashReconciliation(inParameter);
    }

    @Override
    public OutParameter queryAcctDetails(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryAcctDetails(inParameter);
    }

    @Override
    public OutParameter saveReconciliation(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).saveReconciliation(inParameter);
    }

    @Override
    public OutParameter queryReturnDzFee(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryReturnDzFee(inParameter);
    }

    @Override
    public OutParameter corpRegisterQuery(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).corpRegisterQuery(inParameter);
    }

    @Override
    public OutParameter creditAssignReconciliation(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).creditAssignReconciliation(inParameter);
    }

    @Override
    public OutParameter fssPurchaseReconciliation(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).fssPurchaseReconciliation(inParameter);
    }

    @Override
    public OutParameter fssRedeemReconciliation(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).fssRedeemReconciliation(inParameter);
    }

    @Override
    public OutParameter queryFss(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryFss(inParameter);
    }

    @Override
    public OutParameter queryFssAccts(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryFssAccts(inParameter);
    }

    @Override
    public OutParameter queryCardInfo(InParameter inParameter, AccountType accountType) {
        return getQueryManager(accountType).queryCardInfo(inParameter);
    }

}
