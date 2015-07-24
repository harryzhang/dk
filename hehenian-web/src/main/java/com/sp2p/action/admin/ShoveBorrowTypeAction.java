package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.EmalAndMessageService;
import com.sp2p.service.admin.ShoveBorrowAmountTypeService;
import com.sp2p.service.admin.ShoveBorrowStyleService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.DateUtil;

/**
 * 标种类型
 * @author C_J
 *
 */
@SuppressWarnings("unchecked")
public class ShoveBorrowTypeAction  extends BasePageAction{
	public static Log log = LogFactory.getLog(ShoveBorrowTypeService.class);
	private static final long serialVersionUID = 1L;
	
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private ShoveBorrowStyleService  shoveBorrowStyleService;
	private ShoveBorrowAmountTypeService shoveBorrowAmountTypeService;
	private EmalAndMessageService  emalAndMessageService;
	private String paramMapStyles ;
	private  String  paramMaplistcounter;
	private  String  paramMaplistIn;

	/**
	 * 查询所有初始化
	 * @return
	 */
	public String shoveTypeAllInit() {
		
		return SUCCESS;
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public String shoveTypeAllList() {
		try {
			List<Map<String,Object>> mapTypeList = shoveBorrowAmountTypeService.queryBorrowAmountAll();
			shoveBorrowTypeService.queryShoveBorrowTypePageAll(pageBean);
			List<Map<String,Object>> list = pageBean.getPage();
			for (Map<String, Object> map : list) {
				int i = 0;
				String stytyles = map.get("styles")+"";
				if(stytyles.length() > 0){
					List<Map<String,Object>> titleList = shoveBorrowStyleService.queryBorrowAmountByIds(map.get("styles")+"");
					if(titleList != null){
						StringBuffer buf = new StringBuffer();
						for(Map<String,Object> titleMap : titleList){
							buf.append(titleMap.get("title"));
							i++;
							if( i != titleList.size()){
								buf.append("|");
							}
						}
						map.put("titles", buf.toString());
					}
				}
			}
			
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			
			request().setAttribute("mapTypeList", mapTypeList);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 添加标种类型初始化
	 * @return
	 */
	public String addShoveTypeInit() {
		try {
			List<Map<String,Object>> mapList = shoveBorrowStyleService.queryBorrowAll();
			List<Map<String,Object>> mapTypeList = shoveBorrowAmountTypeService.queryBorrowAmountAll();
			request().setAttribute("mapList", mapList);
			request().setAttribute("mapTypeList", mapTypeList);
			//得到担保机构
			List<Map<String,Object>> listInstitution = emalAndMessageService.queryinstitution();
			request().setAttribute("listInstitution",listInstitution);
			//得到反担保方式
			List<Map<String,Object>> listCounter = emalAndMessageService.queryguarantee();
			request().setAttribute("listCounter",listCounter);
			//}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 添加标种类型时验证
	 * @return
	 * @throws IOException
	 */
	public String validateAddShoveType() throws IOException{
		String[] arr=null;
		//名称
		String name = Convert.strToStr( SqlInfusion.FilteSqlInfusion(request().getParameter("tb_name")),"");
		if (StringUtils.isBlank(name)) {
			JSONUtils.printStr("1");
			return null;
		}
		//标识名
		String nid = Convert.strToStr( SqlInfusion.FilteSqlInfusion(request().getParameter("tb_nid")),"");
		if (StringUtils.isBlank(nid)) {
			JSONUtils.printStr("2");
			return null;
		}
		//标题
		String title = Convert.strToStr( SqlInfusion.FilteSqlInfusion(request().getParameter("tb_title")),"");
		if (StringUtils.isBlank(title)) {
			JSONUtils.printStr("3");
			return null;
		}
		//最低借款额度
		String amount_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_amount_first")), "");
		if (StringUtils.isBlank(amount_first)) {
			JSONUtils.printStr("4");
			request().setAttribute("tb_amount_first", amount_first);
			return null;
		}
		if(isNum(amount_first)==false || Double.parseDouble(amount_first)<0){
			JSONUtils.printStr("5");
			return null;
		}
		//最高借款额度
		String amount_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_amount_end")), "");
		if (StringUtils.isBlank(amount_end)) {
			JSONUtils.printStr("6");
			return null;
		}
		if(isNum(amount_end)==false || Double.parseDouble(amount_end)<0){
			JSONUtils.printStr("7");
			return null;
		}
		if(Double.parseDouble(amount_first)>Double.parseDouble(amount_end)){
			JSONUtils.printStr("8");
			return null;
		}
		//额度倍数
		String account_multiple = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_account_multiple")), "");
		if(StringUtils.isBlank(account_multiple)){
			JSONUtils.printStr("9");
			return null;
		}
		if(isNum(account_multiple)==false || Double.parseDouble(account_multiple)<0){
			JSONUtils.printStr("10");
			return null;
		}
		//最低年利率
		String apr_first = Convert.strToStr(request().getParameter("tb_apr_first"), "");
		if(StringUtils.isBlank(apr_first)){
			JSONUtils.printStr("11");
			return null;
		}
		if(isNum(apr_first)==false || Double.parseDouble(apr_first)<0){
			JSONUtils.printStr("12");
			return null;
		}
		//最高年利率
		String apr_end = Convert.strToStr(request().getParameter("tb_apr_end"), "");
		if(StringUtils.isBlank(apr_end)){
			JSONUtils.printStr("13");
			return null;
		}
		if(isNum(apr_end)==false){
			JSONUtils.printStr("14");
			return null;
		}
		if(Double.parseDouble(apr_first)>Double.parseDouble(apr_end)){
			JSONUtils.printStr("15");
			return null;
		}
		if(Double.parseDouble(apr_first)<0.01 ||Double.parseDouble(apr_end)>100){
			JSONUtils.printStr("16");
			return null;
		}
		//借款期限 月标
		String period_month = Convert.strToStr(request().getParameter("tb_period_month"), "");
		if(StringUtils.isBlank(period_month)){
			JSONUtils.printStr("17");
			return null;
		}
		arr = period_month.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("18");
				return null;
			}
			if (arr[i].indexOf(".") != -1){ //判断是不是整数
				 JSONUtils.printStr("18");
					return null;
			 }
			if(Integer.parseInt(arr[i])<0  || Integer.parseInt(arr[i])>60 )	{
				JSONUtils.printStr("18");
				return null;
			}
		}
		//借款期限  天标
		String period_day = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_period_day")), "");
		if(StringUtils.isBlank(period_day)){
			JSONUtils.printStr("19");
			return null;
		}
		 arr = period_day.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("20");
				return null;
			}
			if (arr[i].indexOf(".") != -1){ //判断是不是整数
				 JSONUtils.printStr("20");
					return null;
			 }
			if(Integer.parseInt(arr[i])<0  || Integer.parseInt(arr[i])>25 )	{
				JSONUtils.printStr("20");
				return null;
			}
		}
		//有效期
		String validate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_validate")), "");
		if(StringUtils.isBlank(validate)){
			JSONUtils.printStr("21");
			return null;
		}
		arr = validate.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("22");
				return null;
			}
			if (arr[i].indexOf(".") != -1){ //判断是不是整数
				 JSONUtils.printStr("22");
					return null;
			 }
		}
		//审核最短时间
		String check_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_check_first")), "");
		if(StringUtils.isBlank(check_first)){
			JSONUtils.printStr("23");
			return null;
		}if(isNum(check_first)==false || Double.parseDouble(check_first)<0){
			JSONUtils.printStr("24");
			return null;
		} if (check_first.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("24");
				return null;
		 }
		
