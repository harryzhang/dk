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
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
	

		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="10%" border="0" cellspacing="0" cellpadding="0" >
							<tr>
							<td width="100" height="28" class="main_alll_h2">
									<a href="emailAndMessageindex.do">用户基本信息</a>
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
						
						  <td  style="width: 40%"  valign="top">
						  <div style="margin-top: 0px;">
						<table  border="1" cellspacing="1" cellpadding="3" width="100%">
							<tr>
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
								<td align="left" class="f66">	
										 ${UserMsgmap.realName}	
								</td> 							
							
							</tr>
							<tr>
							       <td  align="right"
									class="blue12">
									邮箱：
								</td>
								<td align="left" class="f66">
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
									class="blue12">
									手机：
								</td>
								<td align="left" class="f66">
									 ${ UserMsgmap.telephone}
								</td> 	
							
							</tr>
							
							
						</table>
						</div>
						 </td>
						<!--第二个table  -->
						
						<td id="secodetble" style="width: 60%" colspan="2">
						  <table  border="0" cellspacing="1" cellpadding="3" width="60%">
							<tr>
							<td colspan="2">
						    <table>
							 <tr><td class="xxk_all_a" height="20" align="center" width="100px">
							<a style="cursor: pointer;" onclick="javascript:window.location.href='queryUserManageBaseInfoinner.do?i=${UserMsgmap.id}';">个人基本信息</a></td><td>&nbsp;</td>
							<td class="main_alll_h2" width="100px" height="20"  align="center">
							<a style="cursor: pointer;" id="li_work" >工作信息</a></td>
							<td>&nbsp;</td>
							<td class="xxk_all_a1" width="100px" height="20" align="center">
							<a href="queryUserManageInvest.do?i=${map.userId}" style="cursor: pointer;">投资信息</a>
							</td>
							</tr>
							</table>
							</td>
							</tr>
						<!-- 
							<tr>
								<td  align="center"
									class="blue12"  bgcolor="#8594A9">
								<a style="cursor: pointer;" onclick="javascript:window.location.href='queryUserManageBaseInfoinner.do?i=${UserMsgmap.id}';">个人基本信息</a>
								</td>
								<td align="center"  bgcolor="#CC0000" style=" font-size: 12px;font-family:serif;" width="10">
								<a style="cursor: pointer;">工作信息</a>
								</td>
							</tr>	
							-->
							<td align="center"><table><tr>
							<tr>
                                 <td  align="right"
									class="blue12">
							单位名称：
								</td>
								<td align="left" class="f66">			
											<input type="text" name="paramMap.orgName" id="orgName"
														class="inp188" value="${map_work.orgName}" disabled="disabled"/>
								</td> 							
							
							</tr>
							<tr>
							       <td  align="right"
									class="blue12">
										职业状态：
								</td>
								<td align="left" class="f66">
								<select name="paramMap.occStatus" id="occStatus" disabled="disabled">
														<option value="" 
														<s:if test='#request.map.occStatus == ""'>selected="selected"</s:if>
														<s:else></s:else>
														>
															请选择
														</option>
														<option value="工薪阶层" 
														<s:if test='#request.map.occStatus == "工薪阶层"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															工薪阶层
														</option>
														<option value="私营企业主"
														<s:if test='#request.map.occStatus == "私营企业主"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															私营企业主
														</option>
														<option value="网络商家"
														<s:if test='#request.map.occStatus == "网络商家"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															网络商家
														</option>
														<option value="其他"
														<s:if test='#request.map.occStatus == "其他"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															其他
														</option>
													</select>
								</td> 	
							
							</tr>
								<tr>
							       <td align="right"
									class="blue12">
								工作城市：
								</td>
								<td align="left" class="f66">
								<s:select id="province" name="workPro" disabled="true"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
															<s:select id="workCity"   name="cityId" disabled="true"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.cityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
												
								</td> 	
							
							</tr>
							
								
							
							<tr>
							       <td  align="right"
									class="blue12">
									公司类别：
								</td>
								<td align="left" class="f66">
								<select id="companyType" name="paramMap.companyType" disabled="disabled">
														<option value=""
														<s:if test='#request.map.companyType == ""'>selected="selected"</s:if>
														<s:else></s:else>
														>
															请选择
														</option>
														<option value="事业单位"
															<s:if test='#request.map.companyType == "事业单位"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															事业单位
														</option>
														<option value="国家单位"
														<s:if test='#request.map.companyType == "国家单位"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															国家单位
														</option>
														<option value="央企(包括下级单位)"
															<s:if test='#request.map.companyType == "央企(包括下级单位)"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															央企(包括下级单位)
														</option>
														<option value="地方国资委直属企业"
															<s:if test='#request.map.occStatus == "地方国资委直属企业"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															央企(包括下级单位)
														</option>
														<option value="世界500强(包括合资企业及下级单位)"
															<s:if test='#request.map.companyType == "世界500强(包括合资企业及下级单位)"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															世界500强(包括合资企业及下级单位)
														</option>
														<option value="外资企业(包括合资企业)"
														
															<s:if test='#request.map.companyType == "外资企业(包括合资企业)"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															外资企业(包括合资企业)
														</option>
														<option value="一般上市公司(包括国外上市公司)"
														<s:if test='#request.map.companyType == "一般上市公司(包括国外上市公司)"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															一般上市公司(包括国外上市公司)
														</option>
														<option value="一般民营企业"
														<s:if test='#request.map.companyType == "一般民营企业"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															一般民营企业
														</option>
													</select>
								</td> 	
							
							</tr>
							
							
							<tr>
								<td height="36" align="right" class="blue12">
									公司行业：
								</td>
								<td>
								<select id="companyLine" name="paramMap.companyLine" disabled="disabled"> 
														<option value=""
														<s:if test='#request.map.companyLine == ""'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															请选择
														</option>
														<option value="事业单位"
														
														<s:if test='#request.map.companyLine == "事业单位"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															事业单位
														</option>
														<option value="国家单位"
														<s:if test='#request.map.companyLine == "国家单位"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															国家单位
														</option>
														<option value="央企(包括下级单位)"
														<s:if test='#request.map.companyLine == "央企(包括下级单位)"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															央企(包括下级单位)
														</option>
														<option value="地方国资委直属企业"
														<s:if test='#request.map.companyLine == "地方国资委直属企业"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															央企(包括下级单位)
														</option>
														<option value="世界500强(包括合资企业及下级单位)"
														<s:if test='#request.map.companyLine == "世界500强(包括合资企业及下级单位)"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															世界500强(包括合资企业及下级单位)
														</option>
														<option value="外资企业(包括合资企业)"
														<s:if test='#request.map.companyLine == "外资企业(包括合资企业)"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															外资企业(包括合资企业)
														</option>
														<option value="一般上市公司(包括国外上市公司)"
														<s:if test='#request.map.companyLine == "一般上市公司(包括国外上市公司)"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															一般上市公司(包括国外上市公司)
														</option>
														<option value="一般民营企业"
														<s:if test='#request.map.companyLine == "一般民营企业"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															一般民营企业
														</option>
													</select>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								公司规模：
								</td>
								<td>
								<select name="paramMap.companyScale" id="companyScale" disabled="disabled">
														<option value="50人以下"
														<s:if test='#request.map.companyScale == "50人以下"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															50人以下
														</option>
														<option value="50-100人"
														<s:if test='#request.map.companyScale == "50-100人"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															50-100人
														</option>
														<option value="100-500人"
														<s:if test='#request.map.companyScale == "100-500人"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															100-500人
														</option>
														<option value="500人以上"
														<s:if test='#request.map.companyScale == "500人以上"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															500人以上
														</option>
													</select>
												
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								职 位：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.job" disabled="disabled"
														id="job"  value="${map.job }"/>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								月收入：
								</td>
								<td>
								<select name="paramMap.monthlyIncome" id="monthlyIncome" disabled="disabled">
														<option value=""
														<s:if test='#request.map.monthlyIncome == ""'>selected="selected"</s:if>
														<s:else></s:else>
														>
															请选择
														</option>
														<option value="1000以下"
														<s:if test='#request.map.monthlyIncome == "1000以下"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															1000以下
														</option>
														<option value="1000-2000"
														<s:if test='#request.map.monthlyIncome == "1000-2000"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															1000-2000
														</option>
														<option value="2000-5000"
														
														<s:if test='#request.map.monthlyIncome == "2000-5000"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															2000-5000
														</option>
														<option value="5000-10000"
														<s:if test='#request.map.monthlyIncome == "5000-10000"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															5000-10000
														</option>
														<option value="10000-20000"
														<s:if test='#request.map.monthlyIncome == "10000-20000"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															10000-20000
														</option>
														<option value="20000以上"
														<s:if test='#request.map.monthlyIncome == "20000以上"'>selected="selected"</s:if>
														<s:else></s:else>
														
														>
															10000-20000
														</option>
													</select>
													
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							现单位工作年限：
								</td>
								<td>
								<select name="paramMap.workYear" id="workYear" disabled="disabled">
														<option value=""
														<s:if test='#request.map.workYear == ""'>selected="selected"</s:if>
														<s:else></s:else>
														>
															请选择
														</option>
														<option value="1年"
														<s:if test='#request.map.workYear == "1年"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															1年
														</option>
														<option value="1-3年"
														<s:if test='#request.map.workYear == "1-3年"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															1-3年
														</option>
														<option value="3-5年"
														<s:if test='#request.map.workYear == "3-5年"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															3-5年
														</option>
														<option value="5年以上"
														<s:if test='#request.map.workYear == "5年以上"'>selected="selected"</s:if>
														<s:else></s:else>
														>
															5年以上
														</option>
													</select>
													
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
								公司电话：
								</td>
								<td>
								<input type="text" class="inp188" disabled="disabled"
														name="paramMap.companyTel" id="companyTel" value="${map.companyTel }"/>
													
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							工作邮箱：
								</td>
								<td>
										<input type="text" class="inp188" name="paramMap.workEmail" disabled="disabled"
														id="workEmail" value="${map.workEmail }"/>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							公司地址：
								</td>
								<td>
										<input type="text" class="inp188"
														name="paramMap.companyAddress" id="companyAddress"  value="${map.companyAddress }" disabled="disabled"/>
								</td>
							</tr>
								<tr>
									<th colspan="2" align="left">
													直系亲属联系人（在您贷款成功后，我们会通过电话核实您的紧急联系人信息）
												</th>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							姓 名：
								</td>
								<td>
								<input type="text" class="inp188"
														name="paramMap.directedName" id="directedName" value="${map.directedName }" disabled="disabled"/>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							关 系：
								</td>
								<td>
								<input type="text" class="inp188"
														name="paramMap.directedRelation" id="directedRelation" value="${map.directedRelation }" disabled="disabled"/>
								</td>
							</tr>
								<tr>
								<td height="36" align="right" class="blue12">
							手 机：
								</td>
								<td>
									<input type="text" class="inp188"
														name="paramMap.directedTel" id="directedTel" value="${map.directedTel }" disabled="disabled"/>
								</td>
							</tr>
							
										<tr>
								<th colspan="2" align="left">
													其他联系人
												</th>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
							姓 名：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.otherName"
														id="otherName"  value="${map.otherName }" disabled="disabled"/>
								</td>
							</tr>
										<tr>
								<td height="36" align="right" class="blue12">
							关 系：
								</td>
								<td>
								<input type="text" class="inp188"
														name="paramMap.otherRelation" id="otherRelation" value="${map.otherRelation }" disabled="disabled"/>
								</td>
							</tr>
										<tr>
								<td height="36" align="right" class="blue12">
							手 机：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.otherTel"
														id="otherTel" value="${map.otherTel }"  disabled="disabled"/>
								</td>
							</tr>
							<tr>
									<th colspan="2" align="left">
													其他联系人
												</th>
							</tr>
												<tr>
								<td height="36" align="right" class="blue12">
							姓 名：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.moredName"
														id="moredName" value="${map.moredName }" disabled="disabled"/>
								</td>
							</tr>
												<tr>
								<td height="36" align="right" class="blue12">
						关 系：
								</td>
								<td>
									<input type="text" class="inp188"
														name="paramMap.moredRelation" id="moredRelation" value="${map.moredRelation }" disabled="disabled"/>
								</td>
							</tr>
							
															<tr>
								<td height="36" align="right" class="blue12">
					手 机：
								</td>
								<td>
								<input type="text" class="inp188" name="paramMap.moredTel"
														id="moredTel" value="${map.moredTel }" disabled="disabled"/>
								</td>
							</tr>
							
							
							
							<tr>
								<td colspan="2"><img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						 </td>
						
					<!--  -->	
						
						</table></td>
						
						
						
						
						
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
