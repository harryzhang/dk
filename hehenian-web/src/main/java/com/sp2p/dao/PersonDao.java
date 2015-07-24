package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 操作t_person表
 * 
 * @author lw
 * 
 */
public class PersonDao {
	/**
	 * @param conn
	 * @param realName
	 *            真实姓名
	 * @param cellPhone
	 *            手机号码
	 * @param sex
	 *            性别(男 女)
	 * @param birthday
	 *            出生日期
	 * @param highestEdu
	 *            最高学历
	 * @param eduStartDay
	 *            入学年份
	 * @param school
	 *            毕业院校
	 * @param maritalStatus
	 *            婚姻状况(已婚 未婚)
	 * @param hasChild
	 *            有无子女(有 无)
	 * @param hasHourse
	 *            是否有房(有 无)
	 * @param hasHousrseLoan
	 *            有无房贷(有 无)
	 * @param hasCar
	 *            是否有车 (有 无)
	 * @param hasCarLoan
	 *            有无车贷 (有 无)
	 * @param nativePlacePro
	 *            籍贯省份(默认为-1)
	 * @param nativePlaceCity
	 *            籍贯城市 (默认为-1)
	 * @param registedPlacePro
	 *            户口所在地省份(默认为-1)
	 * @param registedPlaceCity
	 *            户口所在地城市(默认为-1)
	 * @param address
	 *            居住地址
	 * @param telephone
	 *            居住电话
	 * @param userId
	 *            用户id
	 * @param personalHead
	 *            个人头像
	 * @param idNo
	 *            身份证号码
	 * @param auditStatus
	 *            认证状态(1 默认审核中或等待审核 2 审核不通过 3 审核通过)
	 * @param flag
	 *            1为填写 2 为未填写
	 * @return
	 * @throws SQLException
	 */
	public Long addPerson(Connection conn, String realName, String cellPhone, String sex, String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild, String hasHourse, String hasHousrseLoan, String hasCar, String hasCarLoan,
			Integer nativePlacePro, Integer nativePlaceCity, Integer registedPlacePro, Integer registedPlaceCity, String address, String telephone,
			Long userId, String personalHead, String idNo, Integer auditStatus, Integer flag) throws SQLException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		if (realName != null) {
			person.realName.setValue(realName);
		}
		if (cellPhone != null) {
			person.cellPhone.setValue(cellPhone);
		}
		if (sex != null) {
			person.sex.setValue(sex);
		}
		if (birthday != null) {
			person.birthday.setValue(birthday);
		}
		if (highestEdu != null) {
			person.highestEdu.setValue(highestEdu);
		}
		if (eduStartDay != null) {
			person.eduStartDay.setValue(eduStartDay);
		}
		if (school != null) {
			person.school.setValue(school);
		}
		if (maritalStatus != null) {
			person.maritalStatus.setValue(maritalStatus);
		}

		if (hasChild != null) {
			person.hasChild.setValue(hasChild);
		}
		if (hasHourse != null) {
			person.hasHourse.setValue(hasHourse);
		}
		if (hasHousrseLoan != null) {
			person.hasHousrseLoan.setValue(hasHousrseLoan);
		}
		if (hasCar != null) {
			person.hasCar.setValue(hasCar);
		}

		if (hasCarLoan != null) {
			person.hasCarLoan.setValue(hasCarLoan);
		}
		if (nativePlacePro != null) {
			person.nativePlacePro.setValue(nativePlacePro);
		}
		if (nativePlaceCity != null) {
			person.nativePlaceCity.setValue(nativePlaceCity);
		}
		if (registedPlacePro != null) {
			person.registedPlacePro.setValue(registedPlacePro);
		}
		if (registedPlaceCity != null) {
			person.registedPlaceCity.setValue(registedPlaceCity);
		}

		if (address != null) {
			person.address.setValue(address);
		}
		if (telephone != null) {
			person.telephone.setValue(telephone);
		}
		if (userId != null) {
			person.userId.setValue(userId);
		}
		if (personalHead != null) {
			person.personalHead.setValue(personalHead);
		}
		if (idNo != null) {
			person.idNo.setValue(idNo);
		}
		if (auditStatus != null) {
			person.auditStatus.setValue(auditStatus);
		}
		if (flag != null) {
			person.flag.setValue(flag);
		}

