package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.RegionService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.ChinaPnRInterface;

@SuppressWarnings("serial")
public class UserBankManagerAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(LinksAction.class);
	private AdminService adminService;
	private List<Map<String, Object>> checkers;
	private FundManagementService fundManagementService;
	@SuppressWarnings("unused")
	private RegionService regionService; // 省市Service

	private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> cityList;

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	public String queryUserBankInit() throws SQLException, DataException {
		String types = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("types")), "-1");
		request().setAttribute("types", types);
		return SUCCESS;
	}

	@SuppressWarnings("unused")
	private long workPro = -1L;// 初始化省份默认值

	@SuppressWarnings("unchecked")
	public String queryUserBankList() throws DataException, SQLException {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);
		String checkUser = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("checkUser")), null);
		String commitTimeStart = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("commitTimeStart")), null);
		String commitTimeEnd = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("commitTimeEnd")), null);
		int cardStatus = Convert.strToInt(paramMap.get("cardStatus"), -1);
		/*
		 * String checkStartTime =
		 * Convert.strToStr(paramMap.get("checkTimeStart"), null); String
		 * checkTimeEnd = Convert.strToStr(paramMap.get("checkTimeEnd"), null);
		 * checkTimeEnd = FrontMyPaymentAction.changeEndTime(checkTimeEnd);
		 */

		commitTimeEnd = FrontMyPaymentAction.changeEndTime(commitTimeEnd);

		Long checkUserId = -100L;
		if (checkUser != null) {
			checkUserId = Convert.strToLong(checkUser, -100L);
		}
		try {
			// 加载银行卡信息
			fundManagementService.queryBankCardInfos(pageBean, userName, realName, checkUserId, commitTimeStart, commitTimeEnd, cardStatus);

			checkers = getCheckers();
			List<Map<String, Object>> ll = pageBean.getPage();
			if (ll != null && ll.size() > 0) {
				for (Map<String, Object> mp : ll) {
					if (mp.get("checkUser") != null) {
						String chechUser = mp.get("checkUser").toString();
						for (Map<String, Object> cc : checkers) {
							if (cc.get("id").toString().equals(chechUser)) {
								mp.put("checkUser", cc.get("userName"));
								break;
							}
						}
					}
				}
			}
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String queryUserModifiyBankList() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);
		// username 需要转换成 id 去搜条件
		String checkUser = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("checkUser")), null);
		String cStartTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("commitTimeStart")), null);
		String cEndTime = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("commitTimeEnd")), null);
		int cardStatus = Convert.strToInt(paramMap.get("cardStatus"), -1);

		cEndTime = FrontMyPaymentAction.changeEndTime(cEndTime);
		Long checkUserId = -100L;
		if (checkUser != null) {
			checkUserId = Convert.strToLong(checkUser, -100L);
		}
		try {
			// 加载银行卡信息
			fundManagementService.queryModifyBankCardInfos(pageBean, userName, realName, checkUserId, cStartTime, cEndTime, cardStatus);
			getCheckers();
			List<Map<String, Object>> ll = pageBean.getPage();
			if (ll != null && ll.size() > 0) {
				for (Map<String, Object> mp : ll) {
					if (mp.get("checkUser") != null) {
						String chechUser = mp.get("checkUser").toString();
						for (Map<String, Object> cc : checkers) {
							if (cc.get("id").toString().equals(chechUser)) {
								mp.put("checkUser", cc.get("userName"));
							}
						}
					}
				}
			}
			int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 审核 银行卡
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryOneBankCardInfo() throws DataException, SQLException {
		Long bankId = request("bankId") == null ? -1 : Convert.strToLong(request("bankId"), -1);
		try {
			// 加载银行卡信息
			paramMap = fundManagementService.queryBankCard(bankId);
			if (paramMap != null && paramMap.size() > 0) {
				if (SqlInfusion.FilteSqlInfusion(paramMap.get("mobilePhone")) == null || SqlInfusion.FilteSqlInfusion(paramMap.get("mobilePhone").trim()).equals("")) {
					SqlInfusion.FilteSqlInfusion(paramMap.put("mobilePhone", paramMap.get("cellPhone")));
				}
				request().setAttribute("paramMap", paramMap);
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;

	}

	/**
	 * 银行卡变更
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryModifyBankInfo() throws SQLException, DataException {
		Long bankId = request("bankId") == null ? -100L : Convert.strToLong(request("bankId"), -100);

		try {
			paramMap = fundManagementService.queryOneBankCardInfo(bankId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		request().setAttribute("bankId", bankId);
		return SUCCESS;
	}

	/**
	 * 银行卡审核
	 */
	public String updateUserBankInfo() throws Exception {
		// 汇付绑定银行卡
		String remark = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("remark")), "");
		Integer status = Convert.strToInt(paramMap.get("status"), -100);
		Long bankId = Convert.strToLong(paramMap.get("bankId"), -1);
		String usrCustId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("usrCustId")), ""); // 用户客户号
		String openBankId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("openBankId")), "CIB"); // 银行代号
		String openAcctId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("openAcctId")), ""); // 开户银行账号
		String openProvId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("provinceId")), ""); // 开户银行省份代号
		String openAreaId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cityId")), ""); // 开户银行地区代号
		String openBranchName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("openBranchName")), ""); // 开户支行
		String cmdId = "BgBindCard";

		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		Long result = -1L;
		if (status == 3) {
			result = fundManagementService.updateBankInfo(admin.getId(), bankId, remark, status, admin.getUserName(), admin.getLastIP());
			if (result > 0) {
				JSONUtils.printStr2("操作成功");
				return null;
			}
			JSONUtils.printStr2("操作失败");
			return null;
		}
		try {
			// 用户绑定银行卡
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.bgBindCard(cmdId, usrCustId, openBankId, openAcctId, openProvId, openAreaId,
					openBranchName));
			int ret = json.getInt("RespCode");
			if (ret == 0) {
				result = fundManagementService.updateBankInfo(admin.getId(), bankId, remark, status, admin.getUserName(), admin.getLastIP());
				JSONUtils.printStr2("操作成功");
			} else {
				JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
			}
			return null;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("系统出现异常");
			return null;
		}
	}

	/**
	 * 银行卡变更审核
	 */
	public String updateUserModifyBank() throws DataException, SQLException {
		Long checkUserId = paramMap.get("userName") == null ? null : Convert.strToLong(paramMap.get("userName"), -100);
		String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark") == null ? null : Convert.strToStr(paramMap.get("remark"), null));
		Integer check_result = paramMap.get("status") == null ? -100 : Convert.strToInt(paramMap.get("status"), -100);
		Long bankId = paramMap.get("bankId") == null ? null : Convert.strToLong(paramMap.get("bankId"), -1);
		// String provinceId = paramMap.get("provinceId") == null ? null :
		// Convert.strToStr(paramMap.get("provinceId"), null);
		// String cityId = paramMap.get("cityId") == null ? null :
		// Convert.strToStr(paramMap.get("cityId"), null);
		String openBranchName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("openBranchName")), ""); // 开户支行

		String usrCustId = Convert.strToStr(paramMap.get("usrCustId"), ""); // 用户客户号
		String openBankId = Convert.strToStr(paramMap.get("openBankId"), "CIB"); // 银行代号
		String openAcctId = Convert.strToStr(paramMap.get("openAcctId"), ""); // 开户银行账号
		String openProvId = Convert.strToStr(paramMap.get("provinceId"), ""); // 开户银行省份代号
		String openAreaId = Convert.strToStr(paramMap.get("cityId"), ""); // 开户银行地区代号

		String cmdId = "BgBindCard";
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if (check_result == 100 || check_result == 3) {
				fundManagementService.updateModifyBankInfo(checkUserId, bankId, remark, check_result, paramMap.get("modifiedBankName"),
						paramMap.get("modifiedBranchBankName"), paramMap.get("modifiedCardNo"), paramMap.get("modifiedTime"), false);
				// 添加操作日志
				operationLogService.addOperationLog("t_bankcard", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "银行卡变更审核不通过", 3);
				JSONUtils.printStr2("审核不通过，操作成功!");
				return null;
			}
			// 用户绑定银行卡
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.bgBindCard(cmdId, usrCustId, openBankId, openAcctId, openProvId, openAreaId,
					openBranchName));
			int ret = json.getInt("RespCode");
			if (ret == 0) {// 审核成功
				fundManagementService.updateModifyBankInfo(checkUserId, bankId, remark, check_result, paramMap.get("modifiedBankName"),
						paramMap.get("modifiedBranchBankName"), paramMap.get("modifiedCardNo"), paramMap.get("modifiedTime"), true);
				// 添加操作日志
				operationLogService.addOperationLog("t_bankcard", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "银行卡变更审核成功", 2);
				JSONUtils.printStr2("操作成功");
			} else {
				JSONUtils.printStr2("失败:"+json.getString("RespDesc"));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/*
	 * public UserBankManagerService getUserBankService() { return
	 * userBankService; }
	 * 
	 * public void setUserBankService(UserBankManagerService userBankService) {
	 * this.userBankService = userBankService; }
	 */

	public List<Map<String, Object>> getCheckers() throws SQLException, DataException {
		if (checkers == null) {
			// 加载审核人员列表
			checkers = adminService.queryAdministors(IConstants.ADMIN_ENABLE);
		}
		return checkers;
	}

	public void setCheckers(List<Map<String, Object>> checkers) {
		this.checkers = checkers;
	}

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

}
