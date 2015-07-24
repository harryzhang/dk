package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.trade.IBorrowTypeLogService;
import com.hehenian.biz.common.trade.dataobject.BorrowTypeLogDo;
import com.hehenian.biz.component.trade.IBorrowTypeLogComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("borrowTypeLogService")
public class BorrowTypeLogServiceImpl implements IBorrowTypeLogService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private IBorrowTypeLogComponent  borrowTypeLogComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public BorrowTypeLogDo getById(int id){
	  return borrowTypeLogComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<BorrowTypeLogDo> selectBorrowTypeLog(Map<String,Object> parameterMap){
		return borrowTypeLogComponent.selectBorrowTypeLog(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateBorrowTypeLogById(BorrowTypeLogDo newBorrowTypeLogDo){
		return borrowTypeLogComponent.updateBorrowTypeLogById(newBorrowTypeLogDo);
	}
	
	/**
	 * 新增
	 */
	public int addBorrowTypeLog(BorrowTypeLogDo newBorrowTypeLogDo){
		return borrowTypeLogComponent.addBorrowTypeLog(newBorrowTypeLogDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return borrowTypeLogComponent.deleteById(id);
	}

}
