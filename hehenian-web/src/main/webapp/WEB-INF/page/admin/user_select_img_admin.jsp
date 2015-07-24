<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/includeJs.jsp"%>
<script type="text/javascript">
	$(function() {
		$("img").each(function() {
			var curr = $(this);

			var boxWidth = 300;
			var boxHeight = 150;

			var imgWidth = curr.width();
			var imgHeight = curr.height();
			//比较长宽比
			if ((boxWidth / boxHeight) >= (imgWidth / imgHeight)) {
				//重新设置img的width和height
				curr.width((boxHeight * imgWidth) / imgHeight);
				curr.height(boxHeight);
				//让图片居中显示
				var margin = (boxWidth - curr.width()) / 2;
				curr.css("margin-left", margin);
			} else {
				//重新设置img的width和height
				curr.width(boxWidth);
				curr.height((boxWidth * imgHeight) / imgWidth);
				//让图片居中显示
				var margin = (boxHeight - curr.height()) / 2;
				curr.css("margin-top", margin);
			}

		});
	});
</script>
<div>

	<s:iterator value="pageBean.page" var="bean" status="st">
		<br />
		<s:if test="#bean.imgPath==null || #bean.imgPath==''">
			<s:if test="#bean.source==2">
				<img src="../images/cai.png" />
			</s:if>
			<s:else>
				<img src="../image/xxxxxxxxx.jpg" alt="图片载入失败" />
			</s:else>
		</s:if>
		<s:else>
			<img src="../${bean.imgPath}" />
		</s:else>
		<br />
	</s:iterator>

</div>

