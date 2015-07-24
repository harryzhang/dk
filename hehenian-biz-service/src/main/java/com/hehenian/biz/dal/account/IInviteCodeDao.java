/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.account
 * @Title: IInviteCodeDao.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-3-30 下午9:39:52
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.account;

import com.hehenian.biz.common.account.dataobject.InviteCodeDo;

public interface IInviteCodeDao {
    
    /**
     * 返回邀请码对象（员工邀请码）
     * @Description: TODO
     * @param inviteCode
     * @return
     * @author: chenzhpmf
     * @date 2015-3-30 下午9:11:45
     */
    InviteCodeDo findInviteCodeByDO(InviteCodeDo inviteCode);
    /**
     * 更新邀请码，与用户绑定
     * @Description: TODO
     * @param inviteCode
     * @return
     * @author: chenzhpmf
     * @date 2015-3-30 下午9:12:28
     */
    int updateInviteCode(InviteCodeDo inviteCode); 
}
