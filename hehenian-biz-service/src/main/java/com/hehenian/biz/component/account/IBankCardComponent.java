package com.hehenian.biz.component.account;

import java.util.List;

import com.hehenian.biz.common.account.dataobject.BankCardDo;

public interface IBankCardComponent {

    /**
     * 根据银行ID查询
     * 
     * @param bankId
     * @return
     */
    BankCardDo getById(Long bankId);

    /**
     * 根据用户ID和银行卡号查询银行卡信息
     * 
     * @param userId
     * @param cardNo
     * @return
     */
    BankCardDo getByUserIdAndCardNo(Long userId, String cardNo);

    /**
     * 更新用户银行卡信息
     * 
     * @param bankCardDoList
     * @author: liuzgmf
     * @date: 2014年12月3日上午11:47:44
     */
    void updateCardInfo(List<BankCardDo> bankCardDoList);

    /**
     * 查询用户银行卡信息
     * 
     * @param userId
     * @param cardStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年12月3日下午1:42:16
     */
    List<BankCardDo> queryByUserIdAndCardStatus(Long userId, Integer cardStatus);

    /**
     * 查询用户的银行卡信息
     * 
     * @param userIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年12月22日下午1:51:25
     */
    List<BankCardDo> queryByUserIds(List<Long> userIdList);

}
