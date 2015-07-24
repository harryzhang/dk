package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.ShowShipinService;
/**
 * 视频弹出框
 * @author Administrator
 *
 */
public class ShowShipinAction  extends BaseFrontAction{
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(ShowShipinAction.class);
	private ShowShipinService showShipinService;
	public void setShowShipinService(ShowShipinService showShipinService) {
		this.showShipinService = showShipinService;
	}
	
	/**
	 * 视频弹出框
	 * @return
	 */
	public String showshiping(){
		String dm = SqlInfusion.FilteSqlInfusion(request().getParameter("dm"));
		 request().setAttribute("tmid", dm);
	    return SUCCESS;
	}
	/**
	 * 更新视频认证审核
	 * @return
	 * @throws IOException 
	 */
	public String updateShiping() throws IOException{
		Long tmid = Convert.strToLong(paramMap.get("tmid"), -1L);//资料认证主表的id
		if(tmid==-1L){
			JSONUtils.printStr("1");
			return null;
		}
		Integer ccc = Convert.strToInt(paramMap.get("ccc"), -1);
		if(ccc==-1){
			JSONUtils.printStr("2");
			return null;
		}
		if(ccc!=1){
			JSONUtils.printStr("3");
			return null;
		}
		Long tmtype = 13L;//视频
		Long resutl = -1L;
		try {
			resutl =  showShipinService.updateShiping(tmid, tmtype);
			if(resutl>0){
				JSONUtils.printStr("4");
				return null;
			}else{
				JSONUtils.printStr("5");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
