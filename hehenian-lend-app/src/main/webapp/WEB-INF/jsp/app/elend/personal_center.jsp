<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<title>贷款进行中</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="MobileOptimized" content="320" />
		<link href="${fileServerUrl }/app_res/css/elend/eLoan.css?v=${jsversion}" rel="stylesheet" type="text/css">
	</head>
	<body style="padding-bottom: 60px">
		<header class="center_head">
			<div class="head-inf">
			<a href="<c:url value='/app/elend/tongzhi'/>" class="tongzhi"><c:if test="${tzNum >0}"><em>${tzNum} </em></c:if></a>
				<h2>${loanChannelDo.loanUserDo.name }</h2>
				<p class="db_f">
					<span class="bf1">${loanChannelDo.loanUserDo.mobileShort }</span>
					<span class="bf1">${loanChannelDo.loanUserDo.idNoShort }</span>
				</p>
			</div>
			<%-- <div class="head-lend db_f">
               		<span><em>${personTotalMap.myLoan}次</em>贷款</span>
               		<span><em>${personTotalMap.lateCount}次</em>逾期</span>
               		<span><em>${personTotalMap.refLoanList}次</em>推荐贷款</span>
            </div> --%>
            <c:if test="${loanDo.loanStatus == 'PENDING' || loanDo.loanStatus == 'PROCESSING' || loanDo.loanStatus == 'AUDITED'||loanDo.loanStatus == 'TREATY'||loanDo.loanStatus == 'SUBJECTED' || loanDo.loanStatus == 'REPAYING'}">
				<div class="head-lend db_f">
					<span><em>贷款金额(元)</em>${loanDo.applyAmountString}</span>
					<span><em>还款期限(元)</em>${loanDo.loanPeriod}</span>
					<span><em>月还款额(元)</em><fmt:formatNumber value="${settDetailDo.principal+settDetailDo.interest}" pattern="##0.00"/></span>
				</div>
			</c:if>
		</header>
	
		<article>
			<section>
				<c:choose>
               		<c:when test="${loanDo.loanStatus == 'PENDING' && loanDo.processCurrentStep=='TO_EDIT'}">
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>申请提交成功</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl22">1</span>
							<span class="bf1 pm ">2</span>
							<span class="bf1 pm ">3</span>
							<span class="bf1 pm ">4</span>
							<span class="bf1 pr ">√</span>
						</div>
						</section>
						<section>
							<div class="p1">
								<a href="<c:url value='/app/elend/showUploadList.do?loanId=${loanDo.loanId}'/>" class="apply">查看我提交的资料</a>
							</div>
						</section>
               		</c:when>
               		<c:when test="${ (loanDo.loanStatus == 'PENDING' && loanDo.processCurrentStep=='CALL_COLOR_HOUSE_CHECK') ||( loanDo.loanStatus == 'PROCESSING' && loanDo.processCurrentStep=='MENDSTEP1')}">
<%--                		<c:when test="${loanDo.loanStatus == 'PROCESSING' && (loanDo.processCurrentStep=='CALL_COLOR_HOUSE_CHECK' || loanDo.processCurrentStep=='MENDSTEP1')}"> --%>
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>一审通过</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl2">1</span>
							<span class="bf1 pm pm22">2</span>
							<span class="bf1 pm">3</span>
							<span class="bf1 pm">4</span>
							<span class="bf1 pr">√</span>
						</div>
						</section>
						<section>
							<div class="p1 db_f">
								<div class="p1 bf1"><a href="<c:url value='/app/elend/showUploadList.do?loanId=${loanDo.loanId}'/>" class="apply">查看我提交的资料</a></div>
								<div class="p1 bf1"><a href="<c:url value='/app/elend/uploadAgreement.do?loanId=${loanDo.loanId}'/>" class="apply">上传协议</a></div>
							</div>
						</section>
               		</c:when>
               		<c:when test="${ (loanDo.loanStatus == 'PROCESSING' && loanDo.processCurrentStep=='PROXY_CHECK') }">
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>一审通过</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl2">1</span>
							<span class="bf1 pm pm22">2</span>
							<span class="bf1 pm">3</span>
							<span class="bf1 pm">4</span>
							<span class="bf1 pr">√</span>
						</div>
						</section>
						<section>
							<div class="p1 db_f">
								<div class="p1 bf1"><a href="<c:url value='/app/elend/showUploadList.do?loanId=${loanDo.loanId}'/>" class="apply">查看我提交的资料</a></div>
							</div>
						</section>
               		</c:when>
               		<c:when test="${loanDo.loanStatus == 'NOPASS' &&  (loanDo.processCurrentStep=='CALL_COLOR_HOUSE_CHECK' || loanDo.processCurrentStep=='MENDSTEP1')}">
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>一审未通过</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl3">1</span>
							<span class="bf1 pm pm3">2</span>
							<span class="bf1 pm">3</span>
							<span class="bf1 pm">4</span>
							<span class="bf1 pr">√</span>
						</div>
						</section>
						<section>
							<div class="p1 db_f">
								<div class="p1 bf1"><a href="<c:url value='/app/elend/showUploadList.do?loanId=${loanDo.loanId}'/>" class="apply">查看我提交的资料</a></div>
								<%-- <div class="p1 bf1"><a href="<c:url value=''/>" class="apply">查看原因</a></div> --%>
							</div>
						</section>
               		</c:when>
               		<c:when test="${loanDo.loanStatus == 'PROCESSING' &&  loanDo.processCurrentStep=='INPUT_CREDIT_REPORT' }">
               			<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>小贷审批</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl2">1</span>
							<span class="bf1 pm pm2">2</span>
							<span class="bf1 pm pm22">3</span>
							<span class="bf1 pm">4</span>
							<span class="bf1 pr">√</span>
						</div>
						</section>
               		</c:when>
               		<c:when test="${loanDo.loanStatus == 'AUDITED' }">
