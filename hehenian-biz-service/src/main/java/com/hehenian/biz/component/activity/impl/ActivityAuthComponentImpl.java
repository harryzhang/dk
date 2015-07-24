/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.dataobject.ActivityAuthDo;
import com.hehenian.biz.common.activity.dataobject.ActivityLockDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityAuthComponent;
import com.hehenian.biz.dal.account.IUserDao;
import com.hehenian.biz.dal.activity.IActivityAuthDao;
import com.hehenian.biz.dal.activity.IActivityLockDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Component("activityAuthComponent")
public class ActivityAuthComponentImpl implements IActivityAuthComponent {
    @Autowired
    private IActivityAuthDao activityAuthDao;
    @Autowired
    private IUserDao         userDao;
    @Autowired
    private IUserComponent   userComponent;
    @Autowired
    private IActivityLockDao activityLockDao;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public ActivityAuthDo getById(int id) {
        return activityAuthDao.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<ActivityAuthDo> selectActivityAuth(Map<String, Object> parameterMap) {
        return activityAuthDao.selectActivityAuth(parameterMap);
    }

    /**
     * 更新
     */
    public int updateActivityAuthById(ActivityAuthDo newActivityAuthDo) {
        int result = activityAuthDao.updateActivityAuthById(newActivityAuthDo);
        if (result < 1) {
            throw new BusinessException("更新失败");
        }
        return result;
    }

    /**
     * 新增
     */
    public int addActivityAuth(ActivityAuthDo newActivityAuthDo) {
        return activityAuthDao.addActivityAuth(newActivityAuthDo);
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return activityAuthDao.deleteById(id);
    }

    public ActivityAuthDo getByOrdId(ActivityAuthDo activityAuthDo) {
        return activityAuthDao.getByOrdId(activityAuthDo);
    }

    @Override
    public ActivityAuthDo getByFromUserIdAndToUserId(Long fromUserId, Long toUserId) {
        return activityAuthDao.getByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    @Override
    public boolean updateTransferAuth(long usrCustId, long inUsrCustId, double authAmt) {
        AccountUserDo fromUserDo = userDao.getUserByCustId(usrCustId + "");
        AccountUserDo toUserDo = userDao.getUserByCustId(inUsrCustId + "");
        ActivityAuthDo localActivityAuthDo = activityAuthDao.getByFromUserIdAndToUserId(fromUserDo.getId(),
                toUserDo.getId());
        ActivityAuthDo activityAuthDo = new ActivityAuthDo();
        activityAuthDo.setAuthId(localActivityAuthDo.getAuthId());
        Double authedAmt = getAuthAmtByFromUserIdAndToUserId(fromUserDo.getId(), toUserDo.getId());// 已授权金额
        if (CalculateUtils.gt(CalculateUtils.sub(authAmt, authedAmt), 0d)) {
            authAmt = CalculateUtils.sub(authAmt, authedAmt);
        }
        activityAuthDo.setAuthAmount(authAmt);
        activityAuthDo.setAuthStatus(1);// 授权成功
        updateActivityAuthById(activityAuthDo);

        // 修改用户账户的锁定金额
        AccountUserDo userDo = new AccountUserDo();
        userDo.setId(fromUserDo.getId());
        userDo.setLockAmount(authAmt);
        userComponent.updateAmount(userDo);

        ActivityLockDo activityLockDo = new ActivityLockDo();
        activityLockDo.setOrdId(localActivityAuthDo.getOrdId());
        activityLockDo.setUserId(fromUserDo.getId());
        activityLockDo.setAmount(authAmt);
        activityLockDo.setLockType(0);// 0-锁定
        activityLockDo.setStatus(1);// 1-成功
        activityLockDao.addActivityLock(activityLockDo);

        return true;
    }

    @Override
    public Double getAuthAmtByFromUserIdAndToUserId(Long fromUserId, Long toUserId) {
        Double authAmt = activityAuthDao.getAuthAmtByFromUserIdAndToUserId(fromUserId, toUserId);
        return (authAmt == null ? 0d : authAmt);
    }

}
