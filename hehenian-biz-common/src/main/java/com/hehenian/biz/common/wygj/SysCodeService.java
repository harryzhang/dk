/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj
 * @Title: SysCodeService.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-6 下午7:36:15
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj;

import java.util.List;

import com.hehenian.biz.common.wygj.dataobject.SysCodeDo;

public interface SysCodeService {
	
	/**
	 * 根据类型id查询地区集合
	 * @param type
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-7 下午10:04:00
	 */
	public List<SysCodeDo> queryAllSysCodeByType(Integer type[]);
	/**
	 * 根据对象返回集合
	 * @param codeDo
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-7 下午10:04:36
	 */
	public List<SysCodeDo> querySysCodeListByDo(SysCodeDo codeDo);
	
	public String getByCommunityCode(String id);

}
