package com.hehenian.biz.common.account;

import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.InviteCodeDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;

public interface IUserService {

    /**
     * 根据用户ID查询用户信息
     * 
     * @param id
     * @return
     */
    AccountUserDo getById(Long id);

    /**
     * 查询用户的资金记录
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年12月1日下午4:17:48
     */
    NPageDo<Map<String, Object>> queryUserFundRecords(Map<String, Object> searchItems);

    AccountUserDo findUserByUserName(String username);

    AccountUserDo findUserByPhone(String mobilePhone);

    IResult registerUser(AccountUserDo accountUserDo);

    AccountUserDo loginWithPwd(String username, String password);

    AccountUserDo loginWithId(Long userId);

    UserBindDo findUserBindByPartner(int partnerId, String partnerUserId);

    int saveUserBind(UserBindDo userBindDo);

    Long bindNewUser(AccountUserDo accountUserDo, UserBindDo userBindDo);

    UserBindDo findUserBindByUser(int partnerId, Long userId);

    int updateUserPhone(Long id, String mobilePhone);


    
    int updatePersonPhone(Long id, String mobilePhone);

    /**
     * 根据彩生活用户ID 获取用户ID
     * 
     * @param colorId
     * @return
     */
    public AccountUserDo getUserByColorId(Long colorId);

    /**
     * 查询邀请码
     * 
     * @Description: TODO
     * @param inviteCode
     * @return
     * @author: chenzhpmf
     * @date 2015-3-30 下午9:05:44
     */
    InviteCodeDo findInviteCodeByDO(InviteCodeDo inviteCode);

    /**
     * 更新邀请码，与用户绑定
     * 
     * @Description: TODO
     * @param inviteCode
     * @return
     * @author: chenzhpmf
     * @date 2015-3-30 下午9:05:58
     */
    int updateInviteCode(InviteCodeDo inviteCode);

    int updateUserPassword(Long userId, String password, String type);

    /**
     * 根据用户邮箱获取用户信息
     * @param email
     * @return TODO
     */
	AccountUserDo findUserByEmail(String email);

	/**
	 * 验证用户名和密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	AccountUserDo findUserByUserNamePwd(String username, String password);
    
    /**
     * 根据身份证号码查询用户信息
     * 
     * @param idNo
     * @return
     * @author: liuzgmf
     * @date: 2015年4月21日下午2:52:21
     */
    AccountUserDo getByIdNo(String idNo);

    int updatePayPassword(Long userId, String password);

	/**
	 * 设置汇付注册推荐人
	 * @param recommend
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
	 * 更新用户汇付平台认证信息
	 * @param userId
	 * @param usrCustId TODO
	 * @param email TODO
	 * @param idNo TODO
	 * @param realName TODO
	 * @return
	 */
	long updateUserUsrCust(Long userId, Long usrCustId, String email, String idNo, String realName);


	void joinHyh(String realName, String idNo, long userId);
	
	 /**
	  * 自动注册后更新个人信息
	  * @param user AccountUserDo对象
	  * @param realName 真实姓名
	  * @param idNo 身份证号码
	  * @param mobile 手机
	  * @return
	  */
	public  PersonDo updatePerson(AccountUserDo user, String realName, String idNo, String mobile);

	/**
	  * 自动注册
	  * @param refferee 推荐人ID ， 没有给 -1
	  * @param userName  注册用户名
	  * @param mobilePhone 注册手机
	  * @param pwd         密码
	  * @param source      来源， 100 代表贷款端用户
	  * @param sourceUserId 彩之云ID 没有给-1
	  * @return AccountUserDo 用户对象
	  */
	public  IResult<AccountUserDo> register(long refferee, String userName, String mobilePhone, String pwd, int source, long colorid);

	void updateColourlifeInfo(Long id, Long colourId, Long cid, String cname, String caddress);
}
