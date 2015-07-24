<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="include/top.jsp"%>
	<title>贷款金额</title>
</head>

<body style="padding-bottom:60px;">
	<form id="upd_loan" action="applyfor" method="post" >
		<input type="hidden" name="loanId" value="${loanId}"/>
		<div class="eLoan">  
			<dl class="loaninfo ">
				<dt>
					<label>贷款类型：</label>
					<select  class="sel"  id="loan_type" name="loanType">
						<option value="0">选择类型</option>
						<option value="1" <c:if test="${loanDo.loanType==1}">selected="selected"</c:if> >信用贷款</option>
						<option value="2" <c:if test="${loanDo.loanType==2}">selected="selected"</c:if> >抵押贷</option>
						<%-- <option value="3" <c:if test="${loanDo.loanType==3}">selected="selected"</c:if> >担保贷</option> --%>
					</select>
				</dt>
				<dd></dd>
				<p class="line"></p>
				<dt>
					<label>贷款用途：</label>
					<!-- <input type="text"   name="loanUsage"   placeholder="装修/买车/进修/买房等"  class="txtinput not_null"/> -->
					<select class="sel"  id="loan_Usage" name="loanUsage">
						<option value="经营周转"  <c:if test="${loanDo.loanUsage=='经营周转'}">selected="selected"</c:if> >经营周转</option>
						<option value="个人消费"  <c:if test="${loanDo.loanUsage=='个人消费'}">selected="selected"</c:if> >个人消费</option>
						<option value="购房" <c:if test="${loanDo.loanUsage=='购房'}">selected="selected"</c:if> >购房</option>
						<option value="购车" <c:if test="${loanDo.loanUsage=='购车'}">selected="selected"</c:if> >购车</option>
						<option value="其他" <c:if test="${loanDo.loanUsage=='其他'}">selected="selected"</c:if> >其他</option>
					</select>
					
				</dt>
				<dd></dd>
				<p class="line"></p>
				<dt>
					<label>借款金额：</label>
					<%--           <c:set var="applyAmount"  value="输入100整数倍金额"/> --%>
					<c:if test="${loanDo.applyAmount != null}">
					<c:set var="applyAmount" value="${loanDo.applyAmountString}"/>
				</c:if>
				<input type="text"  min="1"  name="applyAmount" value="${applyAmount}"  placeholder="输入100整数倍金额"  class="txtinput not_null money"/>
				<span>元</span>
			</dt>
			<dd>
				<c:if test="${resultCode eq 'money'}">
				${message}
				</c:if>
			</dd>
			<p class="line"></p>
			<dt>
				<label>借款期限：</label>
				<select class="sel"  id="loan_Date" name="loanPeriod">
					<option value="0"  >选择期限</option>
					<option value="1" <c:if test="${loanDo.loanPeriod==1}">selected="selected"</c:if> >1个月</option>
					<option value="2" <c:if test="${loanDo.loanPeriod==2}">selected="selected"</c:if> >2个月</option>
					<option value="3" <c:if test="${loanDo.loanPeriod==3}">selected="selected"</c:if> >3个月</option>
					<option value="6" <c:if test="${loanDo.loanPeriod==6}">selected="selected"</c:if> >6个月</option>
					<option value="9" <c:if test="${loanDo.loanPeriod==9}">selected="selected"</c:if> >9个月</option>
					<option value="12" <c:if test="${loanDo.loanPeriod==12}">selected="selected"</c:if> >12个月</option>
					<option value="18" <c:if test="${loanDo.loanPeriod==18}">selected="selected"</c:if> >18个月</option>
					<option value="24" <c:if test="${loanDo.loanPeriod==24}">selected="selected"</c:if> >24个月</option>
				</select>
			</dt>
			<dd></dd>
			<p class="line"></p>
			<!-- <dt>
				<label style="width:74px;">还款方式：</label>
				<span class="sel bs"  style="display:inline-block;  -webkit-appearance: none;"  id="payStyle">等本等息</span>
			</dt>
			<dd></dd> -->
			<dt>
				<label>有无房产:</label>				
				<div class="switch bs" <c:if test="${loanDo.loanPersonDo.hasHouse!='T'}">style="background: rgb(204, 204, 204);"</c:if>>

				<!-- <div class="switch bs" ${loanDo.loanPersonDo.hasHouse=='T' ? 'style="background: rgb(231, 126, 35);"' : 'style="background: rgb(204, 204, 204);"'}> -->

				
				    <input type="checkbox" id='hasCheckbox'<c:if test="${loanDo.loanPersonDo.hasHouse=='T'}">checked="true" </c:if>  />
				    <label></label>
				</div>
				<input type="hidden" name="hasHouse" id="hasHouse" value="${loanDo.loanPersonDo.hasHouse=='T'?'T':'F'}" />
			</dt>
			<dd></dd>
			<p class="line"></p>
				<dt class="radioBox">
					<input type="checkbox" id="chkAgreement1" <c:if test="${loanId!=null&&loanId>0}"> checked="checked"</c:if> style="width: 52px; height: 15px;" /> 我已阅读并同意
					<a href="${fileServerUrl }/app_res/word/concat.html" target="_black">《合和年在线借款协议》</a>
				</dt>
				<dd style="padding-left: 25px;"></dd>
			<p class="line"></p>
				<dt class="radioBox">
					<input type="checkbox" id="chkAgreement2"  <c:if test="${loanId!=null&&loanId>0}"> checked="checked"</c:if> style="width: 52px; height: 15px;" /> 我已阅读并同意
					<a href="${fileServerUrl }/app_res/word/consult.html" target="_black">《借款咨询服务协议》</a>
				</dt>
			<dd style="padding-left: 25px;"></dd>
		</dl>
		<div class="submit_btn db_f">
			<div class="bf1"><a href="javascript:void(0);" class="apply look">查看每月还款金额</a></div>
			<div class="bf1"><a href="javascript:void(0);" id="apply" class="apply">下一步</a></div>
		</div>
	</div>

	<div class="opacity" style="display:none">
		<div class="alertcontairn" style="padding:0 0 10px; background:#fff;">
			<h4 class="alerthead">还款计划：</h4>
			<span class="closeOpacity closeCcl"><img src="${fileServerUrl }/app_res/img/elend/alertClose.png" class="boximg" /></span>
			<div class="textinfo">
				<div class="loantable"  >
					<table>
						<thead>
							<tr>
								<th>第x个月</th>
								<th>月还款金额</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
				<!-- <p class="pay_tips">贷款审核通过后，将代收150元征信费</p> -->
			</div>

		</div>
	</div>
