package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.contant.Constants;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.IRepaymentService;
import com.hehenian.biz.common.trade.RepayOperationType;
import com.hehenian.biz.common.trade.dataobject.*;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.trade.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Service("repaymentService")
public class RepaymentServiceImpl implements IRepaymentService {

    private final Logger               logger = Logger.getLogger(this.getClass());

    @Autowired
    private IRepaymentComponent        repaymentComponent;

    // 还款的三种实现
    @Autowired
    private IRepaymentManagerComponent normalRepayComponentImpl;
    @Autowired
    private IRepaymentManagerComponent preSettleRepayComponentImpl;
    @Autowired
    private IRepaymentManagerComponent compRepayComponentImpl;

    @Autowired
    private IAssignmentDebtComponent   assignmentDebtComponent;

    @Autowired
    private IRepaymentRecordComponent  repaymentRecordComponent;

    @Autowired
    private IPersonComponent           personComponent;

    @Autowired
    private IInvestRepaymentComponent  investRepaymentComponent;

    @Autowired
    private IBorrowComponent           borrowComponent;

    /**
     * 根据条件查询列表，用于翻页
     */
    public PageDo selectRepaymentPage(Map<String, Object> parameterMap, PageDo page) {
        parameterMap.put(Constants.MYBATIS_PAGE, page);
        List<Map> pageList = repaymentComponent.selectRepaymentPage(parameterMap);
        page.setPage(pageList);
        return page;
    }

    /**
     * 更新
     */
    public int updateRepaymentById(RepaymentDo newRepaymentDo) {
        return repaymentComponent.updateRepaymentById(newRepaymentDo);
    }

    /**
     * 新增
     */
    public int addRepayment(RepaymentDo newRepaymentDo) {
        return repaymentComponent.addRepayment(newRepaymentDo);
    }

