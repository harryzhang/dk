package com.hehenian.biz.facade.notify.montnets.mwgate.common;

import java.util.List;

public interface ISms
{
	
	
	/**
	 * 1.短信息发送接口（相同内容群发，可自定义流水号）
	 * @param strPtMsgId 	平台返回的流水号
	 * @param strUserId  	帐号,长度最大6个字节
	 * @param strPwd 		密码,长度最大32个字节
	 * @param strMobiles 	手机号,用英文逗号(,)分隔，最大100个号码。每次请求所提交的号码段类型不受限制，但系统会对每个手机的合法性做验证，若有不合法的手机号将会被系统退回，以失败状态报告的形式返回。号码段类型分为：移动、联通、电信手机 ,注意：分隔符是英文逗号，请不要使用中文的逗号
	 * @param strMessage 	短信内容, 最大支持350个字。这里，一个英文字母，一个汉字都视为一个字。
	 * @param strSubPort 	扩展子号,不需要扩展子号请填星号“*”，长度不能超过6位，通道号总长度不能超过20位。
	 * @param strUserMsgId 	用户自编流水号,一个8字节64位的大整型（INT64），格式化成的字符串。因此该字段必须为纯数字，且范围不能超过INT64的取值范围（-2^63……2^63-1）
	 *						即-9223372036854775808……9223372036854775807。格式化成字符串后最大长度不超过20个字节。
	 * @param bKeepAlive 	长连接或短连接,默认短连接,false短连接,true长连接
	 * @param connection 	连接对象,如果是短连接，传null
	 * @return 				0:成功 非0:返回webservice接口返回的错误代码,返回值：
	 *						错误描述对应说明
	 *						发送成功：平台消息编号，如：-8485643440204283743或1485643440204283743
	 *						错误返回： 
	 *						-1 		参数为空。信息、电话号码等有空指针，登陆失败
	 *						-12		有异常电话号码
	 *						-13		 电话号码个数与实际个数不相等 
	 *						-14		实际号码个数超过100
	 *						-999	web服务器内部错误
	 */
	public int SendSms(StringBuffer strPtMsgId, String strUserId, String strPwd, String strMobiles, String strMessage, String strSubPort, String strUserMsgId, boolean bKeepAlive,Object connection);
	
	
	/**
	 * 2.短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）
	 * @param strPtMsgId 	平台返回的流水号
	 * @param strUserId 	帐号,长度最大6个字节
	 * @param strPwd 		密码,长度最大32个字节
	 * @param MultixMt 		批量请求包,该字段中包含N个短信包结构体。每个结构体间用固定的分隔符隔开。N的范围为1~100.分隔符为英文逗号(,).单个短信包结构体的内容见下表.
	 *                      自定义流水号   该流水号为自该短信包的唯一ID编号，可用于短信状态返回时的状态匹配。其取值范围及定义与MongateSendSubmit接口中的MsgId相同，务必遵从。若不需要请填0。
	 *                      分隔符               |
	 *                      通道号                这里可以填写完整的通道号或扩展子号或*号或空。填*号或空时表示不扩展。填写扩展子号时要注意扩展子号位数+主通道位数不能超过通道最大长度（20位）
	 *                      分隔符               |
	 *                      手机号                11位手机号码
	 *                      分隔符                |
	 *                      短信内容             采用base64编码。Base64(gbk编码的短信内容)。GBK编码的短信内容最大支持350个字。一个英文字母，一个汉字都视为一个字。
	 *          例：对手机号13800138000，使用通道号10657120395789 41，发送一条流水号为457894132578945的内容为“你好,欢迎使用!”的短信。其短信包为：
	 *		流水号+完整的通道接入号
	 *		“457894132578945|1065712039578941|13800138000|xOO6wyy7ttOtyrnTwyE=”
	 *		流水号+通道扩展子号
	 *		“457894132578945|41|13800138000|xOO6wyy7ttOtyrnTwyE=”
	 *		不需扩展
	 *		“457894132578945|*|13800138000|xOO6wyy7ttOtyrnTwyE=”
	 *		不需流水号
	 *		“0|1065712039578941|13800138000|xOO6wyy7ttOtyrnTwyE=”
	 *		“0|41|13800138000|xOO6wyy7ttOtyrnTwyE=”
	 *		不需扩展不需流水号
	 *		“0|*|13800138000|xOO6wyy7ttOtyrnTwyE=”
	 *		多个短信单包结构体中间用英文逗号分隔，可组合成一个短信包(multixmt)，例如：
	 *		“457894132578945|1065712039578941|13800138000|xOO6wyy7ttOtyrnTwyE=,457894132578946|*|13900138000|xOO6wyy7ttOtyrnTwyE=,0|*|13700138000|xOO6wyy7ttOtyrnTwyE=,0|44|13600138000|xOO6wyy7ttOtyrnTwyE=,……”
	 * @param bKeepAlive 	长连接或短连接,默认短连接,false短连接,true长连接
	 * @param connection 	连接对象,如果是短连接，传null
	 * @return 				0:成功 非0:返回webservice接口返回的错误代码, 返回值：
	 *						错误描述对应说明
	 *						发送成功：信息编号，如：-8485643440204283743或1485643440204283743
	 *						错误返回： 
	 *						-1 	参数为空。信息、电话号码等有空指针，登陆失败
	 *						-12	有异常电话号码
	 *						-999	web服务器内部错误
	 */
	public int SendMultixSms(StringBuffer strPtMsgId,String strUserId, String strPwd, List<MULTIX_MT> MultixMt, boolean bKeepAlive,Object connection);
	
	
	/**
	 * 3.查询余额接口
	 * @param nBalance 		帐号费用
	 * @param strUserId 	用户帐号,长度最大6个字节
	 * @param strPwd 		用户密码，长度最大32个字节
	 * @param bKeepAlive 	长连接或短连接,默认短连接,false短连接,true长连接
	 * @param connection 	连接对象,如果是短连接，传null
	 * @return 				0:成功 非0:返回webservice接口返回的错误代码,
	 * 						正数或0：帐户可发送条数
	 *						负数：登陆失败
	 *						其他错误见附录
	 */
	public int QueryBalance(StringBuffer nBalance, String strUserId, String strPwd, boolean bKeepAlive,Object connection);
	
	
	/**
	 * 4.获取上行
	 * @param strUserId 帐号
	 * @param strPwd 密码
	 * @param bKeepAlive 长连接或短连接,默认短连接,false短连接,true长连接
	 * @param connection 连接对象,如果是短连接，传null
	 * @return 返回上行集合
	 */
	public List<MO_PACK> GetMo(String strUserId, String strPwd, boolean bKeepAlive,Object connection);
	
	
	/**
	 * 5.状态报告
	 * @param strUserId 帐号
	 * @param strPwd 密码
	 * @param bKeepAlive 长连接或短连接,默认短连接,false短连接,true长连接
	 * @param connection 连接对象,如果是短连接，传null
	 * @return 返回状态报告集合
	 */
	public List<RPT_PACK> GetRpt(String strUserId, String strPwd, boolean bKeepAlive,Object connection);
	
	
}
