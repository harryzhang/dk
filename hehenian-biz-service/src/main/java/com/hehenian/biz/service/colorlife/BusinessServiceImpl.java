/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.service.colorlife
 * @Title: BusinessServiceImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-8 下午9:53:40
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.service.colorlife;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hehenian.biz.common.colorlife.IBusinessService;
import com.hehenian.biz.common.colorlife.dataobject.BusinessDo;
import com.hehenian.biz.dal.colorlife.IBusinessDao;

@Service("businessService")
public class BusinessServiceImpl implements IBusinessService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IBusinessDao businessDao;

	@Override
	public int addBusiness(BusinessDo businessDo) {
		BusinessDo busiDo = getBusinessByDo(businessDo);
		if(busiDo==null){
			return businessDao.addBusiness(businessDo);
		}
		return 0;
	}

	@Override
	public int updateBusiness(BusinessDo businessDo) {
		return businessDao.updateBusiness(businessDo);
	}

	@Override
	public BusinessDo getBusinessByDo(BusinessDo businessDo) {
		return businessDao.getBusinessByDo(businessDo);
		
	}
	
	public List<BusinessDo> queryFailBusinessList(){
		return businessDao.queryFailBusinessList();
	}
}
