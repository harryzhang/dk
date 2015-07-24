/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.activity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.activity.IActivityOrderComponent;
import com.hehenian.biz.dal.activity.IActivityOrderDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Component("activityOrderComponent")
public class ActivityOrderComponentImpl implements IActivityOrderComponent {

	@Autowired
	private IActivityOrderDao activityOrderDao;

	/**
	 * 根据ID 查询
	 * 
	 * @parameter id
	 */
	public ActivityOrderDo getById(long id) {
		return activityOrderDao.getById(id);
	}

	/**
	 * 根据条件查询列表
	 */
	public List<ActivityOrderDo> selectActivityOrder(
			Map<String, Object> parameterMap) {
		return activityOrderDao.selectActivityOrder(parameterMap);
	}

	/**
	 * 更新
	 */
	public int updateActivityOrderById(ActivityOrderDo newActivityOrderDo) {
		int result = activityOrderDao
				.updateActivityOrderById(newActivityOrderDo);
		if (result < 1) {
			throw new BusinessException("更新失败");
		}
		return result;
	}

	/**
	 * 新增
	 */
	public int addActivityOrder(ActivityOrderDo newActivityOrderDo) {
		return activityOrderDao.addActivityOrder(newActivityOrderDo);
	}

	/**
	 * 删除
	 */
	public int deleteById(int id) {
		return activityOrderDao.deleteById(id);
	}

	@Override
	public ActivityOrderDo queryOrderDetail(String userId, String orderSN,
			String orderType) {
		return activityOrderDao.queryOrderDetail(userId, orderSN, orderType);
	}

	@Override
	public ActivityOrderDo getByOrdNo(ActivityOrderDo activityOrderDo) {
		return activityOrderDao.getByOrdNo(activityOrderDo);
	}


	@Override
	public List<ActivityOrderDo> getByUserId(long userId) {
        return getByUserId(userId, null);
	}
    public long hasOrder( int ordType, long userId){
        return activityOrderDao.hasOrder( ordType, userId);
    }

    public int addRechargeMoney( long ordid , double addMoney,long userId){
        return activityOrderDao.addRechargeMoney(ordid, addMoney,userId);
    }

    /*
     * (no-Javadoc) <p>Title: getByUserId</p> <p>Description: </p>
     * 
     * @param id
     * 
     * @param orderSubType
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.activity.IActivityOrderComponent#getByUserId
     * (java.lang.Long, java.lang.String)
     */
    @Override
    public List<ActivityOrderDo> getByUserId(Long id, String orderSubType) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("userId", id);
        parameterMap.put("orderSubType", orderSubType);
        return activityOrderDao.selectActivityOrder(parameterMap);
    }
}
