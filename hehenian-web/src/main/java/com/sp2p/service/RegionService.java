package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.RegionDao;

import freemarker.template.utility.DateUtil;

public class RegionService extends BaseService {
	public static Log log = LogFactory.getLog(RegionService.class);
	private RegionDao regionDao;
	private ConnectionManager connectionManager;

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	/**
	 * 查询地区
	 * 
	 * @param regionId
	 * @param parentId
	 * @param regionType
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryRegionList(Long regionId, Long parentId, Integer regionType) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> listMap = null;
		try {
			listMap = regionDao.queryRegionList(conn, regionId, parentId, regionType);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return listMap;
	}
	/**
	 * 查询地区
	 * 
	 * @param regionId
	 * @param parentId
	 * @param regionType
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, String>> queryRegionList2(Long regionId, Long parentId, Integer regionType) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> citys = null;
		Map<String, String> provs = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			citys = regionDao.queryRegionList2(conn, regionId, parentId, 2);
			provs = regionDao.queryRegionList2(conn, regionId, parentId, 1);
			list.add(provs);
			list.add(citys);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 和合年查询地区
	 * 
	 * @param regionId
	 * @param parentId
	 * @param regionType
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryRegionListHHN(Long regionId, Long parentId, Integer regionType) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> listMap = null;
		try {
			listMap = regionDao.queryRegionListHHN(conn, regionId, parentId, regionType);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return listMap;
	}

	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	/**
	 * 查询彩生活个人资料数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryFromBeautyLife(String realName, String idNo) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = regionDao.importFromBeautyLife(conn, realName, idNo);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 导入彩生活数据
	 * 
	 * @param map
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public long importFromBeautyLife(Map<String, String> map, long userId) throws SQLException, ParseException {
		String idno = map.get("idNo");
		StringBuilder birthday = new StringBuilder();
		if (idno != null) {
			// 15位身份证
			if (idno.length() == 15)
				birthday.append("19").append(idno.substring(6, 12));
			// 18位身份证
			if (idno.length() == 18)
				birthday.append(idno.substring(6, 14));
		}
		if (birthday.length() == 8) {
			birthday.insert(6, "-").insert(4, "-");
			map.put("birthday", birthday.toString());

			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday.toString());
			int age = new Date().getYear();
			age = age - date.getYear();
			map.put("age", age + "");
		}

		Connection conn = MySQL.getConnection();
		try {
			long ret1 = regionDao.updateUserStep(conn, map, userId);
			long ret2 = regionDao.updatePersonStep(conn, map, userId);
			long ret3 = regionDao.updateWorkAuthStep(conn, map, userId);
			long ret4 = regionDao.updatetMaterialsauthAuthStep(conn, map, userId);
			long ret5 = regionDao.updateColorLife(conn, map, userId);

			if (ret1 > 0 && ret2 > 0 && ret3 > 0 && ret4 > 0 && ret5 > 0) {
				conn.commit();
				return 1;
			} else {
				conn.rollback();
				return -1;
			}
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			conn.close();
		}
	}

}
