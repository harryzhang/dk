package com.hehenian.biz.service.loan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.ICommonService;
import com.hehenian.biz.common.loan.dataobject.ConsultVO;
import com.hehenian.biz.common.loan.dataobject.ContactVO;
import com.hehenian.biz.common.task.ConsultCreateTask;
import com.hehenian.biz.common.task.ContactCreateTask;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.common.redis.SpringRedisCacheService;

@Service("commonService")
public class CommonService implements ICommonService{

	@Autowired
	private  SpringRedisCacheService localCacheService ;
	@Override
	public String generateOrderCode(String pri) {
		String date = DateUtils.getCurrentDateTime("yyMMdd");
        String orderCodePri = pri+date;
		String key = "orderCode_"+pri+date;
		long cacheId = localCacheService.increase(key);
		String code= StringUtil.fullString(cacheId,5);
		return orderCodePri+code;
	}
	@Autowired
	@Qualifier("taskExecutor")
	private org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor executor;

	@Override
	public void generateContactPdf(ContactVO contactVO) {
		Thread t = executor.createThread(new ContactCreateTask(contactVO));
		executor.execute(t, 5000);
		//executor.execute(new ContactCreateTask(contactVO));
	}
	
	@Override
	public void generateConsultPdf(ConsultVO consultVO ) {
		Thread t = executor.createThread(new ConsultCreateTask(consultVO));
		executor.execute(t, 5000);
		//executor.execute(new ConsultCreateTask(consultVO));
	}

	
}
