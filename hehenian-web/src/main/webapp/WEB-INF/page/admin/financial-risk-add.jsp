<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
</head>
	<body>
			<div id="right"
				style="background-image: url; background-position: top; background-repeat: repeat-x;">
				<div style="padding: 15px 10px 0px 10px;">
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									操作金额：
								</td>
								<td align="left" class="f66">
									<s:textfield name="paramMap.amount" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
									<span style="color: red;">*
									   <s:fielderror fieldName="paramMap['amount']" theme="simple"></s:fielderror>
									</span>
								</td>

							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									备注：
								</td>
								<td align="left" class="f66">
								    <textarea id="remark" rows="10" cols="30"></textarea>
								    <span style="color: red;"><s:fielderror fieldName="paramMap['remark']" theme="simple"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									&nbsp;
								</td>
								<td>
                                <button id="btn_save" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"  ></button>
                                &nbsp;<button id="reset" type="reset" style="background-image: url('../images/admin/btn_reback.jpg');width: 70px;height: 26px;border: 0px"></button>&nbsp;
                            </td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script>
	$(function(){
	  //提交表单
   	  $('#btn_save').click(function(){
	     param['paramMap.amount'] = $('#paramMap_amount').val();
	     param['paramMap.remark'] = $('#remark').val();
	     $.shovePost('addRisk.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack == 1){
		          alert("操作成功!");
		          window.parent.closeReresh();
		          return false;
		   }
		   alert(callBack);
		 });
	  });
	  $('#reset').click(function(){
	      window.parent.close();
	  });
	});
</script>
	</body>
</html>
