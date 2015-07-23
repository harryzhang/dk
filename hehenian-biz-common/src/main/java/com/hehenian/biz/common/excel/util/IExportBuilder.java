/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.report.excel.util
 * @Title: IExcelExport.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:54:03
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.excel.util;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月17日 上午10:54:03
 */
public interface IExportBuilder {

    /** 
     *   
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午10:56:28
     */
    void builder();

    /** 
     * @return 返回文件名称 
     * @author: zhangyunhmf
     * @date: 2014年10月17日上午10:59:13
     */
    String outToFile();

}
