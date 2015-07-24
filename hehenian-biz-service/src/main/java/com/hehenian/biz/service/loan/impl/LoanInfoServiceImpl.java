/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.loan.impl
 * @Title: LoanInfoServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:31:23
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.loan.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.loan.ILoanInfoService;
import com.hehenian.biz.common.loan.dataobject.LoanInfoDo;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.loan.ILoanInfoComponent;

/**
 * 
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:31:23
 */
@Service("loanInfoService")
public class LoanInfoServiceImpl implements ILoanInfoService {
    private final Logger       logger = Logger.getLogger(this.getClass());

    @Autowired
    private ILoanInfoComponent loanInfoComponent;
    @Autowired
    private IPersonComponent   personComponent;

    @Override
    public IResult<?> addLoanInfo(List<LoanInfoDo> loanInfoDoList) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            loanInfoComponent.addLoanInfo(loanInfoDoList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public IResult<?> updateLoanInfo(LoanInfoDo loanInfoDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            loanInfoComponent.updateLoanInfo(loanInfoDo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public NPageDo<LoanInfoDo> queryLoanInfos(Map<String, Object> searchItems) {
        NPageDo<LoanInfoDo> pageDo = new NPageDo<LoanInfoDo>();
        try {
            long count = loanInfoComponent.countLoanInfo(searchItems);
            pageDo.setTotalCount(count);
            if (count == 0) {
                return pageDo;
            }
            List<LoanInfoDo> loanInfoDoList = loanInfoComponent.queryLoanInfos(searchItems);
            pageDo.setModelList(loanInfoDoList);
            return pageDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            pageDo.setTotalCount(0l);
            return pageDo;
        }
    }

    @Override
    public LoanInfoDo getByLoanInfoId(Long loanInfoId) {
        return loanInfoComponent.getByLoanInfoId(loanInfoId);
    }

    @Override
    public List<LoanInfoDo> queryByLoanInfoIds(List<Long> loanInfoIdList) {
        return loanInfoComponent.queryByLoanInfoIds(loanInfoIdList);
    }

    @Override
    public IResult<?> addLoanDetail(List<Long> loanInfoIdList) {
        IResult<?> result = new ResultSupport<String>();
        StringBuffer resultBuf = new StringBuffer();
        // 查询借款信息
        List<LoanInfoDo> loanInfoDoList = queryByLoanInfoIds(loanInfoIdList);
        for (LoanInfoDo loanInfoDo : loanInfoDoList) {
            // 根据用户姓名和身份证查询用户ID
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("realName", loanInfoDo.getRealName());
            paramMap.put("idNo", loanInfoDo.getIdNo());
            Long userId = personComponent.getIdByRealnameAndIdNum(paramMap);
            try {
                loanInfoComponent.addLoanDetail(loanInfoDo, userId);
            } catch (RuntimeException e) {
                resultBuf.append(loanInfoDo.getRealName() + "的借款发布失败");
                resultBuf.append("\r\n");
            }
        }

        if (StringUtils.isBlank(resultBuf.toString())) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMessage(resultBuf.toString());
        }
        return result;
    }

}
