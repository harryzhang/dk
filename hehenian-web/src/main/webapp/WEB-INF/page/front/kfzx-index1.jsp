<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
  </head> 
  <body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="kfbox">
    <div class="l_nav">
     <div class="l_navmain">
    <h2>帮助中心</h2>
    <ul class="help" style="width:236px; margin-top:0; font-size:14px">
      <s:iterator value="#request.types" id="callcenter" var="bean">
	    <s:if test="#bean.id==#request.typeId">
		    <li class="on"><a href="callcenter.do?type=true&cid=${bean.id}" >${bean.helpDescribe }</a></li>
	    </s:if>
	    <s:else>
	    	<li><a href="callcenter.do?type=true&cid=${bean.id}">${bean.helpDescribe }</a></li>
	    </s:else>
	</s:iterator>
    </ul>
    <h2 style="text-align:left; padding-left:20px"><span><a href="javascript:void(0);" onclick="showKfs()" >更多>></a></span>联系客服</h2>
   		 <table>
                    <tr><td nowrap="nowrap">客服热线：0755-8830982</td></tr>
					<tr><td nowrap="nowrap">客服邮箱：zhengyx@cnfantasia.com</td></tr>
					<tr><td nowrap="nowrap">客服邮箱：zhouaj@cnfantasia.com</td></tr>
					</table>
<%--    <s:if test="#request.lists ==null || #request.lists.size<=0">--%>
<%--        暂无数据--%>
<%--    </s:if>--%>
<%--    <s:else>--%>
<%--    <ul class="lxkf">--%>
<%--	    <s:iterator value="#request.lists"  var="bean">--%>
<%--		     <li>--%>
<%--		      <a target="_blank" --%>
<%--		      href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes" >--%>
<%--		      <shove:img src="${bean.kefuImage }" defaulImg="images/default-img.jpg" title="${bean.name }" width="72" height="72"  ></shove:img>--%>
<%--		      </a>--%>
<%--		      </li>--%>
<%--	    </s:iterator>--%>
<%--     </ul>--%>
<%--    </s:else>--%>
    </div>
    </div>
    <div class="r_main" id="kfs">
      <div class="box">
      <h2><s:property value="#request.curDes" default="新手入门" ></s:property></h2>
      <div class="boxmain">
      <ul>
	      <s:if test="pageBean.page!=null || pageBean.page.size>0">
	      <s:iterator value="pageBean.page"  var="bean" status="sta">
	       <li><a id="qs" href="javascript:showAnswer(${bean.questionId})" onclick="javascript:cp(${bean.questionId});"><strong>问：</strong><span id="qss${bean.questionId}">${bean.question}</span></a>
	      </li>
	      </s:iterator>
	      <s:else>
	          暂无数据
	      </s:else>
	      </s:if>
      </ul>
      </div>
      </div>
      <div class="fenye">
    <div class="page">
	<p>
	   	<shove:page url="callcenter.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	   		<s:param name="cid" >${cid}</s:param>
		</shove:page>
    </p>
</div> 
<div class="box" align="left"><h2>
  <span id="qs1"><!--  答：如何注册网站会员--></span></h2>
<div class="boxmain" align="left">
<p class="txt">
<span id="dataInfo"><!--  通过会员注册页面，完成必填项目进行网站会员注册--></span></p></div></div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
</div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript">

$(function(){
     var aa = '${showkf}';
		if(aa=='kfs'){//跳转到客服页面
			showKfs();
		}
   });
   
    function showKfs(){
    	$.shovePost("callkfs.do",null,function (data){
    		$("#kfs").html(data);
    	});
    }
    
    function showAnswer(id){
        //alert(id);
		$.shovePost("callcenterAnswer.do",{id:id},initCallBack);
 	}
 	
 	function initCallBack(data){
 		$("#dataInfo").html(data);
 	}
 	
 	function cp(id){
	    $("#qs1").html("答："+$("#qss"+id).text());
	}
	
	 $("#jumpPage").attr("href","javascript:void(null)");
	 $("#curPageText").attr("class","cpage");
		$("#jumpPage").click(function(){
		     var curPage = $("#curPageText").val();
			 if(isNaN(curPage)){
				alert("输入格式不正确!");
				return;
			 }
	    window.location.href="callcenter.do?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
		});
	
	$(function(){
    //样式选中
	var sd=parseInt($(".l_navmain").css("height"));
	var sf=parseInt($(".r_main").css("height"));
	if(sd<sf){
	$(".l_navmain").css("height",sf)
	}
		dqzt(6);
 
	});	
</script>

</body>
</html>
