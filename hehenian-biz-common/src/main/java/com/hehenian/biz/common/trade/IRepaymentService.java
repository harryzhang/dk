package com.hehenian.biz.common.trade;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.dataobject.RepaymentContext;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;
/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


public interface IRepaymentService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RepaymentDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<RepaymentDo> selectRepayment(Map<String,Object> parameterMap);
	
	
	
	/**
	 * 根据条件查询列表，用于翻页
	 * @param parameterMap 查询条件
	 * @param page 翻页对象， 列表的数据放在page里
	 * @return  
	 * @author: zhangyunhmf
	 * @date: 2014年10月10日下午6:55:50
	 */
	public PageDo selectRepaymentPage(Map<String,Object> parameterMap,PageDo page);
	
	
	/**
	 * 更新
	 */
	public int  updateRepaymentById(RepaymentDo newRepaymentDo);
	
	/**
	 * 新增
	 */
	public int addRepayment(RepaymentDo newRepaymentDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);
	
	
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
    public RepaymentContext buildContext(String operationType, long borrowId, long payId, long userId);

    /**
     * 入口还款
     * 
     * @param operationType
     *            还款种类：普通，提前，代偿；看RepayOperationType
     * @param borrowId
     *            标的ID
     * @param payId
     *            还款id
     * @param outCustId
     *            借款人汇付账号
     * @param userId
     *            当前用户
     * @param username
     *            当前用户名
     * @param pwd
     *            密码
     * @param webURL
     *            应用url
     * @return
     */
	public IResult doRepay(String operationType ,
	                       long borrowId,
	                       long payId, 
	                       String outCustId, 
	                       long userId, 
	                       String username, 
	                       String pwd, 
	                       String webURL);

    /**
     * 增加还款费用
     * 
     * @param repaymentFeeList
     * @author: zhangyunhmf
     * @date: 2014年11月27日下午4:16:30
     */
    public void addRepaymentFee(List<RepaymentFeeDo> repaymentFeeList);

    /**
     * 根据ID 更新还款记录成 2， 投资人还款记录更新成 2
     * 
     * @param borrowId
     *            标的ID
     * @param repayPeriod
     *            还款期数
     * @param userId
     *            操作用户ID
     * @author: zhangyunhmf
     * @date: 2014年11月27日下午5:20:01
     */
    public void updateRepaymentStatus(long borrowId, String repayPeriod, long userId);


    /**
     * 
     * 手工一个一个投资者还款
     * 
     * @param repaymentId
     *            还款ID
     * @param ordId
     *            回款ID
     * @param operateType
     *            操作类型： 还款 1， 代偿 2
     * @param realPrincipal
     *            还款本金
     * @param realInterest
     *            还款利息
     * @param fee603
     *            罚息
     * @param fee902
     *            咨询费
     * @param fee901
     *            提前结清手续费
     * @param fee903
     *            管理费
     * @param webUrl
     *            系统URL
     * @author: zhangyunhmf
     * @date: 2014年12月1日下午4:32:18
     */
    public IResult<Object> manualRepayment(long repaymentId, long ordId, String operateType, double realPrincipal,
            double realInterest, double fee603, double fee902, double fee901, double fee903, String webURL);

    /**
     * 修复重复请求ID
     * 
     * @param repaymentId
     * @return
     * @author: zhangyunhmf
     * @date: 2014年12月2日下午5:20:58
     */
    public void changeInvestRepaymentId(long repaymentId);
}
