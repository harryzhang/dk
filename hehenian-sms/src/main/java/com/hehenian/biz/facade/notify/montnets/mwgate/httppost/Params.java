package com.hehenian.biz.facade.notify.montnets.mwgate.httppost;

/**
 * get与post请求传参数实体类
 * @author Administrator
 *
 */
public class Params {
	private String userId;  //用户账号
	private String password;  //用户密码
	private String pszMobis;   //目标号码，用英文逗号(,)分隔，最大100个号码。一次提交的号码类型不受限制，但手机会做验证，若有不合法的手机号将会被退回。号码段类型分为：移动、联通、电信手机 注意：请不要使用中文的逗号
	private String pszMsg;     //短信内容， 内容长度不大于350个汉字
	private String iMobiCount;  //号码个数（最大100个手机）
	private String pszSubPort;  //子端口号码，不带请填星号{*} 长度由账号类型定4-6位，通道号总长度不能超过20位。如：10657****主通道号，3321绑定的扩展端口，主+扩展+子端口总长度不能超过20位。
	private String MsgId;  //一个8字节64位的大整型（INT64），格式化成的字符串。因此该字段必须为纯数字，且范围不能超过INT64的取值范围（-(2^63）……2^63-1）
	                       //    即-9223372036854775807……9223372036854775808。
	                       // 格式化成字符串后最大长度不超过20个字节。
	
	private String iReqType;  //请求类型(0: 上行&状态报告 1:上行 2: 状态报告)
	
	private String  Sa;  // 扩展号
	
	private String multixmt;//批量短信请求包。该字段中包含N个短信包结构体。每个结构体间用固定的分隔符隔开。
							//	N的范围为1~200.
							//	分隔符为英文逗号(,).
							//	单个短信包结构体的内容见下表.
	

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPszMobis() {
		return pszMobis;
	}
	public void setPszMobis(String pszMobis)
	{
		this.pszMobis = pszMobis;
	}
	public String getPszMsg() {
		return pszMsg;
	}
	public void setPszMsg(String pszMsg)
	{
		this.pszMsg = pszMsg;
	}
	public String getIMobiCount() {
		return iMobiCount;
	}
	public void setIMobiCount(String iMobiCount)
	{
		this.iMobiCount = iMobiCount;
	}
	
	public String getPszSubPort() {
		return pszSubPort;
	}
	public void setPszSubPort(String pszSubPort)
	{
		this.pszSubPort = pszSubPort;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String MsgId)
	{
		this.MsgId = MsgId;
	}
	public String getIReqType() {
		return iReqType;
	}
	public void setIReqType(String iReqType)
	{
		this.iReqType = iReqType;
	}
	
	public void setSa(String sa)
	{
		this.Sa = sa;
	}
	public String getSa()
	{
		return this.Sa;
	}
	
	public void setMultixmt(String multix_mt)
	{
		this.multixmt = multix_mt;
	}
	public String getMultixmt()
	{
		return multixmt;
	}
	
}
