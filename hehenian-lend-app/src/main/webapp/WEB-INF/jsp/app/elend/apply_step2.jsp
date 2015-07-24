<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="include/top.jsp"%>
	<title>贷款申请</title>
</head>
	<style>
.err{
  width: 100%;
  display: block;
  color: #f00;
  position: absolute;
  left: 0;
  padding: 0 80px;
  margin-top: -18px;
}
</style>
<body>
    <form action="applyStep2" method="post" >
    <input type="hidden" name="loanId" value="${loanDo.loanPersonDo.loanId}"/>
    <input type="hidden" name="loanPersonId" value="${loanDo.loanPersonDo.loanPersonId}"/>
	<section class="p1">
		<article class="loan_inf">
			<h3 class="inf_tip"><i class="t1"></i>基础信息</h3>
			<div class="form-group db_f">
				<label for="" class="lab">
					学&nbsp;&nbsp;&nbsp;&nbsp; 历：
				</label>
				<select name="education" id="education" class="sel bf1 db">
					<option value="0">--请选择--</option>
					<option value="GRADE_SCHOOL" <c:if test="${loanDo.loanPersonDo.education=='GRADE_SCHOOL'}" >selected=selected</c:if> >初中</option>
					<option value="HIGN_SCHOOL" <c:if test="${loanDo.loanPersonDo.education=='HIGN_SCHOOL'}" >selected=selected</c:if>>高中</option>
					<option value="POLYTECH_SCHOOL" <c:if test="${loanDo.loanPersonDo.education=='POLYTECH_SCHOOL'}" >selected=selected</c:if>>中技</option>
					<option value="VOCATION_SCHOOL" <c:if test="${loanDo.loanPersonDo.education=='VOCATION_SCHOOL'}" >selected=selected</c:if>>中专</option>
					<option value="JUNIOR_COLLEGE" <c:if test="${loanDo.loanPersonDo.education=='JUNIOR_COLLEGE'}" >selected=selected</c:if>>大专</option>
					<option value="BACHELOR" <c:if test="${loanDo.loanPersonDo.education=='BACHELOR'}" >selected=selected</c:if>>本科</option>
					<option value="MASTER" <c:if test="${loanDo.loanPersonDo.education=='MASTER'}" >selected=selected</c:if>>硕士</option>
					<option value="DOCTOR" <c:if test="${loanDo.loanPersonDo.education=='DOCTOR'}" >selected=selected</c:if>>博士/海归</option>
				</select> 
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">
					婚姻状况:
				</label>
				<select class="sel bf1 db" name="marriaged" id="marriaged">
					<option value="0">--请选择--</option>
					<option value="UNMARRIED" <c:if test="${loanDo.loanPersonDo.marriaged=='UNMARRIED'}">selected=selected</c:if>>未婚</option>
					<option value="MARRIED" <c:if test="${loanDo.loanPersonDo.marriaged=='MARRIED'}">selected=selected</c:if>>已婚</option>
					<option value="DIVORCE" <c:if test="${loanDo.loanPersonDo.marriaged=='DIVORCE'}">selected=selected</c:if>>离异</option>
				</select>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">
					工作年限:
				</label>
				<select name="jobDo.jobYear" id="jobYear" class="sel bf1 db">
					<option value="0">--请选择--</option>
					<option value="100" <c:if test="${loanDo.loanPersonDo.jobDo.jobYear==100}">selected=selected</c:if>>0~1年</option>
					<option value="101" <c:if test="${loanDo.loanPersonDo.jobDo.jobYear==101}">selected=selected</c:if>>1~2年</option>
					<option value="102" <c:if test="${loanDo.loanPersonDo.jobDo.jobYear==102}">selected=selected</c:if>>2~3年</option>
					<option value="103" <c:if test="${loanDo.loanPersonDo.jobDo.jobYear==103}">selected=selected</c:if>>3~4年</option>
					<option value="104" <c:if test="${loanDo.loanPersonDo.jobDo.jobYear==104}">selected=selected</c:if>>4~5年</option>
					<option value="105" <c:if test="${loanDo.loanPersonDo.jobDo.jobYear==105}">selected=selected</c:if>>5年以上</option>
				</select>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">
					职业信息:
				</label>
				<select name="jobDo.jobType" id="jobType" class="sel bf1 db">
					<option value="0">--请选择--</option>
					<option value="SALARYMAN" <c:if test="${loanDo.loanPersonDo.jobDo.jobType=='SALARYMAN'}">selected=selected</c:if>>工薪族</option>
					<option value="SELF_EMPLOYED" <c:if test="${loanDo.loanPersonDo.jobDo.jobType=='SELF_EMPLOYED'}">selected=selected</c:if>>自由职业</option>
					<option value="EMPLOYER" <c:if test="${loanDo.loanPersonDo.jobDo.jobType=='EMPLOYER'}">selected=selected</c:if>>私营业主</option>
				</select>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">电子邮箱：</label>
				<input class="txt bf1 db" placeholder="请填写有效的电子邮箱" type="email" id="email" name="email" value="${loanDo.loanPersonDo.email}"/>
			</div>
		</article>
		<c:if test="${loanDo.loanPersonDo.hasHouse == 'T' }">
		<article class="loan_inf">
			<h3 class="inf_tip"><i class="t2"></i>房产信息</h3>
			<div class="form-group db_f">
				<label for="" class="lab">建筑面积：</label>
				<input type="text" id="area"  class="txt bf1 db" name="propertyDo.coveredArea"  value="${loanDo.loanPersonDo.propertyDo.coveredArea}" >
				<span class="mm">㎡</span>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">购买方式：</label>
				<select name="propertyDo.purchaseWay" id="purchaseWay" class="sel bf1 db">
					<option value="0">--请选择--</option>
					<option value="NOMORTGAGE" <c:if test="${loanDo.loanPersonDo.propertyDo.purchaseWay=='NOMORTGAGE'}">selected=selected</c:if>>一次性付款</option> 
					<option value="MORTGAGE" <c:if test="${loanDo.loanPersonDo.propertyDo.purchaseWay=='MORTGAGE'}">selected=selected</c:if>>按揭</option>
				</select>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">购买时间</label>
					<label class="bf1 db_f "> <select class="sel year bf1 br1"
						name="companyInTime_year" id="companyInTime_year">
						<option value="${companyInTime_year }">${companyInTime_year }</option>
					</select> <em>年</em>
					</label> <label class="bf1 db_f"> <select class="sel month bf1 br1"
						name="companyInTime_month" id="companyInTime_month">
						<option value="${companyInTime_month }">${companyInTime_month }</option>
					</select> <em>月</em>
					</label> <label class="bf1 db_f"> <select class="sel day bf1 br1"
						name="companyInTime_day" id="companyInTime_day">
						<option value="${companyInTime_day }">${companyInTime_day }</option>
					</select> <em>日</em>
					</label>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">小区地址：</label>
				<input type="text" id="addr"  name="propertyDo.houseAddress" class="txt bf1 db" value="${loanDo.loanPersonDo.propertyDo.houseAddress}">
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">门牌号码：</label>
				<input type="text" id="doorNum"   class="txt bf1 db" placeholder="例如：8栋108号">
			</div>
			
			<div class="form-group db_f">
				<label for="" class="lab">有无抵押：</label>
				<div class="bf1 db">
					<div class="switch bs"  <c:if test="${loanDo.loanPersonDo.propertyDo.houseDy==0}">style="float: right; margin-top: 3px; background: rgb(204, 204, 204);"</c:if> <c:if test="${loanDo.loanPersonDo.propertyDo.houseDy!=0}">style="float:right;margin-top:3px;"</c:if>>
					    <input type="checkbox"  <c:if test="${loanDo.loanPersonDo.propertyDo.houseDy!=0}">checked="true" </c:if>  />
					    <label></label>
					</div>
					<input type="hidden" name="propertyDo.houseDy" id="houseDy" value="${loanDo.loanPersonDo.propertyDo.houseDy==0?0:1}"/>
				</div>
			</div>
		</article>
		</c:if>
		
		
		<c:if test="${loanDo.loanPersonDo.hasHouse == 'F' }">
		<article class="loan_inf">
			<h2 class="inf_tip"><i class="t2"></i>资产信息</h2>
			<!-- <div class="form-group db_f">
				<textarea name="propertyDo.remark" placeholder="请填写个人资产， 例如：一辆车、车牌号：123456"  id ="remark" rows="4" style="width:100%;display:block;" ></textarea>
			</div> -->
			<div class="form-group db_f">
				<label for="" class="lab">是否有车：</label>
				<div class="bf1 db">
					<div class="switch bs" <c:if test="${loanDo.loanPersonDo.propertyDo.carDy==0}">style="float: right; margin-top: 3px; background: rgb(204, 204, 204);"</c:if> <c:if test="${loanDo.loanPersonDo.propertyDo.carDy!=0}">style="float:right;margin-top:3px;"</c:if>>
					    <input type="checkbox"  <c:if test="${loanDo.loanPersonDo.propertyDo.carDy!=0}">checked="true" </c:if>  />
					    <label></label>
					</div>
				<%-- <div class="bf1 db" id='carDyDiv'>
						<input type="radio" class="rad " id='carDy1' name="carDy" value="0" <c:if test="${loanDo.loanPersonDo.propertyDo.carDy==0}">checked="true" </c:if>/>否
						<input type="radio" class="rad " id='carDy2' name="carDy" value="1" <c:if test="${loanDo.loanPersonDo.propertyDo.carDy==1}">checked="true" </c:if>/>是
						<input type="hidden" id="carDy" name="propertyDo.carDy" value="${loanDo.loanPersonDo.propertyDo.carDy}"/>
				</div> --%>
				<input type="hidden" name="propertyDo.carDy" id="carDy" value="${loanDo.loanPersonDo.propertyDo.carDy==0?0:1}"/>
			</div>
		</article>
		</c:if>
		
		
		
		<article class="loan_inf">
			<h3 class="inf_tip">
				<i class="t3"></i>紧急联系亲属				
			</h3>
			<input type="hidden" name="loanRelationDoList[0].relationType" value="1">
			<input type="hidden" name="loanRelationDoList[0].ralationId" value="${relationFamily.ralationId }">
			<input type="hidden" name="loanRelationDoList[1].relationType" value="1">
			<input type="hidden" name="loanRelationDoList[1].ralationId" value="${relationFamily.ralationId }">
						
			<div class="form-group db_f">
				<label for="" class="lab">联系人姓名：</label>
				<input type="text" placeholder="请填写直系亲属姓名"  id ="xm1" class="txt bf1 db"  name="loanRelationDoList[0].ralationName" value="${loanDo.loanPersonDo.loanRelationDoList[0].ralationName }"/>
				
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">与本人关系：</label>
				<select name="loanRelationDoList[0].relationship" id="relationship1" class="sel bf1 db">
					<option value="99">--请选择--</option>
					<option value="0" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[0].relationship==0 }">selected=selected</c:if> >父母</option>
					<option value="1" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[0].relationship==1 }">selected=selected</c:if> >配偶</option>
					<option value="2" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[0].relationship==2 }">selected=selected</c:if> >子女</option>
					<option value="3" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[0].relationship==3 }">selected=selected</c:if> >兄弟</option>
					<option value="4" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[0].relationship==4 }">selected=selected</c:if> >姐妹</option>
				</select>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">手机号码：</label>
				<input type="text" placeholder="请输入真实手机号码"  class="txt bf1 db" id="mobile1" name="loanRelationDoList[0].mobile"  value="${loanDo.loanPersonDo.loanRelationDoList[0].mobile }"/>
				
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">联系人姓名：</label>
				<input type="text" placeholder="请填写直系亲属姓名"  id ="xm2" class="txt bf1 db"  name="loanRelationDoList[1].ralationName" value="${loanDo.loanPersonDo.loanRelationDoList[1].ralationName }"/>
			</div>
			<div class="form-group db_f">
				<label for="" class="lab">与本人关系：</label>
				<select name="loanRelationDoList[1].relationship" id="relationship2" class="sel bf1 db">
					<option value="99">--请选择--</option>
					<option value="5" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[1].relationship==5 }">selected=selected</c:if> >父母</option>
					<option value="6" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[1].relationship==6 }">selected=selected</c:if> >配偶</option>
					<option value="7" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[1].relationship==7 }">selected=selected</c:if> >子女</option>
					<option value="8" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[1].relationship==8 }">selected=selected</c:if> >兄弟</option>
					<option value="9" <c:if test="${loanDo.loanPersonDo.loanRelationDoList[1].relationship==9 }">selected=selected</c:if> >姐妹</option>
				</select>
			</div> 
			<div class="form-group db_f">
				<label for="" class="lab">手机号码：</label>
				<input type="text" placeholder="请输入真实手机号码"  class="txt bf1 db"  id="mobile2" name="loanRelationDoList[1].mobile"  value="${loanDo.loanPersonDo.loanRelationDoList[1].mobile }"/>
			</div>
		</article>
	</section>
	
	<section class="p1" style="padding: 10px 10px 60px;">
			<a href="javascript:void(0);" id="apply" class="apply"><c:if test="${loanDo.loanType==1 }">下一步</c:if><c:if test="${loanDo.loanType==2 }">提交申请</c:if></a>
	</section>
	</form>
	<%@ include file="include/foot.jsp"%>
