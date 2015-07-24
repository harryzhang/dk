package com.sp2p.action.admin;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ExcelUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.ScoreService;

public class ScoreAction extends BasePageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ScoreService scoreService;
	
	public ScoreService getScoreService() {
		return scoreService;
	}
	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}
	public String queryScoreListInit(){
		return SUCCESS;
	}
	public String queryScoreList() throws Exception{
		String attitude = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("attitude")), "");
		int levels = Convert.strToInt(paramMap.get("levels"), 0);
		String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("username")), "");
		try {
			scoreService.queryScorePage(pageBean,attitude,levels ,username,null);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
		}
		
		return SUCCESS;
	}
	
	public String queryScoreByID() throws Exception{
		Long id = Convert.strToLong(request("id"),-1);
		try {
			scoreService.queryScoreById(pageBean,id);
			Map<String, Object> map = (Map<String, Object>) pageBean.getPage().get(0);
			request().setAttribute("paramMap", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String exporScoreList() throws Exception{
		String attitude = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("attitude")), "");
		int levels = Convert.strToInt(request("levels"), 0);
		String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("username")), "");
		String id = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("id")), "");
		attitude = URLDecoder.decode(attitude, "UTF-8");
		scoreService.queryScorePage(pageBean,attitude,levels ,username,id);
		try
        {
            if (pageBean.getPage() == null)
            {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX)
            {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }
            String[] a = new String[]{"id", "username", "realName","sex","age","source","levels",
            		"experience","income","investment","attitude","aim",
            		"total","questionScore","internet","intnetScore","degree","degreeScore","predilection",
            		"prediScore","refferee","writetime","refferee"};
            String[] b = new String[] {"问卷编号", "用户名", "姓名", "性别", "年龄",
                    "用户来源","风险等级", "投资经验", "年可用投资金额百分比", "应还金额",
                    "风险偏好", "投资目标", "个人投资情况得分", "个人投资情况问卷总分", "互联网金融工具使用情况",
                    "使用情况问卷总分", "p2p网贷平台的认知程度", "认知程度问卷总分", "风险偏好得分",
                    "风险偏好问卷总分", "推荐人", "填写时间", "性别"};
            HSSFWorkbook wb =
                ExcelUtils.exportExcel("问卷调查结果", pageBean.getPage(),b, a);
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin)session().getAttribute(IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("v_t_score_list",
                admin.getUserName(),
                IConstants.EXCEL,
                admin.getLastIP(),
                0,
                "导出问卷调查结果",
                2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
	}
}
