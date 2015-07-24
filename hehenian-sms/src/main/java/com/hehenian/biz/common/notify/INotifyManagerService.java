package com.hehenian.biz.common.notify;

import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyManagerDo;


/**
 * 消息发送的管理类
 * @author zhangyunhmf
 *
 */
public interface INotifyManagerService {
	/**
	 * 新增消息管理服务
	 * @param notifyManagerDo
	 * @return
	 */
	public int addNotifyManager(NotifyManagerDo notifyManagerDo);
	/**
	 * 修改消息管理服务
	 * @param notifyManagerDo
	 * @return
	 */
	public int updateNotifyManager(NotifyManagerDo notifyManagerDo);
	/**
	 * 列出所有已经配置的消息管理明细
	 * @return
	 */
	public List<NotifyManagerDo> listNotifyManagerDo();
	
}
