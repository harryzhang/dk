package com.sp2p.action.front;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.service.AwardMoneyService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.CostManagerService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.task.JobTaskService;
import com.shove.Convert;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;

public class BeVipAction extends BaseFrontAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(BeVipAction.class);
	private BeVipService beVipService;;
	private CostManagerService costManagerService;
	private RecommendUserService recommendUserService;
	private AwardMoneyService awardMoneyService;
	@SuppressWarnings("unused")
	private IDCardValidateService iDCardValidateService;
	// add by houli vip扣费处理
	private JobTaskService jobTaskService;

	//

	public void setIDCardValidateService(IDCardValidateService cardValidateService) {
		iDCardValidateService = cardValidateService;
	}

	public void setCostManagerService(CostManagerService costManagerService) {
		this.costManagerService = costManagerService;
	}

	public void setAwardMoneyService(AwardMoneyService awardMoneyService) {
		this.awardMoneyService = awardMoneyService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	/**
	 * 即时验证身份证号码
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public String isIDNO() throws IOException, NumberFormatException, ParseException {
		JSONObject obj = new JSONObject();
		String IDNO = SqlInfusion.FilteSqlInfusion(paramMap.get("idNo"));
		if (StringUtils.isBlank(IDNO)) {
			obj.put("putStr", "0");
			JSONUtils.printObject(obj);
			return null;
		}
		long len = IDNO.length();
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(IDNO.substring(12, 15));
		} else if(len == 18) {
			sortCode = Integer.parseInt(IDNO.substring(14, 17));
		}else
			return null;
		if (sortCode % 2 == 0) {
			MAN_SEX = 2;// 女性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 1;// 男性身份证
		} else {
			obj.put("putStr", "1");// 身份证不合法
			JSONUtils.printObject(obj);
			return null;
		}
		String iDresutl = "";
		iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, IDNO);
		if (iDresutl != "") {
			obj.put("putStr", "1");// 身份证不合法
			JSONUtils.printObject(obj);
			return null;
		}
		return null;
	}

	/**
	 * 查询用户的参数申请vip时候的参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bevip() throws Exception {
		Map<String, String> userMap = null;
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		userMap = beVipService.queryVipParamList(user.getId());
		request().setAttribute("userMap", userMap);
		return SUCCESS;
	}

	/**
	 * 更新用户的vip状态
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateUserVip() throws Exception {
		Map<String, Object> platformCostMap = this.getPlatformCost();
		JSONObject obj = new JSONObject();
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId"));
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code").toString().trim());

		String servicePersonId = SqlInfusion.FilteSqlInfusion(paramMap.get("kefu"));// 客服跟踪人
		Long server = Convert.strToLong(servicePersonId, -1);
		if (StringUtils.isBlank(servicePersonId)) {
			obj.put("msg", "请选择客服");
			JSONUtils.printObject(obj);
			return null;
		}

		if (code == null || !_code.equals(code)) {
			obj.put("msg", "验证码错误");
			JSONUtils.printObject(obj);
			return null;
		}
        AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		Long userId = user.getId();
		double vipFee = Convert.strToDouble(platformCostMap.get(IAmountConstants.VIP_FEE_RATE) + "", 0);
		double money = Convert.strToDouble(platformCostMap.get(IAmountConstants.ORDINARY_FRIENDS_FEE) + "", 0);
		Map<String, String> map = beVipService.beVip(userId, server, vipFee, money);
		int ret = Convert.strToInt(map.get("ret") + "", -1);
		if (ret < 1) {
			obj.put("msg", map.get("ret_desc") + "");
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	public JobTaskService getJobTaskService() {
		return jobTaskService;
	}

	public void setJobTaskService(JobTaskService jobTaskService) {
		this.jobTaskService = jobTaskService;
	}

}
