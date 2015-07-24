/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.service.wygj.impl
 * @Title: SysCodeServiceImpl.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-6 下午7:35:57
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.service.wygj.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.wygj.SysCodeService;
import com.hehenian.biz.common.wygj.dataobject.SysCodeDo;
import com.hehenian.biz.dal.wygj.SysCodeDao;
@Service("sysCodeService")
public class SysCodeServiceImpl implements SysCodeService{
	
	@Autowired
	private SysCodeDao sysCodeDao;

	@Override
	public List<SysCodeDo> queryAllSysCodeByType(Integer type[]) {
		return sysCodeDao.queryAllSysCodeByType(type);
	}

	@Override
	public List<SysCodeDo> querySysCodeListByDo(SysCodeDo codeDo) {
		return sysCodeDao.querySysCodeListByDo(codeDo);
	}

	@Override
	public String getByCommunityCode(String id) {
		return sysCodeDao.getByCommunityCode(id);
	}

}
