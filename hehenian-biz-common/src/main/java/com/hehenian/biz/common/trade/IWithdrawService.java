package com.hehenian.biz.common.trade;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.dataobject.MerCashDo;
import com.hehenian.biz.common.trade.dataobject.WithdrawDo;

public interface IWithdrawService {

    /**
     * 新增借款申请信息
     * 
     * @param withdrawDo
     * @return
     */
    IResult<?> addWithdraw(WithdrawDo withdrawDo);

    /**
     * 根据提现ID删除提现记录
     * 
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * 根据提现ID查询提现记录
     * 
     * @param ordId
     * @return
     */
    WithdrawDo getById(Long id);

    /**
     * 提现申请回调方法
     * 
     * @param withdrawDo
     * @return
     */
    IResult<?> addWithdrawCallback(WithdrawDo withdrawDo);

    /**
     * 提现转账
     * 
     * @param withdrawDo
     * @return
     */
    IResult<?> updateWithdrawTransfer(WithdrawDo withdrawDo);

    /**
     * 提现审核复核
     * 
     * @param withdrawDo
     * @return
     */
    IResult<?> updateWithdrawAudit(WithdrawDo withdrawDo);

    /**
     * 商户提现（平台提现）
     * 
     * @param merCostDo
     * @return
     */
    IResult<?> addMerWithdraw(MerCashDo merCashDo);

    /**
     * 修改提现申请记录的状态
     * 
     * @param withdrawId
     * @param status
     * @param origStatus
     * @author: liuzgmf
     * @date: 2014-11-5下午2:21:48
     */
    boolean updateStatus(Long withdrawId, Integer status, Integer origStatus);

}
