package com.hehenian.biz.service.bid.color.impl;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.bid.IAutoBidAmountService;
import com.hehenian.biz.service.BaseTestCase;

public class ColorlifeBidAmountServiceImplTest extends BaseTestCase {
	@Autowired
	IAutoBidAmountService colorlifeBidAmountService;

	@Test
	public void testGetAutoBidAmount() {
        long userId = 8000123;
		Date bidDate = new Date();
		double d = colorlifeBidAmountService.getAutoBidAmount(userId, bidDate);
		System.out.println(d);
	}

}
