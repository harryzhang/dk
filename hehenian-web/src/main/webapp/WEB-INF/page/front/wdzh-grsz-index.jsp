<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="common/date/calendar.js"></script>
<script type="text/javascript" src="css/popom.js"></script>

</head>

<body>
	<!-- 引用头部公共部分 -->
     <div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="wytz_center">
    <div class="mjd_tzlc_all"><jsp:include page="/include/menu_userManage.jsp"></jsp:include></div>
		<div class="wdzh_top">
			
			<div class="wdzh_next">
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
			<div class="wdzh_next">
				<div class="cle"></div>
			</div>
			<div class="cle"></div>
			<div class="wdzh_next"></div>
			<div class="cle"></div>
			<div class="wdzh_next" style="display: block;">
				<div class="wdzh_next_left">
					<jsp:include page="/include/menu_userManage_4_left.jsp"></jsp:include>
				</div>
				<div class="wdzh_next_right">
					<div class="nd_sjbd_two">
						<h3>1，验证已绑定手机</h3>
						<table width="350" border="0">
							<tr height="40">
								<td width="100" align="right">已绑定手机号码：</td>
								<td><input type="text" id="cellPhone" class="nd_sjbd_two_in" /> <span id="u_cellPhone"></span>
								</td>
							</tr>
							<tr height="40">
								<td>&nbsp;</td>
								<td><a id="reClickCode_" class="yzmbtn" href="javascript:void(0);">发送验证码</a> <%--										<input type="button" id="clickCode5" class="nd_sjbd_two_but1" value="发送手机验证码" />--%>
								</td>
							</tr>
							<tr height="40">
								<td align="right">验证码：</td>
								<td><input type="text" class="nd_sjbd_two_in" />
								</td>
							</tr>
							<tr height="40">
								<td>&nbsp;</td>
								<td>请输入您获取的手机验证码</td>
							</tr>
							<tr height="40">
								<td>&nbsp;</td>
								<td><input type="button" class="nd_sjbd_two_but2" value="手机绑定" />
								</td>
							</tr>
						</table>
					</div>
					<div class="nd_sjbd_two">
						<h3>2，绑定新手机</h3>
						<table width="350" border="0">
							<tr height="40">
								<td width="100" align="right">新手机号码：</td>
								<td><input type="text" class="nd_sjbd_two_in" />
								</td>
							</tr>
							<tr height="40">
								<td>&nbsp;</td>
								<td><input type="button" id="cellPhone" class="nd_sjbd_two_but1" value="发送手机验证码" />
								</td>
							</tr>
							<tr height="40">
								<td align="right">验证码：</td>
								<td><input type="text" class="nd_sjbd_two_in" />
								</td>
							</tr>
							<tr height="40">
								<td>&nbsp;</td>
								<td>请输入您获取的手机验证码</td>
							</tr>
							<tr height="40">
								<td>&nbsp;</td>
								<td><input type="button" class="nd_sjbd_two_but2" value="手机绑定" />
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="cle"></div>
			<div class="cle"></div>
		</div>
	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>

	<script>
  $(function(){
	  $(".wdzh_next_left li").eq(4).addClass("wdzh_next_left_ong");
    if('${map.auditStatus}'=='3'){
       $("#province").attr("disabled","true");
       $("#registedPlacePro").attr("disabled","true");
       $("#city").attr("disabled","true");
       $("#registedPlaceCity").attr("disabled","true");
       $("#clickCode").hide();
    }
  });



</script>
	<script type="text/javascript">
