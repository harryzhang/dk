package com.hehenian.biz.common.identifycode;

import java.io.IOException;
import java.util.Map;

/**
 * 验证码生成器
 * @author zhangyunhmf
 *
 */
public interface IIdentifyCodeService {

	/**
	 * 生成验证码,默认4位长度
	 * 
	 * @return
	 * @throws IOException
	 */
    public String generateIdentifyCode();

	    /**
     * 生成验证码
     * 
     * @param identifyCodeLength
     *            验证码的位数
     * @return
     */
    public String generateIdentifyCode(int identifyCodeLength);

    /**
     * 给手机发送验证码
     * 
     * @param mobile
     *            手机号
     * @return
     * @author: zhangyunhmf
     * @date: 2014年12月22日下午4:44:27
     */
    public String sendIdentifyCode(String mobile);
    
    
    /**
     * 发送手机验证码（新接口）
     * @Description: TODO
     * @param mobile
     * @param userId
     * @param bizType
     * @return
     * @author: chenzhpmf
     * @date 2015-6-15 下午3:25:44
     */
    public String sendSmsCode(String mobile,String bizType);

    /**
     * 验证验证码是否ok
     * 
     * @param mobile
     * @param identifyCode
     * @return
     * @author: zhangyunhmf
     * @date: 2014年12月22日下午4:47:04
     */
    public boolean checkIdentifyCode(String mobile, String identifyCode);
    
    /**
     * 根据业务类型校验验证码
     * @Description: TODO
     * @param bizType
     * @param userId
     * @param code
     * @return
     * @author: chenzhpmf
     * @date 2015-6-15 下午2:47:38
     */
    public boolean checkSmsCodeByBiz(String mobile,String bizType,String code);

    /**
     * 绑定银行卡 ，向银行卡随机充值
     * 
     * @param userId
     *            用户id
     * @param userRealName
     *            用户真实姓名
     * @param bankCode
     *            银行卡号
     * @param bankType
     *            银行标识，哪家银行
     * @return 00表示成功 01 数据无效 02 系统繁忙重试 03银行卡被锁定 04 达到最多5次 05 次卡已经绑定过不能重复绑定
     * @author: zhangyunhmf
     * @date: 2015年1月15日下午4:42:00
     */
    public Map<String, String> sendBankIdentifyCode(long userId, String userRealName, String bankCode, String bankType);

    /**
     * 验证卡的时候调用
     * 
     * @param userId
     *            用户id
     * @param bankCode
     *            银行卡号
     * @param bankType
     *            银行标识，哪家银行
     * @param amount
     *            验证金额
     * @return 00表示成功 01 数据无效 02 系统繁忙重试 03银行卡被锁定 07 验证码不正确 08 请重新获取验证
     * @author: zhangyunhmf
     * @date: 2015年1月15日下午4:52:14
     */
    public String checkBankIdentifyCode(long userId, String bankCode, String bankType, String amount);

}
