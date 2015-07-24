package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.AutomaticBidService;

/**
 * 自动投标
 * @author zhuchao
 *
 */

@SuppressWarnings("unchecked")
public class AutomaticBidAction extends BasePageAction{
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(AutomaticBidAction.class);
	
	private AutomaticBidService automaticBidService;

	/**
	 * 自动投标页面初始化
	 * @return
	 */
	public String automaticBidInit() {		
		return SUCCESS;
	}
	
	/**
	 * 自动投标列表展示
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String automaticBidList(){
		String username = SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
		String bidSetTime = SqlInfusion.FilteSqlInfusion(paramMap.get("bidSetTime"));
		try {
			automaticBidService.automaticBidList(pageBean, username, bidSetTime);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}
	
	/**
	 * 显示关闭用户自动投标界面
	 * @param id
	 */
	public String displayCloseBid() {
		int id=Convert.strToInt(request().getParameter("id"), -1);
		try {
			paramMap = automaticBidService.queryAutomaticBidById(id);;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 关闭用户自动投标
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException
	 */
	public String closeAutomaticBid(){
		Admin admin = (Admin)session().getAttribute(IConstants.SESSION_ADMIN);
		String ids = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("id")), null);
		if(ids!=null){
			try {
				long result =automaticBidService.closeAutomaticBid(admin.getUserName(), admin.getLastIP(), ids);
				if(result>0){
					JSONUtils.printStr("1");
				}else{
					JSONUtils.printStr("2");
				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public void setAutomaticBidService(AutomaticBidService automaticBidService) {
		this.automaticBidService = automaticBidService;
	}
}
