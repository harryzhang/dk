/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: BankCardServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月3日 上午11:09:16
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IBankCardService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.BankCardDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IBankCardComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月3日 上午11:09:16
 */
@Service("bankCardService")
public class BankCardServiceImpl implements IBankCardService {
    private final Logger               logger  = Logger.getLogger(this.getClass());
    private static Map<String, String> bankMap = new HashMap<String, String>();
    @Autowired
    private IBankCardComponent         bankCardComponent;
    @Autowired
    private IUserComponent             userComponent;
    @Autowired
    private IAccountManagerService     accountManagerService;

    static {
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
    }

    @Override
    public IResult<?> updateCardInfo(Long userId) {
        IResult<String> result = new ResultSupport<String>();
        try {
            List<BankCardDo> bankCardDoList = queryCardInfo(userId);
            bankCardComponent.updateCardInfo(bankCardDoList);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年12月3日上午11:13:39
     */
    private List<BankCardDo> queryCardInfo(Long userId) {
        InParameter inParameter = new InParameter();
        AccountUserDo userDo = userComponent.getById(userId);
        inParameter.getParams().put("UsrCustId", userDo.getUsrCustId() + "");
        inParameter.getParams().put("CardId", "");
        OutParameter outParameter = accountManagerService.queryCardInfo(inParameter, AccountType.CHINAPNR);
        if (!outParameter.isSuccess()) {
            throw new BusinessException("查询汇付银行卡信息失败!");
        }
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> usrCardInfoList = (List<Map<String, Object>>) outParameter.getParams().get(
                "UsrCardInfolist");
        if (usrCardInfoList == null || usrCardInfoList.size() == 0) {
            return new ArrayList<BankCardDo>();
        }
        List<BankCardDo> bankCardDoList = new ArrayList<BankCardDo>();
        for (Map<String, Object> map : usrCardInfoList) {
            BankCardDo bankCardDo = new BankCardDo();
            bankCardDo.setUserId(userId);
            bankCardDo.setCardNo((String) map.get("CardId"));
            bankCardDo.setCardUserName((String) map.get("UsrName"));
            bankCardDo.setBankName(bankMap.get((String) map.get("BankId")));
            bankCardDo.setCardStatus(1);// 1-已绑定
            bankCardDo.setOpenBankId((String) map.get("BankId"));
            String isDefault = (String) map.get("IsDefault");
            bankCardDo.setIsDefault(("Y".equals(isDefault) ? 1 : 0));
            String expressFlag = (String) map.get("expressFlag");
            bankCardDo.setIsExpress(("Y".equals(expressFlag) ? 1 : 0));

            bankCardDoList.add(bankCardDo);
        }
        return bankCardDoList;
    }

    @Override
    public List<BankCardDo> queryByUserIdAndCardStatus(Long userId, Integer cardStatus) {
        return bankCardComponent.queryByUserIdAndCardStatus(userId, cardStatus);
    }

}
