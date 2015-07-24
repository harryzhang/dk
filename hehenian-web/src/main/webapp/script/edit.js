
function initCallBack(data) {
	$("#dataInfo").html(data);
}

//弹出窗口关闭
function closeMthod(){
		window.jBox.close();
		window.location.reload();
	}
	
	//取消--关闭弹窗
function closeMthodes(){
		window.jBox.close();
}

function editSize(url,width,height) {
	$.jBox("iframe:" + url , {
		title : "编辑",
		top : "2%",
		width : width,
		height : height,
		buttons : {
			'关闭' : true
		}
	});
}
function edit(url, id) {
$.jBox("iframe:" + url + "?id=" + id, {
	title : "编辑",
	top : "2%",
	width : 987,
	height : 610,
	align : "center",
	buttons : {
		'关闭' : true
	}
});
}		   		

function userInfoAlert(id,username){
	var url = "iframe:queryUserInfoinner.do?i="+id;
	$.jBox(url, {
	    title: username+"的详细资料",
	    width: 679,
	    height: 550,
	    buttons: { '关闭': true }
	});
}

function realNameAuthentication_info_alert(id,username){
	$.jBox("iframe:queryPersonAuditStatusInfoAlert.do?i="+id, {
	    title: username+"的实名认证资料",
	    width: 679,
	    height: 500,
	    buttons: {}
	});
}
function realNameAuthentication_info_select(id,username){
	$.jBox("iframe:queryPersonAuditStatusInfoSelect.do?i="+id, {
	    title: username+"的实名认证资料",
	    width: 679,
	    height: 500,
	    buttons: {}
	});
}
