package com.hehenian.biz.common.util.huifu;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import chinapnr.SecureLink;

import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.UtilDate;
import com.shove.Convert;

public class ChinaPnRInterface {
	public static final String pnrURL = ChinaPnrConfig.chinapnr_gateway;

	/**
	 * 前台 用户注册
	 * 
	 * @param usrName真实姓名
	 * @param usrId合和年用户id
	 */
	public static String userRegister(String cmdId, String usrId, String usrName, String idNo, String usrMp, String usrEmail) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = ChinaPnrConfig.chinapnr_retUrl; // 前台返回
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl; // 后台返回
		String idType = ChinaPnrConfig.chinapnr_idType; // 证件类型 '00': 身份证
		String merPriv = "UserRegister"; // 商户私有域
		String charSet = ChinaPnrConfig.chinapnr_input_charset;
		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("BgRetUrl", bgRetUrl);
		map.put("RetUrl", retUrl);
		map.put("UsrId", usrId);
		map.put("UsrName", usrName);
		map.put("IdType", idType);
		map.put("IdNo", idNo);
		map.put("UsrMp", usrMp);
		map.put("UsrEmail", usrEmail);
		map.put("MerPriv", merPriv);
		map.put("CharSet", charSet);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(bgRetUrl);
		plain.append(retUrl);
		plain.append(usrId);
		plain.append(usrName);
		plain.append(idType);
		plain.append(idNo);
		plain.append(usrMp);
		plain.append(usrEmail);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));

		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());

		return FormUtil.buildHtmlForm(map, pnrURL + "", "post");
	}

	/**
	 * 后台接口开户
	 * 
	 * @param usrId合和年用户id
	 * @param usrName真实姓名
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static String bgRegister(String cmdId, String usrId, String usrName, String loginPwd, String transPwd, String idNo, String usrMp, String usrEmail)
			throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String idType = ChinaPnrConfig.chinapnr_idType; // 证件类型 '00': 身份证
		String merPriv = "bgRegister"; // 商户私有域--后台开户
		String charSet = ChinaPnrConfig.chinapnr_input_charset;

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("UsrId", usrId);
		map.put("UsrName", usrName);
		map.put("IdType", idType);
		map.put("IdNo", idNo);
		map.put("UsrMp", usrMp);
		map.put("UsrEmail", usrEmail);
		map.put("MerPriv", merPriv);
		map.put("CharSet", charSet);

		String longinPwds = "d3776loqw21h77mmh675aas567xyusgKUL2H3DK4735O9861M6Q1ETGHB1C5ZK17K3P32VZR31WC77AC9E7TC43188PD3H9" + loginPwd; // 登录测试私密
		String transPwds = "d3776loqw21h77mmh675aas567xyusgKUL2H3DK4735O9861M6Q1ETGHB1C5ZK17K3P32VZR31WC77AC9E7TC43188PD3H9" + transPwd; // 交易测试私密
		longinPwds = com.shove.security.Encrypt.MD5(longinPwds.trim());
		transPwds = com.shove.security.Encrypt.MD5(transPwds.trim());
		map.put("LoginPwd", longinPwds);
		map.put("TransPwd", transPwds);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(usrId);
		plain.append(usrName);
		plain.append(longinPwds);
		plain.append(transPwds);
		plain.append(idType);
		plain.append(idNo);
		plain.append(usrMp);
		plain.append(usrEmail);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 用户绑卡--后台确认
	 * 
	 * @param version
	 * @param cmdId
	 * @param merCustId
	 * @param usrCustId
	 * @param openAcctId
	 * @param openBankId
	 * @param openAreaId
	 * @param openProvId
	 * @param bgRetUrl
	 * @param merPriv
	 * @param chkValue
	 * @return
	 */
	public static String bgBindCard(String cmdId, String usrCustId, String openBankId, String openAcctId, String openProvId, String openAreaId, String openBranchName) {
		Map<String, String> map = new HashMap<String, String>();
		// 组装接口参数，并进行加密
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String usrCustIds = usrCustId + ""; // 商户客户号，汇付生成，用户的唯一性标识
		String merPriv = "a2"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		String isDefault = "Y";

		map.put("Version", version);
		map.put("UsrCustId", usrCustId);
		map.put("MerCustId", merCustId);
		map.put("CmdId", cmdId);
		map.put("OpenBankId", openBankId);

		map.put("CharSet", ChinaPnrConfig.chinapnr_input_charset);
		map.put("MerPriv", merPriv);
		map.put("OpenAcctId", openAcctId);
		map.put("OpenProvId", openProvId);
		map.put("OpenAreaId", openAreaId);

		map.put("IsDefault", isDefault); // 是否默认 定长 1位 Y/N
		map.put("OpenBranchName", openBranchName); // 开户行支行名称

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version).append(cmdId).append(merCustId).append(usrCustIds).append(openAcctId);
		plain.append(openBankId).append(openProvId).append(openAreaId).append(openBranchName).append(isDefault).append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = -1;
		try {
			ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 前台用户绑卡接口
	 * 
	 * @return
	 */
	public static String userBindCard(String usrCustId) {
		Map<String, String> map = new HashMap<String, String>();
		String cmdId = "UserBindCard";
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "userBindCard"; // 商户私有域,自定义字段，

		map.put("Version", version);
		map.put("UsrCustId", usrCustId);
		map.put("MerCustId", merCustId);
		map.put("CmdId", cmdId);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);
		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(usrCustId);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = -1;
		try {
			ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret != 0) {
			return "发送请求签名错误";
		}
		map.put("ChkValue", sl.getChkValue());
		return FormUtil.buildHtmlForm(map, pnrURL, "post");
	}

	/**
	 * 充值
	 * 
	 * @param body
	 * @param nunber
	 * @param userId
	 * @param bankCode
	 * @param tranDateTime
	 * @param gateBusiId
	 *            网关的细分业务类型 用户B2C,商户B2B
	 */
	public static String netSave(String body, String ordId, String openBankId, String usrCustId, String ordDate, String moneyStr, String gateBusiId, String merPriv, String retUrl,
			String cardDcFlag) throws Exception {
		// 组装接口参数，并进行加密

		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String usrCustIds = usrCustId + ""; // 商户客户号，汇付生成，用户的唯一性标识
		String transAmt = moneyStr + ""; // 交易金额
		// String retUrl = ChinaPnrConfig.chinapnr_retUrl;

		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String cmdId = "NetSave";
		String dcFlag = cardDcFlag; // 银行卡类型：D借记卡，C信用卡
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", version);
		map.put("UsrCustId", usrCustId);
		map.put("GateBusiId", gateBusiId);
		map.put("TransAmt", transAmt);
		map.put("MerCustId", merCustId);
		map.put("CmdId", cmdId);
		map.put("OrdId", ordId);
		map.put("DcFlag", dcFlag);
		map.put("OrdDate", ordDate); // 订单日期
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		if (!"DEFAULT".equals(openBankId)) {// 开户银行代号
			map.put("OpenBankId", openBankId);
		} else {
			map.put("OpenBankId", openBankId);
		}

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(usrCustIds);
		plain.append(ordId);
		plain.append(ordDate);
		plain.append(gateBusiId);
		plain.append(openBankId);
		plain.append(dcFlag);
		plain.append(transAmt);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return FormUtil.buildHtmlForm(map, pnrURL, "post");
	}

	/**
	 * 商户无卡代扣充值
	 */
	public static String posWhSave(String usrCustId, String openAcctId, String transAmt, String ordId, String ordDate, String checkDate) throws UnsupportedEncodingException {
		String cmdId = "PosWhSave";
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId;
		String retUrl = "";
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "posWhSave";
		Map<String, String> map = new HashMap<String, String>();
		map.put("CmdId", cmdId);
		map.put("Version", version);
		map.put("MerCustId", merCustId);
		map.put("UsrCustId", usrCustId);
		map.put("OpenAcctId", openAcctId);

		map.put("TransAmt", transAmt);
		map.put("OrdId", ordId);
		map.put("OrdDate", ordDate);
		map.put("CheckDate", checkDate);
		map.put("RetUrl", retUrl);

		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);
		StringBuilder plain = new StringBuilder(version).append(cmdId).append(merCustId).append(usrCustId).append(openAcctId);
		plain.append(transAmt).append(ordId).append(ordDate).append(checkDate).append(retUrl).append(bgRetUrl).append(merPriv);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 资金（货款）冻结
	 */
	public static String usrFreezeBg(String usrCustId, String subAcctType, String subAcctId, String ordId, String transAmt) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		// 拼接金额小数点
		double amount = Convert.strToDouble(transAmt, 0);
		transAmt = new DecimalFormat("0.00").format(amount);

		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String ordDate = UtilDate.getDate(); // 日期
		String retUrl = "";
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "UsrFreezeBg"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		String cmdId = "UsrFreezeBg"; // 消息类型,每一种消息类型代表一种交易-- 冻结

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("UsrCustId", usrCustId);// 商户客户号，汇付生成，用户的唯一性标识
		map.put("SubAcctType", subAcctType);// 子账号类型
		map.put("SubAcctId", subAcctId);// 子账户号
		map.put("OrdId", ordId);// 订单号
		map.put("OrdDate", ordDate);
		map.put("TransAmt", transAmt);
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(usrCustId);
		plain.append(subAcctType);
		plain.append(subAcctId);
		plain.append(ordId);
		plain.append(ordDate);
		plain.append(transAmt);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());

		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 资金（货款）解冻
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String usrUnFreeze(String ordId, String trxId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		ordId = trxId;

		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String ordDate = UtilDate.getDate(); // 日期
		String retUrl = "";
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "UsrUnFreeze"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		String cmdId = "UsrUnFreeze"; // 消息类型,每一种消息类型代表一种交易-- 解冻

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId); // 订单号
		map.put("OrdDate", ordDate);
		map.put("TrxId", trxId);// 商户专属平台交易唯一标识定长 18 位
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(ordDate);
		plain.append(trxId);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 投标撤销
	 */
	public static String tenderCancle(String ordId, String transAmt, String usrCustId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		double amount = Convert.strToDouble(transAmt, 0);
		transAmt = new DecimalFormat("0.00").format(amount);

		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String ordDate = UtilDate.getDate(); // 日期
		String retUrl = "";
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "TenderCancle"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		String cmdId = "TenderCancle"; // 消息类型

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId); // 订单号
		map.put("OrdDate", ordDate);
		map.put("TransAmt", transAmt);
		map.put("UsrCustId", usrCustId);
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(ordDate);
		plain.append(transAmt);
		plain.append(usrCustId);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		// return FormUtil.buildHtmlForm(map, pnrURL, "post");
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 自动扣款（放款）
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String loans(String OrdId, String OrdDate, String OutCustId, String TransAmt, String Fee, String SubOrdId, String SubOrdDate, String InCustId, String DivDetails,
			String IsDefault) throws Exception {

		SecureLink sl = new SecureLink();
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "Loans"; // 操作类型自动扣款 放款
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户id
		String BgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String MerPriv = "Loans"; // 商户私有域,自定义在交易完成后原样返回
		try {
			Date da = new SimpleDateFormat("yyyy-MM-dd").parse(SubOrdDate);
			SubOrdDate = new SimpleDateFormat("yyyyMMdd").format(da);
			Date da1 = new SimpleDateFormat("yyyy-MM-dd").parse(OrdDate);
			OrdDate = new SimpleDateFormat("yyyyMMdd").format(da1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(OrdId).append(OrdDate);
		plain.append(OutCustId).append(TransAmt).append(Fee).append(SubOrdId).append(SubOrdDate);
		plain.append(InCustId).append(DivDetails).append(IsDefault).append(BgRetUrl).append(MerPriv);

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("OrdId", OrdId);
		map.put("OrdDate", OrdDate);

		map.put("OutCustId", OutCustId);
		map.put("TransAmt", TransAmt);
		map.put("Fee", Fee);
		map.put("SubOrdId", SubOrdId);
		map.put("SubOrdDate", SubOrdDate);

		map.put("InCustId", InCustId);
		map.put("DivDetails", DivDetails);
		map.put("IsDefault", IsDefault);
		map.put("BgRetUrl", BgRetUrl);
		map.put("MerPriv", MerPriv);
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 转账（商户用） 回购代偿扣款--子账户转账
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String transfer(String ordId, String outCustId, String outAcctId, String transAmt, String inCustId, String inAcctId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		double amount = Convert.strToDouble(transAmt, 0);
		transAmt = new DecimalFormat("0.00").format(amount);

		String version = ChinaPnrConfig.chinapnr_version;
		// String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = "";
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "cmdId=" + "Transfer"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		String cmdId = "Transfer"; // 消息类型,每一种消息类型代表一种交易-- 冻结
		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("OrdId", ordId);
		map.put("OutCustId", outCustId);// 出账客户号
		map.put("OutAcctId", outAcctId);// 出账客户号
		map.put("TransAmt", transAmt);
		map.put("InCustId", inCustId);// 入账客户号
		map.put("InAcctId", inAcctId);// 入账子账户
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(ordId);
		plain.append(outCustId);// 出账客户号
		plain.append(outAcctId);// 出账子账户
		plain.append(transAmt);
		plain.append(inCustId); // 入账客户号
		plain.append(inAcctId); // 入账子账户
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());

		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 自动扣款（还款）
	 * @还款使用的ordId取自t_invest_repayment
	 * @如果出账账户是担保账户，请指定 outCustId，否则可空。如果入账账户是担保账户请指定 inCustId，否则可空。
	 */
	public static String repayment(String version,String ordId, String outCustId, String outAcctId, String transAmt, String inCustId, String inAcctId, String subOrdId, String subOrdDate,
			String fee, String divDetails) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 拼接金额小数点
		StringBuffer sb = new StringBuffer();
		if (transAmt != null && transAmt.indexOf(".") == -1) {
			transAmt = sb.append(transAmt).append(".00").toString();
		} else {
			transAmt += "0000";
			transAmt = transAmt.substring(0, transAmt.indexOf(".") + 3);
		}

		// 拼接金额小数点
		double amount = Convert.strToDouble(fee, 0);
		fee = new DecimalFormat("0.00").format(amount);

		String OrdDate = DateUtil.dateToYMD(new Date());
		String cmdId = "Repayment"; // 消息类型
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "Repayment"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("OrdDate", OrdDate);

		map.put("OutCustId", outCustId);// 出账客户号
		map.put("SubOrdId", subOrdId);// 订单号 变长 20 位 由商户的系统生成，必须保证唯一。
		map.put("SubOrdDate", subOrdDate);// 关联投标订单流水日期是
		map.put("OutAcctId", outAcctId);// 出账子账户
		map.put("TransAmt", transAmt);// 金额

		map.put("Fee", fee);// Fee 扣款手续费, 放款或扣款的手续费
		map.put("InCustId", inCustId);// 入账客户号
		map.put("InAcctId", inAcctId);// 入账子账户
		map.put("DivDetails", divDetails);// 分账账户串 变长
		map.put("BgRetUrl", bgRetUrl);

		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version).append(cmdId).append(merCustId).append(ordId).append(OrdDate);
		plain.append(outCustId).append(subOrdId).append(subOrdDate).append(outAcctId).append(transAmt);
		plain.append(fee).append(inCustId).append(inAcctId).append(divDetails).append(bgRetUrl).append(merPriv);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "签名错误 ret=" + ret;
		}

		map.put("ChkValue", sl.getChkValue());
		String html = FormUtil.buildHtmlForm(map, pnrURL + "", "post");
		html.length();
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 取现
	 */
	public static String cash(String ordId, String usrCustId, String transAmt, String openAcctId) {
		Map<String, String> map = new HashMap<String, String>();

		// 拼接金额小数点
		double amount = Convert.strToDouble(transAmt, 0);
		transAmt = new DecimalFormat("0.00").format(amount);

		String version = ChinaPnrConfig.chinapnr_version;
		String cmdId = "Cash"; // 消息类型,每一种消息类型代表一种交易-- 冻结
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = ChinaPnrConfig.chinapnr_retUrl;
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "Cash"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		String remark = "hhnCash"; // 取现描述，长度有限（64 个汉字），请简明扼要的说明
		String charSet = "utf-8";

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("UsrCustId", usrCustId);
		map.put("TransAmt", transAmt);
		map.put("OpenAcctId", openAcctId);
		map.put("RetUrl", retUrl);
		map.put("Remark", remark);
		map.put("BgRetUrl", bgRetUrl);
		map.put("CharSet", charSet);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(usrCustId);
		plain.append(transAmt);
		plain.append(openAcctId);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(remark);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString());

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());

		return FormUtil.buildHtmlForm(map, pnrURL + "", "post");
		// return HttpUtil.http(pnrURL, map);

	}

	/**
	 * 取现复核
	 */
	public static String cashAudit(String ordId, String usrCustId, String transAmt, String auditFlag) {
		Map<String, String> map = new HashMap<String, String>();

		String version = ChinaPnrConfig.chinapnr_version;
		String cmdId = "CashAudit"; // 消息类型,每一种消息类型代表一种交易-- 冻结
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = "";
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "CashAudit"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("UsrCustId", usrCustId);
		map.put("TransAmt", transAmt);
		map.put("AuditFlag", auditFlag);// AuditFlag 复核标识 定长 1 位 取值范围：{R,S} R：拒绝
		// S：复核通过
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(usrCustId);
		plain.append(transAmt);
		plain.append(auditFlag);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString());

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 查询余额，前台用户
	 */
	public static String queryBalance(String usrCustId) throws Exception {
		// 组装接口参数，并进行加密
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		// String usrCustIds = usrCustId + ""; //商户客户号，汇付生成，用户的唯一性标识
		String cmdId = "QueryBalance"; // 消息类型,每一种消息类型代表一种交易

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", version);
		map.put("UsrCustId", usrCustId);
		map.put("MerCustId", merCustId);
		map.put("CmdId", cmdId);

		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(usrCustId);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		// String html = FormUtil.buildHtmlForm(map,
		// pnrURL, "post");
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 用户 查询余额 数据流方式
	 */
	public static String queryBalanceBg(String UsrCustId) throws Exception {
		// String usrCustIds = usrCustId + ""; //商户客户号，汇付生成，用户的唯一性标识
		// 组装接口参数，并进行加密
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号

		String cmdId = "QueryBalanceBg"; // 消息类型 --后台查询余额QueryBalanceBg
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("UsrCustId", UsrCustId);
		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(UsrCustId);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 账户信息修改（页面）接口
	 */
	public static String AccountUpdate(String usrCustId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String version = ChinaPnrConfig.chinapnr_version;
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String cmdId = "AcctModify"; // 消息类型,每一种消息类型代表一种交易-- 账户信息修改

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("UsrCustId", usrCustId);
		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(usrCustId);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return FormUtil.buildHtmlForm(map, pnrURL + "", "post");
	}

	/**
	 * 主动投标
	 */
	public static String initiativeTender(String id, String ordId, String usrCustId, String transAmt, String borrowerDetails) {
		Map<String, String> map = new HashMap<String, String>();
		String maxTenderRate = "0.01";

		String version = ChinaPnrConfig.chinapnr_version;
		String cmdId = "InitiativeTender"; // 消息类型,每一种消息类型代表一种交易-- 冻结
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = ChinaPnrConfig.chinapnr_retUrl;
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = id; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("OrdDate", UtilDate.getDate());
		map.put("TransAmt", transAmt);
		map.put("UsrCustId", usrCustId);
		map.put("MaxTenderRate", maxTenderRate); // 最大投资手 续费率
		map.put("BorrowerDetails", borrowerDetails); // 借款人信息 , 变长
														// ,支持传送多个借款人信息，使用json格式传送
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(UtilDate.getDate());
		plain.append(transAmt);
		plain.append(usrCustId);
		plain.append(maxTenderRate);
		plain.append(borrowerDetails);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString());

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return FormUtil.buildHtmlForm(map, pnrURL + "", "post");
	}
	
	/**
	 * 主动投标
	 */
	public static String initiativeTender1(String id, String ordId, String usrCustId, String transAmt, String borrowerDetails,String url) {
		Map<String, String> map = new HashMap<String, String>();
		String maxTenderRate = "0.01";

		String version = ChinaPnrConfig.chinapnr_version;
		String cmdId = "InitiativeTender"; // 消息类型,每一种消息类型代表一种交易-- 冻结
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = ChinaPnrConfig.chinapnr_retUrl;
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		if (StringUtils.isNotBlank(url)&&StringUtils.contains(url, "colourlife.com")) {
			retUrl = url+"frontChinaPnrUrl.do";
			bgRetUrl =url+"backgroundChinaPnrUrl.do";
		}
		System.out.println("ChinaPnRInterface:initiativeTender1:url"+url);
		String merPriv = id; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("OrdDate", UtilDate.getDate());
		map.put("TransAmt", transAmt);
		map.put("UsrCustId", usrCustId);
		map.put("MaxTenderRate", maxTenderRate); // 最大投资手 续费率
		map.put("BorrowerDetails", borrowerDetails); // 借款人信息 , 变长
		System.out.println("回调地址："+url);												// ,支持传送多个借款人信息，使用json格式传送
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(UtilDate.getDate());
		plain.append(transAmt);
		plain.append(usrCustId);
		plain.append(maxTenderRate);
		plain.append(borrowerDetails);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString());

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return FormUtil.buildHtmlForm(map, pnrURL + "", "post");
	}

	/**
	 * 主动投标--债权购买
	 */
	public static String inDebtTender(String id, String ordId, String usrCustId, String transAmt, String borrowerDetails, String debtUsrCustId) {
		Map<String, String> map = new HashMap<String, String>();
		String maxTenderRate = "888.00";

		String version = ChinaPnrConfig.chinapnr_version;
		String cmdId = "InitiativeTender"; // 消息类型,每一种消息类型代表一种交易-- 冻结
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = ChinaPnrConfig.chinapnr_retUrl;
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = ordId + "=inDebtTender" + "=" + debtUsrCustId; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("OrdDate", UtilDate.getDate());
		map.put("TransAmt", transAmt);
		map.put("UsrCustId", usrCustId);
		map.put("MaxTenderRate", maxTenderRate); // 最大投资手 续费率
		map.put("BorrowerDetails", borrowerDetails); // 借款人信息 , 变长
														// ,支持传送多个借款人信息，使用json格式传送
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(UtilDate.getDate());
		plain.append(transAmt);
		plain.append(usrCustId);
		plain.append(maxTenderRate);
		plain.append(borrowerDetails);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString());

		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return FormUtil.buildHtmlForm(map, pnrURL + "", "post");

	}

	/**
	 * 数据签名
	 */
	public static String SignMsg(String MerId, String MerKeyFile, String MsgData) throws Exception {
		SecureLink sl = new SecureLink();
		sl.SignMsg(MerId, MerKeyFile, MsgData.getBytes("utf-8"));
		return sl.getMsgData();
	}

	/**
	 * 用户账户支付
	 */
	public static String UsrAcctPay(long debtId, long userId, double auctionPrice, String UsrCustId) throws Exception {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "UsrAcctPay";
		String OrdId = debtId + "";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户id
		String TransAmt = auctionPrice + "";
		if (TransAmt.indexOf('.') == -1) {
			TransAmt += ".00";
		} else {
			TransAmt += "00";
			TransAmt = TransAmt.substring(0, TransAmt.indexOf('.') + 3);
		}

		String InAcctId = "6227002101661415050";// 汇付生成的虚拟资金账号
		// BASEDT基本借记户; DEP保证金账户; MERDT专属借记帐户;等
		String InAcctType = "BASEDT";
		String RetUrl = ChinaPnrConfig.chinapnr_retUrl;
		String BgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String MerPriv = "userId=" + userId; // 商户私有域,自定义在交易完成后原样返回

		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(OrdId).append(UsrCustId).append(MerCustId);
		plain.append(TransAmt).append(InAcctId).append(InAcctType).append(RetUrl).append(BgRetUrl).append(MerPriv);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("OrdId", OrdId);
		map.put("TransAmt", TransAmt);
		map.put("RetUrl", RetUrl);
		map.put("BgRetUrl", BgRetUrl);
		map.put("MerPriv", MerPriv);
		map.put("UsrCustId", UsrCustId);
		map.put("InAcctId", InAcctId);
		map.put("InAcctType", InAcctType);
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 商户无卡充值代扣
	 */
	public static String posWhSave(String OrdId, String TransAmt, String UsrCustId, String OpenAcctId) throws Exception {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "PosWhSave";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户id
		if (TransAmt.indexOf('.') == -1) {
			TransAmt += ".00";
		} else {
			TransAmt += "00";
			TransAmt = TransAmt.substring(0, TransAmt.indexOf('.') + 3);
		}
		String OrdDate = UtilDate.getDate();
		String CheckDate = UtilDate.getDate(); // 校验日期 定长 8 位 POS
												// 验证日期，该字段可以为空，如果传送的代扣卡
		String RetUrl = "";
		String BgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String MerPriv = CmdId;
		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(UsrCustId).append(OpenAcctId);
		plain.append(TransAmt).append(OrdId).append(OrdDate).append(RetUrl).append(BgRetUrl).append(MerPriv);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("UsrCustId", UsrCustId);
		map.put("InAcctId", OpenAcctId);
		map.put("TransAmt", TransAmt);
		map.put("OrdId", OrdId);
		map.put("OrdDate", OrdDate);
		map.put("CheckDate", CheckDate);
		map.put("RetUrl", RetUrl);
		map.put("BgRetUrl", BgRetUrl);
		map.put("MerPriv", MerPriv);
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 商户代取现
	 */
	public static String merCash(String OrdId, String UsrCustId, String TransAmt, String Remark) throws UnsupportedEncodingException {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "MerCash";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户id
		if (TransAmt.indexOf('.') == -1) {
			TransAmt += ".00";
		} else {
			TransAmt += "00";
			TransAmt = TransAmt.substring(0, TransAmt.indexOf('.') + 3);
		}
		String RetUrl = "";
		String BgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String MerPriv = CmdId;
		String CharSet = ChinaPnrConfig.chinapnr_input_charset;

		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(OrdId).append(UsrCustId);
		plain.append(TransAmt).append(RetUrl).append(BgRetUrl).append(Remark).append(MerPriv);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("OrdId", OrdId);
		map.put("UsrCustId", UsrCustId);

		map.put("TransAmt", TransAmt);
		map.put("Remark", Remark);
		map.put("RetUrl", RetUrl);
		map.put("BgRetUrl", BgRetUrl);
		map.put("MerPriv", MerPriv);

		map.put("CharSet", CharSet);
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 交易状态查询
	 */
	public static String queryTransStat(String ordId, String ordDate, String queryTransType) throws Exception {
		// 组装接口参数，并进行加密
		String version = ChinaPnrConfig.chinapnr_version;
		String cmdId = "QueryTransStat"; // 消息类型
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("OrdDate", ordDate);
		map.put("QueryTransType", queryTransType);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(ordDate);
		plain.append(queryTransType);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {

			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 商户子账户信息查询
	 */
	public static String queryAccts() throws Exception {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "QueryAccts";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, new StringBuilder(Version).append(CmdId).append(MerCustId).toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
		// return FormUtil.buildHtmlForm(map, pnrURL + "", "post");
	}

	/**
	 * 取现对账
	 */
	public static String cashReconciliation(String beginDate, String endDate, String pageSize, String pageNum) throws Exception {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "CashReconciliation";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;

		SecureLink sl = new SecureLink();

		StringBuilder sb = new StringBuilder(Version).append(CmdId).append(MerCustId);
		sb.append(beginDate).append(endDate).append(pageNum).append(pageSize);
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, sb.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("ChkValue", sl.getChkValue());

		map.put("BeginDate", beginDate);
		map.put("EndDate", endDate);
		map.put("PageSize", pageSize);
		map.put("PageNum", pageNum);
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 充值对账
	 */
	public static String saveReconciliation(String beginDate, String endDate, String pageSize, String pageNum) throws Exception {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "SaveReconciliation";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;

		SecureLink sl = new SecureLink();

		StringBuilder sb = new StringBuilder(Version).append(CmdId).append(MerCustId);
		sb.append(beginDate).append(endDate).append(pageNum).append(pageSize);
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, sb.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("ChkValue", sl.getChkValue());

		map.put("BeginDate", beginDate);
		map.put("EndDate", endDate);
		map.put("PageSize", pageSize);
		map.put("PageNum", pageNum);
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 投标对账
	 */
	public static String reconciliation(String BeginDate, String EndDate, String PageSize, String PageNum, String QueryTransType) throws Exception {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "Reconciliation";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;

		SecureLink sl = new SecureLink();

		StringBuilder sb = new StringBuilder(Version).append(CmdId).append(MerCustId);
		sb.append(BeginDate).append(EndDate).append(PageNum).append(PageSize).append(QueryTransType);
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, sb.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("ChkValue", sl.getChkValue());
		map.put("QueryTransType", QueryTransType);

		map.put("BeginDate", BeginDate);
		map.put("EndDate", EndDate);
		map.put("PageSize", PageSize);
		map.put("PageNum", PageNum);
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 商户扣款对账
	 */
	public static String trfReconciliation(String BeginDate, String EndDate, String PageSize, String PageNum) throws Exception {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "TrfReconciliation";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;

		SecureLink sl = new SecureLink();

		StringBuilder sb = new StringBuilder(Version).append(CmdId).append(MerCustId);
		sb.append(BeginDate).append(EndDate).append(PageNum).append(PageSize);
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, sb.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("ChkValue", sl.getChkValue());

		map.put("BeginDate", BeginDate);
		map.put("EndDate", EndDate);
		map.put("PageSize", PageSize);
		map.put("PageNum", PageNum);
		return HttpUtil.http(pnrURL, map);
	}

	/**
	 * 债权转让
	 * 
	 * @OrdId 订单号
	 * @SellCustId 转让人客户号
	 * @CreditAmt 债权总额
	 * @CreditDealAmt 债权转让承接人付给转让人的金额
	 * @BidDetails 债权转让明细
	 * @BidOrdId 原投标订单号
	 * @BidOrdDate 原投标订单日期
	 * @BidCreditAmt 转出原投标金额
	 * @Fee 手续费
	 * @DivDetails 分账账户串
	 * @BuyCustId 承接人客户号(债权购买人客户号)
	 */
	public static String creditAssign(String OrdId, String SellCustId, String CreditAmt, String CreditDealAmt, String BidDetails, String Fee, String DivDetails, String BuyCustId,
			String OrdDate,String MerPriv) {
		String Version = ChinaPnrConfig.chinapnr_version;
//		String Version = "20";
		String CmdId = "CreditAssign";
		String ReqExt = "";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户id
		String RetUrl = ChinaPnrConfig.chinapnr_retUrl;
		String BgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(SellCustId);
		plain.append(CreditAmt).append(CreditDealAmt).append(BidDetails).append(Fee).append(DivDetails);
		plain.append(BuyCustId).append(OrdId).append(OrdDate).append(RetUrl).append(BgRetUrl).append(MerPriv).append(ReqExt);

		SecureLink sl = new SecureLink();
		int ret = 1;
		try {
			ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret != 0) {
			return "发送请求签名错误";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("SellCustId", SellCustId);
		map.put("CreditAmt", CreditAmt);

		map.put("CreditDealAmt", CreditDealAmt);
		map.put("BidDetails", BidDetails);
		map.put("Fee", Fee);
		map.put("DivDetails", DivDetails);
		map.put("BuyCustId", BuyCustId);

		map.put("OrdId", OrdId);
		map.put("OrdDate", OrdDate);
		map.put("RetUrl", RetUrl);
		map.put("BgRetUrl", BgRetUrl);
		map.put("MerPriv", MerPriv);

		map.put("ReqExt", ReqExt);
		map.put("ChkValue", sl.getChkValue());

		String html = FormUtil.buildHtmlForm(map, pnrURL, "post");
		html = html.replace("value=\"{\"BidDetails", "value='{\"BidDetails");
		html = html.replace("}]}]}\"/>", "}]}]}'/>");
		return html;
	}

	/** 自动投标 **/
	public static String autoTender(String merPriv, String ordId, String usrCustId, String transAmt, String borrowerDetails) {
		Map<String, String> map = new HashMap<String, String>();
		String maxTenderRate = "0.01"; // 最大投资手 续费率

		String version = ChinaPnrConfig.chinapnr_version;
		String cmdId = "AutoTender";
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String retUrl = "";//ChinaPnrConfig.chinapnr_retUrl;
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;

		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("OrdDate", UtilDate.getDate());
		map.put("TransAmt", transAmt);
		map.put("UsrCustId", usrCustId);
		map.put("MaxTenderRate", maxTenderRate);
		map.put("BorrowerDetails", borrowerDetails);
		map.put("RetUrl", retUrl);
		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version);
		plain.append(cmdId);
		plain.append(merCustId);
		plain.append(ordId);
		plain.append(UtilDate.getDate());
		plain.append(transAmt);
		plain.append(usrCustId);
		plain.append(maxTenderRate);
		plain.append(borrowerDetails);
		plain.append(retUrl);
		plain.append(bgRetUrl);
		plain.append(merPriv);

		SecureLink sl = new SecureLink();

		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString());

		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}
		map.put("ChkValue", sl.getChkValue());
		return HttpUtil.http(pnrURL, map);
	}
	

	/** 开启自动投标计划 **/
	public static String autoTenderPlan(String UsrCustId, String TenderPlanType, String TransAmt) {
		return autoTenderPlan(UsrCustId, TenderPlanType, TransAmt,"");
	}
	public static String autoTenderPlan(String UsrCustId, String TenderPlanType, String TransAmt,String retUrl) {
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "AutoTenderPlan";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;
		String RetUrl = ChinaPnrConfig.chinapnr_retUrl;
		if (StringUtils.isNotBlank(retUrl)) {
			RetUrl = retUrl;
		}
		String MerPriv = generateSequenceNo.generateSequenceNo();
		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(UsrCustId).append(TenderPlanType).append(TransAmt).append(RetUrl).append(MerPriv);

		SecureLink sl = new SecureLink();
		int ret = 1;
		try {
			ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret != 0) {
			return "发送请求签名错误";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("UsrCustId", UsrCustId);

		map.put("TenderPlanType", TenderPlanType);
		map.put("TransAmt", TransAmt);
		map.put("RetUrl", RetUrl);
		map.put("MerPriv", MerPriv);
		map.put("ChkValue", sl.getChkValue());

		String html = FormUtil.buildHtmlForm(map, pnrURL, "post");
		return html;
	}

	/** 关闭自动投标计划 **/
	public static String autoTenderPlanClose(String UsrCustId) {
		return autoTenderPlanClose(UsrCustId,"");
	}
	public static String autoTenderPlanClose(String UsrCustId,String retUrl) {

		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "AutoTenderPlanClose";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;
		String RetUrl = ChinaPnrConfig.chinapnr_retUrl;
		if (StringUtils.isNotBlank(retUrl)) {
			RetUrl = retUrl;
		}
		String MerPriv = generateSequenceNo.generateSequenceNo();
		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(UsrCustId).append(RetUrl).append(MerPriv);

		SecureLink sl = new SecureLink();
		int ret = 1;
		try {
			ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret != 0) {
			return "发送请求签名错误";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("UsrCustId", UsrCustId);

		map.put("RetUrl", RetUrl);
		map.put("MerPriv", MerPriv);
		map.put("ChkValue", sl.getChkValue());

		String html = FormUtil.buildHtmlForm(map, pnrURL, "post");
		return html;
	}
	
	/** 企业开户   **/
	public static String corpRegister(String UsrId,String UsrName, String BusiCode, String GuarType) throws Exception{

		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "CorpRegister";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;
		
		String InstuCode = "";
		String TaxCode = "";
		String MerPriv = "";
		
		String Charset = "utf-8";
		String BgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String ReqExt = "";
		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(UsrId).append(UsrName).append(InstuCode);
		plain.append(BusiCode).append(TaxCode).append(MerPriv).append(GuarType).append(BgRetUrl).append(ReqExt);

		SecureLink sl = new SecureLink();
		int ret = 1;
		try {
			ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret != 0) {
			return "发送请求签名错误";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("UsrId", UsrId);
		map.put("UsrName", UsrName);
		
		map.put("InstuCode", InstuCode);
		map.put("BusiCode", BusiCode);
		map.put("TaxCode", TaxCode);
		map.put("MerPriv", MerPriv);
		map.put("Charset", Charset);

		map.put("GuarType", GuarType);
		map.put("BgRetUrl", BgRetUrl);
		map.put("ReqExt", ReqExt);
		map.put("ChkValue", sl.getChkValue());

		String html = FormUtil.buildHtmlForm(map, pnrURL, "post");
		return html;
	}
	
	/**企业开户查询**/
	public static String CorpRegisterQuery(String BusiCode){
		String Version = ChinaPnrConfig.chinapnr_version;
		String CmdId = "CorpRegisterQuery";
		String MerCustId = ChinaPnrConfig.chinapnr_merCustId;
		
		String ReqExt = "";
		// 生成订单
		StringBuffer plain = new StringBuffer();
		plain.append(Version).append(CmdId).append(MerCustId).append(BusiCode).append(ReqExt);

		SecureLink sl = new SecureLink();
		int ret = 1;
		try {
			ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret != 0) {
			return "{\"RespCode\":\"-101\",\"RespDesc\":\"发送请求签名错误\"}";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("Version", Version);
		map.put("CmdId", CmdId);
		map.put("MerCustId", MerCustId);
		map.put("BusiCode", BusiCode);
		map.put("ReqExt", ReqExt);
		map.put("ChkValue", sl.getChkValue());

		return HttpUtil.http(pnrURL, map);
	}
	
	/***2.0版本还款接口 **/
	public static String repayment2(String version,String ordId, String outCustId, String outAcctId, String transAmt, String inCustId, String inAcctId, String subOrdId, String subOrdDate,
			String fee, String divDetails) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 拼接金额小数点
		StringBuffer sb = new StringBuffer();
		if (transAmt != null && transAmt.indexOf(".") == -1) {
			transAmt = sb.append(transAmt).append(".00").toString();
		} else {
			transAmt += "0000";
			transAmt = transAmt.substring(0, transAmt.indexOf(".") + 3);
		}

		// 拼接金额小数点
		double amount = Convert.strToDouble(fee, 0);
		fee = new DecimalFormat("0.00").format(amount);

		String OrdDate = DateUtil.dateToYMD(new Date());
		String cmdId = "Repayment"; // 消息类型
		String merCustId = ChinaPnrConfig.chinapnr_merCustId; // 商户号
		String bgRetUrl = ChinaPnrConfig.chinapnr_bgRetUrl;
		String merPriv = "Repayment"; // 商户私有域,自定义字段，该字段在交易完成后由商户专属平台原样返回
		String ReqExt = "";
		String FeeObjFlag = "";
		map.put("Version", version);
		map.put("CmdId", cmdId);
		map.put("MerCustId", merCustId);
		map.put("OrdId", ordId);
		map.put("OrdDate", OrdDate);

		map.put("OutCustId", outCustId);// 出账客户号
		map.put("SubOrdId", subOrdId);// 订单号 变长 20 位 由商户的系统生成，必须保证唯一。
		map.put("SubOrdDate", subOrdDate);// 关联投标订单流水日期是
		map.put("OutAcctId", outAcctId);// 出账子账户
		map.put("TransAmt", transAmt);// 金额

		map.put("Fee", fee);// Fee 扣款手续费, 放款或扣款的手续费
		map.put("InCustId", inCustId);// 入账客户号
		map.put("InAcctId", inAcctId);// 入账子账户
		map.put("DivDetails", divDetails);// 分账账户串 变长
		map.put("FeeObjFlag", FeeObjFlag);

		map.put("BgRetUrl", bgRetUrl);
		map.put("MerPriv", merPriv);
		map.put("ReqExt", ReqExt);

		// 组织加密明文
		StringBuffer plain = new StringBuffer();
		plain.append(version).append(cmdId).append(merCustId).append(ordId).append(OrdDate);
		plain.append(outCustId).append(subOrdId).append(subOrdDate).append(outAcctId).append(transAmt);
		plain.append(fee).append(inCustId).append(inAcctId).append(divDetails).append(FeeObjFlag).append(bgRetUrl).append(merPriv).append(ReqExt);

		SecureLink sl = new SecureLink();
		int ret = sl.SignMsg(ChinaPnrConfig.chinapnr_merId, ChinaPnrConfig.chinapnr_merKeyFile, plain.toString().getBytes("utf-8"));
		if (ret != 0) {
			return "签名错误 ret=" + ret;
		}

		map.put("ChkValue", sl.getChkValue());
		String html = FormUtil.buildHtmlForm(map, pnrURL + "", "post");
		html.length();
		return HttpUtil.http(pnrURL, map);
	}

}
