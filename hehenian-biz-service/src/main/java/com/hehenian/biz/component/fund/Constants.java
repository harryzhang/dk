package com.hehenian.biz.component.fund;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by lenovo on 2014/12/3.
 */
public class Constants {

    public static final Map<Byte, List<String>> industryMap = new HashMap<Byte,List<String>>();
    static {
        industryMap.put((byte)0, Arrays.asList(new String[]{"计算机","互联网","软件"}));
        industryMap.put((byte)1, Arrays.asList(new String[]{"通信","电子"}));
        industryMap.put((byte)2, Arrays.asList(new String[]{"金融","法律","会计","保险"}));
        industryMap.put((byte)3, Arrays.asList(new String[]{"政府机关"}));
        industryMap.put((byte)4, Arrays.asList(new String[]{"公共事业"}));
        industryMap.put((byte)5, Arrays.asList(new String[]{"教育","培训"}));
        industryMap.put((byte)6, Arrays.asList(new String[]{"媒体","广告"}));
        industryMap.put((byte)7, Arrays.asList(new String[]{"零售","批发"}));
        industryMap.put((byte)8, Arrays.asList(new String[]{"餐饮","酒店","旅馆"}));
        industryMap.put((byte)9, Arrays.asList(new String[]{"交通运输"}));
        industryMap.put((byte)10, Arrays.asList(new String[]{"房地产业"}));
        industryMap.put((byte)11, Arrays.asList(new String[]{"能源业"}));
        industryMap.put((byte)12, Arrays.asList(new String[]{"医疗","制药","卫生","保健"}));
        industryMap.put((byte)13, Arrays.asList(new String[]{"建筑工程"}));
        industryMap.put((byte)14, Arrays.asList(new String[]{"娱乐服务业"}));
        industryMap.put((byte)15, Arrays.asList(new String[]{"体育","艺术"}));
        industryMap.put((byte)16, Arrays.asList(new String[]{"农业"}));
        industryMap.put((byte)17, Arrays.asList(new String[]{"公益组织"}));
        industryMap.put((byte)18, Arrays.asList(new String[]{"其他"}));
    }

    //月收入(0-3000元以下 1-3001到5000元 2-5001到10000元 3-10001到20000元 4-20001到50000元 5-50000以上)
    public static Byte getIncomeCode(String income){
        Double icon = Double.valueOf(income);
        if(3000<icon && icon<=5000){
            return 1;
        }else if(5000<icon && icon<=10000){
            return 2;
        }else if(10000<icon && icon<=20000){
            return 3;
        }else if(20000<icon && icon<=50000){
            return 4;
        }else if(icon>50000){
            return 5;
        }else {
            return 0;
        }
    }

