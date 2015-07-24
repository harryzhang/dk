/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: IOffsetDao.java
 * @Description: TODO
 *
 * @author: jiangwmf
 * @date 2015-5-6 上午11:22:24
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.OffsetDetailsDo;
import com.hehenian.biz.common.wygj.dataobject.OffsetRecordsDo;

public interface IOffsetRecordsDao {
	
	public int insertOffsetRecord(OffsetRecordsDo ord);
	
	
	public List<OffsetRecordsDo> listOffsetRecords(int userId,int infotype);
	

	public OffsetRecordsDo getParkingOffsetJoinEndDate(long mainaddressid,
			String plateno);
	public OffsetRecordsDo getManageOffsetJoinEndDate(long mainaddressid,
			String roomno) ;
	
}
