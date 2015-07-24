/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: DefaultRepayment.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:43:00
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.base.constant.Constants;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.fund.dataobject.FundDo;
import com.hehenian.biz.common.notify.dataobject.MessageType;
import com.hehenian.biz.common.notify.dataobject.SMSNotifyDo;
import com.hehenian.biz.common.notify.dataobject.SmsType;
import com.hehenian.biz.common.trade.dataobject.AssignmentDebtDo;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.trade.dataobject.ChinapnrAccount;
import com.hehenian.biz.common.trade.dataobject.FundrecordDo;
import com.hehenian.biz.common.trade.dataobject.InvestDo;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentWrap;
import com.hehenian.biz.common.trade.dataobject.RepaymentContext;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;
import com.hehenian.biz.common.trade.dataobject.RiskDetailDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.NotifyTemplateUtil;
import com.hehenian.biz.common.util.NumberUtil;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.fund.dao.IFundDao;
import com.hehenian.biz.component.notify.INotifyDao;
import com.hehenian.biz.component.trade.IAssignmentDebtComponent;
import com.hehenian.biz.component.trade.IBorrowComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IGuaranteeInstitutionsComponent;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;
import com.hehenian.biz.component.trade.IRepaymentComponent;
import com.hehenian.biz.component.trade.IRepaymentManagerComponent;
import com.hehenian.biz.component.trade.IRiskDetailComponent;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.chinapnr.ChinaPnrConfig;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

/**
 * 
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:43:00
 */
public abstract class DefaultAbstractRepayComponent implements IRepaymentManagerComponent {

    protected final Logger                    logger = Logger.getLogger(this.getClass());

    @Autowired
    protected IRepaymentComponent             repaymentComponent;
    @Autowired
    protected IAccountManagerService          accountManagerService;
    @Autowired
    protected ChinaPnrConfig                  chinaPnrConfig;
    @Autowired
    protected IPersonComponent                personComponent;
    @Autowired
    protected IBorrowComponent                borrowComponent;
    @Autowired
    protected IGuaranteeInstitutionsComponent guaranteeInstitutionsComponent;
    @Autowired
    protected IRiskDetailComponent            riskDetailComponent;
    @Autowired
    protected INotifyDao                      smsDao;                                    // 短信
    @Autowired
    protected INotifyDao                      mailNotifyDao;                             // 邮件
    @Autowired
    protected IUserComponent                  userComponent;
    @Autowired
    protected IFundrecordComponent            fundrecordComponent;
    @Autowired
    protected IFundDao                        fundDao;
    @Autowired
    protected IInvestComponent                investComponent;
    @Autowired
    protected IInvestRepaymentComponent       investRepaymentComponent;
    @Autowired
    protected IAssignmentDebtComponent        assignmentDebtComponent;

    /**
     * 费用分摊账号映射
     */
    private Map<String, ChinapnrAccount>      feeAccountMap;

    /**
     * 更新标的的状态， 和还款期数
     * 
     * @param borrowId
     *            标的ID
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午4:50:01
     */
    public abstract void updateBorrow(long borrowId);

    /**
     * 构建还款列表
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午1:58:05
     */
    public abstract int buildInvestList(RepaymentContext rc);

    /**
     * 将回款对象转成 还款对象包装对象
     * 
     * @param investRepaymentList
     * @return
     */
    public List<InvestRepaymentWrap> convertInvestRepaymentWrap(List<InvestRepaymentDo> investRepaymentList) {
        if (null == investRepaymentList) {
            return null;
        }
        List<InvestRepaymentWrap> listWrap = new ArrayList<InvestRepaymentWrap>();

        for (InvestRepaymentDo irDo : investRepaymentList) {
            InvestRepaymentWrap investWrap = new InvestRepaymentWrap();
            investWrap.setInvestRepaymentDO(irDo);
            listWrap.add(investWrap);
        }
        return listWrap;
    }

    /**
     * 更新还款状态, 更新已收本金，利息，罚金= 所有费用和
     * 
     * 代偿 @see
     * {@link com.hehenian.biz.component.trade.impl.CompRepayComponentImpl#updateRepaymentStatus()}
     * 提前结清 @see
     * {@link com.hehenian.biz.component.trade.impl.PreSettleRepayComponentImpl#updateRepaymentStatus()}
     * 
     * @param needPI
     *            本金
     * @param lateFI
     *            罚金
     * @param repayId
     *            还款ID
     * @param version
     *            还款记录version
     * @param borrowId
     *            标的ID
     * 
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午4:45:34
     */
    public void updateRepaymentStatus(RepaymentContext rc) {
        RepaymentDo repaymentDo = rc.getRepaymentDo();
        RepaymentDo newRepaymentDo = new RepaymentDo();
        newRepaymentDo.setRepayStatus(2);
        newRepaymentDo.setId(repaymentDo.getId());
        newRepaymentDo.setRealRepayDate(new Date());
        newRepaymentDo.setExecuteTime(new Date());
        repaymentComponent.updateRepaymentById(newRepaymentDo);
    }

