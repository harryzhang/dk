package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.component.trade.IBorrowComponent;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.dal.trade.IBorrowDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.service.BaseTestCase;


/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class BorrowComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IBorrowComponent  borrowComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	    Long id = new Long(1);
	  BorrowDo borrowDo = borrowComponent.getById(id);
	  Assert.notNull(borrowDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectBorrow(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,1);
		List<BorrowDo> list = borrowComponent.selectBorrow(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateBorrowById(){
		BorrowDo  borrowDo =  borrowComponent.getById(3l);
	//columns START
	   //borrowDo.setId(random.nextLong());
	   borrowDo.setBorrowTitle("BorrowTitle");	   
	   borrowDo.setBorrowStatus(random.nextInt());
	   borrowDo.setImgPath("ImgPath");	   
	   borrowDo.setBorrowWay(random.nextInt());
	   borrowDo.setBorrowInfo("BorrowInfo");	   
	   borrowDo.setDeadline(random.nextInt());
	   borrowDo.setPaymentMode(random.nextInt());
	   borrowDo.setBorrowAmount(random.nextDouble());
	   borrowDo.setAnnualRate(random.nextDouble());
	   borrowDo.setMinTenderedSum(random.nextDouble());
	   borrowDo.setMaxTenderedSum(random.nextDouble());
	   borrowDo.setRaiseTerm(random.nextInt());
	   borrowDo.setDetail("Detail");	   
	   borrowDo.setVisitors(random.nextInt());
	   borrowDo.setRemainTimeStart(new java.util.Date());
	   borrowDo.setTradeType(random.nextInt());
	   borrowDo.setAuditOpinion("AuditOpinion");	   
	   borrowDo.setPublisher(random.nextLong());
	   borrowDo.setExcitationType(random.nextInt());
	   borrowDo.setExcitationSum(random.nextDouble());
	   borrowDo.setExcitationMode(random.nextInt());
	   borrowDo.setHasInvestAmount(random.nextDouble());
	   borrowDo.setInvestNum(random.nextInt());
	   borrowDo.setPurpose(random.nextInt());
	   borrowDo.setHasPwd(random.nextInt());
	   borrowDo.setInvestPwd("InvestPwd");	   
	   borrowDo.setPublishTime(new java.util.Date());
	   borrowDo.setPublishIp("PublishIp");	   
	   borrowDo.setRemainTimeEnd(new java.util.Date());
	   borrowDo.setAuditTime(new java.util.Date());
	   borrowDo.setHasDeadline(random.nextInt());
	   borrowDo.setIsAutoBid(random.nextInt());
	   borrowDo.setManageFee(random.nextDouble());
	   borrowDo.setIsDayThe(random.nextInt());
	   borrowDo.setAutoBidEnableTime(new java.util.Date());
	   borrowDo.setVersion(random.nextInt());
	   borrowDo.setFrozenMargin(random.nextDouble());
	   borrowDo.setSmallestFlowUnit(random.nextDouble());
	   borrowDo.setCirculationNumber(random.nextInt());
	   borrowDo.setHasCirculationNumber(random.nextInt());
	   borrowDo.setNidLog("NidLog");	   
	   borrowDo.setSort(random.nextInt());
	   borrowDo.setFeestate("Feestate");	   
	   borrowDo.setFeelog("Feelog");	   
	   borrowDo.setBusinessDetail("BusinessDetail");	   
	   borrowDo.setAssets("Assets");	   
	   borrowDo.setMoneyPurposes("MoneyPurposes");	   
	   borrowDo.setCirculationMode(random.nextInt());
	   borrowDo.setCirculationStatus(random.nextInt());
	   borrowDo.setUndertaker(random.nextLong());
	   borrowDo.setBorrowShow(random.nextInt());
	   borrowDo.setHasRepoNumber(random.nextInt());
	   borrowDo.setAgent("Agent");	   
	   borrowDo.setCounterAgent("CounterAgent");	   
	   borrowDo.setAmountScale(random.nextDouble());
	   borrowDo.setWindControl("WindControl");	   
	   borrowDo.setFirstTrial("FirstTrial");	   
	   borrowDo.setRecheck("Recheck");	   
	   borrowDo.setNumber("Number");	   
	   borrowDo.setIsTimes(random.nextInt());
	   borrowDo.setMaxInvest(random.nextInt());
	   borrowDo.setGuaranteeId(random.nextInt());
	   borrowDo.setLoansOk(random.nextInt());
	   borrowDo.setUnfreeOk(random.nextInt());
	   borrowDo.setBorrowadvisory("Borrowadvisory");	   
	   borrowDo.setAdvisorybranch("Advisorybranch");	   
	   borrowDo.setBorrowGroup(random.nextInt());
	//columns END
		int result = borrowComponent.updateBorrowById(borrowDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddBorrow(){
	    BorrowDo  borrowDo =  new BorrowDo();
	//columns START
	   //borrowDo.setId(random.nextLong());
	   borrowDo.setBorrowTitle("BorrowTitle");	   
	   borrowDo.setBorrowStatus(random.nextInt());
	   borrowDo.setImgPath("ImgPath");	   
	   borrowDo.setBorrowWay(random.nextInt());
	   borrowDo.setBorrowInfo("BorrowInfo");	   
	   borrowDo.setDeadline(random.nextInt());
	   borrowDo.setPaymentMode(random.nextInt());
	   borrowDo.setBorrowAmount(random.nextDouble());
	   borrowDo.setAnnualRate(random.nextDouble());
	   borrowDo.setMinTenderedSum(random.nextDouble());
	   borrowDo.setMaxTenderedSum(random.nextDouble());
	   borrowDo.setRaiseTerm(random.nextInt());
	   borrowDo.setDetail("Detail");	   
	   borrowDo.setVisitors(random.nextInt());
	   borrowDo.setRemainTimeStart(new java.util.Date());
	   borrowDo.setTradeType(random.nextInt());
	   borrowDo.setAuditOpinion("AuditOpinion");	   
	   borrowDo.setPublisher(random.nextLong());
	   borrowDo.setExcitationType(random.nextInt());
	   borrowDo.setExcitationSum(random.nextDouble());
	   borrowDo.setExcitationMode(random.nextInt());
	   borrowDo.setHasInvestAmount(random.nextDouble());
	   borrowDo.setInvestNum(random.nextInt());
	   borrowDo.setPurpose(random.nextInt());
	   borrowDo.setHasPwd(random.nextInt());
	   borrowDo.setInvestPwd("InvestPwd");	   
	   borrowDo.setPublishTime(new java.util.Date());
	   borrowDo.setPublishIp("PublishIp");	   
	   borrowDo.setRemainTimeEnd(new java.util.Date());
	   borrowDo.setAuditTime(new java.util.Date());
	   borrowDo.setHasDeadline(random.nextInt());
	   borrowDo.setIsAutoBid(random.nextInt());
	   borrowDo.setManageFee(random.nextDouble());
	   borrowDo.setIsDayThe(random.nextInt());
	   borrowDo.setAutoBidEnableTime(new java.util.Date());
	   borrowDo.setVersion(random.nextInt());
	   borrowDo.setFrozenMargin(random.nextDouble());
	   borrowDo.setSmallestFlowUnit(random.nextDouble());
	   borrowDo.setCirculationNumber(random.nextInt());
	   borrowDo.setHasCirculationNumber(random.nextInt());
	   borrowDo.setNidLog("NidLog");	   
	   borrowDo.setSort(random.nextInt());
	   borrowDo.setFeestate("Feestate");	   
	   borrowDo.setFeelog("Feelog");	   
	   borrowDo.setBusinessDetail("BusinessDetail");	   
	   borrowDo.setAssets("Assets");	   
	   borrowDo.setMoneyPurposes("MoneyPurposes");	   
	   borrowDo.setCirculationMode(random.nextInt());
	   borrowDo.setCirculationStatus(random.nextInt());
	   borrowDo.setUndertaker(random.nextLong());
	   borrowDo.setBorrowShow(random.nextInt());
	   borrowDo.setHasRepoNumber(random.nextInt());
	   borrowDo.setAgent("Agent");	   
	   borrowDo.setCounterAgent("CounterAgent");	   
	   borrowDo.setAmountScale(random.nextDouble());
	   borrowDo.setWindControl("WindControl");	   
	   borrowDo.setFirstTrial("FirstTrial");	   
	   borrowDo.setRecheck("Recheck");	   
	   borrowDo.setNumber("Number");	   
	   borrowDo.setIsTimes(random.nextInt());
	   borrowDo.setMaxInvest(random.nextInt());
	   borrowDo.setGuaranteeId(random.nextInt());
	   borrowDo.setLoansOk(random.nextInt());
	   borrowDo.setUnfreeOk(random.nextInt());
	   borrowDo.setBorrowadvisory("Borrowadvisory");	   
	   borrowDo.setAdvisorybranch("Advisorybranch");	   
	   borrowDo.setBorrowGroup(random.nextInt());
	//columns END
		borrowComponent.addBorrow(borrowDo);
		Assert.state(borrowDo.getId()>0);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
		int result = borrowComponent.deleteById(2l);
		Assert.state(result>0);
	}

}

