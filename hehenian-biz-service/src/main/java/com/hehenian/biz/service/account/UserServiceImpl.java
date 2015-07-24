package com.hehenian.biz.service.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IUserInfoService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.InviteCodeDo;
import com.hehenian.biz.common.account.dataobject.LoginInfoRelate;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.account.IPhoneVerifyComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.component.trade.IOperationLogComponent;
import com.hehenian.biz.component.trade.IRepaymentComponent;

@Service("userService")
public class UserServiceImpl implements IUserService {

    private final Logger           logger = Logger.getLogger(this.getClass());
    @Autowired
    private IInvestComponent       investComponent;
    @Autowired
    private IRepaymentComponent    repaymentComponent;

    private final static Logger    LOGGER = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private IUserComponent         userComponent;
    @Autowired
    private IOperationLogComponent operationLogComponent;
    @Autowired
    private IPhoneVerifyComponent  phoneVerifyComponent;
    @Autowired
    private IUserInfoService       userInfoService;
    
    @Autowired
    private IPersonComponent personComponent;

    @Override
    public AccountUserDo getById(Long id) {
        return userComponent.getById(id);
    }

    @Override
    public NPageDo<Map<String, Object>> queryUserFundRecords(Map<String, Object> searchItems) {
        try {
            NPageDo<Map<String, Object>> pageDo = new NPageDo<Map<String, Object>>();
            long count = userComponent.countUsers(searchItems);
            pageDo.setTotalCount(count);
            if (count == 0) {
                return pageDo;
            }

            List<Map<String, Object>> userFundRecordList = userComponent.queryUsers(searchItems);
            for (Map<String, Object> map : userFundRecordList) {
                map.put("usableSum", ((BigDecimal) map.get("usableSum")).doubleValue());
                map.put("freezeSum", ((BigDecimal) map.get("freezeSum")).doubleValue());
            }
            List<Long> userIdList = new ArrayList<Long>();
            for (Map<String, Object> map : userFundRecordList) {
                userIdList.add((Long) map.get("userId"));
            }

            // 查询投资的待收本金，待收利息，待收总额
            List<Map<String, Object>> userInvestList = investComponent.queryUserInvests(userIdList);
            for (Map<String, Object> map : userFundRecordList) {
                for (Map<String, Object> userInvestMap : userInvestList) {
                    if (((Long) map.get("userId")).longValue() == ((Long) userInvestMap.get("userId")).longValue()) {
                        map.put("dueinCapitalSum", ((BigDecimal) userInvestMap.get("dueinCapitalSum")).doubleValue());
                        map.put("dueinAccrualSum", ((BigDecimal) userInvestMap.get("dueinAccrualSum")).doubleValue());
                        map.put("dueinSum", ((BigDecimal) userInvestMap.get("dueinSum")).doubleValue());
                        break;
                    }
                }
            }

            // 查询待还金额
            List<Map<String, Object>> userRepaymentList = repaymentComponent.queryUserRepayments(userIdList);
            for (Map<String, Object> map : userFundRecordList) {
                for (Map<String, Object> userRepayment : userRepaymentList) {
                    if (((Long) map.get("userId")).longValue() == ((Long) userRepayment.get("userId")).longValue()) {
                        map.put("forRePaySum", ((BigDecimal) userRepayment.get("forRePaySum")).doubleValue());
                        break;
                    }
                }
            }

            pageDo.setModelList(userFundRecordList);
            return pageDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            NPageDo<Map<String, Object>> pageDo = new NPageDo<Map<String, Object>>();
            pageDo.setTotalCount(0l);
            return pageDo;
        }
    }

    @Override
    public AccountUserDo findUserByUserName(String username) {
        return userComponent.findUserByUserName(username);
    }

    @Override
    public AccountUserDo findUserByPhone(String mobilePhone) {
        return userComponent.findUserByPhone(mobilePhone);
    }