    /**
     * 查询费用子表 代偿 @see
     * {@link com.hehenian.biz.component.trade.impl.CompRepayComponentImpl#buildRepaymentFee()}
     * 提前结清 @see
     * {@link com.hehenian.biz.component.trade.impl.PreSettleRepayComponentImpl#buildRepaymentFee()}
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月22日下午4:25:31
     */
    public void buildRepaymentFee(RepaymentDo repayDo,Integer paymentMode) {
        // 构建应还费用
        repayDo.buildRepaymentFee();
        // 查询实还金额
        List<RepaymentFeeDo> feeList = this.repaymentComponent.getByRepaymentId(repayDo.getId());

        // put已收费用
        if (null != feeList) {
            for (RepaymentFeeDo rpf : feeList) {
                RepaymentFeeDo tmpFee = repayDo.getRepaymentFee(rpf.getFeeCode());
                if (tmpFee == null) {
                    repayDo.addRepaymentFee(rpf);
                } else {
                    tmpFee.setHasAmount(rpf.getHasAmount());
                }
            }
        }

    }

    /**
     * 根据用户ID 获取用户对象， 如果是代偿， 代偿子类会覆盖这个方法根据，配置文件里的代偿账号查询用户对象
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月22日下午6:22:47
     */
    public int buildPubisher(RepaymentContext rc) {
        AccountUserDo user = this.userComponent.getById(rc.getUserId());
        if (null == user) {
            throw new BusinessException("找不到借款人");
        }
        rc.setUserDo(user);
        rc.setUsername(user.getUsername());
        return 0;
    }

    /**
     * 构建还款记录对象根据还款ID 查询一个还款记录， 提前结清是汇总的一个还款对象具体看提前结清的实现
     * 
     * @see com.hehenian.biz.component.trade.impl.PreSettleRepayComponentImpl#buildRepayment()
     * @see com.hehenian.biz.component.trade.impl.CompRepayComponentImpl#buildRepayment()
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月30日上午9:42:19
     */
    public RepaymentDo buildRepayment(RepaymentContext rc) {
        long repayId = rc.getRepaymentId();
        RepaymentDo repaymentDo = this.repaymentComponent.getById(repayId);
        rc.setRepaymentDo(repaymentDo);
        return repaymentDo;
    }

    /**
     * 根据费用分摊到不同的子账户
     * 
     * @param feeCode
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午9:58:24
     */
    public Map<String, ChinapnrAccount> buildFeeAccountMap() {
        Map<String, ChinapnrAccount> feeAccountMap = new HashMap<String, ChinapnrAccount>();
        // 901 提前结清手续费
        ChinapnrAccount cAccount = new ChinapnrAccount();
        cAccount.setAccountId(this.chinaPnrConfig.getConsFeeAccount());
        cAccount.setCustId(this.chinaPnrConfig.getConsFeeCustId());
        feeAccountMap.put(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_PRE, cAccount);
        // 603 扣除逾期罚息
        ChinapnrAccount cAccount1 = new ChinapnrAccount();
        cAccount1.setAccountId(this.chinaPnrConfig.getFxAccount());
        cAccount1.setCustId(this.chinaPnrConfig.getMerCustId());
        feeAccountMap.put(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_FX, cAccount1);
        // consult 咨询费
        ChinapnrAccount cAccount2 = new ChinapnrAccount();
        cAccount2.setAccountId(this.chinaPnrConfig.getConsFeeAccount());
        cAccount2.setCustId(this.chinaPnrConfig.getConsFeeCustId());
        feeAccountMap.put(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_CONSULT, cAccount2);
        // 还款手续费
        ChinapnrAccount cAccount3 = new ChinapnrAccount();
        cAccount3.setAccountId(this.chinaPnrConfig.getFeeAccount());
        cAccount3.setCustId(this.chinaPnrConfig.getFeeCustId());
        feeAccountMap.put(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_SERVICE_CHARGE, cAccount3);
        return feeAccountMap;
    }

    public void finishedInvestRepay(RepaymentContext rc) {
        // RepaymentContext rc = this.getContext();
        // 查询所有投资人的投资信息
        List<InvestRepaymentWrap> investList = rc.getInvestList();
        for (InvestRepaymentWrap irWrap : investList) {
            InvestRepaymentDo investRepay = irWrap.getInvestRepaymentDO();
            // 取收益用户
            AccountUserDo inUser = this.userComponent.getById(investRepay.getOwner());
            investRepay.setOwnerUser(inUser);
            // 取投资
            InvestDo investDo = this.investComponent.getById(investRepay.getInvestId());
            // 因为getById 取出来的投资investDo是被重 sqlsession的缓存中获取，他们将是统一一个对象，
            // 这相同的事务中（即这个for循环中）
            InvestDo cloneInvestDo = new InvestDo();
            BeanUtils.copyProperties(investDo, cloneInvestDo);
            investRepay.setInvestDo(cloneInvestDo);
            // 取债券转让
            AssignmentDebtDo adebt = assignmentDebtComponent.getLastAssignmentDebtByInvestId(cloneInvestDo.getId(),
                    investRepay.getOwner());
            if (null != adebt) { // 如果发生了代偿，此时的owner 是代偿账户，根据投资和代偿找不到债券转让，
                                 // 但是经测试，还代偿人的时候，这个subordId没有限制
                                 // 如果不加这个不等于 null
                                 // 的判断，会把之前找到的债券转让记录覆盖，导致资金流向不对的异常，
                                 // 这是因为investDo 对象在这个for里ibatis返回的是同一个对象
                cloneInvestDo.setAssignDebtDo(adebt);
            }
        }
    }

