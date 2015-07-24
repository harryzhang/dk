package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class RegionDao {
	/**
	 * 查询地区列表
	 * 
	 * @param conn
	 * @param regionId
	 * @param parentId
	 * @param regionType
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryRegionList(Connection conn, Long regionId, Long parentId, Integer regionType) throws SQLException,
			DataException {
		Dao.Tables.t_region region = new Dao().new Tables().new t_region();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1 ");
		if (regionId != null && regionId > 0) {
			condition.append(" AND regionId=" + regionId);
		}
		if (parentId != null && parentId > 0) {
			condition.append(" AND parentId=" + parentId);
		}
		if (regionType != null && regionType > 0) {
			condition.append(" AND regionType=" + regionType);
		}
		DataSet dataSet = region.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 查询地区列表
	 * 
	 * @param conn
	 */
	public Map<String, String> queryRegionList2(Connection conn, Long regionId, Long parentId, Integer regionType) throws SQLException, DataException {
		Dao.Tables.t_region_hhn region = new Dao().new Tables().new t_region_hhn();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1 ");
		if (regionId != null && regionId > 0) {
			condition.append(" AND regionId=" + regionId);
		}
		if (parentId != null && parentId > 0) {
			condition.append(" AND parentId=" + parentId);
		}
		if (regionType != null && regionType > 0) {
			condition.append(" AND regionType=" + regionType);
		}
		DataSet dataSet = region.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 和合年查询地区列表
	 * 
	 * @param conn
	 * @param regionId
	 * @param parentId
	 * @param regionType
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryRegionListHHN(Connection conn, Long regionId, Long parentId, Integer regionType) throws SQLException,
			DataException {
		Dao.Tables.t_region_hhn t_region_hhn = new Dao().new Tables().new t_region_hhn();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1 ");
		if (regionId != null && regionId > 0) {
			condition.append(" AND regionId=" + regionId);
		}
		if (parentId != null && parentId > 0) {
			condition.append(" AND parentId=" + parentId);
		}
		if (regionType != null && regionType > 0) {
			condition.append(" AND regionType=" + regionType);
		}
		DataSet dataSet = t_region_hhn.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 彩生活导入个人资料数据 此方法未判断用户是否已经被激活过
	 * 
	 * @param conn
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws Exception
	 */
	public Map<String, String> importFromBeautyLife(Connection conn, String realName, String idNo) throws SQLException, DataException {
		Dao.Tables.t_colorlife cl = new Dao().new Tables().new t_colorlife();
		String cmd = " realName='" + realName + "' and idNo='" + idNo + "'";
		DataSet ds = cl.open(conn, " * ", cmd, "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return BeanMapUtils.dataSetToMap(ds);
	}

	/** 更新user表认证步骤 */
	public long updateUserStep(Connection conn, Map<String, String> map, long userId) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.source.setValue(2);
		user.authStep.setValue(5);
		return user.update(conn, " id=" + userId);
	}

	/** 更新Person表认证步骤 */
	public long updatePersonStep(Connection conn, Map<String, String> map, long userId) throws SQLException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(map.get("realName"));
		person.birthday.setValue(map.get("birthday"));
		person.age.setValue(map.get("age"));
		person.sex.setValue(map.get("sex"));
		person.highestEdu.setValue(map.get("highestEdu"));
		person.maritalStatus.setValue(map.get("maritalStatus"));
		person.address.setValue(map.get("address"));
		person.hasCar.setValue(map.get("hasCar"));
		person.idNo.setValue(map.get("idNo"));
		person.auditStatus.setValue(3);
		return person.update(conn, " userId=" + userId);
	}

	/** 更新workauth表认证步骤 */
	public long updateWorkAuthStep(Connection conn, Map<String, String> map, long userId) throws SQLException {
		Dao.Tables.t_workauth work = new Dao().new Tables().new t_workauth();
		work.auditStatus.setValue(3);
		work.directedStatus.setValue(3);
		work.otherStatus.setValue(3);
		work.moredStatus.setValue(3);
		work.orgName.setValue(map.get("orgName"));
		work.companyType.setValue(map.get("companyType"));
		work.companyScale.setValue(map.get("companyScale"));
		work.job.setValue(map.get("job"));
		work.workYear.setValue(map.get("workYear"));
		work.companyTel.setValue(map.get("companyPhone"));
		work.companyAddress.setValue(map.get("companyAddress"));
		// work.monthlyIncome.setValue(map.get("annualIncome")/12);
		return work.update(conn, " userId=" + userId);
	}

	/** 更新materialsauth表认证步骤 */
	public long updatetMaterialsauthAuthStep(Connection conn, Map<String, String> map, long userId) throws SQLException {
		Dao.Tables.t_materialsauth mater = new Dao().new Tables().new t_materialsauth();
		mater.auditStatus.setValue(3);
		mater.imgPath.setValue("images/cai.png");
		mater.authTime.setValue(new Date());
		return mater.update(conn, " userId=" + userId);
	}

	/** 更新 colorlife表激活状态和useid */
	public long updateColorLife(Connection conn, Map<String, String> map, long userId) throws SQLException {
		Dao.Tables.t_colorlife color = new Dao().new Tables().new t_colorlife();
		color.activatedState.setValue(2);
		color.userId.setValue(userId);
		return color.update(conn, " realName='" + map.get("realName") + "' and idNo='" + map.get("idNo") + "'");
	}
}
