/**
 * Project Name:hehenian-manager
 * File Name:PropertyDaoImpl.java
 * Package Name:com.hehenian.manager.modules.basicdata.service.impl
 * Date:2015年5月5日下午2:36:49
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.modules.basicdata.dao.SysCodeDao;
import com.hehenian.manager.modules.basicdata.model.SysCode;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 下午2:36:49 	 
 */
@Repository("sysCodeDao")
public class SysCodeDaoImpl implements SysCodeDao {

	@Resource
	protected NamedParameterJdbcTemplate sp2pNameJdbcTemplate;
	
	/**
	 * 根据省/市/区/小区、级别查找编码
	 */
	@Override
	public Long getSubjectByNameAndType(String parentSubjectName,String subjectName, int sysCodeType) {
		String sql = "SELECT s2.id FROM syscode s1,syscode s2 WHERE s1.id = s2.parentId AND s1.remark = ? AND s2.remark = ? AND s1.invalid = 0 AND s2.invalid = 0 AND s2.typeId = ?";
		Object obj;
		try {
			obj = sp2pNameJdbcTemplate.getJdbcOperations().queryForObject(sql, Long.class, parentSubjectName,subjectName,sysCodeType);
		} catch (Exception e) {
			return null;
		}
		return Long.valueOf(obj.toString());
	}
	
	/**
	 * 找区县下面最大的小区编号
	 *
	 * @author songxjmf
	 * @date: 2015年5月5日 下午6:00:47
	 */
	public Long getMaxCommunityId(Long districtId){
		String sql = "SELECT MAX(id) FROM syscode WHERE parentId = ?";
		Object obj;
		try {
			obj = sp2pNameJdbcTemplate.getJdbcOperations().queryForObject(sql, Long.class, districtId);
		} catch (Exception e) {
			return null;
		}
		return obj==null?null:Long.valueOf(obj.toString());
	}
	
	/**
	 * 新增省/市/区/小区
	 * @author songxjmf
	 * @date: 2015年5月5日 下午3:20:51
	 */
	@Override
	public void insertSubject(SysCode sysCode){
		String sql = "INSERT INTO syscode (typeId,id,parentTypeId,parentId,remark,remarkForShow,invalid,createTime)VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		sp2pNameJdbcTemplate.getJdbcOperations().update(sql, sysCode.getTypeId(),sysCode.getId(),sysCode.getParentTypeId(),sysCode.getParentId(),sysCode.getRemark(),sysCode.getRemarkForShow(),sysCode.getInvalid(),sysCode.getCreateTime());
	}
}

