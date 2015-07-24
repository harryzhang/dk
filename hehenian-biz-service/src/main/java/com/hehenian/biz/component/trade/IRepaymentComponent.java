package com.hehenian.biz.component.trade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public interface IRepaymentComponent {

    /**
     * 根据ID 查询
     *
     * @parameter id
     */
    public RepaymentDo getById(Long id);

    /**
     * 根据条件查询列表
     */
    public List<RepaymentDo> selectRepayment(Map<String, Object> parameterMap);

    /**
     * 根据条件查询列表，用于翻页
     */
    public List<Map> selectRepaymentPage(Map<String, Object> parameterMap);

    /**
     * 更新
     */
    public int updateRepaymentById(RepaymentDo newRepaymentDo);

    /**
     * 新增
     */
    public int addRepayment(RepaymentDo newRepaymentDo);

    /**
     * 删除
     */
    public int deleteById(Long id);

    /**
     * upRepaymentVersionById 根据id和新状态更新版本
     *
     * @param oldVersion
     *            更新之前的版本
     * @param newVersion
     *            更新之后的版本
     * @param status
     *            状态 3， 还款中
     * @param repaymentId
     *            还款ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月26日下午5:42:18
     */
    boolean upRepaymentVersionById(int newVersion, int status, long repaymentId);

    /**
     * 还款后更新已经还款本金，罚金，状态
     *
     * @param needPI
     *            已还本金
     * @param lateFI
     *            已还罚金
     * @param repayId
     *            还款ID
     * @param version
     *            记录版本号
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月26日下午5:43:52
     */
    public void updateRepaymentStatusAndAmount(double needPI, double lateFI, int newRepayStatus, long repayId,
            int version);

    /**
     * 提前还款时更新还款状态根据标的ID
     *
     * @param currentRepayId
     *            当前期数的还款记录ID
     * @param borrowId
     *            标的ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月28日下午2:30:10
     */
    public int updateRepaymentStatusByPreRepay(long currentRepayId, long borrowId);

    /**
     * 根据当前日期取当期还款ID
     *
     * @param borrowId
     *            标的ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月26日下午5:48:58
     */
    public RepaymentDo selectCurrentPeriod(long borrowId);

    /**
     * 提前还款借款人应还金额汇总
     *
     * @param borrowId
     *            标的id
     * @param currentRepayId
     *            当前期数还款id，如果当前期数小于第三期，则第三期为当前期数
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月29日下午3:29:42
     */
    public RepaymentDo selectPreRepayTotalAmountByBorrowId(long borrowId, long currentRepayId);

    /**
     * 取第三期的还款记录
     *
     * @param borrowId
     *            标的ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月29日下午3:30:15
     */
    public RepaymentDo selectThirdPeriod(long borrowId);

    /**
     * 更新还款为代偿
     *
     * @param repaymentId
     *            还款ID
     * @param version
     *            版本
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月28日下午6:45:19
     */
    public int updateIsWebRepayById(long repaymentId, int version);

    /**
     * 提前代偿时，不满三期用最后一期
     *
     * @param borrowId
     * @author: zhangyunhmf
     * @date: 2014年10月13日上午10:30:13
     */
    public RepaymentDo selectLastPeriod(long borrowId);

    /**
     * 查询逾期未还款的记录， 定时任务计算罚息会调用
     *
     * @param currentDate
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月15日上午10:23:31
     */
    public List<RepaymentDo> selectOverDueRepayList(Date currentDate);

    /**
     * 新增 还款费用子表
     */
    public int addRepaymentFee(RepaymentFeeDo newRepaymentFeeDo);

    /**
     * 新增 还款费用子表
     *
     * @param feeList
     * @author: zhangyunhmf
     * @date: 2014年10月24日下午12:52:02
     */
    public void addRepaymentFee(List<RepaymentFeeDo> feeList);

    /**
     * 根据还款ID 查询费用列表
     *
     * @param id
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月24日下午1:12:12
     */
    public List<RepaymentFeeDo> getByRepaymentId(Long id);

    /**
     * 如果还款过程中失败， 还款状态可能会被处理成 还款中的， 营运将操作不了，所以要解锁
     *
     * @param repayVersion
     *            版本
     * @param status
     *            状态
     * @param id
     *            还款id
     */
    public void unLock(int repayVersion, int status, Long id);

    /**
     * 根据ID累加已收本息和罚息
     *
     * @param newRepaymentDo
     * @author: zhangyunhmf
     * @date: 2014年11月21日下午5:13:43
     */
    public void updateRepaymentHasAmountById(RepaymentDo newRepaymentDo);

    /**
     * 根据条件查询用户还款记录
     *
     * @param userIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年12月1日下午4:58:02
     */
    public List<Map<String, Object>> queryUserRepayments(List<Long> userIdList);

    /**
     * 根据借款标的ID，还款期限查询还款记录
     * 
     * @param borrowId
     * @param repayPeriod
     * @return
     * @author: liuzgmf
     * @date: 2015年4月24日上午11:02:33
     */
    public RepaymentDo getByBorrowIdAndRepayPeriod(Long borrowId, String repayPeriod);

}
