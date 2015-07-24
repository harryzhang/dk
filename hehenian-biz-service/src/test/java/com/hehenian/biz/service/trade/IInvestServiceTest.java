package com.hehenian.biz.service.trade;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.trade.IInvestService;
import com.hehenian.biz.service.BaseTestCase;

public class IInvestServiceTest extends BaseTestCase {
	
	@Autowired
	private IInvestService investService;
	
	@Test
	public void testSelectSuccessRecordPage(){
	    
	    PageDo page  = new PageDo();
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        //parameterMap.put("number", number);
        //parameterMap.put("publishTimeStart", publishTimeStart);
        //parameterMap.put("endTime", endTime);
        //parameterMap.put("investor", user.getId());
        //parameterMap.put("userName", userName);
        //新旧page类不同，需要转换
        page.setPageNum(1);
        page.setPageSize(10);
        page.setTotalNum(10);
        
	    investService.selectInvestSuccessRecordPage(parameterMap, page);
	}
	
	

}
