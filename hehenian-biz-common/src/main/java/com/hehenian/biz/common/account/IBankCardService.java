/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade
 * @Title: IBankCardService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月3日 上午11:06:36
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.account;

import java.util.List;

import com.hehenian.biz.common.account.dataobject.BankCardDo;
import com.hehenian.biz.common.base.result.IResult;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月3日 上午11:06:36
 */
public interface IBankCardService {

    /**
     * 更新用户银行卡信息。查询用户在汇付绑定的银行卡信息，更新到平台数据表中
     * 
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年12月3日上午11:07:47
     */
    IResult<?> updateCardInfo(Long userId);

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
}
