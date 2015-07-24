/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.task
 * @Title: XiaoDaiBalanceTask.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:43:38
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.excel.util.DirConfig;
import com.hehenian.biz.common.excel.util.ExcelExportor;
import com.hehenian.biz.common.excel.util.IExportBuilder;
import com.hehenian.biz.component.report.IXiaoDaiBalanceReportComponent;

/**
 * 
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:43:38
 */
@Component("xiaoDaiBalanceTask")
public class XiaoDaiBalanceTask extends DefaultTask {

    private String                         jobName = "小贷放款和扣款定时任务";

    // 输出配置文件
    @Autowired
    private DirConfig                      dirConfig;

    @Autowired
    private IXiaoDaiBalanceReportComponent xiaoDaiBalanceReportComponent;

    /*
     * (no-Javadoc) <p>Title: getJobName</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see com.hehenian.biz.common.task.DefaultTask#getJobName()
     */
    @Override
    protected String getJobName() {
        return this.jobName;
    }

    /*
     * (no-Javadoc) <p>Title: doJob</p> <p>Description: </p>
     * 
     * @see com.hehenian.biz.common.task.DefaultTask#doJob()
     */
    @Override
    protected void doJob() {
        // String jobDate = DateUtils.formatDate(UtilDate.getYesterDay());

        String today = DateFormatUtils.format(new Date(), "yyyyMMdd");
        List<Map<String, Object>> publishList = xiaoDaiBalanceReportComponent.queryPublishAmountByDate(today);
        if (null != publishList && publishList.size() > 0) {
            IExportBuilder excelExportor = new ExcelExportor(publishList, "xiaodaiPublishExcel",
                    dirConfig.getPublishTemplateFile(), dirConfig.getPublishExportDir());
            excelExportor.builder();
            excelExportor.outToFile();
        }

        List<Map<String, Object>> repayList = xiaoDaiBalanceReportComponent.queryRepayAmountByDate(today);
        if (null != repayList && repayList.size() > 0) {
            IExportBuilder repayExcelExportor = new ExcelExportor(repayList, "xiaodaiRepayExcel",
                    dirConfig.getRepayTemplateFile(), dirConfig.getRepayExportDir());
            repayExcelExportor.builder();
            repayExcelExportor.outToFile();
        }
    }

}
