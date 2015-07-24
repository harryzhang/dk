/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.facade.colorlife
 * @Title: ColorLifeReturnProcessor.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月30日 上午11:45:09
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.facade.colorlife;

import java.util.Map;

import com.hehenian.biz.common.util.JsonUtil;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年10月30日 上午11:45:09
 */
public class ColorLifeReturnProcessor {
    
    public static int parse(String jsonStr){
        if(null == jsonStr){return 1;}
		Map<String, Object> retMap = (Map) JsonUtil.json2Bean(jsonStr,
				Map.class);
		// JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String status = (String) retMap.get("status");
        if("0".equals(status)){
            return 0;
        }else{
            return 1;
        }
    }

}
