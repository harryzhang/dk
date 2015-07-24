/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.dal.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.activity.dataobject.ActivityAuthDo;

public interface IActivityAuthDao {

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

    /**
     * 根据转让用户ID，转出用户ID查询授权信息
     * 
     * @param fromUserId
     * @param toUserId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月31日下午3:05:51
     */
    ActivityAuthDo getByFromUserIdAndToUserId(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    /**
     * 查询订单的成功授权记录
     * 
     * @param ordId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月31日下午5:03:26
     */
    ActivityAuthDo getByOrdId1(@Param("ordId") long ordId);

    /**
     * @param activityAuthDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月1日上午9:48:58
     */
    ActivityAuthDo getByOrdId(ActivityAuthDo activityAuthDo);

    /**
     * 查询授权金额
     * 
     * @param fromUserId
     * @param toUserId
     * @return
     * @author: liuzgmf
     * @date: 2015年4月29日上午11:29:41
     */
    Double getAuthAmtByFromUserIdAndToUserId(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);
}
