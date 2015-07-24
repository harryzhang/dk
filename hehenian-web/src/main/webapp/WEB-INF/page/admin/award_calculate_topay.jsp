<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />

		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
			
		<script type="text/javascript">
			$(function(){
			   var adminId = '${sessionScope.admin.id }';
			   $("#checkId").attr("value",adminId);
			   $("#checkId").attr("name","paramMap.checkId");
			   
			   
			});	
			
			function addAwardDetail(){//审核  
			 //判断输入信息是否正确
			 if($("#tb_money").val()=="" ){
			    alert("请填写结算金额");
			    return;
			 }
			 
			 if(isNaN($("#tb_money").val())){
			   alert("请填写正确的结算金额");
			   return;
			 }
			 
			 if($("#tb_remark").val()==""){
			    alert("请填写备注");
			    return;
			 }
			  param['paramMap.userId'] =$("#userId").val();
			  param['paramMap.checkId'] = $("#checkId").val();
			  param['paramMap.handleSum'] = $("#tb_money").val();	 
			  param['paramMap.remark'] = $("#tb_remark").val();
			  $.shovePost("addAwardDetail.do",param,function(data){
			     if(data == 1){
			        alert("结算失败");
			        return;
			     }if(data ==2){
			        alert("结算金额大于未结算金额");
			        return;
			     }else if(data ==3){
			         alert("结算金额须大于0");
			         return;
			        }
			     alert("结算成功")
			     
			     var para1 = {};
			     window.parent.initListInfo(para1);
			     window.parent.close();//关闭弹出窗口
			  });
			}
			
			function cancelBtn(){
			   window.parent.close();//关闭弹出窗口
			}			
		</script>
	</head>
	<body>
		<!-- <form id="addAwardDetail" name="addAwardDetail" action="addAwardDetail.do" method="post">  -->

		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 120px; height: 35px;" align="right"
									class="blue12">
									团队长账号：

									<s:textfield id="checkId" name="paramMap.checkId"
										cssClass="in_text_2"
										cssStyle="width: 150px;height:20px;display:none"
										theme="simple"></s:textfield>
									<s:textfield id="userId" name="paramMap.userId"
										cssClass="in_text_2"
										cssStyle="width: 150px;height:20px;display:none"
										theme="simple"></s:textfield>
								</td>
								<td align="left" class="f66">
									<s:property value="paramMap.userName" default="--" />
								</td>
							</tr>
							<tr>
								<td style="width: 120px; height: 35px;" align="right"
									class="blue12">
									真实姓名：
								</td>
								<td align="left" class="f66">
									<s:property value="paramMap.realName" default="--" />
								</td>
							</tr>

							<tr>
								<td style="width: 120px; height: 35px;" align="right"
									class="blue12">
									结算金额(元)：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_money" name="paramMap.handleSum"
										cssClass="in_text_2" cssStyle="width: 150px;height:20px;"
										theme="simple"></s:textfield>
									<span id="money_tip" class="require-field">*<s:fielderror
											fieldName="paramMap['handleSum']"></s:fielderror>
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 120px; height: 35px;" align="right"
									class="blue12">
									备注：

								</td>
								<td>
									&nbsp;
								</td>

							</tr>
							<tr>
								<td colspan="2" class="f66">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
									<s:textarea id="tb_remark" cssStyle="margin-left:80px;"
										name="paramMap.remark" value="" cols="30" rows="5"></s:textarea>
									<span id="remark_tip" class="require-field">*<s:fielderror
											fieldName="paramMap['remark']"></s:fielderror>
									</span>
								</td>
							</tr>

							<tr>
								<td colspan="2">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
									<button id="btn_save" onclick="addAwardDetail();"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;

									<button id="btn_cancel" onclick="cancelBtn();"
										style="background-image: url('../images/admin/btn_reback.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!--  </form> -->
	</body>
</html>