$(function(){
	dqzt(4);
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
}
<%--  $("#grtx").hide(); --%>
	
</script>
	<script type="text/javascript">
	//提交基本资料 start-->
	var timers = 180;
	var tipId;
	var flags = false;
	$(function() {
		//当用户再次输入手机号码时候 add by lw
		$("#cellPhone")
				.change(
						function() {
							var phone = $("#cellPhone").val();
							if ($("#cellPhone").val() == "") {
								$("#u_cellPhone").attr("class",
										"formtips onError");
								$("#u_cellPhone").html("请填写手机号码！");
							} else if ((!/^1[3,5,8]\d{9}$/.test($("#cellPhone")
									.val()))) {
								$("#u_cellPhone").attr("class",
										"formtips onError");
								$("#u_cellPhone").html("请正确填写手机号码！");
							} else {
								$("#u_cellPhone").attr("class", "formtips");
								$("#u_cellPhone").html("");
								if ($("#clickCode5").html() != "重新发送验证码"
										|| $("#clickCode").html() != "点击获取验证码") {
									//当处在发送中的状态时候
									window.clearInterval(tipId);
									flags = false;
									$("#clickCode5").html("点击获取验证码");
									$.post("removeCode.do", "", function(data) {
									});//删除手机验证码
								}
							}
						});

		$("#clickCode5").click(function() {
			//验证手机号码
				if ($("#cellPhone").val() == "") {
					$("#u_cellPhone").attr("class", "formtips onError");
					$("#u_cellPhone").html("请填写手机号码！");
				} else if ((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))) {
					$("#u_cellPhone").attr("class", "formtips onError");
					$("#u_cellPhone").html("请正确填写手机号码！");
				} else {
					$("#u_cellPhone").attr("class", "formtips");
					$("#u_cellPhone").html("");
					if ($("#clickCode5").html() == "重新发送验证码"
							|| $("#clickCode5").html() == "点击获取验证码") {
						if (flags)
							return;
						$.post("sendSMS.do", "phone=" + $("#cellPhone").val(),
								function(data) {
									if (data == 1) {
										timers = 180;
										flags = true;
										tipId = window.setInterval("timer_()",
												1000);
									} else {
										alert("手机验证码发送失败");
									}
								});
					}
				}
			});
	});
	//定时
	function timer_() {

		if (timers >= 0) {
			$("#clickCode5").html("验证码获取：" + timers + "秒");
			timers--;
		} else {
			window.clearInterval(tipId);
			flags = false;
			$("#clickCode5").html("重新发送验证码");
			$.post("removeCode.do", "", function(data) {
			});

		}
	}
	//======
	$(document)
			.ready(
					function() {
						$("#BaseDataform :input")
								.blur(function() {
									//验证手机号码
										if ($(this).attr("id") == "cellPhone") {
											if ($(this).val() == "") {
												$("#u_cellPhone").attr("class",
														"formtips onError");
												$("#u_cellPhone").html(
														"请填写手机号码！");
											} else if ((!/^1[3,5,8]\d{9}$/
													.test($("#cellPhone").val()))) {
												$("#u_cellPhone").attr("class",
														"formtips onError");
												$("#u_cellPhone").html(
														"请正确填写手机号码！");
											} else {
												$("#u_cellPhone").attr("class",
														"formtips");
												$("#u_cellPhone").html("");
											}
										}
										//真实姓名
										if ($(this).attr("id") == "realName") {
											var isName = new Object();
											isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
											if ($(this).val() == "") {
												$("#u_realName").attr("class",
														"formtips onError");
												$("#u_realName").html(
														"请填写真实姓名！");
											} else if ($(this).val().length < 2
													|| $(this).val().length > 20) {
												$("#u_realName").attr("class",
														"formtips onError");
												$("#u_realName").html(
														"名字长度为2-20个字符");
											} else if (!isName.test($(this)
													.val())) {
												$("#u_realName").html(
														"真实姓名输入有误");
											} else {
												$("#u_realName").attr("class",
														"formtips");
												$("#u_realName").html("");
											}
										}
										//========
										//身份号码
										if ($(this).attr("id") == "idNo") {
											var isIDCard1 = new Object();
											var isIDCard2 = new Object();
											//身份证正则表达式(15位) 
											isIDCard1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
											//身份证正则表达式(18位) 
											isIDCard2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/;
											if ($(this).val() == "") {
												//$("#u_idNo").attr("class", "formtips onError");
												$("#u_idNo").html("请填写身份证号码");
											} else if (isIDCard1.test($(this)
													.val())
													|| isIDCard2.test($(this)
															.val())) {
												//验证身份证号码的有效性
												var parama = {};
												parama["paramMap.idNo"] = $(
														this).val();
												$
														.post(
																"isIDNO.do",
																parama,
																function(data) {
																	if (data.putStr == "0") {
																		$(
																				"#u_idNo")
																				.html(
																						"请填写身份证号码");
																	} else if (data.putStr == "1") {
																		$(
																				"#u_idNo")
																				.html(
																						"身份证号码不合法!");
																	} else {
																		$(
																				"#u_idNo")
																				.html(
																						"");
																	}
																});
											} else {
												$("#u_idNo").html("身份证号码不正确");
											}
										}
										//========== 验证出生年月
										if ($(this).attr("id") == "birthday") {
											if ($(this).val() != "") {
												$("#u_birthday").html("");
											}
										}
										//==手机验证码
										if ($(this).attr("id") == "vilidataNum") {
											if ($(this).val() == "") {
												$("#u_vilidataNum").attr(
														"class",
														"formtips onError");
												$("#u_vilidataNum").html(
														"请填写手机验证码");
											} else {
												$("#u_vilidataNum").attr(
														"class", "formtips");
												$("#u_vilidataNum").html("");
											}
										}
										//================居住电话
										if ($(this).attr("id") == "telephone") {
											var mdd = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
											if ($(this).val() == "") {
												//   $("#u_telephone").attr("class", "formtips onError");
												//   $("#u_telephone").html("请填写居住电话");
											} else if (!mdd.test($(this).val())) {
												$("#u_telephone").attr("class",
														"formtips onError");
												$("#u_telephone").html(
														"请填写正确的居住电话");
											} else {
												$("#u_telephone").attr("class",
														"formtips");
												$("#u_telephone").html("");
											}
										}
										//============
									});
						$("#province").change(function() {
							var provinceId = $("#province").val();
							citySelectInit(provinceId, 2);
						});
						$("#registedPlacePro").change(function() {
							var provinceId = $("#registedPlacePro").val();
							registedPlaceCity(provinceId, 2);
						});

					});
	function registedPlaceCity(parentId, regionType) {
		var _array = [];
		_array.push("__tag_410$15_--请选择--__tag_410$41_");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do", param, function(data) {
			for ( var i = 0; i < data.length; i++) {
				_array.push("__tag_416$17_" + data[i].regionName
						+ "__tag_416$76_");
			}
			$("#registedPlaceCity").html(_array.join(""));
		});
	}

	function citySelectInit(parentId, regionType) {
		var _array = [];
		_array.push("__tag_427$15_--请选择--__tag_427$41_");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do", param, function(data) {
			for ( var i = 0; i < data.length; i++) {
				_array.push("__tag_433$17_" + data[i].regionName
						+ "__tag_433$76_");
			}
			$("#city").html(_array.join(""));
		});
	}

	//提交基础资料
	$("#jc_btn").click(function() {
		var tsex = '${map.sex}' == '' ? '' : '${map.sex}';
		if ($("#realName").val() == "") {
			// $("#u_realName").html("请填写真实姓名！");
			alert("请填写真实姓名！");
			return false
		}
		if ($("#idNo").val() == "") {
			// $("#u_idNo").html("请填写身份号码！");
			alert("请填写身份号码！");
			return false
		}
		if ($("#cellPhone").val() == "") {
			//$("#u_cellPhone").html("请填写手机号码！");
			alert("请填写手机号码！");
			return false
		}

		if ($("#vilidataNum").val() == "") {
			// $("#u_vilidataNum").html("请填写手机验证码");
			alert("请填写手机验证码！");
			return false
		}

		if ($("#img").attr("src") == "") {
			alert("请上传你的个人头像");
			return false;
		}

		var param = {};
		param["paramMap.realName"] = $("#realName").val();
		param["paramMap.idNo"] = $("#idNo").val();
		param["paramMap.cellPhone"] = $("#cellPhone").val();
		param["paramMap.vilidataNum"] = $("#vilidataNum").val();

		if (tsex == '') {
			param["paramMap.sex"] = $("input[name='paramMap_sex']:checked")
					.val();
		} else {
			param["paramMap.sex"] = $("#sex").val();
		}

		param["paramMap.birthday"] = $("#birthday").val();
		param["paramMap.highestEdu"] = $("#highestEdu").val();
		param["paramMap.eduStartDay"] = $("#eduStartDay").val();
		param["paramMap.school"] = $("#school").val();
		param["paramMap.maritalStatus"] = $(
				"input[name='paramMap_maritalStatus']:checked").val();
		param["paramMap.hasChild"] = $(
				"input[name='paramMap_hasChild']:checked").val();
		param["paramMap.hasHourse"] = $(
				"input[name='paramMap_hasHourse']:checked").val();
		param["paramMap.hasHousrseLoan"] = $(
				"input[name='paramMap_hasHousrseLoan']:checked").val();
		param["paramMap.hasCar"] = $("input[name='paramMap_hasCar']:checked")
				.val();
		param["paramMap.hasCarLoan"] = $(
				"input[name='paramMap_hasCarLoan']:checked").val();
		param["paramMap.nativePlacePro"] = $("#province").val();
		param["paramMap.nativePlaceCity"] = $("#city").val();

		param["paramMap.registedPlacePro"] = $("#registedPlacePro").val();
		param["paramMap.registedPlaceCity"] = $("#registedPlaceCity").val();

		param["paramMap.address"] = $("#address").val();
		param["paramMap.telephone"] = $("#telephone").val();
		//用户头像路径
		param["paramMap.personalHead"] = $("#img").attr("src");
		param["paramMap.iscellPhone"] = $("#iscellPhone").val();
		param["paramMap.num"] = "1";
		$.post("updateBasedate.do", param, function(data) {
			if (data.msg == "保存成功") {
				alert("保存成功");
				window.clearInterval(tipId);
				$("#clickCode5").html("点击获取验证码");
				window.location.reload();
			} else {
				if (data.msg == "保存成功2") {
					alert("保存成功");
					window.clearInterval(tipId);
					$("#clickCode5").html("点击获取验证码");
					window.location.reload();
				}
				//alert("请正确填写基本资料");
				if (data.msg == "请正确填写真实名字") {
					//$("#u_realName").html("请填写真实姓名！")
				alert("请填写真实姓名！");
			}
			if (data.msg == "真实姓名的长度为不小于2和大于20") {
				//$("#u_realName").html("真实姓名的长度为不小于2和大于20！")
				alert("真实姓名的长度为不小于2和大于20！");
			}
			if (data.msg == "请正确身份证号码") {
				// $("#u_idNo").html("请正确身份证号码！")
				alert("请正确输入你的身份证号码");
			}
			if (data.msg == "身份证已注册") {
				alert("身份证已注册!");
			}
			if (data.msg == "请正确填写手机号码") {
				// $("#u_cellPhone").html("请填写手机号码！");
				alert("请填写手机号码!");
			}
			if (data.msg == "手机号码长度不对") {
				// $("#u_cellPhone").html("手机号码长度不对！")
				alert("手机号码长度不对！");
			}

			if (data.msg == "手机已存在") {
				alert("该手机号码已经被注册过了！");
			}
			//手机验证码
			if (data.msg == "与获取验证码手机号不一致") {
				// $("#u_cellPhone").html("手机号码长度不对！")
				alert("手机号码与获取验证码手机号不一致！");
			}
			if (data.msg == "请填写验证码") {
				// $("#u_cellPhone").html("手机号码长度不对！")
				alert("请填写验证码");
			}
			if (data.msg == "请重新发送验证码") {
				// $("#u_cellPhone").html("手机号码长度不对！")
				alert("请重新发送验证码");
			}
			if (data.msg == "请输入正确的验证码") {
				// $("#u_cellPhone").html("手机号码长度不对！")
				alert("请输入正确的验证码");
			}
		}
		if (data.msg == "请正确填写手机号码") {
			// $("#u_cellPhone").html("手机号码长度不对！")
			alert("手机验证码填写错误！");
		}
		if (data.msg == "请正确填写性别") {
			// $("#u_cellPhone").html("手机号码长度不对！")
			alert("请勾选填写性别！");
		}
		if (data.msg == "请正确填写出生日期") {
			//$("#u_birthday").html("请正确填写出生日期！")
			alert("请正确填写出生日期！");
		}
		if (data.msg == "请正确填写入学年份") {
			// $("#u_eduStartDay").html("请正确填写入学年份！")
			alert("请正确填写入学年份！");
		}
		if (data.msg == "请正确填写入毕业院校") {
			// $("#u_school").html("请正确填写入毕业院校！")
			alert("请正确填写入毕业院校！");
		}
		if (data.msg == "请正确填写入学年份") {
			//  $("#u_eduStartDay").html("请正确填写入学年份！")
			alert("请正确填写入学年份！");
		}
		if (data.msg == "请正确填写最高学历") {
			//$("#u_highestEdu").html("请正确填写最高学历！")
			alert("请正确填写最高学历！");
		}
		if (data.msg == "请正确填写入籍贯省份") {
			// $("#u_nativePlacePro").html("请正确填写入籍贯省份！")
			alert("请正确填写入籍贯省份！");
		}
		if (data.msg == "请正确填写入籍贯城市") {
			//$("#u_nativePlaceCity").html("请正确填写入籍贯城市！")
			alert("请正确填写入籍贯城市！");
		}
		if (data.msg == "请正确填写入户口所在地省份") {
			//  $("#u_registedPlacePro").html("请正确填写入户口所在地省份！")
			alert("请正确填写入户口所在地省份!");
		}
		if (data.msg == "请正确填写入户口所在地城市") {
			// $("#u_registedPlaceCity").html("请正确填写入户口所在地城市！")
			alert("请正确填写入户口所在地城市!");
		}
		if (data.msg == "请正确填写入你的家庭电话") {
			//$("#u_telephone").html("请正确填写入你的家庭电话！")
			alert("请正确填写入你的家庭电话！");

		}
		if (data.msg == "你的家庭电话输入不正确") {
			//$("#u_telephone").html("请正确填写入你的家庭电话！")
			alert("请正确填写入你的居住电话");
		}

		if (data.msg == "你的居住电话输入长度不对") {
			//$("#u_telephone").html("请正确填写入你的家庭电话！")
			alert("你的居住电话输入长度不对");
		}

		if (data.msg == "请正确上传你的个人头像") {
			//$("#u_img").html("个人头像不能为空！")
			alert("个人头像不能为空！");
		}
		if (data.msg == "超时请重新登录") {
			window.location.href = 'login.do';
		}
		if (data.msg == "身份证不合法") {
			alert("你输入的身份证号码不合法,请重新输入");
			$("#u_idNo").html("请输入正确身份证号码！")
		}
		if (data.msg == "身份证已被注册") {
			alert("你输入的身份证号码身份证已被注册,请重新输入");
			$("#u_idNo").html("请重新输入身份证号码！")
		}
	}	);

	});

	$(function() {
		//上传图片
		$("#btn_personalHead")
				.click(
						function() {
							var dir = getDirNum();
							var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"
									+ dir
									+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
							json = encodeURIComponent(json);
							window.showModalDialog("uploadFileAction.do?obj="
									+ json, window,
									"dialogWidth=500px;dialogHeight=400px");
							var headImgPath = $("#img").attr("src");
							if (headImgPath != "") {
								//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
								$("#u_img").html("");
							} else {
								alert("上传失败！");
							}
						});

	});

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
	//======================工作认证

	//===============|~

	$(function() {
		$('#li_5').addClass('on');
		$("#jk_hover div").removeClass('none');
	});

	//end
</script>

	<script>
	function jumpUrl(obj) {
		window.location.href = obj;
	}
	//工作认证
	function loadWorkInit(url) {
		var bb = '${person}';//填写工作信息必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href = "owerInformationInit.do";
		} else {
			window.location.href = url;
		}
	}
	//加载该用户提现银行卡信息
	function loadBankInfo(url) {
		var bb = '${person}';//设置提现银行卡必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href = "owerInformationInit.do";
		} else {
			window.location.href = url;
		}

	}

	function bindingPhoneLoad(url) {
		window.location.href = url;
	}
</script>

	<script>
	function changeBankInfos(id) {
		var url = "queryOneBankInfo.do?bankId=" + id;
		jQuery.jBox.open("post:" + url, "银行卡变更", 600, 400, {
			buttons : {}
		});
	}
</script>
</body>
</html>
