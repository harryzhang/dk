<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<form id="form" action="addBorrow.do" method="post">
<div class="nymain">
<div class="bigbox" style="border:none">
<div class="sqdk" style="background: none;">
	<div class="l-nav">
    <ul>
    <li><a href="querBaseData.do"><span>step1</span> 基本资料</a></li>
    <li><a href="userPassData.do"><span>step2</span>上传资料</a></li>
    <li class="on last"><a href="javascript:void(0);"><span>step3</span> 发布贷款</a></li>
    </ul>
    </div>
    <div class="r-main">
   <div class="rmainbox">
		<div class="fbdk">
        <ul><li>
          <div class="btn">
          <a href="addBorrowInit.do?t=3">业主贷</a>
          </div>
          <p>纯信用贷款，贷款年利率在${minRate }-${maxRate }%之间，额度较小。</p>
        </li><li>
          <div class="btn">
          <a href="addBorrowInit.do?t=4">实地考察借款</a>
          </div>
          <p>小微企业现场考察审批，需要通过现场认证。（实地认证借款能选择性添加抵押标志）</p>
        </li><li>
          <div class="btn">
          <a href="addBorrowInit.do?t=5">机构担保借款</a>
          </div>
          <p>是指合和年在线的合作伙伴为相应的借款提供连带保证，并负有连带保证责任的借款。</p>
                         （机构担保标需要通过机构担保认证）
        </li>
        </ul>
        </div>
    </div>
    </div>
  </div>
  </div>
</div>
</form>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/xl.js"></script>
<script>
$(function(){
    //样式选中
  var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
    dqzt(2);
});		     
</script>
</body>
</html>
