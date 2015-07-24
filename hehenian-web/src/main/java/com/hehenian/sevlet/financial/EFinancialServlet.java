/**  
 * @Project: hehenian-web Maven Webapp
 * @Package com.hehenian.sevlet.financial
 * @Title: EFinancialServlet.java
 * @Description: 用于彩生活用户在购买定期理财，为线下打印纸质凭证提供数据
 *
 * @author: zhanbmf
 * @date 2015-3-17 下午3:04:13
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.sevlet.financial;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.trade.IInvestService;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.activity.dataobject.ActivityConfig;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.web.common.util.ServletUtils;

/**
 * 
 * @author zhanbmf
 *
 */
public class EFinancialServlet extends HttpServlet {

	private static final long serialVersionUID = -2538133985634922067L;
	private static IInvestService investService;
	private static IPersonService personService;
	private static ActivityConfig activityConfig;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        if(investService == null || personService == null || activityConfig == null) {
        	 ServletContext servletContext = config.getServletContext();
             WebApplicationContext webApplicationContext = WebApplicationContextUtils
                     .getWebApplicationContext(servletContext);
             AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
             investService = (IInvestService) autowireCapableBeanFactory.getBean("investService");
             personService = (IPersonService) autowireCapableBeanFactory.getBean("personService");
             activityConfig = (ActivityConfig) autowireCapableBeanFactory.getBean("activityConfig");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     *  返回请求结果
     *  code	结果返回码 -1异常 | 0 token无效| 1成功
     *  token	可检验数据是否被窜改
     *  data.orderNo	订单号
     *  data.transDate	购买时间
     *  data.transAmount	购买金额
     *  data.term	期数
     *  data.yearRate	年化收益率
     *  data.trueName	姓名
     *  data.idCard	身份证号
     *  data.mobile	手机号
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setCharacterEncoding("utf-8");
        String appKey = req.getParameter("appKey");
        String token = req.getParameter("token");
        int queryType = NumberUtils.toInt(req.getParameter("queryType"), -1);
        String queryStr = req.getParameter("queryStr");
        String beginDateStr = ObjectUtils.toString(req.getParameter("beginDate"));
        String endDateStr = ObjectUtils.toString(req.getParameter("endDate"));
        Date beginDate = DateUtil.strToYYMMDDDate(beginDateStr);
        Date endDate = DateUtil.strToYYMMDDDate(endDateStr);
        
        //校验token
        String colorKey = activityConfig.getColorKey();
        String appSecret = activityConfig.getColorSignSecret();
        
        Map result = new HashMap();
        result.put("code", -1);
        result.put("token", DigestUtils.md5Hex(appSecret + "-1" + "null"));
        result.put("data", null);
        //参数校验
        if(StringUtils.isBlank(appKey) || !appKey.equals(colorKey)  || StringUtils.isBlank(token) || 
            queryType < 0 || queryType > 2 || StringUtils.isBlank(queryStr) || beginDate == null || 
            endDate == null || beginDate.compareTo(endDate) > 0) {
        	ServletUtils.write(JSONObject.fromObject(result).toString(), ServletUtils.RESP_CONTTYPE_JSON, resp);
        	return;
        }
        
        //会话标识校验(参数按照字典顺序排序，这里省去排序)
        if(!token.equals(DigestUtils.md5Hex(appSecret + beginDateStr + endDateStr + queryStr + queryType))) {
        	result.put("code", 0);
        	result.put("token", DigestUtils.md5Hex(appSecret + "0" + "null"));
        	result.put("data", null);
        	
        	ServletUtils.write(JSONObject.fromObject(result).toString(), ServletUtils.RESP_CONTTYPE_JSON, resp);
        	return;
        }
        
        PersonDo pd = null;
        List<Map<String, Object>> list = null;
        //0身份证号|1手机号|2订单号  身份证号or手机号转换成会员ID
        if(queryType == 0) {
        	pd= personService.getByUserIdNo(queryStr);
        }else if (queryType == 1) {
        	pd = personService.getByUserCellPhone(queryStr);
        }else{
        	list = investService.getInvestDataForCSH(null, NumberUtils.toInt(queryStr, -1), beginDate, endDate);
        	if(list != null && list.size() > 0) {
        		pd = personService.getByUserId(((Integer)list.get(0).remove("userId")).longValue());
        	}
        }
        
        if(pd == null) {
        	ServletUtils.write(JSONObject.fromObject(result).toString(), ServletUtils.RESP_CONTTYPE_JSON, resp);
        	return;
        }
        
        if(list == null) {
        	list = investService.getInvestDataForCSH(pd.getUserId(), NumberUtils.toInt(queryStr, -1), beginDate, endDate);
        }
        
        if(list != null) {
        	for(int i=0; i<list.size(); i++) {
        		Map<String, Object> map = list.get(i);
        		map.put("trueName", pd.getRealName());
        		map.put("idCard", pd.getIdNo());
        		map.put("mobile", pd.getCellPhone());
        		map.put("transDate", DateFormatUtils.format((Date)map.get("transDate"), "yyyy-MM-dd HH:mm"));
        		if(map.get("cname") == null) {
        			map.put("cname", "null");
        		}
        		if(map.get("groupName") == null) {
        			map.put("groupName", "null");
        		}
        	}
        }
        
        result.put("code", 1);
        result.put("token", DigestUtils.md5Hex(appSecret + "1" + JSONArray.fromObject(list).toString()));
        result.put("data", list);
        
        ServletUtils.write(JSONObject.fromObject(result).toString(), ServletUtils.RESP_CONTTYPE_JSON, resp);
        return;
    }
    
    public  static void main(String[] args) {
    	System.out.println(DigestUtils.md5Hex("PropertyActivity" + "2015-01-20" + "2015-04-19" + "13480864900" + "1"));
    	System.out.println(DigestUtils.md5Hex("%21%40%23JSD" + "2015-03-16" + "2015-04-19" + "8051" + "2"));
    	System.out.println(DigestUtils.md5Hex("%21%40%23JSD" + "2015-03-16" + "2015-04-19" + "127407" + "2"));
    }
}