package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Bean;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSourceFactory;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.AwardDao;
import com.sp2p.dao.AwardLevel4Dao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.AwardDetailDao;
import com.sp2p.dao.admin.RelationDao;

public class AwardService extends BaseService {

	public static Log log = LogFactory.getLog(AwardService.class);

	private RelationDao relationDao;
	private UserDao userDao;
	private AwardDao awardDao;
	private AwardLevel4Dao awardLevel4Dao;
	private AwardDetailDao awardDetailDao;

	public int updataMoney(Connection conn, long userId, BigDecimal money,
			int type, long investOrRepaymentId) throws Exception {
		Map<String, String> user = userDao.queryUserById(conn, userId);
		if (user.get("refferee") == null || "".equals(user.get("refferee"))) {
			return 3;
		}
		Map<String, String> map = queryEconomyByName(conn, user.get("refferee"));
		Map<String, Object> platformCostMap = getPlatformCost();
		return awardHeNew(conn, userId, null, map, investOrRepaymentId, type, money, platformCostMap);
	}

	/**
	 * (新规则) 金额变动
	 * 
	 * @param conn
	 * @param userId用户编号
	 *            (投资人和理财人)
	 * @param money变动金额
	 * @param type1投资2还款
	 * @param investOrRepaymentId投资
	 *            /还款明细编号
	 * @return -1,-2,-3,-4: 0：处理成功 1:此标的不属于实地认证标的，或机构担保标的。
	 *         2：当前用户已经期满1年，不需要进行奖励了。 3：此用户不是奖励体系的角色。 4：与上一级解除关系了不需要进行奖励。
	 * @throws Exception
	 */
	public int updateMoneyNew(Connection conn, long userId, BigDecimal money,
			int type, long investOrRepaymentId) throws Exception {
		List<Map<String, Object>> relationList = relationDao
				.queryRelationByUserId(conn, userId);// 查询用户角色
		if (relationList == null || relationList.size() <= 0) {
			return 3;// 不在角色关系系统内，
		}
		Map<String, Object> relationMap = new HashMap<String, Object>();
		relationMap = relationList.get(0);
		int level = Convert.strToInt(relationMap.get("level") + "", -1);// 当前用户级别
		int enable = Convert.strToInt(relationMap.get("enable") + "", -1);// 是否和上一级解除关系
		long parentId = Convert.strToLong(relationMap.get("parentId") + "", -1);
		if (enable == 2) {
			return 4;// 与上一级解除了关联
		}
		if (level == 4) {// 理财人
			// 获得理财人的经纪人
			List<Map<String, Object>> list = relationDao
					.queryRelationByPeopleId(conn, parentId);// 理财人的上级是投资人，投资人的上级是经纪人
			if (list == null || list.size() <= 0) {
				return 3;// 不在角色关系系统内，
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map = list.get(0);
			enable = Convert.strToInt(map.get("enable") + "", -1);// 是否和上一级解除关系
			parentId = Convert.strToLong(map.get("parentId") + "", -1);// 投资人的上级是经纪人
			// 投资人与上级解除关系了跟理财人跟上级的关系无关
			// if(enable==2){
			// return 4;//与上一级解除了关联
			// }
			return 5;
		}
		Map<String, Object> platformCostMap = getPlatformCost();
		// 奖励机制1：
		award1New(conn, userId, null, relationMap, parentId,
				investOrRepaymentId, level, type, money, platformCostMap);

		return 5;
	}

	public int awardHeNew(Connection conn, long userId,
			Map<String, String> userMap, Map<String, String> economy,
			 long investOrRepaymentId,int type,
			BigDecimal moneys, Map<String, Object> platformCostMap) throws Exception {
		String agent_reward_rate = Convert.strToStr(platformCostMap
				.get(IAmountConstants.AGENT_REWARD_RATE)
				+ "", "0.0005");
		BigDecimal level2Money = moneys.multiply(new BigDecimal(agent_reward_rate));// 经纪人所得奖励
		if(economy.get("enable").equals("2")){
			level2Money= BigDecimal.ZERO;
		}
		awardDao.addAward(conn, userId, Long.parseLong(economy.get("id")), level2Money,
				-1, BigDecimal.ZERO, investOrRepaymentId, type, 2,
				moneys, IConstants.MX_TYPE_MAX, -1, 0);
		return 0;
	}

	/**
	 * 奖励机制1
	 * 
	 * @param conn
	 * @param userId用户编号
	 * @param userMap
	 *            用户明细
	 * @param relationMap
	 *            用户角色关系
	 * @param level
	 *            用户角色等级
	 * @param parentId
	 *            父编号
	 * @param moneys
	 *            总待收本金
	 * @return
	 * @throws Exception
	 */
	public int award1New(Connection conn, long userId,
			Map<String, String> userMap, Map<String, Object> relationMap,
			long parentId, long investOrRepaymentId, int level, int type,
			BigDecimal moneys, Map<String, Object> platformCostMap)
			throws Exception {
		BigDecimal level2Money = BigDecimal.ZERO;
		long level2UserId = -1;
		BigDecimal level1Money = BigDecimal.ZERO;
		long level1UserId = -1;
		// 总的待收本金与历史最大待收本金作比较xmax，
		List<Map<String, Object>> relationLevel1List = relationDao
				.queryRelationStatus(conn, parentId, null);// 根据经纪人查询是否有关联的团队长
		Map<String, Object> relationLevel1Map = null;
		if (relationLevel1List != null && relationLevel1List.size() > 0) {
			relationLevel1Map = relationLevel1List.get(0);
		}
		if (relationLevel1Map != null) {
			int level2enable = Convert.strToInt(relationLevel1Map
					.get("level2enable")
					+ "", -1);
			int level1enable = Convert.strToInt(relationLevel1Map
					.get("level1enable")
					+ "", -1);
			level2UserId = parentId;
			// 团队长奖励比例
			String longTeamRewardRate = Convert.strToStr(platformCostMap
					.get(IAmountConstants.LONG_TEAM_REWARD_RATE)
					+ "", "0.0025");
			// 经纪人奖励比率
			String agent_reward_rate = Convert.strToStr(platformCostMap
					.get(IAmountConstants.AGENT_REWARD_RATE)
					+ "", "0.0005");
			level2Money = moneys.multiply(new BigDecimal(agent_reward_rate));// 经纪人所得奖励
			// 1奖励给经纪人，2奖励完经纪人后，根据经纪人编号查询是否上面有团队长，如果有则按奖励公式给予团队长进行奖励，并作记录
			int enable1 = Convert.strToInt(
					relationLevel1Map.get("enable") + "", -1);// 是否和上一级解除关系
			if (enable1 == 1) {
				long parentId1 = Convert.strToLong(relationLevel1Map
						.get("level1userId")
						+ "", -1);
				level1UserId = parentId1;
				level1Money = level2Money.multiply(new BigDecimal(
						longTeamRewardRate));
				// MySQL.executeNonQuery(conn,
				// " update t_admin set moneys = moneys+"+level1Money+" where id = "+level1UserId);//奖励团队长提成
			}
			if (level2enable != 1) {// 如果经纪人被禁用了则不能获得奖励
				level2Money = BigDecimal.ZERO;
			}
			if (level1enable != 1) {// 如果团队长被禁用了则不能获得奖励
				level1Money = BigDecimal.ZERO;
			}
			awardDao.addAward(conn, userId, level2UserId, level2Money,
					level1UserId, level1Money, investOrRepaymentId, type, 2,
					moneys, IConstants.MX_TYPE_MAX, -1, level);
			// 并把当前总的待收本金赋值给历史最大待收本金，并作记录
			// MySQL.executeNonQuery(conn," update t_user set xmax = "+moneys+" where id ="+userId);
		}
		// 如果当前总的待收本金小于历史最大待收本金，则不作任何处理
		return 0;
	}

	// ----------------------奖励机制----------------------
	/*
	 * /** 金额变动
	 * 
	 * @param conn
	 * 
	 * @param userId用户编号(投资人和理财人)
	 * 
	 * @param money变动金额
	 * 
	 * @param type1投资2还款
	 * 
	 * @param investOrRepaymentId投资/还款明细编号
	 * 
	 * @return -1,-2,-3,-4: 0：处理成功 1:此标的不属于实地认证标的，或机构担保标的。
	 * 2：当前用户已经期满1年，不需要进行奖励了。 3：此用户不是奖励体系的角色。 4：与上一级解除关系了不需要进行奖励。
	 * 
	 * @throws Exception
	 * 
	 * public int updateMoney(Connection conn,long userId,BigDecimal money,int
	 * type,long investOrRepaymentId) throws Exception{ DataSet dataSet = null;
	 * if(type==IConstants.MONEY_TYPE_1){//投资 dataSet = MySQL.executeQuery(conn,
	 * " select a.id as id, a.borrowId as borrowId,b.borrowWay as borrowWay from t_invest a left join t_borrow b on a.borrowId = b.id where a.id = "
	 * +investOrRepaymentId); }else if(type==IConstants.MONEY_TYPE_2){//还款
	 * dataSet = MySQL.executeQuery(conn,
	 * " select a.id as id, a.borrowId as borrowId,b.borrowWay as borrowWay from t_repayment a left join t_borrow b on a.borrowId = b.id where a.id = "
	 * +investOrRepaymentId); } Map<String,String> iorMap =
	 * BeanMapUtils.dataSetToMap(dataSet); //任何标的都产生提成 // int borrowWay =
	 * Convert.strToInt(iorMap.get("borrowWay"), -1); //
	 * if(borrowWay!=IConstants
	 * .BORROWWAY_TYPE_4&&borrowWay!=IConstants.BORROWWAY_TYPE_5){ // return 1;
	 * // } Map<String,String> userMap = userDao.queryUserById(conn,
	 * userId);//查询用户明细 SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date newDate = new Date(); Date
	 * endDate = sdf.parse(userMap.get("createTime"));
	 * endDate.setYear(endDate.getYear()+1);
	 * if(newDate.getTime()>endDate.getTime()){//当前时间大于用户1年期限说明已经不用提交奖励给经纪人了。
	 * return 2; } List<Map<String,Object>> relationList =
	 * relationDao.queryRelationByUserId(conn,userId);//查询用户角色
	 * 
	 * if(relationList==null||relationList.size()<=0){ return 3;//不在角色关系系统内， }
	 * Map<String,Object> relationMap = new HashMap<String, Object>();
	 * relationMap = relationList.get(0); int level =
	 * Convert.strToInt(relationMap.get("level")+"",-1);//当前用户级别 int enable =
	 * Convert.strToInt(relationMap.get("enable")+"",-1);//是否和上一级解除关系 long
	 * parentId = Convert.strToLong(relationMap.get("parentId")+"", -1);
	 * if(enable==2){ return 4;//与上一级解除了关联 } //如果是理财人，判断是否是第一次交易，则给投资人加10元钱，作记录
	 * if(level==4){//理财人 //获得理财人的经纪人 List<Map<String,Object>> list =
	 * relationDao.queryRelationByPeopleId(conn,
	 * parentId);//理财人的上级是投资人，投资人的上级是经纪人 if(list==null||list.size()<=0){ return
	 * 3;//不在角色关系系统内， } Map<String,Object> map = new HashMap<String, Object>();
	 * map = list.get(0); enable =
	 * Convert.strToInt(map.get("enable")+"",-1);//是否和上一级解除关系 parentId =
	 * Convert.strToLong(map.get("parentId")+"", -1);//投资人的上级是经纪人 }
	 * Map<String,Object> platformCostMap = getPlatformCost(); //奖励机制1：
	 * award1(conn
	 * ,userId,userMap,relationMap,parentId,investOrRepaymentId,level,
	 * type,platformCostMap);
	 * 
	 * //奖励机制2： award2(conn,userId,userMap,money,type);
	 * 
	 * return 5; }
	 */

	/**
	 * 奖励机制1
	 * 
	 * @param conn
	 * @param userId用户编号
	 * @param userMap
	 *            用户明细
	 * @param relationMap
	 *            用户角色关系
	 * @param level
	 *            用户角色等级
	 * @param parentId
	 *            父编号
	 * @param moneys
	 *            总待收本金
	 * @return
	 * @throws Exception
	 */
	public int award1(Connection conn, long userId,
			Map<String, String> userMap, Map<String, Object> relationMap,
			long parentId, long investOrRepaymentId, int level, int type,
			Map<String, Object> platformCostMap) throws Exception {
		BigDecimal level2Money = BigDecimal.ZERO;
		long level2UserId = -1;
		BigDecimal level1Money = BigDecimal.ZERO;
		long level1UserId = -1;
		// 根据当前用户查询当前用户的角色，如果是投资人，则走下面的流程，
		// 计算当前用户总的待收本金（计算总金额）
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						" select sum(recivedPrincipal-hasPrincipal) as moneys from t_invest where investor =  "
								+ userId);
		Map<String, String> moneysMap = BeanMapUtils.dataSetToMap(dataSet);
		BigDecimal moneys = new BigDecimal(moneysMap.get("moneys"));// 待收本金和
		BigDecimal xmax = new BigDecimal(userMap.get("xmax"));
		// 总的待收本金与历史最大待收本金作比较xmax，
		List<Map<String, Object>> relationLevel1List = relationDao
				.queryRelationStatus(conn, parentId, null);// 根据经纪人查询是否有关联的团队长
		Map<String, Object> relationLevel1Map = null;
		if (relationLevel1List != null && relationLevel1List.size() > 0) {
			relationLevel1Map = relationLevel1List.get(0);
		}
		if (moneys.compareTo(xmax) == 1 && relationLevel1Map != null) {// 当前待收本金大于历史待收本金
			int level2enable = Convert.strToInt(relationLevel1Map
					.get("level2enable")
					+ "", -1);
			int level1enable = Convert.strToInt(relationLevel1Map
					.get("level1enable")
					+ "", -1);
			// 团队长奖励比例
			String longTeamRewardRate = Convert.strToStr(platformCostMap
					.get(IAmountConstants.LONG_TEAM_REWARD_RATE)
					+ "", "0.25");
			// 经纪人奖励比率
			String agent_reward_rate = Convert.strToStr(platformCostMap
					.get(IAmountConstants.AGENT_REWARD_RATE)
					+ "", "0.0005");
			// 如果当前总的待收本金大于历史最大待收本金，则计算金额差并按公式计算奖励，
			level2UserId = parentId;
			BigDecimal poorMoneys = moneys.subtract(xmax);// 待收本金差
			level2Money = poorMoneys
					.multiply(new BigDecimal(agent_reward_rate));// 经纪人所得奖励

			// 1奖励给经纪人，2奖励完经纪人后，根据经纪人编号查询是否上面有团队长，如果有则按奖励公式给予团队长进行奖励，并作记录
			int enable1 = Convert.strToInt(
					relationLevel1Map.get("enable") + "", -1);// 是否和上一级解除关系
			if (enable1 == 1) {
				long parentId1 = Convert.strToLong(relationLevel1Map
						.get("level1userId")
						+ "", -1);
				level1UserId = parentId1;
				level1Money = level2Money.multiply(new BigDecimal(
						longTeamRewardRate));

			}
			if (level2enable != 1) {// 如果经纪人被禁用了则不能获得奖励
				level2Money = BigDecimal.ZERO;
			}
			if (level1enable != 1) {// 如果团队长被禁用了则不能获得奖励
				level1Money = BigDecimal.ZERO;
			}
			awardDao.addAward(conn, userId, level2UserId, level2Money,
					level1UserId, level1Money, investOrRepaymentId, type, 2,
					moneys, IConstants.MX_TYPE_MAX, -1, level);
			// 并把当前总的待收本金赋值给历史最大待收本金，并作记录
			MySQL.executeNonQuery(conn, " update t_user set xmax = " + moneys
					+ " where id =" + userId);
		}
		// 如果当前总的待收本金小于历史最大待收本金，则不作任何处理
		return 0;
	}

