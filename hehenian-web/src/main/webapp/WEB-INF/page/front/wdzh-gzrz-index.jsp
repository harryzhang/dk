<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
		<script language="javascript" type="text/javascript"
			src="My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="nymain">
			<div class="wdzh">
				<div class="l_nav">
					<div class="box">
						<!-- 引用我的帐号主页左边栏 -->
						<%@include file="/include/left.jsp"%>
					</div>
				</div>
				<div class="r_main">
					<div class="tabtil">
						<ul>
							<li  onclick="jumpUrl('owerInformationInit.do');">
								个人详细信息
							</li>
							<li  class="on" onclick="jumpUrl('queryWorkInit.do');">
								工作认证信息
							</li>
							<li onclick="jumpUrl('updatexgmm.do');">
								修改密码
							</li>
							<li id="li_bp" onclick="bindingPhoneLoad('boundcellphone.do');">
								手机绑定
							</li>
<%--							<li onclick="jumpUrl('szform.do');">--%>
<%--								通知设置--%>
<%--							</li>--%>
							<li id="li_tx" onclick="loadBankInfo('yhbound.do');">
								银行卡设置
							</li>
						</ul>
					</div>

					<!-- 工作详细信息 开始 -->
					<div class="box" id="workdi">
						<div class="boxmain2">
							<p class="tips">
								<span class="fred">*</span> 为必填项，所有资料均会严格保密。
							</p>
							<div class="biaoge2">
								<form id="workform">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<th colspan="2" align="left" style="padding-top: 0px;">
												工作信息
											</th>
										</tr>
										<tr>
											<td align="right">
												单位名称：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" name="paramMap.orgName" id="orgName"
													class="inp188" value="${map.orgName}"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_orgName"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												职业状态：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<select name="paramMap.occStatus" id="occStatus"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if>>
													<option value="">
														请选择
													</option>
													<option value="工薪阶层"
														<s:if test='#request.map.occStatus == "工薪阶层"'>selected="selected"</s:if>
														<s:else></s:else>>
														工薪阶层
													</option>
													<option value="私营企业主"
														<s:if test='#request.map.occStatus == "私营企业主"'>selected="selected"</s:if>
														<s:else></s:else>>
														私营企业主
													</option>
													<option value="网络商家"
														<s:if test='#request.map.occStatus == "网络商家"'>selected="selected"</s:if>
														<s:else></s:else>>
														网络商家
													</option>
													<option value="其他"
														<s:if test='#request.map.occStatus == "其他"'>selected="selected"</s:if>
														<s:else></s:else>>
														其他
													</option>
												</select>
												<span style="color: red; float: none;" id="u_occStatus"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												工作城市：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<s:select id="workPro" name="workPro"
													cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
													list="#request.provinceList" listKey="regionId"
													listValue="regionName" headerKey="-1" headerValue="--请选择--" />
												<s:select id="workCity" name="cityId"
													cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
													list="#request.cityList" listKey="regionId"
													listValue="regionName" headerKey="-1" headerValue="--请选择--" />
												<span style="color: red; float: none;" id="u_workPro"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												公司类别：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<select id="companyType" name="paramMap.companyType"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if>>
													<option value="">
														请选择
													</option>
													<option value="事业单位"
														<s:if test='#request.map.companyType == "事业单位"'>selected="selected"</s:if>
														<s:else></s:else>>
														事业单位
													</option>
													<option value="国家单位"
														<s:if test='#request.map.companyType == "国家单位"'>selected="selected"</s:if>
														<s:else></s:else>>
														国家单位
													</option>
													<option value="央企(包括下级单位)"
														<s:if test='#request.map.companyType == "央企(包括下级单位)"'>selected="selected"</s:if>
														<s:else></s:else>>
														央企(包括下级单位)
													</option>
													<option value="地方国资委直属企业"
														<s:if test='#request.map.occStatus == "地方国资委直属企业"'>selected="selected"</s:if>
														<s:else></s:else>>
														地方国资委直属企业
													</option>
													<option value="世界500强(包括合资企业及下级单位)"
														<s:if test='#request.map.companyType == "世界500强(包括合资企业及下级单位)"'>selected="selected"</s:if>
														<s:else></s:else>>
														世界500强(包括合资企业及下级单位)
													</option>
													<option value="外资企业(包括合资企业)"
														<s:if test='#request.map.companyType == "外资企业(包括合资企业)"'>selected="selected"</s:if>
														<s:else></s:else>>
														外资企业(包括合资企业)
													</option>
													<option value="一般上市公司(包括国外上市公司)"
														<s:if test='#request.map.companyType == "一般上市公司(包括国外上市公司)"'>selected="selected"</s:if>
														<s:else></s:else>>
														一般上市公司(包括国外上市公司)
													</option>
													<option value="一般民营企业"
														<s:if test='#request.map.companyType == "一般民营企业"'>selected="selected"</s:if>
														<s:else></s:else>>
														一般民营企业
													</option>
												</select>
												<span style="color: red; float: none;" id="u_companyType"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												公司行业：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<select id="companyLine" name="paramMap.companyLine"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if>>
													<option value="">
														请选择
													</option>
													<option value="农、林、牧、渔业"
														<s:if test='#request.map.companyLine == "农、林、牧、渔业"'>selected="selected"</s:if>
														<s:else></s:else>>
														农、林、牧、渔业
													</option>
													<option value="采矿业"
														<s:if test='#request.map.companyLine == "采矿业"'>selected="selected"</s:if>
														<s:else></s:else>>
														采矿业
													</option>
													<option value="电力、热力、燃气及水的生产和供应业"
														<s:if test='#request.map.companyLine == "电力、热力、燃气及水的生产和供应业"'>selected="selected"</s:if>
														<s:else></s:else>>
														电力、热力、燃气及水的生产和供应业
													</option>
													<option value="制造业"
														<s:if test='#request.map.companyLine == "制造业"'>selected="selected"</s:if>
														<s:else></s:else>>
														制造业
													</option>
													<option value="教育"
														<s:if test='#request.map.companyLine == "教育"'>selected="selected"</s:if>
														<s:else></s:else>>
														教育
													</option>
													<option value="环境和公共设施管理业"
														<s:if test='#request.map.companyLine == "环境和公共设施管理业"'>selected="selected"</s:if>
														<s:else></s:else>>
														环境和公共设施管理业
													</option>
													<option value="建筑业"
														<s:if test='#request.map.companyLine == "建筑业"'>selected="selected"</s:if>
														<s:else></s:else>>
														建筑业
													</option>
													<option value="交通运输、仓储业和邮政业"
														<s:if test='#request.map.companyLine == "交通运输、仓储业和邮政业"'>selected="selected"</s:if>
														<s:else></s:else>>
														交通运输、仓储业和邮政业
													</option>
													<option value="信息传输、计算机服务和软件业"
														<s:if test='#request.map.companyLine == "信息传输、计算机服务和软件业"'>selected="selected"</s:if>
														<s:else></s:else>>
														信息传输、计算机服务和软件业
													</option>
													<option value="批发和零售业"
														<s:if test='#request.map.companyLine == "批发和零售业"'>selected="selected"</s:if>
														<s:else></s:else>>
														批发和零售业
													</option>
													<option value="住宿、餐饮业"
														<s:if test='#request.map.companyLine == "住宿、餐饮业"'>selected="selected"</s:if>
														<s:else></s:else>>
														住宿、餐饮业
													</option>
													<option value="金融、保险业"
														<s:if test='#request.map.companyLine == "金融、保险业"'>selected="selected"</s:if>
														<s:else></s:else>>
														金融、保险业
													</option>

													<option value="房地产业"
														<s:if test='#request.map.companyLine == "房地产业"'>selected="selected"</s:if>
														<s:else></s:else>>
														房地产业
													</option>
													<option value="文化、体育、娱乐业"
														<s:if test='#request.map.companyLine == "文化、体育、娱乐业"'>selected="selected"</s:if>
														<s:else></s:else>>
														文化、体育、娱乐业
													</option>
													<option value="综合（含投资类、主业不明显)"
														<s:if test='#request.map.companyLine == "综合（含投资类、主业不明显)"'>selected="selected"</s:if>
														<s:else></s:else>>
														综合（含投资类、主业不明显)
													</option>
													<option value="其它"
														<s:if test='#request.map.companyLine == "其它"'>selected="selected"</s:if>
														<s:else></s:else>>
														其它
													</option>
												</select>
												<span style="color: red; float: none;" id="u_companyLine"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												公司规模：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<select name="paramMap.companyScale" id="companyScale"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if>>
													<option value="50人以下"
														<s:if test='#request.map.companyScale == "50人以下"'>selected="selected"</s:if>
														<s:else></s:else>>
														50人以下
													</option>
													<option value="50-100人"
														<s:if test='#request.map.companyScale == "50-100人"'>selected="selected"</s:if>
														<s:else></s:else>>
														50-100人
													</option>
													<option value="100-500人"
														<s:if test='#request.map.companyScale == "100-500人"'>selected="selected"</s:if>
														<s:else></s:else>>
														100-500人
													</option>
													<option value="500人以上"
														<s:if test='#request.map.companyScale == "500人以上"'>selected="selected"</s:if>
														<s:else></s:else>>
														500人以上
													</option>
												</select>
												<span style="color: red; float: none;" id="u_companyScale"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												职 位：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188" name="paramMap.job"
													id="job" value="${map.job }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_job"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												月收入：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<select name="paramMap.monthlyIncome" id="monthlyIncome"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if>>
													<option value="">
														请选择
													</option>
													<option value="1000以下"
														<s:if test='#request.map.monthlyIncome == "1000以下"'>selected="selected"</s:if>
														<s:else></s:else>>
														1000以下
													</option>
													<option value="1000-2000"
														<s:if test='#request.map.monthlyIncome == "1000-2000"'>selected="selected"</s:if>
														<s:else></s:else>>
														1000-2000
													</option>
													<option value="2000-5000"
														<s:if test='#request.map.monthlyIncome == "2000-5000"'>selected="selected"</s:if>
														<s:else></s:else>>
														2000-5000
													</option>
													<option value="5000-10000"
														<s:if test='#request.map.monthlyIncome == "5000-10000"'>selected="selected"</s:if>
														<s:else></s:else>>
														5000-10000
													</option>
													<option value="10000-20000"
														<s:if test='#request.map.monthlyIncome == "10000-20000"'>selected="selected"</s:if>
														<s:else></s:else>>
														10000-20000
													</option>
													<option value="20000以上"
														<s:if test='#request.map.monthlyIncome == "20000以上"'>selected="selected"</s:if>
														<s:else></s:else>>
														20000以上
													</option>
												</select>
												<span style="color: red; float: none;" id="u_monthlyIncome"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												现单位工作年限：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<select name="paramMap.workYear" id="workYear"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if>>
													<option value="">
														请选择
													</option>
													<option value="1年"
														<s:if test='#request.map.workYear == "1年"'>selected="selected"</s:if>
														<s:else></s:else>>
														1年
													</option>
													<option value="1-3年"
														<s:if test='#request.map.workYear == "1-3年"'>selected="selected"</s:if>
														<s:else></s:else>>
														1-3年
													</option>
													<option value="3-5年"
														<s:if test='#request.map.workYear == "3-5年"'>selected="selected"</s:if>
														<s:else></s:else>>
														3-5年
													</option>
													<option value="5年以上"
														<s:if test='#request.map.workYear == "5年以上"'>selected="selected"</s:if>
														<s:else></s:else>>
														5年以上
													</option>
												</select>
												<span style="color: red; float: none;" id="u_workYear"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												公司电话：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188" name="paramMap.companyTel"
													id="companyTel" value="${map.companyTel }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												格式：0755-29556666 或 010-83881140&nbsp;
												<span style="color: red; float: none;" id="u_companyTel"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												工作邮箱：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188" name="paramMap.workEmail"
													id="workEmail" value="${map.workEmail }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_workEmail"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												公司地址：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188"
													name="paramMap.companyAddress" id="companyAddress"
													value="${map.companyAddress }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_companyAddress"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<th colspan="2" align="left">
												直系亲属联系人（在您贷款成功后，我们会通过电话核实您的紧急联系人信息）
											</th>
										</tr>
										<tr>
											<td align="right">
												姓 名：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188"
													name="paramMap.directedName" id="directedName"
													value="${map.directedName }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_directedName"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												关 系：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188"
													name="paramMap.directedRelation" id="directedRelation"
													value="${map.directedRelation }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;"
													id="u_directedRelation" class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												手 机：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188"
													name="paramMap.directedTel" id="directedTel"
													value="${map.directedTel }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_directedTel"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<th colspan="2" align="left">
												其他联系人
											</th>
										</tr>
										<tr>
											<td align="right">
												姓 名：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188" name="paramMap.otherName"
													id="otherName" value="${map.otherName }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_otherName"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												关 系：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188"
													name="paramMap.otherRelation" id="otherRelation"
													value="${map.otherRelation }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_otherRelation"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												手 机：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188" name="paramMap.otherTel"
													id="otherTel" value="${map.otherTel }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_otherTel"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<th colspan="2" align="left">
												更多联系人
											</th>
										</tr>
										<tr>
											<td align="right">
												姓 名：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188" name="paramMap.moredName"
													id="moredName" value="${map.moredName }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_moredName"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												关 系：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188"
													name="paramMap.moredRelation" id="moredRelation"
													value="${map.moredRelation }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_moredRelation"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												手 机：
												<span style="color: red; float: none;" class="formtips">*</span>
											</td>
											<td>
												<input type="text" class="inp188" name="paramMap.moredTel"
													id="moredTel" value="${map.moredTel }"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />
												<span style="color: red; float: none;" id="u_moredTel"
													class="formtips"></span>
											</td>
										</tr>
										<tr>
											<td align="right">
												&nbsp;
											</td>
											<td class="txt">
												请确保您填写的资料真实有效，所有资料将会严格保密。
												<br />
												一旦被发现所填资料有虚假，将会影响您在合和年在线的信用，对以后借款造成影响。
											</td>
										</tr>
										<tr>
											<td align="right">
												&nbsp;
											</td>
											<td style="padding-top: 20px;">
												<input type="button" value="保存" class="bcbtn" id="work_btn"
													<s:if test='#request.allworkmap.audi ==12'>disabled="disabled"</s:if> />

											</td>
										</tr>
									</table>
								</form>

							</div>
						</div>
					</div>

					<!-- 工作详细信息 结束 -->
				</div>
				<span id="bankInfo"></span>
			</div>
		</div>
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script type="text/javascript">
	$(function() {
		dqzt(4);
			$('#li_5').addClass('on');
			if ('${allworkmap.audi}' == '12') {
				$("#workPro").attr("disabled", "true");
				$("#workCity").attr("disabled", "true");
			}
			//工作认证
				var bb = '${person}';//填写工作信息必须先填写个人资料
				if (bb == "0") {
					alert("请先完善个人信息");
					window.location.href="owerInformationInit.do";
				} 
		$("#work_btn").click(function() {
			$("#u_directedName").html("");
			$("#u_directedRelation").html("");
			$("#u_directedTel").html("");
			$("#u_otherName").html("");
			$("#u_otherRelation").html("");
			$("#u_otherTel").html("");
			$("#u_moredName").html("");
			$("#u_moredRelation").html("");
			$("#u_moredTel").html("");
			work();
		});
		//单位名称
		/*
		$("#orgName").blur(function() {
			if ($("#orgName").val() == "") {
				$("#u_orgName").html("请填写单位名称!");
			} else {
				$("#u_orgName").html("");
			}
		});
		//职业状态
		$("#occStatus").blur(function() {
			if ($("#occStatus").val() == "") {
				$("#u_occStatus").html("请填写职业状态!");
			} else {
				$("#u_occStatus").html("");
			}
		});*/
		/**  //工作城市  
		 $("#workPro").blur(function(){
		     	if($("#workPro").val()==""){
		     		  $("#u_workPro").html("请填写工作省份!");
		     	}else if ($("#workCity").val==""){
		     	  $("#u_workPro").html("请填写工作城市!");
		     	}else{
		     		$("#u_workPro").html("");
		     	}
		  }); */
		//公司类别
		  /*
		$("#companyType").blur(function() {
			if ($("#companyType").val() == "") {
				$("#u_companyType").html("请填写公司类别!");
			} else {
				$("#u_companyType").html("");
			}
		});
		//公司行业
		$("#companyLine").blur(function() {
			if ($("#companyLine").val() == "") {
				$("#u_companyLine").html("请填写公司行业!");
			} else {
				$("#u_companyLine").html("");
			}
		});
		//公司规模
		$("#companyScale").blur(function() {
			if ($("#companyScale").val() == "") {
				$("#u_companyScale").html("请填写公司规模!");
			} else {
				$("#u_companyScale").html("");
			}
		});
		//职位
		$("#job").blur(function() {
			if ($("#job").val() == "") {
				$("#u_job").html("请填写工作职位!");
			} else {
				$("#u_job").html("");
			}
		});
		//月收入 
		$("#monthlyIncome").blur(function() {
			if ($("#monthlyIncome").val() == "") {
				$("#u_monthlyIncome").html("请填写月收入!");
			} else {
				$("#u_monthlyIncome").html("");
			}
		});
		//工作年限 
		$("#workYear").blur(function() {
			if ($("#workYear").val() == "") {
				$("#u_workYear").html("请填写现单位工作年限!");
			} else {
				$("#u_workYear").html("");
			}
		});
		//公司电话  
		$("#companyTel").blur(function() {
			if ($("#companyTel").val() == "") {
				$("#u_companyTel").html("请填写公司电话号码!");
			} else {
				$("#u_companyTel").html("");
			}
		});
		//公司地址 
		$("#companyAddress").blur(function() {
			if ($("#companyAddress").val() == "") {
				$("#u_companyAddress").html("请填写公司地址!");
			} else {
				$("#u_companyAddress").html("");
			}
		});
		//验证邮箱
		$("#workEmail")
				.blur(
						function() {
							if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
									.test($("#workEmail").val())) {
								$("#u_workEmail").html("请填写工作邮箱");
							} else {
								$("#u_workEmail").html("");
							}
						});
		*/
 		$("#workPro").change(function(){
		var provinceId = $("#workPro").val();
		citySelectInit(provinceId, 2);
	});

	});
	//工作认证提交按钮
	function work() {

		var param = {};
		param["paramMap.orgName"] = $("#orgName").val();
		param["paramMap.occStatus"] = $("#occStatus").val();
		param["paramMap.workPro"] = $("#workPro").val();
		param["paramMap.workCity"] = $("#workCity").val();
		param["paramMap.companyType"] = $("#companyType").val();
		param["paramMap.companyLine"] = $("#companyLine").val();
		param["paramMap.companyScale"] = $("#companyScale").val();
		param["paramMap.job"] = $("#job").val();
		param["paramMap.monthlyIncome"] = $("#monthlyIncome").val();
		param["paramMap.workYear"] = $("#workYear").val();
		param["paramMap.companyTel"] = $("#companyTel").val();
		param["paramMap.workEmail"] = $("#workEmail").val();
		param["paramMap.companyAddress"] = $("#companyAddress").val();
		param["paramMap.directedName"] = $("#directedName").val();
		param["paramMap.directedRelation"] = $("#directedRelation").val();
		param["paramMap.directedTel"] = $("#directedTel").val();
		param["paramMap.otherName"] = $("#otherName").val();
		param["paramMap.otherRelation"] = $("#otherRelation").val();
		param["paramMap.otherTel"] = $("#otherTel").val();
		param["paramMap.moredName"] = $("#moredName").val();
		param["paramMap.moredRelation"] = $("#moredRelation").val();
		param["paramMap.moredTel"] = $("#moredTel").val();
		param["paramMap.moredName"] = $("#moredName").val();
		alert("在这");
		$.post("updatework.do", param, function(data) {
			if (data == "virtual") {
				window.location.href = "noPermission.do";
				return;
			}
			if (data.msg == "保存成功") {
				alert("保存成功");
				window.location.href = 'home.do';
			}
			if (data.msg == "vip保存成功") {
				alert("保存成功");
				window.location.href = 'home.do';
			}

			if (data.msg == "请正确填写公司名字") {
			//	$("#u_orgName").html("请正确填写单位名字");
				 alert("请正确填写单位名字");
			}
			if (data.msg == "公司名字长度为不小于2和大于50") {
				// $("#u_orgName").html("真实姓名的长度为不小于2和大于50")
				alert("公司名字长度为不小于2和大于50");
			}
			if (data.msg == "请填写职业状态") {
			//	$("#u_occStatus").html("请填写职业状态");
				alert("请填写职业状态");
			}
			if (data.msg == "请填写工作城市省份") {
			//	$("#u_workPro").html("请填写工作城市省份");
				 alert("请填写工作城市省份");
			}
			if (data.msg == "请填写工作城市") {
			//	$("#u_workPro").html("请填写工作城市");
				alert("请填写工作城市");
			}
			if (data.msg == "直系人姓名长度为不小于2和大于50") {
				alert("直系人姓名长度为不小于2和大于50");
			}
			if (data.msg == "请填写公司类别") {
				alert("请填写公司类别");
				//$("#u_companyType").html("请填写公司类别");
			}
			if (data.msg == "请填写公司行业") {
			//	$("#u_companyLine").html("请填写公司行业");
				alert("请填写公司行业");
			}
			if (data.msg == "请填写公司规模") {
			//	$("#u_companyScale").html("请填写公司规模");
				 alert("请填写公司规模");
			}
			if (data.msg == "请填写职位") {
				//$("#u_job").html("请填写职位");
				alert("请填写职位");
			}
			if (data.msg == "请填写月收入") {
			//	$("#u_monthlyIncome").html("请填写月收入");
				alert("请填写月收入");
			}

			if (data.msg == "请填写现单位工作年限") {
				//$("#u_workYear").html("请填写现单位工作年限");
				 alert("请填写现单位工作年限");
			}
			if (data.msg == "请正确填写公司电话") {
				//$("#u_companyTel").html("请填写公司电话");
				alert("请填写公司电话");
			}
			if (data.msg == "请填写工作邮箱") {
			//	$("#u_workEmail").html("请填写工作邮箱");
				alert("请填写工作邮箱");
			}
			if (data.msg == "请填写公司地址") {
				//$("#u_companyAddress").html("请填写公司地址");
				 alert("请填写公司地址");
			}
			//直系联系人的信息
			if (data.msg == "请填写直系人姓名") {
				$("#u_directedName").html("请填写直系人姓名");
			}
			if (data.msg == "请填写直系人关系") {
				$("#u_directedRelation").html("请填写直系人关系");
			}
			if (data.msg == "直系人姓名长度为不小于2和大于50") {
				$("#u_directedName").html("直系人姓名长度为不小于2和大于50");
			}
			if (data.msg == "请正确填写直系人电话") {
				$("#u_directedTel").html("请正确填写直系人手机");
			}
			if (data.msg == "请正确填写直系人电话长度错误") {
				$("#u_directedTel").html("请正确填写直系人电话长度");
			}
			//其他联系人的信息
			if (data.msg == "请填写其他人姓名") {
				$("#u_otherName").html("请填写其他联系人姓名");
			}
			if (data.msg == "其他人姓名长度为不小于2和大于50") {
				$("#u_otherName").html("其他人姓名长度为不小于2和大于50");
			}
			if (data.msg == "请填写其他人关系") {
				$("#u_otherRelation").html("请填写其他联系人关系");

			}
			if (data.msg == "请正确填写其他人联系电话") {
				$("#u_otherTel").html("请正确填写其他联系人联系手机");
			}
			if (data.msg == "请正确填写其他人电话长度错误") {
				$("#u_otherTel").html("请正确填写其他联系人电话长度");
			}
			//更多联系人的信息
			if (data.msg == "请填写更多联系人的名字") {
				$("#u_moredName").html("请正确填写更多联系人名字");
			}
			if (data.msg == "更多联系人姓名长度为不小于2和大于50") {
				$("#u_moredName").html("更多联系人姓名长度为不小于2和大于50");
			}
			if (data.msg == "请填写更多人关系") {
				$("#u_moredRelation").html("请正确填写其他联系人关系");
			}
			if (data.msg == "请填写更多联系人手机") {
				$("#u_moredTel").html("请正确填写更多联系人联系手机");
			}

			if (data.msg == "请正确填写更多人电话长度错误") {
				$("#u_moredTel").html("请正确填写更多人电话长度");
			}

			if (data.msg == "querBaseData") {
				alert('请先完善个人详细信息!');
				window.location.href = 'owerInformationInit.do';
			}

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
			$("#workCity").html(_array.join(""));
		});
	}
	//加载该用户提现银行卡信息
		function loadBankInfo(url) {
			var bb = '${person}';//设置提现银行卡必须先填写个人资料
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href="owerInformationInit.do";
			} else {
				window.location.href=url;
			}

		}
		function jumpUrl(obj) {
			window.location.href = obj;
		}
		function bindingPhoneLoad(url) {
			var bb = '${person}';//申请手机绑定必须先填写个人资料
			var cc = '${pass}';
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href='owerInformationInit.do';
			} else if (cc != 3) {
				alert("请等待个人信息审核通过");
				return ;
			} else {
				   window.location.href=url;
			}
		}
</script>
	</body>
</html>
