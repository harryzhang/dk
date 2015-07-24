package com.hehenian.biz.component.trade.impl;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.FundrecordDo;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.dal.trade.IFundrecordDao;

@Component("fundrecordComponent")
public class FundrecordComponentImpl implements IFundrecordComponent {
    @Autowired
    private IFundrecordDao fundrecordDao;

    @Override
    public Long addFundrecord(FundrecordDo fundrecordDo) {
        int count = fundrecordDao.addFundrecord(fundrecordDo);
        if (count != 1) {
            throw new RuntimeException("新增资金记录失败!");
        }
        return fundrecordDo.getId();
    }

    /*
     * (no-Javadoc) <p>Title: addFundByRepay</p> <p>Description: </p>
     * 
     * @param repayFundrecord
     * 
     * @see
     * com.hehenian.biz.component.trade.IFundrecordComponent#addFundByRepay(
     * com.hehenian.biz.common.trade.dataobject.FundrecordDo)
     */
    @Override
    public void addFundByRepay(FundrecordDo repayFundrecord) {
        fundrecordDao.addFundByRepay(repayFundrecord);
    }

    @Override
    public int deleteById(long id) {
        int count = fundrecordDao.delectById(id);
        if (count != 1) {
            throw new RuntimeException("删除资金记录失败!");
        }
        return count;
    }

    @Override
    public Double getDailyIncentiveAmount(Long userId, Date date) {
        return fundrecordDao.getDailyIncentiveAmount(userId, date, DateUtils.addDays(date, 1));
    }

    @Override
    public Long getAutoIncrementId() {
        Long fundrecordId = addFundrecord(new FundrecordDo());
        deleteById(fundrecordId);
        return fundrecordId;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hehenian.biz.component.trade.IFundrecordComponent#getDailyIncentiveAmount
	 * (java.lang.Long)
	 */
	@Override
	public Double getDailyIncentiveAmount(Long userId) {
		return fundrecordDao.getDailyIncentiveAmount(userId, null, null);
	}

}