	/**
	 * 奖励机制2
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int award2(Connection conn, long userId,
			Map<String, String> userMap, BigDecimal money, int type)
			throws SQLException {
		BigDecimal x = new BigDecimal(userMap.get("x"));
		BigDecimal xmin = new BigDecimal(userMap.get("xmin"));
		// 获得当前用户的x变量，xmin变量
		// 根据用户动作判断x变量是加或减
		if (type == IConstants.MONEY_TYPE_1) {
			x = x.add(money);// +
		} else {
			x = x.subtract(money);// -
		}
		// x变量处理完后，与xmin进行比较，取小值赋给xmin，
		if (x.compareTo(xmin) == -1) {
			MySQL.executeNonQuery(conn, " update t_user set xmin = " + x
					+ ",x=" + x + " where id =" + userId);
		}
		MySQL.executeNonQuery(conn, " update t_user set x=" + x + " where id ="
				+ userId);
		return 0;
	}

	/**
	 * 每月奖励
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void monthAward() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = new Date();
		// Date endDate = sdf1.parse("2014-05-01");
		String endDateStr1 = sdf1.format(endDate);
		endDate = sdf1.parse(endDateStr1);
		Date startDate = sdf1.parse(endDateStr1);
		startDate.setYear(startDate.getYear() - 1);
		String endDateStr = sdf.format(endDate);
		String startDateStr = sdf.format(startDate);// 为了减小误差，程序启动可能会中间误差几秒，我们这里把时间定为每月的00时00分00秒

		// startDate 一年前开始至 endDate
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>();
		pageBean.setPageNum(1);
		pageBean.setPageSize(5000);
		String condition = " AND addDate >'" + startDateStr + "' AND addDate<'"
				+ endDateStr + "' ";
		Connection conn = MySQL.getConnection();
		try {
			monthAwardInfo(conn, " v_t_relation_award_level3 ", condition
					+ " AND level = 3 ", pageBean, endDate,
					IConstants.RELATION_LEVEL3);// 递归计算，每次计算5000人的奖励机制，投资人
			monthAwardInfo(conn, " v_t_relation_award_level4 ", condition
					+ " AND level = 4 ", pageBean, endDate,
					IConstants.RELATION_LEVEL4);// 递归计算，每次计算5000人的奖励机制，理财人
			MySQL.executeNonQuery(conn,
					" update t_user set xmin = x where createTime >'"
							+ startDateStr + "' AND createTime<'" + endDateStr
							+ "'");// 把x赋值给xmin
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 每月计算奖金
	 * 
	 * @param conn
	 * @param condition
	 * @param pageBean
	 * @param endDate
	 * @throws Exception
	 */
	public void monthAwardInfo(Connection conn, String table, String condition,
			PageBean<Map<String, Object>> pageBean, Date endDate, int level)
			throws Exception {
		dataPage(conn, pageBean, table, " * ", "", condition
				+ " AND xmin>0 AND level2enable = 1 ");// 如果当月xmin为0，与经纪人解除了关系。说明没有奖励的必要了
		List<Map<String, Object>> list = pageBean.getPage();
		if (list == null || list.size() <= 0) {
			return;
		}
		pageBean.setPage(null);// 把本次处理的记录清空，
		int level1enalbe = -1;
		BigDecimal xmin = null;
		BigDecimal level2money = null;
		BigDecimal level1money = null;
		long level2userId = 0;
		long level1userId = 0;
		long userId = 0;
		int level2status = -1;
		int level1status = -1;
		BigDecimal goodsMoney1 = new BigDecimal("0.0006");// 第一个标的所奖励比例
		BigDecimal goodsMoney2 = new BigDecimal("0.0008");// 第二个标的所奖励比例
		BigDecimal level1m = new BigDecimal("0.25");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int month;
		for (Map<String, Object> map : list) {
			level1enalbe = Convert.strToInt(map.get("level1enable") + "", -1);
			userId = Convert.strToInt(map.get("userId") + "", -1);
			level2status = Convert.strToInt(map.get("level2status") + "", -1);
			level1status = Convert.strToInt(map.get("level1status") + "", -1);
			xmin = null;
			level2money = null;
			level1money = null;
			level2userId = -1;
			level1userId = -1;
			month = 0;

			level2userId = Convert.strToInt(map.get("level2userId") + "", -1);
			xmin = new BigDecimal(map.get("xmin") + "");
			level2money = xmin.multiply(goodsMoney1).add(
					xmin.multiply(goodsMoney2));// 标的1取万分之6，标的2取万分之8……………………………………………………………………此处可以用于扩展不同的标的
			if (level1enalbe == 1) {// 经纪人与团队长有关联，这里所有的钱都是管理员拿
				level1userId = Convert.strToInt(map.get("level1userId") + "",
						-1);
				level1money = level2money.multiply(level1m);
			}
			month = timeForMonth(endDate, Convert.strToStr(map.get("addDate")
					+ "", null), sdf);
			if (level2status != 1) {// 如果经纪人被禁用了则不能获得奖励
				level2money = BigDecimal.ZERO;
			}
			if (level1status != 1) {// 如果团队长被禁用了则不能获得奖励
				level1money = BigDecimal.ZERO;
			}
			awardDao.addAward(conn, userId, level2userId, level2money,
					level1userId, level1money, -1, -1, 2, xmin,
					IConstants.MX_TYPE_MIN, month, level);// 记录获得奖金记录
			map = null;
		}
		xmin = null;
		level2money = null;
		level1money = null;
		goodsMoney1 = null;
		goodsMoney2 = null;
		level1m = null;
		sdf = null;
		if (list != null && list.size() == 5000) {// 如果不等于null，每页展示5000条，如果相等，说明可能后面还有，由于每页展示数据是5000条，如果不与5000相等说明没有数据了。
			pageBean.setPageNum(pageBean.getPageNum());// 查询下一页5000条数据
			list = null;
			monthAwardInfo(conn, table, condition, pageBean, endDate, level);
		}
	}

