<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-用户银行卡管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script>
 $(function(){
 
 
  	if('${materialsauthMap.criditing}' != ""){
 		$("#sco").attr("disabled","true");
 	}
 	if('${materialsauthMap.criditing}' != ""){
 		$("#dd").hide();
 	}
 	if('${materialsauthMap.criditing}' != ""){
 		$("#yyy").hide();
 	}
 });
 </script>
	</head>

	<body>
		<div id="right"
			style="background-image: url; background-position: top; background-repeat: repeat-x;">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
							
						         <tr>
									<td class="f66" align="left" width="25%" height="36px">
									 
										通过：<input type="radio" value="1" name="ccc"
										 <s:if test="#request.materialsauthMap.auditStatus==3">	checked="checked"</s:if>
										 >
										&nbsp;&nbsp;
									失败通过：<input type="radio" value="2" name="ccc"
									
									 <s:if test="#request.materialsauthMap.auditStatus==2">	checked="checked"</s:if>
									>
									</td>
								 </tr>
								 
								<tr>
									<td class="f66" align="left" width="25%" height="36px">
										通过信用分数：
										&nbsp;
									  <input type="text"  name="sco" id="sco" value="${materialsauthMap.criditing }" >
									</td>
								 </tr>
								 	<tr>
									<td class="f66" align="left" width="25%" height="36px">
										审核情况：<span style="color: red;">${materialsauthMap.option }</span>
										&nbsp;
									</td>
								 </tr>
								 <tr>
									<td class="f66" align="left" width="25%" height="36px">
										<div id="dd">该审核观点：
										&nbsp;
									    <s:textarea name="paramMap.content" cols="40" rows="5" id="content"/></div>
									</td>
								 </tr>
						         <tr>
									<td class="f66" align="left" width="25%" height="36px">
									  <input type="button" value="审核" id="yyy">
									</td>
								 </tr>
							
							  <tr>
									<td  align="left" width="25%" height="36px" >
									<table align="center" style="text-align: center;" border="1px;" class="f66" >
									<tbody>
                                 <tr>
          <th>修改时间</th><th>审核操作员</th><th>修改前的信用分数</th><th>修改后的信用分数</th>
          </tr>
          
          <s:if test="#request.checkList==null">
					<tr align="center" class="gvItem">
						<td colspan="4">暂无数据</td>
					</tr>
		  </s:if>
          <s:else>
           <s:iterator value="#request.checkList" var="bean2" status="sta3">
          <tr>
          <td>
          ${checkdate }
          </td><td>${adminName }</td><td>${perrecode }</td><td>${afterrecode }</td>
          </tr>
          </s:iterator>
          </s:else>
									</tbody>
									</table>
									</td>
							</tr>
							
							
								 
								 
							</tbody>
						</table>
					</div>
				</div>
			</div>
			</div>
			<script>
	 $(function(){
	 $('#yyy').click(function(){
	 var param = {};
      param["paramMap.ccc"] = $("input[name='ccc']:checked").val();
      if(($("input[name='ccc']:checked").val())== undefined ||($("input[name='ccc']:checked").val())==''){
         alert('请选择审核状态');
         return ;
      }
       param["paramMap.tmid"] = ${tmid};
       param["paramMap.sco"] = $("#sco").val();
       param["paramMap.id"] = ${i};
       if($("#sco").val()==""){
         alert('信用分数不能为空');
         return ;
      }
       param["paramMap.content"] = $("#content").val();
            if($("#content").val()==""){
         alert('审核意见不能为空');
         return ;
      }
       $.post("updateShipingadmin.do",param,function(data){
        if(data==0){
        alert("审核失败");
        }
        else if(data==1){
         alert("请选择审核状态");
        }
          else if(data==2){
           alert("审核积分不能为空");
        }
          else if(data==3){
            alert("审核意见不能为空");
        }
          else if(data==4){
           alert("审核失败");
        }
          else if(data==5){
           alert("该项已经审核了");
        }
          else if(data==6){
           alert("该项已经审核了");
        }
          else if(data==7){
           alert("审核失败");
        }
          else if(data==8){
            alert("审核失败");
        }
          else if(data==9){
            alert("审核失败");
        }
         else if(data==10){
            alert("审核成功");
             window.parent.showff();
        }
        
        
        });
    
			});
			});
			</script>
	</body>
</html>
