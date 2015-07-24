/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.service.wygj.impl
 * @Title: IPropertyManagementFeeServiceImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-7 下午10:17:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.service.wygj.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.wygj.IPropertyManagementFeeService;
import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;
import com.hehenian.biz.dal.wygj.IPropertyManagementDetailDao;
import com.hehenian.biz.dal.wygj.IPropertyManagementFeeDao;

@Service("propertyManagementFeeService")
public class PropertyManagementFeeServiceImpl implements
		IPropertyManagementFeeService {
	
	@Autowired
	private IPropertyManagementFeeDao propertyManagementFeeDao;
	@Autowired
	private IPropertyManagementDetailDao propertyManagementDetailDao;
	

	@Override
	public PropertyManagementFeeDo getByParams(long mainaddressid,String buildingno,String roomno, String theOwner) {
		return propertyManagementFeeDao.getByParams(mainaddressid,buildingno,roomno,theOwner);
	}
	@Override
	public List<PropertyManagementFeeDo> queryBuildingByAddressId(Long addressId,String building){
		return propertyManagementFeeDao.queryBuildingByAddressId(addressId,building);
	}

	
	@Override
	public PropertyManagementDetailDo getDefaultByUserId(int userId) {
		return propertyManagementDetailDao.getDefaultByUserId(userId);
	}

	@Override
	public int getPropertyManagementCounts(int userId) {
		return propertyManagementDetailDao.getPropertyManagementCounts(userId);
	}

	@Override
	public List<PropertyManagementDetailDo> listPropertyManagementsByUserId(
			int userId) {
		return propertyManagementDetailDao.listPropertyManagementsByUserId(userId);
	}

	@Override
	public int insertManageDetail(
			PropertyManagementDetailDo propertyManageDetailDo) {
		return propertyManagementDetailDao.insertManageDetail(propertyManageDetailDo);
	}

	@Override
	public int updateDefaultByBuildingNo(int userId, String buildingNo) {
		return propertyManagementDetailDao.updateDefaultByBuildingNo(userId,buildingNo);
	}

	@Override
	public int deleteManagerDetailById(int id) {
		PropertyManagementDetailDo p = propertyManagementDetailDao.getById(id);
		if(p.getDefaultset()==1){//删除的是默认地址，则先设置其它的为默认
			propertyManagementDetailDao.updateAnotherDefaultManageDetail(p.getUser_id(),p.getId());
		}
		return propertyManagementDetailDao.deleteManagerDetailById(id);
	}

	

	@Override
	public PropertyManagementDetailDo getPropertyManagementDetailDoById(
			Integer id) {
		return propertyManagementDetailDao.getById(id);
	}

	@Override
	public List<Map<String, Object>> listPropertyManagementDetailDo(
			Map<String, Object> conditon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updatePropertyManagementDetailDo(PropertyManagementDetailDo pmdd) {
		return propertyManagementDetailDao.updatePropertyManagementDetailDo(pmdd);
	}
	@Override
	public PropertyManagementDetailDo getDetailByParams(long mainaddressid,
			String roomno, int userId) {
		return propertyManagementDetailDao.getDetailByParams(mainaddressid,roomno,userId);
	}
	
	
}
