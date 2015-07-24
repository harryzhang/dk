/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.activity.dataobject.ActivityAuthDo;

public interface IActivityAuthComponent {

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public ActivityAuthDo getById(int id);

    /**
     * 根据条件查询列表
     */
    public List<ActivityAuthDo> selectActivityAuth(Map<String, Object> parameterMap);

    /**
     * 更新
     */
    public int updateActivityAuthById(ActivityAuthDo newActivityAuthDo);

    /**
     * 新增
     */
    public int addActivityAuth(ActivityAuthDo newActivityAuthDo);

    /**
     * 删除
     */
    public int deleteById(int id);

    public ActivityAuthDo getByOrdId(ActivityAuthDo activityAuthDo);

    /**
     * 根据转让用户ID，转出用户ID查询授权信息
     * 
     * @param fromUserId
     * @param toUserId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月31日下午3:04:19
     */
    ActivityAuthDo getByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    /**
     * 修改转账授权记录
     * 
     * @param usrCustId
     * @param inUsrCustId
     * @param authAmt
     * @author: liuzgmf
     * @date: 2014年10月31日下午3:14:05
     */
    boolean updateTransferAuth(long usrCustId, long inUsrCustId, double authAmt);

    /**
     * 查询授权金额
     * 
     * @param fromUserId
     * @param toUserId
     * @return
     * @author: liuzgmf
     * @date: 2015年4月29日上午11:29:41
     */
    Double getAuthAmtByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

}
