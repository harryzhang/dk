package com.hehenian.biz.dal.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.BorrowDo;

public interface IBorrowDao {

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
     * 更新标的的状态， 和还款期数
     * 
     * @param borrowId
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午11:24:00
     */
    public int updateBorrowStatusAndHasDeadlineById(long borrowId);

    /**
     * 更新标的还款状态为完成， 但是有条件， deadline<=hasDeadline 已还期数大于等于借款期数
     * 
     * @param borrowId
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午11:24:23
     */
    public int updateBorrowStatus(long borrowId);

    /**
     * 提前还款完成后更新标的状态
     * 
     * @param borrowId
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月28日下午5:16:18
     */
    public int updateBorrowStatusByPreRepay(long borrowId);

    Map<String, Object> getBorrowDetail(Long borrowId);

    /**
     * 修改标的状态
     * 
     * @param borrowDo
     * @return
     */
    int updateStatus(BorrowDo borrowDo);

    int updateBorrowInvest(BorrowDo borrowDo);

    /**
     * 放款时更新标的信息
     * 
     * @param borrowDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月13日上午9:27:11
     */
    int updateBorrowFullScale(Long borrowId);

    /**
     * 根据条件查询借款标的信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年11月21日上午9:58:00
     */
    List<BorrowDo> queryBorrows(Map<String, Object> searchItems);

    /**
     * 根据条件获取标的记录数
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年11月21日下午2:46:51
     */
    long getBorrowQty(Map<String, Object> searchItems);

    /**
     * 查询用户最后一笔借款记录
     * 
     * @param idNo
     * @return
     * @author: liuzgmf
     * @date: 2014年12月16日下午8:03:40
     */
    public BorrowDo getByIdNo(String idNo);

    /**
     * 根据ID查询借款信息
     * 
     * @param borrowId
     * @return
     * @author: liuzgmf
     * @date: 2014年12月22日上午10:18:15
     */
    Map<String, Object> queryBorrowDetails(Long borrowId);
    
    /**
     * 合和贷与精英贷放款标的查询
     * 
     * @param searchItems
     * @return
     * @author: zhough
     */
    List<Map<String, Object>> queryLoanBorrows(Map<String, Object> searchItems);
    
    /**
     * 根据条件获取标的投资人
     * 
     * @param searchItems
     * @return
     * @author: zhough
     */
    List<Map<String, Object>> queryloanBorrowUser(Map<String, Object> searchItems);
	/**
     * 根据还款日期查询还款详情
     * 
     * @param searchItems
     * @return
     * @author: zhough
     */
	List<Map<String, Object>> queryRepayment(Map<String, Object> searchItems);

}
