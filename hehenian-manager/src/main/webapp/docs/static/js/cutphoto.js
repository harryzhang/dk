var div_move = 0;
var IE = document.all ? true : false;
var tempX, tempY, oldX, oldY;
var have_move = 0;
function grasp() {
	div_move = 1;
	if (IE) {
		document.getElementById("source_div").setCapture();
	}
}

function free() {
	div_move = 0;
	have_move = 0;
	document.getElementById("source_div").releaseCapture();
}

function getMouseXY(e) {
	if (IE) { // grab the x-y pos.s if browser is IE
		tempX = event.clientX + document.body.scrollLeft;
		tempY = event.clientY + document.body.scrollTop;
	} else {
		// grab the x-y pos.s if browser is NS
		tempX = e.pageX;
		tempY = e.pageY;
	}
	// catch possible negative values in NS4
	if (tempX < 0) {
		tempX = 0;
	}
	if (tempY < 0) {
		tempY = 0;
	}
}

function move_it(e) {
	getMouseXY(e);
	if (div_move == 1) {
		if (have_move == 0) {

			oldX = tempX;
			oldY = tempY;
			have_move = 1;
		}
		var left = parseInt(document.getElementById("source_div").style.left);
		var top = parseInt(document.getElementById("source_div").style.top);

		document.getElementById("source_div").style.left = left + tempX - oldX;
		document.getElementById("source_div").style.top = top + tempY - oldY;
		oldX = tempX;
		oldY = tempY;

	}
}

function change_size(method) {
	var per = 1.0;
	if (method == 1) {
		per = 1.25;
	} else {
		per = 0.8;
	}
	w = document.getElementById("show_img").width;
	h = document.getElementById("show_img").height;

	if (method != 1 && (w < 80 || h < 100))
		return;

	document.getElementById("show_img").width = w * per;
	document.getElementById("show_img").height = h * per;

}

function micro_move(method) {
	switch (method) {
		case "up" :
			var top = parseInt(document.getElementById("source_div").style.top);
			document.getElementById("source_div").style.top = top - 5;
			break;
		case "down" :
			var top = parseInt(document.getElementById("source_div").style.top);
			document.getElementById("source_div").style.top = top + 5;
			break;
		case "left" :
			var left = parseInt(document.getElementById("source_div").style.left);
			document.getElementById("source_div").style.left = left - 5;
			break;
		case "right" :
			var left = parseInt(document.getElementById("source_div").style.left);
			document.getElementById("source_div").style.left = left + 5;
			break;
	}
}

function turn(method) {
	var i = document.getElementById('show_img').style.filter.match(/\d/)[0];
	// alert(i);
	i = parseInt(i) + parseInt(method);
	// alert(i);
	if (i < 0) {
		i += 4;
	}
	if (i >= 4) {
		i -= 4;
	}
	// alert(i);
	document.getElementById('show_img').style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(Rotation=' + i + ')';
}

function mysub() {
	var Oform = document.forms[0];
	// Oform.go.value = 1;
	var tt = document.getElementById("cutd");
	var ttop = tt.offsetTop; // TT控件的定位点高
	var tleft = tt.offsetLeft; // TT控件的定位点宽
	while (tt = tt.offsetParent) {
		ttop += tt.offsetTop;
		tleft += tt.offsetLeft;
	}
	var dd = document.getElementById("source_div");
	var ddtop = dd.offsetTop; // dd控件的定位点高
	var ddleft = dd.offsetLeft; // dd控件的定位点宽
	while (dd = dd.offsetParent) {
		ddtop += dd.offsetTop;
		ddleft += dd.offsetLeft;
	}

	Oform.width.value = document.getElementById("show_img").width;
	Oform.left.value = tleft - ddleft;// 截图点横坐标
	Oform.top.value = ttop - ddtop;// 截图点纵坐标
	if (IE) {
		Oform.turn.value = document.getElementById('show_img').style.filter.match(/\d/)[0];
	}
}
