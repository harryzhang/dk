<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript" src="css/popom.js"></script>
<link rel="stylesheet" type="text/css" href="css/fancybox.css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/hhncss.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="cle"></div>
	<div class="wytz_center">
		<div class="wdzh_top">
			<jsp:include page="/include/menu_userManage.jsp"></jsp:include>
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
			<div class="wdzh_next" style="display:block;">
				<div class="wdzh_next_left">
					<jsp:include page="/include/menu_userManage_4_left.jsp"></jsp:include>
				</div>
				<div class="wdzh_next_right">
					<div class="rmainbox">
						<p class="tips">
							温馨提示：合和年信贷对于用户上传的所有信息，都将进行加密处理。 您可以在此放心上传个人材料，您的个人信用将不会被以任何形式外泄。 <br />
							<span class="fred">注：认证资料上传完毕后，等待后台进行审核。</span>
						</p>

						<div class="rzbox">
							<h3>必要信用认证</h3>
							<p class="txt">为了保证审核效率，确保贷款用户尽快通过审核，我们将优先为五项必要认证材料上传齐全的用户提供审核服务。五项必要认证 缺失的用户，无法得到贷款。</p>
							<div class="jbrz">
								<ul>
									<s:if test="#request.basepictur==null">
										<tr align="center" class="gvItem">
											<td colspan="10">暂无数据</td>
										</tr>
									</s:if>
									<s:else>
										<s:iterator value="#request.basepictur" var="bean" status="sta">

											<li <s:if test="#sta.count==3">style="margin-right:0px;"</s:if> <s:if test="#sta.count==4">style="margin-bottom:0px;"</s:if>
												<s:if test="#sta.count==5">style="margin-bottom:0px;"</s:if>>
												<div class="pic">
													<img src="images/baseimage_d_${sta.count}.jpg" width="62" height="62" id="img_identiy" />
												</div>
												<div class="shangchuan">
													<h3>${tmyname }</h3>
													<input type="button" class="scbtn" style="cursor: pointer;" sd="${tmid}" value="点击上传" onclick="fff(this)" />
												</div>
												<div class="shico">
													<s:if test="#bean.auditStatus==1"> 审核中</s:if>
													<s:elseif test="#bean.auditStatus==2">失败</s:elseif>
													<s:elseif test="#bean.auditStatus==3">成功</s:elseif>
													<s:else>待上传</s:else>
												</div>
											</li>
										</s:iterator>
									</s:else>

								</ul>

								<div class="cle"></div>
							</div>
						</div>
						<div class="rzbox">
							<h3>可选信用认证</h3>
							<p class="txt">您可以选择上传的认证项目，只要通过了这些可选信用认证，将会提高您的"信用等级"和"信用额度" 。</p>
							<div class="kxrz">
								<ul>
									<s:if test="#request.selectpictur==null">
										<tr align="center" class="gvItem">
											<td colspan="10">暂无数据</td>
										</tr>
									</s:if>
									<s:else>

										<s:iterator value="#request.selectpictur" var="bean" status="sta">

											<li>
												<div class="shangchuan">
													<h3>${tmyname }</h3>
													<img src="" width="62" height="62" id="img_fc" style="display: none;" /> <input type="button" class="scbtn" style="cursor: pointer;" sd="${tmid }" value="点击上传"
														onclick="fff(this)" />

													<div class="shico">
														<s:if test="#bean.auditStatus==1">审核中</s:if>
														<s:elseif test="#bean.auditStatus==2">失败</s:elseif>
														<s:elseif test="#bean.auditStatus==3">成功</s:elseif>
														<s:else>待上传</s:else>
													</div>
												</div>
											</li>
										</s:iterator>
									</s:else>
								</ul>
								<div class="cle"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="cle"></div>
			<div class="cle"></div>
		</div>
	</div>
	<div class="cle"></div>



	<!-- 这个是layer插件的 
 <a  id="parentImg" style="display: none;">
  <input type="button" value="放大" onclick="PhotoSize.action(1);" /> <input type="button" value="缩小" onclick="PhotoSize.action(-1);" /> <input type="button" value="还原大小" onclick="PhotoSize.action(0);" /> <input type="button" value="查看当前倍数" onclick="alert(PhotoSize.cpu);" /><br> 
