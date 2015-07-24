package com.hehenian.biz.service.loan.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckedDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanDo.ProcessStep;
import com.hehenian.biz.common.loan.dataobject.LoanLogDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCheckDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanStatusDo;
import com.hehenian.biz.common.loan.dataobject.LoanUserBankDo;
import com.hehenian.biz.common.settle.SettleCalculatorUtils;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.common.util.CommonReqUtil;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.common.util.IdCardUtils;
import com.hehenian.biz.common.util.Md5Utils;
import com.hehenian.biz.component.loan.ILoanLogComponent;
import com.hehenian.biz.component.loan.ILoanPersonCheckComponent;
import com.hehenian.biz.component.loan.IManagerLoanComponent;
import com.hehenian.biz.component.loan.IManagerSettSchemeComponent;
import com.hehenian.lend.manager.common.contant.Constants;

/**
 * @author zhengyfmf
 */
@Service("managerLoanService")
public class ManagerLoanServiceImpl implements IManagerLoanService{
	
	private static Log log= LogFactory.getLog(ManagerLoanServiceImpl.class);
	
	@Autowired
	private IManagerLoanComponent managerLoanComponent;
	
	@Autowired
	private IManagerSettSchemeComponent managerSettSchemeComponent;
	
	@Autowired
	private ILoanLogComponent loanLogComponent;
	
	
	@Autowired
	private ILoanPersonCheckComponent loanPersonCheckComponent;
	
	@Override
	public int managerUpdateLoanStatus(LoanDo loanDo) {
		return managerLoanComponent.updateLoanStatus(loanDo);
	}
	
	@Value("#{sysconfig['call.xiaodai.order.http']}")
	private String callUrl;
	
	@Value("#{sysconfig['call.color.house.http']}")
	private String colorHouseUrl;
	
	@Value("#{sysconfig['color.house.key']}")
	private String colorToken;
	
	@Value("#{sysconfig['color.house.resouce.appId']}")
	private String colorHouseResourceAppId;
	
