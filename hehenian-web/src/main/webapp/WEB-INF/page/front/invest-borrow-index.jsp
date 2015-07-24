<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/hhncss.css">
<link href="css/user.css" rel="stylesheet" type="text/css" />
<script src="script/jquery-1.2.6.pack.js" type="text/javascript"></script>
<script src="script/add_all.js" type="text/javascript"></script>
<script src="script/MSClass.js" type="text/javascript"></script>

<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css"></link>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		$(".header_two_right_ul li").hover(function(){
			$(this).find("ul").show();
		},function(){
			$(this).find("ul").hide();
		})
		
		$(".wdzh_top_ul li:first").addClass("wdzhxxk");
		$(".wdzh_top_ul li").click(function(){
			var ppain=$(".wdzh_top_ul li").index(this);
			$(".wdzh_top_ul li").removeClass("wdzhxxk");
			$(this).addClass("wdzhxxk");
<%--			$(".wdzh_next").hide();--%>
<%--			$(".wdzh_next").eq(ppain).show();--%>
		})
		$(".tzjl_fwxy").click(function(){
			$(".tzjl_fwxyh").show();
		})
		$(".tzjl_fwxy1").click(function(){
			$(".tzjl_fwxy1h").show();
		})
	})
</script>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
    <div class="user-right"> 
    <div class="wdzh_next_right">
                	<div class="tzjl_cgtz_box">
                    	<h2><span>投资项目</span></h2>
                        <table width="768" border="0" style="border:1px solid #e5e5e5;">
                            <tr height="30">
                                <td width="300"><span>借出本息总额：</span><strong>￥${paramMap.investAmount }</strong></td>
                                <td><span>应收本息总额：</span><strong>￥${paramMap.shouldGetAmount }</strong></td>
                            </tr>
                            <tr height="30">
                                <td width="300"><span>已收本息总额：</span><strong>￥${hasGetAmount }</strong></td>
                                <td><span>未收本息总额：</span><strong>￥${paramMap.notGetAmount }</strong></td>
                            </tr>
                        </table>
                    </div>
                    <div class="tzjl_cgtz_box">
                    	<h2><span>债权明细</span></h2>
                        <table width="768" border="0" cellpadding="0" cellspacing="1" bgcolor="#e5e5e5">
                            <tr height="60">
                             <td colspan="13" bgcolor="#ffffff" style="padding-left:20px;">
                             <form action="investRecord.do" id="searchForm" method="post">
                             	<input type="hidden" name="type" value="${paramMap.type}" />
	         				    <input type="hidden" name="curPage" id="pageNum" />
                                                                                       交易日期：
                                <input type="text" id="publishTimeStart"  name="publishTimeStart" value="${paramMap.publishTimeStart }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
                                -<input type="text" id="publishTimeEnd"  name="publishTimeEnd" value="${paramMap.publishTimeEnd }" class="inp90 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
                                                                                       借款人：<input type="text" name="userName" id="userName" value="${paramMap.userName }"  />编号：<input type="text" name="id" id="id" value="${paramMap.id }" />
                                 <input type="button" value="搜索" id="search" />
                             </form>                                                          
                             </td>
                            </tr>
                            <tr align="center" height="30">
                                <td bgcolor="#ffffff" width="30">全选</td>
                                <td bgcolor="#ffffff" width="60">债权编号</td>
                                <td bgcolor="#ffffff" width="60">交易日期</td>
                                <td bgcolor="#ffffff" width="50">借款者</td>
                                <td bgcolor="#ffffff" width="70">标题</td>
                                <td bgcolor="#ffffff" width="60">年利率</td>
                                <td bgcolor="#ffffff" width="70">债权期限</td>
                                <td bgcolor="#ffffff" width="60">投资金额</td>
                                <td bgcolor="#ffffff" width="60">已收金额</td>
                                <td bgcolor="#ffffff" width="60">待收金额</td>
                                <td bgcolor="#ffffff" width="60">回收阶段</td>
                                <td bgcolor="#ffffff" width="60">债权协议</td>
                                <td bgcolor="#ffffff" width="50">操作</td>
                            </tr>
                            <s:if test="pageBean.page!=null || pageBean.page.size>0">
                            <s:iterator value="pageBean.page" var="bean">
                            <tr align="center" height="30">
                                <td bgcolor="#ffffff"><input type="checkbox" name="check" class="downloadId" value="${bean.id }" /></td>
                                <td bgcolor="#ffffff">${bean.id }</td>
                                <td bgcolor="#ffffff">${bean.investTime }</td>

                                <td bgcolor="#ffffff">${bean.username }</td>
                                <td bgcolor="#ffffff"><a href="债权详情.html">${bean.borrowTitle }</a></td>
                                <td bgcolor="#ffffff">${bean.annualRate }%</td>
                                <td bgcolor="#ffffff">${bean.debtLimit }</td>
                                <td bgcolor="#ffffff">${bean.investAmount }</td>
                                <td bgcolor="#ffffff">${bean.hasPI }</td>
                                <td bgcolor="#ffffff">${bean.notPI }</td>
                                <td bgcolor="#ffffff">${bean.hasDeadline }/${bean.deadline }</td>
                                <td bgcolor="#ffffff"><b class="tzjl_fwxy">查看</b></td>
                                <td bgcolor="#ffffff"><u class="tzjl_fwxy1">转让</u></td>
                            </tr>
                            </s:iterator>
                            </s:if>
                            <s:else>
                            	<tr align="center" height="30" colspan="13">暂无数据</tr>
                            </s:else>
                        </table>
                
                        
                        <div class="tzjl_fwxy1h">
                        	<table width="480" border="0">
                                <tr>
                                    <td colspan="2"><p>简单的债权说明：包括了债权过程的说明以及成功后收取的费用说明</p></td>
                                </tr>
                                <tr height="10"></tr>
                                <tr>
                                    <td width="120" align="right">转让天数：</td>
                                    <td width="360"><select><option>1</option></select></td>
                                </tr>
                                <tr height="10"></tr>
                                <tr>
                                    <td width="120" align="right">债权金额：</td>
                                    <td width="360">500.00元</td>
                                </tr>
                                <tr height="10"></tr>
                                <tr>
                                    <td width="120" align="right">债权期限：</td>
                                    <td width="360">3个月</td>
                                </tr>
                                <tr height="10"></tr>
                                <tr>
                                    <td width="120" align="right">转让底价：</td>
                                    <td width="360"><input type="text" /></td>
                                </tr>
                                <tr height="10"></tr>
                                <tr>
                                    <td width="120" align="right" valign="top">转让描述：</td>
                                    <td width="360"><textarea></textarea></td>
                                </tr>
                                <tr height="20"></tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td width="360"><input type="button" value="取消" /><input type="button" value="确定" /></td>
                                </tr>
                                <tr height="10"></tr>
                            </table>
                        </div>
                    </div>
                    <div class="cle"></div>
                    <p class="tzjl_quanx"><input type="checkbox" onclick="checkAll(this)"  /><input type="button" value="合并选中债权" /><input type="button" value=" 拆分选中债权" /></p>
               		<div class="cle"></div>
                    <div class="mjd_fy_all">
	    			<s:if test="pageBean.page!=null || pageBean.page.size>0">
	          			<div class="page" style=" padding-top:20px;"><p>
						   <shove:page url="investRecord.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
						   </shove:page>
	                    </p></div>    
	    			</s:if>
	    			</div>
                    <div class="cle"></div>
                </div>
    </div>
  </div>
</div>
	<div class="wytz_center">     
    	<div class="wdzh_top">        	
            <div class="wdzh_next" style="display:block;">
            	<div class="wdzh_next_left">
                	<ul>
                    	<li class="wdzh_next_left_ong"><a href="#" style=" color:#e94718;">成功投资</a></li>
                        <li><a href="#">当前投资</a></li>
                        <li><a href="#">详细统计</a></li>
                    </ul>
                </div>
                
            </div>
            <div class="cle"></div>
        </div>
    </div>
<div class="cle"></div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	$("#search").click(function(){
		if($("#publishTimeStart").val() >$("#publishTimeEnd").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
		}
		$("#pageNum").val(1);
	 	$("#searchForm").submit();
	});
});
//选择所有
function checkAll(e){
	if(e.checked){
		$(".downloadId").attr("checked","checked");
	}else{
		$(".downloadId").removeAttr("checked");
	}
}
</script>
</body>
</html>
