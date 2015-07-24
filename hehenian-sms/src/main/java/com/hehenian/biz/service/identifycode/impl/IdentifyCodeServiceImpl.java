package com.hehenian.biz.service.identifycode.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import com.hehenian.biz.common.bank.IBankBingService;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.NotifyBusinessType;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.SMSNotifyDo;
import com.hehenian.common.redis.SpringRedisCacheService;

@Component("identifyCodeService")
public class IdentifyCodeServiceImpl implements IIdentifyCodeService {

    private final Logger     logger = Logger.getLogger(this.getClass());

    @Autowired
    private INotifyService   notifyService;

    @Autowired
    private IBankBingService bankBingService;
    
	@Autowired
	private SpringRedisCacheService springRedisCacheService;
	
	@Autowired
    private FreeMarkerConfigurationFactory freeMarkerConfigurer;
    

    /**
     * 生成登录验证码,默认4位长度
     * 
     * @param checkCodeLength
     *            验证码的位数
     * @return
     */
    public String generateLoginIdentifyCode() {
        return generateLoginIdentifyCode(4);
    }

    /**
     * 生成登录验证码
     * 
     * @param checkCodeLength
     *            验证码的位数
     * @return
     */
    public String generateLoginIdentifyCode(int identifyCodeLength) {
        return IdentifyCodeGenerator.generateLoginIdentifyCode(identifyCodeLength);
    }

    /**
     * 生成手机验证码,默认4位长度
     * 
     * @return
     * @throws IOException
     */
    public String generateIdentifyCode() {
        return generateIdentifyCode(4);
    }

    /**
     * 生成手机验证码
     * 
     * @param checkCodeLength
     *            验证码的位数
     * @return
     */
    public String generateIdentifyCode(int identifyCodeLength) {
        return IdentifyCodeGenerator.generateIdentifyCode(identifyCodeLength);
    }

    /*
     * (no-Javadoc) <p>Title: sendIdentifyCode</p> <p>Description: </p>
     * 
     * @param mobile
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.checkcode.ICheckCodeService#sendIdentifyCode(
     * java.lang.String)
     */
    @Override
    public String sendIdentifyCode(String mobile) {
        String identifyCode = generateIdentifyCode(6);
        NotifyDo notifyDo = new SMSNotifyDo(identifyCode, mobile, NotifyBusinessType.check.name());
        notifyDo.setAsync(true);
        notifyService.send(notifyDo);
        return identifyCode;
    }

    /*
     * (no-Javadoc) <p>Title: checkIdentifyCode</p> <p>Description: </p>
     * 
     * @param mobile
     * 
     * @param identifyCode
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.checkcode.ICheckCodeService#checkIdentifyCode
     * (java.lang.String, java.lang.String)
     */
    @Override
    public boolean checkIdentifyCode(String mobile, String identifyCode) {
        return notifyService.checkIdentifyCode(mobile, identifyCode);
    }

    /*
     * (no-Javadoc) <p>Title: sendBankIdentifyCode</p> <p>Description: </p>
     * 
     * @param userId
     * 
     * @param bankCode
     * 
     * @param bankType
     * 
     * @return
     * 
     * @see com.hehenian.biz.common.identifycode.IIdentifyCodeService#
     * sendBankIdentifyCode(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Map<String, String> sendBankIdentifyCode(long userId, String userRealName, String bankCode, String bankType) {
        try {
            String amount = "0." + String.valueOf(IdentifyCodeGenerator.generateIdentifyCodeByScope(100));
            BigDecimal amountVal = new BigDecimal(amount);
            return bankBingService.sendBankIdentifyCode(userId, userRealName, bankCode, bankType, amountVal);
        } catch (Exception e) {
            logger.error("绑定银行卡出错：");
            logger.error(e);
            Map<String, String> map = new HashMap<String, String>();
            map.put("retCode", "02");
            return map;
        }
    }

    /*
     * (no-Javadoc) <p>Title: checkBankIdentifyCode</p> <p>Description: </p>
     * 
     * @param userId
     * 
     * @param bankCode
     * 
     * @param bankType
     * 
     * @param amount
     * 
     * @return
     * 
     * @see com.hehenian.biz.common.identifycode.IIdentifyCodeService#
     * checkBankIdentifyCode(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public String checkBankIdentifyCode(long userId, String bankCode, String bankType, String amount) {
        try {
            return bankBingService.checkBankIdentifyCode(userId, bankCode, bankType, amount);
        } catch (Throwable e) {
            logger.error("验证银行卡出错：");
            logger.error(e);
            return "02";
        }
    }

	@Override
	public boolean checkSmsCodeByBiz(String mobile,String bizType,String code) {
		if(StringUtils.isBlank(bizType)){
			bizType = NotifyBusinessType.check.name();
		}
		String key = mobile+"-"+bizType;
		try{
			Object smsCode = springRedisCacheService.get(key);
			if(smsCode!=null&&code.equals(smsCode.toString())){
				springRedisCacheService.delete(key);
				return true;
			}
		}catch(Exception ex){
			return false;
		}
		return false;
	}

	@Override
	public String sendSmsCode(String mobile,String bizType) {
		 String identifyCode = generateIdentifyCode(6);
	     NotifyDo notifyDo = new SMSNotifyDo(identifyCode, mobile, bizType);
	     boolean result = notifyService.send(notifyDo);
	     if(result){
	    	try {
	    		springRedisCacheService.set(mobile+"-"+bizType, identifyCode,30*60);
			} catch (Exception e) {
				e.printStackTrace();
			}
	     }
	     return identifyCode;
	}

}
