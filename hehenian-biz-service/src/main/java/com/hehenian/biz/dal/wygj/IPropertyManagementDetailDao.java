package com.hehenian.biz.dal.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;

public interface IPropertyManagementDetailDao {
	public PropertyManagementDetailDo getById(Integer id) ;
	public PropertyManagementDetailDo getDefaultByUserId(int userId) ;
	public int getPropertyManagementCounts(int userId);
	public List<PropertyManagementDetailDo> listPropertyManagementsByUserId(int userId) ;
	public int insertManageDetail(PropertyManagementDetailDo propertyManageDetailDo) ;
	public int updateDefaultByBuildingNo(int userId, String buildingNo);
	public int deleteManagerDetailById(int id);
	public int updateAnotherDefaultManageDetail(int userId,int id);
	public int updatePropertyManagementDetailDo(PropertyManagementDetailDo pmdd);
	public PropertyManagementDetailDo getDetailByParams(long mainaddressid,
			String roomno, int userId);
}
