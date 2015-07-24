<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script>
	function CostMangager(){
	    var param = {};
		param["costMode"] = '${maps.costMode}';
		param["id"] = '${maps.id}';
		param["costFee"] = $("#tb_number").val();
		$.post("updatePlatformCost.do",param,function(data){
		if(data==1){
		alert("修改失败");
		}
		else if(data==2){
			alert("修改成功");
				window.parent.closeMthod();
				}else if(data==3){
		              alert("输入金额比例必须是0%到100%的范围内");
				}else if(data==4){
				      alert("输入金额不能小于0");
			}
		});
	}
		</script>
	<div>
	
	
		<table width="100%" border="0" cellspacing="1" cellpadding="3">
		     <tbody>
		     	<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								<span>${maps.costName }:</span>
								</td>
								<td align="left" class="f66">
								  <s:if test="#request.CostMap.type==15">
								          <s:if test="#request.maps.costMode==1">
				 	<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
				 	</tr>
				  </s:if>
			     	<s:else>
				   <s:iterator value="#request.Costdetallist" var="bean">
				    	<input type="text" class="in_text_250" name="inputtext" value="${bean.ratio }" id="${bean.id }" stype="${bean.type }">
				    	<s:if test="#bean.type==1">（100万以下奖励比率）</s:if>
				    	<s:if test="#bean.type==2">（100-200万奖励比率）</s:if>
				    	<s:if test="#bean.type==3">（200万以上奖励比率）</s:if>
				    	<br><br>
			     	</s:iterator>
				   </s:else>
				 				       
								       
								  <td>
								  </s:if>
								     <s:else>
									<input type="text" class="in_text_250" value="${maps.costFee }" id="tb_number" style="height:20px;line-height: 20px;">
										<s:if test="#request.maps.costMode==2">元</s:if>
										<s:else>%</s:else>
									<span class="require-field">*</span>
								 </s:else>
								</td>
							</tr>	
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									&nbsp;&nbsp;
								</td>
								<td align="left" class="f66">
									<s:if test="#request.CostMap.type==4"><span style="color: red;">注：普通用户推荐好友奖励（每推荐一个好友并加入vip得到此奖励）</span></s:if>
									
								</td>
							</tr>	
							
							<tr>
								<td height="36" align="right" class="blue12">
									
									&nbsp;
								</td>
								<td>
									
								<input type="button" onclick="CostMangager()"
								style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"/>
								&nbsp; &nbsp;
								<span class="require-field"></span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
		     
		     
			</tbody>
		</table>
	</div>
