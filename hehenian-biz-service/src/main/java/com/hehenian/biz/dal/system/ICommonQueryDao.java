/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.system
 * @Title: IFeeRuleDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:49:44
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.system;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 *@author xiexiangmf
 * @date 2015年3月11日下午3:04:56
 */
public interface ICommonQueryDao {

	/**
	 * 通用分页查询
	 * @param searchItems
	 * @return
	 */
	List<Map<String, Object>> getMapPage(Map<String, Object> searchItems);
	
	/**
	 * 总记录
	 * @param searchItems
	 * @return
	 */
	int getTotalCount(Map<String, Object> searchItems);
	
}
