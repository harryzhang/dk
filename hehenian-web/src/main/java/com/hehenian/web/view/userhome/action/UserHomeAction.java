/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.colorlife.action
 * @Title: ColorLifeAction.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月30日 下午2:47:24
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.userhome.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.UserType;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.userhome.IUserIncomeService;
import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;
import com.hehenian.common.annotations.RequireLogin;
import com.hehenian.web.base.action.PageAction;
import com.shove.web.util.DesSecurityUtil;

/**
 * 提供给彩生活的接口， 以servlet提供
 * 
 * @author: zhangyunhmf
 * @date 2014年10月30日 下午2:47:24
 */
@Scope("prototype")
@Component("userHomeAction")
public class UserHomeAction extends PageAction {

	/** 
     */
	private static final long serialVersionUID = 6423443585271170275L;
	@Autowired
	private IActivityOrderService activityOrderService;
	@Autowired
	private IUserIncomeService userIncomeService;
    @Autowired
    private IPersonService personService;
	private void checkUserId(String userId) {
		if (StringUtils.isBlank(userId)) {

		}

		try {
			Long.parseLong(userId);
		} catch (Exception e) {
			this.LOG.error("userId", "无效参数");
		}
	}

	/**
	 * 订单收益查询接口 接口参数： userId bigInt 非空 20 彩之云用户账号 接口参数：orderSN 字符 非空 32 订单号
	 * 
	 * 返回： userId bigInt 非空 20 彩之云用户账号 orderSN 字符 非空 32 订单号 orderInvestAmount
	 * 保留两位小数Decimal(18,2) 非空 订单投资金额 orderInterestAmount 保留两位小数Decimal(18,2) 非空
	 * 订单利息 　orderWithdrawalAmount 保留两位小数Decimal(18,2) 非空 订单可提取金额
	 * 
	 * @return json 字符串
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:52:49
	 */
	public String orderDetail() {
		String userId = this.request("userId");
		String orderSN = this.request("orderSN");

		checkUserId(userId);
		if (StringUtils.isBlank(orderSN)) {

		}

		activityOrderService.queryOrderDetail(userId, orderSN, "0");
		return null;
	}

	/**
	 * 总收益查询接口: 接口参数： userId bigInt 非空 20 彩之云用户账号
	 * 
	 * 返回： userId bigInt 非空 20 彩之云用户账号 totalInvestAmount 保留两位小数Decimal(18,2) 非空
	 * 累计投资金额 totalInterestAmount 保留两位小数Decimal(18,2) 非空 累计利息 withdrawalAmount
	 * 保留两位小数Decimal(18,2) 非空 可提取金额
	 * 
	 * @return json 字符
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:55:31
	 */
	public String userIncome() {
		String userId = this.request("userId");
		checkUserId(userId);
		userIncomeService.queryUserIncome(userId, UserType.COLOR_LIFE.name());
		return null;

	}

	/**
	 * 用户个人中心的首页， 手机版和PC版都由这个方法处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String homeInit() throws Exception {
        AccountUserDo user = (AccountUserDo) session().getAttribute("user");
        PersonDo personDo = personService.getByUserId(user.getId());

        request().setAttribute("usrCustId", user.getUsrCustId());// 汇付会员编号
        request().setAttribute("userId", user.getId());// 会员编号

		request().setAttribute("realName", personDo.getRealName());// 真实姓名
		request().setAttribute("idNo", personDo.getIdNo());// 身份证

		DesSecurityUtil des = new DesSecurityUtil();
		String userI = des.encrypt(user.getId().toString());
		request().setAttribute("userI", userI);
		String uri = getPath();
		request().setAttribute("url", uri);

		/**
		 * usableAmount,freezeSum ,dailyIncome,assetValue,recivedPrincipal
		 */
		UserIncomeDo userIncomeDo = userIncomeService.queryUserIncome(
				user.getId() + "", UserType.HEHENIAN.name());

		request().setAttribute("userIncomeDo", userIncomeDo);

		return "success";
	}
	
	/**
	 * 母亲节活动详情
	 */
	@RequireLogin
	public String motherDayInit(){
		if(getUser().getId() == null ){
			return "nologin";
		}
		return "success";
	}

}
