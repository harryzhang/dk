package com.hehenian.biz.component.account.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.InviteCodeDo;
import com.hehenian.biz.common.account.dataobject.MaterialsAuth;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;
import com.hehenian.biz.common.account.dataobject.WorkAuth;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IPhoneVerifyComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.dal.account.IInviteCodeDao;
import com.hehenian.biz.dal.account.IMaterialsAuthDao;
import com.hehenian.biz.dal.account.IPersonDao;
import com.hehenian.biz.dal.account.IUserBindDao;
import com.hehenian.biz.dal.account.IUserDao;
import com.hehenian.biz.dal.account.IWorkAuthDao;

@Component("userComponent")
public class UserComponentImpl implements IUserComponent {
    private static final Logger   LOGGER = Logger.getLogger(UserComponentImpl.class);
    @Autowired
    private IUserDao              userDao;
    @Autowired
    private IPersonDao            personDao;
    @Autowired
    private IUserBindDao          userBindDao;
    @Autowired
    private IInviteCodeDao        inviteCodeDao;
    @Autowired
    private IPhoneVerifyComponent phoneVerifyComponent;
    @Autowired
    private IWorkAuthDao		  workAuthDao;
    
    @Autowired
    private IMaterialsAuthDao materialsAuthDao;

    @Override
    public AccountUserDo getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public Boolean updateAmount(Double usableSum, Double freezeSum, Long userId) {
        AccountUserDo userDo = new AccountUserDo();
        userDo.setUsableSum(usableSum);
        userDo.setFreezeSum(freezeSum);
        userDo.setId(userId);
        updateAmount(userDo);
        return true;
    }

    @Override
    public Boolean updateAmount(AccountUserDo userDo) {
        int count = userDao.updateAmount(userDo);
        if (count != 1) {
            throw new RuntimeException("变更用户[" + userDo.getId() + "]账户可用金额，冻结金额失败");
        }
        return true;
    }

    /*
     * (no-Javadoc) <p>Title: updateUsableSum</p> <p>Description: </p>
     * 
     * @param IncrementSum
     * 
     * @param operationDirection
     * 
     * @param userId
     * 
     * @see
     * com.hehenian.biz.component.account.IUserComponent#updateUsableSum(double,
     * java.lang.String, long)
     */
    @Override
    public boolean updateUsableSum(double IncrementSum, String operationDirection, long userId) {
        if ("-".equals(operationDirection)) {
            IncrementSum = -IncrementSum;
        }
        int count = userDao.updateUsableSum(IncrementSum, userId);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * (no-Javadoc) <p>Title: getUserByCustId</p> <p>Description: </p>
     * 
     * @param compCustId
     * 
     * @see
     * com.hehenian.biz.component.account.IUserComponent#getUserByCustId(java
     * .lang.String)
     */
    @Override
    public AccountUserDo getUserByCustId(String compCustId) {
        return userDao.getUserByCustId(compCustId);

    }

    @Override
    public AccountUserDo getUserByColorId(Long colorUserId) {
        return userDao.getUserByColorId(colorUserId);
    }

    @Override
    public long countUsers(Map<String, Object> searchItems) {
        return userDao.countUsers(searchItems);
    }

    @Override
    public List<Map<String, Object>> queryUsers(Map<String, Object> searchItems) {
        return userDao.queryUsers(searchItems);
    }

    @Override
    public List<AccountUserDo> queryByUserIds(List<Long> userIdList) {
        if (userIdList == null || userIdList.size() == 0) {
            return new ArrayList<AccountUserDo>();
        }
        return userDao.queryByUserIds(userIdList);
    }

    @Override
    public List<AccountUserDo> queryUserByCustId(List<Long> userIdList) {
        if (userIdList == null || userIdList.size() == 0) {
            return new ArrayList<AccountUserDo>();
        }
        return userDao.queryByUserCusIds(userIdList);
    }

