<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="include/top.jsp"%>
		<title>贷款进行中</title>
	</head>

	<body>
		<div class="eLoan">
           <div class="person_content">
             <div class="loan_state">
               <div class="person_info  db_f bs">
               		<div class="pi_item1 db"><img src="${fileServerUrl }/app_res/img/elend/personLink.png" class="boximg" /></div>
               		<div class="pi_item2 bf1">
               			<h5>${loanChannelDo.loanUserDo.name }
               			</h5>
               			 <p>${loanChannelDo.loanUserDo.mobileShort }</p>
                         <p>${loanChannelDo.loanUserDo.idNoShort }</p>
               		</div>	
               </div>
               <%--
               <p class="p1 leve">用户等级：<span>Lv${loanChannelDo.loanUserDo.level }</span><a  href="<c:url value='/app/elend/levelInfo.do'/>"  >用户等级</a></p>
                --%>              		
               <div class="db_f person2">
               		<a class="bf1" style="width:20%;" href="###"><em>${personTotalMap.myLoan}次</em>贷款</a>
               		<a class="bf1" style="width:20%;"  href="###"><em>${personTotalMap.lateCount}次</em>逾期</a>
               		<a class="bf1" href="###"><em>${personTotalMap.refLoanList}次</em>推荐贷款</a>
               		<%--<a class="bf1" style="width:35%;"  href="###"><em>0元</em>推荐理财额</a> --%>
               </div>
             </div>
             <p class="state_title">申请状态</p>
             <div class="loaninfo" style="margin-top:0;">
               <div class="state_list">
                 <div class="state_step">
                   <div class="clearfix">
                     <span class="tag"><img src="${fileServerUrl }/app_res/img/elend/pass.png" class="boximg" /></span>
                     <p class="tag_txt pass_txt">
                       <span><fmt:formatDate value="${loanDo.createTime}" pattern="yyyy-MM-dd"/></span><br />
                       		申请提交成功
                       		<a href="<c:url value='/app/elend/showUploadList.do?loanId=${loanDo.loanId}'/>" class="apply person_loan">查看资料</a>
                     </p>
                   </div>
                   <c:choose>
	                   <c:when test="${loanDo.loanStatus == 'AUDITED' || loanDo.loanStatus == 'TREATY' || loanDo.loanStatus == 'SUBJECTED'}">
	                   <div class="clearfix">
	                     <span class="tag"><img src="${fileServerUrl }/app_res/img/elend/pass.png" class="boximg" /></span>
	                     <p class="tag_txt pass_txt">
	                       	审核通过
	                     </p>
	                   </div>
	                   </c:when>
	                   <c:when test="${loanDo.loanStatus == 'NOPASS'}">
	                   <div class="clearfix">
	                     <span class="tag "><img src="${fileServerUrl }/app_res/img/elend/untreated.png" class="boximg" /></span>
	                     <p class="tag_txt untreated_txt">
	                       	审核失败
	                     </p>
	                   </div>
	                   </c:when>
	                   <c:otherwise>
	                   <div class="clearfix">
	                     <span class="tag processing"><img src="${fileServerUrl }/app_res/img/elend/processing.png" class="boximg" /></span>
	                     <p class="tag_txt processin_txt">
	                       	审核中
	                     </p>
	                   </div>
	                   </c:otherwise>
                   </c:choose>
                   
                   <c:choose>
	                   <c:when test="${loanDo.loanStatus == 'SUBJECTED'}">
	                   <div class="clearfix">
	                     <span class="tag"><img src="${fileServerUrl }/app_res/img/elend/pass.png" class="boximg" /></span>
	                     <p class="tag_txt pass_txt">
	                       	放款成功
	                     </p>
	                   </div>
	                   </c:when>
	                   <c:when test="${loanDo.loanStatus == 'AUDITED' || loanDo.loanStatus == 'TREATY'}">
	                   <div class="clearfix">
	                     <span class="tag processing"><img src="${fileServerUrl }/app_res/img/elend/processing.png" class="boximg" /></span>
	                     <p class="tag_txt processin_txt">
	                       	放款中
	                     </p>
	                   </div>
	                   </c:when>
                   </c:choose>
                 </div>
               </div>
             </div>
             <p class="state_title">还款计划表</p>
             
             <c:if test="${loanDo.loanStatus =='PENDING' || loanDo.loanStatus == 'AUDITED' || loanDo.loanStatus == 'TREATY'  || loanDo.loanStatus == 'SUBJECTED'  || loanDo.loanStatus == 'REPAYING'}">
             <div class="loaninfo" style="margin-top:0;">
               <div class="state_list_table">
                 <table>
                   <tr>
                     <th>期数</th>
                     <th>还款日期</th>
                     <th>月还款额</th>
                   </tr>
                   <c:set var="index" value="1"/>
                   <c:forEach items="${settDetailDoList}" var="settDetailDo" >
                   		<tr>
		                     <td>${index}</td>
		                     <td><fmt:formatDate value="${settDetailDo.repayDate}" pattern="yyyy-MM-dd"/></td>
		                     <td><fmt:formatNumber value="${settDetailDo.principal+settDetailDo.interest}" pattern="##0.00"/></td>
		                   </tr>
		                   <c:set var="index" value="${index + 1}"/>
                   </c:forEach>
                 </table>
               </div>
             </div>
             </c:if>
             
             <p class="state_title">历史贷款情况</p>
             <c:if test="${loanDoListSize gt 1}">
             <div class="loaninfo" style="margin-top:0; margin-bottom:30px; padding-bottom:40px;">
               <ul class="loan_history">
               	 <c:forEach items="${loanDoList}" var="loanDo" begin="1">
               	 	<li class="clearfix">
	                   <p>
	                   	<span><fmt:formatDate value="${loanDo.createTime}" pattern="yyyy-MM-dd"/></span>
	                   	<c:set var="loanAmount" value="${loanDo.applyAmount}"/>
	                   	<c:if test="${loanDo.loanAmount != null}"><c:set var="loanAmount" value="${loanDo.loanAmount}"/></c:if>
	                   	<span>贷款<fmt:formatNumber value="${loanAmount}" pattern="##0.00"/>元</span>
	                   </p>
	                   <span class="history_state">
	                   	<c:choose>
	                   		<c:when test="${loanDo.loanStatus == 'SUBJECTED'}">成功</c:when>
	                   		<c:when test="${loanDo.loanStatus == 'NOPASS'}">失败</c:when>
	                   		<c:otherwise>审核中</c:otherwise>
	                   	</c:choose>
	                   </span>
	                 </li>
               	 </c:forEach>
               </ul>
             </div>
             </c:if>
           </div>
		</div>
	<%@ include file="include/foot.jsp"%>
	<script type="text/javascript">
	  $(document).ready(function(){
		  var tr=$('.state_list_table tr')
		  var len=tr.length;
		  for(var i=0; i<len; i++){
			if(i%2==0){
			  tr.eq(i).find('td').addClass('colourBack');
			}  
		  }
		  
	  })
	  $("nav ul li").eq(1).addClass("current");
    </script>	
	</body>

</html>