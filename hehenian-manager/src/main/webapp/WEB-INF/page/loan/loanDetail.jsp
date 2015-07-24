
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- --------------------------------订单基础信息--------------------------------- -->
<!DOCTYPE html>
<html>
<head>

</head>

<body >
<div id = "div1">
<div style="width:100%; height:28px; padding:5px 0px; border-bottom:1px solid #ccc;">
<a class="l-btn l_btn_size" onclick="closeDialog()"><i class="fa fa-close"></i>&nbsp;关闭</a>
	
	<c:if test="${loanDo.loanStatus == 'AUDITED' && isAdmin == true }">
		<a class="l-btn l_btn_size" onclick="auditexc(${loanDo.loanId},1)"><i class="fa fa-check-circle-o"></i>&nbsp;上标</a>
	</c:if>
	
	<c:if test="${ canEdit == true }">
		<a class="l-btn l_btn_size" onclick="to_edit(${loanDo.loanId})"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</a>
	</c:if>
	
	<c:if test="${displayBtnAdmin }">
				<a class="l-btn l_btn_size" onclick="auditexc(${loanDo.loanId},2)"><i class="fa fa-ban"></i>&nbsp;拒绝</a>
				<a class="l-btn l_btn_size" onclick="auditexc(${loanDo.loanId},3)"><i class="fa fa-times-circle-o"></i>&nbsp;失效</a>
	</c:if>
	
  	<c:if test="${displayBtnCheckStep1}">
		<a class="l-btn l_btn_size" href="javascript:void(0)"
			onclick="toChecked(${loanDo.loanId},1)"> <i class="fa fa-gavel"></i>
			审核（一审）
		</a>
	</c:if>
	<c:if test="${displayBtnCheckStep2}">
		<a class="l-btn l_btn_size " href="javascript:void(0)"
			onclick="toChecked(${loanDo.loanId},2)"> <i class="fa fa-gavel"></i>
			审核（二审）&nbsp;&nbsp;
		</a>
	</c:if>
			
</div>
<div style="margin:10px 0px; width:100%;display:inline-block;">
	<table style="width:98%; margin-left:10px; border:1px dashed #ccc;"> 
        <thead ><tr><td colspan="4"><span style="color:red;font-size:10px;"><b>订单号:&nbsp;${loanDo.orderCode}</b>&nbsp;
        </span></td></tr>
        </thead>
         <tr style="width:100%;height:35px;"> 
            <td style="border:0;width:25%;font-size:10px;"><b>订单状态: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.loanStatusDesc}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>产品类型: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.productName}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>贷款类型: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.loanType == '1' ? '信用贷款' : loanDo.loanType == '2' ? '抵押贷款' : loanDo.loanType == '3' ? '担保贷款' :"" } </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>借款人: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.realName } </font></td> 
        </tr>
        <tr style="width:100%;height:35px;"> 
            <td style="border:0;width:25%;font-size:10px;"><b>手机号: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.mobile } </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>借款金额: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.applyAmount}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>借款期限: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.loanPeriod}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>借款年利率: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.annualRate}%  </font></td> 
        </tr>
        <tr style="width:100%;height:35px;"> 
            <td style="border:0;width:25%;font-size:10px;"><b>还款方式: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.schemeName}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>申请日期: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.createTime}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>是否推荐: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.referenceMobile } </font></td> 
            <td style="border:0;width:20%;font-size:10px;"><b>投资人利率: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.investAnnualRate } </font></td> 
        </tr>
        <tr style="width:100%;height:35px;"> 
            <td style="border:0;width:25%;font-size:10px;"><b>审核人: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.auditUser}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>审核时间: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.auditTime } </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>放款金额: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.loanAmount}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>放款日期: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.loanTime}  </font></td> 
        </tr>
        <tr style="width:100%;height:35px;"> 
            <td style="border:0;width:25%;font-size:10px;"><b>贷款用途: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.loanUsage}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>贷款标题: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanDo.loanTitle}  </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>推荐人手机号: </b><font style="color:#2E8B75;font-weight:bold;">${loanPerson.referenceMobile} </font></td> 
            <td style="border:0;width:25%;font-size:10px;"><b>风控额度: </b><font style="color:#2E8B75;font-weight:bold;">${loanDo.auditAmount } </font></td> 
         </tr>
    </table>
