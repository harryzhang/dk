<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@include file="/include/taglib.jsp" %>
<script type="text/javascript">

     $(function(){
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
		$.post("querySendMails.do",praData,initCallBack);
	}
	function initCallBack(data){
		$("#send_mail_list").html(data);
	}
	
	function returnToPage_ss(pNum){
	  $("#show_send_mail").hide();
	  $("#srh").show();
	  $("#biaoge").show();
	   param['paramMap.pageNum'] = pNum;
	   initListInfo(param);
	}
	
	function showSysMailInfo(id,type){
		 $("#send_srh").hide();
		 $("#send_biaoge").hide();
	    $.post("queryEmailById.do?mailId="+id+"&type="+type,function(da){
		     $("#show_send_mail").html(da);
		});
     }
     
     //直接删除邮件
   	 function delSends(){
 		
 		var stIdArray = [];
		$("input[name='sendMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择需要删除的内容");
			return ;
		}
		if(!window.confirm("确定要删除吗?")){
 			return;
 		}
		var ids = stIdArray.join(",");
		$.post("deleteSendMails.do",{ids:ids},function(data){
           $("#send_mail_list").html(data);
        });
	}
</script>
<div class="boxmain2">
		<span id="send_mail_list"></span>
        <span id="show_send_mail"></span> 
        <div class="srh" id="send_srh">
        	<a href="javascript:void(0);" class="scbtn" onclick="delSends();">删除</a> 
        </div>
        
    </div>