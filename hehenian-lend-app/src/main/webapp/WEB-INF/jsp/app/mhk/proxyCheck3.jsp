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
			    	<div class=" p1 loan_person" style="padding-top:70px;">
			    	  <h3 class="loan_person_name">${loanPerson.realName}</h3>
			    	  <span class="loan_person_phone">${loanPerson.idNo }</span>
			    	</div>
			    </div>
			</header>
		<form name="frm" action="<c:url value='/app/mhk/proxyCheckSave.do' />" method="post">
			<input type="hidden"  name="loanPerson.loanId" value="${loanPerson.loanId}"/>
			<input type="hidden"  name="pageId" value="${pageId}"/>
			<input type="hidden"  name="tableCode" value="CertificateDo"/>
			<section>
				<article>
					<h3 class="personinf_tip tip_1"><span>贷款人资产信息</span></h3>
					<div  class="p1  imglist">
					<c:if test="${loanPerson.hasHouse == 'T' }">
							<p style="width:100%;">建筑面积：${propertyDo.coveredAreaString }</p>
							<p style="width:100%;">购买方式：${propertyDo.purchaseWay == 'NOMORTGAGE' ? "一次性" : "按揭"}</p>
							<p style="width:100%;">购买日期：${propertyDo.purchaseDateString}</p>
							<p style="width:100%;">住宅地址： ${propertyDo.houseAddress }</p>
							<p style="width:100%;">是否抵押他人： ${propertyDo.houseDy == 0? "否":"是"}</p>
							<ul>
					</c:if>
						<c:forEach var="cdListOut" items="${cdListOut}" varStatus="status">
						<c:if test ="${ cdListOut.certificateTypeHead == 'HOUSE'}">
							<%-- <p style="width:100%;">资产介绍：</p>
							<p class="p1">${propertyDo.remark }</p> --%>
							<p style="width:100%;">是否有车： ${propertyDo.carDy == 0? "否":"是"}</p>
							<ul>
						</c:if>
					
						<c:if test ="${ cdListOut.certificateTypeHead == 'DRIVERCARD'}">
							</div>
							</ul>
							<h3 class="personinf_tip tip_1"><span>行驶证</span></h3>
							<div  class="p1  imglist">
							<ul>
						</c:if>
						<c:if test ="${ cdListOut.certificateTypeHead == 'INCOME'}">
							</div>
							</ul>
							<h3 class="personinf_tip tip_1"><span>银行流水</span></h3>
							<div  class="p1  imglist">
							<ul>
						</c:if>
							<li>
								<c:if test ="${ cdListOut.certificateTypeHead != 'HOUSE'}"><img src="<c:url value='/app/mhk/showImg.do?imgPath=${cdListOut.filePath }'/>" alt="" ><span class="showimg bs">查看大图</span></c:if>
								<div class="db_f p1 personinf_radio">
									<div class="bf1">
										<c:if test ='${ cdListOut.statusInt == 0}'><label class="checked pass"> </c:if>
										<c:if test ='${ cdListOut.statusInt != 0}'><label class="pass"></c:if>
											<input type="radio" class="rad " name="${status.index}"  value="0" />
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
					//alert($(this).val);
				})	
				
				
				$("input[type='radio']").change(function(){
					var btnIdx = $(this).attr("name");
					var btnVal = $(this).val();
					$("input[name='certificateDoList["+btnIdx+"].statusInt']").val(btnVal);
				});
			});
		</script>
	</body>
</html>