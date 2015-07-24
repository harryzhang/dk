<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<link rel="stylesheet" href="../css/jbox/Gray/jbox.css" type="text/css"></link>
<script language="javascript" type="text/javascript"
	src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript">
		function edit(id){
			$.jBox("iframe:updateIndexRollImgInit.do?commonId="+id, {
			    title: "编辑",
			   	top:"2%",
			    width: 610,
			    height:377,
			    buttons: { '关闭': true }
			});
		}
		//弹出窗口关闭
		function closeMthod(){
			window.jBox.close();
			window.location.reload();
		}
		function initCallBack(data){
	 		$("#dataInfo").html(data);
		}
		function showImage(id){
		$("#img_show").remove();
		 var src = $("#hid_"+id).val();
		 var html = "<img src='"+src+"' onclick='hiddenImage()' id='img_show' style='width: 600px;height: 377px;'/> ";
		 $(".content").append(html);
		 $.jBox('id:content', { title: '图片预览',width: '600px'});
		 $("#img_show").remove();
			//$(".content").append(html).css({'display':'block'});
		}
</script>
	<style>
.content{display:none;position:fixed;top:50%;margin-top:-250px;
background:#fff;z-index:3;left:50%;margin-left:-200px; border: 1px red solid;  
font-size: 12px;color: #3e4959;width: auto;height: auto;}
</style>
<div>
<s:hidden name="paramMap.messageInfo" id="info"/>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th scope="col">
					选中
				</th>
				<th scope="col">
					序号
				</th>
				<th scope="col">
					排序
				</th>
				<th scope="col">
					图片
				</th>
				
				<th scope="col">
					操作	
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="8">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<input id="gvNews_ctl02_cbID" class="helpId" type="checkbox"
								value="${bean.serialCount }" name="cb_ids" />
						</td>
						<td align="center" style="width: 100px;">
							<s:property value="#s.count+#counts" />
						</td>
						<td>
							${bean.ordershort}
						</td>
						<td>
							<a href="../${bean.companyImg}" target="_bank" title="查看原图"><img
									src="../${bean.companyImg}" width="100px" height="100px">
									<input type="hidden" value="../${bean.companyImg}" id="hid_${bean.serialCount}" />
							</a>
							
						</td>
						<td>
							<a href="javascript:edit(${bean.serialCount})"> 编辑 </a> &nbsp;&nbsp;
							<a href="javascript:del(${bean.serialCount})"> 删除 </a>&nbsp;&nbsp;
							<a href="javascript:showImage(${bean.serialCount})"> 查看 </a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>
<div class ="content" id="content"></div>
<table style="border-collapse: collapse; border-color: #cccccc"
	cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
	<tbody>
		<tr>
			<td class="blue12" style="padding-left: 8px" align="left">
				<input id="chkAll" onclick="checkAll(this); " type="checkbox"
					value="checkbox" name="chkAll" />
				全选 &nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btnDel" onclick="dels();" type="button" value="删除选中记录"
					name="btnDel" />
			</td>
		</tr>
	</tbody>
</table>
<script>

</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
