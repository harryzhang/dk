package com.sp2p.action.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.agreement.common.utils.AgreementEnum;
import com.hehenian.agreement.common.utils.FilePathUtlis;
import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.colorlife.ColorLifeBuyService;
import com.hehenian.biz.common.trade.IInvestService;
import com.hehenian.biz.common.trade.dataobject.FundTradeAgreement;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.huifu.IConstants;
import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.entity.Admin;
import com.sp2p.service.BeVipService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.admin.ColorLifeService;

@SuppressWarnings("rawtypes")
public class ColorLifeInfoAction extends BasePageAction {
	public static Log log = LogFactory.getLog(ColorLifeInfoAction.class);
	private static final long serialVersionUID = 1L;
	private File userFile;
	private String userFileName;
	private String userFileContentName;
	private BeVipService beVipService;
	private IDCardValidateService iDCardValidateService;
	private ColorLifeService colorLifeService;
	private ColorLifeBuyService colorLifeBuyService;
	private List<Map<String, Object>> colorLifeBuyMap;
	
	@Autowired
	private IUserService accountUserService;
	
	@Autowired
	private IPersonService hhnPersonService;
	
	@Autowired
    private IInvestService hhnInvestService;
	
	private Log logger = LogFactory.getLog(ColorLifeInfoAction.class);
	
	/**
	 * @param accountUserService the accountUserService to set
	 */
	public void setAccountUserService(IUserService accountUserService) {
		this.accountUserService = accountUserService;
	}

	/**
	 * @param hhnPersonService the hhnPersonService to set
	 */
	public void setHhnPersonService(IPersonService hhnPersonService) {
		this.hhnPersonService = hhnPersonService;
	}

	/**
	 * @param hhnInvestService the hhnInvestService to set
	 */
	public void setHhnInvestService(IInvestService hhnInvestService) {
		this.hhnInvestService = hhnInvestService;
	}

	/**
	 * @return the colorLifeBuyMap
	 */
	public List<Map<String, Object>> getColorLifeBuyMap() {
		return colorLifeBuyMap;
	}

	/**
	 * @param colorLifeBuyMap the colorLifeBuyMap to set
	 */
	public void setColorLifeBuyMap(List<Map<String, Object>> colorLifeBuyMap) {
		this.colorLifeBuyMap = colorLifeBuyMap;
	}

	/**
	 * @param colorLifeBuyService the colorLifeBuyService to set
	 */
	public void setColorLifeBuyService(ColorLifeBuyService colorLifeBuyService) {
		this.colorLifeBuyService = colorLifeBuyService;
	}

	public String listColorLifeBuyInfoInit() {
		return SUCCESS;
	}
	
	/**
	 * 订单查询列表
	 * @return
	 * @throws Exception
	 */
	public String listColorLifeBuyInfo() throws Exception {
		/*Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.putAll(this.paramMap);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		paramsMap.put("status", ColorLifeBuyService.OrderStatus.CHECK);
		long totalNum = this.colorLifeBuyService.countBuyInfo(paramsMap);
		pageBean.setTotalNum(totalNum);
		paramsMap.put("pageNum", pageNum);
		paramsMap.put("pageSize", pageBean.getPageSize());
		colorLifeBuyMap = this.colorLifeBuyService.listBuyInfo(paramsMap);
		pageBean.setPage(colorLifeBuyMap);
		request().setAttribute("pageNum", pageNum);*/
		initPageBean(ColorLifeBuyService.OrderStatus.CHECK);
		
		return SUCCESS;
	}
	
	public String listColorLifeVerifierInfoInit() {
		return SUCCESS;
	}
	
