/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj.impl
 * @Title: LotteryDaoImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午3:09:49
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import com.hehenian.biz.common.wygj.dataobject.LotteryInfo;
import com.hehenian.biz.dal.wygj.ILotteryDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;

public class LotteryDaoImpl extends AbstractBaseDaoImpl<LotteryInfo> implements ILotteryDao {
	
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;
	
	private static RowMapper<LotteryInfo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(LotteryInfo.class);

	@Override
	public int getLotteryNumber(Integer userId) {
		int number = 0;
		String sql = "select sum(a.trade_amount) as amount from td_fund_trade as a " +
				" inner join td_product_rate as b on a.rate_id = b.product_rate_id " +
				"where a.trade_time>20150515 and a.trade_time<20150531 and a.rate_id>=5 and a.rate_id<=15 and b.period>=6 and a.user_id="+userId;
		try {
			List<Double> amount = queryDouble(sp2pJdbcTemplate,sql);
			if(CollectionUtils.isNotEmpty(amount)){
				Double d = amount.get(0)/1000;
				number = d.intValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return number;
	}
	
	@Override
	public int addLotteryInfo(LotteryInfo info) {
		String sql = "insert into m_lottery_info(userId,userName,prizeName,createTime)values(?,?,?,now())";
		return sp2pJdbcTemplate.update(sql, info.getUserId(),info.getUserName(),info.getPrizeName());
	}
	
	@Override
	public int countLotteryByUser(Integer userId){
		String sql = "select count(id) from m_lottery_info where userId =?";
		try {
			return queryCount(sp2pJdbcTemplate, sql, new Integer[]{userId});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	@Override
	public List<LotteryInfo> queryTopLotteryList() {
		String sql = "select * from m_lottery_info order by createTime desc limit 20";
		try {
			return queryList(sp2pJdbcTemplate, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<LotteryInfo>();
	}
	
	public int updateUserBalance(Integer userId,BigDecimal amount){
		String sql = "UPDATE td_fund_user_account SET balance_amount = balance_amount + ?,total_recharge_amount = total_recharge_amount + ?,update_time=now() WHERE user_id = ?";
		return sp2pJdbcTemplate.update(sql, amount,amount,userId);
	}
	
	public int insertFundAccountLog(Map<String,Object> params){
		String sql = " insert into td_fund_account_log(log_type,trade_amount,user_id,user_account_id,remark,status,trade_time)" +
				"values(?,?,?,?,?,?,now())";
		return sp2pJdbcTemplate.update(sql,params.get("log_type"),params.get("trade_amount"),params.get("user_id"),params.get("user_account_id"),params.get("remark"),1);
	}
	
	public Map<String,Object> getFundUserAccountById(Integer userId){
		String sql ="select user_account_id,user_id from td_fund_user_account where user_id=? limit 1";
		return sp2pJdbcTemplate.queryForMap(sql, userId);
	}
	
	

	@Override
	public RowMapper<LotteryInfo> getRowMapper() {
		return rowMapper;
	}



}
