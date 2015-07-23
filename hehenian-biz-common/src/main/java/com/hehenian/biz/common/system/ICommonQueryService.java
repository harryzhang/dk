/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.system
 * @Title: ISettSchemeService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月5日 下午9:06:14
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.system;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.NPageDo;


/**
 *@author xiexiangmf
 * @date 2015年3月11日下午3:13:37
 */
public interface ICommonQueryService {
   
	/**
	 * 通用分页查询
	 * @param searchItems
	 * @return
	 */
	NPageDo<List<Map<String, Object>>> getMap(Map<String, Object> searchItems);
	
	
	Map<String, Object> getData(Map<String, Object> searchItems);
	
	
	List<Map<String, Object>> getListMap(Map<String, Object> searchItems);
}
