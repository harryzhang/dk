package com.hehenian.biz.component.notify.manager.dao;

import java.util.List;

import com.hehenian.biz.common.notify.dataobject.NotifyManagerDo;

/**
 * 消息管理DAO
 * @author zhangyunhmf
 *
 */
public interface INotifyManagerDao {
	
	/**
	 * 新增
	 * @param notifyManagerDo
	 * @return
	 */
	public int addNotifyManager(NotifyManagerDo notifyManagerDo);
	
	/**
	 * 根据ID获取消息管理对象
	 * @param id
	 * @return
	 */
	public NotifyManagerDo getNotifyManagerById(int id);	
	
	/**
	 * 修改
	 * @param notifyManagerDo
	 * @return
	 */
	public int updateNotifyManager(NotifyManagerDo notifyManagerDo);
	
	/**
	 * 根据ID 删除
	 * @param id
	 */
	public void deleteById(int id);
	
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