		return person.insert(conn);
	}

	/**
	 * 根据手机号码查询信息
	 * 
	 * @param conn
	 * @param cellphone
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> querCellPhone(Connection conn, String cellphone) throws DataException, SQLException {
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		DataSet ds = t_person.open(conn, " * ", " cellPhone = '" + StringEscapeUtils.escapeSql(cellphone) + "' ", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();

		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 添加
	 * */
	public Long addPerson(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_person table = new Dao().new Tables().new t_person();
		// personMap = new HashMap<String, String>();
		// personMap.put("realName", realName);
		// personMap.put("sex", datas[i][2]); //性别
		// personMap.put("age", datas[i][3]); //年龄
		// personMap.put("maritalStatus", datas[i][4]); //婚姻状况
		// personMap.put("dwell", datas[i][5]); //居住情况
		// personMap.put("highestEdu", datas[i][6]); //学历
		// personMap.put("creditCardSum", datas[i][7]); //信用卡张数
		// personMap.put("creditCardAmount", datas[i][8]); //信用卡额度
		// personMap.put("hasHousrseLoan", datas[i][28]); //房贷
		// personMap.put("hasCarLoan", datas[i][29]); //车贷
		// personMap.put("cellPhone", datas[i][12]); //手机号码
		// personMap.put("bankCard", datas[i][10]); //银行卡号
		// personMap.put("idNo", datas[i][11]); //身份证号
		// personMap.put("auditStatus", "1"); //审核状态1 等待审核
		// personMap.put("personalHead", "images/index_46.jpg"); //头像

		// table.nativePlaceCity.setValue(paramMap.get("NATIVEPLACECITY"));
		// table.nativePlacePro.setValue(paramMap.get("nativePlacePro"));

		table.personalHead.setValue(paramMap.get("personalHead"));
		table.maritalStatus.setValue(paramMap.get("maritalStatus"));
		table.cellPhone.setValue(paramMap.get("cellPhone"));
		table.hasHourse.setValue(paramMap.get("dwell"));
		table.hasHousrseLoan.setValue(paramMap.get("hasHousrseLoan"));
		table.hasCarLoan.setValue(paramMap.get("hasCarLoan"));
		table.highestEdu.setValue(paramMap.get("highestEdu"));
		table.auditStatus.setValue(3);
		table.realName.setValue(paramMap.get("realName"));
		table.userId.setValue(paramMap.get("USERID"));
		table.sex.setValue(paramMap.get("sex"));
		table.idNo.setValue(paramMap.get("idNo"));
		table.bankCar.setValue(paramMap.get("bankCard"));
		table.nativePlaceCity.setValue(paramMap.get("workCity"));
		table.nativePlacePro.setValue(paramMap.get("workPro"));
		return table.insert(conn);

	}

	/**
	 * 查询有没有这个用户记录
	 * 
	 * @param conn
	 * @param userId
	 * @throws SQLException
	 * @throws DataException
	 */
	public boolean queryPersonByuserId(Connection conn, Long userId) throws SQLException, DataException {
		Dao.Tables.t_person tp = new Dao().new Tables().new t_person();
		com.shove.data.DataSet ds = tp.open(conn, "*", " userId=" + userId, "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		if (map == null || map.size() == 0)
			return false;
		return true;
	}

	public long updatePerson(Connection conn, String realName, String sex, String birthday, String highestEdu, String eduStartDay, String school,
			Long nativePlacePro, Long nativePlaceCity, Long registedPlacePro, Long registedPlaceCity, String address, Long userId, String idNo,
			String email) throws Exception {
		Dao.Tables.t_person tp = new Dao().new Tables().new t_person();
		tp.realName.setValue(realName);
		tp.sex.setValue(sex);
		tp.birthday.setValue(birthday);
		tp.highestEdu.setValue(highestEdu);
		tp.eduStartDay.setValue(eduStartDay);
		tp.school.setValue(school);
		tp.nativePlacePro.setValue(nativePlacePro);
		tp.nativePlaceCity.setValue(nativePlaceCity);
		tp.registedPlacePro.setValue(registedPlacePro);
		tp.registedPlaceCity.setValue(registedPlaceCity);
		tp.idNo.setValue(idNo);
		tp.address.setValue(address);
		tp.email.setValue(email);

		return tp.update(conn, " userId=" + userId);
	}

	/**
	 * 更新用户电子邮箱
	 * 
	 * @param conn
	 * @param userId
	 * @param email
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateUser(Connection conn, Long userId, String email) throws Exception {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		t_user.email.setValue(email);
		return t_user.update(conn, " id=" + userId);
	}

	public long insertRecord(Connection conn, String realName, String sex, String birthday, String highestEdu, String eduStartDay, String school,
			Long nativePlacePro, Long nativePlaceCity, Long registedPlacePro, Long registedPlaceCity, String address, Long userId, String idNo)
			throws SQLException {
		Dao.Tables.t_person tp = new Dao().new Tables().new t_person();
		tp.realName.setValue(realName);
		tp.sex.setValue(sex);
		tp.birthday.setValue(birthday);
		tp.highestEdu.setValue(highestEdu);
		tp.eduStartDay.setValue(eduStartDay);
		tp.school.setValue(school);
		tp.nativePlacePro.setValue(nativePlacePro);
		tp.nativePlaceCity.setValue(nativePlaceCity);
		tp.registedPlacePro.setValue(registedPlacePro);
		tp.registedPlaceCity.setValue(registedPlaceCity);
		tp.idNo.setValue(idNo);
		tp.address.setValue(address);
		tp.userId.setValue(userId);

		return tp.insert(conn);
	}

	/** 汇付回调,更新真实姓名,身份证,同时通过审核 */
	public long updatePerson(Connection conn, long userId, String realName, String idNo) throws SQLException {
		Dao.Tables.t_person tp = new Dao().new Tables().new t_person();
		tp.realName.setValue(realName);
		tp.idNo.setValue(idNo);
		tp.auditStatus.setValue(3);
		return tp.update(conn, " userId=" + userId);
	}

}
