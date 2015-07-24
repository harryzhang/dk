<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
  <div class="baner">
         <div id="slide-index" >
            <div class="slides">
            	<s:if test="#request.imgList == null || #request.imgList.size == 0">
	                <div class="slide autoMaxWidth">
	                    <div id="bi_0" class="image"><a href="#"><img src="images/banner/SP2P-B1.jpg" width="980" height="224" /></a>
	                    </div>
	                    <div id="bt_0" class="text">
	                    </div>
	                    <div id="bb_0" class="button">
	                    </div>
	                </div>
	                <div class="slide autoMaxWidth">
	                    <div id="bi_0" class="image"><a href="#"><img src="images/banner/SP2P-B2.jpg" width="980" height="224" /></a>
	                    </div>
	                    <div id="bt_0" class="text">
	                    </div>
	                    <div id="bb_0" class="button">
	                    </div>
	                </div><div class="slide autoMaxWidth">
	                    <div id="bi_0" class="image"><a href="#"><img src="images/banner/SP2P-B3.jpg" width="980" height="224" /></a>
	                    </div>
	                    <div id="bt_0" class="text">
	                    </div>
	                    <div id="bb_0" class="button">
	                    </div>
	                </div>
	                <div class="slide autoMaxWidth">
	                    <div id="bi_0" class="image"><a href="#"><img src="images/banner/SP2P-B4.jpg" width="980" height="224" /></a>
	                    </div>
	                    <div id="bt_0" class="text">
	                    </div>
	                    <div id="bb_0" class="button">
	                    </div>
	                </div>
            	</s:if><s:else>
	            	<s:iterator value="#request.imgList" var="imglist" status="step">
	                <div class="slide autoMaxWidth">
	                    <div id="bi_0" class="image"><a href="#"><img src="${imglist.companyImg }" width="980" height="224" /></a>
	                    </div>
	                    <div id="bt_0" class="text">
	                    </div>
	                    <div id="bb_0" class="button">
	                    </div>
	                </div>
	                </s:iterator>
                </s:else>
            </div>
         	
        </div>
        <div class="control">
	    	<div style="width:980px; margin:auto; text-align:right">
	             <a href="#"></a><a href="#"> </a><a href="#"> </a><a href="#"> </a>
	        </div>
       	</div>
</div>
<div class="banner_ty"> </div>