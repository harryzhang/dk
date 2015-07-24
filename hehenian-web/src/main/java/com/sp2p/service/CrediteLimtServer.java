package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;

public class CrediteLimtServer extends BaseService{
	public static Log log = LogFactory.getLog(CrediteLimtServer.class);
     /**
      * 
      * @param pageBean
      * @param status
      * @param userName
      * @param startDate
      * @param endDate
      * @throws SQLException
      * @throws DataException
      */
	public void queryOrderRechargeRecords(PageBean<Map<String,Object>> pageBean,Integer status,String userName,String startDate,String endDate) throws SQLException, DataException{
		//只要订单类型(orderType)是3的就是充值记录，不管是否成功都展示出来
		StringBuffer condition = new StringBuffer();
		condition.append(" AND orderType = 3");
		if(status!=null&&status>=-1){
			condition.append(" AND status = "+status);
		}
		if(StringUtils.isNotBlank(userName)){
			condition.append(" AND userName  LIKE CONCAT('%','"+StringEscapeUtils.escapeSql(userName.trim())+"','%')");
		}
		if(StringUtils.isNotBlank(startDate)){
			condition.append(" AND addDate >= DATE('"+StringEscapeUtils.escapeSql(startDate)+"')");
		}
		if(StringUtils.isNotBlank(endDate)){
			condition.append(" AND addDate <= DATE('"+StringEscapeUtils.escapeSql(endDate)+"')");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_order_recharge_records ", " * ", " order by id"+IConstants.SORT_TYPE_DESC, condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	
	
}
