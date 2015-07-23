/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.fee
 * @Title: FeeUtil.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年9月24日 下午4:51:17
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.constant.Constants;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年9月24日 下午4:51:17
 */
public class FeeUtil {
    
    /**
     * 需还款给投资人的罚息 = 罚息*0.5
     * @param repayLateFI 还款延期的罚金
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午9:56:37
     */
    public static double getFI( double repayLateFI ){
        return repayLateFI * 0.5;
    }
    
    
    /**
     * 创建手续费和咨询费等金额分账字符格式 2.0接口格式
     * 
     * @param feeArray[][] 二维数组， 一个账户的信息包按： 第一个参数： 金额， 第二参数：汇付账号， 第三个参数： 汇付账号的子账户
     *            手续费
     * @param consultFee
     *            咨询费
     * @param feeAccount 费用账户
     * @param consultFeeAccount 咨询费账户         
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月28日上午9:29:11
     */
    public static String buildFee(Object[][] feeArray) {
        
		if (null == feeArray || feeArray.length < 1) {
			return "";
		}
        
        
		List<Map<String, Object>> jarray = new ArrayList<Map<String, Object>>();
        for(Object[] fee : feeArray){
            double  feeVal = (Double)fee[0];
            if(feeVal > 0 ){
				Map<String, Object> jconsultFee = new HashMap<String, Object>();
                jconsultFee.put("DivCustId", fee[1]);
                jconsultFee.put("DivAcctId", fee[2]);
                jconsultFee.put("DivAmt", NumberUtil.formatDouble(feeVal));
                jarray.add(jconsultFee);
            }
        }       
		try {
			return JsonUtil.toString(jarray);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

    }

    /**
     * 查询借款信息得到借款时插入的平台收费标准,解析json
     * 
     * @param feelogJsonstr
     *            来自标的feelogJsonstr, 创建的时候保存成json格式了
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月23日下午7:32:53
     */
    public static double parseBorrowFeelog(String feelogJsonstr) {
        if (null == feelogJsonstr || "".equals(feelogJsonstr)) {
            return 0;
        }
        Map<String, Double> feeMap = (Map<String, Double>) JsonUtil.json2Bean(feelogJsonstr, HashMap.class);
        return NumberUtil.strToDouble(feeMap.get(Constants.INVEST_FEE_RATE) + "", 0);

    }
    
    /**
     * 按比例分摊
     * @param baseAmount  基数
     * @param numeratorVal 分子double[]数组
     * @param denominatorVal 分母数
     * @return  分摊后的double数组
     * @author: zhangyunhmf
     * @date: 2014年9月29日上午10:35:19
     */
    public static double[] splitByRate(double baseAmount , double[] numeratorVal, double denominatorVal ){
        double[] resultVal = new double[numeratorVal.length];
        
        if(0==baseAmount){return resultVal;}
        
        double totalFeeSum = 0; //分摊的时候累计手续费
        for(int i  = 0 , size = numeratorVal.length; i < size ; i++){
            double fee = 0 ;
            // 分摊费用(投资人应收总额/借款人应还款总额)* 总费用
            if(i == size -1){
                fee = CalculateUtils.round((baseAmount - totalFeeSum),2);
            }else{
                fee = CalculateUtils.round(CalculateUtils.splitByRate(baseAmount, numeratorVal[i], denominatorVal),2);
                totalFeeSum = CalculateUtils.add(totalFeeSum , fee);
            }
            resultVal[i]=fee;
        }
        return resultVal;
    }

}
