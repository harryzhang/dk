<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/user.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head><body>
<%--<!-- 引用头部公共部分 -->--%>



<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <div style=" overflow:hidden">
      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
      <div class="u-right"><div class="user-right">
          <h2>基本资料</h2>
          
      <table width="100%" border="0">
        <tr height="5"></tr>
          <tr height="20">
                  <td align="center" colspan="2">
                      <span style="color: red;">身份信息直接影响交易操作，请务必如实填写</span>
                  </td>
          </tr>
        <tr height="45">
          <td width="380" height="60" align="right"><strong>*</strong>真实姓名：</td>
          <td><s:if test='#session.user.usrCustId>0'> ${map.realName }
              <input type="hidden" name="paramMap.realName" id="realName" value="${map.realName}" />
            </s:if>
            <s:else>
              <input type="text" class="inp188" name="paramMap.realName" id="realName" value="${map.realName}" />
              <span style="color: red; float: none;" id="u_realName" class="formtips"></span> </s:else></td>
        </tr>
        <tr>
          <td width="100" height="60" align="right"><strong>*</strong>身份证号：</td>
          <td>
          	<s:if test='#session.user.usrCustId>0'> <a id='idNo1'>${map.idNo}</a>
          	<img src="images/neiye2_44.jpg" alt="ok" style="margin-left: 20px;" />
            <input type="hidden" class="inp188" name="paramMap.idNo" id="idNo" value="${map.idNo}" />
            </s:if>
            <s:else>
            <input type="text" class="inp188" name="paramMap.idNo" id="idNo" value="${map.idNo}" />
            <span style="color: red; float: none;" id="u_idNo" class="formtips"></span> 
            </s:else>
          </td>
        </tr>
        <tr height="45">
          <td width="100" height="60" align="right"><strong>*</strong>电子邮箱：</td>
          <td><input type="text" class="inp188" name="paramMap.email" id="email" value="${map.email}"  />
            <span style="color: red; float: none;" id="u_email" class="formtips"></span></td>
        </tr>
        

        <tr height="10"></tr>
        
        <tr height="10">
                  <td align="center" colspan="2">
                      <span style="color: red;">注：身份信息保存后将无法修改</span>
                  </td>
         </tr>
         <tr height="5"></tr>    
          <tr>
            <td colspan="2" align="center"><input type="button" value="保存" class="bcbtn" id="jc_btn" style="${request.realName!=null && request.realName!='' ?'display: none;':''}"/>
          </td>
          </tr>

      </table>
    </div></div>
    </div>
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>

<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script> 
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script> 
<script type="text/javascript" src="common/date/calendar.js"></script> 
<script type="text/javascript" src="css/popom.js"></script> 
<script>
		$(function() {
			//样式选中
			$(".wdzh_next_left li").eq(1).addClass("wdzh_next_left_ong").find("a").attr("style","color: rgb(233, 71, 24);");
		//	dqzt(0);
		//	$('#li_1').addClass('on');
			if('${map.auditStatus}'=="3"){
				$("#registedPlacePro").attr("disabled",true);
				$("#registedPlaceCity").attr("disabled",true);
				$("#email").attr("disabled",true);
				
				var t = '${map.idNo}' == '' ? '' : '${map.idNo}';
				var len = t.length;
				if (len == 15) {
					var tmplen = t.substr(0, 2);
					//$('#idNo1').html(tmplen + '***** **** ****');
				} else if (len == 18) {
					var tttttt = t.substr(0, 2);
					//$('#idNo1').html(tttttt + '**** **** **** ****');
				}
			}
		});
	</script> 
