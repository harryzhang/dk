/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.loan.action
 * @Title: LoanDetailAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午10:00:04
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.manager.actions.loan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanCMobileService;
import com.hehenian.biz.common.loan.dataobject.LoanCMobileDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.actions.common.Maps;
import com.hehenian.manager.actions.common.PageDoUtil;
import com.hehenian.manager.commons.NewPagination;

/**
 * @Description 输入小区事业部负责人
 * @author huangzl QQ: 272950754
 * @date 2015年4月20日 下午2:32:14
 * @Project hehenian-lend-web
 * @Package com.hehenian.web.view.loan.action
 * @File CreditAction.java
 */
@Controller
@RequestMapping(value="/community")
public class CommunityController extends BaseAction{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ILoanCMobileService loanCMobileService;
	
	Map<Object, Object> map_success = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_SUCCESS);
	Map<Object, Object> map_failure  = Maps.mapByAarray(EXECUTE_STATUS,EXECUTE_FAILURE);
	

	
	/**
	 * 编辑事业部人员
	 * @return
	 */
	@RequestMapping("/editCommunity")
	public String editCommunity(String id, ModelMap modelMap,HttpServletResponse response) {
		logger.info("----editcommunity----");
		if(null != id && !"".equals(id.trim())){
			Integer searchId = Integer.valueOf(id);
			LoanCMobileDo loanCmobile = loanCMobileService.getById(searchId);
			if(null != loanCmobile ){
				modelMap.addAttribute("loanCmobile", loanCmobile);
			}
		}
		return "/loan/editCommunity";
	}
	
	/**
	 * 事业部下拉框
	 * @param response
	 */
	@RequestMapping("/allCommuntiy")
	public void settList( HttpServletResponse response){
		List<LoanCMobileDo> cmList = loanCMobileService.getAllBusinessDept();
		outPrint(response, JSONArray.fromObject(cmList));
	}
	
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping("/toListCommunity")
	public String toListCommunity() {
		logger.info("----toListcommunity----");
		return "/loan/listCommunity";
	}
	
	/**
	 * 房价清单列表
	 * @return
	 */
	@RequestMapping("/listCommunity")
	public void listCommunity(NewPagination<LoanCMobileDo> pagination,HttpServletResponse response) {
		
//		PageDo<LoanCMobileDo> page = new PageDo<LoanCMobileDo>();
//		page.setPageNum(1);
//		page.setPageSize(10);
		
		logger.info("----listcommunity----");
		PageDo<LoanCMobileDo> page = PageDoUtil.getPage(pagination);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String cname = getString("searchStr");
		if(null != cname){
			paramMap.put("cname", cname);
		}
		String cusername = getString("searchCuserName");
		if(null != cname){
			paramMap.put("cusername", cusername);
		}
		paramMap.put("status", "T");//代表状态		
		page = loanCMobileService.getLoanCMobilePage(paramMap, page);
		
		
//		NewPagination<LoanCMobileDo> pagination = new NewPagination<LoanCMobileDo>();
//		pagination.setPage(Integer.parseInt(page.getPageNum()+""));
//		pagination.setRows(Integer.parseInt(page.getPageSize()+""));
//		pagination.setDatas(page.getPage());
//		int total = Integer.parseInt(page.getTotalNum()+"");
//		pagination.setTotal(total);
		pagination = PageDoUtil.getPageValue(pagination, page);
		outPrint(response, JSONObject.fromObject(pagination));
		
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/deleteCommunity")
	@ResponseBody
	public void deleteCommunity(String id ,HttpServletResponse response) {
		
		logger.info("----deleteCommunity----");
		int count =  0 ;
		if(null != id){
			Integer searchId = Integer.valueOf(id);
			count = loanCMobileService.deleteLoanCMobileById(searchId);
		}
		
		if(count <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
		}else{
			outPrint(response, JSONSerializer.toJSON(map_success));
		}
	}
	
	
	/**
	 * 保存
	 * 
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/saveCommunity")
	@ResponseBody
	public void saveCommunity(LoanCMobileDo community,HttpServletResponse response) {
		
		logger.info("----savecommunity------");
		int i = 0 ;
		if(community.getId() !=null &&  community.getId() >0 ){
			i = loanCMobileService.updateLoanCMoblie(community);
		}else{
			i = loanCMobileService.addLoanCMobile(community);
		}
		if(i <= 0){
			 outPrint(response, JSONSerializer.toJSON(map_failure));
			 return;
		}
        outPrint(response, JSONSerializer.toJSON(map_success));
        
		logger.info("----end savecommunity--------");
	}
}
