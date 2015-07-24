package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.sp2p.service.FundrecordService;

/**
 * 资金记录  Action
 * @author C_J
 *
 */
public class FundrecordAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FundrecordAction.class);
	
	private FundrecordService fundrecordService;
	
	/**
	 * 查询初始化
	 * @return
	 */
	public String querybugetInit(){
		int buget =Convert.strToInt(request("buget"),1);
		request().setAttribute("buget", buget);
		return SUCCESS;
	}
	
	/**
	 * 查询收入/支出明细 
	 * @return
	 */
   public String  querybugetList(){
	   int buget =Convert.strToInt(request("buget"),1);
	   String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("userName")), "");
	   try {
		fundrecordService.queryfundrecord_buget(pageBean, userName);
		//得到所有操作金额
		Map<String,String>  map = fundrecordService.queryAmountSum(buget, userName);
		request().setAttribute("hasMap", map);

		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
	   return SUCCESS;
   }

	public FundrecordService getFundrecordService() {
		return fundrecordService;
	}

	public void setFundrecordService(FundrecordService fundrecordService) {
		this.fundrecordService = fundrecordService;
	}
	
   
   
}
