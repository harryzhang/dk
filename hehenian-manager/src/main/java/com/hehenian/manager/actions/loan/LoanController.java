package com.hehenian.manager.actions.loan;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.ICommonService;
import com.hehenian.biz.common.loan.ILoanApplyService;
import com.hehenian.biz.common.loan.ILoanContractService;
import com.hehenian.biz.common.loan.ILoanLogService;
import com.hehenian.biz.common.loan.ILoanPersonCheckService;
import com.hehenian.biz.common.loan.ILoanPersonCreditService;
import com.hehenian.biz.common.loan.IManagerLoanRepaymentService;
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.IManagerLoanUserService;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckedDo;
import com.hehenian.biz.common.loan.dataobject.LoanCreditRecDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanLogDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCheckDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.LoanStatusDo;
import com.hehenian.biz.common.loan.dataobject.LoanUserBankDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.common.system.ISettSchemeService;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.common.util.Md5Utils;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.actions.common.Constants;
import com.hehenian.manager.actions.common.ExeclTools;
import com.hehenian.manager.actions.common.Maps;
import com.hehenian.manager.actions.common.PageDoUtil;
import com.hehenian.manager.commons.NewPagination;
import com.hehenian.manager.modules.sys.model.UserInfos;

@Controller
@RequestMapping("/loan/*")
public class LoanController extends BaseAction {
	private static Log log=LogFactory.getLog(LoanController.class);

	@Autowired
	private IManagerLoanService managerLoanService;
	
	@Autowired
	private IManagerLoanUserService managerLoanUserService;
	
	@Autowired
	private ISettSchemeService settSchemeService;
	
	@Autowired
	private ILoanLogService loanLogService;
	
	@Autowired
	private ICommonService commonService;
	

	@Autowired
	private ILoanApplyService loanApplyService;
	
	@Autowired
	private ILoanContractService loanContractService;
	
	

	@Autowired
	private ILoanPersonCreditService loanPersonCreditService;
	
	@Autowired
	private ILoanPersonCheckService loanPersonCheckService;
	
	@Autowired
	private IManagerLoanRepaymentService loanRepaymentService;

