package com.sp2p.action.front;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import chinapnr.SecureLink;

import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;

/**
 * 用户注册
 * 
 * @author
 * 
 */
public class HFPayAction extends BaseFrontAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(HFPayAction.class);


	public String getPayKeyValue(){

		String MerCustId = SqlInfusion.FilteSqlInfusion(paramMap.get("merCustId"));
		String MerData =SqlInfusion.FilteSqlInfusion(paramMap.get("merData"));
		String MerKeyFile = this.getClass().getClassLoader().getResource("").getPath()+ "MerPrK530044.key";
		SecureLink sl = new SecureLink();

		int ret=sl.SignMsg(MerCustId,MerKeyFile,MerData);
		
		if(ret!=0) 
		{
			return "0";
		}
		JSONObject json =new JSONObject();
		String ChkValue = sl.getChkValue();
		json.put("msg", ChkValue);
		System.out.println(ChkValue);
		try {
			JSONUtils.printObject(json);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String resultUrlParam(){
		String CmdId =SqlInfusion.FilteSqlInfusion((request().getParameter("CmdId")+"").trim());
		String RespCode =SqlInfusion.FilteSqlInfusion((request().getParameter("RespCode")+"").trim());
		String RespDesc =SqlInfusion.FilteSqlInfusion((request().getParameter("RespDesc")+"").trim());
		String MerCustId =SqlInfusion.FilteSqlInfusion((request().getParameter("MerCustId")+"").trim());
		String UsrId =SqlInfusion.FilteSqlInfusion((request().getParameter("UsrId")+"").trim());
		String UsrCustId =SqlInfusion.FilteSqlInfusion((request().getParameter("UsrCustId")+"").trim());
		String BgRetUrl =SqlInfusion.FilteSqlInfusion((request().getParameter("BgRetUrl")+"").trim());
		String TrxId =SqlInfusion.FilteSqlInfusion((request().getParameter("TrxId")+"").trim());
		String RetUrl =SqlInfusion.FilteSqlInfusion((request().getParameter("RetUrl")+"").trim());
		String MerPriv =SqlInfusion.FilteSqlInfusion((request().getParameter("MerPriv")+"").trim());
		String ChkValue =SqlInfusion.FilteSqlInfusion((request().getParameter("ChkValue")+"").trim());
		
		

		request().setAttribute("CmdId", CmdId);
		request().setAttribute("RespCode", RespCode);
		request().setAttribute("RespDesc", RespDesc);
		request().setAttribute("MerCustId", MerCustId);
		request().setAttribute("UsrId", UsrId);
		request().setAttribute("UsrCustId", UsrCustId);
		request().setAttribute("BgRetUrl", BgRetUrl);
		request().setAttribute("TrxId", TrxId);
		request().setAttribute("RetUrl", RetUrl);
		request().setAttribute("MerPriv", MerPriv);
		request().setAttribute("ChkValue", ChkValue);
		request().setAttribute("BgRetUrl", BgRetUrl);
		
		return SUCCESS;
	}
    
}
