package com.hehenian.app.view.loan.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hehenian.app.common.PageVO;
import com.hehenian.app.common.exception.SessionException;
import com.hehenian.app.view.loan.common.AppConstants;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.colorlife.IColorHttpService;
import com.hehenian.biz.common.filesaving.IFileServerService;
import com.hehenian.biz.common.loan.ILoanApplyService;
import com.hehenian.biz.common.loan.ILoanPersonService;
import com.hehenian.biz.common.loan.ILoanRepaymentService;
import com.hehenian.biz.common.loan.dataobject.AuditLogDo;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.CertificateDo.CertificateType;
import com.hehenian.biz.common.loan.dataobject.CertificateDo.FileType;
import com.hehenian.biz.common.loan.dataobject.CertificateDoFrom;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.JobDo.JobType;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanDo.ProcessStep;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo.PurchaseWay;
import com.hehenian.biz.common.util.IdCardUtils;
import com.hehenian.web.common.constant.WebConstants;

/**
 * @Description 彩管家  贷款业务受理  
 * @author huangzl QQ: 272950754
 * @date 2015年6月1日 上午10:05:08
 * @Project hehenian-lend-app
 * @Package com.hehenian.app.view.loan.controllor 
 * @File LoanYwslController.java
*/
@Controller
@RequestMapping("/app/mhk")
public class LoanYwslController extends AppBaseController{

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
	private ILoanPersonService  loanPersonService;
	@Autowired
	private IFileServerService  fileServerService;
	@Autowired
	private ILoanRepaymentService loanRepaymentService;
	@Autowired
	private ILoanApplyService loanApplyService;
	
	@Autowired
	private IColorHttpService colorHttpService;
	
	@Value("#{appconfig['activity.colorServiceURL']}")
	private String baseUrl;
	@Value("#{appconfig['color.kefu.resouceURL']}")
    private String businessURL;
	@Value("#{appconfig['activity.colorlife.key']}")
    private String key;
	@Value("#{appconfig['activity.colorlife.secret']}")
    private String colorResourcescret;
    
	
	/**
	 * 保存代理人审核信息
	 * @return
	 */
	@RequestMapping(value="")
	public String saveProxyCheck(){
		LoanProxyCheckDo newLoanProxyCheckDo = new LoanProxyCheckDo();
		loanApplyService.addLoanProxyCheck(newLoanProxyCheckDo);
		return "";
	}
	
	
	/**
	 * 业务受理 主界面   跳转到  
	 * @param param
	 * @param sign
	 * @param map
	 * @return
	 */
    @RequestMapping("/index")
    public String index(@RequestParam(required=true) String param,@RequestParam(required=true) String sign,ModelMap map, HttpSession session) {
    	if (StringUtils.isBlank(param) || StringUtils.isBlank(sign)) {
            map.put("message","参数有误!");
            return "/common/error";
        }
        
        try {
        	//汉子在encode之后会变成+ 符号    ，带有+符号的  字符串   decode之后   会有空格
            String jsonString = new String(Base64.decodeBase64(param.replaceAll(" ", "+").getBytes("utf-8")), "utf-8");
            String secret = "HHN&XD#$%CD%des$";
            if (!DigestUtils.md5Hex(secret + jsonString + secret).equalsIgnoreCase(sign)) {
            	map.put("message", "请求非法!");
            	return "/common/error";
            }
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> params = mapper.readValue(jsonString, new TypeReference<HashMap<String, String>>() {
            });
            logger.info("彩管家登录信息："+params);
            
            
            
            //去彩管家验证数据查询权限            
            Map<String,String> parameterMap = new HashMap<String,String>();
            parameterMap.put("employee_id", params.get("userId"));
            
            String result = colorHttpService.callColorResouce(baseUrl, businessURL, key, colorResourcescret, parameterMap);
			logger.info("彩获取客户绑定小区的资源接口返回:"+result);
			ObjectMapper mapper1 = new ObjectMapper();
			Map<String, Object> params2 = mapper1.readValue(result.toString(), new TypeReference<HashMap<String, Object>>() {});
			//回复格式： {"list":["701","585","689"]}
			List<String> cidList = (List<String>)params2.get("list");
			List<Map<String,Object>> temp =loanApplyService.getJBCmobileDo(params);
			if((temp!=null&&temp.size()>0) ||(cidList != null && cidList.size()>0)){ //登录成功
				session.setAttribute(AppConstants.COLOURLIFE_ADMIN_USER, params);
                session.setAttribute("platformType", "HT");
                List<Map<String,Object>> tempList;
                if(temp!=null&&temp.size()>0){
                	tempList=temp;
                }else{
                	tempList = new ArrayList<Map<String,Object>>();
                }
               if(cidList != null && cidList.size()>0){
					for(String cnameId : cidList){
						Map<String,Object> cidMap = new HashMap<String,Object>();
						cidMap.put("cid", cnameId);
						tempList.add(cidMap);
					}
               }
                // 看 this.getLoanCidList 如何解析 cid
                session.setAttribute("JBCmobile", tempList);
                
			}else{
            	 logger.error("登录异常，无权限访问！");
                 map.put("message",  "您无权限访问，请联系客服， 客服电话：400 830 3737");
                 return "/common/notify_message";
            }
        } catch (Exception e) {
        	logger.error("借款业务受理异常："+e.getMessage());
            map.put("message",  "程序异常!");
            return "/common/notify_message";
        }
        //检索主界面

        return "/app/mhk/orderList";
    }
    
    
	/**
	 * 业务受理 主界面   跳转到  
	 * @param param
	 * @param sign
	 * @param map
	 * @return
	 */
    @RequestMapping("/index1")
    public String index1(@RequestParam(required=true) String param,@RequestParam(required=true) String sign,ModelMap map, HttpSession session) {
    	if (StringUtils.isBlank(param) || StringUtils.isBlank(sign)) {
            map.put("message","参数有误!");
            return "/common/error";
        }
        
        try {
        	//汉子在encode之后会变成+ 符号    ，带有+符号的  字符串   decode之后   会有空格
            String jsonString = new String(Base64.decodeBase64(param.replaceAll(" ", "+").getBytes("utf-8")), "utf-8");
            String secret = "HHN&XD#$%CD%des$";
            if (!DigestUtils.md5Hex(secret + jsonString + secret).equalsIgnoreCase(sign)) {
            	map.put("message", "请求非法!");
            	return "/common/error";
            }
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> params = mapper.readValue(jsonString, new TypeReference<HashMap<String, String>>() {
            });
            logger.info("彩管家登录信息："+params);
            
            //去彩管家验证数据查询权限
//            String colorLifeAG_URL = "http://iceapi.colourlife.com:8081/v1/auth?";
//            Map<String,String> params1 = new HashMap<String,String>(10);
//			StringBuffer url = new StringBuffer();
//			url.append(colorLifeAG_URL).append("totle=").append(sign);//.append("&ts=").append(ts).append("&appID=").append(COLOR_APP_ID)
//			String result = HttpClientUtils.post(url.toString(), params1);
//			logger.info("彩管家验证数据:"+result);
//			ObjectMapper mapper1 = new ObjectMapper();
//			Map<Object, Object> params2 = mapper1.readValue(result.toString(), new TypeReference<HashMap<Object, Object>>() {});
//			int code =Integer.valueOf(params2.get("code").toString());
//			if(code==0){
//				
//			}else{
//				session.setAttribute(WebConstants.MESSAGE_KEY,"财管家数据异常");
//				return "common/notify_message";
//			}
            List<Map<String,Object>> temp =loanApplyService.getJBCmobileDo(params);
            if(temp!=null&&temp.size()>0){
            	 //权限信息放在session中  拦截器通过session获取权限信息
                session.setAttribute(AppConstants.COLOURLIFE_ADMIN_USER, params);
                session.setAttribute("platformType", "HT");
                session.setAttribute("JBCmobile", temp);
            }else{
            	 logger.error("登录异常，无权限访问！");
                 map.put("message",  "您无权限访问，请联系客服， 客服电话：400 830 3737");
                 return "/common/notify_message";
            }
        } catch (Exception e) {
        	logger.error("借款业务受理异常："+e.getMessage());
            map.put("message",  "程序异常!");
            return "/common/notify_message";
        }
        //检索主界面

        return "/app/mhk/orderList";
    }
    
    /**
     * 借款人受理信息查询列表
     * @return
     * @throws SessionException 
     */
    @RequestMapping("/getLoanPerson")
    public String getLoanPerson(String realName ,String loanStatus,ModelMap map) throws SessionException{
        Map<String, Object> searchItems = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(realName)){
        	searchItems.put("realName", realName);
        }
        if(StringUtils.isNotBlank(loanStatus)){
        	searchItems.put("loanStatus", loanStatus);
        }else{
        	List  loanStatusList = new ArrayList();
        	loanStatusList.add(LoanStatus.PENDING.name());
        	loanStatusList.add(LoanStatus.AUDITED.name());
        	loanStatusList.add(LoanStatus.PROCESSING.name());
        	loanStatusList.add(LoanStatus.TREATY.name());
        	loanStatusList.add(LoanStatus.NOPASS.name() );
        	loanStatusList.add(LoanStatus.SUBJECTED.name() );
        	searchItems.put("loanStatusList", loanStatusList);
        }
//        searchItems.put("cname",getCurentCname());
        searchItems.put("loanCidList",getLoanCidList());
        PageVO page = getPageVO(request);
        
        searchItems.put("beginCount",page.getBeginCount());
        searchItems.put("pageSize", page.getPageSize());
        searchItems.put("productCode", "D03");
        
        List<LoanPersonDo> list = loanPersonService.queryLoanPersonByApp(searchItems);
        map.put("loanStatus", loanStatus);
        map.put("realName", realName);
        map.put("list", list);
        
