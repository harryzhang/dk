package com.hehenian.biz.component.account.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.BankCardDo;
import com.hehenian.biz.component.account.IBankCardComponent;
import com.hehenian.biz.dal.account.IBankCardDao;

@Component("bankCardComponent")
public class BankCardComponentImpl implements IBankCardComponent {
    @Autowired
    private IBankCardDao bankCardDao;

    @Override
    public BankCardDo getById(Long bankId) {
        return bankCardDao.getById(bankId);
    }

    @Override
    public BankCardDo getByUserIdAndCardNo(Long userId, String cardNo) {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("userId", userId);
        searchItems.put("cardNo", cardNo);
        return bankCardDao.getByUserIdAndCardNo(searchItems);
    }

    @Override
    public void updateCardInfo(List<BankCardDo> bankCardDoList) {
        if (bankCardDoList == null || bankCardDoList.size() == 0) {
            return;
        }

        for (BankCardDo bankCardDo : bankCardDoList) {
            BankCardDo localBankCardDo = getByUserIdAndCardNo(bankCardDo.getUserId(), bankCardDo.getCardNo());
            if (localBankCardDo != null) {
                bankCardDo.setId(localBankCardDo.getId());
                bankCardDao.updateCardInfo(bankCardDo);
            } else {
                bankCardDao.addCardInfo(bankCardDo);
            }
        }
    }

    @Override
    public List<BankCardDo> queryByUserIdAndCardStatus(Long userId, Integer cardStatus) {
        return bankCardDao.queryByUserIdAndCardStatus(userId, cardStatus);
    }

    @Override
    public List<BankCardDo> queryByUserIds(List<Long> userIdList) {
        if (userIdList == null || userIdList.size() == 0) {
            return new ArrayList<BankCardDo>();
        }
        return bankCardDao.queryByUserIds(userIdList);
    }

}
