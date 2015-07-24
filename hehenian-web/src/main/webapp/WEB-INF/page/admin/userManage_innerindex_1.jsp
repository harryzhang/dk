<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
		<script>
		    $(function(){
			    if($("#img").val() == ''){
			    	$("#img").attr("src","../images/NoImg.GIF");
				    }	        
		         
		       }); 
		       
		       $("#li_work").click(function() {
		       alert("3435435");
			window.location.href = 'queryUserMangework.do?uid=${map.userId}';
		});
		</script>

		
	</head>
	<body style="min-width: 1000px;">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="10%" border="0" cellspacing="0" cellpadding="0">
							<tr >
							<td width="100%" height="28" class="main_alll_h2">
									<a href="javascript:void(0)">用户基本信息</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<!--第1个table  -->
						
						  <td  style="width: 500px"  valign="top">
						  <div style="margin-top: 0px;">
						<table  border="1" cellspacing="1" cellpadding="3"  width="100%" style="min-width: 200px">
						
							<tr class="gvItem">
						
								<td  align="right" 
									class="blue12">
									用户名：
								</td>
								<td align="left" width="80">
								 ${ UserMsgmap.username}
								</td>
							</tr>	
							<tr>
                                 <td  align="right" 
									class="blue12">
									真实姓名：
								</td>
								<td align="left" class="f66" >	
										 ${UserMsgmap.realName}	
								</td> 							
							
							</tr>
							<tr>
							       <td  align="right" 
									class="blue12">
									邮箱：
								</td>
								<td align="left" class="f66" >
								 ${ UserMsgmap.email}
								</td> 	
								
							</tr>
							
								<tr>
							       <td align="right" 
									class="blue12">
								身份证：
								</td>
								<td align="left" class="f66">
									 ${ UserMsgmap.idNo}
								</td> 	
							
							</tr>
							
								
							
							<tr>
							       <td  align="right"
									class="blue12" >
									手机：
								</td>
								<td align="left" class="f66">
									 ${ UserMsgmap.telephone}
								</td> 	
							
							</tr>
							
							<tr>
							       <td  align="right"
									class="blue12" >
									公司电话：
								</td>
								<td align="left" class="f66">
								<input type="text"
														name="paramMap.companyTel" id="companyTel" value="${map.companyTel }"/>
								</td> 
							</tr>
							
						</table>
						</div>
						 </td>
						<!--第二个table  -->
						<td id="secodetble" style="width: 60%">
						<table  border="0" cellspacing="1" cellpadding="3" width="60%">
							<tr>
							<td>
						    <table>
							 <tr><td class="main_alll_h2" width="100px" height="20"  align="center">
							<a style="cursor: pointer;">个人基本信息</a></td>
							<td>&nbsp;</td>
							<td class="xxk_all_a" width="100px" height="20" align="center">
							<a style="cursor: pointer;" href="queryUserMangework.do?uid=${map.userId}" >工作信息</a></td>
							<td>&nbsp;</td>
							<td class="xxk_all_a1" width="100px" height="20"  align="center">
							<a href="queryUserManageInvest.do?i=${map.userId}" style="cursor: pointer;">投资信息</a>
							</td>
							</tr>
							</table>
							</td>
							</tr>
							<!-- 
								<td  align="center" colspan="2" height="20px;"
									 bgcolor="#CC0000" style=" font-size: 12px;font-family:serif;" >
								<span style="margin-right: 0px; " align="left" ><a style="cursor: pointer;">个人基本信息</a></span>
								<span> <a style="cursor: pointer;" id="li_work">工作信息</a></span>
								</td>
							 
								<td align="center" class="blue12" bgcolor="#8594A9" width="10" >
								
								</td>
								-->
								
								
								
							<tr>
							<td align="center"><table><tr>
                                 <td  align="right"
									class="blue12">
							 真实姓名：
								</td>
								<td align="left" class="f66">			
										<input type="text" class="inp188" name="paramMap.realName"
														id="realName" value="${map.realName}" disabled="disabled"/>
								</td> 							
							
							</tr>
							<tr>
							       <td  align="right"
									class="blue12">
									身份证号：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.idNo"
														id="idNo" value="${map.idNo }" disabled="disabled"/>
								</td> 	
							
							</tr>
								<tr>
							       <td align="right"
									class="blue12">
									手机号码：
								</td>
								<td align="left" class="f66">
								  	<input type="text" class="inp188" name="paramMap.idNo"
														id="idNo" value="${map.cellPhone }" disabled="disabled"/>
								</td> 	
							
							</tr>
							
								
							
							<tr>
							       <td  align="right"
									class="blue12">
									性 别：
								</td>
								<td align="left" class="f66">
										<input type="radio" name="radio" id="sex" value="男"
														name="paramMap.sex"  disabled="disabled"
													<s:if test='#request.map.sex == "男"'>checked="checked"</s:if>
												<s:else></s:else>
														/>
													男
													<input type="radio" name="radio" id="sex" value="女" disabled="disabled"
														name="paramMap.sex" 	<s:if test='#request.map.sex == "女"'>checked="checked"</s:if>
												<s:else></s:else> />
													女
								</td> 	
							
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
								出生日期：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.birthday" disabled="disabled"
														id="birthday" onclick="selectStartDate();" value="${birth }"/>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							最高学历：
								</td>
								<td>
										<select name="paramMap.highestEdu" id="highestEdu" disabled="disabled" >
														<option value="-1">
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
								
								
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								入学年份：
								</td>
								<td>
								<input type="text" class="inp188"
														name="paramMap.eduStartDay" id="eduStartDay"
														onclick="selectStartDate1();" value="${rxedate }" disabled="disabled"/>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								毕业院校：
								</td>
								<td>
																					<input type="text" class="inp188" name="paramMap.school"
														id="school" value="${map.school }" disabled="disabled"/>
								
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							婚姻状况：
								</td>
								<td>
								<input type="radio" name="paramMap.maritalStatus"  disabled="disabled"
														id="maritalStatus" value="已婚" 
															<s:if test='#request.map.maritalStatus == "已婚"'>checked="checked"</s:if>
														<s:else></s:else>
														
														 />
													已婚
													<input type="radio" name="paramMap.maritalStatus" disabled="disabled"
														id="maritalStatus" value="未婚" 
															<s:if test='#request.map.maritalStatus == "未婚"'>checked="checked"</s:if>
														<s:else></s:else>
														
														/>
													未婚
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								有无子女：
								</td>
								<td>
								<input type="radio" name="paramMap.hasChild" id="hasChild" disabled="disabled"
														value="有" 
															<s:if test='#request.map.hasChild == "有"'>checked="checked"</s:if>
														<s:else></s:else>
														
														 />
													有
													<input type="radio" name="paramMap.hasChild" id="hasChild" disabled="disabled"
														value="无"
														<s:if test='#request.map.hasChild == "无"'>checked="checked"</s:if>
														<s:else></s:else>
														 />
													无
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								是否有房：
								</td>
								<td>
											<input type="radio" name="paramMap.hasHourse" disabled="disabled"
														id="hasHourse" value="有" 
															<s:if test='#request.map.hasHourse == "有"'>checked="checked"</s:if>
														<s:else></s:else>
														
														 />
													有
													<input type="radio" name="paramMap.hasHourse" disabled="disabled"
														id="hasHourse" value="无" 
																<s:if test='#request.map.hasHourse == "无"'>checked="checked"</s:if>
														<s:else></s:else>
														
														/>
													无
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								是否有车：
								</td>
								<td>
										<input type="radio" name="paramMap.hasCar" id="hasCar" disabled="disabled"
														value="有"
															<s:if test='#request.map.hasCar == "有"'>checked="checked"</s:if>
														<s:else></s:else>
														
														/>
													有
													<input type="radio" name="paramMap.hasCar" id="hasCar" disabled="disabled"
														value="无"
														<s:if test='#request.map.hasCar == "无"'>checked="checked"</s:if>
														<s:else></s:else>
														
														 />
													无
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								有无车贷：
								</td>
								<td>
										<input type="radio" name="paramMap.hasCarLoan" disabled="disabled"
														id="hasCarLoan" value="有" 
														
														<s:if test='#request.map.hasCarLoan == "有"'>checked="checked"</s:if>
														<s:else></s:else>
														
														/>
													有
													<input type="radio" name="paramMap.hasCarLoan" disabled="disabled"
														id="hasCarLoan" value="无" 
															<s:if test='#request.map.hasCarLoan == "无"'>checked="checked"</s:if>
														<s:else></s:else>
														
														 />
													无
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							 籍 贯：
								</td>
								<td>
											<s:select id="province" name="workPro" disabled="true"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
													<s:select id="city" name="cityId" disabled="true"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.cityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							户口所在地：
								</td>
								<td>
								<s:select id="registedPlacePro" 
														name="regPro" disabled="true"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
													<s:select id="registedPlaceCity" disabled="true"
														name="regCity"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.regcityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							居住地址：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.address"
														id="address"  value="${ map.address}" disabled="disabled"/>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							居住电话：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.telephone"
														id="telephone" value="${map.telephone}" disabled="disabled"/>
								</td>
							</tr>
							
										<tr>
								<td height="36" align="right" class="blue12">
							个人头像：
								</td>
								<td>
										<s:if test="#request.map.personalHead==null||#request.map.personalHead==''">
								<img id="img" src="../images/NoImg.GIF"
										style="display: block; width: 100px; height: 100px;" />
								</s:if>
								<s:else>
											<img id="img" src="../${map.personalHead}" width="62" height="62"
														name="paramMap.personalHead" />
								</s:else>
								
								</td></tr></table></td>
							</tr>
							
							
							
							
							
							<tr>
								<td colspan="2"><img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						 </td>
						
					<!--  -->	
						
						
						
						
						
						
						
						  </tr>
						</table>
						
						
						<br />
					</div>
				</div>
			</div>
		<script>
		  $(function(){
		     if(${message!=null}){
		       alert('${request.message}');
		      }
		  });
		</script>
	</body>
</html>
