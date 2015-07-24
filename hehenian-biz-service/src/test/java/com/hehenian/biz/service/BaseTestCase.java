package com.hehenian.biz.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = {
        "classpath:/spring/hehenian-redis.xml" })
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	static {
		System.setProperty("catalina.home",
				"D:/tomcat7/apache-tomcat-7.0.55");
	}
}
