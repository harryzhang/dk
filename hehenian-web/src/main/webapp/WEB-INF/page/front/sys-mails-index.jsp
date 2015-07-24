<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags"  prefix="s"%>

<script type="text/javascript">
    $(function(){
        param['paramMap.mailStatus'] = -1;
        param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
		$.shovePost("querySysMails.do",praData,initCallBack);
	}
	function initCallBack(data){
		$("#sys_mail_list").html(data);
	}
	
    function showSysMailInfo(id,type){
		 $("#srh").hide();
		 $("#biaoge").hide();
		 $("#show_mail").show();
		 var curPage = $("#curPage").val();
		
	    $.post("queryEmailById.do?mailId="+id+"&type="+type+"&curPage="+curPage,function(da){
		     $("#show_mail").html(da);
		});
     }
     
     function delSys(){
 		
 		var stIdArray = [];
		$("input[name='sysMail']:checked").each(function(){
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
		$.shovePost("deleteSysMails.do",{ids:ids},function(data){
           $("#sys_mail_list").html(data);
        });
	}
	
	function readedSys(){
 		if(!window.confirm("确定要将所选邮件标记为已读吗?")){
 			return;
 		}
 		var stIdArray = [];
		$("input[name='sysMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			return ;
		}
		var ids = stIdArray.join(",");
		param["paramMap.ids"] = ids;
		param["paramMap.type"]="readed";
		$.shovePost("updateSys2Readed.do",param,function(data){
           $("#sys_mail_list").html(data);
        });
	}
	
	
	function unReadSys(){
 		if(!window.confirm("确定要将所选邮件标记为未读吗?")){
 			return;
 		}
 		var stIdArray = [];
		$("input[name='sysMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			return ;
		}
		var ids = stIdArray.join(",");
		param["paramMap.ids"] = ids;
		param["paramMap.type"]="unread";
		$.shovePost("updateSys2UNReaded.do",param,function(data){
           $("#sys_mail_list").html(data);
        });
	}
	
	function showUnReadSys(){
	  param['paramMap.mailStatus'] = 1;
	  param["pageBean.pageNum"] = 1;
	  initListInfo(param);
	}
	
	function returnToPage_s(pNum){
	  $("#show_mail").hide();
	  $("#srh").show();
	  $("#biaoge").show();
	   param['paramMap.pageNum'] = pNum;
	   initListInfo(param);
	}
</script>

<div class="boxmain2">
          <div class="srh" id="srh">
        <a href="javascript:void(0);" class="scbtn" onclick="delSys();">删除</a> 
        <a href="javascript:void(0);" class="scbtn" onclick="readedSys();">标记为已读</a> 
        <a href="javascript:void(0);" class="scbtn" onclick="unReadSys();">标记为未读</a>
        <a href="javascript:void(0);" class="scbtn" onclick="showUnReadSys();">未读邮件</a>
        </div>
        <span id="sys_mail_list"></span>
           <span id="show_mail"></span> 
    </div>