<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="common/date/calendar.css" />
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="cle"></div>
	<div class="wytz_center">
		<div class="wdzh_top">
			<jsp:include page="/include/menu_userManage.jsp"></jsp:include>
			<div class="cle"></div>
			<div class="wdzh_next" style="display:block;">
				<div class="wdzh_next" style="display:block;">
					<div class="wdzh_next_left">
						<jsp:include page="/include/menu_userManage_4_left.jsp"></jsp:include>
					</div>
					<div class="wdzh_next_right">
						<div class="ggxxxx_box">
							<h3>
								<span>工作信息</span>
							</h3>
							<table width="770" border="0">
								<tr height="5"></tr>
								<tr height="45">
									<td width="120" align="right"><strong>*</strong>单位名称：</td>
									<td><input type="text" name="paramMap.orgName" id="orgName" class="inp188" value="${map.orgName}" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if> />
									</td>
								</tr>
								<tr>
									<td width="120" align="right"><strong>*</strong>职业状态：</td>
									<td><select name="paramMap.occStatus" id="occStatus" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if>>
											<option value="">请选择</option>
											<option value="工薪阶层" <s:if test='#request.map.occStatus == "工薪阶层"'>selected="selected"</s:if> <s:else></s:else>>工薪阶层</option>
											<option value="私营企业主" <s:if test='#request.map.occStatus == "私营企业主"'>selected="selected"</s:if> <s:else></s:else>>私营企业主</option>
											<option value="网络商家" <s:if test='#request.map.occStatus == "网络商家"'>selected="selected"</s:if> <s:else></s:else>>网络商家</option>
											<option value="其他" <s:if test='#request.map.occStatus == "其他"'>selected="selected"</s:if> <s:else></s:else>>其他</option>
									</select>
									</td>
								</tr>
								<tr height="45">
									<td width="120" align="right"><strong>*</strong>工作城市：</td>
									<td><s:select id="workPro" name="workPro" cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;"
											list="#request.provinceList" listKey="regionId" listValue="regionName" headerKey="-1" headerValue="--请选择--" /> <s:select id="workCity" name="cityId"
											cssStyle="border-left:1px solid #dedede;border-top:1px solid #dedede;border-right:1px solid #dedede;border-bottom:1px solid #dedede;" list="#request.cityList" listKey="regionId"
											listValue="regionName" headerKey="-1" headerValue="--请选择--" />
									</td>
								</tr>
								<tr>
									<td width="120" align="right"><strong>*</strong>公司类别：</td>
									<td><select id="companyType" name="paramMap.companyType" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if>>
											<option value="">请选择</option>
											<option value="事业单位" <s:if test='#request.map.companyType == "事业单位"'>selected="selected"</s:if> <s:else></s:else>>事业单位</option>
											<option value="国家单位" <s:if test='#request.map.companyType == "国家单位"'>selected="selected"</s:if> <s:else></s:else>>国家单位</option>
											<option value="央企(包括下级单位)" <s:if test='#request.map.companyType == "央企(包括下级单位)"'>selected="selected"</s:if> <s:else></s:else>>央企(包括下级单位)</option>
											<option value="地方国资委直属企业" <s:if test='#request.map.companyType == "地方国资委直属企业"'>selected="selected"</s:if> <s:else></s:else>>地方国资委直属企业</option>
											<option value="世界500强(包括合资企业及下级单位)" <s:if test='#request.map.companyType == "世界500强(包括合资企业及下级单位)"'>selected="selected"</s:if> <s:else></s:else>>世界500强(包括合资企业及下级单位)</option>
											<option value="外资企业(包括合资企业)" <s:if test='#request.map.companyType == "外资企业(包括合资企业)"'>selected="selected"</s:if> <s:else></s:else>>外资企业(包括合资企业)</option>
											<option value="一般上市公司(包括国外上市公司)" <s:if test='#request.map.companyType == "一般上市公司(包括国外上市公司)"'>selected="selected"</s:if> <s:else></s:else>>一般上市公司(包括国外上市公司)</option>
											<option value="一般民营企业" <s:if test='#request.map.companyType == "一般民营企业"'>selected="selected"</s:if> <s:else></s:else>>一般民营企业</option>
									</select>
									</td>
								</tr>
								<tr height="45">
									<td width="120" align="right"><strong>*</strong>公司行业：</td>
									<td><select id="companyLine" name="paramMap.companyLine" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if>>
											<option value="">请选择</option>
											<option value="农、林、牧、渔业" <s:if test='#request.map.companyLine == "农、林、牧、渔业"'>selected="selected"</s:if> <s:else></s:else>>农、林、牧、渔业</option>
											<option value="采矿业" <s:if test='#request.map.companyLine == "采矿业"'>selected="selected"</s:if> <s:else></s:else>>采矿业</option>
											<option value="电力、热力、燃气及水的生产和供应业" <s:if test='#request.map.companyLine == "电力、热力、燃气及水的生产和供应业"'>selected="selected"</s:if> <s:else></s:else>>电力、热力、燃气及水的生产和供应业</option>
											<option value="制造业" <s:if test='#request.map.companyLine == "制造业"'>selected="selected"</s:if> <s:else></s:else>>制造业</option>
											<option value="教育" <s:if test='#request.map.companyLine == "教育"'>selected="selected"</s:if> <s:else></s:else>>教育</option>
											<option value="环境和公共设施管理业" <s:if test='#request.map.companyLine == "环境和公共设施管理业"'>selected="selected"</s:if> <s:else></s:else>>环境和公共设施管理业</option>
											<option value="建筑业" <s:if test='#request.map.companyLine == "建筑业"'>selected="selected"</s:if> <s:else></s:else>>建筑业</option>
											<option value="交通运输、仓储业和邮政业" <s:if test='#request.map.companyLine == "交通运输、仓储业和邮政业"'>selected="selected"</s:if> <s:else></s:else>>交通运输、仓储业和邮政业</option>
											<option value="信息传输、计算机服务和软件业" <s:if test='#request.map.companyLine == "信息传输、计算机服务和软件业"'>selected="selected"</s:if> <s:else></s:else>>信息传输、计算机服务和软件业</option>
											<option value="批发和零售业" <s:if test='#request.map.companyLine == "批发和零售业"'>selected="selected"</s:if> <s:else></s:else>>批发和零售业</option>
											<option value="住宿、餐饮业" <s:if test='#request.map.companyLine == "住宿、餐饮业"'>selected="selected"</s:if> <s:else></s:else>>住宿、餐饮业</option>
											<option value="金融、保险业" <s:if test='#request.map.companyLine == "金融、保险业"'>selected="selected"</s:if> <s:else></s:else>>金融、保险业</option>
											<option value="房地产业" <s:if test='#request.map.companyLine == "房地产业"'>selected="selected"</s:if> <s:else></s:else>>房地产业</option>
											<option value="文化、体育、娱乐业" <s:if test='#request.map.companyLine == "文化、体育、娱乐业"'>selected="selected"</s:if> <s:else></s:else>>文化、体育、娱乐业</option>
											<option value="综合（含投资类、主业不明显)" <s:if test='#request.map.companyLine == "综合（含投资类、主业不明显)"'>selected="selected"</s:if> <s:else></s:else>>综合（含投资类、主业不明显)</option>
											<option value="其它" <s:if test='#request.map.companyLine == "其它"'>selected="selected"</s:if> <s:else></s:else>>其它</option>
									</select>
									</td>
								</tr>
								<tr>
									<td width="120" align="right"><strong>*</strong>公司规模：</td>
									<td><select name="paramMap.companyScale" id="companyScale" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if>>
											<option value="50人以下" <s:if test='#request.map.companyScale == "50人以下"'>selected="selected"</s:if> <s:else></s:else>>50人以下</option>
											<option value="50-100人" <s:if test='#request.map.companyScale == "50-100人"'>selected="selected"</s:if> <s:else></s:else>>50-100人</option>
											<option value="100-500人" <s:if test='#request.map.companyScale == "100-500人"'>selected="selected"</s:if> <s:else></s:else>>100-500人</option>
											<option value="500人以上" <s:if test='#request.map.companyScale == "500人以上"'>selected="selected"</s:if> <s:else></s:else>>500人以上</option>
									</select>
									</td>
								</tr>
								<tr height="45">
									<td width="120" align="right"><strong>*</strong>职位：</td>
									<td><input type="text" class="inp188" name="paramMap.job" id="job" value="${map.job }" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if> />
									</td>
								</tr>
								<tr>
									<td width="120" align="right"><strong>*</strong>月收入：</td>
									<td><select name="paramMap.monthlyIncome" id="monthlyIncome" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if>>
											<option value="">请选择</option>
											<option value="1000以下" <s:if test='#request.map.monthlyIncome == "1000以下"'>selected="selected"</s:if> <s:else></s:else>>1000以下</option>
											<option value="1000-2000" <s:if test='#request.map.monthlyIncome == "1000-2000"'>selected="selected"</s:if> <s:else></s:else>>1000-2000</option>
											<option value="2000-5000" <s:if test='#request.map.monthlyIncome == "2000-5000"'>selected="selected"</s:if> <s:else></s:else>>2000-5000</option>
											<option value="5000-10000" <s:if test='#request.map.monthlyIncome == "5000-10000"'>selected="selected"</s:if> <s:else></s:else>>5000-10000</option>
											<option value="10000-20000" <s:if test='#request.map.monthlyIncome == "10000-20000"'>selected="selected"</s:if> <s:else></s:else>>10000-20000</option>
											<option value="20000以上" <s:if test='#request.map.monthlyIncome == "20000以上"'>selected="selected"</s:if> <s:else></s:else>>20000以上</option>
									</select>
									</td>
								</tr>
								<tr height="45">
									<td width="120" align="right"><strong>*</strong>现单位工作年限：</td>
									<td><select name="paramMap.workYear" id="workYear" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if>>
											<option value="">请选择</option>
											<option value="1年" <s:if test='#request.map.workYear == "1年"'>selected="selected"</s:if> <s:else></s:else>>1年</option>
											<option value="1-3年" <s:if test='#request.map.workYear == "1-3年"'>selected="selected"</s:if> <s:else></s:else>>1-3年</option>
											<option value="3-5年" <s:if test='#request.map.workYear == "3-5年"'>selected="selected"</s:if> <s:else></s:else>>3-5年</option>
											<option value="5年以上" <s:if test='#request.map.workYear == "5年以上"'>selected="selected"</s:if> <s:else></s:else>>5年以上</option>
									</select>
									</td>
								</tr>
								<tr>
									<td width="120" align="right"><strong>*</strong>公司电话：</td>
									<td><input type="text" class="inp188" name="paramMap.companyTel" id="companyTel" value="${map.companyTel }" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if> />
										格式：0755-29556666&nbsp;</td>
									</td>
								</tr>
								<tr height="45">
									<td width="120" align="right"><strong>*</strong>工作邮箱：</td>
									<td><input type="text" class="inp188" name="paramMap.workEmail" id="workEmail" value="${map.workEmail }" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if> />
									</td>
								</tr>
								<tr>
									<td width="120" align="right"><strong>*</strong>公司地址：</td>
									<td><input type="text" class="inp188" name="paramMap.companyAddress" id="companyAddress" value="${map.companyAddress }"
										<s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if> />
									</td>
								</tr>
								<tr height="45">
									<td width="120" align="right"></td>
									<td><input type="button" value="保存" style=" margin:0;" id="work_btn" <s:if test='#request.allworkmap.audi ==3'>disabled="disabled"</s:if> /></td>
								</tr>
								<tr height="15"></tr>
							</table>
						</div>
					</div>
				</div>
				<div class="cle"></div>
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
		</div>
	</div>
	<div class="cle"></div>
	<!-- 引用底部公共部分 -->
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="common/date/calendar.js"></script>
	<script>
		$(document).ready(function() {
			if ('${allworkmap.audi}' == '12') {
				$("#workPro").attr("disabled", "true");
				$("#workCity").attr("disabled", "true");
			}
			$(".wdzh_next_left li").eq(1).addClass("wdzh_next_left_ong");
		});
	</script>
	<script>
		$(function() {
			var ff ='${request.allworkmap.audi}';
			if(ff=="3"){
				$("#workPro").attr("disabled",true);
				$("#workCity").attr("disabled",true);
			}
			//上传图片
			$("#btn_personalHead").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img").attr("src");
						if (headImgPath != "") {
							alert("上传成功！");
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
						} else {
							alert("上传失败！");
						}
					});

			//省份改变
			$("#workPro").change(function() {
				var provinceId = $("#workPro").val();
				citySelectInit(provinceId, 2);
				//$("#area").html("<option value='-1'>--请选择--</option>");
			});

		});

		//城市跟随改变
		function citySelectInit(parentId, regionType) {
			var _array = [];
			_array.push("<option value='-1'>--请选择--</option>");
			var param = {};
			param["regionType"] = regionType;
			param["parentId"] = parentId;
			$.post("ajaxqueryRegion.do", param, function(data) {
				for ( var i = 0; i < data.length; i++) {
					_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
				}
				$("#workCity").html(_array.join(""));
			});
		}

		function uploadCall(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img").attr("src", path);
				$("#setImg").attr("src", path);
			}
		}

		function getDirNum() {
			var date = new Date();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			if (m < 10) {
				m = "0" + m;
			}
			if (d < 10) {
				d = "0" + d;
			}
			var dirName = date.getFullYear() + "" + m + "" + d;
			return dirName;
		}

		$("#work_btn").click(function() {
			if ($("#orgName").val() == "") {
				alert('请填写单位名称');
				return;
			}
			if ($("#occStatus").val() == "") {
				alert('请填写职业状态');
				return;
			}
			if ($("#workPro").val() == "-1") {
				alert('请填写工作省份');
				return;
			}
			if ($("#workCity").val() == '-1') {
				alert('请填写工作城市');
				return;
			}
			if ($("#companyType").val() == "") {
				alert('请填写公司类别');
				return;
			}
			if ($("#companyLine").val() == "") {
				alert('请填写公司行业');
				return;
			}

			if ($("# companyScale").val() == "") {
				alert('请填写公司规模');
				return;
			}
			if ($("#job").val() == "") {
				alert('请填写职位');
				return;
			}
			if ($("#monthlyIncome").val() == "") {
				alert('请填写月收入');
				return;
			}
			if ($("#workYear").val() == "") {
				alert('请填写现单位工作年限');
				return;
			}
			if ($("#workYear").val() == "") {
				alert('请填写现单位工作年限');
				return;
			}

			if ($("#companyTel").val() == "") {
				alert('请填写公司电话');
				return;
			} else if ((!/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/.test($("#companyTel").val()))) {
				alert('请正确填写公司电话');
				return;
			}

			if ($("#workEmail").val() == '') {
				alert("工作邮箱不能为空");
				return;
			}
			if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test($("#workEmail").val())) {
				alert("请正确填写工作邮箱");
				return;
			}
			if ($("#companyAddress").val() == "") {
				alert('请填写现公司地址');
				return;
			}
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
			var btype = '${btype}';
			$.post("updatework.do", param, function(data) {
				if (data.msg == "vip保存成功") {
					alert("保存成功");
					window.location.href = 'userPassData.do?btype=' + btype;
				}
				if (data.msg == "保存成功") {
					alert("保存成功");
					window.location.href = 'userPassData.do?btype=' + btype;
				}
				if (data.msg == "请正确填写公司名字") {
					alert("请正确填写单位名字");
				}
				if (data.msg == "真实姓名的长度为不小于2和大于50") {
					alert("真实姓名的长度为不小于2和大于50");
				}
				if (data.msg == "请填写职业状态") {
					alert("请填写职业状态");
				}
				if (data.msg == "请填写工作城市省份") {
					alert("请填写工作城市省份");
				}
				if (data.msg == "请填写工作城市") {
					alert("请填写工作城市");
				}
				if (data.msg == "直系人姓名长度为不小于2和大于50") {
					alert("直系人姓名长度为不小于2和大于50");
				}
				if (data.msg == "请填写公司类别") {
					alert("请填写公司类别");
				}
				if (data.msg == "请填写公司行业") {
					alert("请填写公司行业");
				}
				if (data.msg == "请填写公司规模") {
					alert("请填写公司规模");
				}
				if (data.msg == "请填写职位") {
					alert("请填写职位");
				}
				if (data.msg == "请填写月收入") {
					alert("请填写月收入");
				}
				if (data.msg == "请填写现单位工作年限") {
					alert("请填写现单位工作年限");
				}
				if (data.msg == "请正确填写公司电话") {
					alert("请正确填写公司电话");
				}
				if (data.msg == "请填写工作邮箱") {
					alert("请填写工作邮箱");
				}
				if (data.msg == "请填写公司地址") {
					alert("请填写公司地址");
				}
				if (data.msg == "querBaseData") {
					alert("基本信息未填写!")
					window.location.href = 'querBaseData.do';
				}
			});

		});
	</script>
</body>
</html>
