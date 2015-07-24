package com.hehenian.biz.component.notify.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.notify.dataobject.NotifyManagerDo;
import com.hehenian.biz.component.notify.manager.INotifyManagerComponent;
import com.hehenian.biz.component.notify.manager.dao.INotifyManagerDao;

@Component("notifyManagerComponent")
public class NotifyManagerComponentImpl implements INotifyManagerComponent {
	@Autowired
	private INotifyManagerDao notifyManagerDao;

	@Override
	public int addNotifyManager(NotifyManagerDo notifyManagerDo) {
		return notifyManagerDao.addNotifyManager(notifyManagerDo);
	}

	@Override
	public int updateNotifyManager(NotifyManagerDo notifyManagerDo) {
		return notifyManagerDao.updateNotifyManager(notifyManagerDo);
	}

	@Override
	public List<NotifyManagerDo> listNotifyManager() {
		return notifyManagerDao.listNotifyManager();
	}
	
	/**
	 * 根据消息类型查询出消息发送的配置
	 * @param notifyType
	 * @return
	 */
	public NotifyManagerDo listNotifyManagerByNotifyType(String notifyType){
		return notifyManagerDao.listNotifyManagerByNotifyType(notifyType);
	}
	
}
