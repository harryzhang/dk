/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: ParkingFeeService.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-27 下午4:43:03
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;

public interface IParkingFeeService {
	/**
	 * 
	 * @Description: 获取详细信息
	 * @param id
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:45:51
	 */
	ParkingFeeDo getById(Integer id);
	
	/**
	 * 查询基础数据记录
	 * @Description: TODO
	 * @param conditon
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:48:47
	 */
	List<ParkingFeeDo> listParkingFee(Object[] obj);

	/**
	 * 更新物业停车费基础信息
	 * @Description: TODO
	 * @param pf
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:50:12
	 */
	int updateParkingFee(ParkingFeeDo pf);
	
	/**
	 * 
	 * @Description: 获取冲抵物业停车费车辆详细信息
	 * @param id
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:45:51
	 */
	ParkingDetailDo getParkingDetailDoById(Integer id);
	/**
	 * 
	 * @Description: 获取冲抵物业停车费车辆详细信息
	 * @param id
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:45:51
	 */
	List<ParkingDetailDo> getParkingDetailDo(ParkingDetailDo p);
	/**
	 * 查询冲抵物业停车费车辆记录
	 * @Description: TODO
	 * @param obj
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:48:47
	 */
	List<ParkingDetailDo> listParkingDetailDo(Object[] obj);

	/**
	 * 更新冲抵物业停车费车辆信息
	 * @Description: TODO
	 * @param pdd
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:50:12
	 */
	int updateParkingDetail(ParkingDetailDo pdd);
	
	/**
	 * 
	 * @Description: 查询+车宝投资收益记录
	 * @param conditon
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-27 下午4:48:47
	 */
	Map<String, Object> listParkingFeeBusiness(Map<String, Object> map);
	/**
	 * 
	 * @Description:导出数据
	 * @param object
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-28 上午11:57:04
	 */
	List<Map<String,Object>> exportParkingFeeBusiness(Object[] object);
	
	//--------------------------------------------------------------------
	/**
	 * 
	 * @Description:根据用户id查询冲抵停车费信息
	 * @param userId
	 * @return ParkingDetailDo
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	ParkingDetailDo getDefaultByUserId(int userId);
	/**
	 * 
	 * @Description:查询用户冲抵信息条数
	 * @param userId
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int getParkingDetailCounts(int userId);
	/**
	 * 
	 * @Description:查询用户冲抵信息
	 * @param userId
	 * @return List
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	List<ParkingDetailDo> listParkingDetailsByUserId(int userId);
	/**
	 * 
	 * @Description:插入冲抵停车费信息
	 * @param parkingDetailDo
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int insertParkingDetail(ParkingDetailDo parkingDetailDo);
	/**
	 * 
	 * @Description:更新冲抵停车费信息
	 * @param userId,plateNo
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int updateDefaultByPlateNo(int userId , String plateNo);
	/**
	 * 
	 * @Description:删除冲抵停车费信息
	 * @param 
	 * @return int
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	int deleteParkingDetailById(int id);
	/**
	 * 
	 * @Description:根据小区编号和车牌号，查询基础数据
	 * @param community,plateNum
	 * @return ParkingFeeDo
	 * @author: jiangwmf
	 * @date 2015-4-29 上午10:50:03
	 */
	ParkingFeeDo getByParams(long community , String plateNum);
	
	/**
	 * 根据mainaddressid,infotype=1查询是否有冲抵信息
	 */
	ParkingDetailDo getDetailByParams(long mainaddressid,String plateNum,int userId);
	
}
