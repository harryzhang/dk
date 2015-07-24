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
	<div class="xinshou">
    	<div class="xinshou_top_all">
        	<div class="xinshou_top_pic"><img src="images/new/xinshou.png"/></div>
        </div>
        <div class="xinshou_all">
         <div style=" width:1170px; margin:0px auto; background:url(../images/new/xinshou_bg.png?t=10);"> 
	<div class="xinshou_menu">
    	<ul class="daohang" style="margin-left: -12px;">
					<li id="links">关于我们</li>
					<li id="touchus">操作指引</li>
					<li id="mediaReport">常见问题</li>
					<li id="links">本金保障</li>
					<li id="teamwork" style="display: none;">收益计算</li>
					<li id="costdesc">视频中心</li>
					<li id="costdesc">免责声明</li>
					<li id="costdesc">风险披露</li>
                   <!-- <li id="invite">常见问题</li>
					<li id="invite" style="display: none">招贤纳士</li>
					<li id="teamwork" style="display: none">合同下载</li>-->
				</ul>
                <ul style=" float:left; background:#a05426; margin-left:6px;">
                	<li style=" color:#FFF;"><a href="finance.do" style=" color:#FFF">我是老手，立即投资</a></li>
                </ul>
    </div> </div>
 
    
    
</div>  
      <div style=" width:1170px; margin:0px auto; background:url(../images/new/xinshou_bg.png?t=10);">  <div id="xxContent" class="xinshou_nr"></div>
    </div>  </div>




		

<div class="cle"></div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>

<script>
		$(function() {
//			hhn(3);
            hhnNew("topIndex-callcenter");
			var showIndex = "${param.id}";
			if(!showIndex){
				showIndex=1;
			}
			$(".xinshou_menu_nr").hide();
			$(".xinshou_menu_nr").eq(showIndex-1).show();
			$(".daohang li").eq(showIndex-1).addClass("xinshou_menu_nr_ong");
            if(showIndex=='1'||showIndex==1){
                $("#xxContent").load("/xinshou/index.jsp");
            }else{
                $("#xxContent").load("/xinshou/a"+showIndex+".jsp");
            }

		
			$(".daohang li").click(function() {
				var index = $(this).index();
				$(".daohang li").removeClass();
				$(".daohang li").eq(index).addClass("xinshou_menu_nr_ong");

				$(".xinshou_menu_nr").hide();
				$(".xinshou_menu_nr").eq(index).show();
				
				var typeid=index+1;
                if(typeid=='1'||typeid==1){
                    $("#xxContent").load("/xinshou/index.jsp");
                }else{
                    $("#xxContent").load("/xinshou/a"+typeid+".jsp");
                }

				
				/*
				if(index==6){
					$("#xxContent").load("http://baidu.com");
				}else{
					var typeid=index+1;
				$.post("queryMessageDetail.do", "typeId="+typeid, function(data) {
					
					$("#xxContent").html(data.content);
				});*/
				//})
				

				
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