    @Override
    public IResult registerUser(AccountUserDo accountUserDo) {
        IResult result = new ResultSupport();
        AccountUserDo accountUserDo1 = userComponent.findUserByUserName(accountUserDo.getUsername());
        if (accountUserDo1 != null) {
            result.setSuccess(false);
            result.setErrorMessage("用户名已存在");
        } else {
        	if(accountUserDo.getMobilePhone().length()>0){
        		accountUserDo1 = userComponent.findUserByPhone(accountUserDo.getMobilePhone());
        	}else{
        		accountUserDo1=null;
        	}
            if (accountUserDo1 != null) {
                result.setSuccess(false);
                result.setErrorMessage("手机号已存在");
            } else{
            	if(accountUserDo.getColorid()!=-1L){
            		accountUserDo1 = userComponent.findUserByColorid(accountUserDo.getColorid()+"");
            	}else{
            		accountUserDo1=null;
            	}
            	if(accountUserDo1 != null){
            		result.setSuccess(false);
                    result.setErrorMessage("彩之云ID已存在");
            	}else {
	                try {
	                	if(accountUserDo.getColorid()==-1){
	                		accountUserDo.setColorid(null);
	                	}
	                    Long userId = userComponent.saveUser(accountUserDo);
	                    // 新注册业务：支持手机、账号及第三方登录
	                    if (userId != null) {
	                        LoginInfoRelate lir = null;
	                        // 如果账号和手机号一样，则以手机号为登录标识
	                        if (!accountUserDo.getUsername().equals(accountUserDo.getMobilePhone())) {
	                            lir = new LoginInfoRelate();
	                            lir.setLoginInfo(accountUserDo.getUsername());
	                            lir.setUserId(userId.intValue());
	                            lir.setType(1); // 普通用户名
	                            userInfoService.insert(lir);
	                        }
	                        if(accountUserDo.getMobilePhone().length()>0){
		                        lir = new LoginInfoRelate();
		                        lir.setLoginInfo(accountUserDo.getMobilePhone());
		                        lir.setUserId(userId.intValue());
		                        lir.setType(2); // 手机号码
		                        userInfoService.insert(lir);
	                        }
	                    }
	                    // end
	                    result.setSuccess(true);
	                    result.setModel(userId);
	                } catch (BusinessException e) {
	                    result.setSuccess(false);
	                    result.setErrorMessage(e.getMessage());
	                } catch (Exception e) {
	                    LOGGER.error(e.getMessage(), e);
	                    result.setSuccess(false);
	                    result.setErrorMessage("保存用户信息失败");
	                }
            	}
            }
        }

        return result;
    }

    @Override
    public AccountUserDo loginWithPwd(String username, String password) {
        AccountUserDo accountUserDo = userComponent.findUserByUserNamePwd(username, password);
        return login(accountUserDo);
    }

    @Override public AccountUserDo findUserByUserNamePwd(String username, String password) {
    	return userComponent.findUserByUserNamePwd(username, password);
    }

    @Override
    public AccountUserDo loginWithId(Long userId) {
        AccountUserDo accountUserDo = userComponent.getById(userId);
        return login(accountUserDo);
    }

    private AccountUserDo login(AccountUserDo accountUserDo) {
        if (accountUserDo != null) {
            PhoneVerifyDo phoneVerify = phoneVerifyComponent.findPhoneVerify(accountUserDo.getId());
            if (phoneVerify != null) {
                accountUserDo.setPhoneHasVerify(true);
            } else {
                accountUserDo.setPhoneHasVerify(false);
            }
            // 记录用户登录日志
            operationLogComponent.addOperationLog("t_user", accountUserDo.getUsername(), 2, "", 0.0, "用户登录", 1);
        }
        return accountUserDo;
    }

    @Override
    public UserBindDo findUserBindByPartner(int partnerId, String partnerUserId) {
        return userComponent.findUserBindByPartner(partnerId, partnerUserId);
    }

    @Override
    public int saveUserBind(UserBindDo userBindDo) {
        return userComponent.saveUserBind(userBindDo);
    }

    private static final Random RANDOM = new Random();

    @Override
    public Long bindNewUser(AccountUserDo accountUserDo, UserBindDo userBindDo) {
        AccountUserDo userByUserName = userComponent.findUserByUserName(accountUserDo.getUsername());
        while (userByUserName != null) {
            accountUserDo.setUsername(userByUserName.getUsername() + RANDOM.nextInt(10));
            userByUserName = userComponent.findUserByUserName(accountUserDo.getUsername());
        }
        return userComponent.saveNewUserBind(accountUserDo, userBindDo);
    }

    @Override
    public UserBindDo findUserBindByUser(int partnerId, Long userId) {
        return userComponent.findUserBindByUser(partnerId, userId);
    }

    @Override
    public int updateUserPhone(Long id, String mobilePhone) {
        return userComponent.updateUserPhone(id, mobilePhone);
    }
    
    /**
     * 根据彩生活用户ID 获取用户ID
     * @param colorId
     * @return
     */
    @Override
	public AccountUserDo getUserByColorId(Long colorId){
    	return userComponent.getUserByColorId(colorId);
    }

    @Override
	public int updatePersonPhone(Long id, String mobilePhone) {
		return userComponent.updatePersonPhone(id, mobilePhone);
	}

    @Override
    public InviteCodeDo findInviteCodeByDO(InviteCodeDo inviteCode) {
        return userComponent.findInviteCodeByDO(inviteCode);
    }

    @Override
    public int updateInviteCode(InviteCodeDo inviteCode) {
        return userComponent.updateInviteCode(inviteCode);
    }

    @Override
    public int updateUserPassword(Long userId, String password, String type) {
        return userComponent.updateUserPassword(userId, password, type);
    }
    
    @Override
    public int updatePayPassword(Long userId, String password) {
        return userComponent.updatePayPassword(userId, password);
    }
    
    

    @Override
    public AccountUserDo getByIdNo(String idNo) {
        return userComponent.getByIdNo(idNo);
    }


