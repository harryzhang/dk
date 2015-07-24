
$(document).ready(function () {

	$(".classical-tag li a").mouseover(function () {
		$(this).parents("ul").children("li").removeClass("on");
		$(this).parent("li").addClass("on");
		var a = $(this).attr("ProjectID");
		$("#classproject").html(getClassicalInfo(myData, a)).show() 
	});
});
function getClassicalInfo(d, g) {
	var a;
	for (var c = 0;c < d.l.length;c++) {
		if (d.l[c].p == g) {
			a = d.l[c] 
		}
	}
	var e = a.p;
	var b = Math.ceil(e / 100) - 1;
	var h = "";
	var f = "";
	h = h + '<div class="classical-tag-detail">';
	h = h + a.d;
	h = h + "</div>";
	return h 
}