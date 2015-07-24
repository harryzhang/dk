package com.hehenian.biz.dal.trade;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.FundrecordDo;

public interface IFundrecordDao {

    /**
     * 新增交易记录信息
     * 
     * @param fundrecordDo
     * @return
     */
    Integer addFundrecord(FundrecordDo fundrecordDo);

    /**
     * 还款的时候，更改用户的可用资金，记录用户资金更改过程
     * 
     * @param repayFundrecord
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午4:20:50
     */
    int addFundByRepay(FundrecordDo repayFundrecord);

    int delectById(long id);

    /**
     * 获取每日奖励收益金额
     * 
     * @param userId
     * @param beginDate
     * @param endDate
     * @return
     * @author: liuzgmf
     * @date: 2014年10月11日下午3:53:59
     */
    Double getDailyIncentiveAmount(@Param(value = "userId") Long userId, @Param(value = "beginDate") Date beginDate,
            @Param(value = "endDate") Date endDate);

}
