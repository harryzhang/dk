<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!--=======================-->
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css" href="common/date/calendar.css" />
					<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		    $(function(){
		       $("#li_work").click(function(){
		           var from = '${from}'
		           window.location.href='querWorkData.do?from='+from;
		        });
		        
		               $("#li_vip").click(function(){
		               var from = '${from}'
		       window.location.href='quervipData.do?from='+from;
		    });
		        
		       }); 
		        
		        
		    
		</script>
		<script>
  $(function(){
   if('${map.auditStatus}'=='3'){
    $("#province").attr("disabled","true");
    $("#registedPlacePro").attr("disabled","true");
      $("#city").attr("disabled","true");
       $("#registedPlaceCity").attr("disabled","true");
       $("#clickCode").hide();
    }
  });
  
  
</script>
<script>
$(function(){
  var t = '${map.idNo}'==''?'':'${map.idNo}';
  var len = t.length;
  
  if(len==15){
  var tmplen = t.substr(0,2);
  
  $('#idNo1').html(tmplen+'***** **** ****');  
  }else if(len==18){
  var tttttt = t.substr(0,2);
  $('#idNo1').html(tttttt+'**** **** **** ****');  
  }
  
  //alert(t.substr);
  
  var tt = '${map.cellPhone}'==''?'':'${map.cellPhone}';
  var len1 = tt.length;
  var tmplen1;
  var tmplen2;
  if(len1!=0){
  tmplen1 = tt.substr(0,3);
  tmplen2 = tt.substr(7,11);
  $('#cellPhone1').html(tmplen1+' **** '+tmplen2); 
  }

});
</script>



	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="nymain">
			<div class="bigbox" style="border:none">
				
				<div class="sqdk" style="background: none;">
					<div class="l-nav">
						<ul>
							<li class="on"><a><span>step1 </span>基本资料</a></li>
							<li>
							<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
							  <a href="userPassData.do?from=${from }"><span>step2 </span>上传资料</a>
							</s:if>
							<s:else>
								<s:if test="#session.user.authStep>=4">
									<a href="userPassData.do"><span>step2 </span>上传资料</a>
								</s:if>	
								<s:else>
								     <a><span>step2 </span> 上传资料</a>
								</s:else>
							</s:else>
							</li>
							<li>
							<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
								<s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.do?t=${session.borrowWay}"><span>step3 </span> 发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.do"><span>step3 </span> 申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.do?t=0"><span>step3 </span>发布贷款</a>	
								  </s:else>
							</s:if>
							<s:else>
								<s:if test="#session.user.authStep>=5">
								  <s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.do?t=${session.borrowWay}"><span>step3 </span>发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.do"><span>step3 </span> 申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.do?t=0"><span>step3 </span>发布贷款</a>	
								  </s:else>
								</s:if>	
								<s:else>
								  <s:if test="#session.stepMode ==1">
								       <a><span>step3 </span>发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a><span>step3 </span> 申请额度</a>
								  </s:elseif>
								</s:else>
							</s:else>
							</li>
						</ul>
					</div>
					<div class="r-main">
						<div class="til01">
							<ul>
								<li class="on" id="li_geren">
									个人详细信息
								</li>
								<li  id="li_work">
										<s:if test="#session.user.authStep>=2">
											工作认证信息
							      </s:if>	
							 <s:else>
							     
							 </s:else>
									
									
									
								</li>
								<li  id="li_vip">
								<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
								  申请vip
								</s:if>
								<s:else>	
									  <s:if test="#session.user.authStep>=3">
												申请vip
								      </s:if>	
							     </s:else>
									
									
									
								</li>
							</ul>
						</div>
						<div class="rmainbox">
						<!-- 基础资料div -->
							<div class="box01" id="jibenziliao">
								<p class="tips">
									<span class="fred">*</span> 为必填项，所有资料均会严格保密。
								</p>
								<div class="tab">
									<form id="BaseDataform">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>真实姓名：
												</td>
												<td>
												<s:if test='#request.flag=="2"'><input type="text" class="inp188" name="paramMap.realName"
														id="realName"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>/>
													<span style="color: red; float: none;" id="u_realName"
														class="formtips"></span>
											    </s:if>
												<s:else>
												  ${map.realName}
												  <input type="hidden" class="inp188" name="paramMap.realName"
														id="realName" value="${map.realName}"/>
												</s:else>
												
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>身份证号：
												</td>
												<td>
												<s:if test='#request.flag=="2"'>
													<input type="text" class="inp188" name="paramMap.idNo"
														id="idNo" />
													<span style="color: red; float: none;" id="u_idNo"
														class="formtips"></span>
												</s:if>
														<s:else>
												 <a id='idNo1'></a>
												  <input type="hidden" class="inp188" name="paramMap.idNo"
														id="idNo" value="${map.idNo}"/>
												</s:else>
												</td>
											</tr>
											<tr>
					<td align="right">
						<span style="color: red; float: none;" class="formtips">*</span>手机号码：
					</td>
					<td>
						<s:if test="#request.map.cellPhone!=null">
							<a id="cellPhone1"></a>
								<input type="hidden" class="inp188" name="paramMap.cellPhone"
									id="cellPhone" value="${map.cellPhone}" />
						</s:if>
						<s:else>
								<s:if test='#request.flag=="2"'>
									<input type="text" class="inp188" name="paramMap.cellPhone"
										id="cellPhone" />
									<a id="clickCode" class="yzmbtn" style="cursor: pointer;">点击获取验证码</a>
							
									<span style="color: red; float: none;" id="u_cellPhone"
										class="formtips"></span>
								</s:if>
								<s:else>
									<a id="cellPhone1"></a>
									<input type="hidden" class="inp188" name="paramMap.cellPhone"
										id="cellPhone" value="${map.cellPhone}" />
								</s:else>
						</s:else>
					</td>
					</tr>
					<s:if test="#request.map.cellPhone!=null">
							<input type="hidden" class="inp100x" name="iscellPhone"
										id="vilidataNum" value="${map.cellPhone}" />
					</s:if>
					<s:else>
									<s:if test='#request.flag=="2"'>
							<tr>
								<td align="right">
									<span style="color: red; float: none;" class="formtips">*</span>手机验证码：
								</td>
								<td>
									<input type="text" class="inp100x" name="paramMap.vilidataNum"
										id="vilidataNum" />
								<s:if test="#request.ISDEMO==1">
								<span style="color: red; float: none;" id="u_vilidat2"
										class="formtips">
										* 演示站点测试不发送验证码，随意填写.
										</span>
								</s:if>
									<span style="color: red; float: none;" id="u_vilidataNum"
										class="formtips">
										</span>
								</td>
							</tr>
						
						</s:if>
						<s:else>
		
						</s:else>
					</s:else>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>性 别：
												</td>
												<td>
									<%--			<s:if test='#request.flag=="2"'> --%>
													<input type="radio"  id="sex" value="男"
													<s:if test='#request.map.sex == "男"'>checked="checked"</s:if>
														name="paramMap_sex"  class="sex"
												<s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
													onclick="javascript:$('#u_sex').html('')"	 />
													男
													<input type="radio"  id="sex" value="女"
														name="paramMap_sex" class="sex"
													 <s:if test='#request.map.sex == "女"'>checked="checked"</s:if>
													onclick="javascript:$('#u_sex').html('')"	
														<s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>/>
													女
													<span style="color: red; float: none;" id="u_sex"
														class="formtips"></span>
												<%--		</s:if>
													<s:else>
												        ${map.sex }		
												            <input type="hidden" class="inp188" name="paramMap_sex"
														id="sex" value="${map.sex }"/>
													</s:else> --%>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>出生日期：
												</td>
												<td>
										<%--		<s:if test='#request.flag=="2"'> --%>
													<input type="text" class="inp188 Wdate" name="paramMap.birthday"
													value="${birth } "
													id="birthday"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"  
													<s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>/>
													<span style="color: red; float: none;" id="u_birthday"
														class="formtips"></span>
											<%--			</s:if>
														<s:else>
														     ${birth }		
												            <input type="hidden" class="inp188" name="paramMap.birthday"
														id="birthday" value="${birth }"/>
														</s:else>
														 --%>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>最高学历：
												</td>
												<td>
													<select name="paramMap.highestEdu" id="highestEdu" onchange="javascript:if($('#highestEdu').val()!=''){$('#u_highestEdu').html('');} " <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>>
															<option value="">
													请选择
														</option>
														<option value="高中或以下"
														<s:if test='#request.map.highestEdu == "高中或以下"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															高中或以下
														</option>
														<option value="大专"
															<s:if test='#request.map.highestEdu == "大专"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															大专
														</option>
														<option value="本科"
															<s:if test='#request.map.highestEdu == "本科"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															本科
														</option>
														<option value="研究生或以上"
															<s:if test='#request.map.highestEdu == "研究生或以上"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															研究生或以上
														</option>
														<option value="其他"
															<s:if test='#request.map.highestEdu == "其他"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															其他
													   </option>
													</select>
													<span style="color: red; float: none;" id="u_highestEdu"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>入学年份：
												</td>
												<td>
													<input type="text" class="inp188 Wdate"
														name="paramMap.eduStartDay" id="eduStartDay"
														onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})" value="${rxedate }" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>/>
													<!--  
    <select name="paramMap.eduStartDay" id="eduStartDay" >
      <option value="">请选择</option>
    </select>
    -->


													<span style="color: red; float: none;" id="u_eduStartDay"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>毕业院校：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.school"
														id="school" value="${map.school }" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>/>
													<span style="color: red; float: none;" id="u_school"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>婚姻状况：
												</td>
												<td>
													<input type="radio" name="paramMap_maritalStatus"
														id="maritalStatus" value="已婚" 
														<s:if test='#request.map.maritalStatus == "已婚"'>checked="checked"</s:if>
														<s:else></s:else>
														onclick="javascript:$('#u_maritalStatus').html('')"
														<s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
														/>
													已婚
													<input type="radio" name="paramMap_maritalStatus"
														id="maritalStatus" value="未婚" 
															<s:if test='#request.map.maritalStatus == "未婚"'>checked="checked"</s:if>
														<s:else></s:else> <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
														onclick="javascript:$('#u_maritalStatus').html('')"
														/>
													未婚
													<span style="color: red; float: none;" id="u_maritalStatus"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>有无子女：
												</td>
												<td>
													<input type="radio" name="paramMap_hasChild" id="hasChild"
														value="有"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
															<s:if test='#request.map.hasChild == "有"'>checked="checked"</s:if>
														<s:else></s:else>
														onclick="javascript:$('#u_hasChild').html('')"
														 />
													有
													<input type="radio" name="paramMap_hasChild" id="hasChild"
														value="无"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
															<s:if test='#request.map.hasChild == "无"'>checked="checked"</s:if>
														<s:else></s:else>
														onclick="javascript:$('#u_hasChild').html('')"
														/>
													无
													<span style="color: red; float: none;" id="u_hasChild"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
												<span style="color: red; float: none;" 
														class="formtips">*</span>	是否有房：
												</td>
												<td>
													<input type="radio" name="paramMap_hasHourse"
														id="hasHourse" value="有"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
																<s:if test='#request.map.hasHourse == "有"'>checked="checked"</s:if>
														<s:else></s:else>
														onclick="javascript:$('#u_hasHourse').html('')"
														/>
													有
													<input type="radio" name="paramMap_hasHourse"
														id="hasHourse" value="无"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
																<s:if test='#request.map.hasHourse == "无"'>checked="checked"</s:if>
														<s:else></s:else>
														onclick="javascript:$('#u_hasHourse').html('')"
														/>
													无
													<span style="color: red; float: none;" id="u_hasHourse"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>有无房贷：
												</td>
												<td>
													<input type="radio" name="paramMap_hasHousrseLoan"
														id="hasHousrseLoan" value="有"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
																<s:if test='#request.map.hasHousrseLoan == "有"'>checked="checked"</s:if>
														<s:else></s:else>
														onclick="javascript:$('#u_hasHousrseLoan').html('')"
														/>
													有
													<input type="radio" name="paramMap_hasHousrseLoan"
														id="hasHousrseLoan" value="无"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
															<s:if test='#request.map.hasHousrseLoan == "无"'>checked="checked"</s:if>
														<s:else></s:else>
														onclick="javascript:$('#u_hasHousrseLoan').html('')"
														 />
													无
													<span style="color: red; float: none;"
														id="u_hasHousrseLoan" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>是否有车：
												</td>
												<td>
													<input type="radio" name="paramMap_hasCar" id="hasCar"
														value="有"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
															<s:if test='#request.map.hasCar == "有"'>checked="checked"</s:if>
														<s:else></s:else>
															onclick="javascript:$('#u_hasCar').html('')"
														/>
													有
													<input type="radio" name="paramMap_hasCar" id="hasCar"
														value="无" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
														<s:if test='#request.map.hasCar == "无"'>checked="checked"</s:if>
														<s:else></s:else>
															onclick="javascript:$('#u_hasCar').html('')"
														 />
													无
													<span style="color: red; float: none;" id="u_hasCar"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>有无车贷：
												</td>
												<td>
													<input type="radio" name="paramMap_hasCarLoan"
														id="hasCarLoan" value="有" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
														<s:if test='#request.map.hasCarLoan == "有"'>checked="checked"</s:if>
														<s:else></s:else>
															onclick="javascript:$('#u_hasCarLoan').html('')"
														/>
													有
													<input type="radio" name="paramMap_hasCarLoan"
														id="hasCarLoan" value="无"  <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
															<s:if test='#request.map.hasCarLoan == "无"'>checked="checked"</s:if>
														<s:else></s:else>
															onclick="javascript:$('#u_hasCarLoan').html('')"
														/>
													无
													<span style="color: red; float: none;" id="u_hasCarLoan"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>籍 贯：
												</td>
												<td>
													<s:select id="province" name="workPro"   
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" onchange="javascript:if($('#province').val()!=-1){$('#u_Pro_City').html('')}"/>
													<s:select id="city" name="cityId"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.cityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--"   onchange="javascript:if($('#city').val()!=-1){$('#u_Pro_City').html('')}"/>

													<span style="color: red; float: none;" id="u_Pro_City"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
												<span style="color: red; float: none;" 
														class="formtips">*</span>	户口所在地：
												</td>
												<td>
													<s:select id="registedPlacePro" 
														name="regPro"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--"  onchange="javascript:if($('#registedPlacePro').val()!=-1){$('#u_reg_Pro_City').html('')}"/>
													<s:select id="registedPlaceCity"
														name="regCity"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.regcityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--"   onchange="javascript:if($('#registedPlaceCity').val()!=-1){$('#u_reg_Pro_City').html('')}"/>
													<span style="color: red; float: none;" id="u_reg_Pro_City"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>居住地址：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.address"
														id="address" 
														value="${ map.address}" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>
														/>
													<span style="color: red; float: none;" id="u_address"
														class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>居住电话：
												</td>
												
												<td>
													<input type="text" class="inp188" name="paramMap.telephone"id="telephone" value="${map.telephone}" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>/>
													<span style="color:#999">格式：0755-83881140或者010-83881140&nbsp;</span><span style="color: red; float: none;" id="u_telephone" class="formtips"></span>
												</td>
											</tr>
											<tr>
												<td align="right" id="personalHead">
													个人头像：
												</td>
												<td>
													<input type="button" id="btn_personalHead" class="scbtn"
														style="cursor: pointer;" value="点击上传" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if>/>
												</td>
											</tr>
											<tr>
												<td align="right">&nbsp;
													
												</td>
												<td class="tx">
                                                     
                                                  <s:if test="#request.map.personalHead==null">	
                                                  	<img id="img" src="${headImg}" width="62" height="62"
														name="paramMap.personalHead" /></s:if>
                                                    <s:elseif test="#request.map.personalHead!=null">
                                                    	<img id="img" src="${map.personalHead}" width="62" height="62"
														name="paramMap.personalHead" />
                                                    </s:elseif>
                                                    <s:else>
                                                      <img id="img" src="images/default-img.jpg" width="62" height="62"
														name="paramMap.personalHead" />
                                                    </s:else>
                                                    <!--  
													<img id="img" src="${headImg}" width="62" height="62"
														name="paramMap.personalHead" />
													 -->
													<!-- - 
    	<img id="img" src="../images/NoImg.GIF" width="300px" height="300px" />
        <img id="setImg" src="" style="display: none" width="10px" height="10px"/>
    - -->
												</td>
											</tr>
											<tr>
												<td align="right">&nbsp;
													
												</td>
												<td class="tishi">
													请确保您填写的资料真实有效，所有资料将会严格保密。
													<br />
													一旦被发现所填资料有虚假，将会影响您在合和年在线的信用，对以后借款造成影响。
												</td>
											</tr>
											<tr>
												<td align="right">&nbsp;
													
												</td>
												<td style="padding-top: 20px;">
												<!--  
												<s:if test="#session.user.map.auditStatus==3"></s:if>
												<s:else>
												-->
													<input type="button" value="保存并继续" class="bcbtn" 
														id="jc_btn" <s:if test='#request.map.auditStatus ==3'>disabled="disabled"</s:if> />
												<!--</s:else>	-->	
												</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
							<!-- -- -->
							<!-- 是工作认证资料 -->
						   <!-- -- -->
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
			<script type="text/javascript" src="common/date/calendar.js"></script>

		<script>
		  var timers=180;
		  var tipId;
		  var flags = false;
		   $(function(){
		     	//当用户再次输入手机号码时候 add by lw
		       	$("#cellPhone").change( function() {
		       var phone=$("#cellPhone").val();
		        if($("#cellPhone").val()==""){
		        $("#u_cellPhone").attr("class", "formtips onError");
		        $("#u_cellPhone").html("请填写手机号码！");
		        }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
                      $("#u_cellPhone").attr("class", "formtips onError");
	                  $("#u_cellPhone").html("请正确填写手机号码！");
                }else{
                      $("#u_cellPhone").attr("class", "formtips");
	                  $("#u_cellPhone").html(""); 
	                   if($("#clickCode").html()!="重新发送验证码"||$("#clickCode").html()!="点击获取验证码") { 
	                     //当处在发送中的状态时候
	                    window.clearInterval(tipId);
	                    $("#clickCode").html("点击获取验证码");
	                    flags = false;
		                $.post("removeCode.do","",function(data){});//删除手机验证码
		                 }
	                   }
		        
                });
		       //.......................
		       $("#clickCode").click(function(){
		           var phone=$("#cellPhone").val();
		           //验证手机号码是
		           if($("#cellPhone").val()==""){
                      $("#u_cellPhone").attr("class", "formtips onError");
		              $("#u_cellPhone").html("请填写手机号码！");
                   }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
                      $("#u_cellPhone").attr("class", "formtips onError");
	                  $("#u_cellPhone").html("请正确填写手机号码！");
                   }else{
                      $("#u_cellPhone").attr("class", "formtips");
	                  $("#u_cellPhone").html(""); 
	                 if($("#clickCode").html()=="重新发送验证码"||$("#clickCode").html()=="点击获取验证码") {  
		                 if(flags) return;
	                    $.post("sendSMS.do","phone="+phone,function(data){
	                       if(data==1){
	                          timers=180;
	                          flags = true;
	                          tipId=window.setInterval("timer()",1000);
	                       }else{
	                          alert("手机验证码发送失败");
	                       }
	                    });
	                  }
                   }
		       });
		   });
		   
		   //定时
		   function timer(){
		    
		       if(timers>=0){
		         $("#clickCode").html("验证码获取："+timers+"秒");
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		           $("#clickCode").html("重新发送验证码");
		           flags = false;
		           $.post("removeCode.do","",function(data){});
		           
		       }
		   }
		</script>
		<script>

