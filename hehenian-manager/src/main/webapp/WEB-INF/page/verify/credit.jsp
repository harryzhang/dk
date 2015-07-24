<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>诚信认证</title>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="/js/jquery-easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
	<style type="text/css">
		body {
		    font-family:helvetica,tahoma,verdana,sans-serif;
		    font-size:14px;
		}
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .uploadimg{ float: left;}
		.uploadimg li{ float: left; display: inline; width: 63px; height: 63px; border: 1px dashed #D1D1D1; margin-right: 5px; overflow: hidden;}
		.uploadimg li img{ width:100%; height:100%;}
		.reply {list-style:none;}
		.reply li {border: 1px dashed #D1D1D1; padding: 5px 5px;}
		.reply span {display:block;}
    </style>
</head>
<body>
<form id="ff" method="post">
<div class="tabelMod">
	<label>会员ID:</label>
    <input id="userId" type="text" />
	<label>上传时间:</label>
    <input id="createFrom" type="text"/> 至 <input id="createTo" type="text"/>
</div>

<div class="tabelMod">
	<label>认证类型:</label>
	<select class="easyui-combobox" id="type" style="width:150px;" editable="false">
		<option value="1">工作证明</option>
		<option value="2">收入证明</option>
		<option value="3">学历</option>
	</select>
	<label>审核状态:</label>
	<select class="easyui-combobox" id="status" style="width:150px;" editable="false">
	    <option value="-1">全部</option>
	    <option value="0" selected="selected">未审核</option>
		<option value="1">审核通过</option>
		<option value="2">审核未通过</option>
		<option value="3">未认证</option>
	</select>
</div>

 <div class="btnBox">
	<a href="#" onclick="doSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
 </div>
</form>
    <table id="dg" title="反馈" class="easyui-datagrid" style="width:1200px;height:550px;"
            striped="true"
            url="/verify/credit/select.html"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="userId" align="center" width="10">会员ID</th>
                <th field="age_name" align="center" width="10" formatter="ageNameFormate">年龄+姓名</th>
                <th field="createTime" align="center" width="15" formatter="timeFormate">上传时间</th>
                <th field="avatar" align="center" width="40" formatter="contentFormate">内容</th>
                <th field="status" align="center" width="10" formatter="statusFormate">审核状态</th>
                <th field="op" align="center" width="10" formatter="opFormate">操作</th>
                <th field="opHistory" align="center" width="10" formatter="opHistoryFormate">操作记录</th>
            </tr>
        </thead>
    </table>

    <div id="dlg" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" novalidate>
            <p><label><input name="reason" type="radio" value="图片与用户资料不符" checked="checked"/>图片与用户资料不符</label></p>
            <p><label><input name="reason" type="radio" value="图片不符合规范，请上传清晰，完整的图片" />图片不符合规范，请上传清晰，完整的图片</label></p>
            <p><label><input name="reason" type="radio" value="-1" />其他原因</label></p>
            <p><textarea name="otherReason" style="margin-left:20px;width:280px;height:75px;"></textarea></p>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="nopass()">确认</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
    <div id="hst" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
            closed="true" buttons="#hst-buttons">
        <div id="hstContent"></div>
    </div>
    <div id="hst-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#hst').dialog('close')">关闭</a>
    </div>
    <script>
	    $('#createFrom').datebox({
	    	required:true 
	    });
	    $('#createTo').datebox({
	    	required:true 
	    });
	    $(function(){
    	   var curr_time = new Date();
    	   var strDate = curr_time.getFullYear()+"-";
    	   strDate += curr_time.getMonth()+1+"-";
    	   strDate += curr_time.getDate();
    	   //$("#createFrom").datebox("setValue", strDate);
    	   //$("#createTo").datebox("setValue", strDate);
	    });
	</script>
    <script type="text/javascript">
        shStatus = {"0": "未审核", "1": "审核通过", "2": "审核未通过", "3": "审核未认证"};

    	$('#dg').datagrid({
    		 nowrap:false,
    		 onLoadError: function (){
    			 $.messager.show({
                     title: '出错了',
                     msg: '请联系技术'
                 });
     		 }
    	});
        var url;
        function doSearch(){
            $('#dg').datagrid('load',{
            	userId: $('#userId').val(),
                type: $('#type').combobox('getValue'),
                status: $('#status').combobox('getValue'),
                createFrom: $('#createFrom').datebox('getValue'),
                createTo: $('#createTo').datebox('getValue')
            });
        }
        function editReason(index){
        	$('#dg').datagrid('selectRow',index);
        	var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','原因描述');
                var selectType = $('#type').combobox('getValue');
                url = '/verify/credit/pass.html?userId=' + row.userId + "&ispass=2&type=" + selectType;
            }
        }
        function nopass(){
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(ret){
                    if (ret == 1){
                    	 $('#dlg').dialog('close');        // close the dialog
                         $('#dg').datagrid('reload');    // reload the user data
                    } else {
                    	$.messager.show({
                            title: '出错了',
                            msg: '您没有删除的权限，请联系客服经理'
                        });
                    }
                }
            });
        }
        function showHistory(userId, type) {
            var selectType = $('#type').combobox('getValue');
            $.get("/verify/credit/ophistory.html", { "userId": userId, "type": selectType},
              function(data){
                var html = "";
                $.each(data,function(index, msg){
                    var content = eval('(' + msg.actionId + ')');
                    var result = "";
                    if(content.ispass == 1) {
                        result += "审核通过";
                    } else if(content.ispass == 2) {
                        result += "审核不通过——" + content.reason;
                    }
                    html += "<p>" + new Date(msg.actionTime).toLocaleString() + "——" + content.kefu + "——" + result + "</p>";
                });
                $('#hstContent').html(html);
                $('#hst').dialog('open').dialog('setTitle','操作历史');
            });
        }
        function pass(userId){
            var selectType = $('#type').combobox('getValue');
            $.ajax({
            	url:"/verify/credit/pass.html",
            	type:"post",
            	data:{ "userId": userId, "ispass": 1, "type": selectType},
            	success:function(ret){
            		if(ret == 1) {
                        $('#dg').datagrid('reload');
            		}
            	},
            	error:function(jqXHR,textStatus,errorThrown){
        			if(jqXHR.status==403){
        				$.messager.alert('无权限操作','您没有删除的权限，请联系客服经理');
        			}
        		}
            });
        }
        function ageNameFormate(val,row,index) {
            var html = "<p>" + row.trueName + "</p>";
            html += "<p>" + row.age + "</p>";
            html += "<p>" + row.ageInfo + "</p>";
            if(!!row.constellation) {
                html += "<p>" + row.constellation + "</p>";
            }
            return html;
        }
        function timeFormate(val,row,index) {
            return new Date(val).toLocaleString();
        }
        function statusFormate(val,row,index) {
            var selectType = $('#type').combobox('getValue');
            if (selectType == 0) {
                return shStatus[row.idStatus];
            } else if (selectType == 1) {
                return shStatus[row.jStatus];
            } else if (selectType == 2) {
                return shStatus[row.sStatus];
            } else if (selectType == 3) {
                return shStatus[row.eStatus];
            } else if (selectType == 4) {
                return shStatus[row.cStatus];
            }
            return "";
        }
        function opFormate(val,row,index) {
        	var selectType = $('#type').combobox('getValue');
        	var vstatus = 0;
            if (selectType == 0) {
            	vstatus = row.idStatus;
            } else if (selectType == 1) {
            	vstatus = row.jStatus;
            } else if (selectType == 2) {
            	vstatus = row.sStatus;
            } else if (selectType == 3) {
            	vstatus = row.eStatus;
            } else if (selectType == 4) {
            	vstatus = row.cStatus;
            }
            var html = '';
            if(vstatus != 1) {
            	html += '<p><a href="javascript:void(0)" onclick="pass(' + row.userId + ', 1)">通过</a></p>';
            }
            if(vstatus != 2) {
            	html += '<p><a style="color:red;" href="javascript:void(0)" onclick="editReason(' + index + ')">不通过</a></p>';
            }
            return html;
        }
        function opHistoryFormate(val,row,index) {
            var selectType = $('#type').combobox('getValue');
            return '<p><a href="javascript:void(0)" onclick="showHistory(' + row.userId + ',' + selectType + ')">查看</a></p>';
        }
        function contentFormate(val,row,index) {
            var selectType = $('#type').combobox('getValue');
            var html = "";
            if(!!row.verifyText) {
                html += "<p>" + row.verifyText + "</p>";
            }
            // if (selectType == 0) {
            //     html = "<p>" + row.trueName + "</p><p>" + row.ageInfo + "</p>";
            // } else if (selectType == 1) {
            //     html = "<p>" + row.a + "</p>";
            // } else if (selectType == 2) {
            //     html = "<p>" + row.verifyText + "</p>";
            // } else if (selectType == 3) {
            //     html = "<p>" + row.a + "</p>";
            // } else if (selectType == 4) {
            //     html = "<p>" + row.a + "</p>";
            // }
            html += '<a href="' + row.lphoto + '" target="_blank"><img src="' + row.sphoto + '"/></a>';
            return html;
        }
    </script>
</body>
</html>