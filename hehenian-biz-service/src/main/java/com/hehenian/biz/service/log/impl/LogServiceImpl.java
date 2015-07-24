package com.hehenian.biz.service.log.impl;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.hehenian.biz.common.exception.BusinessException;

@Component("logService")
public class LogServiceImpl {
	private final Logger logger = Logger.getLogger(this.getClass());

	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		try {
			stopWatch.start();
			Object retVal = joinPoint.proceed();
			stopWatch.stop();
			logger.info(getLogMessage(joinPoint, stopWatch.getTotalTimeMillis()));
			return retVal;
		} catch (BusinessException e) {
			stopWatch.stop();
			logger.info(getLogMessage(joinPoint, stopWatch.getTotalTimeMillis()));
			logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			stopWatch.stop();
			logger.info(getLogMessage(joinPoint, stopWatch.getTotalTimeMillis()));
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取日志消息
	 * 
	 * @param joinPoint
	 * @param totalTimeMillis
	 * @return
	 */
	private String getLogMessage(ProceedingJoinPoint joinPoint,
			Long totalTimeMillis) {
		StringBuffer logMessage = new StringBuffer();
		try {
			logMessage.append(joinPoint.getTarget().getClass().getName());
			logMessage.append(".");
			logMessage.append(joinPoint.getSignature().getName());
			logMessage.append("(");
			// append args
			Object[] args = joinPoint.getArgs();
			for (int i = 0; i < args.length; i++) {
				logMessage.append(args[i]).append(",");
			}
			if (args.length > 0) {
				logMessage.deleteCharAt(logMessage.length() - 1);
			}
			logMessage.append(")");
			logMessage.append(" execution time: ");
			logMessage.append(totalTimeMillis);
			logMessage.append(" ms");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return logMessage.toString();
	}

}