</div>
<div class="cardbox" >
	<div style="height:30px; margin-left: 10px;">
	    <ul id="oneul">
	    	<li><a href="#" id="lia1" onclick="checked(1)" style="background-color:#bfcbd6;color:#465c71;text-decoration: none;">基本信息</a></li>
	        <li><a href="#" id="lia2" onclick="checked(2)">资产信息</a></li>
	        <li><a href="#" id="lia3" onclick="checked(3)">工作信息</a></li>
	        <li><a href="#" id="lia4" onclick="checked(4)">资料信息</a></li>
	        <li><a href="#" id="lia5" onclick="checked(5)">银行卡信息</a></li>
	        <li><a href="#" id="lia6" onclick="checked(6)">信审信息</a></li>
	        <li><a href="#" id="lia7" onclick="checked(7)">征信信息</a></li>
	        <li><a href="#" id="lia8" onclick="checked(8)">联系人</a></li>
	        <li><a href="#" id="lia9" onclick="checked(9)">银行流水</a></li>
	        <li><a href="#" id="lia10" onclick="checked(10)">还款计划</a></li>
	       
	    </ul>
 </div>
     	<div id="tabl1" class="card">
     	<table class="comDiv" cellpadding="10px" cellspacing="10px">
     	<tr>
	     	<td style="width:180px;border-right: 1px  dashed #B6B6B6; vertical-align: top;">
		     	<div style="width:100%; height:20px; padding-bottom: 50px;">
		     		<h1>借款人个人信息</h1>
		     	</div>
		     	<div>
			     	<div style="position:relative; width:30px; height:20px; padding:10px 0; float: left;  ">
			     	<c:choose>
			     		<c:when test='${loanPersonCheckDo.name eq "1" }'>
			     		 <i class="fa fa-2x fa-check-circle green"></i> 
			     		</c:when>
			     		<c:when test='${loanPersonCheckDo.name eq "0" }'>
			     		 <i class="fa fa-2x fa-times-circle red"></i> 
			     		</c:when>
			     		<c:otherwise>
			     		<i class="fa fa-2x fa-exclamation-circle"></i> 
			     		</c:otherwise>
			     	</c:choose>
			     	  
			     	</div>
			     	<div style="position:relative; width:100px; height:40px; float: left; ">
			     	<h2>${loanPerson.realName}</h2>
			     	</div>
			     	<div style="position:relative; width:40px; height:40px; float: left;">
			     	<h2 >${loanPerson.sex}</h2>
			     	</div>
			     	
		     	</div>
		     	<div>
			     	<div style="position:relative; width:30px; height:40px; float: left; ">
			     	</div>
			     	<div style="position:relative; width:100px; height:40px; float: left;">
			     	<h2>年龄:</h2>
			     	</div>
			     	<div style="position:relative; width:40px; height:40px; float: left;">
			     	<h2 >${loanPerson.age}</h2>
			     	</div>
		     	</div>
			     
			     <div style="clear:both; width:100%; height:120px; padding-top: 50px;">
			     	<h2>学历：${loanPerson.education}</h2>
			     	<h2>婚姻状况： ${loanPerson.marriaged}</h2>
			     	</div>
		     	</td>
		     	<td style="width:350px; border-right: 1px dashed #B6B6B6; vertical-align: top;">
		     	<div style="width:100%; height:20px; padding-bottom: 50px; ">
		     		&nbsp;
		     	</div>
		     	<div style="width:100%;">
		     		<div style="position:relative; width:30px; height:20px; padding:10px 0; float: left;  ">
			     	  &nbsp;
			     	</div>
			     	<div style="position:relative; width:80px; height:40px; float: left; "><h2>电话号码:</h2></div>
		     		<div  style="position:relative; width:100px; height:40px; float: left; "><h2>${loanPerson.mobile}</h2></div>
		     	</div>
		     	<div style="width:100%;">
		     		<div style="position:relative; width:30px; height:20px; padding:10px 0; clear:both; float: left;  ">
		     		<c:choose>
			     		<c:when test='${loanPersonCheckDo.idno eq "1" }'>
			     		 <i class="fa fa-2x fa-check-circle green"></i> 
			     		</c:when>
			     		<c:when test='${loanPersonCheckDo.idno eq "0" }'>
			     		 <i class="fa fa-2x fa-times-circle red"></i> 
			     		</c:when>
			     		<c:otherwise>
			     		<i class="fa fa-2x fa-exclamation-circle"></i> 
			     		</c:otherwise>
			     	</c:choose>
			     	</div>
			     	<div style="position:relative; width:80px; height:40px; float: left; "><h2>身份证号:</h2></div>
		     		<div  style="position:relative; width:100px; height:40px; float: left; "><h2>${loanPerson.idNo}</h2></div>
		     	</div>
		     		<div style="clear:both; width:100%; padding-top: 50px;">
			     	<h2>职业信息：${empty loanPerson.jobDo?"":loanPerson.jobDo.jobType}</h2>
			     	<h2>工作年限：${empty loanPerson.jobDo?"":
			     	loanPerson.jobDo.jobYear == '100' ? '0~1年':
			     	loanPerson.jobDo.jobYear == '101' ? '1~2年':
			     	loanPerson.jobDo.jobYear == '102' ? '2~3年':
			     	loanPerson.jobDo.jobYear == '103' ? '3~4年':
			     	loanPerson.jobDo.jobYear == '104' ? '4~5年':
			     	loanPerson.jobDo.jobYear == '105' ? '5年以上':''}</h2>
			     	<h2>电子邮箱： ${loanPerson.email} </h2>
			     	</div>
	     	</td>
     	
     		<td>
     	 		<div style="display: inline-block;width:100%;height:100%;">
     	 			
     	 			<table style="width:100%;height:100%;" id="person_ul">
     			
     				<tr style="width:100%;height:50%;">
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${idcertz!=null}">
						     			 <a href="${fileSeverDir}${idcertz.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${idcertz.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
					     	 		</c:choose>
     							</li>
     							<li>
     								身份证正面
     							</li>
     							<li>
     								<c:if test="${idcertz!=null}">
							     		<h3>
							     			社区管理审核:
									     	<c:choose>
										     	<c:when test="${idcertz_check==1}">
										     		<font style="color:green;">一致</font>
										     	</c:when>
										     	<c:when test="${idcertz_check==0}">
										     		<font style="color:red;">不一致</font>
										     	</c:when>
										     	<c:otherwise>
										     		未审核
										     	</c:otherwise>
									     	</c:choose>
								     	</h3>
								     </c:if>
     							</li>
     						</ul>
     					</td>
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${idcertf!=null}">
						     			 <a href="${fileSeverDir}${idcertf.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${idcertf.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
					     	 		</c:choose>
     							</li>
     							<li>
     								身份证反面
     							</li>
     							<li>
     								<c:if test="${idcertf!=null}">
							     		<h3>
							     			社区管理审核:
									     	<c:choose>
										     	<c:when test="${idcertf_check==1}">
										     		<font style="color:green;">一致</font>
										     	</c:when>
										     	<c:when test="${idcertf_check==0}">
										     		<font style="color:red;">不一致</font>
										     	</c:when>
										     	<c:otherwise>
										     		未审核
										     	</c:otherwise>
									     	</c:choose>
								     	</h3>
								     </c:if>
     							</li>
     						</ul>
     					</td>
     				</tr>
     				
     				<tr style="width:100%;height:50%;">
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${idcertzs!=null}">
						     			 <a href="${fileSeverDir}${idcertzs.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${idcertzs.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
					     	 		</c:choose>	
     							</li>
     							<li>
     								本人半身手持身份证正面
     							</li>
     							<li>
     								<c:if test="${idcertzs!=null}">
							     		<h3>
							     			社区管理审核:
									     	<c:choose>
										     	<c:when test="${idcertzs_check==1}">
										     		<font style="color:green;">一致</font>
										     	</c:when>
										     	<c:when test="${idcertzs_check==0}">
										     		<font style="color:red;">不一致</font>
										     	</c:when>
										     	<c:otherwise>
										     		未审核
										     	</c:otherwise>
									     	</c:choose>
								     	</h3>
								     </c:if>
     							</li>
     						</ul>
     					</td>
     					
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${jobcert!=null}">
						     			 <a href="${fileSeverDir}${jobcert.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${jobcert.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
						     	 	</c:choose>
     							</li>
     							<li>
     								工作证明
     							</li>
     							<li>
     								<c:if test="${jobcert!=null}">
							     		<h3>
							     			社区管理审核:
									     	<c:choose>
										     	<c:when test="${jobcert_check==1}">
										     		<font style="color:green;">一致</font>
										     	</c:when>
										     	<c:when test="${jobcert_check==0}">
										     		<font style="color:red;">不一致</font>
										     	</c:when>
										     	<c:otherwise>
										     		未审核
										     	</c:otherwise>
									     	</c:choose>
								     	</h3>
								     </c:if>
     							</li>
     						</ul>
     					</td>
     					
     				</tr>
     			</table>
     	 
		     	</div>
		     
     		</td>
     	</tr>
     	</table>
     	</div>
     	
     	<div id="tabl2" class="card" style="display:none;"> 
     	<table class="comDiv">
     	<tr>
     	<td style="width:500px;">
     		<div style="width:100%;height:45px;">
	     		<div style="position:relative; width:30px; height:20px; padding:10px 0; clear:both; float: left;  ">
	     		 <c:choose>
		     		<c:when test='${loanPersonCheckDo.cname eq "1" }'>
		     		 <i class="fa fa-2x fa-check-circle green"></i> 
		     		</c:when>
		     		<c:when test='${loanPersonCheckDo.cname eq "0" }'>
		     		 <i class="fa fa-2x fa-times-circle red"></i> 
		     		</c:when>
		     		<c:otherwise>
		     		<i class="fa fa-2x fa-exclamation-circle"></i> 
		     		</c:otherwise>
		     	</c:choose>
		     	</div>
		     	<div style="position:relative; width:450px; height:40px; float: left; "><h2>小区名称:&nbsp;&nbsp;${loanPerson.cname}</h2></div>
	     	</div>
	     	
     		<div style="width:100%;height:45px;">
		     		<div style="position:relative; width:30px; height:20px; padding:10px 0; clear:both; float: left;  ">
		     		
					<c:choose>
			     		<c:when test='${loanPersonCheckDo.houseAddress eq "1" }'>
			     		 <i class="fa fa-2x fa-check-circle green"></i> 
			     		</c:when>
			     		<c:when test='${loanPersonCheckDo.houseAddress eq "0" }'>
			     		 <i class="fa fa-2x fa-times-circle red"></i> 
			     		</c:when>
			     		<c:otherwise>
			     		<i class="fa fa-2x fa-exclamation-circle"></i> 
			     		</c:otherwise>
			     	</c:choose>
			     	</div>
			     	<div style="position:relative; width:450px; height:40px; float: left; "><h2>房产地址:&nbsp;&nbsp; 
		     	${empty loanPerson.propertyDo?"": loanPerson.propertyDo.houseAddress} 
		     	 </h2></div>
     		</div>
     		<div style="width:100%;height:45px;">
		     	<div style="position:relative; width:30px; height:20px; padding:10px 0; clear:both; float: left;  ">
		     		<c:choose>
			     		<c:when test='${loanPersonCheckDo.mngfee eq "1" }'>
			     		 <i class="fa fa-2x fa-check-circle green"></i> 
			     		</c:when>
			     		<c:when test='${loanPersonCheckDo.mngfee eq "0" }'>
			     		 <i class="fa fa-2x fa-times-circle red"></i> 
			     		</c:when>
			     		<c:otherwise>
			     		<i class="fa fa-2x fa-exclamation-circle"></i> 
			     		</c:otherwise>
			     	</c:choose>
			     	</div>
			     	<div style="position:relative; width:450px; height:40px; float: left; "><h2>物业缴费情况:&nbsp;&nbsp;近18个月缴费情况</h2></div>
		     </div>
	     	<div style="width:100%;height:45px;">${mngfeeHtml}
	     	</div>
     	 
     	</td>
     	
     	<td style="width:220px; border-right: 1px dashed #B6B6B6; vertical-align: top;">
	     	<div style="padding:20px 0;">
		     	<h2>购买方式：${empty loanPerson.propertyDo?"":loanPerson.propertyDo.purchaseWay}</h2>
		     	<h2>是否抵押： ${empty loanPerson.propertyDo?"":loanPerson.propertyDo.houseDy}</h2>
		     	<h2>面积(平方米)：${empty loanPerson.propertyDo?"":loanPerson.propertyDo.coveredArea}</h2>
	     	</div>
     	</td>
     	
     	<td>
     	   <!-- tabl2  图片显示DIV -->
     		<div style="display: inline-block;width:100%;height:100%;">
     			
     			<table style="width:100%;height:100%;" id="propert_ul">
     			
     				<tr style="width:100%;height:50%;">
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${housecert!=null}">
						     			 <a href="${fileSeverDir}${housecert.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${housecert.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
					     	 		</c:choose>
     							</li>
     							<li>
     								房产证照片
     							</li>
     							<li>
     								<c:if test="${housecert!=null}">
	     								<h3>
								   		  社区管理审核:
								     	<c:choose>
									     	<c:when test="${housecert_check==1}">
									     	   <font style="color:green;">一致</font>
									     	</c:when>
									     	<c:when test="${housecert_check==0}">
									     		<font style="color:red;">不一致</font>
									     	</c:when>
									     	<c:otherwise>
									     		未审核
									     	</c:otherwise>
								     	</c:choose>
								     	</h3>
							     	</c:if>
     							</li>
     						</ul>
     					</td>
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${driveCard!=null}">
						     			 <a href="${fileSeverDir}${driveCard.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${driveCard.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
					     	 		</c:choose>
     							</li>
     							<li>
     								行驶证
     							</li>
     							<li>
     								<c:if test="${driveCard!=null}">
	     								<h3>社区管理审核:
									     	<c:choose>
										     	<c:when test="${driveCard_check == 1}">
										     		<font style="color:green;">一致</font>
										     	</c:when>
										     	<c:when test="${driveCard_check == 0}">
										     		<font style="color:red;">不一致</font>
										     	</c:when>
										     	<c:otherwise>
										     		未审核
										     	</c:otherwise>
									     	</c:choose>
									     </h3>
								     </c:if>
     							</li>
     						</ul>
     					</td>
     				</tr>
     				
     				<tr style="width:100%;height:50%;">
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${entrust_protocol!=null}">
						     			 <a href="${fileSeverDir}${entrust_protocol.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${entrust_protocol.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
					     	 		</c:choose>
     							</li>
     							<li>
     								代扣款协议
     							</li>
     						</ul>
     					</td>
     					<td style="width:50%;height:100%;" align="center">
     						<ul>
     							<li>
     								<c:choose>
								     	<c:when test="${creaudpro!=null}">
						     			 <a href="${fileSeverDir}${creaudpro.filePath }" target="_blank" title="点击查看大图"><img src="${fileSeverDir}${creaudpro.destFilePath }"  style="width:140px; height: 100px"></a>
						     	 		</c:when>
						     	 		<c:otherwise>
						     	 		<a><img src="${basePath}/images/upload-wait.png"  style="width:140px; height: 100px"></a>
						     	 		</c:otherwise>
					     	 		</c:choose>
     							</li>
     							<li>
     								征信协议
     							</li>
     						</ul>
     					</td>
     				</tr>
     			</table>
		    </div>
     	</td>
     	</tr>
     	</table>
     	</div>
     	
        
        
         <div id="tabl3" class="card" style="display:none;"> 
        
        <table class="comDiv" > 
            <tr style="width:100%;height:45px;"> 
                <td style="border:0;width:20%;font-size:14px;color:blue;" colspan="3">
                
                <h1>借款人工作信息</h1>
                </td> 
            </tr>
            <c:if test="${loanPerson.jobDo != '' }">
            <tr style="width:100%;height:45px;"> 
                <td style="border:0;width:33%;font-size:10px;"><b>工作单位: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanPerson.jobDo.companyName}  </font></td> 
                <td style="border:0;width:33%;font-size:10px;"><b>工作岗位: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanPerson.jobDo.position}  </font></td> 
                <td style="border:0;width:33%;font-size:10px;"><b>入职时间: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanPerson.jobDo.companyInTime}  </font></td>   
            </tr>
            <tr style="width:100%;height:45px;"> 
                <td style="border:0;width:34%;font-size:10px;"><b>工作年限: </b>
                		<font style="color:#2E8B75;font-weight:bold;"> 
                			${empty loanPerson.jobDo?"":
							     	loanPerson.jobDo.jobYear == '100' ? '0~1年':
							     	loanPerson.jobDo.jobYear == '101' ? '1~2年':
							     	loanPerson.jobDo.jobYear == '102' ? '2~3年':
							     	loanPerson.jobDo.jobYear == '103' ? '3~4年':
							     	loanPerson.jobDo.jobYear == '104' ? '4~5年':
							     	loanPerson.jobDo.jobYear == '105' ? '5年以上':''}
                		</font>
                </td> 
                <td style="border:0;width:33%;font-size:10px;"><b>月收入(元): </b><font style="color:#2E8B75;font-weight:bold;">  ${loanPerson.jobDo.jobIncome}  </font></td> 
                <td style="border:0;width:34%;font-size:10px;"><b>工作类型: </b><font style="color:#2E8B75;font-weight:bold;">  ${empty  loanPerson.jobDo?"":loanPerson.jobDo.jobType}  </font></td> 
            </tr>
            <tr style="width:100%;height:45px;"> 
                <td style="border:0;width:33%;font-size:10px;"><b>单位地址: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanPerson.jobDo.companyAddr}  </font></td> 
                <td style="border:0;width:33%;font-size:10px;"><b>单位电话: </b><font style="color:#2E8B75;font-weight:bold;"> ${ loanPerson.jobDo.companyPhone}  </font></td> 
                <td style="border:0;width:34%;font-size:10px;"><b>营业执照号: </b><font style="color:#2E8B75;font-weight:bold;">  ${loanPerson.jobDo.certNo}  </font></td> 
            </tr>
            </c:if>
            <c:if test="${loanPerson.jobDo == ''}">
            	<tr style="width:100%;height:40px;"> 
	                <td style="border:0;width:30%;font-size:10px;" colspan="3"><font style="color:red;font-weight:bold;">暂无工作信息  </font></td> 
	            </tr>
            </c:if>
        </table>
        </div>
        <div id="tabl4" class="card" style="display:none;"> 
        
        <table class="comDiv"> 
            <tr style="width:100%;height:35px;"> 
                <td style="border:0;width:20%;font-size:14px;color:blue;" colspan="3"><h1>资料信息 </h1></td> 
            </tr>
            <c:if test="${!empty loanPerson.certList}">
		    <c:forEach var="item" items="${loanPerson.certList}" varStatus="no">
		            <tr style="width:100%;height:25px; border: 1px solid #CCC;"> 
		            	<td style="width:10px;border-right:1px solid #CCC;">${no.index+1} </td>
		            	<td style="width:33%;font-size:10px;" align="center"><img src="${item.destFilePath}" alt="${item.certificateType}"  width="100" height="100" />&nbsp;<a href="${item.filePath}" target="_blank" >查看大图</a></td>
		            	 <!-- <td style="border:0;width:34%;font-size:10px;" colspan="2"><img src="${item.destFilePath}" alt="${item.certificateType}"  width="100" height="100" /></td> -->  
		                <!-- <td style="border:0;width:33%;font-size:10px;"><b>证件名称: </b><font style="color:#2E8B75;font-weight:bold;"> ${ item.certificateName }  </font></td>  -->
		                <td style="width:66%;font-size:10px;"><b>证件类型: </b><font style="color:#2E8B75;font-weight:bold;">  ${item.certificateType}  </font>
		                	&nbsp;&nbsp;<b>文件类型: </b><font style="color:#2E8B75;font-weight:bold;">  ${item.fileType}</font>
		                	&nbsp;&nbsp;<b>上传人: </b><font style="color:#2E8B75;font-weight:bold;">  ${item.createUser}  </font>
		                </td> 
		                <!-- <td style="border:0;width:33%;font-size:10px;"><b>文件类型: </b><font style="color:#2E8B75;font-weight:bold;">  ${item.fileType}  </font></td> 
		                <td style="border:0;width:34%;font-size:10px;"><b>上传人: </b><font style="color:#2E8B75;font-weight:bold;">  ${item.createUser}  </font></td> --> 
		            </tr>
		      </c:forEach>
		      </c:if> 
		      <c:if test="${empty loanPerson.certList}">
            	<tr style="width:100%;height:40px;"> 
	                <td style="border:0;width:50%;font-size:10px;" colspan="3"><font style="color:red;font-weight:bold;">暂无资料信息  </font></td> 
	            </tr>
            </c:if>     
        </table>
        </div>
        <div id="tabl5" class="card" style="display:none;"> 
        
        <table  class="comDiv"> 
        	<c:if test="${bankCard eq '1'}">
        		<tr style="width:100%;height:30px;"> 
	                <td style="border:0;width:20%;font-size:14px;color:blue;" colspan="3"><h1> 汇付卡信息 </h1></td> 
	            </tr>
        		<c:if test="${!empty tbcMap}">
	        		<c:forEach var="itemtbc" items="${tbcMap}"  varStatus="notbc">
				            
				            <tr style="width:100%;height:30px; border-top: 1px solid #CCC;"> 
				            	<%-- <td style="width:10px;border-right:1px solid #CCC;" rowspan="2">${notbc.index+1} </td> --%>
				            	<td style="border:0;width:33%;font-size:10px;"><b>用户名: </b><font style="color:#2E8B75;font-weight:bold;">  ${itemtbc.userName}  </font></td> 
				                <td style="border:0;width:33%;font-size:10px;"><b>银行卡号: </b><font style="color:#2E8B75;font-weight:bold;"> ${itemtbc.cardNo}  </font></td> 
				                <td style="border:0;width:34%;font-size:10px;"><b>所属银行: </b><font style="color:#2E8B75;font-weight:bold;">  ${itemtbc.bankName}  </font></td> 
				            </tr>
				            <%-- <tr style="width:100%;height:25px; border-bottom: 1px solid #CCC;"> 
				            	<td style="border:0;width:33%;font-size:10px;"><b>状态: </b><font style="color:#2E8B75;font-weight:bold;">  ${itemtbc.cardStatus == '1' ? '<font style="color:red;">已绑定</font>' : itemtbc.cardStatus == '2'  ? '申请中' :'失败' }  </font></td> 
				                <td style="border:0;width:33%;font-size:10px;"><b>是否默认: </b><font style="color:#2E8B75;font-weight:bold;"> ${itemtbc.isDefault == '0' ? '否':'是'}  </font></td> 
				                <td style="border:0;width:34%;font-size:10px;"><b>快捷充值卡: </b><font style="color:#2E8B75;font-weight:bold;">  ${itemtbc.isExpress == '0' ? "否" :'是'}  </font></td>  
				            </tr> --%>
				      </c:forEach>
			      </c:if>
			      <c:if test="${empty tbcMap}">
				      <tr style="width:100%;height:30px;"> 
		                <td style="border:0;width:50%;font-size:10px;" colspan="4"><font style="color:red;font-weight:bold;">暂无汇付卡信息  </font></td> 
		             </tr>
			      </c:if>
			      <tr style="width:100%;height:30px;"> 
	             </tr>
			      <tr style="width:100%;height:30px;"> 
	                <td style="border:0;width:20%;font-size:14px;color:blue;" colspan="3"><h1> 通联卡信息 </h1></td> 
	             </tr>
	             <c:if test="${!empty tdbcMap}">
	        		<c:forEach var="itemtdbc" items="${tdbcMap}" varStatus="notdbc">
				            
				            <tr style="width:100%;height:30px; border-top: 1px solid #CCC;"> 
				            	<%-- <td style="width:10px;border-right:1px solid #CCC;" rowspan="2" >${notdbc.index+1} </td> --%>
				            	<td style="border:0;width:33%;font-size:10px;"><b>用户名: </b><font style="color:#2E8B75;font-weight:bold;">  ${itemtdbc.userName}  </font></td> 
				                <td style="border:0;width:33%;font-size:10px;"><b>银行卡号: </b><font style="color:#2E8B75;font-weight:bold;"> ${itemtdbc.cardNo}  </font></td> 
				                <td style="border:0;width:34%;font-size:10px;"><b>所属银行: </b><font style="color:#2E8B75;font-weight:bold;">  ${itemtdbc.bankName}  </font></td> 
				            </tr>
				            <%-- <tr style="width:100%;height:25px; border-bottom: 1px solid #CCC;"> 
				            	<td style="border:0;width:33%;font-size:10px;"><b>状态: </b><font style="color:#2E8B75;font-weight:bold;">  
				            		${itemtdbc.cardStatus == '1' ? '未绑定' : itemtdbc.cardStatus == '2' ? '绑定中' : itemtdbc.cardStatus == '3' ? '<font style="color:red;">已绑定</font>' :  itemtdbc.cardStatus == '4' ? '绑定失败' : itemtdbc.cardStatus == '5' ? '冻结' : '解绑' }  
				            		</font>
				            	</td> 
				            </tr> --%>
				      </c:forEach>
			      </c:if>
			      <c:if test="${empty tdbcMap}">
				      <tr style="width:100%;height:30px;"> 
		                <td style="border:0;width:50%;font-size:10px;" colspan="4"><font style="color:red;font-weight:bold;">暂无通联卡信息  </font></td> 
		             </tr>
			      </c:if> 
		      </c:if> 
		      <c:if test="${bankCard eq '0'}">
            	<tr style="width:100%;height:40px;"> 
	                <td style="border:0;width:30%;font-size:10px;" colspan="3"><font style="color:red;font-weight:bold;">暂无银行卡信息  </font></td> 
	            </tr>
            </c:if>     
        </table>
        </div>
        <div id="tabl6" class="card" style="display:none;"> 
        
        <table  class="comDiv"> 
            <tr style="width:100%;height:30px;"> 
            	<td style="border:0;width:50%;font-size:14px;color:blue;" colspan="2" align="center"><h1>一审 </h1></td>
            	<td style="border:0;width:50%;font-size:14px;color:blue;border-left:1px dashed #ccc;" colspan="2" align="center"><h1>二审 </h1></td>
            </tr>
           	<tr style="width:100%;height:30px;">
           		<c:if test="${!empty loanCheckedOne}">
	           		<td style="border:0;width:50%;font-size:10px;" rowspan="3" colspan="2">
           				<div style="border:0;width:100%;height:30px;font-size:10px;" ><b>意见:</b><font style="color:#2E8B75;font-weight:bold;"> ${loanCheckedOne.opinion}</font></div>
            			<div style="border:0;width:100%;height:30px;font-size:10px;" ><b>建议放款金额:</b><font style="color:#2E8B75;font-weight:bold;"> ${loanCheckedOne.loanAmount}</font></div>
            			<div style="border:0;width:100%;height:30px;font-size:10px;" ><b>结果:</b><font style="color:#2E8B75;font-weight:bold;"> 
	            			${loanCheckedOne.checkResult eq '0' ? "不通过":"通过"}
	            			</font>
            			</div>
	           		</td>
	           		
	           		<c:if test="${!empty loanCheckedTwo}">
		           		<td style="border:0;width:50%;font-size:10px;border-left:1px dashed #ccc;" rowspan="3" colspan="2">
	           				<div style="border:0;width:100%;height:30px;font-size:10px;" ><b>意见:</b><font style="color:#2E8B75;font-weight:bold;"> ${loanCheckedTwo.opinion}</font></div>
	            			<div style="border:0;width:100%;height:30px;font-size:10px;" ><b>建议放款金额:</b><font style="color:#2E8B75;font-weight:bold;"> ${loanCheckedTwo.loanAmount}</font></div>
	            			<div style="border:0;width:100%;height:30px;font-size:10px;" ><b>结果:</b><font style="color:#2E8B75;font-weight:bold;"> 
		            			${loanCheckedTwo.checkResult eq '0' ? "不通过":"通过"}
		            			</font>
	            			</div>
		           		</td>
		           	</c:if>	
		           	<c:if test="${empty loanCheckedTwo}">
		           		<td style="border:0;width:50%;font-size:10px;border-left:1px dashed #ccc;" colspan="2" align="center"><font style="color:red;font-weight:bold;">暂无二审信息  </font></td>
		           	</c:if>
	           	
	           	</c:if>	
            	 
            	 <c:if test="${empty loanCheckedOne}">
	            	<td style="border:0;width:50%;font-size:10px;" colspan="2" align="center"><font style="color:red;font-weight:bold;">暂无一审信息  </font></td>
	            	<td style="border:0;width:50%;font-size:10px;border-left:1px dashed #ccc;" colspan="2" align="center"><font style="color:red;font-weight:bold;">暂无二审信息  </font></td>
	            </c:if>
            </tr>
        </table>
        </div>
	        <div  id="tabl7"   class="card"  style="display:none;">
	        <table class="comDiv"> 
	            <tr style="height:30px;"> 
	            	<td style="border:0; width:50%; text-indent: 10px;" colspan="2" align="left"><h2>负债信息 </h2></td>
	            	<td style="border:0; width:50%;text-indent: 10px; border-left:1px dashed #ccc;" colspan="2" align="left"><h2>其他信息 </h2></td>
	            </tr>
	           	<tr style="height:30px;">
	           		
	           		<td style="border:0;width:50%;font-size:10px;" colspan="2"><b>贷款总额: </b><font style="color:#2E8B75;font-weight:bold;"> ${loanDo.applyAmount}  </font></td> 
	           		
	           		<td style="border:0;width:25%;font-size:10px;"><b>信用卡额度: </b>
	           			<font style="color:#2E8B75;font-weight:bold;">
		           		 	<c:if test="${!empty loanPersonCreditRec}">
		           		    	${loanPersonCreditRec.creditAmount}
		           		    </c:if> 
	           		 	</font>
	           		 </td> 
	           		<td style="border:0;width:25%;font-size:10px;"><b>可用额:</b>
	           			<font style="color:#2E8B75;font-weight:bold;">
		           		 	<c:if test="${!empty loanPersonCreditRec}">
	           			       ${ loanPersonCreditRec.creditAmount * 1.5 }
		           		    </c:if> 
	           		 	</font>
					</td> 
	           	
	            </tr>
	            
	            <tr style="height:30px;">
	            		
	            	<td style="border:0;width:25%;font-size:10px;"><b>月均还款:</b>
	           			<font style="color:#2E8B75;font-weight:bold;">
		           		 	<c:if test="${!empty loanPersonCreditRec}">
	           				   ${loanPersonCreditRec.repayAmount}
		           		    </c:if> 
	           		 	</font>
	           		 </td> 
	           		
	           		<td style="border:0;width:25%;font-size:10px;"><b>可用额度: </b>
	           			<font style="color:#2E8B75;font-weight:bold;">
		           		 	<c:if test="${!empty loanPersonCreditRec}">
	           				   ${loanPersonCreditRec.repayAmount *0.5 * loanDo.loanPeriod}
		           		    </c:if> 
	           		 	</font>
	           		 </td> 
	           		<td style="border:0;width:50%;font-size:10px;" colspan="2"><b>一年内查询记录:</b>
	           			<c:if test="${!empty loanPersonCreditRec}">
		           			<font style="color:#2E8B75;font-weight:bold;">
		           				   ${loanPersonCreditRec.queryNumber}
		           		 	</font>
	           		 	<b>次</b>
	           		 	</c:if> 	
					</td> 	
					
	            
	            </tr>
	            
	            <tr style="height:30px;">
	            	<td style="border:0;width:50%;font-size:10px;" colspan="2"><b>还款异议标注:</b>
	           			<font style="color:#2E8B75;font-weight:bold;">
		           		 	<c:if test="${!empty loanPersonCreditRec}">
	           				    ${loanPersonCreditRec.repayExceptionNumber}
		           		    </c:if> 
	           		 	</font>
					</td> 	
					
					<td style="border:0;width:50%;font-size:10px;" colspan="2"><b>公积金缴费年限:</b>
						<c:if test="${!empty loanPersonCreditRec}">
		           			<font style="color:#2E8B75;font-weight:bold;">
		           				    ${loanPersonCreditRec.houseFundYears}
		           		 	</font>
		           		 	<b>年</b>
	           		 	</c:if> 
					</td> 	
					
	            </tr>
	            <tr style="height:30px;">
	            
	            	<td style="border:0;width:50%;font-size:10px;" colspan="2"><b>近6个月逾期次数:</b>
	           			<font style="color:#2E8B75;font-weight:bold;">
		           		 	<c:if test="${!empty loanPersonCreditRec}">
	           				    ${loanPersonCreditRec.overNumber}
		           		    </c:if> 
	           		 	</font>
					</td> 	
					
					<td style="border:0;width:25%;font-size:10px;"><b>公积金缴费金额:</b>
	           			<font style="color:#2E8B75;font-weight:bold;">
		           		 	<c:if test="${!empty loanPersonCreditRec}">
	           				    ${loanPersonCreditRec.houseFundAmount}
		           		    </c:if> 
	           		 	</font>
					</td>
					
					<td style="border:0;width:25%;font-size:10px;"><b>缴存比例:</b>
						<c:if test="${!empty loanPersonCreditRec}">
		           			<font style="color:#2E8B75;font-weight:bold;">
		           				   ${loanPersonCreditRec.houseFundScale}
		           		 	</font>
		           		 	<b>%</b>
	           		 	</c:if> 
					</td>
					
	            </tr>
	        </table>
	     </div>
	     
	     <div id="tabl8" class="card"  style="display:none;"> 
		        <table class="comDiv"> 
		            <tr style="width:100%;height:30px;"> 
		            	<td style="border:0;width:100%;font-size:14px;color:blue;" colspan="3" align="center"><h1>联系人信息</h1></td>
		            </tr>
		           	
	           		<c:if test="${!empty listRelat}">
	           				<tr style="width:100%;height:30px;"> 
				            	<td style="border:0;width:33%;font-size:10px; border-top: 1px dashed #CCC;border-left: 1px dashed #CCC;"><b>联系人姓名</b></td> 
				                <td style="border:0;width:33%;font-size:10px;border-top: 1px dashed #CCC;border-left: 1px dashed #CCC;"><b>与本人关系</b></td> 
				                <td style="border:0;width:34%;font-size:10px;border-top: 1px dashed #CCC;border-left: 1px dashed #CCC;"><b>联系人电话</b></td> 
				            </tr>
	           			<c:forEach var="itemrelat" items="${listRelat}" varStatus="norelat">
				            <tr style="width:100%;height:30px;"> 
				            	<td style="border:0;width:33%;font-size:10px;border-top: 1px dashed #CCC;border-left: 1px dashed #CCC;"><font style="color:#2E8B75;font-weight:bold;">  ${itemrelat.ralationName}  </font></td> 
				                <td style="border:0;width:33%;font-size:10px;border-top: 1px dashed #CCC;border-left: 1px dashed #CCC;"><font style="color:#2E8B75;font-weight:bold;"> 
				                ${itemrelat.relationship == '' ? '' :
				                itemrelat.relationship == '0' ? '父母' :
				                 itemrelat.relationship == '1' ? '配偶' :
				                itemrelat.relationship == '2' ? '子女' :
				                itemrelat.relationship == '3' ? '兄弟' :
				                itemrelat.relationship == '4' ? '姐妹' :
				                itemrelat.relationship == '5' ? '父母' :
				                 itemrelat.relationship == '6' ? '配偶' :
				                itemrelat.relationship == '7' ? '子女' :
				                itemrelat.relationship == '8' ? '兄弟' :
				                itemrelat.relationship == '9' ? '姐妹' :''}  
				                </font></td> 
				                <td style="border:0;width:34%;font-size:10px;border-top: 1px dashed #CCC;border-left: 1px dashed #CCC;"><font style="color:#2E8B75;font-weight:bold;">  ${itemrelat.mobile}  </font></td> 
				            </tr>
					    </c:forEach>
		           	</c:if>	
	            	 
	            	<c:if test="${empty listRelat}">
	            		<tr style="width:100%;height:30px;">
		            		<td style="border:0;width:100%;font-size:10px;" colspan="3" align="center"><font style="color:red;font-weight:bold;">暂无联系人信息  </font></td>
		            	</tr>
		            </c:if>
		            
	        	</table>
	      </div>
	      
	       <div id="tabl9" class="card" style="display:none;"> 
		        <table class="comDiv"  > 
		            <tr style="width:100%;height:30px;"> 
		            	<td style="border:0;width:100%;font-size:14px;color:blue;" colspan="3" align="center"><h1>银行流水</h1></td>
		            </tr>
		            
		            
		             <c:if test="${!empty incomes}">
		             	<tr style="width:100%; border: 1px solid #CCC;"> 
		             		<td style="border:0;" align="center">
							    <c:forEach var="itemIncome" items="${incomes}" varStatus="noincome">
								      <%-- <c:if test="${itemIncome.income.certType == 'INCOME'}">   --%>    
								      	 	<div style="float: left;margin-left:10px;">
										     	<ul style="list-style:none; margin-left:22px;padding: 0px; width: auto;">
					     							<li>
											     		<a href="${itemIncome.income.filePath }" target="_blank" title="点击查看大图"><img src="${itemIncome.income.destFilePath }"  style="width:140px; height: 100px"></a>
					     							</li>
					     							<li>
					     								银行流水${noincome.index+1}
					     							</li>
					     							<li>
					     								<c:if test="${itemIncome.income != null}">
						     								<h3>社区管理审核:
														     	<c:choose>
															     	<c:when test="${itemIncome.income_check == 1}">
															     		<font style="color:green;">一致</font>
															     	</c:when>
															     	<c:when test="${itemIncome.income_check == 0}">
															     		<font style="color:red;">不一致</font>
															     	</c:when>
															     	<c:otherwise>
															     		未审核
															     	</c:otherwise>
														     	</c:choose>
														     </h3>
													     </c:if>
					     							</li>
					     						</ul>
				     					  	</div> 
			     					   <%-- </c:if> --%>	      
							     </c:forEach>
						     </td>
					     </tr>
				     </c:if> 
				     
				     
				     <c:if test="${empty incomes}">
		            	<tr style="width:100%;height:40px;"> 
			                <td style="border:0;width:50%;font-size:10px;" colspan="3"><font style="color:red;font-weight:bold;">银行流水信息  </font></td> 
			            </tr>
		             </c:if>     
		            
	        	</table>
	      </div>
	      
	      <div id="tabl10" class="card" style="display:none;"> 
		        <table  id="repaymentTab" > 
	        	</table>
	      </div>
	      
     </div>
     
