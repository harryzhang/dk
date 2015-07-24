package com.sp2p.action.front;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.hehenian.biz.common.report.IColorReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ColourLifeAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(ColourLifeAction.class);
    @Autowired
    private IColorReportService colorReportService;
    static final  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public String index(){
        //总的
        request().setAttribute("tz", colorReportService.queryInvest().get("jer"));
        request().setAttribute("fk", colorReportService.queryFK().get("jer"));
        request().setAttribute("zc", colorReportService.queryUser().get("rs"));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE,-1);
        String date1 = dateFormat.format(calendar1.getTime());
        //昨日
        request().setAttribute("tz1", colorReportService.queryInvestByDate(date1).get("jer"));
        request().setAttribute("fk1", colorReportService.queryFKByDate(date1).get("jer"));
        request().setAttribute("zc1", colorReportService.queryUserByDate(date1).get("rs"));
        //今日
        String date2 = dateFormat.format(new Date());
        request().setAttribute("tz2", colorReportService.queryInvestByDate(date2).get("jer"));
        request().setAttribute("fk2", colorReportService.queryFKByDate(date2).get("jer"));
        request().setAttribute("zc2", colorReportService.queryUserByDate(date2).get("rs"));
        return SUCCESS;
    }

}
















