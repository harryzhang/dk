<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags"  prefix="s"%>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">

    $(function(){
        var param={};
        param["pageBean.pageNum"] = 1;
        param['paramMap.mailStatus'] = -1;
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
		$.post("queryReciveMails.do",praData,initCallBack);
	}
	function initCallBack(data){
		$("#receive_mail_list").html(data);
	}
	
    function showSysMailInfo(id,type){
		 $("#re_srh").hide();
		 $("#re_biaoge").hide();
		 $("#show_receive_mail").show();
	    $.post("queryEmailById.do?mailId="+id+"&type="+type,function(da){
		     $("#show_receive_mail").html(da);
		});
     }
     
     function delReceives(){
 		
 		var stIdArray = [];
		$("input[name='receiveMail']:checked").each(function(){
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
		$.post("deleteReceiveMails.do",{ids:ids},function(data){
           $("#receive_mail_list").html(data);
        });
	}
	
	function readedReceives(){
 		if(!window.confirm("确定要将所选邮件标记为已读吗?")){
 			return;
 		}
 		var stIdArray = [];
		$("input[name='receiveMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			return ;
		}
		var ids = stIdArray.join(",");
		
		param["paramMap.ids"] = ids;
		param["paramMap.type"]="readed";
		$.post("updateReceive2Readed.do",param,function(data){
           $("#receive_mail_list").html(data);
        });
	}
	
	function unReadReceives(){
 		if(!window.confirm("确定要将所选邮件标记为未读吗?")){
 			return;
 		}
 		var stIdArray = [];
		$("input[name='receiveMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			return ;
		}
		var ids = stIdArray.join(",");
		param["paramMap.ids"] = ids;
		param["paramMap.type"]="unread";
		$.shovePost("updateReceive2UNReaded.do",param,function(data){
           $("#receive_mail_list").html(data);
        });
	}
	 
    function replayMailInit(id,type){//邮件回复
       var url = "replayMail.do?id="+id+"&type="+type;
       $.jBox("iframe:" + url, {
		     title: "站内信回复",
		     width: 550,
		     height: 500,
		     buttons: { }
		  });
       return false;
       
    }
    
    function showUnReadReceives(){
      param["pageBean.pageNum"] = 1;
	  param['paramMap.mailStatus'] = 1;
	  initListInfo(param);
	}
	
	function returnToPage_r(pNum){
	  $("#show_receive_mail").hide();
	  $("#re_srh").show();
	  $("#re_biaoge").show();
	   param['paramMap.pageNum'] = pNum;
	   initListInfo(param);
	}
</script>
<div class="boxmain2">
	<span id="receive_mail_list"></span>
	<span id="show_receive_mail"></span> 
    <div class="srh" id="re_srh">
    <a href="javascript:void(0);" class="scbtn" onclick="delReceives();">删除</a> 
    <a href="javascript:void(0);" class="scbtn" onclick="readedReceives();">标记为已读</a> 
    <a href="javascript:void(0);" class="scbtn" onclick="unReadReceives();">标记为未读</a>
    </div>
</div>