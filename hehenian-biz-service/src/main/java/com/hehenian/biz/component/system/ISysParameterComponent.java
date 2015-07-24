/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system
 * @Title: ISysParameterComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月8日 下午2:50:26
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.system;

import com.hehenian.biz.common.system.dataobject.SysParameterDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月8日 下午2:50:26
 */
public interface ISysParameterComponent {

    /**
     * 根据参数KEY查询系统参数信息
     * 
     * @param parameterKey
     * @return
     * @author: liuzgmf
     * @date: 2015年1月8日下午2:52:34
     */
    SysParameterDo getByParameterKey(String parameterKey);

    /**
     * 修改系统参数信息
     * 
     * @param sysParameterDo
     * @author: liuzgmf
     * @date: 2015年1月8日下午2:53:43
     */
    int updateSysParameter(SysParameterDo sysParameterDo);

}
