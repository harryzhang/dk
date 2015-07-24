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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.loan.ILoanApplyService;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.actions.common.Maps;
import com.hehenian.manager.commons.NewPagination;

/**
 * @Description 房价输入
 * @author huangzl QQ: 272950754
 * @date 2015年4月20日 下午2:32:14
 * @Project hehenian-lend-web
 * @Package com.hehenian.web.view.loan.action
 * @File CreditAction.java
 */
@Controller
@RequestMapping(value = "/housePrice")
public class HousePriceController extends BaseAction {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ILoanApplyService loanApplyService;

	Map<Object, Object> map_success = Maps.mapByAarray(EXECUTE_STATUS, EXECUTE_SUCCESS);
	Map<Object, Object> map_failure = Maps.mapByAarray(EXECUTE_STATUS, EXECUTE_FAILURE);

	/**
	 * 房价清单初始化
	 * 
	 * @return
	 */
	@RequestMapping("/toListHousePrice")
	public String toListHousePrice() {
		logger.info("----toListHousePrice----");
		return "/loan/listHousePrice";
	}

	/**
	 * 房价清单列表
	 * 
	 * @return
	 */
	@RequestMapping("/listHousePrice")
	public void listHousePrice(HttpServletResponse response) {
		// PageDo<Map<String,Object>> page = PageDoUtil.getPage(pagination);
		logger.info("----listHousePrice----");

		String cname = getString("searchStr");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (null != cname && !"".equals(cname.trim())) {
			paramMap.put("cname", cname);
		}
		paramMap.put("type", "C");// c小区， s代表事业部
		List<Map<String, Object>> cnameList = loanApplyService.getAreaList(paramMap);

		NewPagination<Map<String, Object>> pagination = new NewPagination<Map<String, Object>>();
		pagination.setDatas(cnameList);
		pagination.setRows(cnameList == null ? 0 : cnameList.size());
		pagination.setPage(1);
		pagination.setPageSize(2000);
		outPrint(response, JSONObject.fromObject(pagination));
	}

	/**
	 * 房价编辑
	 * 
	 * @return
	 */
	@RequestMapping("/editHousePrice")
	public String editHousePrice(String id, ModelMap modelMap, HttpServletResponse response) {
		logger.info("----editHousePrice----");
		if (!id.equals("save")) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			paramMap.put("type", "C");// c小区， s代表事业部
			List<Map<String, Object>> cnameList = loanApplyService.getAreaList(paramMap);
			if (null != cnameList && cnameList.size() > 0) {
				modelMap.addAttribute("areaObj", cnameList.get(0));
			}
		}
		return "/loan/editHousePrice";
	}

	/**
	 * 房价保存更新
	 * 
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/saveHousePrice")
	@ResponseBody
	public void saveHousePrice(String id, long cid, String cname, double housePrice, HttpServletResponse response) {

		logger.info("----saveHousePrice------");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		int i = 0;
		if (id.trim().equals("")) {
			parameterMap.put("cid", cid);
			parameterMap.put("cname", cname);
			parameterMap.put("type", "C");
			parameterMap.put("housePrice", housePrice);
			i = loanApplyService.saveHousePrice(parameterMap);
		} else {
			parameterMap.put("cid", cid);
			parameterMap.put("housePrice", housePrice);
			i = loanApplyService.updateHousePrice(parameterMap);
		}

		if (i <= 0) {
			outPrint(response, JSONSerializer.toJSON(map_failure));
			return;
		}
		outPrint(response, JSONSerializer.toJSON(map_success));

		logger.info("----end saveHousePrice--------");
	}
}
