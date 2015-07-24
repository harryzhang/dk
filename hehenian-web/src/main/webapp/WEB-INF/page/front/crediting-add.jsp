<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <jsp:include page="/include/head.jsp"></jsp:include>

<script type="text/javascript">
	$(function(){
		$(".header_two_right_ul li").hover(function(){
			$(this).find("ul").show();
		},function(){
			$(this).find("ul").hide();
		})
		
		$(".fbjk_all_toux ul li:first").addClass(".inpp");
		$(".fbjk_all_toux ul li").click(function(){
			var ppin=$(".fbjk_all_toux ul li").index(this);
			$(".fbjk_all_toux ul li").removeClass(".inpp");
			$(this).addClass(".inpp");
			$(".fbjk_all_touxh").hide();
			$(".fbjk_all_touxh").eq(ppin).show();
		})
	})
</script>
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="center">
	<div class="wytz_center">
    	<div class="fbjk_all">
        	<h5><strong>额度申请</strong><u>当前信用额度：${creditMap.creditLimit }</u></h5>
            <table width="955" border="0">
                <tr>
                    <td align="right" width="90"><span>申请账户：</span></td>
                    <td><b>${user.username }</b></td>
                </tr>
                <tr height="10"></tr>
                <tr>
                    <td align="right" width="90"><span>申请类型：</span></td>
                    <td>信用额度</td>
                </tr>
                <tr height="10"></tr>
                <tr>
                    <td align="right" width="90"><span>申请资金：</span></td>
                    <td><input type="text" id="applyAmount" name="paramMap.applyAmount" value="${paramMap.applyAmount }" /><span>元</span></td>
                </tr>
                <tr height="10"></tr>
                <tr>
                    <td align="right" width="90" valign="top"><span>详细说明：</span></td>
                    <td><textarea id="applyDetail" name="paramMap.applyDetail">${paramMap.applyDetail }</textarea></td>
                </tr>
                <tr height="10"></tr>
                <tr>
                    <td align="right" width="90"></td>
                    <td><input type="button" value="提交申请" class="fbjk_all_put" id="bcbtn" /></td>
                </tr>
                <tr height="5"></tr>
                <tr>
                    <td align="right" width="90"></td>
                    <!-- <td><p>* 温馨提示：额度申请原则上每次最多申请1万。额度申请后，无论申请是否批准，必须隔一个月后才能再次申请，每个月只能申请 一次。如有问题,请与客服联系。</p></td>  -->
                </tr>
                <tr height="10"></tr>
            </table>
            <table width="805" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5" style="margin-left:75px;">
                <tr height="30" align="center">
                    <th scope="col" bgcolor="#f8f8f8" width="100">序号</th>
                    <th scope="col" bgcolor="#f8f8f8" width="150">申请类型</th>
                    <th scope="col" bgcolor="#f8f8f8" width="150">申请金额</th>
                    <th scope="col" bgcolor="#f8f8f8" width="300">详细说明</th>
                    <th scope="col" bgcolor="#f8f8f8" width="100">状态</th>
                </tr>
                <s:if test="pageBean.page!=null || pageBean.page.size>0">
				    <s:iterator value="pageBean.page" var="credting">
				    <tr>
				    <td bgcolor="#ffffff" align="center"><s:property value="#credting.id"/></td>
				    <td bgcolor="#ffffff" align="center"><s:property value="#credting.creditingName"/></td>
				    <td bgcolor="#ffffff" align="center"><s:property value="#credting.applyAmount"/></td>
				    <td bgcolor="#ffffff" align="center" class="ck"><s:property value="#credting.applyDetail"/></td>
				    <td bgcolor="#ffffff" align="center" class="ck">
				    <s:if test="#credting.status ==1">审核中</s:if>
				    <s:elseif test="#credting.status ==2">审核失败</s:elseif>
				    <s:elseif test="#credting.status ==3">审核通过</s:elseif>
				    </td>
				    </tr>
				    </s:iterator>
			    </s:if>
			    <s:else>
				   <tr><td colspan="5" align="center">没有数据</td></tr>
			    </s:else>
            </table>
            <div class="cle"></div>
                <div class="mjd_fy_all">
    			<s:if test="pageBean.page!=null || pageBean.page.size>0">
          			<div class="page" style=" padding-top:20px;"><p>
					   <shove:page url="creditingInit.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
					   </shove:page>
                    </p></div>    
    			</s:if>
    			</div>
                <div class="cle"></div>
        	</div>
	    <div class="cle"></div>
   </div>
</div>
<div class="cle"></div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>	     
</script>
<script type="text/javascript">
    $("#jumpPage").attr("href","javascript:void(null)");
	$("#jumpPage").click(function(){
	     var curPage = $("#curPageText").val();
		 if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
		 }
    window.location.href="creditingInit.do?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
	});
</script>
<script type="text/javascript">
       var flag = true;
       $(function(){
	      $("#bcbtn").click(function(){
	         if(flag){
	           flag = false;
	           param['paramMap.applyAmount']=$('#applyAmount').val();
	           param['paramMap.applyDetail']=$('#applyDetail').val();
	           //--add by houli 增加信用类型
	           param['paramMap.creditingName']='信用额度';
	           //--end
	           $.shovePost('addCrediting.do',param,function(data){
		         var callBack = data.msg;
		         if(callBack == 1){
		                alert("操作成功!");
		                window.location.href= 'creditingInit.do';
		                return false;
		         }
		         flag = true;
		         alert(callBack);
		       });
		     }
	      });
      });		     
</script>
</body>
</html>