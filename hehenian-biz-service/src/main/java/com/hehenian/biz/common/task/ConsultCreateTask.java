package com.hehenian.biz.common.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.loan.ILoanProtocolService;
import com.hehenian.biz.common.loan.dataobject.ConsultVO;
import com.hehenian.biz.common.loan.dataobject.LoanProtocolDo;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.common.utils.SpringHelper;

public class ConsultCreateTask implements Runnable{

	private static final Logger logger = Logger.getLogger(ConsultCreateTask.class);

	private ConsultVO consultVO;
	public ConsultCreateTask(ConsultVO vo){
		this.consultVO= vo;
	}
	
	@Autowired
	private  ILoanProtocolService loanProtocolService = SpringHelper.getBean("loanProtocolService");
	
	@Override
	public void run() {
		String url =consultVO.getUrl();//+"?reqTmplate="+consultVO.getReqTmplate()
		Map map = new HashMap();
		map.put("reqTmplate", consultVO.getReqTmplate());
		map.put("realName", consultVO.getRealName());
		map.put("idNo", consultVO.getIdNo());
		map.put("cmd", consultVO.getCmd());
		map.put("fileName", consultVO.getFilePrefix()+".pdf");
		map.put("req_save_path", consultVO.getSavePath());
		map.put("userId", consultVO.getUserId());		
        map.put("loanDay", consultVO.getLoanDay());		
  
        if(StringUtils.isNotBlank(consultVO.getCreateDate())){
        	String arrLoan[] = consultVO.getCreateDate().split("/");
        	map.put("conYear", arrLoan[0]);
        	map.put("conMonth", arrLoan[1]);
        	map.put("conDay", arrLoan[2]);
        }
        map.put("filePrefix", consultVO.getFilePrefix());
        
        map.put("address", consultVO.getAddress());
        map.put("compAddr", consultVO.getCompAddr());
        map.put("mobile", consultVO.getMobile());
        
        map.put("penalty", consultVO.getPenalty());
        map.put("credit", consultVO.getCredit());
        map.put("rate", consultVO.getRate());
		try {
			String str = HttpClientUtils.getCallBack(url, map);
			logger.info("result:"+str);
			if("1".equals(str)){
				LoanProtocolDo loanProtocolDo = new LoanProtocolDo();
				loanProtocolDo.setFilePath(consultVO.getSavePath());
				loanProtocolDo.setFileName(map.get("fileName").toString());
				loanProtocolDo.setFileType("PDF");
				loanProtocolDo.setProType("2");
				loanProtocolDo.setLoanId(consultVO.getLoanId());
				loanProtocolService.addLoanProtocol(loanProtocolDo);
				logger.info("生成PDF成功！param: 借款人="+consultVO.getRealName()+" 身份证="+consultVO.getIdNo()+"协议号="+consultVO.getFilePrefix());
			}else{
				logger.error("生成PDF失败!result="+str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