<img id="focusphoto" src="http://files.jb51.net/upload/201107/20110713233007487.jpg" /> 
  <img  id="paramtImgscr" src=""/></a>
  -->
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<!--  <script src="layer/layer.js" type="text/javascript"></script>
<script src="layer/layer.min.js" type="text/javascript"></script> -->
	<script type="text/javascript" src="script/fancybox.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
	<script>
		var photo_w;
		var photo_y;
		function showbigpictur(data) {
			//data图片路径
			if (1) {
				$("#imagesa").attr("src", data);//将图片的值付给

				if (1) {
					$('#fancybox').trigger("click");//触发单击事件
					photo_w = $('#imagesa').css('width');
					photo_y = $('#imagesa').css('height');
					$("#imagesa").css("width");
					$("#imagesa").css("height");
					setTimeout("", 10000);
				}
			}
		}
	</script>

	<script type="text/javascript">
	<!--
		$(document).ready(function() {
			$("#fancybox").fancybox({
				'onClosed' : function() {
					$('#imagesa').attr("style", "");
				}//关闭时候调用
			});
			// 还原
			$('#normal').click(function() {
				$('#imagesa').css('width', photo_w);
				$('#imagesa').css('height', photo_y);
				$('#fancybox').click();
			});
			// 放大
			$('#big').click(function() {
				var photoWidth = parseInt($('#imagesa').css("width"));
				var w = photoWidth + photoWidth * 0.1;
				var photoHeight = parseInt($('#imagesa').css("height"));
				var h = photoHeight + photoHeight * 0.1;
				$('#imagesa').css('width', w + 'px');
				$('#imagesa').css('height', h + 'px');
				$('#fancybox').click();
			});

			//缩小
			$('#small').click(function() {
				var photoWidth = parseInt($('#imagesa').css("width"));
				var w = photoWidth - photoWidth * 0.1;
				var photoHeight = parseInt($('#imagesa').css("height"));
				var h = photoHeight - photoHeight * 0.1;
				$('#imagesa').css('width', w + 'px');
				$('#imagesa').css('height', h + 'px');
				$('#fancybox').click();
			});
		});
	</script>



	<script>
		$(function() {
			//样式选中
			$(".wdzh_next_left li").eq(2).addClass("wdzh_next_left_ong");
			dqzt(2)
			var sd = parseInt($(".l-nav").css("height"));
			var sdf = parseInt($(".r-main").css("height"));
			$(".l-nav").css("height", sd > sdf ? sd : sdf - 15);

			$("#btn_db").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall16','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=600px;dialogHeight=500px");
						var headImgPath = $("#img_db").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 16;
							$.post("addImg.do", param, function(data) {
								if (data == "-1") {
									alert("上传失败，请上传认证资料");
								}
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall16(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_db").attr("src", path);
			}
		}
	</script>

	<script>
		$(function() {

			$("#btn_dy").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall15','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_dy").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 15;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
								//alert(data);
								// window.location.href=data;
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall15(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_dy").attr("src", path);
			}
		}
	</script>

	<script>
		$(function() {

			$("#btn_xc").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall14','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_xc").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 14;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall14(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_xc").attr("src", path);
			}
		}
	</script>
	<script>
		$(function() {

			$("#btn_sp").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall13','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_sp").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 13;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
								// alert("跳转到发布页面");
								//window.location.href='data';
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall13(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_sp").attr("src", path);
			}
		}
	</script>
	<script>
		$(function() {
			//weibo
			$("#btn_wb").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall12','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_wb").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 12;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
								//window.location.href=data;
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall12(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_wb").attr("src", path);
			}
		}
	</script>


	<script>
		$(function() {

			$("#btn_sj").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall11','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_sj").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 11;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall11(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_sj").attr("src", path);
			}
		}
	</script>


	<script>
		$(function() {

			$("#btn_js").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall10','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_js").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 10;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall10(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_js").attr("src", path);
			}
		}
	</script>

	<script>
		$(function() {

			$("#btn_xl").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall9','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_xl").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 9;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall9(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_xl").attr("src", path);
			}
		}
	</script>




	<script>
		$(function() {
			//结婚
			$("#btn_jh").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall8','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_jh").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 8;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall8(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_jh").attr("src", path);
			}
		}
	</script>




	<script>
		$(function() {

			$("#btn_gc").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall7','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_gc").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 7;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall7(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_gc").attr("src", path);
			}
		}
	</script>




	<script>
		$(function() {

			$("#btn_fc").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall6','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_fc").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 6;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall6(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_fc").attr("src", path);
			}
		}
	</script>





	<script>
		$(function() {

			$("#btn_response").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall4','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_response").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 4;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall4(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_response").attr("src", path);
			}
		}
	</script>

	<script>
		$(function() {
			//收入
			$("#btn_income").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall3','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_income").attr("src");
						if (headImgPath != "") {
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	

							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 5;
							$.post("addImg.do", param, function(data) {
								if (data == -1) {
									window.location.reload();
								} else if (data == 1) {
									window.location.reload();
								} else {
									window.location.href = data;
								}
							});

						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall3(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_income").attr("src", path);
			}
		}
	</script>
	<script>
		$(function() {
			//地址
			$("#btn_address").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall2','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img_address").attr("src");
						if (headImgPath != "") {
							//window.location.href="addImage.do?userHeadImg="+headImgPath+"&materAuthTypeId=1";
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 3;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall2(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_address").attr("src", path);
			}
		}
	</script>



	<script>
		$(function() {
			//身份
			$("#bu_identiy").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall1','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPat = $("#img_identiy").attr("src");
						if (headImgPat != "") {
							var param = {};
							param["paramMap.userHeadImg"] = headImgPat;
							param["paramMap.materAuthTypeId"] = 1;
							$.post("addImg.do", param, function(data) {
								if (data == -1) {
									window.location.reload();
								} else if (data == 1) {
									window.location.reload();
								} else {
									window.location.href = data;
								}

							});
						} else {
							alert("上传失败！");
						}
					});

		});

		function uploadCall1(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img_identiy").attr("src", path);
			}
		}
	</script>





	<script>
		$(function() {
			//工作
			$("#btn_personalHead").click(
					function() {
						var dir = getDirNum();
						var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
								+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
						json = encodeURIComponent(json);
						window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
						var headImgPath = $("#img").attr("src");
						if (headImgPath != "") {
							var param = {};
							param["paramMap.userHeadImg"] = headImgPath;
							param["paramMap.materAuthTypeId"] = 2;
							$.post("addImg.do", param, function(data) {
								window.location.reload();
							});
							//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
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
	</script>
	<script type="text/javascript">
		function fff(data) {
			var t = $(data).attr("sd");
			$.jBox("iframe:showpastpictur.do?dm=" + t, {
				title : "上传",
				width : 700,
				height : 480,
				buttons : {}
			});
		}
		function ffff() {
			window.parent.window.jBox.close();
			window.location.reload();
		}
	</script>
	<script>
		//视频上传验证
		function shipingcheck(data) {
			var t = $(data).attr("sd");
			jQuery.jBox.open("showshiping.do?dm=" + t, "上传", 460, 400, {
				buttons : {}
			});
		}
		function shipingcheckf() {
			window.location.reload();
		}
	</script>



</body>
</html>
