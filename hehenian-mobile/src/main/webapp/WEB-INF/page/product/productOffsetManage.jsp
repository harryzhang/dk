<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>

<head>
	<%@ include file="../common/head.jsp" %>
	<% request.setAttribute("menuIndex", 2); %>
	<title>${channel_name}</title>
    <meta charset="utf-8">
</head>

<body class="bg-2">
    <header class="top-bar">
        <a href="javascript:history.go(-1);">
            <span class="icon-back">
			</span>
        </a>
        <p>冲抵信息管理</p>
        <a href="http://m.hehenian.com/product/setOffset.do?from=1&pid=${pid}" class="btn">
        <c:if test="${product.sub_channel eq 3}">添加车辆</c:if>
        <c:if test="${product.sub_channel eq 2}">添加地址</c:if>
        </a>
    </header>

    <nav class="nav-tip">
        <p>温馨提示：在缴费地址区域点击选择地址，然后继续购买。
        </p>
    </nav>
    <c:forEach var="row" items="${offsetList}">
    <c:if test="${row.defaultset == 0}">
	    <div class="address-list br-2">
	        <ul>
	        	<input type="hidden" id = "offsetId" value="${row.id}"/>
	            <li>
	                <c:if test="${product.sub_channel eq 3}"> <label>停车卡号：</label> ${row.plate_number}</c:if>
               <c:if test="${product.sub_channel eq 2}"> <label>房 间 号：</label> ${row.roomnum}</c:if>
	            </li>
	            <li>
	                <label>小　　区：</label>${row.addressDesc}
	            </li>
	        </ul>
	    </div>
    </c:if>
    <c:if test="${row.defaultset == 1}">
    <div class="address-list br-2 selected">
        <ul>
        	<input type="hidden" id = "offsetId" value="${row.id}"/>
            <li>
               <c:if test="${product.sub_channel eq 3}"> <label>停车卡号：</label> ${row.plate_number}</c:if>
               <c:if test="${product.sub_channel eq 2}"> <label>房 间 号：</label> ${row.roomnum}</c:if>
            </li>
            <li>
                <label>小　　区：</label>${row.addressDesc}
            </li>
        </ul>
    </div>
    </c:if>
    </c:forEach>
    <div class="ph-3">
			<div>
				<footer class="buy-now">
				<a href="javascript:void(0)" onclick="doBuy()">继续购买</a>
				</footer>
			</div>
	</div>
    <script>
    $(function() {
        sbh();
    })
    </script>
    <script>
    var list = $('.address-list');
    list.on('click', function(){
    	list.removeClass('selected');
    	$(this).addClass('selected');
    });
    function doBuy(){
    	var offsetId = $(".selected").find("input").val();
    	window.location.href = "http://m.hehenian.com/finance/prepayCB.do?pid="+${pid }+"&offsetId="+offsetId;
    }
    </script>
</body>

</html>
