package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;

public class ActivityService extends BaseService{
	public static Log log = LogFactory.getLog(ActivityService.class);

	/**
	 * 查询最新的活动
	 * @return
	 */
	public Map<String, String> queryActivity(int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="SELECT * from t_activity where status=1 and id="+activityId+" order by id desc limit 1";
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			Map<String, String> map=BeanMapUtils.dataSetToMap(dataSet);
			return map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 查询奖项设置
	 * @return
	 */
	public List<Map<String, Object>> queryAwards(int activityId) {
			Connection conn = connectionManager.getConnection();
			try {
				String sql="SELECT t1.*,SUM(if(t2.id is not null,1,0)) hasNum from t_activity_award t1 left join t_activity_record t2 on t1.id=t2.awardId and DATE(t2.createTime)=CURDATE() where t1.activityId="+activityId+" GROUP BY t1.id";
				DataSet dataSet = MySQL.executeQuery(conn, sql);
				dataSet.tables.get(0).rows.genRowsMap();
				return dataSet.tables.get(0).rows.rowsMap;
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return null;
	}
	/**
	 * 保存中奖信息
	 * @param userId
	 * @param awardId
	 */
	public void saveRecord(long userId,int awardId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="insert into t_activity_record (awardId,userId,createTime) values("+awardId+","+userId+",now())";
			MySQL.executeNonQuery(conn, sql);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 更新用户活动积分
	 * @param userId
	 * @param amount
	 * @param activityId
	 */
	public void updateScore(long userId,int amount,int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="update t_activity_userinfo set score="+amount+" where activityId="+activityId+" and userId="+userId;
			MySQL.executeNonQuery(conn, sql);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 更新用户抽奖次数
	 * @param userId
	 * @param amount
	 * @param activityId
	 */
	public void updateHasDraw(long userId,int amount,int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="update t_activity_userinfo set hasDraw=hasDraw+"+amount+" where activityId="+activityId+" and userId="+userId;
			MySQL.executeNonQuery(conn, sql);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 更新用户活动现金奖励
	 * @param userId
	 * @param amount
	 * @param activityId
	 */
	public void updateMoney(long userId,double amount,int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="update t_activity_userinfo set money=money+"+amount+" where activityId="+activityId+" and userId="+userId;
			MySQL.executeNonQuery(conn, sql);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 签到 每天只能签到一次
	 * @param userId
	 * @param activityId
	 */
	public boolean sign(long userId,int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="insert into t_activity_sign (userId,signDay,activityId) values("+userId+",CURDATE(),"+activityId+")";
			long ret = MySQL.executeNonQuery(conn, sql);
			return ret>0;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 查询用户签到记录
	 * @param activityId
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> querySignRecord(int activityId,long userId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="SELECT  DATE_FORMAT(day,'%y-%c-%e') day,DATE_FORMAT(signDay,'yy-M-d') signDay,DAY(day) d,MONTH(day) m from t_activity_day t1 left join t_activity_sign t2 on t1.day=t2.signDay and userId="+userId+" and activityId="+activityId+" ORDER BY `day`";
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 查询用户活动信息
	 * @param userId
	 * @param activityId
	 * @return
	 */
	public Map<String, String> queryUserInfo(long userId,int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="select * from t_activity_userinfo where userId="+userId+" and activityId="+activityId;
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			Map<String, String> map=BeanMapUtils.dataSetToMap(dataSet);
			if (map==null) {
				addUserinfo(userId, activityId);
			}
			dataSet = MySQL.executeQuery(conn, sql);
			map=BeanMapUtils.dataSetToMap(dataSet);
			return map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 插入一条用户的活动信息
	 * @param userId
	 * @param activityId
	 */
	private void addUserinfo(long userId,int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="insert into  t_activity_userinfo (userId,activityId) values("+userId+","+activityId+")";
			MySQL.executeNonQuery(conn, sql);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 查询中奖纪录
	 * @param activityId
	 * @return
	 */
	public List<Map<String, Object>> queryActivityRecore(int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="SELECT `t1`.`id` AS `id`,`t1`.`awardId` AS `awardId`,`t1`.`userId` AS `userId`,`t1`.`createTime` AS `createTime`, "
                    + "`f_formatting_username`(`t3`.`username`)AS `username`,`t2`.`title` AS `title`,`t2`.`activityId` AS `activityId` "
                    + "FROM `t_activity_record` `t1` JOIN `t_activity_award` `t2` ON `t1`.`awardId` = `t2`.`id` "
                    + "JOIN `t_user` `t3` ON `t1`.`userId` = `t3`.`id` where t2.activityId="+activityId+" and t2.isShow=1 ORDER BY `t1`.`createTime` DESC limit 50";
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 查询中奖人次
	 * @param activityId
	 * @return
	 */
	public int queryActivityRecoreCount(int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="SELECT count(1) c from t_activity_record t1 "+
			" join t_activity_award t2 on t1.awardId=t2.id where t2.isShow=1 and  t2.activityId= "+activityId;
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			Map<String, String> map=BeanMapUtils.dataSetToMap(dataSet);
			return Integer.parseInt(map.get("c"));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * 计算用户的积分
	 * @param userId
	 * @param activityId
	 */
	public void computeScore(long userId,int activityId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql="SELECT SUM(investAmount) s from t_invest t1 join t_borrow t2 on t1.borrowId=t2.id and t2.borrowStatus<>6 where investTime BETWEEN 20140901 and 20140906 and investAmount>0 and t1.investor="+userId;
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			Map<String, String> map=BeanMapUtils.dataSetToMap(dataSet);
			if (map!=null&&StringUtils.isNotBlank(map.get("s"))) {
				int s = new BigDecimal(map.get("s")).intValue();
				if (s>0) {
					sql="update t_activity_userinfo set score="+(s/2000+1)+" where activityId="+activityId+" and userId="+userId;;
					MySQL.executeNonQuery(conn, sql);
				}
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 查询借款列表
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> queryBorrowList(int userGroup) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql = "SELECT borrowAmount,number,deadline,investNum,id,schedules,borrowTitle,borrowStatus,annualRate,hasInvestAmount from v_t_borrow_list where borrowStatus in(2,3,4) and borrowGroup="+userGroup+" ORDER BY borrowStatus,publishTime desc limit 5";
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 查询某个用户的中奖记录
	 * @param activityId
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> queryMyActivityRecords(int activityId,long userId) {
		Connection conn = connectionManager.getConnection();
		try {
			String sql = "SELECT date(t1.createTime) cTime,t2.title from t_activity_record t1 "+
					" join t_activity_award t2 on t1.awardId=t2.id and t2.activityId= "+activityId+
					" where t1.userId="+userId+
					" and t2.isShow=1  ORDER BY t1.createTime desc limit 10 ";;
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

    /**
     * 获取指定日期的中奖记录
     * @param activityId
     * @param userId
     * @param date
     * @return
     */
    public List<Map<String, Object>> queryMyActivityRecordsByDate(int activityId,long userId,String date) {
        Connection conn = connectionManager.getConnection();
        try {
            String sql = "SELECT t1.*,t2.title from t_activity_record t1  join t_activity_award t2 on t1.awardId=t2.id and t2.activityId= "+activityId
            +" where t1.userId="+userId
            +" and DATE(createTime)="+date;;
            DataSet dataSet = MySQL.executeQuery(conn, sql);
            dataSet.tables.get(0).rows.genRowsMap();
            return dataSet.tables.get(0).rows.rowsMap;
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Map<String, String> queryDefaultAwardId(int activityId){
        Connection conn = connectionManager.getConnection();
        String sql = "select * from t_activity_award where isDefault=1 and activityId="+activityId;
        try {
            DataSet dataSet = MySQL.executeQuery(conn, sql);
            return BeanMapUtils.dataSetToMap(dataSet);
        } catch (DataException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Map<String, String> getAward(int id){
        Connection conn = connectionManager.getConnection();
        String sql = "select * from t_activity_award where id="+id;
        try {
            DataSet dataSet = MySQL.executeQuery(conn, sql);
            return BeanMapUtils.dataSetToMap(dataSet);
        } catch (DataException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}