<script type="text/javascript">
		//提交基本资料 start   
		$(document).ready(function() {
			$("input").focus(function(){
				$(this).next().html("");
			});
			$("input").blur(function() {
				//验证号码
				var mdd = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
				
				//真实姓名
				if ($(this).attr("id") == "realName") {
					var isName = new Object();
					isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
					if ($(this).val() == "") {
						$("#u_realName").attr("class", "formtips onError");
						$("#u_realName").html("请填写真实姓名！");
					} else if ($(this).val().length < 2 || $(this).val().length > 20) {
						$("#u_realName").attr("class", "formtips onError");
						$("#u_realName").html("名字长度为2-20个字符");
					} else if (!isName.test($(this).val())) {
						$("#u_realName").html("真实姓名输入有误");
					} else {
						$("#u_realName").attr("class", "formtips");
						$("#u_realName").html("");
					}
				}
				//========
				//身份号码
				if ($(this).attr("id") == "idNo" ) {
					var isIDCard1 = new Object();
					var isIDCard2 = new Object();
					//身份证正则表达式(15位) 
					isIDCard1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
					//身份证正则表达式(18位) 
					isIDCard2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/;
					if ($(this).val() == "") {
						//alert("请填写身份证号码");
						 $("#u_idNo").html("请填写身份号码！");
					} else if (isIDCard1.test($(this).val()) || isIDCard2.test($(this).val())) {
						//验证身份证号码的有效性
						$(this).next().html("");
						var parama = {};
						parama["paramMap.idNo"] = $(this).val();
						$.post("/isIDNO.do", parama, function(data) {
							if (data.putStr == "0") {
								$("#u_idNo").html("请填写身份证号码");
							} else if (data.putStr == "1") {
								$("#u_idNo").html("身份证号码不合法!");
							}
						});
					} else {
						$(this).next().html("身份证号码不正确");
					}
				}
				//========== 验证出生年月
				if ($(this).attr("id") == "birthday") {
					if ($(this).val() != "") {
						$("#u_birthday").html("");
					}
				}
			});
			$("#province").change(function() {
				var provinceId = $("#province").val();
				citySelectInit(provinceId, 2);
			});
			$("#registedPlacePro").change(function() {
				var provinceId = $("#registedPlacePro").val();
				registedPlaceCity(provinceId, 2);
			});

		});
		function registedPlaceCity(parentId, regionType) {
			var _array = [];
			_array.push("<option value='-1'>--请选择--</option>");
			var param = {};
			param["regionType"] = regionType;
			param["parentId"] = parentId;
			$.post("ajaxqueryRegion.do", param, function(data) {
				for ( var i = 0; i < data.length; i++) {
					_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
				}
				$("#registedPlaceCity").html(_array.join(""));
			});
		}

		function citySelectInit(parentId, regionType) {
			var _array = [];
			_array.push("<option value='-1'>--请选择--</option>");
			var param = {};
			param["regionType"] = regionType;
			param["parentId"] = parentId;
			$.post("ajaxqueryRegion.do", param, function(data) {
				for ( var i = 0; i < data.length; i++) {
					_array.push("<option value='"+data[i].regionId+"'>" + data[i].regionName + "</option>");
				}
				$("#city").html(_array.join(""));
			});
		}

		//提交基础资料
		$("#jc_btn").click(function() {

			var tsex = '${map.sex}' == '' ? '' : '${map.sex}';
			if ($("#realName").val() == "") {
				 $("#u_realName").html("请填写真实姓名！");
				return false
			}else{
                var isName = new Object();
                isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
                if ($("#realName").val() == "") {
                    $("#u_realName").attr("class", "formtips onError");
                    $("#u_realName").html("请填写真实姓名！");
                    return false
                } else if ($("#realName").val().length < 2 || $("#realName").val().length > 20) {
                    $("#u_realName").attr("class", "formtips onError");
                    $("#u_realName").html("名字长度为2-20个字符");
                    return false
                } else if (!isName.test($("#realName").val())) {
                    $("#u_realName").html("真实姓名输入有误");
                    return false
                } else {
                    $("#u_realName").attr("class", "formtips");
                    $("#u_realName").html("");
                }

            }


			if ($("#idNo").val() == "") {
				 $("#u_idNo").html("请填写身份号码！");
				return false
			}
			 if ($("#email").val() == "") {
				 $("#u_email").html("请填电子邮箱！");
				return false
			} 

			var mail = $("#email").val();
            //对电子邮件的验证
	            if(!/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(mail))
	            {
	               alert('请输入有效的电子邮箱！');
	               $("#email").focus();
	               return false;
	           	} 
			
			/* if ($("#registedPlacePro").val() == "-1") {
				 $("#u_reg_Pro_City").html("请选择省份！");
				return false
			}
			if ($("#registedPlaceCity").val() == "-1") {
				 $("#u_reg_Pro_City").html("请选择城市！");
				return false
			} */

			
			var param = {};
			param["paramMap.realName"] = $("#realName").val();
			param["paramMap.idNo"] = $("#idNo").val();
			param["paramMap.cellPhone"] = $("#cellPhone").val();
			param["paramMap.vilidataNum"] = $("#vilidataNum").val();
			param["paramMap.sex"] = $("input[name='paramMap_sex']:checked").val();
			param["paramMap.birthday"] = $("#birthday").val();
			param["paramMap.highestEdu"] = $("#highestEdu").val();
			param["paramMap.eduStartDay"] = $("#eduStartDay").val();
			param["paramMap.school"] = $("#school").val();
			param["paramMap.maritalStatus"] = $("input[name='paramMap_maritalStatus']:checked").val();
			param["paramMap.hasChild"] = $("input[name='paramMap_hasChild']:checked").val();
			param["paramMap.hasHourse"] = $("input[name='paramMap_hasHourse']:checked").val();
			param["paramMap.hasHousrseLoan"] = $("input[name='paramMap_hasHousrseLoan']:checked").val();
			param["paramMap.hasCar"] = $("input[name='paramMap_hasCar']:checked").val();
			param["paramMap.hasCarLoan"] = $("input[name='paramMap_hasCarLoan']:checked").val();
			param["paramMap.nativePlacePro"] = $("#province").val();
			param["paramMap.nativePlaceCity"] = $("#city").val();
			//param["paramMap.registedPlacePro"] = $("#registedPlacePro").val();
			//param["paramMap.registedPlaceCity"] = $("#registedPlaceCity").val();

			param["paramMap.address"] = $("#registedPlacePro").find("option:selected").text() + "-" + $("#registedPlaceCity").find("option:selected").text();
			param["paramMap.telephone"] = $("#telephone").val();
			param["paramMap.iscellPhone"] = $("#iscellPhone").val();
			param["paramMap.email"] = $("#email").val();
			param["paramMap.num"] = "1";
			$.post("/updateBasedate.do", param, function(data) {
				if (data.msg == "保存成功") {
					alert("保存成功");
                    var fromUrl = "${param.fromUrl}";
                    if(fromUrl==""){
                        fromUrl = "home.do";
                    }else{
                        fromUrl = decodeURI(fromUrl);
                    }
					window.location.href=fromUrl;
				}else{
					alert(data.msg);
				}
			});
		});


		function uploadCall(basepath, fileName, cp) {
			if (cp == "img") {
				var path = "upload/" + basepath + "/" + fileName;
				$("#img").attr("src", path);
				$("#setImg").attr("src", path);
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
</body>
</html>
