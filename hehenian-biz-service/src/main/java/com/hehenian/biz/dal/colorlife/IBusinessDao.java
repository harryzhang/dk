/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj.impl
 * @Title: IBusinessDao.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-8 下午6:55:35
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.colorlife;

import java.util.List;

import com.hehenian.biz.common.colorlife.dataobject.BusinessDo;



public interface IBusinessDao{
	/**
	 * 添加业务日志
	 * @param businessDo
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-8 下午9:38:33
	 */
	public int addBusiness(BusinessDo businessDo);
	/**
	 * 修改业务日志
	 * @param businessDo
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-8 下午9:38:59
	 */
	public int updateBusiness(BusinessDo businessDo);
	/**
	 * 查询业务日志
	 * @param businessDo
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-8 下午9:39:33
	 */
	public BusinessDo getBusinessByDo(BusinessDo businessDo);
	
	public List<BusinessDo> queryFailBusinessList();
}