    	return "/app/mhk/orderList";
    }
    @RequestMapping("/getLoanPersonAjax")
    @ResponseBody
    public Map getLoanPersonAjax(String realName ,String loanStatus) throws SessionException{
    	Map m = new HashMap();
    	Map<String, Object> searchItems = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(realName)){
        	searchItems.put("realName", realName);
        }
        if(StringUtils.isNotBlank(loanStatus)){
        	searchItems.put("loanStatus", loanStatus);
        }else{
        	List  loanStatusList = new ArrayList();
        	loanStatusList.add(LoanStatus.PENDING.name());
        	loanStatusList.add(LoanStatus.AUDITED.name());
        	loanStatusList.add(LoanStatus.PROCESSING.name());
        	loanStatusList.add(LoanStatus.TREATY.name());
        	loanStatusList.add(LoanStatus.NOPASS.name() );
        	loanStatusList.add(LoanStatus.SUBJECTED.name() );

        	searchItems.put("loanStatusList", loanStatusList);
        }
//        searchItems.put("cname",getCurentCname());
        searchItems.put("loanCidList",getLoanCidList());
        PageVO page = getPageVO(request);
        
        searchItems.put("beginCount",page.getBeginCount());
        searchItems.put("pageSize", page.getPageSize());
        searchItems.put("productCode", "D03");  //只查询E贷款的订单
        
        List<LoanPersonDo> list = loanPersonService.queryLoanPersonByApp(searchItems);
        m.put("loanStatus", loanStatus);
        m.put("realName", realName);
        m.put("list", list);
    	return m;
    }

    /**
     * 获取借款人信息
     */
