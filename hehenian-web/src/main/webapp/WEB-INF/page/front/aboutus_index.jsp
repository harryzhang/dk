<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<jsp:include page="/include/head.jsp"></jsp:include>
    
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="kfbox">
    <div class="leftbox">
    <h3>关于我们</h3>
    <div class="boxmain">
       <ul>
     <li id="aboutower"><a href="javascript:void(0);" >公司介绍</a></li>
    <li id="touchus" ><a  href="javascript:void(0);" >联系我们</a></li>
     <li  id="costdesc" ><a href="javascript:void(0);" >资费说明</a></li>
    <li id="mediaReport" ><a href="javascript:void(0);" >媒体报道</a></li>
<%--    <li  id="invite"><a href="javascript:void(0);" >招贤纳士</a></li>--%>
    <li id="teamwork"><a  href="javascript:void(0);" >合作伙伴</a></li>
    <li class="last" id="links"  ><a href="javascript:void(0);">友情链接</a></li>
    </ul>
    </div>
    </div>
    <div id="showcontent" class="rightbox">
       <h3>${paramMap.columName}</h3>
        <p class="zw">${paramMap.content}</p>
      <!-- 内容显示位置 -->
    </div>
 </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
     //样式选中
	dqzt(0);
$('#aboutower').addClass('on');
	//联系我们 
	$("#touchus").click(function(){
		$('#aboutower').removeClass('on');
		$('#costdesc').removeClass('on');
		$('#invite').removeClass('on');
		$('#teamwork').removeClass('on');
		$('#links').removeClass('on');
		$('#mediaReport').removeClass('on');
		 $('#touchus').addClass('on');
	    $.post("queryMessageDetail.do","typeId=7",function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html("<h3>"+data.columName+"</h3>"+
	                           "<p class='zw'>"+data.content+"</p>");
	    });
	});
	
	//关于我们
	$("#aboutower").click(function(){
		$('#costdesc').removeClass('on');
		$('#invite').removeClass('on');
		$('#teamwork').removeClass('on');
		$('#links').removeClass('on');
		$('#mediaReport').removeClass('on');
		 $('#touchus').removeClass('on');
		 $('#aboutower').addClass('on');
	    $.post("queryMessageDetail.do","typeId=4",function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html("<h3>"+data.columName+"</h3>"+
	                           "<p class='zw'>"+data.content+"</p>");
	    });
	});
	
		//资费说明
	$("#costdesc").click(function(){
		 $('#costdesc').addClass('on');
			$('#invite').removeClass('on');
			$('#teamwork').removeClass('on');
			$('#links').removeClass('on');
			$('#mediaReport').removeClass('on');
			 $('#touchus').removeClass('on');
			 $('#aboutower').removeClass('on');
	    $.post("queryMessageDetail.do","typeId=6",function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html("<h3>"+data.columName+"</h3>"+
	                           "<p class='zw'>"+data.content+"</p>");
	    });
	});
	
	
		//招贤纳士
	$("#invite").click(function(){
		 $('#invite').addClass('on');
		 $('#costdesc').removeClass('on');
		 $('#teamwork').removeClass('on');
		 $('#links').removeClass('on');
		 $('#mediaReport').removeClass('on');
		 $('#touchus').removeClass('on');
		 $('#aboutower').removeClass('on');
	    $.post("queryMessageDetail.do","typeId=10",function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html("<h3>"+data.columName+"</h3>"+
	                           "<p class='zw'>"+data.content+"</p>");
	    });
	});
	
			//合作伙伴
	$("#teamwork").click(function(){
		 $('#invite').removeClass('on');
		 $('#costdesc').removeClass('on');
		 $('#links').removeClass('on');
		 $('#mediaReport').removeClass('on');
		 $('#touchus').removeClass('on');
		 $('#aboutower').removeClass('on');
		 $('#teamwork').addClass('on');
	    $.post("queryMessageDetail.do","typeId=11",function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html("<h3>"+data.columName+"</h3>"+
	                           "<p class='zw'>"+data.content+"</p>");
	    });
	});
	
				//友情链接
	$("#links").click(function(){
		 $('#invite').removeClass('on');
		 $('#costdesc').removeClass('on');
		 $('#teamwork').removeClass('on');
		 $('#mediaReport').removeClass('on');
		 $('#touchus').removeClass('on');
		 $('#aboutower').removeClass('on');
		 $('#links').addClass('on');
	    $.post("frontQueryMediaReportdList.do","",function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html(data);
	    });
	});
	
					//媒体报道
	$("#mediaReport").click(function(){
		 $('#mediaReport').addClass('on');
		 $('#invite').removeClass('on');
		 $('#costdesc').removeClass('on');
		 $('#teamwork').removeClass('on');
		 $('#links').removeClass('on');
		 $('#touchus').removeClass('on');
		 $('#aboutower').removeClass('on');
		  param["pageBean.pageNum"] = 1; 
		  queryMtbd(param);
	});
	
	
	
});
function doMtbdJumpPage(i){
		if(isNaN(i)){
			alert("输入格式不正确!");
			return;
		}
		$("#pageNum").val(i);
		param["pageBean.pageNum"]=i;
		//回调页面方法
		queryMtbd(param);
	}
	function queryMtbd(parDate){
		 $.post("queryMediaReportListPage.do",parDate,function(data){
	         $("#showcontent").html("");
	         $("#showcontent").html(data);
	    });
	}
</script>
</body>
</html>
