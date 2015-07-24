package com.hehenian.biz.dal.trade;

import com.hehenian.biz.common.trade.dataobject.RechargeDo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * User: liuwtmf Date: 2014/9/23 Time: 14:32
 */
public interface IRechargeDao {
    /**
     * 插入充值记录
     *
     * @param rechargeDo
     * @return
     */
    int addRecharge(RechargeDo rechargeDo);

    /**
     * 修改充值记录
     *
     * @param rechargeDo
     * @return
     */
    int updateRecharge(RechargeDo rechargeDo);

    /**
     * 查询充值记录
     * 
     * @param parameterMap
     * @return
     */
    List<RechargeDo> queryRecharges(Map<String, Object> parameterMap);

    RechargeDo getById(long id);

    void testU();

    /**
     * 根据ID查询充值记录
     * 
     * @param idList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月26日上午11:46:58
     */
    List<RechargeDo> queryByIds(@Param("idList") List<Long> idList);
}
