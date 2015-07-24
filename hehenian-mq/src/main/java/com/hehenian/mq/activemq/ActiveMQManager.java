/**
 * @fileName ActiveManager.java
 * @auther liminglmf
 * @createDate 2015年7月8日
 */
package com.hehenian.mq.activemq;


import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liminglmf
 *
 */
public class ActiveMQManager {
	
	private final static Logger logger = LoggerFactory.getLogger(ActiveMQManager.class); 
	
	private String host;//activem访问地址= "10.50.10.152"
	private String port;//端口号 = "61616"
	private String user;//用户名 = "admin"
	private String password;//密码= "admin"
	/*private String host= "10.50.10.152";//activem访问地址= "10.50.10.152"
	private String port= "61616";//端口号 = "61616"
	private String user= "admin";//用户名 = "admin"
	private String password= "admin";//密码= "admin"
*/	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	private static final DestinationType DESTINATION_TYPE = DestinationType.TOPIC;//目的地
	private static final String DESTINATION_NAME = "event";//目的地
	/**
	 * QUEUE-队列,TOPIC-主题
	 * @author liminglmf
	 *
	 */
	public enum DestinationType {
		QUEUE,TOPIC
    }
	
	/**
	 * 生产者-PROD,接收者-CONS
	 * @author liminglmf
	 *
	 */
	public enum PersonFactory {
		PROD,CONS
    }
	//private static Connection connection;
	
	/**
	 * 初始化aciveMQ连接
	 * @auther liminglmf
	 * @date 2015年7月8日
	 * @throws JMSException
	 */
	private Connection getConnection() throws JMSException{
		ActiveMQConnectionFactory  factory = new ActiveMQConnectionFactory("tcp://" + host.trim() + ":" + port.trim());
		Connection connection = factory.createConnection(
				user == null ? ActiveMQConnection.DEFAULT_USER:user.trim(),
						password == null ? ActiveMQConnection.DEFAULT_PASSWORD:password.trim());
		return connection;
		//connection.start();
	}
	
	/**
	 * 创建生产者
	 * @auther liminglmf
	 * @date 2015年7月8日
	 * @param Destination dest 目的地
	 * @return
	 * @throws JMSException 
	 */
	private static MessageProducer createProducer(Session session,DestinationType desTpye,String desName) throws JMSException  {
		Destination dest = createDestination(
				desTpye == null ? DESTINATION_TYPE:desTpye,
						desName == null ? DESTINATION_NAME:desName,session);
		MessageProducer producer = session.createProducer(dest);
	    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	    return producer;
	}
	
	
	/**
	 * 创建接收者
	 * @auther liminglmf
	 * @date 2015年7月8日
	 * @param Destination dest 目的地
	 * @return
	 * @throws JMSException 
	 */
	private MessageConsumer createConsumer(Session session,DestinationType desTpye,String desName) throws JMSException {
		Destination dest = createDestination(
				desTpye == null ? DESTINATION_TYPE:desTpye,
						desName == null ? DESTINATION_NAME:desName,session);
		MessageConsumer consumer = session.createConsumer(dest);
	    return consumer;
	}
	
	/**
	 * 创建会话
	 * @auther liminglmf
	 * @date 2015年7月9日
	 * @param connection
	 * @param desType
	 * @param perFactory
	 * @return
	 * @throws JMSException
	 */
	private static Session createSession(Connection connection,DestinationType desType,PersonFactory perFactory) throws JMSException{
		Session session;
		if(connection != null){
			if(perFactory.toString().equals("PROD")){
				if(desType.toString().equals("TOPIC")){
					session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
				}else if(desType.toString().equals("QUEUE")){
					session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
				}else{
					session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
				}
			}else{
				session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			}
		}else{
			logger.info("the connection is null......");
			return null;
		}
		return session;
	}
	
