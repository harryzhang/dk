package com.sp2p.action.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.config.ChinaPnrConfig;
import com.shove.util.JSONUtils;
import com.shove.web.action.BasePageAction;
import com.shove.web.action.ChinaPnrmentAction;
import com.shove.web.util.ExcelUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.merService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * 汇付商户管理action类
 */
@SuppressWarnings({ "serial", "unchecked" })
public class merAction extends BasePageAction {
	private merService merService;

	public void setMerService(merService merService) {
		this.merService = merService;
	}

	/** 充值初始化(专属户) */
	public String merRechargeInit() {
		return SUCCESS;
	}

	/** 提交充值(专属户) */
	public String merRecharge() throws Exception {
		double amount = Convert.strToDouble(request("amount"), -1);
		String subAcct = Convert.strToStr(request("subAcct"), "");
		String cardDcFlag = Convert.strToStr(request("cardDcFlag"), null);
		if (!"cfb".equals(subAcct)) {
			JSONUtils.printStr2("目前只支持彩付宝专属户充值");
			return null;
		}
		if (amount <= 0) {
			JSONUtils.printStr2("充值金额错误");
			return null;
		}
		if (cardDcFlag == null) {
			JSONUtils.printStr2("请选择银行卡类型");
			return null;
		}
		subAcct = ChinaPnrConfig.chinapnr_cfb;
		String html = merService.merRecharge(subAcct, amount, cardDcFlag);
		sendHtml(html);
		return null;
	}

