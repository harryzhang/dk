<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<style>
 .wdzh_next_right h3{
 font-size: 18px;
 margin: 6px auto 25px auto;
 padding: 0 auto 0 auto;
 text-align: center;
 }
 .zwww{
 padding:0px 20px 0px 20px;
 }
</style>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
    

	<!-- 浮动QQ div  暂时无需求 不显示 -->
	<%--<div data-label="浮动QQ" style="z-index: 1;display: none;" id="u2">
		<div data-label="展开" style="visibility: hidden;" id="pd1u2">
			<div class="u3_container" id="u3">
				<div class="u3_normal detectCanvas" id="u3_img"></div>
				<div style="visibility:hidden;" class="u4" id="u4">
					<div id="u4_rtf"></div>
				</div>
			</div>
			<div class="u5_container" id="u5">
				<div class="u5_normal detectCanvas" id="u5_img"></div>
				<div class="u6" id="u6">
					<div id="u6_rtf">
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:20px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">客服咨询</span>
						</p>
					</div>
				</div>
			</div>
			<div class="u7_container" id="u7">
				<div class="u7_normal detectCanvas" id="u7_img"></div>
				<div style="visibility:hidden;" class="u8" id="u8">
					<div id="u8_rtf"></div>
				</div>
			</div>
			<div class="u9_container" id="u9" style="cursor: pointer;">
				<div class="u9_normal detectCanvas" id="u9_img"></div>
				<div class="u10" id="u10">
					<div id="u10_rtf">
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">》</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;text-decoration:none;">&nbsp;</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">客</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">服</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">咨</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">询</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;text-decoration:none;">&nbsp;</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">》</span>
						</p>
					</div>
				</div>
			</div>
			<div class="u11_container" id="u11" style="cursor: pointer;">
				<div class="u11_normal detectCanvas" id="u11_img"></div>
				<div style="visibility:hidden;" class="u12" id="u12">
					<div id="u12_rtf"></div>
				</div>
			</div>
			<div class="u13" id="u13" style="cursor: pointer;">
				<div id="u13_rtf">
					<p style="text-align:left;">
						<span style="font-family:应用字体;font-size:18px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">官方微博</span>
					</p>
				</div>
			</div>
			<div class="u14_container" id="u14" style="cursor: pointer;">
				<div class="u14_normal detectCanvas" id="u14_img"></div>
				<div style="visibility:hidden;" class="u15" id="u15">
					<div id="u15_rtf"></div>
				</div>
			</div>
			<div class="u16_container" id="u16">
				<div class="u16_normal detectCanvas" id="u16_img"></div>
				<div style="visibility:hidden;" class="u17" id="u17">
					<div id="u17_rtf"></div>
				</div>
			</div>
			<div class="u18_container" id="u18">
				<div class="u18_normal detectCanvas" id="u18_img"></div>
				<div style="visibility:hidden;" class="u19" id="u19">
					<div id="u19_rtf"></div>
				</div>
			</div>
		</div>
		<div data-label="收缩" style="" id="pd0u2">
			<div class="u20_container" id="u20" style="cursor: pointer;">
				<div class="u20_normal detectCanvas" id="u20_img"></div>
				<div class="u21" id="u21">
					<div id="u21_rtf">
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:16px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">在</span> <span
								style="font-family:应用字体;font-size:16px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">线</span>
						</p>
						<p style="text-align:center;">
							<span style="font-family:应用字体;font-size:16px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">客</span> <span
								style="font-family:应用字体;font-size:16px;font-weight:normal;font-style:normal;text-decoration:none;color:#FFFFFF;">服</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div> --%>


<div class="gywm">
	<div class="gywm_menu">
    	<ul class="daohang" style="margin-left: -12px;">
					<li id="links">关于我们</li>
					<li id="touchus">平台原理</li>
					<li id="mediaReport">法律政策</li>
					<li class="last" id="links">资质证照</li>
					<li id="teamwork">合作伙伴</li>
					<li id="costdesc" style="display: none">收费标准</li>
					<li id="invite" style="display: none">招贤纳士</li>
					<li id="teamwork" style="display: none">合同下载</li>
				</ul>
    </div> 
</div>    

<div style=" width:1170px; margin:0px auto;">
			<!-- 平台原理 div  -->
			<div class="gywm_menu_nr "></div>


			<!-- 法律政策 div  -->
			<div class="gywm_menu_nr "></div>


			<!-- 资质证照 div  -->
			<div class="gywm_menu_nr" ></div>


			<!-- 招贤纳士 div  -->
			<div class="gywm_menu_nr"  "display: none"></div>


			<!-- 资费说明 div  -->
			<div class="gywm_menu_nr"></div>


			<!-- 合同下载 div  -->
			<div class="gywm_menu_nr"></div>


			<!-- 合作伙伴 div  -->
			<div class="gywm_menu_nr" ></div>


			<!-- 关于我们div  -->
			<div class="gywm_menu_nr" ></div>
			
			
			<!-- 提现标准 div  -->
			<div class="gywm_menu_nr"></div>


			<div class="cle"></div>

</div>


	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
	<script>
		$(function() {
			hhn(0);
			
			var showIndex = "${request.id}";
			$(".gywm_menu_nr").hide();
			$(".gywm_menu_nr").eq(showIndex-1).show();
			$(".daohang li").eq(showIndex-1).addClass("gywm_menu_nr_ong");
			$.post("queryMessageDetail.do?typeId="+showIndex,null, function(data) {
				$(".gywm_menu_nr").eq(showIndex-1).html("<h3>" + data.columName + "</h3>" + "<div class='zwww'>" + data.content + "</div>");
			});

			$(".daohang li").click(function() {
				var index = $(this).index();
				$(".daohang li").removeClass();
				$(".daohang li").eq(index).addClass("gywm_menu_nr_ong");

				$(".gywm_menu_nr").hide();
				$(".gywm_menu_nr").eq(index).show();

				var typeid=index+1;
				$.post("queryMessageDetail.do", "typeId="+typeid, function(data) {
					$(".gywm_menu_nr").eq(index).html("<h3 >" + data.columName + "</h3>" + "<div class='zwww'>" + data.content + "</div>");
				});
			});

			$(".daohang li").hover(function() {
				$(this).css("color", "#e94718");
			}, function() {
				$(this).css("color", "#333333");
			});
		});

		function doMtbdJumpPage(i) {
			if (isNaN(i)) {
				alert("输入格式不正确!");
				return;
			}
			$("#pageNum").val(i);
			param["pageBean.pageNum"] = i;
			//回调页面方法
			queryMtbd(param);
		}
		function queryMtbd(parDate) {
			$.post("queryMediaReportListPage.do", parDate, function(data) {
				$("#showcontent").html("");
				$("#showcontent").html(data);
			});
		}
	</script>
</body>
</html>
