/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system.impl
 * @Title: FeeRuleComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:04:57
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.system.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.system.ICommonQueryComponent;

/**
 * 
 * 
 *@author xiexiangmf
 * @date 2015年3月11日下午3:24:51
 */
@Component("commonQueryComponent")
public class CommonQueryComponentImpl implements ICommonQueryComponent {
    
    @Autowired
    private org.mybatis.spring.SqlSessionFactoryBean sqlSession;

	@Override
	public List<Map<String, Object>> getMap(Map<String, Object> searchItems) {
		SqlSession session = null;
		List<Map<String, Object>> list = null;
		try {
			session = sqlSession.getObject().openSession();
			list =  session.selectList(searchItems.get("selectMethodId")+"", searchItems);
		} catch (SecurityException e) {
			throw new BusinessException("通用查询出错!");
		} catch (NoSuchFieldException e) {
			throw new BusinessException("通用查询出错!");
		} catch (Exception e) {
			throw new BusinessException("通用查询出错!");
		}finally {
			session.close();
		}
		return list;
	}

	@Override
	public int getTotalCount(Map<String, Object> searchItems) {
		int count = 0;
		SqlSession session = null;
		try {
			session = sqlSession.getObject().openSession();
			count = (Integer)session.selectOne(searchItems.get("countMethod")+"", searchItems);
		} catch (SecurityException e) {
			throw new BusinessException("通用查询总记录出错!");
		} catch (NoSuchFieldException e) {
			throw new BusinessException("通用查询总记录出错!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("通用查询总记录出错!");
		}finally {
			session.close();
		}
		return count;
	}

	@Override
	public Map<String, Object> getData(Map<String, Object> searchItems) {
		Map<String, Object> dataMap = null;
		SqlSession session = null;
		try {
			session = sqlSession.getObject().openSession();
			dataMap = (Map<String, Object>)session.selectOne(searchItems.get("selectMethodId")+"", searchItems);
		} catch (SecurityException e) {
			throw new BusinessException("通用查询总记录出错!");
		} catch (NoSuchFieldException e) {
			throw new BusinessException("通用查询总记录出错!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("通用查询总记录出错!");
		}finally {
			session.close();
		}
		return dataMap;
	}
}
