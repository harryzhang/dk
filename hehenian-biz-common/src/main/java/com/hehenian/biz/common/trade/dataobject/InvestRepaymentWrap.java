/**
 * 
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.util.FeeUtil;

/**
 * @author zhangyunhmf
 *
 */
public class InvestRepaymentWrap implements Serializable {

	/**
	 * 回款对象
	 */
	private InvestRepaymentDo investRepaymentDO;

	/**
	 * 调用汇付的结果
	 */
    private boolean              success;

	/**
	 * 分摊的各种费用明细
	 */
    private List<RepaymentFeeDo> proportionFeeList;


	    /**
     * 获取应还费用总额
     * 
     * @return the fee
     */
	public double getFeeSum() {
		double retVal = 0;
		if (null == this.proportionFeeList) {
			return 0;
		} else {
            for (RepaymentFeeDo pf : proportionFeeList) {
                retVal += pf.getHasAmount();
			}
		}
		return retVal;
	}

    /**
     * 根据费用码获取已还费用
     * 
     * @param code
     *            费用码
     * @return
     * @author: zhangyunhmf
     * @date: 2014年11月21日下午5:09:14
     */
    public double getFeeByFeeCode(String code) {
        double retVal = 0;
        if (null == this.proportionFeeList) {
            return 0;
        } else {
            for (RepaymentFeeDo pf : proportionFeeList) {
                if (code.equals(pf.getFeeCode())) {
                    return pf.getHasAmount();
                }
            }
        }
        return retVal;
    }


	/**
	 * @return the divDetails
	 */
	public String getDivDetails(Map<String, ChinapnrAccount> feeAccountMap) {
		if (null == proportionFeeList || proportionFeeList.isEmpty()) {
			return "";
		}

		Object[][] feeDiv = new Object[proportionFeeList.size()][3];
		int feeCount = 0;
        for (RepaymentFeeDo pf : proportionFeeList) {
			String feeCode = pf.getFeeCode();
			ChinapnrAccount account = feeAccountMap.get(feeCode);
            feeDiv[feeCount][0] = pf.getHasAmount();
			feeDiv[feeCount][1] = account.getCustId();
			feeDiv[feeCount][2] = account.getAccountId();
			feeCount++;
		}

		return FeeUtil.buildFee(feeDiv);
	}

	
    public void addProportionFee(RepaymentFeeDo repaymentFeeDo) {
        List<RepaymentFeeDo> pfList = this.getProportionFeeList();
        if (null == pfList) {
            pfList = new ArrayList<RepaymentFeeDo>();
            this.setProportionFeeList(pfList);
        }
        repaymentFeeDo.setRepaymentId(this.getInvestRepaymentDO().getRepayId());
        repaymentFeeDo.setInvestRepaymentId(this.getInvestRepaymentDO().getId());
        pfList.add(repaymentFeeDo);
	}
	
    /**
     * @return the investRepaymentDO
     */
    public InvestRepaymentDo getInvestRepaymentDO() {
        return investRepaymentDO;
    }

    /**
     * @param investRepaymentDO
     *            the investRepaymentDO to set
     */
    public void setInvestRepaymentDO(InvestRepaymentDo investRepaymentDO) {
        this.investRepaymentDO = investRepaymentDO;
    }

    /**
     * @return proportionFeeList
     */
    public List<RepaymentFeeDo> getProportionFeeList() {
        return proportionFeeList;
    }

    /**
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @param proportionFeeList
     *            the proportionFeeList to set
     */
    public void setProportionFeeList(List<RepaymentFeeDo> proportionFeeList) {
        this.proportionFeeList = proportionFeeList;
    }


}