    /**
     * 删除
     */
    public int deleteById(Long id) {
        return repaymentComponent.deleteById(id);
    }

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public RepaymentDo getById(Long id) {
        return repaymentComponent.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<RepaymentDo> selectRepayment(Map<String, Object> parameterMap) {
        return repaymentComponent.selectRepayment(parameterMap);
    }

    private IRepaymentManagerComponent getIRepaymentManagerComponent(String operationType) {
        if (RepayOperationType.NORMAL_REPAY.toString().equals(operationType)) {
            return normalRepayComponentImpl;
        } else if (RepayOperationType.PRE_SETTLE_REPAY.toString().equals(operationType)) {
            return preSettleRepayComponentImpl;
        } else if (RepayOperationType.COMP_REPAY.toString().equals(operationType)) {
            return compRepayComponentImpl;
        } else {
            return null;
        }
    }

    /**
     * 返回 提前结清对象，方便核对数据： 提前结清金额， 提前结清手续费等
     * 
     * @param operationType
     *            操作类型
     * @param borrowId
     *            标的ID
     * @param payId
     *            还款id
     * @param userId
     *            借款人
     * @return {@link}
     *         See(com.hehenian.biz.component.trade.impl.RepaymentContext)
     * @author: zhangyunhmf
     * @date: 2014年11月26日上午9:11:04
     */
    public RepaymentContext buildContext(String operationType, long borrowId, long payId, long userId) {

        IRepaymentManagerComponent repayment = getIRepaymentManagerComponent(operationType);

        RepaymentContext rc = repayment.buildContext(payId, borrowId, userId, null, operationType, null, null);
        PersonDo person = personComponent.getByUserId(rc.getBorrow().getPublisher());
        rc.getUserDo().setPerson(person);

        return rc;

    }

    /**
     * 还款服务入口
     */
    public IResult<Object> doRepay(String operationType, long borrowId, long payId, String outCustId, long userId,
            String username, String pwd, String webURL) {

        IRepaymentManagerComponent repayment = getIRepaymentManagerComponent(operationType);
        // 准备参数
        RepaymentContext rc = repayment.buildContext(payId, borrowId, userId, username, operationType, outCustId,
                webURL);
        try {

            if (null == rc) {
                return buildResult(11);
            }

            // 检查完整性和合法性
            int result = repayment.doCheck(rc);
            if (result != 0) {
                return buildResult(result);
            }

            // 更改成还款中的状态
            repayment.lock(rc.getRepaymentDo());
            // 记录还款
            repaymentRecordComponent.logProcess(rc.getRepaymentDo(), operationType);

            // 调用汇付
            IResult<Object> callResult = repayment.callChinaPnrService(rc); // 判断是否成功
            if (!callResult.isSuccess()) {
                Integer errorIdx = (Integer) rc.getCallChinapnrErrorIndex();
                if (errorIdx.intValue() < 1) { // 没有一条成功就返回页面
                    return callResult;
                }
            }
            // end 调用汇付

            try {
                assignmentDebtComponent.updateDebtStatusFailure(borrowId);
            } catch (Exception e) {
                logger.error("标的id：" + borrowId + "失效债券转让失败");
            }

            // 写出资人的流水，和可用金额
            repayment.updatePublisherAmount(rc);

            // 汇付调用全部成功 更新数据库相关单据状态
            if (callResult.isSuccess()) {
                repayment.updateStatus(rc);
            }

            repaymentRecordComponent.updateProcess(rc.getRepaymentDo(), operationType);

            if (!callResult.isSuccess()) {
                return new ResultSupport<Object>(false, "调用汇付部分成功， 部分失败：" + callResult.getErrorMessage());
            }

            return new ResultSupport<Object>(true);

        } catch (Exception e) {
            logger.error(e);
            return new ResultSupport<Object>(false, e.getMessage());
        } finally {
            // 如果失败解锁
            repayment.unLock(rc.getRepaymentDo());
            // 清理参数
            rc.freeContext();

        }
    }

    private IResult<Object> buildResult(int errorCode) {

        switch (errorCode) {
        case 1:
            return new ResultSupport<Object>(false, "找不到还款记录");
        case 2:
            return new ResultSupport<Object>(false, "该笔还款已处理");
        case 3:
            return new ResultSupport<Object>(false, "你的金额不够，请充值再操作");
        case 4:
            return new ResultSupport<Object>(false, "你获取的还款记录信息不是最新的信息，请刷新重试");
        case 5:
            return new ResultSupport<Object>(false, "你获取的还款记录信息不是最新的信息，该还款记录状态已经被更新");
        case 6:
            return new ResultSupport<Object>(false, "你没有此还款记录操作权限");
        case 7:
            return new ResultSupport<Object>(false, "此标的还款期数以还满");
        case 8:
            return new ResultSupport<Object>(false, "没有找到要还款的数据");
        case 9:
            return new ResultSupport<Object>(false, "检查数据过程中出错");
        case 10:
            return new ResultSupport<Object>(false, "没有正确配置代偿账户");
        case 11:
            return new ResultSupport<Object>(false, "准备还款数据过程中失败");
        default:
            return new ResultSupport<Object>(false, "还款失败，未知错误");
        }
    }

    /*
     * (no-Javadoc) <p>Title: addRepaymentFee</p> <p>Description: </p>
     * 
     * @param repaymentFeeList
     * 
     * @see
     * com.hehenian.biz.common.trade.IRepaymentService#addRepaymentFee(java.
     * util.List)
     */
    public void addRepaymentFee(List<RepaymentFeeDo> repaymentFeeList) {
        repaymentComponent.addRepaymentFee(repaymentFeeList);
    }

    /*
     * (no-Javadoc) <p>Title: updateRepaymentStatus</p> <p>Description: </p>
     * 
     * @param borrowId
     * 
     * @param repayPeriod
     * 
     * @param userId
     * 
     * @see
     * com.hehenian.biz.common.trade.IRepaymentService#updateRepaymentStatus
     * (long, java.lang.String, long)
     */
    @Override
    public void updateRepaymentStatus(long borrowId, String repayPeriod, long userId) {

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("borrowId", borrowId);
        parameterMap.put("repayPeriod", repayPeriod);
        List<RepaymentDo> repaymentList = repaymentComponent.selectRepayment(parameterMap);
        if (null == repaymentList || repaymentList.size() != 1) {
            throw new BusinessException("参数不对，查询不到对应的还款记录");
        }

        RepaymentDo newRepaymentDo = repaymentList.get(0);

        List<InvestRepaymentDo> investRepaymentList = investRepaymentComponent.selectInvestInfoByRepayId(newRepaymentDo
                .getId());
        for (InvestRepaymentDo irdo : investRepaymentList) {
            irdo.setRepayStatus(2);
            investRepaymentComponent.updateInvestRepaymentById(irdo);
        }

        borrowComponent.updateBorrowStatusAndHasDeadlineById(borrowId);
        borrowComponent.updateBorrowStatus(borrowId);

        newRepaymentDo.setRepayStatus(2);
        newRepaymentDo.setExecuteTime(new Date());
        repaymentComponent.updateRepaymentById(newRepaymentDo);
        
        RepaymentRecordDo newRepaymentRecord = new RepaymentRecordDo();
        newRepaymentRecord.setRemark(RepayOperationType.CLOSE_REPAY.name());
        newRepaymentRecord.setCreateTime(new Date());
        newRepaymentRecord.setOporator(userId);
        newRepaymentRecord.setProcessStatus("2");
        newRepaymentRecord.setRepayAmount(0d);
        newRepaymentRecord.setRepayType("2");
        newRepaymentRecord.setRepayId(newRepaymentDo.getId());
        repaymentRecordComponent.addRepaymentRecord(newRepaymentRecord);
    }

    /*
     * (no-Javadoc) <p>Title: manualRepayment</p> <p>Description: </p>
     * 
     * @param repaymentId
     * 
     * @param ordId
     * 
     * @param operateType
     * 
     * @param realPrincipal
     * 
     * @param realInterest
     * 
     * @param fee603
     * 
     * @param fee902
     * 
     * @param fee901
     * 
     * @param fee903
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.trade.IRepaymentService#manualRepayment(java.
     * lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public IResult<Object> manualRepayment(long repaymentId, long ordId, String operateType, double realPrincipal,
            double realInterest, double fee603, double fee902, double fee901, double fee903, String webURL) {
        IRepaymentManagerComponent repayment = this.getIRepaymentManagerComponent(operateType);
        RepaymentDo repaymentDo = this.repaymentComponent.getById(repaymentId);
        BorrowDo borrowDo = this.borrowComponent.getById(repaymentDo.getBorrowId());

        RepaymentContext rc = repayment.buildContext(repaymentId, borrowDo.getId(),
                borrowDo.getPublisher(), "",
                operateType, "",
                webURL);

        List<InvestRepaymentWrap> investList = rc.getInvestList();
        InvestRepaymentWrap investRepaymentWrap = null;
        for (InvestRepaymentWrap irWrap : investList) {
            if (ordId == irWrap.getInvestRepaymentDO().getId().longValue()) {
                investRepaymentWrap = irWrap;
                break;
            }
        }
        InvestRepaymentDo investRepaymentDO = investRepaymentWrap.getInvestRepaymentDO();
        investRepaymentDO.setRecivedPrincipal(realPrincipal);
        investRepaymentDO.setRecivedInterest(realInterest);

        List<RepaymentFeeDo> proportionFeeList = new ArrayList<RepaymentFeeDo>();
        // 代偿的时候不需要费用
        if (!RepayOperationType.COMP_REPAY.toString().equals(operateType)) {
            if (fee902 > 0) {
                RepaymentFeeDo rf = new RepaymentFeeDo();
                rf.setFeeCode(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_CONSULT);
                rf.setHasAmount(fee902);
                rf.setInvestRepaymentId(ordId);
                rf.setRemark("手动还款");
                rf.setLastUpdateDate(new Date());
                rf.setRepaymentId(repaymentId);
                proportionFeeList.add(rf);
            }

            if (fee901 > 0) {
                RepaymentFeeDo rf = new RepaymentFeeDo();
                rf.setFeeCode(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_PRE);
                rf.setHasAmount(fee901);
                rf.setInvestRepaymentId(ordId);
                rf.setRemark("手动还款");
                rf.setLastUpdateDate(new Date());
                rf.setRepaymentId(repaymentId);
                proportionFeeList.add(rf);
            }

            if (fee903 > 0) {
                RepaymentFeeDo rf = new RepaymentFeeDo();
                rf.setFeeCode(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_SERVICE_CHARGE);
                rf.setHasAmount(fee903);
                rf.setInvestRepaymentId(ordId);
                rf.setRemark("手动还款");
                rf.setLastUpdateDate(new Date());
                rf.setRepaymentId(repaymentId);
                proportionFeeList.add(rf);
            }

            if (fee603 > 0) {
                RepaymentFeeDo rf = new RepaymentFeeDo();
                rf.setFeeCode(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_FX);
                rf.setHasAmount(fee603);
                rf.setInvestRepaymentId(ordId);
                rf.setRemark("手动还款");
                rf.setLastUpdateDate(new Date());
                rf.setRepaymentId(repaymentId);
                proportionFeeList.add(rf);
            }
        }
        if (proportionFeeList.size() > 1) {
            investRepaymentWrap.setProportionFeeList(proportionFeeList);
        } else {
            investRepaymentWrap.setProportionFeeList(null);
        }

        investList.clear();
        investList.add(investRepaymentWrap);
        rc.setInvestList(investList);

        rc.getRepaymentDo().setFeeList(proportionFeeList);

        IResult<Object> result = repayment.callChinaPnrService(rc);
        if (result.isSuccess()) {
            rc.freshRepaymentAmount();
            repayment.updatePublisherAmount(rc);
        } else {
            return result;
        }
        return new ResultSupport<Object>(true);

    }

    /*
     * (no-Javadoc) <p>Title: changeInvestRepaymentId</p> <p>Description: </p>
     * 
     * @param repaymentId
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.trade.IRepaymentService#changeInvestRepaymentId
     * (long)
     */
    @Override
    public void changeInvestRepaymentId(long repaymentId) {
        List<InvestRepaymentDo> investRepayList = this.investRepaymentComponent.selectInvestInfoByRepayId(repaymentId);
        if (null == investRepayList || investRepayList.isEmpty()) {
            return;
        }
        this.investRepaymentComponent.updateId(investRepayList);
    }
}
