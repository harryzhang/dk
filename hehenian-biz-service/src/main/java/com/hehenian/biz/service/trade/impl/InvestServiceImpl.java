package com.hehenian.biz.service.trade.impl;

import com.hehenian.agreement.common.share.FileClient;
import com.hehenian.agreement.common.utils.AgreementEnum;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.contant.Constants;
import com.hehenian.biz.common.contant.FundConstant;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.IInvestService;
import com.hehenian.biz.common.trade.dataobject.*;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.*;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import com.hehenian.biz.service.dao.AbstractBaseDaoImpl;
import com.ibm.icu.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.Resource;

/**
 * User: liuwtmf
 * Date: 2014/9/28
 * Time: 9:22
 */
@Service("investService")
public class InvestServiceImpl implements IInvestService {
    private static final Logger LOGGER = Logger.getLogger(InvestServiceImpl.class);
    @Autowired
    private IInvestComponent       investComponent;
    @Autowired
    private IBorrowComponent       borrowComponent;
    @Autowired
    private IAccountManagerService accountManagerService;
    @Autowired
    private IUserComponent         userComponent;
    @Autowired
    private IFundrecordComponent   fundrecordComponent;
    @Autowired
    private IInvestRepaymentComponent investRepaymentComponent;
    @Autowired
    private IAssignmentDebtComponent  assignmentDebtComponent;
    
    @Resource
	private JdbcTemplate sp2pJdbcTemplate;

    @Override public InvestDo getById(int id) {
        return null;
    }

    @Override public List<InvestDo> selectInvest(Map<String, Object> parameterMap) {
        return null;
    }

