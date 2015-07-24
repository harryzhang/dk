package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sp2p.database.Dao;

/**
 * @ClassName: FinanceDao.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:15:29
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 还款处理层
 */
public class RepamentDao {

	public long addPreRepament(Connection conn, long id, String identify, String repayPeriod, double stillPrincipal, double stillInterest,
			double principalBalance, double interestBalance, double totalSum, double totalAmount, double mRate, String repayDate, int count, double consultFee)
			throws SQLException {

		Dao.Tables.t_pre_repayment t_pre_repayment = new Dao().new Tables().new t_pre_repayment();
		t_pre_repayment.borrowId.setValue(id);
		t_pre_repayment.identify.setValue(identify);
		t_pre_repayment.repayPeriod.setValue(repayPeriod);
		t_pre_repayment.stillPrincipal.setValue(stillPrincipal);
		t_pre_repayment.stillInterest.setValue(stillInterest);
		t_pre_repayment.principalBalance.setValue(principalBalance);
		t_pre_repayment.interestBalance.setValue(interestBalance);
		t_pre_repayment.totalSum.setValue(totalSum);
		t_pre_repayment.totalAmount.setValue(totalAmount);
		t_pre_repayment.mRate.setValue(mRate);
		t_pre_repayment.repayDate.setValue(repayDate);
		t_pre_repayment.sort.setValue(count);
		t_pre_repayment.consultFee.setValue(consultFee);
		return t_pre_repayment.insert(conn);
	}
}