package com.hehenian.biz.component.notify;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.notify.dataobject.NotifyDo;

public interface INotifyDao {
	
	/**
	 * 根据id获取消息
	 * @param id
	 * @return
	 */
	public NotifyDo getMessageById(int id);
	/**
	 * 查询还未发送成功的消息
	 * @return
	 */
	public List<NotifyDo> listUnSendMessageList();
	
	/**
	 * 增加新消息
	 * @param msg
	 * @return
	 */
	public int addMessage(NotifyDo msg);
	/**
	 * 修改消息
	 * @param msg
	 * @return
	 */
	public int updateMessage(NotifyDo msg);
	
	/**
	 * 根据id更新消息发送状态
	 * @param newSendFlag
	 * @param messageId
	 * @return
	 */
	public int updateMessageFlag(@Param(value="newSendFlag") String newSendFlag,  @Param(value="messageId") int messageId);

    /**
     * 查询
     * 
     * @param queryNotifyDo
     * @return
     * @author: zhangyunhmf
     * @date: 2014年12月31日下午2:46:39
     */
    public List<NotifyDo> selectNotify(NotifyDo queryNotifyDo);

    /**
     * @param queryNotifyDo
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月5日下午7:37:05
     */
    List<NotifyDo> getLastIdentifyCode(NotifyDo queryNotifyDo);

}
