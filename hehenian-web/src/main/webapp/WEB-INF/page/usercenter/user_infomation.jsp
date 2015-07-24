<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!--=======================-->
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css" href="common/date/calendar.css" />
		<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		
		
				$(document).ready(function(){
		   var t = ${authStep}
		   if(t==1){
		     $("#li_geren").attr("class","on"); 
		      $("#jibenziliao").show();
		   }else if(t==2){
		   /*
		      $("#li_geren").attr("class","");
		      */
		       $("#jibenziliao").hide();
		          $("#div_work").show();
		          $("#li_geren").removeClass("on");
		          $("#li_work").attr("class","on"); 
		   }else if(t==3){//vip
		        $("#jibenziliao").hide();
		          $("#div_work").hide();
		         $("#div_vip").show();
		      $("#li_vip").attr("class","on");
		      $("#li_work").attr("class","");   
		       $("#li_geren").attr("class","");  
		   }
		   //按钮
		   $("#li_geren").click(function(){
		       if(t!=1){
		         window.location.href='';
		       }
		   });
         });
		
		
		 
		</script>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<!--=======================-->
		<div class="nymain">
			<div class="bigbox">
				<div class="til">
					申请贷款
				</div>
				<div class="sqdk" style="background: none;">
					<div class="l-nav">
						<ul>
							<li class="on">
								<a href="information.do">基本资料</a>
							</li>
							<li>
								<a href="userPassData.do">上传资料</a>
							</li>
							<li class="last">
								<a href="sqdk-fbdk.html">发布贷款</a>
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
									工作认证信息
								</li>
								<li  id="li_vip">
									申请vip
								</li>
							</ul>
						</div>
						<div class="rmainbox">
						<!-- 基础资料div -->
						<s:if test="true">
							<div class="box01" id="jibenziliao">
								<p class="tips">
									<span class="fred">*</span> 为必填项，所有资料均会严格保密。
								</p>
								<div class="tab">
									<form id="BaseDataform">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="right">
													真实姓名：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.realName"
														id="realName" />
													<span style="color: red; float: none;" id="u_realName"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													身份证号：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.idNo"
														id="idNo" />
													<span style="color: red; float: none;" id="u_idNo"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													手机号码：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.cellPhone"
														id="cellPhone" />
													<a href="#" class="yzmbtn">点击获取验证码</a>
													<span style="color: red; float: none;" id="u_cellPhone"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													手机验证码：
												</td>
												<td>
													<input type="text" class="inp100x"
														name="paramMap.vilidataNum" id="vilidataNum" />
													<span style="color: red; float: none;" id="u_vilidataNum"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													性 别：
												</td>
												<td>
													<input type="radio" name="radio" id="sex" value="男"
														name="paramMap.sex" />
													男
													<input type="radio" name="radio" id="sex" value="女"
														name="paramMap.sex" checked="checked" />
													女
													<span style="color: red; float: none;" id="u_sex"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													出生日期：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.birthday"
														id="birthday" onclick="selectStartDate();" />
													<span style="color: red; float: none;" id="u_birthday"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													最高学历：
												</td>
												<td>
													<select name="paramMap.highestEdu" id="highestEdu">
														<option value="">
															请选择
														</option>
														<option value="博士">
															博士
														</option>
														<option value="研究生">
															研究生
														</option>
														<option value="本科">
															本科
														</option>
														<option value="大专">
															大专
														</option>
													</select>
													<span style="color: red; float: none;" id="u_highestEdu"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													入学年份：
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.eduStartDay" id="eduStartDay"
														onclick="selectStartDate1();" />
													<!--  
    <select name="paramMap.eduStartDay" id="eduStartDay" >
      <option value="">请选择</option>
    </select>
    -->


													<span style="color: red; float: none;" id="u_eduStartDay"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													毕业院校：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.school"
														id="school" />
													<span style="color: red; float: none;" id="u_school"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													婚姻状况：
												</td>
												<td>
													<input type="radio" name="paramMap.maritalStatus"
														id="maritalStatus" value="已婚" checked="checked" />
													已婚
													<input type="radio" name="paramMap.maritalStatus"
														id="maritalStatus" value="未婚" />
													未婚
													<span style="color: red; float: none;" id="u_maritalStatus"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													有无子女：
												</td>
												<td>
													<input type="radio" name="paramMap.hasChild" id="hasChild"
														value="有" checked="checked" />
													有
													<input type="radio" name="paramMap.hasChild" id="hasChild"
														value="无" />
													无
													<span style="color: red; float: none;" id="u_hasChild"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													是否有房：
												</td>
												<td>
													<input type="radio" name="paramMap.hasHourse"
														id="hasHourse" value="有" checked="checked" />
													有
													<input type="radio" name="paramMap.hasHourse"
														id="hasHourse" value="无" />
													无
													<span style="color: red; float: none;" id="u_hasHourse"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													有无房贷：
												</td>
												<td>
													<input type="radio" name="paramMap.hasHousrseLoan"
														id="hasHousrseLoan" value="有" />
													有
													<input type="radio" name="paramMap.hasHousrseLoan"
														id="hasHousrseLoan" value="无" checked="checked" />
													无
													<span style="color: red; float: none;"
														id="u_hasHousrseLoan" class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													是否有车：
												</td>
												<td>
													<input type="radio" name="paramMap.hasCar" id="hasCar"
														value="有" checked="checked" />
													有
													<input type="radio" name="paramMap.hasCar" id="hasCar"
														value="无" />
													无
													<span style="color: red; float: none;" id="u_hasCar"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													有无车贷：
												</td>
												<td>
													<input type="radio" name="paramMap.hasCarLoan"
														id="hasCarLoan" value="有" />
													有
													<input type="radio" name="paramMap.hasCarLoan"
														id="hasCarLoan" value="无" checked="checked" />
													无
													<span style="color: red; float: none;" id="u_hasCarLoan"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													籍 贯：
												</td>
												<td>
													<s:select id="province" name="paramMap.nativePlacePro"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
													<s:select id="city" name="paramMap.nativePlaceCity"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.cityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />

													<span style="color: red; float: none;" id="u_Pro_City"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													户口所在地：
												</td>
												<td>
													<s:select id="registedPlacePro"
														name="paramMap.registedPlacePro"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
													<s:select id="registedPlaceCity"
														name="paramMap.registedPlaceCity"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.cityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
													<span style="color: red; float: none;" id="u_reg_Pro_City"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													居住地址：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.address"
														id="address" />
													<span style="color: red; float: none;" id="u_address"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													居住电话：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.telephone"
														id="telephone" />
													<span style="color: red; float: none;" id="u_telephone"
														class="formtips">*</span>
												</td>
											</tr>
											<tr>
												<td align="right" id="personalHead">
													个人头像：
												</td>
												<td>
													<input type="button" id="btn_personalHead" class="scbtn"
														style="cursor: pointer;" value="点击上传" />
												</td>
											</tr>
											<tr>
												<td align="right">&nbsp;
													
												</td>
												<td class="tx">

													<img id="img" src="${headImg}" width="62" height="62"
														name="paramMap.personalHead" />
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
													<input type="button" value="保存并继续" class="bcbtn"
														id="jc_btn" />
												</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
							</s:if>
							<!-- 是工作认证资料 -->
							<div class="box01" style="display: none;" id="div_work">
								<p class="tips">
									<span class="fred">*</span> 为必填项，所有资料均会严格保密。
								</p>
								<div class="tab">
									<form id="workform">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<th colspan="2" align="left">
													工作信息
												</th>
											</tr>
											<tr>
												<td align="right">
													单位名称：
												</td>
												<td>
													<input type="text" name="paramMap.orgName" id="orgName"
														class="inp188" />
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													职业状态：
												</td>
												<td>
													<select name="paramMap.occStatus" id="occStatus">
														<option value="">
															请选择
														</option>
														<option value="工薪阶层">
															请选择
														</option>
														<option value="私营企业主">
															私营企业主
														</option>
														<option value="网络商家">
															网络商家
														</option>
														<option value="其他">
															其他
														</option>
													</select>
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													工作城市：
												</td>
												<td>
													<s:select id="workPro" name="paramMap.workPro"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.provinceList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />
													<s:select id="workCity" name="paramMap.workCity"
														cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
														list="#request.cityList" listKey="regionId"
														listValue="regionName" headerKey="-1"
														headerValue="--请选择--" />




													<a href="#" class="yzmbtn">点击获取验证码</a>
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													公司类别：
												</td>
												<td>
													<select id="companyType" name="paramMap.companyType">
														<option value="">
															请选择
														</option>
														<option value="事业单位">
															事业单位
														</option>
														<option value="国家单位">
															国家单位
														</option>
														<option value="央企(包括下级单位)">
															央企(包括下级单位)
														</option>
														<option value="地方国资委直属企业">
															央企(包括下级单位)
														</option>
														<option value="世界500强(包括合资企业及下级单位)">
															世界500强(包括合资企业及下级单位)
														</option>
														<option value="外资企业(包括合资企业)">
															外资企业(包括合资企业)
														</option>
														<option value="一般上市公司(包括国外上市公司)">
															一般上市公司(包括国外上市公司)
														</option>
														<option value="一般民营企业">
															一般民营企业
														</option>
													</select>
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													公司行业：
												</td>
												<td>
													<select id="companyLine" name="paramMap.companyLine">
														<option value="">
															请选择
														</option>
														<option value="事业单位">
															事业单位
														</option>
														<option value="国家单位">
															国家单位
														</option>
														<option value="央企(包括下级单位)">
															央企(包括下级单位)
														</option>
														<option value="地方国资委直属企业">
															央企(包括下级单位)
														</option>
														<option value="世界500强(包括合资企业及下级单位)">
															世界500强(包括合资企业及下级单位)
														</option>
														<option value="外资企业(包括合资企业)">
															外资企业(包括合资企业)
														</option>
														<option value="一般上市公司(包括国外上市公司)">
															一般上市公司(包括国外上市公司)
														</option>
														<option value="一般民营企业">
															一般民营企业
														</option>
													</select>
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													公司规模：
												</td>
												<td>
													<select name="paramMap.companyScale" id="companyScale">
														<option value="50人以下">
															50人以下
														</option>
														<option value="50-100人">
															50-100人
														</option>
														<option value="100-500人">
															100-500人
														</option>
														<option value="500人以上">
															500人以上
														</option>
													</select>
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													职 位：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.job"
														id="job" />
													<strong>* </strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													月收入：
												</td>
												<td>
													<select name="paramMap.monthlyIncome" id="monthlyIncome">
														<option value="">
															请选择
														</option>
														<option value="1000以下">
															1000以下
														</option>
														<option value="1000-2000">
															1000-2000
														</option>
														<option value="2000-5000">
															2000-5000
														</option>
														<option value="5000-10000">
															5000-10000
														</option>
														<option value="10000-20000">
															10000-20000
														</option>
														<option value="20000以上">
															10000-20000
														</option>
													</select>
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													现单位工作年限：
												</td>
												<td>
													<select name="paramMap.workYear" id="workYear">
														<option value="">
															请选择
														</option>
														<option value="1年">
															1年
														</option>
														<option value="1-3年">
															1-3年
														</option>
														<option value="3-5年">
															3-5年
														</option>
														<option value="5年以上">
															5年以上
														</option>
													</select>
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													公司电话：
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.companyTel" id="companyTel" />
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													工作邮箱：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.workEmail"
														id="workEmail" />
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													公司地址：
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.companyAddress" id="companyAddress" />
													<strong>*</strong>
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
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.directedName" id="directedName" />
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													关 系：
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.directedRelation" id="directedRelation" />
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													手 机：
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.directedTel" id="directedTel" />
													<strong>*</strong>
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
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.otherName"
														id="otherName" />
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													关 系：
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.otherRelation" id="otherRelation" />
													<strong>*</strong>
												</td>
											</tr>
											<tr>
												<td align="right">
													手 机：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.otherTel"
														id="otherTel" />
													<strong>*</strong>
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
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.moredName"
														id="moredName" />
												</td>
											</tr>
											<tr>
												<td align="right">
													关 系：
												</td>
												<td>
													<input type="text" class="inp188"
														name="paramMap.moredRelation" id="moredRelation" />
												</td>
											</tr>
											<tr>
												<td align="right">
													手 机：
												</td>
												<td>
													<input type="text" class="inp188" name="paramMap.moredTel"
														id="moredTel" />
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
													<input type="button" value="保存并继续" class="bcbtn"
														id="work_btn" />

												</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
							<div class="box01" style="display: none;"  id="div_vip">
								<p class="tips" style="color: #ff0000;">
									投资者：
									<br />
									网站合作商提供投资担保，享受100%本金保障。对于担保标、推荐标，还能100%保利息。（普通用户仅保障本金）
									有专业客服跟踪服务，体验尊贵感受。 享有尊贵VIP身份标识。 借款者：享有借款资格，及时缓解资金压力。
									参与网站举行的各种活动。
								</p>
								<div class="tab">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="right">
												您的状态是：
											</td>
											<td>
												${msg}
											</td>
										</tr>
										<tr>
											<td align="right">
												用 户 名：
											</td>
											<td>
												${userName }
											</td>
										</tr>
										<tr>
											<td align="right">
												姓 名：
											</td>
											<td>
												${realName }
											</td>
										</tr>
										<tr>
											<td align="right">
												邮 箱：
											</td>
											<td>
												${email }
											</td>
										</tr>
										<tr>
											<td align="right">
												选择客服：
											</td>
											<td>
												人人阿明
												<a href="#" class="scbtn">选择客服</a>
											</td>
										</tr>
										<tr>
											<td align="right">
												备 注：
											</td>
											<td>
												<textarea class="txt420"></textarea>
											</td>
										</tr>
										<tr>
											<td align="right">
												验 证 码：
											</td>
											<td>
												<input type="text" class="inp100x" />
												<img src="images/neiye2_48.jpg" width="52" height="20" />
											</td>
										</tr>
										<tr>
											<td align="right">&nbsp;
												
											</td>
											<td style="padding-top: 20px;">
												<a href="#" class="bcbtn">我要申请</a>
											</td>
										</tr>
									</table>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
		<script type="text/javascript" src="script/nav-jk.js"></script>
		<script type="text/javascript" src="script/nav-lc.js"></script>
		<script type="text/javascript" src="script/nav.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
		<script>
  		function selectStartDate(){
			return showCalendar('birthday', '%Y-%m-%d', '24', true, 'birthday');
		}
		//入学年份
			function selectStartDate1(){
			return showCalendar('eduStartDay', '%Y-%m-%d', '24', true, 'eduStartDay');
		}
		
