/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.trade.action
 * @Title: AgreementAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月19日 上午11:11:00
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.trade.action;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.IBorrowService;
import com.hehenian.web.common.contant.WebConstants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.shove.util.AsianFontProvider;

import freemarker.template.Template;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月19日 上午11:11:00
 */
@Scope("prototype")
@Controller("newAgreementAction")
public class AgreementAction extends ActionSupport implements ServletRequestAware, SessionAware {
    private final Logger                   logger           = Logger.getLogger(this.getClass());
    private static final long              serialVersionUID = 1L;
    @Autowired
    private FreeMarkerConfigurationFactory freeMarkerConfigurer;
    @Autowired
    private IBorrowService                 borrowService;
    private HttpServletRequest             request;
    private Map<String, Object>            session;
    private Long                           borrowId;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * 借款协议
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月19日上午11:12:03
     */
    public String agreement() {
        try {
            AccountUserDo user = (AccountUserDo) session.get(WebConstants.SESSION_USER);
            Long userId = (user != null ? user.getId() : null);
            IResult<?> result = borrowService.queryAgreementParams(userId, borrowId);
            if (!result.isSuccess()) {
                request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
                return ERROR;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> params = (Map<String, Object>) result.getModel();
            @SuppressWarnings("unchecked")
            Map<String, Object> borrow = (Map<String, Object>) params.get("borrow");
            params.put("userId", userId);
            String templateFileName = getTemplateFileName((Integer) borrow.get("paymentMode"));
            Template template = freeMarkerConfigurer.createConfiguration().getTemplate(templateFileName);
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);

            // 创建PDF
            Document document = new Document(PageSize.A4, 25, 25, 25, 25);
            OutputStream out = ServletActionContext.getResponse().getOutputStream();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
            document.open();

            String path = ServletActionContext.getServletContext().getRealPath("images/admin/hhnxd-1.png");
            Image image = Image.getInstance(path);
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(content.getBytes()), null,
                    new AsianFontProvider());
            image.setAbsolutePosition(350, 40);// 最后一页右下角
            document.add(image);// 最后一页右下角
            document.close();
            return null;
        } catch (Exception e) {
            logger.error("Error while generate Agreement Content ", e);
            request.setAttribute(WebConstants.MESSAGE_KEY, "获取协议出错，请稍后再试!");
            return ERROR;
        }
    }

    private String getTemplateFileName(Integer paymentMode) {
        if (paymentMode.intValue() == 5) {
            return "/agreement/agreement_group.ftl";
        } else if (paymentMode.intValue() == 7) {
            return "/agreement/agreement_property.ftl";
        } else if(paymentMode.intValue() == 8 || paymentMode.intValue() == 9 || paymentMode.intValue() == 10 || paymentMode.intValue() == 11){
        	return "/agreement/agreement_newJy.ftl";
        } else {
            return "/agreement/agreement_default.ftl";
        }
    }

    /**
     * @return borrowId
     */
    public Long getBorrowId() {
        return borrowId;
    }

    /**
     * @param borrowId
     *            the borrowId to set
     */
    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

}