    /**
     * 检查还款期数
     * 
     * @param deadline
     *            借款期数
     * @param hasDeadline
     *            已还款期数
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午9:12:00
     */
    protected boolean checkDeadline(int deadline, int hasDeadline) {
        return deadline > hasDeadline;
    }

    /**
     * 获取接口人， 如果是代偿获取代偿账号对应的用户
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月29日下午4:09:27
     */
    public AccountUserDo getPublisher(RepaymentContext rc) {
        AccountUserDo user = rc.getUserDo();
        return user;
    }

    /**
     * 从上下文中取还款记录
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月29日下午4:09:27
     */
    protected RepaymentDo getRepaymentDo(RepaymentContext rc) {
        // RepaymentContext rc = context.get();
        RepaymentDo repaymentDo = rc.getRepaymentDo();
        if (null == repaymentDo) {
            repaymentDo = this.buildRepayment(rc);
            rc.setRepaymentDo(repaymentDo);
        }
        return repaymentDo;
    }

    /**
     * 构建还款的 所需的数据上下文
     * 
     * @param repaymentId
     *            还款ID
     * @param borrowId
     *            标的ID
     * @param userId
     *            当期用户ID
     * @param username
     *            用户名
     * @param operationType
     *            还款操作种类
     * @param outCustId
     *            出资汇付ID
     * @param webURL
     *            系统URL
     * @author: zhangyunhmf
     * @date: 2014年9月30日上午9:06:16
     */
    public RepaymentContext buildContext(long repaymentId, long borrowId, long userId, String username,
            String operationType, String outCustId, String webURL) {

        RepaymentContext rc;
        rc = new RepaymentContext(repaymentId, borrowId, userId, username, operationType, outCustId, webURL);

        this.feeAccountMap = buildFeeAccountMap();
        RepaymentDo repaymentDo = this.buildRepayment(rc);
        
        this.buildPubisher(rc);
        BorrowDo borrow = borrowComponent.getById(borrowId);
        
        this.buildRepaymentFee(repaymentDo, borrow.getPaymentMode());
        
        rc.setBorrow(borrow);

        // 构建还款列表
        this.buildInvestList(rc);
        // 分摊费用
        rc.splitFee();

        // 根据回款列表取投资和债券转让
        this.finishedInvestRepay(rc);

        return rc;
    }

    /**
     * 
     * updateRepayVersion更新版本
     * 
     * @param newVersion
     *            新版本
     * @param status
     *            状态
     * @param repaymentId
     *            还款id
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    protected boolean updateRepayVersion(int newVersion, int status, long repaymentId) {
        return repaymentComponent.upRepaymentVersionById(newVersion, status, repaymentId);
    }

    /**
     * 
     * checkUser 检查用户合法性
     * 
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    protected boolean checkUser(RepaymentContext rc) {
        BorrowDo borrow = rc.getBorrow();
        long userId = rc.getUserDo().getId();
        return borrow.getPublisher().longValue() == userId;
    }

    /**
     * 
     * checkRepaymentStatus 检查还款记录的状态
     * 
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    protected boolean checkRepaymentStatus(RepaymentDo repay) {
        return repay.getRepayStatus() == 1;
    }

    protected boolean checkRepaymentAmount(double needAmount) {
        return needAmount > 0;
    }

    /**
     * 出资账户，如果代偿是代偿方，从配置文件读取， 否则就是借款人
     */
    public String getOutCustId(RepaymentContext rc) {
        return String.valueOf(rc.getUserDo().getUsrCustId());
    }

    /**
     * 出资账户，如果代偿是代偿方，从配置文件读取， 否则就是借款人
     */
    public String getOutAcctId() {
        return "";
    }

