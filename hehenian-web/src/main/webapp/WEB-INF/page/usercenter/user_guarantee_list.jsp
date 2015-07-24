<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
$(function(){
	$('.tabmain').find('li').click(function(){
	$('.tabmain').find('li').removeClass('on');
	$(this).addClass('on');
	$('.lcmain_l').children('div').hide();
    $('.lcmain_l').children('div').eq($(this).index()).show();
	})
	})
</script>
  <jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
  <jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="lcnav">
    <div class="tab">
    
<div class="tabmain">
  <ul><li class="on">担保人列表</li></ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
    <div class="lcmain_l" style="float:none; margin-left:0px; width:auto;">
    <div class="lctab"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center">头像 </th>
    <th align="center">用户名</th>
    <th align="center">担保投资额度</th>
    <th align="center">所在地</th>
    <th align="center">信用积分</th>
    <th align="center">会员积分</th>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
  <tr>
    <td align="center" class="tx"><a href="#"><img src="images/neiye1_33.jpg" width="80" height="79" /></a></td>
    <td align="center" class="bt"><h2><a href="#"> 周杰伦</a></h2></td>
    <td align="center" class="lilv" style="font-size:14px; font-weight:bold;">￥40870.97<br /></td>
    <td align="center" class="xinyong">广东省 深圳 福田区</td>
    <td align="center" class="jindu"><img src="images/index9_54.jpg" width="33" height="23" /></td>
    <td align="center" class="jindu"><img src="images/credit_s33.gif" width="50" height="15" /></td>
  </tr>
    </table>
    <div class="fenye">
    <div class="fenyemain">
    <a href="index.html">首页</a>   <a href="#">上一页</a>  <a href="#" class="yema on">1</a> <a href="#" class="yema">2</a> <a href="#" class="yema">3</a> <a href="#" class="yema">4</a> <a href="#" class="yema">5</a> <a href="#" class="yema">..</a> <a href="#">下一页</a>   <a href="#">末页</a>   共 10/100 条记录   转到<select><option>1</option></select> 页</div>
    </div>
    </div>
    </div>
  </div>
</body>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-jk.js"></script>
<script>
$(function(){
    //样式选中
     $("#jk_hover").attr('class','nav_first');
	 $("#jk_hover div").removeClass('none');
});		     
</script>
</html>
