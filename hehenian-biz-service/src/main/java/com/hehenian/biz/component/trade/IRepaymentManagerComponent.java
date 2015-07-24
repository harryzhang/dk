/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade
 * @Title: IRepayment.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:37:33
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.dataobject.ChinapnrAccount;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentWrap;
import com.hehenian.biz.common.trade.dataobject.RepaymentContext;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;


/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:37:33
 */
public interface IRepaymentManagerComponent {
    
    /**
     * 构建还款的上下文
     * 
     * @param repaymentId 还款ID
     * @param borrowId    标的ID
     * @param userId      当期用户ID
     * @param username    用户名
     * @param operationType 还款操作种类
     * @param outCustId     出资汇付ID
     * @param webURL        系统URL
     * @author: zhangyunhmf
     * @date: 2014年9月30日上午9:06:16
     */
    public RepaymentContext buildContext(long repaymentId,
                              long borrowId, 
                              long userId, 
                              String username,
                              String operationType,
                              String outCustId,
                              String webURL);
    
    
    /** 
     * 数据和权限检查  
     * @author: zhangyunhmf
     * @return 返回0表示调用成功
     * @date: 2014年10月22日上午10:24:03
     */
    public int doCheck(RepaymentContext rc);
    
    /**
     * 
     * callChinaprService 调用汇付
     * @param list
     * @return 
     *OutParameter
     * @exception 
     * @since  1.0.0
     */
    public IResult<Object> callChinaPnrService(RepaymentContext rc);
    
    /**
     * 
     * 更改数据库的相关记录的金额  
     * @author: zhangyunhmf
     * @date: 2014年9月28日下午3:55:30
     */
    public int updateStatus(RepaymentContext rc);
    
    /**
	 * 如果还款过程中失败， 还款状态可能会被处理成 还款中的， 营运将操作不了，所以要解锁
	 */
    public void unLock(RepaymentDo repaymentDo);

    /**
     * 还款之前把需要还款的记录状态更新成还款中
     * 
     * @return
     */
    public boolean lock(RepaymentDo repaymentDo);
    
    /**
     * 更新借款人金额
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午6:47:41
     */
    public void updatePublisherAmount(RepaymentContext rc);


    /**
     * 更新标的的状态， 和还款期数
     * 
     * @param borrowId
     *            标的ID
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午4:50:01
     */
    public void updateBorrow(long borrowId);

    /**
     * 构建还款列表
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午1:58:05
     */
    public int buildInvestList(RepaymentContext rc);

    /**
     * 将回款对象转成 还款对象包装对象
     * 
     * @param investRepaymentList
     * @return
     */
    public List<InvestRepaymentWrap> convertInvestRepaymentWrap(List<InvestRepaymentDo> investRepaymentList);

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
    public void updateRepaymentStatus(RepaymentContext rc);

    /**
     * 查询费用子表 代偿 @see
     * {@link com.hehenian.biz.component.trade.impl.CompRepayComponentImpl#buildRepaymentFee()}
     * 提前结清 @see
     * {@link com.hehenian.biz.component.trade.impl.PreSettleRepayComponentImpl#buildRepaymentFee()}
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月22日下午4:25:31
     */
    public void buildRepaymentFee(RepaymentDo repayDo, Integer paymentMode);

    /**
     * 根据用户ID 获取用户对象， 如果是代偿， 代偿子类会覆盖这个方法根据，配置文件里的代偿账号查询用户对象
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月22日下午6:22:47
     */
    public int buildPubisher(RepaymentContext rc);

    /**
     * 构建还款记录对象根据还款ID 查询一个还款记录， 提前结清是汇总的一个还款对象具体看提前结清的实现
     * 
     * @see com.hehenian.biz.component.trade.impl.PreSettleRepayComponentImpl#buildRepayment()
     * @see com.hehenian.biz.component.trade.impl.CompRepayComponentImpl#buildRepayment()
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月30日上午9:42:19
     */
    public RepaymentDo buildRepayment(RepaymentContext rc);

    /**
     * 根据费用分摊到不同的子账户
     * 
     * @param feeCode
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午9:58:24
     */
    public Map<String, ChinapnrAccount> buildFeeAccountMap();

    public void finishedInvestRepay(RepaymentContext rc);

    /**
     * 获取接口人， 如果是代偿获取代偿账号对应的用户
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月29日下午4:09:27
     */
    public AccountUserDo getPublisher(RepaymentContext rc);

    /**
     * 出资账户，如果代偿是代偿方，从配置文件读取， 否则就是借款人
     */
    public String getOutCustId(RepaymentContext rc);

    /**
     * 出资账户，如果代偿是代偿方，从配置文件读取， 否则就是借款人
     */
    public String getOutAcctId();

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
    public String buildInvestorFundRecordRemark(RepaymentContext rc, String fundrecordRemarkTemplate, String fundName,
            double amount);

    /**
     * 构建投资人短信通知的内容
     * 
     * @param notifyTemplate
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午5:34:56
     */
    public String buildInvestorSmsContent(RepaymentContext rc, String notifyTemplate, String username,
            double recivedPrincipal, double recivedInterest, double recivedFI, double investFee);

    /**
     * 构建借款人资金变动记录的remark
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午5:29:43
     */
    public String buildPublisherFundRecordRemark(RepaymentContext rc, String fundrecordRemarkTemplate, String fundMode);

    /**
     * 构建借款人短信通知的内容
     * 
     * @param notifyTemplate
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午5:34:56
     */
    public String buildPubliserSmsContent(RepaymentContext rc, String notifyTemplate);

    public void updateInvestorAmount(RepaymentContext rc, long repaymentId, long investId, long borrowId,
            double recivedPrincipal, double recivedInterest, double recivedFI, double investFee, long investor,
            int isWebRepay);

    /**
     * 更新投资人金额
     * 
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午9:58:13
     */
    public void updateInVestor(RepaymentContext rc, InvestRepaymentWrap irWrap);

    /**
     * 
     * 更新回款表 , 如果是代偿操作isWebPay = 2 ,并且要加一条新的还款记录表示代偿
     * 
     * @param investDo
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午10:53:41
     */
    public void updateInvestRepayment(RepaymentContext rc, InvestRepaymentDo investDo);

    /**
     * 更新投资状态
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午11:35:26
     */
    public void updateInvestStatus(long borrowId);

}
