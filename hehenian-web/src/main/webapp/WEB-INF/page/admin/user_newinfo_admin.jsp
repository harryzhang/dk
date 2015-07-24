<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../common/date/calendar.css"/>
		<script type="text/javascript" src="../common/date/calendar.js"></script> 
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>


<script type="text/javascript">

$(function(){

   		$("#province").change(function(){
			var provinceId = $("#province").val();
			citySelectInit(provinceId, 2);
			//$("#area").html("<option value='-1'>--请选择--</option>");
		});

});
	function citySelectInit(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#city").html(_array.join(""));
		});
	}


</script>
<div ><span class="f66">总计 ${distributemap.cou } 个新用户待分配</span></div>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
				<th style="width: 35px;" scope="col">
						选中
					</th>
					<th style="width: 100px;" scope="col">
						用户名
					</th>
					<th style="width: 100px;" scope="col">
						信用积分
					</th>
					<!--  
					<th style="width: 120px;" scope="col">
						上传资料时间
					</th>
					-->
					<th style="width: 100px;" scope="col">
						待审核资料
					</th>
					<th style="width: 150px;" scope="col">
						分配
					</th>
					<th style="width: 150px;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="7">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				
				<s:iterator value="pageBean.page" var="bean" status="sta">
				<!--<s:if test="#bean.servicePersonId==null">-->
					<tr class="gvItem">
					<td>
								<input id="gvNews_ctl02_cbID" class="helpId" type="checkbox"
									value="${id};${sta.count}" name="cb_ids" /><input id="s_count" type="hidden" value="${sta.count}" />
							</td>
						<td>
							${username}
						</td>
											<td align="center">
						${creditrating}
						</td>
							<!-- 
						<td>
					
						<s:if test="#bean.passtime ==null">时间未知</s:if>
						<s:else>
						${passtime}
						</s:else>
						
						</td>
						 -->
						<td>
						${nocheck}
						</td>
						<td>
						<s:if test="#bean.tausername==null">
						
						
						<select id="gg_${sta.count}" >
				    <option value="">-请选择-</option>
				  <s:iterator value="#request.map" var="bean">
				    <option value="${bean.id }">${bean.userName}</option>
				  </s:iterator>
				</select>
						</s:if>	
						<s:else>
						${tausername }
						</s:else>
						</td>
						<td>
						<s:if test="#bean.tausername==null">
						<input type="button" value="确认审核人员" onclick="addService(this); "   id="${sta.count}" userId = "${id}" >
							</s:if>	
								<s:else>
								分配完成
								</s:else>
					</td>
					</tr>
					<!--</s:if>-->
				</s:iterator>
				</s:else>
			</tbody>
		</table>
		<table style="border-collapse: collapse; border-color: #cccccc"
		cellspacing="1" cellpadding="3" width="100%" align="center" border="1">
		<tbody>
			<tr>
				<td class="blue12" style="padding-left: 8px" align="left">
					<input id="chkAll" onclick="checkAll(this); " type="checkbox" value="checkbox" name="chkAll" />
					全选 &nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnTrans" onclick="addSelectedService();" type="button"
						value="批量确认审核人员" name="btnDel" />
				</td>
			</tr>
		</tbody>
	</table>
	
		<!-- <input type="button" value="确认审核人员" id ="bt_check" class="bcbtn"> -->
				<script type="text/javascript">
		  function  addService(data){
		     var param = {};
		     var  t = $(data).attr("id");
		     var tt = $('#gg_'+t).val();
		    param["paramMap.selectid"]= tt;
		    if(tt==""){
		       alert("请选择跟踪人员");
		       return false;
		    }
		    param["paramMap.userId"]= $(data).attr("userId");
		   $.post("updataServiceMan.do",param,function(data){
		        if(data=1){alert("确认成功")
		        window.location.reload();
		      }else{alert("确认失败")}
		  })
		  }
		  
		  function addSelectedService(){
		    var stIdArray = [];
		    var countArray = [];
	 			$("input[name='cb_ids']:checked").each(function(){
	 			   var selectVal = $(this).val().split(";");
	 				stIdArray.push(selectVal[0]);
	 				var t = selectVal[1];
	 				var tt = $('#gg_'+t).val();
	 				countArray.push(tt);
	 			});
	 			if(stIdArray.length == 0){
	 				alert("请选择要确认审核员的记录");
	 				return ;
	 			}
	 			if(!window.confirm("确定要批量修改吗?")){
		 			return;
		 		}
	 			var adminId = '${sessionScope.admin.id }';
	 			var ids = stIdArray.join(",");
	 			var admins = countArray.join(",");
	 			param["paramMap.ids"] = ids;
	 			param["paramMap.admins"] = admins;
	 			$.post("updateUserServiceMans.do",param,function(data){
	 			   if(data == 1){
	 			      alert("请确定完整审核人员");
	 			      return;
	 			   }else if(data == 2){
	 			     alert("批量确认操作失败，请重新操作");
	 			      return;
	 			   }
	 			   alert("批量确认成功");
	 			   window.location.reload();
	 			});
		  }
		</script>
		
	</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>