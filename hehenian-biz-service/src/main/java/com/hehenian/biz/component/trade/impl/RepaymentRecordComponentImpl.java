/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentRecordDo;
import com.hehenian.biz.component.trade.IRepaymentRecordComponent;
import com.hehenian.biz.dal.trade.IRepaymentRecordDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("repaymentRecordComponent")
public class RepaymentRecordComponentImpl implements IRepaymentRecordComponent {

	@Autowired
    private IRepaymentRecordDao  repaymentRecordDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RepaymentRecordDo getById(int id){
	  return repaymentRecordDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<RepaymentRecordDo> selectRepaymentRecord(Map<String,Object> parameterMap){
		return repaymentRecordDao.selectRepaymentRecord(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateRepaymentRecordById(RepaymentRecordDo newRepaymentRecordDo){
		int result = repaymentRecordDao.updateRepaymentRecordById(newRepaymentRecordDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addRepaymentRecord(RepaymentRecordDo newRepaymentRecordDo){
		return repaymentRecordDao.addRepaymentRecord(newRepaymentRecordDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return repaymentRecordDao.deleteById(id);
	}

    /**
     * 
     * logProcess 记录处理进程 void
     * 
     * @exception
     * @since 1.0.0
     */
    public boolean logProcess(RepaymentDo repayment, String operationType) {
        // RepaymentDo repayment = this.getRepaymentDo();
        RepaymentRecordDo repaymentRecordDo = new RepaymentRecordDo();
        repaymentRecordDo.setRepayId(repayment.getId());
        repaymentRecordDo.setRepayAmount(repayment.getStillInterest() + repayment.getStillPrincipal()
                + repayment.getConsultFee() + repayment.getRepayFee() + repayment.getLateFi());
        repaymentRecordDo.setOporator(1l);
        repaymentRecordDo.setRemark(operationType);
        repaymentRecordDo.setCreateTime(new java.util.Date());
        repaymentRecordDo.setRepayType("1");
        repaymentRecordDo.setProcessStatus("1");
        this.addRepaymentRecord(repaymentRecordDo);
        return true;
    }

    /**
     * 
     * updateProcess 更新记录处理进程 void
     * 
     * @exception
     * @since 1.0.0
     */
    public boolean updateProcess(RepaymentDo repayment, String operationType) {
        // RepaymentDo repayment = this.getRepaymentDo();
        RepaymentRecordDo repaymentRecordDo = new RepaymentRecordDo();
        repaymentRecordDo.setRepayId(repayment.getId());
        repaymentRecordDo.setRepayAmount(repayment.getStillInterest() + repayment.getStillPrincipal()
                + repayment.getConsultFee() + repayment.getRepayFee() + repayment.getLateFi());
        repaymentRecordDo.setOporator(1l);
        repaymentRecordDo.setRemark(operationType);
        repaymentRecordDo.setCreateTime(new java.util.Date());
        repaymentRecordDo.setRepayType("1");
        repaymentRecordDo.setProcessStatus("2");
        this.addRepaymentRecord(repaymentRecordDo);
        return true;
    }

}
