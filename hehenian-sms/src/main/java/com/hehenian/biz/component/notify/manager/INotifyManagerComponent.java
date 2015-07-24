package com.hehenian.biz.component.notify.manager;

import java.util.List;

import com.hehenian.biz.common.notify.dataobject.NotifyManagerDo;

/**
 * 消息管理组件： 是否发送，重发次数，定时发送
 * @author zhangyunhmf
 *
 */

public interface INotifyManagerComponent {
	
	/**
	 * 新增
	 * @param notifyManagerDo
	 * @return
	 */
	public int addNotifyManager(NotifyManagerDo notifyManagerDo);
	/**
	 * 修改
	 * @param notifyManagerDo
	 * @return
	 */
	public int updateNotifyManager(NotifyManagerDo notifyManagerDo);
	
	/**
	 * 列出所有配置
	 * @return
	 */
	public List<NotifyManagerDo> listNotifyManager();
	
	/**
	 * 根据消息类型查询出消息发送的配置
	 * @param notifyType
	 * @return
	 */
	public NotifyManagerDo listNotifyManagerByNotifyType(String notifyType);

}
