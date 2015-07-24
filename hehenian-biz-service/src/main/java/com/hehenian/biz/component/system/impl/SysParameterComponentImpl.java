/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system.impl
 * @Title: SysParameterComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月9日 上午11:28:01
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.system.dataobject.SysParameterDo;
import com.hehenian.biz.component.system.ISysParameterComponent;
import com.hehenian.biz.dal.system.ISysParameterDao;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月9日 上午11:28:01
 */
@Component("sysParameterComponent")
public class SysParameterComponentImpl implements ISysParameterComponent {
    @Autowired
    private ISysParameterDao sysParameterDao;

    @Override
    public SysParameterDo getByParameterKey(String parameterKey) {
        return sysParameterDao.getByParameterKey(parameterKey);
    }

    @Override
    public int updateSysParameter(SysParameterDo sysParameterDo) {
        return sysParameterDao.updateSysParameter(sysParameterDo);
    }

}
