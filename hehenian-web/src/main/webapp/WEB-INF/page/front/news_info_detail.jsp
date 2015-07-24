<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/inside.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#imgxz img {
	max-width: 620px;
}
.center_data {
	width: 1110px;
	margin-top: 1px;
}
.data_left {
	float: left;
	width: 810px;
}
.date_right {
	float: right;
	width: 280px;
}
.lctab .newlist {
	margin-top: 5px;

}
.lctab {
	padding: 0px 20px;

	margin-left: 10px;
	text-align: left;
	margin-bottom: 30px;
}
.lctab .newlist ul li {
	line-height: 36px;
	background-image: url(images/neiye1_333.jpg);
	background-repeat: repeat-x;
	background-position: left bottom;
	padding-right: 15px;
	width: 660px;
	text-align: left;

}
.lctab .newlist ul li span {
	color: #999;
	float: right;
}
.lctab .newlist ul li a {
	color: #136dad;
	background-image: url(images/gonggao_36.jpg);
	background-position: left center;
	padding-left: 15px;
	display: block;
	background-repeat: no-repeat;
}
.lctab .newlist ul li a:hover {
	color: #00315d;
	text-decoration: underline;
}
.fenye {
	text-align: center;
	padding-top: 10px;
	padding-bottom: 10px;
	font-size: 13px;
}
.fenye .fenyemain {
	line-height: 40px;
	display: inline-block;
	color: #666;
}
.fenye .fenyemain a {
	color: #666;
	vertical-align: baseline;
}
.fenye .fenyemain a:hover {
	color: red;
}
.sxnews a {
	color: #666;
}
.sxnews a:hover {
	color: #E94718;
}
.fenye .fenyemain a.yema {
	line-height: 18px;
	color: #666;
	text-align: center;
	display: inline-block;
	height: 18px;
	width: 18px;
	margin-right: 4px;
	border: 1px solid #ccc;
}
.fenye .fenyemain a.yema:hover {
	color: #FFF;
	background-color: #148dd9;
	height: 20px;
	width: 20px;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	line-height: 20px;
}
.fenye .fenyemain a.yema.on {
	line-height: 20px;
	color: #FFF;
	background-color: #148dd9;
	height: 20px;
	width: 20px;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
}
</style>
</head>
<body>

<!-- 引用头部公共部分 -->
<div style="position: absolute; height:54px; line-height:54px; background:#f3f3f3; width:100%; margin-top:151px !important; margin-top:177px;border-bottom:1px solid #e5e5e5;border-top:1px solid #e5e5e5;"></div>
<jsp:include page="/include/top.jsp"></jsp:include>
<div align="center">
  <div class="wytz_center">
    <div class="mjd_tzlc_all" style=" margin-bottom:26px;">
      <ul>
        <li id="tab_1" class="tzlc"><a href="initNews.do" style="color:#333;">公司公告</a></li>

        <%--					<li id="tab_3">正在转让的债权</li>--%>
      </ul>
    </div>
  </div>
  <div class="center_data">
    <div id="showdownlistss" class="data_left" style="	border-right: 1px #e5e5e5 solid;"> 
      <!-- 网站列表显示位置 -->
      
      <div class="lctab">
        <s:if test="#request.paramMap.title != ''">
          <div style=" line-height:40px;">
            <h2 style=" text-align:center ">${paramMap.title}</h2>
            <p class="time" style="border-bottom:1px solid #e5e5e5; text-align:center ">${paramMap.publishTime} </p>
            <div id="imgxz">${paramMap.content }</div>
          </div>
        </s:if>
        <s:else>
          <div class="lctab">
            <div class="gginfo"> 此公告已取消！ </div>
          </div>
        </s:else>
        <div class="sxnews">
          <ul style=" margin-bottom:40px;">
            <li style="background:none; line-height:20px;line-height:40px; border-bottom:0 ; border-top: 1px solid 
                       #e5e5e5;width:50%; float:left; padding-bottom:30px;">
              <s:if test="#request.previousDate!=null"> 上一条：<a style="max-width: 30px;overflow: hidden;" href="frontNewsDetails.do?id=${request.previousDate.id}">${request.previousDate.title}</a> </s:if>
              <s:else> </s:else>
            </li>
            <li style="background:none; line-height:20px;line-height:40px; border-bottom:0 ; border-top: 1px solid 
                       #e5e5e5; width:50%; float:left; text-align:right;padding-bottom:30px;">
              <s:if test="#request.lastDate!=null"> 下一条：<a style="max-width: 30px;overflow: hidden;" href="frontNewsDetails.do?id=${request.lastDate.id}">${request.lastDate.title}</a> </s:if>
              <s:else> </s:else>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div id="showDongtai" class="date_right"> 
      <!-- 网站动态显示位置 --> 
    </div>
  </div>
</div>
</div>
<div class="cle"></div>
<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script>
		$(function() {
//			hhn(4);
			$.shovePost("frontQueryNewsList.do", null, function(data) {
				$("#showDongtai").html(data);
			});
		});
	</script>
</body>
</html>
