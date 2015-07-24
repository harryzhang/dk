<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../common/head.jsp" %>
	<title>冲抵地址列表</title>
	<meta charset="utf-8">
</head>
<body class="bg-2">
	<header class="top-bar">
		<a href="javascript:history.go(-1);">
			<span class="icon-back">
			</span>
		</a>
		<p>冲抵地址列表
		<a href="http://m.hehenian.com/product/setOffset.do?pid=${pid}&from=2" class="btn">
			<c:if test="${product.sub_channel eq 3}">添加车辆</c:if>
        	<c:if test="${product.sub_channel eq 2}">添加地址</c:if>
		</a>
		</p>
	</header>
	<nav class="nav-tip">
        <p>温馨提示：在缴费地址区域向左滑动，可以删除缴费地址。
        </p>
    </nav>
     <div id="address-list">
    
    <c:forEach var="row" items="${data}">
    
	    <c:if test="${product.sub_channel eq 3}">
	    <div class="address-list br-1">
	        <ul>
	            <li>
	                <label>停车卡号：</label>${row.plate_number}
	            </li>
	            <li>
	                <label>小　　区：</label>${row.addressDesc}
	                
	            </li>
	        </ul>
	        <i class="tagging"><a href="javascript:void(0)" onclick="chongdi(${row.id},'${row.plate_number}','${row.addressDesc}')">冲抵</a></i>
	        <a href="javascript:;" data-address="${row.addressDesc}" data-plateno = "${row.plate_number}" data-id="${row.id}" class="btn-delete"></a>
	        
	    </div>
	    </c:if>
    
	    <c:if test="${product.sub_channel eq 2}">
	    <div class="address-list br-1">
	        <ul>
	        	<!-- <li>
	            	<label>业主：</label>${row.theowner}
	            </li>
	             -->
	            <li>
	            	<label>房 间 号：</label>${row.roomnum}
	            </li>
	            <li>
	                <label>小　　区：</label>${row.addressDesc}
	                
	            </li>
	        </ul>
	        <i class="tagging"><a href="javascript:void(0)" onclick="chongdi(${row.id},'${row.roomnum}','${row.addressDesc}')">冲抵</a></i>
	        <a href="javascript:;" data-address="${row.addressDesc}" data-plateno = "${row.roomnum}" data-id="${row.id}" class="btn-delete"></a>
	        
	    </div>
	    </c:if>
    
	</c:forEach>
		
	</div>
	
	<!-- 确认冲抵地址弹框 -->
    <section class="modal hide center" id="BuyInfo">
        <div class="modal-bd">
            <h3 class="modal-title">
               请您确认冲抵地址
            </h3>
            <ul class="modal-info">
                <li><c:if test="${product.sub_channel eq 3}">停车卡号：</c:if><c:if test="${product.sub_channel eq 2}">房间号：</c:if><span class="plateno"></span></li>
                <li>小区：<span class="addr"></span></li>
                <li></li>
            </ul>
            <div class="modal-btns">
                <input ok type="button" value="确认">
                <input cancel type="button" value="返回">
            </div>
        </div>
    </section>
    
    <!-- 确认冲抵地址弹框 -->
    <section class="modal hide center" id="DeleteInfo">
        <div class="modal-bd">
            <h3 class="modal-title">
               请您确认删除地址
            </h3>
            <ul class="modal-info">
                <li><c:if test="${product.sub_channel eq 3}">停车卡号：</c:if><c:if test="${product.sub_channel eq 2}">房间号：</c:if><span class="plateno"></span></li>
                <li>小区：<span class="addr"></span></li>
                <li></li>
            </ul>
            <div class="modal-btns">
                <input ok type="button" value="确认">
                <input cancel type="button" value="返回">
            </div>
        </div>
    </section>
    <%@ include file="../common/tail.jsp" %>
</body>
<script type="text/javascript">
swipOpera( $('#address-list'), '.address-list' );

$('#address-list').delegate('.btn-delete', 'click', function(e){
    e.stopPropagation();
    e.preventDefault();
    // alert($(this).data('id'));
    shanchu($(this).data('id'),$(this).data('plateno'),$(this).data('address'));
    //$(this).parents('.address-list').remove();
});

var offsetId = -1;
var myModal = new Modal({
            id: '#BuyInfo',
            showCallBack: function() {
            },
            closeCallBack: function() {},
            events: {
                'input[ok] click': function() {
                    doOffset();
                },
                'input[cancel] click': function() {
                    myModal.close();
                }
            }
        });
var deleteModal = new Modal({
            id: '#DeleteInfo',
            showCallBack: function() {
            	
            },
            closeCallBack: function() {},
            events: {
                'input[ok] click': function() {
                    deleteOffset();
                },
                'input[cancel] click': function() {
                    deleteModal.close();
                }
            }
        });
function chongdi(id,platenoText,addrText){
	offsetId = id;
	myModal.modal.find('.addr').html(addrText);
	myModal.modal.find('.plateno').html(platenoText);
	myModal.show();
}
function shanchu(id,platenoText,addrText){
	offsetId = id;
	deleteModal.modal.find('.addr').html(addrText);
	deleteModal.modal.find('.plateno').html(platenoText);
	deleteModal.show();
}	

	function deleteOffset(){//长按3秒触发删除事件
		
		
		var pid = ${pid};
  		var param = [];
    	param.push("id="+offsetId);
		param.push("pid="+pid);
		
		$.ajax({
			url : "http://m.hehenian.com/product/deleteOffset.do",
			type : "post",//非必须.默认get
			data : param.join('&'),
			dataType : "json",//非必须.默认text
			async : true,//非必须.默认true
			cache : false,//非必须.默认false
			timeout : 15000,//非必须.默认30秒
			success : function(data){
				if(data.result == 0 ){
					location.href="http://m.hehenian.com/product/queryOffsetList.do?pid="+pid;
				}else{
					popWindow("删除失败");
				}
			   }
		}); 

	}
	//冲抵
	function doOffset(){//长按3秒触发删除事件
			window.location.href = "http://m.hehenian.com/finance/prepayCB.do?pid="+${pid}+"&offsetId="+offsetId;
	}
</script>
</html>
