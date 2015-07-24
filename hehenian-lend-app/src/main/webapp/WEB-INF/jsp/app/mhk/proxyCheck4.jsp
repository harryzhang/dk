<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=0" />
		<link rel="stylesheet" href="${fileServerUrl }/app_res/css/common.css?v=${cssversion}">
		<link rel="stylesheet" href="${fileServerUrl }/app_res/css/mhk/style.css?v=${cssversion}">
		<title>个人信息</title>
	</head>
	<body>
		<header>
			<div class="p1  enable_inf" >
					<c:if test="${message != ''}">
						<date class="bf1">${message}</date>
					</c:if>
					<div class=" p1 loan_person" style="padding-top:20px;">
					<p class="db_f"><label class="lab" >请客户经理务必核实贷款人真实信息，所提交的资料与资料原件是否一致。如客户经理选择“一致”，因为贷款人上传资料与资料原件不一致所造成的损失，客户经理将承担相应责任。</label></p>
					</div>
				</div>
		</header>
		<form name="frm" action="<c:url value='/app/mhk/proxyCheckSave.do' />" method="post">
			<input type="hidden"  name="loanPerson.loanId" value="${loanPerson.loanId}"/>
			<input type="hidden"  name="pageId" value="${pageId}"/>
			<input type="hidden"  name="tableCode" value="LoanRelationDo"/>
			<section>
				<article>
					<h3 class="personinf_tip tip_1"><span>贷款人联系人信息</span></h3>
					<div  class="p1  imglist">
						<ul>
							<c:forEach var="cdListOut" items="${cdListOut}" varStatus="status">
								<li>
								<p class="db_f"><label class="lab" >姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label><span class="lab">${cdListOut.certificateName}</span></p>
								<p class="db_f"><label class="lab" >与本人关系：</label><span class="lab">
									<c:if test="${cdListOut.relationType== 0 }">父母</c:if>
									<c:if test="${cdListOut.relationType== 1 }">配偶</c:if>
									<c:if test="${cdListOut.relationType== 2 }">子女</c:if>
									<c:if test="${cdListOut.relationType== 3 }">兄弟</c:if>
									<c:if test="${cdListOut.relationType== 4 }">姐妹</c:if>
									<c:if test="${cdListOut.relationType== 5 }">父母</c:if>
									<c:if test="${cdListOut.relationType== 6 }">配偶</c:if>
									<c:if test="${cdListOut.relationType== 7 }">子女</c:if>
									<c:if test="${cdListOut.relationType== 8 }">兄弟</c:if>
									<c:if test="${cdListOut.relationType== 9 }">姐妹</c:if>
								</span></p> 
								<p class="db_f"><label class="lab" >手&nbsp;机&nbsp;号&nbsp;码：</label><span class="lab">${cdListOut.certificateTypeHead}</span></p>
									<div class="db_f p1 personinf_radio">
										<div class="bf1">
											<c:if test ='${ cdListOut.statusInt == 0}'><label class="checked pass"> </c:if>
											<c:if test ='${ cdListOut.statusInt != 0}'><label class="pass"></c:if>
												<input type="radio" class="rad " name="${status.index}" value="0" />
												与原件不一致
											</label>
										</div>
										<div class="bf1">
											<c:if test ='${ cdListOut.statusInt == 1}'><label class="checked nopass"> </c:if>
											<c:if test ='${ cdListOut.statusInt != 1}'><label class="nopass"></c:if>
												<input type="radio" class="rad "  name="${status.index}" value="1" />
													与原件一致
												</label>
										</div>
				 						<input type="hidden"  name="certificateDoList[${status.index}].loanId" value="${cdListOut.loanId}"/>
										<input type="hidden"  name="certificateDoList[${status.index}].loanPersonId" value="${cdListOut.loanPersonId}"/>
										<input type="hidden"  name="certificateDoList[${status.index}].certificateId" value="${cdListOut.certificateId}"/>
										<input type="hidden"  name="certificateDoList[${status.index}].certificateType" value="${cdListOut.certificateType}"/>
										<input type="hidden"  name="certificateDoList[${status.index}].certificateName" value="${cdListOut.certificateName}"/>
										<input type="hidden"  name="certificateDoList[${status.index}].statusId" value="${cdListOut.statusId}"/>
										<input type="hidden"  name="certificateDoList[${status.index}].statusInt" value="${cdListOut.statusInt}"/>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="btn_box">
						<button class="btn w10" id="sub">提交</button>
					</div>
				</article>
			</section>
		</form>
		<script src="${fileServerUrl }/app_res/js/libs/zepto.js?v=${cssversion}"></script>
		<script src="${fileServerUrl }/app_res/js/app.js?v=${jsversion}"></script>
		<script>
			$(function() {
				$(".personinf_radio label").click(function(){
					$(this).addClass("checked").parent().siblings().find("label").removeClass("checked");
					/* var radio = $(this).find(".rad");
					$.ajax({
						url:'proxyCheckSaveAjax.do',
						data: {
							loanId:${loanPerson.loanId},
							loanPersonId: radio.attr("data_personId"),
							certificateId: radio.attr("data_id"),
							certificateType: radio.attr("data_certificateType"),
							certificateName: radio.attr("data_certificateName"),
							statusId: radio.attr("data_statusId"),
							statusInt: radio.val()
						},
						success: function(result){
							if(!result.isSuccess){
								popWindow(result.message);
				    	   	}
						}
					}); */
				});
				
				$("input[type='radio']").change(function(){
					var btnIdx = $(this).attr("name");
					var btnVal = $(this).val();
					$("input[name='certificateDoList["+btnIdx+"].statusInt']").val(btnVal);
				});
			})
		</script>
	</body>
</html>