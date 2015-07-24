/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.system
 * @Title: SettSchemeAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月9日 上午9:50:47
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.system.ISettSchemeService;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月9日 上午9:50:47
 */
@Scope("prototype")
@Controller("settSchemeAction")
public class SettSchemeAction extends ActionSupport implements ServletRequestAware {
    private static final long  serialVersionUID = 1L;
    @Autowired
    private ISettSchemeService settSchemeService;
    private HttpServletRequest request;
    private SettSchemeDo       settSchemeDo     = new SettSchemeDo();

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String querySettSchemes() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("schemeCode", settSchemeDo.getSchemeCode());
        searchItems.put("repayWay", settSchemeDo.getRepayWay());
        searchItems.put("receiptWay", settSchemeDo.getReceiptWay());
        searchItems.put("schemeStatus", settSchemeDo.getSchemeStatus());
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<SettSchemeDo> pageDo = settSchemeService.querySettSchemes(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    public String addSettScheme() {
        int count = settSchemeService.addSettScheme(settSchemeDo);
        if (count > 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String updateSettScheme() {
        int count = settSchemeService.updateSettScheme(settSchemeDo);
        if (count > 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    /**
     * 根据方案ID删除结算方案和费用规则信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午10:00:37
     */
    public String deleteBySchemeId() {
        String schemeId = request.getParameter("schemeId");
        if (StringUtils.isBlank(schemeId) || !StringUtils.isNumeric(schemeId)) {
            return ERROR;
        }
        int count = settSchemeService.deleteBySchemeId(Long.parseLong(schemeId));
        if (count > 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    /**
     * 查看结算方案明细信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:57:46
     */
    public String toSettSchemeDetail() {
        String schemeId = request.getParameter("schemeId");
        if (StringUtils.isBlank(schemeId) || !StringUtils.isNumeric(schemeId)) {
            return ERROR;
        }
        request.setAttribute("settSchemeDo", settSchemeService.getBySchemeId(Long.parseLong(schemeId)));
        return SUCCESS;
    }

    /**
     * @return settSchemeDo
     */
    public SettSchemeDo getSettSchemeDo() {
        return settSchemeDo;
    }

    /**
     * @param settSchemeDo
     *            the settSchemeDo to set
     */
    public void setSettSchemeDo(SettSchemeDo settSchemeDo) {
        this.settSchemeDo = settSchemeDo;
    }

}
