/*
	公共方法文件
*/
var $parent = self.parent.$;

$(function(){
	//隐藏显示查询条件区域
	$('#openOrClose').on("click",function(){
		$('#conditon').toggle(80);
		setTimeout(domresize,100);//条件隐藏，改变表格高度
	});	
});

//打印对象的属性
function printObject(o){
	for(var k in o){
		//console.log(k + ":" + o[k]);
	}
}