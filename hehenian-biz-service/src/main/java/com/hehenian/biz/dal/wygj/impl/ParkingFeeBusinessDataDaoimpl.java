/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: ParkingFeeDaoimpl.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-27 下午4:56:57
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;
import com.hehenian.biz.dal.wygj.IParkingFeeBusinessDataDao;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;
public class ParkingFeeBusinessDataDaoimpl extends AbstractBaseDaoImpl<ParkingFeeDo> implements IParkingFeeBusinessDataDao{
	@Resource
	private JdbcTemplate sp2pJdbcTemplate;
	@Resource
	private NamedParameterJdbcTemplate sp2pNameJdbcTemplate;

	private static RowMapper<ParkingFeeDo> rowMapper = ParameterizedBeanPropertyRowMapper
			.newInstance(ParkingFeeDo.class);

	/**
	 * 
	 * @Description: 预留权限查询
	 * @param authorityCode
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-28 上午10:21:12
	 */
	public Map<String,Object> listParkingFeeBusiness(Map<String,Object> map) {
		String sql = " SELECT a.*,b.period,b.rate,b.money_scope,c.realName user_name,f.* FROM td_fund_trade a INNER JOIN td_product_rate b ON a.rate_id = b.product_rate_id INNER JOIN t_person c ON  a.user_id = c.userId INNER join t_parking_property_detailinfo d on a.trade_id = d.trade_id INNER JOIN t_parking_detailinfo f on d.id = f.id;";
		try {
			this.queryForMap(sp2pNameJdbcTemplate, sql, map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 
	 * @Description: 预留权限查询
	 * @param authorityCode
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-28 上午10:21:12
	 */
	public List<Map<String,Object>> exportParkingFeeBusiness(Object[] object) {
		String sql = " SELECT a.*,b.period,b.rate,b.money_scope,c.realName user_name,f.* FROM td_fund_trade a INNER JOIN td_product_rate b ON a.rate_id = b.product_rate_id INNER JOIN t_person c ON  a.user_id = c.userId INNER join t_parking_property_detailinfo d on a.trade_id = d.trade_id INNER JOIN t_parking_detailinfo f on d.id = f.id;";
		try {
			return this.queryForMap(sp2pJdbcTemplate, sql, object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public RowMapper<ParkingFeeDo> getRowMapper() {
		return null;
	}

}