	/**
	 * 订单审核列表
	 * @return
	 * @throws Exception
	 */
	public String listColorLifeVerifierInfo() throws Exception {
		/*Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.putAll(this.paramMap);
		paramsMap.put("status", ColorLifeBuyService.OrderStatus.CHECK);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		long totalNum = this.colorLifeBuyService.countBuyInfo(paramsMap);
		pageBean.setTotalNum(totalNum);
		paramsMap.put("pageNum", pageNum);
		paramsMap.put("pageSize", pageBean.getPageSize());
		colorLifeBuyMap = this.colorLifeBuyService.listBuyInfo(paramsMap);
		pageBean.setPage(colorLifeBuyMap);
		
		request().setAttribute("pageNum", pageNum);*/
		initPageBean(ColorLifeBuyService.OrderStatus.CHECK);
		
		return SUCCESS;
	}
	
	
	/**
	 * 单个订单审核
	 * @param orderNo
	 * @param message
	 */
	public int singleVerifier(Long orderNo) {
		Admin admin = (Admin) ServletActionContext.getRequest().getSession().getAttribute(IConstants.SESSION_ADMIN);
		long userId = -1;
		try {
			int sign = this.colorLifeBuyService.updateBuyInfo(orderNo, ColorLifeBuyService.OrderStatus.CHECK_SUCCESS,
					admin.getId());
			Map<String, Object> colorLifeBuyMap = this.colorLifeBuyService.findById(orderNo);
			if (sign > 0) {
				FundTradeAgreement fta = new FundTradeAgreement();
				String fileName = FilePathUtlis.getFileName("re_", "pdf");
				fta.setId(null);
				userId = Long.parseLong(colorLifeBuyMap.get("userId").toString());
				fta.setUserId(userId);
				fta.setAgreementType(3);
				fta.setTradeId(Integer.parseInt(orderNo.toString()));
				fta.setCreateTime(new Date());
				fta.setAgreementFileName(fileName);

				String userName = ObjectUtils.toString(userId);
				AccountUserDo aud = accountUserService.getById(userId);
				if (aud != null) {
					userName = aud.getUsername();
				}
				Date updateDate = (Date) colorLifeBuyMap.get("updateTime");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(updateDate);
				calendar.add(Calendar.DAY_OF_MONTH, 1);

				String buyMoney = colorLifeBuyMap.get("buyMoney").toString();

				PersonDo pd = hhnPersonService.getByUserId(userId);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("agreementNo", FilePathUtlis.getAgreementNo("FI"));
				map.put("aName", pd.getRealName());
				map.put("idCard", pd.getIdNo());
				map.put("tel", pd.getCellPhone());
				map.put("userName", userName);
				map.put("createTime", DateFormatUtils.format(updateDate, "yyyy年MM月dd日"));
				map.put("term", colorLifeBuyMap.get("productPeriod"));
				map.put("yearRate", ((BigDecimal) colorLifeBuyMap.get("rate")).floatValue() * 100 + "%");

				map.put("principal", ObjectUtils.toString(Double.parseDouble(buyMoney)));

				map.put("computeDay", DateFormatUtils.format(calendar.getTime(), "yyyy年MM月dd日"));

				calendar.add(Calendar.MONTH, (Integer) colorLifeBuyMap.get("productPeriod"));

				map.put("dueDay", DateFormatUtils.format(calendar.getTime(), "yyyy年MM月dd日"));
				map.put("pName", colorLifeBuyMap.get("productName"));

				map.put("lockPeriod", "3");
				map.put("minInvest", "500");

				int ret = hhnInvestService.saveAgreement(fta, AgreementEnum.RedEnvelope, map);
				logger.info("fta======>" + fta + "|" + ret);
				
				return sign;
			}
		} catch (Exception e) {
			// 保存失败将订单状态改为未支付状态
			this.colorLifeBuyService.updateStatus(orderNo, ColorLifeBuyService.OrderStatus.CHECK);
			e.printStackTrace();
			logger.error(userId + "生成购买协议失败", e);
			return -1;
		}
		return -1;
	}
	
	/**
	 * 审核订单
	 * @return
	 * @throws Exception
	 */
	public String colorLifeVerifier() throws Exception {
		JSONObject obj = new JSONObject();
		StringBuffer checkSuccessMsg = new StringBuffer();
		StringBuffer checkFailureMsg = new StringBuffer();
		String orderNoStr = ServletActionContext.getRequest().getParameter("orderId");
		if (StringUtils.isNotEmpty(orderNoStr)) {
			String[] orderNos = orderNoStr.split(",");
			if (orderNos.length > 0) {
				for (int i = 0; i < orderNos.length; i++) {
					String orderNo = orderNos[i];
					Long longOrderNo = Long.parseLong(orderNo);
					int sign = singleVerifier(longOrderNo);
					if (sign > 0) {
						checkSuccessMsg.append(longOrderNo);
						if (orderNos.length - 1 > i) {
							checkSuccessMsg.append(",");
						}
					} else {
						checkFailureMsg.append(longOrderNo);
						if (orderNos.length - 1 > i) {
							checkFailureMsg.append(",");
						}
					}
				}
				obj.put("success", checkSuccessMsg.toString());
				obj.put("failure", checkFailureMsg.toString());
				obj.put("msg", "2");
			} else {
				try {
					singleVerifier(Long.parseLong(orderNoStr));
					obj.put("msg", "1");
				} catch (Exception e) {
					obj.put("msg", "审核失败！");
					e.printStackTrace();
				}
			}
		}
		JSONUtils.printObject(obj);
		return null;
	}
	
