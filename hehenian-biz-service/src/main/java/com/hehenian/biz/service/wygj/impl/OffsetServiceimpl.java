/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.service.wygj.impl
 * @Title: OffsetServiceimpl.java
 * @Description: TODO
 *
 * @author: jiangwmf
 * @date 2015-5-6 下午5:21:51
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.service.wygj.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.wygj.IOffsetService;
import com.hehenian.biz.common.wygj.dataobject.OffsetDetailsDo;
import com.hehenian.biz.common.wygj.dataobject.OffsetRecordsDo;
import com.hehenian.biz.dal.wygj.IOffsetDetailsDao;
import com.hehenian.biz.dal.wygj.IOffsetRecordsDao;
@Service("offsetService")
public class OffsetServiceimpl implements IOffsetService {
	@Resource
	private IOffsetDetailsDao offsetDetailsDao;
	@Resource
	private IOffsetRecordsDao offsetRecordsDao;
	@Override
	public int insertOffsetRecord(OffsetRecordsDo ord) {
		return offsetRecordsDao.insertOffsetRecord(ord);
	}
	@Override
	public int insertOffsetDetail(OffsetDetailsDo odd) {
		return offsetDetailsDao.insertOffsetDetail(odd);
	}
	@Override
	public List<OffsetRecordsDo> listOffsetRecords(int userId,int infotype) {
		return offsetRecordsDao.listOffsetRecords(userId,infotype);
	}
	@Override
	public List<OffsetDetailsDo> listOffsetDetails(int tradeId) {
		return offsetDetailsDao.listOffsetDetails(tradeId);
	}
	@Override
	public OffsetRecordsDo getParkingOffsetJoinEndDate(long mainaddressid,
			String plateno) {
		return offsetRecordsDao.getParkingOffsetJoinEndDate(mainaddressid,plateno);
	}
	@Override
	public OffsetRecordsDo getManageOffsetJoinEndDate(long mainaddressid,
			String roomno) {
		return offsetRecordsDao.getManageOffsetJoinEndDate(mainaddressid,roomno);
	}

	


}