    @Override public int updateInvestById(InvestDo newInvestDo) {
        return 0;
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Override public IResult<?> addInvest(InvestDo newInvestDo, Long usrCustId, int userGroup, String usableSum) {
        BorrowDo borrowDo = borrowComponent.getById(newInvestDo.getBorrowId());
        LOGGER.info("borrowDo:"+borrowDo);
        IResult<String> result = new ResultSupport<String>();
        if (borrowDo != null) {
            if (borrowDo.getBorrowStatus() == 2) {
                long publisher = borrowDo.getPublisher();
                if (newInvestDo.getInvestor() != publisher) {
                    if (usrCustId > 0) {
                        int borrowGroup = borrowDo.getBorrowGroup();
                        if (borrowGroup == 0 || userGroup == borrowGroup) {
                            if (borrowDo.getBorrowAmount() >= (newInvestDo.getInvestAmount() + borrowDo
                                    .getHasInvestAmount())) {
                                if (Double.parseDouble(usableSum) >= newInvestDo.getInvestAmount()) {
                                    InvestDo investDo = new InvestDo();
                                    //                                    investDo.setBorrowId(newInvestDo.getBorrowId());
                                    investDo.setInvestTime(new Date());
                                    investDo.setDeadline(borrowDo.getDeadline());
                                    investDo.setMonthRate(borrowDo.getAnnualRate() / 12 * 100);
                                    investDo.setInvestor(newInvestDo.getInvestor());
                                    investDo.setOriInvestor(newInvestDo.getInvestor());
                                    investDo.setFlag(newInvestDo.getFlag());
                                    investDo.setSourceFrom(newInvestDo.getSourceFrom());
                                    int i = investComponent.addInvest(investDo);
                                    if (i > 0) {
                                        InParameter inParameter = new InParameter();
                                        inParameter.setOrdId(investDo.getId() + "");
                                        inParameter.getParams().put("OrdDate",DateUtil.dateToYMD(new Date()));
                                        inParameter.getParams().put("TransAmt",DECIMAL_FORMAT.format(newInvestDo.getInvestAmount() ));
                                        AccountUserDo accountUserDo = userComponent.getById(investDo.getInvestor());
                                        inParameter.getParams().put("UsrCustId",
                                                accountUserDo.getUsrCustId() + "");
										// JSONObject borrowerDetail = new
										// JSONObject();
										Map<String, Object> borrowerDetail = new HashMap<String, Object>();
                                        AccountUserDo borrowUser = userComponent.getById(publisher);
                                        borrowerDetail.put("BorrowerCustId",borrowUser.getUsrCustId()+"");
                                        borrowerDetail.put("BorrowerAmt", DECIMAL_FORMAT.format(
                                                newInvestDo.getInvestAmount()));
                                        // 汇付还款总额为借款金额*(1+利率).改为1防止出现手续费过多出现:本次还款金额加上已还金额超过还款总额的情况
                                        borrowerDetail.put("BorrowerRate","1.00");
										String borrowrDetailStr = "";
										try {
											borrowrDetailStr = JsonUtil
													.toString(borrowerDetail);
										} catch (IOException e1) {
											borrowrDetailStr = "";
											LOGGER.error(e1);
										}

										inParameter.getParams().put(
												"BorrowerDetails",
												"[" + borrowrDetailStr + "]");
                                        inParameter.getParams().put("borrowId", newInvestDo.getBorrowId() + "");
                                        inParameter.setRetUrl("borrowInvestCallback.do");
                                        inParameter.setBgRetUrl("");

                                        FundrecordDo fundDo = new FundrecordDo();
                                        fundrecordComponent.addFundrecord(fundDo);
                                        fundrecordComponent.deleteById(fundDo.getId());
                                        inParameter.getParams().put("FreezeOrdId",fundDo.getId()+"");
                                        try {
                                            OutParameter outParameter = accountManagerService.initiativeTender(inParameter, AccountType.CHINAPNR);
                                            if (outParameter.isSuccess()) {
                                                result.setSuccess(true);
                                                result.setModel((String)outParameter.getParams().get("htmlText"));
                                            } else {
                                                result.setSuccess(false);
                                                result.setErrorMessage(outParameter.getRespDesc());
                                            }
                                        }catch (BusinessException e){
                                            result.setSuccess(false);
                                            result.setErrorMessage(e.getMessage());
                                            LOGGER.error(e.getMessage(), e);
                                        } catch (Exception e) {
                                            result.setSuccess(false);
                                            result.setErrorMessage("操作失败,请稍后再试!");
                                            LOGGER.error(e.getMessage(), e);
                                        }
                                    }
                                }else{
                                    //可用余额不足
                                    result.setSuccess(false);
                                    result.setErrorMessage("可用余额不足");
                                }
                            }else{
                                //投资总额超过可投总额
                                result.setSuccess(false);
                                result.setErrorMessage("投资总额超过可投总额");
                            }
                        }
                    }else {
                        //没有注册汇付
                        result.setSuccess(false);
                        result.setErrorMessage("没有注册汇付");
                    }
                }else{
                    //不能投自己发布的标的
                    result.setSuccess(false);
                    result.setErrorMessage("不能投自己发布的标的");
                }
            }else{
                //标的不在投标中的状态
                result.setSuccess(false);
                result.setErrorMessage("标的不在投标中的状态");
            }
        }else{
            //标的不存在
            result.setSuccess(false);
            result.setErrorMessage("标的不存在");
        }
        return result;
    }



    @Override public int deleteById(int id) {
        return 0;
    }

    @Override public IResult<?> addInvestCallback(InvestDo investDo) {
        IResult<Integer> result = new ResultSupport<Integer>();
        InvestDo localInvestDo = investComponent.getById(investDo.getId());
        BorrowDo borrowDo = borrowComponent.getById(investDo.getBorrowId());
        if (localInvestDo == null){
            //记录不存在
            result.setSuccess(false);
            result.setErrorMessage("记录不存在");
        }else if (localInvestDo.getTrxId()>0){
            //记录已处理
//            result.setSuccess(false);
            result.setSuccess(true);
        }else{
            //更新标的数据
            //
            borrowDo.setInvestNum(borrowDo.getInvestNum()+1);
            borrowDo.setHasInvestAmount(borrowDo.getHasInvestAmount() + investDo.getInvestAmount());
            int j = borrowComponent.updateBorrowInvest(borrowDo);
            if( j>0 ){
                //更新成功
                localInvestDo.setBorrowId(investDo.getBorrowId());
                localInvestDo.setInvestAmount(investDo.getInvestAmount());
                localInvestDo.setRealAmount(investDo.getInvestAmount());
                localInvestDo.setTrxId(investDo.getTrxId());
                localInvestDo.setInvestNumber("ZQ"+DateUtil.dateToYMD(new Date())+LPAD(StringUtils.right(investDo.getId()+"",5),5));
                int i = investComponent.updateInvest(localInvestDo);
                if (i>0){
                    //查询标的是否满标
                    //如果满标 就设置标的状态为满标
                    if (borrowDo.getBorrowAmount() <= borrowDo.getHasInvestAmount()) {
                        borrowDo.setBorrowStatus(Constants.BORROW_STATUS_FULL);
                        borrowComponent.updateStatus(borrowDo);
                    }
                    //修改用户的可用余额和冻结金额
                    try {
                        userComponent.updateAmount(-investDo.getInvestAmount(),investDo.getInvestAmount(),localInvestDo.getInvestor());
                    }catch (Exception e){
                        LOGGER.error(e.getMessage(),e);
                        LOGGER.error("修改用户金额失败");
                    }

                    result.setSuccess(true);
                }else{
                    result.setSuccess(false);
                    result.setErrorMessage("操作失败，请稍后再试!");
                }
            }else{
                //失败
                //解冻金额
                InParameter inParameter = new InParameter();
                inParameter.setOrdId(investDo.getTrxId() + "");
                inParameter.setTrxId(investDo.getTrxId() + "");
                OutParameter outParameter = accountManagerService.usrUnFreeze(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    result.setSuccess(false);
                    result.setErrorMessage(outParameter.getRespDesc());
                }else{
                    result.setSuccess(false);
                    result.setErrorMessage("标的已满，投标失败！");
                }
            }
        }
        return result;
    }
private String LPAD(String x,int length){
    while (x.length()<length){
        x = "0" + x;
    }
    return x;
}
    @Override
    public IResult<?> discardBorrow(long borrowId){
        BorrowDo borrowDo = borrowComponent.getById(borrowId);
        IResult<Integer> result = new ResultSupport<Integer>();
        if (borrowDo!=null && (borrowDo.getBorrowStatus()==Constants.BORROW_STATUS_FULL || borrowDo.getBorrowStatus()==Constants.BORROW_STATUS_NOT_FULL)){
            borrowDo.setBorrowStatus(Constants.BORROW_STATUS_DISCARD);
            int i = borrowComponent.updateStatus(borrowDo);
            if (i>0){
                result.setSuccess(true);
                // 修改标的状态成功
                List<InvestDo> investDos = investComponent.queryByBorrowId(borrowId);
                if (investDos!=null){
                    StringBuilder sb = new StringBuilder();
                    for (InvestDo investDo : investDos) {
                        InParameter inParameter = new InParameter();
                        inParameter.setOrdId(investDo.getTrxId() + "");
                        inParameter.setTrxId(investDo.getTrxId() + "");
                        OutParameter outParameter = accountManagerService.usrUnFreeze(inParameter, AccountType.CHINAPNR);
                        if (outParameter.isSuccess()){
                            //修改用户可用余额 冻结金额
                            Boolean aBoolean = userComponent.updateAmount(investDo.getInvestAmount(), -investDo.getInvestAmount(),
                                    investDo.getInvestor());
                            LOGGER.info("update user amount:"+aBoolean);
                            //添加资金流水
                            FundrecordDo fundrecordDo = new FundrecordDo();
                            fundrecordDo.setUserId(investDo.getInvestor());
                            fundrecordDo.setFundMode(FundConstant.UNFREEZE_INVEST);
                            fundrecordDo.setHandleSum(investDo.getInvestAmount());
                            fundrecordDo.setOperateType(-1);

                            AccountUserDo userDo = userComponent.getById(investDo.getInvestor());
                            fundrecordDo.setUsableSum(userDo.getUsableSum());
                            fundrecordDo.setFreezeSum(userDo.getFreezeSum());
                            Double dueinSum = investComponent.getDueinSum(investDo.getInvestor());
                            fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);
                            fundrecordDo.setRecordTime(new Date());
                            String remark = "投标失败解冻投标金额"+investDo.getInvestAmount()+"元";
                            fundrecordDo.setRemarks(remark);
                            fundrecordDo.setIncome(investDo.getInvestAmount());
                            fundrecordDo.setSpending(0.0);
                            try {
                                fundrecordComponent.addFundrecord(fundrecordDo);
                            }catch (Exception e){
                                LOGGER.error("插入资金记录失败");
                                LOGGER.error(e.getMessage(),e);
                            }

                        }else {
                            //解冻失败
                            String msg = "投资"+investDo.getId()+"解冻失败,金额"+investDo.getInvestAmount()+",汇付返回："+outParameter.getRespDesc();
                            sb.append(msg).append(";\n");
                            LOGGER.error(msg);
                        }
                    }
                    if (sb.length()>0){
                        result.setSuccess(false);
                        result.setErrorMessage(sb.toString());
                    }
                }
            }
        }
        return result;

    }

