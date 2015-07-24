/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.service.bank.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hehenian.biz.common.bank.IBankBingService;
import com.hehenian.biz.common.bank.dataobject.BankBingDo;
import com.hehenian.biz.common.notify.SMSConfig;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.biz.component.bank.IBankBingComponent;
import com.hhn.service.pay.IAllinPayRemoteService;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Service("bankBingService")
public class BankBingServiceImpl implements IBankBingService {

    private final Logger       logger = Logger.getLogger(this.getClass());
    
    
    public static String RESULT_CODE_00 = "00"; //成功
    public static String RESULT_CODE_01 = "01"; //数据无效
    public static String RESULT_CODE_02 = "02"; //系统繁忙重试
    public static String RESULT_CODE_03 = "03"; //银行卡被锁定
    public static String RESULT_CODE_04 = "04"; //达到最多5次
    public static String RESULT_CODE_05 = "05"; //次卡已经绑定过不能重复绑定
    public static String RESULT_CODE_07 = "07"; //验证码不正确
    public static String       RESULT_CODE_08 = "08";                             // 此卡无绑定记录
    public static String       OPERATE_TRUE   = "T";                              // 操作成功
    public static String       OPERATE_FALSE  = "F";                              // 操作失败

    @Autowired
    private IBankBingComponent bankBingComponent;

    @Autowired
    SMSConfig                  smsConfig;