</div>
<div id="showImgWin"></div>
<style type="text/css">
div#div1 ul#oneul{
    list-style:none; /* 去掉ul前面的符号 */
    margin: 0px; /* 与外界元素的距离为0 */
    padding: 0px; /* 与内部元素的距离为0 */
    width: auto; /* 宽度根据元素内容调整 */
}
div#div1 ul#oneul li{
    float:left; /* 向左漂移，将竖排变为横排 */
}

div#div1 ul#oneul li a, div#div1 ul#oneul li a:visited{
    background-color: #465c71; /* 背景色 */
    border: 1px #4e667d solid; /* 边框 */
    color: #dde4ec; /* 文字颜色 */
    display: block; /* 此元素将显示为块级元素，此元素前后会带有换行符 */
    line-height: 1.35em; /* 行高 */
    padding: 4px 20px; /* 内部填充的距离 */
    text-decoration: none; /* 不显示超链接下划线 */
    white-space: nowrap; /* 对于文本内的空白处，不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 */
}
/* div中的ul中的a样式(鼠标移动到元素中的样式) */
div#div1 ul#oneul li a:hover{
    background-color: #bfcbd6; /* 背景色 */
    color: #465c71; /* 文字颜色 */
    text-decoration: none; /* 不显示超链接下划线 */
}
/* div中的ul中的a样式(鼠标点击元素时的样式) */
div#div1 ul#oneul li a:active{
    background-color: #465c71; /* 背景色 */
    color: #cfdbe6; /* 文字颜色 */
    text-decoration: none; /* 不显示超链接下划线 */
}
#div1{
background-color: #fff !important;
}
.cardbox{
background-color:#F5F5F5;padding:20px 0px;
border-top:1px solid #dddddd;
}
.cardbox h1{
font-size: 18px;
color:#3F3F3F;
}
.cardbox h2{
font-size: 16px;
color:#3F3F3F;
}
.cardbox div.card{
margin:10px 0px; width:100%;

}
.comDiv{
	width:98%;margin:10px;border:1px dashed #B6B6B6; 
}
div#detailLoanDiv{
background-color: #F5F5F5  !important;
}
.tagbox{
width:18px;
height:18px;
padding:2px;
background-color: #8C8282;
border:1px soild #ffffff;
margin: 1px;
float: left;
text-align: center;
vertical-align: middle;
font-size: 16px;
font-weight: bold;
}
.tagY{
color:#ffffff;
background-color: #68A886;
}
.tagN{
color:#ffffff;
background-color: #9E4E4E;
}
.ml10{margin-left: 10px;}
.red{color:red;}
.green{color:#217B03;}
.yellow{color:yellow;}
.l_btn_size{
margin-left:10px !important; 
text-decoration: none !important; 
padding: 4px 14px !important;
}
#propert_ul ul{
  	list-style:none; /* 去掉ul前面的符号 */
    margin: 0px; /* 与外界元素的距离为0 */
    padding: 0px; /* 与内部元素的距离为0 */
    width: auto; /* 宽度根据元素内容调整 */
}
#person_ul ul{
  	list-style:none; /* 去掉ul前面的符号 */
    margin: 0px; /* 与外界元素的距离为0 */
    padding: 0px; /* 与内部元素的距离为0 */
    width: auto; /* 宽度根据元素内容调整 */
}
</style>
<script>
function checked(type){
	/* 为了节约资源减少遍历  这里就直接写死了
	var tables = document.getElementsByTagName("table");
	var count = tables.length; */
	var count = 10;
	var repaytab = document.getElementById("tabl10");
	repaytab.style.display = "block";
	
	for(var i=1;i<=count;i++){
		var tabl = document.getElementById("tabl"+i);
		var lia = document.getElementById("lia"+i);
		if(type == i){
			tabl.style.display = "block";
			lia.style.backgroundColor = "#bfcbd6";
			lia.style.color = "#465c71";
			lia.style.textDecoration = "none";
		}else{
			tabl.style.display = "none";
			lia.style.backgroundColor = "#465c71";
			lia.style.color = "#dde4ec";
			lia.style.textDecoration = "none";
		}
	}
	
	if(10 == type){
		repayment('${loanDo.loanId}');	
	}
	
}

var count = 0;
function repayment(loanId){
	if(count == 0){
		$('#repaymentTab').datagrid({
		    height: 340,
		    url: '/loan/getRepayment.html',
		    method: 'POST',
		    queryParams: { 'loanId': loanId },
		    striped: true,
		    fitColumns: true,
		    singleSelect: false,
		    rownumbers: true,
		    pagination: false,
		    nowrap: false,
		    showFooter: true,
		    columns: [[
		        { field: 'repayPeriod', title: '期数', width: 180, align: 'left' },
		        { field: 'repayDate', title: '还款日期', width: 150, align: 'left' },
		        { field: 'stillRepayAll', title: '还款金额', width: 100, align: 'left' },
		    ]],
		    onBeforeLoad: function (param) {
		    },
		    onLoadSuccess: function (data) {
		        
		    },
		    onLoadError: function () {
		        
		    },
		    onClickCell: function (rowIndex, field, value) {
		        
		    }
		});
		count++;
	}
	
}

function closeDialog()
{
	$("#detailLoanDiv").dialog("close");	
}
</script>

</body>

</html>		