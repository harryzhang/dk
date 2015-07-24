package com.hehenian.biz.dal.trade;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.InvestDo;

public interface IInvestDao {

    /**
     * 获取用户的代收金额
     * 
     * @param userId
     * @return
     */
    Double getDueinSum(Long userId);

    /**
     * 查询标的的投资信息
     * 
     * @param borrowId
     * @return
     */
    List<InvestDo> queryByBorrowId(Long borrowId);

    /**
     * 获取所有投资人的信息， 这个查询关联了用户表
     * 
     * @param borrowId
     *            标的ID
     * @return 返回投资人列表， 每条记录是map<String,Object>结构
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午5:10:02
     */
    // List<Map<String,Object>> getAllInvestByBorrowId(long borrowId);

    /**
     * 更新投资记录还款信息
     * 
     * @param newInvestDo
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午7:15:32
     */
    int updateHasAmount(InvestDo newInvestDo);

    /**
     * 还款完成修改投资状态
     * 
     * @param investId
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午7:15:55
     */
    int updateRepayStatusById(long investId);

    /**
     * 修改投资记录信息
     * 
     * @param udpateInvestDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月25日下午2:35:06
     */
    Integer updateInvest(InvestDo udpateInvestDo);

    /**
     * 添加投资记录
     * 
     * @param newInvestDo
     * @return
     */
    int addInvest(InvestDo newInvestDo);

    /**
     * 根据投资ID查询投资信息
     * 
     * @param investId
     * @return
     * @author: liuzgmf
     * @date: 2014年9月29日下午3:14:43
     */
    InvestDo getById(Long investId);

    /**
     * 提前结清时更新状态成提前结清状态
     * 
     * @param borrowId
     *            标的ID
     * @author: zhangyunhmf
     * @date: 2014年10月9日下午2:40:32
     */
    void updateRepayStatusByBorrowId(long borrowId);

    /**
     * 根据投资ID删除投资记录
     * 
     * @param id
     * @author: liuzgmf
     * @date: 2014年10月16日上午11:06:44
     */
    int deleteById(Long id);

    /**
     * 成功投资记录
     * 
     * @param parameterMap
     *            查询条件， 包含KEY=page 翻页的对象
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月20日下午12:36:03
     */
    List<Map<String, Object>> selectInvestSuccessRecordPage(Map<String, Object> parameterMap);

    /**
     * 成功债券转让记录
     * 
     * @param parameterMap
     *            查询条件， 包含KEY=page 翻页的对象
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月20日下午12:36:12
     */
    List<Map<String, Object>> selectDebtSuccessRecordPage(Map<String, Object> parameterMap);

    /**
     * 提前结清更新投资上的还款状态
     * 
     * @param id
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午11:49:13
     */
    void updateRepayStatusForPreSettle(Long id);

    /**
     * 查询用户投资的次数
     * 
     * @param userId
     * @return
     */
    long hasInvest(long userId);

    /**
     * 查询用户的投资记录
     * 
     * @param userIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年12月1日下午4:50:34
     */
    List<Map<String, Object>> queryUserInvests(@Param("userIdList") List<Long> userIdList);

    /**
     * 根据ID查询投资记录信息
     * 
     * @param idList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日下午2:47:11
     */
    List<InvestDo> queryByIds(@Param("idList") List<Long> idList);

    /**
     * 查询标的的投资信息
     * 
     * @param borrowId
     * @return
     * @author: liuzgmf
     * @date: 2014年12月22日上午9:37:01
     */
    List<Map<String, Object>> queryInvestDetails(Long borrowId);
}
