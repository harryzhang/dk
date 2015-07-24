package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.common.trade.dataobject.RechargeDo;
import com.hehenian.biz.component.trade.IRechargeComponent;
import com.hehenian.biz.dal.trade.IRechargeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * User: liuwtmf Date: 2014/9/23 Time: 14:28
 */
@Component("rechargeComponent")
public class IRechargeComponentImpl implements IRechargeComponent {
    @Autowired
    private IRechargeDao rechargeDao;

    @Override
    public RechargeDo getById(long id) {
        RechargeDo rechargeDo = rechargeDao.getById(id);
        System.out.println("s---" + rechargeDo);
        return rechargeDo;
    }

    @Override
    public int addRecharge(RechargeDo rechargeDo) {

        return rechargeDao.addRecharge(rechargeDo);
    }

    @Override
    public int updateRecharge(RechargeDo rechargeDo) {

        return rechargeDao.updateRecharge(rechargeDo);
    }

    @Override
    public List<RechargeDo> selectRecharges(Map<String, Object> parameterMap) {
        return null;
    }

    public void setRechargeDao(IRechargeDao rechargeDao) {
        this.rechargeDao = rechargeDao;
    }

    public void updatetestU() {
        rechargeDao.testU();
        // String x=null;
        // System.out.println(x.length());
        System.out.println(1);
    }

    @Override
    public List<RechargeDo> queryByIds(List<Long> idList) {
        return rechargeDao.queryByIds(idList);
    }

}
