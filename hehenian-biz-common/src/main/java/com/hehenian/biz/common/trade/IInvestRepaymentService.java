package com.hehenian.biz.common.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;

public interface IInvestRepaymentService {

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public InvestRepaymentDo getById(Long id);

    /**
     * 根据条件查询列表
     */
    public List<InvestRepaymentDo> selectInvestRepayment(Map<String, Object> parameterMap);

    /**
     * 更新
     */
    public int updateInvestRepaymentById(InvestRepaymentDo newInvestRepaymentDo);

    /**
     * 新增
     */
    public int addInvestRepayment(InvestRepaymentDo newInvestRepaymentDo);

    /**
     * 删除
     */
    public int deleteById(int id);

    /**
     * 获取每日收益
     * 
     * @param userId
     * @return
     */
    Double getDailyIncome(Long userId);

    /**
     * 获取用户的资产估值
     * 
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月16日上午11:35:01
     */
    Double getAssetValue(Long userId);

    /**
     * 查询用户的代收本金金额
     * 
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月16日上午11:35:01
     */
    Double getRecivedPrincipal(Long userId);
}
