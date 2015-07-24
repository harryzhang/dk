package com.hehenian.biz.component.notify.mail.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.notify.dataobject.MailNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyBusinessType;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.component.notify.mail.IMailNotifyComponent;
import com.hehenian.biz.component.notify.mail.dao.IMailNotifyDao;

@Component("mailNotifyComponent")
public class MailNotifyComponentImpl implements IMailNotifyComponent{
	
	@Autowired
	private IMailNotifyDao mailNotifyDao;
	
	@Override
	public NotifyDo getMessageById(int id) {
		return mailNotifyDao.getMessageById(id);
	}

	@Override
	public List<NotifyDo> listUnSendMessageList() {
		return mailNotifyDao.listUnSendMessageList();
	}

	@Override
	public int addMessage(NotifyDo msg) {
		return mailNotifyDao.addMessage(msg);
	}

	@Override
	public int updateMessage(NotifyDo msg) {
		return mailNotifyDao.updateMessage(msg);
	}

	@Override
	public int updateMessageFlag(String newSendFlag, int messageId) {
		return mailNotifyDao.updateMessageFlag(newSendFlag, messageId);
	}

    /*
     * (no-Javadoc) <p>Title: getMessageByReciever</p> <p>Description: </p>
     * 
     * @param reciever
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.notify.INotifyComponent#getMessageByReciever
     * (java.lang.String)
     */
    @Override
    public List<NotifyDo> getMessageByReciever(String reciever) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (no-Javadoc) <p>Title: getLastIdentifyCode</p> <p>Description: </p>
     * 
     * @param mobile
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.notify.INotifyComponent#getLastIdentifyCode
     * (java.lang.String)
     */
    @Override
    public List<NotifyDo> getLastIdentifyCode(String mobile) {
        NotifyDo queryNotifyDo = new MailNotifyDo();
        queryNotifyDo.setRecievers(mobile);
        queryNotifyDo.setSendFlag(null);
        queryNotifyDo.setBusinessType(NotifyBusinessType.check.name());
        return mailNotifyDao.getLastIdentifyCode(queryNotifyDo);
    }
	
}
