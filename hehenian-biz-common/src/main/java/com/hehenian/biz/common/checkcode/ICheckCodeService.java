package com.hehenian.biz.common.checkcode;

/**
 * 验证码生成器
 * @author zhangyunhmf
 *
 */
public interface ICheckCodeService {

	/**
	 * 生成验证码,默认4位长度
	 * 
	 * @return
	 * @throws IOException
	 */
	public String generateCheckCode();

	/**
	 * 生成验证码
	 * 
	 * @param checkCodeLength
	 *            验证码的位数
	 * @return
	 */
	public String generateCheckCode(int checkCodeLength);

}
