package com.hehenian.web.view.trade.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.trade.IReconciliationService;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo;
import com.opensymphony.xwork2.ActionSupport;

/** 
 * @author: xiexiang
 * @date 2014年12月22日 下午3:19:40
 */
@Scope("prototype")
@Component("reconciliationAction")
public class ReconciliationAction extends  ActionSupport implements ServletRequestAware {
    private static final long   serialVersionUID = 1L;
    @Autowired
    private IReconciliationService reconciliationService;
    @Autowired
    private IUserService userService;
    private ReconciliationDo                reconciliationDo     = new ReconciliationDo();

    private HttpServletRequest  request;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }


    public String queryReconciliations() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        if(reconciliationDo.getUserDo() != null && !"".equals(reconciliationDo.getUserDo().getUsername())){
            searchItems.put("username", reconciliationDo.getUserDo().getUsername());
        }
        searchItems.put("ordId", reconciliationDo.getOrdId());
        searchItems.put("ordDate", reconciliationDo.getOrdDate());
        searchItems.put("reconciliationType", reconciliationDo.getReconciliationType());
        searchItems.put("reconciliationStatus", reconciliationDo.getReconciliationStatus());
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<ReconciliationDo> pageDo = reconciliationService.getReconciliations(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    /** 
     * @return reconciliationDo 
     */
    public ReconciliationDo getReconciliationDo() {
        return reconciliationDo;
    }

    /**
     * @param reconciliationDo the reconciliationDo to set
     */
    public void setReconciliationDo(ReconciliationDo reconciliationDo) {
        this.reconciliationDo = reconciliationDo;
    }
}
