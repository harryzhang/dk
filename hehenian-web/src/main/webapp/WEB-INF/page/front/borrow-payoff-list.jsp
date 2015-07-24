<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

  <script>
  $(function(){
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
	    var borrowId = $("#borrow_des").val();
	    praData["paramMap.borrowId"] = borrowId;
	    praData["paramMap.status"] = "2";
		$.shovePost("queryPayingDetails.do",praData,initCallBack);
	}
	function initCallBack(data){
		$("#borrow_payoff_details").html(data);
	}
	
	function payingDetails1(id){
		 $("#borrow_des").attr("value",id);
		 $("#payoff_srh").hide();
		 $("#borrow_payoff_list").hide();
		 var param = {};
		 param["paramMap.borrowId"] = id;
		 param["pageBean.pageNum"]=1;
		 param["paramMap.status"] = "2";
        $.shovePost("queryPayingDetails.do",param,function(data){
	       $("#borrow_payoff_detail").html(data);
	    });
     }
 </script>