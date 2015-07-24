package com.hehenian.biz.component.trade;

import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.trade.dataobject.WithdrawDo;

public interface IWithdrawComponent {

    /**
     * 新增借款申请信息
     * 
     * @param withdrawDo
     * @return
     */
    Long addWithdraw(WithdrawDo withdrawDo);

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
     * 修改提现申请记录
     * 
     * @param withdrawDo
     * @return
     */
    Boolean updateWithdraw(WithdrawDo withdrawDo);

    /**
     * 提现转账
     * 
     * @param withdrawDo
     * @return
     */
    Boolean updateWithdrawTransfer(WithdrawDo withdrawDo);

    /**
     * 提现复核
     * 
     * @param withdrawDo
     * @return
     */
    Boolean updateWithdrawAudit(WithdrawDo withdrawDo);

    /**
     * 提现复核不通过
     * 
     * @param withdrawDo
     * @author: liuzgmf
     * @date: 2014-11-5下午3:07:51
     */
    boolean updateWithdrawFault(WithdrawDo withdrawDo);

    /**
     * 查询用户在指定的时间内的提现次数
     * 
     * @param userId
     * @param beginDate
     * @param endDate
     * @return
     * @author: liuzgmf
     * @date: 2014年10月17日下午2:26:46
     */
    int countWithdrawQty(Long userId, Date beginDate, Date endDate);

    /**
     * 提现申请汇付回调
     * 
     * @param withdrawDo
     * @return
     * @author: liuzgmf
     * @date: 2014年10月20日上午9:15:43
     */
    boolean updateWithdrawCallback(WithdrawDo withdrawDo);

    /**
     * 修改提现申请记录的状态
     * 
     * @param ordId
     * @param status
     * @param origStatus
     * @return
     * @author: liuzgmf
     * @date: 2014-11-5下午2:23:40
     */
    boolean updateStatus(Long withdrawId, Integer status, Integer origStatus);

    /**
     * 根据提现ID查询提现记录
     * 
     * @param withdrawIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:26:54
     */
    List<WithdrawDo> queryByIds(List<Long> withdrawIdList);

}
