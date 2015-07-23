package com.hehenian.biz.common.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;

public interface IBorrowService {

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public BorrowDo getById(Long id);

    /**
     * 根据条件查询列表
     */
    public List<BorrowDo> selectBorrow(Map<String, Object> parameterMap);

    /**
     * 更新
     */
    public int updateBorrowById(BorrowDo newBorrowDo);

    /**
     * 新增
     */
    public int addBorrow(BorrowDo newBorrowDo);

    /**
     * 删除
     */
    public int deleteById(Long id);

    /**
     * 满标放款处理
     * 
     * @param borrowId
     * @param status
     * @param auditOpinion
     * @param adminId
     * @return
     */
    IResult<?> updateBorrowFullScale(Long borrowId, Integer status, String auditOpinion, Long adminId);

    /**
     * 根据条件查询标的信息
     * 
     * @param currentPage
     * @param pageSize
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年11月21日上午9:35:42
     */
    NPageDo<BorrowDo> queryBorrows(Long currentPage, Long pageSize, Map<String, Object> searchItems);

    /**
     * 查询借款协议数据
     * 
     * @param userId 用户ID
     * @param borrowId
     * @return
     * @author: liuzgmf
     * @date: 2014年12月19日上午11:33:26
     */
    IResult<?> queryAgreementParams(Long userId, Long borrowId);
    
    /**
     * 合和贷与精英贷放款标的查询
     * 
     * @param currentPage
     * @param pageSize
     * @param searchItems
     * @return
     * @author: zhough
     */
    List<Map<String,Object>> queryLoanBorrowList(Map<String, Object> searchItems);
    
    /**
     * 合和贷与精英贷放款标的投资人查询
     * 
     * @param currentPage
     * @param pageSize
     * @param searchItems
     * @return
     * @author: zhough
     */
    List<Map<String,Object>> queryloanBorrowUserList(Map<String, Object> searchItems);

	/**
     * 根据还款日期查询还款详情
     * 
     * @param currentPage
     * @param pageSize
     * @param searchItems
     * @return
     * @author: zhough
     */
    List<Map<String,Object>> queryRepaymentList(Map<String, Object> searchItems);

}
