package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.trade.IGuaranteeInstitutionsService;
import com.hehenian.biz.common.trade.dataobject.GuaranteeInstitutionsDo;
import com.hehenian.biz.component.trade.IGuaranteeInstitutionsComponent;
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


@Service("guaranteeInstitutionsService")
public class GuaranteeInstitutionsServiceImpl implements IGuaranteeInstitutionsService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private IGuaranteeInstitutionsComponent  guaranteeInstitutionsComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public GuaranteeInstitutionsDo getById(int id){
	  return guaranteeInstitutionsComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<GuaranteeInstitutionsDo> selectGuaranteeInstitutions(Map<String,Object> parameterMap){
		return guaranteeInstitutionsComponent.selectGuaranteeInstitutions(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateGuaranteeInstitutionsById(GuaranteeInstitutionsDo newGuaranteeInstitutionsDo){
		return guaranteeInstitutionsComponent.updateGuaranteeInstitutionsById(newGuaranteeInstitutionsDo);
	}
	
	/**
	 * 新增
	 */
	public int addGuaranteeInstitutions(GuaranteeInstitutionsDo newGuaranteeInstitutionsDo){
		return guaranteeInstitutionsComponent.addGuaranteeInstitutions(newGuaranteeInstitutionsDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return guaranteeInstitutionsComponent.deleteById(id);
	}

}
