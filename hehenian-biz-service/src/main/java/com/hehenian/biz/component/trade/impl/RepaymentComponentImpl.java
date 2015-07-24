package com.hehenian.biz.component.trade.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.NumberUtil;
import com.hehenian.biz.component.trade.IRepaymentComponent;
import com.hehenian.biz.dal.trade.IRepaymentDao;
import com.hehenian.biz.dal.trade.IRepaymentFeeDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Component("repaymentComponent")
public class RepaymentComponentImpl implements IRepaymentComponent {

    public static Logger     log = Logger.getLogger(RepaymentComponentImpl.class);

    @Autowired
    private IRepaymentDao    repaymentDao;

    @Autowired
    private IRepaymentFeeDao repaymentFeeDao;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public RepaymentDo getById(Long id) {
        return repaymentDao.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<RepaymentDo> selectRepayment(Map<String, Object> parameterMap) {
        return repaymentDao.selectRepayment(parameterMap);
    }

    /**
     * 根据条件查询列表，用于翻页
     */
    public List<Map> selectRepaymentPage(Map<String, Object> parameterMap) {
        List<Map> resultList = repaymentDao.selectRepaymentPage(parameterMap);
        return wrapPage(resultList);

    }

    /**
     * 更新
     */
    public int updateRepaymentById(RepaymentDo newRepaymentDo) {
        int result = repaymentDao.updateRepaymentById(newRepaymentDo);
        if (result < 1) {
            throw new BusinessException("还款计划更新失败");
        }
        return result;
    }

    /**
     * 新增
     */
    public int addRepayment(RepaymentDo newRepaymentDo) {
        return repaymentDao.addRepayment(newRepaymentDo);
    }

    /**
     * 删除
     */
    public int deleteById(Long id) {
        int result = repaymentDao.deleteById(id);
        if (result < 1) {
            throw new BusinessException("还款计划删除失败");
        }
        return result;
    }

    /**
     * 封装处理翻页查询的结果： 1.计算：datediff(nextDate,preDate) days 2.计算提前还款金额 3. 处理hessian
     * 不支持的数据类型
     */
    private List<Map> wrapPage(List<Map> page) {
        if (page == null) {
            return Collections.EMPTY_LIST;
        }

        // 处理hessian不支持的类型
        for (Map<String, Object> map : page) {

            double principalBalance = map.get("principalBalance") == null ? 0 : ((BigDecimal) map
                    .get("principalBalance")).doubleValue();
            double stillInterest = map.get("stillInterest") == null ? 0 : ((BigDecimal) map.get("stillInterest"))
                    .doubleValue();
            double fee = map.get("fee") == null ? 0 : ((BigDecimal) map.get("fee")).doubleValue();

            map.put("borrowAmount", NumberUtil.formatObject(map.get("borrowAmount")));
            map.put("annualRate", NumberUtil.formatObject(map.get("annualRate")));
            map.put("stillAmount", NumberUtil.formatObject(map.get("stillAmount")));
            map.put("stillInterest", stillInterest);
            map.put("hasFI", NumberUtil.formatObject(map.get("hasFI")));
            map.put("fee", fee);
            map.put("principalBalance", principalBalance);

            // nextDate, preDate映射成 byte[]
            Object oNextDate = map.get("nextDate");
            String nDate = "";
            if (null == oNextDate) {
                nDate = "";
            } else if (oNextDate instanceof byte[]) {
                byte[] nextDateBytes = (byte[]) map.get("nextDate");
                nDate = new String(nextDateBytes);
            } else if (oNextDate instanceof Date) {
                nDate = DateUtil.dateToString((Date) oNextDate);
            } else {
                nDate = (String) oNextDate;
            }

            Object oPreDate = map.get("preDate");
            String preDate = "";
            if (null == oPreDate) {
                preDate = "";
            } else if (oPreDate instanceof byte[]) {
                byte[] preDateBytes = (byte[]) oPreDate;
                preDate = new String(preDateBytes);
            } else if (oPreDate instanceof Date) {
                preDate = DateUtil.dateToString((Date) oPreDate);
            } else {
                preDate = (String) oPreDate;
            }

            map.put("nextDate", nDate);
            map.put("preDate", preDate);

            // double preSum =
            // getPreSum(nDate,preDate,principalBalance,stillInterest,fee);
            // map.put("preSum", NumberUtil.formatDouble(preSum));//提前还款金额
        }
        return page;
    }

    /**
     * 计算提前 还款金额
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月23日下午3:12:31
     */
    public double getPreSum(String nextDate, String preDate, double principalBalance, double stillInterest, double fee) {

        // 当月天数
        int days = DateUtil.getDaysOfMonth();

        // 剩余本金×（1+3%）+本期已产生利息+本期咨询费+手续费
        long day = 0;
        try {
            Date subtrahendDate = DateUtil.strToYYMMDDDate(nextDate);
            Date minuendDate = DateUtil.strToYYMMDDDate(preDate);
            day = DateUtil.diffDays(minuendDate, subtrahendDate);
            if (day <= 0) {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1.03 * principalBalance + stillInterest * day / days + fee;
    }

    @Override
    public boolean upRepaymentVersionById(int newVersion, int status, long repaymentId) {
        int oldVersion = newVersion - 1;
        int result = repaymentDao.upRepaymentVersionById(oldVersion, newVersion, status, repaymentId);
        if (result < 1) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * (no-Javadoc) <p>Title: updateRepaymentStatusAndAmount</p> <p>Description:
     * </p>
     * 
     * @param needPI
     * 
     * @param lateFI
     * 
     * @param repayId
     * 
     * @param version
     * 
     * @see com.hehenian.biz.component.trade.IRepaymentComponent#
     * updateRepaymentStatusAndAmount(double, double, long, int)
     */
    @Override
    public void updateRepaymentStatusAndAmount(double needPI, double lateFI, int newRepayStatus, long repayId,
            int version) {

        int result = repaymentDao.updateRepaymentStatusAndAmount(needPI, lateFI, newRepayStatus, repayId, version);
        if (result < 1) {
            throw new BusinessException("还款金额和状态更新失败");
        }
    }

    /*
     * (no-Javadoc) <p>Title: selectCurrentPeriod</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#selectCurrentPeriod
     * ()
     */
    @Override
    public RepaymentDo selectCurrentPeriod(long borrowId) {
        return repaymentDao.selectCurrentPeriod(borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: updateIsWebRepayById</p> <p>Description: </p>
     * 
     * @param repaymentId
     * 
     * @param version
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#updateIsWebRepayById
     * (long, int)
     */
    @Override
    public int updateIsWebRepayById(long repaymentId, int version) {
        int result = repaymentDao.updateIsWebRepayById(repaymentId, version);
        if (result < 1) {
            throw new BusinessException("更新还款的代偿标记失败");
        }
        return result;
    }

    /*
     * (no-Javadoc) <p>Title: selectPreRepayTotalAmountByBorrowId</p>
     * <p>Description: </p>
     * 
     * @param borrowId
     * 
     * @param currentRepayId
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IRepaymentComponent#
     * selectPreRepayTotalAmountByBorrowId(long, long)
     */
    @Override
    public RepaymentDo selectPreRepayTotalAmountByBorrowId(long borrowId, long currentRepayId) {
        return repaymentDao.selectPreRepayTotalAmountByBorrowId(borrowId, currentRepayId);
    }

    /*
     * (no-Javadoc) <p>Title: selectThirdPeriod</p> <p>Description: </p>
     * 
     * @param borrowId
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#selectThirdPeriod
     * (long)
     */
    @Override
    public RepaymentDo selectThirdPeriod(long borrowId) {
        return repaymentDao.selectThirdPeriod(borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: updateRepaymentStatusByPreRepay</p>
     * <p>Description: </p>
     * 
     * @param currentRepayId
     * 
     * @param borrowId
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IRepaymentComponent#
     * updateRepaymentStatusByPreRepay(long, long)
     */
    @Override
    public int updateRepaymentStatusByPreRepay(long currentRepayId, long borrowId) {
        return repaymentDao.updateRepaymentStatusByPreRepay(currentRepayId, borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: selectLastPeriod</p> <p>Description: </p>
     * 
     * @param borrowId
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#selectLastPeriod
     * (long)
     */
    @Override
    public RepaymentDo selectLastPeriod(long borrowId) {
        return repaymentDao.selectLastPeriod(borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: selectOverDueRepayList</p> <p>Description: </p>
     * 
     * @param currentDate
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#selectOverDueRepayList
     * (java.util.Date)
     */
    @Override
    public List<RepaymentDo> selectOverDueRepayList(Date currentDate) {
        return repaymentDao.selectOverDueRepayList(currentDate);
    }

    /*
     * (no-Javadoc) <p>Title: addRepaymentFee</p> <p>Description: </p>
     * 
     * @param newRepaymentFeeDo
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#addRepaymentFee(
     * com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo)
     */
    @Override
    public int addRepaymentFee(RepaymentFeeDo newRepaymentFeeDo) {
        return repaymentFeeDao.addRepaymentFee(newRepaymentFeeDo);
    }

    /*
     * (no-Javadoc) <p>Title: addRepaymentFee</p> <p>Description: </p>
     * 
     * @param feeList
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#addRepaymentFee(
     * java.util.List)
     */
    @Override
    public void addRepaymentFee(List<RepaymentFeeDo> feeList) {
        if (null == feeList) {
            return;
        }
        for (RepaymentFeeDo rpf : feeList) {
            this.addRepaymentFee(rpf);
        }
    }

    /*
     * (no-Javadoc) <p>Title: getByRepaymentId</p> <p>Description: </p>
     * 
     * @param id
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IRepaymentComponent#getByRepaymentId
     * (int)
     */
    @Override
    public List<RepaymentFeeDo> getByRepaymentId(Long id) {
        return repaymentFeeDao.getByRepaymentId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hehenian.biz.component.trade.IRepaymentComponent#unLock(int,
     * int, java.lang.Long)
     */
    @Override
    public void unLock(int repayVersion, int status, Long id) {
        int oldVersion = repayVersion - 1;
        repaymentDao.unLockById(oldVersion, repayVersion, id);
    }

    /*
     * (no-Javadoc) <p>Title: updateRepaymentHasAmountById</p> <p>Description:
     * </p>
     * 
     * @param newRepaymentDo
     * 
     * @see com.hehenian.biz.component.trade.IRepaymentComponent#
     * updateRepaymentHasAmountById
     * (com.hehenian.biz.common.trade.dataobject.RepaymentDo)
     */
    @Override
    public void updateRepaymentHasAmountById(RepaymentDo newRepaymentDo) {
        repaymentDao.updateRepaymentHasAmountById(newRepaymentDo);
    }

    @Override
    public List<Map<String, Object>> queryUserRepayments(List<Long> userIdList) {
        return repaymentDao.queryUserRepayments(userIdList);
    }

    @Override
    public RepaymentDo getByBorrowIdAndRepayPeriod(Long borrowId, String repayPeriod) {
        return repaymentDao.getByBorrowIdAndRepayPeriod(borrowId, repayPeriod);
    }

}
