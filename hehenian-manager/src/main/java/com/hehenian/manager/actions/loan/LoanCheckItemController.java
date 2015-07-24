/**
 * @fileName LoanCheckItem.java
 * @auther liminglmf
 * @createDate 2015年6月2日
 */
package com.hehenian.manager.actions.loan;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanCheckItemService;
import com.hehenian.biz.common.loan.dataobject.LoanCheckDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckItemDo;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.actions.common.Maps;
import com.hehenian.manager.actions.common.PageDoUtil;
import com.hehenian.manager.commons.NewPagination;

/**
 * @author liminglmf
 *
 */
@Controller
@RequestMapping("/loancheck/*")
public class LoanCheckItemController extends BaseAction{
	
	private Logger logger = Logger.getLogger(LoanCheckItemController.class);
	
	Map<Object, Object> map_success = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_SUCCESS);
	Map<Object, Object> map_failure  = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_FAILURE);
	
	@Autowired
	private ILoanCheckItemService loanCheckItemService;

	/*######################################评分项配置#####begin##################################################*/		
	/**
	 * 评分项配置管理入口
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/item/index")
	public String menuCheckItem(ModelMap modelMap){
		return "/loan/loanCheckItemIndex";
	}
	
	/**
	 * 评分项配置列表
	 * @auther liminglmf
	 * @date 2015年5月11日
	 * @param pagination
	 * @param modelMap
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/item/list")
	public void checkItemList(NewPagination<LoanCheckItemDo> pagination,ModelMap modelMap,HttpServletResponse response){
		PageDo<LoanCheckItemDo> page = PageDoUtil.getPage(pagination);
		Map<String,Object> param = new HashMap<String,Object>();
		
		
		String checkItemName = getString("checkItemName");
		if(StringUtils.isNotBlank(checkItemName)){
			param.put("checkItemName", checkItemName);
		}
		
		String status = getString("status");
		if(StringUtils.isNotBlank(status)){
			param.put("status", status);
		}
		
		page = loanCheckItemService.getCheckItemPage(param,page);
		pagination = PageDoUtil.getPageValue(pagination, page);
		outPrint(response, JSONObject.fromObject(pagination));
	}
	
	@RequestMapping("/item/toedit")
	public String loanCheckItemTo(Long checkId,ModelMap modelMap){
		LoanCheckItemDo loanCheckItemDo = null;
		if(checkId != null){
			loanCheckItemDo = loanCheckItemService.getById(checkId);
		}else{
			loanCheckItemDo = new LoanCheckItemDo();
			loanCheckItemDo.setStatus("T");
		}
		modelMap.addAttribute("loanCheckItemDo",loanCheckItemDo);
		return "loan/editLoanCheckItem";
	}
	
	/**
	 * 
	 * @auther liminglmf
	 * @date 2015年6月2日
	 * @param loanCheckItemDo
	 * @param response
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public void saveCheckItem(LoanCheckItemDo loanCheckItemDo,HttpServletResponse response){
		int i = 0;
		Date date = new Date();
		if(loanCheckItemDo.getCheckId() != null && !"".equals(loanCheckItemDo.getCheckId())){
			loanCheckItemDo.setUpdatetime(date);
			i = loanCheckItemService.updateLoanCheckItemById(loanCheckItemDo);
		}else{
			loanCheckItemDo.setCreatetime(date);
			loanCheckItemDo.setUpdatetime(date);
			loanCheckItemDo.setCreateby(Long.parseLong(getUserId()+""));
			i = loanCheckItemService.addLoanCheckItem(loanCheckItemDo);
		}
		if(i <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
		}
        outPrint(response, JSONSerializer.toJSON(map_success));
	}
	
	@RequestMapping("/item/delete")
	@ResponseBody
	public void deleteCheckItem(Long id,HttpServletResponse response){
		int i = 0;
		if(id != null){
			int count = loanCheckItemService.countDetailByCheckId(id);
			if(count > 0){
				Map<Object, Object> map = Maps.mapByAarray(EXECUTE_STATUS,"2","msg","评分项下包含<font style='color:red;'>"+count+"</font>条目信息，请先删除条目信息");
				outPrint(response, JSONSerializer.toJSON(map));
			}else{
				i = loanCheckItemService.deleteById(id);
				if(i <= 0){
					outPrint(response, JSONSerializer.toJSON(map_failure));
				}
				outPrint(response, JSONSerializer.toJSON(map_success));
			}
		}else{
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
	}
	
/*######################################评分项配置#####end##################################################*/		

