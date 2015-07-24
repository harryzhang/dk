<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript">
</script>
<script>
$(function(){
 $("#dd").click(function(){
 var ttt = $("#tat").val();
 window.parent.ffff(ttt,'${id}')
 });


});
</script>
</head>
<body>
<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 400px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
							
						         <tr>
									<td class="f66" align="center" width="25%" height="36px">
										<a style="font-family: 12px;font-family: sans-serif">修改修改金额范围：</a> <input type="text"  id="tat" />&nbsp;&nbsp;&nbsp;
									</td>
								 </tr>
									      <tr>
										<td class="f66"   style="padding-left:  185px" width="25%" height="50px">
									    <input type="button"   id="dd"  style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"/>
									</td>
								 </tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			</div>
</body>
</html>
