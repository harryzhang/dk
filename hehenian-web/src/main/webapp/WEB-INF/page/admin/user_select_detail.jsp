<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>用户可选认证界面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									可选资料认证
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center"  class="white12">
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left">
									用户名：${map.username }
								</td>
								<td class="f66 leftp200">
									真实姓名：${map.realName }
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
								跟踪审核员：${map.tausername }
								</td>
							</tr>
							<tr>
							 <td colspan="2" class="blue12 left">基本认证：通过了 <a  style="color:  red;"> ${totalPass.total } </a> 项</td>
							</tr>
							<tr>
							    <td class="blue12 left">
							     身份证： 
							     <s:if test="#request.map.identifyStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.identifyStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.identifyStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
								<td class="f66 leftp200">
								 工作认证：
								   <s:if test="#request.map.workStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.workStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.workStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									信用报告：
									  <s:if test="#request.map.responseStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.responseStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.responseStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
								<td class="f66 leftp200">
								       居住认证：
								         <s:if test="#request.map.addrressStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.addrressStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.addrressStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									收入验证：
									  <s:if test="#request.map.incomeStatus==1">待审核</s:if>
							     <s:elseif test="#request.map.incomeStatus==2">失败</s:elseif>
							     <s:elseif test="#request.map.incomeStatus==3">成功</s:elseif>
							     <s:else>待上传</s:else>
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									可选认证：通过了 <a style="color:  red;">${SelectPassTotal.total}</a> 项
								</td>
							</tr>
							<tr>
							</tr>
							</table>
						<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
							<tr class="gvHeader" style="height: 22px;"><th class="blue12 center">认证</th><th class="blue12 center">认证状态</th ><th class="blue12 center">认证积分</th><th class="blue12 center">审核观点</th><th class="blue12 center">审核时间</th><th class="blue12 center">操作</th></tr>
							<tr class="gvItem">
							<td>房产</td><td>
							<s:if test="#request.SelectCledit.tmhouseauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmhouseauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmhouseauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else>
							</td>
							<td>
							${SelectCledit.tmhousecriditing }
						    </td>
						    <td>${SelectCledit.houseoption}</td><td>${SelectCledit.houseauthTime }</td>
						    <td>
						    <s:if test="#request.SelectCledit.tmhouseauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=6">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmhouseauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=6">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmhouseauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=6">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!-- -->
							</tr>
							<tr class="gvItem">
							<!--  -->
							<td>购车</td>
							<td><s:if test="#request.SelectCledit.tmcarauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmcarauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmcarauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
							<td>
							${SelectCledit.carcriditing }
						    </td>
						    <td>${SelectCledit.caroption}</td><td>${SelectCledit.carauthTime }</td>
						    <td><s:if test="#request.SelectCledit.tmcarauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=7">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmcarauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=7">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmcarauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=7">审核</a></s:elseif>
						    <s:else>--</s:else></td>
							<!--  -->
							</tr>
							<tr class="gvItem">
							<!--  -->
							<td>结婚</td><td><s:if test="#request.SelectCledit.tmmerrayauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmmerrayauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmmerrayauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						 ${SelectCledit.merraycriditing }
						    </td>
						    <td>${SelectCledit.merrayoption}</td><td>${SelectCledit.merrayauthTime }</td><td>
						    <s:if test="#request.SelectCledit.tmmerrayauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=8">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmmerrayauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=8">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmmerrayauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=8">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!--  -->
							</tr>
						<tr class="gvItem">
							<!--  -->
							<td>学历</td><td><s:if test="#request.SelectCledit.tmxueliauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmxueliauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmxueliauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.xuelicriditing }
						    </td>
						    <td>${SelectCledit.xuelioption}</td><td>${SelectCledit.xueliauthTime }</td><td>
						     <s:if test="#request.SelectCledit.tmxueliauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=9">查看</s:if>
						    <s:elseif test="#request.SelectCledit.tmxueliauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=9">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmxueliauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=9">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!--  -->
							</tr>
							<tr class="gvItem">
						    <!--  -->
							<td>技术</td><td><s:if test="#request.SelectCledit.tmjishuauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmjishuauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmjishuauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						  ${SelectCledit.jishucriditing }
						    </td>
						    <td>${SelectCledit.jishuoption}</td><td>${SelectCledit.jishuauthTime }</td><td>
						       <s:if test="#request.SelectCledit.tmjishuauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=10">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmjishuauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=10">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmjishuauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=10">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
							<tr class="gvItem">
							<!--  -->
							<td>手机</td><td><s:if test="#request.SelectCledit.tmtelephoneauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmtelephoneauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmtelephoneauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
					 ${SelectCledit.telephonecriditing }
						    </td>
						    <td>${SelectCledit.telephoneoption}</td><td>${SelectCledit.telephoneauthTime }</td><td>
						         <s:if test="#request.SelectCledit.tmtelephoneauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=11">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmtelephoneauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=11">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmtelephoneauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=11">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!--  -->
							</tr>
							<tr class="gvItem">
							<!--  -->
							<td>微博</td><td><s:if test="#request.SelectCledit.tmweiboauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmweiboauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmweiboauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						    
						     ${SelectCledit.weibocriditing }
						    </td>
						    <td>${SelectCledit.weibooption}</td><td>${SelectCledit.weiboauthTime }</td><td>
						         <s:if test="#request.SelectCledit.tmweiboauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=12">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmweiboauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=12">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmweiboauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=12">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!--  -->
							</tr>
							<!--  
							<tr>
							<td>视频</td><td><s:if test="#request.SelectCledit.tmshipingauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmshipingauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmshipingauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						      ${SelectCledit.shipingcriditing }
						    </td>
						    <td>${SelectCledit.shipingoption}</td><td>${SelectCledit.shipingauthTime }</td><td>
						      <s:if test="#request.SelectCledit.tmshipingauditStatus==1">  <a href="javascript:void(0)" style="cursor: pointer;" onclick="javascript:show();">审核</a></s:if>
						      <s:elseif test="#request.SelectCledit.tmshipingauditStatus==2">  <a href="javascript:void(0)" style="cursor: pointer;" onclick="javascript:show();">查看</a></s:elseif>
						      <s:elseif test="#request.SelectCledit.tmshipingauditStatus==3">  <a href="javascript:void(0)" style="cursor: pointer;" onclick="javascript:show();">查看</a></s:elseif>
						       <s:else>未申请</s:else>
						    </td>
							</tr>
							-->
							<tr class="gvItem">
							<!--  -->
							<td>现场</td><td><s:if test="#request.SelectCledit.tmxcauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmxcauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmxcauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td><td>
						   
						         ${SelectCledit.xccriditing }
						   </td>
						    <td>${SelectCledit.xcoption}</td><td>${SelectCledit.xcauthTime }</td><td>
						    
						             <s:if test="#request.SelectCledit.tmxcauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=13">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmxcauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=13">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmxcauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=13">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
							<!--  -->
							</tr>
							<tr class="gvItem">
							<!--  -->
							<td>抵押</td><td><s:if test="#request.SelectCledit.tmdiyaauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmdiyaauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmdiyaauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						    ${SelectCledit.diyacriditing }
						    </td>
						    <td>${SelectCledit.diyaoption}</td><td>${SelectCledit.diyaauthTime }</td><td>
						    
						             <s:if test="#request.SelectCledit.tmdiyaauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=14">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmdiyaauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=14">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmdiyaauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=14">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						   <tr class="gvItem">
							<!--  -->
							<td>担保</td>
							<td><s:if test="#request.SelectCledit.tmdanbaoauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmdanbaoauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmdanbaoauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else>
						    </td>
						    <td>
						   ${SelectCledit.danbaocriditing }
						    </td>
						 <!--  modify by houli 放错地方了    ${SelectCledit.danbaooption} -->
						    <td>${SelectCledit.danbaooption}</td><td>${SelectCledit.danbaoauthTime }</td><td>
						             <s:if test="#request.SelectCledit.tmdanbaoauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=15">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmdanbaoauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=15">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmdanbaoauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=15">审核</a></s:elseif>
						    <s:else>--</s:else>
						    
						    </td>
						    <!--  -->
						    </tr> 
						    <tr class="gvItem">
							<!--  -->
							<td>其他</td><td><s:if test="#request.SelectCledit.tmqitaauditStatus==1">待审核</s:if>
							<s:elseif test="#request.SelectCledit.tmqitaauditStatus==2">失败</s:elseif>
							<s:elseif test="#request.SelectCledit.tmqitaauditStatus==3">成功</s:elseif>
						    <s:else>未申请</s:else></td>
						    <td>
						    ${SelectCledit.qitacriditing }
						    </td>
						    <td>${SelectCledit.qitaoption}</td><td>${SelectCledit.qitaauthTime }</td><td>
						    
						             <s:if test="#request.SelectCledit.tmqitaauditStatus==3"><a href="queryselectindex.do?userId=${map.id}&TypeId=16">查看</a></s:if>
						    <s:elseif test="#request.SelectCledit.tmqitaauditStatus==2"><a href="queryselectindex.do?userId=${map.id}&TypeId=16">查看</a></s:elseif>
						    <s:elseif test="#request.SelectCledit.tmqitaauditStatus==1"><a href="queryselectindex.do?userId=${map.id}&TypeId=16">审核</a></s:elseif>
						    <s:else>--</s:else>
						    </td>
						    <!--  -->
						    </tr>
						    
						    </tbody>
							
							</table>
							
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script>
/*
		function show(){ 
		     var t = ${SelectCledit.tmid}
   			  var url = "showshipingpop.do?m="+t+"&id="+${SelectCledit.id};
              ShowIframe("视频认证审核",url,500,450);
   			}
   			function showff(){ 
		     window.location.reload();
		     ClosePop();
   			}	
   			
   */			
</script>
	</body>	
</html>