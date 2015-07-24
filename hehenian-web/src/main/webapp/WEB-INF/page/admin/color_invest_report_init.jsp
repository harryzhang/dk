<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>彩生活报表统计</title>
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
  <div style="padding: 15px 10px 0px 10px;">
    <div>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="100" height="28"  class="main_alll_h2"><a href="javascript:void(0);">彩生活报表</a></td>
          <td width="2">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </div>
    <div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
      <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
        <tbody>
          <tr>
            <td class="f66" align="left" width="50%" height="36px">
              用户创建时间段:&nbsp;<input id="regTimeStart" class="Wdate" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;--&nbsp;<input id="regTimeEnd" class="Wdate" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;&nbsp;
              最近投资时间段:&nbsp;<input id="investTimeStart" class="Wdate" value="2014-06-30" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;--&nbsp;<input id="investTimeEnd" class="Wdate" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>&nbsp;
              <div style="padding-top:15px;">
              	投资人所属事业部:&nbsp;<input id="ciBusGroupName" value=""  class="inp80" />&nbsp;&nbsp;
                投资人所属小区:&nbsp;<input id="ciCname" value=""  class="inp80" />&nbsp;&nbsp;
                推荐人所属事业部:&nbsp;<input id="refBusGroupName" value=""  class="inp80" />&nbsp;&nbsp;
                推荐人所属小区:&nbsp;<input id="refCname" value=""  class="inp80" />&nbsp;&nbsp;
                <input id="search" type="button" value="查找" name="search" />&nbsp;&nbsp;
              </div></td>
          </tr>
        </tbody>
      </table>
      <div id="divList" style="display:none;overflow-x:auto;"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></div>
      <div> </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
			$(function(){
				//initListInfo(param);
				$('#search').click(function(){
				   $("#divList").show();
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});				
			});			
			function initListInfo(praData){
				praData["paramMap.regTimeStart"] = $("#regTimeStart").val();
				praData["paramMap.regTimeEnd"] = $("#regTimeEnd").val();
				praData["paramMap.investTimeStart"] = $("#investTimeStart").val();
				praData["paramMap.investTimeEnd"] = $("#investTimeEnd").val();
				praData["paramMap.ciBusGroupName"] = $("#ciBusGroupName").val();
				praData["paramMap.ciCname"] = $("#ciCname").val();
				praData["paramMap.refBusGroupName"] = $("#refBusGroupName").val();
				praData["paramMap.refCname"] = $("#refCname").val();
		 		$.shovePost("colorInvestReport.do",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
			
</script>
</html>
