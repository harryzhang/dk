/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.activity.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.dataobject.ActivityAuthDo;
import com.hehenian.biz.common.activity.dataobject.ActivityLockDo;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.activity.dataobject.ActivityTradeDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityTradeComponent;
import com.hehenian.biz.dal.activity.IActivityAuthDao;
import com.hehenian.biz.dal.activity.IActivityLockDao;
import com.hehenian.biz.dal.activity.IActivityOrderDao;
import com.hehenian.biz.dal.activity.IActivityTradeDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Component("activityTradeComponent")
public class ActivityTradeComponentImpl implements IActivityTradeComponent {
    @Autowired
    private IActivityTradeDao activityTradeDao;
    @Autowired
    private IActivityAuthDao  activityAuthDao;
    @Autowired
    private IActivityOrderDao activityOrderDao;
    @Autowired
    private IUserComponent    userComponent;
    @Autowired
    private IActivityLockDao  activityLockDao;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public ActivityTradeDo getById(int id) {
        return activityTradeDao.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<ActivityTradeDo> selectActivityTrade(Map<String, Object> parameterMap) {
        return activityTradeDao.selectActivityTrade(parameterMap);
    }

    /**
     * 更新
     */
    public int updateActivityTradeById(ActivityTradeDo newActivityTradeDo) {
        int result = activityTradeDao.updateActivityTradeById(newActivityTradeDo);
        if (result < 1) {
            throw new BusinessException("更新失败");
        }
        return result;
    }

    /**
     * 新增
     */
    public int addActivityTrade(ActivityTradeDo newActivityTradeDo) {
        return activityTradeDao.addActivityTrade(newActivityTradeDo);
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return activityTradeDao.deleteById(id);
    }

    @Override
    public boolean addActivityTrades(long ordId) {
        ActivityOrderDo orderDo = activityOrderDao.getById(ordId);
        if (orderDo == null) {
            throw new RuntimeException("活动订单[" + ordId + "]记录不存在!");
        }
        ActivityAuthDo authDo = activityAuthDao.getByOrdId1(ordId);
        if (authDo == null || authDo.getAuthStatus().intValue() != 1) {
            throw new RuntimeException("授权[" + ordId + "]（订单ID）记录不存在或者授权失败");
        }

        double totalDedeductAmount = 0.00;// 总扣款金额
        int deductPeriods = calDeductPeriods(orderDo.getBeginDate(), orderDo.getEndDate());
        Date beginDate = orderDo.getBeginDate();
        Date tradeDate = DateUtils.addDays(new Date(), 1);// 交易日期
        while (beginDate.before(orderDo.getEndDate())) {
            ActivityTradeDo tradeDo = new ActivityTradeDo();
            tradeDo.setOrdId(ordId);
            tradeDo.setFromUserId(authDo.getFromUserId());
            tradeDo.setToUserId(authDo.getToUserId());
            tradeDo.setAmount(CalculateUtils.div(orderDo.getDeductAmount(), deductPeriods, 2));
            totalDedeductAmount = CalculateUtils.add(totalDedeductAmount, tradeDo.getAmount());
            tradeDo.setRealAmount(0d);
            tradeDate = DateUtils.addMonths(tradeDate, 1);
            tradeDo.setTradeTime(tradeDate);
            tradeDo.setTradeStatus(0);// 0-未转账
            addActivityTrade(tradeDo);

            beginDate = DateUtils.addMonths(beginDate, 1);
        }

        if (CalculateUtils.gt(totalDedeductAmount, authDo.getAuthAmount())) {
            throw new RuntimeException("活动订单[" + ordId + "]的转账金额大于授权金额!");
        }

        return true;
    }

    /**
     * 计算扣款期数
     * 
     * @param beginDate
     * @param endDate
     * @return
     * @author: liuzgmf
     * @date: 2014年10月31日下午4:50:56
     */
    private int calDeductPeriods(Date beginDate, Date endDate) {
        int deductPeriods = 0;
        while (beginDate.before(endDate)) {
            deductPeriods++;
            beginDate = DateUtils.addMonths(beginDate, 1);
        }
        return deductPeriods;
    }

    @Override
    public List<ActivityTradeDo> queryNoTransferTrades(Date tradeTime) {
        return activityTradeDao.queryNoTransferTrades(tradeTime);
    }

    public static void main(String[] args) throws Exception {
        ActivityTradeComponentImpl tradeComponent = new ActivityTradeComponentImpl();
        Date beginDate = DateUtils.parseDate("20141101", new String[] { "yyyyMMdd" });
        Date endDate = DateUtils.parseDate("20151102", new String[] { "yyyyMMdd" });
        System.out.println(tradeComponent.calDeductPeriods(beginDate, endDate));
        System.out.println(DateFormatUtils.format(beginDate, "yyyyMMdd"));
    }

    @Override
    public List<ActivityTradeDo> selectLastUnDeductActivityTrade(Map<String, Object> parameterMap) {

        return activityTradeDao.selectLastUnDeductActivityTrade(parameterMap);
    }

    @Override
    public boolean updateActivityTradeTransfer(ActivityTradeDo activityTradeDo) {
        // 修改转账记录为转账成功
        activityTradeDo.setTradeStatus(1);// 1-已转账
        activityTradeDo.setRealTradeTime(new Date());
        activityTradeDo.setRemark("转账成功");
        activityTradeDao.updateActivityTradeById(activityTradeDo);

        // 修改用户账户的锁定金额
        AccountUserDo userDo = new AccountUserDo();
        userDo.setId(activityTradeDo.getFromUserId());
        userDo.setUsableSum(-activityTradeDo.getRealAmount());
        userDo.setLockAmount(-activityTradeDo.getRealAmount());
        userComponent.updateAmount(userDo);

        // 新增解锁记录
        ActivityLockDo activityLockDo = new ActivityLockDo();
        activityLockDo.setOrdId(activityTradeDo.getOrdId());
        activityLockDo.setUserId(activityTradeDo.getFromUserId());
        activityLockDo.setAmount(activityTradeDo.getRealAmount());
        activityLockDo.setLockType(1);// 1-解锁
        activityLockDo.setStatus(1);// 1-成功
        activityLockDao.addActivityLock(activityLockDo);

        return true;
    }

    @Override
    public List<ActivityTradeDo> queryByIds(List<Long> idList) {
        return activityTradeDao.queryByIds(idList);
    }

}
