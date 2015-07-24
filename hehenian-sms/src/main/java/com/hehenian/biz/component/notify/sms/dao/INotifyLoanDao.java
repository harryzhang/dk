package com.hehenian.biz.component.notify.sms.dao;

import java.util.List;

import com.hehenian.biz.common.notify.dataobject.LoanNotifyDo;

/**
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年7月2日 下午3:40:03
 * @Project hehenian-sms
 * @Package com.hehenian.biz.component.notify.sms.dao
 * @File ILoanDao.java
 */
public interface INotifyLoanDao {

	/**
	 * 增加新消息
	 * 
	 * @param msg
	 * @return
	 */
	public int addMessage(LoanNotifyDo msg);

	/**
	 * 修改消息
	 * 
	 * @param msg
	 * @return
	 */
	public int updateMessage(LoanNotifyDo msg);

	/**
	 * 查询
	 * 
	 * @param queryNotifyDo
	 * @return
	 * @author: zhangyunhmf
	 * @date: 2014年12月31日下午2:46:39
	 */
	public List<LoanNotifyDo> selectNotify(LoanNotifyDo queryNotifyDo);
}
