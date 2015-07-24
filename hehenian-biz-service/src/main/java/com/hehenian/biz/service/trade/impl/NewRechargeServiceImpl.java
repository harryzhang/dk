package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.contant.FundConstant;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.INewRechargeService;
import com.hehenian.biz.common.trade.dataobject.FundrecordDo;
import com.hehenian.biz.common.trade.dataobject.RechargeDo;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityOrderComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.component.trade.IRechargeComponent;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import com.hehenian.biz.facade.colorlife.IColorOrderFacade;
import com.ibm.icu.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuwtmf
 * Date: 2014/9/23
 * Time: 10:47
 */
@Service("newRechargeService")
public class NewRechargeServiceImpl implements INewRechargeService {
    private static final Logger LOGGER = Logger.getLogger(NewRechargeServiceImpl.class);
    @Autowired
    private    IRechargeComponent      rechargeComponent;
    @Autowired
    private    IUserComponent          userComponent;
    @Autowired
    private    IAccountManagerService  accountManagerService;
    @Autowired
    private    IFundrecordComponent    fundrecordComponent;
    @Autowired
    private    IInvestComponent        investComponent;
    @Autowired IActivityOrderComponent activityOrderComponent;
    @Autowired
    private    IColorOrderFacade       colorOrderFacade;
    @Autowired
    private    IActivityOrderService   activityOrderService;

    @Override public IResult<?> addRecharge(RechargeDo rechargeDo) {
        int i = rechargeComponent.addRecharge(rechargeDo);
        if (i > 0) {
            IResult<String> result = new ResultSupport<String>();
            InParameter inParameter = new InParameter();
            inParameter.getParams().put("userId", rechargeDo.getUserId() + "");
            inParameter.setOrdId(rechargeDo.getId() + "");
            inParameter.setRetUrl("addRechargeCallback1.do");
            inParameter.setBgRetUrl("addRechargeCallback1.do");
            AccountUserDo accountUserDo = userComponent.getById(rechargeDo
                    .getUserId());
            inParameter.getParams().put("usrCustId",
                    accountUserDo.getUsrCustId() + "");
            inParameter.getParams().put("transAmt",
                    new DecimalFormat("#0.00").format(rechargeDo.getRechargeMoney()));
            inParameter.getParams().put("DcFlag", "D");
            inParameter.getParams().put("OrdDate", DateUtil.dateToYMD(new Date()));
            inParameter.getParams().put("OpenBankId", rechargeDo.getBankName());
            inParameter.getParams().put("gateBusiId", rechargeDo.getGateBusiId());
            inParameter.getParams().put("MerPriv", rechargeDo.getInfo());
            try {
                OutParameter outParameter = accountManagerService.netSave(inParameter,
                        AccountType.CHINAPNR);
                if (outParameter.isSuccess()) {
                    result.setSuccess(true);
                    result.setModel((String) outParameter.getParams().get("htmlText"));
                } else {
                    result.setSuccess(false);
                    result.setErrorMessage(outParameter.getRespDesc());
                }
            } catch (BusinessException e){
                result.setSuccess(false);
                result.setErrorMessage(e.getMessage());
                LOGGER.error(e.getMessage(), e);
            } catch (Exception e) {
                result.setSuccess(false);
                result.setErrorMessage("操作失败,请稍后再试!");
                LOGGER.error(e.getMessage(), e);
            }

            return result;
        }
        return null;
    }