//    @RequestMapping("/loanInit")
    public String loanInit( @RequestParam Long loanId ,@RequestParam Integer type ,ModelMap map){
    	LoanPersonDo loanPersonDo= loanPersonService.getInitData(loanId);
    	if(loanPersonDo.getLoanId()==null){
    		loanPersonDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
    	} 
    	map.put("loanPersonDo", loanPersonDo);
    	if(type==1){
    		//个人信息1
    		if(StringUtils.isNotBlank(loanPersonDo.getFamilyPhone())&& loanPersonDo.getFamilyPhone().split("-").length>1){
            	String [] fps = loanPersonDo.getFamilyPhone().split("-");
            	map.put("familyPhone1", fps[0]);
                loanPersonDo.setFamilyPhone(fps[1]);
        	}
    		if(loanPersonDo.getAge()==null){
    			loanPersonDo.setAge(IdCardUtils.getAgeByIdCard(loanPersonDo.getIdNo()));
    		}
    		return "/app/mhk/person1";
    	}
    	if (type==3){
    		//初始化联系人信息
    		//放3个对象 
    		for(LoanRelationDo relation:loanPersonDo.getLoanRelationDoList()){
    			if(relation!=null && relation.getRelationType()==1){
    				//家庭联系人
    				map.put("relationFamily", relation);
    			}else if (relation!=null && relation.getRelationType()==2){
    				map.put("relationWork", relation);

    			}else if (relation!=null && relation.getRelationType()==3){
    				map.put("relationOther", relation);
    			}
    		}
    		return "/app/mhk/contact";
    	}
    	map.put("loanId",loanId);
		return "redirect:/app/mhk/initPersonInfo.do";
    }
   
    
    /***
     * 更新借款人 个人信息 （个人信息   ）  person1界面的保存
     * @return
     * @throws SessionException 
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/updateLoanPerson")
    public String updateLoanPerson(@ModelAttribute LoanPersonDo loanPersonDo,ModelMap map) throws SessionException{
    	String familyPhone = request.getParameter("familyPhone1");
    	if(StringUtils.isNotBlank(familyPhone)){
    		loanPersonDo.setFamilyPhone(familyPhone+"-"+loanPersonDo.getFamilyPhone());
    	}  	 
		loanPersonDo.setUpdateUser(getCurentUserName());
		
         IResult result = loanPersonService.updatePersonAndLoan(loanPersonDo);
         map.put("loanPersonDo", loanPersonDo);
         if(result.isSuccess()){
        	 //跳转到个人信息2 （工作信息）
        	 loanPersonDo= loanPersonService.getInitData(loanPersonDo.getLoanId());
        	 //
        	 JobDo jobDo = loanPersonDo.getJobDo();
        	 if(jobDo==null){
        		 jobDo= new JobDo();
        		 jobDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
        		 jobDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
        	 }else{
        	 //处理单位电话信息
        		 if(StringUtils.isNotBlank(jobDo.getCompanyPhone())){
        			 if(jobDo.getCompanyPhone().split("-").length>1){
        				 String []str = jobDo.getCompanyPhone().split("-");
        				 map.put("companyPhone1", str[0]);
        				 map.put("companyPhone2", str[1]);
        			 }
        		 }        	 
        		 if(StringUtils.isNotBlank(jobDo.getCompanyInTime())){
        			 String[] s = jobDo.getCompanyInTime().split("-");
        	        	map.put("companyInTime_year", s[0]);
        	        	map.put("companyInTime_month", s[1]);
        	        	map.put("companyInTime_day", s[2]);
        		 }
        	 }
        	 map.put("jobDo", jobDo);
        	 return "/app/mhk/person2";
         }else{
        	 map.put("familyPhone1", request.getParameter("familyPhone1"));
        	 if(StringUtils.isNotBlank(loanPersonDo.getFamilyPhone()) && loanPersonDo.getFamilyPhone().split("-").length>1){
        		 String [] fps = loanPersonDo.getFamilyPhone().split("-");
             	
                 loanPersonDo.setFamilyPhone(fps[1]);
        	 }
        	 //用途
        	 loanPersonDo.getLoanDo().setLoanUsage(loanPersonDo.getLoanUsage());
        	 map.put("message","保存失败");
        	 //错误信息
        	 return "/app/mhk/person1";
         }
         //受理界面 
    }
    /**
     * 更新借款人信息 （工作信息） person2
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/updatePersonJob")
    public String updatePersonJob(@ModelAttribute  JobDo jobDo,ModelMap map){
    	//单位电话
    	String companyPhone1 = request.getParameter("companyPhone1");
    	String companyPhone2 =  request.getParameter("companyPhone2");
    	jobDo.setCompanyPhone(companyPhone1+"-"+companyPhone2);
    	//如果是工薪阶级     重新获取税后营业额  营业时长
    	System.out.println(jobDo.getJobType().name()+"---"+JobType.SALARYMAN);
    	if(jobDo.getJobType().name().equals(JobType.SALARYMAN.name())){
    		//年月日  设置年月日
        	String companyInTime_year = request.getParameter("companyInTime_year");
        	String companyInTime_month = request.getParameter("companyInTime_month");
        	String companyInTime_day =request.getParameter("companyInTime_day");
        	jobDo.setCompanyInTime(companyInTime_year+"-"+companyInTime_month+"-"+companyInTime_day);
        	map.put("companyInTime_year",companyInTime_year );
    		map.put("companyInTime_month",companyInTime_month );
    		map.put("companyInTime_day",companyInTime_day );
    	}else{
    		String income = request.getParameter("jobIncome1");
    		jobDo.setJobIncome(Double.valueOf(StringUtils.isNotBlank(income)?income:"0"));
    		jobDo.setJobYear(Integer.valueOf(request.getParameter("jobYear1") ));
    	}
    	IResult result =loanPersonService.saveJobInfo(jobDo);
    	if(result.isSuccess()){
    		//返回主模块    		
    		map.put("loanId",jobDo.getLoanId());
    		return "redirect:/app/mhk/initPersonInfo.do";
    	}else{
    		//保存失败  把界面信息还原
    		map.put("jobDo", jobDo);
    		map.put("message", "保存失败！");
    		return "/app/mhk/person2";
    	}
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping("/updatePersonContact")
    public String updatePersonContact(LoanPersonDo loanPersonDo,ModelMap map){
       IResult result =	loanPersonService.updateRelations(loanPersonDo);
    	if(result.isSuccess()){
    		//返回主模块    		
    		map.put("loanId",loanPersonDo.getLoanId());
    		return "redirect:/app/mhk/initPersonInfo.do";
    	}else{
    		//保存失败  把界面信息还原
    		for(LoanRelationDo relation:loanPersonDo.getLoanRelationDoList()){
    			if(relation!=null && relation.getRelationType()==1){
    				//家庭联系人
    				map.put("relationFamily", relation);
    			}else if (relation!=null && relation.getRelationType()==2){
    				map.put("relationWork", relation);

    			}else if (relation!=null && relation.getRelationType()==3){
    				map.put("relationOther", relation);
    			}
    		}
    		map.put("loanPersonDo", loanPersonDo);
    		map.put("message", "保存失败！请检查数据！");
    		return "/app/mhk/contact";
    	}
    }
    
    /**
     * 初始个人信息页面
     * @param loanId
     * @param model
     * @author huangzl
     * @return
     */
    @RequestMapping(value="/initPersonInfo")
    public String proxyCheckInfo(@RequestParam Long loanId,Model model){
    	
    	LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
    	if (loanPersonDo.getLoanId() == null) {
			loanPersonDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
		}
    	LoanDo loanDo =loanPersonDo.getLoanDo();
    	loanDo.setLoanPersonDo(loanPersonDo);
    	double applyAmount = getApplyAmountTemp(loanDo);
    	String typeTemp="";
		if (loanPersonDo.getHasHouse().equals("T")) {
			typeTemp="HOUSE";
		}else{
			typeTemp="ASSETS";
		}
    	//读取状态表
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("loanId", loanId);
		parameterMap.put("tableCode","CertificateDo");
    	List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
    	List<CertificateDo> cdList = loanPersonService.initCertificateData(loanId);
    	int personBooleanInt=0;
    	int propertyBooleanInt=0;
    	int entrust_protocol=0;
    	int credit_auditk_protocol=0;
    	for(CertificateDo temp:cdList){
    		if(temp.getCertificateType().toString().equals("IDCARDZ")||temp.getCertificateType().toString().equals("IDCARDZS")||temp.getCertificateType().toString().equals("IDCARDF")||temp.getCertificateType().toString().equals("JOB")){
    			personBooleanInt++;
    		}
    		if(//判断驾驶证
    			(loanPersonDo.getPropertyDo()!=null&&loanPersonDo.getPropertyDo().getCarDy()!=null&&loanPersonDo.getPropertyDo().getCarDy()==1&&loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("F")&&temp.getCertificateType().toString().equals("DRIVERCARD"))
    			//判断房产或资产证明
    			||temp.getCertificateType().toString().equals(typeTemp)||
    			//判断银行流水
    			(((loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("F")&&loanDo.getApplyAmount()>applyAmount) ||
    			(loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("T")&&loanDo.getApplyAmount()>200000.0))
    			 && temp.getCertificateType().toString().equals("INCOME"))){
    			propertyBooleanInt++;
    		}
    		if(temp.getCertificateType().toString().equals("ENTRUST_PROTOCOL")){
    			entrust_protocol++;
    		}
    		if(temp.getCertificateType().toString().equals("CREDIT_AUDITK_PROTOCOL")){
    			credit_auditk_protocol++;
    		}
    	}
    	if(!(entrust_protocol>0&&credit_auditk_protocol>0)){
    		model.addAttribute("message", "请先联系客户签订《信用审核服务协议》和《委托划款协议》并上传，才可以受理！");
    		return "/common/notify_message";
    	}
    	
    	//判断状体
    	Boolean personBoolean=true;
    	Boolean propertyBoolean=true;
    	Boolean lrdBoolean=true;
    	int statusInt=0;
    	//先判断是否为空
    	if(!(null!=tempMap&&tempMap.size()>0)){
    		personBoolean=false;
    		propertyBoolean=false;
    	}else{
	    	//判断个人信息的是否都通过审核
	    	for(LoanProxyCheckDo temp:tempMap){
	    		if(temp.getNameCode().toString().equals("IDCARDZ")||temp.getNameCode().toString().equals("IDCARDZS")||temp.getNameCode().toString().equals("IDCARDF")||temp.getNameCode().toString().equals("JOB")){
	    			if(null!=temp.getStatus()&&!(temp.getStatus()>=0)){
	    				personBoolean=false;
	    				break;
	    			}
	    			statusInt++;
	    		}
	    	}
	    	if(personBoolean&&statusInt==0&&((personBooleanInt-statusInt)!=0)){
	    		personBoolean=false;
	    	}
	    	//判断资产信息是否都通过审核
	    	statusInt=0;
	    	for(LoanProxyCheckDo temp:tempMap){
	    		if(temp.getNameCode().toString().equals("DRIVERCARD")||temp.getNameCode().toString().equals(typeTemp)||temp.getNameCode().toString().equals("INCOME")){
    				if(null!=temp.getStatus()&&!(temp.getStatus()>=0)){
	    				propertyBoolean=false;
	    				break;
	    			}
	    			statusInt++;
	    		}
	    	}
	    	if(propertyBoolean&&statusInt==0&&((propertyBooleanInt-statusInt)!=0)){
	    		propertyBoolean=false;
	    	}
    	}
    	//个人基本信息填写完整
    	if(loanDo.getApplyAmount()<=applyAmount && loanPersonDo.getHasHouse().equals("T")){
    		if(personBoolean.booleanValue()&&loanPersonDo != null  && StringUtils.isNotBlank(loanPersonDo.getIdNo())
    		   && StringUtils.isNotBlank(loanPersonDo.getRealName()) && StringUtils.isNotBlank(loanPersonDo.getMobile())){
    		    	model.addAttribute("pnal", 0); //填写完整
    		   }else{
    		    	model.addAttribute("pnal", 1);
    		   }
    	}else{
	    	if(personBoolean.booleanValue()&&loanPersonDo != null  && loanPersonDo.getEducation() != null
	    	   && loanPersonDo.getMarriaged() != null && StringUtils.isNotBlank(loanPersonDo.getEmail())
	    	   && StringUtils.isNotBlank(loanPersonDo.getRealName()) && StringUtils.isNotBlank(loanPersonDo.getIdNo())
	    	   && StringUtils.isNotBlank(loanPersonDo.getMobile())){
	    		JobDo jobDo = loanPersonDo.getJobDo();
	    		if(jobDo != null && jobDo.getJobType() != null  && jobDo.getJobYear() != null ){
	    			model.addAttribute("pnal", 0); //填写完整
	    		}else{
	    			model.addAttribute("pnal", 1);
	    		}
	    	}else{
	    		model.addAttribute("pnal", 1);
	    	}
    	}
    	// 资产信息填写完整
    	PropertyDo propertyDo = loanPersonDo.getPropertyDo();
    	if(loanDo.getApplyAmount()<=applyAmount && loanPersonDo.getHasHouse().equals("T")){
    		if(propertyBoolean.booleanValue()){
	    		model.addAttribute("ppty", 0); //填写完整
	    	}else{
	    		model.addAttribute("ppty", 1); 
	    	}
    	}else if(loanPersonDo.getHasHouse().equals("F")){
    		if(propertyBoolean&&propertyDo != null && propertyDo.getCarDy() != null ){
    			  model.addAttribute("ppty", 0); //填写完整
	    	}else{
	    		model.addAttribute("ppty", 1); 
	    	}
    	}else{
	    	if(propertyBoolean.booleanValue()&&propertyDo != null && StringUtils.isNotBlank(propertyDo.getHouseAddress())
 		    	   && propertyDo.getCoveredArea() != null && propertyDo.getCoveredArea().doubleValue()>0d
 		    	   && propertyDo.getHouseDy() != null && propertyDo.getPurchaseWay() != null){
	    		model.addAttribute("ppty", 0); //填写完整
	    	}else{
	    		model.addAttribute("ppty", 1); 
	    	}
    	}
    	if(loanDo.getApplyAmount()>applyAmount || loanPersonDo.getHasHouse().equals("F")){
    		
    		parameterMap.put("tableCode","LoanRelationDo");
        	List<LoanProxyCheckDo> tempMap1 = loanApplyService.selectLoanProxyCheck(parameterMap);
        	//先判断是否为空
        	if(!(null!=tempMap1&&tempMap1.size()>0)){
        		lrdBoolean=false;
        	}else{
	    		statusInt=0;
	        	for(LoanProxyCheckDo temp:tempMap1){
	    			if(null!=temp.getStatus()&&!(temp.getStatus()>=0)){
	    				lrdBoolean=false;
	    				break;
	    			}
	    			statusInt++;
	        	}
	        	if(lrdBoolean&&statusInt==0){
	        		lrdBoolean=false;
	        	}
        	}
	    	//联系人信息填写完整
	    	List<LoanRelationDo> lrdList = loanPersonDo.getLoanRelationDoList();
	        if(lrdBoolean.booleanValue()&&lrdList != null && lrdList.size()>0){
	        	LoanRelationDo lrd = null;
	        	for(int i=0;i<lrdList.size();i++){
	        		if(null !=lrdList.get(i).getRelationType()&&lrdList.get(i).getRelationType() == 1){
	        			lrd = lrdList.get(i);
	        		}
	        	}
	        	if(lrd != null && StringUtils.isNotBlank(lrd.getRalationName()) 
	        			&& StringUtils.isNotBlank(lrd.getMobile())){
	        		model.addAttribute("pred", 0); //填写完整
	        	}else{
	        		model.addAttribute("pred", 1); 
	        	}
	        }else{
	        	model.addAttribute("pred", 1); 
	        }    
	        model.addAttribute("ZSTYPE", "3");
    	}
    	model.addAttribute("loanId", loanId);
    	model.addAttribute("loanPersonId",loanPersonDo.getLoanPersonId());    
    	return "/app/mhk/proxyCheckInfo";
    }
    
    /**
     * 获取借款人信息
     * huangzl   2015年6月2日 19:38:59
     */
    @RequestMapping("/loanInit")
    public String proxyCheckInit( @RequestParam Long loanId ,@RequestParam Integer pageId ,ModelMap map){
    	if(!(null!=pageId && pageId.intValue()>0)) return "redirect:/app/mhk/initPersonInfo.do?loanId="+loanId;
    	LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
    	if (loanPersonDo.getLoanId() == null) {
			loanPersonDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
		}
    	LoanDo loanDo =loanPersonDo.getLoanDo();
    	loanDo.setLoanPersonDo(loanPersonDo);
    	double applyAmount =  getApplyAmountTemp(loanDo);
    	String typeTemp="";
		if (loanPersonDo.getHasHouse().equals("T")) {
			typeTemp="HOUSE";
		}else{
			typeTemp="ASSETS";
		}
//    	map.addAttribute(WebConstants.MESSAGE_KEY,  new SimpleDateFormat ("yyyy-MM-dd").format(new Date()));
    	CertificateDo certificateDo = new CertificateDo();
		certificateDo.setLoanId(loanId);
		List<CertificateDo> cdList = loanPersonService.initCertificateData(loanId);
		List<CertificateDo> cdListOut = new ArrayList<CertificateDo>();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("loanId", loanId);
		parameterMap.put("tableCode","CertificateDo");
		//页面一1
    	if(pageId.intValue()==1){
    		//加载图片与对应状态
    		List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
    		if(cdList != null && cdList.size()>0){
    			for(CertificateDo temp :cdList){
    				if(temp.getCertificateType().toString().equals("IDCARDZ")||temp.getCertificateType().toString().equals("IDCARDZS")||temp.getCertificateType().toString().equals("IDCARDF")){
    				if(null !=tempMap && tempMap.size()>0){
	    				for(LoanProxyCheckDo proxyCheck :tempMap){
	    					if(proxyCheck.getNameCode().toString().equals("IDCARDZ")||proxyCheck.getNameCode().toString().equals("IDCARDZS")||proxyCheck.getNameCode().toString().equals("IDCARDF")){
		    					if(temp.getCertificateId().longValue()==proxyCheck.getRecordId().longValue()){
		    						temp.setStatusId(proxyCheck.getId());
		    						temp.setStatusInt(proxyCheck.getStatus());
		    					}
	    					}
	    				}
	    			}
    				cdListOut.add(temp);
    				}
    			}
    		}
    		if(loanPersonDo.getHasHouse().equals("T") && loanDo.getApplyAmount().doubleValue()<=applyAmount){
    			map.addAttribute("pageId","3");
    		}else{
    			map.addAttribute("pageId","2");
    		}
    	//页面2
    	}else if (pageId.intValue()==2){
    		//加载图片与对应状态
    		List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
    		JobDo jobDo = loanPersonDo.getJobDo();
	    	if(jobDo==null){
		    	jobDo= new JobDo();
		    	jobDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
		    	jobDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
	    	}
	    	map.put("jobDo", jobDo);
	    	if(cdList != null && cdList.size()>0){
    			for(CertificateDo temp :cdList){
    				if(temp.getCertificateType().toString().equals("JOB")){
    				if(null !=tempMap && tempMap.size()>0){
	    				for(LoanProxyCheckDo proxyCheck :tempMap){
	    					if(proxyCheck.getNameCode().toString().equals("JOB")&&temp.getCertificateId().longValue()==proxyCheck.getRecordId().longValue()){
	    						temp.setStatusId(proxyCheck.getId());
	    						temp.setStatusInt(proxyCheck.getStatus());
	    					}
	    				}
	    			}
    				cdListOut.add(temp);
    				}
    			}
    		}
	    	map.addAttribute("pageId","3");
	    //页面3
    	}else if (pageId.intValue()==3){
    		//加载对应数据
    		PropertyDo propertyDo = loanPersonService.initPropertyData(loanId);
    		if(propertyDo == null){
    			propertyDo = new PropertyDo();
    			propertyDo.setLoanId(loanId);
    			propertyDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
    		}
    		map.addAttribute("propertyDo",propertyDo);
    		//加载图片与对应状态
    		List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
    		if(cdList != null && cdList.size()>0){
//    			if(loanPersonDo.getHasHouse().equals("T")){
	    			for(CertificateDo temp :cdList){
	    				if(temp.getCertificateType().toString().equals(typeTemp)){
	    					if(null !=tempMap && tempMap.size()>0){
	    	    				for(LoanProxyCheckDo proxyCheck :tempMap){
	    	    					if(proxyCheck.getNameCode().toString().equals(typeTemp)&&temp.getCertificateId().longValue()==proxyCheck.getRecordId().longValue()){
	    	    						temp.setStatusId(proxyCheck.getId());
	    	    						temp.setStatusInt(proxyCheck.getStatus());
	    	    					}
	    	    				}
	    	    			}
	    					cdListOut.add(temp);
	    				} 
	    			}
//    			}else{
//    				//无房产的显示资产信息(手工录入)
//    				parameterMap.put("nameCode","HOUSE");
//    	    		parameterMap.put("fieldName","remark");
//    	    		List<LoanProxyCheckDo> tempMap1 = loanApplyService.selectLoanProxyCheck(parameterMap);
//    				CertificateDo temp =new CertificateDo();
//    				temp.setLoanId(loanId);
//    				temp.setLoanPersonId(loanPersonDo.getLoanPersonId());
//    				temp.setCertificateType(CertificateType.HOUSE);
//    				temp.setCertificateName("remark");
//    				temp.setCertificateTypeHead(CertificateType.HOUSE.toString());
//    				temp.setRelationType(propertyDo.getRemark());
//    				temp.setCertificateId(propertyDo.getPropertyId());
//    				if(null !=tempMap1 && tempMap1.size()>0){
//	    				for(LoanProxyCheckDo proxyCheck :tempMap1){
//	    					if(temp.getLoanId().longValue()==proxyCheck.getLoanId().longValue()){
//	    						temp.setStatusId(proxyCheck.getId());
//	    						temp.setStatusInt(proxyCheck.getStatus());
//	    					}
//	    				}
//	    			}
//    				cdListOut.add(temp);
//    			}
//    			//2万以下，有房产的不用取这些数据
//    			if(loanDo.getApplyAmount()>applyAmount || loanPersonDo.getHasHouse().equals("F")){
					int certificateTypeHead = 0;
					if(loanPersonDo.getPropertyDo()!=null &&loanPersonDo.getPropertyDo().getCarDy()!=null&&loanPersonDo.getPropertyDo().getCarDy()==1&&loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("F")){
						for(CertificateDo temp :cdList){
							if(temp.getCertificateType().toString().equals("DRIVERCARD")){
								if(null !=tempMap && tempMap.size()>0){
				    				for(LoanProxyCheckDo proxyCheck :tempMap){
				    					if(proxyCheck.getNameCode().toString().equals("DRIVERCARD")&&temp.getCertificateId().longValue()==proxyCheck.getRecordId().longValue()){
				    						temp.setStatusId(proxyCheck.getId());
				    						temp.setStatusInt(proxyCheck.getStatus());
				    					}
				    				}
				    			}
								if(certificateTypeHead++==0){
									temp.setCertificateTypeHead("DRIVERCARD");
								}
								cdListOut.add(temp);
							} 
						}
					}
					certificateTypeHead=0;
					for(CertificateDo temp :cdList){
						if(temp.getCertificateType().toString().equals("INCOME")){
							if(null !=tempMap && tempMap.size()>0){
			    				for(LoanProxyCheckDo proxyCheck :tempMap){
			    					if(proxyCheck.getNameCode().toString().equals("INCOME")&&temp.getCertificateId().longValue()==proxyCheck.getRecordId().longValue()){
			    						temp.setStatusId(proxyCheck.getId());
			    						temp.setStatusInt(proxyCheck.getStatus());
			    					}
			    				}
			    			}
							if(certificateTypeHead++==0){
								temp.setCertificateTypeHead("INCOME");
							}
							cdListOut.add(temp);
						} 
					}
//				}
    		}
//    		if(loanDo.getApplyAmount()<=applyAmount && loanPersonDo.getHasHouse().equals("T")){
//    			loanPersonDo.setHasHouse("3");
//    		}
    		map.addAttribute("pageId","4");
    	//页面4
    	}else  if (pageId.intValue()==4){
    		//加载联系人信息与对应状态
    		parameterMap.put("tableCode","LoanRelationDo");
    		List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
	    	List<LoanRelationDo> lrdList = loanPersonDo.getLoanRelationDoList();
	    	if(lrdList != null && lrdList.size()>0){
    			for(LoanRelationDo temp :lrdList){
    				CertificateDo tempAdd =new CertificateDo();
    				tempAdd.setLoanId(loanId);
    				tempAdd.setLoanPersonId(loanPersonDo.getLoanPersonId());
    				tempAdd.setCertificateType(CertificateType.CREDIT);
    				tempAdd.setCertificateName(temp.getRalationName());
    				tempAdd.setCertificateTypeHead(temp.getMobile());
    				tempAdd.setRelationType(temp.getRelationship());
    				tempAdd.setCertificateId(temp.getRalationId());
    				if(null !=tempMap && tempMap.size()>0){
	    				for(LoanProxyCheckDo proxyCheck :tempMap){
	    					if(tempAdd.getCertificateId().longValue()==proxyCheck.getRecordId().longValue()){
	    						tempAdd.setStatusId(proxyCheck.getId());
	    						tempAdd.setStatusInt(proxyCheck.getStatus());
	    					}
	    				}
	    			}
    				cdListOut.add(tempAdd);
    			}
    		}
    		map.addAttribute("pageId","0");
    	}
    	map.addAttribute("loanPerson", loanPersonDo);
    	map.addAttribute("cdListOut",cdListOut);
    	return "/app/mhk/proxyCheck"+pageId;
    }
    
    /**
     * huangzl   2015年6月2日 19:38:59
     */
    @RequestMapping(value = "/proxyCheckSave", method = RequestMethod.POST)
    public String proxyCheckSave(@ModelAttribute CertificateDoFrom certificateDoFrom,Model map ){
    	List<CertificateDo> cdList = certificateDoFrom.getCertificateDoList();
    	Integer pageId =Integer.valueOf(certificateDoFrom.getPageId());
    	Long loanId =certificateDoFrom.getLoanPerson().getLoanId();
    	String tableCode =certificateDoFrom.getTableCode();
    	//彩管家登录，登录信息
		Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(WebConstants.COLOURLIFE_ADMIN_USER);
		//E贷款登录的，登录信息
		AccountUserDo userDo =(AccountUserDo) request.getSession().getAttribute("user");
		
		if(null!=cdList){
	    	for(CertificateDo temp:cdList){
	    		if(null!=temp.getLoanId()){
	    			LoanProxyCheckDo proxyCheck=new LoanProxyCheckDo();
	    			proxyCheck.setLoanId(temp.getLoanId());
	    			proxyCheck.setTableCode(tableCode);
	    			proxyCheck.setRecordId(temp.getCertificateId());
	    			proxyCheck.setNameCode(temp.getCertificateType().toString());
	    			proxyCheck.setFieldName(temp.getCertificateName());
	    			proxyCheck.setStatus(temp.getStatusInt());
	    			//设置当前登录人信息
	    			if(params != null){
	    				proxyCheck.setAuditUserId(Long.valueOf(params.get("userId")));// 之后要获取值填充
	    				proxyCheck.setAuditUserName(params.get("userName"));
	    			}
//	    			if(userDo != null){
//	    				proxyCheck.setAuditUserId(userDo.getId());
//	    			}
	    			proxyCheck.setAuditTime(new Date());
	    			proxyCheck.setUpdateTime(new Date());
	    			if(null != temp.getStatusId() && temp.getStatusId()>0){
	    				proxyCheck.setId(temp.getStatusId());
	    				loanApplyService.updateLoanProxyCheckById(proxyCheck) ;
	    			}else{
	    				proxyCheck.setCreateTime(new Date());
	    				loanApplyService.addLoanProxyCheck(proxyCheck) ;
	    			}
	    		}
	    	}
    	}
    	map.addAttribute("loanId",loanId);
    	if(pageId.intValue()==2){
    		return "redirect:/app/mhk/loanInit.do?loanId="+loanId+"&pageId="+pageId;
    	}else{
    		return "redirect:/app/mhk/initPersonInfo.do";
    	}
		
	}
    /**
     * huangzl   2015年6月2日 19:38:59
     */
    @RequestMapping("/proxyCheckSaveAjax")
    @ResponseBody
    public Map<String, Object> proxyCheckSaveAjax(@ModelAttribute CertificateDo certificateDo ){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("isSuccess",true);
    	//彩管家登录，登录信息
		Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(WebConstants.COLOURLIFE_ADMIN_USER);
		//E贷款登录的，登录信息
		AccountUserDo userDo =(AccountUserDo) request.getSession().getAttribute("user");
		
		if(null!=certificateDo&&null!=certificateDo.getLoanId()){
			LoanProxyCheckDo proxyCheck=new LoanProxyCheckDo();
			proxyCheck.setLoanId(certificateDo.getLoanId());
			proxyCheck.setTableCode("CertificateDo");
			proxyCheck.setRecordId(certificateDo.getCertificateId());
			proxyCheck.setNameCode(certificateDo.getCertificateType().toString());
			proxyCheck.setFieldName(certificateDo.getCertificateName());
			proxyCheck.setStatus(certificateDo.getStatusInt());
			//设置当前登录人信息
			if(params != null){
				proxyCheck.setAuditUserId(Long.valueOf(params.get("userId")));
				proxyCheck.setAuditUserName(params.get("userName"));
			}
//			if(userDo != null){
//				proxyCheck.setAuditUserId(userDo.getId());
//			}
			proxyCheck.setAuditTime(new Date());
			proxyCheck.setUpdateTime(new Date());
			if(null != certificateDo.getStatusId() && certificateDo.getStatusId()>0){
				proxyCheck.setId(certificateDo.getStatusId());
				loanApplyService.updateLoanProxyCheckById(proxyCheck) ;
			}else{
				proxyCheck.setCreateTime(new Date());
				loanApplyService.addLoanProxyCheck(proxyCheck) ;
			}
			map.put("loanId",certificateDo.getLoanId());
		}else{
			map.put("isSuccess",false);
			map.put(WebConstants.MESSAGE_KEY, "登录过期请重新登录");
			map.put("loanId",certificateDo.getLoanId());
		}
    	return map;
    	
    }
