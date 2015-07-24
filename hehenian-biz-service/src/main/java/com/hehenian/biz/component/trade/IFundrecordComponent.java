package com.hehenian.biz.component.trade;

import java.util.Date;

import com.hehenian.biz.common.trade.dataobject.FundrecordDo;

public interface IFundrecordComponent {

    /**
     * 新增交易记录信息
     * 
     * @param fundrecordDo
     * @return
     */
    Long addFundrecord(FundrecordDo fundrecordDo);

    /**
     * 还款的时候，更改用户的可用资金，记录用户资金更改过程
     * 
     * @param repayFundrecord
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午4:20:50
     */
    void addFundByRepay(FundrecordDo repayFundrecord);

    /**
     * 
     * @param id
     * @return
     * @author: liuzgmf
     * @date: 2014年10月11日下午3:52:41
     */
    int deleteById(long id);

    /**
     * 获取每日奖励收益金额
     * 
     * @param userId
     * @param yesterday
     * @return
     * @author: liuzgmf
     * @date: 2014年10月11日下午3:51:45
     */
    Double getDailyIncentiveAmount(Long userId, Date date);

	/**
	 * 获取奖励收益金额
	 * 
	 * @param userId
	 * @return
	 * @author: zhangyunhua
	 * @date: 2014年11月10日下午3:51:45
	 */
	Double getDailyIncentiveAmount(Long userId);

    /**
     * 获取资金流水自增ID值
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月15日下午5:04:04
     */
    Long getAutoIncrementId();

}
