package com.hehenian.web;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = {
		"file:../hehenian-web/src/main/resources/hehenian/hehenian-service.xml",
		"file:../hehenian-web/src/main/resources/beans_all.xml" })
public class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {

}
