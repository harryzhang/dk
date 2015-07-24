<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<input type="hidden" id="payId" value="${payMap.id}"/>
<div class="nymain" style="width:400px;margin-top:-10px;">
  <div class="wdzh" style="border:none;" >
    <div class="r_main" style="border:none;" >
      <div class="box" style="border:none;" >
        <div class="boxmain2" style="border:none;" >
          <div class="biaoge2" style="border:none;" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11%">账户余额： </td>
    <td width="89%"><strong>${payMap.totalSum}元 </strong></td>
  </tr>
  <tr>
    <td>可用余额： </td>
    <td><strong>${payMap.usableSum }元 </strong></td>
  </tr>
  <tr>
    <td>还款日期： </td>
    <td><strong> ${payMap.repayDate }  </strong></td>
  </tr>
  <tr>
    <td>待还本息：</td>
    <td><strong>${payMap.forPI }元</strong></td>
  </tr>
  <tr>
    <td>逾期本息：</td>
    <td><strong>0.00元</strong></td>
  </tr>
  <tr>
    <td>需还总额：</td>
    <td><strong>${payMap.needSum }元</strong></td>
  </tr>
  <tr>
    <td>交易密码：</td>
    <td><input type="password" class="inp140" id="pwd" maxlength="20"/>
    <span style="color: red; float: none;" id="pwd_tip" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td>验证码：</td>
    <td><input type="text" class="inp100x" name="paramMap.code" id="code"/>
		 <img src="admin/imageCode.do?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td style="padding-top:20px;"><a style="cursor:pointer;" id="btnsave" class="bcbtn">确认还款</a></td>
  </tr>
        </table>

        </div>
    </div>
</div>
    </div>
  </div>
</div>

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
     $('#btnsave').click(function(){
	     param['paramMap.id'] = $("#payId").val();
	     param["paramMap.code"] = $("#code").val();
	     param['paramMap.pwd'] = $('#pwd').val();
	     $('#btnsave').text('受理中...');
	     $.shovePost('submitPay.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack == ''){
		          alert("操作成功!");
		          parent.location.reload();
		          return false;
		   }
		   alert(callBack);
		   switchCode();
		   $('#btnsave').text('确认还款');
		});
	 });
	 switchCode();
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.do?pageId=invest&d='+timenow);
};
</script>
</body>
</html>
