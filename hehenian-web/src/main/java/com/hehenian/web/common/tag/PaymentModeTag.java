package com.hehenian.web.common.tag;

import com.hehenian.biz.common.system.ISettSchemeService;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 根据不汉口方式id展示对应的还款方式名称
 * User: liuwtmf
 * Date: 2015-1-29
 * Time: 9:02
 */
public class PaymentModeTag extends SimpleTagSupport {
    private Long paymentModeId;
    @Override public void doTag() throws JspException, IOException {
        super.doTag();
        PageContext pageContext = (PageContext) this.getJspContext();
        ServletContext servletContext = pageContext.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        ISettSchemeService settSchemeService = wac.getBean("settSchemeService", ISettSchemeService.class);
        String schemeName = "其他";
        SettSchemeDo settSchemeDo = settSchemeService.getBySchemeId(paymentModeId);
        if (settSchemeDo!=null && StringUtils.isNotBlank(settSchemeDo.getSchemeName())){
            schemeName = settSchemeDo.getSchemeName();
        }
        getJspContext().getOut().write(schemeName);
    }

    public Long getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(Long paymentModeId) {
        this.paymentModeId = paymentModeId;
    }
}

