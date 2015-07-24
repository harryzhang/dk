<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<head>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
*{font-size: 12px;}
 .input_b{ width:95px; height:42px; background:url(images/sprit.png) -0px -733px no-repeat; border:none; color:#fff; cursor:pointer; margin-left:15px;}
 .input_b:hover{ background:url(images/sprit.png) no-repeat scroll -0px -774px rgba(0, 0, 0, 0);}
 .input_a{ width:95px; height:42px; background:url(images/sprit.png) -0px -692px no-repeat; border:none; color:#fff; cursor:pointer; margin-left:15px;}
 .input_a:hover{ background:url(images/sprit.png) no-repeat scroll -0px -774px rgba(0, 0, 0, 0);}
.fullbg {background: url(images/Catch.gif) center center #969d9f no-repeat;
	display: none;z-index: 3;position: fixed;left: 0px;top: 0px;width: 100%;
	height: 100%;filter: Alpha(Opacity = 30);
	/* IE */
	-moz-opacity: 0.4;
	/* Moz + FF */
	opacity: 0.4;}
</style>
<script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
	function showloading() {
		jQuery("body").append("<div class='fullbg'></div>");
		jQuery(".fullbg").css("display", "block");
	}

	// 隐藏层
	function hideloading() {
		jQuery(".fullbg").css("display", "none");
	}
	$(function() {
		$('.tabmain').find('li').click(function() {
			$('.tabmain').find('li').removeClass('on');
			$(this).addClass('on');
			$('.lcmain_l').children('div').hide();
			$('.lcmain_l').children('div').eq($(this).index()).show();
		})
	})
</script>
<script>
	$(function() {
		$('.til01 li').click(function() {
			$('.til01 li').removeClass('on');
			$(this).addClass('on');
			$('.rmainbox').children('div').hide();
			$('.rmainbox').children('div').eq($(this).index()).show();
		})
		$("#gggd").click(function() {
			layer.close(i);
		});
	})
	function dd(data) {
		window.parent.showbigpictur($("#ddss" + data + " img").attr("src"))//调用父类的方法
	}
</script>
<script>
	$(function() {
		$('#scbtn01').click(function() {
			$('.tcbox').show();
		})
		$('#gbtck').click(function() {
			$('.tcbox').hide();
		})
	})
</script>
</head>
<body style="margin-bottom: -10px;padding: 0px;">
	<div class="nymain" style="width: 540px;margin:0px;text-align: center;margin: 0 auto;">
		<div class="bigbox" style="border:none;">
			<div class="sqdk" style="background:none;height:auto;padding: 0px;">
				<div class="r-main" style="border:none; float:none; width:auto;">
					<div class="rmainbox" style="padding-left:0px; padding-right:0px; padding:0px;">
						<div class="tcbox" style="display:-none; position:static; margin:0px auto; padding:0px;">
							<div class="tcmain" style="margin-top: -5px;">
								<h3 style="margin-left: -30px;font-weight: bold;margin-bottom: -5px;color:black;">上传认证图片：</h3>
								<p style="color:black;padding-bottom: 5px;">
									<strong style="color:black;">认证说明：</strong><br />1、这些资料必须属于您本人 <br /> 2、请确认您上传的资料是清晰的、未经修改的照片，每张照片最大限制为1M
								</p>
								<h3 style="margin-top: -5px;">已上传资料：</h3>
								<div class="ysctab">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" id="sssd">
										<tr id="fg">
											<th align="center">选中</th>
											<th align="center">序号</th>
											<th align="center">名称</th>
											<th align="center">图片</th>
											<th align="center">状态</th>
											<th align="center">操作</th>
										</tr>
										<s:if test="#request.userPicDate==null || request.userPicDate.size == 0 ">
											<tr align="center" class="gvItem">
												<td colspan="6">暂无数据</td>
											</tr>
										</s:if>
										<s:else>
											<s:iterator value="#request.userPicDate" var="bean" status="st">
												<tr id='s_${st.count }'>
													<!--   <input type="hidden" name="hii" value="${imagePath }" id="h_${st.count }" chd=""/> -->
													<td align="center"><input type="checkbox" name="items" id="${st.count }" value="${id}" <s:if test="#bean.visiable==1">checked="checked"</s:if> />
													</td>
													<td align="center">${st.count }</td>
													<td align="center">${tmyname }${st.count }</td>
													<td align="center" class="pic">
														<!-- 点击放大 <a href="javascript:dd(${st.count})" id="dds${st.count }"  title="查看图片"> <img  src="${imagePath }" width="62" height="62"/></a> --> <a href="${imagePath }"
														target="_blank" title="查看图片"><img src="${imagePath }" width="62" height="62" /> </a>
													</td>
													<td align="center"><s:if test="#bean.auditStatus==1">审核中</s:if> <s:if test="#bean.auditStatus==2">失败</s:if> <s:if test="#bean.auditStatus==3">通过</s:if>
													</td>
													<td align="center">-</td>
												</tr>
											</s:iterator>
										</s:else>
										<tr id="ggggs">
											<td colspan="5" style="border:none; padding-top:10px;"><a style="padding-left:6.5%"> <input type="checkbox" name="checkbox4" id="select" /> 全选 </a> <a style="color: red">
													提示：选中资料可见</a> <!--    <a style="cursor: pointer;" class="yzmbtn" id="sub">确认选中资料可见</a> -->
											</td>
										</tr>
									</table>

								</div>
								<div class="xzzl" style="margin-top: 0px;margin-bottom: -6px;">
									<form action="addpastPicturdate.do" id="myform" method="post">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr id="mytable">
												<td align="right" width="48%">
												<input type="button" class="input_b" value="选择图片" id="shanc" style="margin-right: 20px;" onclick="updateFiledatefirest(this)">
												</td>
												<td align="left">
													<input type="hidden" value="${tmid }" name="paramMap.tm"> 
													<input type="hidden" value="${typmap.materAuthTypeId}" name="paramMap.materAuthTypeId">
													<input type="button" value="提交审核" class="input_a" id="bt_sub" style="margin-left: 20px;"> <!-- <input type="text" class="inp188" name='rmlink' id="img1"  disabled="disabled"/>  -->
												</td>
											</tr>
											<tr style="width: 100%" >
												<td style="color: red;line-height: 20px;padding-top: 5px;" colspan="2"><img src="images/warning.png" width="20px;" height="20px;" style="margin-right: 5px;">合和年在线是一个注重诚信的网络平台。如果我们发现您上传的资料系伪造或有人工修改痕迹，合和年信贷会将你加入系统黑名单，永久取消您在合和年信贷的借款资格。</td>
											</tr>
											<!--  <tr id="mytable"> <td>&nbsp;</td> <td><input type="text" class="inp188" />  <a href="#" class="scbtn">浏览</a> <a href="#" class="shanchu">删除</a></td></tr>-->
										</table>
									</form>
									<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
										 <td><a  href='javascript:void(0)' class="shanchu" id="addInput">添加一个附件</a></table></td> -->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
<script>
	//放大图片
	function ddimg(data) {
		/*
		 var i = $.layer({
		 type : 1,
		 //   move : ['#ddssa'+data , true],
		 title : ['图片查看',false],
		 fix : false,
		 offset:['50%' , '50%'],
		 area : ['400px','400px'],
		 page : {dom : '#ddssa'+data}
		 });
		 */
		window.parent.showbigpictur($("#ddssa" + data + " img").attr("src"))//调用父类的方法
	}
	function chan(data) {
		var va = $(data).attr("id");
		var gee = $("#hh_" + va).attr("value");

		if (data.checked) {
			$(data).attr("value", gee + ".v");
		} else {
			$(data).attr("value", gee);
		}
	}
	//上传资料 - 提交图片资料审核
	$(function() {
		var parama = {};
		var typed = '${typmap.materAuthTypeId}';
		$("#bt_sub").click(function() {
			var visable1 = 0;
			var visable3 = 0;
			//获取本来在数据库的图片获取他们的id
			$("input[name=items]").each(function() {
				visable3++;
			});

			$("input[name=items]").each(function() {
				if ($(this).attr("checked")) {
					visable1++;
					parama["paramMap.id" + visable1] = $(this).val();
				}
			});
			//取上传到tomcat的图片的地址和是否选择
			var visable2 = 0;
			$("input[name=itemss]").each(function() {
				//获取图片选矿的值
				visable2++;
				parama["paramMap.ids" + visable2] = $(this).val();
			});
			//控制上传个数

			if (typed == '1') {//身份证
				if ((visable2 + visable3) > 5) {
					alert("身份证认证图片只能上传5张,如需再上传,请联系客服!谢谢");
					return false;
				}
			} else if (typed == '2') {//工作
				if ((visable2 + visable3) > 10) {
					alert("工作认证图片只能上传10张,如需再上传,请联系客服!谢谢");
					return false;
				}
			} else if (typed == '3') {//居住认证
				if ((visable2 + visable3) > 5) {
					alert("居住认证图片只能上传5张,如需再上传,请联系客服!谢谢");
					return false;
				}
			} else if (typed == '4') {//收入认证
				if ((visable2 + visable3) > 30) {
					alert("收入认证图片只能上传30张,如需再上传,请联系客服!谢谢");
					return false;
				}
			} else if (typed == '5') {//信用报告
				if ((visable2 + visable3) > 10) {
					alert("信用报告认证图片只能上传10张,如需再上传,请联系客服!谢谢");
					return false;
				}
			} else {
				if ((visable2 + visable3) > 10) {
					alert("可选认证图片只能上传10张,如需再上传,请联系客服!谢谢");
					return false;
				}
			}

			//将数据传到后台处理
			parama["paramMap.tmid"] = '${tmid}';
			parama["paramMap.materAuthTypeId"] = '${typmap.materAuthTypeId}';
			parama["paramMap.listlen"] = '${len}';
			parama["paramMap.len"] = visable2;//将要上传的图片个数
			$.post("addpastPicturdate.do", parama, function(data) {
				if (data == 123) {
					alert('申请认证成功');
					window.parent.ffff()//关闭弹出窗口
				}
			});
			return;
		});
	});
</script>

<script>
	//保存图片
	$(function() {
		var paramimg = {};
		var gimg = 0;
		$("#bt_sub_1").click(function() {
			//$("#myform").submit();
			$("input[name=rmlink]").each(function() {
				gimg++;
				paramimg["paramMap.img" + gimg] = $(this).val();
			});

			paramimg["paramMap.tmid"] = '${tmid }';
			paramimg["paramMap.materAuthTypeId"] = '${typmap.materAuthTypeId}';
			paramimg["paramMap.len"] = gimg;
			paramimg["paramMap.listlen"] = '${len}';
			$.post("addpastPicturdate.do", paramimg, function(data) {
				if (data == 1) {
					alert("身份证只能上传5张图片");
				} else if (data == 2) {
					alert("工作认证只能上传10张图片");
				} else if (data == 3) {
					alert("居住地认证只能上传5张图片");
				} else if (data == 5) {
					alert("收入认证只能上传30张图片");
				} else if (data == 4) {
					alert("信用报告只能上传10张图片");
				} else if (data == 17) {
					alert("上传失败");
				} else if (data == 18) {
					alert("上传失败");
				} else if (data == 321) {
					alert("上传失败");
				} else if (data == 123) {
					window.parent.ffff()//关闭弹出窗口
				} else if (data == 6) {
					alert("可选认证只能上传10张图片");
				}
			})
			gimg = 0;

		});

	});
</script>
<script>
	$(function() {
		$("#dd").click(function() {
			var ttt = $("#tat").val();
			window.parent.ffff(ttt, '${id}')
		});
	});
	//删除图片
	function deltemp(data) {
		var dt = $(data).attr("id")
		$("#s_" + dt).remove();
	}
</script>
<script>
	//上传图片
	var flag = false;
	var gv1 = 0;
	var gv2 = 0;
	var allgv = 0;

	function updateFiledatefirest(data) {
		showloading();
		var dir = getDirNum();
		var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
				+ "','fileLimitSize':1,'title':'上传图片','cfn':'uploadCall2','cp':'img'}";
		json = encodeURIComponent(json);
		window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");
		//var headImgPath = $("#img").attr("value");
		//if(headImgPath!=""){
		//}
		hideloading();
	}

	function uploadCall2(basepath, fileName, cp) {
		//检测上传个数 如果达到了上传上限 那么给予提示
		$("input[name=items]").each(function() {
			gv1++;
		});
		$("input[name=itemss]").each(function() {
			gv2++;
		});

		allgv = gv1 + gv2;

		if (cp == "img") {
			var path = "upload/" + basepath + "/" + fileName;
			//$("#img1").attr("value",path);
			//处理文件上传
			var Idd = $('#sssd tr:last').prev().attr("id");
			var te = parseInt($("#" + Idd + " td:eq(1)").text());
			if (isNaN(te)) {
				te = 0;
			}
			var charStr = "";
			if ('${typmap.materAuthTypeId}' == '1') {
				charStr = "身份证" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '2') {
				charStr = "工作认证" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '3') {
				charStr = "居住地认证" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '4') {
				charStr = "信用报告" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '5') {
				charStr = "收入认证" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '6') {
				charStr = "房产证" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '7') {
				charStr = "房屋租赁合同" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '8') {
				charStr = "水电单据" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '9') {
				charStr = "工作证明" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '10') {
				charStr = "银行流水" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '11') {
				charStr = "信用卡账单" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '12') {
				charStr = "车产证" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '13') {
				charStr = "社保" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '14') {
				charStr = "保单权益" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '15') {
				charStr = "股票或基金资产" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '16') {
				charStr = "税单" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '17') {
				charStr = "营业执照" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '18') {
				charStr = "租赁合同" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '19') {
				charStr = "其他资产证明" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '20') {
				charStr = "支付宝流水" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '21') {
				charStr = "财付通流水" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '22') {
				charStr = "网店交易记录" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '23') {
				charStr = "其它网络消费流水" + (te + 1)
			}
			if ('${typmap.materAuthTypeId}' == '24') {
				charStr = "征信报告" + (te + 1)
			}
			//$("#"+Idd).after('<tr  id="s_'+(allgv+1)+'"><td align="center"><input type="checkbox" name="itemss" id="'+(allgv+1)+'" value="'+path+'"  onchange="chan(this)"   /><input type="hidden" value="'+path+'" id="hh_'+(allgv+1)+'"/></td><td align="center"></td><td align="center"></td> <td align="center" class="pic" ><img src="'+path+'" width="62" height="62"/></td><td align="center"> 待提交 </td><td align="center"><a  href="javascript:void(0)"   onclick="deltemp(this)" id ="'+(allgv+1)+'">删除</a></td>');	
			$("#" + Idd)
					.after(
							'<tr  id="s_'
									+ (allgv + 1)
									+ '"><td align="center"><input type="checkbox" name="itemss" id="'
									+ (allgv + 1)
									+ '" value="'
									+ path
									+ '"  onchange="chan(this)"   /><input type="hidden" value="'
									+ path
									+ '" id="hh_'
									+ (allgv + 1)
									+ '"/></td><td align="center">'
									+ (te + 1)
									+ '</td><td align="center">'
									+ charStr
									+ '</td> <td align="center" class="pic" ><a href="'+path+'" target="_blank" title="查看图片"><img    src="'+path+'" width="62" height="62"/></a>  <a  id="ddssa'
									+ (allgv + 1)
									+ '" style="display: none;"> <img src="'+path+'" width="400" height="400"/></a></td><td align="center"> 待提交 </td><td align="center"><a  href="javascript:void(0)"   onclick="deltemp(this)" id ="'
									+ (allgv + 1) + '">删除</a></td>');

			te = 0;
			hideloading();

		}
	}
</script>
<script>
	var gggg;
	//上传函数
	function updateFiledate(data) {
		gggg = "img" + $(data).attr("id");
		var dir = getDirNum();
		var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/" + dir
				+ "','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
		json = encodeURIComponent(json);
		window.showModalDialog("uploadFileAction.do?obj=" + json, window, "dialogWidth=500px;dialogHeight=400px");

	}

	function uploadCall(basepath, fileName, cp) {
		if (cp == "img") {
			var path = "upload/" + basepath + "/" + fileName;
			$("#" + gggg).val(path);
			return;
		}
	}

	function getDirNum() {
		var date = new Date();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		if (m < 10) {
			m = "0" + m;
		}
		if (d < 10) {
			d = "0" + d;
		}
		var dirName = date.getFullYear() + "" + m + "" + d;
		return dirName;
	}
</script>

<script>
	$(function() {
		$("#select").click(function() {
			if ($(this).attr("checked")) {
				$("input[name=items]").each(function() {
					$(this).attr("checked", true);
				});
				$("input[name=itemss]").each(function() {
					$(this).attr("checked", true);
					var ip = $(this).attr("id");
					var p = $("#hh_" + ip).attr("value");
					$(this).attr("value", p + ".v");
				});

			} else {
				$("input[name=items]").each(function() {
					$(this).attr("checked", false);
				});
				$("input[name=itemss]").each(function() {
					$(this).attr("checked", false);
					var ip = $(this).attr("id");
					var p = $("#hh_" + ip).attr("value");
					$(this).attr("value", p);
				});
			}
		});
	});
</script>



<script>
	var type;
	var gouble = 2;
	var gd = 1;
	$(function() {

		//处理类型身份证5张 等等其他的
		$("#addInput")
				.click(
						function() {
							type = $
							{
								typmap.materAuthTypeId
							}
							;

							//获取input框内的值的个数
							var countinput = 0;
							$("input[name=rmlink]").each(function() {
								countinput++
							});

							if (type == 1) {
								if (countinput >= 5) {
									alert("身份证只能上传5张图片");
								}
								if (countinput < 5) {
									if (gd == 1) {
										$("#mytable")
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
										gd++;
									} else {
										$("#tr" + (gouble - 1))
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
									}

								}
							}

							if (type == 2) {
								if (countinput >= 10) {
									alert("工作认证只能上传10张图片");
								}
								if (countinput < 10) {
									if (gd == 1) {
										$("#mytable")
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
										gd++;
									} else {
										$("#tr" + (gouble - 1))
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
									}

								}
							}

							if (type == 3) {
								if (countinput >= 5) {
									alert("居住地认证只能上传5张图片");
								}
								if (countinput < 5) {
									if (gd == 1) {
										$("#mytable")
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
										gd++;
									} else {
										$("#tr" + (gouble - 1))
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
									}

								}
							}
							if (type == 5) {
								if (countinput >= 30) {
									alert("收入认证只能上传30张图片");
								}
								if (countinput < 30) {
									if (gd == 1) {
										$("#mytable")
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
										gd++;
									} else {
										$("#tr" + (gouble - 1))
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
									}

								}
							}

							if (type == 4) {
								if (countinput >= 10) {
									alert("信用报告只能上传10张图片");
								}
								if (countinput < 10) {

									if (gd == 1) {
										$("#mytable")
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
										gd++;
									} else {
										$("#tr" + (gouble - 1))
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
									}
								}
							}

							if (type > 5) {
								if (countinput >= 10) {
									alert("可选认证只能上传10张图片");
								}
								if (countinput < 10) {
									if (gd == 1) {
										$("#mytable")
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
										gd++;
									} else {
										$("#tr" + (gouble - 1))
												.after(
														"<tr  id='tr"+gouble+"'><td>&nbsp;</td><td><input type='text' class='inp188' id='img"+gouble+"' name='rmlink' disabled='disabled'/>  <a href='javascript:void(0)' class='scbtn' id='"
																+ gouble
																+ "' onclick='updateFiledate(this)'>浏览</a>  <a  href='javascript:void(0)' class='shanchu' onclick='del(this)' id ='"
																+ gouble + "'>删除</a></td></tr>");
										gouble++;
									}

								}
							}
						});

	});
</script>
<script>
	//得到选中的值，ajax操作使用  
	$(function() {
		var gv = 0;
		var param = {};
		$("#sub").click(function() {
			var text = "";
			$("input[name=items]").each(function() {
				if ($(this).attr("checked")) {
					// alert($(this).val()); 
					gv++;
					param["paramMap.id" + gv] = $(this).val();
				}
			});
			param["paramMap.len"] = gv;
			if (gv != 0) {
				param["paramMap.tmidStr"] = $
				{
					tmidStr
				}
				;
				$.post("updatevisiable.do", param, function(data) {
					alert("操作成功");
					window.location.reload();//刷新页面
				});
			}
			gv = 0;
		});

	});
</script>
<script>
	function del(data) {
		var dt = $(data).attr("id")
		$("#tr" + dt).remove();
		gouble = $(data).attr("id");
	}
</script>
<script>
	function sure(data) {
		var llen = '${len}';
		$('#s_' + llen)
				.after(
						"<tr  id='s_"
								+ (llen + 1)
								+ "> <td align='center'>--</td><td align='center'>12</td><td align='center'>5454</td><td align='center' class='pic'>img</td><td align='center'>4545<td><tr>");
	}
</script>






