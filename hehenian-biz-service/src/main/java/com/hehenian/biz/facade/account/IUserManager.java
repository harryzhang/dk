package com.hehenian.biz.facade.account;

import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

/**
 * 银行或者第三方支付用户管理接口
 * 
 * @author liuzgmf
 *
 */
public interface IUserManager {
	/**
	 * 用户开户
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter userRegister(InParameter inParameter);

	/**
	 * 后台用户开户
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter bgRegister(InParameter inParameter);

	/**
	 * 用户绑卡
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter userBindCard(InParameter inParameter);

	/**
	 * 后台接口绑卡
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter bgBindCard(InParameter inParameter);

	/**
	 * 用户登录
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter userLogin(InParameter inParameter);

	/**
	 * 账户信息修改
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter acctModify(InParameter inParameter);

	/**
	 * 担保类型企业开户接口
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter corpRegister(InParameter inParameter);

	/**
	 * 删除银行卡接口
	 * 
	 * @param inParameter
	 *            输入参数
	 * @return
	 */
	OutParameter delCard(InParameter inParameter);
}
