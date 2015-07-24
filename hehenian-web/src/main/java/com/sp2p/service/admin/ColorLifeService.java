package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.ColorLifeDao;

public class ColorLifeService extends BaseService {
	
	private ColorLifeDao colorLifeDao;

	public List<String> importInfo(List<Map<String, String>> paramList)
			throws SQLException {
		Connection conn = null;
		List<String> errorList = new ArrayList<String>();
		Long result = -1L;
		Map<String, String> paramMap;
		try {
			conn = MySQL.getConnection();
			for (int i = 0; i < paramList.size(); i++) {
				paramMap = paramList.get(i);
				Long id = colorLifeDao.addColorLife(conn, paramMap);
				if (id <= 0) {
					result = -1L;
					errorList.add("第" + (i + 2) + "行插入失败!");
				}
				result = 1L;
			}
			if (result > 0) {
				conn.commit();
			}
		} catch (SQLException e) {
			errorList.add("导入失败!");
			conn.rollback();
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return errorList;
	}

	public void setColorLifeDao(ColorLifeDao colorLifeDao) {
		this.colorLifeDao = colorLifeDao;
	}

}
