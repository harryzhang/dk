<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
    <div class="til" style="background-image: url(images/neiye2_32.jpg); background-repeat: repeat-x;">
    <ul><li class="on">留言板</li>
    </ul>
    <div class="til_bot">
    </div>
    </div>
    <div class="boxmain">
	  <div class="liuyan">
      <ul>
       <s:if test="pageBean.page!=null || pageBean.page.size>0">
       <s:iterator value="pageBean.page" var="message">
       <li>
        <div class="pic">
          <a href="userMeg.do?id=${message.userId}" target="_blank">
           <shove:img src="${message.personalHead}" defaulImg="images/default-img.jpg" width="62" height="62"></shove:img>
          </a>
          </div>
          <div class="lyinfo">
          <h4><span><s:date name="#message.msgTime" format="yyyy-MM-dd HH:mm:ss"/> </span><strong><s:property value="#message.username"/></strong> 说：</h4>
          <p><s:property value="#message.msgContent"/></p>
          </div>
      </li>
    </s:iterator>
  </s:if>
  <s:else>
      <li>
        <div class="plbox">暂无留言</div>
      </li>
  </s:else>
      </ul>
      </div>
      <div class="fenye">
    <div class="fenyemain">
    <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <div class="page" style=" padding-top:20px;">
                    <p>
                       <shove:page url="borrowmessage.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}">
							<s:param name="paramMap.id">${message.modeId}</s:param>
					   </shove:page>
                    </p>
                </div>    
    </s:if>
    </div>
      <s:hidden name="id" value="%{#request.id}"></s:hidden>
      <div id="msgadd" class="plbox" style="text-align:left;">
      <h4>咨询或评论：(字数在1-120之间)</h4>
      <textarea id="msgContent" class="txt918"></textarea>
      <p>验证码：
      <input type="text" class="inp100x" name="paramMap.code" id="code"/>
		 <img src="" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
       <a id="savetbn" href="javascript:void(0);" class="chaxun">提交</a></p>
      </div>
    </div>
</div>
<script type="text/javascript">
var flag = true;
$(function(){
     $('#savetbn').click(function(){
	     param['paramMap.id']=$('#id').val();
	     param['paramMap.code']=$('#code').val();
	     param['paramMap.msg']=$('#msgContent').val();
         if(flag){
           flag = false;
           $.shovePost('addBorrowMSG.do',param,function(data){
		     if(data.msg == 1){
		       initListInfo(param);
		     }else{
		       $('#msgadd').html(data);
		       flag = true;
		     }
           });
         }
         
     });
     switchCode();
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.do?pageId=msg&d='+timenow);
};	
</script>