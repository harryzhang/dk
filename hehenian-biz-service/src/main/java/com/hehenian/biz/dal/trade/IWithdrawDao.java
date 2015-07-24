package com.hehenian.biz.dal.trade;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.WithdrawDo;

public interface IWithdrawDao {

    /**
     * 新增提现申请
     * 
     * @param withdrawDo
     * @return
     */
    Integer addWithdraw(WithdrawDo withdrawDo);

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
    Integer updateWithdraw(WithdrawDo withdrawDo);

    /**
     * 查询用户在指定的时间内的提现次数
     * 
     * @param userId
     * @param beginDate
     * @param endDate
     * @return
     * @author: liuzgmf
     * @date: 2014年10月17日下午2:30:14
     */
    int countWithdrawQty(@Param("userId") Long userId, @Param("beginDate") Date beginDate,
            @Param("endDate") Date endDate);

    /**
     * 用户申请提现后修改提现申请记录
     * 
     * @param withdrawDo
     * @return
     * @author: liuzgmf
     * @date: 2014-11-4下午3:38:36
     */
    int updateWithdrawApply(WithdrawDo withdrawDo);

    /**
     * 修改提现申请记录的状态
     * 
     * @param withdrawId
     * @param status
     * @param origStatus
     * @return
     * @author: liuzgmf
     * @date: 2014-11-5下午2:35:55
     */
    int updateStatus(@Param("withdrawId") Long withdrawId, @Param("status") Integer status,
            @Param("origStatus") Integer origStatus);

    /**
     * 根据提现ID查询提现记录
     * 
     * @param withdrawIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:26:54
     */
    List<WithdrawDo> queryByIds(@Param("withdrawIdList") List<Long> withdrawIdList);

}
