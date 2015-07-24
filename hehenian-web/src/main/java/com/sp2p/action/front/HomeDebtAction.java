package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.SelectedService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * 债权转让
 */
public class HomeDebtAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(HomeDebtAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;

	private AuctionDebtService auctionDebtService;

	private SelectedService selectedService;

	private List<Map<String, Object>> auctionDaysList;

	/**
	 * 可以转让的债权
	 * 
	 * @return
	 */
	public String queryCanAssignmentDebt() {
		String borrowTitle = SqlInfusion.FilteSqlInfusion(request("borrowTitle"));
		String borrowerName = SqlInfusion.FilteSqlInfusion(request("borrowerName"));
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
	
		long userId = this.getUserId();
		try {
			assignmentDebtService.queryCanAssignmentDebt(userId, borrowTitle,
					borrowerName, pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		this.setRequestToParamMap();
		return SUCCESS;
	}

	
	/**
	 * 结束竞拍
	 * @return
	 */
	public String auctingDebtEnd(){
		long debtId = Convert.strToLong(request("debtId"), -1);
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		try {
			assignmentDebtService.auctingDebtSuccess(debtId,user.getId(),1);
		} catch (SQLException e) {
		    log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 查询竞拍中的债权
	 * 
	 * @return
	 */
	public String queryAuctingDebt() {
		String borrowTitle = SqlInfusion.FilteSqlInfusion(request("borrowTitle"));
		String borrowerName = SqlInfusion.FilteSqlInfusion(request("borrowerName"));
		pageBean.setPageNum(request("curPage"));
	
		long userId = this.getUserId();
		try {
			assignmentDebtService.queryAuctingDebt(userId, borrowTitle,
					borrowerName, "2", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();

			if (list != null) {
				Date nowDate = new Date();
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("remainAuctionTime");
					map.put("remainDays", DateUtil.remainDateToString(nowDate,
							date));
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询竞拍结束的债权
	 * 
	 * @return
	 */
	public String queryAuctedDebt() {
		String borrowTitle = SqlInfusion.FilteSqlInfusion(request("borrowTitle"));
		String borrowerName = SqlInfusion.FilteSqlInfusion(request("borrowerName"));
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		
		long userId = this.getUserId();
		try {
			assignmentDebtService.queryAuctingDebt(userId, borrowTitle,
					borrowerName, "3", pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 添加债权转让
	 */
	public String addAssignmentDebt() throws IOException {
		long userId = this.getUserId();
//		double auctionBasePrice = Convert.strToDouble(paramMap.get("auctionBasePrice"), -1);
//		double debtSum = Convert.strToDouble(paramMap.get("debtSum"), -1);
//		double lowerPrice = debtSum*0.5;
		
//		if(auctionBasePrice < lowerPrice || auctionBasePrice > debtSum){
//			JSONUtils.printStr2("转让价格范围错误");
//			return null;
//		}
		paramMap.put("alienatorId", userId + "");
		paramMap.put("applyTime", DateUtil.dateToString(new Date()));
		long reslut = -1;
		try {
			reslut = assignmentDebtService.addAssignmentDebt(paramMap);
//			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.creditAssign(OrdId, SellCustId, CreditAmt, CreditDealAmt, BidDetails, Fee, DivDetails, BuyCustId, OrdDate);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		try {
			if (reslut != -1) {

				JSONUtils.printStr("1");

			} else {
				JSONUtils.printStr("-1");
			}
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询失败的债权
	 * 
	 * @return
	 */
	public String queryAuctionFailDebt() {
		String borrowTitle = SqlInfusion.FilteSqlInfusion(request("borrowTitle"));
		String borrowerName = SqlInfusion.FilteSqlInfusion(request("borrowerName"));
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));

		long userId = this.getUserId();
		try {
			assignmentDebtService.queryAuctingDebt(userId, borrowTitle,
					borrowerName, "4,5,6,7", pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 取消申请债权转让
	 * 
	 * @return
	 */
	public String cancelApplyDebt() {
		long debtId = Convert.strToLong(request("debtId"), -1);
        AccountUserDo   user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		try {
			assignmentDebtService.cancelAssignmentDebt(debtId,5,user.getId(),1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 参与竞拍的债权
	 * 
	 * @return
	 */
	public String queryBuyingDebt() {
		String borrowTitle = SqlInfusion.FilteSqlInfusion(request("borrowTitle"));
		String startTime = SqlInfusion.FilteSqlInfusion(request("startTime"));
		String endTime = SqlInfusion.FilteSqlInfusion(request("endTime"));
		long userId = this.getUserId();
		pageBean.setPageNum(request("curPage"));
		
		try {
			auctionDebtService.queryAuctionDebt(borrowTitle, startTime,
					endTime, userId, "", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();

			if (list != null) {
				Date nowDate = new Date();
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("remainAuctionTime");
					map.put("remainDays", DateUtil.remainDateToString(nowDate,
							date));
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 成功竞拍的债权
	 * 
	 * @return
	 */
	public String querySucessBuyedDebt() {
		String borrowTitle = SqlInfusion.FilteSqlInfusion(request("borrowTitle"));
		String startTime = SqlInfusion.FilteSqlInfusion(request("startTime"));
		String endTime = SqlInfusion.FilteSqlInfusion(request("endTime"));
		long userId = this.getUserId();
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		try {
			auctionDebtService.querySuccessAuctionDebt(borrowTitle, startTime,
					endTime, userId, pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		this.setRequestToParamMap();
		return SUCCESS;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public List<Map<String, Object>> getAuctionDaysList() throws SQLException,
			DataException {
		auctionDaysList = selectedService.getDebtAuctionDays();
		return auctionDaysList;
	}

	public void setAuctionDaysList(List<Map<String, Object>> auctionDaysList) {
		this.auctionDaysList = auctionDaysList;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

}
