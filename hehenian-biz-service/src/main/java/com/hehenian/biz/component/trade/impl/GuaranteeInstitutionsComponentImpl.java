package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.component.trade.IGuaranteeInstitutionsComponent;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.trade.dataobject.GuaranteeInstitutionsDo;
import com.hehenian.biz.dal.trade.IGuaranteeInstitutionsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("guaranteeInstitutionsComponent")
public class GuaranteeInstitutionsComponentImpl implements IGuaranteeInstitutionsComponent {

	@Autowired
    private IGuaranteeInstitutionsDao  guaranteeInstitutionsDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public GuaranteeInstitutionsDo getById(int id){
	  return guaranteeInstitutionsDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<GuaranteeInstitutionsDo> selectGuaranteeInstitutions(Map<String,Object> parameterMap){
		return guaranteeInstitutionsDao.selectGuaranteeInstitutions(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateGuaranteeInstitutionsById(GuaranteeInstitutionsDo newGuaranteeInstitutionsDo){
		return guaranteeInstitutionsDao.updateGuaranteeInstitutionsById(newGuaranteeInstitutionsDo);
	}
	
	/**
	 * 新增
	 */
	public int addGuaranteeInstitutions(GuaranteeInstitutionsDo newGuaranteeInstitutionsDo){
		return guaranteeInstitutionsDao.addGuaranteeInstitutions(newGuaranteeInstitutionsDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return guaranteeInstitutionsDao.deleteById(id);
	}

    /* (no-Javadoc) 
     * <p>Title: updateOrganMoney</p> 
     * <p>Description: </p> 
     * @param needAmount 
     * @see com.hehenian.biz.component.trade.IGuaranteeInstitutionsComponent#updateOrganMoney(double) 
     */
    @Override
    public int updateOrganMoney(double needAmount) {
        //更新担保公司的资金
        return guaranteeInstitutionsDao.updateOrganMoney(needAmount);        
        
    }

}
