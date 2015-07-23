package com.hehenian.biz.common.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.agreement.common.utils.AgreementEnum;
import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.dataobject.FundTradeAgreement;
import com.hehenian.biz.common.trade.dataobject.InvestDo;


public interface IInvestService {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public InvestDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<InvestDo> selectInvest(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateInvestById(InvestDo newInvestDo);

	/**
	 * 新增
	 */
	public IResult<?> addInvest(InvestDo newInvestDo,Long usrCustId,int userGroup,String usableSum);
	
	/**
	 * 删除
	 */
	int deleteById(int id);

    IResult<?> addInvestCallback(InvestDo investDo);

    /**
     * 流标
     * @param borrowId
     */
    IResult<?> discardBorrow(long borrowId);
    

    /**
     * 成功投资记录
     * @param parameterMap 查询条件
     * @param page 翻页对象
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月20日下午12:36:03
     */
    PageDo selectInvestSuccessRecordPage(Map<String, Object> parameterMap, PageDo page);

    /**
     * 成功债券转让记录
     * @param parameterMap 查询条件
     * @param page 翻页对象
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月20日下午12:36:12
     */
    PageDo selectDebtSuccessRecordPage(Map<String, Object> parameterMap,PageDo page);

    /** 
     * 成功投资的汇总金额
     * @param id 用户ID
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年10月28日下午4:47:03
     */
    public Map<String, String> getInvestSuccessAmountByUserId(Long id);

    /**
     * 查询用户投资的次数
     * @param userId
     * @return
     */
    long hasInvest(long userId);
    
    /**
     * @Description: 用于彩生活用户在购买定期理财，为线下打印纸质凭证提供数据
     * @param userId
     * @param beginDate
     * @param endDate
     * @return
     * @author: zhanbmf
     * @date 2015-3-17 下午8:45:37
     */
    List<Map<String, Object>> getInvestDataForCSH(Long userId, Integer orderNo, Date beginDate, Date endDate);
    
    /**
     * @Description: 保持协议数据记录,并存协议文档
     * @param fta
     * @param agreementType 协议类型
     * @param agreementData 协议数据
     * @return -1 异常
     * @author: zhanbmf
     * @date 2015-3-23 下午10:03:54
     */
    int saveAgreement(FundTradeAgreement fta, AgreementEnum agreementType, Map agreementData);
}
