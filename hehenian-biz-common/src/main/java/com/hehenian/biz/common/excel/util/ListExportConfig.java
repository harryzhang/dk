/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.report.excel.util
 * @Title: ExportConfig.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午11:22:45
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.excel.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.util.NotifyTemplateUtil;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午11:22:45
 */
public class ListExportConfig {
    
    private List<Column> columList;  //输出列的定义
    private String templateFileName; //导出模版文件
    private String exportFilePath;  //文件写出路径
    private int startRow;
    private final static String properties_suffix = ".properties";
    
    private static Properties getProperties(String exportConfigName){
        
        Properties properties =  new Properties();
        if(null == exportConfigName){
            throw new BusinessException("初始化导出配置出错，配置文件名称不能为空");
        }
        InputStream fileInput = null;
        try {
            URL templateURL = NotifyTemplateUtil.class.getResource("/exportConfig");
            String filePath = templateURL.toURI().getPath();
            StringBuffer fileNameBuffer = new StringBuffer(filePath);
            fileNameBuffer.append("/");
            fileNameBuffer.append(exportConfigName);
            
            if(!exportConfigName.endsWith(properties_suffix)){
                fileNameBuffer.append(properties_suffix);
            }
            File file =  new File (fileNameBuffer.toString());
            fileInput = new BufferedInputStream( new FileInputStream( file));
            properties.load(fileInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }finally{
            try {
                if(null != fileInput)
                fileInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileInput = null;
        }
        return properties;
    }
    
    //私有构造函数
    private ListExportConfig(Properties properties){
        columList = new ArrayList<Column>();
        if(null == properties){
            return;
        }
        
        // this.templateFileName = properties.getProperty("templateFileName");
        // this.exportFilePath = properties.getProperty("exportFilePath");
        String columns = properties.getProperty("columns");
        int startRow = properties.getProperty("startRow") == null ? 0 : Integer.parseInt(properties.getProperty("startRow"));
        this.setStartRow(startRow);
        if(null != columns && !"".equals(columns)){
            String[] columnArray = columns.split(",");
            for(String col :columnArray){
                col = col.trim();
                String cIdx  = properties.getProperty(col+".col.index");
                String rIdx = properties.getProperty(col+".row.index");
                String length = properties.getProperty(col+".length");
                String pattern = properties.getProperty(col+".pattern");
                
                if(cIdx == null || "".equals(cIdx)){
                    cIdx="0";
                }
                if(rIdx == null || "".equals(rIdx)){
                    rIdx="0";
                }
                if(length == null || "".equals(length)){
                    length="0";
                }
                
                Column column = new Column(col,
                                           Integer.parseInt(rIdx),
                                           Integer.parseInt(cIdx),
                                           Integer.parseInt(length),
                                           pattern);
                columList.add(column);
            }
        }
    }
    
    
   
    
    /** 
     * 根据配置创建配置元数据对象
     * @param exportConfigName 配置文件
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午11:23:54
     */
    public static ListExportConfig getExportConfig(String exportConfigName) {
        Properties properties =  getProperties(exportConfigName);
        ListExportConfig exportConfig = new ListExportConfig(properties);
        return exportConfig;
    }

    /** 
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午11:31:01
     */
    public Iterator<Column> getColumnIterator() {
        return columList.iterator();
    }

    /** 
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日下午2:46:55
     */
    public String getTemplateFileName() {
        return this.templateFileName;
    }

    /** 
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日下午2:54:28
     */
    public String getExportFilePath() {
        return this.exportFilePath;
    }

    /** 
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年6月20日上午9:29:37
     */
    public int getStartRow() {
        return this.startRow;
    }

    /** 
     * @return columList 
     */
    public List<Column> getColumList() {
        return columList;
    }

    /**
     * @param columList the columList to set
     */
    public void setColumList(List<Column> columList) {
        this.columList = columList;
    }

    /** 
     * @return propertiesSuffix 
     */
    public static String getPropertiesSuffix() {
        return properties_suffix;
    }

    /**
     * @param templateFileName the templateFileName to set
     */
    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    /**
     * @param exportFilePath the exportFilePath to set
     */
    public void setExportFilePath(String exportFilePath) {
        this.exportFilePath = exportFilePath;
    }

    /**
     * @param startRow the startRow to set
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

}
