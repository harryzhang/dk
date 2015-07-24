package com.sp2p.action.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.ColumnCollection;
import com.shove.data.DataSet;
import com.shove.data.RowCollection;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.FinanceService;
import com.sp2p.service.GuaranteeService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;

public class SjbAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(SjbAction.class);
	private FinanceService financeService;
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}
	private static DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
	private static final NumberFormat doubleFormat = new DecimalFormat("0.00");
	public String sjb() {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if ( user != null){
			computeInvestScore(user.getId());
			String sql="select (investScore+jcScore) s from t_sjb_score where userId="+user.getId();
			int score = 0;
			Map<String, String> map= queryMap(sql);
			if( map !=null){
				score = Integer.parseInt(map.get("s"));
			}
			request().setAttribute("score", score);
			
			sql="SELECT IFNULL(sum(money),0) money from t_sjb_money where userId="+user.getId();
			double money = 0;
			map= queryMap(sql);
			if( map !=null){
				money = Double.parseDouble(map.get("money"));
			}
			request().setAttribute("money", doubleFormat.format(money));
			
			sql="SELECT IFNULL(SUM(investAmount),0)/100 s from t_invest where investor = "+user.getId()+" and date(investTime)=CURRENT_DATE()";
			map=queryMap(sql);
			request().setAttribute("investScore", (int)Double.parseDouble(map.get("s")));
//			System.out.println(investScore);
			sql="select t2.winTeam from t_sjb_game t1 left join t_sjb_jc t2 on t1.gameId=t2.gameId and userId="+user.getId()+" order by t1.gameId";
			List<Map<String, Object>> list = queryList(sql);
			StringBuilder sb=new StringBuilder();
			if(list!=null){
				for (Map<String, Object> map2 : list) {
					sb.append(map2.get("winTeam")==null?"":map2.get("winTeam")).append(",");
				}
				request().setAttribute("myJc", sb);
			}
			
		}
		
		List<Map<String, Object>> mapList;
		try {
			if (user!=null&&user.getUserGroup()==1) {
				String borrowStatus = " (2,3,4,5) ";
				pageBean.setPageNum(request("curPage"));
				pageBean.setPageSize(IConstants.PAGE_SIZE_6);
				financeService.queryBorrowByConditions(borrowStatus, null, null, "", "", "", "", "", "",
						IConstants.SORT_TYPE_DESC, pageBean, null, "", "", user.getUserGroup());
				if (pageBean.getPage()==null) {
					mapList = financeService.queryLastestBorrow();
					pageBean.setPage(mapList);
				}else if (pageBean.getPage().size()<6) {
					mapList = financeService.queryLastestBorrow();
					pageBean.getPage().addAll(mapList);
					if (pageBean.getPage().size()>6) {
						pageBean.setPage(pageBean.getPage().subList(0, 6));
					}
				}
				request().setAttribute("mapList", pageBean.getPage());
			}else{
				mapList = financeService.queryLastestBorrow();
				request().setAttribute("mapList", mapList);
			}
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sql="select username,userId,score from t_sjb_top where score>0 order by score desc limit 20;";
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn, sql);
			List<Map<String, Object>> lists=new ArrayList<Map<String,Object>>();
			RowCollection rowCollection = ds.tables.get(0).rows;
			ColumnCollection columnCollection = ds.tables.get(0).columns;
			for (int i = 0; i < rowCollection.getCount(); i++) {
				Map<String, Object> paramMap =  new HashMap<String, Object>();
				for (int j = 0; j < columnCollection.getCount(); j++) {
					paramMap.put(columnCollection.get(j).getName(),rowCollection
							.get(i).get(j));
				}
				lists.add(paramMap);
			}
			request().setAttribute("tops", lists);
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
		
		sql="select * from t_sjb_game where ";
		String d = dateFormat.format(new Date());
		
		return "sjb"+d;
	}
	
	public String jc() {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user!=null) {
			for(int i=1;i<=4;i++){
				int gameId=Convert.strToInt(request("gameId"+i),0);
				int winTeam=Convert.strToInt(request("winTeam"+i),0);
				if(gameId>0&&winTeam>0){
					String sql = "REPLACE t_sjb_jc (userId,gameId,winTeam,createTime) VALUES "
							+ "("+user.getId()+",'"+gameId+"',"+winTeam+",now()) ";
					executeSql(sql);
				}
			}
			/*String[] gameIds=request().getParameterValues("gameId");
			String[] winTeams=request().getParameterValues("gameId");
			if(gameIds!=null&&gameIds.length>0&&winTeams!=null&&winTeams.length>0){
				for(int i=0;i<gameIds.length;i++){
					int gameId=Convert.strToInt(gameIds[i],0);
					int winTeam=Convert.strToInt(winTeams[i],0);
					String sql = "REPLACE t_sjb_jc (userId,gameId,winTeam,createTime) VALUES "
							+ "("+user.getId()+",'"+gameId+"',"+winTeam+",now()) ";
					executeSql(sql);
				}
			}*/
		}else{
			return "login";
		}
		/*JSONObject json = new JSONObject();
		json.put("ret", "1");
		try {
			JSONUtils.printObject(json);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return SUCCESS;
		
	}
	public String dealJcResult() throws ParseException {
		String pwdString=request("pwd");
		if ("sdfdsfsx".equals(pwdString)) {
			String beginDate=request("beginDate");
			int x=1;
			Date js8Date=dateFormat.parse("20140627");
			Date js4Date=dateFormat.parse("20140703");
			Date js2Date=dateFormat.parse("20140707");
			Date jsDate=dateFormat.parse("20140711");
			Date nowDate=dateFormat.parse(beginDate);
			if(nowDate.after(jsDate)){
				x=5;
			}else if(nowDate.after(js2Date)){
				x=4;
			}else if(nowDate.after(js4Date)){
				x=3;
			}else if(nowDate.after(js8Date)){
				x=2;
			}
//			for (int i = 0; i < gameIds.length; i++) {
//				String sql="select * from t_sjb_jc where gameId="+gameIds[i]+" and winTeam="+winTeams[i];
				String sql="select t2.userId from t_sjb_game t1 join t_sjb_jc t2 on t1.gameId=t2.gameId and t1.status=0 and t1.result=t2.winTeam and t2.createTime<t1.endTime where beginTime ="+beginDate;;
				List<Map<String, Object>> list=queryList(sql);
				if (list!=null) {
					for (Map<String, Object> map : list) {
//						sql="select sum(InvestAmount) from t_invest where date(investTime)="+beginDate+" and invetor="+map.get("userId");
						sql="update t_sjb_score t1 join (select sum(InvestAmount) s,investor "+
						" from t_invest t3 where date(investTime)="+beginDate+" and investor="+map.get("userId")+") t2 "+
						"on t1.userId=t2.investor set t1.jcScore=t1.jcScore+t2.s/100*"+x	;
						executeSql(sql);
					}
				}
//			}
			sql="update t_sjb_game set status=1 where beginTime="+beginDate;
			executeSql(sql);
			 sql="replace t_sjb_top SELECT t1.userId,f_formatting_username(t2.username),(t1.investScore+t1.jcScore) as s from t_sjb_score t1 join t_user t2 on t1.userId=t2.id where (t1.investScore+t1.jcScore)>0 ORDER BY s desc limit 20  ";
			executeSql(sql);
			
			
		}
		/*String[] gameIds=request().getParameterValues("gameId");
		String[] winTeams=request().getParameterValues("winTeam");*/
		
		return SUCCESS;
	}
	public String resultx(){
		String sql="";
		if (StringUtils.isNotBlank(request("result"))) {
			String result=request("result");
			String []ss=result.split("-");
			if (ss.length==2) {
				sql="update t_sjb_game set result="+ss[1]+" where gameId="+ss[0];
				executeSql(sql);
			}
		}
		sql="select gameId,result from t_sjb_game where result>0 order by gameId";
		List<Map<String, Object>> listsList=queryList(sql);
		request().setAttribute("result", listsList);
		
		return SUCCESS;
	}
	
	private void computeInvestScore(long userId){
		String sql = "SELECT IFNULL(SUM(investAmount),0)/100 s from t_invest where investor = "+userId+" and investTime BETWEEN 20140612 and 20140714";
		Map<String, String> map = queryMap(sql);
		String string = map.get("s");
//		return string;
		sql = "insert into t_sjb_score(userId,investScore,lastupdateTime) values("+userId+","+string+",now()) "
				+ " on DUPLICATE KEY update lastupdateTime =now(), investScore ="+string;
		executeSql(sql);
	}
	
	
	private Map<String, String> queryMap(String sql){
		Connection conn = BaseService.connectionManager.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn, sql);
			Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
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
	
	private void executeSql(String sql) {
		Connection conn = MySQL.getConnection();
		MySQL.executeNonQuery(conn, sql);
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	private List<Map<String, Object>> queryList(String sql) {
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = MySQL.executeQuery(conn, sql);
			List<Map<String, Object>> lists=new ArrayList<Map<String,Object>>();
			RowCollection rowCollection = ds.tables.get(0).rows;
			ColumnCollection columnCollection = ds.tables.get(0).columns;
			for (int i = 0; i < rowCollection.getCount(); i++) {
				Map<String, Object> paramMap =  new HashMap<String, Object>();
				for (int j = 0; j < columnCollection.getCount(); j++) {
					paramMap.put(columnCollection.get(j).getName(),rowCollection
							.get(i).get(j));
				}
				lists.add(paramMap);
			}
			return lists;
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
	 * 花样年专区首页
	 * @return
	 * @throws Exception
	 */
	public String hynIndex() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user.getUserGroup()==1) {
			String borrowStatus = " (2,3,4,5) ";
			pageBean.setPageNum(request("curPage"));
			pageBean.setPageSize(12);
			financeService.queryBorrowByConditions(borrowStatus, null, null, "", "", "", "", "", "",
					IConstants.SORT_TYPE_DESC, pageBean, null, "", "", user.getUserGroup());
		}else{
			return "noRight";
		}
		
		return SUCCESS;
	}
	/**
	 * 花样会专区首页
	 * @return
	 * @throws Exception
	 */
	public String hyhIndex() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user.getUserGroup()==2) {
			String borrowStatus = " (2,3,4,5) ";
			pageBean.setPageNum(request("curPage"));
			pageBean.setPageSize(12);
			financeService.queryBorrowByConditions(borrowStatus, null, null, "", "", "", "", "", "",
					IConstants.SORT_TYPE_DESC, pageBean, null, "", "", user.getUserGroup());
		}else{
			return "noRight";
		}
		
		return SUCCESS;
	}
	/*
	 * 各种专区
	 */
	public String zone() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		int id = Convert.strToInt(request("id"),0);
		if (id>0&&user.getUserGroup() == id) {
			String borrowStatus = " (2,3,4,5) ";
			pageBean.setPageNum(request("curPage"));
			pageBean.setPageSize(12);
			financeService.queryBorrowByConditions(borrowStatus, null, null, "", "", "", "", "", "",
					IConstants.SORT_TYPE_DESC, pageBean, null, "", "", user.getUserGroup());
		}else{
			return "noRight";
		}
		request().setAttribute("id", id);
		return SUCCESS;
	}
}
