		//审核最长时间
		String check_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_check_end")), "");
		if(StringUtils.isBlank(check_end)){
			JSONUtils.printStr("25");
			return null;
		}if(isNum(check_end)==false || Double.parseDouble(check_end)<0){
			JSONUtils.printStr("26");
			return null;
		}if(Double.parseDouble(check_first)>Double.parseDouble(check_end)){
			JSONUtils.printStr("27");
			return null;
		}if (check_end.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("26");
				return null;
		 }
		/*if(Double.parseDouble(check_first)>31 || Double.parseDouble(check_end)>31){
			JSONUtils.printStr("28");
			return null;
		}*/
		//最低投标金额
		String tender_account_min = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_tender_account_min")), "");
		if(StringUtils.isBlank(tender_account_min)){
			JSONUtils.printStr("29");
			return null;
		}
		//最高投标金额
		String account_max = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_account_max")), "");
		if(StringUtils.isBlank(account_max)){
			JSONUtils.printStr("30");
			return null;
		}
		arr = tender_account_min.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("31");
				return null;
			}
			if(Double.parseDouble(arr[i])<0 )	{
				JSONUtils.printStr("31");
				return null;
			}
		}
		arr = account_max.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("32");
				return null;
			}
			if(Double.parseDouble(arr[i])<0 )	{
				JSONUtils.printStr("32");
				return null;
			}
		}
		//奖励比例最小值
		String award_scale_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_scale_first")), "");
		if(StringUtils.isBlank(award_scale_first)){
			JSONUtils.printStr("33");
			return null;
		}
		//奖励比例最大值
		String award_scale_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_scale_end")), "");
		if(StringUtils.isBlank(award_scale_end)){
			JSONUtils.printStr("34");
			return null;
		}
		if(isNum(award_scale_first)==false ||isNum(award_scale_end)==false || Double.parseDouble(award_scale_first) <0 ||Double.parseDouble(award_scale_end) <0){
			JSONUtils.printStr("35");
			return null;
		}
		if(Double.parseDouble(award_scale_first)>Double.parseDouble(award_scale_end)){
			JSONUtils.printStr("36");
			return null;
		}if(Double.parseDouble(award_scale_first)<0 || Double.parseDouble(award_scale_end)>100){
			JSONUtils.printStr("37");
			return null;
		}
		//奖励固定金额最小值
		String award_account_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_account_first")), "");
		if(StringUtils.isBlank(award_account_first)){
			JSONUtils.printStr("38");
			return null;
		}
		//奖励固定金额最大值
		String award_account_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_account_end")), "");
		if(StringUtils.isBlank(award_account_end)){
			JSONUtils.printStr("39");
			return null;
		}
		if(isNum(award_account_first)==false ||isNum(award_account_end)==false || Double.parseDouble(award_account_first) <0 ||Double.parseDouble(award_account_end) <0){
			JSONUtils.printStr("40");
			return null;
		}
		if(Double.parseDouble(award_account_first)>Double.parseDouble(award_account_end)){
			JSONUtils.printStr("41");
			return null;
		}
		//初审自动通过备注
		String verify_auto_remark = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_verify_auto_remark")), "");
		if(StringUtils.isBlank(verify_auto_remark)){
			JSONUtils.printStr("42");
			return null;
		}
		//Vip冻结保证金
		/*String vip_frost_scale = Convert.strToStr(request().getParameter("tb_vip_frost_scale"), "");
		if(StringUtils.isBlank(vip_frost_scale)){
			JSONUtils.printStr("43");
			return null;
		}
		if(isNum(vip_frost_scale)==false){
			JSONUtils.printStr("44");
			return null;
		}
		if(Double.parseDouble(vip_frost_scale)<0 || Double.parseDouble(vip_frost_scale)>100){
			JSONUtils.printStr("45");
			return null;
		}*/
		
		//会员冻结保证金
		String all_frost_scale = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_all_frost_scale")), "");
		if(StringUtils.isBlank(all_frost_scale)){
			JSONUtils.printStr("46");
			return null;
		}
		if(isNum(all_frost_scale)==false){
			JSONUtils.printStr("47");
			return null;
		}
		if(Double.parseDouble(all_frost_scale)<0 || Double.parseDouble(all_frost_scale)>100){
			JSONUtils.printStr("48");
			return null;
		}
		//垫付逾期天数 (月标)
		String late_days_month = Convert.strToStr(request().getParameter("tb_late_days_month"), "");
		if(StringUtils.isBlank(late_days_month)){
			JSONUtils.printStr("49");
			return null;
		}
		if(Double.parseDouble(late_days_month)<0  ||isNum(late_days_month)==false){
			JSONUtils.printStr("50");
			return null;
		}
		 if (late_days_month.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("50");
				return null;
		 }
		//垫付逾期天数  (天标)
		String late_days_day = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_late_days_day")), "");
		if(StringUtils.isBlank(late_days_day)){
			JSONUtils.printStr("51");
			return null;
		}
		if(Double.parseDouble(late_days_day)<0 || isNum(late_days_day)==false){
			JSONUtils.printStr("52");
			return null;
		}
		 if (late_days_day.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("52");
				return null;
		 }
		//vip垫付利息比例
		/*String vip_late_scale = Convert.strToStr(request().getParameter("tb_vip_late_scale"), "");
		if(StringUtils.isBlank(vip_late_scale)){
			JSONUtils.printStr("53");
			return null;
		}
		if(Double.parseDouble(vip_late_scale)<0 ||Double.parseDouble(vip_late_scale) >100 || isNum(vip_late_scale)==false){
			JSONUtils.printStr("54");
			return null;
		}*/
		//会员垫付利息比例
		String all_late_scale = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_all_late_scale")), "");
		if(StringUtils.isBlank(all_late_scale)){
			JSONUtils.printStr("55");
			return null;
		}
		if(Double.parseDouble(all_late_scale)<0 ||Double.parseDouble(all_late_scale) >100 || isNum(all_late_scale)==false){
			JSONUtils.printStr("56");
			return null;
		}
		if("flow".equals(Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_nid")),""))){
			//担保机构
			String paramMapInstitution = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMapInstitution")), "");
			if(StringUtils.isBlank(paramMapInstitution)){
				JSONUtils.printStr("57");
				return null;
			}	
			//反担保方式
			String paramMapcounter = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMapcounter")), "");
			if(StringUtils.isBlank(paramMapcounter)){
				JSONUtils.printStr("58");
				return null;
			}
		}
		//还款方式 
		String paramMapStyles = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMapStyles")), "");
		if(StringUtils.isBlank(paramMapStyles)){
			JSONUtils.printStr("59");
			return null;
		}
		//月标借款费
		String  locan_fee = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("tb_locan_fee")),"");//借款费 
		if (StringUtils.isBlank(locan_fee)) {
			JSONUtils.printStr("60");
			return null;
		}
		if( isNum(locan_fee)==false ||  Double.parseDouble(locan_fee)<0 ||Double.parseDouble(locan_fee) >100){
			JSONUtils.printStr("61");
			return null;
		}
		
		String  locan_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("tb_locan_month")),"");//借款限定期数
		if (StringUtils.isBlank(locan_month)) {
			JSONUtils.printStr("62");
			return null;
		}
		if(Double.parseDouble(locan_month)<0 ||  isNum(locan_month)==false ||  Double.parseDouble(locan_month) >100){
			JSONUtils.printStr("63");
			return null;
		}
		 if (locan_month.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("63");
				return null;
		 }
		//超出借款限定期数借款费
		String  locan_fee_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("tb_locan_fee_month")),"");//超出借款限定期数借款费
		if (StringUtils.isBlank(locan_fee_month)) {
			JSONUtils.printStr("64");
			return null;
		}
		if( isNum(locan_fee_month)==false || Double.parseDouble(locan_fee_month)<0 || Double.parseDouble(locan_fee_month) >100 ){
			JSONUtils.printStr("65");
			return null;
		}
		//天标借款费
		String  day_fee = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("tb_day_fee")),"");	//天标借款费
		if (StringUtils.isBlank(day_fee)) {
			JSONUtils.printStr("66");
			return null;
		}
		if( isNum(day_fee)==false || Double.parseDouble(day_fee)<0 ||Double.parseDouble(day_fee) >100 ){
			JSONUtils.printStr("67");
			return null;
		}
		
		else{
			JSONUtils.printStr("68");
			return null;
		}
	} 
	
	
	/**
	 * 添加标种类型
	 * @return
	 */
	public String addShoveTypeInfo() {
		//名称
		String name = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.name")),null) ;
		//标识名
		String nid = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.nid")),null) ;
		//标题
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.title")),null) ;
		//描述
		String description = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.description")),null) ;
		//状态
		int  status = Convert.strToInt(request("paramMap_status"),-1) ;
		//额度类型
		int  amount_type = Convert.strToInt(request("paramMap_amount_type"),-1) ;
		//最低借款额度
		double amount_first = Convert.strToDouble(request("paramMap.amount_first"),0) ;
		//最高的借款额度
		double amount_end = Convert.strToDouble(request("paramMap.amount_end"),0) ;
		//借款金额倍数
		double account_multiple = Convert.strToDouble(request("paramMap.account_multiple"),0) ;
		//开始年利率
		double apr_first = Convert.strToDouble(request("paramMap.apr_first"),0) ;
		//结束年利率
		double apr_end = Convert.strToDouble(request("paramMap.apr_end"),0) ;
		//借款期限 月
		String period_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.period_month")),null) ;
		//借款期限  天
		String period_day = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.period_day")),null) ;
		//有效期
		String validate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.validate")),null) ;
		//审核最短时间
		int check_first = Convert.strToInt(request("paramMap.check_first"),0) ;
		//审核最长时间
		int check_end = Convert.strToInt(request("paramMap.check_end"),0) ;
		//最低投标金额
		String tender_account_min = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.tender_account_min")),null) ;
		//最高投标金额
		String tender_account_max = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.tender_account_max")),null) ;
		//是否启用 奖励
		int award_status = Convert.strToInt(request("paramMap_award_status"),0) ;
		//是否启用投标密码
		int password_status = Convert.strToInt(request("paramMap_password_status"),0) ;
		//奖励比例的最小值
		double award_scale_first = Convert.strToDouble(request("paramMap.award_scale_first"),0) ;
		//奖励比例的最大值
		double award_scale_end = Convert.strToDouble(request("paramMap.award_scale_end"),0) ;
		//不能小于此奖励金额
		double award_account_first = Convert.strToDouble(request("paramMap.award_account_first"),0) ;
		//不能高于此奖励金额
		double award_account_end = Convert.strToDouble(request("paramMap.award_account_end"),0) ;
		//初审自动通过
		int verify_auto_status = Convert.strToInt(request("paramMap_verify_auto_status"),0) ;
		//初审自动通过的备注
		String verify_auto_remark = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.verify_auto_remark")),null) ;
		//VIP冻结保证金
		//double vip_frost_scale = Convert.strToDouble(request("paramMap.vip_frost_scale"),0) ;
		//会员冻结保证金
		double all_frost_scale = Convert.strToDouble(request("paramMap.all_frost_scale"),0) ;
		//垫付逾期的天数  月标
		int late_days_month = Convert.strToInt(request().getParameter("paramMap.late_days_month"),0) ;
		//垫付逾期的天数  天标
		int late_days_day = Convert.strToInt(request("paramMap.late_days_day"),0) ;
		//vip会员垫付本金比例：
		//double vip_late_scale = Convert.strToDouble(request("paramMap.vip_late_scale"),0) ;
		//会员垫付本金比例
		double all_late_scale = Convert.strToDouble(request("paramMap.all_late_scale"),0) ;
		//还款方式
		String listStyle  = this.getParamMapStyles();
		//是否开启认购模式
		int subscribe_status = Convert.strToInt(request("paramMap_subscribe_status"),0) ;
		//借款费  
		double locan_fee = Double.parseDouble(paramMap.get("locan_fee"));
		//  超出限定期数  
		int locan_month = Convert.strToInt(request("paramMap.locan_month"),0) ;
		//  超出限定期数借款费
		double locan_fee_month = Double.parseDouble(paramMap.get("locan_fee_month")) ;
		//天标借款费
		double day_fee = Convert.strToDouble(request("paramMap.day_fee"),0);
		long result = -1L;
		try {
			String listIn = null;
			String counter = null;
			//转换时间
			long time =DateUtil.getTimeCur("yyyy-MM-dd HH:mm:ss", new Date());
			//得到后台登陆的用户
			  Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				if ("flow".equals(nid)) {
					 listIn = this.getParamMaplistIn();//得到担保机构
					 counter = this.getParamMaplistcounter();//反担保方式
				}
				
				result = shoveBorrowTypeService.addShoveBorrowType(nid, status, name, title, description, account_multiple, password_status,
						amount_type, amount_first, amount_end, 0, apr_first, apr_end, check_first, check_end, tender_account_min,
						tender_account_max, period_month, period_day, validate, award_status, award_scale_first, award_scale_end, award_account_first,
						award_account_end, subscribe_status, verify_auto_status, verify_auto_remark, listStyle, 0, late_days_month, late_days_day,
						0, all_late_scale, all_frost_scale, locan_fee, locan_fee_month, locan_month, day_fee);
				
				if (result > 0) {
					Map<String,String> map = shoveBorrowTypeService.queryShoveBorrowTypeById((int)result);
					String identifier = map.get("nid")+"_"+result+"_"+map.get("version");
					result = shoveBorrowTypeService.updateShoveBorrowType(result, identifier);
					//添加操作日志
					operationLogService.addOperationLog("t_borrow_type", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加标种类型", 2);
					shoveBorrowTypeService.addBorrowTypeLog(nid, status, name, title, description, account_multiple, password_status, amount_type,
							amount_first, amount_end, apr_first, apr_end, check_first, check_end, tender_account_min, tender_account_max, period_month,
							period_day, validate, award_status, award_scale_first, award_scale_end, award_account_first, award_account_end, subscribe_status,
							verify_auto_status, verify_auto_remark, listIn, counter, listStyle, 0, late_days_month, late_days_day, 0, all_late_scale,
							all_frost_scale, admin.getId(), time, admin.getLastIP(), identifier, locan_fee, locan_month, locan_fee_month, day_fee);
				}
				
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		if(result > 0)
			return SUCCESS;
		else
			return INPUT;
	}
	
	/**
	 * 修改初始化
	 * @return
	 */
	public String updateShoveTypeInit() {
		int id=Convert.strToInt(request().getParameter("id"),-1);
		try {
			List<Map<String,Object>>  mapList =shoveBorrowStyleService.queryBorrowAll();
			List<Map<String,Object>>  mapTypeList=shoveBorrowAmountTypeService.queryBorrowAmountAll();
			paramMap = shoveBorrowTypeService.queryShoveBorrowTypeById(id);
			String arr[] = paramMap.get("styles").split(",");
			request().setAttribute("mapList", mapList);
			request().setAttribute("mapTypeList", mapTypeList);
			request().setAttribute("arr",arr);
			if("flow".equals(paramMap.get("nid"))){
				String arrInsti[] = paramMap.get("institution").split(",");  //机构担保
				String arrCounter[] = paramMap.get("counter_guarantee").split(","); //反担保方式
				//得到担保机构
				List<Map<String,Object>> listInstitution = emalAndMessageService.queryinstitution();
				request().setAttribute("listInstitution",listInstitution);
				request().setAttribute("arrInsti",arrInsti);
				//得到反担保方式
				List<Map<String,Object>> listCounter =emalAndMessageService.queryguarantee();
				request().setAttribute("listCounter",listCounter);
				request().setAttribute("arrCounter",arrCounter);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateShoveTypeInfo() {
		//id
		int id = Convert.strToInt(request("paramMap.id"), -1);
		//标题
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.title")),null) ;
		//描述
		String description = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.description")),null) ;
		//状态
		int  status = Convert.strToInt(request("paramMap.status"),-1) ;
		//额度类型
		int  amount_type = Convert.strToInt(request("paramMap_amount_type"),-1) ;
		//最低借款额度
		double amount_first = Convert.strToDouble(request("paramMap.amount_first"),0) ;
		//最高的借款额度
		double amount_end = Convert.strToDouble(request("paramMap.amount_end"),0) ;
		//借款金额倍数
		double account_multiple = Convert.strToDouble(request("paramMap.account_multiple"),0) ;
		//开始年利率
		double apr_first = Convert.strToDouble(request("paramMap.apr_first"),0) ;
		//结束年利率
		double apr_end = Convert.strToDouble(request("paramMap.apr_end"),0) ;
		//借款期限 月
		String period_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.period_month")),null) ;
		//借款期限  天
		String period_day = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.period_day")),null) ;
		//有效期
		String validate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.validate")),null) ;
		//审核最短时间
		int check_first = Convert.strToInt(request("paramMap.check_first"),0) ;
		//审核最长时间
		int check_end = Convert.strToInt(request("paramMap.check_end"),0) ;
		//最低投标金额
		String tender_account_min = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.tender_account_min")),null) ;
		//最高投标金额
		String tender_account_max = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("paramMap.tender_account_max")),null) ;
		//是否启用 奖励
		int award_status = Convert.strToInt(request("paramMap_award_status"),0) ;
		//是否启用投标密码
		int password_status = Convert.strToInt(request("paramMap_password_status"),0) ;
		//奖励比例的最小值
		double award_scale_first = Convert.strToDouble(request("paramMap.award_scale_first"),0) ;
		//奖励比例的最大值
		double award_scale_end = Convert.strToDouble(request("paramMap.award_scale_end"),0) ;
		//不能小于此奖励金额
		double award_account_first = Convert.strToDouble(request("paramMap.award_account_first"),0) ;
		//不能高于此奖励金额
		double award_account_end = Convert.strToDouble(request("paramMap.award_account_end"),0) ;
		//初审自动通过
		//int  verify_auto_status = Convert.strToInt(request("paramMap_verify_auto_status"),0) ;
		//初审自动通过的备注
		//String verify_auto_remark = Convert.strToStr(request("paramMap.verify_auto_remark"),null) ;
		//VIP冻结保证金
		//double vip_frost_scale = Convert.strToDouble(request("paramMap.vip_frost_scale"),0) ;
		//会员冻结保证金
		double all_frost_scale = Convert.strToDouble(request("paramMap.all_frost_scale"),0) ;
		//垫付逾期的天数  月标
		int late_days_month = Convert.strToInt(request().getParameter("paramMap.late_days_month"),0) ;
		//垫付逾期的天数  天标
		int late_days_day = Convert.strToInt(request("paramMap.late_days_day"),0) ;
		//vip会员垫付本金比例：
		//double vip_late_scale = Convert.strToDouble(request("paramMap.vip_late_scale"),0) ;
		//会员垫付本金比例
		double all_late_scale = Convert.strToDouble(request("paramMap.all_late_scale"),0) ;
		//还款方式
		String listStyle  = this.getParamMapStyles();
		//是否开启认购模式
		int subscribe_status = Convert.strToInt(request("paramMap_subscribe_status"),0) ;
		//借款费  
		double locan_fee = Double.parseDouble(paramMap.get("locan_fee"));
		//  超出限定期数  
		int locan_month = Convert.strToInt(request("paramMap.locan_month"),0) ;
		//  超出限定期数借款费
		double locan_fee_month = Double.parseDouble(paramMap.get("locan_fee_month")) ;
		//天标借款费
		double day_fee = Convert.strToDouble(request("paramMap.day_fee"),0);
		long result = -1L;
		Map<String,String> map = null;
		String name = null;
		int version= 0;
		String nid= null;
		try {
			map = shoveBorrowTypeService.queryShoveBorrowTypeById(id);
			name = Convert.strToStr(map.get("name"), null);
			version = Convert.strToInt(map.get("version"),-1)+1;
			nid = Convert.strToStr(map.get("nid"), null);
			String listIn = null;
			String counter = null;
			//转换时间
			long time = DateUtil.getTimeCur("yyyy-MM-dd HH:mm:ss", new Date());
			//得到后台登陆的用户
			  Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
				if ("flow".equals(nid)) {
					 listIn = this.getParamMaplistIn();//得到担保机构
					 counter = this.getParamMaplistcounter();//反担保方式
				}
				
				String identifier = nid+"_"+id+"_"+version;
				result = shoveBorrowTypeService.updateShoveBorrowType(id, status, title, description, account_multiple,
						password_status, amount_type, amount_first, amount_end,  apr_first, apr_end, check_first, check_end,
						tender_account_min, tender_account_max, period_month, period_day, validate, award_status, award_scale_first,
						award_scale_end, award_account_first, award_account_end, subscribe_status, listIn, counter, listStyle,
						0, late_days_month, late_days_day, 0, all_late_scale, all_frost_scale,version,identifier,locan_fee,locan_month,locan_fee_month,day_fee);
				
				if (result > 0) {
					//添加操作日志
					operationLogService.addOperationLog("t_borrow_type", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "修改标种类型", 2);
				}
				result = shoveBorrowTypeService.addBorrowTypeLog(nid, status, name, title, description, account_multiple,
						password_status, amount_type, amount_first, amount_end,  apr_first, apr_end, check_first, check_end,
						tender_account_min, tender_account_max, period_month, period_day, validate, award_status, award_scale_first,
						award_scale_end, award_account_first, award_account_end, subscribe_status, 0, null, listIn, counter, listStyle, 0,
						late_days_month, late_days_day, 0, all_late_scale, all_frost_scale, admin.getId(), time, admin.getLastIP(),identifier,
						locan_fee,locan_month,locan_fee_month,day_fee);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error(e);
			e.printStackTrace();
		}
		if(result > 0 )
			return SUCCESS;
		else
			return INPUT;
	}
	
	//判断字符串是不是数字
	public static boolean isNum(String str){		
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	/**
	 *  标种类型修改时验证
	 * @return
	 * @throws IOException 
	 */
	public String updateBorrowType() throws IOException{
		String[] arr=null;
		//标题
		String title = Convert.strToStr( SqlInfusion.FilteSqlInfusion(request().getParameter("tb_title")),"");
		if (StringUtils.isBlank(title)) {
			JSONUtils.printStr("1");
			return null;
		}
	
		//最低借款额度
		String amount_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_amount_first")), "");
		if (StringUtils.isBlank(amount_first)) {
			JSONUtils.printStr("2");
			return null;
		}
		if(isNum(amount_first)==false || Double.parseDouble(amount_first)<0){
			JSONUtils.printStr("3");
			return null;
		}
		//最高借款额度
		String amount_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_amount_end")), "");
		if (StringUtils.isBlank(amount_end)) {
			JSONUtils.printStr("4");
			return null;
		}
		if(isNum(amount_end)==false || Double.parseDouble(amount_end)<0){
			JSONUtils.printStr("5");
			return null;
		}
		if(Double.parseDouble(amount_first)>Double.parseDouble(amount_end)){
			JSONUtils.printStr("6");
			return null;
		}
		//额度倍数
		String account_multiple = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_account_multiple")), "");
		if(StringUtils.isBlank(account_multiple)){
			JSONUtils.printStr("7");
			return null;
		}
		if(isNum(account_multiple)==false || Double.parseDouble(account_multiple)<0){
			JSONUtils.printStr("8");
			return null;
		}
		//最低年利率
		String apr_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_apr_first")), "");
		if(StringUtils.isBlank(apr_first)){
			JSONUtils.printStr("9");
			return null;
		}
		if(isNum(apr_first)==false || Double.parseDouble(apr_first)<0){
			JSONUtils.printStr("10");
			return null;
		}
		//最高年利率
		String apr_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_apr_end")), "");
		if(StringUtils.isBlank(apr_end)){
			JSONUtils.printStr("11");
			return null;
		}
		if(isNum(apr_end)==false){
			JSONUtils.printStr("12");
			return null;
		}
		if(Double.parseDouble(apr_first)>Double.parseDouble(apr_end)){
			JSONUtils.printStr("13");
			return null;
		}
		if(Double.parseDouble(apr_first)<0.01 ||Double.parseDouble(apr_end)>100){
			JSONUtils.printStr("14");
			return null;
		}
		//借款期限 月标
		String period_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_period_month")), "");
		if(StringUtils.isBlank(period_month)){
			JSONUtils.printStr("15");
			return null;
		}
		arr = period_month.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("16");
				return null;
			}
			if (arr[i].indexOf(".") != -1){ //判断是不是整数
				 JSONUtils.printStr("16");
					return null;
			 }
			if(Integer.parseInt(arr[i])<0  || Integer.parseInt(arr[i])>60 )	{
				JSONUtils.printStr("16");
				return null;
			}
		}
		//借款期限  天标
		String period_day = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_period_day")), "");
		if(StringUtils.isBlank(period_day)){
			JSONUtils.printStr("17");
			return null;
		}
		 arr = period_day.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("18");
				return null;
			}
			if (arr[i].indexOf(".") != -1){ //判断是不是整数
				 JSONUtils.printStr("18");
					return null;
			 }
			if(Integer.parseInt(arr[i])<0  || Integer.parseInt(arr[i])>25 )	{
				JSONUtils.printStr("18");
				return null;
			}
		}
		//有效期
		String validate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_validate")), "");
		if(StringUtils.isBlank(validate)){
			JSONUtils.printStr("19");
			return null;
		}
		arr = validate.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("20");
				return null;
			}
			if (arr[i].indexOf(".") != -1){ //判断是不是整数
				 JSONUtils.printStr("20");
					return null;
			 }
		}
		//审核最短时间
		String check_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_check_first")), "");
		if(StringUtils.isBlank(check_first)){
			JSONUtils.printStr("21");
			return null;
		}if(isNum(check_first)==false || Double.parseDouble(check_first)<0){
			JSONUtils.printStr("22");
			return null;
		} if (check_first.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("22");
				return null;
		 }
		
		//审核最长时间
		String check_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_check_end")), "");
		if(StringUtils.isBlank(check_end)){
			JSONUtils.printStr("23");
			return null;
		}if(isNum(check_end)==false || Double.parseDouble(check_end)<0){
			JSONUtils.printStr("24");
			return null;
		}if(Double.parseDouble(check_first)>Double.parseDouble(check_end)){
			JSONUtils.printStr("25");
			return null;
		}if (check_end.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("24");
				return null;
		 }
		/*if(Double.parseDouble(check_first)>31 || Double.parseDouble(check_end)>31){
			JSONUtils.printStr("26");
			return null;
		}*/
		//最低投标金额
		String tender_account_min = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_tender_account_min")), "");
		if(StringUtils.isBlank(tender_account_min)){
			JSONUtils.printStr("27");
			return null;
		}
		//最高投标金额
		String account_max = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_account_max")), "");
		if(StringUtils.isBlank(account_max)){
			JSONUtils.printStr("28");
			return null;
		}
		arr = tender_account_min.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("29");
				return null;
			}
			if(Double.parseDouble(arr[i])<0 )	{
				JSONUtils.printStr("29");
				return null;
			}
		}
		arr = account_max.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(isNum(arr[i])==false){
				JSONUtils.printStr("30");
				return null;
			}
			if(Double.parseDouble(arr[i])<0 )	{
				JSONUtils.printStr("30");
				return null;
			}
		}
		//奖励比例最小值
		String award_scale_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_scale_first")), "");
		if(StringUtils.isBlank(award_scale_first)){
			JSONUtils.printStr("31");
			return null;
		}
		//奖励比例最大值
		String award_scale_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_scale_end")), "");
		if(StringUtils.isBlank(award_scale_end)){
			JSONUtils.printStr("32");
			return null;
		}
		if(isNum(award_scale_first)==false ||isNum(award_scale_end)==false || Double.parseDouble(award_scale_first) <0 ||Double.parseDouble(award_scale_end) <0){
			JSONUtils.printStr("33");
			return null;
		}
		if(Double.parseDouble(award_scale_first)>Double.parseDouble(award_scale_end)){
			JSONUtils.printStr("34");
			return null;
		}if(Double.parseDouble(award_scale_first)<0 || Double.parseDouble(award_scale_end)>100){
			JSONUtils.printStr("35");
			return null;
		}
		//奖励固定金额最小值
		String award_account_first = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_account_first")), "");
		if(StringUtils.isBlank(award_account_first)){
			JSONUtils.printStr("36");
			return null;
		}
		//奖励固定金额最大值
		String award_account_end = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_award_account_end")), "");
		if(StringUtils.isBlank(award_account_end)){
			JSONUtils.printStr("37");
			return null;
		}
		if(isNum(award_account_first)==false ||isNum(award_account_end)==false || Double.parseDouble(award_account_first) <0 ||Double.parseDouble(award_account_end) <0){
			JSONUtils.printStr("38");
			return null;
		}
		if(Double.parseDouble(award_account_first)>Double.parseDouble(award_account_end)){
			JSONUtils.printStr("39");
			return null;
		}
		/*//初审自动通过备注
		String verify_auto_remark = Convert.strToStr(request().getParameter("tb_verify_auto_remark"), "");
		if(StringUtils.isBlank(verify_auto_remark)){
			JSONUtils.printStr("40");
			return null;
		}*/
		//Vip冻结保证金
		/*String vip_frost_scale = Convert.strToStr(request().getParameter("tb_vip_frost_scale"), "");
		if(StringUtils.isBlank(vip_frost_scale)){
			JSONUtils.printStr("41");
			return null;
		}
		if(isNum(vip_frost_scale)==false){
			JSONUtils.printStr("42");
			return null;
		}
		if(Double.parseDouble(vip_frost_scale)<0 || Double.parseDouble(vip_frost_scale)>100){
			JSONUtils.printStr("43");
			return null;
		}*/
		
		//会员冻结保证金
		String all_frost_scale = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_all_frost_scale")), "");
		if(StringUtils.isBlank(all_frost_scale)){
			JSONUtils.printStr("44");
			return null;
		}
		if(isNum(all_frost_scale)==false){
			JSONUtils.printStr("45");
			return null;
		}
		if(Double.parseDouble(all_frost_scale)<0 || Double.parseDouble(all_frost_scale)>100){
			JSONUtils.printStr("46");
			return null;
		}
		//垫付逾期天数 (月标)
		String late_days_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_late_days_month")), "");
		if(StringUtils.isBlank(late_days_month)){
			JSONUtils.printStr("47");
			return null;
		}
		if(Double.parseDouble(late_days_month)<0  ||isNum(late_days_month)==false){
			JSONUtils.printStr("48");
			return null;
		}
		 if (late_days_month.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("48");
				return null;
		 }
		//垫付逾期天数  (天标)
		String late_days_day = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_late_days_day")), "");
		if(StringUtils.isBlank(late_days_day)){
			JSONUtils.printStr("49");
			return null;
		}
		if(Double.parseDouble(late_days_day)<0 || isNum(late_days_day)==false){
			JSONUtils.printStr("50");
			return null;
		}
		 if (late_days_day.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("50");
				return null;
		 }
		//vip垫付利息比例
		/*String vip_late_scale = Convert.strToStr(request().getParameter("tb_vip_late_scale"), "");
		if(StringUtils.isBlank(vip_late_scale)){
			JSONUtils.printStr("51");
			return null;
		}
		if(Double.parseDouble(vip_late_scale)<0 ||Double.parseDouble(vip_late_scale) >100 || isNum(vip_late_scale)==false){
			JSONUtils.printStr("52");
			return null;
		}*/
		//会员垫付利息比例
		String all_late_scale = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("tb_all_late_scale")), "");
		if(StringUtils.isBlank(all_late_scale)){
			JSONUtils.printStr("53");
			return null;
		}
		if(Double.parseDouble(all_late_scale)<0 ||Double.parseDouble(all_late_scale) >100 || isNum(all_late_scale)==false){
			JSONUtils.printStr("54");
			return null;
		}
		if("flow".equals(Convert.strToStr(request().getParameter("nid"),""))){
			//担保机构
			String paramMapInstitution = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMapInstitution")), "");
			if(StringUtils.isBlank(paramMapInstitution)){
				JSONUtils.printStr("55");
				return null;
			}	
			//反担保方式
			String paramMapcounter = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMapcounter")), "");
			if(StringUtils.isBlank(paramMapcounter)){
				JSONUtils.printStr("56");
				return null;
			}
		}
		//还款方式 
		String paramMapStyles = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request().getParameter("paramMapStyles")), "");
		if(StringUtils.isBlank(paramMapStyles)){
			JSONUtils.printStr("57");
			return null;
		}
		//月标借款费
		String  locan_fee = Convert.strToStr(request("tb_locan_fee"),"");//借款费 
		if (StringUtils.isBlank(locan_fee)) {
			JSONUtils.printStr("58");
			return null;
		}
		if( isNum(locan_fee)==false ||  Double.parseDouble(locan_fee)<0 ||Double.parseDouble(locan_fee) >100){
			JSONUtils.printStr("59");
			return null;
		}
		
		String  locan_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("tb_locan_month")),"");//借款限定期数
		if (StringUtils.isBlank(locan_month)) {
			JSONUtils.printStr("60");
			return null;
		}
		if(Double.parseDouble(locan_month)<0 ||  isNum(locan_month)==false ||  Double.parseDouble(locan_month) >100){
			JSONUtils.printStr("61");
			return null;
		}
		 if (locan_month.indexOf(".") != -1){ //判断是不是整数
			 JSONUtils.printStr("61");
				return null;
		 }
		//超出借款限定期数借款费
		String  locan_fee_month = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("tb_locan_fee_month")),"");//超出借款限定期数借款费
		if (StringUtils.isBlank(locan_fee_month)) {
			JSONUtils.printStr("62");
			return null;
		}
		if( isNum(locan_fee_month)==false || Double.parseDouble(locan_fee_month)<0 || Double.parseDouble(locan_fee_month) >100 ){
			JSONUtils.printStr("63");
			return null;
		}
		//天标借款费
		String day_fee = Convert.strToStr(request("tb_day_fee"),"");	//天标借款费
		if (StringUtils.isBlank(day_fee)) {
			JSONUtils.printStr("64");
			return null;
		}
		if( isNum(day_fee)==false || Double.parseDouble(day_fee)<0 ||Double.parseDouble(day_fee) >100 ){
			JSONUtils.printStr("65");
			return null;
		}
		
		else{
			JSONUtils.printStr("66");
			return null;
		}
	} 
	
	/**
	 * 根据ID删除标种类型
	 * @param strId
	 * @return
	 */
	public String deleteShoveType() {
		long result = -1L;
		int id = Convert.strToInt(request().getParameter("id"),-1);
		if(id != -1){
			try {
				result = shoveBorrowTypeService.deleteShoveBorrowType(id);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
			
		if(result > 0 )
			return SUCCESS;
		else
			return INPUT;
	}
	
	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setShoveBorrowStyleService(
			ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public void setShoveBorrowAmountTypeService(
			ShoveBorrowAmountTypeService shoveBorrowAmountTypeService) {
		this.shoveBorrowAmountTypeService = shoveBorrowAmountTypeService;
	}

	public String getParamMapStyles() {
		return paramMapStyles;
	}

	public void setParamMapStyles(String paramMapStyles) {
		this.paramMapStyles = paramMapStyles;
	}

	public void setEmalAndMessageService(EmalAndMessageService emalAndMessageService) {
		this.emalAndMessageService = emalAndMessageService;
	}

	public String getParamMaplistcounter() {
		return paramMaplistcounter;
	}

	public void setParamMaplistcounter(String paramMaplistcounter) {
		this.paramMaplistcounter = paramMaplistcounter;
	}

	public String getParamMaplistIn() {
		return paramMaplistIn;
	}

	public void setParamMaplistIn(String paramMaplistIn) {
		this.paramMaplistIn = paramMaplistIn;
	}

	public ShoveBorrowTypeService getShoveBorrowTypeService() {
		return shoveBorrowTypeService;
	}

	public ShoveBorrowStyleService getShoveBorrowStyleService() {
		return shoveBorrowStyleService;
	}

	public ShoveBorrowAmountTypeService getShoveBorrowAmountTypeService() {
		return shoveBorrowAmountTypeService;
	}

	public EmalAndMessageService getEmalAndMessageService() {
		return emalAndMessageService;
	}
}
