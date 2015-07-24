<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
<style>
	.table{
		width:600px;
		float:left;
		margin-left:30px;
	}
	.table label{
	padding-right:20px;
	}	
	.table .red{
	color:#f00;
	padding-right:5px;
	}
	.txt{
		width:280px;
		height:36px;
		line-height:36px;
	}
	.btn{
  width: 280px;
  height: 36px;
  line-height: 32px;
  font-size: 20px;
  background: #44608B;
  color: #fff;
}
.tip{
  font-size: 14px;
  color: #852D5F;
  background: #EEE;
  padding: 20px;
}
.tip em{
font-style:normal;
}
</style>
</head>
<body>

<!-- 内容 -->
<div >

	<h2 class="tip">温馨提示：<em>请您按照征信报告内容如实填写以下信息,祝您工作愉快</em></h2>
	<form id="addCreditForm" method="post"> 
    <table class="table" align="center" cellpadding="3">
      <tr>
        <td width="150" align="right">
        	<label for="applyAmount"><em class="red">*</em>借款人姓名:</label>
        	
        </td>
        <td width="210"> <input type="text" class="txt" name="loanUserName" value="" /> </td>
      </tr>
      
      <tr>
        <td align="right">
        	<label for="annualRate"><em class="red">*</em>借款人电话:</label>
        </td>
       	<td> <input type="text" class="txt"  name="loanMobile" value="" /> </td>
      </tr>
      
      <tr>
        <td align="right"><label for="repayAmount"><em class="red">*</em>最近6个月月均还款额:</label></td>
        <td><input type="text" class="txt" name="repayAmount" value="" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="repayExceptionNumber"><em class="red">*</em>还款异议标注数目:</label></td>
        <td>
        	<input class="easyui-combobox"  class="txt"  id="repayExceptionNumber"  name="repayExceptionNumber" size="7"  data-options="panelHeight:'90',
							 valueField: 'value', textField: 'text', data: [{ text: '非正常', value: '非正常' },{ text: '正常', value: '正常' }]" />
        </td>
      </tr>
      
       <tr>
        <td align="right"><label for="overNumber"><em class="red">*</em>最近6个月逾期次数:</label></td>
        <td><input type="text" class="txt" name="overNumber" value="" /></td>
      </tr>
      
      <tr>
        <td align="right"><label for="creditAmount"><em class="red">*</em>信用卡额度:</label></td>
        <td>
			<input type="text" class="txt" name="creditAmount" value="" /> 
        </td>
      </tr>
      <tr>
        <td align="right"><label for="queryNumber"><em class="red">*</em>一年内机构查询贷款情况次数:</label></td>
        <td>
        	<input type="text" class="txt" name="queryNumber" value="" />
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="houseFundYears"><em class="red">*</em>公积金缴费年限:</label></td>
        <td>
        	<input type="text" class="txt" name="houseFundYears" value="" />
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="houseFundAmount"><em class="red">*</em>公积金个人存缴额:</label></td>
        <td>
			<input type="text" class="txt" name="houseFundAmount" value="" />
        </td>
      </tr>
      
      <tr>
        <td align="right"><label for="houseFundScale"><em class="red">*</em>公积金个人存缴比例%:</label></td>
        <td>
        	<input type="text" class="txt" name="houseFundScale" value="" />
        </td>
      </tr>
      
      <tr>
      	<td colspan="2">
      		<div align="right">
	      	<input type="button" class="btn" value="保存" onclick="javascript:saveCredit();" >
	      	</div>
      	</td>
      </tr>
    </table>
    
   </form>
</div>
</body>
</html>
<script type="text/javascript">

	function checkInput(){
		var loanUserName = $("input[name='loanUserName']").val();
		if("" == loanUserName){
			alert("借款人姓名不能为空");
			return false;
		}
		
		
		var loanMobile = $("input[name='loanMobile']").val();
		if("" == loanMobile){
			alert("借款人电话不能为空");
			return false;
		}
		
		var repayAmount = $("input[name='repayAmount']").val();
		if("" == repayAmount){
			alert("最近6个月月均还款额不能为空");
			return false;
		}
		
		var repayExceptionNumber = $("input[name='repayExceptionNumber']").val();
		if("" == repayExceptionNumber){
			alert("还款异议标注数目不能为空");
			return false;
		}
		
		var overNumber = $("input[name='overNumber']").val();
		if("" == overNumber){
			alert("最近6个月逾期次数不能为空");
			return false;
		}
		
		var creditAmount = $("input[name='creditAmount']").val();
		if("" == creditAmount){
			alert("信用卡额度不能为空");
			return false;
		}
		
		var queryNumber = $("input[name='queryNumber']").val();
		if("" == queryNumber){
			alert("一年内机构查询贷款情况次数不能为空");
			return false;
		}
		
		var houseFundYears = $("input[name='houseFundYears']").val();
		if("" == houseFundYears){
			alert("公积金缴费年限不能为空");
			return false;
		}
		
		var houseFundAmount = $("input[name='houseFundAmount']").val();
		if("" == houseFundAmount){
			alert("公积金个人存缴额不能为空");
			return false;
		}
		
		var houseFundScale = $("input[name='houseFundScale']").val();
		if("" == houseFundScale){
			alert("公积金个人存缴比例不能为空");
			return false;
		}
		
		return true;
	}
	
	
	function saveCredit(){
		var  url ="/credit/addCredit.html?&rand=" + Math.random();
		//检查必填项
		if(!checkInput()){
			return;
		}
		
		 $.ajax({   
			 type: 'POST',
			 dataType: 'json',
			 url: url,  
			 data:$("#addCreditForm").serialize(),
			 success: function(data){ 
				 if(data.executeStatus == 1){					 
					 $.messager.alert("提示","操作成功","info");
				 }else{
					 if(data.msg){
					 	$.messager.alert("提示",data.msg,"error");
				     }else{
				    	 $.messager.alert("提示","操作失败","error");
				     }
				 }
				 
				 $("input[type='text']").each(function(index,domEle){
					 $(this).val("");
				 });
			 } 
		});
	}
</script>