package com.hehenian.biz.common.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.loan.ILoanProtocolService;
import com.hehenian.biz.common.loan.dataobject.ContactVO;
import com.hehenian.biz.common.loan.dataobject.LoanProtocolDo;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.common.utils.SpringHelper;


public class ContactCreateTask implements Runnable {

	private static final Logger logger = Logger.getLogger(ContactCreateTask.class);
	
	@Autowired
	private  ILoanProtocolService loanProtocolService = SpringHelper.getBean("loanProtocolService");
	
	private ContactVO contactVO;
	public ContactCreateTask(ContactVO vo){
		this.contactVO= vo;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String url =contactVO.getUrl();//+"?reqTmplate="+contactVO.getReqTmplate()
		Map map = new HashMap();
		map.put("reqTmplate", contactVO.getReqTmplate());
		map.put("realName", contactVO.getRealName());
		map.put("idNo", contactVO.getIdNo());
		map.put("cmd", contactVO.getCmd());
		map.put("fileName", contactVO.getFilePrefix()+".pdf");
		map.put("req_save_path", contactVO.getSavePath());
		map.put("userId", contactVO.getUserId());
        map.put("lendUserName", contactVO.getLendUserName());		
        map.put("lendIdNo", contactVO.getLendIdNo());		
        map.put("borrowerName", contactVO.getBorrowerName());		
        map.put("borrowerIdNo", contactVO.getBorrowerIdNo());		
        map.put("loanAmount", contactVO.getLoanAmount());		
        map.put("repayType", contactVO.getRepayType());		
        map.put("yearRate", contactVO.getYearRate());		
        map.put("loanPeriod", contactVO.getLoanPeriod());		
        map.put("loanDay", contactVO.getLoanDay());		
        map.put("repayDay", contactVO.getRepayDay());		
        map.put("limitTime", contactVO.getLimitTime());		
        map.put("account", contactVO.getAccount());		
        map.put("loanUsage", contactVO.getLoanUsage());	
        if(StringUtils.isNotBlank(contactVO.getCreateDate())){
        	String arrLoan[] = contactVO.getCreateDate().split("/");
        	map.put("conYear", arrLoan[0]);
        	map.put("conMonth", arrLoan[1]);
        	map.put("conDay", arrLoan[2]);
        }
        map.put("filePrefix", contactVO.getFilePrefix());
        map.put("procedure", contactVO.getProcedure());
        map.put("scale", contactVO.getScale());
        map.put("advance", contactVO.getAdvance());
		try {
			String str = HttpClientUtils.getCallBack(url, map);
			logger.info("result:"+str);
			if("1".equals(str)){
				LoanProtocolDo loanProtocolDo = new LoanProtocolDo();
				loanProtocolDo.setFilePath(contactVO.getSavePath());
				loanProtocolDo.setFileName(map.get("fileName").toString());
				loanProtocolDo.setFileType("PDF");
				loanProtocolDo.setProType("1");
				loanProtocolDo.setLoanId(contactVO.getLoanId());
				loanProtocolService.addLoanProtocol(loanProtocolDo);
				logger.info("生成PDF成功！param: 借款人="+contactVO.getRealName()+" 身份证="+contactVO.getIdNo()+"协议号="+contactVO.getFilePrefix());
			}else{
				logger.error("生成PDF失败!result="+str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