    /**
     * 
     * checkAmount 检查用户账户金额是否足够
     * 
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    protected boolean checkAmount(long userId, double needAmount) {
        AccountUserDo user = userComponent.getById(userId);
        return user.getUsableSum() >= needAmount;
    }

    /**
     * 构建投资人资金变动记录的remark
     * 
     * @param fundrecordRemarkTemplate
     * @param fundName
     *            费用名称
     * @param amount
     *            还款金额
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月9日上午9:43:14
     */
    public String buildInvestorFundRecordRemark(RepaymentContext rc, String fundrecordRemarkTemplate,
            String fundName, double amount) {
        try {
            // RepaymentContext rc = this.getContext();
            BorrowDo borrow = rc.getBorrow();
            Map<String, Object> messageObject = new HashMap<String, Object>();
            messageObject.put("webURL", rc.getWebURL());
            messageObject.put("borrowTitle", borrow.getBorrowTitle());
            messageObject.put("borrowId", borrow.getId());
            messageObject.put("needAmount", NumberUtil.formatDouble(amount));
            messageObject.put("fundMode", fundName);
            return NotifyTemplateUtil.getContentByTemplate(fundrecordRemarkTemplate, messageObject);
        } catch (Throwable e) {
            logger.error(e);
            return "";
        }
    }

    /**
     * 构建投资人短信通知的内容
     * 
     * @param notifyTemplate
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午5:34:56
     */
    public String buildInvestorSmsContent(RepaymentContext rc, String notifyTemplate, String username,
            double recivedPrincipal, double recivedInterest, double recivedFI, double investFee) {

        // 尊敬的[${userName}]:
        // 您投资的借款[${borrowTitle}],第[${repayPeriod}]期还款已经完成.<br/>[${fundMode}]本期应得总额：￥[${recivedSum}],其中本金部分为：[${hasP}]元,利息部分：[${hasI}]元,实得逾期罚息：[${hasFI}]元<br/>扣除投资管理费：￥[${mFee}]元<br/>实得总额：￥[${msFee}]元
        try {
            BorrowDo borrow = rc.getBorrow();
            RepaymentDo repayDo = rc.getRepaymentDo();
            Map<String, Object> messageObject = new HashMap<String, Object>();
            double recivedSum = recivedInterest + recivedPrincipal + recivedFI;
            messageObject.put("userName", username);
            messageObject.put("borrowTitle", borrow.getBorrowTitle());
            messageObject.put("repayPeriod", repayDo.getRepayPeriod());
            messageObject.put("fundMode", "");
            messageObject.put("hasP", NumberUtil.formatDouble(recivedPrincipal));// 本金
            messageObject.put("hasI", NumberUtil.formatDouble(recivedInterest));// 利息
            messageObject.put("hasFI", NumberUtil.formatDouble(recivedFI));// 逾期罚息
            messageObject.put("mFee", NumberUtil.formatDouble(investFee));// 投资管理费
            messageObject.put("msFee", NumberUtil.formatDouble(recivedSum - investFee));// 实得总额
            messageObject.put("recivedSum", NumberUtil.formatDouble(recivedSum));// 本期应得总额
            return NotifyTemplateUtil.getContentByTemplate(notifyTemplate, messageObject);
        } catch (Throwable e) {
            logger.error(e);
            return "";
        }
    }

    /**
     * 构建借款人资金变动记录的remark
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午5:29:43
     */
    public String buildPublisherFundRecordRemark(RepaymentContext rc, String fundrecordRemarkTemplate,
            String fundMode) {
        try {
            // RepaymentContext rc = this.getContext();
            String userName = rc.getUsername();
            BorrowDo borrow = rc.getBorrow();
            RepaymentDo repayDo = rc.getRepaymentDo();
            Map<String, Object> messageObject = new HashMap<String, Object>();
            messageObject.put("userName", userName);
            messageObject.put("borrowTitle", borrow.getBorrowTitle());
            messageObject.put("repayPeriod", repayDo.getRepayPeriod());
            messageObject.put("borrowId", borrow.getId());
            messageObject.put("fundMode", fundMode);
            messageObject.put("webURL", rc.getWebURL());
            messageObject.put("lateFI", repayDo.getLateFi());
            messageObject.put("fee", repayDo.getRepayFee());
            messageObject.put("consultFee", repayDo.getConsultFee());
            messageObject.put(
                    "needAmount",
                    NumberUtil.formatDouble(repayDo.getStillInterest() + repayDo.getStillPrincipal()
                            + repayDo.getRepayFee() + repayDo.getConsultFee()));
            messageObject.put("hasPi",
                    NumberUtil.formatDouble(repayDo.getStillInterest() + repayDo.getStillPrincipal()));

            return NotifyTemplateUtil.getContentByTemplate(fundrecordRemarkTemplate, messageObject);
        } catch (Throwable e) {
            logger.error(e);
            return "";
        }
    }

    /**
     * 构建借款人短信通知的内容
     * 
     * @param notifyTemplate
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午5:34:56
     */
    public String buildPubliserSmsContent(RepaymentContext rc, String notifyTemplate) {
        try {
            String userName = rc.getUsername();
            BorrowDo borrow = rc.getBorrow();
            RepaymentDo repayDo = rc.getRepaymentDo();
            Map<String, Object> messageObject = new HashMap<String, Object>();
            messageObject.put("userName", userName);
            messageObject.put("borrowTitle", borrow.getBorrowTitle());
            messageObject.put("repayPeriod", repayDo.getRepayPeriod());
            messageObject.put("needAmount",
                    NumberUtil.formatDouble(repayDo.getStillInterest() + repayDo.getStillPrincipal()));
            messageObject.put("fee", NumberUtil.formatDouble(repayDo.getRepayFee()));
            messageObject.put("consultFee", NumberUtil.formatDouble(repayDo.getConsultFee()));
            return NotifyTemplateUtil.getContentByTemplate(notifyTemplate, messageObject);
        } catch (Throwable e) {
            logger.error(e);
            return "";
        }
    }

