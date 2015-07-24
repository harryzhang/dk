package com.hehenian.biz.component.notify;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.notify.dataobject.NotifyDo;

public interface INotifyComponent {
	
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
     * 根据接收人
     * 
     * @param reciever
     *            接收人
     * @return
     * @author: zhangyunhmf
     * @date: 2014年12月22日下午5:11:48
     */
    public List<NotifyDo> getMessageByReciever(String reciever);

    /**
     * 根据接收人取最后的发送的验证码
     * 
     * @param mobile
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月5日下午7:26:41
     */
    public List<NotifyDo> getLastIdentifyCode(String mobile);

}
