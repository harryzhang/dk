package com.hehenian.biz.facade.notify.montnets.mwgate.common;

public class MULTIX_MT
{
	private String strUserMsgId;/*用户自定义的消息编号*/
	private String strSpNumber;/*通道,可填完整,可不填,可填*,可只填扩展*/
	private String strMobile;/*手机号*/
	private String strBase64Msg;/*短信内容,需为base64编码,编码前为GBK*/
	public String getStrUserMsgId()
	{
		return strUserMsgId;
	}
	public void setStrUserMsgId(String strUserMsgId)
	{
		this.strUserMsgId = strUserMsgId;
	}
	public String getStrSpNumber()
	{
		return strSpNumber;
	}
	public void setStrSpNumber(String strSpNumber)
	{
		this.strSpNumber = strSpNumber;
	}
	public String getStrMobile()
	{
		return strMobile;
	}
	public void setStrMobile(String strMobile)
	{
		this.strMobile = strMobile;
	}
	public String getStrBase64Msg()
	{
		return strBase64Msg;
	}
	public void setStrBase64Msg(String strBase64Msg)
	{
		this.strBase64Msg = strBase64Msg;
	}
	
}