    /**
     * 插入通知
     * 
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午10:04:58
     */
    protected void addNotify(String userPhone, String messageContent) {
        try {
            SMSNotifyDo msg = new SMSNotifyDo();
            msg.setAsync(false);
            msg.setRecievers(userPhone);
            msg.setSendFlag("F");
            msg.setValidate("T");
            msg.setSmsType(SmsType.YOUXUNTONG.name());
            msg.setMessageType(MessageType.SMS.name());
            msg.setSimpleMessage(messageContent);
            smsDao.addMessage(msg);
        } catch (Throwable e) {
            logger.error(e);
        }
    }

    /**
     * 更新借款人金额
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午6:47:41
     */
    public void updatePublisherAmount(RepaymentContext rc) {
        // 跟新成功的金额
        rc.freshRepaymentAmount();

        RepaymentDo repaymentDo = rc.getRepaymentDo();
        String userAmountChangeDirect = "-";
        FundDo fund = fundDao.getById(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_REPAY);
        String url = this
                .buildPublisherFundRecordRemark(rc, RepaymentContext.fundrecord_remark_url,
                fund.getFundName());
        long userId = rc.getUserId();
        double pi = repaymentDo.getHasPi();

        // 扣除还款金额 本金和利息
        userComponent.updateUsableSum(pi, userAmountChangeDirect, userId);
        FundrecordDo repayFundrecord = new FundrecordDo();
        repayFundrecord.setRemarks(url + fund.getFundName() + "[" + pi + "]元");
        repayFundrecord.setBorrowId(repaymentDo.getBorrowId());
        repayFundrecord.setHandleSum(pi);
        repayFundrecord.setUserId(userId);
        repayFundrecord.setFundMode(fund.getFundName());
        repayFundrecord.setRepaymentId(repaymentDo.getId());
        repayFundrecord.setPaynumber(repaymentDo.getId() + "");
        repayFundrecord.setOperateType(new Integer(com.hehenian.biz.common.base.constant.Constants.FEE_CODE_REPAY));
        repayFundrecord.setSpending(pi);
        fundrecordComponent.addFundByRepay(repayFundrecord);

        // 扣除各种费用
        List<RepaymentFeeDo> feeList = repaymentDo.getFeeList();
        if (null != feeList) {
            for (RepaymentFeeDo rf : feeList) {
                double feeAmount = rf.getHasAmount();
                if (feeAmount <= 0) {
                    continue;
                }
                userComponent.updateUsableSum(feeAmount, userAmountChangeDirect, userId);
                fund = fundDao.getById(rf.getFeeCode());
                String fundRemark = url + fund.getFundName() + "[" + feeAmount + "]元";
                repayFundrecord = new FundrecordDo();
                repayFundrecord.setRemarks(fundRemark);
                repayFundrecord.setBorrowId(repaymentDo.getBorrowId());
                repayFundrecord.setHandleSum(feeAmount);
                repayFundrecord.setUserId(userId);
                repayFundrecord.setFundMode(fund.getFundName());
                repayFundrecord.setRepaymentId(repaymentDo.getId());
                repayFundrecord.setPaynumber(repaymentDo.getId() + "");
                repayFundrecord.setOperateType(Integer.valueOf(rf.getFeeCode()));
                repayFundrecord.setSpending(feeAmount);
                fundrecordComponent.addFundByRepay(repayFundrecord);
            }
        }
    }