    @Override public IResult<?> addRechargeCallback(RechargeDo rechargeDo) {
        IResult<Integer> result = new ResultSupport<Integer>();
        RechargeDo localRechargeDo = rechargeComponent.getById(rechargeDo.getId());
        if (localRechargeDo == null){
            //记录不存在
            result.setSuccess(false);
            result.setErrorMessage("操作失败，请稍后再试!");
        }else if (localRechargeDo.getResult()==1){
            //记录已处理
//            result.setSuccess(false);
//            result.setErrorMessage("重复操作!");
            result.setSuccess(true);
        }else{
            rechargeDo.setResult(1);
            rechargeDo.setUserId(localRechargeDo.getUserId());
            int i = rechargeComponent.updateRecharge(rechargeDo);
            if (i>0){
                try {
                    userComponent.updateAmount(rechargeDo.getRechargeMoney(), 0.0,localRechargeDo.getUserId());
                }catch (Exception e){
                    LOGGER.error(e.getMessage(),e);
                }
                //添加资金流水
                FundrecordDo fundrecordDo = new FundrecordDo();
                fundrecordDo.setUserId(localRechargeDo.getUserId());
                fundrecordDo.setFundMode(FundConstant.RECHARGE);
                fundrecordDo.setHandleSum(rechargeDo.getRechargeMoney());
                fundrecordDo.setOperateType(-1);

                AccountUserDo userDo = userComponent.getById(localRechargeDo.getUserId());
                fundrecordDo.setUsableSum(userDo.getUsableSum());
                fundrecordDo.setFreezeSum(userDo.getFreezeSum());
                Double dueinSum = investComponent.getDueinSum(localRechargeDo.getUserId());
                fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);
                fundrecordDo.setRecordTime(new Date());
                String remark = "充值成功";
                fundrecordDo.setRemarks(remark);
                fundrecordDo.setIncome(rechargeDo.getRechargeMoney());
                fundrecordDo.setSpending(0.0);
                try {
                    fundrecordComponent.addFundrecord(fundrecordDo);
                }catch (Exception e){
                    LOGGER.error("插入资金记录失败");
                    LOGGER.error(e.getMessage(),e);
                }
                result.setSuccess(true);

                if (StringUtils.isNotBlank(rechargeDo.getInfo())){
                    LOGGER.info("-------rechargeDo.getInfo:"+rechargeDo.getInfo());
                    //JSONObject jsonObject = JSONObject.fromObject(rechargeDo.getInfo());
                    Map<String,Object> jsonObject =(Map) JsonUtil.json2Bean(rechargeDo.getInfo(),Map.class);
                    if (jsonObject!=null){
                        if ("wyf".equals(jsonObject.get("type"))){
                            long wyfOrdId = Long.parseLong(jsonObject.get("id").toString());
                            int i1 = activityOrderComponent.addRechargeMoney(wyfOrdId, rechargeDo.getRechargeMoney(),
                                    localRechargeDo.getUserId());
                            if (i1<=0){
                                LOGGER.error("修改订单充值金额失败！wyfOrdId:"+wyfOrdId+",localRechargeDo:"+localRechargeDo);
                            }else {
                                ActivityOrderDo activityOrderDo = activityOrderComponent.getById(wyfOrdId);
                                if (activityOrderDo!=null && rechargeDo.getRechargeMoney()>=activityOrderDo.getInvestAmount()){
                                    //彩富一号订单充值完成
                                    int j = activityOrderService.updateStatus(userDo,activityOrderDo);
                                    if (j<=0){
                                        //修改订单状态失败
                                        LOGGER.error("修改订单状态失败.activityOrderDo:"+activityOrderDo);
                                    }
                                }

                            }
                        }
                    }
                }
            }else{
                result.setSuccess(false);
                result.setErrorMessage("操作失败，请稍后再试!");
            }

        }
        return result;
    }

    @Override public List<RechargeDo> selectRecharges(Map<String, Object> parameterMap) {

        return null;
    }

    @Override public RechargeDo getById(long id) {
        RechargeDo rechargeDo = rechargeComponent.getById(id);
        return rechargeDo;
    }

    public void setRechargeComponent(IRechargeComponent rechargeComponent) {
        this.rechargeComponent = rechargeComponent;
    }

    public void setUserComponent(IUserComponent userComponent) {
        this.userComponent = userComponent;
    }

    public void setAccountManagerService(IAccountManagerService accountManagerService) {
        this.accountManagerService = accountManagerService;
    }
}
