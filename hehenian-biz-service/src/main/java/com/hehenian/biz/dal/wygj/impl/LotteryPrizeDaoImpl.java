/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj.impl
 * @Title: LotteryPrizeDaoImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午5:30:24
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.hehenian.biz.common.wygj.dataobject.LotteryPrize;
import com.hehenian.biz.dal.wygj.ILotteryPrizeDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class LotteryPrizeDaoImpl extends AbstractBaseDaoImpl<LotteryPrize> implements ILotteryPrizeDao{
	
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;
	
	private static RowMapper<LotteryPrize> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(LotteryPrize.class);

	@Override
	public LotteryPrize getLotteryPrize(LotteryPrize prize) {
		String sql = "select * from m_lottery_prize where status=1";
		if(prize.getId()!=null){
			sql+=" and id = "+prize.getId();
		}
		if(StringUtils.isNotBlank(prize.getPrizeName())){
			sql+=" and prizeName = "+prize.getPrizeName();
		}
		sql+=" limit 1";
		try {
			return queryObject(sp2pJdbcTemplate, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LotteryPrize> queryLotteryPrize() {
		String sql = "select * from m_lottery_prize where status=1";
		try {
			return queryList(sp2pJdbcTemplate, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<LotteryPrize>();
	}

	@Override
	public int updateLotteryPrize(LotteryPrize prize) {
		String sql = "update m_lottery_prize set yetQuantity=?,dayQuantity=? where id=?";
		return sp2pJdbcTemplate.update(sql, prize.getYetQuantity(),prize.getDayQuantity(),prize.getId());
	}
	
	public int updateDayQuantity(Integer id,Integer quantity) {
		String sql = "update m_lottery_prize set dayQuantity=? where id=?";
		return sp2pJdbcTemplate.update(sql, quantity,id);
	}
	

	@Override
	public RowMapper<LotteryPrize> getRowMapper() {
		return rowMapper;
	}

}
