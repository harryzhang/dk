package com.hehenian.biz.service.notify.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.notify.INotifyManagerService;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyManagerDo;
import com.hehenian.biz.component.notify.manager.INotifyManagerComponent;

@Service("notifyManagerService")
public class NotifyManagerServiceImpl implements INotifyManagerService {
	@Autowired
	private INotifyManagerComponent notifyManagerComponent;

	@Override
	public int addNotifyManager(NotifyManagerDo notifyManagerDo) {
		return notifyManagerComponent.addNotifyManager(notifyManagerDo);
	}

	@Override
	public int updateNotifyManager(NotifyManagerDo notifyManagerDo) {
		return notifyManagerComponent.updateNotifyManager(notifyManagerDo);
	}

	@Override
	public List<NotifyManagerDo> listNotifyManagerDo() {
		return notifyManagerComponent.listNotifyManager();
	}
	
	

}
