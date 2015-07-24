/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system
 * @Title: IFeeRuleComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:03:06
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.system;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 *@author xiexiangmf
 * @date 2015年3月11日下午3:20:08
 */
public interface ICommonQueryComponent {
 
	/**
	 * 通用分页查询
	 * @param searchItems
	 * @return
	 */
	List<Map<String, Object>> getMap(Map<String, Object> searchItems);
	
	/**
	 * 总记录
	 * @param searchItems
	 * @return
	 */
	int getTotalCount(Map<String, Object> searchItems);
	
	/**
	 * 单条记录
	 * @param searchItems
	 * @return
	 */
	Map<String, Object> getData(Map<String, Object> searchItems);
}
