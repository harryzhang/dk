/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.service.wygj.impl
 * @Title: ParkingFeeServiceimpl.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-27 下午5:21:51
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.service.wygj.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.wygj.IParkingFeeService;
import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;
import com.hehenian.biz.dal.wygj.IParkingFeeBusinessDataDao;
import com.hehenian.biz.dal.wygj.IParkingFeeDao;
import com.hehenian.biz.dal.wygj.IParkingFeeDetailDao;
@Service("parkingFeeService")
public class ParkingFeeServiceimpl implements IParkingFeeService {
	@Resource
	private IParkingFeeDao parkingFeeDao;
	@Resource
	private IParkingFeeDetailDao parkingFeeDetailDao;
	@Resource
	private IParkingFeeBusinessDataDao parkingFeeBusinessDataDao;

	@Override
	public ParkingFeeDo getById(Integer id) {
		return parkingFeeDao.getById(id);
	}

	@Override
	public List<ParkingFeeDo> listParkingFee(Object[] obj) {
		return parkingFeeDao.listParkingFee(obj);
	}

	@Override
	public int updateParkingFee(ParkingFeeDo pf) {
		return parkingFeeDao.updateParkingFee(pf);
	}

	@Override
	public ParkingDetailDo getParkingDetailDoById(Integer id) {
		return parkingFeeDetailDao.getById(id);
	}

	@Override
	public List<ParkingDetailDo> listParkingDetailDo(Object[] obj) {
		return parkingFeeDetailDao.listParkingDetailDo(obj);
	}

	@Override
	public int updateParkingDetail(ParkingDetailDo pdd) {
		return parkingFeeDetailDao.updateParkingDetail(pdd);
	}
	/**
	 * 
	 * @Description: 根据传入的省市县区生成主要地址信息
	 * @param province
	 * @param city
	 * @param district
	 * @param community
	 * @return
	 * @author: zhangjhmf
	 * @date 2015-4-28 上午9:33:03
	 */
	public String getMainaddressid(String province,String city,String district,String community){
		//省市县code固定，省2位，市2位，县2位，小区
		return province+city+district+community;
	}

	@Override
	public Map<String, Object> listParkingFeeBusiness(Map<String, Object> map) {
		return parkingFeeBusinessDataDao.listParkingFeeBusiness(map);
	}

	@Override
	public List<Map<String, Object>> exportParkingFeeBusiness(Object[] object) {
		return parkingFeeBusinessDataDao.exportParkingFeeBusiness(object);
	}

	/**
	 * 
	 * @Description: 根据用户id查询冲抵信息
	 * @param userId
	 * @return
	 * @author: jiangwmf
	 * @date 2015-4-28 上午9:33:03
	 */
	@Override
	public ParkingDetailDo getDefaultByUserId(int userId) {
		return parkingFeeDetailDao.getDefaultByUserId(userId);
	}

	@Override
	public int getParkingDetailCounts(int userId) {
		return parkingFeeDetailDao.getParkingDetailCounts(userId);
	}

	@Override
	public List<ParkingDetailDo> listParkingDetailsByUserId(int userId) {
		return parkingFeeDetailDao.listParkingDetailsByUserId(userId);
	}

	@Override
	public int insertParkingDetail(ParkingDetailDo parkingDetailDo) {
		return parkingFeeDetailDao.insertParkingDetail(parkingDetailDo);
	}

	@Override
	public int updateDefaultByPlateNo(int userId, String plateNo) {
		//先将所有的都设置为非默认，然后将当前用户车牌的冲抵信息设置为默认
		parkingFeeDetailDao.updateDefaultByPlateNo(userId , null , 0);
		return parkingFeeDetailDao.updateDefaultByPlateNo(userId , plateNo ,1);
	}

	@Override
	public int deleteParkingDetailById(int id) {
		ParkingDetailDo p = parkingFeeDetailDao.getById(id);
		if(p.getDefaultset()==1){//删除的是默认地址，则先设置其它的为默认
			parkingFeeDetailDao.updateAnotherDefaultParkingDetail(p.getUser_id(),p.getId());
		}
		return parkingFeeDetailDao.deleteParkingDetail(id);
	}

	@Override
	public ParkingFeeDo getByParams(long community, String plateNum) {
		
		return parkingFeeDao.getByParams(community,plateNum);
	}

	@Override
	public ParkingDetailDo getDetailByParams(long mainaddressid, String platenum,int userId) {
		return parkingFeeDetailDao.getDetailByParams(mainaddressid,platenum,userId);
	}

	@Override
	public List<ParkingDetailDo> getParkingDetailDo(ParkingDetailDo p) {
		return parkingFeeDetailDao.getParkingDetailDo(p);
	}


}
