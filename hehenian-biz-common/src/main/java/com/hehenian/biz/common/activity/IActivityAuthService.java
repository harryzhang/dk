/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.activity.dataobject.ActivityAuthDo;
import com.hehenian.biz.common.base.result.IResult;

public interface IActivityAuthService {

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
     * 删除
     */
    public int deleteById(int id);

    public ActivityAuthDo getByOrdId(ActivityAuthDo activityAuthDo);

    /**
     * 转账授权
     * 
     * @param activityAuthDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月1日上午9:41:22
     */
    IResult<?> transferAuth(long ordId, long fromUserId);

    /**
     * 转账授权回调
     * 
     * @param usrCustId
     * @param inUsrCustId
     * @param authAmt
     * @return
     * @author: liuzgmf
     * @date: 2014年11月1日上午10:03:07
     */
    IResult<?> transferAuthCb(long usrCustId, long inUsrCustId, double authAmt);
}
