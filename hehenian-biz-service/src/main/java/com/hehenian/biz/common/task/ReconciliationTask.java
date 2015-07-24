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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component("reconciliationTask")
public class ReconciliationTask extends DefaultTask {
    
    @Autowired
    private ICommonQueryComponent commonQueryComponent;
    @Autowired
    private ICapitalAccountComponent capitalAccountComponent;
    @Autowired
    private INotifyService notifyService;
    // 输出配置文件
    @Autowired
    private DirConfig                      dirConfig;
    
    private String jobName = "检测对账信息是否存在问题定时任务";

    @Override
    protected String getJobName() {
        return jobName;
    }

    @Override
    protected void doJob() {
        List<Map<String, Object>> mapList= null;
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> seachr = new HashMap<String, Object>();
        String nameSpace = "com.hehenian.biz.dal.system.ICommonQueryDao";
        try {
        	logger.info("--检测对账信息定时任务开始--");
        	seachr.put("selectMethodId", nameSpace+".getReconciliations");
        	
        	seachr.put("startTime", formatter.format(new Date(currentDate.getTime() - 1 * 24 * 60 * 60 * 1000)));
        	seachr.put("endTime", formatter.format(currentDate));
        	
        	mapList = commonQueryComponent.getMap(seachr);
            if (null != mapList && mapList.size() > 0) {
                IExportBuilder excelExportor = new ExcelExportor(mapList, "reconciliationlExcel",
                        dirConfig.getReconciliationTemplateFile(), dirConfig.getReconciliationExportDir());
                excelExportor.builder();
                String fileName = excelExportor.outToFile();
                //发送邮件
                sendMain(fileName);
            }
        } catch (Exception e) {
        	logger.info("--检测对账信息是否存在问题定时任务失败--");
        	logger.error(e);
        } finally {
        	logger.info("--检测对账信息是否存在问题定时任务结束--");
        }
    }
    
    public void sendMain(String filePath) {
    	MailNotifyDo notifyDo = new MailNotifyDo();
    	notifyDo.setAsync(false);
    	 notifyDo.setCcList(dirConfig.getCcList().split("&")[1]);
         notifyDo.setFilePath(filePath);
         notifyDo.MESSAGE_CONTEXT="亲，对账信息问题汇总，请查收！";
         notifyDo.setSimpleMessage("");
         notifyDo.setFileName("对账信息问题汇总表");
         notifyDo.setMessageType(MailNotifyDo.MAIL);
         notifyDo.setSendFlag("F");
         notifyDo.setRecievers(dirConfig.getAddressee().split("&")[1]);
         notifyDo.setSubject("对账信息问题汇总表");
    	notifyService.send(notifyDo);
    }
}
