package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.sp2p.database.Dao;

public class ColorLifeDao {

	public long addColorLife(Connection conn,
			Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_colorlife colorlife = new Dao().new Tables().new t_colorlife();
		colorlife.realName.setValue(paramMap.get("realName"));
		colorlife.age.setValue(paramMap.get("age"));
		colorlife.sex.setValue(paramMap.get("sex"));
		colorlife.maritalStatus.setValue(paramMap.get("maritalStatus"));
		colorlife.houseStatus.setValue(paramMap.get("houseStatus"));
		colorlife.highestEdu.setValue(paramMap.get("highestEdu"));
		colorlife.address.setValue(paramMap.get("address"));
		colorlife.houseWorth.setValue(paramMap.get("houseWorth"));
		colorlife.surplusMortgageYear.setValue(paramMap.get("surplusMortgageYear"));
		colorlife.hasCar.setValue(paramMap.get("hasCar"));
		colorlife.orgName.setValue(paramMap.get("orgName"));
		colorlife.companyType.setValue(paramMap.get("companyType"));
		colorlife.companyScale.setValue(paramMap.get("companyScale"));
		colorlife.job.setValue(paramMap.get("job"));
		colorlife.annualIncome.setValue(paramMap.get("annualIncome"));
		colorlife.companyPhone.setValue(paramMap.get("companyPhone"));
		colorlife.workYear.setValue(paramMap.get("workYear"));
		colorlife.companyEmail.setValue(paramMap.get("companyEmail"));
		colorlife.companyAddress.setValue(paramMap.get("companyAddress"));
		colorlife.idNo.setValue(paramMap.get("idNo"));
		return colorlife.insert(conn);
	}
}
