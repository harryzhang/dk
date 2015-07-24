/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.manager.actions.loan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanProductService;
import com.hehenian.biz.common.loan.ILoanProvidedService;
import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanProvidedDo;
import com.hehenian.biz.common.loan.dataobject.LoanSettSchemeDo;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.actions.common.Maps;
import com.hehenian.manager.actions.common.PageDoUtil;
import com.hehenian.manager.commons.NewPagination;
import com.hehenian.manager.modules.sys.model.UserInfos;

/**
 * @author liminglmf
 *
 */
@Controller
@RequestMapping("/loan/product/*")
public class LoanProductController extends BaseAction{
	
	@Autowired
	private ILoanProductService loanProductService;
	
	@Autowired
	private ILoanProvidedService loanProvidedService;
	
	Map<Object, Object> map_success = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_SUCCESS);
	Map<Object, Object> map_failure  = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_FAILURE);
	
	@RequestMapping("index")
	public String menuProd(ModelMap modelMap){
		return "/loan/loanProductIndex";
	}
	
	/**
	 * 产品列表
	 * @param pagination
	 * @param response
	 */
	@RequestMapping("list")
	@ResponseBody
	public void listDatas(NewPagination<LoanProductDo> pagination,
			HttpServletResponse response){
		PageDo<LoanProductDo> page = PageDoUtil.getPage(pagination);
		String name = getString("name");
		String code = getString("code");		
		String guarantee = getString("guarantee");
		String mortgage = getString("mortgage");
		String retaLock = getString("retaLock");
		String status = getString("status");
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(name)){
			param.put("name", name);
		}
		if(StringUtils.isNotBlank(code)){
			param.put("code", code);
		}
		if(StringUtils.isNotBlank(mortgage)){
			param.put("mortgage", mortgage);
		}
		if(StringUtils.isNotBlank(guarantee)){
			param.put("guarantee", guarantee);
		}
		if(StringUtils.isNotBlank(retaLock)){
			param.put("retaLock", retaLock);
		}
		if(StringUtils.isNotBlank(status)){
			param.put("status", status);
		}
		page = loanProductService.getLoanProductPage(param, page);
		pagination = PageDoUtil.getPageValue(pagination, page);
		outPrint(response, JSONObject.fromObject(pagination, getDefaultJsonConfig()));
	}
	
	/**
	 * 跳转编辑页面
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@RequestMapping("toedit")
	public String toedit(ModelMap modelMap,Long id){
		LoanProductDo loanProductDo = null;
		if(id != null){
			loanProductDo = loanProductService.getProdById(id);
		}else{
			loanProductDo = new LoanProductDo();
			loanProductDo.setGuarantee("0");
			loanProductDo.setMortgage("0");
			loanProductDo.setRetaLock("1");
			loanProductDo.setStatus("T");
		}
		modelMap.addAttribute("prodDo", loanProductDo);
		return "/loan/editLoanProduct";
	}
	
	/**
	 * 保存产品信息
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param loanProductDo
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void addLoanProduct(LoanProductDo loanProductDo,HttpServletResponse response){
		int i = 0;
		if(loanProductDo.getId() !=null && !"".equals(loanProductDo.getId())){
			 i = loanProductService.updateLoanProductDo(loanProductDo);
		}else{
			 i = loanProductService.addLoanProductDo(loanProductDo);
		}
		if(i <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
		}
        //return JSONSerializer.toJSON(map);
        outPrint(response, JSONSerializer.toJSON(map_success));
	}
	
	/**
	 * 删除产品信息
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void deleteLoanProduct(HttpServletResponse response,String ids){
		int i = 0;
		if(StringUtils.isNotBlank(ids)){
			i = loanProductService.deleteLoanProductDo(ids);
		}else{
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
		if(i <= 0){
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
		outPrint(response, JSONSerializer.toJSON(map_success));
	}
	
	/**
	 * 查看产品明细
	 * @auther liminglmf
	 * @date 2015年5月12日
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String prodDetail(Long id,ModelMap modelMap){
		LoanProductDo loanProductDo = loanProductService.getProdById(id);
		modelMap.addAttribute("prodDo", loanProductDo);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodId", id);
		List<LoanProvidedDo> provList = loanProvidedService.getLoanProvidList(param);
		if(provList != null && provList.size() >0){
			modelMap.addAttribute("provDoBolean", "1");
			modelMap.addAttribute("provDo", provList.get(0));
		}else{
			modelMap.addAttribute("provDoBolean", "0");
		}
		List<LoanSettSchemeDo> listSett = loanProductService.queryByProdId(id);
		if(listSett != null && listSett.size() >0){
			modelMap.addAttribute("settDoBolean", "1");
			modelMap.addAttribute("settDoList", listSett);
		}else{
			modelMap.addAttribute("settDoBolean", "0");
		}
		return "loan/loanProdDetail";
	}
	
/************************************************产品前提**********begin******************************************************/
	
	@RequestMapping("/prov/toedit")
	public String toAddProv(Long prodId,ModelMap modelMap){
		int count  = loanProvidedService.selectCountByProdId(prodId);
		LoanProvidedDo  loanProvidedDo = null;
		if(count > 0){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("prodId", prodId);
			List<LoanProvidedDo> provList = loanProvidedService.getLoanProvidList(param);
			loanProvidedDo=provList.get(0);
		}else{
			loanProvidedDo = new LoanProvidedDo();
			loanProvidedDo.setProdId(prodId);
			loanProvidedDo.setStatus("T");
			loanProvidedDo.setIfpunch("1");
		}
		
		modelMap.addAttribute("provDo", loanProvidedDo);
		return "loan/editLoanProvid";
	}
	
	@RequestMapping("/prov/save")
	@ResponseBody
	public void addLoanProvid(LoanProvidedDo loanProvidedDo,HttpServletResponse response){
		int i = 0;
		if(loanProvidedDo.getId() != null && !"".equals(loanProvidedDo.getId())){
			 i = loanProvidedService.updateLoanProvidedDo(loanProvidedDo);
		}else{
			if(loanProvidedDo.getProdId() == null){
				outPrint(response, JSONSerializer.toJSON(map_failure));
			}
			 i = loanProvidedService.addLoanProvidedDo(loanProvidedDo);
		}
		if(i <= 0){
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
		outPrint(response, JSONSerializer.toJSON(map_success));
	}

/************************************************产品前提******end**********************************************************/
	/************************************************产品方案**********************begin******************************************/	
	@RequestMapping("/sett/toadd")
	public String toAddSett(Long prodId,ModelMap modelMap){
		LoanSettSchemeDo  loanSettSchemeDo = new LoanSettSchemeDo();
		loanSettSchemeDo.setProdId(prodId);
		loanSettSchemeDo.setStatus("P");
		modelMap.addAttribute("settDo", loanSettSchemeDo);
		return "loan/editLoanSett";
	}
	
	@RequestMapping("/sett/toedit")
	public String toEditSett(Long id,ModelMap modelMap){
		LoanSettSchemeDo  loanSettSchemeDo = loanProductService.getLoanSettById(id);
		modelMap.addAttribute("settDo", loanSettSchemeDo);
		return "loan/editLoanSett";
	}
	
	
	@RequestMapping("/sett/save")
	@ResponseBody
	public void addLoanSett(LoanSettSchemeDo loanSettSchemeDo,HttpServletResponse response){
		int i = 0;
		UserInfos userDetails = (UserInfos) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(loanSettSchemeDo.getId() !=null && !"".equals(loanSettSchemeDo.getId())){
			loanSettSchemeDo.setUpdateUserId(Long.parseLong(userDetails.getUserId()+""));
			 i = loanProductService.updateLoanSettSchemeDo(loanSettSchemeDo);
		}else{
			loanSettSchemeDo.setCreateUserId(Long.parseLong(userDetails.getUserId()+""));
			loanSettSchemeDo.setUpdateUserId(Long.parseLong(userDetails.getUserId()+""));
			 i = loanProductService.addLoanSettSchemeDo(loanSettSchemeDo);
		}
		if(i <=0){
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
		outPrint(response, JSONSerializer.toJSON(map_success));
	}
	
	@RequestMapping("/sett/index")
	public String menuProdSett(ModelMap modelMap){
		return "/loan/loanSettIndex";
	}
	
	@RequestMapping("/sett/list")
	@ResponseBody
	public void listSett(NewPagination<LoanSettSchemeDo> pagination,
			HttpServletResponse response){
		PageDo<LoanSettSchemeDo> page = PageDoUtil.getPage(pagination);
		String strProdId = getString("prodId");
		String status = getString("status");
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(strProdId) && !"0".equals(strProdId)){
			param.put("prodId", Long.parseLong(strProdId));
		}
		if(StringUtils.isNotBlank(status)){
			param.put("status", Long.parseLong(status));
		}
		page = loanProductService.getLoanSettPage(param, page);
		pagination = PageDoUtil.getPageValue(pagination, page);
		outPrint(response, JSONObject.fromObject(pagination, getDefaultJsonConfig()));
	}
	
	@RequestMapping("/sett/delete")
	@ResponseBody
	public void deleteLoanSett(String ids,HttpServletResponse response){
		if(StringUtils.isNotBlank(ids)){
			int i = loanProductService.deleteLoanSettDo(ids);
			if(i <= 0){
				outPrint(response, JSONSerializer.toJSON(map_failure));
			}
			outPrint(response, JSONSerializer.toJSON(map_success));
		}else{
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
	}
	
/************************************************产品方案**********************end******************************************/

/************************************************产品方案规则****************begin************************************************/
	@RequestMapping("/fee/toadd")
	public String toAddFee(Long settId,ModelMap modelMap){
		LoanFeeRuleDo  loanFeeRuleDo = new LoanFeeRuleDo();
		loanFeeRuleDo.setSchemeId(settId);
		loanFeeRuleDo.setIsInclude("F");
		loanFeeRuleDo.setIsInitRepayPlanUse("F");
		modelMap.addAttribute("feeDo", loanFeeRuleDo);
		return "loan/editLoanFee";
	}
	
	@RequestMapping("/fee/toedit")
	public String toeditFee(Long id,ModelMap modelMap){
		LoanFeeRuleDo  loanFeeRuleDo = loanProductService.getLoanFeeById(id);
		modelMap.addAttribute("feeDo", loanFeeRuleDo);
		return "loan/editLoanFee";
	}
	
	
	@RequestMapping("/fee/save")
	@ResponseBody
	public void addLoanFee(LoanFeeRuleDo loanFeeRuleDo,HttpServletResponse response){
		int i = 0;
		UserInfos userDetails = (UserInfos) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(loanFeeRuleDo.getId() !=null && !"".equals(loanFeeRuleDo.getId())){
			loanFeeRuleDo.setUpdateUserId(Long.parseLong(userDetails.getUserId()+""));
			 i = loanProductService.updateLoanFeeDo(loanFeeRuleDo);
		}else{
			loanFeeRuleDo.setUpdateUserId(Long.parseLong(userDetails.getUserId()+""));
			loanFeeRuleDo.setCreateUserId(Long.parseLong(userDetails.getUserId()+""));
			 i = loanProductService.addLoanFeeDo(loanFeeRuleDo);
		}
		if(i <= 0){
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
		outPrint(response, JSONSerializer.toJSON(map_success));
	}
	
	@RequestMapping("/fee/delete")
	@ResponseBody
	public void deleteLoanFee(String ids,HttpServletResponse response){
		if(StringUtils.isNotBlank(ids)){
			int i = loanProductService.deleteLoanFeeDo(ids);
			if(i <= 0){
				outPrint(response, JSONSerializer.toJSON(map_failure));
			}
			outPrint(response, JSONSerializer.toJSON(map_success));
		}else{
			outPrint(response, JSONSerializer.toJSON(map_failure));
		}
	}
	
/************************************************产品方案规则****************end************************************************/	
/*	*/
	
	
/*	

	
	*//************************************************产品前提****************************************************************//*
	
	
	@RequestMapping("/prov/delete")
	@ResponseBody
	public ModelAndView deleteLoanProvided(String ids){
		if(StringUtils.isNotBlank(ids)){
			int i = loanProvidedService.deleteLoanProvidedDo(ids);
			if(i>0){
				return this.ajaxSuccessAndRefresh("操作成功");
			}else{
				return this.ajaxFailure("操作失败");
			}
		}else{
			return this.ajaxFailure("请选择需要操作的数据");
		}
	}
	
	*//************************************************产品方案****************************************************************//*
	
	@RequestMapping("/sett/list/{prodId}")
	public String loanSettList(@PathVariable("prodId") String prodId,Model model){
		PageDo<LoanSettSchemeDo> page = getPage();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("prodId", Long.parseLong(prodId));
		page = loanProductService.getLoanSettPage(param, page);
		model.addAttribute("pageDo", page);
		return "loan/loanSettList";
	}
	
	
	
	@RequestMapping("/sett/delete")
	@ResponseBody
	public ModelAndView deleteLoanSett(String ids){
		if(StringUtils.isNotBlank(ids)){
			int i = loanProductService.deleteLoanSettDo(ids);
			if(i>0){
				return this.ajaxSuccessAndRefresh("操作成功");
			}else{
				return this.ajaxFailure("操作失败");
			}
		}else{
			return this.ajaxFailure("请选择需要操作的数据");
		}
	}
	
	*//************************************************产品方案规则****************************************************************//*
	@RequestMapping("/fee/list/{settId}")
	public String loanFeeList(@PathVariable("settId") String settId,Model model){
		PageDo<LoanFeeRuleDo> page = getPage();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("settId", Long.parseLong(settId));
		page = loanProductService.getLoanFeePage(param, page);
		model.addAttribute("pageDo", page);
		return "loan/loanFeeList";
	}
	
	
	
	@RequestMapping("/fee/delete")
	@ResponseBody
	public ModelAndView deleteLoanFee(String ids){
		if(StringUtils.isNotBlank(ids)){
			int i = loanProductService.deleteLoanFeeDo(ids);
			if(i>0){
				return this.ajaxSuccessAndRefresh("操作成功");
			}else{
				return this.ajaxFailure("操作失败");
			}
		}else{
			return this.ajaxFailure("请选择需要操作的数据");
		}
	}*/
	
	
}
