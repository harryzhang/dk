/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: IParkingFeeDetailDao.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-29 上午11:23:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;

public interface IParkingFeeDetailDao {
	public ParkingDetailDo getById(Integer id) ;

	public List<ParkingDetailDo> listParkingDetailDo(Object[] obj);
	

	public int updateParkingDetail(ParkingDetailDo pdd);
	
	/**
	 * 查询用户默认冲抵信息
	 * @param id
	 * @return
	 */
	public ParkingDetailDo getDefaultByUserId(int userId);
	/**
	 * 删除用户默认冲抵信息
	 * @param id
	 * @return
	 */
	public int deleteParkingDetail(int id);
	
	public int getParkingDetailCounts(int userId);
	
	public List<ParkingDetailDo> listParkingDetailsByUserId(int userId);
	
	public int insertParkingDetail(ParkingDetailDo pdd);
	
	public int updateDefaultByPlateNo(int userId, String plateNo , int defaultSet);
	
	public int updateAnotherDefaultParkingDetail(int userId, int id);
	
	public ParkingDetailDo getDetailByParams(long mainaddressid, String platenum,int userId);
	
	public List<ParkingDetailDo> getParkingDetailDo(ParkingDetailDo p);
}
