package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.admin.AwardDetailDao;
import com.sp2p.service.AwardService;
import com.shove.base.BaseService;
import com.shove.data.dao.Database;

public class AwardDetailService extends BaseService {
	
	public static Log log = LogFactory.getLog(AwardService.class);
	
	private AwardDetailDao awardDetailDao;
	
	public Long addAwardDetail(long userId,double handleSum,
			long checkId,Date checkTime,String remark) throws SQLException{
		Connection conn = Database.getConnection();
		try {
			//添加团队长、经纪人提成结算
			long result = awardDetailDao.addAwardDetail(conn, userId,  handleSum, checkId, checkTime, remark);
			if(result <= 0){
				conn.rollback();
				return result;
			}
			conn.commit();
			return result;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	

	public AwardDetailDao getAwardDetailDao() {
		return awardDetailDao;
	}

	public void setAwardDetailDao(AwardDetailDao awardDetailDao) {
		this.awardDetailDao = awardDetailDao;
	}

}
