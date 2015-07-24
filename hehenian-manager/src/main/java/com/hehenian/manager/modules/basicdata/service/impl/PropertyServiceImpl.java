/**
 * Project Name:hehenian-manager
 * File Name:PropertyServiceImpl.java
 * Package Name:com.hehenian.manager.modules.basicdata.service.impl
 * Date:2015年5月5日下午1:57:25
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
 */

package com.hehenian.manager.modules.basicdata.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.manager.commons.ExcelUtils;
import com.hehenian.manager.commons.Result;
import com.hehenian.manager.modules.basicdata.dao.PropertyDao;
import com.hehenian.manager.modules.basicdata.dao.SysCodeDao;
import com.hehenian.manager.modules.basicdata.model.Parking;
import com.hehenian.manager.modules.basicdata.model.Property;
import com.hehenian.manager.modules.basicdata.model.SysCode;
import com.hehenian.manager.modules.basicdata.service.PropertyService;

/**
 * 
 * @author songxjmf
 * @date: 2015年5月5日 下午1:57:25
 */
@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

	private final static Logger LOG = Logger
			.getLogger(PropertyServiceImpl.class);

	private DecimalFormat df = new DecimalFormat("#");
	
	@Autowired
	private SysCodeDao sysCodeDao;
	@Autowired
	private PropertyDao propertyDao;

	@Override
	public Result importPropertyData(String filePath) {
		LOG.info("从excel文件[" + filePath + "]导入物业数据");
		Result result = new Result(true);
		// 从excel读取多宝车宝物业数据
		int[] cellCounts = { 10, 8 };
		try {
			Map<String, List<String[]>> dataMap = ExcelUtils.read(filePath,
					cellCounts);
			for (Map.Entry<String, List<String[]>> entry : dataMap.entrySet()) {
				List<String[]> data = entry.getValue();
				if(entry.getKey().contains("多宝")){
					// 保存多宝数据
					savePropertyData(data);
				}else if(entry.getKey().contains("車宝")){
					// 保存車宝数据
					saveParkingData(data);
				}else{
					LOG.warn("未知的数据类型["+entry.getKey()+"]");
				}
			}
			return result;
		} catch (Exception e) {
			LOG.error("从excel读取多宝车宝物业数据异常", e);
			return new Result(false, "", e.getMessage());
		}

	}

	/**
	 * 保存多宝物业数据
	 *
	 * @author songxjmf
	 * @date: 2015年5月5日 下午6:07:05
	 */
	private void saveParkingData(List<String[]> parkingData) {
		if (parkingData == null) {
			return;
		}
		int length = parkingData.size();
		for (int i = 0; i < length; i++) {
			// 忽略标题行
			if (i < 2) {
				continue;
			}
			String[] rowData = parkingData.get(i);
			// 市
			String city = rowData[1];
			// 区县
			String district = rowData[2];
			// 小区/大厦
			String community = rowData[3];
			// 车库类型
			String grageType = rowData[4];
			// 车卡号
			String plateNumber = rowData[5];
			// 排量
			String carEmissions = rowData[6];
			// 停车费
			String parkingFee = rowData[7];
			
			try {
				Parking parking = new Parking();
				parking.setCarEmissions(trim(carEmissions));
				parking.setCreateTime(new Date());
				parking.setMainAddressId(getAddressCode(trim(city),trim(district), trim(community)));
				parking.setParkingFee(Double.valueOf(trim(parkingFee).replace("￥", "")));
				parking.setPlateNumber(trim(plateNumber).replace(".00", "").replace(".0", ""));
				parking.setTheArageType(Parking.getGarageType(trim(grageType)));
				propertyDao.insertParkingData(parking);
			} catch (Exception e) {
				LOG.error("保存车宝数据异常[plateNumber="+plateNumber+"]",e);
			} 
		}
	}
	
	
	/**
	 * 保存多宝物业数据
	 *
	 * @author songxjmf
	 * @date: 2015年5月5日 下午6:07:05
	 */
	private void savePropertyData(List<String[]> propertyData) {
		if (propertyData == null) {
			return;
		}
		int length = propertyData.size();
		for (int i = 0; i < length; i++) {
			// 忽略标题行
			if (i < 2) {
				continue;
			}
			String[] rowData = propertyData.get(i);
			// 业主姓名
			String ownerName = rowData[0];
			// 市
			String city = rowData[2];
			// 区县
			String district = rowData[3];
			// 小区/大厦
			String community = rowData[4];
			// 楼栋
			String buildingNum = rowData[5];
			// 房间号
			String roomNum = rowData[6];
			// 房屋类型
			String roomType = rowData[7];
			// 房屋面积
			String floorArea = rowData[8];
			// 物业费
			String propertyFee = rowData[9];
			try {
				Property property = new Property();
				try {
					property.setBuilding(df.format(Double.valueOf(trim(buildingNum))));
				} catch (Exception e) {
					property.setBuilding(trim(buildingNum));
				}
				property.setFloorArea(Double.valueOf(trim(floorArea)));
				property.setPropertyFee(Double.valueOf(trim(propertyFee)));
				property.setRoomNum(trim(roomNum));
				property.setRoomType(Property.getRommType(trim(roomType)));
				property.setTheowner(trim(ownerName));
				property.setMainAddressId(getAddressCode(trim(city),trim(district), trim(community)));
				property.setCreateTime(new Date());
				propertyDao.insertPropertyData(property);
			} catch (Exception e) {
				LOG.error("保存多宝数据异常[ownerName="+ownerName+"]",e);
			} 
		}
	}

	private synchronized Long getAddressCode(String city,String district, String community) throws Exception {
		// 首先通过小区找编码,找到了直接返回
		Long code = sysCodeDao.getSubjectByNameAndType(district,community, 5);
		if (code == null) {
			// 没有找到小区编码则找小区所属区县的编码
			code = sysCodeDao.getSubjectByNameAndType(city,district, 4);
			if (code == null) {
				throw new Exception("找不到区县[" + district + "]");
			}
			Long communityCode = 0l;
			// 找区县下面小区的最大编号
			Long maxCommunityCode = sysCodeDao.getMaxCommunityId(code);
			if(maxCommunityCode == null){
				// 没找到最大编码，区县编码+1
				communityCode = code + 1;
			}else{
				// 最大的小区编码+1
				communityCode = maxCommunityCode + 1;
			}
			// 新增小区数据
			SysCode ss = new SysCode();
			ss.setCreateTime(new Date());
			ss.setId(communityCode);
			ss.setParentId(code);
			ss.setParentTypeId(4);
			ss.setRemark(community);
			ss.setRemarkForShow(community);
			ss.setTypeId(5);
			ss.setInvalid(SysCode.INVALID_OFF);
			sysCodeDao.insertSubject(ss);
			return communityCode;
		}
		return code;
	}
	
	private String trim(String value){
		return value==null?"":value.trim();
	}

}
