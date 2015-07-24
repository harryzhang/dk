<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="/include/includeJs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>投资曲线图</title>
<script type="text/javascript">
	$(function() {
		var income = '${income}';
		var invest = '${invest}';
		$('#container').highcharts({
			credits :{
				text : ''
			},
			chart : {
				type : 'line'
			},
			title : {
				text : '投资曲线收益图'
			},
			xAxis : {
				categories : [ '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12' ],
				title : {text:'月份'}
				
			},
			yAxis : {
				name : ['1','2'],
				title : {
					text : '金额( 元 )'
				}
			},
			tooltip : {
				enabled : false,
				formatter : function() {
					return '<b>' + this.series.name + '</b><br/>' + this.x + ': ' + this.y + '°C';
				}
			},
			plotOptions : {
				line : {
					dataLabels : {
						enabled : true
					},
					enableMouseTracking : false
				}
			},
			series : [ {
				name : '投资曲线',
				data : [ ${invest} ]
			}, {
				name : '收益曲线',
				data : [ ${income} ]
			} ]
		});
	});
</script>
</head>
<body>
	<script src="../script/highcharts.js"></script>
	<div id="container"
		style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