	/**
	 * 
	 * @param ownerName 业主真实姓名
	 * @param ownerIDCardNo 业主身份证号
	 * @param cname 小区名称
	 * @param houseNo 房号
	 * @param loanId 订单ID
	 */
	@Override
	public void callColorHouseCheck(String ownerName, String ownerIDCardNo , String cname, String houseNo,Long loanId){
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("cname", cname);
		params.put("houseNo", houseNo);
		params.put("ownerName", ownerName);
		params.put("ownerIDCardNo", ownerIDCardNo);
		long ts = System.currentTimeMillis()/1000;
		params.put("ts", String.valueOf(ts));
		params.put("appID", colorHouseResourceAppId);
		
		//组织参数
		StringBuilder sb = new StringBuilder();
		//sign=MD5($appID+$ts+$token+false)
		sb.append(colorHouseResourceAppId).append(String.valueOf(ts)).append(colorToken)
		  .append("false");
		String sign=Md5Utils.MD5(sb.toString());
		params.put("sign", sign);
		
		try{
			//调用彩生活地产
			String jsonString = HttpClientUtils.post(colorHouseUrl, params);
			Integer respCode = 1;
			String respMsg ="";
			//处理返回结果
			try {
	            ObjectMapper mapper = new ObjectMapper();
	            Map<String, Object> returnParams = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {
	            });
	            respCode = (Integer) returnParams.get("code");
	            respMsg = (String) returnParams.get("message");
	            //请求成功
	            if(0==respCode){
		            Map context = (Map) returnParams.get("content");
		            Integer returnCname = (Integer)context.get("cname");
		            Integer returnHouseNo = (Integer) context.get("houseNo");
		            Integer returnOwnerName = (Integer) context.get("ownerName");
		            Integer returnOwnerIDCardNo = (Integer)  context.get("ownerIDCardNo");
		            String returnManagementFee = (String) context.get("feeRecord");
		            LoanPersonCheckDo newLoanPersonCheckDo = new LoanPersonCheckDo();
		            newLoanPersonCheckDo.setCname(String.valueOf(returnCname));
		            newLoanPersonCheckDo.setHouseAddress(String.valueOf(returnHouseNo));
		            newLoanPersonCheckDo.setIdno(String.valueOf(returnOwnerIDCardNo));
		            newLoanPersonCheckDo.setName(String.valueOf(returnOwnerName));
		            newLoanPersonCheckDo.setMngfee(returnManagementFee);
		            newLoanPersonCheckDo.setLoanId(loanId);
		            
		            
		            LoanDo loanDo = new LoanDo();
		            loanDo.setLoanId(loanId);
		            boolean isPass = true;
		            if(0==returnCname||0==returnOwnerIDCardNo||0==returnOwnerName){
		            	isPass = false;
		            }
		            
		            /*
		            int count_one = 0;
		            for(int i = 0 ; i <returnManagementFee.length();i++){
		            	char oneMonth =  returnManagementFee.charAt(i);
		            	char  one = '1';
		            	if(one == oneMonth){
		            		count_one++;
		            	}
		            }
		            if(count_one<6){
		            	isPass = false;
		            }
		            */
		            if(isPass){
		            	loanDo.setLoanStatus(LoanStatus.PENDING);
		            	loanDo.setProcessNextStep(LoanDo.ProcessStep.PROXY_CHECK);
		            }else{
		            	loanDo.setLoanStatus(LoanStatus.NOPASS);
		            	loanDo.setProcessNextStep(LoanDo.ProcessStep.TO_EDIT);
		            }
		            
		            loanPersonCheckComponent.addLoanPersonCheck(newLoanPersonCheckDo);
		            
		            loanDo.setProcessCurrentStep(LoanDo.ProcessStep.CALL_COLOR_HOUSE_CHECK);
		            
		            managerLoanComponent.updateLoanStatus(loanDo);
	            }
	        } catch (IOException e) {
	            log.error(e.getMessage(), e);
	        }
			
			LoanLogDo log = new LoanLogDo();
			log.setLoanId(loanId);
			log.setLogType(6);//日志类型：1-上标，2-生成还款计划表5-二审通过大于30W走小贷 6, 调用验证业主
			log.setOrderCode("");
			log.setLogMsg(respMsg);
			if(0==respCode){
				log.setLogStatus(0);
				log.setLogMsg("请求彩生活验证业主接口成功");
			}else{
				log.setLogStatus(1);
				log.setLogMsg("请求彩生活验证业主接口失败");
			}
			loanLogComponent.addLoanLog(log);
			
		}catch(Exception e){
			log.error(e);
			
		}
        
	}

	@Override
	public boolean managerUpdateLoanStatus(List<Map<String,Object>> paramList){
		return managerLoanComponent.updateLoanStatusByMap(paramList);
	}
	
	@Override
	public PageDo<Map<String,Object>> managerGetLoanPage(Map<String,Object> param, PageDo<Map<String,Object>> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<Map<String, Object>> list;
		try {
			list = managerLoanComponent.getLoanPage(param);
			if(list != null){
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("##,###.00");
				for(int i=0 ;i<list.size();i++){
					Map<String,Object> map = list.get(i);
					/*map.remove("LOANID");
					map.remove("SCHEMEID");
					map.remove("CREATETIME");
					map.remove("LOANSTATUS");
					map.remove("PRODUCTNAME");
					map.remove("PRODUCTCODE");
					map.remove("SCHEMENAME");
					map.remove("APPLYAMOUNT");
					map.remove("REALNAME");
					map.remove("ORDERCODE");
					map.remove("MOBILE");
					map.remove("LOANPERIOD");
					map.remove("ANNUALRATE");
					map.remove("REFERENCEMOBILE");
					map.remove("AUDITUSER");*/
					map.put("num", i+1);
					map.put("loanStatusDesc", LoanStatusDo.getLoanStatusDesc((String)map.get("loanStatus")));
					if(map.get("applyAmount")!=null){
						map.put("applyAmount", myformat.format(map.get("applyAmount")));
					}
					if(map.get("annualRate")!=null){
						map.put("annualRate", String.valueOf(map.get("annualRate")));
					}
					if(map.get("createTime")!=null){
						try {
							map.put("createTime", DateUtils.formatTime(DateUtils.parseTime(map.get("createTime").toString())));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			page.setPage(list);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return page;
	}
	
	@Override
	public LoanDo getDetailLoanByLoanId(Long loanId) {
		return managerLoanComponent.getDetailLoanByLoanId(loanId);
	}

	@Override
	public PageDo<LoanDo> getLoanListPage(Map<String,Object> param, PageDo<LoanDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanDo> list = managerLoanComponent.getLoanListPage(param);
		page.setPage(list);
		return page;
	}

	@Override
	public LoanPersonDo getLoanDetailByLoanId(Long loanId) {
		return managerLoanComponent.getLoanDetailByLoanId(loanId);
	}

	@Override
	public List<LoanProductDo> listLoanProduct(Map<String, Object> param) {
		return managerLoanComponent.listLoanProduct(param);
	}

	@Override
	public List<Map<String, Object>> getLabelExportData(Map<String, Object> param) {
		List<Map<String, Object>> labelExportDataList =managerLoanComponent.getLabelExportData(param);
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("#####");
		for(Map<String, Object> temp:labelExportDataList){
			String idNo =null==temp.get("idNo")?"":temp.get("idNo").toString();
			temp.put("age", IdCardUtils.getAgeByIdCard(idNo));
			String sexString =IdCardUtils.getGenderByIdCard(idNo);
			sexString=sexString.equals("M")?"男":sexString.equals("F")?"女":"未知";
			temp.put("sex", sexString);
			temp.put("annualRate","10");
			if(temp.get("applyAmount")!=null){
				temp.put("applyAmount", myformat.format(temp.get("applyAmount")));
			}
			if(temp.get("jobIncome")!=null){
				temp.put("jobIncome", myformat.format(temp.get("jobIncome")));
			}
		}
		return labelExportDataList;
	}

	@Override
	public boolean initRepayPlan(LoanDo loanDo) {
		try {
			SettSchemeDo settSchemeDo = managerSettSchemeComponent.getBySchemeId(loanDo.getSchemeId());
			List<SettDetailDo> list = SettleCalculatorUtils.calSettDetailForRepayPlanRecord(loanDo.getLoanAmount(), loanDo.getLoanPeriod(), loanDo.getAnnualRate(), loanDo.getInvestAnnualRate(), settSchemeDo);
			boolean res = managerLoanComponent.initRepayPlan(loanDo, list);
			return res ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false ;
	}

	@Override
	public LoanDo getLoanByLoanId(Long loanId) {
		return managerLoanComponent.getLoanByLoanId(loanId);
	}

	@Override
	public LoanPersonDo getLoanInfoForSbByLoanId(Long loanId) {
		return managerLoanComponent.getLoanInfoForSbByLoanId(loanId);
	}

	@Override
	public LoanPersonDo getLoanInfoForFkByOrderCode(String orderCode) {
		return managerLoanComponent.getLoanInfoForFkByOrderCode(orderCode);
	}

	@Override
	public Map<String, Object> getBankAccountForHF(String idNo) {
		return managerLoanComponent.getBankAccountForHF(idNo);
	}

	@Override
	public Map<String, Object> getBankAccountForTL(String idNo) {
		return managerLoanComponent.getBankAccountForTL(idNo);
	}

	@Override
	public List<Map<String,Object>> getSbNameForHF(String orderCode) {
		return managerLoanComponent.getSbNameForHF(orderCode);
	}

	@Override
	public List<Map<String,Object>> getSbNameForTL(String orderCode) {
		return managerLoanComponent.getSbNameForTL(orderCode);
	}

	@Override
	public int updateLoan(LoanDo loanDo) {
		return managerLoanComponent.updateLoan(loanDo);
	}

	@Override
	public LoanPersonDo getLoanPersonDetail(Long loanId) {
		return managerLoanComponent.getLoanPersonDetail(loanId);
	}

	@Override
	public List<LoanUserBankDo> managerGetTbcInfo(Long userId) {
		return managerLoanComponent.getTbcInfo(userId);
	}

	@Override
	public List<LoanUserBankDo> managerGetTdbcInfo(Long userId) {
		return managerLoanComponent.getTdbcInfo(userId);
	}

	@Override
	public List<LoanCheckedDo> getLoanCheckedByLoanId(Long loanId, String checkType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loanId", loanId);
		param.put("checkType", checkType);
		return managerLoanComponent.getLoanCheckedByLoanId(param);
	}

	@Override
	public int updateLoanChecked(LoanCheckedDo loanCheckedDo) {
		//return managerLoanComponent.updateLoanChecked(loanCheckedDo);
		//--ljc 20150703 注掉上面代码，并替换为下面代码
		int rlt=0;
		try{
			settingLoanProcessStep(loanCheckedDo);
			rlt=managerLoanComponent.updateLoanChecked(loanCheckedDo);
			/*
			if("1".equals(loanCheckedDo.getCheckResult()) && "2".equals(loanCheckedDo.getCheckType() )){//二审通过，判断申请金额是否大于30万，30万走小贷
				sendToXiaoDaiDoLoanLog(loanCheckedDo.getLoanId());
			}
			*/
		}catch(BusinessException e)
		{
			rlt=Integer.valueOf(e.getErrorCode());
		}catch(Exception e)
		{
			rlt=-1;
			log.error("修改流程异常",e);
		}
		return rlt;
	}
	
	//记录日志
	private void sendToXiaoDaiDoLoanLog(final long loanId){
		final LoanDo loanDo = managerLoanComponent.getFullLoanDo(loanId);
		new Thread(new Runnable() {
			public void run() {
				//==============lml add date:2015/07/07
				String[] strArr = sendOrderToXiaoDai(loanDo);
				Map<String, Object> param = new HashMap<String, Object>();
				LoanLogDo log = new LoanLogDo();
				log.setLoanId(loanDo.getLoanId());
				log.setLogType(5);//日志类型：1-上标，2-生成还款计划表5-二审通过大于30W走小贷
				log.setOrderCode(loanDo.getOrderCode());
				if(strArr[0].equals("true")){
					log.setLogStatus(0);
					log.setLogMsg("请求小贷接口成功...");
				}else{
					log.setLogStatus(1);
					log.setLogMsg("请求小贷接口失败...");
				}
				loanLogComponent.addLoanLog(log);
				//==============lml add date:2015/07/07
			}
		}).start();	
	}
	
	/**
	 * 处理订单流程步骤
	 * @author ljc
	 * @date 20150703
	 * @param loanCheckedDo
	 * @throws Exception
	 */
	private void settingLoanProcessStep(LoanCheckedDo loanCheckedDo)throws Exception
	{
		if(loanCheckedDo==null)
		{
			throw new BusinessException("审核对象不能为空");
		}
		if(loanCheckedDo.getLoanId()==null)
		{
			throw new BusinessException("借款ID不能为空", "-101");
		}
		String checkResult=loanCheckedDo.getCheckResult();
		if(org.apache.commons.lang.StringUtils.isBlank(checkResult))
		{
			throw new BusinessException("审核状态值错误", "-102");
		} 
		LoanDo loanDo=new LoanDo();
		loanDo.setLoanId(loanCheckedDo.getLoanId());
		String step=loanCheckedDo.getCheckType();
		
		
		if("1".equals(checkResult))//一审通过，流程进入二审状态
		{
			loanDo.setLoanStatus(LoanStatus.PROCESSING);
		}else if("0".equals(checkResult))//一审不通过，流程返回审核失败。
		{
			loanDo.setLoanStatus(LoanStatus.NOPASS);
		}else if("2".equals(checkResult))//一审修改，流程状态进入草稿状态。
		{
			loanDo.setLoanStatus(LoanStatus.DRAFT);
		}else{
			throw new BusinessException("审核状态值错误", "-103");
		}
		
		
		LoanDo oldLoanDo = this.getLoanByLoanId(loanCheckedDo.getLoanId());
		double Amt = oldLoanDo.getApplyAmount();
		LoanStatus Status = loanDo.getLoanStatus();
		if("2".equals(step)){//二审
			loanDo.setLoanStatus(getNextStatus(Status, ProcessStep.MENDSTEP2,Amt));
			loanDo.setProcessCurrentStep(ProcessStep.MENDSTEP2);
			loanDo.setProcessNextStep(getNextStep(Status, ProcessStep.MENDSTEP2,Amt));
		}else if("1".equals(step)){//一审
			loanDo.setProcessCurrentStep(ProcessStep.MENDSTEP1);
			loanDo.setProcessNextStep(getNextStep(Status, ProcessStep.MENDSTEP1,Amt));
		}else{
			throw new BusinessException("审核步骤错误", "-104");
		}
		managerUpdateLoanStatus(loanDo);
	}

	
	private LoanStatus getNextStatus(LoanStatus currentStatus, ProcessStep currentStep ,Double applayAmount ){
		
		if(LoanStatus.DRAFT.equals(currentStatus)){
			return LoanStatus.DRAFT;
		}
		
		if(LoanStatus.NOPASS.equals(currentStatus)){
			return LoanStatus.NOPASS;
		}
		
    	if(LoanStatus.PROCESSING.equals(currentStatus)&&ProcessStep.MENDSTEP1.equals(currentStep)){
    		return LoanStatus.PROCESSING;
    	}
    	
    	if(LoanStatus.PROCESSING.equals(currentStatus)&&ProcessStep.MENDSTEP2.equals(currentStep)){
//    		if(applayAmount.doubleValue() >= 300000){
//    			return LoanStatus.PROCESSING;
//    		}else{
//    			return LoanStatus.AUDITED;
//    		}
    		return LoanStatus.AUDITED;
    	}
    		
    	return LoanStatus.DRAFT;
    }
    
    /**
     * 获取下个步骤
     * @param loanDo
     * @return
     */
	private ProcessStep getNextStep(LoanStatus currentStatus, ProcessStep currentStep ,Double applayAmount){
    	
		if(LoanStatus.NOPASS.equals(currentStatus)){
			return ProcessStep.NULL;
		}
		
    	if(LoanStatus.PROCESSING.equals(currentStatus)&&ProcessStep.MENDSTEP2.equals(currentStep)){
//    		if(applayAmount.doubleValue() >= 300000){
//    			return ProcessStep.XIAODAI_AUDIT;
//    		}else{
//    			return ProcessStep.TO_SUBJECT;
//    		}
    		return ProcessStep.TO_SUBJECT;
    	}
    		
    	if(LoanStatus.PROCESSING.equals(currentStatus)&&ProcessStep.MENDSTEP1.equals(currentStep)){
    		return ProcessStep.MENDSTEP2;
    	}
    	
    	return ProcessStep.TO_EDIT;
    }
	
	
    
	@Override
	public int createLoanChecked(LoanCheckedDo loanCheckedDo) {
		//return managerLoanComponent.createLoanChecked(loanCheckedDo);
		//--ljc 20150703 注掉上面代码，并替换为下面代码(修改订单流程状态)
		int rlt=0;
		try{
			settingLoanProcessStep(loanCheckedDo);
			rlt=managerLoanComponent.createLoanChecked(loanCheckedDo);
			/*
			if("1".equals(loanCheckedDo.getCheckResult()) && "2".equals(loanCheckedDo.getCheckType() )){//二审通过，判断申请金额是否大于30万，30万走小贷
				sendToXiaoDaiDoLoanLog(loanCheckedDo.getLoanId());
			}
			*/
		}catch(BusinessException e)
		{
			rlt=Integer.valueOf(e.getErrorCode());
		}catch(Exception e)
		{
			rlt=-1;
			log.error("修改流程异常",e);
		}
		return rlt;
	}

	@Override
	public LoanDo getLoanforUpdate(Map<String, Object> param) {
		return managerLoanComponent.getLoanforUpdate(param);
	}

	/**
	 * 获取渠道列表
	 */
	@Override
	public List<Map<String, Object>> getChannelTypeList() {
		return managerLoanComponent.getChannelTypeList();
	}
	
	@Override
	public List<LoanRelationDo> getLoanRelationListByLoanId(Long loanId) {
		return managerLoanComponent.getLoanRelationDoList(loanId);
	}
	
	@Override
	public String[] sendOrderToXiaoDai(LoanDo loan) {
		String[] arrStr = new String[2];
		arrStr[0] = "false";
		Map<String,Object> mapBig = new HashMap<String,Object>();
		List list = new ArrayList();
		Map<String,String> map = new HashMap<String,String>();
		if(loan != null){
			LoanPersonDo person = loan.getLoanPersonDo();
			SimpleDateFormat toDate = new SimpleDateFormat("yyyy/MM/dd");
			//person
			String name= "",ide= "",handset= "",educe= "",marriage= "",age= "",sex= "",localtime= "",communitycode= "",housetel= "",islocals= "",houseProvince
			= "",houseCity= "",housearea= "",houseaddress= "",regionProvince= "",regionCity= "",region
	        = "",regionaddress= "",livingconditions= "",email= "",qq= "",workunit= "",unitproperty= "",industry= "",posttime= "",postgrade= "",
	        workUnitProvince= "",workUnitCity= "",workunitarea= "",unitaddress= "",unittel= "",salarymonth= "",paydate= "",otherincome= "",
	        monthincomesum= "",partnerName= "",partnerIDEType= "",partnerIDE= "",partnerHandset
	        = "",partnerUnittel= "",partnerHousetel= "",partnerWorkunit= "",partnerDuty= "",partnerHouseProvice= "",partnerHouseCity
	        = "",partnerHouseArea= "",partnerHouseaddress= "",isknow = "";
			//project
			String isFirstLoan= "",applyGuaSum= "",buninessType= "",applyGuaTerm= "",loanRate
	        = "",returnType= "",bussinessStatus= "",organdto= "",clientSource= "",channelType= "",
	        loanPurposeType = "";
			
			if(person != null){//person
				name = StringUtils.defaultString(person.getRealName());
				ide = StringUtils.defaultString(person.getIdNo());
				handset = StringUtils.defaultString(person.getMobile());   
				educe = person.getEducation() == null ? "": 
					person.getEducation().toString() == "GRADE_SCHOOL" ? "960097":
						person.getEducation().toString() == "HIGN_SCHOOL" ? "1700171":
							person.getEducation().toString() == "POLYTECH_SCHOOL" ? "1700171":
								person.getEducation().toString() == "VOCATION_SCHOOL" ? "1700171":
									person.getEducation().toString() == "JUNIOR_COLLEGE" ? "960096":
										person.getEducation().toString() == "BACHELOR" ? "321":
											person.getEducation().toString() == "MASTER" ? "1700170":"";
				marriage = person.getMarriaged() == null ? "": 
					person.getMarriaged().toString() == "UNMARRIED" ? "330":
						person.getMarriaged().toString() == "MARRIED" ? "331":
							person.getMarriaged().toString() == "DIVORCE" ? "1700172":"";
				age = StringUtils.defaultString(person.getAge().toString()); 
				sex = person.getSex() == null ? "": 
						person.getSex().toString() == "MALE" ? "男":
							person.getSex().toString() == "FEMALE" ? "女":"";
				email = StringUtils.defaultString(person.getEmail()); 
				//1010101 居民身份证 1010106 其它
				partnerIDEType = "1010101";
				
			}
			//project
			buninessType = StringUtils.defaultString(loan.getProductName());
			loanRate = StringUtils.defaultString(loan.getAnnualRate().toString());
			applyGuaSum = StringUtils.defaultString(loan.getApplyAmount().toString(),"0");
			applyGuaTerm = StringUtils.defaultString(loan.getLoanPeriod().toString());
			returnType = StringUtils.defaultString(loan.getSchemeName());
			bussinessStatus = StringUtils.defaultString(LoanStatusDo.getLoanStatusDesc(loan.getLoanStatus().toString()));
			
			//
			
			//------------------------person对象组装------------------------
				map.put("name",name);//借款人姓名
				map.put("ide",ide);//身份证
				map.put("handset",handset);//手机号码
				map.put("educe",educe);//教育程度
				map.put("marriage",marriage);//婚姻
				map.put("age",age);//年龄
				map.put("sex",sex);//性别
				map.put("localtime",localtime);//来本地时间 格式2015/07/06
				map.put("communitycode",communitycode);
				map.put("housetel",housetel);
				map.put("islocals",islocals);
				map.put("houseProvince",houseProvince);
				map.put("houseCity",houseCity);
				map.put("housearea",housearea);
				map.put("houseaddress",houseaddress);
				map.put("regionProvince",regionProvince);
		        map.put("regionCity",regionCity);
		        map.put("region", region);
		        map.put("regionaddress", regionaddress);
		        map.put("livingconditions", livingconditions);
		        map.put("email", email);
		        map.put("qq", qq);
		        map.put("workunit", workunit);
		        map.put("unitproperty", unitproperty);
		        map.put("industry",industry);
		        map.put("posttime", posttime);
		        map.put("postgrade", postgrade);
		        map.put("workUnitProvince", workUnitProvince);
		        map.put("workUnitCity", workUnitCity);
		        map.put("workunitarea",workunitarea);
		        map.put("unitaddress", unitaddress);
		        map.put("unittel",unittel);
		        map.put("salarymonth",salarymonth);
		        map.put("paydate",paydate);
		        map.put("otherincome",otherincome);
		        map.put("monthincomesum",monthincomesum);
		        map.put("partnerName", partnerName);
		        map.put("partnerIDEType", partnerIDEType);
		        map.put("partnerIDE", partnerIDE);
		        map.put("partnerHandset", partnerHandset);
		        map.put("partnerUnittel",partnerUnittel);
		        map.put("partnerHousetel",partnerHousetel);
		        map.put("partnerWorkunit", partnerWorkunit);
		        map.put("partnerDuty",partnerDuty);
		        map.put("partnerHouseProvice",partnerHouseProvice);
		        map.put("partnerHouseCity", partnerHouseCity);
		        map.put("partnerHouseArea", partnerHouseArea);
		        map.put("partnerHouseaddress",partnerHouseaddress);
		        map.put("isknow", isknow);
	        //添加到大MAP对象
	        mapBig.put("person", map);
	        //------------------------project对象组装------------------------
		        map = new HashMap<String,String>();
		        map.put("isFirstLoan", isFirstLoan);
		        map.put("applyGuaSum", applyGuaSum);//申请金额
		        map.put("buninessType", buninessType);//产品类型
		        map.put("applyGuaTerm", applyGuaTerm);//贷款期限（月）
		        map.put("loanRate", loanRate);//贷款利率
		        map.put("returnType", returnType);//还款方式
		        map.put("bussinessStatus", bussinessStatus);//订单状态
		        map.put("organdto", organdto);
		        map.put("clientSource", clientSource);
		        map.put("channelType",channelType);
		        map.put("loanPurposeType", loanPurposeType);
		    //添加到大MAP对象
		    mapBig.put("project", map); 
		    //------------------------linkman对象组装------------------------
		    if(person != null){
		    	List<LoanRelationDo> relatList = person.getLoanRelationDoList();
		    	if(relatList.size() > 0){
		    		for (LoanRelationDo loanRelationDo : relatList) {
			    		map = new HashMap<String,String>();
				        map.put("name", StringUtils.defaultString(loanRelationDo.getRalationName()));
				        String rekatShip = loanRelationDo.getRelationship() == null ? "":
				        loanRelationDo.getRelationship().equals("父母") ? "1800202":
				        loanRelationDo.getRelationship().equals("子女") ? "1800203":
				        loanRelationDo.getRelationship().equals("兄弟") ? "1800204":
				        loanRelationDo.getRelationship().equals("姐妹") ? "1800206":
				        loanRelationDo.getRelationship().equals("兄妹") ? "1800207":
						loanRelationDo.getRelationship().equals("姐弟") ? "1910193":
						loanRelationDo.getRelationship().equals("亲戚") ? "9460949":
						loanRelationDo.getRelationship().equals("朋友") ? "9460950":
						loanRelationDo.getRelationship().equals("同事") ? "9460951":
						loanRelationDo.getRelationship().equals("配偶") ? "9460952":
						loanRelationDo.getRelationship().equals("本人") ? "9460964":"ENU150117000001";
				        map.put("relationNotice", rekatShip);
				        map.put("handset", StringUtils.defaultString(loanRelationDo.getMobile()));
				        map.put("houseTel", "");
				        map.put("workUnit", "");
				        map.put("post", "");
				        map.put("workUnitTel", "");
				        list.add(map);
			    	}
		    	}
			    //添加到大MAP对象
			    mapBig.put("linkman", list);
			    map = new HashMap<String,String>();
			    list = new ArrayList();
			    //------------------------file对象组装------------------------
			    List<CertificateDo> certifiList = person.getCertificateDoList();
			    if(certifiList.size() > 0){
			    	for (CertificateDo certificateDo : certifiList) {
			    		map = new HashMap<String,String>();
				        map.put("code", StringUtils.defaultString(certificateDo.getFileType().toString()));
				        map.put("path", StringUtils.defaultString(certificateDo.getFilePath()));
			        		list.add(map);
					}
			    }
				//添加到大MAP对象
			    mapBig.put("file", list);
			    map = new HashMap<String,String>();
		    }
		    list = new ArrayList();
		    list.add(mapBig);
		    
		    JSONArray jsonArray = JSONArray.fromObject(list);
		    System.out.println(jsonArray.toString());
		    String jsonString  = jsonArray.toString();
		    String url = callUrl;
		    //String url = "http://10.111.2.7:8080/credit.core.platform/p2p/order.do";
		    String res = CommonReqUtil.pushServiceToPost(url.trim(),jsonString);
		    JSONObject jsonObject = JSONObject.fromObject(res);
		    arrStr[0] = jsonObject.get("success").toString();
		    arrStr[1] = jsonObject.get("errorMessage").toString();
		}else{
			arrStr[1] = "loan is null";
		}
	    return arrStr;
	}

}
