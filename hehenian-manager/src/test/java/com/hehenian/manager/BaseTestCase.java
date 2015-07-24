package com.hehenian.manager;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/mvc-dispatcher-servlet.xml",
		"file:src/main/resources/spring/spring.xml",
		"file:src/main/resources/spring/spring-hehenian-service.xml",
		"file:src/main/resources/spring/spring-jdbc.xml" })
public class BaseTestCase extends AbstractJUnit4SpringContextTests  {
	
	static {
		System.setProperty("catalina.home",
				"D:/apache-tomcat-7.0.55");
	}
	
	protected final static Logger LOG = Logger.getLogger("BaseTestCase");
}
