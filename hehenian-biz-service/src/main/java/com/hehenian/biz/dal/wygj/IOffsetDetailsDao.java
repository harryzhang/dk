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

public interface IOffsetDetailsDao {
	
	
	public int insertOffsetDetail(OffsetDetailsDo odd);
	
	
	public List<OffsetDetailsDo> listOffsetDetails(int tradeId);
	
}
