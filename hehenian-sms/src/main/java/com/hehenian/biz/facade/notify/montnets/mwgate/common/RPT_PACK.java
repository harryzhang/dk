package com.hehenian.biz.facade.notify.montnets.mwgate.common;

public class RPT_PACK
{
	private String strMoTime; /*状态报告时间*/
	private String strPtMsgId;/*消息编号*/
	private String strMobile; /*手机号*/
	private String strSpNumber; /*通道号*/
	private String strUserMsgId;     /*用户自定义的消息编号*/
	private String strReserve;  /*预留字段*/
	private int nStatus;		/*发送状态,0:成功 非0:失败*/
	private String strErCode;	/*状态报告错误代码*/
	public String getStrMoTime()
	{
		return strMoTime;
	}
	public void setStrMoTime(String strMoTime)
	{
		this.strMoTime = strMoTime;
	}
	public String getStrPtMsgId()
	{
		return strPtMsgId;
	}
	public void setStrPtMsgId(String strPtMsgId)
	{
		this.strPtMsgId = strPtMsgId;
	}
	public String getStrMobile()
	{
		return strMobile;
	}
	public void setStrMobile(String strMobile)
	{
		this.strMobile = strMobile;
	}
	public String getStrSpNumber()
	{
		return strSpNumber;
	}
	public void setStrSpNumber(String strSpNumber)
	{
		this.strSpNumber = strSpNumber;
	}
	public String getStrUserMsgId()
	{
		return strUserMsgId;
	}
	public void setStrUserMsgId(String strUserMsgId)
	{
		this.strUserMsgId = strUserMsgId;
	}
	public String getStrReserve()
	{
		return strReserve;
	}
	public void setStrReserve(String strReserve)
	{
		this.strReserve = strReserve;
	}
	public int getnStatus()
	{
		return nStatus;
	}
	public void setnStatus(int nStatus)
	{
		this.nStatus = nStatus;
	}
	public String getStrErCode()
	{
		return strErCode;
	}
	public void setStrErCode(String strErCode)
	{
		this.strErCode = strErCode;
	}
	
	
}
