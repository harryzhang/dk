<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户资料修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
<style type="text/css">
.right{
 position:relative;
 top:-370px;
 left:500px;
 right:0px;
 margin:0px;
}
td{

}
td.rightTxt{
text-align:right;
font-weight:bold;
font-size:10pt;
}
.red{
color:red;
}
table.tab_css_3{
 width:100%;
}
table.tab_css_3 th {
 background-repeat::repeat-x;
 height:30px;
 color:#35031b;
}
table.tab_css_3 td{
 border:1px solid #ddd;
 padding:0.2em 1.5em;
}
table.tab_css_3 th{
 border:1px solid #fafafa;
 padding:0 2px 0;
}
table.tab_css_3 tr.tr_css{
 background-color:#fbd8e8;
}
.selectCity select{ padding:2px; border:1px solid #D7DEE3;}
</style>
</head>
<body>
	<div class="m-search">
		<input id="search" class="easyui-searchbox" searcher="" prompt="请输入用户ID或者登录账号" menu="#mm" />
		<div id="mm" style="width:120px">
			<div name="userId" iconCls="icon-ok">会员ID</div>
			<div name="loginInfo" iconCls="icon-ok">手机号码/邮箱</div>
		</div>
	</div>
	<div id="baseInfo">
		<div class="easyui-panel" title="用户若申请修改，请先核对用户信息再进行修改" style="width:800px;height:800px;">
			<div class="userinfoEdit">
				<form id="userInfoForm" method="post" action="/loginInfo/saveUserInfo.html">
					<table class="tab_css_3">
						<tbody>
							<tr>
								<td class="rightTxt">用户ID:</td>
								<td>
									<span id="userId"></span>
									<input type="hidden" name="userId" id="userIdHidden" value="${userId }"/>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">真实姓名:</td>
								<td>
									<span id="trueName"></span>
									<span id="trueNameTips" class="red"></span>
									<a id="cancelTrueName" href="javascript:void(0);" style="display:none;">取消实名认证</a>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">昵称:</td>
								<td>
									<input type="text" id="nickName" name="nickName"></input>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">性别:</td>
								<td>
									<select id="gender" class="easyui-combobox" name="gender" style="width:50px;" panelHeight="60px;">
										<option value="0">男</option>
										<option value="1">女</option>
									</select>
									<!-- <span id="genderCredit" class="red"></span> -->
								</td>
							</tr>
							<tr>
								<td class="rightTxt">年龄:</td>
								<td>
								<input type="text" id="birthDay" class="easyui-datebox" style="width:90x" name="birthDay"/>
								<span id="animalTxt" class="red"></span>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">婚姻状况:</td>
								<td>
									<select id="marriage" class="easyui-combobox" style="width:120px;" name="marriage" panelHeight="90px;"></select>
									<span style="display: none">（保密<input type="checkbox" id="marriageIsHide" name="marriageIsHide" value="1" disabled="disabled"></input>）</span> 
								</td>
							</tr>
							<tr>
								<td class="rightTxt">有无小孩:</td>
								<td>
									<select id="children" class="easyui-combobox" style="width:120px;" name="children" panelHeight="90px;"></select>
									<span style="display: none">（保密<input type="checkbox" id="childrenIsHide" name="childrenIsHide" value="1" disabled="disabled"></input>）</span> 
								</td>
							</tr>
							<tr>
								<td class="rightTxt">身高:</td>
								<td>
									<select id="height" class="easyui-combobox" style="width:120px;" name="height"></select>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">
									体重:
								</td>
								<td>
									<select id="weight" class="easyui-combobox" style="width:120px;" name="weight" disabled="disabled"></select>
									<font color="red">用户自行修改</font>
								</td>
							</tr>
							<tr class="selectCity">
								<td class="rightTxt">
									居住地:
								</td>
								<td>
									<select id="cmbProvince1" name="cmbProvince1"></select>
									<select id="cmbCity1" name="cmbCity1"></select>
									<select id="cmbArea1" name="cmbArea1"></select>
								</td>
							</tr>
							<tr class="selectCity">
								<td class="rightTxt">
									籍贯:
								</td>
								<td>
									<select id="cmbProvince2" name="cmbProvince2"></select>
									<select id="cmbCity2" name="cmbCity2"></select>
									<select id="cmbArea2" name="cmbArea2"></select>
								</td>
							</tr>
							<tr class="selectCity">
								<td class="rightTxt">
									户口所在地:
								</td>
								<td>
									<select id="cmbProvince3" name="cmbProvince3"></select>
									<select id="cmbCity3" name="cmbCity3"></select>
									<select id="cmbArea3" name="cmbArea3"></select>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">学历:</td>
								<td>
									<select id="education" class="easyui-combobox" style="width:120px;" name="education"></select>
									<span style="display: none">（保密<input type="checkbox" id="educationIsHide" name="educationIsHide" value="1" disabled="disabled"></input>）</span>
									<!-- <span id="educationTips" class="red"></span> -->
								</td>
							</tr>
							<tr>
								<td class="rightTxt">
									毕业院校:
								</td>
								<td>
									<span id="graduteSchool"></span>
									<!-- <span id="graduteSchoolTips" class="red"></span> -->
									<font color="red">用户自行修改</font>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">
									月收入:
								</td>
								<td>
									<select id="salary" name="salary" class="easyui-combobox" style="width:120px;"></select>
									（保密<input type="checkbox" id="salaryIsHide" name="salaryIsHide" value="1" disabled="disabled"></input>）
									<!-- <span id="salaryCredit" class="red"></span> -->
									<!-- <font color="red">用户自行修改</font> -->
								</td>
							</tr>
							<tr>
								<td class="rightTxt">
									职业:
								</td>
								<td>
									<select id="occupationBig" name="occupationBig" class="easyui-combobox" style="width:120px;margin-right:5px;" disabled="disabled"></select>
									<select id="occupationSmall" name="occupation" class="easyui-combobox" style="width:120px;" disabled="disabled"></select>
									<font color="red">用户自行修改</font>
								</td>
							</tr>
							<tr>
								<td class="rightTxt">
									企业名称:
								</td>
								<td>
									<input type="text" id="cmpName" name="cmpName" disabled="disabled"></input>
									（保密<input type="checkbox" id="cmpNameIsHide" name="cmpNameIsHide" value="1" disabled="disabled"></input>） 
									<!-- <span id="cmpNameTips" class="red"></span> -->
									<font color="red">用户若申请修改，请通知“市场部-关慧敏”处理</font>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align:center">
									<div class="btnBox">
										<a id="saveBtn" href="javascript:void(0);" class="easyui-linkbutton" plain="true" icon="icon-save">提交修改</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<input type="hidden" id="updateCount" value="${updateCount}"/>
			</div>
		</div>
	</div>
	
	<script src="../../js/city/jsAddress.js?v000"></script>
	<script type="text/javascript">
		//addressInit('cmbProvince1', 'cmbCity1', 'cmbArea1','北京','北京','北京');
		//addressInit('cmbProvince2', 'cmbCity2', 'cmbArea2','北京','北京','北京');
		//addressInit('cmbProvince3', 'cmbCity3', 'cmbArea3','北京','北京','北京');
	</script>
	
	<script type="text/javascript">
	
		var UserInfoIndex=function(){
			
			//直接将数值填入，会自动实现文本回填
			//像这样，$('#gender').combobox('select',gender);，gender是一个数字
			var marriages = ${marriages};
			var childrens = ${childrens};
			var educations = ${educations};
			
			var _this;
			var occupations = {};
			occupations.first = [
			 ['c100','销售'],
			 ['c200','客户服务'],
			 ['c300','计算机'],
			 ['c400','通信/电子'],
			 ['c500','生产/制造'],
			 ['c600','物流/仓储'],
			 ['c700','商贸/采购'],
			 ['c800','人事/行政'],
			 ['c900','高级管理'],
			 ['c1000','广告/市场'],
			 ['c1100','传媒/艺术'],
			 ['c1200','生物/制药'],
			 ['c1300','医疗/护理'],
			 ['c1400','金融/保险'],
			 ['c1500','建筑/地产'],
			 ['c1600','咨询/顾问'],
			 ['c1700','法律'],
			 ['c1800','财会/审计'],
			 ['c1900','教育/科研'],
			 ['c2000','服务业'],
			 ['c2100','交通运输'],
			 ['c2200','政府机构'],
			 ['c2300','军人/警察'],
			 ['c2400','农林牧渔'],
			 ['c2500','自由职业'],
			 ['c2600','在校学生'],
			 ['c2700','待业'],
			 ['c2800','其他行业']
		 ];
	
		occupations.second = 
				{
					'c100': [
						['101', '销售总监'],
						['102', '销售经理'],
						['103', '销售主管'],
						['104', '销售专员'],
						['105', '渠道/分销管理'],
						['106', '渠道/分销专员'],
						['107', '经销商'],
						['108', '客户经理'],
						['109', '客户代表'],
						['110', '其他']
					],
					'c200': [
						['201', '客服经理'],
						['202', '客服主管'],
						['203', '客服专员'],
						['204', '客服协调'],
						['205', '客服技术支持'],
						['206', '其他']
					],
					'c300': [
						['301','IT技术总监'],
						['302','IT技术经理'],
						['303','IT工程师'],
						['304','系统管理员'],
						['305','测试专员'],
						['306','运营管理'],
						['307','网页设计'],
						['308','网站编辑'],
						['309','网站产品经理'],
						['310','其他']
					],
					'c400': [
						['401','通信技术'],
						['402','电子技术'],
						['403','其他']
					],
					'c500': [
						['501','工厂经理'],
						['502','工程师'],
						['503','项目主管'],
						['504','营运经理'],
						['505','营运主管'],
						['506','车间主任'],
						['507','物料管理'],
						['508','生产领班'],
						['509','操作工人'],
						['510','安全管理'],
						['511','其他']
					],
					'c600': [
						['601','物流经理'],
						['602','物流主管'],
						['603','物流专员'],
						['604','仓库经理'],
						['605','仓库管理员'],
						['606','货运代理'],
						['607','集装箱业务'],
						['608','海关事物管理'],
						['609','报单员'],
						['610','快递员'],
						['611','其他']
					],
					'c700': [
						['701','商务经理'],
						['702','商务专员'],
						['703','采购经理'],
						['704','采购专员'],
						['705','外贸经理'],
						['706','外贸专员'],
						['707','业务跟单'],
						['708','报关员'],
						['709','其他']
					],
					'c800': [
						['801','人事总监'],
						['802','人事经理'],
						['803','人事主管'],
						['804','人事专员'],
						['805','招聘经理'],
						['806','招聘专员'],
						['807','培训经理'],
						['808','培训专员'],
						['809','秘书'],
						['810','文员'],
						['811','后勤'],
						['812','其他']
					],
					'c900': [
						['901','总经理'],
						['902','副总经理'],
						['903','合伙人'],
						['904','总监'],
						['905','经理'],
						['906','总裁助理'],
						['907','其他']
					],
					'c1000': [
						['1001','广告客户经理'],
						['1002','广告客户专员'],
						['1003','广告设计经理'],
						['1004','广告设计专员'],
						['1005','广告策划'],
						['1006','市场营销经理'],
						['1007','市场营销专员'],
						['1008','市场策划'],
						['1009','市场调研与分析'],
						['1010','市场拓展'],
						['1011','公关经理'],
						['1012','公关专员'],
						['1013','媒介经理'],
						['1014','媒介专员'],
						['1015','品牌经理'],
						['1016','品牌专员'],
						['1017','其他']
					],
					'c1100': [
						['1101','主编'],
						['1102','编辑'],
						['1103','作家'],
						['1104','撰稿人'],
						['1105','文案策划'],
						['1106','出版发行'],
						['1107','导演'],
						['1108','记者'],
						['1109','主持人'],
						['1110','演员'],
						['1111','模特'],
						['1112','经纪人'],
						['1113','摄影师'],
						['1114','影视后期制作'],
						['1115','设计师'],
						['1116','画家'],
						['1117','音乐家'],
						['1118','舞蹈'],
						['1119','其他']
					],
					'c1200': [
						['1201','生物工程'],
						['1202','药品生产'],
						['1203','临床研究'],
						['1204','医疗器械'],
						['1205','医药代表'],
						['1206','化工工程师'],
						['1207','其他']
					],
					'c1300': [
						['1301','医疗管理'],
						['1302','医生'],
						['1303','心理医生'],
						['1304','药剂师'],
						['1305','护士'],
						['1306','兽医'],
						['1307','其他']
					],
					'c1400': [
						['1401','投资'],
						['1402','保险'],
						['1403','金融'],
						['1404','银行'],
						['1405','证券'],
						['1406','其他']
					],
					'c1500': [
						['1501','建筑师'],
						['1502','工程师'],
						['1503','规划师'],
						['1504','景观设计'],
						['1505','房地产策划'],
						['1506','房地产交易'],
						['1507','物业管理'],
						['1508','其他']
					],
					'c1600': [
						['1601','专业顾问'],
						['1602','咨询经理'],
						['1603','咨询师'],
						['1604','培训师'],
						['1605','其他']
					],
					'c1700': [
						['1701','律师'],
						['1702','律师助理'],
						['1703','法务经理'],
						['1704','法务专员'],
						['1705','知识产权专员'],
						['1706','其他']
					],
					'c1800': [
						['1801','财务总监'],
						['1802','财务经理'],
						['1803','财务主管'],
						['1804','会计'],
						['1805','注册会计师'],
						['1806','审计师'],
						['1807','税务经理'],
						['1808','税务专员'],
						['1809','成本经理'],
						['1810','其他']
					],
					'c1900': [
						['1901','教授'],
						['1902','讲师/助教'],
						['1903','中学教师'],
						['1904','小学教师'],
						['1905','幼师'],
						['1906','教务管理人员'],
						['1907','职业技术教师'],
						['1908','培训师'],
						['1909','科研管理人员'],
						['1910','科研人员'],
						['1911','其他']
					],
					'c2000': [
						['2001','餐饮管理'],
						['2002','厨师'],
						['2003','餐厅服务员'],
						['2004','酒店管理'],
						['2005','大堂经理'],
						['2006','酒店服务员'],
						['2007','导游'],
						['2008','美容师'],
						['2009','健身教练'],
						['2010','商场经理'],
						['2011','零售店店长'],
						['2012','店员'],
						['2013','保安经理'],
						['2014','保安人员'],
						['2015','家政服务'],
						['2016','其他']
					],
					'c2100': [
						['2101','飞行员'],
						['2102','空乘人员'],
						['2103','地勤人员'],
						['2104','列车司机'],
						['2105','乘务员'],
						['2106','船长'],
						['2107','船员'],
						['2108','司机'],
						['2109','其他']
					],
					'c2200': [
						['2201','公务员'],
						['2202','其他']
					],
					'c2300': [
						['2301','军人/警察']
					],
					'c2400': [
						['2401','农林牧渔']
					],
					'c2500': [
						['2501','自由职业']
					],
					'c2600': [
						['2601','在校学生']
					],
					'c2700': [
						['2701','待业']
					],
					'c2800': [
						['2801','其他行业']
					]
				};
			return {
				searchUserData:function(userId){
					$.ajax({
						url:"/loginInfo/queryUserInfo.html",
						type:"post",
						data:{type:1,loginInfo:userId},
						success:function(data){
							_this.fillForm(data);
						}
					});
				},
				init:function(){
					_this=this;
					$('#search').searchbox({   
					    width:300,   
					    searcher:function(value,name){   
					       _this.searchLoginInfo(name,$.trim(value),_this);
					    },   
					    prompt:'请输入用户ID或者登录账号'  
					});
					
					$("#marriage").combobox({data:marriages});
					$("#children").combobox({data:childrens});
					$("#education").combobox({data:educations});
					
					var heightData=new Array();
					heightData.push({value:139,text:'140以下'});
					for(var i=140;i<210;i++){
						heightData.push({value:i,text:i});
					}
					heightData.push({value:211,text:'210以上'});
					$("#height").combobox({data:heightData});
					var salaryData=[{value:21, text:"2000及以下"}, {value:22, text:"2001-5000元"}, {value:23, text:"5001-8000元"}, {value:24, text:"8001-10000元"}, {value:25, text:"10001-20000元"}, {value:26, text:"20001-50000元"}, {value:27, text:"50000以上"}];
					$("#salary").combobox({data:salaryData});
					var occupationBig=new Array();
					for(var i=0,n=occupations.first.length;i<n;i++){
						var tmp=occupations.first[i];
						occupationBig.push({value:tmp[0],text:tmp[1]});
					}
					$("#occupationBig").combobox({data:occupationBig});
					
					//保存按钮
					$("#saveBtn").bind("click",function(){
						var data=_this.getSaveData();
						if(!data)return;
						
						if(data.userId){
							//判断字符串
							$("#userInfoForm").submit();
						}
						
					});
					//保存按钮
					
					var weightDatas=new Array();
					weightDatas.push({value:0,text:'未填写'});
					for (var i = 30; i <131; i++) {
						weightDatas.push({value:i,text:i});
					};
					weightDatas.push({value:131,text:'131以上'});
					$("#weight").combobox({data:weightDatas});
					var userId=$("#userIdHidden").val();
					if(userId && /^\d{5,8}$/.test(userId)){
						//alert('563:error');
						_this.searchUserData(userId);
					}
					$('#occupationBig').combobox({
						onChange:function(newValue,oldValue){
							if(!newValue){
								return;
							}
							var datas=_this.getOccupations();
							var occupationSmall=datas.second[newValue];
							var newOccupationDatas=new Array();
							for(var i=0,n=occupationSmall.length;i<n;i++){
								newOccupationDatas.push({value:occupationSmall[i][0],text:occupationSmall[i][1]});
							}
							$("#occupationSmall").combobox({data:newOccupationDatas});
						}
					});
					var updateCount=$("#updateCount").val();
					if(updateCount){
						updateCount=+updateCount;
						if(updateCount>0){
							$.messager.alert("修改成功","修改用户信息成功，修改后的用户信息如下");
							//alert('585:error');
							//searchUserData(userId);
						}else{
							$.messager.alert("修改失败","修改用户信息失败，请稍后再试");
						}
						
					}
				},
				getSaveData:function(){
					var userId=$.trim($("#userId").text());
					if(!userId || !/^\d{5,8}$/.test(userId)){
						return null;
					}
					var data={};
					data.userId=userId;
					return data;
				},
				searchLoginInfo:function(name,value,obj){
					if(!name || !value){
						$.messager.alert("输入错误","请输入用户ID或者登录账号");
						return;
					}
					var regex=/^(13|14|15|18)[0-9]{9}$|^[0-9a-z][a-z0-9\._-]{1,}@[a-z0-9-]{1,}[a-z0-9]\.[a-z\.]{1,}[a-z]$|^\d{5,8}$/;
					if(regex.test(value)){
						$.ajax({
							url:"/loginInfo/queryUserInfo.html",
							type:"post",
							data:{type:name=='userId'?1:2,loginInfo:value},
							success:function(data){
								obj.fillForm(data);
							}
						});
					}else{
						$.messager.alert("输入错误","输入格式不对，请重新输入");
						return;
					}
				},
				fillForm:function(result){
					if(result.status==1){
						var data=result.data;
						$("#userId").text(data.userId);
						$("#userIdHidden").val(data.userId);
						$("#nickName").val(data.nickName);
						if(data.idStatus){
							$("#trueName").text(data.trueName);
							//$("#trueNameTips").text("【已认证】");
							$("#gender").combobox('disable');   //不可用
							//$("#genderCredit").text("【已认证】");
							
							//生日可改吗
							//$("#birthDay").datebox({disabled: true});
							
							//$("#cancelTrueName").show().bind("click",function(){
								//var userId=data.userId;
								//$.ajax({
									//url:"/loginInfo/cancelTrueNameCredit.html",
									//type:"post",
									//data:{userId:data.userId},
									//success:function(data){
										//if(data.ret==1){
											//$.messager.alert("取消成功","取消验证成功");
											//alert('643:error');
											//_this.searchUserData(userId);
										//}
									//}
								//});
							//});
						}else{
							$("#trueName").text('未填写');
							$("#trueNameTips").text("");
							$("#cancelTrueName").hide().unbind("click");
							$("#gender").combobox('enable');   //可用 
							$("#genderCredit").text("【未认证】");
							$("#birthDay").datebox({disabled: false});
						}
						var gender=data.genderVal;
						//alert(gender);
						$('#gender').combobox('select',gender);
						$("#birthDay").datebox('setValue', data.birthDay);
						$("#animalTxt").text(data.age+"岁");
						
						//$("#marriage").combobox('select',data.marriageVal!=-1?data.marriageVal:'未填写');
						$("#marriage").combobox('select',data.marriageVal);
						if(data.marriageIsHide==1){
							document.getElementById("marriageIsHide").checked = true;
						}else{
							document.getElementById("marriageIsHide").checked = false;
						}
						
						$("#height").combobox('select',data.height);
						$("#weight").combobox('select',data.weight);
						
						$("#education").combobox('select',data.educationVal);
						//$("#education").combobox('select',data.education);
						if(data.educationIsHide==1){
							document.getElementById("educationIsHide").checked = true;
						}else{
							document.getElementById("educationIsHide").checked = false;
						}
						if(data.eStatus){
							$("#education").combobox('disable');   //不可用
							$("#educationTips").text("【已认证】");
							$("#graduteSchoolTips").text("【已认证】");
						}else{
							$("#education").combobox('enable');   //可用 
							$("#educationTips").text("【未认证】");
							$("#graduteSchoolTips").text("【未认证】");
						}
						$("#graduteSchool").text(data.graduateSchool);
						
						//$("#salary").combobox('select',(data.salaryVal || data.salaryVal==-1)?'未填写':data.salaryVal);
						//$("#salary").combobox('select', data.salary);
						$("#salary").combobox('select', data.salaryVal);
						//if(data.salaryIsHide==1){
							//document.getElementById("salaryIsHide").checked = true;
						//}else{
							//document.getElementById("salaryIsHide").checked = false;
						//}
						
						document.getElementById("salaryIsHide").checked = true;
						
						addressInit('cmbProvince1', 'cmbCity1', 'cmbArea1',data.provinceWork,data.cityWork,data.areaWork);
						addressInit('cmbProvince2', 'cmbCity2', 'cmbArea2',data.provinceHome,data.cityHome,data.areaHome);
						addressInit('cmbProvince3', 'cmbCity3', 'cmbArea3',data.provinceHouse,data.cityHouse,data.areaHouse);
						
						if(data.salaryCredit){
							$("#salaryCredit").text("【已认证】");
							$("#salary").combobox('disable');   //不可用
						}else{
							$("#salaryCredit").text("【未认证】");
							$("#salary").combobox('enable');   //可用 
						}
						var occupation=+data.occupationVal;
						if(occupation==-1){
							$("#occupationBig").combobox('select','未填写');
							$("#occupationSmall").combobox('select','未填写');
						}else{
							var occupationBig="c"+(occupation-occupation%100);
							var datas=_this.getOccupations();
							$("#occupationBig").combobox('select',occupationBig);
							var occupationSmall=datas.second[occupationBig];
							var newOccupationDatas=new Array();
							for(var i=0,n=occupationSmall.length;i<n;i++){
								newOccupationDatas.push({value:occupationSmall[i][0],text:occupationSmall[i][1]});
							}
							$("#occupationSmall").combobox({data:newOccupationDatas});
							$("#occupationSmall").combobox('select',occupation);
						}
						if(data.cmpName){
							$("#cmpName").val(data.cmpName);
						}else{
							$("#cmpName").val("未填写");
						}
						if(data.cmpNameIsHide==1){
							document.getElementById("cmpNameIsHide").checked = true;
						}else{
							document.getElementById("cmpNameIsHide").checked = false;
						}
						if(data.cmpNameCredit){
							$("#cmpNameTips").text("【已认证】");
						}else{
							$("#cmpNameTips").text("【未认证】");
						}
						
						//$("#children").combobox('select',(data.childrenVal || data.childrenVal==-1)?'未填写':data.childrenVal);
						//$("#children").combobox('select', data.children);
						$("#children").combobox('select', data.childrenVal);
						
						if(data.avatar=='无形象照'){
							$("#imageDiv").html("该用户未上传形象照");
						}else{
							$("#imageDiv").html("<img src='"+data.avatar+"'></img>");
						}
					}else{
						$("#userId").text("");
						$("#userIdHidden").val("");
						$("#nickName").val("");
						$("#trueName").text("");
						$("#trueNameTips").text("");
						$("#cancelTrueName").hide().unbind("click");
						$("#gender").combobox('enable');   //可用 
						$("#genderCredit").text("");
						$("#birthDay").datebox({disabled: false});
						$("#cmpName").text("");
						$("#cmpNameTips").text("");
						$("#imageDiv").html("");
					}
					
				},
				getOccupations:function(){
					return occupations;
				}
			}
		};
		
		$(function(){
			var userInfoIndex=new UserInfoIndex();
			userInfoIndex.init();
			window.userInfoIndex=userInfoIndex;
		});
	
	</script>
</body>
</html>