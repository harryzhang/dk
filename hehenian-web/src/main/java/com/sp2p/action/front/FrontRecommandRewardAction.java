package com.sp2p.action.front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.invite.IRecommandRateService;
import com.hehenian.biz.common.invite.IRecommandRewardService;
import com.hehenian.biz.common.invite.InviteFriendService;
import com.hehenian.biz.common.invite.dataobject.RecommondRewardDo;
import com.hehenian.web.common.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.util.JsonDateValueProcessor;

public class FrontRecommandRewardAction extends BaseFrontAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontRecommandRewardAction.class);
	@Autowired
	private IRecommandRewardService recommandRewardService;
	@Autowired
	private IRecommandRateService recommandRateService;
	@Autowired
	private InviteFriendService inviteFriendService;
	
	public String queryReferRewardList(){
		
        
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Map<String, Object> map = new HashMap<String, Object>();
		/*
		Integer pageNow = Integer.parseInt(request().getParameter("pageNow"));
		Integer pageSize = Integer.parseInt(request().getParameter("pageSize"));
		if(pageNow==null || pageNow.toString().length()==0){
			
        }else {
            map.put("pageNow", pageNow);
        }
        if(pageSize==null || pageSize.toString().length()==0){
        	
        }else {
            map.put("pageSize", pageSize);
        }
        if(pageNow>1){
            map.put("pageNow", (pageNow-1) * pageSize);
        }else{
            map.put("pageNow", 0);
        }
        */
		// 获取用户的信息
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        
        Map<String, Date> dateMap =new HashMap<String, Date>();
        String time = request().getParameter("time");
        Date date;
        try {
        if(time==null||time.equals("")){
        	date = new Date();
        }else{
			date = df.parse(time);
        }
        dateMap = this.getFirstday_Lastday_Month(date);
        } catch (ParseException e) {
			map.put("returnCode",-1);
			map.put("message", "日期解析异常");
			ServletUtils.writeJson(JSONObject.fromObject(map).toString());
			return null;
		}
        Date startDate = dateMap.get("first");
        Date endDate = dateMap.get("last");
        Map<String, Object> mapParms = new HashMap<String, Object>();
        mapParms.put("startDate", startDate);
        mapParms.put("endDate",endDate);
        mapParms.put("userId",user.getId().toString());
        //mapParms.put("pageNow", map.get("pageNow"));
        //mapParms.put("pageSize", pageSize);
		List<RecommondRewardDo> rewardList = recommandRewardService.listRecommandReward(mapParms);
		map.put("totalCount", rewardList.size());
		if(rewardList.size()==0){
			map.put("sumInvestNum", 0);
			map.put("sumRewardAmount", 0.00);
			map.put("sumTradeAmount", 0.00);
		}else{
			RecommondRewardDo rrd = recommandRewardService.getSumRewardAmount(user.getId().toString(), startDate, endDate);
			map.put("sumRewardAmount", rrd.getReward_amount());
			map.put("sumTradeAmount", rrd.getTrade_amount());
			double num = recommandRewardService.getRewardNum(user.getId().toString(), startDate, endDate);
			map.put("sumInvestNum", num);
		}
		//好友注册数
		//TODO
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("userId", user.getId().toString());
		//TODO
		long regnum = inviteFriendService.countInviteFriend(mapParam);
		map.put("sumRegNum",regnum);
		map.put("returnCode",0);
		map.put("data", rewardList);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		
		ServletUtils.writeJson(JSONObject.fromObject(map,jsonConfig).toString());
		return null;
	}

	 /**
     * 某一个月第一天和最后一天
     * @param date
     * @return
     */
    private  Map<String, Date> getFirstday_Lastday_Month(Date date)throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        
        //第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(date);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, Date> map = new HashMap<String, Date>();
        map.put("first", df.parse(day_first));
        map.put("last", df.parse(day_last));
        return map;
    }
}