    /* (no-Javadoc)
     * <p>Title: selectInvestSuccessRecordPage</p>
     * <p>Description: </p>
     * @param parameterMap
     * @param page
     * @return
     * @see com.hehenian.biz.common.trade.IInvestService#selectInvestSuccessRecordPage(java.util.Map, com.hehenian.biz.common.base.dataobject.PageDo)
     */
    @Override
    public PageDo selectInvestSuccessRecordPage(Map<String, Object> parameterMap, PageDo page) {
        parameterMap.put(Constants.MYBATIS_PAGE, page);
        String currentUser = String.valueOf((Long)parameterMap.get("investor"));
        List<Map<String,Object>> investList = investComponent.selectInvestSuccessRecordPage(parameterMap);
        if(null == investList || investList.isEmpty()) return page;
        
        // 封装处理翻页查询的结果 ,处理hessian 不支持的数据类型
        Map<String,List<Integer>>  rowMap = new HashMap<String,List<Integer>>();
        List<Long> investIdList = new ArrayList<Long>();
        for(int i = 0 ; i < investList.size() ; i++ ){
           Map<String,Object> row  =  investList.get(i);
           long investId = (Long)row.get("investId");
           BigDecimal annualRate = row.get("annualRate") == null ? BigDecimal.ZERO :(BigDecimal)row.get("annualRate");
           row.put("annualRate", CalculateUtils.round(annualRate.doubleValue()));
           
           BigDecimal realAmount = row.get("realAmount") == null ? BigDecimal.ZERO :(BigDecimal)row.get("realAmount");
           row.put("realAmount", CalculateUtils.round(realAmount.doubleValue()));
           String investTime = "";
           try{
               investTime = DateUtil.dateToString((Date)row.get("investTime"));
           }catch(Exception e){
               LOGGER.error("investTime format error");
           }
           
           row.put("investTime", investTime);
           row.put("currentUser", currentUser);
           
           //如果是投资去取债券转让信息
           AssignmentDebtDo ad = assignmentDebtComponent.getSuccessInvestAssignmentDebt(investId, new Long(currentUser));
           if(null != ad){
               AuctionDebtDo auddo =  ad.getAuctionDebtDo();
               if(null != auddo){
                   row.put("auctionStatus", auddo.getAuctionStatus());
                   //auddo.getAuctionTime();
               }
               row.put("alienatorId", ad.getAlienatorId());
               row.put("auctionerId", ad.getAuctionerId());
               row.put("debtId", ad.getId());
               row.put("debtStatus", ad.getDebtStatus());
           }
           investIdList.add(investId);
           String rowKey = currentUser +String.valueOf(investId);
           List<Integer> rowIdx = rowMap.get(rowKey);
           if(rowIdx == null){
               rowIdx = new ArrayList<Integer>();
               rowMap.put(rowKey, rowIdx);
           }
           rowIdx.add(i);
        }
        // end  封装处理翻页查询的结果 ,处理hessian 不支持的数据类型

        List<Map<String,Object>> investAmountList = investRepaymentComponent.getInvestSuccessAmount(investIdList);
        for(Map<String,Object> map : investAmountList){
            Long investId = (Long)map.get("invest_id");
            double recivedPrincipal = map.get("recivedPrincipal") == null ? 0 :((BigDecimal)map.get("recivedPrincipal")).doubleValue();
            double hasPI = map.get("hasPI") == null ? 0 :((BigDecimal)map.get("hasPI")).doubleValue();
            double notPI = map.get("notPI") == null ? 0 :((BigDecimal)map.get("notPI")).doubleValue();
            double whbj = map.get("whbj") == null ? 0 :((BigDecimal)map.get("whbj")).doubleValue();
            double blanceP = map.get("blanceP") == null ? 0 :((BigDecimal)map.get("blanceP")).doubleValue();
            Long investor = (Long)map.get("investor");

            /**
             * 如果转让了，投资人可能不是当前用户
             */
            List<Integer> rowIdxList = rowMap.get(String.valueOf(investor)+String.valueOf(investId));
            
            if(rowIdxList != null){
                for(int idx : rowIdxList){
                    Map<String,Object>row = investList.get(idx);
                    row.put("recivedPrincipal", CalculateUtils.round(recivedPrincipal));
                    row.put("hasPI", CalculateUtils.round(hasPI));
                    row.put("notPI", CalculateUtils.round(notPI));
                    row.put("whbj", CalculateUtils.round(whbj));
                    row.put("blanceP", CalculateUtils.round(blanceP));
                }
            }

        }
        page.setPage(investList);
        return page;
    }

