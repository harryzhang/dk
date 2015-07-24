package com.hehenian.biz.dal.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.account.dataobject.BankCardDo;

public interface IBankCardDao {

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
     * @param searchItems
     * @return
     */
    BankCardDo getByUserIdAndCardNo(Map<String, Object> searchItems);

    /**
     * 查询用户银行卡信息
     * 
     * @param userId
     * @param cardStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年12月3日下午1:42:16
     */
    List<BankCardDo> queryByUserIdAndCardStatus(@Param("userId") Long userId, @Param("cardStatus") Integer cardStatus);

    /**
     * 修改用户的银行卡信息
     * 
     * @param bankCardDo
     * @author: liuzgmf
     * @date: 2014年12月3日下午1:44:54
     */
    int updateCardInfo(BankCardDo bankCardDo);

    /**
     * 新增用户的银行卡信息
     * 
     * @param bankCardDo
     * @author: liuzgmf
     * @date: 2014年12月3日下午1:45:20
     */
    int addCardInfo(BankCardDo bankCardDo);

    /**
     * 查询用户的银行卡信息
     * 
     * @param userIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年12月22日下午1:51:25
     */
    List<BankCardDo> queryByUserIds(@Param("userIdList") List<Long> userIdList);

}