	@Override
	public AccountUserDo findUserByEmail(String email) {
		return this.userComponent.findUserByEmail(email);
	}


	@Override
	public long setReferee(String recommendId, String userId) {
		return this.userComponent.setReferee(recommendId, userId);
	}


	@Override
	public void saveUserReffer(String reffer, Long userId) {
		this.userComponent.saveUserReffer(reffer, userId);
	}


	@Override
	public long updateUserUsrCust(Long userId, Long usrCustId, String email, String idNo, String realName) {
		return this.userComponent.updateUserUsrCust(userId, usrCustId, email, idNo, realName);
	}

	@Override
	public void joinHyh(String realName, String idNo, long userId) {
		
	}
	
	
	/**
	  * 自动注册 登录
	  * @param refferee 推荐人ID ， 没有给 -1
	  * @param userName  注册用户名
	  * @param mobilePhone 注册手机
	  * @param pwd         密码
	  * @param source      来源， 100 代表贷款端用户
	  * @param colorid 彩之云ID
	  * @return AccountUserDo 用户对象
	  */
	 @Override
	public IResult<AccountUserDo>  register(long refferee, String userName, String mobilePhone,String pwd, int source , long colorid) {
	        
		    IResult<AccountUserDo> result = null;
		    
		    //处理参数
	    	if(StringUtils.isBlank(userName)) {
	    		userName = "user_"+mobilePhone;
	    	}
	    	if(StringUtils.isBlank(pwd)) {
	    		pwd =  pwd;
	    	}
	    	if(source == 0 ) {
	    		source = 100;
	    	}
	    	if(refferee == 0 ) {
	    		refferee = -1;
	    	}
	    	if(colorid == 0) {
	    		colorid = -1;
	    	}
		    //end 处理参数
		    
	        AccountUserDo accountUserDo = new AccountUserDo();
	        accountUserDo.setUsername(userName);
	        accountUserDo.setMobilePhone(mobilePhone);
	        accountUserDo.setPassword(pwd);
	        accountUserDo.setDealpwd(pwd);
	        accountUserDo.setColorid(colorid);
	        accountUserDo.setVipStatus(2);
	        accountUserDo.setVipCreateTime(new Date());
	        //处理推荐人
	        if (refferee > 0) {
	            if (userComponent.getById(refferee) == null) {
	                AccountUserDo accountUserDo1 = userComponent.findUserByPhone(refferee + "");
	                if (accountUserDo1 != null) {
	                    refferee = accountUserDo1.getId();
	                } else {
	                	result.setSuccess(false);
	                	result.setErrorMessage( "推荐人不存在，请重新填写");
	                	result = new ResultSupport<AccountUserDo>(false);
	                	return result;
	                }
	            }
	        }
	        accountUserDo.setReffer(refferee + "");
	        accountUserDo.setCreateTime(new Date());
	        accountUserDo.setSource(source);

	    
	        //注册
	        result = registerUser(accountUserDo);
	        //写登录日志
	        if (!result.isSuccess()) {
	        	if(result.getErrorMessage().equals("用户名已存在")){
	        		accountUserDo=userComponent.findUserByUserName(userName);
	        	}else if (result.getErrorMessage().equals("手机号已存在")){
	        		accountUserDo=userComponent.findUserByPhone(mobilePhone);
	        	}else if (result.getErrorMessage().equals("彩之云ID已存在")){
	        		accountUserDo=userComponent.findUserByColorid(colorid+"");
	        	}
	        } 
	        accountUserDo = loginWithPwd(accountUserDo.getUsername(), accountUserDo.getPassword());
	        accountUserDo.setPerson(personComponent.getByUserId(accountUserDo.getId()));
            result.setModel(accountUserDo);
	        
	        return result;
	    }
	 
	
	 /**
	  * 自动注册后更新个人信息
	  * @param user AccountUserDo对象
	  * @param realName 真实姓名
	  * @param idNo 身份证号码
	  * @param mobile 手机
	  * @return
	  */
	 @Override
	public PersonDo  updatePerson(AccountUserDo user, String realName, String idNo , String mobile) {
		 PersonDo person = new PersonDo();
		 person.setUserId(user.getId());
		 person.setRealName(realName);
		 person.setIdNo(idNo);
		 person.setTelephone(mobile);
		 person.setCellPhone(mobile);
		 personComponent.updatePersonByUserId(person);
		 return person;
	 }
	 
	 /**
	  * 自动注册后插入彩生活信息到t_colourlife_info
	  * @param user AccountUserDo对象
	  * @param colourId 彩生活id
	  * @param cid 小区id
	  * @param cname 小区名称
	  * @param caddress 小区地址
	  * @return
	  */
	 @Override
	 public void updateColourlifeInfo(Long id, Long colourId, Long cid, String cname, String caddress) {
		 userComponent.updateColourlifeInfo(id,colourId,cid,cname,caddress);
	 }
}