/*######################################评分项条目配置#####begin##################################################*/	
	/**
	 * 评分项条目配置管理入口
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/detail/index")
	public String menuCheckDetail(ModelMap modelMap){
		return "/loan/loanCheckDetailIndex";
	}
	
	/**
	 * 评分项条目明细配置列表
	 * @auther liminglmf
	 * @date 2015年5月11日
	 * @param pagination
	 * @param modelMap
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/detail/list")
	public void checkDetailList(NewPagination<LoanCheckDetailDo> pagination,ModelMap modelMap,HttpServletResponse response){
		PageDo<LoanCheckDetailDo> page = PageDoUtil.getPage(pagination);
		Map<String,Object> param = new HashMap<String,Object>();
		
		String checkItemName = getString("checkItemName");
		if(StringUtils.isNotBlank(checkItemName)){
			param.put("checkItemName", checkItemName);
		}
		
		String status = getString("status");
		if(StringUtils.isNotBlank(status)){
			param.put("status", status);
		}
		
		String valType = getString("valType");
		if(StringUtils.isNotBlank(valType)){
			param.put("valType", valType);
		}
		
		page = loanCheckItemService.getCheckDetailPage(param,page);
		pagination = PageDoUtil.getPageValue(pagination, page);
		outPrint(response, JSONObject.fromObject(pagination));
	}
	
	@RequestMapping("/detail/toadd")
	public String loanCheckDetailToadd(Long checkId,ModelMap modelMap){
		LoanCheckDetailDo loanCheckDetailDo = new LoanCheckDetailDo();
		loanCheckDetailDo.setCheckId(checkId);
		modelMap.addAttribute("loanCheckDetailDo",loanCheckDetailDo);
		return "loan/editLoanCheckDetail";
	}
	
	@RequestMapping("/detail/toupdate")
	public String loanCheckDetailToupdate(Long id,ModelMap modelMap){
		LoanCheckDetailDo loanCheckDetailDo = loanCheckItemService.getCheckDetailById(id);
		modelMap.addAttribute("loanCheckDetailDo",loanCheckDetailDo);
		return "loan/editLoanCheckDetail";
	}
	
	/**
	 * 
	 * @auther liminglmf
	 * @date 2015年6月2日
	 * @param loanCheckDetailDo
	 * @param response
	 */
	@RequestMapping("/detail/save")
	@ResponseBody
	public void saveCheckDetail(LoanCheckDetailDo loanCheckDetailDo,HttpServletResponse response){
		int i = 0;
		if(loanCheckDetailDo.getItemCheckId() != null && !"".equals(loanCheckDetailDo.getItemCheckId())){
			i = loanCheckItemService.updateLoanCheckDetailById(loanCheckDetailDo);
		}else{
			i = loanCheckItemService.addLoanCheckDetail(loanCheckDetailDo);
		}
		if(i <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
		}
        outPrint(response, JSONSerializer.toJSON(map_success));
	}
	
	@RequestMapping("/detail/delete")
	@ResponseBody
	public void deleteCheckDetail(String ids,HttpServletResponse response){
		int i = 0;
		if(StringUtils.isNotBlank(ids)){
			i = loanCheckItemService.deleteCheckDetailIds(ids);
			if(i <= 0){
				outPrint(response, JSONSerializer.toJSON(map_failure));
			}
			outPrint(response, JSONSerializer.toJSON(map_success));
		}else{
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
	}
/*######################################评分项条目配置#####end##################################################*/		
	
}
