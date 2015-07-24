<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="../common/head.jsp" %>
    <link rel="stylesheet" href="http://static.hehenian.com/m/css/sideSelector.css">
    <link rel="stylesheet" href="http://static.hehenian.com/m/js/widget/modal/modalmin.css">
	<% request.setAttribute("menuIndex", 0); %>
	<title>${channel_name}-增加冲抵信息</title>
</head>
  
  <body class="bg-2 add-address">
    <header class="top-bar bg-f">
        <a href="javascript:history.go(-1);">
            <span class="icon-back2"></span>
        </a>
        <p>增加冲抵信息</p>
    </header>
    <nav class="nav-title">
        <p>
        <c:if test="${product.sub_channel==2 }">请选择冲抵物业费地址</c:if>
        <c:if test="${product.sub_channel==3 }">请选择冲抵停车费车辆</c:if>
        </p>
    </nav>
    <section class="sign-area" style="margin-top: 0;">
        <div class="sign-style br-1" id="address">
            <span class="sign-lable pr75">请选择地区</span>
            <span class="sign-ctn" id="addressText">&gt;</span>
        </div>
        <div class="sign-style br-4" id="village">
            <span class="sign-lable">请选择小区</span>
            <span class="sign-ctn" id="villageText">&gt;</span>
        </div>
       <c:if test="${product.sub_channel==3 }">
        <div class="sign-style br-3 bb-2">
            <span class="sign-lable">停车卡号</span>
            <input type="text" placeholder="请填写停车卡号" id="plateNo">
        </div>
        </c:if>
        <c:if test="${product.sub_channel==2 }">
        <div class="sign-style br-1" id="building">
            <span class="sign-lable">请选择楼栋</span>
            <span class="sign-ctn" id="buildingText">&gt;</span>
        </div>
        <div class="sign-style br-2" id="roomnum">
            <span class="sign-lable">请选择房间号</span>
            <span class="sign-ctn" id="roomnumText">&gt;</span>
        </div>
        
        <div class="sign-style br-3 bb-1">
            <span class="sign-lable">业主</span>
            <input type="text" placeholder="请填写业主姓名" id="theOwner">
        </div>
        </c:if>
    </section>

    <nav class="nav-tip2">
        <p>
        	<c:if test="${product.sub_channel==2 }">
        	温馨提示：您在设置冲抵物业费地址时，如果无法选择小区、楼栋、房间号等信息，有可能该地址暂未开通此类活动，请联系小区管理处或拨打电话400-662-1777
        	</c:if>
        	<c:if test="${product.sub_channel==3 }">
        	温馨提示：您在设置冲抵停车费车辆时，如果无法选择小区等信息，有可能该地址暂未开通此类活动，请联系小区管理处或拨打电话400-662-1777
        	</c:if>
        </p>
    </nav>

    <div class="res-sub" style="margin-top: 0;">
        <input type="submit" value="请保存信息" id="sbmitbt">
    </div>
    <input type="hidden" id="addressInput">
    <input type="hidden" id="villageInput">
    <input type="hidden" id="buildingInput">
    <input type="hidden" id="roomnumInput">
    <script src="http://static.hehenian.com/m/js/data/distrit.js"></script>
    <script src="http://static.hehenian.com/m/js/distritSelector.js"></script>
    <script src="http://static.hehenian.com/m/js/selector.js"></script>
    <script src="http://static.hehenian.com/m/js/widget/modal/modalmin.js"></script>
    <script>
    $(function() {
        sbh();
    })
    </script>
    <script>
    function showModal(msg){
    	popWindow(msg);
    }
    var myDistrit = new DistritSelector({
        data: district,
        value: '',
        text: $('#addressText'),
        input: $('#addressInput'),
        callback: function(code, text) {
            if (!code) return;
            this.text.text(text.join('-'));
            this.input.val(code.join(','));
            clearOptions(); //清除记录
        }
    });

    $('#address').on('click', function() {
        myDistrit.show();
    });

    var village = new Selector({
        title: '请选择小区',
        text: $('#villageText'),
        input: $('#villageInput'),
        showCallBack: function(target) {
            var that = this;
            target.html('<span class="loading">加载中...</span>');
            var addressInput = $('#addressInput').val();
            addressInput = addressInput.substring(addressInput.lastIndexOf("c")+1);
            var options = {type:"POST",url:"http://m.hehenian.com/common/loadCommunity.do",data:{id: addressInput,flag:1}};
            ajaxRequest(options,function(json){
            	 village.data = json;
                 village.create();
            });
        },
        callback: function(code, text) {
            this.text.text(text);
            this.input.val(code);
        }
    });
    
    var building = new Selector({
        title: '请选择楼栋',
        text: $('#buildingText'),
        input: $('#buildingInput'),
        showCallBack: function(target) {
            var that = this;
            target.html('<span class="loading">加载中...</span>');
            var villageInput = $('#villageInput').val();
            var options = {type:"POST",url:"http://m.hehenian.com/common/loadCommunity.do",data:{id: villageInput,flag:2}};
            ajaxRequest(options,function(json){
            	  building.data = json;
                  building.create();
            });
        },
        callback: function(code, text) {
            this.text.text(text);
            this.input.val(code);
        }
    });
  
   var roomnum = new Selector({
        title: '请选择房间号',
        text: $('#roomnumText'),
        input: $('#roomnumInput'),
        showCallBack: function(target) {
            var that = this;
            target.html('<span class="loading">加载中...</span>');
            var villageInput = $('#villageInput').val();
            var buildingInput = $('#buildingInput').val();
            var options = {type:"POST",url:"http://m.hehenian.com/common/loadCommunity.do",data:{id: villageInput,building:buildingInput,flag:3}};
            ajaxRequest(options,function(json){
            	 roomnum.data = json;
                 roomnum.create();
            });
        },
        callback: function(code, text) {
            this.text.text(text);
            this.input.val(code);
        }
    });
    

    $('#village').on('click', function() {
        if (!$('#addressInput').val()) {
        	showModalmin("请先选择地区");
        } else {
            village.show();
        }
    });
    
    $('#building').on('click', function() {
        if (!$('#villageInput').val()) {
        	showModalmin("请先选择小区");
        } else {
            building.show();
        }
    });
    $('#roomnum').on('click', function() {
        if (!$('#buildingInput').val()) {
        	showModalmin("请先选择楼栋");
        } else {
            roomnum.show();
        }
    });
    function clearOptions(){
	    $('#villageText').html("&gt;");
	    $('#buildingText').html("&gt;");
	    $('#roomnumText').html("&gt;");
	    $('#villageInput').val("");
	    $('#buildingInput').val("");
	    $('#roomnumInput').val("");
    }
    
    function showModalmin(content){
    	new Modalmin({
               model: 'popup',
               type: 'warning',
               content: content
        });
    }
    
    //异步请求
	function ajaxRequest(options,callback){
		$.ajax({ 
			type:options.type,
			dataType: "json",			
			url:options.url, 
			data:options.data,
			success:callback,
			error:function(data){
				showModal("网络异常，稍后重试");
			}
		});
	}
    </script>
 <script type="text/javascript">
 $("#sbmitbt").click(function () {
 　	var community = $("#villageInput").val();
 	var plateNo = $("#plateNo").val();
 	var building = $("#buildingInput").val();
 	var roomno = $("#roomnumInput").val();
 	var theOwner = $("#theOwner").val();
 	var address = $("#addressInput").val();
 	if(address==null || address.length==0){
 		showModalmin("请先选择地区");
 		return;
 	}
 	if(community==null || community.length==0){
 		showModalmin("请先选择小区");
 		return;
 	}
 	<c:if test="${product.sub_channel==3 }">
 	if(plateNo==null || plateNo.length==0){
 		showModalmin("请填写停车卡号");
 		return;
 	}
 	</c:if>
 	<c:if test="${product.sub_channel==2 }">
 	if(building==null || building.length==0){
 		showModalmin("请先选择楼栋");
 		return;
 	}
 	if(roomno==null || roomno.length==0){
 		showModalmin("请先选择房间号");
 		return;
 	}
 	if(theOwner==null || theOwner.length==0){
 		showModalmin("请填写业主姓名");
 		return;
 	}
 	</c:if>
 	var from =  ${from};
 	var pid = ${pid};
 	var param = [];
   	param.push("community="+community);
   	param.push("plateNo="+plateNo);
   	param.push("building="+building);
   	param.push("roomno="+roomno);
   	param.push("theOwner="+theOwner);
	param.push("pid="+pid);
 	 $.ajax({ 
              url: "http://m.hehenian.com/product/addOffset.do", 
              type: "post",
              dataType : "json",
              //传送请求数据
              data: param.join('&'),
              success: function (data) { //成功后返回的数据
                  //根据返回值进行状态显示
                  if (data.result == 0) {
                  		if(from == "0"){//设置默认冲抵信息
                  			window.location.href = "http://m.hehenian.com/finance/prepayCB.do?pid="+pid+"&offsetId="+data.offsetId;
                  		}else if(from == "1"){//购买修改冲抵信息
							window.location.href = "http://m.hehenian.com/product/managerOffset.do?pid="+pid;
						} else{//2冲抵列表
							window.location.href ="http://m.hehenian.com/product/queryOffsetList.do?pid="+pid;
						}
                  }else if (data.result == 2) {
                  		showModal("您的地址已经参加冲活动，冲抵结束日期为"+data.enddate);
                  }else if (data.result == 3) {
                  		showModal("该地址物业费为0,不能参加活动");
                  }else if (data.result == 4) {
                  		showModal("该地址已设置,不能重复设置");
                  }else{
                  		showModal("您填写的冲抵信息错误");
                  }
              }
          })
 }
 );
 </script>
  </body>
</html>
