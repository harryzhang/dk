package com.hehenian.biz.dal.account;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

public interface IUserDao {

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
     * @param userDo
     * @return
     */
    Integer updateAmount(AccountUserDo userDo);

    /**
     * @param IncrementSum
     *            增加或减少的金额 ，如果是减少金额是负数
     * @param userId
     *            用户ID
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月24日下午2:22:42
     */
    int updateUsableSum(@Param("IncrementSum") double IncrementSum, @Param("userId") long userId);

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
     * 根据彩生活用户id 取用户
     * 
     * @param colorUserId
     *            彩生活用户id
     * @return
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
     * @date: 2014年11月21日上午10:40:23
     */
    List<AccountUserDo> queryByUserIds(@Param("userIdList") List<Long> userIdList);

    /**
     * 通过用户名查找用户
     * 
     * @param username
     * @return
     */
    AccountUserDo findUserByUserName(String username);

    AccountUserDo findUserByPhone(String mobilePhone);

    Integer saveUser(AccountUserDo accountUserDo);

    AccountUserDo findUserByUserNamePwd(@Param("username") String userName, @Param("password") String password);

    /**
     * 根据用户usrCustId查询用户信息
     * 
     * @param userIdList
     * @return
     * @author: xiexiangmf
     * @date: 2014年12月24日下午2:16:40
     */
    List<AccountUserDo> queryByUserCusIds(@Param("userIdList") List<Long> userIdList);

    /**
     * 根据用户编号修改用户邮箱
     * @param id
     * @param mobilePhone
     * @return
     */
    int updateUserEmail(@Param("id") Long id , @Param("email") String email);
    
    int updateUserPhone(@Param("id") Long id, @Param("mobilePhone") String mobilePhone);
    
    int updatePersonPhone(@Param("id") Long id, @Param("mobilePhone") String mobilePhone);

    int updateUserPassword(@Param("id") Long userId, @Param("password") String password);

    /**
     * 根据身份证号码查询用户信息
     * 
     * @param idNo
     * @return
     * @author: liuzgmf
     * @date: 2015年4月21日下午2:52:21
     */
    AccountUserDo getByIdNo(String idNo);

	AccountUserDo findUserByEmail(@Param("email") String email);
    
	int updatePayPassword(@Param("id")Long userId, @Param("payPassword")String payPassword);

	/**
	 * 设置推荐人
	 * @param recommendId
	 * @param userId
	 * @return
	 */
	long setReferee(@Param("recommendId") String recommendId, @Param("userId") String userId);

	/**
	 * 保存用户推荐人
	 * @param reffer
	 * @param userId
	 */
	void saveUserReffer(@Param("reffer") String reffer,@Param("userId") Long userId);

	/**
	 * 修改用户信息
	 * @param accountUserDo
	 * @return
	 */
	int updateUser(AccountUserDo accountUserDo);

	AccountUserDo findUserByColorid(String colorid);
	
	void updateColourlifeInfo(@Param("id")Long id,@Param("colourId") Long colourId, @Param("cid")Long cid, @Param("cname")String cname,  @Param("caddress")String caddress);

	
}
