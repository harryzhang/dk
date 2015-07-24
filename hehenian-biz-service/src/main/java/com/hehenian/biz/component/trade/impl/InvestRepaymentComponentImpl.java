package com.hehenian.biz.component.trade.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;
import com.hehenian.biz.dal.trade.IInvestRepaymentDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Component("investRepaymentComponent")
public class InvestRepaymentComponentImpl implements IInvestRepaymentComponent {
    @Autowired
    private IInvestRepaymentDao investRepaymentDao;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public InvestRepaymentDo getById(Long id) {
        return investRepaymentDao.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<InvestRepaymentDo> selectInvestRepayment(Map<String, Object> parameterMap) {
        return investRepaymentDao.selectInvestRepayment(parameterMap);
    }

    /**
     * 更新
     */
    public int updateInvestRepaymentById(InvestRepaymentDo newInvestRepaymentDo) {
        return investRepaymentDao.updateInvestRepaymentById(newInvestRepaymentDo);
    }

    /**
     * 新增
     */
    public int addInvestRepayment(InvestRepaymentDo newInvestRepaymentDo) {
        return investRepaymentDao.addInvestRepayment(newInvestRepaymentDo);
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return investRepaymentDao.deleteById(id);
    }

    @Override
    public List<InvestRepaymentDo> queryNoRepayRecordsRecently(Long userId) {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("userId", userId);
        searchItems.put("repayStatus", 1);// 未还款
        searchItems.put("beginDate", DateUtils.truncate(new Date(), Calendar.DATE));
        Date endDate = DateUtils.addMonths(new Date(), 1);// 一个月后的前一天
        searchItems.put("endDate", DateUtils.truncate(endDate, Calendar.DATE));
        return investRepaymentDao.queryNoRepayRecordsRecently(searchItems);
    }

    @Override
    public Double getTotalRecivedInterest(Long userId) {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("userId", userId);
        searchItems.put("repayStatus", 2);// 已还款
        return investRepaymentDao.getTotalRecivedInterest(searchItems);
    }

    @Override
    public List<InvestRepaymentDo> queryByUserIdAndRepayDate(Long userId, Date beginDate, Date endDate) {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("userId", userId);
        searchItems.put("beginDate", beginDate);
        searchItems.put("endDate", endDate);
        searchItems.put("today", DateUtils.truncate(new Date(), Calendar.DATE));
        return investRepaymentDao.queryByUserIdAndRepayDate(searchItems);
    }

    /*
     * (no-Javadoc) <p>Title: getTotalRecived</p> <p>Description: </p>
     * 
     * @param investId
     * 
     * @param repaymentId
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IInvestRepaymentComponent#getTotalRecived
     * (long, long)
     */
    @Override
    public List<Map<String, Object>> getTotalRecived(long investId, long repaymentId) {
        return investRepaymentDao.getTotalRecived(investId, repaymentId);
    }

    /*
     * (no-Javadoc) <p>Title: updateRecievedAmount</p> <p>Description: </p>
     * 
     * @param newInvestRepaymentDo
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * updateRecievedAmount
     * (com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo)
     */
    @Override
    public int updateRecievedAmount(InvestRepaymentDo newInvestRepaymentDo) {
        return investRepaymentDao.updateRecievedAmount(newInvestRepaymentDo);
    }

    /*
     * (no-Javadoc) <p>Title: updateRepayStatusByPreRepay</p> <p>Description:
     * </p>
     * 
     * @param newInvestRepaymentDo
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * updateRepayStatusByPreRepay
     * (com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo)
     */
    @Override
    public int updateRepayStatusByPreRepay(InvestRepaymentDo newInvestRepaymentDo) {
        return investRepaymentDao.updateRepayStatusByPreRepay(newInvestRepaymentDo);
    }

    @Override
    public int addWebPayRecord(long oldId, long userId) {
        return investRepaymentDao.addWebPayRecord(oldId, userId);

    }

    // @Override
    // public void updateWebPayRecordStatus(Long repaymentId) {
    // investRepaymentDao.updateWebPayRecordStatus(repaymentId);
    // }

    @Override
    public void updateRepayStatus(int repayStatus, int isDebt, Long id) {
        Map<String, Object> updateItems = new HashMap<String, Object>();
        updateItems.put("repayStatus", repayStatus);
        updateItems.put("isDebt", isDebt);
        updateItems.put("id", id);
        int count = investRepaymentDao.updateRepayStatus(updateItems);
        if (count != 1) {
            throw new BusinessException("修改回款[" + id + "]记录失败!");
        }
    }

    /*
     * (no-Javadoc) <p>Title: selectOverDueInvestRepayList</p> <p>Description:
     * </p>
     * 
     * @param currentDate
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * selectOverDueInvestRepayList(java.util.Date)
     */
    @Override
    public List<InvestRepaymentDo> selectOverDueInvestRepayList(Date currentDate) {
        return investRepaymentDao.selectOverDueInvestRepayList(currentDate);
    }

    @Override
    public Double getRecivedPrincipal(Long userId) {
        return investRepaymentDao.getRecivedPrincipal(userId);
    }

    /*
     * (no-Javadoc) <p>Title: getInvestSuccessAmount</p> <p>Description: </p>
     * 
     * @param investIdList
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * getInvestSuccessAmount(java.util.List)
     */
    @Override
    public List<Map<String, Object>> getInvestSuccessAmount(List<Long> investIdList) {
        return investRepaymentDao.getInvestSuccessAmount(investIdList);
    }

    /*
     * (no-Javadoc) <p>Title: selectInvestInfoByRepayId</p> <p>Description: </p>
     * 
     * @param repaymentId
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * selectInvestInfoByRepayId(java.lang.Long)
     */
    @Override
    public List<InvestRepaymentDo> selectInvestInfoByRepayId(Long repaymentId) {
        return investRepaymentDao.selectInvestInfoByRepayId(repaymentId);
    }

    /*
     * (no-Javadoc) <p>Title: selectPreRepayByBorrowId</p> <p>Description: </p>
     * 
     * @param currentRepayId
     * 
     * @param borrowId
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * selectPreRepayByBorrowId(long, long)
     */
    @Override
    public List<InvestRepaymentDo> selectPreRepayByBorrowId(long currentRepayId, long borrowId) {
        return investRepaymentDao.selectPreRepayByBorrowId(currentRepayId, borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: selectCompInvestByRepayId</p> <p>Description: </p>
     * 
     * @param id
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * selectCompInvestByRepayId(java.lang.Long)
     */
    @Override
    public List<InvestRepaymentDo> selectCompInvestByRepayId(Long id) {
        return investRepaymentDao.selectCompInvestByRepayId(id);
    }

    /*
     * (no-Javadoc) <p>Title: getInvestSuccessAmountByUserId</p> <p>Description:
     * </p>
     * 
     * @param id
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestRepaymentComponent#
     * getInvestSuccessAmountByUserId(java.lang.Long)
     */
    @Override
    public Map<String, Object> getInvestSuccessAmountByUserId(Long id) {
        return investRepaymentDao.getInvestSuccessAmountByUserId(id);
    }

    @Override
    public InvestRepaymentDo getNoRepayRecordRecently(Long investId, Long userId) {
        return investRepaymentDao.getNoRepayRecordRecently(investId, userId);
    }


    @Override
    public List<InvestRepaymentDo> queryByIds(List<Long> idList) {
        return investRepaymentDao.queryByIds(idList);
    }



    /*
     * (no-Javadoc) <p>Title: updateId</p> <p>Description: </p>
     * 
     * @param investRepayList
     * 
     * @param ids
     * 
     * @see
     * com.hehenian.biz.component.trade.IInvestRepaymentComponent#updateId(java
     * .util.List, long[])
     */
    @Override
    public void updateId(List<InvestRepaymentDo> investRepayList) {
        for (InvestRepaymentDo ir : investRepayList) {
            ir.setParentId(ir.getId());
            investRepaymentDao.deleteById(ir.getParentId());
            investRepaymentDao.addInvestRepayment(ir); // 自动增长
        }

    }


}