//======
$(document).ready(function(){
    $("#BaseDataform :input").blur(function(){
    //验证手机号码
      if($(this).attr("id")=="cellPhone"){
      if( $(this).val() ==""){
         $("#u_cellPhone").attr("class", "formtips onError");
		 $("#u_cellPhone").html("请填写手机号码！");
      }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
       $("#u_cellPhone").attr("class", "formtips onError");
	     $("#u_cellPhone").html("请正确填写手机号码！");
      }else{
           $("#u_cellPhone").attr("class", "formtips");
	       $("#u_cellPhone").html(""); 
      }
  }
  //真实姓名
  		if($(this).attr("id")=="realName"){  
  		        var isName = new Object();
  		        isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
				if($(this).val() ==""){
			      	$("#u_realName").attr("class", "formtips onError");
			      	$("#u_realName").html("请填写真实姓名！");
			    }else if($(this).val().length <2 || $(this).val().length> 20){   
	            	$("#u_realName").attr("class", "formtips onError");
	                $("#u_realName").html("名字长度为2-20个字符"); 
	            }else if(!isName.test($(this).val())){
	                  $("#u_realName").html("真实姓名输入有误"); 
	            }
	            else{   
	            	$("#u_realName").attr("class", "formtips");
	                $("#u_realName").html(""); 
	            } 
          }
  //========
  //身份号码
  if($(this).attr("id")=="idNo"){
     var isIDCard1 = new Object();
     var isIDCard2 = new Object();
     //身份证正则表达式(15位) 
     isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
     //身份证正则表达式(18位) 
     isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/; 
    if($(this).val() ==""){
       //$("#u_idNo").attr("class", "formtips onError");
      $("#u_idNo").html("请填写身份证号码");
    }else if(isIDCard1.test($(this).val())||isIDCard2.test($(this).val())){
           //验证身份证号码的有效性
      var parama = {};
      parama["paramMap.idNo"] = $(this).val();
      $.post("isIDNO.do",parama,function(data){
       if(data.putStr=="0"){
        $("#u_idNo").html("请填写身份证号码");
      }else if(data.putStr=="1"){
           $("#u_idNo").html("身份证号码不合法!");
      }else{
        $("#u_idNo").html("");
      }
    });
    }else {
        $("#u_idNo").html("身份证号码不正确");
    }
  }
  //========== 验证出生年月
      if($(this).attr("id")=="birthday"){
    if($(this).val() !=""){
       $("#u_birthday").html("");
    }
  }

  //===========验证毕业院校
      if($(this).attr("id")=="school"){
    if($(this).val() ==""){
    $("#u_school").attr("class", "formtips onError");
    $("#u_school").html("请填写毕业院校");
    }else{
    $("#u_school").attr("class", "formtips");
       $("#u_school").html("");
    }
  }
 //==手机验证码
       if($(this).attr("id")=="vilidataNum"){
    if($(this).val() ==""){
    $("#u_vilidataNum").attr("class", "formtips onError");
    $("#u_vilidataNum").html("请填写手机验证码");
    }else{
    $("#u_vilidataNum").attr("class", "formtips");
       $("#u_vilidataNum").html("");
    }
  }
 //=========最高学历
        if($(this).attr("id")=="highestEdu"){
    if($(this).val() ==""){
    $("#u_highestEdu").attr("class", "formtips onError");
    $("#u_highestEdu").html("请填写最高学历");
    }else{
    $("#u_highestEdu").attr("class", "formtips");
       $("#u_highestEdu").html("");
    }
  }
 //============入学年份
      if($(this).attr("id")=="eduStartDay"){
    if($(this).val() !=""){
       $("#u_eduStartDay").html("");
    }
  }
 //=====籍　　贯

    if($(this).attr("id")=="nativePlacePro"){
    if($(this).val() ==""){
    $("#u_Pro_City").attr("class", "formtips onError");
    $("#u_Pro_City").html("请填写籍贯");
    }else{
    $("#u_Pro_City").attr("class", "formtips");
       $("#u_Pro_City").html("");
    }
  }
         if($(this).attr("id")=="nativePlaceCity"){
    if($(this).val() ==""){
    $("#u_Pro_City").attr("class", "formtips onError");
    $("#u_Pro_City").html("请填写籍贯");
    }else{
    $("#u_Pro_City").attr("class", "formtips");
       $("#u_Pro_City").html("");
    }
  }

 //======户口所在地
       if($(this).attr("id")=="registedPlacePro"){
    if($(this).val() ==""){
    $("#u_reg_Pro_City").attr("class", "formtips onError");
    $("#u_reg_Pro_City").html("请填写户口所在地");
    }else{
    $("#u_reg_Pro_City").attr("class", "formtips");
       $("#u_reg_Pro_City").html("");
    }
  }
         if($(this).attr("id")=="registedPlaceCity"){
    if($(this).val() ==""){
    $("#u_reg_Pro_City").attr("class", "formtips onError");
    $("#u_reg_Pro_City").html("请填写户口所在地");
    }else{
    $("#u_reg_Pro_City").attr("class", "formtips");
       $("#u_reg_Pro_City").html("");
    }
  }
 
 //============居住地址
        if($(this).attr("id")=="address"){
    if($(this).val() ==""){
    $("#u_address").attr("class", "formtips onError");
    $("#u_address").html("请填写居住地址");
    }else{
    $("#u_address").attr("class", "formtips");
       $("#u_address").html("");
    }
  }
 //================居住电话
        if($(this).attr("id")=="telephone"){
            var mdd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
    if($(this).val() ==""){
    $("#u_telephone").attr("class", "formtips onError");
    $("#u_telephone").html("请填写居住电话");
    }else if(!mdd.test($(this).val())){
       $("#u_telephone").attr("class", "formtips onError");
       $("#u_telephone").html("请填写正确的居住电话"	);
    }else{
    $("#u_telephone").attr("class", "formtips");
       $("#u_telephone").html("");
    }
  }
 //============
     });
      
     		$("#province").change(function(){
			var provinceId = $("#province").val();
			citySelectInit(provinceId, 2);
			//$("#area").html("<option value='-1'>--请选择--</option>");
		});
			 $("#registedPlacePro").change(function(){
			var provinceId = $("#registedPlacePro").val();
			registedPlaceCity(provinceId, 2);
			//$("#area").html("<option value='-1'>--请选择--</option>");
		});
		
     
});

	function registedPlaceCity(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#registedPlaceCity").html(_array.join(""));
		});
	}




	function citySelectInit(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#city").html(_array.join(""));
		});
	}
	
	
	//提交基础资料
	  $("#jc_btn").click(function(){
	  

	  
	  
	     var tsex = '${map.sex}'==''?'':'${map.sex}';
	     if($("#realName").val()==""){
	    // $("#u_realName").html("请填写真实姓名！");
	     alert("请填写真实姓名！");
	     return false 
	     }
	     if($("#idNo").val()==""){
	    // $("#u_idNo").html("请填写身份号码！");
	     alert("请填写身份号码！");
	     return false
	      }
	     if($("#cellPhone").val()==""){
	     //$("#u_cellPhone").html("请填写手机号码！");
	      alert("请填写手机号码！");
	     return false 
	     }
	     if($("#vilidataNum").val()==""){ 
	    // $("#u_vilidataNum").html("请填写手机验证码");
	      alert("请填写手机验证码！");
	      return false
	      }
	      
	      
	     if(tsex==''){ 
         if(($("input[name='paramMap_sex']:checked").val())==undefined||($("input[name='paramMap_sex']:checked").val())==""){
	      //$("#u_sex").html("请填写你的性别");
	       alert("请勾选你的性别");
	      return false;
	      }
	      }else{
	      
	       if($("#sex").val()==""){ 
	    // $("#u_vilidataNum").html("请填写手机验证码");
	          alert("请勾选你的性别");
	      return false
	      }
	      
	      
	      }

       if($("#birthday").val() ==""){
     $("#u_birthday").html("请填写出生年月");
       alert("请填写出生年月");
       return false;
     }else if(!(!/^[1,2][0-9]\d{2}$/.test($("#birthday").val()))){
       //  $("#u_birthday").attr("class", "formtips onError");
     $("#u_birthday").html("年份格式错误");
       alert("出生年月年份格式错误");
       return false;
    }
	     if($("#highestEdu").val()==""){
	     //$("#u_highestEdu").html("请填写最高学历！");
	      alert("请填写最高学历！");
	      return false 
	     }

	     
	     
	  if($("#eduStartDay").val() ==""){
	  $("#u_eduStartDay").html("请填写入学年月");
       alert("请填写入学年月");
       return false;
     }else if(!(!/^[1,2][0-9]\d{2}$/.test($("#eduStartDay").val()))){
      $("#u_eduStartDay").html("入学年月格式错误");
       alert("入学年月格式错误");
       return false;
      }
	     
	     
	     if($("#school").val()==""){
	     //$("#u_school").html("请填写毕业院校!");
	     alert("请填写毕业院校!");
	     return false 
	     }
	     if(($("input[name='paramMap_maritalStatus']:checked").val())==undefined||($("input[name='paramMap_maritalStatus']:checked").val())==""){
	      //$("#u_maritalStatus").html("请填写你结婚状态");
	      alert("请填写你的结婚状态!");
	      return false;
	     }
	      if(($("input[name='paramMap_hasChild']:checked").val())==undefined||($("input[name='paramMap_hasChild']:checked").val())==""){
	     // $("#u_hasChild").html("请填写你是否有孩子");
	      alert("请填写你是否有孩子");
	      return false;
	    }
	        if(($("input[name='paramMap_hasHourse']:checked").val())==undefined||($("input[name='paramMap_hasHourse']:checked").val())==""){
	      //$("#u_hasHourse").html("请填写你是否有房子");
	      alert("请填写你是否有房子");
	      return false;
	    }
	        if(($("input[name='paramMap_hasHousrseLoan']:checked").val())==undefined||($("input[name='paramMap_hasHousrseLoan']:checked").val())==""){
	    //  $("#u_hasHousrseLoan").html("请填写你是否有房贷");
	       alert("请填写你是否有房贷");
	      return false;
	    }
	     if(($("input[name='paramMap_hasCar']:checked").val())==undefined||($("input[name='paramMap_hasCar']:checked").val())==""){
	      //$("#u_hasCar").html("请填写你是否有车");
	        alert("请填写你是否有车");
	      return false;
	    }
	     if(($("input[name='paramMap_hasCarLoan']:checked").val())==undefined||($("input[name='paramMap_hasCarLoan']:checked").val())==""){
	     // $("#u_hasCarLoan").html("请填写你是否有车贷");
	      alert("请填写你是否有车贷");
	      return false;
	    }
	    
	    if($('#province').val()==-1){
	    //$('#u_Pro_City').html('请选择你的省份');
	     alert("请选择你的籍贯省份");
	     return false;
	     }
	    
	     if($('#city').val()==-1){
	    //$('#u_Pro_City').html('请选择你的城市') ;
	     alert("请选择你的籍贯城市");
	     return false;
	     }
	    if($('#registedPlacePro').val()==-1){
	    //$('#u_reg_Pro_City').html('请选择你的省份');
	     alert("请选择你的户口省份");
	     return false;}
	  if($('#registedPlaceCity').val()==-1){
	 // $('#u_reg_Pro_City').html('请选择你的城市');
	     alert("请选择你的户口城市");
	    return false;
	   }
	   
	 if($('#address').val()==""){
	     alert("请填写居住地址");
	    return false;
	   }
	   
	   
	   if($("#img").attr("src")==""){
	       alert("请上传你的个人头像");
	       return false;
	   }

	   var param = {};
	    param["paramMap.realName"]=$("#realName").val();
	    param["paramMap.idNo"]=$("#idNo").val();
	    param["paramMap.cellPhone"]=$("#cellPhone").val();
	    param["paramMap.vilidataNum"]=$("#vilidataNum").val();
	    
	    
	    if(tsex==''){
	    param["paramMap.sex"]=$("input[name='paramMap_sex']:checked").val();
	    }else{
	     param["paramMap.sex"]=$("#sex").val();
	    }
	   // alert($("input[name='paramMap_sex']:checked").val());
	   /* if(($("input[name='paramMap_sex']:checked").val())=='undefinded'){
	     alert("填写");
	    }
	    */
	    param["paramMap.isDemo"]=$("#isDemo").val();
	    param["paramMap.birthday"]=$("#birthday").val();
	    param["paramMap.highestEdu"]=$("#highestEdu").val();
	    param["paramMap.eduStartDay"]=$("#eduStartDay").val();
	    param["paramMap.school"]=$("#school").val();
	    param["paramMap.maritalStatus"]=$("input[name='paramMap_maritalStatus']:checked").val();
	    param["paramMap.hasChild"]=$("input[name='paramMap_hasChild']:checked").val();
	    param["paramMap.hasHourse"]=$("input[name='paramMap_hasHourse']:checked").val();
	    param["paramMap.hasHousrseLoan"]=$("input[name='paramMap_hasHousrseLoan']:checked").val();
	    param["paramMap.hasCar"]=$("input[name='paramMap_hasCar']:checked").val();
	    param["paramMap.hasCarLoan"]=$("input[name='paramMap_hasCarLoan']:checked").val();
	    param["paramMap.nativePlacePro"]=$("#province").val();
	    param["paramMap.nativePlaceCity"]=$("#city").val();
	   
	    param["paramMap.registedPlacePro"]=$("#registedPlacePro").val();
	    param["paramMap.registedPlaceCity"]=$("#registedPlaceCity").val();
	    
	    param["paramMap.address"]=$("#address").val();
	    param["paramMap.telephone"]=$("#telephone").val();
	    param["paramMap.iscellPhone"]=$("#iscellPhone").val();
	    //用户头像路径
	    param["paramMap.personalHead"]=$("#img").attr("src");
	    param["paramMap.num"]="2";
	    $.post("updateBasedate.do",param,function(data){
	       if(data.msg=="保存成功"){
	         window.clearInterval(tipId);
	         $("#clickCode").html("点击获取验证码");
	         var from = '${from}';
	         var btype = '${btype}';
	         alert("保存成功");
	         if(from != ""){//如果是从发布借款跳转过来，那么填写个人资料完成之后跳到申请VIP modify by houli
	           window.location.href='quervipData.do?from='+from+"&btype="+btype;
	         }else{//如果不是从发布借款跳转过来，那么按照正常的步骤一步一步走
	        	window.location.href='querWorkData.do';
	        }
	       }else{
	         //alert("请正确填写基本资料");
	         if(data.msg=="请正确填写真实名字"){
	         //$("#u_realName").html("请填写真实姓名！")
	         alert("请填写真实姓名！");
	       }
	            if(data.msg=="真实姓名的长度为不小于2和大于20"){
	            //$("#u_realName").html("真实姓名的长度为不小于2和大于20！")
	             alert("真实姓名的长度为不小于2和大于20！");
	       }
	            if(data.msg=="请正确身份证号码"){
	        // $("#u_idNo").html("请正确身份证号码！")
	         alert("请正确输入你的身份证号码");
	       }
	            if(data.msg=="请正确填写手机号码"){
	           // $("#u_cellPhone").html("请填写手机号码！");
	              alert("请填写手机号码!");
	       }
	       if(data.msg=="手机号码长度不对"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码长度不对！");
	       }
	       
	       if(data.msg=="手机已存在"){
	        alert("该手机号码已经被注册过了！");
	       }
	       
	       
	       
 //........................................... 
	       //手机验证码
	       if(data.msg=="与获取验证码手机号不一致"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码与获取验证码手机号不一致！");
	       }
	       if(data.msg=="请填写验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请填写验证码");
	       }
	       if(data.msg=="请重新发送验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请重新发送验证码");
	       }
	       
	        if(data.msg=="请输入正确的验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请输入正确的验证码");
	       }
	       
	       
	       }
	       if(data.msg=="请正确填写手机号码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机验证码填写错误！");
	       }
	      if(data.msg=="请正确填写性别"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请勾选填写性别！");
	       }
	       if(data.msg=="请正确填写出生日期"){
	         //$("#u_birthday").html("请正确填写出生日期！")
	         alert("请正确填写出生日期！");
	       }
	       if(data.msg=="请正确填写入学年份"){
	        // $("#u_eduStartDay").html("请正确填写入学年份！")
	         alert("请正确填写入学年份！");
	       }
	             if(data.msg=="请正确填写入毕业院校"){
	        // $("#u_school").html("请正确填写入毕业院校！")
	          alert("请正确填写入毕业院校！");
	       }
	             if(data.msg=="请正确填写入学年份"){
	       //  $("#u_eduStartDay").html("请正确填写入学年份！")
	        alert("请正确填写入学年份！");
	       }
	        if(data.msg=="请正确填写最高学历"){
	         //$("#u_highestEdu").html("请正确填写最高学历！")
	          alert("请正确填写最高学历！");
	       }
	         if(data.msg=="请正确填写入籍贯省份"){
	        // $("#u_nativePlacePro").html("请正确填写入籍贯省份！")
	         alert("请正确填写入籍贯省份！");
	       }
	       if(data.msg=="请正确填写入籍贯城市"){
	         //$("#u_nativePlaceCity").html("请正确填写入籍贯城市！")
	            alert("请正确填写入籍贯城市！");
	       }
             if(data.msg=="请正确填写入户口所在地省份"){
	       //  $("#u_registedPlacePro").html("请正确填写入户口所在地省份！")
	          alert("请正确填写入户口所在地省份!");
	       }
	                 if(data.msg=="请正确填写入户口所在地城市"){
	        // $("#u_registedPlaceCity").html("请正确填写入户口所在地城市！")
	           alert("请正确填写入户口所在地城市!");
	       }
	        if(data.msg=="请正确填写入你的家庭电话"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	        alert("请正确填写入你的家庭电话！");
 
	       }
	      if(data.msg=="你的家庭电话输入不正确"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	         alert("请正确填写入你的居住电话");
	       }
	      
	      if(data.msg=="你的居住电话输入长度不对"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	         alert("你的居住电话输入长度不对");
	       }
	       
	    
	           if(data.msg=="请正确上传你的个人头像"){
	         //$("#u_img").html("个人头像不能为空！")
	          alert("个人头像不能为空！");
	       }
	       if(data.msg=="超时请重新登录"){
	          window.location.href='login.do';
	       }
	       if(data.msg=="身份证不合法"){
	        $("#u_idNo").html("请输入正确身份证号码！")
	       }
	       if(data.msg=="身份证已注册"){
		        $("#u_idNo").html("该身份证已经注册!")
		   }
	    });
	    
	});
</script>

		<script>
			$(function(){
				//上传图片
				$("#btn_personalHead").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					 window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
					var headImgPath = $("#img").attr("src");
					if(headImgPath!=""){
						//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
						 $("#u_img").html("");
					}else{ 
						alert("上传失败！");	
					}
				});
				
			});
			
			function uploadCall(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "upload/"+basepath+"/"+fileName;
					$("#img").attr("src",path);
					$("#setImg").attr("src",path);
		  		}
			}
			
			function getDirNum(){
		      var date = new Date();
		 	  var m = date.getMonth()+1;
		 	  var d = date.getDate();
		 	  if(m<10){
		 	  	m = "0"+m;
		 	  }
		 	  if(d<10){
		 	  	d = "0"+d;
		 	  }
		 	  var dirName = date.getFullYear()+""+m+""+d;
		 	  return dirName; 
			}
			//======================工作认证
			
			//===============|~
		</script>
			<script type="text/javascript">
		  $(function(){
		     //样式选中
   dqzt(2)
   var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);

		  });
		</script>
	</body>
</html>
