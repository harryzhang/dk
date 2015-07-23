/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.report.excel.util
 * @Title: DefaultExportor.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午11:21:15
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.excel.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hehenian.biz.common.util.DateUtils;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午11:21:15
 */
public abstract class DefaultListExportor {
    
    protected List<Map<String, Object>> repayList; //导出数据
    
    protected ListExportConfig exportConfig; //导出配置
    
    protected static Logger logger = Logger.getLogger(DefaultListExportor.class);
    
    
    public void builder() {
        if(null == this.repayList || this.repayList.isEmpty()) return;
        int rowCount = exportConfig.getStartRow();
        for(Map<String,Object> row :repayList){
            rowCount++;
            Iterator<Column> it = this.exportConfig.getColumnIterator();
            while(it.hasNext()){
                Column col  = it.next();
                Object colData = row.get(col.getColumnName());
                col.setValue(colData);
                col.setRowIdx(rowCount);
                writeColumn(col);
            }
        }
    }
    
    /** 
     * @param col  
     * @author: zhangyunhmf
     * @date: 2014年10月17日下午12:38:06
     */
    protected abstract  void writeColumn(Column col) ;
    
    /**
     * 取文件号
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月20日上午10:25:43
     */
    protected static int getNo() {
        
        String indexStr = null;
        int start = 1;
        String dateStr = DateUtils.formatDate(new Date());
        if(currentDate.equals(dateStr)){
            return ++FileCount;
        }else{
            currentDate = dateStr;
            return ++FileCount;
        }
        
    }
    
    private static String currentDate = "";
    private static int FileCount = 0;

}
