/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.report
 * @Title: IColorReportServiceImplTest.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月13日 下午3:52:47
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.report.IColorReportService;
import com.hehenian.biz.service.BaseTestCase;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月13日 下午3:52:47
 */
public class IColorReportServiceImplTest  extends BaseTestCase {
    
    @Autowired
    IColorReportService colorReportService;
    
    @Test
    public void testColorInvestReport(){
        Map parameterMap = new HashMap();
        parameterMap.put("investStartDate", "2014-01-01");
        parameterMap.put("investEndDate", "2014-10-13");
        PageDo page = new PageDo();
        page = colorReportService.queryColorInvest(parameterMap, page);
        List<Map<String,Object>> l = page.getPage();
        for(Map<String,Object> map :l){
            for(Map.Entry<String, Object>  entry : map.entrySet()){
                System.out.println("key:"+entry.getKey()+" / "+entry.getValue());
            }
        }
    }

}