<%--                		<c:when test="${loanDo.loanStatus == 'PROCESSING' && (loanDo.processCurrentStep=='PROXY_CHECK' || loanDo.processCurrentStep=='MENDSTEP2' || loanDo.processCurrentStep=='INPUT_CREDIT_REPORT')}"> --%>
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em><c:if test="${loanDo.processCurrentStep!='XIAODAI_AUDIT'}">二审通过</c:if><c:if test="${loanDo.processCurrentStep=='XIAODAI_AUDIT'}">小贷审批通过</c:if></h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl2">1</span>
							<span class="bf1 pm pm2">2</span>
							<span class="bf1 pm pm22">3</span>
							<span class="bf1 pm">4</span>
							<span class="bf1 pr">√</span>
						</div>
						</section>
						<section>
							<div>
								<div class="p1 db_f">
									<div class="bf1"><input type="checkbox"  id="chkAgreement1" >同意<a href="${fileServerUrl }/app_res/word/consult.html">《合和年在线借款协议》</a></div>
									<div class="bf1"><input type="checkbox"  id="chkAgreement2" >同意<a href="${fileServerUrl }/app_res/word/concat.html">《借款咨询服务协议》</a></div>
								</div>
								<p style="padding:0 10px;">请仔细阅读协议并勾选</p>
								<p class="p1">尊敬的e贷款用户${loanChannelDo.loanUserDo.name }您好，您本次申请贷款审批额度为${loanDo.applyAmounttemp }元，还款期限${loanDo.loanPeriod }个月，请您确定是否同意贷款。</p>
								<div class="p1 db_f">
									<div class="p1 bf1"><a href="<c:url value='/app/elend/updataLoanStatus.do?loanId=${loanDo.loanId}&status=0'/>" class="apply">不同意贷款</a></div>
									<div class="p1 bf1"><a href="javascript:void(0);"  onclick="javascript:tosubmit();" id="apply" class="apply">同意贷款</a></div>
								</div>
							</div>	
						</section>
               		</c:when>
               		<c:when test="${loanDo.loanStatus == 'NOPASS' && (loanDo.processCurrentStep=='PROXY_CHECK' || loanDo.processCurrentStep=='MENDSTEP2' || loanDo.processCurrentStep=='INPUT_CREDIT_REPORT')}">
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>二审未通过</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl3">1</span>
							<span class="bf1 pm pm32">2</span>
							<span class="bf1 pm pm3">3</span>
							<span class="bf1 pm">4</span>
							<span class="bf1 pr">√</span>
						</div>
						</section>
						<section>
							<div class="p1 db_f">
								<div class="p1 bf1"><a href="<c:url value='/app/elend/showUploadList.do?loanId=${loanDo.loanId}'/>" class="apply">查看我提交的资料</a></div>
								<%-- <div class="p1 bf1"><a href="<c:url value=''/>" class="apply">查看原因</a></div> --%>
							</div>
						</section>
               		</c:when>
               		<c:when test="${ loanDo.loanStatus == 'TREATY'||loanDo.loanStatus == 'SUBJECTED'}">
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>放款中</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl2">1</span>
							<span class="bf1 pm pm2">2</span>
							<span class="bf1 pm pm2">3</span>
							<span class="bf1 pm pm22">4</span>
							<span class="bf1 pr">√</span>
						</div>
						</section>
               		</c:when>
               		<c:when test="${loanDo.loanStatus == 'REPAYING'}">
               		<h2 class="progess-tip p1"><em><fmt:formatDate value="${loanDo.updateTime}" pattern="yyyy-MM-dd"/></em>还款中</h2>
               			<div class="p1 db_f progess">
							<span class="bf1 pl pl2">1</span>
							<span class="bf1 pm pm2">2</span>
							<span class="bf1 pm pm2">3</span>
							<span class="bf1 pm pm2">4</span>
							<span class="bf1 pr pr2">√</span>
						</div>
						</section>
               		</c:when>
               		<c:otherwise>
					</c:otherwise>
				</c:choose>
			<section>
				<ul class="center-list">
					<li><span class="hj">还款计划表</span><em></em>
						<table class="r_table h_table">
							<thead>
								<tr>
									<th>期数</th>
									<c:if test="${ loanDo.loanStatus == 'AUDITED'||loanDo.loanStatus == 'TREATY'||loanDo.loanStatus == 'SUBJECTED' || loanDo.loanStatus == 'REPAYING'}">
									<th>还款日期</th> 
									</c:if>
									<th>月还款额(元)</th>
								</tr>
							</thead>
							<tbody>
							
								<c:set var="index" value="1"/>
								<c:forEach items="${settDetailDoList}" var="settDetailDo" >
				                 	<tr>
					                     <td>${index}</td>
					                     <c:if test="${loanDo.loanStatus == 'AUDITED'||loanDo.loanStatus == 'TREATY'||loanDo.loanStatus == 'SUBJECTED' || loanDo.loanStatus == 'REPAYING'}">
					                     	<td><fmt:formatDate value="${settDetailDo.repayDate}" pattern="yyyy-MM-dd"/></td>
					                     </c:if>
					                     <td><fmt:formatNumber value="${settDetailDo.principal+settDetailDo.interest}" pattern="##0.00"/></td>
						            </tr>
						            <c:set var="index" value="${index + 1}"/>
				                </c:forEach>
							</tbody>
						</table>
					</li>
					<li>
						<span class="ld">历史贷款情况</span><em></em>
						<c:if test="${loanDoListSize gt 1}">
							<table class="r_table h_table">
								<thead>
									<tr>
										<th>申请日期</th>
										<th>贷款金额(元)</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${loanDoList}" var="loanDo" begin="1">
									<tr>
										<td><fmt:formatDate value="${loanDo.createTime}" pattern="yyyy-MM-dd"/></td>
										<c:set var="loanAmount" value="${loanDo.applyAmount}"/>
	                   					<c:if test="${loanDo.loanAmount != null}"><c:set var="loanAmount" value="${loanDo.loanAmount}"/></c:if>
										<td><fmt:formatNumber value="${loanAmount}" pattern="##0.00"/></td>
										<c:choose>
					                   		<c:when test="${loanDo.loanStatus == 'REPAYED'}"><td class="status0">已还清</td></c:when>
					                   		<c:when test="${loanDo.loanStatus == 'NOPASS'}"><td class="status1">拒绝</td></c:when>
					                   		<c:when test="${loanDo.loanStatus == 'INVALID'}"><td class="status2">失效</td></c:when>
					                   		<c:otherwise><td class="status2">失败</td></c:otherwise>
					                   	</c:choose>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</li>
				</ul>
			</section>
		</article>
		<%@ include file="include/foot.jsp"%>
		
		<script  type="text/javascript">
		$(function(){
			$(".center-list li span").bind('touchend', function(){
				var that = $(this);
				if(that.next(".down").length){
					that.siblings('.h_table').hide().prev("em").removeClass("down");
				} else {
					that.next('em').addClass("down").next('.h_table').show();
					that.parent().siblings().find('.h_table').hide().prev("em").removeClass("down");
				}
			})
			
			$("nav ul li").eq(1).addClass("current");
		})
		
		
		 function tosubmit(){
			 var a = 0;
			 
			if($("#ck1").length || $("#ck2").length) {
				return false;
			}
				
			if(!$("#chkAgreement1").attr("checked")){
				$("#chkAgreement1").parent().parent().after('<p id="ck1" class="uperr">请先阅读并同意[合和年在线借款协议]。</p>');
 	  			a++;
 			}
			 if(!$("#chkAgreement2").attr("checked")){
				 $("#chkAgreement2").parent().parent().after('<p id="ck2" class="uperr">请先阅读并同意[借款咨询服务协议]。</p>');
 	  			a++;
 			}
			 if(a>0){
				 return false;
			 }else{
				 window.location.href = "<c:url value='/app/elend/updataLoanStatus.do?loanId=${loanDo.loanId}&status=1'/>"; 
			 }
		}
		
		$("#chkAgreement1").change(function(){
			if($(this).attr("checked")){
				$("#ck1").remove();
			}
		})
		
		$("#chkAgreement2").change(function(){
			if($(this).attr("checked")){
				$("#ck2").remove();
			}
		})
		
		</script>
	</body>
</html>