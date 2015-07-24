package com.hehenian.biz.facade.notify.montnets.mwgate.httppost;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

import com.hehenian.biz.facade.notify.montnets.mwgate.common.ISms;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.MO_PACK;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.MULTIX_MT;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.RPT_PACK;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.StaticValue;
import com.hehenian.biz.facade.notify.montnets.mwgate.common.ValidateParamTool;


/**
 * post请求实现类
 * @author Administrator
 *
 */
public class CHttpPost implements ISms {

	public List<MO_PACK> GetMo(String strUserId, String strPwd, boolean bKeepAlive, Object connection)
	{
		List<MO_PACK> moPackList=null;
		try {
			String[] result = null;			
			Params p = new Params();
			p.setUserId(strUserId);
			p.setPassword(strPwd);
			p.setIReqType("1");
			
			result = new SmsUtil().MongateGetDeliverForPost(p,bKeepAlive,connection);
			
			if(result != null){
				moPackList=new ArrayList<MO_PACK>();
				for (int i = 0; i < result.length; i++)
				{
					String[] resultArr=result[i].split(",");
					//数组长度大于等于7才算合法
					if(resultArr.length>=7){
						MO_PACK moPack=new MO_PACK();
						moPack.setStrMoTime(resultArr[1]);
						moPack.setStrMobile(resultArr[2]);
						moPack.setStrSpNumber(resultArr[3]);
						moPack.setStrExNo(resultArr[4]);
						moPack.setStrReserve(resultArr[5]);
					
						//处理上行信息，信息中可能出现英文逗号
						int start=0;
						int index=0;
						while(index<6){
							start+=resultArr[index].length()+1;
							index++;
						}
						moPack.setStrMessage(result[i].substring(start, result[i].length()));
						
						moPackList.add(moPack);
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("出现了异常，访问地址配置错误！");
			e.printStackTrace();
		}	
		return moPackList;
	}
	
	public List<RPT_PACK> GetRpt(String strUserId, String strPwd, boolean bKeepAlive, Object connection)
	{
		List<RPT_PACK> rptPackList=null;
		try {
			String[] result = null;			
			Params p = new Params();
			p.setUserId(strUserId);
			p.setPassword(strPwd);
			p.setIReqType("2");
			
		    result = new SmsUtil().MongateGetDeliverForPost(p,bKeepAlive,connection);
		    
		    if(result != null)
			{
				rptPackList=new ArrayList<RPT_PACK>();
				for (int i = 0; i < result.length; i++)
				{
					String[] resultArr=result[i].split(","); 
					RPT_PACK rptPack=new RPT_PACK();
					rptPack.setStrMoTime(resultArr[1]);
					rptPack.setStrPtMsgId(resultArr[2]);
					rptPack.setStrSpNumber(resultArr[3]);
					rptPack.setStrMobile(resultArr[4]);
					rptPack.setStrUserMsgId(resultArr[5]);
					rptPack.setStrReserve(resultArr[6]);
					rptPack.setnStatus(Integer.parseInt(resultArr[7]));
					rptPack.setStrErCode(resultArr[8]);
					rptPackList.add(rptPack);
				}
			}
			
		} catch (Exception e) {
			System.out.println("出现了异常，访问地址配置错误！");
			e.printStackTrace();
		}	
		return rptPackList;
	}
	
	public int QueryBalance(StringBuffer nBalance, String strUserId, String strPwd, boolean bKeepAlive, Object connection)
	{
		Integer result=null;
		try {	
			Params p = new Params();
			
			//验证账号
			if(!ValidateParamTool.validateUserId(strUserId)){
				nBalance.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setUserId(strUserId);
			
			//验证密码
			if(!ValidateParamTool.validatePwd(strPwd)){
				nBalance.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setPassword(strPwd);
			
			result = new SmsUtil().MongateQueryBalanceForPost(p,bKeepAlive,connection);
		} catch (Exception e) {
			System.out.println("出现了异常，访问地址配置错误！");
			e.printStackTrace();
		}
		if(result==null){
			nBalance.append("-1");
			result=-1;
		}else if(result>=0){
			nBalance.append(result);
			result=0;
		}else{
			nBalance.append(result);
		}
		return result.intValue();
	}

	public int SendMultixSms(StringBuffer strPtMsgId, String strUserId, String strPwd, List<MULTIX_MT> MultixMt, boolean bKeepAlive, Object connection)
	{
		int returnInt=-200;
		try {
			String result = null;
			StringBuffer multixmt =new StringBuffer();
			
			Params p = new Params();
			
			//验证账号
			if(!ValidateParamTool.validateUserId(strUserId)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setUserId(strUserId);
			
			//验证密码
			if(!ValidateParamTool.validatePwd(strPwd)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setPassword(strPwd);

			//验证MultixMt是否合法，如果MultixMt大于0并且小于101，则合法。
			if(MultixMt!=null&&MultixMt.size()>0&&MultixMt.size()<101){
				for(int j=0;j<MultixMt.size();j++)
				{
					MULTIX_MT multixMt = MultixMt.get(j);
					
					//流水号
					//验证流水号，不合法就返回
					if(!ValidateParamTool.validateUserMsgId(multixMt.getStrUserMsgId())){
						strPtMsgId.append(StaticValue.PARAM_ERROR);
						return StaticValue.PARAM_ERROR;
					}
					multixmt.append(multixMt.getStrUserMsgId());
					multixmt.append("|");
					
					//通道号
					//验证通道号，不合法就返回
					if(!ValidateParamTool.validateSpNumber(multixMt.getStrSpNumber())){
						strPtMsgId.append(StaticValue.PARAM_ERROR);
						return StaticValue.PARAM_ERROR;
					}
					multixmt.append(multixMt.getStrSpNumber());
					multixmt.append("|");
					
					//手机号
					//验证手机号码，不合法就返回
					if(!ValidateParamTool.validateMobile(multixMt.getStrMobile())){
						strPtMsgId.append(StaticValue.PARAM_ERROR);
						return StaticValue.PARAM_ERROR;
					}
					multixmt.append(multixMt.getStrMobile()).append("|");	
					
					//短信内容
					//验证短信内容，不合法就返回
					if(!ValidateParamTool.validateMessage(multixMt.getStrBase64Msg())){
						strPtMsgId.append(StaticValue.PARAM_ERROR);
						return StaticValue.PARAM_ERROR;
					}
					String strBase64Msg = new String(Base64.encodeBase64(multixMt.getStrBase64Msg().getBytes("GBK")));	
					
					multixmt.append(strBase64Msg).append(",");					
				}
			}else{
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			String Multixmt = multixmt.substring(0,multixmt.length()-1);
			p.setMultixmt(Multixmt);
			
			result = new SmsUtil().MongateMULTIXSendForPost(p, bKeepAlive, connection);
			
			 if(result != null && !"".equals(result)&&result.length()>10){
				returnInt=0;
				strPtMsgId.append(result);
			 }else if(result==null||"".equals(result)){
				strPtMsgId.append(returnInt);
			 }else{
				returnInt=Integer.parseInt(result);
				strPtMsgId.append(returnInt);
			 }
			 
		} catch (Exception e) {
			returnInt=-200;
			strPtMsgId.append(returnInt);
			e.printStackTrace();
		}
		return returnInt;
	}
	
	public int SendSms(StringBuffer strPtMsgId, String strUserId, String strPwd, String strMobiles, String strMessage, String strSubPort, String strUserMsgId, boolean bKeepAlive, Object connection)
	{
		int returnInt=-200;
		try {
			String result = null;
		
			Params p = new Params();
			
			//验证账号
			if(!ValidateParamTool.validateUserId(strUserId)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setUserId(strUserId);
			
			//验证密码
			if(!ValidateParamTool.validatePwd(strPwd)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setPassword(strPwd);
			
			//验证手机号码,不合法就返回
			if(!ValidateParamTool.validateMobiles(strMobiles)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setPszMobis(strMobiles);
			
			//验证短信内容，不合法就返回
			if(!ValidateParamTool.validateMessage(strMessage)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setPszMsg(strMessage);
			
			p.setIMobiCount(String.valueOf(strMobiles.split(",").length));
			
			//验证扩展子号是否合法，不合法就返回
			if(!ValidateParamTool.validateSubPort(strSubPort)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setPszSubPort(strSubPort);
			
			//验证流水号，不合法就返回
			if(!ValidateParamTool.validateUserMsgId(strUserMsgId)){
				strPtMsgId.append(StaticValue.PARAM_ERROR);
				return StaticValue.PARAM_ERROR;
			}
			p.setMsgId(strUserMsgId);
			
			result =new SmsUtil().MongateSendSubmitForPost(p,bKeepAlive,connection);
			
			if(result != null && !"".equals(result)&&result.length()>10){
				returnInt=0;
				strPtMsgId.append(result);
			}else if(result==null||"".equals(result)){
				strPtMsgId.append(returnInt);
			}else{
				returnInt=Integer.parseInt(result);
				strPtMsgId.append(returnInt);
			}
		} catch (Exception e) {
			returnInt=-200;
			strPtMsgId.append(returnInt);
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
}
