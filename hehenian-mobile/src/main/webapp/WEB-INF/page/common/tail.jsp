<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${ empty loginId or loginId le 0}">
<footer class="pro-ft-unlog">
	<p><a href="http://m.hehenian.com/login/index.do">立即登录</a>开启财富之门<a href="http://m.hehenian.com/login/index.do" class="log-fw"></a></p>
</footer>
</c:if>
<div class="ph-2"></div>
<c:if test="${ loginId gt 0}">
<footer class="pro-ft">
	<c:choose>
		<c:when test="${sessionScope.channel == '1'}">
			<!-- <a href="http://m.hehenian.com/product/index.do"><span class="icon-home">首页</span></a> 
			<a href="http://m.hehenian.com/product/plist.do"> <span class="icon-pro">理财产品</span></a>
			<a href="http://m.hehenian.com/profile/index.do"> <span class="icon-me">我的账户</span></a> 
			<a href="http://m.hehenian.com/about/index.do"> <span class="icon-set">更多</span></a> -->
			<div class="nav-fixed">
				<a href="http://m.hehenian.com/product/index.do"> <span class="icon-home"> 首页 </span>
				</a> <a href="http://m.hehenian.com/product/plist.do"> <span class="icon-pro"> 理财产品 </span>
				</a> <a href="http://m.hehenian.com/profile/index.do"> <span class="icon-me"> 我的账户 </span>
				</a> <a href="http://m.hehenian.com/about/index.do"> <span class="icon-set"> 更多 </span>
				</a>
			</div>
		</c:when>
		<c:when test="${sessionScope.channel == '2' and utp != null}">
			<!-- <a href="http://m.hehenian.com/product/plist.do"><span class="icon-home">首页</span></a> 
			<a href="http://m.hehenian.com/profile/buy.do"> <span class="icon-pro">购买记录</span></a>
			<a href="http://m.hehenian.com/profile/buy.do?type=shouyi"> <span class="icon-me">收益记录</span></a> 
			<a href="http://m.hehenian.com/about/index.do"> <span class="icon-set">更多</span></a> -->
			<div class="nav-fixed">
				<a href="http://m.hehenian.com/product/plist.do"> <span class="icon-home"> 首页 </span>
				</a> <a href="http://m.hehenian.com/profile/buy.do"> <span class="icon-pro"> 理财产品 </span>
				</a> <a href="http://m.hehenian.com/profile/buy.do?type=shouyi"> <span class="icon-me"> 我的账户 </span>
				</a> <a href="http://m.hehenian.com/about/index.do"> <span class="icon-set"> 更多 </span>
				</a>
			</div>
		</c:when>
		<c:otherwise>
			<!-- <a href="http://m.hehenian.com/product/index.do"><span class="icon-home">首页</span></a> 
			<a href="http://m.hehenian.com/product/plist.do"> <span class="icon-pro">理财产品</span></a>
			<a href="http://m.hehenian.com/profile/index.do"> <span class="icon-me">我的账户</span></a> 
			<a href="http://m.hehenian.com/about/index.do"> <span class="icon-set">更多</span></a> -->
			<footer class="pro-ft">
				<!-- nav-current-[1~4] 表示当前 -->
				<div class="nav-fixed">
					<a href="http://m.hehenian.com/product/index.do"> <span class="icon-home"> 首页 </span>
					</a> <a href="http://m.hehenian.com/product/plist.do"> <span class="icon-pro"> 理财产品 </span>
					</a> <a href="http://m.hehenian.com/profile/index.do"> <span class="icon-me"> 我的账户 </span>
					</a> <a href="http://m.hehenian.com/about/index.do"> <span class="icon-set"> 更多 </span>
					</a>
				</div>
			</footer>
			</c:otherwise>
	</c:choose>
	
</footer>
<script type="text/javascript">
cah('${menuIndex}');
</script>
</c:if>
<script>
	$(function(){
		sbh();
	})
</script>