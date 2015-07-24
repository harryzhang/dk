package com.hehenian.biz.component.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;

public interface IInvestRepaymentComponent {

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
     * 查询最近一个月投资收款记录
     * 
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年9月23日下午3:12:20
     */
    List<InvestRepaymentDo> queryNoRepayRecordsRecently(Long userId);

    /**
     * 查询用户总的已收利息
     * 
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年9月23日下午3:13:27
     */
    Double getTotalRecivedInterest(Long userId);

    /**
     * 根据投资id， 还款ID 汇总投资回款 应收本金，应收罚金，应收利息，应收管理费
     * 
     * @param investId
     *            投资id
     * @param repaymentId
     *            还款ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午6:38:28
     */
    List<Map<String, Object>> getTotalRecived(@Param("investId") long investId, @Param("repaymentId") long repaymentId);

    /**
     * 还款后，修改已收金额和状态
     * 
     * @param newInvestRepaymentDo
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午6:59:17
     */
    int updateRecievedAmount(InvestRepaymentDo newInvestRepaymentDo);

    /**
     * 提前还款批量更新 回款表的状态， 如果之前状态=1 更新为10， 提前还款
     * newInvestRepaymentDo的repayId=当期的还款ID, 大于当期还款id的hasInterest = 0
     * 
     * @param newInvestRepaymentDo
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月29日下午6:33:55
     */
    int updateRepayStatusByPreRepay(InvestRepaymentDo newInvestRepaymentDo);

    /**
     * 根据用户ID，还款日期查询收款（汇款）记录
     * 
     * @param userId
     * @param beginDate
     * @param endDate
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日下午6:07:55
     */
    public List<InvestRepaymentDo> queryByUserIdAndRepayDate(Long userId, Date beginDate, Date endDate);

    /**
     * 代偿后根据原来的回款记录生成新的回款记录，新生成的记录的parentId是原记录的ID
     * 
     * @param oldId
     *            原来的回款记录ID
     * @param userId
     *            代偿后回款记录的OWNER 是代偿机构
     * @return 新生成的记录条数
     */
    public int addWebPayRecord(long oldId, long userId);

    /**
     * 将代偿记录状态更新成 未还款状态=1 ,根据状态=3已及repaymentId
     * 
     * @param repaymentId
     */
    // public void updateWebPayRecordStatus(Long repaymentId);

    /**
     * 根据回款ID修改还款状态，债权转让状态
     * 
     * @param repayStatus
     * @param isDebt
     * @param id
     * @author: liuzgmf
     * @date: 2014年10月8日下午5:05:47
     */
    void updateRepayStatus(int repayStatus, int isDebt, Long id);

    /**
     * 查询逾期未回款的投资，定时任务将调用
     * 
     * @param currentDate
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月15日上午10:15:37
     */
    public List<InvestRepaymentDo> selectOverDueInvestRepayList(Date currentDate);

    /**
     * 查询的用户待收本金金额
     * 
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月16日下午1:29:24
     */
    public Double getRecivedPrincipal(Long userId);

    /**
     * 获取成功投资的金额，回款等金额
     * 
     * @param investIdList
     *            投资id list
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月20日下午2:42:51
     */
    public List<Map<String, Object>> getInvestSuccessAmount(@Param("investIdList") List<Long> investIdList);

    /**
     * 根据还款ID查询投资，还款等信息
     * 
     * @param repaymentId
     *            还款ID
     * @return
     */
    public List<InvestRepaymentDo> selectInvestInfoByRepayId(Long repaymentId);

    /**
     * 取提前还款的投资回款记录
     * 
     * @param currentRepayId
     *            当期还款ID
     * @param borrowId
     *            标的ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月26日下午5:46:53
     */
    public List<InvestRepaymentDo> selectPreRepayByBorrowId(long currentRepayId, long borrowId);

    /**
     * 查询回款记录用来代偿
     * 
     * @param id
     *            还款ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月11日上午8:34:29
     */
    public List<InvestRepaymentDo> selectCompInvestByRepayId(Long id);

    /**
     * 成功投资汇总金额
     * 
     * @param id
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月28日下午4:48:32
     */
    public Map<String, Object> getInvestSuccessAmountByUserId(Long id);

    /**
     * 根据投资ID,转让用户查询最近未还款汇款记录
     * 
     * @param investId
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年11月14日上午10:07:50
     */
    InvestRepaymentDo getNoRepayRecordRecently(Long investId, Long userId);


    /**
     * 根据ID查询回款记录
     * 
     * @param idList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月26日上午10:37:29
     */
    List<InvestRepaymentDo> queryByIds(List<Long> idList);


    /**
     * 修复重复请求， 修改ID
     * 
     * @param investRepayList
     * @param ids
     * @author: zhangyunhmf
     * @date: 2014年12月3日上午8:46:36
     */
    public void updateId(List<InvestRepaymentDo> investRepayList);


}
