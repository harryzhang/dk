package com.hehenian.biz.common.util.huifu;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;

/* *
 *汇付天下配置
 */
public class ChinaPnrConfig {
	/**
	 * 商户客户号
	 */
	// public static String chinapnr_merCustId = "6000060000042715";
	
//	public static String chinapnr_merCustId = "6000060000060703";
//	public static String chinapnr_merId = "530044"; // 客户号
	public static String chinapnr_merCustId = "6000060000477719";
	public static String chinapnr_merId = "830036"; // 客户号

	/**
	 * 商户账户
	 */
	public static String chinapnr_virCardNoIn = "";

	/**
	 * 汇付天下网关地址
	 */
	public static String chinapnr_gateway = "";
	/**
	 * 汇付天下 子账户充值 回调
	 */
	public static String chinapnr_retUrl_bg = "";

	/**
	 * 汇付私钥
	 */
	public static String chinapnr_merKeyFile = "";

	/**
	 * 汇付私公钥
	 */
	public static String chinapnr_PgPubkFile = "";

	/**
	 * 编码
	 */
	public static String chinapnr_input_charset = "utf-8";

	/**
	 * 版本号
	 */
	public static String chinapnr_version = "10";

	public static String chinapnr_signtype = "";

	public static String chinapnr_server_time_url = "";

	public static String chinapnr_tranCode = "8888";

	public static String chinapnr_see_key = "";
	/** 彩付宝子账户 */
	public static String chinapnr_cfb = "";
	/** 合和年咨询代偿子账户 */
	public static String chinapnr_dc = "";
	/** 合和年咨询咨询费子账户 */
	public static String chinapnr_zxf = "";

	/**投标最大手续费费率*/
	public static String MaxTenderRate = "";
	
	/**借款手续费率*/
	public static String BorrowerRate = "";
	
	/**
	 * 后台返回地址
	 */
	public static String chinapnr_bgRetUrl = "";

	/**
	 * 前台页面返回url
	 */
	public static String chinapnr_retUrl = "";
	public static String chinapnr_retUrl1 = "";
	public static String chinapnr_idType = "00";

	public static String chinapnr_verficationCode = "";

	public static String chinapnr_gateBusiId = "B2C";// 网关的细分业务类型，如 B2C、B2B、WH 等

	public static Map<String, String> bankMap = new HashMap<String, String>();

	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() throws Exception {
		// 获取汇付天下配置文件信息
		com.shove.io.file.PropertyFile pf = new com.shove.io.file.PropertyFile();
		
		chinapnr_merId = pf.read("chinapnr_merId");
		chinapnr_merCustId = pf.read("chinapnr_merCustId");
		
		chinapnr_gateway = pf.read("chinapnr_gateway");
		chinapnr_input_charset = pf.read("chinapnr_input_charset");
		chinapnr_retUrl_bg = getPath() + pf.read("chinapnr_retUrlBg");

		chinapnr_cfb = pf.read("chinapnr_cfb");
		chinapnr_zxf = pf.read("chinapnr_zxf");
		chinapnr_input_charset = pf.read("chinapnr_input_charset");

		chinapnr_retUrl = getPath() + pf.read("chinapnr_retUrl");
		chinapnr_bgRetUrl = getPath() + pf.read("chinapnr_bgRetUrl");

		chinapnr_version = pf.read("chinapnr_version");
		chinapnr_signtype = pf.read("chinapnr_signtype");
		chinapnr_server_time_url = pf.read("chinapnr_server_time_url");
		chinapnr_merKeyFile = pf.read("chinapnr_merKeyFile");
		chinapnr_PgPubkFile = pf.read("chinapnr_PgPubkFile");
		/*MaxTenderRate = pf.read("MaxTenderRate");
		BorrowerRate = pf.read("BorrowerRate");*/

		bankMap.put("ICBC", "工商银行");
		bankMap.put("ABC", "农行");
		bankMap.put("CMB", "招行");
		bankMap.put("CCB", "建设银行");
		bankMap.put("BCCB", "北京银行");
		bankMap.put("BJRCB", "北京农村商业银行");
		bankMap.put("BOC", "中国银行");
		bankMap.put("BOCOM", "交通银行");
		bankMap.put("CMBC", "民生银行");
		bankMap.put("BOS", "上海银行");
		bankMap.put("CBHB", "渤海银行");
		bankMap.put("CEB", "光大银行");
		bankMap.put("CIB", "兴业银行");
		bankMap.put("CITIC", "中信银行");
		bankMap.put("CZB", "浙商银行");
		bankMap.put("GDB", "广发银行");
		bankMap.put("HKBEA", "东亚银行");
		bankMap.put("HXB", "华夏银行");
		bankMap.put("HZCB", "杭州银行");
		bankMap.put("NJCB", "南京银行");
		bankMap.put("PINGAN", "平安银行");
		bankMap.put("PSBC", "邮储银行");
		bankMap.put("SDB", "深发银行");
		bankMap.put("SPDB", "浦发");
		bankMap.put("SRCB", "上海农村商业银行");

		chinapnr_dc = pf.read("chinapnr_dc");
		if (StringUtils.isBlank(chinapnr_dc)) {
			Connection conn = null;
			try {
				conn = MySQL.getConnection();
				DataSet ds = MySQL.executeQuery(conn, "select usrCustId from t_admin where id in (select distinct id from t_company where auditStat='开户成功') limit 1");
				Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
				if (map != null) {
					chinapnr_dc = Convert.strToStr(map.get("usrCustId"), "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

	public static String getPath() {
		// int port = ServletActionContext.getRequest().getServerPort();
		// String portStr = "";
		// if (port != 80) {
		// portStr = ":" + port;
		// }
		// String path = ServletActionContext.getRequest().getScheme() + "://" +
		// ServletActionContext.getRequest().getServerName() + portStr
		// + ServletActionContext.getRequest().getContextPath() + "/";
		// return path;
		return IConstants.WEB_URL;
	}
}
