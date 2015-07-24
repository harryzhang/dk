<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />

</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
      <div class="box">
     	<!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      </div>
    </div>
    <div class="r_main">
    <div class="tabtil">
        <ul>
        <li class="on" onclick="jumpUrl('queryFundrecordInit.do');">资金记录</li>
        <li  onclick="jumpUrl('rechargeInit.do');">充值</li>
        <li  onclick="jumpUrl('withdrawLoad.do');">提现</li>
        </ul>
        </div>
      <div class="box">
      <div class="boxmain2">
        <div class="tabmain">
          <div class="biaoge">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>账户总额</th>
    <th>可用余额</th>
    <th>冻结金额</th>
  </tr>
  <tr>
    <td align="center">￥${handleSum }</td>
    <td align="center">￥${usableSum }</td>
    <td align="center">￥${freezeSum }</td>
  </tr>
</table>

          </div>
        </div>
        </div>
       <h2 class="otherh2x">资金记录</h2>
      <div class="boxmain2">
        <div class="srh">
        查询时间
        起始时间：<input id="startTime" type="text" class="inp140" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
        结束时间：<input id="endTime" type="text" class="inp140" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
        类型:  
         <s:select list="potypeList" id="momeyType" name="momeyType" cssClass="inp80" listKey="operateType" listValue="fundMode"   headerKey="" headerValue="--请选择--"></s:select>
        <a href="javascript:void(0)" class="cxbtn" onclick="fundRecordList();">查询</a>
        </div>
        <div class="biaoge" id="fundRecord">
        </div>
      </div>
      </div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="/script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script>
    $(function(){
	     $("#zh_hover").attr('class','nav_first');
	      $('#li_2').addClass('on');
		  $('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
       }); 
       param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
		$.shovePost("queryFundrecordList.do",praData,initCallBack);
	}
	function initCallBack(data){
		$("#fundRecord").html(data);
	}
	
   function fundRecordList(){
      if($("#startTime").val()!="" && $("#endTime").val()!=""){
	        if($("#startTime").val() >$("#endTime").val() ){
	         alert("结束日期要大于开始日期");
	         return;
	      }
      }
      param["pageBean.pageNum"] = 1;
      param["paramMap.startTime"]=$("#startTime").val();
      param["paramMap.endTime"]=$("#endTime").val();
      param["paramMap.momeyType"]=$("#momeyType").val();
      $.shovePost("queryFundrecordList.do",param,function(data){
         $("#fundRecord").html(data);
      });
   }
   
   function jumpUrl(obj){
          window.location.href=obj;
       }
</script>
</body>
</html>
