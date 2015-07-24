 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags"  prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <div class="biaoge2">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11%">发件人：</td>
    <td width="89%"><strong>${sender}</strong></td>
  </tr>
  <tr>
    <td>收件人：</td>
    <td>${receiver}</td>
  </tr>
  <tr>
    <td>标题：</td>
    <td>${title}</td>
  </tr>
  <tr>
    <td>日期：</td>
    <td>${date }</td>
  </tr>
  <tr>
    <td valign="top">内容：</td>
    <td>${content}</td>
  </tr>
  <tr>
  <td colspan="2" align="left">&nbsp;</td>
  </tr>
  <tr>
  <td colspan="2" align="left">
  <a href="javascript:void(0);" class="scbtn" onclick="returnToPage_(${curPage},${mType });">返回</a> 
  </td>
  </tr>
        </table>

        </div>