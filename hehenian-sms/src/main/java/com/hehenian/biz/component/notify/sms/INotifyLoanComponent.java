package com.hehenian.biz.component.notify.sms;

import java.util.List;

import com.hehenian.biz.common.notify.dataobject.LoanNotifyDo;

/**
 * @Description 融资端站内通知
 * @author huangzl QQ: 272950754
 * @date 2015年7月1日 下午6:06:48
 * @Project hehenian-sms
 * @Package com.hehenian.biz.component.notify.sms
 * @File INotifyManagerComponent.java
 */
public interface INotifyLoanComponent {
	/**
	 * 增加新消息
	 * 
	 * @param msg
	 * @return
	 * @date: 2015年7月1日下午7:37:05
	 */
	public int addMessage(LoanNotifyDo msg);

	/**
	 * 修改消息
	 * 
	 * @param msg
	 * @return
	 * @date: 2015年7月1日下午7:37:05
	 */
	public int updateMessage(LoanNotifyDo msg);

	/**
	 * 查询
	 * 
	 * @param queryNotifyDo
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年7月1日下午7:37:05
	 */
	public List<LoanNotifyDo> selectNotify(LoanNotifyDo queryNotifyDo);

}
