/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.report.excel.util
 * @Title: ExcelExportor.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:50:27
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.excel.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.Md5Utils;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:50:27
 */
public class ExcelExportor extends DefaultListExportor implements IExportBuilder {
    
    private HSSFWorkbook wb ;
    
    private void buildWb(String excelTemplateFile){
        InputStream fileInput = null;
        try {
            fileInput = new BufferedInputStream( new FileInputStream(excelTemplateFile));
            this.wb = new HSSFWorkbook(fileInput);
          } catch (IOException e) {
              logger.error(e);
          }finally{
              if(null != fileInput){
                  try {
                      fileInput.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                  fileInput = null;
              }
          }      
    }

    /** 
     * 
     * @param publishList 
     * zhangyunhmf
     * @param string 
     */
    public ExcelExportor(List<Map<String, Object>> publishList, String exportConfigName, String templateFile,
            String outDir) {
      //导出数据
      this.repayList = publishList;
      //导出配置
      this.exportConfig = ListExportConfig.getExportConfig(exportConfigName);
        this.exportConfig.setExportFilePath(outDir);
        this.exportConfig.setTemplateFileName(templateFile);
      buildWb(exportConfig.getTemplateFileName());
    }
    

    /* (no-Javadoc) 
     * <p>Title: outToFile</p> 
     * <p>Description: </p> 
     * @return 
     * @see com.hehenian.biz.report.excel.util.IExportBuilder#outToFile() 
     */
    @Override
    public String outToFile(){
        // Write the output to a file
        StringBuffer fileName = new StringBuffer();
        fileName.append(exportConfig.getExportFilePath());
        int startIdx = exportConfig.getTemplateFileName().lastIndexOf("/");
        fileName.append(exportConfig.getTemplateFileName().substring(startIdx).replaceAll(".xls", ""));
		fileName.append(DateUtil.YYYYMMDDHHMMSS.format(new Date()));
        fileName.append(getNo()).append(".xls");
        FileOutputStream fileOut = null;
        FileOutputStream md5FileStream = null;
        File f =null;
        try {
            f= new File(fileName.toString());
            fileOut = new FileOutputStream(f);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
            
            
            String fileMD5 = Md5Utils.getMd5ByFile(new File(f.getAbsolutePath()));
            File  md5File = new File(fileName+".md5");
            md5FileStream = new FileOutputStream(md5File);
            md5FileStream.write(fileMD5.getBytes());
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null != fileOut ){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileOut = null;
            }
            
            if(null != md5FileStream){
                try {
                    md5FileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                md5FileStream = null;
            }
        }
        this.wb = null;
        return fileName.toString();
        
    }

    /* (no-Javadoc) 
     * <p>Title: writeColumn</p> 
     * <p>Description: </p> 
     * @param col 
     * @see com.hehenian.biz.report.excel.util.DefaultExportor#writeColumn(com.hehenian.biz.report.excel.util.Column) 
     */
    @Override
    protected void writeColumn(Column col) {
        if(null == wb){
            throw new BusinessException("初始化excel失败");
        }
        
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(col.getRowIdx());
        if(null == row){
            row = sheet.createRow(col.getRowIdx());
        }
        HSSFCell cell = row.getCell(col.getColIdx());
        if(null == cell ){
            cell = row.createCell(col.getColIdx());
        }
        cell.setCellValue(String.valueOf(col.getColValue()));
    }
    
    public static void main(String[] args)throws Exception {
        
        
//        InputStream fileInput = new BufferedInputStream( new FileInputStream("C:/Temp/publish_template.xls"));
//        OutputStream fileInput1 = new BufferedOutputStream( new FileOutputStream("C:/Temp/publish_template_存量.xls"));
        
        InputStream fileInput = new BufferedInputStream( new FileInputStream("C:/Temp/repay_template - 存量.xls"));
        OutputStream fileInput1 = new BufferedOutputStream( new FileOutputStream("C:/Temp/repay_template - 存量1.xls"));
        
        HSSFWorkbook wb = new HSSFWorkbook(fileInput);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        for(int i = 0; i<rows ; i ++){
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell(1);
            String val = cell.getStringCellValue();
            String newVal = val.replace("ZZ", "");
            cell.setCellValue(newVal);
        }
        wb.write(fileInput1);
        fileInput1.flush();
        fileInput1.close();
    }

}