    public void updateInvestorAmount(RepaymentContext rc, long repaymentId, long investId, long borrowId,
            double recivedPrincipal, double recivedInterest, double recivedFI, double investFee, long investor,
            int isWebRepay) {

        double recivedSum = recivedPrincipal + recivedInterest;

        FundDo fund = fundDao.getById("151");
        String fundRemark = this.buildInvestorFundRecordRemark(rc, RepaymentContext.fundrecord_remark_template_invest,
                fund.getFundName(), recivedSum);
        // 扣除还款金额
        userComponent.updateUsableSum(recivedSum, "+", investor);

        FundrecordDo repayFundrecord = new FundrecordDo();
        repayFundrecord.setRemarks(fundRemark);
        repayFundrecord.setBorrowId(borrowId);
        repayFundrecord.setHandleSum(recivedSum);
        repayFundrecord.setUserId(investor);
        repayFundrecord.setFundMode(fund.getFundName());
        repayFundrecord.setRepaymentId(repaymentId);
        repayFundrecord.setPaynumber(repaymentId + "");
        repayFundrecord.setOperateType(151);
        repayFundrecord.setIncome(recivedSum);
        fundrecordComponent.addFundByRepay(repayFundrecord);

        // 罚息收入
        if (isWebRepay == 1) {
            if (recivedFI > 0) {
                fund = fundDao.getById("156");
                fundRemark = this.buildInvestorFundRecordRemark(rc, RepaymentContext.fundrecord_remark_template_invest,
                        fund.getFundName(), recivedFI);
                // 扣除还款金额
                userComponent.updateUsableSum(recivedFI, "+", investor);

                FundrecordDo fundrecordFI = new FundrecordDo();
                fundrecordFI.setRemarks(fundRemark);
                fundrecordFI.setBorrowId(borrowId);
                fundrecordFI.setHandleSum(recivedFI);
                fundrecordFI.setUserId(investor);
                fundrecordFI.setFundMode(fund.getFundName());
                fundrecordFI.setRepaymentId(repaymentId);
                fundrecordFI.setPaynumber(repaymentId + "");
                fundrecordFI.setOperateType(156);
                fundrecordFI.setIncome(recivedFI);
                fundrecordComponent.addFundByRepay(fundrecordFI);
            }
        }

        // 投资人扣除投资管理费
        if (investFee > 0) {
            fund = fundDao.getById("651");
            fundRemark = this.buildInvestorFundRecordRemark(rc, RepaymentContext.fundrecord_remark_template_invest,
                    fund.getFundName(), investFee);
            // 扣除还款金额
            userComponent.updateUsableSum(investFee, "-", investor);

            FundrecordDo fundrecordFee = new FundrecordDo();
            fundrecordFee.setRemarks(fundRemark);
            fundrecordFee.setBorrowId(borrowId);
            fundrecordFee.setHandleSum(investFee);
            fundrecordFee.setUserId(investor);
            fundrecordFee.setFundMode(fund.getFundName());
            fundrecordFee.setRepaymentId(repaymentId);
            fundrecordFee.setPaynumber(repaymentId + "");
            fundrecordFee.setOperateType(651);
            fundrecordFee.setSpending(investFee);
            fundrecordComponent.addFundByRepay(fundrecordFee);

            // 投资手续费累加到风险保障金
            insertRiskDetail(borrowId, investor, "投资管理费", "收入", investFee, 0);
        }
        // 插入短信通知
        try {
            AccountUserDo user = this.userComponent.getById(investor);
            String smsContent = this.buildInvestorSmsContent(rc, RepaymentContext.sms_invest_template,
                    user.getUsername(), recivedPrincipal, recivedInterest, recivedFI, investFee);
            PersonDo person = personComponent.getByUserId(investor);
            String userPhone = person.getCellPhone();
            addNotify(userPhone, smsContent);
        } catch (Exception e) {
            logger.error("构建短信信息失败");
            logger.error(e);
        }
        // end 插入短信通知

    }

    /**
     * 处理代偿:更新担保公司的资金; 并登记风险保障金变动过程
     * 
     * @param needAmount
     *            还款金额
     * @param newRiskDetailDo
     *            风险保障对象
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午4:44:42
     */
    protected void insertRiskDetail(long borrowId, long userId, String resource, String riskType, double riskInCome,
            double riskSpending) {
        RiskDetailDo newRiskDetailDo = new RiskDetailDo();
        newRiskDetailDo.setBorrowId(borrowId);
        newRiskDetailDo.setTrader(userId);
        newRiskDetailDo.setResource(resource);
        newRiskDetailDo.setRiskType(riskType);
        newRiskDetailDo.setRiskInCome(riskInCome);
        newRiskDetailDo.setRiskSpending(riskSpending);
        // 偿还给网站累加到风险保障金中
        riskDetailComponent.addRiskAndSumBalance(newRiskDetailDo);
    }

    /**
     * 数据和权限检查
     * 
     * @author: zhangyunhmf
     * @return 返回0表示调用成功
     * @date: 2014年10月22日上午10:24:03
     */
    public int doCheck(RepaymentContext rc) {

        boolean checkResult = false;
        try {

            // 获取还款对象
            RepaymentDo repaymentDo = this.getRepaymentDo(rc);
            if (null == repaymentDo) {
                return 1;
            }

            // 检查回款记录
            if (null == rc.getInvestList() || rc.getInvestList().size() < 1) {
                return 8;
            }

            // 获取总还款金额
            double needSum = repaymentDo.getStillTotalAmount();
            checkResult = checkRepaymentAmount(needSum);
            if (!checkResult) {
                return 2;
            }

            long userId = rc.getUserId();
            // 不检查金额了
            // checkResult = checkAmount(userId, needSum);
            // if (!checkResult) {
            // return 3;
            // }

            // 检查状态还款记录
            checkResult = checkRepaymentStatus(repaymentDo);
            if (!checkResult) {
                return 5;
            }

            checkResult = checkUser(rc);
            if (!checkResult) {
                return 6;

            }

            // 通过还款id获取 标的
            BorrowDo borrow = rc.getBorrow();
            // 检查还款期数, 如果已还款期数>= 借款期数 表示已经还款完毕

            /*
             * 2015-02-07 集团贷的 deadline 期数不太正确 checkResult =
             * checkDeadline(borrow.getDeadline(), borrow.getHasDeadline()); if
             * (!checkResult) { return 7; }
             */

            return 0;
        } catch (Throwable e) {
            logger.error("还款失败", e);
            return 9;
        }
    }