    //月收入转换为收入区间
    public static String getIncomePeroid(Byte b){
        String incomeStr="";
        switch (b){
            case 0:
                incomeStr = "3000元以下";
                break;
            case 1:
                incomeStr = "3001到5000元";
                break;
            case 2:
                incomeStr = "5001到10000元";
                break;
            case 3:
                incomeStr = "10001到20000元";
                break;
            case 4:
                incomeStr = "20001到50000元";
                break;
            case 5:
                incomeStr = "50000以上";
                break;
        }
        return incomeStr;
    }
    //工作级别(0:普通员工 1:管理人员 2.股东 3.私营业主4.科级 5.处级 6.厅局级)
    public static Byte getPositionCode(String position){
        if("管理人员".equals(position)){
            return 1;
        }else if ("股东".equals(position)){
            return 2;
        }else if ("私营业主".equals(position)){
            return 3;
        }else if ("科级".equals(position)){
            return 4;
        }else if("处级".equals(position)){
            return 5;
        }else if ("厅局级".equals(position)){
            return 6;
        }else {
            return 0;
        }
    }
    //根据级别返回岗位
    public static String getPositionName(Byte b){
        String positionName = "";
        switch (b){
            case 0:
                positionName = "普通员工";
                break;
            case 1:
                positionName = "管理人员";
                break;
            case 2:
                positionName = "股东";
                break;
            case 3:
                positionName = "私营业主";
                break;
            case 4:
                positionName = "科级";
                break;
            case 5:
                positionName = "处级";
                break;
            case 6:
                positionName = "厅局级";
                break;
        }
        return positionName;
    }
    //借款用途（0-资金周转1-扩大经营2-房屋装修3-助学/培训4-旅游/婚庆5-耐用品消费6-其他消费）
    public static Byte getLoanUseageCode(String loanStr){
        if("资金周转".equals(loanStr.trim())){
            return 0;
        }else if("扩大经营".equals(loanStr.trim())){
            return 1;
        }else if ("房屋装修".equals(loanStr.trim())){
            return 2;
        }else if("助学/培训".equals(loanStr.trim())){
            return 3;
        }else if ("旅游/婚庆".equals(loanStr.trim())){
            return 4;
        }else if ("耐用品消费".equals(loanStr.trim())){
            return 5;
        }else{
            return 6;
        }
    }
    //根据借款代码返回借款类型
    public static String getLoanName(Byte b){
        String loanStr="";
        switch (b){
            case 0:
                loanStr = "资金周转";
                break;
            case 1:
                loanStr = "扩大经营";
                break;
            case 2:
                loanStr = "房屋装修";
                break;
            case 3:
                loanStr = "助学/培训";
                break;
            case 4:
                loanStr = "旅游/婚庆";
                break;
            case 5:
                loanStr = "耐用品消费";
                break;
            case 6:
                loanStr = "其他消费";
                break;
        }
        return loanStr;
    }
    //借款人类型(0-个人借款1-企业借款2-车易贷 3-房易贷 4-担保)
    public static Byte getBorrowerTypeCode(String borrwoer){
        if("个人借款".equals(borrwoer)){
            return 0;
        }else if("企业借款".equals(borrwoer)){
            return 1;
        }else if("车易贷".equals(borrwoer)){
            return 2;
        }else if ("房易贷".equals(borrwoer)){
            return 3;
        }else if ("担保".equals(borrwoer)){
            return 4;
        }else {
            return null;
        }
    }
    //根据借款代码返回借款类型
    public static String getBorrowerName(Byte b){
        String borrowerStr = "";
        switch (b){
            case 0:
                borrowerStr = "个人借款";
                break;
            case 1:
                borrowerStr = "企业借款";
                break;
            case 2:
                borrowerStr = "车易贷";
                break;
            case 3:
                borrowerStr = "房易贷";
                break;
            case 4:
                borrowerStr = "担保";
                break;
        }
        return borrowerStr;
    }
    //居住类型（0-自由房屋1-个人租房3-单位宿舍4-寄宿亲友）
    public static Byte getResidenceCode(String residence){
        if("自由房屋".equals(residence) || "买房".equals(residence)){
            return 0;
        }else if("个人租房".equals(residence) || "租房".equals(residence)){
            return 1;
        }else if("单位宿舍".equals(residence)){
            return 3;
        }else if ("寄宿亲友".equals(residence)){
            return 4;
        }else {
            return null;
        }
    }
    //根据居住编码返回居住类型
    public static String getResidenceName(Byte b){
        String residenceName = "";
        switch (b){
            case 0:
                residenceName = "自由房屋";
                break;
            case 1:
                residenceName = "个人租房";
                break;
            case 2:
                residenceName = "";
                break;
            case 3:
                residenceName = "单位宿舍";
                break;
            case 4:
                residenceName = "寄宿亲友";
                break;
        }
        return residenceName;
    }
    //公司类别(0-私营企业1-个体经营者2-国营企业3-外贸企业4-合资企业5-政府机关6-事业单位7-其他)
    public static Byte getCompanyType(String company){
        if("私营企业".equals(company)){
            return 0;
        }else if("个体经营者".equals(company)){
            return 1;
        }else if("国营企业".equals(company)){
            return 2;
        }else if ("外贸企业".equals(company)){
            return 3;
        }else if("合资企业".equals(company)){
            return 4;
        }else if ("政府机关".equals(company)){
            return 5;
        }else if ("事业单位".equals(company)){
            return 6;
        }else {
            return 7;
        }
    }
    //根据公司类型代码返回公司类型
    public static String getCompanyType(Byte b){
        String companyType = "";
        switch (b){
            case 0:
                companyType = "私营企业";
                break;
            case 1:
                companyType = "个体经营者";
                break;
            case 2:
                companyType = "国营企业";
                break;
            case 3:
                companyType = "外贸企业";
                break;
            case 4:
                companyType = "合资企业";
                break;
            case 5:
                companyType = "政府机关";
                break;
            case 6:
                companyType = "事业单位";
                break;
            case 7:
                companyType = "其他";
                break;
        }
        return companyType;
    }
    //公司规模（0-10人以下  1-10到100人 2-100到500人 3-500到2000人  4-2000人以上）
    public static Byte getCompanyScale(String scale){
        if("10人以下".equals(scale)){
            return 0;
        }else if ("10到100人".equals(scale)){
            return 1;
        }else if ("100到500人".equals(scale)){
            return 2;
        }else if ("500到2000人".equals(scale)){
            return 3;
        }else if ("2000人以上".equals(scale)){
            return 4;
        }else {
            return null;
        }
    }
    //根据公司规模编码返回公司规模
    public static String getCompanyScaleName(Byte b){
        String scaleName = "";
        switch (b){
            case 0:
                scaleName = "10人以下";
                break;
            case 1:
                scaleName = "10到100人";
                break;
            case 2:
                scaleName = "100到500人";
                break;
            case 3:
                scaleName = "500到2000人";
                break;
            case 4:
                scaleName = "2000人以上";
                break;
        }
        return scaleName;
    }
    //公司经营年限（0-1年以下1-1到3年  2-3到5年  3-5到10年 4-10年以上）
    public static Byte getOperateYearCode(String operateYear){
        if("1年以下".equals(operateYear)){
            return 0;
        }else if ("1到3年".equals(operateYear)){
            return 1;
        }else if ("3到5年".equals(operateYear)){
            return 2;
        }else if ("5到10年".equals(operateYear)){
            return 3;
        }else if ("10年以上".equals(operateYear)){
            return 4;
        }else {
            return null;
        }
    }
    //根据公司经营年限代码返回公司经营年限
    public static String getOperateYearName(Byte b){
        String yearName = "";
        switch (b){
            case 0:
                yearName = "1年以下";
                break;
            case 1:
                yearName = "1到3年";
                break;
            case 2:
                yearName = "3到5年";
                break;
            case 3:
                yearName = "5到10年";
                break;
            case 4:
                yearName = "10年以上";
                break;
        }
        return yearName;
    }
    //婚姻状况(0-未婚1-已婚 2.离异 3.丧偶)
    public static Byte getMarriedCode(String marryStr){
        if ("已婚".equals(marryStr.trim())){
            return 1;
        }else if("离异".equals(marryStr.trim())){
            return 2;
        }else if("丧偶".equals(marryStr.trim())){
            return 3;
        }else {
            return 0;
        }
    }
    public static String getMarriedName(Byte b){
        String marriedStr = "";
        switch (b){
            case 0:
                marriedStr = "未婚";
                break;
            case 1:
                marriedStr = "已婚";
                break;
            case 2:
                marriedStr = "离异";
                break;
            case 3:
                marriedStr = "丧偶";
                break;
        }
        return marriedStr;
    }

