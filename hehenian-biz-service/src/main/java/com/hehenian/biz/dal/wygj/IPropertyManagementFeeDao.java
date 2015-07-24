/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: IPropertyManagementFeeDao.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-7 下午10:23:36
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;

public interface IPropertyManagementFeeDao {
	
	public List<PropertyManagementFeeDo> queryBuildingByAddressId(Long addressId,String building);
	
	public PropertyManagementFeeDo getByParams(long mainaddressid,String buildingno,String roomno, String theOwner);
}
