/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.activity.impl;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.activity.IActivityLockComponent;
import com.hehenian.biz.dal.activity.IActivityLockDao;
import com.hehenian.biz.common.activity.dataobject.ActivityLockDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("activityLockComponent")
public class ActivityLockComponentImpl implements IActivityLockComponent {

    @Autowired
    private IActivityLockDao activityLockDao;

    /**
     * 根据ID 查询
     * @parameter id
     */
    public ActivityLockDo getById(int id) {
        return activityLockDao.getById(id);
    }

    /**
     *根据条件查询列表
     */
    public List<ActivityLockDo> selectActivityLock(Map<String, Object> parameterMap) {
        return activityLockDao.selectActivityLock(parameterMap);
    }

    /**
     * 更新
     */
    public int updateActivityLockById(ActivityLockDo newActivityLockDo) {
        int result = activityLockDao.updateActivityLockById(newActivityLockDo);
        if (result < 1) {
            throw new BusinessException("更新失败");
        }
        return result;
    }

    /**
     * 新增
     */
    public int addActivityLock(ActivityLockDo newActivityLockDo) {
        return activityLockDao.addActivityLock(newActivityLockDo);
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return activityLockDao.deleteById(id);
	}

}
