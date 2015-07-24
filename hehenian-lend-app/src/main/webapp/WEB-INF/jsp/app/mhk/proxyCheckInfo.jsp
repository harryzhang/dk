<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../include/top.jsp"%>
		<title>个人信息</title>
	</head>
	<body>
		<section>
			<article>
				<ul class="order_status">
					<li><a href="<c:url value='/app/mhk/loanInit.do?loanId=${loanId}&pageId=1'/>">个人信息</a>
						<em class="${pnal == 0 ?'fail':'' }"></em>
					</li>
					<li><a href="<c:url value='/app/mhk/loanInit.do?loanId=${loanId}&pageId=3'/>">资产信息</a>
						<em class="${ppty == 0 ?'fail':'' }"></em>
					</li>
					<c:if test="${!empty ZSTYPE}"><li><a href="<c:url value='/app/mhk/loanInit.do?loanId=${loanId}&pageId=4'/>">联系人信息</a>
						<em class="${pred == 0 ?'fail':'' }"></em>
					</li>
					</c:if>
				</ul>
				<div class="btn_box">
					<button class="btn w10" id="check">初审完成</button>
				</div>
			</article>	
		</section>
		<%@ include file="../include/foot.jsp"%>
		<script type="text/javascript">
		    $(function(){
		    	$("#check").click(function (){
		    		var failArr = $(".fail");
		    		if(${pnal}==0&&${ppty}==0<c:if test="${!empty ZSTYPE}">&&${pred}==0</c:if>){
		    			//异步提交  url= /app/mhk/firstAudit.do  参数loanId   
		    			$.post(
		    				  '<c:url value="/app/mhk/firstAudit.do?loanId=${loanId}"/>',
		    				   {},
		    				  function(data){
		    					  if(data.message!=null){
		    						  alert(data.message);
		    						  return false;
		    					  }else{
		    						  location.href=data.url;
		    					  }
		    				  },
		    				  "json"
		    			);
		    		}else{
		    			alert("请审核完上面资料再提交！");
		    			return false;
		    		}
		    		
		    	})
		    });
		</script>
	</body>
</html>