    /**
     * 还款之前把需要还款的记录状态更新成还款中
     * 
     * @return
     */
    public boolean lock(RepaymentDo repaymentDo) {
        // 更新版本
        int repayVersion = repaymentDo.getVersion();
        repayVersion = repayVersion + 1;
        repaymentDo.setVersion(repayVersion);
        return updateRepayVersion(repayVersion, 3, repaymentDo.getId());// 还款中
    }

    /**
     * 如果还款过程中失败， 还款状态可能会被处理成 还款中的， 营运将操作不了，所以要解锁
     */
    public void unLock(RepaymentDo repaymentDo) {
        // 更新版本
        int repayVersion = repaymentDo.getVersion();
        repayVersion = repayVersion + 1;
        repaymentDo.setVersion(repayVersion);
        repaymentComponent.unLock(repayVersion, 1, repaymentDo.getId()); // 解锁
    }

    /**
     * 
     * callManagerService 调用汇付
     * 
     * @param list
     * @return OutParameter
     * @exception
     * @since 1.0.0
     */
    public IResult callChinaPnrService(RepaymentContext rc) {

        // RepaymentContext rc = this.getContext();
        String outCustId = this.getOutCustId(rc);
        String divDetails = ""; // 包装咨询费和手续费json字符
        double transAmt = 0; // 投资者应收总额
        String inAcctId = "";
        String outAcctId = this.getOutAcctId();

        List<InvestRepaymentWrap> investList = rc.getInvestList();
        // String[][] feeDiv = rc.getFeeDiv();
        for (int i = 0, size = investList.size(); i < size; i++) {
            InvestRepaymentWrap irWrap = investList.get(i);
            InvestRepaymentDo investRepayDo = irWrap.getInvestRepaymentDO();
            transAmt = investRepayDo.getRecivedTotalAmount();

            divDetails = irWrap.getDivDetails(this.feeAccountMap);
            String ordId = String.valueOf(investRepayDo.getId());
            String inCustId = String.valueOf(investRepayDo.getOwnerUser().getUsrCustId());
            String subOrdId = String.valueOf(investRepayDo.getInvestDo().getSubOrdId());
            String subOrdDate = DateUtil.dateToYMD(investRepayDo.getInvestDo().getSubOrdDate());

            // 如果该记录已代偿,收款人为担保公司,inCustId为商户子账户
            if (investRepayDo.getIsWebRepay() == 2) {
                inCustId = chinaPnrConfig.getCompCustId(); // 代偿账户汇付ID
                inAcctId = chinaPnrConfig.getCompAccount(); // 代偿账户
                transAmt = investRepayDo.getRecivedPI();
            }

            // 累加罚息
            // transAmt = CalculateUtils.add(transAmt, fi);

            InParameter inParameter = new InParameter();
            inParameter.setOrdId(ordId);
            inParameter.setVersion("20");
            inParameter.getParams().put("OutCustId", outCustId);// 出账客户号
            inParameter.getParams().put("OutAcctId", outAcctId);// 出账子账户
            inParameter.getParams().put("InCustId", inCustId);// 入账客户号
            inParameter.getParams().put("InAcctId", inAcctId);// 入账子账户

            inParameter.getParams().put("SubOrdId", subOrdId);// 订单号 变长 20 位
                                                              // 由商户的系统生成，必须保证唯一。
            inParameter.getParams().put("SubOrdDate", subOrdDate);// 关联投标订单流水日期是

            inParameter.getParams().put("TransAmt", NumberUtil.formatDouble(transAmt));// 金额
            // Fee 扣款手续费, 放款或扣款的手续费
            String totalInvestorFee = CalculateUtils.round(irWrap.getFeeSum());
            inParameter.getParams().put("Fee", totalInvestorFee);

            inParameter.getParams().put("DivDetails", divDetails);// 分账账户串 变长
            inParameter.getParams().put("MerPriv", "Repayment");

            OutParameter outParameter = accountManagerService.repayment(inParameter, AccountType.CHINAPNR);

            // 保存汇付调用信息
            irWrap.setSuccess(outParameter.isSuccess());

            if (outParameter.isSuccess()) {// 调用成功更新数据
                this.updateInVestor(rc, irWrap);
            } else {
                // 调用汇付失败下标号
                rc.setCallChinapnrErrorIndex(i);
                return new ResultSupport<Object>(false, "错误的订单号：" + inParameter.getOrdId() + outParameter.getRespDesc());
            }
        }
        return new ResultSupport<Object>(true, "调用汇付成功");
    }

