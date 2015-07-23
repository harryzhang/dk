/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.service.colorlife
 * @Title: IUserIncome.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月29日 下午5:26:29
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.userhome;

import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;

/**
 * 
 * @author: zhangyunhmf
 * @date 2014年10月29日 下午5:26:29
 */
public interface IUserIncomeService {

	/**
	 * 查询用户总收益
	 * 
	 * @param userId
	 *            客户用户ID
	 * @param userType
	 *            用户类型
	 * @return JSON 格式字符串
	 * @author: zhangyunhmf
	 * @date: 2014年10月29日下午5:27:39
	 */
	UserIncomeDo queryUserIncome(String userId, String userType);

}
