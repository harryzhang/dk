<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<head>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../css/admin/common/dialog/popup.js"></script>
      
       <style type="text/css">
         .m table{
         font-size: 12px;
         font-family: serif;
         }
         .m table tr{
          height: 10px;
         }
       
       </style>
</head>
	<div class="m">
		<table id="help" style="width: 100%; color: #333333;"
			 bgcolor="#dee7ef" border="0">
			<tbody>
				<tr  height="2px;">
					<th colspan="2">
						<strong>添加积分</strong>
					</th>
				</tr>
				<tr >
				<td>
				用户名：
				</td>
				<td>
				${popmap.username }
				</td>
				</tr>
					<tr >
				<td>
				真实姓名：
				</td>
				<td>
				${popmap.realName }
				</td>
				</tr>
					<tr >
				<td>
				信用积分：
				</td>
				<td>
				${popmap.creditrating }
				</td>
				</tr>
						<tr >
				<td>
				会员积分：
				</td>
				<td>
				${popmap.rating }
				</td>
				</tr>
						<tr >
				<td>
				选择积分：
				</td>
				<td>
				<s:select list="#{1:'信用积分',2:'会员积分'}" id="type"></s:select>
				</td>
				</tr>
							<tr >
				<td>
				添加积分：
				</td>
				<td>
				<input type="text" id="inter" >
				</td>
				</tr>
					<tr >
				<td>
				备注：
				</td>
				<td>
				<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.remark" value="" cols="30" rows="10" id="remark"></s:textarea>
				</td>
				</tr>
							<tr >
				<td colspan="2" align="center">
			<input type="button" value="添加" id="mybut">
				</td>
				</tr>
			</tbody>
		</table>
	</div>
<script>
$(function(){
    $("#mybut").click(function(){
        var param = {};
        param["paramMap.id"]=${popmap.id};
        param["paramMap.type"]=$("#type").val();
        param["paramMap.s"]=$("#inter").val();
        param["paramMap.remark"]=$("#remark").val();
        $.post("addintegralreal.do",param,function(data){
           if(data==3){
            alert("添加成功");
            window.parent.closeMthod();
           }
           if(data==1){
           alert("添加分数不能为空");
           }
             if(data==2){
               alert("备注不能为空");
           }
             if(data==4){
               alert("添加积分失败");
           }
                if(data==5){
               alert("输入分值错误，只能为数字");
           }
        });
    });
});
</script>
<script>

</script>



