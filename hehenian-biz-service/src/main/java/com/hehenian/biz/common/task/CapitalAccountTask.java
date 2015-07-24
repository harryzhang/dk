/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.task
 * @Title: RepayFITask.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月15日 上午9:14:21
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.activity.dataobject.CapitalAccountDo;
import com.hehenian.biz.common.excel.util.DirConfig;
import com.hehenian.biz.common.excel.util.ExcelExportor;
import com.hehenian.biz.common.excel.util.IExportBuilder;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.MailNotifyDo;
import com.hehenian.biz.component.activity.ICapitalAccountComponent;
import com.hehenian.biz.component.system.ICommonQueryComponent;


/**
 * 计算每天的资金情况表
 * 
 *@author xiexiangmf
 * @date 2015年3月16日上午10:45:03
 */
@Component("capitalAccountTask")
public class CapitalAccountTask extends DefaultTask {
    
    @Autowired
    private ICommonQueryComponent commonQueryComponent;
    @Autowired
    private ICapitalAccountComponent capitalAccountComponent;
    @Autowired
    private INotifyService notifyService;
    // 输出配置文件
    @Autowired
    private DirConfig                      dirConfig;
    
    private String jobName = "计算每天的资金情况表";

    @Override
    protected String getJobName() {
        return jobName;
    }

    @Override
    protected void doJob() {
        Map<String, Object> map= null;
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        CapitalAccountDo capitalAccountDo = new CapitalAccountDo();
        Map<String, Object> seachr = new HashMap<String, Object>();
        String nameSpace = "com.hehenian.biz.dal.system.ICommonQueryDao";
        try {
        	logger.info("--计算每天的资金情况表开始--");
        	//彩富人生 每天的充值金额
        	seachr.put("selectMethodId", nameSpace+".getRechargeMoney");
        	seachr.put("applyTime", formatter.format(new Date(currentDate.getTime() - 1 * 24 * 60 * 60 * 1000)));
        	seachr.put("endTime", formatter.format(currentDate));
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setRechargeMoney(map!=null ? getDouble((BigDecimal)map.get("rechargeMoney")):null);
        	
        	//彩富人生购买散标金额
        	seachr.put("selectMethodId", nameSpace+".getInvestAmount");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setInvestAmount(map!=null ? getDouble((BigDecimal)map.get("investAmount")):null);
        	
        	//彩富人生购买定期理财的金额
        	seachr.put("selectMethodId", nameSpace+".getTradeAmount");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setTradeAmount(map!=null ? getDouble((BigDecimal)map.get("tradeAmount")):null);
        	
        	//红包理财
        	seachr.put("selectMethodId", nameSpace+".getHbBuyMoney");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setHbBuyMoney(map!=null ? (Double)map.get("hbBuyMoney"):null);
        	
        	//加薪宝
        	seachr.put("selectMethodId", nameSpace+".getGjwyXMoney");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setJxbBuyMoney(map!=null ?getDouble((BigDecimal)map.get("jxbBuyMoney")):null);
        	
        	//+族宝
        	seachr.put("selectMethodId", nameSpace+".getGjwyZMoney");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setJzbBuyMoney(map!=null ? getDouble((BigDecimal)map.get("jzbBuyMoney")):null);
        	
        	//平台购买散标金额
        	seachr.put("selectMethodId", nameSpace+".getPlatformInvestAmount");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setPlatformInvestAmount(map!=null ? getDouble((BigDecimal)map.get("platformInvestAmount")):null);
        	//平台购买定期理财的金额
        	seachr.put("selectMethodId", nameSpace+".getPlatformTradeAmount");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setPlatformTradeAmount(map!=null ? getDouble((BigDecimal)map.get("platformTradeAmount")):null);
        	
        	//放款金额
        	seachr.put("selectMethodId", nameSpace+".getBorrowAmount");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setBorrowAmount(map!=null ? getDouble((BigDecimal)map.get("borrowAmount")):null);
        	//提现金额
        	seachr.put("selectMethodId", nameSpace+".getWithdrawal");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setWithdrawal(map!=null ? getDouble((BigDecimal)map.get("withdrawal")):null);
        	
        	//统计通联可用金额
        	seachr.put("selectMethodId", nameSpace+".getTlAvailableMoney");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setTlAvailableMoney(map!=null ? getDouble((BigDecimal)map.get("tlAvailableMoney")):null);
        	
        	//汇付通联可用金额
        	seachr.put("selectMethodId", nameSpace+".getHfAvailableMoney");
        	map = commonQueryComponent.getData(seachr);
        	capitalAccountDo.setHfAvailableMoney(map!=null ? getDouble((BigDecimal)map.get("hfAvailableMoney")):null);
        	//时间
        	capitalAccountDo.setCreateTime(formatter.parse(formatter.format(new Date(currentDate.getTime() - 1 * 24 * 60 * 60 * 1000))));
        	//插入到表中
        	capitalAccountComponent.addCapitalAccount(capitalAccountDo);
        	logger.info("--插入每天的资金情况表成功--");
        	
        	logger.info("--发送邮件开始--");
        	seachr.put("selectMethodId", nameSpace+".getCapitalData");
        	seachr.put("startTime", formatter.format(new Date(currentDate.getTime() - 8 * 24 * 60 * 60 * 1000)));
        	sendDataToMail(seachr);
        	logger.info("--发送邮件结束--");
        	
        } catch (Exception e) {
        	logger.info("--插入每天的资金情况表失败--");
        	logger.error(e);
        } finally {
        	logger.info("--计算每天的资金情况表结束--");
        }
    }
    
   /**
    * 发送邮件
    * @param searchItems
    */
    public void sendDataToMail(Map<String, Object> searchItems) {
    	List<Map<String, Object>> publishList = commonQueryComponent.getMap(searchItems);
        if (null != publishList && publishList.size() > 0) {
            IExportBuilder excelExportor = new ExcelExportor(publishList, "acountMailExcel",
                    dirConfig.getMailTemplateFile(), dirConfig.getMailExportDir());
            excelExportor.builder();
            String fileName = excelExportor.outToFile();
            //发送邮件
            sendMain(fileName);
        }
    }
    
    public void sendMain(String filePath) {
    	MailNotifyDo notifyDo = new MailNotifyDo();
    	notifyDo.setAsync(false);
    	 notifyDo.setCcList(dirConfig.getCcList().split("&")[0]);
         notifyDo.setFilePath(filePath);
         notifyDo.setSimpleMessage("");
         notifyDo.setFileName("合和年在线各渠道进出账资金汇总表");
         notifyDo.MESSAGE_CONTEXT="亲，您好，附件为平台每日资金报表，加油！";
         notifyDo.setMessageType(MailNotifyDo.MAIL);
         notifyDo.setSendFlag("F");
         notifyDo.setRecievers(dirConfig.getAddressee().split("&")[0]);
         notifyDo.setSubject("合和年在线各渠道进出账资金汇总表");
    	notifyService.send(notifyDo);
    }
    
    public Double getDouble (BigDecimal bigD) {
    	if(bigD != null) {
    		return bigD.doubleValue();
    	}
    	return null;
    }
}