	/**
	 * 子账户转账初始化
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 */
	public String merRechargeIndex() throws Exception {
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.queryAccts());
		JSONArray js = json.getJSONArray("AcctDetails");
		List<Map<String, String>> bean = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < js.size(); i++) {
			JSONObject j = js.getJSONObject(i);
			map = new HashMap<String, String>();
			map.put("SubAcctId", j.getString("SubAcctId"));// 子账户
			map.put("AcctBal", j.getString("AcctBal"));// 账户余额
			map.put("FrzBal", j.getString("FrzBal"));// 冻结
			map.put("AvlBal", j.getString("AvlBal"));// 可用
			bean.add(map);
		}
		request().setAttribute("bean", bean);
		return SUCCESS;
	}

	/**
	 * 子账户转账
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String merRechargeTransfer() throws Exception {
		double amount = Convert.strToDouble(request("amount"), -1);
		String outSubAcct = Convert.strToStr(request("outSubAcct"), "");
		String inSubAcct = Convert.strToStr(request("inSubAcct"), "");
		Admin admin = (Admin) session("admin");
		if (amount <= 0) {
			JSONUtils.printStr2("充值金额错误");
			return null;
		}
		if (outSubAcct == null || outSubAcct.equals("")) {
			JSONUtils.printStr2("请选择转出账户");
			return null;
		}
		if (inSubAcct == null || inSubAcct.equals("")) {
			JSONUtils.printStr2("请选择入出账户");
			return null;
		}
		if (outSubAcct == inSubAcct || outSubAcct.equals(inSubAcct)) {
			JSONUtils.printStr2("转入与转出子账户一致");
			return null;
		}
		String html = merService.merRechargeTransfer(admin.getId(), amount, outSubAcct, inSubAcct);
		JSONUtils.printStr2(html);
		return null;
	}

	/** 充值记录 */
	public String queryMerRechargeList() throws Exception {
		String start = Convert.strToStr(request("start"), null);
		String end = Convert.strToStr(request("end"), null);
		request().setAttribute("start", start);
		request().setAttribute("end", end);
		int num = Convert.strToInt(request("num"), 1);
		pageBean.setPageNum(num);
		merService.queryMerRechargeList(pageBean, start, end);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 交易状态初始化
	 */
	public String queryTransStatusIndex() {
		return SUCCESS;
	}

	/**
	 * 订单号初始化
	 */
	public String queryTransStatusListIndex() {
		return SUCCESS;
	}

	/**
	 * 查询订单号
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 */
	public String queryOrdidList() throws Exception {
		String userName = Convert.strToStr(request("userName"), null);
		String startTime = Convert.strToStr(request("startTime"), null);
		String endTime = Convert.strToStr(request("endTime"), null);
		String queryTrade = Convert.strToStr(request("queryTrade"), null);
		// if(userName == null)
		// {
		// JSONUtils.printStr("请输入用户名!");
		// return null;
		// }
		// if(startTime == null)
		// {
		// JSONUtils.printStr("请选择开始时间!");
		// return null;
		// }
		// if(endTime == null)
		// {
		// JSONUtils.printStr("请选择结束时间!");
		// return null;
		// }
		// if(queryTrade == null)
		// {
		// JSONUtils.printStr("请选择交易类型!");
		// return null;
		// }
		request().setAttribute("startTime", startTime);
		request().setAttribute("queryTrade", queryTrade);
		int num = Convert.strToInt(request("num"), 1);
		pageBean.setPageNum(num);
		List<Map<String, Object>> map = merService.queryOrdIdList(userName, startTime, endTime, queryTrade);
		pageBean.setPage(map);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 汇付查询交易状态
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 */
	public String queryTransStatus() throws Exception {
		String ordId = Convert.strToStr(paramMap.get("ordId"), null);
		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String queryTrade = Convert.strToStr(paramMap.get("queryTrade"), null);
		if (ordId == null || startTime == null || queryTrade == null) {
			JSONUtils.printStr2("系统异常!");
			return null;
		}
		startTime = startTime.replaceAll("-", "");
		try {
			String json = merService.queryTransStatus(ordId, startTime, queryTrade);
			JSONUtils.printStr2(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String merCashCheck() {
		return SUCCESS;
	}

	public String merRechargeCheck() {
		return SUCCESS;
	}

	public String merInvestCheck() {
		return SUCCESS;
	}

	public String merBackCheck() {
		return SUCCESS;
	}

	/** 取现对账 */
	public String merCashCheckList() throws Exception {
		String beginDate = request("beginDate");
		String endDate = request("endDate");
		if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
			JSONUtils.printStr2("请选择时间");
			return null;
		}
		String pageNum = pageBean.getPageNum() + "";// 页数
		String pageSize = "10";
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.cashReconciliation(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), pageSize, pageNum));
		if (json.getInt("RespCode") != 0) {
			JSONUtils.printStr2("失败: " + json.getString("RespDesc"));
			return null;
		}
		try {
			pageBean.setPage((List) json.get("CashReconciliationDtoList"));
			merService.getUsernameByCustId(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pageBean.setTotalNum(json.getLong("TotalItems"));
		int pagNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pagNum);
		request().setAttribute("beginDate", beginDate);
		request().setAttribute("endDate", endDate);
		return SUCCESS;
	}

	/** 充值对账 */
	public String merRechargeCheckList() throws Exception {
		String beginDate = request("beginDate");
		String endDate = request("endDate");
		if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
			JSONUtils.printStr2("请选择时间");
			return null;
		}
		String pageNum = pageBean.getPageNum() + "";// 页数
		String pageSize = "10";
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.saveReconciliation(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), pageSize, pageNum));
		if (json.getInt("RespCode") != 0) {
			JSONUtils.printStr2("失败: " + json.getString("RespDesc"));
			return null;
		}
		try {
			pageBean.setPage((List) json.get("SaveReconciliationDtoList"));
			merService.getUsernameByCustId(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pageBean.setTotalNum(json.getLong("TotalItems"));
		int pagNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pagNum);
		request().setAttribute("beginDate", beginDate);
		request().setAttribute("endDate", endDate);
		return SUCCESS;
	}

	/** 投标对账 */
	public String merInvestCheckList() throws Exception {
		String beginDate = request("beginDate");
		String endDate = request("endDate");
		String type = request("type");// LOANS放款交易查询 REPAYMENT还款交易查询
		if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
			JSONUtils.printStr2("请选择时间");
			return null;
		}
		if (StringUtils.isBlank(type)) {
			JSONUtils.printStr2("请选择类型");
			return null;
		}
		String pageNum = pageBean.getPageNum() + "";// 页数
		String pageSize = "10";
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.reconciliation(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), pageSize, pageNum, type));
		if (json.getInt("RespCode") != 0) {
			JSONUtils.printStr2("失败: " + json.getString("RespDesc"));
			return null;
		}
		try {
			pageBean.setPage((List) json.get("ReconciliationDtoList"));
			merService.getUsernameByCustId2(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pageBean.setTotalNum(json.getLong("TotalItems"));
		int pagNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pagNum);
		request().setAttribute("beginDate", beginDate);
		request().setAttribute("endDate", endDate);
		return SUCCESS;
	}

	/** 商户扣款对账 */
	public String merBackCheckList() throws Exception {
		String beginDate = request("beginDate");
		String endDate = request("endDate");
		if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
			JSONUtils.printStr2("请选择时间");
			return null;
		}
		String pageNum = pageBean.getPageNum() + "";// 页数
		String pageSize = "10";
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.trfReconciliation(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), pageSize, pageNum));
		if (json.getInt("RespCode") != 0) {
			JSONUtils.printStr2("失败: " + json.getString("RespDesc"));
			return null;
		}
		try {
			pageBean.setPage((List) json.get("TrfReconciliationDtoList"));
			merService.getUsernameByCustId(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pageBean.setTotalNum(json.getLong("TotalItems"));
		int pagNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pagNum);
		request().setAttribute("beginDate", beginDate);
		request().setAttribute("endDate", endDate);
		return SUCCESS;
	}

	/** 子账户取现初始化(专属户) */
	public String cfbMerCashInit() {
		return SUCCESS;
	}

	/** 提交取现(专属户) */
	public String cfbMerCash() throws Exception {
		double amount = Convert.strToDouble(request("amount"), -1);
		String subAcct = Convert.strToStr(request("subAcct"), "");
		// String cardDcFlag = Convert.strToStr(request("cardDcFlag"), null);
		Admin admin = (Admin) session("admin");
		if (!"cfb".equals(subAcct)) {
			JSONUtils.printStr2("目前只支持彩付宝专属户取现");
			return null;
		}
		if (amount <= 0) {
			JSONUtils.printStr2("充值金额错误");
			return null;
		}
		// subAcct = ChinaPnrConfig.chinapnr_cfb;
		subAcct = ChinaPnrConfig.chinapnr_merCustId;
		// if (cardDcFlag == null) {
		// JSONUtils.printStr2("请选择银行卡类型");
		// return null;
		// }
		String result = merService.addMerCash(amount, admin.getId() + "", admin.getId(), subAcct, admin.getUsrCustId());
		JSONUtils.printStr2(result);
		return null;
	}

	/**
	 * 
	 * 查询子账户取现记录
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 */
	public String queryCfbMerCashList() throws Exception {
		Admin admin = (Admin) session("admin");
		List<Map<String, Object>> map = merService.queryCfbMerCashList(admin.getId());
		pageBean.setPage(map);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/** 企业开户初始化 */
	public String companyRegInit() throws Exception {
		return SUCCESS;
	}

	/** 企业开户 */
	public String companyReg() throws Exception {
		String usrname = Convert.strToStr(request("usrname"), "");
		String busiCode = Convert.strToStr(request("busiCode"), "");
		if (StringUtils.isBlank(busiCode)) {
			JSONUtils.printStr2("请填写营业执照编号");
			return null;
		}
		if (StringUtils.isBlank(usrname)) {
			JSONUtils.printStr2("请填写企业全称");
			return null;
		}
		String guar = StringUtils.isBlank(Convert.strToStr(request("guar"), "")) ? "N" : "Y";

		String usrId = merService.getUsrId(busiCode);// 企业开户和普通开户usrId一致.
		if (StringUtils.isBlank(usrId)) {
			JSONUtils.printStr2("数据库异常");
			return null;
		}
		String html = ChinaPnRInterface.corpRegister(usrId, usrname, busiCode, guar);
		sendHtml(html);
		return null;
	}

	public String queryCompany() throws Exception {
		String code = request("code");
		JSONObject json = JSONObject.fromObject(ChinaPnRInterface.CorpRegisterQuery(code));

		String ret = null;
		if (json.getInt("RespCode") == 0) {
			ret = ChinaPnrmentAction.auditStatMap.get(json.get("AuditStat"));
		} else {
			ret = json.getString("RespDesc");
		}
		JSONUtils.printStr2(ret);
		return null;
	}

	/**
	 * 导出交易状态
	 */
	public String exportTransStatus() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			String userName = Convert.strToStr(request("userName"), null);
			String startTime = Convert.strToStr(request("startTime"), null);
			String endTime = Convert.strToStr(request("endTime"), null);
			String queryTrade = Convert.strToStr(request("queryTrade"), null);
			String status = request("status");
			int num = Convert.strToInt(request("num"), 1);
			pageBean.setPageNum(num);
			List<Map<String, Object>> map = merService.queryOrdIdList(userName, startTime, endTime, queryTrade);

			// 待还款详情
			if (map == null || map.size() == 0) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (status != "" && status != null) {
				String[] strs = status.split(",");
				for (int i = 0; i < strs.length; i++) {
					Map<String, Object> map2 = map.get(i);
					String str = new String(strs[i].getBytes("ISO8859-1"), "UTF-8");
					map2.put("status", str);
				}
			}
			if (map.size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("交易状态", map, new String[] { "订单号", "用户名", "金额 ", "时间 ", "结果状态" }, new String[] { "ordId", "userName", "money", "time",
					"status" });
			this.export(wb, new Date().getTime() + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*** 企业信息 */
	public String companyInfo() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long usrCustId = Convert.strToLong(admin.getUsrCustId(), 0);
		Map<String, String> map = merService.queryCompanyInfo(usrCustId, pageBean);
		if (map == null) {
			JSONUtils.printStr2("<h2 style='color:red;width:400px;margin: 50 auto 50 auto;'>您不是企业用户!</h2>");
			return null;
		}
		request().setAttribute("map", map);
		return SUCCESS;
	}

	/*** 企业绑卡 */
	public String bindCard() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long usrCustId = Convert.strToLong(admin.getUsrCustId(), -1);
		if (usrCustId <= 0) {
			JSONUtils.printStr2("您还不是汇付天下会员!");
			return null;
		}
		String html = ChinaPnRInterface.userBindCard(usrCustId + "");
		JSONUtils.printStr2(html);
		return null;
	}

	/*** 企业充值 */
	public String comRecharge() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (admin == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}

		double money = Convert.strToDouble(request("money"), 0);
		String DcFlag = Convert.strToStr(request("types"), null);
		String usrCustId = admin.getUsrCustId();

		if (!"D".equals(DcFlag) && !"C".equals(DcFlag)) {
			JSONUtils.printStr2("参数非法!");
			return null;
		}
		if (Convert.strToLong(usrCustId, -1) < 0) {
			JSONUtils.printStr2("您还不是汇付天下会员!");
			return null;
		}
		if (money <= 0) {
			JSONUtils.printStr2("金额非法!");
			return null;
		}

		String amount = new DecimalFormat("0.00").format(money);
		String date = DateUtil.dateToYMD(new Date());

		Map<String, String> result = merService.addRecharge(amount, usrCustId);
		if (result == null) {
			sendHtml("支付失败");
			return null;
		}
		String userId = result.get("userId");
		String html = ChinaPnRInterface.netSave("在线充值", result.get("ordId"), "", usrCustId, date, amount, "B2C", userId, ChinaPnrConfig.chinapnr_retUrl, DcFlag);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
		sendHtml(html);
		return null;
	}
	
	/*** 企业提现*/
	public String comWithdraw() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (admin == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}
		
		double money = Convert.strToDouble(request("money"), 0);
		String usrCustId = admin.getUsrCustId();
		
		if (Convert.strToLong(usrCustId, -1) < 0) {
			JSONUtils.printStr2("您还不是汇付天下会员!");
			return null;
		}
		if (money <= 0) {
			JSONUtils.printStr2("金额非法!");
			return null;
		}
		
		String amount = new DecimalFormat("0.00").format(money);
		money = Double.valueOf(amount);
		long ordId = merService.comWithdraw(money,usrCustId);
		
		if (ordId <= 0) {
			JSONUtils.printStr2("取现失败!");
			return null;
		}
		String html = ChinaPnRInterface.cash(ordId+"", usrCustId, amount, "");
		sendHtml(html);
		return null;
	}
}
