package com.hehenian.biz.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = {
		"file:../hehenian-biz-service/src/main/resources/spring/hehenian-dao.xml",
		"file:../hehenian-biz-service/src/main/resources/spring/hehenian-support.xml",
		"file:../hehenian-biz-service/src/main/resources/spring/hehenian-task.xml",
        "classpath*:/spring/hehenian-sms-*.xml",
		"file:../hehenian-biz-service/src/main/resources/spring/remoting-servlet.xml" })
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	static {
		System.setProperty("catalina.home",
				"D:/dev/apache-tomcat-7.0.55");
	}
}