//    /**
//     * 初始个人信息页面 old 
//     * @param loanId
//     * @param model
//     * @author zhengyfmf
//     * @return
//     */
//    @RequestMapping(value="/initPersonInfo")
//    public String initPersonInfo(@RequestParam Long loanId,Model model){
//    	
//    	LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
//    	loanPersonService.updateLoanShInfo(loanPersonDo);
//    	//个人基本信息填写完整
//    	if(loanPersonDo != null && loanPersonDo.getAge() != null
//    			&& loanPersonDo.getSex() != null && loanPersonDo.getEducation() != null
//    			&& loanPersonDo.getMarriaged() != null && StringUtils.isNotBlank(loanPersonDo.getCaddress())
//    			&& StringUtils.isNotBlank(loanPersonDo.getEmail())
//    			&& StringUtils.isNotBlank(loanPersonDo.getLoanDo().getLoanUsage())){
//    		JobDo jobDo = loanPersonDo.getJobDo();
//    		if(jobDo != null && jobDo.getJobType() != null && StringUtils.isNotBlank(jobDo.getCompanyName()) 
//    				&& StringUtils.isNotBlank(jobDo.getPosition()) && jobDo.getJobIncome() != null 
//    				&& jobDo.getJobYear() != null 
//    				){
//    			model.addAttribute("pnal", 0); //填写完整
//    		}else{
//    			model.addAttribute("pnal", 1);
//    		}
//    	}else{
//    		model.addAttribute("pnal", 1);
//    	}
//    	// 资产信息填写完整
//    	PropertyDo propertyDo = loanPersonDo.getPropertyDo();
//    	if(propertyDo != null && StringUtils.isNotBlank(propertyDo.getHouseAddress())
//    			&& propertyDo.getHousePrice() != null && propertyDo.getPurchaseDate() != null 
//    			&& propertyDo.getCoveredArea() != null && propertyDo.getCoveredArea().doubleValue()>0d
//    			&& propertyDo.getHouseDy() != null && propertyDo.getPurchaseWay() != null){
//    		if(isHouseMortgageComplete(propertyDo) && isCarMortgageComplete(propertyDo)){
//    			model.addAttribute("ppty", 0); //填写完整
//    		}else{
//    			model.addAttribute("ppty", 1); 
//    		}
//    	}else{
//    		model.addAttribute("ppty", 1); 
//    	}
//    	
//    	//联系人信息填写完整
//    	List<LoanRelationDo> lrdList = loanPersonDo.getLoanRelationDoList();
//    	if(lrdList != null && lrdList.size()>0){
//    		LoanRelationDo lrd = null;
//    		for(int i=0;i<lrdList.size();i++){
//    			if(lrdList.get(i).getRelationType() == 1){
//    				lrd = lrdList.get(i);
//    			}
//    		}
//    		if(lrd != null && StringUtils.isNotBlank(lrd.getRalationName()) 
//    				&& StringUtils.isNotBlank(lrd.getRelationship())
//    				&& StringUtils.isNotBlank(lrd.getMobile())){
//    			model.addAttribute("pred", 0); //填写完整
//    		}else{
//    			model.addAttribute("pred", 1); 
//    		}
//    	}else{
//    		model.addAttribute("pred", 1); 
//    	}    
//    	
//    	//资料上传 完整
//    	List<CertificateDo> cdList = loanPersonDo.getCertificateDoList();
//    	if(cdList != null && cdList.size()>=5){
//    		boolean idcardZ=false,idcardF=false,credit=false,job=false,house=false,income=false;
//    		for(int i =0;i<cdList.size();i++){
//    			if(cdList.get(i).getCertificateType().equals(CertificateDo.CertificateType.IDCARDZ)){
//    				idcardZ = true ;
//    			}
//    			if(cdList.get(i).getCertificateType().equals(CertificateDo.CertificateType.IDCARDF)){
//    				idcardF = true ;
//    			}
//    			if(cdList.get(i).getCertificateType().equals(CertificateDo.CertificateType.CREDIT)){
//    				credit = true ;
//    			}
//    			if(cdList.get(i).getCertificateType().equals(CertificateDo.CertificateType.JOB)){
//    				job = true ;
//    			}
//    			if(cdList.get(i).getCertificateType().equals(CertificateDo.CertificateType.HOUSE)){
//    				house = true ;
//    			}
//    			if(cdList.get(i).getCertificateType().equals(CertificateDo.CertificateType.INCOME)){
//    				income = true ;
//    			}
//    		}
//    		if(idcardZ&&idcardF&&credit&&job&&house&&income){
//    			model.addAttribute("pcee", 0); //资料上传完整
//    		}else{
//    			model.addAttribute("pcee", 1); 
//    		}
//    	}else{
//    		model.addAttribute("pcee", 1); 
//    	}
//    	
//    	model.addAttribute("loanId", loanId);
//    	model.addAttribute("loanPersonId",loanPersonDo.getLoanPersonId());    
//    	return "/app/mhk/personalInfo";
//    }
    
    /**
     * 房产 按揭 参数信息是否完整
     * @param propertyDo
     * @author zhengyfmf
     * @return
     */
    private static boolean isHouseMortgageComplete(PropertyDo propertyDo){
    	if(PurchaseWay.MORTGAGE.equals(propertyDo.getPurchaseWay())){
			if(propertyDo.getHouseReDur() != null && propertyDo.getHouseReMtg() != null && propertyDo.getHouseMthOwing() != null){
				return true ;
			}else{
				return false ;
			}
		}
    	return true ;
    }
    
    /**
     * 车产 按揭 参数信息是否完整
     * @param propertyDo
     * @author zhengyfmf
     * @return
     */
    private static boolean isCarMortgageComplete(PropertyDo propertyDo){
    	if(propertyDo.getCarWay() == 1){
    		if(propertyDo.getCarReDur() != null && propertyDo.getCarMthOwing() != null && propertyDo.getCarReMtg() != null){
    			return true ;
    		}else{
    			return false ;
    		}
    	}
    	return true ;
    }
    
    /**
	 * 资产信息初始化
	 * @param loanId
	 * @param loanPersonId
	 * @param model
	 * @author huangzlmf
	 * @return
	 */
	@RequestMapping(value="/initProperty")
	public String initPropertyData(Long loanId,Long loanPersonId,Model model){
		PropertyDo propertyDo = loanPersonService.initPropertyData(loanId);
		if(propertyDo != null){
			if(PurchaseWay.NOMORTGAGE.equals(propertyDo.getPurchaseWay())){
				propertyDo.setHoustWay(0);
			}
			if(PurchaseWay.MORTGAGE.equals(propertyDo.getPurchaseWay())){
				propertyDo.setHoustWay(1);
			}
			String tempDate="";
			if(null!=propertyDo.getPurchaseDate()){
				tempDate=new SimpleDateFormat("yyyyMMdd").format(propertyDo.getPurchaseDate());
				propertyDo.setHyear(Integer.valueOf(tempDate.substring(0, 4)));
				propertyDo.setHmonth(Integer.valueOf(tempDate.substring(4, 6)));
				propertyDo.setHday(Integer.valueOf(tempDate.substring(6, 8)));
			}
			if(null!=propertyDo.getCarDate()){
				tempDate=new SimpleDateFormat("yyyyMMdd").format(propertyDo.getCarDate());
				propertyDo.setCyear(Integer.valueOf(tempDate.substring(0, 4)));
				propertyDo.setCmonth(Integer.valueOf(tempDate.substring(4, 6)));
				propertyDo.setCday(Integer.valueOf(tempDate.substring(6, 8)));
			}
			
		}else{
			propertyDo = new PropertyDo();
			propertyDo.setLoanId(loanId);
			propertyDo.setLoanPersonId(loanPersonId);
		}
		model.addAttribute("propertyDo",propertyDo);
		return "app/mhk/property";
	}
	
	/**
	 * 修改资产信息
	 * @param propertyDo
	 * @author huangzlmf
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveOrUpdateProperty")
	public String saveOrUpdatePropertyData(PropertyDo propertyDo,Model model){
		if(propertyDo.getHoustWay().intValue() == 0){
			propertyDo.setPurchaseWay(PurchaseWay.NOMORTGAGE);
		}else{
			propertyDo.setPurchaseWay(PurchaseWay.MORTGAGE);
		}
		try {
			String tempDateString ="";
			String month="";
			String day="";
			if(null!=propertyDo.getHyear()||null!=propertyDo.getHmonth()||null!=propertyDo.getHday()){
				month=propertyDo.getHmonth().toString().length()==1?"0"+propertyDo.getHmonth().toString():propertyDo.getHmonth().toString();
				day=propertyDo.getHday().toString().length()==1?"0"+propertyDo.getHday().toString():propertyDo.getHday().toString();
				tempDateString =propertyDo.getHyear()+"-"+month+"-"+day;
				propertyDo.setPurchaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(tempDateString));
			}
			if(null!=propertyDo.getCyear()||null!=propertyDo.getCmonth()||null!=propertyDo.getCday()){
				month=propertyDo.getCmonth().toString().length()==1?"0"+propertyDo.getCmonth().toString():propertyDo.getCmonth().toString();
				day=propertyDo.getCday().toString().length()==1?"0"+propertyDo.getCday().toString():propertyDo.getCday().toString();
				tempDateString =propertyDo.getCyear()+"-"+month+"-"+day;
				propertyDo.setCarDate(new SimpleDateFormat("yyyy-MM-dd").parse(tempDateString));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ResultSupport<String> rs = (ResultSupport<String>) loanPersonService.saveOrUpdateProperty(propertyDo);
		if(rs.isSuccess()){
			model.addAttribute("loanId",propertyDo.getLoanId());
			return "redirect:/app/mhk/initPersonInfo.do";
		}
		LoanPersonDo loanPersonDo = new LoanPersonDo();
		loanPersonDo.setLoanId(propertyDo.getLoanId());
		loanPersonDo.setLoanPersonId(propertyDo.getLoanPersonId());
		loanPersonDo.setPropertyDo(propertyDo);
		model.addAttribute("loanPersonDo", loanPersonDo);
		model.addAttribute(WebConstants.MESSAGE_KEY, rs.getErrorMessage());
		return "app/mhk/property";
	}

	
	
	/**
	 * 业绩查询
	 * @throws SessionException 
	 */
	@RequestMapping("/performList")
	public String performList(ModelMap map) throws SessionException{
		Map<String,Object> searchItems = new HashMap<String,Object> ();
		Map<String,Object> tmpMap = null;
//		searchItems.put("cname", getCurentCname());
		searchItems.put("loanCidList",getLoanCidList());
		searchItems.put("productCode", "D03");
		//查询  新订单
		List list = new ArrayList();
		list.add(LoanStatus.PENDING.name());
		list.add(LoanStatus.AUDITED.name());
		list.add(LoanStatus.PROCESSING.name());
		list.add(LoanStatus.TREATY.name());
		list.add(LoanStatus.SUBJECTED.name());
		 searchItems.put("loanStatusList",list);
		tmpMap = loanPersonService.getSumLoan(searchItems);
		list= null;
		map.put("pendingOrder", tmpMap.get("sumIds"));
		//查询 已拒绝
		searchItems.remove("loanStatusList");
		searchItems.put("loanStatus", "NOPASS");
		
		tmpMap = loanPersonService.getSumLoan(searchItems);
		map.put("nopassOrder", tmpMap.get("sumIds"));
		//还款中
		searchItems.put("loanStatus", LoanStatus.REPAYING.name());
		int count =loanPersonService.getTotalCount(searchItems);
		
		map.put("borrowingOrder", count);
		//已还清
		searchItems.put("loanStatus", LoanStatus.REPAYED.name());
		count =loanPersonService.getTotalCount(searchItems);
		map.put("borrowedOrder", count);
		return "/app/mhk/performList";
	}
	
	/**
	 * 查询单个订单类型的业绩
	 * @author wangt  
	 *
	 * 2015年3月30日 下午2:54:42 
	 * @return
	 * @throws SessionException 
	 */
	@RequestMapping("/getPerform")
	public String getPerform(String status,String realName ,ModelMap map) throws SessionException{
		Map<String,Object> searchItems = new HashMap<String,Object> ();
//		searchItems.put("cname", getCurentCname());
		searchItems.put("loanCidList",getLoanCidList());
		searchItems.put("productCode", "D03");
        if(StringUtils.isNotBlank(realName)){
        	searchItems.put("realName", realName);
        }
      //查询   
      	PageVO page = getPageVO(request);
        Map<String,Object> tmpMap = null;
        searchItems.put("beginCount",page.getBeginCount());
        searchItems.put("pageSize", page.getPageSize());
        List<LoanPersonDo> list= null;
		if("PENDING".equals(status) || "NOPASS".equals(status)){
			if("PENDING".equals(status)){
				List list1 = new ArrayList();
				list1.add(LoanStatus.PENDING.name());
				list1.add(LoanStatus.AUDITED.name());
				list1.add(LoanStatus.PROCESSING.name());
				list1.add(LoanStatus.TREATY.name());
				list1.add(LoanStatus.SUBJECTED.name());
				 searchItems.put("loanStatusList",list1);
			}else{
				searchItems.put("loanStatus", status);

			}
			tmpMap = loanPersonService.getSumLoan(searchItems);
			map.put("sumIds", tmpMap.get("sumIds"));
			map.put("sumApplyAmount", tmpMap.get("sumApplyAmount"));
			list = loanPersonService.queryLoanPersonByApp(searchItems);
			 
		}else if("BORROWING".equals(status) ){
			tmpMap=loanPersonService.getSumBorrowing(searchItems);
			map.put("sumIds", tmpMap.get("sumIds"));
            map.put("sumBorrowingAmount", tmpMap.get("sumBorrowingAmount"));
            map.put("sumBorrowedAmount", tmpMap.get("sumBorrowedAmount"));
            searchItems.put("loanStatus", LoanStatus.REPAYING.name());
            list = loanPersonService.queryLoanBorrowByApp(searchItems);
		}else if("BORROWED".equals(status)){
			
			tmpMap =loanPersonService.getSumBorrowed(searchItems);
			map.put("sumIds", tmpMap.get("sumIds"));
            map.put("sumBorrowAmount", tmpMap.get("sumBorrowAmount"));
          
            searchItems.put("loanStatus", LoanStatus.REPAYED.name());
            list = loanPersonService.queryLoanBorrowByApp(searchItems);
		}
		map.put("status", status);
        map.put("realName", realName);
		map.put("list", list);

		return "/app/mhk/performInfo";
	}
	@RequestMapping("/getPerformAjax")
	@ResponseBody
	public Map getPerformAjax(String beginCount,String pageSize ,String realName,String status ) throws SessionException{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> searchItems = new HashMap<String,Object> ();
//		searchItems.put("cname", getCurentCname());
		searchItems.put("loanCidList",getLoanCidList());
		searchItems.put("productCode", "D03");
        if(StringUtils.isNotBlank(realName)){
        	searchItems.put("realName", realName);
        }
      //查询   
      	PageVO page = getPageVO(request);
        Map<String,Object> tmpMap = null;
        searchItems.put("beginCount",page.getBeginCount());
        searchItems.put("pageSize", page.getPageSize());
        List<LoanPersonDo> list= null;
		if("PENDING".equals(status) || "NOPASS".equals(status)){
			if("PENDING".equals(status)){
				List list1 = new ArrayList();
				list1.add(LoanStatus.PENDING.name());
				list1.add(LoanStatus.AUDITED.name());
				list1.add(LoanStatus.PROCESSING.name());
				list1.add(LoanStatus.TREATY.name());
				list1.add(LoanStatus.SUBJECTED.name());
				 searchItems.put("loanStatusList",list1);
			}else{
				searchItems.put("loanStatus", status);

			}
			tmpMap = loanPersonService.getSumLoan(searchItems);
			map.put("sumIds", tmpMap.get("sumIds"));
			map.put("sumApplyAmount", tmpMap.get("sumApplyAmount"));
			list = loanPersonService.queryLoanPersonByApp(searchItems);
			 
		}else if("BORROWING".equals(status) ){
			tmpMap=loanPersonService.getSumBorrowing(searchItems);
			map.put("sumIds", tmpMap.get("sumIds"));
            map.put("sumBorrowingAmount", tmpMap.get("sumBorrowingAmount"));
            map.put("sumBorrowedAmount", tmpMap.get("sumBorrowedAmount"));
            searchItems.put("loanStatus", LoanStatus.REPAYING.name());
            list = loanPersonService.queryLoanBorrowByApp(searchItems);
		}else if("BORROWED".equals(status)){
			
			tmpMap =loanPersonService.getSumBorrowed(searchItems);
			map.put("sumIds", tmpMap.get("sumIds"));
            map.put("sumBorrowAmount", tmpMap.get("sumBorrowAmount"));
          
            searchItems.put("loanStatus", LoanStatus.REPAYED.name());
            list = loanPersonService.queryLoanBorrowByApp(searchItems);
		}
		map.put("status", status);
        map.put("realName", realName);
		map.put("list", list);

		return map;
	}
	/**
	 * 初始 图片资料信息
	 * @param loanId
	 * @param loanPersonId
	 * @param model
	 * @author zhengyfmf
	 * @return
	 */
	@RequestMapping(value="/initCertificate")
	public String initCertificateData(Long loanId,Long loanPersonId,Model model){
		CertificateDo certificateDo = new CertificateDo();
		certificateDo.setLoanId(loanId);
		certificateDo.setLoanPersonId(loanPersonId);
		List<CertificateDo> cdList = loanPersonService.initCertificateData(loanId);
		
		if(cdList != null && cdList.size()>0){
			for(int i=0;i<cdList.size();i++){
				model.addAttribute(cdList.get(i).getCertificateType().toString(), cdList.get(i));
			}
		}
		model.addAttribute("certificateDo",certificateDo);
		model.addAttribute("fileAccessUrl", fileServerService.getFileAccessUrl());
		return "app/mhk/dataUpload";
	}
	
	/**
	 * 添加文件后缀
	 * @param fileName
	 * @return
	 */
	private String getUploadFileNameWithSuffix(String fileName){
		int idx = fileName.indexOf(".");
		if( idx > -1 ){
			return fileName;
		}else{
			return fileName+".jpg";
		}
		
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteCertificate")
	@ResponseBody
	public Map<String,Object> deleteCertificate(HttpServletRequest request){
		String certificateName = request.getParameter("certificateName");
		String loanId = request.getParameter("loanId");
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("loanId", loanId);
			paraMap.put("certificateName", certificateName);
			CertificateDo certificateDo = loanPersonService.getcertificate(paraMap);
			if(certificateDo != null && certificateDo.getCertificateId() != null && certificateDo.getCertificateId().intValue()>0){
				fileServerService.delFile(certificateDo.getFilePath());
				fileServerService.delFile(certificateDo.getDestFilePath());
			}
			
			ResultSupport<String> rs = (ResultSupport<String>)loanPersonService.deleteCertificateById(certificateDo.getCertificateId());
			if(!rs.isSuccess()){
				map.put("status", 1);
				map.put("errorMsg", "删除图片失败！");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 1);
			map.put("errorMsg", "删除图片失败！");
			return map;
		}
		map.put("status", 1);
		map.put("errorMsg", "删除图片成功！");
		return map;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveCertificate")
	@ResponseBody
	public Map<String,Object> saveCertificate(@RequestParam MultipartFile file,HttpServletRequest request){
		
		String imageType = request.getParameter("ctype");
		String loanId = request.getParameter("loanId");
		String personId = request.getParameter("loanPersonId");
		CertificateType certificateType=null;
		/**
		  身份证正面：IDCARDZ
		  本人身份证正面：IDCARDZS
			身份证反面：IDCARDF
			房产证明：HOUSE
			收入流水：INCOME
			工作证明：JOB
			征信报告： CREDIT 
			协议：PROTOCOL
			ENTRUST_PROTOCOL-委托划款协议
			CREDIT_AUDITK_PROTOCOL-信用审核服务协议
			DRIVERCARD-驾驶证
			ASSETS -资产相关证明
		 */
		if("ASSETS".equals(imageType)){
			certificateType = CertificateType.ASSETS;
		}
		if("DRIVERCARD".equals(imageType)){
			certificateType = CertificateType.DRIVERCARD;
		}
		if("IDCARDZ".equals(imageType)){
			certificateType = CertificateType.IDCARDZ;
		}
		if("IDCARDZS".equals(imageType)){
			certificateType = CertificateType.IDCARDZS;
		}
		if("ENTRUST_PROTOCOL".equals(imageType)){
			certificateType = CertificateType.ENTRUST_PROTOCOL;
		}
		if("CREDIT_AUDITK_PROTOCOL".equals(imageType)){
			certificateType = CertificateType.CREDIT_AUDITK_PROTOCOL;
		}
		
		if("IDCARDF".equals(imageType)){
			certificateType = CertificateType.IDCARDF;
		}
		
		if("HOUSE".equals(imageType)){
			certificateType = CertificateType.HOUSE;		
		}
		
		if("INCOME".equals(imageType)){
			certificateType = CertificateType.INCOME;	
		}
		
		if("JOB".equals(imageType)){
			certificateType = CertificateType.JOB;	
		}
		
		if("CREDIT".equals(imageType)){
			certificateType = CertificateType.CREDIT;
		}
		if("PROTOCOL".equals(imageType)){
			certificateType = CertificateType.PROTOCOL;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//彩管家登录，登录信息
		Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(WebConstants.COLOURLIFE_ADMIN_USER);
		//E贷款登录的，登录信息
		AccountUserDo userDo =(AccountUserDo) request.getSession().getAttribute("user");
		
		try {
			String fileName = file.getOriginalFilename();
			fileName = getUploadFileNameWithSuffix(fileName);
			CertificateDo certificateDo = new CertificateDo();
			certificateDo.setLoanId(Long.parseLong(loanId));
			//certificateDo.setLoanPersonId(Long.parseLong(personId));
			certificateDo.setCertificateType(certificateType);
			
			if(certificateDo.getCertificateId() != null && certificateDo.getCertificateId().intValue()>0){
				fileServerService.delFile(certificateDo.getFilePath());
				fileServerService.delFile(certificateDo.getDestFilePath());
			}
			
			List<String> fpList = fileServerService.saveAppFile(file.getInputStream(), fileName,new int[][] { { 200, 200 } });
			if(fpList == null || fpList.size()<1){
				map.put("status", 1);
				map.put("errorMsg", "上传图片失败！");
				return map;
			}
			certificateDo.setFilePath(fpList.get(0));
			certificateDo.setDestFilePath(fpList.get(1));
			//设置当前登录人信息
			if(params != null){
				certificateDo.setCreateUser(params.get("userName"));// 之后要获取值填充
			}
			if(userDo != null){
				certificateDo.setCreateUser(userDo.getUsername());
			}
			//end 设置当前登录人信息
			
			certificateDo.setCertificateName(fileName);
			certificateDo.setFileType(FileType.IMAGE);
			if(!("INCOME".equals(imageType))){
				List<CertificateDo> cdList = loanPersonService.initCertificateData(Long.parseLong(loanId));
				if(cdList != null && cdList.size()>0){
					int a =cdList.size();
					for(int i=0;i<a;i++){
						if(cdList.get(i).getCertificateType().toString().equals(imageType)){
							certificateDo.setCertificateId(cdList.get(i).getCertificateId());
						}
					}
				}
			}
			ResultSupport<String> rs = (ResultSupport<String>)loanPersonService.saveOrUpdateCertificate(certificateDo);
			if(rs.isSuccess()){
				map.put("status", 0);
				map.put("imgPath", fpList.get(1)); //压缩图片路径
				return map;
			}else{
				map.put("status", 1);
				map.put("errorMsg", "上传图片失败！");
				return map;
			}
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", 1);
			map.put("errorMsg", "上传图片失败！");
			return map;
		}
	}
	
	/**
	 * 初始 合同信息
	 * @param loanId
	 * @param model
	 * @author huangzlmf
	 * @return
	 */
	@RequestMapping(value="/initTreatyData")
	public String initTreatyData(Long loanId,Model model){
		Map<String, Object> searchItems = new HashMap<String, Object>();
		searchItems.put("loanId", loanId);
		LoanPersonDo loanPersonDo = loanPersonService.initTreatyData(searchItems);
        model.addAttribute("loanPerson", loanPersonDo);
        List<CertificateDo> cfList = loanPersonDo.getCertificateDoList();
        for(int i=0;i<cfList.size();i++){
        	model.addAttribute("treaty"+(i+1), cfList.get(i));
        }
        model.addAttribute("fileAccessUrl", fileServerService.getFileAccessUrl());
		return "/app/mhk/treaty";
	}
	
	/**
	 * 合同信息上传
	 * @author zhengyfmf
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveTreaty")
	@ResponseBody
	public Map<String,Object> saveTreaty(@RequestParam MultipartFile file,CertificateDo certificateDo){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(WebConstants.COLOURLIFE_ADMIN_USER);
		try {
			if(certificateDo.getCertificateId() != null && certificateDo.getCertificateId().intValue()>0){
				fileServerService.delFile(certificateDo.getFilePath());
				fileServerService.delFile(certificateDo.getDestFilePath());
			}
			List<String> fpList = fileServerService.saveAppFile(file.getInputStream(), file.getOriginalFilename(),new int[][] { { 400, 400 } });
			if(fpList == null || fpList.size()<1){
				map.put("status", 1);
				map.put("errorMsg", "上传合同失败！");
				return map;
			}
			certificateDo.setFilePath(fpList.get(0));
			certificateDo.setDestFilePath(fpList.get(1));
			certificateDo.setCreateUser(params.get("userName"));// 之后要获取值填充
			certificateDo.setCertificateName(file.getOriginalFilename());
			certificateDo.setCertificateType(CertificateType.PROTOCOL);
			certificateDo.setFileType(FileType.IMAGE);
			ResultSupport<String> rs = (ResultSupport<String>)loanPersonService.saveOrUpdateCertificate(certificateDo);
			if(rs.isSuccess()){
				map.put("status", 0);
				map.put("imgPath", fpList.get(1)); //压缩图片路径
				return map;
			}else{
				map.put("status", 1);
				map.put("errorMsg", "上传合同失败！");
				return map;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("status", 1);
		map.put("errorMsg", "上传合同失败！");
		return map;
	}
	
	@RequestMapping(value="/showImg")
	public void showImg(String imgPath,HttpServletResponse response){
		String fileName = fileServerService.getFilePath()+imgPath;
		File file = new File(fileName);
		if(!(file.exists() && file.canRead())) {
		}else{
			try {
				FileInputStream inputStream = new FileInputStream(file);
				byte[] data = new byte[(int)file.length()];
				inputStream.read(data);
				inputStream.close();
				response.setContentType("image/png");
				OutputStream stream = response.getOutputStream();
				stream.write(data);
				stream.flush();
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 资料读取
	 * @author huangzl  
	 *
	 * 2015年6月5日 09:10:29
	 * @return
	 * @throws SessionException 
	 */
	@RequestMapping("/firstAudit")
	@ResponseBody
	public Map<String,Object> firstAudit(@RequestParam(required=true) Long loanId ) throws SessionException{
		Map<String,Object> map = new HashMap();
		//先验证   
		LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
    	if (loanPersonDo.getLoanId() == null) {
			loanPersonDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
		}
    	LoanDo loanDo =loanPersonDo.getLoanDo();
    	loanDo.setLoanPersonDo(loanPersonDo);
    	double applyAmount =  getApplyAmountTemp(loanDo);
    	String typeTemp="";
		if (loanPersonDo.getHasHouse().equals("T")) {
			typeTemp="HOUSE";
		}else{
			typeTemp="ASSETS";
		}
		//读取状态表
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("loanId", loanId);
		parameterMap.put("tableCode","CertificateDo");
    	List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
    	//判断状体
    	Boolean personBoolean=true;
    	Boolean propertyBoolean=true;
    	Boolean lrdBoolean=true;
    	int statusInt=0;
    	//先判断是否为空
    	if(!(null!=tempMap&&tempMap.size()>0)){
    		personBoolean=false;
    		propertyBoolean=false;
    	}else{
	    	//判断个人信息的是否都通过审核
	    	for(LoanProxyCheckDo temp:tempMap){
	    		if(temp.getNameCode().toString().equals("IDCARDZ")||temp.getNameCode().toString().equals("IDCARDZS")||temp.getNameCode().toString().equals("IDCARDF")||temp.getNameCode().toString().equals("JOB")){
	    			if(null!=temp.getStatus()&&!(temp.getStatus()>=0)){
	    				personBoolean=false;
	    				break;
	    			}
	    			statusInt++;
	    		}
	    	}
	    	if(personBoolean&&statusInt==0){
	    		personBoolean=false;
	    	}
	    	//判断资产信息是否都通过审核
	    	statusInt=0;
	    	for(LoanProxyCheckDo temp:tempMap){
	    		
	    		if((loanPersonDo.getPropertyDo()!=null&&loanPersonDo.getPropertyDo().getCarDy()!=null&&loanPersonDo.getPropertyDo().getCarDy()==1&&loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("F")&&temp.getNameCode().toString().equals("DRIVERCARD"))
    				||temp.getNameCode().toString().equals(typeTemp)||
    				(((loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("F")&&loanDo.getApplyAmount()>applyAmount) ||
	    			(loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("T")&&loanDo.getApplyAmount()>200000.0))
	    			 && temp.getNameCode().toString().equals("INCOME"))){
	    			if(null!=temp.getStatus()&&!(temp.getStatus()>=0)){
	    				propertyBoolean=false;
	    				break;
	    			}
	    			statusInt++;
	    		}
	    	}
	    	if(propertyBoolean&&statusInt==0){
	    		propertyBoolean=false;
	    	}
    	}
    	//个人基本信息填写完整
    	if(loanDo.getApplyAmount()<=applyAmount && loanPersonDo.getHasHouse().equals("T")){
    		if(personBoolean.booleanValue()&&loanPersonDo != null  && StringUtils.isNotBlank(loanPersonDo.getIdNo())
    		   && StringUtils.isNotBlank(loanPersonDo.getRealName()) && StringUtils.isNotBlank(loanPersonDo.getMobile())){
    		}else{
		   		map.put("message", "个人信息不完整");
		   		return map;
    		}
    	}else{
			if(personBoolean&&loanPersonDo != null  && loanPersonDo.getEducation() != null
			    	   && loanPersonDo.getMarriaged() != null && StringUtils.isNotBlank(loanPersonDo.getEmail())
			    	   && StringUtils.isNotBlank(loanPersonDo.getRealName()) && StringUtils.isNotBlank(loanPersonDo.getIdNo())
			    	   && StringUtils.isNotBlank(loanPersonDo.getMobile())){
	    		JobDo jobDo = loanPersonDo.getJobDo();
	    		if(jobDo != null && jobDo.getJobType() != null  && jobDo.getJobYear() != null ){
	    		}else{
	    			map.put("message", "个人信息不完整");
	    			return map;
	    		}
	    	}else{
	    		map.put("message", "个人信息不完整");
	    		return map;
	    	}
    	}
    	// 资产信息填写完整
    	PropertyDo propertyDo = loanPersonDo.getPropertyDo();
    	if(loanDo.getApplyAmount()<=applyAmount && loanPersonDo.getHasHouse().equals("T")){
    		if(!propertyBoolean.booleanValue()){
    			map.put("message", "资产信息不完整");
	    		return map;
    		}
    	}else if(loanPersonDo.getHasHouse().equals("F")){
    		if(propertyBoolean&&propertyDo != null && propertyDo.getCarDy() != null ){
	    	}else{
	    		map.put("message", "资产信息不完整");
	    		return map;
	    	}
    	}else{
	    	if(propertyBoolean&&propertyDo != null && StringUtils.isNotBlank(propertyDo.getHouseAddress())
	    	    	   && propertyDo.getCoveredArea() != null && propertyDo.getCoveredArea().doubleValue()>0d
	    	    	   && propertyDo.getHouseDy() != null && propertyDo.getPurchaseWay() != null){
	    		
	    	}else{
	    		map.put("message", "资产信息不完整");
	    		return map;
	    	}
    	}
    	if(!(loanDo.getApplyAmount()<=applyAmount && loanPersonDo.getHasHouse().equals("T"))){
//    	if(loanDo.getApplyAmount()>20000.0 || loanPersonDo.getHasHouse().equals("F")){
    		parameterMap.put("tableCode","LoanRelationDo");
        	List<LoanProxyCheckDo> tempMap1 = loanApplyService.selectLoanProxyCheck(parameterMap);
        	//先判断是否为空
        	if(!(null!=tempMap1&&tempMap1.size()>0)){
        		lrdBoolean=false;
        	}else{
	    		statusInt=0;
	        	for(LoanProxyCheckDo temp:tempMap1){
	    			if(null!=temp.getStatus()&&!(temp.getStatus()>=0)){
	    				lrdBoolean=false;
	    				break;
	    			}
	    			statusInt++;
	        	}
	        	if(lrdBoolean&&statusInt==0){
	        		lrdBoolean=false;
	        	}
        	}
	    	//联系人信息填写完整
	    	List<LoanRelationDo> lrdList = loanPersonDo.getLoanRelationDoList();
	        if(lrdBoolean&&lrdList != null && lrdList.size()>0){
	        	LoanRelationDo lrd = null;
	        	for(int i=0;i<lrdList.size();i++){
	        		if(null !=lrdList.get(i).getRelationType()&&lrdList.get(i).getRelationType() == 1){
	        			lrd = lrdList.get(i);
	        		}
	        	}
	        	if(lrd != null && StringUtils.isNotBlank(lrd.getRalationName()) 
	        			&& StringUtils.isNotBlank(lrd.getMobile())){
	        	}else{
	        		map.put("message", "联系人信息不完整");
	        		return map;
	        	}
	        }else{
	        	map.put("message", "联系人信息不完整");
	        	return map;
	        }    
    	}
        
    	boolean isOver=false;
    	//根据当前金额和是否一线城市，给出下一步 和，单据状态
    	if(loanDo.getApplyAmount()<=applyAmount && loanPersonDo.getHasHouse().equals("T")){
    		isOver=true;
    	}
    	boolean isPass=true;
    	//判断是否有没有通过项
    	for(LoanProxyCheckDo proxyCheck : tempMap){
    		if(0 == proxyCheck.getStatus().intValue()){
    			isPass = false;
    			break;
    		}
    	}
    	if(isPass){//通过    	
    		if(isOver){
    			loanPersonDo.getLoanDo().setLoanStatus(LoanStatus.AUDITED);
				loanPersonDo.getLoanDo().setProcessCurrentStep(ProcessStep.PROXY_CHECK);
				loanPersonDo.getLoanDo().setProcessCurrentStep(ProcessStep.TO_SUBJECT);
    		}else{
				loanPersonDo.getLoanDo().setLoanStatus(LoanStatus.PROCESSING);
				loanPersonDo.getLoanDo().setProcessCurrentStep(ProcessStep.PROXY_CHECK);
				loanPersonDo.getLoanDo().setProcessNextStep(ProcessStep.INPUT_CREDIT_REPORT);
    		}
    	}else{
    		loanPersonDo.getLoanDo().setLoanStatus(LoanStatus.NOPASS);
    		loanPersonDo.getLoanDo().setProcessCurrentStep(ProcessStep.PROXY_CHECK);
    		loanPersonDo.getLoanDo().setProcessNextStep(ProcessStep.NULL);
    	}
    	
    	
    	IResult result =loanPersonService.updateLoanShInfo(loanPersonDo,getCurentUserName(),getCurentUserId(),LoanStatus.PENDING.name() ,"" );
		if(result.isSuccess()){
			 map.put("url", "getLoanPerson.do");
		}else{
		     map.put("message", "更新失败！");
		}
		return map;
	}
	
	/**
	 * 查看原因
	 * 
	 */
	@RequestMapping("/getReason")
	public String getReason(@RequestParam(required=true) Long loanId ,ModelMap map){
		LoanPersonDo loanPersonDo =loanPersonService.getLoanPersonById(loanId);
		AuditLogDo auditLog = loanPersonService.getOneAuditLogDoByLoanId(loanId);
		map.put("loanPersonDo", loanPersonDo);
		map.put("auditLog", auditLog);
		
		return "/app/mhk/orderResult";
	}
	
	/**
	 * 查看还款记录的 明细
	 * @author wangt  
	 *
	 * 2015年4月1日 下午9:34:07 
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/getRecord")
	public String getRecord(Long loanId,ModelMap map){
		//查询借款人信息
		LoanPersonDo loanPersonDo = loanPersonService.getLoanPersonById(loanId);
		
		//查询还款信息
		List<LoanRepaymentDo> list = loanRepaymentService.queryRepaymentByLoanId(loanId);
		if(list!=null ){
			for(LoanRepaymentDo repay :list ){
				if(repay.getRepayStatus()==1){
					map.put("loanRepaymentDo", repay);
					break;
				}
			}
		}
		//还款计划表
		map.put("list", list);
		map.put("loanPersonDo",loanPersonDo);
		return "app/mhk/overdue/repayDetail";
	}
	/**
	 * 读取否是一线城市的，一线城市5W别的2W
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	public double getApplyAmountTemp(LoanDo loanDo) {
		String caddress = loanDo.getLoanPersonDo().getCaddress();
		double applyAmount = 20000.0;
		if (caddress != null && caddress.length() > 0) {
			String[] caddressArray = caddress.split("-");
			if (caddressArray != null && caddressArray.length > 2) {
				if (caddressArray[1].equals("深圳市") || caddressArray[1].equals("北京市") || caddressArray[1].equals("上海市") || caddressArray[1].equals("广州市") || caddressArray[1].equals("澳门市") || caddressArray[1].equals("香港市")) {
					applyAmount = 50000.0;
				}
			}
		}
		return applyAmount;
	}
}
