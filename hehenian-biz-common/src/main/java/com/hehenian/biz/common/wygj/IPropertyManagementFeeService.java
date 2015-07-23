/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj
 * @Title: PropertyManagementFeeService.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-27 下午4:44:08
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;

public interface IPropertyManagementFeeService {
	
	/**
	 * 
	 * @Description: 获取冲抵物业费地址详细信息
	 * @param id
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:45:51
	 */
	PropertyManagementDetailDo getPropertyManagementDetailDoById(Integer id);
	
	/**
	 * 查询冲抵物业费地址数据记录
	 * @Description: TODO
	 * @param conditon
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:48:47
	 */
	List<Map<String, Object>> listPropertyManagementDetailDo(Map<String, Object> conditon);

	/**
	 * 更新冲抵物业费地址信息
	 * @Description: TODO
	 * @param pmdd
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:50:12
	 */
	int updatePropertyManagementDetailDo(PropertyManagementDetailDo pmdd);
	
	/**
	 * 根据addressId查询
	 * @param addressId
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-7 下午10:01:56
	 */
	public List<PropertyManagementFeeDo> queryBuildingByAddressId(Long addressId,String building);
	
	/**
	 * 
	 * @Description:根据用户id查询冲抵停车费信息
	 * @param userId
	 * @return ParkingDetailDo
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	PropertyManagementDetailDo getDefaultByUserId(int userId);
	/**
	 * 
	 * @Description:查询用户冲抵信息条数
	 * @param userId
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int getPropertyManagementCounts(int userId);
	/**
	 * 
	 * @Description:查询用户冲抵信息
	 * @param userId
	 * @return List
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	List<PropertyManagementDetailDo> listPropertyManagementsByUserId(int userId);
	/**
	 * 
	 * @Description:插入冲抵停车费信息
	 * @param parkingDetailDo
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int insertManageDetail(PropertyManagementDetailDo propertyManageDetailDo);
	/**
	 * 
	 * @Description:更新冲抵停车费信息
	 * @param userId,plateNo
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int updateDefaultByBuildingNo(int userId , String buildingNo);
	/**
	 * 
	 * @Description:删除冲抵停车费信息
	 * @param 
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int deleteManagerDetailById(int id);
	/**
	 * 
	 * @Description:根据小区编号和车牌号，查询基础数据
	 * @param community,plateNum
	 * @return ParkingFeeDo
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	PropertyManagementFeeDo getByParams(long mainaddressid,String buildingno,String roomno, String theOwner);
	
	PropertyManagementDetailDo getDetailByParams(long mainaddressid,String roomno,int userId);
}