    IAllinPayRemoteService     allinPay;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public BankBingDo getById(int id) {
        return bankBingComponent.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<BankBingDo> selectBankBing(Map<String, Object> parameterMap) {
        return bankBingComponent.selectBankBing(parameterMap);
    }

    /**
     * 更新
     */
    public int updateBankBingById(BankBingDo newBankBingDo) {
        return bankBingComponent.updateBankBingById(newBankBingDo);
    }

    /**
     * 新增
     */
    public int addBankBing(BankBingDo newBankBingDo) {
        return bankBingComponent.addBankBing(newBankBingDo);
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return bankBingComponent.deleteById(id);
    }

    /**
     * 充值前检查
     * 
     * @param userBankBingList
     *            用户所有的绑卡记录
     * @param currentBankCode
     *            当次需要绑定的卡号
     * @return boolean false 不能发送， true 可以发送
     * @author: zhangyunhmf
     * @date: 2015年1月16日上午11:13:12
     */
    // public String checkUserBankBingRecord(List<BankBingDo> userBankBingList,
    // String currentBankCode) {
    //
    // if (null == userBankBingList) {
    // return RESULT_CODE_00;
    // }
    //
    // int currentSendCount = 0; // 本次卡的充值成功次数
    // int totalBingCount = 0; // 本用户绑卡成功次数
    // Set<String> sendCardSet = new HashSet<String>();
    //
    // int sendBankCardCount =
    // StringUtil.strToInt(smsConfig.getSendBankCardCount(), 5);
    // int sendPerCardCount =
    // StringUtil.strToInt(smsConfig.getSendPerCardCount(), 2);
    //
    // for (BankBingDo bank : userBankBingList) {
    // if (OPERATE_TRUE.equals(bank.getCheckFlag()) &&
    // currentBankCode.equals(bank.getBankCode())) {
    // return RESULT_CODE_05; // 已经绑定过的卡不用再绑定
    // }
    //
    // if (OPERATE_TRUE.equals(bank.getSendFlag()) &&
    // currentBankCode.equals(bank.getBankCode())) {
    // currentSendCount++;
    // }
    // if (OPERATE_TRUE.equals(bank.getSendFlag())) {// 累计发送成功的卡
    // sendCardSet.add(bank.getBankCode());
    // totalBingCount++;
    // }
    // if (sendCardSet.size() >= sendBankCardCount) {// 最多绑定5张卡
    // return RESULT_CODE_04;
    // }
    //
    // }
    //
    // if (totalBingCount >= sendPerCardCount * sendBankCardCount) { //
    // 累计发送成功的卡最多10次
    // return RESULT_CODE_04;
    // }
    //
    // if (currentSendCount < sendPerCardCount) {// 同一张卡充值成功次数最多2次
    // return RESULT_CODE_00;
    // }
    //
    // return RESULT_CODE_03;
    // }

    public String checkUserBankBingRecord(String bankCode, long userId) {
        String result = "00";
        Long count = bankBingComponent.getSucCardBingCount(bankCode);
        if (count > 0) {
            result = "05";// 卡已经绑定过；
        } else {
            Long verifyCardNum = bankBingComponent.countUserVerifyCard(userId);
            if (verifyCardNum != null && verifyCardNum >= 5) {
                result = "04";// 一个人最多验5张卡;
            } else {
                count = bankBingComponent.getCardSendCount(bankCode);
                if (count >= 2) {
                    result = "03";// 一张卡从未绑到绑定最多随机充值2次;
                }
            }
        }
        return result;
    }


    /**
     * 验证银行充值的金额是否正确
     * 
     * @param
     * @return
     * @author: zhangyunhmf
     * @date: 2015年1月16日上午11:54:15
     */
    private String checkBankBingAmount(BankBingDo bankBing, BigDecimal amount) {

        int tryCount = StringUtil.strToInt(smsConfig.getTryCountWhenCheck(), 3);
        if (bankBing.getCheckNumber() >= tryCount) {
            return RESULT_CODE_03;
        }
        if (OPERATE_TRUE.equals(bankBing.getSendFlag()) && OPERATE_FALSE.equals(bankBing.getCheckFlag())) {// 发送成功，还没有验证成功过的记录
            return amount.compareTo(bankBing.getAmount()) == 0 ? RESULT_CODE_00 : RESULT_CODE_07;
        }
        return RESULT_CODE_07;

    }

    /*
     * 
     * (no-Javadoc) <p>Title: sendBankIdentifyCode</p> <p>Description: 绑卡的规则： 1.
     * 一张卡从未绑到绑定最多随机充值2次 2. 一个人最多绑定5张卡 3. 一次绑定最多只能试3次， 3次不成功， 记录无效 4.
     * 绑定成功的记录不可以重复绑定 </p>
     * select checkflag from td_bank_bing where cardNo = ?
     * if(checkflag= true){
     *    return 卡已经绑定过；
     * }
     *
     * cardcount = select count(*) from td_bank_bing where user_id = ... and sendflag = true;
     * if(cardcount>=5){
     *  return 2. 一个人最多绑定5张卡;
     * }
     * else{
     * cardNum = select count(*) from td_bank_bing where user_id = ? and cardNo = ? and sendflag = true;
     *  if(cardNum >=2){
     *    return 一张卡从未绑到绑定最多随机充值2次;
     *  }
     *
     * }
     * @param userId
     * 
     * @param bankCode
     * 
     * @param bankType
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.bank.IBankBingService#sendBankIdentifyCode(java
     * .lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, String> sendBankIdentifyCode(long userId, String userRealName, String bankCode, String bankType,
            BigDecimal amount) {
        Map<String, String> retMap = new HashMap<String, String>();
        logger.info("开始随机充值： userRealName:" + userRealName + "userId:" + userId + " bankcode:" + bankCode + " amount:"
                + amount);
        if (null == bankCode || bankCode.trim().length() < 1) {
            retMap.put("retCode", RESULT_CODE_01);
            return retMap;
        }

        if (userId == 0) {
            retMap.put("retCode", RESULT_CODE_01);
            return retMap;
        }

        // List<BankBingDo> userBankBingList =
        // bankBingComponent.getByUserId(userId);
        // String result = this.checkUserBankBingRecord(userBankBingList,
        // bankCode);

        String result = this.checkUserBankBingRecord(bankCode, userId);

        /*select checkflag from td_bank_bing where cardNo = ?
        * if(checkflag= true){
            *    return 卡已经绑定过；
            * }
        *
        * cardcount = select count(*) from td_bank_bing where user_id = ... and sendflag = true;
        * if(cardcount>=5){
            *  return 2. 一个人最多绑定5张卡;
            * }
        * else{
            * cardNum = select count(*) from td_bank_bing where user_id = ? and cardNo = ? and sendflag = true;
            *  if(cardNum >=2){
                *    return 一张卡从未绑到绑定最多随机充值2次;
                *  }*/

        if (RESULT_CODE_00.equals(result)) {

            if (null == allinPay) {
                HessianProxyFactory factory = new HessianProxyFactory();
                try {
                    allinPay = (IAllinPayRemoteService) factory.create(IAllinPayRemoteService.class,
                            smsConfig.getDqlcServiceUrl() + "/allinPay");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            /*
             * 绑卡的时候调用 向用户银行卡充值，验卡S
             * 
             * @param userId 用户id
             * 
             * @param userName 用户名
             * 
             * @param bankCode 银行卡
             * 
             * @param bankType 银行代码
             * 
             * @param amount
             * 
             * @return Map key="result" boolean true 充值成功 false 充值失败
             * key="trade_id" long 交易ID
             * 
             * @author: zhangyunhmf
             * 
             * @date: 2015年1月16日下午4:34:50
             */
            Map sendResultMap = allinPay.sendBankCheckAmount(userId, userRealName, bankCode, bankType,
                    amount.toString());
            logger.info(sendResultMap);
            boolean sendResult = (Boolean) sendResultMap.get("result");
            String tradeId = null;
            if (true) {
                Object id = sendResultMap.get("actual_account_log_id");
                if (null != id)
                    tradeId = String.valueOf(id);
            }

            BankBingDo bank = new BankBingDo();
            bank.setAmount(amount);
            bank.setBankCode(bankCode);
            if (null != tradeId) {
                bank.setBusinessRecordId(tradeId);
            }
            bank.setCheckFlag(OPERATE_FALSE); // 未验证
            bank.setCheckNumber((short) 0);
            bank.setRecordStatus(OPERATE_TRUE); // 有效
            bank.setSendFlag(sendResult == true ? OPERATE_TRUE : OPERATE_FALSE); // 发送成功
            bank.setSendTime(new Date());
            bank.setUserId(userId);
            bankBingComponent.addBankBing(bank);
            if (sendResult) {
                result = RESULT_CODE_00;
            } else {
                retMap.put("retMsg", (String) sendResultMap.get("errorMessage"));
                logger.error("绑卡的时候调用定期理财代付接口失败， 银行卡：" + bankCode + " 错误原因：" + sendResultMap.get("errorMessage"));
                Object retCode = sendResultMap.get("retCode");

                if (null == retCode) {
                    result = RESULT_CODE_02;
                } else {
                    result = retCode.toString();
                }
            }
        }

        logger.info("结束随机充值： userRealName:" + userRealName + "userId:" + userId + " bankcode:" + bankCode + " amount:"
                + amount + "  充值结果result： " + result);

        retMap.put("retCode", result);
        return retMap;

    }

    /*
     * 
     * 
     * (no-Javadoc) <p>Title: checkBankIdentifyCode</p> <p>Description: 绑卡的规则：
     * 1. 一张卡从未绑到绑定最多随机充值2次 2. 一个人最多绑定5张卡 3. 一次绑定最多只能试3次， 3次不成功， 记录无效 4.
     * 绑定成功的记录不可以重复绑定 </p>
     * 
     * @param userId
     * 
     * @param bankCode
     * 
     * @param bankType
     * 
     * @param identifyCode
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.bank.IBankBingService#checkBankIdentifyCode(java
     * .lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String checkBankIdentifyCode(long userId, String bankCode, String bankType, String amount) {

        logger.info("开始验证卡：" + "userId:" + userId + " bankcode:" + bankCode + " amount:" + amount);
        if (null == bankCode || bankCode.trim().length() < 1) {
            return RESULT_CODE_01;
        }
        if (userId < 1) {
            return RESULT_CODE_01;
        }
        if (null == amount) {
            return RESULT_CODE_01;
        }

        BigDecimal amountVal;
        try {
            amountVal = new BigDecimal(amount);
        } catch (Throwable e) {
            return RESULT_CODE_01;
        }// 金额不能转成数字提示无效数据

        // 通过用户和卡取最后一次的验证记录
        BankBingDo bank = bankBingComponent.getLastBankBingByUserIdBankCode(userId, bankCode);
        if (null == bank) {
            return RESULT_CODE_08;
        }
        // 检查验证是否正确
        String result = checkBankBingAmount(bank, amountVal);
        // 更新验证次数和验证结果
        bank.setCheckNumber((short) (bank.getCheckNumber() + 1));
        bank.setCheckFlag(RESULT_CODE_00.equals(result) ? OPERATE_TRUE : OPERATE_FALSE);
        int tryCount = StringUtil.strToInt(smsConfig.getTryCountWhenCheck(), 3);
        if (bank.getCheckNumber() >= tryCount || RESULT_CODE_00.equals(result)) { // 超过三次验证，和已经验证成功过记录不再有效
            bank.setRecordStatus(OPERATE_FALSE);
        }
        bank.setCheckTime(new Date());
        bankBingComponent.updateBankBingById(bank);

        logger.info("结束验证卡：" + "userId:" + userId + " bankcode:" + bankCode + " amount:" + amount + "验证结果result："
                + result);

        return result;
    }

}
