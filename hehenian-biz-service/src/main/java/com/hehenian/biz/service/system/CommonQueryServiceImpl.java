/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.system
 * @Title: SettSchemeServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:34:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.system;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.system.ICommonQueryService;
import com.hehenian.biz.component.system.ICommonQueryComponent;

/**
 * 
 * 
 *@author xiexiangmf
 * @date 2015年3月11日下午5:25:56
 */
@Service("commonQueryService")
public class CommonQueryServiceImpl implements ICommonQueryService {
    private final Logger         logger = Logger.getLogger(this.getClass());
    @Autowired
    private ICommonQueryComponent commonQueryComponent;
	
	@Override
	public NPageDo<List<Map<String, Object>>> getMap(Map<String, Object> searchItems) {
		NPageDo<List<Map<String, Object>>> map = new NPageDo<List<Map<String, Object>>>();
		try {
			long count = commonQueryComponent.getTotalCount(searchItems);
			map.setTotalCount(count);
	        if (count == 0) {
	            return map;
	        }
	        
	        List<Map<String, Object>> list = commonQueryComponent.getMap(searchItems);
	        BigDecimal bigDc = null;
	        Iterator<String> iter = null;
	        String k = null;
	        for(Map<String, Object> listMap : list) {
	        	iter=listMap.keySet().iterator();
	        	 while (iter.hasNext()) {
		        	k=(String)iter.next();
		        	if(listMap.get(k) instanceof BigDecimal) {
		        		 bigDc = (BigDecimal)listMap.get(k);
		        		 listMap.put(k, bigDc.toString());
		        	}
	        	 }
	        }
	        map.setCommonModeList(list);
	        return map;
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		    map.setTotalCount(0l);
		    return map;
		}
	}

	@Override
	public Map<String, Object> getData(Map<String, Object> searchItems) {
		Map<String, Object> map = null;
		try {
			map = commonQueryComponent.getData(searchItems);
	        BigDecimal bigDc = null;
	        String k = null;
	        Iterator<String> iter =map.keySet().iterator();
        	 while (iter.hasNext()) {
	        	k=(String)iter.next();
	        	if(map.get(k) instanceof BigDecimal) {
	        		 bigDc = (BigDecimal)map.get(k);
	        		 map.put(k, bigDc.toString());
	        	}
        	 }
	        return map;
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		    return map;
		}
	}
	
	@Override
	public List<Map<String, Object>> getListMap(Map<String, Object> searchItems) {
		List<Map<String, Object>> list = null;
		try {
	        list = commonQueryComponent.getMap(searchItems);
	        BigDecimal bigDc = null;
	        Iterator<String> iter = null;
	        String k = null;
	        for(Map<String, Object> listMap : list) {
	        	iter=listMap.keySet().iterator();
	        	 while (iter.hasNext()) {
		        	k=(String)iter.next();
		        	if(listMap.get(k) instanceof BigDecimal) {
		        		 bigDc = (BigDecimal)listMap.get(k);
		        		 listMap.put(k, bigDc.toString());
		        	}
	        	 }
	        }
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		    return null;
		}
		return list;
	}
}
