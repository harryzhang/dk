/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade
 * @Title: IPreRepaymentComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月24日 下午2:02:15
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade;

import com.hehenian.biz.common.trade.dataobject.PreRepaymentDo;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月24日 下午2:02:15
 */
public interface IPreRepaymentComponent {

    /**
     * 新增预还款记录
     * 
     * @param preRepaymentDo
     * @author: liuzgmf
     * @date: 2014年9月24日下午2:02:56
     */
    Long addPreRepayment(PreRepaymentDo preRepaymentDo);

}
