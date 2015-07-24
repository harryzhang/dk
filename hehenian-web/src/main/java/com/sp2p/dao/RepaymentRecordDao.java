package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.sp2p.database.Dao;

/**   
 * @param:还款记录表
 * @ClassName: RepaymentRecordDao.java
 * @Author: gang.lv
 * @Date: 2013-7-23 下午04:05:56
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 
 */
public class RepaymentRecordDao {
	
    /**
     * 
     * @MethodName: addRepayMentRecord  
     * @Param: RepaymentRecordDao  
     * @Author: gang.lv
     * @Date: 2013-7-23 下午04:11:49
     * @Return:    
     * @Descb: 添加还款记录
     * @Throws:
     */
	public long addRepayMentRecord(Connection conn,long repayId,double repayAmount,long oporator,String remark) throws SQLException{
		Dao.Tables.t_repayment_record t_repayment_record = new Dao().new Tables().new t_repayment_record();
		t_repayment_record.repayId.setValue(repayId);
		t_repayment_record.repayAmount.setValue(repayAmount);
		t_repayment_record.oporator.setValue(oporator);
		t_repayment_record.remark.setValue(remark);
		t_repayment_record.createTime.setValue(new Date());
		return t_repayment_record.insert(conn);
	}
	
}
