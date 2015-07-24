function userInfoAlert(id,username){
		$.jBox("iframe:queryUserInfoinner.do?i="+id, {
	    title: username+"的详细资料",
	    width: 679,
	    height: 500,
	    buttons: { '关闭': true }
	});
}