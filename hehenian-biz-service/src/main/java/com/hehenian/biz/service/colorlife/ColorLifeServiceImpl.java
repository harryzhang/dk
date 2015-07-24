/**
 * 
 */
package com.hehenian.biz.service.colorlife;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hehenian.biz.common.colorlife.ColorLifeBuyService;
import com.hehenian.biz.dal.colorlife.ColorLifeBuyInfoDao;

/**
 * 
 * @Description 
 * @Author chenzhwmf@hehenian.com
 * @Date:2015年4月14日
 * @Version 1.0.0
 */
@Service("colorLifeBuyService")
public class ColorLifeServiceImpl implements ColorLifeBuyService {
	
	@Resource
	private ColorLifeBuyInfoDao colorLifeBuyInfoDao;

	@Override
	public List<Map<String, Object>> listBuyInfo(Map<String, Object> conditon) {
		return this.colorLifeBuyInfoDao.listBuyInfo(conditon);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map saveBuyInfo(Map<String, Object> corolLifeBuyInfo) throws Exception {
		try {
			this.colorLifeBuyInfoDao.saveBuyInfo(corolLifeBuyInfo);
			return corolLifeBuyInfo;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateBuyInfo(long orderNo, int status, long verifierId)
			throws Exception {
		int result = -1;
		try {
			result = this.colorLifeBuyInfoDao.updateBuyInfo(orderNo, status, verifierId);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> weblistBuyInfo(Map<String, Object> conditon) {
		return this.colorLifeBuyInfoDao.weblistBuyInfo(conditon);
	}

	@Override
	public Map<String, Object> findById(Long orderId) {
		return this.colorLifeBuyInfoDao.findById(orderId);
	}

	@Override
	public long countBuyInfo(Map<String, Object> conditon) {
		return this.colorLifeBuyInfoDao.countBuyInfo(conditon);
	}

	@Override
	public BigDecimal queryInvestment(Integer userId) {
		return colorLifeBuyInfoDao.queryInvestment(userId);
	}

	@Override
	public BigDecimal queryInterest(Integer userId) {
		return colorLifeBuyInfoDao.queryInterest(userId);
	}

	@Override
	public BigDecimal queryInterested(Integer userId) {
		return colorLifeBuyInfoDao.queryInterested(userId);
	}

	@Override
	public int updateStatus(long orderNo, int status) {
		return this.colorLifeBuyInfoDao.updateStatus(orderNo, status);
	}
}