	public String listColorLifeCheckedInfoInit() {
		return SUCCESS;
	}
	
	/**
	 * 已审核订单列表
	 * @return
	 * @throws Exception
	 */
	public String listColorLifeCheckedInfo() throws Exception {
		initPageBean(ColorLifeBuyService.OrderStatus.CHECK_SUCCESS);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private int initPageBean(int orderStatus) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.putAll(this.paramMap);
		paramsMap.put("status", orderStatus);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		long totalNum = this.colorLifeBuyService.countBuyInfo(paramsMap);
		pageBean.setTotalNum(totalNum);
		paramsMap.put("pageNum", pageNum);
		paramsMap.put("pageSize", pageBean.getPageSize());
		List<Map<String, Object>> colorLifeBuyMap = this.colorLifeBuyService.listBuyInfo(paramsMap);
		pageBean.setPage(colorLifeBuyMap);
		
		request().setAttribute("pageNum", pageNum);
		return pageNum;
	}
	
	/**
	 * 导出红包理财购买成功Excel
	 */
	public void exportExcelCheckedInfo() {
		
	}
	
	/**
	 * 红包理财导出Excel
	 * @param colorLifeBuyList
	 * 			数据集合
	 * @param titles
	 * 			导出excel列明
	 * @param columns
	 * 			数据key
	 */
	public void exportExcel(List<Map<String, Object>> colorLifeBuyList, String[] titles, String[] columns) {
		try {
			if (null == colorLifeBuyList) {
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return;
			}
			
			if (colorLifeBuyList.size() > IConstants.EXCEL_MAX) {
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return;
			}// 序号 用户名 真实姓名 手机号码 身份证 用户来源 注册时间 可用金额 冻结金额 待收金额
			
			for (Map<String, Object> colorLifeBuyMap : colorLifeBuyList) {
				Date date = (Date) colorLifeBuyMap.get("buyDate");
				String productName = (String) colorLifeBuyMap.get("productName");
				BigDecimal rate = (BigDecimal) colorLifeBuyMap.get("rate");
				Integer productPeriod = (Integer) colorLifeBuyMap.get("productPeriod");
				if (date != null) {
					colorLifeBuyMap.put("buyDate", DateUtils.formatDate(date));
				}
				if (productName != null) {
					StringBuffer pdBuffer = new StringBuffer().append(productName).append("·").append(productPeriod).append("-").append("M");
					colorLifeBuyMap.put("productName", pdBuffer.toString());
				}
				if (rate != null) {
					colorLifeBuyMap.put("rate", rate + "%");
				}
				if (productPeriod != null) {
					colorLifeBuyMap.put("productPeriod", productPeriod + "个月");
				}
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("红包理财订购列表", colorLifeBuyList, titles, columns);
			this.export(wb, new Date().getTime() + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 红包订单理财导出Excel
	 * @return
	 */
	public String exportExcelBuyInfo() {
		initPageBean(ColorLifeBuyService.OrderStatus.CHECK);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> colorLifeBuyList = pageBean.getPage();
		exportExcel(colorLifeBuyList, new String[] { "交易ID", "用户名称", "用户ID", "彩管家OA", "购买产品名称", "购买金额", "利率", "购买期限", "购买日期", "状态" }, 
				new String[] { "orderId", "realName", "userId", "thethirdusername", "productName", "buyMoney", "rate", "productPeriod", "buyDate", "statusLabel" });
		return null;
	}

	/**
	 * 彩生活数据导入
	 * */
	public String importInfo() {
		int result = 0;
		if (userFile != null) {
			try {
				//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String[][] datas = ExcelUtils.getData(userFile, 2);
				//Date nowDate = new Date();
				//boolean falg = true;
				List<String[]> dataList = new ArrayList<String[]>();

				if (datas != null && datas.length > 0) {
					for (int j = 0; j < datas.length; j++) {
						if (datas[j].length < 19) {
							dataList.add(datas[j]);
						}
					}
					if (dataList.size() > 0) {
						this.addFieldError("userFile", "导入错误,请严格按照模板填写资料!");
						return INPUT;
					}
					List<Map<String, String>> personList = new ArrayList<Map<String, String>>();
					for (int i = 0; i < datas.length; i++) {
						Map<String, String> personMap = new HashMap<String, String>();
						String realName = datas[i][0]; // 客户名称
						String idNo = datas[i][19];// 身份证号码
						//String companyPhone = datas[i][16];// 单位电话

						if (!checkIdNo(idNo)) {
							this.addFieldError("userFile", "导入错误,第" + (i + 2)
									+ "行,身份证号码不正确,请重试");
							return INPUT;
						}
						personMap = new HashMap<String, String>();
						personMap.put("realName", realName);
						personMap.put("age", datas[i][1]); // 年龄
						personMap.put("sex", datas[i][2]); // 性别
						personMap.put("maritalStatus", datas[i][3]); // 婚姻状况
						personMap.put("houseStatus", datas[i][4]); // 居住情况
						personMap.put("highestEdu", datas[i][5]); // 文化程度
						personMap.put("address", datas[i][6]); // 文化程度
						personMap.put("houseWorth", datas[i][7]); // 房产实际价值
						personMap.put("surplusMortgageYear", datas[i][8]); // 剩余按揭年限
						personMap.put("hasCar", datas[i][9]); // 是否有车
						personMap.put("orgName", datas[i][10]); // 工作单位
						personMap.put("companyType", datas[i][11]); // 单位性质
						personMap.put("companyScale", datas[i][12]); // 单位规模
						personMap.put("job", datas[i][13]); // 工作职位
						personMap.put("annualIncome", datas[i][14]); // 年收入
						personMap.put("workYear", datas[i][15]); // 现单位工作年限
						personMap.put("companyPhone", datas[i][16]); // 单位电话
						personMap.put("companyEmail", datas[i][17]); // 单位邮箱
						personMap.put("companyAddress", datas[i][18]); // 公司地址
						personMap.put("idNo", idNo); // 身份证号
						personList.add(personMap);
					}
					// 导入数据
					List<String> resultList = colorLifeService
							.importInfo(personList);
					if (resultList != null && resultList.size() > 0) {
						request().setAttribute("resultList", resultList);
						return INPUT;
					} else {
						result = 1;
					}
				} else {
					result = -1;
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.error(e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
			}
		}
		if (result == 1) {
			this.addActionMessage("导入成功!");
			return SUCCESS;
		} else {
			this.addFieldError("userFile", "导入错误,请严格按照模板填写资料!");
			return INPUT;
		}
	}

	/**
	 * 随机生成账户密码
	 * */
	public String randomUserNamePassword() {
		String randomStr = UUID.randomUUID().toString();
		return randomStr.substring(randomStr.length() - 8, randomStr.length());
	}

	/**
	 * //验证身份证
	 * 
	 * @throws DataException
	 * @throws SQLException
	 * */
	public boolean checkIdNo(String idNo) throws SQLException, DataException {
		long len = idNo.length();
		if (len < 15) {
			return false;
		}
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(idNo.substring(12, 15));
		} else {
			sortCode = Integer.parseInt(idNo.substring(14, 17));
		}
		if (sortCode % 2 == 0) {
			MAN_SEX = 2;// 女性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 1;// 男性身份证
		} else {
			return false;
		}
		String iDresutl = "";
		System.out.println(this.iDCardValidateService);
		iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
		if (iDresutl.equals("")) {
			// 验证身份证唯一
			Map<String, String> map = beVipService.queryIDCard(idNo);
			if (map != null && map.size() > 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证手机号码正确 和 唯一
	 * 
	 * @param cellPhone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public boolean checkCellPhone(String cellPhone) throws SQLException,
			DataException {
		String regex = "^[1][3,5,4,8][0-9]+{8}$";
		// String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		if (Pattern.matches(regex, cellPhone)) {
			// 验证手机的唯一性
			Map<String, String> phonemap = beVipService.queryIsPhone(cellPhone);
			if (phonemap != null) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 随机取得借款默认图片
	 * 
	 * @return
	 */
	public String randomImg() {
		String[] imgArr = { "images/random/1.jpg", "images/random/2.jpg",
				"images/random/3.jpg", "images/random/4.jpg",
				"images/random/5.jpg", "images/random/6.jpg" };
		int index = (int) (Math.random() * imgArr.length);
		return imgArr[index];
	}

	public File getUserFile() {
		return userFile;
	}

	public void setUserFile(File userFile) {
		this.userFile = userFile;
	}

	public String getUserFileName() {
		return userFileName;
	}

	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}

	public String getUserFileContentName() {
		return userFileContentName;
	}

	public void setUserFileContentName(String userFileContentName) {
		this.userFileContentName = userFileContentName;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setiDCardValidateService(
			IDCardValidateService iDCardValidateService) {
		this.iDCardValidateService = iDCardValidateService;
	}

	public void setColorLifeService(ColorLifeService colorLifeService) {
		this.colorLifeService = colorLifeService;
	}

}
