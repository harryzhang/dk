/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.account.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AdminDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IAdminComponent;
import com.hehenian.biz.dal.account.IAdminDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("adminComponent")
public class AdminComponentImpl implements IAdminComponent {

	@Autowired
    private IAdminDao  adminDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AdminDo getById(int id){
	  return adminDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<AdminDo> selectAdmin(Map<String,Object> parameterMap){
		return adminDao.selectAdmin(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateAdminById(AdminDo newAdminDo){
		int result = adminDao.updateAdminById(newAdminDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addAdmin(AdminDo newAdminDo){
		return adminDao.addAdmin(newAdminDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return adminDao.deleteById(id);
	}

}
