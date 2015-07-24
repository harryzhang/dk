<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<%@ include file="include/top.jsp"%>
	<style>
.personinf_tip{
	height: 40px;
	background: #fff2df;
	clear: both;
}
.personinf_tip span{
	height: 40px;
	padding: 10px;
	line-height: 20px;
    display: inline-block;
    color: #393939;
    font-weight: normal;
}
.personinf_tip span:after{
  content: '';
  position: absolute;
  border-width: 10px 10px 0px 10px;
  border-style: solid;
  border-color: #fff2df transparent transparent transparent;
  margin: 30px 0 0 -48px;
}

.personinf_radio div{
	padding: 0 10px;
}
.personinf_ {
	background: #fff;
}
.personinf_ p{
	width: 50%;
	line-height: 24px;
}
.personinf_img{
-webkit-display: flex;
display: flex;
padding:10px 1.1vw;
background:#fff;
width:100%;
-webkit-box-sizing: border-box;
box-sizing: border-box;
-webkit-flex-wrap: wrap;
flex-wrap: wrap;
/*justify-content:center;*/
}
.psrimgli{
width:32.6vw;
height:32.6vw;
padding: 1.1vw;
overflow:hidden;
float: left;
}
	</style>
</head>
<body style="padding-bottom:60px;">
	<article>
		<section>
			<h3 class="personinf_tip"><span>个人信息</span></h3>
			<div class="p1 personinf_">
				<div style="font-size:24px;">${loanPersonDo.realName }</div>
				<div class="db_f">
					<p style="width:100px;">${loanPersonDo.mobile }</p>
					<p class="bf1">${loanPersonDo.idNo }</p>
				</div>
				<c:if test="${hasType != '1'}">
				<div class="db_f">
					<p class="bf1">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：
						<c:if test="${loanPersonDo.education=='GRADE_SCHOOL' }">初中</c:if>
						<c:if test="${loanPersonDo.education=='HIGN_SCHOOL' }">高中</c:if>
						<c:if test="${loanPersonDo.education=='POLYTECH_SCHOOL' }">中技</c:if>
						<c:if test="${loanPersonDo.education=='VOCATION_SCHOOL' }">中专</c:if>
						<c:if test="${loanPersonDo.education=='JUNIOR_COLLEGE' }">大专</c:if>
						<c:if test="${loanPersonDo.education=='BACHELOR' }">本科</c:if>
						<c:if test="${loanPersonDo.education=='MASTER' }">硕士</c:if>
						<c:if test="${loanPersonDo.education=='DOCTOR' }">博士/海归</c:if>
					</p>
					<p class="bf1">婚姻状态：
						<c:if test="${loanPersonDo.marriaged=='UNMARRIED' }">未婚</c:if>
						<c:if test="${loanPersonDo.marriaged=='MARRIED' }">已婚</c:if>
						<c:if test="${loanPersonDo.marriaged=='DIVORCE' }">离异</c:if>
					</p>
				</div>
				<div class="db_f">
					<p class="bf1">职业信息：
						<c:if test="${loanPersonDo.jobDo.jobType=='SALARYMAN' }">工薪族 </c:if>
						<c:if test="${loanPersonDo.jobDo.jobType=='SELF_EMPLOYED' }">自由职业 </c:if>
						<c:if test="${loanPersonDo.jobDo.jobType=='EMPLOYER' }">私营业主 </c:if>
					</p>
					<p class="bf1">工作年限：
						<c:if test="${loanPersonDo.jobDo.jobYear ==100}">0~1年</c:if>
						<c:if test="${loanPersonDo.jobDo.jobYear ==101}">1~2年</c:if>
						<c:if test="${loanPersonDo.jobDo.jobYear ==102}">2~3年</c:if>
						<c:if test="${loanPersonDo.jobDo.jobYear ==103}">3~4年</c:if>
						<c:if test="${loanPersonDo.jobDo.jobYear ==104}">4~5年</c:if>
						<c:if test="${loanPersonDo.jobDo.jobYear ==105}">5年以上</c:if>
					</p>
				</div>
				<div class="db_f">
					<p style="width:100%">电子邮箱：${loanPersonDo.email }</p>
				</div>
				</c:if>
			</div>
			
	        <ul class="personinf_img p1 bs">
	        	<c:if test="${ hasType != '2T'}">
	        	<c:forEach var="pageId1" items="${pageId1}" varStatus="status">
					 <li class="bs psrimgli" data-img="<c:url value='/app/mhk/showImg.do?imgPath=${pageId1.filePath }'/>">
						<img src="<c:url value='/app/mhk/showImg.do?imgPath=${pageId1.destFilePath }'/>" alt="" >
					</li>
				</c:forEach>
				<c:forEach var="pageId2" items="${pageId2}" varStatus="status">
					 <li class="bs psrimgli" data-img="<c:url value='/app/mhk/showImg.do?imgPath=${pageId2.filePath }'/>">
						<img src="<c:url value='/app/mhk/showImg.do?imgPath=${pageId2.destFilePath }'/>" alt="" >
					</li>
				</c:forEach>
				</c:if>
	        </ul>
		</section>
		<section>
			<h3 class="personinf_tip"><span>资产信息</span></h3>
			<c:if test="${hasType == '1T' || hasType == '2T'}">
				<div class="p1 personinf_">
					<p style="width:100%;">建筑面积： ${propertyDo.coveredAreaString }</p>
					<p style="width:100%;">购买方式：${propertyDo.purchaseWay == 'NOMORTGAGE' ? "一次性" : "按揭"}</p>
					<p style="width:100%;">购买日期：${propertyDo.purchaseDateString}</p>
					<p style="width:100%;">住宅地址： ${propertyDo.houseAddress }</p>
					<p style="width:100%;">是否抵押他人：  ${propertyDo.houseDy == 0? "否":"是"}</p>
				</div>
				<ul class="personinf_img p1 bs">
			</c:if>
			<c:if test="${hasType == '1F'}">
				<div class="p1 personinf_">
						<p style="width:100%;">是否有车： ${propertyDo.carDy == 0? "否":"是"}</p>
					</div>
					<ul class="personinf_img p1 bs">
			</c:if>
			<c:if test="${ hasType != '2T'}">
			<c:forEach var="pageId3" items="${pageId3}" varStatus="status">
			  	<c:if test ="${ pageId3.certificateTypeHead == 'DRIVERCARD'}">
				  	</ul>
					</section>
					<section>
			  		<h3 class="personinf_tip"><span>行驶证</span></h3>
			  		<ul class="personinf_img p1 bs">
			  	</c:if>
				<c:if test ="${ pageId3.certificateTypeHead == 'INCOME'}">
					</ul>
					</section>
					<section>
					<h3 class="personinf_tip"><span>银行流水</span></h3>
					<ul class="personinf_img p1 bs">
				</c:if>
				<c:if test ="${ pageId3.certificateTypeHead != 'HOUSE'}">
				 <li class="bs psrimgli" data-img="<c:url value='/app/mhk/showImg.do?imgPath=${pageId3.filePath }'/>">
					<img src="<c:url value='/app/mhk/showImg.do?imgPath=${pageId3.destFilePath }'/>" alt="" >
	          	</li>
	          	</c:if>
	          </c:forEach>
	          </c:if>
	        </ul>
		</section>
		
		<c:if test="${hasType != '1'}">
		<section>
			<h3 class="personinf_tip"><span>紧急联系亲属</span></h3>
			<c:forEach var="pageId4" items="${pageId4}" varStatus="status">
				<div class="p1 personinf_">
				<p style="width:100%;">姓名：${pageId4.certificateName}</p>
				<p style="width:100%;">与本人关系：
					<c:if test="${pageId4.relationType== 0 }">父母</c:if>
					<c:if test="${pageId4.relationType== 1 }">配偶</c:if>
					<c:if test="${pageId4.relationType== 2 }">子女</c:if>
					<c:if test="${pageId4.relationType== 3 }">兄弟</c:if>
					<c:if test="${pageId4.relationType== 4 }">姐妹</c:if>
					<c:if test="${pageId4.relationType== 5 }">父母</c:if>
					<c:if test="${pageId4.relationType== 6 }">配偶</c:if>
					<c:if test="${pageId4.relationType== 7 }">子女</c:if>
					<c:if test="${pageId4.relationType== 8 }">兄弟</c:if>
					<c:if test="${pageId4.relationType== 9 }">姐妹</c:if>
				</p> 
				<p style="width:100%;">手机号码：${pageId4.certificateTypeHead}</p>
				</div>
			</c:forEach>
		</section>
		</c:if>
	</article>
		
		
	<div class="p1 upload_tip">
		<div style="padding-bottom:15px;"><h5 style="display: inline-block;color: #4A4848;font-weight: 600; padding-right:5px;">[温馨提示]</h5>如在上传中出现问题，请联系客服。</div>
        <div><i class="upload-icon icon1"></i>400-830-3737</div>
        <div><i class="upload-icon icon2"></i>3092603671(朵朵)</div>
        <div><i class="upload-icon icon3"></i>2106139248</div>
	</div>
	
	<input type="hidden" name="loanId" value="${loanPersonDo.loanId}"/>
	<input type="hidden" name="loanPersonId" value="${loanPersonDo.loanPersonId}"/>
	
	 <%@ include file="include/foot.jsp"%>
<script src="${fileServerUrl }/app_res/js/libs/jquery-2.1.3.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/upload.js?v=${jsversion}"></script>
<script src="${fileServerUrl }/app_res/js/libs/webuploader.js?v=${jsversion}"></script>
<script>
	
	$(function(){
		
		var touchObj = {};
		$(".personinf_img li").bind('touchstart', function(e){
        }).bind('touchmove', function(e){
        	
        }).bind('touchend', function(e){
			touchObj.endDate = new Date();
			if(touchObj.endDate - touchObj.startDate > 200) {
				return false;
			}
        	 var imgurl = $(this).attr('data-img');
             var str = 
             '<section class="dialog" style="overflow: scroll;">'+
             '    <img src="'+ imgurl +'" alt="">'+
             '    <span class="close-dialog"></span>'+
             '</section>';
             $("body").append(str);
        })
        
        $("body").on('touchstart', '.close-dialog', function(){
            $(this).parent().remove();
        })
		
	})
</script>
</body>
</html>