<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class="box" style="display:none;" id="writeMail">
        <div class="boxmain2">
        <div class="biaoge2">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11%">发件人：</td>
    <td width="89%"><strong><s:property value="#request.userName" default="---" ></s:property></strong></td>
  </tr>
  <tr>
    <td>收件人：</td>
    <td><input type="text" class="inp140" id="receiver" />
    <span style="color: red; float: none;" id="s_receiver" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td>标题：</td>
    <td><input type="text" class="inp280" id="title_s" maxlength="200"/>
    <span style="color: red; float: none;" id="s_title" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td valign="top">内容：</td>
    <td><textarea class="txt420" id="content"></textarea>
    <span style="color: red; float: none;" id="s_content" class="formtips"></span>
    </td>
  </tr>
  <tr>
    <td>验证码：</td>
    <td><input type="text" class="inp100x" id="code"/>
    <img src="admin/imageCode.do?pageId=userlogin" title="点击更换验证码"
		style="cursor: pointer;" id="codeNum" width="46" height="18"
		onclick="javascript:switchCode()" />
		<span style="color: red; float: none;" id="s_code" class="formtips"></span>
      </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td style="padding-top:20px;"><a href="javascript:void(0);" class="bcbtn" onclick="addMail()">提交发送</a></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="txt">* 温馨提示：如果要送管理员发送信息，请输入收件人admin</td>
  </tr>
        </table>

        </div>
    </div>
</div>