    /* (no-Javadoc)
     * <p>Title: selectDebtSuccessRecordPage</p>
     * <p>Description: </p>
     * @param parameterMap
     * @param page
     * @return
     * @see com.hehenian.biz.common.trade.IInvestService#selectDebtSuccessRecordPage(java.util.Map, com.hehenian.biz.common.base.dataobject.PageDo)
     */
    @Override
    public PageDo selectDebtSuccessRecordPage(Map<String, Object> parameterMap, PageDo page) {
        parameterMap.put(Constants.MYBATIS_PAGE, page);
        List<Map<String,Object>> debttList = investComponent.selectDebtSuccessRecordPage(parameterMap);
        return page;
    }

    /* (no-Javadoc) 
     * <p>Title: getInvestSuccessAmountByUserId</p> 
     * <p>Description: </p> 
     * @param id
     * @return 
     * @see com.hehenian.biz.common.trade.IInvestService#getInvestSuccessAmountByUserId(java.lang.Long) 
     */
    @Override
    public Map<String, String> getInvestSuccessAmountByUserId(Long id) {
        Map<String, Object> tmpMap =  investRepaymentComponent.getInvestSuccessAmountByUserId(id);
        //处理hessian bigdecimal 传不了
        if(null == tmpMap){
            return Collections.EMPTY_MAP;
        }
        
        Map<String, String> newMap = new HashMap<String,String>();
        BigDecimal realAmount= tmpMap.get("realAmount") == null ? BigDecimal.ZERO:(BigDecimal) tmpMap.get("realAmount") ;
        BigDecimal shouldGetAmount= tmpMap.get("shouldGetAmount") == null ? BigDecimal.ZERO:(BigDecimal) tmpMap.get("shouldGetAmount") ;
        BigDecimal hasGetAmount= tmpMap.get("hasGetAmount") == null ? BigDecimal.ZERO:(BigDecimal) tmpMap.get("hasGetAmount") ;
        newMap.put("realAmount", realAmount.toString());
        newMap.put("shouldGetAmount", shouldGetAmount.toString());
        newMap.put("hasGetAmount", hasGetAmount.toString());
        newMap.put("noGetAmount",CalculateUtils.round(shouldGetAmount.doubleValue()-hasGetAmount.doubleValue()));
        return newMap;
    }

