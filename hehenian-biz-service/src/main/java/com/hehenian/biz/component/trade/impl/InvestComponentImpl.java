package com.hehenian.biz.component.trade.impl;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.exception.BusinessException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.InvestDo;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.dal.trade.IInvestDao;

@Component("investComponent")
public class InvestComponentImpl implements IInvestComponent {

    private static final Logger LOGGER = Logger.getLogger(InvestComponentImpl.class);
    @Autowired
    private IInvestDao          investDao;

    @Override
    public List<InvestDo> queryByBorrowId(Long borrowId) {
        return investDao.queryByBorrowId(borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: updateHasAmount</p> <p>Description: </p>
     * 
     * @param newInvestDo
     * 
     * @see
     * com.hehenian.biz.component.trade.IInvestComponent#updateHasAmount(com
     * .hehenian.biz.common.trade.dataobject.InvestDo)
     */
    @Override
    public void updateHasAmount(InvestDo newInvestDo) {
        investDao.updateHasAmount(newInvestDo);
    }

    /*
     * (no-Javadoc) <p>Title: updateRepayStatusById</p> <p>Description: </p>
     * 
     * @param investId
     * 
     * @see
     * com.hehenian.biz.component.trade.IInvestComponent#updateRepayStatusById
     * (long)
     */
    @Override
    public void updateRepayStatusById(long investId) {
        investDao.updateRepayStatusById(investId);
    }

    @Override
    public Integer updateInvest(InvestDo udpateInvestDo) {
        int result = investDao.updateInvest(udpateInvestDo);
        if (result <= 0) {
            LOGGER.error("更新投资记录失败");
            throw new BusinessException("恭喜您，投资成功。");
        }
        return result;
    }

    @Override
    public int addInvest(InvestDo newInvestDo) {
        int result = investDao.addInvest(newInvestDo);
        if (result <= 0) {
            LOGGER.error("投资记录添加失败");
            throw new BusinessException("投资记录添加失败");
        }
        return result;
    }

    @Override
    public InvestDo getById(Long investId) {
        return investDao.getById(investId);
    }

    /*
     * (no-Javadoc) <p>Title: updateRepayStatusByBorrowId</p> <p>Description:
     * </p>
     * 
     * @param borrowId
     * 
     * @see
     * com.hehenian.biz.component.trade.IInvestComponent#updateRepayStatusByBorrowId
     * (long)
     */
    @Override
    public void updateRepayStatusByBorrowId(long borrowId) {
        investDao.updateRepayStatusByBorrowId(borrowId);
    }

    @Override
    public Double getDueinSum(Long userId) {
        return investDao.getDueinSum(userId);
    }

    @Override
    public Long getAutoIncrementId() {
        InvestDo investDo = new InvestDo();
        investDao.addInvest(investDo);
        investDao.deleteById(investDo.getId());
        return investDo.getId();
    }

    /*
     * (no-Javadoc) <p>Title: selectInvestSuccessRecordPage</p> <p>Description:
     * </p>
     * 
     * @param parameterMap
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IInvestComponent#
     * selectInvestSuccessRecordPage(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> selectInvestSuccessRecordPage(Map<String, Object> parameterMap) {
        return investDao.selectInvestSuccessRecordPage(parameterMap);
    }

    /*
     * (no-Javadoc) <p>Title: selectDebtSuccessRecordPage</p> <p>Description:
     * </p>
     * 
     * @param parameterMap
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IInvestComponent#selectDebtSuccessRecordPage
     * (java.util.Map)
     */
    @Override
    public List<Map<String, Object>> selectDebtSuccessRecordPage(Map<String, Object> parameterMap) {
        return investDao.selectDebtSuccessRecordPage(parameterMap);
    }

    /*
     * (no-Javadoc) <p>Title: updateRepayStatusForPreSettle</p> <p>Description:
     * </p>
     * 
     * @param id
     * 
     * @see com.hehenian.biz.component.trade.IInvestComponent#
     * updateRepayStatusForPreSettle(java.lang.Long)
     */
    @Override
    public void updateRepayStatusForPreSettle(Long id) {
        investDao.updateRepayStatusForPreSettle(id);
    }

    /**
     * 查询用户投资的次数
     * 
     * @param userId
     * @return
     */
    public long hasInvest(long userId) {
        return investDao.hasInvest(userId);
    }

    @Override
    public List<Map<String, Object>> queryUserInvests(List<Long> userIdList) {
        return investDao.queryUserInvests(userIdList);
    }

    @Override
    public List<InvestDo> queryByIds(List<Long> idList) {
        return investDao.queryByIds(idList);
    }

    @Override
    public List<Map<String, Object>> queryInvestDetails(Long borrowId) {
        return investDao.queryInvestDetails(borrowId);
    }

}