<script src="${fileServerUrl }/app_res/js/app.js?v=${jsversion}"></script>
	<script>
	/* $("#remark").val("${loanDo.loanPersonDo.propertyDo.remark}"); */
	<c:if test="${loanDo.loanPersonDo.propertyDo.houseAddress == null}">
		$("#addr").val("${loanChannelDo.loanUserDo.caddress }");
	</c:if>
   	<c:if test="${loanDo.loanPersonDo.propertyDo.houseAddress != null}">
   		var	a ="${loanDo.loanPersonDo.propertyDo.houseAddress }";
	   	var strs= new Array(); //定义一数组 
	   	strs=a.split(","); 
	   	var b ="";
	   	for (i=0;i<strs.length ;i++ ) 
	   	{ 
	   		if(i==strs.length-1){
	   			$("#doorNum").val(strs[i]);
	   		}else{
	   			b=b+strs[i];
	   		}
	   	} 
	   	$("#addr").val(b);
 	</c:if>
	$(function(){
	    var validateEducation = function(){
			var education = $("#education");
			if(education.val()==0){
				education.parent().after('<em class="err bs" style="z-index:0;">请选择学历</em>');
				return false;
			}
			return true;
		}
	    
		var validateMarriaged = function(){
			var $marriaged = $("#marriaged");
			if($marriaged.val()==0){
				$marriaged.parent().after('<em class="err bs" style="z-index:0;">请选择婚姻状况</em>');
				return false;
			}
			return true;
		}
	    
		var validateJobYear = function(){
			var $jobYear = $("#jobYear");
			if($jobYear.val()==0){
				$jobYear.parent().after('<em class="err bs" style="z-index:0;">请选择工作年限</em>');
				return false;
			}
			return true;
		}
	    
		var validateJobType = function(){
			var $jobType = $("#jobType");
			if($jobType.val()==0){
				$jobType.parent().after('<em class="err bs" style="z-index:0;">请选择职业</em>');
				return false;
			}
			return true;
		}
		
		var validateEmail = function(){
			var $email = $("#email");
			var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
			if(!reg.test($email.val())){
				$email.parent().after('<em class="err bs" style="z-index:0;">邮箱格式错误</em>');
				return false;
			}
			return true;
		}
		
		<c:if test="${loanDo.loanPersonDo.hasHouse == 'T' }">
		var validateCoveredArea = function(){
			var $coveredArea = $("#area");
			if($coveredArea.val().length<1){
				$coveredArea.parent().after('<em class="err bs" style="z-index:0;">请填写建筑面积</em>');
				return false;
			}
			return true;
		}
		
		var validatePurchaseWay = function(){
			var $purchaseWay = $("#purchaseWay");
			if($purchaseWay.val()==0){
				$purchaseWay.parent().after('<em class="err bs" style="z-index:0;">请选择房屋购买方式</em>');
				return false;
			}
			return true;
		}
		
		var validateHouseAddress = function(){
			var $houseAddress = $("#addr");
			if($houseAddress.val().length<1){
				$houseAddress.parent().after('<em class="err bs" style="z-index:0;">请填写小区地址</em>');
				return false;
			}
			return true;
		}
		
		var validateDoorNum = function(){
			var $doorNum = $("#doorNum");
			if($doorNum.val().length<1){
				$doorNum.parent().after('<em class="err bs" style="z-index:0;">请填写门牌号</em>');
				return false;
			}
			return true;
		}
		</c:if>
		<c:if test="${loanDo.loanPersonDo.hasHouse == 'F' }">
	    /* var validateRemark = function(){
			var $remark = $("#remark");
			if($remark.val().length<1){
				$remark.parent().after('<em class="err bs" style="z-index:0;">请输入资产信息</em>');
				$("#remark").next().css('margin-top',"62px");
				return false;
			}
			return true;
		} */
		var validateCarDy = function(){
			var $carDy = $("#carDy");
			if($carDy.val().length<1){
				$("#carDyDiv").parent().after('<em class="err bs" style="z-index:0;">请选着是否有车</em>');
				return false;
			}
			return true;
		}
	    </c:if>
		var validateLianxi1 = function(){
			var $xm1 = $("#xm1");
			if($xm1.val().length<1){
				$xm1.parent().after('<em class="err bs" style="z-index:0;">请填写联系人</em>');
				return false;
			}else if (!/^[\u4e00-\u9fa5]+$/.test($xm1.val())) {
				$xm1.parent().after('<em class="err bs" style="z-index:0;">请输入中文</em>');
				return false;
			}  
			return true;
		}	
		
		var validateRelationship1 = function(){
			var $relationship = $("#relationship1");
			if($relationship.val()>10){
				$relationship.parent().after('<em class="err bs" style="z-index:0;">请填选择与本人关系</em>');
				return false;
			}
			return true;
		}
		
		var validateMobile1 = function(){
			var $mobile1 = $("#mobile1");
			if($mobile1.val().length<1){
				$mobile1.parent().after('<em class="err bs" style="z-index:0;">请填写手机号码</em>');
				return false;
			}else if (!/^0{0,1}(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$/.test($mobile1.val())){
				$mobile1.parent().after('<em class="err bs" style="z-index:0;">输入手机号的格式有误</em>');
		         return false;
			}
			return true;
		}
		
		var validateLianxi2 = function(){
			var $xm2 = $("#xm2");
			if($xm2.val().length<1){
				$xm2.parent().after('<em class="err bs" style="z-index:0;">请填写联系人</em>');
				return false;
			}else if (!/^[\u4e00-\u9fa5]+$/.test($xm2.val())) {
				$xm2.parent().after('<em class="err bs" style="z-index:0;">请输入中文</em>');
				return false;
			}   
			return true;
		}	
		
		var validateRelationship2 = function(){
			var $relationship = $("#relationship2");
			if($relationship.val()>10){
				$relationship.parent().after('<em class="err bs" style="z-index:0;">请填选择与本人关系</em>');
				return false;
			}
			return true;
		}
		
		var validateMobile2 = function(){
			var $mobile2 = $("#mobile2");
			if($mobile2.val().length<1){
				$mobile2.parent().after('<em class="err bs" style="z-index:0;">请填写手机号码</em>');
				return false;
			}else if (!/^0{0,1}(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$/.test($mobile2.val())){
				$mobile2.parent().after('<em class="err bs" style="z-index:0;">输入手机号的格式有误</em>');
		         return false;
			}
			return true;
		}
	/* 	
		$("select").focus(function(){
			$(this).parent().next('.err').remove();
		}) */
		
		$(".txt,input,select").focus(function(){
			$(this).parent().next('.err').remove();
		})

		$("#education").blur(validateEducation);
		$("#marriaged").blur(validateMarriaged);
		$("#jobYear").blur(validateJobYear);
		$("#jobType").blur(validateJobType);
		$("#email").blur(validateEmail);
		<c:if test="${loanDo.loanPersonDo.hasHouse == 'T' }">
		 	$("#area").blur(validateCoveredArea);
			$("#purchaseWay").blur(validatePurchaseWay);
			$("#addr").blur(validateHouseAddress);
			$("#doorNum").blur(validateDoorNum);
		</c:if>
		<c:if test="${loanDo.loanPersonDo.hasHouse == 'F' }">
			/* $("#remark").blur(validateRemark); */
			$("#carDy1").blur(validateCarDy);
			$("#carDy2").blur(validateCarDy);
		</c:if>
		$("#xm1").blur(validateLianxi1);
		$("#relationship1").blur(validateRelationship1);
		$("#mobile1").blur(validateMobile1);
		$("#xm2").blur(validateLianxi2);
		$("#relationship2").blur(validateRelationship2);
		$("#mobile2").blur(validateMobile2);
		
		$("#apply").bind('touchstart', function(){
			$(".err").remove();
		 var tempInt = 0 ;
		 if(!validateEducation()) tempInt++;
		 if(!validateEmail()) tempInt++;
		 if(!validateMarriaged()) tempInt++;
		 if(!validateJobYear()) tempInt++;
		 if(!validateJobType()) tempInt++;
		 <c:if test="${loanDo.loanPersonDo.hasHouse == 'T' }">
			 if(!validateCoveredArea()) tempInt++;
			 if(!validatePurchaseWay()) tempInt++;
			 if(!validateHouseAddress()) tempInt++;
			 if(!validateDoorNum()) tempInt++;
		 </c:if>
		 <c:if test="${loanDo.loanPersonDo.hasHouse == 'F' }">
		 	/* if(!validateRemark()) tempInt++; */
		 	if(!validateCarDy()) tempInt++;
		 </c:if>
		 if(!validateLianxi1()) tempInt++;
		 if(!validateRelationship1()) tempInt++;
		 if(!validateMobile1()) tempInt++;
		 if(!validateLianxi2()) tempInt++;
		 if(!validateRelationship2()) tempInt++;
		 if(!validateMobile2()) tempInt++;
		 if(tempInt>0){
			return false;
		 }
		  
		$("#addr").val($("#addr").val()+","+$("#doorNum").val());
			document.forms[0].submit();
		})
		var companyInTime_year = '${companyInTime_year }'; 
		var companyInTime_month = '${companyInTime_month }'; 
		var companyInTime_day = '${companyInTime_day }'; 
		
		setDateSelect(companyInTime_year,companyInTime_month,companyInTime_day,function(_strY, _strM, _strD){
			$("#companyInTime_year").html(_strY);
			$("#companyInTime_month").html(_strM);
			$("#companyInTime_day").html(_strD);
		});
		
		$(".switch").bind('touchend',function(){
      		var ck=$(this).find('input');
      		var housedy;
      		<c:if test="${loanDo.loanPersonDo.hasHouse == 'T' }">
      			housedy = $("#houseDy");
      		</c:if>
      		<c:if test="${loanDo.loanPersonDo.hasHouse == 'F' }">
      			housedy= $("#carDy");
      		</c:if>
      		if(ck.attr("checked")){
      			ck.removeAttr("checked");
      			housedy.val(0);
      			$(this).css('background', '#ccc');
      		} else {
      			ck.attr('checked', true);
      			housedy.val(1);
      			$(this).css('background', '#e77e23');
      		}
      	});
/* 		<c:if test="${loanDo.loanPersonDo.hasHouse == 'F' }">
		$("input[type='radio']").change(function(){
			var btnIdx = $(this).attr("name");
			var btnVal = $(this).val();
			$("input[name='propertyDo.carDy']").val(btnVal);
		});
		</c:if> */
	})
	</script>
</body>
</html>