    //学历对应编码（0-初中及以下1-高中或中专2-大专3-本科4-研究生5-博士及以上）
    public static Byte getGradeCode(String gradeStr){
        if ("高中或中专".equals(gradeStr.trim())){
            return 1;
        }else if("大专".equals(gradeStr.trim())){
            return 2;
        }else if ("本科".equals(gradeStr.trim())){
            return 3;
        }else if("研究生".equals(gradeStr.trim())){
            return 4;
        }else if("博士及以上".equals(gradeStr.trim())){
            return 5;
        }else{
            return 0;
        }
    }
    public static String getGradeName(Byte b){
        String gradeName = "";
        switch (b){
            case 0:
                gradeName = "初中及以下";
                break;
            case 1:
                gradeName = "高中或中专";
                break;
            case 2:
                gradeName = "大专";
                break;
            case 3:
                gradeName = "本科";
                break;
            case 4:
                gradeName = "研究生";
                break;
            case 5:
                gradeName = "博士及以上";
                break;
        }
        return gradeName;
    }

    
    //还款方式（1-等本等息,2-一次付息到期还本3-按月付息到期还本4-等额本息5-等本等息（集团贷））
    public static Byte getRepayTypeCode(String name){
        if ("等本等息".equals(name)){
            return 1;
        }else if("一次付息到期还本".equals(name)){
            return 2;
        }else if("按月付息到期还本".equals(name)){
            return 3;
        }else if("等额本息".equals(name)){
            return 4;
        }else if("等本等息（集团贷）".equals(name)){
            return 5;
        }else if("E贷款等本等息".equals(name)){
            return 101;
        }else if("E贷款等额本金".equals(name)){
	        return 102;
	    }else if("E贷款等额本息".equals(name)){
	    	return 103;
	    }else{
            return null;
        }
    }
    public static String getRepayTypeName(Byte b){
        String repayName = "";
        switch (b) {
            case 1:
                repayName = "等本等息";
                break;
            case 2:
                repayName = "一次付息到期还本";
                break;
            case 3:
                repayName = "按月付息到期还本";
                break;
            case 4:
                repayName = "等额本息";
                break;
            case 5:
                repayName = "等本等息（集团贷）";
                break;
            case 101:
            	repayName="E贷款等本等息";
            	break;
            case 102:
            	repayName="E贷款等额本金";
            	break;
            case 103:
            	repayName="E贷款等额本息";
            	break;
        }
        return repayName;
    }

