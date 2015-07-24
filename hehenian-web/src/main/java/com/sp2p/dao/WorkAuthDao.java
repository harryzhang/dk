package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.util.DBReflectUtil;

public class WorkAuthDao {

	/**
	 * 添加记录
	 * 
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public Long addWorkAuth(Connection conn, Map<String, String> paramMap) throws SQLException {

		// personMap = new HashMap<String, String>();
		// personMap.put("orgName", datas[i][16]); //公司名称
		// personMap.put("companyAddress", datas[i][17]); //公司地址
		// personMap.put("job", datas[i][18]); //职位
		// personMap.put("companyType", datas[i][19]); //公司性质
		// personMap.put("workYear", datas[i][20]); //现单位工作年限
		// personMap.put("companyLine", datas[i][21]); //现单位工作年限
		// personMap.put("monthlyIncome", datas[i][22]); //月收入
		// personMap.put("allowance", datas[i][23]); //奖金/津贴
		// personMap.put("otherIncome", datas[i][24]); //其他收入
		// personMap.put("incomeSum", datas[i][25]); //收入合计
		// personMap.put("alimony", datas[i][26]); //生活费
		// personMap.put("creditCardRepayment", datas[i][27]); //信用卡还款
		// personMap.put("paySum", datas[i][30]); //支出合计
		// personMap.put("informationSearch", datas[i][31]); //信息查询结果
		// personMap.put("auditStatus", "1"); //工作信息认证状态(1 默认审核中或等待审核 2 审核不通过 3
		// 审核通过)
		// String workPlace = datas[i][17];
		// personMap.put("workCity",
		// getNativePlaceId(workPlace).get("CityCode"));//工作地
		// personMap.put("workPro",
		// getNativePlaceId(workPlace).get("ProCode"));//工作地

		Dao.Tables.t_workauth table = new Dao().new Tables().new t_workauth();

		table.orgName.setValue(paramMap.get("orgName"));
		table.companyAddress.setValue(paramMap.get("companyAddress"));
		table.workYear.setValue(paramMap.get("workYear"));
		table.workCity.setValue(paramMap.get("workCity"));
		table.workPro.setValue(paramMap.get("workPro"));
		table.companyScale.setValue(paramMap.get("companyScale"));
		table.companyLine.setValue(paramMap.get("companyLine"));
		table.companyType.setValue(paramMap.get("companyType"));
		table.monthlyIncome.setValue(paramMap.get("monthlyIncome"));
		table.auditStatus.setValue(3);
		table.userId.setValue(paramMap.get("USERID"));
		table.job.setValue(paramMap.get("job"));

		return table.insert(conn);

	}

	public Long updateWorkAuth(Connection conn, long id, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_workauth table = new Dao().new Tables().new t_workauth();
		DBReflectUtil.mapToTableValue(table, paramMap);
		return table.update(conn, "id=" + id);

	}

	public Map<String, String> queryFamilyById(Connection conn, Long id) throws DataException, SQLException {
		Dao.Tables.t_workauth tw = new Dao().new Tables().new t_workauth();
		DataSet ds = tw.open(conn, " * ", "  userId=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 合和年 添加记录
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public long addWorkAuth(Connection conn, String familyName, String familyRelation, String familyAdd, String familyTel, String familyIdNo,
			String otherAdd, String otherIdNo, String otherName, String otherRelation, String otherTel, Long id) throws SQLException {
		Dao.Tables.t_workauth tw = new Dao().new Tables().new t_workauth();

		tw.directedName.setValue(familyName);
		tw.directedRelation.setValue(familyRelation);
		tw.directedTel.setValue(familyTel);
		tw.directedAddress.setValue(familyAdd);
		tw.directedIdNo.setValue(familyIdNo);

		tw.otherName.setValue(otherName);
		tw.otherRelation.setValue(otherRelation);
		tw.otherTel.setValue(otherTel);
		tw.otherAddress.setValue(otherAdd);
		tw.otherIdNo.setValue(otherIdNo);

		tw.userId.setValue(id);

		return tw.insert(conn);
	}

	/**
	 * 合和年 更新记录
	 */
	public long updateWorkAuth(Connection conn, Long userid) throws SQLException {
		Dao.Tables.t_workauth tw = new Dao().new Tables().new t_workauth();
		// 合和年无此项信息审核,直接通过
		tw.moredStatus.setValue(3);
		tw.directedStatus.setValue(3);
		tw.otherStatus.setValue(3);
		return tw.update(conn, " userId=" + userid);
	}

	/**
	 * 手动录入 更新认证
	 */
	public long updateWorkAuthImport(Connection conn, Long userid) throws SQLException {
		Dao.Tables.t_workauth tw = new Dao().new Tables().new t_workauth();
		// 直接通过
		tw.moredStatus.setValue(3);
		tw.directedStatus.setValue(3);
		tw.otherStatus.setValue(3);
		tw.auditStatus.setValue(3);
		return tw.update(conn, " userId=" + userid);
	}

}