$(function(){
 /*
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	*/
	})
	
	
	
	
	$(function(){
	 /*
	$('.til01 li').click(
	function(){
		$('.til01 li').removeClass('on');
		$(this).addClass('on');
		$('.rmainbox').children('div').hide();
		$('.rmainbox').children('div').eq($(this).index()).show();
	}
		
	)
	*/
})
	
	
	
	
	
	
</script>
		<script>

//======
$(document).ready(function(){
    $("#BaseDataform :input").blur(function(){
    //验证手机号码
      if($(this).attr("id")=="cellPhone"){
      if( $(this).val() ==""){
         $("#u_cellPhone").attr("class", "formtips onError");
		 $("#u_cellPhone").html("*请填写手机号码！");
      }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
       $("#u_cellPhone").attr("class", "formtips onError");
	 $("#u_cellPhone").html("*请正确填写手机号码！");
      }else{
           $("#u_cellPhone").attr("class", "formtips");
	       $("#u_cellPhone").html(""); 
      }
  }
  //真实姓名
  		if($(this).attr("id")=="realName"){  
				if($(this).val() ==""){
			      	$("#u_realName").attr("class", "formtips onError");
			      	$("#u_realName").html("*请填写真实姓名！");
			    }else if($(this).val().length <2 || $(this).val().length> 20){   
	            	$("#u_realName").attr("class", "formtips onError");
	                $("#u_realName").html("*名字长度为2-20个字符"); 
	            }else{   
	            	$("#u_realName").attr("class", "formtips");
	                $("#u_realName").html(""); 
	            } 
          }
  //========
  //身份号码
  if($(this).attr("id")=="idNo"){
    if($(this).val() ==""){
    //$("#u_idNo").attr("class", "formtips onError");
    $("#u_idNo").html("*请填写身份证号码");
    }else {
     $("#u_idNo").html("");
      //===
    }
  }
  //========== 验证出生年月
    if($(this).attr("id")=="birthday"){
    if($(this).val() ==""){
    $("#u_birthday").attr("class", "formtips onError");
    $("#u_birthday").html("*请填写出生年月");
    }else if(!(!/^[1,2][0-9]\d{2}$/.test($("#birthday").val()))){
    $("#u_birthday").attr("class", "formtips onError");
      $("#u_birthday").html("*年份格式错误");
    }else{
    $("#u_birthday").attr("class", "formtips");
       $("#u_birthday").html("");
    }
  }
  //===========验证毕业院校
      if($(this).attr("id")=="school"){
    if($(this).val() ==""){
    $("#u_school").attr("class", "formtips onError");
    $("#u_school").html("*请填写毕业院校");
    }else{
    $("#u_school").attr("class", "formtips");
       $("#u_school").html("");
    }
  }
 //==手机验证码
       if($(this).attr("id")=="vilidataNum"){
    if($(this).val() ==""){
    $("#u_vilidataNum").attr("class", "formtips onError");
    $("#u_vilidataNum").html("*请填写手机验证码");
    }else{
    $("#u_vilidataNum").attr("class", "formtips");
       $("#u_vilidataNum").html("");
    }
  }
 //=========最高学历
        if($(this).attr("id")=="highestEdu"){
    if($(this).val() ==""){
    $("#u_highestEdu").attr("class", "formtips onError");
    $("#u_highestEdu").html("*请填写最高学历");
    }else{
    $("#u_highestEdu").attr("class", "formtips");
       $("#u_highestEdu").html("");
    }
  }
 //============入学年份
        if($(this).attr("id")=="eduStartDay"){
    if($(this).val() ==""){
    $("#u_eduStartDay").attr("class", "formtips onError");
    $("#u_eduStartDay").html("*请填写入学年份");
    }else{
    $("#u_eduStartDay").attr("class", "formtips");
       $("#u_eduStartDay").html("");
    }
  }
 //=====籍　　贯

    if($(this).attr("id")=="nativePlacePro"){
    if($(this).val() ==""){
    $("#u_Pro_City").attr("class", "formtips onError");
    $("#u_Pro_City").html("*请填写籍贯");
    }else{
    $("#u_Pro_City").attr("class", "formtips");
       $("#u_Pro_City").html("");
    }
  }
         if($(this).attr("id")=="nativePlaceCity"){
    if($(this).val() ==""){
    $("#u_Pro_City").attr("class", "formtips onError");
    $("#u_Pro_City").html("*请填写籍贯");
    }else{
    $("#u_Pro_City").attr("class", "formtips");
       $("#u_Pro_City").html("");
    }
  }

 //======户口所在地
       if($(this).attr("id")=="registedPlacePro"){
    if($(this).val() ==""){
    $("#u_reg_Pro_City").attr("class", "formtips onError");
    $("#u_reg_Pro_City").html("*请填写户口所在地");
    }else{
    $("#u_reg_Pro_City").attr("class", "formtips");
       $("#u_reg_Pro_City").html("");
    }
  }
         if($(this).attr("id")=="registedPlaceCity"){
    if($(this).val() ==""){
    $("#u_reg_Pro_City").attr("class", "formtips onError");
    $("#u_reg_Pro_City").html("*请填写户口所在地");
    }else{
    $("#u_reg_Pro_City").attr("class", "formtips");
       $("#u_reg_Pro_City").html("");
    }
  }
 
 //============居住地址
        if($(this).attr("id")=="address"){
    if($(this).val() ==""){
    $("#u_address").attr("class", "formtips onError");
    $("#u_address").html("*请填写居住地址");
    }else{
    $("#u_address").attr("class", "formtips");
       $("#u_address").html("");
    }
  }
 //================居住电话
        if($(this).attr("id")=="telephone"){
    if($(this).val() ==""){
    $("#u_telephone").attr("class", "formtips onError");
    $("#u_telephone").html("*请填写居住电话");
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
		
			 $("#workPro").change(function(){
			var provinceId = $("#registedPlacePro").val();
			workCitySelectInit(provinceId, 2);
			//$("#area").html("<option value='-1'>--请选择--</option>");
		});
     

});
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
	
	
		function workCitySelectInit(parentId, regionType){
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
	
	
	//提交基础资料
	  $("#jc_btn").click(function(){
	  
      /*
	  if($("#realName").val()==""){$("#u_realName").html("*请填写真实姓名！");return false }
	  
	  if($("#idNo").val()==""){$("#u_idNo").html("*请填写身份号码！");return false }
	  
	
	  if($("#cellPhone").val()==""){$("#u_cellPhone").html("*请填写手机号码！");return false }
	  if($("#highestEdu").val()==""){$("#u_highestEdu").html("*请填写最高学历！");return false }
	  if($("#eduStartDay").val()==""){$("#u_eduStartDay").html("*请填写入学年份！");return false }
	  if($("#address").val()==""){$("#u_address").html("*请填写地址！");return false }
	   if($("#telephone").val()==""){$("#u_telephone").html("*请填写电话号码！");return false }
	  if($("#birthday").val()==""){$("#u_birthday").html("*请填写真实姓名！");return false }
	 
     */

	   var param = {};
	    param["paramMap.realName"]=$("#realName").val();
	    param["paramMap.idNo"]=$("#idNo").val();
	    param["paramMap.cellPhone"]=$("#cellPhone").val();
	    param["paramMap.sex"]=$("#sex").val();
	     param["paramMap.birthday"]=$("#birthday").val();
	    param["paramMap.highestEdu"]=$("#highestEdu").val();
	    param["paramMap.eduStartDay"]=$("#eduStartDay").val();
	    param["paramMap.school"]=$("#school").val();
	    param["paramMap.maritalStatus"]=$("#maritalStatus").val();
	    param["paramMap.hasChild"]=$("#hasChild").val();
	    param["paramMap.hasHourse"]=$("#hasHourse").val();
	    param["paramMap.hasHousrseLoan"]=$("#hasHousrseLoan").val();
	    param["paramMap.hasCar"]=$("#hasCar").val();
	    param["paramMap.hasCarLoan"]=$("#hasCarLoan").val();
	    param["paramMap.nativePlacePro"]=$("#nativePlacePro").val();
	    param["paramMap.nativePlaceCity"]=$("#nativePlaceCity").val();
	    param["paramMap.registedPlacePro"]=$("#registedPlacePro").val();
	    param["paramMap.registedPlaceCity"]=$("#registedPlaceCity").val();
	    param["paramMap.address"]=$("#address").val();
	    param["paramMap.telephone"]=$("#telephone").val();
	    //用户头像路径
	    param["paramMap.personalHead"]=$("#img").attr("src");
	    $.post("userBaseDate.do",param,function(data){
	       if(data.msg=="保存成功"){
	       window.location.href='againJumpToUserInf.do';
	        alert("保存成功");
	          
	          /*
	            $("#realName").attr("disabled","disabled");
	              $("#idNo").attr("disabled","disabled");
	                $("#cellPhone").attr("disabled","disabled");
	                  $("#sex").attr("disabled","disabled");
	                    $("#realName").attr("disabled","disabled");
	                      $("#birthday").attr("disabled","disabled");
	                      $("#highestEdu").attr("disabled","disabled");
	                      $("#eduStartDay").attr("disabled","disabled");
	                      $("#maritalStatus").attr("disabled","disabled");
	                      $("#hasChild").attr("disabled","disabled");
	                      $("#hasHourse").attr("disabled","disabled");
	                      $("#hasHousrseLoan").attr("disabled","disabled");
	                      $("#hasCar").attr("disabled","disabled");
	                      $("#hasCarLoan").attr("disabled","disabled");
	                      $("#nativePlacePro").attr("disabled","disabled");
	                      $("#nativePlaceCity").attr("disabled","disabled");
	                      $("#registedPlacePro").attr("disabled","disabled");
	                      $("#registedPlaceCity").attr("disabled","disabled");
	                      $("#address").attr("disabled","disabled");
	                      $("#telephone").attr("disabled","disabled");
	                      */
	       }else{
	         //alert("请正确填写基本资料");
	         if(data.msg=="请正确填写真实名字"){
	         $("#u_realName").html("*请填写真实姓名！")
	       }
	            if(data.msg=="真实姓名的长度为不小于2和大于20"){
	            $("#u_realName").html("*真实姓名的长度为不小于2和大于20！")
	       }
	            if(data.msg=="请正确填写手机号码"){
	            $("#u_cellPhone").html("*请填写手机号码！");
	       }
	            if(data.msg=="手机号码长度不对"){
	         $("#u_cellPhone").html("*手机号码长度不对！")
	       }
	       
	            if(data.msg=="请正确填写出生日期"){
	         $("#u_birthday").html("*请正确填写出生日期！")
	       }
	            if(data.msg=="请正确填写入学年份"){
	         $("#u_eduStartDay").html("*请正确填写入学年份！")
	       }
	             if(data.msg=="请正确填写入毕业院校"){
	         $("#u_school").html("*请正确填写入毕业院校！")
	       }
	             if(data.msg=="请正确填写入学年份"){
	         $("#u_eduStartDay").html("*请正确填写入学年份！")
	       }
	             if(data.msg=="请正确填写最高学历"){
	         $("#u_highestEdu").html("*请正确填写最高学历！")
	       }
	                 if(data.msg=="请正确填写入籍贯省份"){
	         $("#u_nativePlacePro").html("*请正确填写入籍贯省份！")
	       }
	                 if(data.msg=="请正确填写入籍贯城市"){
	         $("#u_nativePlaceCity").html("*请正确填写入籍贯城市！")
	       }
             if(data.msg=="请正确填写入户口所在地省份"){
	         $("#u_registedPlacePro").html("*请正确填写入户口所在地省份！")
	       }
	                 if(data.msg=="请正确填写入户口所在地城市"){
	         $("#u_registedPlaceCity").html("*请正确填写入户口所在地城市！")
	       }
	                  if(data.msg=="请正确填写入你的家庭电话"){
	         $("#u_telephone").html("*请正确填写入你的家庭电话！")
	       }
	       
	         if(data.msg=="请正确身份证号码"){
	         $("#u_idNo").html("*请正确身份证号码！")
	       }
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
						alert("上传成功！");	
						//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
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
			$("#work_btn").click(function(){
			      var param = {};
			       param["paramMap.orgName"]=$("#orgName").val();
			        param["paramMap.occStatus"]=$("#occStatus").val();
			         param["paramMap.workPro"]=$("#workPro").val();
			          param["paramMap.workCity"]=$("#workCity").val();
			           param["paramMap.companyType"]=$("#companyType").val();
			           param["paramMap.companyLine"]=$("#companyLine").val();
			            param["paramMap.companyScale"]=$("#companyScale").val();
			             param["paramMap.job"]=$("#job").val();
			              param["paramMap.monthlyIncome"]=$("#monthlyIncome").val();
			               param["paramMap.workYear"]=$("#workYear").val();
			                param["paramMap.companyTel"]=$("#companyTel").val();
			                 param["paramMap.workEmail"]=$("#workEmail").val();
			                  param["paramMap.companyAddress"]=$("#companyAddress").val();
			                  param["paramMap.directedName"]=$("#directedName").val();
			                  param["paramMap.directedRelation"]=$("#directedRelation").val();
			                  param["paramMap.directedTel"]=$("#directedTel").val();
			                   param["paramMap.otherName"]=$("#otherName").val();
			                    param["paramMap.otherRelation"]=$("#otherRelation").val();
			                     param["paramMap.otherTel"]=$("#otherTel").val();
			                      param["paramMap.moredName"]=$("#moredName").val();
			                      param["paramMap.moredRelation"]=$("#moredRelation").val();
			                      param["paramMap.moredTel"]=$("#moredTel").val();
			                      param["paramMap.moredName"]=$("#moredName").val();
			                    $.post("userWorkDate.do",param,function(data){
			                          if(data.msg=="保存成功"){
			                            alert("保存成功");
			                            window.location.href='againJumpToUserInfo.do';
			                          }
			                              if(data.msg=="请正确填写公司名字"){
			                            alert("请正确填写单位名字");
			                          }
			                              if(data.msg=="真实姓名的长度为不小于2和大于50"){
			                            alert("真实姓名的长度为不小于2和大于50");
			                          }
			                              if(data.msg=="请填写职业状态"){
			                            alert("请填写职业状态");
			                          }
			                              if(data.msg=="请填写工作城市省份"){
			                            alert("请填写工作城市省份");
			                          }
			                              if(data.msg=="请填写工作城市"){
			                            alert("请填写工作城市");
			                          }
			                              if(data.msg=="请填写公司行业"){
			                            alert("请填写公司行业");
			                          }
			                              if(data.msg=="请填写公司规模"){
			                            alert("请填写公司规模");
			                          }
			                              if(data.msg=="请填写职位"){
			                            alert("请填写职位");
			                          }
			                              if(data.msg=="请填写月收入"){
			                            alert("请填写月收入");
			                          }
			                          
			                              if(data.msg=="请填写现单位工作年限"){
			                            alert("请填写现单位工作年限");
			                          }
			                              if(data.msg=="请填写公司电话"){
			                            alert("请填写公司电话");
			                          }
			                              if(data.msg=="请填写工作邮箱"){
			                            alert("请填写工作邮箱");
			                          }
			                              if(data.msg=="请填写公司地址"){
			                            alert("请填写公司地址");
			                          }
			                              if(data.msg=="请填写直系人姓名"){
			                            alert("请填写直系人姓名");
			                          }
			                              if(data.msg=="请填写直系人关系"){
			                            alert("请填写直系人关系");
			                          }
			                              if(data.msg=="请填写直系人电话"){
			                            alert("请填写直系人电话");
			                          }
			                              if(data.msg=="请填写其他人姓名"){
			                            alert("请填写其他人姓名");
			                          }
			                              if(data.msg=="请填写其他人关系"){
			                            alert("请填写其他人关系");
			                          }
			                             if(data.msg=="请填写其他人电话"){
			                            alert("请填写其他人电话");
			                          }
			                          
			                          
			                          
			                          
			                    });  
			
			});
			//===============|~
		</script>
	</body>
</html>
