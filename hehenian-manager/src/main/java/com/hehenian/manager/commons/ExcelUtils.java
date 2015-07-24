/**
 * Project Name:hehenian-manager
 * File Name:ExcelUtils.java
 * Package Name:com.hehenian.manager.commons
 * Date:2015年5月5日上午9:45:34
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 * excel读写工具类
 * @author   songxjmf
 * @date: 2015年5月5日 上午9:45:34 	 
 */
public class ExcelUtils {
	
	private final static String parttern = "#.00";
    private final static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
	
    /**
     * 读取单sheet excel
     * @param filePath 文件路径
	 * @param cellCount sheet对应的有效列数
     * @author songxjmf
     * @date: 2015年5月6日 上午10:22:05
     */
    public static List<String[]> read(String filePath,int cellCount) throws InvalidFormatException, IOException{
    	List<String[]> result = new ArrayList<String[]>();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
			Workbook book = WorkbookFactory.create(inputStream);
			Sheet sheet = book.getSheetAt(0);
		    if(sheet!=null){
		        int lastRowNum = sheet.getLastRowNum();
		        Row row = null;
		        for (int i = 0; i <= lastRowNum; i++) {
		            row = sheet.getRow(i);
		            if (row != null) {
		            	String[] cellValues = new String[cellCount];
		            	for(int cellIdx=0;cellIdx<cellCount;cellIdx++){
		            		String value = getCellData(row, cellIdx, parttern);
		            		cellValues[cellIdx] = value;
		            	}
		            	result.add(cellValues);
		            }
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(inputStream!=null){
				inputStream.close();
			}
		}
		return result;
	}
    
	/**
	 * 读取多sheet excel
	 * @param filePath 文件路径
	 * @param cellCounts sheet对应的有效列数
	 * @author songxjmf
	 * @date: 2015年5月5日 下午12:59:56
	 */
	public static Map<String,List<String[]>> read(String filePath,int[] cellCounts) throws InvalidFormatException, IOException{
		Map<String,List<String[]>> result = new HashMap<String,List<String[]>>();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
			Workbook book = WorkbookFactory.create(inputStream);
			int sheetLength = book.getNumberOfSheets();
			if(sheetLength>cellCounts.length){
				sheetLength = cellCounts.length;
			}
			for(int sheetIdx = 0;sheetIdx<sheetLength;sheetIdx++){
				int cellCount = cellCounts[sheetIdx];
				Sheet sheet = book.getSheetAt(sheetIdx);
			    if(sheet!=null){
			        int lastRowNum = sheet.getLastRowNum();
			        List<String[]> rowData = new ArrayList<String[]>();
			        Row row = null;
			        for (int i = 0; i <= lastRowNum; i++) {
			            row = sheet.getRow(i);
			            if (row != null) {
			            	String[] cellValues = new String[cellCount];
			            	for(int cellIdx=0;cellIdx<cellCount;cellIdx++){
			            		String value = getCellData(row, cellIdx, parttern);
			            		cellValues[cellIdx] = value;
			            	}
			            	rowData.add(cellValues);
			            }
			        }
			        result.put(sheetIdx+"-"+sheet.getSheetName(), rowData);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(inputStream!=null){
				inputStream.close();
			}
		}
		return result;
	}
	
	/**
	 * 根据类型获取单元格数据
	 * @author songxjmf
	 * @date: 2015年5月5日 下午1:44:01
	 */
    private static String getCellData(Row row, int num,String pattern){
        Cell cell = row.getCell(num);
        String value = "";
        if (cell!=null){
            int type = cell.getCellType();
            DecimalFormat df = new DecimalFormat(pattern);
            switch (type) {
                case 0:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        value = sFormat.format(date);
                    } else {
                        double tempValue = cell.getNumericCellValue();
                        value = df.format(tempValue);
                    }
                    break;
                case 1:
                	value = cell.getStringCellValue();
                    break;
                case 2:
                	try {
                        double tempValue = cell.getNumericCellValue();
                        value = df.format(tempValue);
    				} catch (Exception e) {
    					value = cell.getCellFormula();
    				}
                    break;
                case 3:
                    value = cell.getStringCellValue();
                    break;
                case 4:
                    boolean tempValue = cell.getBooleanCellValue();
                    value = String.valueOf(tempValue);
                    break;
                case 5:
                    byte b = cell.getErrorCellValue();
                    value = String.valueOf(b);
                default:
                    break;
            }
        }
        return value;
    }
    
    public static void main(String[] args) throws InvalidFormatException, IOException {
    	String filePath = "E:/需求/省市区基础数据/数据收集/+多宝、+车宝基础数据库数据统计表(成都喜年).xlsx";
    	List<String[]> m = read(filePath, 10);
		System.out.println(m);
	}
}

