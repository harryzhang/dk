package com.hehenian.biz.component.notify.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.notify.dataobject.NotifyBusinessType;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.SMSNotifyDo;
import com.hehenian.biz.component.notify.sms.ISMSComponent;
import com.hehenian.biz.component.notify.sms.dao.ISMSDao;

@Component("smsComponent")
public class ISMSComponentImpl implements ISMSComponent {

	@Autowired
	private ISMSDao smsDao;
	
	@Override
	public NotifyDo getMessageById(int id) {
		return smsDao.getMessageById(id);
	}

	@Override
	public List<NotifyDo> listUnSendMessageList() {
		return smsDao.listUnSendMessageList();
	}

	@Override
	public int addMessage(NotifyDo msg) {
		return smsDao.addMessage(msg);
	}

	@Override
	public int updateMessage(NotifyDo msg) {
		return smsDao.updateMessage(msg);
	}

	@Override
	public int updateMessageFlag(String newSendFlag, int messageId) {
		return smsDao.updateMessageFlag(newSendFlag, messageId);
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
        NotifyDo queryNotifyDo = new SMSNotifyDo();
        queryNotifyDo.setRecievers(reciever);
        queryNotifyDo.setSendFlag(null);
        return smsDao.selectNotify(queryNotifyDo);
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
        NotifyDo queryNotifyDo = new SMSNotifyDo();
        queryNotifyDo.setRecievers(mobile);
        queryNotifyDo.setSendFlag(null);
        queryNotifyDo.setBusinessType(NotifyBusinessType.check.name());
        return smsDao.getLastIdentifyCode(queryNotifyDo);
    }
	
}
