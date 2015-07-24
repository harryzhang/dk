/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: IParkingFeeDao.java
 * @Description: TODO
 *
 * @author: zhangjhmf
 * @date 2015-4-29 上午11:22:24
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;

public interface IParkingFeeDao {
	public ParkingFeeDo getById(Integer id) ;

	public List<ParkingFeeDo> listParkingFee(Object[] obj);
	
	public int insertParkingFee(ParkingFeeDo pf);
	
	public int updateParkingFee(ParkingFeeDo pf);
	
	public ParkingFeeDo getByParams(long community, String plateNum);
}
