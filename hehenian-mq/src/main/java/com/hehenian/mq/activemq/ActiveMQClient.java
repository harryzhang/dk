/**
 * @fileName ActiveMQClient.java
 * @auther liminglmf
 * @createDate 2015年7月9日
 */
package com.hehenian.mq.activemq;

import java.io.Serializable;


/**
 * @author liminglmf
 *
 */
public class ActiveMQClient {
	
	private ActiveMQManager activeMQManager;
	
	
	/**
	 * activeSend发送消息
	 * @auther liminglmf
	 * @date 2015年7月9日
	 * @param message
	 */
	public void sendMessage(Serializable message){
		activeMQManager.sendMessage(message);
	}
	
	
	/**
	 * activeSend发送消息
	 * @auther liminglmf
	 * @date 2015年7月9日
	 * @param message
	 */
	public void sendMessage(String message){
		activeMQManager.sendMessage(message);
	}
	
	/**
	 * activeGet得到消息
	 * @auther liminglmf
	 * @date 2015年7月9日
	 * @return
	 */
	public void getMessage(){
		activeMQManager.getMessage();
	}

	public ActiveMQManager getActiveMQManager() {
		return activeMQManager;
	}

	public void setActiveMQManager(ActiveMQManager activeMQManager) {
		this.activeMQManager = activeMQManager;
	}
	
	 
}
