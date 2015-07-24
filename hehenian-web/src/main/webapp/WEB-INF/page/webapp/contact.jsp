<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>问题反馈</title>
<jsp:include page="/include/wap-head.jsp"></jsp:include>
</head>
<body>
<div id="preloader">
  <div id="status">
    <p class="center-text"> 正在加载... </p>
  </div>
</div>
<div class="all-elements  page-sidebar-on">
  <jsp:include page="/include/wap-left.jsp"></jsp:include>

    <div id="content" class="page-content">
    	<div class="page-header">
        	<a href="#" class="deploy-sidebar"></a>
            <p class="bread-crumb">问题反馈</p>
            <a href="contact.html" class="deploy-contact"></a>
        </div>

        

        
        	<div class="one-half-responsive">
                <p style=" padding:20px 20px 0px 20px;">您在投资使用过程中若有任何疑问，请通过下方信息反馈给我们。</p>
                <div class="container no-bottom">
                    <div class="contact-form no-bottom"> 
                        <div class="formSuccessMessageWrap" id="formSuccessMessageWrap">
                            <div class="big-notification green-notification">
                                <h3 class="uppercase">提交成功</h3>
                                <a href="#" class="close-big-notification">x</a>
                                <p>您的问题已经提交，我我们会尽快与您联系</p>
                            </div>
                        </div>
                    
                        <form action="php/contact.php" method="post" class="contactForm" id="contactForm">
                            <fieldset>
                                <div class="formValidationError" id="contactNameFieldError">
                                    <div class="static-notification-red tap-dismiss-notification">
                                        <p class="center-text uppercase">请填写名字</p>
                                    </div>
                                </div>             
                                <div class="formValidationError" id="contactEmailFieldError">
                                    <div class="static-notification-red tap-dismiss-notification">
                                        <p class="center-text uppercase">请填写电子邮件地址</p>
                                    </div>
                                </div> 
                                <div class="formValidationError" id="contactEmailFieldError2">
                                    <div class="static-notification-red tap-dismiss-notification">
                                        <p class="center-text uppercase">请输入正确的电子邮件</p>
                                    </div>
                                </div> 
                                <div class="formValidationError" id="contactMessageTextareaError">
                                    <div class="static-notification-red tap-dismiss-notification">
                                        <p class="center-text uppercase">请输入内容!</p>
                                    </div>
                                </div>   
                                <div class="formFieldWrap">
                                    <label class="field-title contactNameField" for="contactNameField">名字:</label>
                                    <input type="text" name="contactNameField" value="" class="contactField requiredField" id="contactNameField"/>
                                </div>
                                <div class="formFieldWrap">
                                    <label class="field-title contactEmailField" for="contactEmailField">Email: </label>
                                    <input type="text" name="contactEmailField" value="" class="contactField requiredField requiredEmailField" id="contactEmailField"/>
                                </div>
                                <div class="formTextareaWrap">
                                    <label class="field-title contactMessageTextarea" for="contactMessageTextarea">内容: </label>
                                    <textarea name="contactMessageTextarea" class="contactTextarea requiredField" id="contactMessageTextarea"></textarea>
                                </div>
                                <div class="formSubmitButtonErrorsWrap">
                                    <input type="submit" class="buttonWrap button button-green contactSubmitButton" id="contactSubmitButton" value="提交" data-formId="contactForm"/>
                                </div>
                            </fieldset>
                        </form>       
                    </div>
                </div>
            </div>                                
            <div class="decoration"></div>      
      <div class="content-footer" style=" text-align:center;margin-bottom:10px; font-size:10px;"> 爱生活、爱理财、就上E理财<br>
        花样年集团成员 （香港联交所上市企业HK1777） </div>
    </div>
  </div>
</div>
</body>
</html>