	/**
	 * 创建目的地
	 * @auther liminglmf
	 * @date 2015年7月8日
	 * @param desType
	 * @param desName
	 * @param session
	 * @return
	 * @throws JMSException
	 */
	private static Destination createDestination(DestinationType desType,String desName,Session session) throws JMSException {
		Destination dest = null;
		if(desType.toString().equals("TOPIC")){
			dest = new ActiveMQTopic(desName == null ? DESTINATION_NAME:desName);
		}else if(desType.toString().equals("QUEUE")){
			dest = session.createQueue(desName == null ? DESTINATION_NAME:desName);
		}else{
			dest = new ActiveMQTopic(desName == null ? DESTINATION_NAME:desName);
		}
		return dest;
	}
	
	
	/**
	 * 默认发送消息方法
	 * @auther liminglmf
	 * @date 2015年7月9日
	 * @param message
	 * @throws JMSException
	 */
	public void sendMessage(Serializable message) {
		Connection connection = null;
		Session session = null;
		try {
			connection = getConnection();
			connection.start();
			session = createSession(connection,DESTINATION_TYPE,PersonFactory.PROD);
			if(session != null){
				MessageProducer producer = createProducer(session,DESTINATION_TYPE,DESTINATION_NAME);
				ObjectMessage msg = session.createObjectMessage(message);
		        //msg.setIntProperty("id", 1);
		        System.out.println("send befor:"+msg);
		        producer.send(msg);
		        System.out.println("send after:"+msg);
		        if(DESTINATION_TYPE.toString().equals("QUEUE")){
		        	session.commit();
		        }
			}else{
				logger.info("the session is null......");
			}
			closed(session,connection);
		} catch (JMSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 默认发送消息方法
	 * @auther liminglmf
	 * @date 2015年7月9日
	 * @param message
	 * @throws JMSException
	 */
	public void sendMessage(String message) {
		Connection connection = null;
		Session session = null;
		try {
			connection = getConnection();
			connection.start();
			session = createSession(connection,DESTINATION_TYPE,PersonFactory.PROD);
			if(session != null){
				MessageProducer producer = createProducer(session,DESTINATION_TYPE,DESTINATION_NAME);
				TextMessage msg = session.createTextMessage(message);
		        //msg.setIntProperty("id", 1);
		        //System.out.println("send befor:"+msg);
		        producer.send(msg);
		        if(DESTINATION_TYPE.toString().equals("QUEUE")){
		        	session.commit();
		        }
		        System.out.println("send after:"+msg);
			}else{
				logger.info("the session is null......");
			}
			closed(session,connection);
		} catch (JMSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	/**
	 * 默认接收消息方法
	 * @auther liminglmf
	 * @date 2015年7月9日
	 * @throws JMSException
	 */
	public void getMessage(){
		Connection connection = null;
		Session session = null;
		try {
			connection = getConnection();
			connection.start();
			session = createSession(connection,DESTINATION_TYPE,PersonFactory.CONS);
			if(session != null){
				MessageConsumer consumer = createConsumer(session,DESTINATION_TYPE,DESTINATION_NAME);
				Message msg = null;
				while (true) {
					msg = consumer.receive();
					if (null != msg) {
						System.out.println("the receive is:" + msg);
						if( msg instanceof  TextMessage ) {
							logger.info("get msg detail:"+msg);
							String text = ((TextMessage) msg).getText();
							logger.info("text:"+text);
							//int count = msg.getIntProperty("id");
						}else if(msg instanceof ObjectMessage){
							Object object = ((ObjectMessage) msg).getObject();
							System.out.println("object:"+object);
						}else{
							logger.info("Unexpected message type: "+msg.getClass());
						}
					} else {
						break;
					}
				}
			}else{
				logger.info("the session is null......");
			}
			closed(session,connection);
		} catch (JMSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
	}
	
	/**
	 * 关闭会话
	 * @auther liminglmf
	 * @date 2015年7月10日
	 * @param session
	 * @param connection
	 */
	private void closed(Session session){
		try {
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != session){
					session.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭连接
	 * @auther liminglmf
	 * @date 2015年7月10日
	 * @param session
	 * @param connection
	 */
	private void closed(Connection connection){
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != connection){
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭会话和连接
	 * @auther liminglmf
	 * @date 2015年7月10日
	 * @param session
	 * @param connection
	 */
	private void closed(Session session,Connection connection){
		try {
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != session){
					session.close();
				}
				if(null != connection){
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		ActiveMQManager a = new ActiveMQManager();
		a.sendMessage("11111111111");
		a.sendMessage("22222222");
		a.sendMessage("33333333");
		a.sendMessage("4444444");
		
		/*JobDo job = new JobDo();
		job.setJobId(1111L);
		job.setCompanyName("我那个去");
		a.sendMessage(job);
		job.setJobId(2222L);
		job.setCompanyName("我那个去2222");
		a.sendMessage(job);*/
	}
}
