<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>投资统计管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
		<div style="padding: 15px 10px 0px 20px;">
		<table id="gvNews" style="width: 1120px; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
			    <tr  class=gvHeader>
			       <th colspan="2">投资盈利统计</th>
			    </tr>
			    <tr  class="gvItem" >
			       <td colspan="2">
			       <input type="radio" name="ck_radio" value="1" onclick="clearVal();">当日&nbsp;&nbsp;
			       <input type="radio" name="ck_radio" value="2" onclick="clearVal();">本月&nbsp;&nbsp;
			       <input type="radio" name="ck_radio" value="3" onclick="clearVal();">当年&nbsp;&nbsp;
			       <input type="radio" id="radio_4" name="ck_radio" value="4">其他时间段:&nbsp;&nbsp;
									<input id="timeStart" class="Wdate" disabled="disabled" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" disabled="disabled" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									<input id="search" type="button" value="查找" name="search" />&nbsp;&nbsp;
									<input id="excel" type="button" value="导出Excel" name="excel" />
							    
			       </td>
			    </tr>
			</tbody>
		</table>
		<span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
</div>
   <div>
	</div>
</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
   $('#radio_4').click(function(){
      $('#timeStart').attr('disabled',false);
      $('#timeEnd').attr('disabled',false);
   });
   $('#search').click(function(){
   param["pageBean.pageNum"] = 1;
      initListInfo(param);
   });
   
   $("#excel").click(function(){
      var radio = $('input[name="ck_radio"]:checked').val();
      var timeStart=$("#timeStart").val();
      var timeEnd= $("#timeEnd").val();
      window.location.href="exportfinanceStatis.do?radio="+radio+"&timeStart="+timeStart+"&timeEnd="+timeEnd;
   });
   
   initListInfo(param);
});
function initListInfo(praData){
        var radio = $('input[name="ck_radio"]:checked').val();
        praData["paramMap.radio"] = radio;
		praData["paramMap.timeStart"] = $("#timeStart").val();
		praData["paramMap.timeEnd"] = $("#timeEnd").val();
		$.shovePost("financeStatisList.do",praData,initCallBack);
}
function initCallBack(data){
	$("#divList").html(data);
}
function clearVal(){
   $('#timeStart').val('');
   $('#timeEnd').val('');
   $('#timeStart').attr('disabled',true);
   $('#timeEnd').attr('disabled',true);
}
</script>
</html>