</form>
        <%@ include file="include/foot.jsp"%>
        <script type="text/javascript">
        	var loanType = 1;
        	var money =0;

        	var annualRateArr= {'101':1.6,'102':2.0,'103':1.95};

        	$("#loan_type").change(function() {
        		loanType = $("#loan_type").val()*1;
        		if(loanType==0){
			  		$(this).parents().next("dd").text("请选择贷款类型");
			  		return false;
			  	}
        	}) 

        	$(".switch").click(function(){
        		var ck=$(this).find('input');
        		var hasHouse = $("#hasHouse");
        		if(ck.attr("checked")){
        			ck.removeAttr("checked");
        			hasHouse.val("F");
        			$(this).css('background', '#ccc');
        		} else {
        			ck.attr('checked', true);
        			hasHouse.val("T");
        			$(this).css('background', '#e77e23');
        		}
        	})

        	$('.money').focus(function(){
				 $(this).parent().next("dd").text("");
			 }).blur(function() {
        		var that = $(this);
        		money =that.val()*1;
				  if(money.toString().indexOf('') > 0){
				 	$(this).parents().next("dd").text("只能输入整数金额");
				 	return false;
				 } else if(money % 100) {
				 	$(this).parents().next("dd").text("只能输入100的整数倍金额");
				 	return false;
				 }
			})

        	var loanDate = $("#loan_Date").val();
        	$("#loan_Date").change(function() {
        		loanDate = $(this).val();
        		 if(loanDate<1){
				 	$(this).parent().next("dd").text('请选择借款期限');
				 	return false;
				 }
        	})
        	var loanUsage = "";
        	$("#loan_Usage").change(function() {
        		loanUsage = $(this).val();
        	})

        	$(".look").click(function(){
        	if(loanType==0){
		  		$("#loan_type").parents().next("dd").text("请选择贷款类型");
		  		return false;
		  	} 
			
        	money = $('.money').val()*1;
	  		  if(money.toString().indexOf('') > 0){
			 	$(".money").parents().next("dd").text("只能输入整数金额");
			 	return false;
			 } else if(money % 100) {
			 	$(".money").parents().next("dd").text("只能输入100的整数倍金额");
			 	return false;
			 }		
			 if(loanDate<1){
			 	$("#loan_Date").parent().next("dd").text('请选择借款期限');
			 	return false;
			 }
			 getLoanType();
        	})

        	function getLoanType() {
		  	if(loanType==0){
		  		return false;
		  	} 

		  	 if(money.toString().indexOf('') > 0){
			 	return false;
			 } else if(money % 100) {
			 	return false;
			 }	

				 if(loanDate<1){
				 	return false;
				 }
				 var hasHouse = $("#hasHouse").val();
				 $.get('<c:url value="calSettDetail.do"/>',{loanAmount: money,loanPeriod: loanDate,hasHouse:hasHouse}, function (result) {
				 	if(!result.success){
				 		if(result.resultCode=='money'){
				 			$(".money").parents().next("dd").text(result.errorMessage);
				 		}else{
				 			alert(result.errorMessage);
				 		}

				 		return ;
				 	}					
				 	var data =  result.model[0]; 
				 	var str='';
				 	for(var i in data) {
				 		str+='<tr><td>'+(i * 1+1) +'</td><td>'+ data[i] +'</td></tr>';
				 	}

				 	$(".loantable tbody").html(str);
				 	$('.opacity').show();
				 });
        	}

        	$("#apply").click(function(){
        		var a = 0 ;
        		var loanTypeS= $("#loan_type").val()*1;
        		var hasHouseS= $("#hasHouse").val();
        		if(loanTypeS==2 ){
        			if(hasHouseS=='F'){
        				$("#hasCheckbox").parents().next("dd").text("抵押贷款必须要有房产");
        		  		a++;
        			}else{
        				$("#hasCheckbox").parents().next("dd").text("");
        			}
        		}else{
    				$("#hasCheckbox").parents().next("dd").text("");
    			}
        		if(loanTypeS==0){
    		  		$("#loan_type").parents().next("dd").text("请选择贷款类型");
    		  		a++;
    		  	} else{
    		  		$("#loan_type").parents().next("dd").text("");
    		  	}

    	  		  if(money.toString().indexOf('') > 0){
    			 	$(".money").parents().next("dd").text("只能输入整数金额");
    			 	a++;
    			 } else if(money % 100) {
    			 	$(".money").parents().next("dd").text("只能输入100的整数倍金额");
    			 	a++;
    			 }		else{
    				 $(".money").parents().next("dd").text("");
    			 }
    			 if(loanDate<1){
    			 	$("#loan_Date").parent().next("dd").text('请选择借款期限');
    			 	a++;
    			 }else{
    				 $("#loan_Date").parent().next("dd").text('');
    			 }
    			 if(!$("#chkAgreement1").attr("checked")){
     	  			$("#chkAgreement1").parents().next("dd").text("请先阅读并同意[合和年在线借款协议]");
     	  			a++;
     			}else{
     				$("#chkAgreement1").parents().next("dd").text("");
     			}
    			 if(!$("#chkAgreement2").attr("checked")){
     	  			$("#chkAgreement2").parents().next("dd").text("请先阅读并同意[借款咨询服务协议]");
     	  			a++;
     			}else{
     				$("#chkAgreement2").parents().next("dd").text("");
     			}
    			if(a>0){
    				return false;
    			}else{
    				$("#upd_loan").submit();
    			}
        		
        	})


		$("select").change(function(){
			$(this).parent().next("dd").text("");
			})

		  //关闭弹出框
		  $('.closeOpacity').click(function(){
		  	$('.opacity').hide();	
		  })
		</script>
		
	</body>

	</html>