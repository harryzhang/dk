/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.report.excel.util
 * @Title: Column.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午11:30:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.excel.util;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午11:30:53
 */
public class Column {
    
    private String columnName; //列名称
    private int rowIdx; //行号
    private int colIdx; //列号
    private Object colValue; //列的值
    private int length; //输出长度， 定长的文本文件有用
    private String pattern; //输出格式
    
    public Column(String columnName){
        this.columnName = columnName;
    }
    
    public Column(String columnName, Object colValue){
        this.columnName = columnName;
        this.colValue = colValue;
    }
    
    public Column(String columnName, int rowIdx, int colIdx){
        this.columnName = columnName;
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    public Column(String columnName, int rowIdx, int colIdx,int length, String pattern){
        this.columnName = columnName;
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
        this.length = length;
        this.pattern = pattern;
    }
    
    /** 
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午11:31:12
     */
    public Object getColumnName() {
        return this.columnName;
    }

    /** 
     * @param colData  
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午11:31:20
     */
    public void setValue(Object colData) {
        this.colValue = colData;
    }

    /** 
     * @return rowIdx 
     */
    public int getRowIdx() {
        return rowIdx;
    }

    /**
     * @param rowIdx the rowIdx to set
     */
    public void setRowIdx(int rowIdx) {
        this.rowIdx = rowIdx;
    }

    /** 
     * @return colIdx 
     */
    public int getColIdx() {
        return colIdx;
    }

    /**
     * @param colIdx the colIdx to set
     */
    public void setColIdx(int colIdx) {
        this.colIdx = colIdx;
    }

    /** 
     * @return colValue 
     */
    public Object getColValue() {
        return colValue;
    }

    /**
     * @param colValue the colValue to set
     */
    public void setColValue(Object colValue) {
        this.colValue = colValue;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /** 
     * @return length 
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /** 
     * @return pattern 
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
