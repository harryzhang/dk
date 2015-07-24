package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.component.trade.IBorrowTypeLogComponent;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.BorrowTypeLogDo;
import com.hehenian.biz.dal.trade.IBorrowTypeLogDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("borrowTypeLogComponent")
public class BorrowTypeLogComponentImpl implements IBorrowTypeLogComponent {

	@Autowired
    private IBorrowTypeLogDao  borrowTypeLogDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public BorrowTypeLogDo getById(int id){
	  return borrowTypeLogDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<BorrowTypeLogDo> selectBorrowTypeLog(Map<String,Object> parameterMap){
		return borrowTypeLogDao.selectBorrowTypeLog(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateBorrowTypeLogById(BorrowTypeLogDo newBorrowTypeLogDo){
		return borrowTypeLogDao.updateBorrowTypeLogById(newBorrowTypeLogDo);
	}
	
	/**
	 * 新增
	 */
	public int addBorrowTypeLog(BorrowTypeLogDo newBorrowTypeLogDo){
		return borrowTypeLogDao.addBorrowTypeLog(newBorrowTypeLogDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return borrowTypeLogDao.deleteById(id);
	}

}
