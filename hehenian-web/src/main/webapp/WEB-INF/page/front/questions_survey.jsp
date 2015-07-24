<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="/include/head.jsp"></jsp:include>
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
			<script type="text/javascript">
			function  submit(){
				var total = 0;
				var questionScore = 0;
				var internet=0
				var intnetScore=0
				var degree=0
				var degreeScore=1
				var predilection=0
				var prediScore=0
				$("input[type='radio']:checked").each(function(){
					var attr = $(this).attr('class');
					var a = attr.split('_');
					if(Number(a[0])==1){
						total+=Number($(this).val());
						questionScore+=Number(a[1]);
					}
					if(Number(a[0])==2){
						predilection+=Number($(this).val());
						prediScore+=Number(a[1])
					}
					if(Number(a[0])==3){
						degree+=Number($(this).val());
						degreeScore+=Number(a[1])
					}
					if(Number(a[0])==4){
						internet+=Number($(this).val());
						intnetScore+=Number(a[1])
					}
				});
				param["paramMap.total"]=total;
				param["paramMap.questionScore"]=questionScore;
				param["paramMap.internet"]=internet;
				param["paramMap.intnetScore"]=intnetScore;
				param["paramMap.degree"]=degree;
				param["paramMap.degreeScore"]=degreeScore;
				param["paramMap.predilection"]=predilection;
				param["paramMap.prediScore"]=prediScore;	
				if($("input[type='radio']:checked").size()!=$("#questionsize").val()){
					alert("请完成所有的问卷 ………………")
					return false
				}
				else{
					$.post("addScore.do",param, function(data){
						if(data==3){
							alert("您已经填写过调查问卷！！");
						}
						if(data==1){
							alert("提交成功！！！");
							window.location.href='home.do';
						}
						if(data==2){
							alert("提交失败！！");
						}
					})
				}			
			}
		
		
		</script>
	</head>
	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>

		<div class="cle"></div>
		<div class="cle"></div>
	<div class="wytz_center">
        <div class="dcfk_wj">
            <h3>调查问卷</h3>
            <h4>调查问卷说明</h4>
            <table width="960" border="0">
            <s:hidden value="%{#request.questionsize}"  id ='questionsize'></s:hidden>
                <s:iterator var="bean" value="#request.questions" status="s"> 
                	<tr height="30">
                    <td>${s.count }，${bean.question}</td>
	                </tr>
	                <tr height="30">
	                    <td>
	                     <s:iterator value="#request.options.{?#this.questionId==#bean.id}" var="sbean">
	                    	<input type="radio" value="${sbean.score}" name="quesion_${bean.id }" class="${bean.type}_${bean.maxScore}"/><span>${ sbean.option}</span>
	                    </s:iterator>
	                    </td>
	                </tr>
                </s:iterator>
            </table>
			<p><input type="button" value="取消" onclick=" window.location.href='home.do'"/><input type="button" value="提交" onclick="submit()" /></p>
        </div>
        <div class="cle"></div>
    </div>
<div class="cle"></div>
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
	
	</body>
</html>
