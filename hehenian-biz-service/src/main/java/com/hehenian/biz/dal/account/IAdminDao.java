/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.dal.account;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AdminDo;

public interface IAdminDao {

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public AdminDo getById(int id);

    /**
     * 根据条件查询列表
     */
    public List<AdminDo> selectAdmin(Map<String, Object> parameterMap);

    /**
     * 更新
     */
    public int updateAdminById(AdminDo newAdminDo);

    /**
     * 新增
     */
    public int addAdmin(AdminDo newAdminDo);

    /**
     * 删除
     */
    public int deleteById(int id);

    /**
     * 根据ID查询管理员信息
     * 
     * @param adminId
     * @return
     * @author: liuzgmf
     * @date: 2014年9月25日下午2:30:10
     */
    AdminDo getById(Long adminId);

}
