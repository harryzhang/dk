<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>管理后台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<link href="${basePath}/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery-easyui/jquery.easyui.pack.js"></script>
<script type="text/javascript" src="${basePath}/js/menus/index.js"></script>
<script type="text/javascript">
     var _menus={};
     $.ajax({
    	 url:'/menu/getMenus.html',
    	 type:'GET',
    	 async:false,
    	 success:function(data){
    		 var obj=eval(data);
    		 _menus.menus=[];
    		 for(var i=0,n=obj.length;i<n;i++){
    			 var module=obj[i];
    			 var menu={};
    			 menu.id=module.id;
    			 menu.icon="icon-sys";
    			 menu.menuname=module.name;
    			 var resources=module.resources;
    			 var menuItems=[];
    			 for(var j=0,m=resources.length;j<m;j++){
    				 var resource=resources[j];
    				 var menuItem={};
    				 menuItem.id=resource.id;
    				 menuItem.menuname=resource.name;
    				 menuItem.icon=resource.icon||"icon-nav";
    				 menuItem.url=resource.resourceStr;
    				 menuItems.push(menuItem);
    			 }
    			 menu.menus=menuItems;
    			 _menus.menus.push(menu);
    		 }
    	 }
     });
	 
        //设置登录窗口
        function openPwd() {
            $('#w').window('open');
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }

        

        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');
            var $oldPass=$("#oldPass");
            if(!$oldPass.val()){
            	msgShow('系统提示','请输入旧密码','warning');
            	return false;
            }
            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.ajax({
            	url:'/auth/resetLoginUserPwd.html',
            	type:'post',
            	data:{
            		oldPassword:$.trim($oldPass.val()),
            		newPassword:$.trim($newpass.val()),
            		newPasswordAgain:$.trim($rePass.val())
            		},
            	success:function(msg) {
            		if(msg.ret==1){
	                    msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + $.trim($newpass.val()), 'info');
	                    $newpass.val('');
	                    $rePass.val('');
	                    close();
            		}else if(msg.ret==-2){
            			msgShow('系统提示','密码不正确！','info')
            		}
                }
            });
            
        }

        $(function() {

            //openPwd();
            //
            closePwd();
            $("#editpass").click(function(){
            	openPwd();
            });

            $('#btnEp').click(function() {
                serverLogin();
            })

			$('#btnCancel').click(function(){closePwd();})

           

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = '/auth/logout.html';
                    }
                });

            })
			
			
			
        });
		
		

    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="${basePath}/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(${basePath}/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 25px;color: #fff;">
        <span style="float:right; padding-right:20px;" class="head">欢迎 ${user.nickName } <a href="#" id="editpass">修改密码</a> <a id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px;"><img src="${basePath}/images/blocks.gif" width="20" height="20" align="absmiddle" /> 我的后台</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">合和年管理后台</div>
    </div>
    <div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
<div class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
				
			</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden;">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:20px;overflow:hidden;background-image: url('../../images/welcome.png');background-repeat: no-repeat;background-position: center;background-attachment: inherit;" id="home">
				
			</div>
		</div>
    </div>
    
    
    <!--修改密码窗口-->
	    <div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
	        maximizable="false" icon="icon-save"  style="width: 300px; height: 200px; padding: 5px;
	        background: #fafafa;">
	        <div class="easyui-layout" fit="true">
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                <table cellpadding=3>
	                	<tr>
	                	<td>旧密码：</td>
	                	<td><input id="oldPass" type="password" class="txt01"/></td>
	                	</tr>
	                    <tr>
	                        <td>新密码：</td>
	                        <td><input id="txtNewPass" type="Password" class="txt01" /></td>
	                    </tr>
	                    <tr>
	                        <td>确认密码：</td>
	                        <td><input id="txtRePass" type="Password" class="txt01" /></td>
	                    </tr>
	                </table>
	            </div>
	            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
	                <a id="btnEp" class="easyui-linkbutton"  href="javascript:void(0)" >
	                    确定</a> <a id="btnCancel" class="easyui-linkbutton" href="javascript:void(0)">取消</a>
	            </div>
	        </div>
	    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>


</body>
</html>