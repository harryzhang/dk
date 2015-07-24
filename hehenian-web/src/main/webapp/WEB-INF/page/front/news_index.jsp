<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<style type="text/css">
.center_data {
	width: 989px;
}
.data_left {
	float: left;
	width: 859px;
	padding-right:15px;
	border-right:1px solid #e5e5e5;
}
.date_right {
	float: left;
	width: 280px;
	margin-left:15px;
}
</style>
</head>
<body>
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
<jsp:include page="/include/top.jsp"></jsp:include>
<div align="center">
  <div class="wytz_center">
    <div class="mjd_tzlc_all" style=" margin-bottom:26px;">
      <ul>
        <li id="tab_1" class="tzlc">公司公告</li>

        <%--					<li id="tab_3">正在转让的债权</li>--%>
      </ul>
    </div>
  </div>
  <div class="wytz_center">
    <div id="showdownlistss" class="data_left"> 
      <!-- 网站列表显示位置 --> 
    </div>
    <div id="showDongtais" class="date_right"> 
      <!-- 网站动态显示位置 --> 
    </div>
  </div>
</div>
<div class="cle"></div>

<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script>
		$(function() {

			param["pageBean.pageNum"] = 1;
			initListInfo(param);
			initNewsListInfo(null);
		});

		function initListInfo(praData) {
			$.shovePost("queryNewsListPage.do", praData, function(data) {
				$("#showdownlistss").html(data);
			});

		}

		function initNewsListInfo(praData) {
			$.shovePost("frontQueryNewsList.do", praData, function(data) {
				$("#showDongtais").html(data);
			});

		}
	</script>
</body>
</html>