    public static String getHidePhone(String phone){
        StringBuffer sb = new StringBuffer();
        if (phone!=null && !"".equals(phone) && phone.length()>=11){
            sb.append(phone.substring(0,3));
            sb.append("****");
            sb.append(phone.substring(7, phone.length()));
        }
        return sb.toString();
    }

    public static String getHideBandNo(String bankNo){
        StringBuffer sb = new StringBuffer();
        if (bankNo!=null && !"".equals(bankNo)){
            sb.append(bankNo.substring(0,4));
            int flag = bankNo.length()-8;
            for(int i=0;i<flag;i++){
                sb.append("*");
            }
            sb.append(bankNo.substring((flag+4), bankNo.length()));
        }
        return sb.toString();
    }

    public static String getHide2BandNo(String bankNo){
        StringBuffer sb = new StringBuffer();
        if (bankNo!=null && !"".equals(bankNo)){
            sb.append(bankNo.substring(0,4));
            int flag = bankNo.length();
            sb.append("**");
            sb.append(bankNo.substring((flag-4), bankNo.length()));
        }
        return sb.toString();
    }

    public static String getHideIdNo(String idNo){
        StringBuffer sb = new StringBuffer();
        if (idNo!=null && !"".equals(idNo)){
            sb.append(idNo.replace(idNo.subSequence(6, idNo.length() - 4),"********"));
        }
        return sb.toString();
    }
    
    //先显示后四位
    public static String getCardNo(String cardNo){
    	String  subString=cardNo.substring(cardNo.length()-4,cardNo.length());
    	return subString;
    }
    
    //先显示后四位
    public static String getLastFourCardNo(String cardNo){
    	if(StringUtils.isNotBlank(cardNo) && cardNo.length() >=4) {
    		return cardNo.substring(cardNo.length()-4,cardNo.length());
    	}
    	return cardNo;
    }

    public static String getBankImg(String bankCode){
        StringBuffer sb = new StringBuffer();
        if (bankCode!=null && StringUtils.isNotEmpty(bankCode)) {
            if ("0104".equals(bankCode)) {
                sb.append("zgyh");
            } else if ("0103".equals(bankCode)) {
                sb.append("nyyh");
            } else if ("0105".equals(bankCode)) {
                sb.append("jsyh");
            } else if ("0301".equals(bankCode)) {
                sb.append("jtyh");
            } else if ("0308".equals(bankCode)) {
                sb.append("zsyh");
            } else if ("0403".equals(bankCode)) {
                sb.append("ycyh");
            } else if ("0309".equals(bankCode)) {
                sb.append("xyyh");
            } else if ("0303".equals(bankCode)) {
                sb.append("gdyh");
            } else if ("0302".equals(bankCode)) {
                sb.append("zxyh");
            } else if ("0304".equals(bankCode)) {
                sb.append("hxyh");
            } else if ("0305".equals(bankCode)) {
                sb.append("msyh");
            } else if ("0307".equals(bankCode)) {
                sb.append("payh");
            } else if ("0310".equals(bankCode)) {
                sb.append("pfyh");
            }
        }
        return sb.toString();
    }
}