	//上标服务URL
	@Value("#{sysconfig['loan.sb.url']}")
	private String loanSbUrl ;
	//查看E贷款图片资料地址
	@Value("#{sysconfig['manager.accessUrl']}")
	private String fileSeverDir ;
	//放款
	@Value("#{sysconfig['loan.fk.url']}")
	private String loanFkUrl ;
	//生产协议,合同
	@Value("#{sysconfig['manager.genPdfUrl']}")
	private String genPdfUrl ;
	//访问协议和合同地址
	@Value("#{sysconfig['manager.genPdfFilePath']}")
	private String savePath;
	
	
	private Logger logger = Logger.getLogger(LoanController.class);
	
	
	Map<Object, Object> map_success = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_SUCCESS);
	Map<Object, Object> map_failure  = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_FAILURE);
	
	
	/**
	 * 彩生活的接口没有完成， 用次接口模拟彩生活的回复
	 * @return
	 */
	@RequestMapping("/proxyColorCheck")
	public void proxyColorCheck(String sign, String cname ,String houseNo,String ownerName,String  ownerIDCardNo,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", "0");
		resultMap.put("message", "ok");
		Map<String,String> contextMap = new HashMap<String,String>();
		java.util.Random r = new java.util.Random();
		int value = r.nextInt(3);
		contextMap.put("cname", ""+value);
		contextMap.put("houseNo", ""+value);
		contextMap.put("ownerName", ""+value);
		contextMap.put("ownerIDCardNo", ""+value);
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i<18;i++){
			sb.append(r.nextInt(3));
		}
		contextMap.put("managementFee", ""+sb.toString());
		resultMap.put("content", contextMap);
		outPrint(response, JSONSerializer.toJSON(resultMap));
	}
	
	/**
	 * 进入订单页面
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/index")
	public String menuLoan(ModelMap modelMap){
		return "/loan/loanIndex";
	}
	
	/**
	 * 产品类型下拉列表
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param response
	 */
	@RequestMapping("/combox/prod/type")
	public void loanProdType(HttpServletResponse response){
		Map<String,Object> proParam = new HashMap<String,Object>();
		proParam.put("status","T");
		List<LoanProductDo> productDoList = managerLoanService.listLoanProduct(proParam);
		LoanProductDo loanProductDo = new LoanProductDo();
		loanProductDo.setName("请选择...");
		loanProductDo.setId(null);
		productDoList.add(0, loanProductDo);
		outPrint(response, JSONSerializer.toJSON(productDoList));
	}
	
	/**
	 * 订单状态
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param response
	 */
	@RequestMapping("/combox/status")
	public void loanStatusList(HttpServletResponse response){
		List<LoanStatusDo> loanStatusList = LoanDo.getLoanStatusList();
		LoanStatusDo loanStatusDo = new LoanStatusDo();
		loanStatusDo.setDesc("请选择...");
		loanStatusList.add(0, loanStatusDo);
		outPrint(response, JSONSerializer.toJSON(loanStatusList));
	}
	
	
	/**
	 * 订单状态
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param response
	 */
	@RequestMapping("/combox/channelType")
	public void channelTypeList(HttpServletResponse response){
		List<Map<String,Object>> channelTypeList = managerLoanService.getChannelTypeList();
		outPrint(response, JSONSerializer.toJSON(channelTypeList));
	}
	
	

	
	@RequestMapping("/getRepayment")
	public void getRepayment(long loanId , ModelMap modelMap,HttpServletResponse response){
		logger.debug("getRepayment: loanId:"+loanId);
		List<LoanRepaymentDo> repaymentList =  loanRepaymentService.selectRepaymentByLoanId(loanId);
		
		 JsonConfig jsonConfig = new JsonConfig();
	        jsonConfig.registerJsonValueProcessor(Date.class , new JsonValueProcessor(){
	        	private String format ="yyyy-MM-dd";   
	            public Object processArrayValue(Object value, JsonConfig config) {   
	                return process(value);   
	            }   
	            public Object processObjectValue(String key, Object value, JsonConfig config) {   
	                return process(value);   
	            }   
	            private Object process(Object value){   
	                if(value instanceof Date){   
	                    SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);   
	                    return sdf.format(value);   
	                }   
	                return value == null ? "" : value.toString();   
	            } 
	            
				});
	        
		outPrint(response, JSONSerializer.toJSON(repaymentList , jsonConfig));
	}
	
	/**
	 * 订单列表
	 * @auther liminglmf
	 * @date 2015年5月11日
	 * @param pagination
	 * @param modelMap
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public void loanList(NewPagination<LoanDo> pagination,ModelMap modelMap,HttpServletResponse response){
		PageDo<LoanDo> page = PageDoUtil.getPage(pagination);
		Map<String,Object> param = new HashMap<String,Object>();
		
		String searchStr = getString("searchStr");//搜索条件
		if(StringUtils.isNotBlank(searchStr)){
			boolean result=searchStr.matches("[0-9]+");
			if(result){
				if(searchStr.length()>3){
					param.put("mobile", searchStr);
				}
			}else{
				if(searchStr.startsWith("D")){
					param.put("orderCode", searchStr);
				}else{
					param.put("realName", searchStr);
				}
			}
		}
		String productCode = getString("productCode");//产品类型
		if(StringUtils.isNotBlank(productCode)){
			param.put("productCode", productCode);
		}
		
		String loanType = getString("loanType");//贷款类型
		if(StringUtils.isNotBlank(loanType)){
			param.put("loanType", loanType);
		}
		
		String loanStatus = getString("loanStatus");//订单状态
		if(StringUtils.isNotBlank(loanStatus)){
			param.put("loanStatus", loanStatus);
		}
		
		String startDate = getString("startDate");//开始日期
		if(StringUtils.isNotBlank(startDate)){
			param.put("startDate", startDate);
		}
		
		String endDate = getString("endDate");//结束日期
		if(StringUtils.isNotBlank(endDate)){
			param.put("endDate", endDate);
		}
		
		String processNextStep = getString("processNextStep");//下一节点
		if(StringUtils.isNotBlank(processNextStep)){
			param.put("processNextStep", processNextStep);
		}
		
		page = managerLoanService.getLoanListPage(param,page);
		pagination = PageDoUtil.getPageValue(pagination, page);
		outPrint(response, JSONObject.fromObject(pagination, registerJsonBeanProcessor()));
	}
	
	@RequestMapping("/sett/all")
	public void settList(Long id,String productCode , HttpServletResponse response){
		Map<String,Object> searchItems = new HashMap<String,Object>();
		searchItems.put("productCode", productCode);
		List<SettSchemeDo> settList = settSchemeService.getSettSchemesList(searchItems);
		outPrint(response, JSONArray.fromObject(settList,registerSett()));
	}
	
	private JsonConfig registerSett() {
        JsonConfig jsonconfig = new JsonConfig();
        jsonconfig.registerJsonBeanProcessor(SettSchemeDo.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
            	SettSchemeDo sett = (SettSchemeDo)bean;
                JSONObject jsonobj = new JSONObject();
					jsonobj.element("id", sett.getSchemeId())
					.element("text",sett.getSchemeName() == null ? sett.getSchemeCode() : sett.getSchemeName());
                return jsonobj;
            }
        });
        return jsonconfig;
    }
	
	/**
	 *  注册解析json
	 * @auther liminglmf
	 * @date 2015年5月20日
	 * @return
	 */
    private JsonConfig registerJsonBeanProcessor() {
        JsonConfig jsonconfig = new JsonConfig();
        jsonconfig.registerJsonBeanProcessor(LoanDo.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("##,###.00");
            	LoanDo loanDo = (LoanDo)bean;
                JSONObject jsonobj = new JSONObject();
                //LoanPersonDo  loanPersonDo  = loanDo.getLoanPersonDo();
					jsonobj.element("loanId", loanDo.getLoanId())
					.element("loanType",loanDo.getLoanType() == null ? "":loanDo.getLoanType())
					.element("userId",loanDo.getUserId() == null ? "":loanDo.getUserId())
					.element("schemeId",loanDo.getSchemeId() == null ? "":loanDo.getSchemeId())
					.element("annualRate", loanDo.getAnnualRate() == null ?"":loanDo.getAnnualRate())
					.element("borrowId", loanDo.getBorrowId() == null ?"":loanDo.getBorrowId())
					.element("loanTitle", StringUtils.defaultString(loanDo.getLoanTitle()))
					.element("loanUsage", StringUtils.defaultString(loanDo.getLoanUsage()))
					
					.element("applyAmount",loanDo.getApplyAmount() == null ? "":myformat.format(loanDo.getApplyAmount()))
					
					.element("loanPeriod", loanDo.getLoanPeriod() == null ?"":loanDo.getLoanPeriod())
					.element("loanTime", loanDo.getLoanTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loanDo.getLoanTime()))
					.element("loanAmount",loanDo.getLoanAmount() == null ? "":loanDo.getLoanAmount())
					.element("remark", StringUtils.defaultString(loanDo.getRemark()))
					.element("loanStatus", StringUtils.defaultString(loanDo.getLoanStatus().toString()))
					.element("processCurrentStep", StringUtils.defaultString(loanDo.getProcessCurrentStep()== null ? "": LoanDo.getLoanStepName(loanDo.getProcessCurrentStep())))
					.element("processNextStep", StringUtils.defaultString(loanDo.getProcessNextStep()==null?"": LoanDo.getLoanStepName(loanDo.getProcessNextStep())))
					
					
				    .element("realName", StringUtils.defaultString(loanDo.getRealName()))
					.element("mobile", StringUtils.defaultString(loanDo.getMobile()))
					.element("referenceMobile", StringUtils.defaultString(loanDo.getReferenceMobile()))
					.element("productName", StringUtils.defaultString(loanDo.getProductName()))
					.element("schemeName", StringUtils.defaultString(loanDo.getSchemeName()))
					.element("auditAmount", StringUtils.defaultString(loanDo.getAuditAmount()==null? "":loanDo.getAuditAmount().toString()))
					.element("loanStatusDesc", StringUtils.defaultString(LoanStatusDo.getLoanStatusDesc(loanDo.getLoanStatus().toString())))
					
					.element("auditUserId",loanDo.getAuditUserId() == null ? "":loanDo.getAuditUserId())
					.element("auditUser", StringUtils.defaultString(loanDo.getAuditUser()))
					.element("auditTime", loanDo.getAuditTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loanDo.getAuditTime()))
					.element("createTime", loanDo.getCreateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loanDo.getCreateTime()))
					.element("updateTime", loanDo.getUpdateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loanDo.getUpdateTime()))
					.element("productCode", StringUtils.defaultString(loanDo.getProductCode()))
					.element("orderCode", StringUtils.defaultString(loanDo.getOrderCode()))
					.element("channelId", StringUtils.defaultString(LoanDo.getChannelName(loanDo.getChannelId())))
					.element("subjectType", loanDo.getSubjectType() == null? "":( loanDo.getSubjectType()==1?"汇付":"通联"))
					.element("investAnnualRate",loanDo.getInvestAnnualRate() == null ? "":loanDo.getInvestAnnualRate().toString());
                return jsonobj;
            }
        });
        return jsonconfig;
    }
    
    /**
     * 贷款人信息
     * @auther liminglmf
     * @date 2015年5月20日
     * @return
     */
    private JsonConfig registerJsonBeanPersion() {
    	JsonConfig jsonconfig = new JsonConfig();
    	jsonconfig.registerJsonBeanProcessor(LoanPersonDo.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
            	LoanPersonDo loanPersonDo = (LoanPersonDo)bean;
                JSONObject jsonobj = new JSONObject();
                	jsonobj.element("loanPersonId", loanPersonDo.getLoanPersonId())
					.element("loanId", loanPersonDo.getLoanId() == null ? "":loanPersonDo.getLoanId())
					.element("realName", StringUtils.defaultString(loanPersonDo.getRealName()))
					.element("mobile", StringUtils.defaultString(loanPersonDo.getMobile()))
					.element("referenceMobile", StringUtils.defaultString(loanPersonDo.getReferenceMobile()))
					.element("idNo", StringUtils.defaultString(loanPersonDo.getIdNo()))
					.element("age", loanPersonDo.getAge() == null ? "":loanPersonDo.getAge())
					.element("sex", loanPersonDo.getSex() == null ? "": loanPersonDo.getSex().toString() == "MALE" ? "男":"女")
					.element("colorId", StringUtils.defaultString(loanPersonDo.getColorId()))
					.element("cid", loanPersonDo.getCid() == null ? "":loanPersonDo.getCid())
					.element("cname", StringUtils.defaultString(loanPersonDo.getCname()))
					.element("caddress", StringUtils.defaultString(loanPersonDo.getCaddress()))
					.element("marriaged", loanPersonDo.getMarriaged() == null ? "": 
						loanPersonDo.getMarriaged().toString() == "UNMARRIED" ? "未婚":loanPersonDo.getMarriaged().toString() == "MARRIED" ? "已婚":"离异")
					.element("education", loanPersonDo.getEducation() == null ? "": 
						loanPersonDo.getEducation().toString() == "GRADE_SCHOOL" ? "初中以下":
							loanPersonDo.getEducation().toString() == "HIGN_SCHOOL" ? "高中":
								loanPersonDo.getEducation().toString() == "POLYTECH_SCHOOL" ? "中技":
									loanPersonDo.getEducation().toString() == "VOCATION_SCHOOL" ? "中专":
										loanPersonDo.getEducation().toString() == "JUNIOR_COLLEGE" ? "大专":
											loanPersonDo.getEducation().toString() == "BACHELOR" ? "本科以上":
												loanPersonDo.getEducation().toString() == "MASTER" ? "硕士 ":"博士")	
					.element("familyPhone", StringUtils.defaultString(loanPersonDo.getFamilyPhone()))	
					.element("email", StringUtils.defaultString(loanPersonDo.getEmail()))	
					.element("remark", StringUtils.defaultString(loanPersonDo.getRemark()))	
					.element("updateUser", StringUtils.defaultString(loanPersonDo.getUpdateUser()))	
					.element("applyTime", loanPersonDo.getApplyTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loanPersonDo.getApplyTime()))
					.element("createTime", loanPersonDo.getCreateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loanPersonDo.getCreateTime()))
					.element("updateTime", loanPersonDo.getUpdateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loanPersonDo.getUpdateTime()))
                	.element("propertyDo", loanPersonDo.getPropertyDo() == null ? "" : loanPersonDo.getPropertyDo(),registerJsonBeanProperty())
                	.element("certList", loanPersonDo.getCertificateDoList() == null ? "" : loanPersonDo.getCertificateDoList(),registerJsonBeanCert())
                	.element("jobDo", loanPersonDo.getJobDo() == null ? "" : loanPersonDo.getJobDo(),registerJsonBeanJob());
            	return jsonobj;
            }
    	});    
    	return jsonconfig;
    }
    
    /**
     * 贷款人资产信息
     * @auther liminglmf
     * @date 2015年5月20日
     * @return
     */
    private JsonConfig registerJsonBeanProperty() {
    	JsonConfig jsonconfig = new JsonConfig();
    	jsonconfig.registerJsonBeanProcessor(PropertyDo.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
            	PropertyDo propertyDo = (PropertyDo)bean;
                JSONObject jsonobj = new JSONObject();
                	jsonobj.element("propertyId", propertyDo.getPropertyId())
					.element("loanId", propertyDo.getLoanId() == null ? "":propertyDo.getLoanId())
					.element("loanPersonId", propertyDo.getLoanPersonId() == null ? "":propertyDo.getLoanPersonId())
					.element("houseAddress", StringUtils.defaultString(propertyDo.getHouseAddress()))
					.element("advisedPeople", StringUtils.defaultString(propertyDo.getAdvisedPeople()))
					.element("carNo", StringUtils.defaultString(propertyDo.getCarNo()))
					.element("carBrand", StringUtils.defaultString(propertyDo.getCarBrand()))
					
					.element("houstWay", propertyDo.getHoustWay() == null ? "":propertyDo.getHoustWay() == 0 ? "一次性":"按揭 ")
					.element("hyear", propertyDo.getHyear() == null ? "":propertyDo.getHyear())
					.element("hmonth", propertyDo.getHmonth() == null ? "":propertyDo.getHmonth())
					.element("hday", propertyDo.getHday() == null ? "":propertyDo.getHday())
					.element("housePrice", propertyDo.getHousePrice() == null ? "":propertyDo.getHousePrice().intValue())
					.element("coveredArea", propertyDo.getCoveredArea() == null ? "":propertyDo.getCoveredArea())
					.element("houseDy", propertyDo.getHouseDy() == null ? "":propertyDo.getHouseDy() == 0 ? "否":"是")
					.element("houseReDur", propertyDo.getHouseReDur() == null ? "":propertyDo.getHouseReDur())
					.element("houseMthOwing", propertyDo.getHouseMthOwing() == null ? "":propertyDo.getHouseMthOwing())
					.element("houseReMtg", propertyDo.getHouseReMtg() == null ? "":propertyDo.getHouseReMtg())
					.element("carPrice", propertyDo.getCarPrice() == null ? "":propertyDo.getCarPrice().intValue())
					.element("cyear", propertyDo.getCyear() == null ? "":propertyDo.getCyear())
					
					.element("cmonth", propertyDo.getCmonth() == null ? "":propertyDo.getCmonth())
					.element("cday", propertyDo.getCday() == null ? "":propertyDo.getCday())
					.element("carWay", propertyDo.getCarWay() == null ? "": propertyDo.getCarWay() == 0 ? "一次性" : "按揭")
					.element("carDy", propertyDo.getCarDy() == null ? "":propertyDo.getCarDy() == 0 ? "否":"是")
					.element("carReDur", propertyDo.getCarReDur() == null ? "":propertyDo.getCarReDur())
					.element("carMthOwing", propertyDo.getCarMthOwing() == null ? "":propertyDo.getCarMthOwing())
					.element("carReMtg", propertyDo.getCarReMtg() == null ? "":propertyDo.getCarReMtg())
					.element("purchWay",propertyDo.getPurchaseWay() == null ? "":propertyDo.getPurchaseWay().toString())
					.element("carWayInt", propertyDo.getCarWay() == null ? "": propertyDo.getCarWay())
					.element("purchaseWay", propertyDo.getPurchaseWay() == null ? "":propertyDo.getPurchaseWay().toString() == "NOMORTGAGE" ? "自置无按揭":"自置有按揭") /** 购买方式（NOMORTGAGE-自置无按揭，MORTGAGE-自置有按揭） */
					.element("houseStatus", propertyDo.getHouseStatus() == null ? "":propertyDo.getHouseStatus().toString() == "PASS" ? "审批通过" : "审批不过")/** 个人资产验证状态（PASS-审批通过，NOPASS-审批不过） */

					.element("purchaseDate", propertyDo.getPurchaseDate() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(propertyDo.getPurchaseDate()))
					.element("carDate", propertyDo.getCarDate() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(propertyDo.getCarDate()))
					.element("createTime", propertyDo.getCreateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(propertyDo.getCreateTime()))
					.element("updateTime", propertyDo.getUpdateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(propertyDo.getUpdateTime()));
            	return jsonobj;
            }
    	});    
    	return jsonconfig;
    }
    
    /**
     * 贷款人工作信息
     * @auther liminglmf
     * @date 2015年5月20日
     * @return
     */
    private JsonConfig registerJsonBeanJob() {
    	JsonConfig jsonconfig = new JsonConfig();
    	jsonconfig.registerJsonBeanProcessor(JobDo.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
            	JobDo jobDo = (JobDo)bean;
                JSONObject jsonobj = new JSONObject();

                	
                	jsonobj.element("jobId", jobDo.getJobId() == null ? "":jobDo.getJobId())
                	.element("loanPersonId", jobDo.getLoanPersonId())
					.element("loanId", jobDo.getLoanId() == null ? "":jobDo.getLoanId())
					
					
					.element("certNo", StringUtils.defaultString(jobDo.getCertNo()))
					.element("companyAddr", StringUtils.defaultString(jobDo.getCompanyAddr()))
					.element("companyInTime", StringUtils.defaultString(jobDo.getCompanyInTime()))
					.element("position", StringUtils.defaultString(jobDo.getPosition()))
					.element("companyName", StringUtils.defaultString(jobDo.getCompanyName()))
					.element("companyPhone", StringUtils.defaultString(jobDo.getCompanyPhone()))
					
					.element("jobType", jobDo.getJobType() == null ? "":
						jobDo.getJobType().toString() == "SALARYMAN" ?"工薪族" :
							jobDo.getJobType().toString() == "SELF_EMPLOYED" ? "自雇人士" :"私营业主")//工作类型（SALARYMAN-工薪族、SELF_EMPLOYED-自雇人士、EMPLOYER-私营业主）
					.element("jobIncome", jobDo.getJobIncome() == null ? "":jobDo.getJobIncome().intValue())
					.element("jobYear", jobDo.getJobYear() == null ? "":jobDo.getJobYear())
					
					.element("createTime", jobDo.getCreateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(jobDo.getCreateTime()))
					.element("updateTime", jobDo.getUpdateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(jobDo.getUpdateTime()));
            	return jsonobj;
            }
    	});    
    	return jsonconfig;
    }
    
    /**
     * 资料
     * @auther liminglmf
     * @date 2015年5月20日
     * @return
     */
    private JsonConfig registerJsonBeanCert() {
    	JsonConfig jsonconfig = new JsonConfig();
    	jsonconfig.registerJsonBeanProcessor(CertificateDo.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
            	CertificateDo certificateDo = (CertificateDo)bean;
                JSONObject jsonobj = new JSONObject();
                	jsonobj.element("certificateId", certificateDo.getCertificateId() == null ? "":certificateDo.getCertificateId())
                	.element("loanPersonId", certificateDo.getLoanPersonId())
					.element("loanId", certificateDo.getLoanId() == null ? "":certificateDo.getLoanId())
					/** （IDCARDZ-身份证正面，
					IDCARDZS-本人手持身份证正面，
					IDCARDF-身份证反面，
					HOUSE-房产证明，
					JOB-工作证明，
					PROTOCOL -协议，
					INCOME-收入流水,
					ENTRUST_PROTOCOL-委托划款协议  , 
					CREDIT_AUDITK_PROTOCOL-信用审核服务协议,
					DRIVERCARD-驾驶证,
					ASSETS --资产相关证明 
					OTHERFILE-其他证件,）*/
					.element("certificateType", certificateDo.getCertificateType() == null ? "" :
						certificateDo.getCertificateType().toString() == "IDCARDZ" ? "身份证正面" :
							certificateDo.getCertificateType().toString() == "IDCARDZS" ? "手持身份证正面" :
							certificateDo.getCertificateType().toString() == "IDCARDF" ? "身份证反面" :
							certificateDo.getCertificateType().toString() == "HOUSE" ? "房产证明" :
								certificateDo.getCertificateType().toString() == "JOB" ? "工作证明" :
									certificateDo.getCertificateType().toString() == "CREDIT" ? "征信报告" :
									certificateDo.getCertificateType().toString() == "PROTOCOL" ? "协议" :
										certificateDo.getCertificateType().toString() == "INCOME" ? "收入流水" :
											certificateDo.getCertificateType().toString() == "ENTRUST_PROTOCOL" ? "委托划款协议" :
											certificateDo.getCertificateType().toString() == "CREDIT_AUDITK_PROTOCOL" ? "信用审核服务协议" :
											certificateDo.getCertificateType().toString() == "DRIVERCARD" ? "驾驶证" :
											certificateDo.getCertificateType().toString() == "ASSETS" ? "资产相关证明 " :
											certificateDo.getCertificateType().toString() == "OTHERFILE" ? "其他证件" :"未定义类型")
					.element("certType", certificateDo.getCertificateType() == null ? "" : certificateDo.getCertificateType().toString())							
					.element("certificateName",StringUtils.defaultString(certificateDo.getCertificateName()))
					.element("filePath",certificateDo.getFilePath()== null ? "" :fileSeverDir + certificateDo.getFilePath())
					.element("destFilePath",certificateDo.getDestFilePath() == null ? "" : fileSeverDir + certificateDo.getDestFilePath())
					/** 文件类型（IMAGE-图片，VIDEO-视频，PDF-PDF） */
					.element("fileType", certificateDo.getFileType() == null ? "" :
						certificateDo.getFileType().toString() == "IMAGE" ? "图片" :
							certificateDo.getFileType().toString() == "VIDEO" ? "视频" :"PDF")
					.element("createUser",StringUtils.defaultString(certificateDo.getCreateUser()))
					.element("createTime", certificateDo.getCreateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(certificateDo.getCreateTime()))
					.element("updateTime", certificateDo.getUpdateTime() == null ? "":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(certificateDo.getUpdateTime()));
            	return jsonobj;
            }
    	});    
    	return jsonconfig;
    }
    
    
	/**
	 * 订单详情
	 * @auther liminglmf
	 * @date 2015年5月11日
	 * @param loanId
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String loanDetail(Long loanId,ModelMap modelMap){
		try{
			LoanDo loanDo = managerLoanService.getDetailLoanByLoanId(loanId);
			modelMap.addAttribute("loanDo", JSONSerializer.toJSON(loanDo == null ? "" : loanDo,registerJsonBeanProcessor()));
			LoanPersonDo loanPersonDo = managerLoanService.getLoanPersonDetail(loanId);
			modelMap.addAttribute("loanPerson", JSONSerializer.toJSON(loanPersonDo == null ? "" : loanPersonDo,registerJsonBeanPersion()));
			Long userId = loanDo.getUserId();
			if(userId != null){
				modelMap.addAttribute("bankCard", "1");
				List<LoanUserBankDo> tbcMap = managerLoanService.managerGetTbcInfo(userId);
				List<LoanUserBankDo> tdbcMap = managerLoanService.managerGetTdbcInfo(userId);
				modelMap.addAttribute("tbcMap", JSONArray.fromObject(tbcMap,registerJsonBeanBankCard()));
				modelMap.addAttribute("tdbcMap", JSONArray.fromObject(tdbcMap,registerJsonBeanBankCard()));
			}else{
				modelMap.addAttribute("bankCard", "0");
			}
			List<LoanCheckedDo> loanChecked = managerLoanService.getLoanCheckedByLoanId(loanId, null);
			LoanCheckedDo loanCheckedOne = null;
			LoanCheckedDo loanCheckedTwo = null;
			if(loanChecked != null && loanChecked.size()>0){
				for (LoanCheckedDo loanCheckedDo : loanChecked) {
					if(loanCheckedDo.getCheckType().equals("1")){
						loanCheckedOne = loanCheckedDo;
					}else{
						loanCheckedTwo = loanCheckedDo;
					}
				}
			}
			
			String creditAmount="";//信用卡额度
			String repayAmount="";//近6个月还款额
			 
			
			//征信数据
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			parameterMap.put("userId", userId);
			List<LoanPersonCreditDo> personCreditList= loanPersonCreditService.selectLoanPersonCreditWithDetail(parameterMap);
			
			//征信记录行转列
			if(null != personCreditList &&  personCreditList.size()>0){
				List<LoanCreditRecDo> creditRecList = personCreditList.get(0).getCreditRecordList();
				if(null != creditRecList){
					Map<String,String> creditRecMap = new HashMap<String,String>();
					for(LoanCreditRecDo creditRec : creditRecList){
						creditRecMap.put(creditRec.getCreditItem(),creditRec.getCreditItemVal());
					}
					modelMap.addAttribute("loanPersonCreditRec", creditRecMap);
					creditAmount=creditRecMap.get("creditAmount");
					repayAmount=creditRecMap.get("repayAmount");
				}
			}
			//end 征信数据
			
			
			modelMap.addAttribute("loanCheckedOne", loanCheckedOne);
			modelMap.addAttribute("loanCheckedTwo", loanCheckedTwo);
			
			//---ljc --20150701 ---。
			List<CertificateDo> certlist= loanPersonDo.getCertificateDoList();
			Map<String,Object> selectItem=new HashMap<String,Object>();
			selectItem.put("loanId", loanId);
			List<LoanProxyCheckDo> proxyCheckDoList=loanApplyService.selectLoanProxyCheck(selectItem);
			setModelMap_LoanCert(modelMap,proxyCheckDoList,certlist);
			modelMap.addAttribute("fileSeverDir", fileSeverDir);
			
			List<LoanPersonCheckDo> personCheckDaoList= loanPersonCheckService.selectLoanPersonCheck(selectItem);
			LoanPersonCheckDo loanPersionCheckDo=null;
			if(personCheckDaoList!=null && personCheckDaoList.size()>0)
			{
				loanPersionCheckDo=personCheckDaoList.get(0);
			}else{
				//注：1代表匹配、0代表不匹配、2代表无数据
				loanPersionCheckDo=new LoanPersonCheckDo();
				loanPersionCheckDo.setCaddress("2");
				loanPersionCheckDo.setCname("2");
				loanPersionCheckDo.setHouseAddress("2");
				loanPersionCheckDo.setIdno("2");
				loanPersionCheckDo.setMngfee("222222222222222222");
				loanPersionCheckDo.setName("2");
			}
			
			
			StringBuilder mngfeehtml=new StringBuilder();
			int yesnum=getYesNumAndSetMngFeeHtml(loanPersionCheckDo.getMngfee(),mngfeehtml);
			loanPersionCheckDo.setMngfee(yesnum>5?"1":(yesnum<0?"2":"0"));
			modelMap.addAttribute("mngfeeHtml",mngfeehtml.toString());
			modelMap.addAttribute("loanPersonCheckDo",loanPersionCheckDo);
			
			/**
			 * 房价已经保存的时候就计算好了， 征信的补入的时候会重新计算一下， 保存最大值
			//计算风控额度
			double adviceAmount=0.00;
			double fk1=0.00;
			double fk2=0.00;
			double fk3=0.00;
			//第一种：房价*房平方米*0.1
			if("T".equals(loanPersonDo.getHasHouse()) && loanPersonDo.getPropertyDo()!=null && loanPersonDo.getPropertyDo().getHousePrice()!=null)
			{
				double houseprice=loanPersonDo.getPropertyDo().getHousePrice();
				double housearea=loanPersonDo.getPropertyDo().getCoveredArea();
				fk1=houseprice*housearea*0.1;
			}
			//第二种：近6个月未完款额*借款期限*0.5
			Integer loanPeriod= loanDo.getLoanPeriod();
			if(StringUtils.isNotBlank(repayAmount) && loanPeriod!=null)
			{
				double repayamt=Double.valueOf(repayAmount);
				double repaylmt=loanPeriod*1.00;
				fk2=repayamt*repaylmt*0.5;
			}
			//第三种：信用卡额度*1.5
			if(StringUtils.isNotBlank(creditAmount))
			{
				double creditamt=Double.valueOf(creditAmount);
				fk3=creditamt*1.5;
			}
			//取三种额度最大值 
			adviceAmount=Math.max(Math.max(fk1, fk2),fk3);
			modelMap.addAttribute("adviceAmount",adviceAmount);
			**/
			
			boolean displayStep1=false;
			boolean displayStep2=false;
			boolean displayAdmin=false;
			switch (loanDo.getLoanStatus()) {
				case PENDING:
					displayAdmin=true;
				case DRAFT:
					displayStep1=true;
					displayAdmin=true;
					break;
				case PROCESSING:
					displayStep2=true;
					break;
				default:
					displayStep1=false;
					displayStep2=false;
					break;
				}
		 
			modelMap.addAttribute("displayBtnCheckStep1",hasRoleCheckStep(Constants.ROLE_LOANCHECKSTEP1)&& displayStep1);
			modelMap.addAttribute("displayBtnCheckStep2",hasRoleCheckStep(Constants.ROLE_LOANCHECKSTEP2)&& displayStep2);
			modelMap.addAttribute("displayBtnAdmin",hasRoleAdmin()&& displayAdmin);
			modelMap.addAttribute("isAdmin",hasRoleAdmin());
			modelMap.addAttribute("canEdit",canEdit(loanDo.getLoanStatus()));
			
			
			//联系人信息
			List<LoanRelationDo> listRelat = managerLoanService.getLoanRelationListByLoanId(loanId);
			modelMap.addAttribute("listRelat",JSONArray.fromObject(listRelat));
		}catch(Exception e)
		{
			log.error("获取订单明细异常：",e);
		}
		return "loan/loanDetail";
	}
	
	/**
	 * 计算近18个月缴费情况是,并生成HTML代码.
	 * @author ljc
	 * @param mngfee
	 * @param html
	 * 保存 html代码的可变长度字符串对象.
	 * @return
	 */
	private int getYesNumAndSetMngFeeHtml(String mngfee,StringBuilder html)
	{
		if(mngfee==null)
		{
			mngfee="222222222222222222";
		}
		int len=mngfee.length();
		String tagstart="<div class=\"tagbox";
		String tagend="</div>";
		len=len>18?18:len;
		int yesnum=0;
		int nonum=0;
		for(int i=0;i<len;i++)
		{
			char tag=mngfee.charAt(i);
			
			html.append(tagstart);
				String text="";
				if(tag=='0')
				{
					html.append(" tagN");
					text="N";
					nonum++;
				}else if(tag=='1')
				{
					html.append(" tagY");
					text="Y";
					yesnum++;
				}
				if(i%3==0)
				{
					html.append(" ml10");
				}
				html.append("\">");
				html.append(text);
				html.append(tagend);
		}
		if(len<18)
		{
			for(int i=len;i<18;i++)
			{
				html.append(tagstart);
				if(i%3==0)
				{
					html.append(" ml10");
				}
				html.append("\">");
				html.append(tagend);
				
			}
		}
		return (yesnum==0 && nonum==0 )?-1:yesnum;
		
	}
	
	/**
	 * 获取图片的审核状态
	 * @param proxyCheckDoList
	 * @param certdo
	 * @return
	 */
	private Integer getCertStatus(List<LoanProxyCheckDo> proxyCheckDoList,CertificateDo certdo){
		Integer result=2;
		if(proxyCheckDoList != null && certdo != null){
			for(LoanProxyCheckDo checkitem:proxyCheckDoList){
				if(certdo.getCertificateId().equals(checkitem.getRecordId())){
					result=checkitem.getStatus();
					proxyCheckDoList.remove(checkitem);
					break;
				}
			}
		}
		return result;
	}
	/**
	 * 设置ModelMap有关图片信息，包括社区管理审核信息。
	 * @param modelMap
	 * @param proxyCheckDoList
	 * 社区审核上传图片列表
	 * @param certlist
	 * 图片列表 
	 */
	private void setModelMap_LoanCert(ModelMap modelMap,List<LoanProxyCheckDo> proxyCheckDoList,List<CertificateDo> certlist)
	{
		CertificateDo certificate=null;
		Integer default_ckeck = 2;
		List listIncome = new ArrayList();
		if(certlist != null){
			for(CertificateDo item:certlist){
				CertificateDo.CertificateType  certtype=item.getCertificateType();
				switch (certtype) {
				case IDCARDZ://身份证正面
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("idcertz", certificate);
					modelMap.addAttribute("idcertz_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case IDCARDF://身份证反面
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("idcertf", certificate);
					modelMap.addAttribute("idcertf_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case IDCARDZS://手持身份证
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("idcertzs", certificate);
					modelMap.addAttribute("idcertzs_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case HOUSE://房产证
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("housecert", certificate);
					modelMap.addAttribute("housecert_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case PROTOCOL://代扣款协议
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("protocolcert", certificate);
					modelMap.addAttribute("protocolcert_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case CREDIT://征信协议
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("creditcert", certificate);
					modelMap.addAttribute("creditcert_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case DRIVERCARD://行驶证
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("driveCard", certificate);
					modelMap.addAttribute("driveCard_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case ASSETS://资产及相关
					{	
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("assets", certificate);
					modelMap.addAttribute("assets_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case JOB://工作
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("jobcert", certificate);
					modelMap.addAttribute("jobcert_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;
				case INCOME://收入流水
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					Map map = new HashMap();
					map.put("income", JSONObject.fromObject(certificate,registerJsonBeanCert()));
					map.put("income_check", default_ckeck);
					listIncome.add(map);
					certificate=null;
					default_ckeck = 2;
					break;
				case ENTRUST_PROTOCOL://委托划款协议
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("entrust_protocol", certificate);
					//modelMap.addAttribute("entrust_protocol_check", default_ckeck);
					certificate=null;
					//default_ckeck = 2;
					break;
				case CREDIT_AUDITK_PROTOCOL://信用审核服务协议
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("creaudpro", certificate);
					//modelMap.addAttribute("creaudpro_check", default_ckeck);
					certificate=null;
					//default_ckeck = 2;
					break;
				case OTHERFILE://其他证件
					{
						certificate=item;
						default_ckeck=getCertStatus(proxyCheckDoList,item);
					}
					modelMap.addAttribute("otherfile", certificate);
					modelMap.addAttribute("otherfile_check", default_ckeck);
					certificate=null;
					default_ckeck = 2;
					break;	
				default:
					break;
				}
				modelMap.addAttribute("incomes", listIncome);
			}
		}
			
	}
	
	
	/**
	 * 信审编辑
	 * @auther liminglmf
	 * @date 2015年5月29日
	 * @param loanId
	 * @param checkType
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/checked/toedit")
	public String loanDetail(Long loanId,String checkType,ModelMap modelMap){
		List<LoanCheckedDo> loanCheckedDoList = managerLoanService.getLoanCheckedByLoanId(loanId,checkType);
		LoanCheckedDo loanCheckedDo = null;
		if(loanCheckedDoList != null && loanCheckedDoList.size()>0){
			loanCheckedDo = loanCheckedDoList.get(0);
		}
		if(loanCheckedDo == null){
			loanCheckedDo = new LoanCheckedDo();
			loanCheckedDo.setLoanId(loanId);
			loanCheckedDo.setCheckType(checkType);
			loanCheckedDo.setCheckResult("0");
		}
//		else{
//			if(loanCheckedDo.getCheckType().equals("1")){
//				List<LoanCheckedDo> loanCheckedTwoList = managerLoanService.getLoanCheckedByLoanId(loanId,"2");
//				LoanCheckedDo loanCheckedTwo = null;
//				if(loanCheckedTwoList != null && loanCheckedTwoList.size()>0){
//					loanCheckedTwo = loanCheckedTwoList.get(0);
//				}
//				if(loanCheckedTwo != null){
//					modelMap.addAttribute("loanCheckTwo","1");
//				}
//			}
//		}
		modelMap.addAttribute("loanCheckedDo", loanCheckedDo);
		if(StringUtils.isNotBlank(checkType) && checkType.equals("1")){
			return "loan/editOneLoanChecked";
		}else{
//			List<LoanCheckedDo> loanCheckedOneList = managerLoanService.getLoanCheckedByLoanId(loanId,"1");
//			LoanCheckedDo loanCheckedOne = null;
//			if(loanCheckedOneList != null && loanCheckedOneList.size()>0){
//				loanCheckedOne = loanCheckedOneList.get(0);
//			}
//			if(loanCheckedOne == null){
//				modelMap.addAttribute("loanCheckOne","1");
//			}else{
//				if(loanCheckedOne.getCheckResult().equals("0")){
//					modelMap.addAttribute("loanCheckOne","1");
//				}
//			}
//			modelMap.addAttribute("loanCheckedOne", loanCheckedOne);
			return "loan/editTwoLoanChecked";
		}
		
	}
	
	/**
	 * 信审保存
	 * @auther liminglmf
	 * @date 2015年5月29日
	 * @param loanDo
	 * @param response
	 */
	@RequestMapping("/checked/save")
	@ResponseBody
	public void saveLoanChecked(LoanCheckedDo loanCheckedDo,HttpServletResponse response){
		int i = 0;
		//--ljc modify 20150703 --
		//--去掉修改数据，添加流程处理步骤--
		try{
			LoanDo loanDo=managerLoanService.getLoanByLoanId(loanCheckedDo.getLoanId());
			boolean displayStep1=false;
			boolean displayStep2=false;
			switch (loanDo.getLoanStatus()) {
				case PENDING:
				case DRAFT:
					displayStep1=true;
					break;
				case PROCESSING:
					displayStep2=true;
					break;
				default:
					displayStep1=false;
					displayStep2=false;
					break;
				}
			if("1".equals(loanCheckedDo.getCheckType()))
			{
				if(!hasRoleCheckStep(Constants.ROLE_LOANCHECKSTEP1) || !displayStep1)
				{
					throw new BusinessException("没有一审审核权限");
				}
			}else{
				if(!hasRoleCheckStep(Constants.ROLE_LOANCHECKSTEP2) || !displayStep2)
				{
					throw new BusinessException("没有二审审核权限");
				}
			}
			if(!"1".equals(loanCheckedDo.getCheckResult()))
			{
				if(StringUtils.isBlank(loanCheckedDo.getOpinion()))
				{
					throw new BusinessException("审核意见内容不能为空", "201");
				}
			}
			if(loanCheckedDo.getId() != null && !"".equals(loanCheckedDo.getId())){
				 i = managerLoanService.updateLoanChecked(loanCheckedDo);
			}else{
				loanCheckedDo.setCheckUser(getUserInfos().getUsername());
				loanCheckedDo.setCheckDate(new Date());
				i = managerLoanService.createLoanChecked(loanCheckedDo);
			}
		}catch(BusinessException e)
		{
			i=0;
		}catch(Exception e)
		{
			i=0;
			logger.error("订单审核异常", e);
		}
		if(i <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
		}else{
			outPrint(response, JSONSerializer.toJSON(map_success));
		}
	}
	
	 
	
	private JsonConfig registerJsonBeanBankCard() {
    	JsonConfig jsonconfig = new JsonConfig();
    	jsonconfig.registerJsonBeanProcessor(LoanUserBankDo.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
            	LoanUserBankDo loanUserBankDo = (LoanUserBankDo)bean;
                JSONObject jsonobj = new JSONObject();
                	jsonobj.element("userName", StringUtils.defaultString(loanUserBankDo.getUserName()))
                	.element("cardNo", StringUtils.defaultString(loanUserBankDo.getCardNo()))
                	.element("bankName", StringUtils.defaultString(loanUserBankDo.getBankName()))
                	.element("cardStatus", StringUtils.defaultString(loanUserBankDo.getCardStatus()))
                	.element("isDefault", StringUtils.defaultString(loanUserBankDo.getIsDefault()))
                	.element("isExpress", StringUtils.defaultString(loanUserBankDo.getIsExpress()));
            	return jsonobj;
            }
    	});    
    	return jsonconfig;
    }
    
    @RequestMapping("/toupdate")
	public String loanUpdate(Long loanId,ModelMap modelMap){
		LoanDo loanDo = managerLoanService.getLoanByLoanId(loanId);
		modelMap.addAttribute("loanDo",loanDo);
		return "loan/editLoan";
	}
    
    
    @RequestMapping("/update")
	@ResponseBody
	public void updateLoan(LoanDo loanDo,HttpServletResponse response){
		int i = 0;
		loanDo.setUpdateBy(Long.valueOf(getUserId()));
		if(loanDo.getLoanId() !=null && !"".equals(loanDo.getLoanId())){
			 i = managerLoanService.updateLoan(loanDo);
		}else{
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
		if(i <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
		}
        outPrint(response, JSONSerializer.toJSON(map_success));
	}
    
	
	
	@RequestMapping("/audited")
	@ResponseBody
	public void audited(Integer audType,String ids,HttpServletResponse response,HttpServletRequest request){
		if(StringUtils.isNotBlank(ids) && audType != null){
			boolean res = false ;
			if(audType.intValue() == 1){
				res = updateLoanStatus(ids,"SUBJECTED",request);  //通过
			}else if(audType.intValue() == 2){
				res = updateLoanStatus(ids,"NOPASS",request);  //拒绝
			} else if(audType.intValue() == 3){
				res = updateLoanStatus(ids,"INVALID",request);  //作废
			} 
			if(res){
				outPrint(response, JSONSerializer.toJSON(map_success));
			}else{
				outPrint(response, JSONSerializer.toJSON(map_failure));
			}
		}else{
			 outPrint(response, JSONSerializer.toJSON(map_failure));
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	private boolean updateLoanStatus(String cbLoanId,String loanStatus,HttpServletRequest request){
		String []cbLoanIdArr = cbLoanId.split(",");
		List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
		
		UserInfos userDetails = (UserInfos) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(cbLoanIdArr != null && cbLoanIdArr.length>0){
			for(int i=0;i<cbLoanIdArr.length;i++){
				Map<String,Object> param = new HashMap<String,Object>();
				Long loanId = Long.valueOf(cbLoanIdArr[i]);
				if("SUBJECTED".equals(loanStatus)){ //审核通过 就上标
					LoanLogDo log = new LoanLogDo();
					log.setLogType(1);
					log.setLoanId(loanId);
					String res = superScript(loanId);
					if(StringUtils.isNotBlank(res)){
						ObjectMapper mapper = new ObjectMapper();
						try {
							Map resMap = mapper.readValue(res, Map.class);
							if("true".equals(resMap.get("success").toString())){
									param.put("loanStatus", loanStatus);
									param.put("loanId", cbLoanIdArr[i]);
									param.put("auditUserId", userDetails.getUserId());
									param.put("auditUser", userDetails.getUsername());
									paramList.add(param);
									log.setLogMsg("订单上标成功....");
									log.setLogStatus(0);
							}else{
								log.setLogMsg("订单上标失败,Info["+resMap.get("message")+"]");
								log.setLogStatus(1);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						log.setLogMsg("订单上标失败....");
						log.setLogStatus(1);
					}
					loanLogService.addLoanLog(log);
				}else{
					param.put("loanStatus", loanStatus);
					param.put("loanId", cbLoanIdArr[i]);
					param.put("auditUserId", userDetails.getUserId());
					param.put("auditUser", userDetails.getUsername());
					paramList.add(param);
				}
			}
		}
		boolean res = managerLoanService.managerUpdateLoanStatus(paramList);
		return res;
	}
	

	/**
	 * 上标
	 * @return
	 */
	private String superScript(Long loanId){
		LoanPersonDo loanPerson = managerLoanService.getLoanInfoForSbByLoanId(loanId);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		list.add(map);

		map.put("realName",getStr(loanPerson.getRealName())); // 客户名称 
		map.put("sex",getStr(loanPerson.getSexStr())); // 性别 
		map.put("age",loanPerson.getAge()); // 年龄 
		map.put("marriaged", getStr(loanPerson.getMarriaged())); // 婚姻状况 
		
		map.put("residenceType",""); // 居住状况 
		map.put("education",getStr(loanPerson.getEducation())); // 学历 
		map.put("creditNum", 0); // 信用卡总张数 
		map.put("creditAmt", 0.0); // 信用卡总额度 
		map.put("idNo",getStr(loanPerson.getIdNo())); // 身份证号码 

		map.put("carQty",0); // 车辆总数量 
		//
		JobDo job = loanPerson.getJobDo();
		if(null != job){
			map.put("companyName",getStr(job.getCompanyName())); // 公司名称 
			map.put("companyAddress",getStr(job.getCompanyAddr())); // 公司地址 
			map.put("position",getStr(job.getPosition())); // 职位级别 
			map.put("workYear",String.valueOf(loanPerson.getJobDo().getJobYear())); // 现公司工作年限
			map.put("income",loanPerson.getJobDo().getJobIncome()); // 月收入
		}else{
			map.put("companyName",""); // 公司名称 
			map.put("companyAddress",""); // 公司地址 
			map.put("position",""); // 职位级别 
			map.put("workYear","0"); // 现公司工作年限
			map.put("income",0.0); // 月收入
		}
		map.put("companyType",""); // 企业性质 
		map.put("industry",""); // 公司行业 
		map.put("expense",0.0); // 支出合计 
		map.put("loanAmt",loanPerson.getLoanDo().getApplyAmount()); // 借款金额 

		map.put("loanPeriod",loanPerson.getLoanDo().getLoanPeriod()); // 申请期限 
		map.put("productType",loanPerson.getLoanDo().getProductCode()); // 产品类型 
		map.put("repayType",String.valueOf(loanPerson.getLoanDo().getSchemeId())); // 还款方式 
		map.put("loanAnnualRate",loanPerson.getLoanDo().getAnnualRate()) ; // 年利率 
		map.put("annualRate",0.01); // 投资年利率 
		map.put("tenderDay",5); // 筹标期限（天） 
		String loanUsage = loanPerson.getLoanDo().getLoanUsage();
		if(StringUtils.isBlank(loanUsage)){
			//1-信用贷款、2-抵押贷款、3-担保贷款
			int loanType = loanPerson.getLoanDo().getLoanType();
			loanUsage = loanType == 1 ? "信用贷款" : loanType == 2 ? "抵押贷款" : "担保贷款";
		}
		map.put("loanUsage",loanUsage) ; // 贷款资金用途 
		map.put("consultant","互联网金融部"); // 借款咨询方 
		map.put("consultantBranch",""); // 咨询方分行 
		map.put("borrowGroup","0"); // 所属专区 
		map.put("businessNo",getStr(loanPerson.getLoanDo().getOrderCode())); // 业务编号
		
		String paramsStr = "";
		try {
			paramsStr = new ObjectMapper().writeValueAsString(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("===================================================");
		logger.debug("paramsStr:"+paramsStr);
		logger.debug("===================================================");
		String key = "HHN&XD#$%CD%des$";
		String signStr = Md5Utils.MD5(key+paramsStr+key, "UTF-8");
		Map<String,String> data = new HashMap<String,String>();
		data.put("params", paramsStr);
		data.put("sign", signStr);
		try {
			return HttpClientUtils.sbPost(loanSbUrl,data);
		} catch (Exception e) {
			logger.error("上标错误, 上标参数：data:"+data);
			e.printStackTrace();
		}
		return null;
	}
	
	public String getStr(Object obj){
		if(obj == null){
			return "";
		}
		return obj.toString();
	}
	
/*################################订单同步放款数据######################################################*/
	@RequestMapping("/syn/tocall")
	public String syspage(ModelMap modelMap){
		return "/loan/synLoanData";
	}
	
	@RequestMapping("/syn/callback")
	@ResponseBody
	public void synCallback(String selectDate,HttpServletResponse response,HttpServletRequest request) throws ParseException{
		//String selectDate = getString("selectDate");//开始日期
		if(StringUtils.isBlank(selectDate)){
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}else{
			SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd");
			Date date = toDate.parse(selectDate);
			selectDate = new SimpleDateFormat("yyyy/MM/dd").format(date); 
			loanContractService.fankuanProcess(selectDate);
			outPrint(response, JSONSerializer.toJSON(map_success));
		}
		
	}
	
			
	
	/**
	 * 导出Excel
	 * @auther liminglmf 迁移zhulin功能
	 * @date 2015年5月12日 
	 * @param searchStr
	 * @param productCode
	 * @param loanStatus
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/export/excel")
	public void exportExcel( String searchStr,String productCode,String loanType,String loanStatus,String startDate,String endDate,String processNextStep,HttpServletResponse response ,HttpServletRequest request)throws IOException {
		logger.info("---Start:addLoanDetail;searchStr="+searchStr+"; productCode="+productCode+"; loanStatus="+loanStatus+"; startDate="+startDate+"; endDate="+endDate+";");
		String excelHead = "标的导出";
		String saleDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileName = URLEncoder.encode(excelHead + saleDate + ".xls", "utf-8");
		List<String[]> excelheaderList = new ArrayList<String[]>();
		String[] excelheader1={""};
		String[] excelheader={"客户名称","性别"," 年龄","婚姻状况","居住状况","学历","信用卡总张数","信用卡总额度","申请者身份证号码","车辆总数量","公司名称","公司地址","职位级别","企业性质","现公司工作年限","公司行业","月收入","支出合计","借款金额","申请期限"," 产品类型","*还款方式","*年利率(%)","*筹标期限(天)","贷款资金用途","借款咨询方","咨询方分行","所属专区","业务编号"};
		excelheaderList.add(0,excelheader1);
		excelheaderList.add(1,excelheader);
		String[] excelData={"realName","sex","age","hyzk","jzzk","xl","xykzzs","xykzed","idNo","clzsl","gsmc","gsdz","zwjb","qyxz","xgsgznx","gshy","jobIncome","zchj","applyAmount","loanPeriod","cplx","hkfs","annualRate","cbqx","hkzjyt","jkzxf","zxffh","sszq","orderCode"};
		
		Map<String,Object> param = new HashMap<String,Object>();
		searchStr =URLDecoder.decode(searchStr, "utf-8");//搜索条件
		if (StringUtils.isNotBlank(searchStr)) {
			boolean result = searchStr.matches("[0-9]+");
			if (result) {
				if (searchStr.length() > 3) param.put("mobile", searchStr);
			} else {
				param.put("realName", searchStr);
			}
		}

		if (StringUtils.isNotBlank(productCode)) param.put("productCode", productCode);
		 
		if (StringUtils.isNotBlank(loanType)) param.put("loanType", loanType);
		
		if (StringUtils.isNotBlank(loanStatus))  param.put("loanStatus", loanStatus);

		if (StringUtils.isNotBlank(startDate))  param.put("startDate", startDate);

		if (StringUtils.isNotBlank(endDate))  param.put("endDate", endDate);
		
		if (StringUtils.isNotBlank(processNextStep))  param.put("processNextStep", processNextStep);

		
		List<Map<String, Object>> contractList = new ArrayList<Map<String,Object>>() ;
		contractList=managerLoanService.getLabelExportData(param);
		HSSFWorkbook wb = ExeclTools.execlExport(excelheaderList, excelData, excelHead, contractList);
		  try {	
		    response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	        wb.write(response.getOutputStream());
		} catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("下载失败");
			e.printStackTrace();
		} finally {
			response.flushBuffer();
			logger.info("---End:addLoanDetail;searchStr="+searchStr+"; productCode="+productCode+"; loanStatus="+loanStatus+"; startDate="+startDate+"; endDate="+endDate+";");
		}
	}
	
	/**
	 * 是否有一审审核权限。
	 * @return
	 */
	public boolean hasRoleCheckStep(String rolename)
	{
		boolean result=false;
		UserInfos user=this.getUserInfos();
		if(user!=null)
		{
			Collection<GrantedAuthority> grantedAuthorityList=user.getAuthorities();
			for (GrantedAuthority authority : grantedAuthorityList) {  
				if(rolename.equals(authority.getAuthority()))
				{
					result= true;
					break;
				}
			}
		}
		log.info("判断用户是否有一审权限："+result);
		return result;
	}
	
	public boolean canEdit( LoanDo.LoanStatus status)
	{
		boolean result=false;
		UserInfos user=this.getUserInfos();
		if(user!=null)
		{
			Collection<GrantedAuthority> grantedAuthorityList=user.getAuthorities();
			for (GrantedAuthority authority : grantedAuthorityList) {  
				if(Constants.ROLE_LOANDKADMIN_EDIT.equals(authority.getAuthority()))
				{
					result= true;
					break;
				}
			}
		}
		
		boolean display =false;
		
		switch (status) {
		case PENDING:
			display=true;
		case DRAFT:
			display=true;
			break;
		case INVALID:
			display=true;
			break;
		case NOPASS:
			display=true;
			break;
		default:
			display=false;
			break;
		}
		
		log.info("判断用户是否有edit权限："+result);
		return result&&display;
	}
	
	public boolean hasRoleAdmin()
	{
		boolean result=false;
		UserInfos user=this.getUserInfos();
		if(user!=null)
		{
			Collection<GrantedAuthority> grantedAuthorityList=user.getAuthorities();
			for (GrantedAuthority authority : grantedAuthorityList) {  
				if(Constants.ROLE_LOANDKADMIN.equals(authority.getAuthority()))
				{
					result= true;
					break;
				}
			}
		}
		log.info("判断用户是否有admin权限："+result);
		return result;
	}
	
	
}
