/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: IParkingFeeBusinessDataDao.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-29 上午11:24:41
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.util.List;
import java.util.Map;

public interface IParkingFeeBusinessDataDao {
	/**
	 * 
	 * @Description: 预留权限查询
	 * @param authorityCode
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-28 上午10:21:12
	 */
	public Map<String,Object> listParkingFeeBusiness(Map<String,Object> map);
	/**
	 * 
	 * @Description: 预留权限查询
	 * @param authorityCode
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-28 上午10:21:12
	 */
	public List<Map<String,Object>> exportParkingFeeBusiness(Object[] object) ;
}