	// ----------------------后台统计----------------------
	public void queryAwardLevel2(Long level1userId, Long level2userId,
			String level2userName, String userName, String realName,
			Integer level, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND level1userId = " + level1userId);
		}
		if (level2userId != null && level2userId > 0) {
			condition.append(" AND level2userId = " + level2userId);
		}
		if (StringUtils.isNotBlank(level2userName)) {
			condition
					.append(" AND level2userName like '%"
							+ StringEscapeUtils
									.escapeSql(level2userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (level != null && level > 0) {
			condition.append(" AND level = " + level);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_award_level2 ", "*", "", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	// ----------------------后台统计----------------------
	public Map<String, String> querySumAwardLevel2(Long level1userId,
			Long level2userId, String level2userName, String userName,
			String realName, Integer level) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND level1userId = " + level1userId);
		}
		if (level2userId != null && level2userId > 0) {
			condition.append(" AND level2userId = " + level2userId);
		}
		if (StringUtils.isNotBlank(level2userName)) {
			condition
					.append(" AND level2userName like '%"
							+ StringEscapeUtils
									.escapeSql(level2userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (level != null && level > 0) {
			condition.append(" AND level = " + level);
		}
		Connection conn = connectionManager.getConnection();
		try {
			DataSet dataSet = MySQL
					.executeQuery(
							conn,
							" select sum(level2moneys) as  sumLevel2moneys from  v_t_award_level2   where 1=1  "
									+ condition + "");

			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public void queryAwardLevel2mxType(Long level2userId, Long userId,
			Integer mxType, String startDate, String endDate, Integer month,
			Integer level, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level2userId != null && level2userId > 0) {
			condition.append(" AND level2userId = " + level2userId);
		}
		if (userId != null && userId > 0) {
			condition.append(" AND userId = " + userId);
		}
		if (mxType != null && mxType > 0) {
			condition.append(" AND mxType = " + mxType);
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" AND addDate >= '"
					+ StringEscapeUtils.escapeSql(startDate) + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" AND addDate <= '"
					+ StringEscapeUtils.escapeSql(endDate) + "'");
		}
		if (month != null && month > 0) {
			condition.append(" AND month = " + month);
		}
		if (level != null && level > 0) {
			condition.append(" AND level = " + level);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_award_mxType ", " * ", "", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryIoRInfo(Long userId, Long level2userId,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId > 0) {
			condition.append(" AND userId = " + userId);
		}
		if (level2userId != null && level2userId > 0) {
			condition.append(" AND level2userId = " + level2userId);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_award_ior_info ", " * ", "  ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 团队长提成统计
	 * 
	 * @param level1userId
	 * @param level1userName
	 * @param level2userName
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryAwardLevel1(Long level1userId, String level1userName,
			String level2userName, PageBean<Map<String, Object>> pageBean,
			long adminId, String adminName) throws Exception {
		StringBuffer condition = new StringBuffer();
		condition.append(" where 1=1 ");
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND a.level1userId = " + level1userId);
		}
		if (StringUtils.isNotBlank(level1userName)) {
			condition
					.append(" AND d.userName like '%"
							+ StringEscapeUtils
									.escapeSql(level1userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(level2userName)) {
			condition
					.append(" AND e.userName like '%"
							+ StringEscapeUtils
									.escapeSql(level2userName.trim()) + "%'");
		}
		if (adminId == 1) {
			condition.append(" AND d.userName = '"
					+ StringEscapeUtils.escapeSql(adminName.trim()) + "'");

		}
		StringBuffer topStr = new StringBuffer();
		StringBuffer countStr = new StringBuffer();
		StringBuffer paramStr = new StringBuffer();
		StringBuffer centerStr = new StringBuffer();
		topStr.append(" select ");
		countStr.append(" count(*) as totalNum ");
		paramStr.append(" a.level1userId as level1userId,");
		paramStr.append(" a.level2userId as level2userId,");
		paramStr.append(" d.userName as level1userName,");
		paramStr.append(" d.realName as level1realName,");
		paramStr.append(" e.userName as level2userName,");
		paramStr.append(" e.realName as level2realName,");
		paramStr.append(" e.card as card,");
		paramStr.append(" g.count3userId as count3userId,");
		paramStr.append(" b.level2money3 as level2money3,");
		paramStr.append(" j.count4userId as count4userId,");
		paramStr.append(" c.level2money4 as level2money4,");
		paramStr.append(" b.level1money3 as level1money3,");
		paramStr.append(" c.level1money4 as level1money4 ");
		centerStr.append(" from (select ");
		centerStr.append(" level1userId as level1userId,");
		centerStr.append(" level2userId as level2userId ");
		centerStr.append(" from t_award ");
		centerStr.append(" group by level1userId,level2userId) a ");
		centerStr.append(" left join ");
		centerStr.append(" (select  ");
		centerStr.append(" level1userId as level1userId,");
		centerStr.append(" level2userId as level2userId,");
		// centerStr.append(" count(userId) as count3userId,");
		centerStr.append(" sum(level2money) as level2money3,");
		centerStr.append(" sum(level1money) as level1money3 ");
		centerStr.append(" from t_award ");
		centerStr.append(" where `level` = 3 ");
		centerStr.append(" group by level1userId,level2userId) b ");
		centerStr
				.append(" on a.level1userId = b.level1userId and a.level2userId = b.level2userId ");
		centerStr.append(" left join  ");
		centerStr.append(" (select ");
		centerStr.append(" level1userId as level1userId,");
		centerStr.append(" level2userId as level2userId,");
		// centerStr.append(" count(userId) as count4userId,");
		centerStr.append(" sum(level2money) as level2money4,");
		centerStr.append(" sum(level1money) as level1money4 ");
		centerStr.append(" from t_award ");
		centerStr.append(" where `level` = 4 ");
		centerStr.append(" group by level1userId,level2userId) c ");
		centerStr
				.append(" on a.level1userId = c.level1userId and a.level2userId = c.level2userId ");
		centerStr.append(" left join t_admin d ");
		centerStr.append(" on a.level1userId = d.id ");
		centerStr.append(" left join t_admin e ");
		centerStr.append(" on a.level2userId = e.id ");
		centerStr
				.append(" left join (select count(f.peopleId) as count3userId,f.parentId as parentId from t_relation f where f.`level`=3 group by f.parentId ) g on a.level2userId=g.parentId ");
		centerStr
				.append(" left join (select count(h.peopleId) as count4userId,i.parentId as level2userId from t_relation h left join t_relation i on h.parentId = i.peopleId where h.`level`=4  group by i.parentId ) j on a.level2userId = j.level2userId");
		Connection conn = connectionManager.getConnection();
		StringBuffer command = new StringBuffer();
		try {
			long totalNum;
			String countSql = command.append(topStr).append(countStr).append(
					centerStr).append(condition).toString();
			DataSet dataSet = MySQL.executeQuery(conn, countSql);
			Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
			totalNum = Convert.strToLong(map.get("totalNum"), -1);
			boolean result = pageBean.setTotalNum(totalNum);
			if (result) {
				command = new StringBuffer();
				String querySql = command.append(topStr).append(paramStr)
						.append(centerStr).append(condition).toString()
						+ " limit "
						+ pageBean.getStartOfPage()
						+ " , "
						+ pageBean.getPageSize();
				DataSet ds = MySQL.executeQuery(conn, querySql);
				ds.tables.get(0).rows.genRowsMap();
				pageBean.setPage(ds.tables.get(0).rows.rowsMap);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 团队长提成统计
	 * 
	 * @param level1userId
	 * @param level1userName
	 * @param level2userName
	 * @param pageBean
	 * @throws Exception
	 */
	public Map<String, String> queryAwardLevel1Sum(Long level1userId,
			String level1userName, String level2userName, long adminId,
			String adminName) throws Exception {
		StringBuffer condition = new StringBuffer();
		condition.append(" where 1=1 ");
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND a.level1userId = " + level1userId);
		}
		if (StringUtils.isNotBlank(level1userName)) {
			condition
					.append(" AND d.userName like '%"
							+ StringEscapeUtils
									.escapeSql(level1userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(level2userName)) {
			condition
					.append(" AND e.userName like '%"
							+ StringEscapeUtils
									.escapeSql(level2userName.trim()) + "%'");
		}
		if (adminId == 1) {
			condition.append(" AND d.userName = '"
					+ StringEscapeUtils.escapeSql(adminName.trim()) + "'");

		}
		StringBuffer topStr = new StringBuffer();
		StringBuffer countStr = new StringBuffer();
		StringBuffer centerStr = new StringBuffer();
		topStr.append(" select ");
		countStr
				.append(" sum(ifnull(b.level1money3,0) + ifnull(c.level1money4,0)) as  leve1MoneySum ");
		centerStr.append(" from (select ");
		centerStr.append(" level1userId as level1userId,");
		centerStr.append(" level2userId as level2userId ");
		centerStr.append(" from t_award ");
		centerStr.append(" group by level1userId,level2userId) a ");
		centerStr.append(" left join ");
		centerStr.append(" (select  ");
		centerStr.append(" level1userId as level1userId,");
		centerStr.append(" level2userId as level2userId,");
		centerStr.append(" sum(level2money) as level2money3,");
		centerStr.append(" sum(level1money) as level1money3 ");
		centerStr.append(" from t_award ");
		centerStr.append(" where `level` = 3 ");
		centerStr.append(" group by level1userId,level2userId) b ");
		centerStr
				.append(" on a.level1userId = b.level1userId and a.level2userId = b.level2userId ");
		centerStr.append(" left join  ");
		centerStr.append(" (select ");
		centerStr.append(" level1userId as level1userId,");
		centerStr.append(" level2userId as level2userId,");
		centerStr.append(" sum(level2money) as level2money4,");
		centerStr.append(" sum(level1money) as level1money4 ");
		centerStr.append(" from t_award ");
		centerStr.append(" where `level` = 4 ");
		centerStr.append(" group by level1userId,level2userId) c ");
		centerStr
				.append(" on a.level1userId = c.level1userId and a.level2userId = c.level2userId ");
		centerStr.append(" left join t_admin d ");
		centerStr.append(" on a.level1userId = d.id ");
		centerStr.append(" left join t_admin e ");
		centerStr.append(" on a.level2userId = e.id ");
		centerStr
				.append(" left join (select count(f.peopleId) as count3userId,f.parentId as parentId from t_relation f where f.`level`=3 group by f.parentId ) g on a.level2userId=g.parentId ");
		centerStr
				.append(" left join (select count(h.peopleId) as count4userId,i.parentId as level2userId from t_relation h left join t_relation i on h.parentId = i.peopleId where h.`level`=4  group by i.parentId ) j on a.level2userId = j.level2userId");
		Connection conn = connectionManager.getConnection();
		StringBuffer command = new StringBuffer();
		try {
			String countSql = command.append(topStr).append(countStr).append(
					centerStr).append(condition).toString();
			DataSet dataSet = MySQL.executeQuery(conn, countSql);
			topStr = null;
			countStr = null;
			centerStr = null;
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 提成明细
	 * 
	 * @param parentId
	 * @param enable
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryLevel2Award(Long parentId, Integer enable,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (parentId != null && parentId > 0) {
			condition.append(" AND parentId = " + parentId);
		}
		if (enable != null && enable > 0) {
			condition.append(" AND enable = " + enable);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_level2_award ", "*", "", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 提成明细（合计）
	 * 
	 * @param parentId
	 * @param enable
	 * @param pageBean
	 * @throws Exception
	 */
	public Map<String, String> queryLevel2AwardSum(Long parentId, Integer enable)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (parentId != null && parentId > 0) {
			condition.append(" AND parentId = " + parentId);
		}
		if (enable != null && enable > 0) {
			condition.append(" AND enable = " + enable);
		}
		Connection conn = connectionManager.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn,
					" select sum(level2moneys) as level2moneySum from v_t_level2_award where 1=1  "
							+ condition + "");
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 理财人 --- 投资人 --提成奖励
	 * 
	 * @param level1userId
	 * @param level2userName
	 * @param level
	 * @param startDate
	 * @param endDate
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryLevel1level34(Long level1userId, String level2userName,
			Integer level, String startDate, String endDate,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND level1userId = " + level1userId);
		}
		if (level != null && level > 0) {
			condition.append(" AND level = " + level);
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" AND addDate >= '"
					+ StringEscapeUtils.escapeSql(startDate) + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" AND addDate <= '"
					+ StringEscapeUtils.escapeSql(endDate) + "'");
		}
		if (StringUtils.isNotBlank(level2userName)) {
			condition
					.append(" AND level2userName like '%"
							+ StringEscapeUtils
									.escapeSql(level2userName.trim()) + "%'");
		}

		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_level1_34 ", "*", "", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 理财人 --- 投资人 --提成奖励合计
	 * 
	 * @param level1userId
	 * @param level2userName
	 * @param level
	 * @param startDate
	 * @param endDate
	 * @param pageBean
	 * @throws Exception
	 */
	public Map<String, String> queryLevel1level34Sum(Long level1userId,
			String level2userName, Integer level, String startDate,
			String endDate) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND level1userId = " + level1userId);
		}
		if (level != null && level > 0) {
			condition.append(" AND level = " + level);
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" AND addDate >= '"
					+ StringEscapeUtils.escapeSql(startDate) + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" AND addDate <= '"
					+ StringEscapeUtils.escapeSql(endDate) + "'");
		}
		if (StringUtils.isNotBlank(level2userName)) {
			condition
					.append(" AND level2userName like '%"
							+ StringEscapeUtils
									.escapeSql(level2userName.trim()) + "%'");
		}

		Connection conn = connectionManager.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn,
					" select  sum(level2moneys) as level2moneySum from  v_t_level1_34  where 1=1 "
							+ condition + "");
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 经纪人提成总计
	 * 
	 * @param level1userId
	 * @param level2userId
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryLevel2Moneys(Long level1userId,
			Long level2userId, Long userId, Integer level, Integer mxType)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level1userId != null && level1userId > 0) {
			condition.append(" and level1userId =" + level1userId);
		}
		if (level2userId != null && level2userId > 0) {
			condition.append(" and level2userId =" + level2userId);
		}
		if (userId != null && userId > 0) {
			condition.append(" and userId =" + userId);
		}
		if (level != null && level > 0) {
			condition.append(" and level =" + level);
		}
		if (mxType != null && mxType > 0) {
			condition.append(" and mxType =" + mxType);
		}
		Connection conn = connectionManager.getConnection();
		try {
			DataSet dataSet = MySQL
					.executeQuery(
							conn,
							" select sum(level2money) as moneys,sum(level1money) as level1moneys,level1userId as level1userId ,level2userId as level2userId   from t_award where 1=1 "
									+ condition.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryLevel2AwardMoneys(Long parentId)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (parentId != null && parentId > 0) {
			condition.append(" AND parentId = " + parentId);
		}
		Connection conn = connectionManager.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn,
					" select sum(level2moneys) as moneys from v_t_level2_award where 1=1 "
							+ condition.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryIorMoneys(Long level2userId, Long userId)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level2userId != null && level2userId > 0) {
			condition.append(" AND level2userId = " + level2userId);
		}
		if (userId != null && userId > 0) {
			condition.append(" AND userId = " + userId);
		}
		Connection conn = connectionManager.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn,
					" select sum(level2money) as moneys from v_t_award_ior_info where 1=1 "
							+ condition.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	private int timeForMonth(Date endDate, String addDateStr,
			SimpleDateFormat sdf) throws ParseException {
		Date addDate = sdf.parse(addDateStr);
		try {
			if (addDate.getYear() == endDate.getYear()) {
				return endDate.getMonth() - addDate.getMonth();
			} else {
				return 12 - addDate.getMonth() + endDate.getMonth();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			endDate = null;
			addDate = null;
			addDateStr = null;
		}
		return 0;
	}

	public void queryAllLevel1Info(String userName, String realName,
			PageBean<Map<String, Object>> pageBean, int roleId)
			throws SQLException, DataException {
		Connection conn = Database.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			StringBuffer condition1 = new StringBuffer();
			StringBuffer condition2 = new StringBuffer();
			// condition.append();
			String fields = "b.id  AS  id , b.userName  AS  userName , b.realName AS  realName ,IFNULL(c.totalMoney , 0)AS  totalMoney ,IFNULL(d.hasPaySum , 0)AS  hasPaySum ,( IFNULL( totalMoney , 0)- IFNULL( hasPaySum , 0) )AS  forPaySum ";
			if (roleId == IConstants.RELATION_LEVEL1) {

				condition1.append(" t_admin b ");
				condition1
						.append(" LEFT JOIN( SELECT level1userId, sum(level1money) AS   totalMoney  FROM t_award   group BY level1userId ) c ON b.id  = c.level1userId  ");
				condition1
						.append("LEFT JOIN( SELECT  userId,sum(handleSum) AS  hasPaySum  FROM  t_award_detail  group BY userId )d ON b.id  = d. userId   ");
				if (StringUtils.isNotBlank(userName)) {
					condition.append(" and userName like '%"
							+ StringEscapeUtils.escapeSql(userName) + "%'");
				}
				if (StringUtils.isNotBlank(realName)) {
					condition.append(" and realName like '%"
							+ StringEscapeUtils.escapeSql(realName) + "%'");
				}
				condition.append("  and b.enable  = 1 AND b.roleId = 1 ");
				dataPage(conn, pageBean, condition1.toString(), fields, " ",
						condition.toString());

			} else if (roleId == IConstants.RELATION_LEVEL2) {// 经纪人

				condition2.append(" t_admin b ");
				condition2
						.append("LEFT JOIN( SELECT level2userId, sum(level2money)AS totalMoney FROM t_award  group BY level2userId ) c ON b.id = c.level2userId ");
				condition2
						.append("LEFT JOIN( SELECT userId, sum(handleSum)AS hasPaySum FROM t_award_detail  group BY userId ) d ON b.id = d.userId ");

				if (StringUtils.isNotBlank(userName)) {
					condition.append(" and userName like '%"
							+ StringEscapeUtils.escapeSql(userName) + "%'");
				}
				if (StringUtils.isNotBlank(realName)) {
					condition.append(" and realName like '%"
							+ StringEscapeUtils.escapeSql(realName) + "%'");
				}
				condition.append("  and b.enable = 1 AND b.roleId = 2 ");
				dataPage(conn, pageBean, condition2.toString(), fields, " ",
						condition.toString());
			}
			condition1 = null;
			fields = null;
			condition2 = null;
			condition = null;
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneLevelInfo(Long userId)
			throws SQLException, DataException {
		Connection conn = Database.getConnection();
		Map<String, String> map = null;
		try {
			map = awardDao.queryOneLevel1Info(conn, userId, -1, -1);
			if (map == null) {
				map = awardDao.queryOneLevel2Info(conn, userId, -1, -1);
			}

			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public void queryAwardDetailByUserId(Long userId, String startTime,
			String endTime, PageBean<Map<String, Object>> pageBean)
			throws SQLException, DataException {
		Connection conn = Database.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			if (userId != null) {
				condition.append(" and userId = " + userId);
			}
			if (StringUtils.isNotBlank(startTime)) {
				condition.append(" and  checkTime  >= '");
				condition.append(StringEscapeUtils.escapeSql(startTime));
				condition.append("' ");
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition.append(" and  checkTime <= '");
				condition.append(StringEscapeUtils.escapeSql(endTime));
				condition.append("' ");
			}
			String fields = "id,userId,handleSum,checkTime ,userName,realName,checkName,remark";
			String result = "(SELECT b.id,b.userId,b.handleSum, checkTime,"
					+ "c.userName ,c.realName ,(SELECT userName from t_admin where id=b.checkId) as checkName , b.remark from t_award_detail b LEFT JOIN t_admin c on b.userId=c.id) u ";
			dataPage(conn, pageBean, result, fields,
					" order by checkTime desc ", condition.toString());
			fields = null;
			result = null;
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 团队长提成
	 * 
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryAwardT(long leve1userId, String username,
			PageBean<Map<String, Object>> pageBean) throws SQLException,
			DataException {
		Connection conn = Database.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			if (leve1userId > 0) {
				condition.append(" and  id = " + leve1userId);
			}
			if (StringUtils.isNotBlank(username)) {
				condition.append(" and  userName  like  '%"
						+ StringEscapeUtils.escapeSql(username) + "%' ");
			}
			dataPage(conn, pageBean, " v_t_award_leve1 ", " * ", "", condition
					+ "");
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 团队长提成统计
	 * 
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAwardTSum(long leve1userId, String username)
			throws Exception {
		Connection conn = Database.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			if (leve1userId > 0) {
				condition.append(" and  id = " + leve1userId);
			}
			if (StringUtils.isNotBlank(username)) {
				condition.append(" and  userName  like  '%"
						+ StringEscapeUtils.escapeSql(username) + "%' ");
			}
			DataSet ds = MySQL.executeQuery(conn,
					" select sum(level1money) as level1moneySum from  v_t_award_leve1  where 1=1 "
							+ condition + "");
			return BeanMapUtils.dataSetToMap(ds);
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 经济人提成
	 * 
	 * @param leve2Name
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public void queryLeve2SumCount(long leve2userId, String leve2Name,
			PageBean<Map<String, Object>> pageBean) throws DataException,
			SQLException {
		StringBuffer condition = new StringBuffer();
		if (leve2userId > 0) {
			condition.append(" and  id = " + leve2userId);
		}
		if (StringUtils.isNotBlank(leve2Name)) {
			condition.append(" and  userName like '%"
					+ StringEscapeUtils.escapeSql(leve2Name) + "%' ");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_award_leve2_sum_money ", " * ", "",
					condition + "");
			conn.commit();
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
	}

	/**
	 * 经济人提成 统计
	 * 
	 * @param leve2userId
	 * @param leve2Name
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryLeve2CountToMap(long leve2userId,
			String leve2Name) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (leve2userId > 0) {
			condition.append(" and  id = " + leve2userId);
		}
		if (StringUtils.isNotBlank(leve2Name)) {
			condition.append(" and  userName like '%"
					+ StringEscapeUtils.escapeSql(leve2Name) + "%' ");
		}
		Connection conn = MySQL.getConnection();
		try {
			DataSet dataSet = MySQL
					.executeQuery(
							conn,
							" select sum(level2money) as sumLeve2Money from v_t_award_leve2_sum_money where 1=1 "
									+ condition.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	/**
	 * 明细查询
	 * 
	 * @param level1userId
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryLevel1AwardMoneys(Long level1userId, long level2userId,
			String username, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND level1userId = " + level1userId);
		}
		if (level2userId > 0) {
			condition.append(" AND level2userId = " + level2userId);
		}
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND username = '"
					+ StringEscapeUtils.escapeSql(username) + "'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_award_detail  ", " * ", "",
					condition + "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 明细查询
	 * 
	 * @param level1userId
	 * @param pageBean
	 * @throws Exception
	 */
	public Map<String, String> queryLevel1Sum(Long level1userId,
			long level2userId, String username) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (level1userId != null && level1userId > 0) {
			condition.append(" AND level1userId = " + level1userId);
		}
		if (level2userId > 0) {
			condition.append(" AND level2userId = " + level2userId);
		}
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND username = '"
					+ StringEscapeUtils.escapeSql(username) + "'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn,
					" select sum(level1money) as level1money from v_t_award_detail where 1=1 "
							+ condition.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryEconomyAwardList(String userName,
			PageBean<Map<String, Object>> pageBean, String name)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(name)) {
			condition.append(" AND realName <= '"
					+ StringEscapeUtils.escapeSql(name.trim()) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_economy_award ", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryAwardDetailInfo(String userName,
			PageBean<Map<String, Object>> pageBean, String name, Long id)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (id > 0) {
			condition.append(" AND id = " + id);
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(name)) {
			condition.append(" AND realName <= '"
					+ StringEscapeUtils.escapeSql(name.trim()) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_award_list ", " * ", " ", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryNotUseById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn,
					" select level2userId ,notuse from v_notuse_money where level2userId =  "
							+ id);
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public void awardSettlement(PageBean<Map<String, Object>> pageBean, Long id)
			throws Exception {
		StringBuffer condition = new StringBuffer();
		if (id > 0) {
			condition.append(" AND userId = " + id);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_detail ", " * ", " ", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void addEconomyAwardDetail(long userId, double handleSum,
			long checkId, String remark) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			awardDetailDao.addAwardDetail(conn, userId, handleSum, checkId,
					new Date(), remark);
			awardDao.updataStatu(conn, userId);
			MySQL.executeNonQuery(conn,
					" update t_user set usableSum = usableSum + " + handleSum
							+ " where id =" + userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryEconomyById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn,
					" select * from t_economy_list where id =  " + id);
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	public Map<String, String> queryEconomyByName(Connection conn, String name)
			throws Exception {
		DataSet ds = MySQL.executeQuery(conn,
				" select * from t_economy_list where username =  '" + name+"'");
		return BeanMapUtils.dataSetToMap(ds);

	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAwardDao(AwardDao awardDao) {
		this.awardDao = awardDao;
	}

	public void setAwardLevel4Dao(AwardLevel4Dao awardLevel4Dao) {
		this.awardLevel4Dao = awardLevel4Dao;
	}

	public AwardDetailDao getAwardDetailDao() {
		return awardDetailDao;
	}

	public void setAwardDetailDao(AwardDetailDao awardDetailDao) {
		this.awardDetailDao = awardDetailDao;
	}

	public RelationDao getRelationDao() {
		return relationDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public AwardDao getAwardDao() {
		return awardDao;
	}

	public AwardLevel4Dao getAwardLevel4Dao() {
		return awardLevel4Dao;
	}

}