    /**
     * 通过用户名查找用户
     * 
     * @param username
     * @return
     */
    public AccountUserDo findUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }

    public AccountUserDo findUserByPhone(String mobilePhone) {
        return userDao.findUserByPhone(mobilePhone);
    }

    public Long saveUser(AccountUserDo accountUserDo) {
        accountUserDo.setCreateTime(new Date());
        Integer i = userDao.saveUser(accountUserDo);
        if (i != null && i > 0) {
            PersonDo personDo = new PersonDo();
            personDo.setUserId(accountUserDo.getId());
            personDo.setCellPhone(accountUserDo.getMobilePhone());
            personDo.setTelephone(accountUserDo.getMobilePhone());
            i = personDao.savePerson(personDo);
            if (i <= 0) {
                throw new BusinessException("保存用户信息失败");
            } else {
                i = personDao.saveWorkauth(accountUserDo.getId());
                if (i <= 0) {
                    throw new BusinessException("保存用户信息失败");
                } else {
                    try {
                        // 保存手机号验证记录
                        PhoneVerifyDo phoneVerifyDo = new PhoneVerifyDo();
                        phoneVerifyDo.setUserId(accountUserDo.getId());
                        phoneVerifyDo.setMobilePhone(accountUserDo.getMobilePhone());
                        phoneVerifyDo.setStatus(1);
                        IResult result = phoneVerifyComponent.savePhoneVerify(phoneVerifyDo);
                        if (result.isSuccess()) {
                            LOGGER.info("保存手机号验证记录成功");
                        } else {
                            LOGGER.info("保存手机号验证记录失败,失败原因:" + result.getErrorMessage());
                        }
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return accountUserDo.getId();
                }

            }
        }
        return 0l;
    }

    @Override
    public AccountUserDo findUserByUserNamePwd(String userName, String password) {
        return userDao.findUserByUserNamePwd(userName, password);
    }

    @Override
    public UserBindDo findUserBindByPartner(int partnerId, String partnerUserId) {
        return userBindDao.findUserBindByPartner(partnerId, partnerUserId);
    }

    @Override
    public int saveUserBind(UserBindDo userBindDo) {
        return userBindDao.saveUserBind(userBindDo);
    }

    @Override
    public Long saveNewUserBind(AccountUserDo accountUserDo, UserBindDo userBindDo) {
        Long userId = saveUser(accountUserDo);
        if (userId != null && userId > 0) {
            userBindDo.setUserId(userId);
            userBindDo.setCreateTime(accountUserDo.getCreateTime());
            int i = saveUserBind(userBindDo);
            if (i <= 0) {
                throw new BusinessException("保存用户绑定信息失败");
            } else {
                return userId;
            }
        }
        return 0l;
    }

    @Override
    public UserBindDo findUserBindByUser(int partnerId, Long userId) {
        return userBindDao.findUserBindByUser(partnerId, userId);
    }

    @Override
    public int updateUserPhone(Long id, String mobilePhone) {
        return userDao.updateUserPhone(id, mobilePhone);
    }

    @Override
	public int updatePersonPhone(Long id, String mobilePhone) {
		return userDao.updatePersonPhone(id, mobilePhone);
	}

	@Override
    public InviteCodeDo findInviteCodeByDO(InviteCodeDo inviteCode) {
        return inviteCodeDao.findInviteCodeByDO(inviteCode);
    }

    @Override
    public int updateInviteCode(InviteCodeDo inviteCode) {
        return inviteCodeDao.updateInviteCode(inviteCode);
    }

    @Override
    public int updateUserPassword(Long userId, String password, String type) {
        return userDao.updateUserPassword(userId, password);
    }
	
	@Override
	public int updatePayPassword(Long userId, String password){
		return userDao.updatePayPassword(userId, password);
	}

	@Override
	public int updateUserEmail(Long id, String email) {
		return this.userDao.updateUserEmail(id, email);
	}

	@Override
	public AccountUserDo findUserByEmail(String email) {
		return this.userDao.findUserByEmail(email);
	}

	@Override
	public long setReferee(String recommendId, String userId) {
		return this.userDao.setReferee(recommendId, userId);
	}

	@Override
	public void saveUserReffer(String reffer, Long userId) {
		this.userDao.saveUserReffer(reffer, userId);
	}

	@Override
	public int updateUserUsrCust(Long userId, Long usrCustId, String email, String idNo, String realName) {
		int result = -1;
		try {
			// 更新用户信息
			AccountUserDo accountUserDo = new AccountUserDo();
			accountUserDo.setId(userId);
			accountUserDo.setUsrCustId(usrCustId);
			accountUserDo.setEmail(email);
			result = this.userDao.updateUser(accountUserDo);
			
			// 更新用户个人信息
			PersonDo personDo = new PersonDo();
			personDo.setRealName(realName);
			personDo.setUserId(userId);
			personDo.setIdNo(idNo);
			personDo.setEmail(email);
			result = this.personDao.updatePersonByUserId(personDo);
			
			// 更新工作认证信息
			WorkAuth workAuth = new WorkAuth();
			workAuth.setMoredStatus(3);
			workAuth.setDirectedStatus(3);
			workAuth.setOtherStatus(3);
			workAuth.setAuditStatus(3);
			result = this.workAuthDao.updateWorkAuthByUserId(workAuth);
			
			// 更新资料认证信息
			MaterialsAuth materialsAuth = new MaterialsAuth();
			materialsAuth.setUserId(userId);
			materialsAuth.setAuditStatus(3);
			materialsAuth.setAuthTime(new Date());
			materialsAuth.setPassTime(new Date());
			result = this.materialsAuthDao.updateByUserId(materialsAuth);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result;
	}
    @Override
    public AccountUserDo getByIdNo(String idNo) {
        return userDao.getByIdNo(idNo);
    }

	@Override
	public AccountUserDo findUserByColorid(String colorid) {
		return userDao.findUserByColorid(colorid);
	}

	@Override
	public void updateColourlifeInfo(Long id, Long colourId, Long cid, String cname, String caddress) {
		userDao.updateColourlifeInfo(id,colourId,  cid,  cname,  caddress);
		
	}
}