    /**
     * 查询用户投资的次数
     * @param userId
     * @return
     */
    public long hasInvest(long userId){
        return investComponent.hasInvest(userId);
    }

    public List<Map<String, Object>> getInvestDataForCSH(Long userId, Integer orderNo, Date beginDate, Date endDate) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("select tft.trade_id orderNo,tft.trade_time transDate,concat(tft.trade_amount,'') transAmount")
    	.append(",tft.user_id userId,concat(tft.rate,'') yearRate,pr.period term,cli.cname,cli.busGroupName groupName ")
    	.append("from td_fund_trade tft inner join td_product_rate pr on tft.rate_id=pr.product_rate_id ")
    	.append("left join t_colourlife_info cli on tft.user_id=cli.userId where ");
    	
    	List<Map<String, Object>> resultList = null;
    	try {
	    	if(orderNo == null || orderNo <=0) {
	    		sb.append("tft.user_id=? and tft.trade_time>=? and tft.trade_time<=? + interval 1 day;");
	    		resultList = sp2pJdbcTemplate.queryForList(sb.toString(), userId, beginDate, endDate);
	    	} else {
	    		sb.append("tft.trade_id=?;");
	    		resultList =sp2pJdbcTemplate.queryForList(sb.toString(), orderNo);
	    	}

    		return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
    }

	@Override
	public int saveAgreement(FundTradeAgreement fta, AgreementEnum agreementType, Map agreementData) {
		if(fta == null || fta.getUserId() == null) {
			return -1;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("insert into td_fund_trade_agreement values (?,?,?,?,?,?);");
		int ret = sp2pJdbcTemplate.update(sb.toString(), fta.getId(), fta.getUserId(), fta.getAgreementType(), fta.getTradeId(), fta.getAgreementFileName(), fta.getCreateTime());
		if(ret > 0) {
			FileClient fc = new FileClient();
			fc.saveAgreement(fta.getUserId(), fta.getAgreementFileName(), agreementType, agreementData);
			return 1;
		}
		
		return -1;
	}
}
