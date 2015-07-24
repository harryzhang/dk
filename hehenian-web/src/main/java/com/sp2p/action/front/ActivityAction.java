package com.sp2p.action.front;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.web.common.util.HttpClientUtils;
import com.hehenian.web.common.util.ServletUtils;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.web.util.JSONUtils;
import com.sp2p.service.ActivityService;

public class ActivityAction extends BaseFrontAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(ActivityAction.class);
	private ActivityService activityService;
	private static final DateFormat DATE_FORMAT=new SimpleDateFormat("yy-M-d");
	private int activityId;
	static Calendar calendar1=Calendar.getInstance();
	static Calendar calendar2=Calendar.getInstance();
	static{
		calendar1.set(2014, 8, 1, 0, 0, 0);
		calendar2.set(2014, 8, 6, 0, 0, 0);
	}
    public String sjmIndex(){
        session().setAttribute("partnerUserId",request("wxToken"));
        session().setAttribute("partnerId",101);
        return SUCCESS;
    }
	/**
	 * 抽奖首页
	 * @return
	 */
	public String luckDrawIndex() {
		
		int userGroup = 0;
		//中奖动态
		//-------
		List<Map<String, Object>> records = activityService.queryActivityRecore(activityId);
		request().setAttribute("records", records);
		int recordCount = activityService.queryActivityRecoreCount(activityId);
		request().setAttribute("recordCount", recordCount);
		//奖项设置
		//------
		
		//查询用户抽奖次数
		//----
        AccountUserDo user = (AccountUserDo)session("user");
		if (user!=null) {
			userGroup = user.getUserGroup();
			activityService.computeScore(user.getId(), activityId);
			Map<String, String> map = activityService.queryUserInfo(user.getId(), activityId);
			request().setAttribute("userinfo", map);
			
			//签到记录
			List<Map<String, Object>> signRecords = activityService.querySignRecord(activityId, user.getId());
			request().setAttribute("signRecords", signRecords);
		}else{
			List<Map<String, Object>> signRecords = activityService.querySignRecord(activityId, 0);
			request().setAttribute("signRecords", signRecords);
		}
		//标的列表
		List<Map<String, Object>> borrowList = activityService.queryBorrowList(userGroup);
		request().setAttribute("borrowList", borrowList);
		
		request().setAttribute("now", DATE_FORMAT.format(new Date()));
		return SUCCESS;
	}
	/**
	 * 进行抽奖
	 * @return
	 */
	private static final Random RANDOM = new Random();
	public String luckDraw() throws IOException {
		Calendar calendar=Calendar.getInstance();
		JSONObject jsonObject = new JSONObject();
		if (calendar.after(calendar1)&&calendar.before(calendar2)) {
            AccountUserDo user = (AccountUserDo)session("user");
			
			//判断是否有剩余抽奖次数
			Map<String, String> userinfo = activityService.queryUserInfo(user.getId(), activityId);
			if (userinfo!=null&&Integer.parseInt(userinfo.get("score"))>Integer.parseInt(userinfo.get("hasDraw"))) {
				//抽奖 得到结果 减少抽奖次数
				Map<String, String> activity = activityService.queryActivity(activityId);
				List<Map<String, Object>> list = activityService.queryAwards(activityId);
				if (list!=null&&!list.isEmpty()) {
					int amount = Integer.parseInt(activity.get("amount"));
					int index=0;
					int defaultAwardId = 0;
					List<Map<String, Integer>> list2 = new ArrayList<Map<String,Integer>>();
					for (Map<String, Object> map : list) {
						if ((Integer)map.get("isDefault")==1) {
							defaultAwardId = (Integer)map.get("id"); 
						}
						double probability = (Double)map.get("probability");
						if (((BigDecimal)map.get("hasNum")).intValue() <(Integer)map.get("dayMaxNum")) {
							Map<String, Integer> map1 = new HashMap<String, Integer>();
							index += (int)(probability*amount);
							map1.put("index", index);
							map1.put("awardId", (Integer)map.get("id"));
							list2.add(map1);
						}
					}
					Collections.sort(list2, new Comparator<Map<String, Integer>>() {
						@Override
						public int compare(Map<String, Integer> o1,
								Map<String, Integer> o2) {
							return o1.get("index")-o2.get("index");
						}
					});
					int result = RANDOM.nextInt(amount);
					int awardId = 0;
					for (Map<String, Integer> map : list2) {
						if (result<map.get("index")) {
							awardId = map.get("awardId");
							break;
						}
					}
					if (awardId <= 0) {
						awardId = defaultAwardId;
					}
					activityService.saveRecord(user.getId(), awardId);
					activityService.updateHasDraw(user.getId(), 1, activityId);
					jsonObject.put("ret", 0);
					jsonObject.put("awardId", awardId);
				}else {
					//指定活动找不到奖项
					jsonObject.put("ret", 1);
				}
			}else{
				jsonObject.put("ret", 2);
				
			}
		}else{
			jsonObject.put("ret", 3);
		}
		JSONUtils.printStr(jsonObject.toString());
		return null;
	}
	/**
	 * 抽奖签到
	 * @return
	 * @throws IOException 
	 */
	public String sign() throws IOException {
        AccountUserDo user = (AccountUserDo)session("user");
		Calendar calendar=Calendar.getInstance();
		if (calendar.after(calendar1)&&calendar.before(calendar2)) {
			if (user.getUsrCustId()!=null&&user.getUsrCustId()>0) {
				boolean b = activityService.sign(user.getId(), activityId);
				if (b) {
					activityService.updateMoney(user.getId(), 0.88, activityId);
					JSONUtils.printStr("0");
				}else{
					JSONUtils.printStr("1");
				}
			}else{
				JSONUtils.printStr("2");
			}
		}else{
			JSONUtils.printStr("3");
		}
		
		
		return null;
	}
    private int draw( Map<String, String> activity,List<Map<String, Object>> awards,long userId,int defaultAwardId){
        int amount = Integer.parseInt(activity.get("amount"));
        int index=0;
        List<Map<String, Integer>> list2 = new ArrayList<Map<String,Integer>>();
        for (Map<String, Object> map : awards) {
            if ((Integer)map.get("isDefault")==1) {
                defaultAwardId = (Integer)map.get("id");
            }
            double probability = (Double)map.get("probability");
            if (((BigDecimal)map.get("hasNum")).intValue() <(Integer)map.get("dayMaxNum")) {
                Map<String, Integer> map1 = new HashMap<String, Integer>();
                index += (int)(probability*amount);
                map1.put("index", index);
                map1.put("awardId", (Integer)map.get("id"));
                list2.add(map1);
            }
        }
        Collections.sort(list2, new Comparator<Map<String, Integer>>() {
            @Override
            public int compare(Map<String, Integer> o1,
                    Map<String, Integer> o2) {
                return o1.get("index")-o2.get("index");
            }
        });
        int result = RANDOM.nextInt(amount);
        int awardId = 0;
        for (Map<String, Integer> map : list2) {
            if (result<map.get("index")) {
                awardId = map.get("awardId");
                break;
            }
        }
        if (awardId <= 0) {
            awardId = defaultAwardId;
        }
        activityService.saveRecord(userId, awardId);
        Map<String, String> award = activityService.getAward(awardId);
        if (award!=null){
            String money = award.get("money");
            try {
                activityService.updateMoney(userId,Double.parseDouble(money),activityId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return awardId;
    }
	/**
	 * 获取自己的中奖记录
	 * @return
	 */
	public String myActivityRecords() {
        AccountUserDo user = (AccountUserDo)session("user");
		List<Map<String, Object>> list = activityService.queryMyActivityRecords(activityId, user.getId());
		request().setAttribute("myRecords", list);
		return SUCCESS;
	}


    private static final int        sjmActivityId = 2;
    private static final DateFormat DATE_FORMAT1   = new SimpleDateFormat("yyyyMMdd");

    /**
     * 神经猫抽奖
     * @return
     */
    public String sjmDraw() {
        AccountUserDo user = (AccountUserDo) session("user");
        JSONObject jsonObject = new JSONObject();
        if (user != null) {
            int defaultAwardId = 0;
            Map<String, String> stringStringMap = activityService.queryDefaultAwardId(sjmActivityId);
            if (stringStringMap != null) {
                try {
                    defaultAwardId = Integer.parseInt(stringStringMap.get("id"));
                } catch (Exception e) {
                }
            }
            List<Map<String, Object>> maps = activityService
                    .queryMyActivityRecordsByDate(sjmActivityId, user.getId(), DATE_FORMAT1.format(new Date()));
            boolean flag = true;
            if (maps != null && !maps.isEmpty()) {
                for (Map<String, Object> map : maps) {
                    if (!map.get("awardId").equals(defaultAwardId)) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                //今天还没中奖，可以有机会中奖
                Map<String, String> activity = activityService.queryActivity(sjmActivityId);
                List<Map<String, Object>> list = activityService.queryAwards(sjmActivityId);
                if (list != null && !list.isEmpty()) {
                    int awardId = draw(activity, list, user.getId(), defaultAwardId);
                    jsonObject.put("ret", 0);
                    jsonObject.put("award", (int)Double.parseDouble(activityService.getAward(awardId).get("money")));
                } else {
                    //指定活动找不到奖项
                    jsonObject.put("ret", 1);
                    jsonObject.put("msg", "该活动没有设置奖项");
                }
            } else {
                activityService.saveRecord(user.getId(), defaultAwardId);
                jsonObject.put("ret", 0);
                jsonObject.put("award", (int)Double.parseDouble(activityService.getAward(defaultAwardId).get("money")));
            }

        }
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }
    public String userInfo(){
        AccountUserDo user = (AccountUserDo) session("user");
        JSONObject jsonObject = new JSONObject();
        if (user!=null){
            Map<String, String> map = activityService.queryUserInfo(user.getId(), activityId);
            jsonObject.put("userinfo",map);
        }
        ServletUtils.writeJson(jsonObject.toString());
        return  null;
    }
    public String activityRecores(){
        //中奖动态
        //-------
        List<Map<String, Object>> records = activityService.queryActivityRecore(activityId);
        int recordCount = activityService.queryActivityRecoreCount(activityId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("records",records);
        jsonObject.put("recordCount",recordCount);
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }
    /**
     * 获取自己的中奖记录
     * @return
     */
    private static final DateFormat DATE_FORMAT2=new SimpleDateFormat("yyyy-MM-dd");
    public String myActivityRecordList() {
        AccountUserDo user = (AccountUserDo)session("user");
        List<Map<String, Object>> list = activityService.queryMyActivityRecords(activityId, user.getId());
        if (list!=null&&!list.isEmpty()){
            for (Map<String, Object> stringObjectMap : list) {
                stringObjectMap.put("day",DATE_FORMAT2.format(stringObjectMap.get("cTime")));
                stringObjectMap.remove("cTime");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("myRecords", list);
        try {
            ;
            ServletUtils.writeJson(JsonUtil.toString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(jsonObject.toString());


        return null;
    }
    private String wenxinToken;
    private String appid = "wx5c583d80448a89a9";
    private String secret = "1da5272e384a73c53eeb6f56d3f8c9c6";
    public String weixinAuth(){

       /* String code = request().getParameter("code");
        if (StringUtils.isBlank(code)) {
            ServletUtils.write("授权失败");
            return null;
        }

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="
                + code + "&grant_type=authorization_code";
        IResult<?> result = HttpClientUtils.post(url, new HashMap<String, String>());
        System.out.println("result.getModel():"+result.getModel());
        JSONObject jsonObject = JSONObject.fromObject(result.getModel());
        String openid = jsonObject.getString("openid");
        System.out.println("openid:"+openid);
        wenxinToken=openid;*/
//        out.print("Code:" + code);
//        out.print("授权成功，微信返回的数据:" + result.getModel());
//        response.sendRedirect("http://www.hehenian.com/webapp/webapp-index.do");
        return SUCCESS;
    }

    public String getWenxinToken() {
        return wenxinToken;
    }

    public void setWenxinToken(String wenxinToken) {
        this.wenxinToken = wenxinToken;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

}