    /**
     * 更新投资人金额
     * 
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午9:58:13
     */
    public void updateInVestor(RepaymentContext rc, InvestRepaymentWrap irWrap) {

        RepaymentDo repaymentDo = this.getRepaymentDo(rc);
        int isWebRepay = repaymentDo.getIsWebRepay(); // 是否代偿
        long borrowId = repaymentDo.getBorrowId(); // 标的ID
        InvestRepaymentDo investDo = irWrap.getInvestRepaymentDO();

        double hasfi = irWrap.getFeeByFeeCode(Constants.FEE_CODE_FX);

        // 判断该还款记录是否被代偿过
        // if (2 == isWebRepay) {
        // // 更新担保公司的资金
        // guaranteeInstitutionsComponent.updateOrganMoney(investDo.getRecivedTotalAmount());
        // insertRiskDetail(borrowId, rc.getUserId(), "逾期垫付网站收入", "收入", needSum,
        // 0);
        // }

        // 更新投资收款信息
        updateInvestRepayment(rc, investDo);

        // 记录交易费用
        repaymentComponent.addRepaymentFee(irWrap.getProportionFeeList());


        if (!"COMP_REPAY".equals(rc.getOperationType())) {

            // 记录已还本金和利息
            RepaymentDo newRepaymentDo = new RepaymentDo();
            newRepaymentDo.setId(repaymentDo.getId());
            newRepaymentDo.setHasFi(hasfi);
            newRepaymentDo.setHasPi(investDo.getRecivedPI());
            repaymentComponent.updateRepaymentHasAmountById(newRepaymentDo);

        }

        if (2 != isWebRepay) { // 代偿的时候已经更新过投资记录，所以这里不重复更新
            // 更新投资记录还款信息
            InvestDo newInvestDo = new InvestDo();
            newInvestDo.setId(investDo.getInvestId());
            newInvestDo.setHasInterest(investDo.getRecivedInterest());
            newInvestDo.setHasPrincipal(investDo.getRecivedPrincipal());
            newInvestDo.setHasFI(hasfi);
            newInvestDo.setManageFee(investDo.getImanageFee());
            investComponent.updateHasAmount(newInvestDo);
        }

        // 获取owner
        long investor = investDo.getOwner();
        if (2 == isWebRepay) { // 如果代偿修改代偿用户的可用资金
            AccountUserDo accountUser = this.userComponent.getUserByCustId(this.chinaPnrConfig.getCompCustId());
            if (null != accountUser) {
                investor = accountUser.getId();
            }
        }
        // 修改投资人资金收入
        updateInvestorAmount(rc, repaymentDo.getId(), investDo.getInvestId(), borrowId, investDo.getRecivedPrincipal(),
                investDo.getRecivedInterest(), investDo.getRecivedFi(), investDo.getImanageFee(), investor, isWebRepay);

    }

    /**
     * 
     * 更新回款表 , 如果是代偿操作isWebPay = 2 ,并且要加一条新的还款记录表示代偿
     * 
     * @param investDo
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午10:53:41
     */
    public void updateInvestRepayment(RepaymentContext rc, InvestRepaymentDo investDo) {
        InvestRepaymentDo newInvestRepaymentDo = new InvestRepaymentDo();
        newInvestRepaymentDo.setInterestOwner(investDo.getOwner());
        // 如果是代偿操作这里应该设置成2
        newInvestRepaymentDo.setIsWebRepay(investDo.getIsWebRepay());
        newInvestRepaymentDo.setRecivedFi(investDo.getRecivedFi());
        newInvestRepaymentDo.setOwner(investDo.getOwner());
        newInvestRepaymentDo.setInvestId(investDo.getInvestId());
        newInvestRepaymentDo.setRepayId(investDo.getRepayId());
        newInvestRepaymentDo.setId(investDo.getId());
        newInvestRepaymentDo.setRecivedPrincipal(investDo.getRecivedPrincipal());
        newInvestRepaymentDo.setRecivedInterest(investDo.getRecivedInterest());
        investRepaymentComponent.updateRecievedAmount(newInvestRepaymentDo);
    }

    /**
     * 
     * 更改数据库的相关记录的金额
     * 
     * @author: zhangyunhmf
     * @return 0 表示调用成功
     * @date: 2014年9月28日下午3:55:30
     */
    public int updateStatus(RepaymentContext rc) {
        RepaymentDo repaymentDo = rc.getRepaymentDo();

        // 更新还款状态
        updateRepaymentStatus(rc);

        // 更新担保公司
        // updateGuarantee(rc);
        // start 更新借款人的可用资金

        // 更新投资状态
        updateInvestStatus(repaymentDo.getBorrowId());
        // 更新标的的状态， 和还款期数
        updateBorrow(repaymentDo.getBorrowId());

        return 0;
    }

    /**
     * 更新投资状态
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午11:35:26
     */
    public void updateInvestStatus(long borrowId) {
        investComponent.updateRepayStatusByBorrowId(borrowId);
    }

    /**
     * 更新风险保障金
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午11:29:56
     */
    protected void updateGuarantee(RepaymentContext rc) {
        // TODO Auto-generated method stub

    }
}