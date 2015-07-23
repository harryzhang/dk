/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: IOffsetService.java
 * @Description: TODO
 *
 * @author: jiangwmf
 * @date 2015-5-6 下午4:43:03
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.OffsetDetailsDo;
import com.hehenian.biz.common.wygj.dataobject.OffsetRecordsDo;

public interface IOffsetService {
	
	
	int insertOffsetRecord(OffsetRecordsDo ord);
	
	int insertOffsetDetail(OffsetDetailsDo odd);
	
	public List<OffsetRecordsDo> listOffsetRecords(int userId,int infotype);
	
	public List<OffsetDetailsDo> listOffsetDetails(int tradeId);
	
	public OffsetRecordsDo getParkingOffsetJoinEndDate(long mainaddressid,String plateno);
	
	public OffsetRecordsDo getManageOffsetJoinEndDate(long mainaddressid,String roomno);
}
