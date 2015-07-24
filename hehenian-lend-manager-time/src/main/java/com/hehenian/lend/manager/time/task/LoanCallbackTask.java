/**
 * @fileName LoanCallbackTask.java
 * @auther liminglmf
 * @createDate 2015年6月9日
 */
package com.hehenian.lend.manager.time.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.ILoanContractService;
import com.hehenian.biz.common.util.DateUtils;

/**
 * 取放款和还款计划的数据
 * 
 * @author liminglmf
 * 
 */
@Component
public class LoanCallbackTask {

	private Logger logger = Logger.getLogger(LoanCallbackTask.class);
	
	@Autowired
	private ILoanContractService loanContractService;

	@Scheduled(cron = "0  0 1 * * ?")
	public void work() {
		logger.info("===================do work begin===============");
		// 得到前面一天的时间
		String loanDate = DateUtils.getEveryTime("yyyy/MM/dd", new Date(), -1);
		loanContractService.fankuanProcess(loanDate);
		logger.info("===================do work end ===============");
	}

}