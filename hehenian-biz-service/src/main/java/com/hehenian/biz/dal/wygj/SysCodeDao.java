/**  
 * @Project: hehenian-biz-service Maven Webapp
 * @Package com.hehenian.biz.dal.wygj
 * @Title: SysCodeDao.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-6 下午7:01:26
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.dal.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.SysCodeDo;

public interface SysCodeDao {
	
	/**
	 * 查询集合对象
	 * @param codeDo
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-7 下午10:02:33
	 */
	public List<SysCodeDo> querySysCodeListByDo(SysCodeDo codeDo);
	/**
	 * 根据类型返回集合
	 * @param type
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-7 下午10:03:04
	 */
	public List<SysCodeDo> queryAllSysCodeByType(Integer type[]);

	public String getByCommunityCode(String id);
}
