<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript">

  
  function Costfriend(){
      var number=$("#tb_number").val();
      if((/^[1-4]{1,2}(\.\d{1,4})?$/.test(number))){
          $.post("updateCostManager.do","number="+number+"&typeId=2",function(data){
           if(data==1){
              alert("修改成功");
           }else if(data==2){
              alert("修改失败");
           }
           
          });
      }else{
          alert("请输入正确百分比数字,不能高于50%");
      }
    
  }
</script>
<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
                                       投资利息管理费: 
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_number" name="paramMap.number"
										cssClass="in_text_250" theme="simple"></s:textfield>%
									<span class="require-field">*<s:fielderror fieldName="paramMap['number']"></s:fielderror></span>
								</td>
							</tr>	
						
							
							<tr>
								<td height="36" align="right" class="blue12">
									
									&nbsp;
								</td>
								<td>
									
								<input type="button" onclick="Costfriend()"
								style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"/>
								&nbsp; &nbsp;
								<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>