/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.service.account;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IAdminService;
import com.hehenian.biz.common.account.dataobject.AdminDo;
import com.hehenian.biz.component.account.IAdminComponent;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("adminService")
public class AdminServiceImpl implements IAdminService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private IAdminComponent  adminComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AdminDo getById(int id){
	  return adminComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<AdminDo> selectAdmin(Map<String,Object> parameterMap){
		return adminComponent.selectAdmin(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateAdminById(AdminDo newAdminDo){
		return adminComponent.updateAdminById(newAdminDo);
	}
	
	/**
	 * 新增
	 */
	public int addAdmin(AdminDo newAdminDo){
		return adminComponent.addAdmin(newAdminDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return adminComponent.deleteById(id);
	}

}
