package com.hehenian.biz.component.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.InviteCodeDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;

public interface IUserComponent {

    /**
     * 根据用户ID查询用户信息
     * 
     * @param id
     * @return
     */
    AccountUserDo getById(Long id);

    /**
     * 修改用户账户的可用金额和冻结余额
     * 
     * @param usableSum
     *            增加或减少的可用余额
     * @param freezeSum
     *            增加或减少的冻结余额
     * @param userId
     *            用户ID
     * @return
     */
    Boolean updateAmount(Double usableSum, Double freezeSum, Long userId);

    /**
     * 修改用户账户金额
     * 
     * @param userDo
     * @author: liuzgmf
     * @date: 2014年10月8日下午5:22:24
     */
    Boolean updateAmount(AccountUserDo userDo);

    /**
     * 
     * @param IncrementSum
     *            增加或减少用户的可用金额
     * @param operationDirection
     *            操作方向： +，-
     * @param userId
     *            用户ID
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午2:19:10
     */
    boolean updateUsableSum(double IncrementSum, String operationDirection, long userId);

    /**
     * 根据汇付的用户账户取用户
     * 
     * @param compCustId
     *            汇付户账
     * @author: zhangyunhmf
     * @date: 2014年10月16日下午4:30:34
     */
    AccountUserDo getUserByCustId(String compCustId);

    /**
     * 
     * @param colorUserId
     *            彩生活用户id
     * @return 用户对象
     */
    AccountUserDo getUserByColorId(Long colorUserId);

    /**
     * 根据条件查询用户信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年12月1日下午4:41:35
     */
    long countUsers(Map<String, Object> searchItems);

    /**
     * 根据条件查询用户信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年12月1日下午4:41:51
     */
    List<Map<String, Object>> queryUsers(Map<String, Object> searchItems);

    /**
     * 根据用户ID查询用户信息
     * 
     * @param userIdList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月21日上午10:39:27
     */
    List<AccountUserDo> queryByUserIds(List<Long> userIdList);

    /**
     * 通过用户名查找用户
     * 
     * @param username
     * @return
     */
    AccountUserDo findUserByUserName(String username);

    AccountUserDo findUserByPhone(String mobilePhone);
    
    AccountUserDo findUserByColorid(String colorid);

    Long saveUser(AccountUserDo accountUserDo);

    AccountUserDo findUserByUserNamePwd(String userName, String password);

    UserBindDo findUserBindByPartner(int partnerId, String partnerUserId);

    int saveUserBind(UserBindDo userBindDo);

    Long saveNewUserBind(AccountUserDo accountUserDo, UserBindDo userBindDo);

    UserBindDo findUserBindByUser(int partnerId, Long userId);

    /**
     * @param userIdList
     * @return
     * @author: xiexiangmf
     * @date: 2014年12月24日下午2:19:39
     */
    List<AccountUserDo> queryUserByCustId(List<Long> userIdList);

    int updateUserPhone(Long id, String mobilePhone);


    /**
     * 根据用户ID修改用户邮箱
     * @param id
     * @param email
     * @return
     */
    int updateUserEmail(Long id ,  String email);
    
    int updatePersonPhone(Long id, String mobilePhone);

    /**
     * 更新邀请码，与用户绑定
     * 
     * @Description: TODO
     * @param inviteCode
     * @return
     * @author: chenzhpmf
     * @date 2015-3-30 下午9:12:28
     */
    int updateInviteCode(InviteCodeDo inviteCode);

    /**
     * 返回邀请码对象（员工邀请码）
     * 
     * @Description: TODO
     * @param inviteCode
     * @return
     * @author: chenzhpmf
     * @date 2015-3-30 下午9:11:45
     */
    InviteCodeDo findInviteCodeByDO(InviteCodeDo inviteCode);

    int updateUserPassword(Long userId, String password, String type);

    /**
     * 根据身份证号码查询用户信息
     * 
     * @param idNo
     * @return
     * @author: liuzgmf
     * @date: 2015年4月21日下午2:52:21
     */
    AccountUserDo getByIdNo(String idNo);

	/**
	 * 根据用户邮箱获取用户信息
	 * @param email
	 * @return
	 */
	AccountUserDo findUserByEmail(String email);

	int updatePayPassword(Long userId, String password);

	/**
	 * 设置推荐人
	 * @param recommendId
	 * @param userId
	 * @return
	 */
	long setReferee(String recommendId, String userId);

	/**
	 * 保存用户推荐人
	 * @param reffer
	 * @param userId
	 */
	void saveUserReffer(String reffer, Long userId);

	/**
	 * 修改账户汇付天下认证信息
	 * @param userId	用户ID
	 * @param usrCustId 汇付平台ID
	 * @param email 	邮箱
	 * @param idNo 		身份证号码
	 * @param realName 	真实姓名
	 * @return
	 */
	int updateUserUsrCust(Long userId, Long usrCustId, String email, String idNo, String realName);

	void updateColourlifeInfo(Long id, Long colourId, Long cid, String cname, String caddress);
}
