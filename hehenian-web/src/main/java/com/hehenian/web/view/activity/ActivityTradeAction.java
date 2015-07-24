/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.web.view.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.activity.IActivityTradeService;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component("activityTradeAction")
public class ActivityTradeAction extends ActionSupport {
    private static final long     serialVersionUID = 1L;
    @Autowired
    private IActivityTradeService activityTradeService;

    /**
     * 新增或修改
     * 
     */
    public String saveActivityTrade() {
        return "";
    }

    /**
     * 删除
     * 
     */
    public String deleteActivityTrade() {
        return "";
    }

    /**
     * 查找
     * 
     * @return
     */
    public String findById() {
        return "";
    }

    /**
     * 根据条件查找
     * 
     * @return
     */
    public String query() {
        return "";
    }

}
